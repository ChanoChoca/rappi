package com.chanochoca.app.handler;

import com.chanochoca.app.entity.Package;
import com.chanochoca.app.services.PackageService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PackageHandler {

    private final PackageService packageService;

    public PackageHandler(PackageService packageService) {
        this.packageService = packageService;
    }

    public Mono<ServerResponse> getAllPackages(ServerRequest request) {
        Flux<Package> packages = packageService.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(packages, Package.class);
    }

    public Mono<ServerResponse> getPackageById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<Package> packageMono = packageService.findById(id);
        return packageMono
                .flatMap(p -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(p))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createPackage(ServerRequest request) {
        Mono<Package> packageMono = request.bodyToMono(Package.class);
        return packageMono
                .flatMap(p -> packageService.save(p))
                .flatMap(p -> ServerResponse.created(request.uri()).contentType(MediaType.APPLICATION_JSON).bodyValue(p));
    }

    public Mono<ServerResponse> deletePackage(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return packageService.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
