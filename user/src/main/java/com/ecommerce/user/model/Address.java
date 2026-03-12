package com.ecommerce.user.model;


import lombok.Data;
import lombok.NoArgsConstructor;


//Address is already a part of the user document
//No need to make this a seperate document
@Data
public class Address {

    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
