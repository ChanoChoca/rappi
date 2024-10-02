package com.chanochoca.app.services;

import com.chanochoca.app.entity.models.Delivery;
import com.chanochoca.app.repositories.DeliveryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public Flux<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    public Mono<Delivery> findById(Long id) {
        return deliveryRepository.findById(id);
    }

    public Mono<Delivery> save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public Mono<Void> deleteById(Long id) {
        return deliveryRepository.deleteById(id);
    }
}
