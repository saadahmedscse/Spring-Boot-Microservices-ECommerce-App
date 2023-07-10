package org.saadahmedev.controller;

import org.saadahmedev.dto.ProductRequest;
import org.saadahmedev.model.ApiResponseBody;
import org.saadahmedev.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseBody createProduct(ProductRequest productRequest) {
        productService.createProduct(productRequest);
        return new ApiResponseBody(true, "Product created successfully");
    }
}
