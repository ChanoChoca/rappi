package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.models.Delivery;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DeliveryRepository extends ReactiveCrudRepository<Delivery, Long> {
}
