CREATE TABLE IF NOT EXISTS orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        order_date DATE NOT NULL,
                        total_price DECIMAL(10, 2) NOT NULL CHECK (total_price >= 0),
                        status VARCHAR(50) NOT NULL,
                        INDEX (user_id),
                        INDEX (order_date)
);

CREATE TABLE IF NOT EXISTS order_items (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             order_id BIGINT NOT NULL,
                             product_id BIGINT NOT NULL,
                             quantity INT NOT NULL CHECK (quantity > 0),
                             price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
                             FOREIGN KEY (order_id) REFERENCES orders(id),
                             FOREIGN KEY (product_id) REFERENCES products(id),
                             INDEX (order_id),
                             INDEX (product_id)
);

CREATE TABLE IF NOT EXISTS payments (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          order_id BIGINT NOT NULL,
                          amount DECIMAL(10, 2) NOT NULL CHECK (amount >= 0),
                          payment_method VARCHAR(50) NOT NULL,
                          payment_date DATE NOT NULL,
                          status VARCHAR(50) NOT NULL,
                          FOREIGN KEY (order_id) REFERENCES orders(id),
                          INDEX (order_id),
                          INDEX (payment_date)
);
