

DROP TABLE IF EXISTS billing_accounts;

CREATE TABLE billing_accounts(
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    userId BINARY(16) NOT NULL UNIQUE,
    currency VARCHAR(3) DEFAULT 'USD',
    status VARCHAR(20), -- ACTIVE , SUSPENDED, CLOSED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_userId (userId)

);

DROP TABLE IF EXISTS payment_methods;
CREATE TABLE payment_methods (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    billing_account_id BINARY(16) NOT NULL,
    type VARCHAR(50) NOT NULL, -- card, bank, paypal
    provider VARCHAR(50) NOT NULL, -- 'stripe', 'paypal', etc.
    token VARCHAR(255) NOT NULL, -- gateway token
    last_four VARCHAR(4),
    brand VARCHAR(20), -- 'visa', 'mastercard'
    expires_at DATE,
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (billing_account_id) REFERENCES billing_accounts(id),
    INDEX idx_billing_account (billing_account_id),
    UNIQUE KEY unique_token (provider, token)
);

DROP TABLE IF EXISTS invoices;

CREATE TABLE invoices (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    billing_account_id BINARY(16) NOT NULL,
    order_id BINARY(16), -- reference to order service
    amount DECIMAL(10,2) NOT NULL,
    tax_amount DECIMAL(10,2) DEFAULT 0.00,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(100), -- pending, paid, failed, cancelled, refunded
    due_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP NULL,

    FOREIGN KEY (billing_account_id) REFERENCES billing_accounts(id),
    INDEX idx_billing_account (billing_account_id),
    INDEX idx_order (order_id),
    INDEX idx_status (status)
);

CREATE TABLE transactions (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    invoice_id BINARY(16) NOT NULL,
    payment_method_id BINARY(16),
    amount DECIMAL(10,2) NOT NULL,
    type ENUM('payment', 'refund', 'chargeback') NOT NULL,
    status ENUM('pending', 'completed', 'failed', 'cancelled') DEFAULT 'pending',
    gateway_transaction_id VARCHAR(255),
    gateway_response TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (invoice_id) REFERENCES invoices(id),
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id),
    INDEX idx_invoice (invoice_id),
    INDEX idx_payment_method (payment_method_id),
    INDEX idx_status (status)
);







