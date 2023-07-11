package org.saadahmedev.service;

import org.saadahmedev.dto.ApiResponseBody;
import org.saadahmedev.dto.InventoryResponse;
import org.saadahmedev.dto.OrderLineItemDto;
import org.saadahmedev.dto.OrderRequest;
import org.saadahmedev.model.Order;
import org.saadahmedev.model.OrderLineItem;
import org.saadahmedev.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ApiResponseBody placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItems(orderRequest.getOrderLineItemDtos()
                        .stream().map(this::mapToOrderLineItem).toList()).build();

        List<String> skuCodes = order.getOrderLineItems().stream().map(OrderLineItem::getSkuCode).toList();

        //Call Inventory Service and Place order if item is in stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponseArray != null;
        boolean allItemsAreInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if (allItemsAreInStock) {
            orderRepository.save(order);
            return new ApiResponseBody(true, "Order placed successfully");
        }
        return new ApiResponseBody(false, "Product is not available in stock");
    }

    private OrderLineItem mapToOrderLineItem(OrderLineItemDto orderLineItemDto) {
        return OrderLineItem.builder()
                .skuCode(orderLineItemDto.getSkuCode())
                .price(orderLineItemDto.getPrice())
                .quantity(orderLineItemDto.getQuantity()).build();
    }
}
