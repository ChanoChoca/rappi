package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.models.OrderProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderProductRepository extends ReactiveCrudRepository<OrderProduct, Long> {
    Mono<Void> deleteByProductId(Long productId);
    Mono<OrderProduct> findByOrderIdAndProductId(Long orderId, Long productId);
    Flux<OrderProduct> findByOrderId(Long orderId);
}
