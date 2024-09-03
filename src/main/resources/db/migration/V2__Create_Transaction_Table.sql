DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender           VARCHAR(255) ,
    receiver         VARCHAR(255) ,
    amount DOUBLE NOT NULL,
    transaction_date TIMESTAMP    NOT NULL,
    status           VARCHAR(50)
);
