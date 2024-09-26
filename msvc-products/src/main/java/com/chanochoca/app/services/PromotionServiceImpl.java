package com.chanochoca.app.services;

import com.chanochoca.app.entity.Promotion;
import com.chanochoca.app.repositories.PromotionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Flux<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public Mono<Promotion> getPromotionById(Long id) {
        return promotionRepository.findById(id);
    }

    public Mono<Promotion> createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    public Mono<Promotion> updatePromotion(Long id, Promotion updatedPromotion) {
        return promotionRepository.findById(id)
                .flatMap(promotion -> {
                    promotion.setName(updatedPromotion.getName());
                    promotion.setDiscountPercentage(updatedPromotion.getDiscountPercentage());
                    promotion.setStartDate(updatedPromotion.getStartDate());
                    promotion.setEndDate(updatedPromotion.getEndDate());
                    promotion.setProductIds(updatedPromotion.getProductIds());
                    return promotionRepository.save(promotion);
                });
    }

    public Mono<Void> deletePromotion(Long id) {
        return promotionRepository.deleteById(id);
    }
}

