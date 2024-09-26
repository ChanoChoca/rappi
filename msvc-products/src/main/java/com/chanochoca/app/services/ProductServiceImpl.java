package com.chanochoca.app.services;

import com.chanochoca.app.entity.Product;
import com.chanochoca.app.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id);
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
                    product.setCategory(updatedProduct.getCategory());
                    product.setStock(updatedProduct.getStock());
                    return productRepository.save(product);
                });
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }
}
