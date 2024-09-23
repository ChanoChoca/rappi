package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.Category;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CategoryRepository extends ReactiveCrudRepository<Category, Long> {
}
