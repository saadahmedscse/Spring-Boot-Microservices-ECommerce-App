package org.saadahmedev.controller;

import org.saadahmedev.dto.ProductRequest;
import org.saadahmedev.dto.ProductResponse;
import org.saadahmedev.model.ApiResponseBody;
import org.saadahmedev.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts() {
        return productService.getProducts();
    }
}
