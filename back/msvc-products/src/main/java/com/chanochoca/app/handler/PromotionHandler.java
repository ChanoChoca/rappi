package com.chanochoca.app.handler;

import com.chanochoca.app.entity.models.Product;
import com.chanochoca.app.entity.models.Promotion;
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

    public Mono<ServerResponse> list(ServerRequest request) {
        return ServerResponse.ok().body(promotionService.list(), Promotion.class);
    }

    public Mono<ServerResponse> detail(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return promotionService.byIdWithProducts(id)
                .flatMap(promotion -> ServerResponse.ok().bodyValue(promotion))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Promotion.class)
                .flatMap(promotion -> promotionService.save(promotion))
                .flatMap(promotionDb -> ServerResponse.status(201).bodyValue(promotionDb))
                .onErrorResume(e ->
                        ServerResponse.badRequest().bodyValue("Error al crear promotion: " + e.getMessage()));
    }

    public Mono<ServerResponse> edit(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Promotion.class)
                .flatMap(promotion -> promotionService.byId(id)
                        .flatMap(existingPromotion -> {
                            existingPromotion.setName(promotion.getName());
                            existingPromotion.setDiscountPercentage(promotion.getDiscountPercentage());
                            existingPromotion.setStartDate(promotion.getStartDate());
                            existingPromotion.setEndDate(promotion.getEndDate());
                            return promotionService.save(existingPromotion);
                        })
                        .flatMap(updatedPromotion -> ServerResponse.status(201).bodyValue(updatedPromotion))
                        .switchIfEmpty(ServerResponse.notFound().build()))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al actualizar promotion"));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return promotionService.byId(id)
                .flatMap(promotion -> promotionService.delete(promotion.getId())
                        .then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> assignProduct(ServerRequest request) {
        Long promotionId = Long.valueOf(request.pathVariable("promotionId"));
        return request.bodyToMono(Product.class)
                .flatMap(product -> {
                    // Imprimir la información del usuario
                    System.out.println("Product recibido: " + product);
                    return promotionService.assignProduct(product, promotionId);
                })
                .flatMap(assignedProduct -> ServerResponse.status(201).bodyValue(assignedProduct))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al asignar product"));
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        Long promotionId = Long.valueOf(request.pathVariable("promotionId"));
        return request.bodyToMono(Product.class)
                .flatMap(product -> promotionService.createProduct(product, promotionId))
                .flatMap(productCreated -> ServerResponse.status(201).bodyValue(productCreated))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al crear product: " + e.getMessage()));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Long promotionId = Long.valueOf(request.pathVariable("promotionId"));
        return request.bodyToMono(Product.class)
                .flatMap(product -> promotionService.deleteProduct(product, promotionId))
                .flatMap(productRemoved -> ServerResponse.ok().bodyValue(productRemoved))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al eliminar product"));
    }

    public Mono<ServerResponse> deletePromotionProductById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return promotionService.deletePromotionProductById(id)
                .then(ServerResponse.noContent().build());
    }
}
