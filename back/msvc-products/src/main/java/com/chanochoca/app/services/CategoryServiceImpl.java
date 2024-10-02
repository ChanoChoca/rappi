package com.chanochoca.app.services;

import com.chanochoca.app.entity.models.Category;
import com.chanochoca.app.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Mono<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Mono<Category> createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Mono<Category> updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .flatMap(category -> {
                    category.setName(updatedCategory.getName());
                    category.setDescription(updatedCategory.getDescription());
                    return categoryRepository.save(category);
                });
    }

    public Mono<Void> deleteCategory(Long id) {
        return categoryRepository.deleteById(id);
    }
}
