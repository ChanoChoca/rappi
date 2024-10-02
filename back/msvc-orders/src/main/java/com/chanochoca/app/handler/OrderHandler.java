package com.chanochoca.app.handler;

import com.chanochoca.app.entity.Product;
import com.chanochoca.app.entity.models.Order;
import com.chanochoca.app.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OrderHandler {

    private final OrderService orderService;

    @Autowired
    public OrderHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        return ServerResponse.ok().body(orderService.list(), Order.class);
    }

    public Mono<ServerResponse> detail(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return orderService.byIdWithProducts(id)
                .flatMap(curso -> ServerResponse.ok().bodyValue(curso))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Order.class)
                .flatMap(order -> orderService.save(order))
                .flatMap(orderDb -> ServerResponse.status(201).bodyValue(orderDb))
                .onErrorResume(e ->
                        ServerResponse.badRequest().bodyValue("Error al crear order: " + e.getMessage()));
    }

    public Mono<ServerResponse> edit(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Order.class)
                .flatMap(order -> orderService.byId(id)
                        .flatMap(existingOrder -> {
                            existingOrder.setOrderDate(order.getOrderDate());
                            existingOrder.setTotalPrice(order.getTotalPrice());
                            existingOrder.setStatus(order.getStatus());
                            return orderService.save(existingOrder);
                        })
                        .flatMap(updatedOrder -> ServerResponse.status(201).bodyValue(updatedOrder))
                        .switchIfEmpty(ServerResponse.notFound().build()))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al actualizar order"));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return orderService.byId(id)
                .flatMap(order -> orderService.delete(order.getId())
                        .then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> assignProduct(ServerRequest request) {
        Long orderId = Long.valueOf(request.pathVariable("orderId"));
        return request.bodyToMono(Product.class)
                .flatMap(product -> {
                    // Imprimir la información del usuario
                    System.out.println("Product recibido: " + product);
                    return orderService.assignProduct(product, orderId);
                })
                .flatMap(assignedProduct -> ServerResponse.status(201).bodyValue(assignedProduct))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al asignar product"));
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        Long orderId = Long.valueOf(request.pathVariable("orderId"));
        return request.bodyToMono(Product.class)
                .flatMap(product -> orderService.createProduct(product, orderId))
                .flatMap(productCreated -> ServerResponse.status(201).bodyValue(productCreated))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al crear product: " + e.getMessage()));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Long orderId = Long.valueOf(request.pathVariable("orderId"));
        return request.bodyToMono(Product.class)
                .flatMap(product -> orderService.deleteProduct(product, orderId))
                .flatMap(productRemoved -> ServerResponse.ok().bodyValue(productRemoved))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al eliminar product"));
    }

    public Mono<ServerResponse> deleteOrderProductById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return orderService.deleteOrderProductById(id)
                .then(ServerResponse.noContent().build());
    }
}
