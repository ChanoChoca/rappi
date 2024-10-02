package com.chanochoca.app.entity.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("promotions_products")
public class PromotionProduct {

    @Id
    private Long id;
    private Long promotionId;
    private Long productId;

    public PromotionProduct() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PromotionProduct)) {
            return false;
        }
        PromotionProduct o = (PromotionProduct) obj;
        return this.productId != null && this.productId.equals(o.productId) && this.promotionId != null && this.promotionId.equals(o.promotionId);
    }
}
