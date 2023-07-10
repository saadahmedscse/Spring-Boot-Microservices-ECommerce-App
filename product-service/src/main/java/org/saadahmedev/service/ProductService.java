package org.saadahmedev.service;

import lombok.extern.slf4j.Slf4j;
import org.saadahmedev.dto.ProductRequest;
import org.saadahmedev.model.Product;
import org.saadahmedev.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product {} saved successfully", product.getId());
    }
}
