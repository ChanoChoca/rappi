package com.chanochoca.app.services;

import com.chanochoca.app.entity.models.OrderItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderItemService {

    Flux<OrderItem> getAllOrderItems();

    Mono<OrderItem> getOrderItemById(Long id);

    Mono<OrderItem> createOrderItem(OrderItem orderItem);

    Mono<OrderItem> updateOrderItem(Long id, OrderItem orderItem);

    Mono<Void> deleteOrderItem(Long id);
}
