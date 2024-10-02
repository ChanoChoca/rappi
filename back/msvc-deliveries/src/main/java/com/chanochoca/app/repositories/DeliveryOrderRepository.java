package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.models.DeliveryAddress;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryOrderRepository {
    Mono<Void> deleteByOrderId(Long orderId);
    Mono<DeliveryAddress> findByDeliveryIdAndOrderId(Long deliveryId, Long orderId);
    Flux<DeliveryAddress> findByDeliveryId(Long deliveryId);
}
