package com.ecommerce.order.controller;


import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.service.cartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.WeakHashMap;

@Controller
@RequestMapping("/api/cart")
@RequiredArgsConstructor//inject the required arguments when obj created
public class CartController {

    private final cartService cartService;




    @GetMapping
    public ResponseEntity<List<CartItemResponse>> fetchCart(@RequestHeader("X-User-ID") String UserId){
        return ResponseEntity.ok(cartService.fetchCartItems(UserId));

    }





    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                          //Get userId of RequestHeader (To identify the owner of the cart)
                                          @RequestBody CartItemRequest request){
                                            //Get the product details (CartItem)

        if(cartService.addToCart(userId,request)){
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }
        else {
            return ResponseEntity.badRequest().body("Product out of stock or user not found or Product not found");

        }
        }




        @DeleteMapping("/items/{productId}")
        public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String UserId, @PathVariable String productId){

            boolean deleted = cartService.deleteItemFromCart(UserId, productId);

            return deleted? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }



    }

