package com.chanochoca.app;

import com.chanochoca.app.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> stripeRoutes(StripeHandler stripeHandler) {
        return route(POST("/create-payment-intent"), stripeHandler::createPaymentIntent);
    }

    @Bean
    public RouterFunction<ServerResponse> orderRoutes(OrderHandler handler) {
        return route(GET("cursos"), handler::list)
                .andRoute(GET("cursos/{id}"), handler::detail)
                .andRoute(POST("cursos"), handler::create)
                .andRoute(PUT("cursos/{id}"), handler::edit)
                .andRoute(DELETE("cursos/{id}"), handler::delete)
                .andRoute(PUT("cursos/asignar-usuario/{orderId}"), handler::assignProduct)
                .andRoute(POST("cursos/crear-usuario/{orderId}"), handler::createProduct)
                .andRoute(DELETE("cursos/eliminar-usuario/{orderId}"), handler::deleteProduct)
                .andRoute(DELETE("cursos/eliminar-curso-usuario/{id}"), handler::deleteOrderProductById);
    }
}
