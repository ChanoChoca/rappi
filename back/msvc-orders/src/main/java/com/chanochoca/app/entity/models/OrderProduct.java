package com.chanochoca.app.entity.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("orders_products")
public class OrderProduct {

    @Id
    private Long id;
    private Long orderId;
    private Long productId;

    public OrderProduct() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long itemId) {
        this.productId = itemId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OrderProduct)) {
            return false;
        }
        OrderProduct o = (OrderProduct) obj;
        return this.productId != null && this.productId.equals(o.productId) && this.orderId != null && this.orderId.equals(o.orderId);
    }
}
