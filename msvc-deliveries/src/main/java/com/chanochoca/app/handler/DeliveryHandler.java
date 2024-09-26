package com.chanochoca.app.handler;

import com.chanochoca.app.entity.Delivery;
import com.chanochoca.app.services.DeliveryService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DeliveryHandler {

    private final DeliveryService deliveryService;

    public DeliveryHandler(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public Mono<ServerResponse> getAllDeliveries(ServerRequest request) {
        Flux<Delivery> deliveries = deliveryService.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(deliveries, Delivery.class);
    }

    public Mono<ServerResponse> getDeliveryById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<Delivery> delivery = deliveryService.findById(id);
        return delivery
                .flatMap(d -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(d))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createDelivery(ServerRequest request) {
        Mono<Delivery> deliveryMono = request.bodyToMono(Delivery.class);
        return deliveryMono
                .flatMap(delivery -> deliveryService.save(delivery))
                .flatMap(d -> ServerResponse.created(request.uri()).contentType(MediaType.APPLICATION_JSON).bodyValue(d));
    }

    public Mono<ServerResponse> deleteDelivery(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return deliveryService.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
