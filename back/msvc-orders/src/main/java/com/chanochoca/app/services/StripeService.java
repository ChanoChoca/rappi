package com.chanochoca.app.services;

import com.chanochoca.app.entity.ChargeRequest;
import com.chanochoca.app.entity.models.Payment;
import com.chanochoca.app.repositories.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    private final PaymentRepository paymentRepository;

    @Value("${stripe.api.secretKey}")
    private String secretKey;

    public StripeService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Mono<Charge> charge(ChargeRequest chargeRequest) {
        return Mono.fromCallable(() -> {
                    Map<String, Object> chargeParams = new HashMap<>();
                    chargeParams.put("amount", chargeRequest.getAmount());
                    chargeParams.put("currency", chargeRequest.getCurrency());
                    chargeParams.put("description", chargeRequest.getDescription());
                    chargeParams.put("source", chargeRequest.getStripeToken());

                    // Stripe's Charge.create method is blocking, so we wrap it in a reactive Mono
                    try {
                        return Charge.create(chargeParams);
                    } catch (StripeException e) {
                        throw new RuntimeException("Failed to create charge", e);
                    }
                })
                .flatMap(charge -> {
                    // Almacenar información del pago en la base de datos
                    Payment payment = new Payment(chargeRequest.getDescription(), chargeRequest.getAmount(),
                            chargeRequest.getCurrency().name(),
                            chargeRequest.getStripeEmail(),
                            charge.getId());

                    return paymentRepository.save(payment)
                            .then(Mono.just(charge)); // Devuelve el Charge después de guardar
                });
    }
}
