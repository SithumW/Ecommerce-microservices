package com.ecommerce.order.service;


import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional //To run the Delete Operations
public class cartService {

    //private final ProductRepository productRepository;
     private final CartItemRepository cartItemRepository;
    //private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {

        /* This kind of  validation cannot be done in microservices
        //---Validation---
        //Check the product exists
        Optional<Product> productOpt = productRepository.findById((request.getProductId()));
        if(productOpt.isEmpty()){
            return false;
        }

        //save product if found
        Product product = productOpt.get();

        //Check quantity
        if(product.getStockQuantity() < request.getQuantity()){
            return false;
        }

        //Check user exists
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){
            return false;
        }

        //save user from optional to product;
        User user = userOpt.get();

        */

        //--Check if product Already exists in user Cart or not --
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());

        if(existingCartItem != null){
            //Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity()+ request.getQuantity()); //quantity ++

            //we dont have product, cartItem dont have price. Hence we hardcode this to change later
            //existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            existingCartItem.setPrice(BigDecimal.valueOf(1000));
            cartItemRepository.save(existingCartItem);
        }
        else{
            //Create a new Cart Item
            CartItem newCartItem = new CartItem();
            newCartItem.setProductId(request.getProductId());
            newCartItem.setUserId(userId);
            newCartItem.setQuantity(request.getQuantity());
            newCartItem.setPrice(BigDecimal.valueOf(1000)); //change later
            cartItemRepository.save(newCartItem);

        }

        return true;
    }

    public boolean deleteItemFromCart (String userId, String productId){

        //---Validation---
        //Check the product and user exists
        // Optional<Product> productOpt = productRepository.findById(productId);
       // Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        /*

        if (productOpt.isPresent() && userOpt.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }


         */
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId,productId);

        if (cartItem != null){
            cartItemRepository.deleteByUserIdAndProductId(userId, productId);
            return true;
        }


        return  false;


    }


    public List<CartItemResponse> fetchCartItems(String userId) {

        //Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        //User user = userOpt.get();


        return cartItemRepository.findByUserId(userId).stream()
                .map(cartItem -> {
                    CartItemResponse cartItemResponse = new CartItemResponse(); //create new for each cartItem
                    cartItemResponse.setId(cartItem.getId());
                    cartItemResponse.setQuantity(cartItem.getQuantity());
                    cartItemResponse.setPrice(cartItem.getPrice());
                    cartItemResponse.setProductId(cartItem.getProductId());

                    return cartItemResponse;

                }).toList();



        /*
        Without using a CartResponse : Directly the cart will be returned.

        return userRepository.findById(Long.valueOf(userId))
        .map(cartItemRepository::findByUser)
        .orElseGet(List::of); // else return an empty list
        * */
    }




    public void clearCart(String userId) { //delete cart items of particular user
        cartItemRepository.deleteByUserId(userId);
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }
}
