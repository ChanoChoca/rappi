package com.chanochoca.app.services;

import com.chanochoca.app.entity.models.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Flux<Category> getAllCategories();

    Mono<Category> getCategoryById(Long id);

    Mono<Category> createCategory(Category category);

    Mono<Category> updateCategory(Long id, Category updatedCategory);

    Mono<Void> deleteCategory(Long id);
}
