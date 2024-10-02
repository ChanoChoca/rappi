package com.chanochoca.app;

import com.chanochoca.app.handler.CategoryHandler;
import com.chanochoca.app.handler.ProductHandler;
import com.chanochoca.app.handler.PromotionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> categoryRoutes(CategoryHandler categoryHandler) {
        return route()
                .GET("/categories", categoryHandler::getAllCategories)
                .GET("/categories/{id}", categoryHandler::getCategoryById)
                .POST("/categories", categoryHandler::createCategory)
                .PUT("/categories/{id}", categoryHandler::updateCategory)
                .DELETE("/categories/{id}", categoryHandler::deleteCategory)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productRoutes(ProductHandler productHandler) {
        return route(GET("/products/products-by-order"), productHandler::getProductsByOrder)
                .andRoute(GET("/products"), productHandler::getAllProducts)
                .andRoute(GET("/products/{id}"), productHandler::getProductById)
                .andRoute(POST("/products"), productHandler::createProduct)
                .andRoute(PUT("/products/{id}"), productHandler::updateProduct)
                .andRoute(DELETE("/products/{id}"), productHandler::deleteProduct);
    }

    @Bean
    public RouterFunction<ServerResponse> promotionRoutes(PromotionHandler handler) {
        return route(GET("promotions"), handler::list)
                .andRoute(GET("promotions/{id}"), handler::detail)
                .andRoute(POST("promotions"), handler::create)
                .andRoute(PUT("promotions/{id}"), handler::edit)
                .andRoute(DELETE("promotions/{id}"), handler::delete)
                .andRoute(PUT("promotions/asignar-product/{promotionId}"), handler::assignProduct)
                .andRoute(POST("promotions/crear-product/{promotionId}"), handler::createProduct)
                .andRoute(DELETE("promotions/eliminar-producto/{promotionId}"), handler::deleteProduct)
                .andRoute(DELETE("promotions/eliminar-promotion-product/{id}"), handler::deletePromotionProductById);
    }
}
