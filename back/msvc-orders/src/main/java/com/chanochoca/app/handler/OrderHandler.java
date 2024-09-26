package com.chanochoca.app.handler;

import com.chanochoca.app.entity.models.Order;
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

//    @Value("${stripe.api.publicKey}")
//    private String publicKey;
//
//    @PostMapping("/cart-details/{id}")
//    public String cartDetails(@PathVariable Long id,
//                              @RequestParam("desde") LocalDate desde,
//                              @RequestParam("hasta") LocalDate hasta,
//                              Model model) {
//
//        Alquiler alquiler = alquilerService.get(id);
//        CartDetails cart = new CartDetails(desde, hasta, (int) (alquiler.getPrecio() * (ChronoUnit.DAYS.between(desde, hasta) + 1)));
//        cartDetailsService.save(cart);
//        alquiler.setCartDetails(cart);
//        alquilerService.save(alquiler);
//
//        model.addAttribute("publicKey", publicKey);
//        model.addAttribute("amount", cart.getTotal());
//        model.addAttribute("email", "");
//        model.addAttribute("productName", alquiler.getTitulo());
//
//        model.addAttribute("alquiler", alquiler);
//
//        return "visitantes/checkout";
//    }
}
