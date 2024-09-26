package com.chanochoca.app.handler;

import com.chanochoca.app.entity.OrderItem;
import com.chanochoca.app.services.OrderItemService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OrderItemHandler {

    private final OrderItemService orderItemService;

    public OrderItemHandler(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    public Mono<ServerResponse> getAllOrderItems(ServerRequest request) {
        return ServerResponse.ok()
                .body(orderItemService.getAllOrderItems(), OrderItem.class);
    }

    public Mono<ServerResponse> getOrderItemById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return orderItemService.getOrderItemById(id)
                .flatMap(orderItem -> ServerResponse.ok().bodyValue(orderItem))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createOrderItem(ServerRequest request) {
        return request.bodyToMono(OrderItem.class)
                .flatMap(orderItemService::createOrderItem)
                .flatMap(orderItem -> ServerResponse.ok().bodyValue(orderItem));
    }

    public Mono<ServerResponse> updateOrderItem(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(OrderItem.class)
                .flatMap(orderItem -> orderItemService.updateOrderItem(id, orderItem))
                .flatMap(orderItem -> ServerResponse.ok().bodyValue(orderItem))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteOrderItem(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return orderItemService.deleteOrderItem(id)
                .then(ServerResponse.noContent().build());
    }
}
