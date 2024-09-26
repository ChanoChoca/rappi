package com.chanochoca.app.repositories;

import com.chanochoca.app.entity.Package;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PackageRepository extends ReactiveCrudRepository<Package, Long> {
}
