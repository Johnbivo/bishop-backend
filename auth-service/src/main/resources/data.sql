DROP TABLE IF EXISTS users;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       is_active BOOLEAN NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                       UNIQUE KEY uq_users_email (email),
                       INDEX idx_users_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

