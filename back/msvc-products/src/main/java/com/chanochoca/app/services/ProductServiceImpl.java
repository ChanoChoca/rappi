package com.chanochoca.app.services;

import com.chanochoca.app.entity.models.Product;
import com.chanochoca.app.repositories.CategoryRepository;
import com.chanochoca.app.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Flux<Product> listByIds(Iterable<Long> ids) {
        return productRepository.findAllById(ids);
    }

    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product);
    }

    public Mono<Product> updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .flatMap(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setAvailable(updatedProduct.getAvailable());
                    product.setCategoryId(updatedProduct.getCategoryId());
                    product.setStock(updatedProduct.getStock());
                    return productRepository.save(product);
                });
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }

    public Mono<Boolean> isCategoryIdValid(Long categoryId) {
        return categoryRepository.findById(categoryId) // Verificar si la categoría existe
                .map(category -> category != null) // Devuelve true si la categoría existe
                .defaultIfEmpty(false); // Devuelve false si no se encuentra la categoría
    }
}
