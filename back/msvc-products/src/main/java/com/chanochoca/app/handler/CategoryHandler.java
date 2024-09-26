package com.chanochoca.app.handler;

import com.chanochoca.app.entity.Category;
import com.chanochoca.app.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CategoryHandler {

    private final CategoryService categoryService;

    public CategoryHandler(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Mono<ServerResponse> getAllCategories(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.getAllCategories(), Category.class);
    }

    public Mono<ServerResponse> getCategoryById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return categoryService.getCategoryById(id)
                .flatMap(category -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(category))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createCategory(ServerRequest request) {
        return request.bodyToMono(Category.class)
                .flatMap(category -> categoryService.createCategory(category))
                .flatMap(savedCategory -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedCategory));
    }

    public Mono<ServerResponse> updateCategory(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Category.class)
                .flatMap(category -> categoryService.updateCategory(id, category))
                .flatMap(updatedCategory -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedCategory))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteCategory(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return categoryService.deleteCategory(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
