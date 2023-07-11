package org.saadahmedev.service;

import org.saadahmedev.dto.OrderLineItemDto;
import org.saadahmedev.dto.OrderRequest;
import org.saadahmedev.model.Order;
import org.saadahmedev.model.OrderLineItem;
import org.saadahmedev.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItems(orderRequest.getOrderLineItemDtos()
                        .stream().map(this::mapToOrderLineItem).toList()).build();

        orderRepository.save(order);
    }

    private OrderLineItem mapToOrderLineItem(OrderLineItemDto orderLineItemDto) {
        return OrderLineItem.builder()
                .skuCode(orderLineItemDto.getSkuCode())
                .price(orderLineItemDto.getPrice())
                .quantity(orderLineItemDto.getQuantity()).build();
    }
}
