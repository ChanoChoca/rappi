package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.DeliveryPerson;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DeliveryPersonRepository extends ReactiveCrudRepository<DeliveryPerson, Long> {
}
