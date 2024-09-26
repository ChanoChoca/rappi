package com.chanochoca.app.handler;

import com.chanochoca.app.entity.Payment;
import com.chanochoca.app.services.PaymentService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PaymentHandler {

    private final PaymentService paymentService;

    public PaymentHandler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public Mono<ServerResponse> getAllPayments(ServerRequest request) {
        return ServerResponse.ok()
                .body(paymentService.getAllPayments(), Payment.class);
    }

    public Mono<ServerResponse> getPaymentById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return paymentService.getPaymentById(id)
                .flatMap(payment -> ServerResponse.ok().bodyValue(payment))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createPayment(ServerRequest request) {
        return request.bodyToMono(Payment.class)
                .flatMap(paymentService::createPayment)
                .flatMap(payment -> ServerResponse.ok().bodyValue(payment));
    }

    public Mono<ServerResponse> updatePayment(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Payment.class)
                .flatMap(payment -> paymentService.updatePayment(id, payment))
                .flatMap(payment -> ServerResponse.ok().bodyValue(payment))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deletePayment(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return paymentService.deletePayment(id)
                .then(ServerResponse.noContent().build());
    }
}
