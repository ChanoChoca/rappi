package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.models.Promotion;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PromotionRepository extends ReactiveCrudRepository<Promotion, Long> {
}
