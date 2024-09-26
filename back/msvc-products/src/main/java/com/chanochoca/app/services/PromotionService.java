package com.chanochoca.app.services;

import com.chanochoca.app.entity.Promotion;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PromotionService {

    Flux<Promotion> getAllPromotions();

    Mono<Promotion> getPromotionById(Long id);

    Mono<Promotion> createPromotion(Promotion promotion);

    Mono<Promotion> updatePromotion(Long id, Promotion updatedPromotion);

    Mono<Void> deletePromotion(Long id);
}
