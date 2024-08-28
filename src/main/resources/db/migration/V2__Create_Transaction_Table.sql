DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender           VARCHAR(255) NOT NULL,
    receiver         VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    transaction_date TIMESTAMP    NOT NULL,
    status           VARCHAR(50)
);
