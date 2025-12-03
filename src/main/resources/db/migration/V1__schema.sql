CREATE DATABASE IF NOT EXISTS pizza_byte;
USE pizza_byte;

CREATE TABLE IF NOT EXISTS stock_item (
    id BIGINT NOT NULL AUTO_INCREMENT,
    namespace VARCHAR(255) NOT NULL,
    amount INT NOT NULL,
    last_update BIGINT NOT NULL,
    PRIMARY KEY (id)
    );
