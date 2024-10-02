package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.models.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
}
