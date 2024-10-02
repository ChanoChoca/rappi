package com.chanochoca.app.handler;

import com.chanochoca.app.entity.models.Product;
import com.chanochoca.app.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class ProductHandler {

    private final ProductService productService;

    public ProductHandler(ProductService productService) {
        this.productService = productService;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.getAllProducts(), Product.class);
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productService.getProductById(id)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(product -> productService.isCategoryIdValid(product.getCategoryId()) // Validar categoryId
                        .flatMap(isValid -> {
                            if (!isValid) {
                                return ServerResponse.badRequest().bodyValue("Invalid category ID");
                            }
                            return productService.createProduct(product)
                                    .flatMap(savedProduct -> ServerResponse.status(HttpStatus.CREATED)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(savedProduct));
                        }));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Product.class)
                .flatMap(product -> productService.isCategoryIdValid(product.getCategoryId()) // Validar categoryId
                        .flatMap(isValid -> {
                            if (!isValid) {
                                return ServerResponse.badRequest().bodyValue("Invalid category ID");
                            }
                            return productService.updateProduct(id, product)
                                    .flatMap(updatedProduct -> ServerResponse.ok()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(updatedProduct))
                                    .switchIfEmpty(ServerResponse.notFound().build());
                        }));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productService.deleteProduct(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getProductsByOrder(ServerRequest request) {
        List<Long> ids = request.queryParam("ids")
                .map(idsStr -> List.of(idsStr.split(",")).stream()
                        .map(Long::parseLong)
                        .toList())
                .orElse(Collections.emptyList());

        System.out.println("Verificación: " + ids.size());

        return productService.listByIds(ids)
                .collectList()
                .flatMap(products -> ServerResponse.ok().bodyValue(products))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
