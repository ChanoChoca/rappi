package com.chanochoca.app.handler;

import com.chanochoca.app.entity.Order;
import com.chanochoca.app.services.OrderService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OrderHandler {

    private final OrderService orderService;

    public OrderHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    public Mono<ServerResponse> getAllOrders(ServerRequest request) {
        return ServerResponse.ok()
                .body(orderService.getAllOrders(), Order.class);
    }

    public Mono<ServerResponse> getOrderById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return orderService.getOrderById(id)
                .flatMap(order -> ServerResponse.ok().bodyValue(order))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createOrder(ServerRequest request) {
        return request.bodyToMono(Order.class)
                .flatMap(orderService::createOrder)
                .flatMap(order -> ServerResponse.ok().bodyValue(order));
    }

    public Mono<ServerResponse> updateOrder(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Order.class)
                .flatMap(order -> orderService.updateOrder(id, order))
                .flatMap(order -> ServerResponse.ok().bodyValue(order))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteOrder(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return orderService.deleteOrder(id)
                .then(ServerResponse.noContent().build());
    }
}
