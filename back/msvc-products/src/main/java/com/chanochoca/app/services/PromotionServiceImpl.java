package com.chanochoca.app.services;

import com.chanochoca.app.entity.models.Product;
import com.chanochoca.app.entity.models.Promotion;
import com.chanochoca.app.entity.models.PromotionProduct;
import com.chanochoca.app.repositories.ProductRepository;
import com.chanochoca.app.repositories.PromotionProductRepository;
import com.chanochoca.app.repositories.PromotionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionProductRepository promotionProductRepository;
    private final ProductRepository productRepository;  // Ahora se usa el repositorio de productos

    public PromotionServiceImpl(PromotionRepository promotionRepository, PromotionProductRepository promotionProductRepository, ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.promotionProductRepository = promotionProductRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Promotion> list() {
        return promotionRepository.findAll()
                .flatMap(promotion -> promotionProductRepository.findByPromotionId(promotion.getId())
                        .collectList()
                        .flatMapMany(promotionProducts -> {
                            promotion.setPromotionProducts(promotionProducts);
                            List<Long> productIds = promotionProducts.stream()
                                    .map(PromotionProduct::getProductId)
                                    .collect(Collectors.toList());

                            if (productIds.isEmpty()) {
                                promotion.setProducts(Collections.emptyList());
                                return Flux.just(promotion);
                            }

                            return productRepository.findAllById(productIds)
                                    .collectList()
                                    .map(products -> {
                                        promotion.setProducts(products);
                                        return promotion;
                                    })
                                    .onErrorResume(error -> {
                                        promotion.setProducts(Collections.emptyList());
                                        return Mono.just(promotion);
                                    })
                                    .flux();
                        }));
    }

    @Override
    public Mono<Promotion> byId(Long id) {
        return promotionRepository.findById(id)
                .flatMap(promotion -> promotionProductRepository.findByPromotionId(promotion.getId())
                        .collectList()
                        .map(promotionProducts -> {
                            promotion.setPromotionProducts(promotionProducts);
                            return promotion;
                        }));
    }

    @Override
    public Mono<Promotion> byIdWithProducts(Long id) {
        return promotionRepository.findById(id)
                .flatMap(promotion -> promotionProductRepository.findByPromotionId(promotion.getId())
                        .collectList()
                        .flatMap(promotionProducts -> {
                            promotion.setPromotionProducts(promotionProducts);
                            List<Long> productIds = promotionProducts.stream()
                                    .map(PromotionProduct::getProductId)
                                    .collect(Collectors.toList());

                            if (!productIds.isEmpty()) {
                                return productRepository.findAllById(productIds)
                                        .collectList()
                                        .map(products -> {
                                            promotion.setProducts(products);
                                            return promotion;
                                        });
                            } else {
                                return Mono.just(promotion);
                            }
                        }));
    }

    @Override
    public Mono<Promotion> save(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return promotionRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deletePromotionProductById(Long id) {
        return promotionProductRepository.deleteByProductId(id);
    }

    @Override
    public Mono<Product> assignProduct(Product product, Long promotionId) {
        return promotionRepository.findById(promotionId)
                .flatMap(promotion -> productRepository.findById(product.getId())
                        .flatMap(existingProduct -> {
                            PromotionProduct promotionProduct = new PromotionProduct();
                            promotionProduct.setProductId(existingProduct.getId());
                            promotionProduct.setPromotionId(promotionId);
                            return promotionProductRepository.save(promotionProduct)
                                    .flatMap(savedPromotionProduct -> {
                                        promotion.addPromotionProduct(savedPromotionProduct);
                                        return promotionRepository.save(promotion)
                                                .thenReturn(existingProduct);
                                    });
                        })
                );
    }

    @Override
    public Mono<Product> createProduct(Product product, Long promotionId) {
        return promotionRepository.findById(promotionId)
                .flatMap(promotion -> productRepository.save(product)
                        .flatMap(savedProduct -> {
                            PromotionProduct promotionProduct = new PromotionProduct();
                            promotionProduct.setProductId(savedProduct.getId());
                            promotionProduct.setPromotionId(promotionId);
                            return promotionProductRepository.save(promotionProduct)
                                    .thenReturn(savedProduct);
                        })
                );
    }

    @Override
    public Mono<Product> deleteProduct(Product product, Long promotionId) {
        return promotionRepository.findById(promotionId)
                .flatMap(promotion -> promotionProductRepository.findByPromotionIdAndProductId(promotionId, product.getId())
                        .flatMap(promotionProduct -> {
                            promotion.removePromotionProduct(promotionProduct);
                            return promotionRepository.save(promotion)
                                    .then(promotionProductRepository.delete(promotionProduct))
                                    .thenReturn(product);
                        })
                );
    }
}
//@Service
//public class PromotionServiceImpl implements PromotionService {
//
//    private final PromotionRepository promotionRepository;
//    private final PromotionProductRepository promotionProductRepository;
//
//    public PromotionServiceImpl(PromotionRepository promotionRepository, PromotionProductRepository promotionProductRepository) {
//        this.promotionRepository = promotionRepository;
//        this.promotionProductRepository = promotionProductRepository;
//    }
//
//    public Flux<Promotion> list() {
//        // Obtener todos los cursos
//        return promotionRepository.findAll()
//                .flatMap(promotion -> {
//                    // Para cada curso, obtenemos sus CursoUsuarios
//                    return promotionProductRepository.findByPromotionId(promotion.getId())
//                            .collectList()  // Convertimos a lista
//                            .flatMapMany(promotionProducts -> {
//                                // Agregamos cursoUsuarios a la entidad Curso
//                                promotion.setPromotionProducts(promotionProducts);
//
//                                // Ahora obtenemos los usuarios basados en los cursoUsuarios
//                                List<Long> productIds = promotionProducts.stream()
//                                        .map(PromotionProduct::getProductId)
//                                        .collect(Collectors.toList());
//
//                                // Si no hay usuarios relacionados, devolvemos el curso directamente
//                                if (productIds.isEmpty()) {
//                                    promotion.setProducts(Collections.emptyList());
//                                    return Flux.just(promotion);
//                                }
//
//                                // Si hay usuarios, los buscamos en el cliente
//                                return clients.obtenerProductsPorOrder(productIds)
//                                        .collectList()
//                                        .map(products -> {
//                                            // Si no se encuentran usuarios, se asigna una lista vacía
//                                            promotion.setProducts(products.isEmpty() ? Collections.emptyList() : products);
//                                            return promotion;
//                                        })
//                                        .onErrorResume(error -> {
//                                            // En caso de error en la llamada al cliente, se asigna una lista vacía de usuarios
//                                            promotion.setProducts(Collections.emptyList());
//                                            return Mono.just(promotion);
//                                        })
//                                        .flux();
//                            });
//                });
//    }
//
//    public Mono<Promotion> byId(Long id) {
//        // Obtener el curso por ID
//        return promotionRepository.findById(id)
//                .flatMap(promotion -> {
//                    // Obtener los CursoUsuarios relacionados con el curso
//                    return promotionProductRepository.findByPromotionId(promotion.getId())
//                            .collectList()
//                            .map(cursoUsuarios -> {
//                                // Asignar los CursoUsuarios al curso
//                                promotion.setPromotionProducts(cursoUsuarios);
//                                return promotion;
//                            });
//                });
//    }
//
//    public Mono<Promotion> byIdWithProducts(Long id) {
//        // Obtener el curso por id
//        return promotionRepository.findById(id)
//                .flatMap(promotion -> {
//                    // Obtener los CursoUsuarios relacionados
//                    return promotionProductRepository.findByPromotionId(promotion.getId())
//                            .collectList()
//                            .flatMap(promotionProducts -> {
//                                // Asignar los CursoUsuarios al curso
//                                promotion.setPromotionProducts(promotionProducts);
//
//                                // Obtener los IDs de los usuarios a partir de CursoUsuarios
//                                List<Long> productIds = promotionProducts.stream()
//                                        .map(PromotionProduct::getProductId)
//                                        .collect(Collectors.toList());
//
//                                if (!productIds.isEmpty()) {
//                                    // Obtener los usuarios por IDs de forma reactiva
//                                    return clients.obtenerProductsPorPromotion(productIds)
//                                            .collectList()
//                                            .map(products -> {
//                                                // Asignar los usuarios al curso
//                                                promotion.setProducts(products);
//                                                return promotion;
//                                            });
//                                } else {
//                                    // Si no hay usuarios relacionados, devolvemos el curso sin usuarios
//                                    return Mono.just(promotion);
//                                }
//                            });
//                });
//    }
//
//    public Mono<Promotion> save(Promotion promotion) {
//        return promotionRepository.save(promotion);
//    }
//
//    public Mono<Void> delete(Long id) {
//        return promotionRepository.deleteById(id);
//    }
//
//    public Mono<Void> deletePromotionProductById(Long id) {
//        // Eliminar el CursoUsuario basado en el usuarioId
//        return promotionProductRepository.deleteByProductId(id);
//    }
//
//    public Mono<Product> assignProduct(Product product, Long promotionId) {
//        return promotionRepository.findById(promotionId)
//                .flatMap(promotion -> clients.detalle(product.getId())
//                        .flatMap(productMsvc -> {
//                            System.out.println("Product test: " + productMsvc.getPrice() + productMsvc.getId());
//                            PromotionProduct cursoUsuario = new PromotionProduct();
//                            cursoUsuario.setProductId(productMsvc.getId());
//                            cursoUsuario.setPromotionId(promotionId);
//                            return promotionProductRepository.save(cursoUsuario)
//                                    .flatMap(savedPromotionProduct -> {
//                                        promotion.addPromotionProduct(savedPromotionProduct);
//                                        return promotionRepository.save(promotion)
//                                                .thenReturn(productMsvc);
//                                    });
//                        })
//                )
//                .onErrorResume(e -> Mono.error(new RuntimeException("Error al asignar product al order", e)));
//    }
//
//    public Mono<Product> createProduct(Product product, Long promotionId) {
//        return promotionRepository.findById(promotionId)
//                .flatMap(promotion -> clients.crear(product)
//                        .flatMap(newProductMsvc -> {
//                            PromotionProduct promotionProduct = new PromotionProduct();
//                            promotionProduct.setProductId(newProductMsvc.getId());
//                            promotionProduct.setPromotionId(promotionId);
//                            System.out.println("Mensaje: " + promotionProduct.getPromotionId() + promotionProduct.getProductId());
//                            return promotionProductRepository.save(promotionProduct)
//                                    .thenReturn(newProductMsvc);
//                        })
//                );
//    }
//
//    public Mono<Product> deleteProduct(Product product, Long promotionId) {
//        return promotionRepository.findById(promotionId)
//                .flatMap(promotion -> clients.detalle(product.getId())
//                        .flatMap(productMsvc -> {
//                            // Buscar y eliminar el CursoUsuario correspondiente
//                            return promotionProductRepository.findByPromotionIdAndProductId(promotionId, product.getId())
//                                    .flatMap(promotionProduct -> {
//                                        promotion.removePromotionProduct(promotionProduct);
//                                        // Guardar el curso actualizado sin ese CursoUsuario
//                                        return promotionRepository.save(promotion)
//                                                .then(promotionProductRepository.delete(promotionProduct)) // Eliminar la relación en cursoUsuarioRepository
//                                                .thenReturn(productMsvc);
//                                    });
//                        })
//                );
//    }
//}
