package com.chanochoca.app.handler;

import com.chanochoca.app.entity.Promotion;
import com.chanochoca.app.services.PromotionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PromotionHandler {

    private final PromotionService promotionService;

    public PromotionHandler(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public Mono<ServerResponse> getAllPromotions(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(promotionService.getAllPromotions(), Promotion.class);
    }

    public Mono<ServerResponse> getPromotionById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return promotionService.getPromotionById(id)
                .flatMap(promotion -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(promotion))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createPromotion(ServerRequest request) {
        return request.bodyToMono(Promotion.class)
                .flatMap(promotion -> promotionService.createPromotion(promotion))
                .flatMap(savedPromotion -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedPromotion));
    }

    public Mono<ServerResponse> updatePromotion(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Promotion.class)
                .flatMap(promotion -> promotionService.updatePromotion(id, promotion))
                .flatMap(updatedPromotion -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedPromotion))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deletePromotion(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return promotionService.deletePromotion(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
