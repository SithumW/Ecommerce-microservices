package com.ecommerce.order.dto;

import lombok.Data;

@Data
public class CartItemRequest {

    //Only two things are taken from the user
    private String productId;
    private Integer quantity;


}
