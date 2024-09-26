CREATE TABLE IF NOT EXISTS categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            description VARCHAR(255),
                            UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description VARCHAR(255),
                          price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
                          available BOOLEAN NOT NULL DEFAULT TRUE,
                          category_id BIGINT NOT NULL,
                          stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
                          FOREIGN KEY (category_id) REFERENCES categories(id),
                          INDEX (category_id),
                          UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS promotions (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            discount_percentage DECIMAL(5, 2) NOT NULL CHECK (discount_percentage >= 0 AND discount_percentage <= 100),
                            start_date DATE NOT NULL,
                            end_date DATE NOT NULL,
                            UNIQUE(name)
);

ALTER TABLE promotions
    ADD CONSTRAINT promotions_chk_2 CHECK (end_date > start_date);

CREATE TABLE IF NOT EXISTS promotion_products (
                                    promotion_id BIGINT,
                                    product_id BIGINT,
                                    PRIMARY KEY (promotion_id, product_id),
                                    FOREIGN KEY (promotion_id) REFERENCES promotions(id),
                                    FOREIGN KEY (product_id) REFERENCES products(id),
                                    INDEX (promotion_id),
                                    INDEX (product_id)
);
