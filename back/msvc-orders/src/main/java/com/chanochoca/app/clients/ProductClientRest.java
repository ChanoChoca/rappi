package com.chanochoca.app.clients;

import com.chanochoca.app.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ProductClientRest {

    @Value("${ubication}")
    private static String ubication;

    private final WebClient webClient;

    @Autowired
    public ProductClientRest(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<Product> detalle(Long id) {
        return webClient.get()
                .uri("http://localhost:8001/products/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(product -> {
                    if (product.getId() == null) {
                        System.out.println("Error: El producto no tiene un id válido.");
                    } else {
                        System.out.println("Producto obtenido: " + product.getId());
                    }
                })
                .doOnError(e -> System.out.println("Error obteniendo producto: " + e.getMessage()));
    }

    public Mono<Product> crear(Product product) {
        System.out.println("Creando producto: " + product.getName() + " " + product.getDescription() + " " + product.getPrice() + " " + product.getAvailable() + " " + product.getCategoryId() + " " + product.getStock());
        return webClient.post()
                .uri("http://localhost:8001/products")
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class);
    }

    public Flux<Product> obtenerProductsPorOrder(Iterable<Long> ids) {
        // Convertir Iterable<Long> a una cadena de texto separada por comas
        String idsParam = StreamSupport.stream(ids.spliterator(), false)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        System.out.println(idsParam);
        return webClient.get()
                .uri("http://localhost:8001/products/products-by-order?ids=" + idsParam)
                .retrieve()
                .bodyToFlux(Product.class);
    }
}
