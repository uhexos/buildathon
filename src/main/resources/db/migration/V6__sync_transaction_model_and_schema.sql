ALTER TABLE `transaction`
    -- Add new columns to match the model
    ADD COLUMN `transaction_type` VARCHAR(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    ADD COLUMN `currency` VARCHAR(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
    ADD COLUMN `description` TEXT COLLATE utf8mb4_general_ci  NOT NULL,
    ADD COLUMN `available_balance` DECIMAL(19, 2) DEFAULT NULL,
    ADD COLUMN `current_balance` DECIMAL(19, 2) DEFAULT NULL,
    ADD COLUMN `location` VARCHAR(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    ADD COLUMN `uid` int COLLATE utf8mb4_general_ci,
    ADD COLUMN `category` VARCHAR(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    ADD COLUMN `account_number` VARCHAR(255) COLLATE utf8mb4_general_ci DEFAULT NULL,

    -- Modify existing columns to align with your model
    MODIFY COLUMN `amount` DECIMAL(19, 2) NOT NULL,
    MODIFY COLUMN `transaction_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

