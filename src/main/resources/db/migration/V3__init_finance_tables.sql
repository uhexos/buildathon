-- Users table
CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(50) UNIQUE  NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Categories table
CREATE TABLE categories
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- Payees/Payers table (for tracking who money went to or came from)
CREATE TABLE entities
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) -- e.g., 'individual', 'business', 'organization'
);

-- Transactions table
CREATE TABLE transactions
(
    id               SERIAL PRIMARY KEY,
    user_id          INTEGER        NOT NULL REFERENCES users (id),
    amount           DECIMAL(10, 2) NOT NULL,
    transaction_date DATE           NOT NULL,
    description      TEXT,
    is_expense       BOOLEAN        NOT NULL, -- TRUE for expense, FALSE for income
    is_need          BOOLEAN,                 -- TRUE for need, FALSE for want, NULL if not specified
    category_id      INTEGER REFERENCES categories (id),
    entity_id        INTEGER REFERENCES entities (id),
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Budget table
CREATE TABLE budgets
(
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER        NOT NULL REFERENCES users (id),
    category_id INTEGER        NOT NULL REFERENCES categories (id),
    amount      DECIMAL(10, 2) NOT NULL,
    period VARCHAR (20) NOT NULL, -- e.g., 'monthly', 'yearly'
    start_date  DATE           NOT NULL,
    end_date    DATE,
    UNIQUE (user_id, category_id, period)
);

-- Entities-Category suggestions table (for auto-identification)
CREATE TABLE entity_category_suggestions
(
    id               SERIAL PRIMARY KEY,
    entity_id        INTEGER NOT NULL REFERENCES entities (id),
    category_id      INTEGER NOT NULL REFERENCES categories (id),
    suggestion_count INTEGER DEFAULT 1,
    UNIQUE (entity_id, category_id)
);

-- Tags table for additional categorization
CREATE TABLE tags
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- Pivot table for transaction tags
CREATE TABLE transaction_tags
(
    transaction_id INTEGER NOT NULL REFERENCES transactions (id),
    tag_id         INTEGER NOT NULL REFERENCES tags (id),
    PRIMARY KEY (transaction_id, tag_id)
);