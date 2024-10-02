package com.chanochoca.app.entity.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("payments") // Nombre de la tabla en la base de datos
public class Payment {
    @Id
    private Long id; // ID autogenerado
    private String description;
    private int amount;
    private String currency;
    private String stripeEmail;
    private String transactionId; // ID de la transacción de Stripe

    public Payment(String description, int amount, String currency, String stripeEmail, String transactionId) {
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.stripeEmail = stripeEmail;
        this.transactionId = transactionId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStripeEmail() {
        return stripeEmail;
    }

    public void setStripeEmail(String stripeEmail) {
        this.stripeEmail = stripeEmail;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
