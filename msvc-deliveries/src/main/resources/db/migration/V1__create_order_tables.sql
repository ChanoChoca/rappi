CREATE TABLE IF NOT EXISTS deliveries (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            order_id BIGINT NOT NULL,
                            delivery_person_id BIGINT NOT NULL,
                            status VARCHAR(50) NOT NULL,
                            delivery_date DATE NOT NULL,
                            delivery_address VARCHAR(255) NOT NULL,
                            FOREIGN KEY (order_id) REFERENCES orders(id),
                            FOREIGN KEY (delivery_person_id) REFERENCES delivery_persons(id),
                            INDEX (order_id),
                            INDEX (delivery_person_id),
                            INDEX (delivery_date)
);

CREATE TABLE IF NOT EXISTS delivery_persons (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  name VARCHAR(100) NOT NULL,
                                  phone_number VARCHAR(20) NOT NULL,
                                  available BOOLEAN NOT NULL DEFAULT TRUE,
                                  UNIQUE (phone_number)
);

CREATE TABLE IF NOT EXISTS delivery_vehicles (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   vehicle_type VARCHAR(50) NOT NULL,
                                   license_plate VARCHAR(50) NOT NULL,
                                   delivery_person_id BIGINT,
                                   FOREIGN KEY (delivery_person_id) REFERENCES delivery_persons(id),
                                   UNIQUE (license_plate),
                                   INDEX (delivery_person_id)
);
