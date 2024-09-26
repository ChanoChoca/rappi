package com.chanochoca.app;

import com.chanochoca.app.handler.OrderHandler;
import com.chanochoca.app.handler.OrderItemHandler;
import com.chanochoca.app.handler.PaymentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> orderRoutes(OrderHandler orderHandler) {
        return RouterFunctions
                .route(GET("/orders"), orderHandler::getAllOrders)
                .andRoute(GET("/orders/{id}"), orderHandler::getOrderById)
                .andRoute(POST("/orders"), orderHandler::createOrder)
                .andRoute(PUT("/orders/{id}"), orderHandler::updateOrder)
                .andRoute(DELETE("/orders/{id}"), orderHandler::deleteOrder);
    }

    @Bean
    public RouterFunction<ServerResponse> orderItemRoutes(OrderItemHandler orderItemHandler) {
        return RouterFunctions
                .route(GET("/order-items"), orderItemHandler::getAllOrderItems)
                .andRoute(GET("/order-items/{id}"), orderItemHandler::getOrderItemById)
                .andRoute(POST("/order-items"), orderItemHandler::createOrderItem)
                .andRoute(PUT("/order-items/{id}"), orderItemHandler::updateOrderItem)
                .andRoute(DELETE("/order-items/{id}"), orderItemHandler::deleteOrderItem);
    }

    @Bean
    public RouterFunction<ServerResponse> paymentRoutes(PaymentHandler paymentHandler) {
        return RouterFunctions
                .route(GET("/payments"), paymentHandler::getAllPayments)
                .andRoute(GET("/payments/{id}"), paymentHandler::getPaymentById)
                .andRoute(POST("/payments"), paymentHandler::createPayment)
                .andRoute(PUT("/payments/{id}"), paymentHandler::updatePayment)
                .andRoute(DELETE("/payments/{id}"), paymentHandler::deletePayment);
    }
}
