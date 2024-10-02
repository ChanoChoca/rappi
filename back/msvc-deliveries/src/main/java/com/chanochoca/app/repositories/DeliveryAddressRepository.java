package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.models.DeliveryAddress;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryAddressRepository {
    Mono<Void> deleteByAddressId(Long addressId);
    Mono<DeliveryAddress> findByDeliveryIdAndAddressId(Long deliveryId, Long addressId);
    Flux<DeliveryAddress> findByDeliveryId(Long deliveryId);
}
