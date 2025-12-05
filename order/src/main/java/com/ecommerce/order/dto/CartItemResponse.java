package com.ecommerce.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {

    private long id;
    private String productId;
    private Integer quantity;
    private BigDecimal price;


}
