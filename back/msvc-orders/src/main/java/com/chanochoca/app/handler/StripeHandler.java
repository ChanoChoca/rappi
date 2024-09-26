package com.chanochoca.app.handler;

import com.chanochoca.app.entity.Request;
import com.chanochoca.app.entity.Response;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class StripeHandler {

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

                        PaymentIntent intent = PaymentIntent.create(params);

                        Response response = new Response(intent.getId(), intent.getClientSecret());
                        return ServerResponse.ok().bodyValue(response);

                    } catch (StripeException e) {
                        return ServerResponse.status(500).bodyValue("Error processing payment: " + e.getMessage());
                    }
                });
    }
}
