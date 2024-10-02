package com.chanochoca.app.services;

import com.chanochoca.app.entity.Product;
import com.chanochoca.app.entity.models.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

    Flux<Order> list();
    Mono<Order> byId(Long id);
    Mono<Order> byIdWithProducts(Long id);
    Mono<Order> save(Order order);
    Mono<Void> delete(Long id);

    Mono<Void> deleteOrderProductById(Long id);

    Mono<Product> assignProduct(Product product, Long orderId);
    Mono<Product> createProduct(Product product, Long orderId);
    Mono<Product> deleteProduct(Product product, Long orderId);
}
