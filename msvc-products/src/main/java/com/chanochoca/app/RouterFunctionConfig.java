package com.chanochoca.app;

import com.chanochoca.app.handler.CategoryHandler;
import com.chanochoca.app.handler.ProductHandler;
import com.chanochoca.app.handler.PromotionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> categoryRoutes(CategoryHandler categoryHandler) {
        return RouterFunctions.route()
                .GET("/categories", categoryHandler::getAllCategories)
                .GET("/categories/{id}", categoryHandler::getCategoryById)
                .POST("/categories", categoryHandler::createCategory)
                .PUT("/categories/{id}", categoryHandler::updateCategory)
                .DELETE("/categories/{id}", categoryHandler::deleteCategory)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productRoutes(ProductHandler productHandler) {
        return RouterFunctions.route()
                .GET("/products", productHandler::getAllProducts)
                .GET("/products/{id}", productHandler::getProductById)
                .POST("/products", productHandler::createProduct)
                .PUT("/products/{id}", productHandler::updateProduct)
                .DELETE("/products/{id}", productHandler::deleteProduct)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> promotionRoutes(PromotionHandler promotionHandler) {
        return RouterFunctions.route()
                .GET("/promotions", promotionHandler::getAllPromotions)
                .GET("/promotions/{id}", promotionHandler::getPromotionById)
                .POST("/promotions", promotionHandler::createPromotion)
                .PUT("/promotions/{id}", promotionHandler::updatePromotion)
                .DELETE("/promotions/{id}", promotionHandler::deletePromotion)
                .build();
    }
}
