CREATE TABLE IF NOT EXISTS emails
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    uid            INT                 NOT NULL,
    message_id     VARCHAR(255) UNIQUE NOT NULL,
    subject        TEXT,
    `from`         TEXT,
    `to`           TEXT,
    date           DATETIME,
    body_structure JSON,
    body           LONGTEXT,
    text           LONGTEXT,
    html           LONGTEXT,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)