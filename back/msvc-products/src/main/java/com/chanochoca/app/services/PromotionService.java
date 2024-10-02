package com.chanochoca.app.services;

import com.chanochoca.app.entity.models.Product;
import com.chanochoca.app.entity.models.Promotion;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PromotionService {

    Flux<Promotion> list();
    Mono<Promotion> byId(Long id);
    Mono<Promotion> byIdWithProducts(Long id);
    Mono<Promotion> save(Promotion promotion);
    Mono<Void> delete(Long id);

    Mono<Void> deletePromotionProductById(Long id);

    Mono<Product> assignProduct(Product product, Long promotionId);
    Mono<Product> createProduct(Product product, Long promotionId);
    Mono<Product> deleteProduct(Product product, Long promotionId);
}
