DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS carts;

CREATE TABLE carts (
                       id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
                       user_id BINARY(16),
                       session_id VARCHAR(255) NULL,
                       total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       expires_at TIMESTAMP NULL,
                       INDEX idx_user_id (user_id),
                       INDEX idx_session_id (session_id),
                       INDEX idx_expires_at (expires_at)
);

CREATE TABLE cart_items (
                            id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
                            cart_id BINARY(16) NOT NULL,
                            product_id BINARY(16) NOT NULL,
                            quantity INT NOT NULL CHECK (quantity > 0),
                            currency VARCHAR(3),
                            product_name VARCHAR(255),
                            product_brand VARCHAR(255),
                            product_model VARCHAR(255),
                            unit_price DECIMAL(10, 2) NOT NULL,
                            total_price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
                            added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
                            INDEX idx_cart_id (cart_id),
                            INDEX idx_product_id (product_id),
                            UNIQUE KEY unique_cart_product (cart_id, product_id)
);

-- Trigger: set total_price before insert
DELIMITER $$
CREATE TRIGGER set_cart_item_total
    BEFORE INSERT ON cart_items
    FOR EACH ROW
BEGIN
    SET NEW.total_price = COALESCE(NEW.quantity * NEW.unit_price, 0.00);
END$$
DELIMITER ;

-- Trigger: update total_price before update
DELIMITER $$
CREATE TRIGGER update_cart_item_total
    BEFORE UPDATE ON cart_items
    FOR EACH ROW
BEGIN
    SET NEW.total_price = COALESCE(NEW.quantity * NEW.unit_price, 0.00);
END$$
DELIMITER ;

-- Trigger: update cart total after insert
DELIMITER $$
CREATE TRIGGER update_cart_total_on_insert
    AFTER INSERT ON cart_items
    FOR EACH ROW
BEGIN
    UPDATE carts
    SET total_amount = COALESCE(
            (SELECT SUM(total_price) FROM cart_items WHERE cart_id = NEW.cart_id),
            0.00
                       )
    WHERE id = NEW.cart_id;
END$$
DELIMITER ;

-- Trigger: update cart total after update
DELIMITER $$
CREATE TRIGGER update_cart_total_on_update
    AFTER UPDATE ON cart_items
    FOR EACH ROW
BEGIN
    UPDATE carts
    SET total_amount = COALESCE(
            (SELECT SUM(total_price) FROM cart_items WHERE cart_id = NEW.cart_id),
            0.00
                       )
    WHERE id = NEW.cart_id;
END$$
DELIMITER ;

-- Trigger: update cart total after delete
DELIMITER $$
CREATE TRIGGER update_cart_total_on_delete
    AFTER DELETE ON cart_items
    FOR EACH ROW
BEGIN
    UPDATE carts
    SET total_amount = COALESCE(
            (SELECT SUM(total_price) FROM cart_items WHERE cart_id = OLD.cart_id),
            0.00
                       )
    WHERE id = OLD.cart_id;
END$$
DELIMITER ;
