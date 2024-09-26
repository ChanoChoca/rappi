package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.models.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
}
