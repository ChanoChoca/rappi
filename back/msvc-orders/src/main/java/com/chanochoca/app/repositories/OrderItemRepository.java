package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.models.OrderItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderItemRepository extends ReactiveCrudRepository<OrderItem, Long> {
}
