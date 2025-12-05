package com.ecommerce.product.controller;



import com.ecommerce.product.DTO.ProductRequest;
import com.ecommerce.product.DTO.ProductResponse;
import com.ecommerce.product.service.productService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    public final productService productService;


    @PostMapping
    public ResponseEntity<ProductResponse> CreateProduct (@RequestBody ProductRequest productRequest){

        return new ResponseEntity<ProductResponse>(productService.CreateProduct(productRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest UpdatedproductRequest){

        return productService.updateProduct(id, UpdatedproductRequest)
        .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
    //List of ProductResponse, wrapped in a ResponseEntity

        return ResponseEntity.ok(productService.fetchAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById (@PathVariable long id, @RequestBody ProductRequest productRequest){

        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
        public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword){

        return ResponseEntity.ok(productService.searchProduct(keyword));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){ //Only returns ResponseEntity

       //using ternary operator
        boolean productDeleted = productService.deleteProduct(id);
        return productDeleted? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

        /*
        if (productService.deleteProduct(id)){
            return ResponseEntity.noContent().build();

        }
        else{
            return ResponseEntity.notFound().build();

        }*/



    }
}
