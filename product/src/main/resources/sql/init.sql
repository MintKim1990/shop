CREATE TABLE IF NOT EXISTS PRODUCT
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    description      VARCHAR(255),
    image_url         VARCHAR(255) NOT NULL,
    consumer_price    DECIMAL NOT NULL,
    quantity         INT NOT NULL,
    seller_no         BIGINT NOT NULL,
    created_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);