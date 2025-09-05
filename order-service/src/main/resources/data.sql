
DROP TABLE IF EXISTS orders;


CREATE TABLE orders(
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    user_id BINARY(16) NOT NULL,
    order_status VARCHAR(100) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    currency VARCHAR(3) NOT NULL,

    -- shipping address
    shipping_address_line1 VARCHAR(255) NOT NULL,
    shipping_address_line2 VARCHAR(255) NULL,
    shipping_city VARCHAR(100) NOT NULL,
    shipping_state VARCHAR(100) NOT NULL,
    shipping_postal_code VARCHAR(20) NOT NULL,
    shipping_country VARCHAR(100) NOT NULL,

    -- billing address
    billing_address_line1 VARCHAR(255) NOT NULL,
    billing_address_line2 VARCHAR(255) NULL,
    billing_city VARCHAR(100) NOT NULL,
    billing_state VARCHAR(100) NOT NULL,
    billing_postal_code VARCHAR(20) NOT NULL,
    billing_country VARCHAR(100) NOT NULL,

    -- payment
    billing_account_id BINARY(16) NOT NULL,
    payment_method_id BINARY(16) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_user_id (user_id),
    INDEX idx_order_status (order_status),
    INDEX idx_created_at (created_at),
    INDEX idx_billing_account (billing_account_id),
    INDEX idx_payment_method (payment_method_id)
);

DROP TABLE IF EXISTS order_items;

CREATE TABLE order_items (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    order_id BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10, 2) NOT NULL, -- price at the time of order
    total_price DECIMAL(10, 2) NOT NULL, -- quantity * unit_price
    product_name VARCHAR(255) NOT NULL,
    product_sku VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,

    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id)
);


DROP TABLE IF EXISTS order_status_history;
CREATE TABLE order_status_history (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    order_id BINARY(16) NOT NULL,
    old_status VARCHAR(100),   -- ENUM('pending', 'confirmed', 'processing', 'shipped', 'delivered', 'cancelled') NULL,
    new_status VARCHAR(100) NOT NULL,    -- ENUM('pending', 'confirmed', 'processing', 'shipped', 'delivered', 'cancelled') NOT NULL,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes TEXT NULL,

    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,

    INDEX idx_order_id (order_id),
    INDEX idx_changed_at (changed_at)
);

-- trigger for automatic status change when orders are updated

DELIMITER $$
CREATE TRIGGER order_status_change_trigger
    AFTER UPDATE ON orders
    FOR EACH ROW
BEGIN
    IF OLD.order_status != NEW.order_status THEN
        INSERT INTO order_status_history (order_id, old_status, new_status, changed_at)
        VALUES (NEW.id, OLD.order_status, NEW.order_status, NOW());
    END IF;
END$$
DELIMITER ;



DELIMITER $$
CREATE TRIGGER update_order_item_total
    BEFORE UPDATE ON order_items
    FOR EACH ROW
BEGIN
    SET NEW.total_price = NEW.quantity * NEW.unit_price;
END$$
DELIMITER ;




-- Trigger to set total_price in order_items on insert
DELIMITER $$
CREATE TRIGGER set_order_item_total
    BEFORE INSERT ON order_items
    FOR EACH ROW
BEGIN
    SET NEW.total_price = NEW.quantity * NEW.unit_price;
END$$
DELIMITER ;