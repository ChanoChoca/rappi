package com.chanochoca.app.services;

import com.chanochoca.app.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> getAllProducts();

    Mono<Product> getProductById(Long id);

    Mono<Product> createProduct(Product product);

    Mono<Product> updateProduct(Long id, Product updatedProduct);

    Mono<Void> deleteProduct(Long id);
}
