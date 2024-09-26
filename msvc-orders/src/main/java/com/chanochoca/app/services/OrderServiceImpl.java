package com.chanochoca.app.services;

import com.chanochoca.app.entity.Order;
import com.chanochoca.app.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Mono<Order> createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Mono<Order> updateOrder(Long id, Order order) {
        return orderRepository.findById(id)
                .flatMap(existingOrder -> {
                    existingOrder.setUserId(order.getUserId());
                    existingOrder.setOrderDate(order.getOrderDate());
                    existingOrder.setTotalPrice(order.getTotalPrice());
                    existingOrder.setStatus(order.getStatus());
                    existingOrder.setItems(order.getItems());
                    return orderRepository.save(existingOrder);
                });
    }

    public Mono<Void> deleteOrder(Long id) {
        return orderRepository.deleteById(id);
    }
}
