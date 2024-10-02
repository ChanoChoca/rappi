CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT,
                        user_id VARCHAR(36) NOT NULL,
                        order_date DATE NOT NULL,
                        total_price DECIMAL(10, 2) NOT NULL,
                        status VARCHAR(255) NOT NULL,
                        PRIMARY KEY (id));

CREATE TABLE orders_products (
                              id BIGINT AUTO_INCREMENT,
                              product_id BIGINT UNIQUE,
                              order_id BIGINT REFERENCES orders(id),
                              PRIMARY KEY (id)
);


CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    amount INT NOT NULL,
    currency VARCHAR(3) NOT NULL,
    stripe_email VARCHAR(255) NOT NULL,
    transaction_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
