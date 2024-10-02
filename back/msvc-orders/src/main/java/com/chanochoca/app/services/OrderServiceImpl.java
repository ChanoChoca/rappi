package com.chanochoca.app.services;

import com.chanochoca.app.clients.ProductClientRest;
import com.chanochoca.app.entity.Product;
import com.chanochoca.app.entity.models.Order;
import com.chanochoca.app.entity.models.OrderProduct;
import com.chanochoca.app.repositories.OrderProductRepository;
import com.chanochoca.app.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductClientRest client;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProductRepository orderProductRepository, ProductClientRest client) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.client = client;
    }

    public Flux<Order> list() {
        // Obtener todos los cursos
        return orderRepository.findAll()
                .flatMap(order -> {
                    // Para cada curso, obtenemos sus CursoUsuarios
                    return orderProductRepository.findByOrderId(order.getId())
                            .collectList()  // Convertimos a lista
                            .flatMapMany(orderProducts -> {
                                // Agregamos cursoUsuarios a la entidad Curso
                                order.setOrderProducts(orderProducts);

                                // Ahora obtenemos los usuarios basados en los cursoUsuarios
                                List<Long> productIds = orderProducts.stream()
                                        .map(OrderProduct::getProductId)
                                        .collect(Collectors.toList());

                                // Si no hay usuarios relacionados, devolvemos el curso directamente
                                if (productIds.isEmpty()) {
                                    order.setProducts(Collections.emptyList());
                                    return Flux.just(order);
                                }

                                // Si hay usuarios, los buscamos en el cliente
                                return client.obtenerProductsPorOrder(productIds)
                                        .collectList()
                                        .map(products -> {
                                            // Si no se encuentran usuarios, se asigna una lista vacía
                                            order.setProducts(products.isEmpty() ? Collections.emptyList() : products);
                                            return order;
                                        })
                                        .onErrorResume(error -> {
                                            // En caso de error en la llamada al cliente, se asigna una lista vacía de usuarios
                                            order.setProducts(Collections.emptyList());
                                            return Mono.just(order);
                                        })
                                        .flux();
                            });
                });
    }

    public Mono<Order> byId(Long id) {
        // Obtener el curso por ID
        return orderRepository.findById(id)
                .flatMap(order -> {
                    // Obtener los CursoUsuarios relacionados con el curso
                    return orderProductRepository.findByOrderId(order.getId())
                            .collectList()
                            .map(cursoUsuarios -> {
                                // Asignar los CursoUsuarios al curso
                                order.setOrderProducts(cursoUsuarios);
                                return order;
                            });
                });
    }

    public Mono<Order> byIdWithProducts(Long id) {
        // Obtener el curso por id
        return orderRepository.findById(id)
                .flatMap(order -> {
                    // Obtener los CursoUsuarios relacionados
                    return orderProductRepository.findByOrderId(order.getId())
                            .collectList()
                            .flatMap(orderProducts -> {
                                // Asignar los CursoUsuarios al curso
                                order.setOrderProducts(orderProducts);

                                // Obtener los IDs de los usuarios a partir de CursoUsuarios
                                List<Long> productIds = orderProducts.stream()
                                        .map(OrderProduct::getProductId)
                                        .collect(Collectors.toList());

                                if (!productIds.isEmpty()) {
                                    // Obtener los usuarios por IDs de forma reactiva
                                    return client.obtenerProductsPorOrder(productIds)
                                            .collectList()
                                            .map(products -> {
                                                // Asignar los usuarios al curso
                                                order.setProducts(products);
                                                return order;
                                            });
                                } else {
                                    // Si no hay usuarios relacionados, devolvemos el curso sin usuarios
                                    return Mono.just(order);
                                }
                            });
                });
    }

    public Mono<Order> save(Order order) {
        return orderRepository.save(order);
    }

    public Mono<Void> delete(Long id) {
        return orderRepository.deleteById(id);
    }

    public Mono<Void> deleteOrderProductById(Long id) {
        // Eliminar el CursoUsuario basado en el usuarioId
        return orderProductRepository.deleteByProductId(id);
    }

    public Mono<Product> assignProduct(Product product, Long orderId) {
        return orderRepository.findById(orderId)
                .flatMap(order -> client.detalle(product.getId())
                        .flatMap(productMsvc -> {
                            System.out.println("Product test: " + productMsvc.getPrice() + productMsvc.getId());
                            OrderProduct cursoUsuario = new OrderProduct();
                            cursoUsuario.setProductId(productMsvc.getId());
                            cursoUsuario.setOrderId(orderId);
                            return orderProductRepository.save(cursoUsuario)
                                    .flatMap(savedOrderProduct -> {
                                        order.addOrderProduct(savedOrderProduct);
                                        return orderRepository.save(order)
                                                .thenReturn(productMsvc);
                                    });
                        })
                )
                .onErrorResume(e -> Mono.error(new RuntimeException("Error al asignar product al order", e)));
    }

    public Mono<Product> createProduct(Product product, Long orderId) {
        return orderRepository.findById(orderId)
                .flatMap(order -> client.crear(product)
                        .flatMap(newProductMsvc -> {
                            OrderProduct orderProduct = new OrderProduct();
                            orderProduct.setProductId(newProductMsvc.getId());
                            orderProduct.setOrderId(orderId);
                            System.out.println("Mensaje: " + orderProduct.getOrderId() + orderProduct.getProductId());
                            return orderProductRepository.save(orderProduct)
                                    .thenReturn(newProductMsvc);
                        })
                );
    }

    public Mono<Product> deleteProduct(Product product, Long orderId) {
        return orderRepository.findById(orderId)
                .flatMap(order -> client.detalle(product.getId())
                        .flatMap(productMsvc -> {
                            // Buscar y eliminar el CursoUsuario correspondiente
                            return orderProductRepository.findByOrderIdAndProductId(orderId, product.getId())
                                    .flatMap(orderProduct -> {
                                        order.removeOrderProduct(orderProduct);
                                        // Guardar el curso actualizado sin ese CursoUsuario
                                        return orderRepository.save(order)
                                                .then(orderProductRepository.delete(orderProduct)) // Eliminar la relación en cursoUsuarioRepository
                                                .thenReturn(productMsvc);
                                    });
                        })
                );
    }
}
