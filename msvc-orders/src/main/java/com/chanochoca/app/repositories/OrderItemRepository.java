package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.OrderItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderItemRepository extends ReactiveCrudRepository<OrderItem, Long> {
}
