-- Users table
CREATE TABLE ganun.users (
    telegram_id BIGINT PRIMARY KEY,
    username VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    is_bot BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Usage table
CREATE TABLE ganun.usage (
    id SERIAL PRIMARY KEY,
    telegram_id BIGINT NOT NULL,
    total_searches INTEGER DEFAULT 0,
    last_search_date TIMESTAMP,
    last_search_query VARCHAR(255),
    total_messages INTEGER DEFAULT 0,
    first_usage_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (telegram_id) REFERENCES ganun.users(telegram_id)
);

-- Indexes
CREATE INDEX idx_usage_telegram_id ON ganun.usage(telegram_id);
CREATE INDEX idx_users_created_at ON ganun.users(created_at);
CREATE INDEX idx_usage_last_search ON ganun.usage(last_search_date);