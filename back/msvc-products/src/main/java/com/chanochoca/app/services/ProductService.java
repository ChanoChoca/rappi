package com.chanochoca.app.services;

import com.chanochoca.app.entity.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> getAllProducts();

    Mono<Product> getProductById(Long id);

    Flux<Product> listByIds(Iterable<Long> ids);

    Mono<Product> createProduct(Product product);

    Mono<Product> updateProduct(Long id, Product updatedProduct);

    Mono<Void> deleteProduct(Long id);

    Mono<Boolean> isCategoryIdValid(Long categoryId);
}
