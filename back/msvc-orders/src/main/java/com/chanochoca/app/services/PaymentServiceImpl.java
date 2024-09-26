package com.chanochoca.app.services;

import com.chanochoca.app.entity.models.Payment;
import com.chanochoca.app.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Flux<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Mono<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Mono<Payment> createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Mono<Payment> updatePayment(Long id, Payment payment) {
        return paymentRepository.findById(id)
                .flatMap(existingPayment -> {
                    existingPayment.setOrderId(payment.getOrderId());
                    existingPayment.setAmount(payment.getAmount());
                    existingPayment.setPaymentMethod(payment.getPaymentMethod());
                    existingPayment.setPaymentDate(payment.getPaymentDate());
                    existingPayment.setStatus(payment.getStatus());
                    return paymentRepository.save(existingPayment);
                });
    }

    public Mono<Void> deletePayment(Long id) {
        return paymentRepository.deleteById(id);
    }
}
