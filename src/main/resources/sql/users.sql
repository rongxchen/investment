CREATE TABLE users (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    app_id VARCHAR(100) NOT NULL,
    username VARCHAR(100),
    email VARCHAR(255),
    password VARCHAR(512),
    salt VARCHAR(512),
    is_deleted TINYINT(1),
    is_active TINYINT(1),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
