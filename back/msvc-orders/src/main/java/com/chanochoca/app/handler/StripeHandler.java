package com.chanochoca.app.handler;

import com.chanochoca.app.entity.Request;
import com.chanochoca.app.entity.Response;
import com.chanochoca.app.entity.models.Payment;
import com.chanochoca.app.repositories.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class StripeHandler {

    private final PaymentRepository paymentRepository;

    public StripeHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Mono<ServerResponse> createPaymentIntent(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Request.class)
                .flatMap(request -> {
                    try {
                        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                                .setAmount(request.getAmount() * 100L)
                                .putMetadata("productName", request.getProductName())
                                .setCurrency("usd")
                                .setAutomaticPaymentMethods(PaymentIntentCreateParams
                                        .AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build())
                                .build();

                        // Crear el PaymentIntent
                        PaymentIntent intent = PaymentIntent.create(params);

                        // Persistir la información del pago en la base de datos
                        Payment payment = new Payment(
                                request.getProductName(), // o el campo que necesites
                                Math.toIntExact(request.getAmount()),
                                "usd", // puedes ajustar según la moneda que uses
                                request.getEmail(),
                                intent.getId()
                        );

                        return paymentRepository.save(payment) // Almacena el pago
                                .then(ServerResponse.ok().bodyValue(new Response(intent.getId(), intent.getClientSecret())));

                    } catch (StripeException e) {
                        return ServerResponse.status(500).bodyValue("Error processing payment: " + e.getMessage());
                    }
                });
    }
}
