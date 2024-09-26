package com.chanochoca.app.services;

import com.chanochoca.app.entity.models.Payment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentService {

    Flux<Payment> getAllPayments();

    Mono<Payment> getPaymentById(Long id);

    Mono<Payment> createPayment(Payment payment);

    Mono<Payment> updatePayment(Long id, Payment payment);

    Mono<Void> deletePayment(Long id);
}
