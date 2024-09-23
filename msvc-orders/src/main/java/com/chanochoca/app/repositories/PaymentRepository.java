package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.Payment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PaymentRepository extends ReactiveCrudRepository<Payment, Long> {
}
