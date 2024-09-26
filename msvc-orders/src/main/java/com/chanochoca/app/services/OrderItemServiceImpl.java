package com.chanochoca.app.services;

import com.chanochoca.app.entity.OrderItem;
import com.chanochoca.app.repositories.OrderItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public Flux<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public Mono<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    public Mono<OrderItem> createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public Mono<OrderItem> updateOrderItem(Long id, OrderItem orderItem) {
        return orderItemRepository.findById(id)
                .flatMap(existingOrderItem -> {
                    existingOrderItem.setProductId(orderItem.getProductId());
                    existingOrderItem.setQuantity(orderItem.getQuantity());
                    existingOrderItem.setPrice(orderItem.getPrice());
                    return orderItemRepository.save(existingOrderItem);
                });
    }

    public Mono<Void> deleteOrderItem(Long id) {
        return orderItemRepository.deleteById(id);
    }
}
