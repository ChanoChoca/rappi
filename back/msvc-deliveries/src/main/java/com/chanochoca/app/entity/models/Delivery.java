package com.chanochoca.app.entity.models;

import com.chanochoca.app.entity.Order;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table("deliveries")
public class Delivery {

    @Id
    private Long id;

    @NotNull
    private Long riderId;

    @NotNull
    private LocalDateTime deliveryTime;

    @NotNull
    @Size(min = 1, max = 50)
    private String status;

    @Transient
    private List<DeliveryOrder> deliveryOrders;

    @Transient
    private List<Order> orders;

    @Transient
    private List<DeliveryAddress> deliveryAddresses;

    @Transient
    private List<Address> addresses;

    public Delivery() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Long getRiderId() {
        return riderId;
    }

    public void setRiderId(@NotNull Long riderId) {
        this.riderId = riderId;
    }

    public @NotNull LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(@NotNull LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public @NotNull @Size(min = 1, max = 50) String getStatus() {
        return status;
    }

    public void setStatus(@NotNull @Size(min = 1, max = 50) String status) {
        this.status = status;
    }

    public void addDeliveryOrder(DeliveryOrder deliveryOrder) {
        if (deliveryOrders == null) {
            deliveryOrders = new ArrayList<>();
        }
        this.deliveryOrders.add(deliveryOrder);
    }

    public void removeDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrders.remove(deliveryOrder);
    }

    public List<DeliveryOrder> getDeliveryOrders() {
        return deliveryOrders;
    }

    public void setDeliveryOrders(List<DeliveryOrder> deliveryOrders) {
        this.deliveryOrders = deliveryOrders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addDeliveryAddress(DeliveryAddress deliveryAddress) {
        if (deliveryAddresses == null) {
            deliveryAddresses = new ArrayList<>();
        }
        this.deliveryAddresses.add(deliveryAddress);
    }

    public void removeDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddresses.remove(deliveryAddress);
    }

    public List<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
