package com.ecommerce.product.DTO;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductResponse {
    //These info will be presented to the user
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private Boolean active;//whether the product is available in database
}
