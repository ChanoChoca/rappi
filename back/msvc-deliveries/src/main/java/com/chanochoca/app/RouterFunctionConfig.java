package com.chanochoca.app;

import com.chanochoca.app.handler.DeliveryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> deliveryRoutes(DeliveryHandler deliveryHandler) {
        return route()
                .GET("/deliveries", deliveryHandler::getAllDeliveries)
                .GET("/deliveries/{id}", deliveryHandler::getDeliveryById)
                .POST("/deliveries", deliveryHandler::createDelivery)
                .DELETE("/deliveries/{id}", deliveryHandler::deleteDelivery)
                .build();
    }

//    @Bean
//    public RouterFunction<ServerResponse> packageRoutes(PackageHandler packageHandler) {
//        return route()
//                .GET("/packages", packageHandler::getAllPackages)
//                .GET("/packages/{id}", packageHandler::getPackageById)
//                .POST("/packages", packageHandler::createPackage)
//                .DELETE("/packages/{id}", packageHandler::deletePackage)
//                .build();
//    }
}
