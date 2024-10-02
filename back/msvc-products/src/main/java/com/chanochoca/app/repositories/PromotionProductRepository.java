package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.models.PromotionProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PromotionProductRepository extends ReactiveCrudRepository<PromotionProduct, Long> {
    Mono<Void> deleteByProductId(Long productId);
    Mono<PromotionProduct> findByPromotionIdAndProductId(Long promotionId, Long productId);
    Flux<PromotionProduct> findByPromotionId(Long promotionId);
}
