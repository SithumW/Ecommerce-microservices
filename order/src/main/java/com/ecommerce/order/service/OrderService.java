package com.ecommerce.order.service;


import com.ecommerce.order.dto.OrderItemDTO;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.model.OrderItem;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderStatus;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final cartService cartService;
   // private final UserRepository userRepository;
   private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {


        //validate for cart items
        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()){
            return Optional.empty(); //Empty optional -> error

        }

        /*This user and product validation should be done on InterService Communication

        // validate for users
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()){
            return Optional.empty();
        }

        User user = userOpt.get();


         */


        //calculate total price
        BigDecimal totalPrice = cartItems.stream()//Convert cartItems to stream
                .map(CartItem::getPrice) //Getting price of each cart item (no need other columns)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                //Equivalent to for loop
                //BigDecimal.ZERO -> Initial value
                //Add price one by one


        //create order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);


        //We have a list of Cart Items, but we need a list of order items
        //to place inside an order

        List<OrderItem> orderItems = cartItems.stream()
                        .map(item-> new OrderItem( //create object for each cartItem
                                //The OrderItem should have @AllArgsConstructor
                                null, //id is auto generated
                                item.getProductId(),
                                item.getQuantity(),
                                item.getPrice(),
                                order
                                ))
                .toList(); //convert to list

        order.setItems(orderItems); //set the order list

        Order savedOrder = orderRepository.save(order);

        //clear the cart
        cartService.clearCart(userId);

        //we have saved order obj
        //return should be Optional of OrderResponse

        return Optional.of(mapToOrderResponse(savedOrder));

    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {

        return new OrderResponse( //creating obj here (convert Order to OrderResponse) using @Allargsconstructor
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getItems().stream()
                        .map(orderItem -> new OrderItemDTO( //Convert OrderItem to OrderItemDTO (OrderResponse need OrderItemDTO)
                                orderItem.getId(),
                                orderItem.getProductId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))


                        )).toList(),
                savedOrder.getCreatedAt()
        );



    }
}
