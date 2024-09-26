package com.chanochoca.app.services;

import com.chanochoca.app.entity.Package;
import com.chanochoca.app.repositories.PackageRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PackageService {

    private final PackageRepository packageRepository;

    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public Flux<Package> findAll() {
        return packageRepository.findAll();
    }

    public Mono<Package> findById(Long id) {
        return packageRepository.findById(id);
    }

    public Mono<Package> save(Package aPackage) {
        return packageRepository.save(aPackage);
    }

    public Mono<Void> deleteById(Long id) {
        return packageRepository.deleteById(id);
    }
}
