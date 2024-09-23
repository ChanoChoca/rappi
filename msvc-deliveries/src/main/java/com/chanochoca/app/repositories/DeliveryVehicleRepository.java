package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.DeliveryVehicle;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DeliveryVehicleRepository extends ReactiveCrudRepository<DeliveryVehicle, Long> {
}
