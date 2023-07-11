package org.saadahmedev.controller;

import org.saadahmedev.dto.ApiResponseBody;
import org.saadahmedev.dto.OrderRequest;
import org.saadahmedev.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseBody placeOrder(OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }
}
