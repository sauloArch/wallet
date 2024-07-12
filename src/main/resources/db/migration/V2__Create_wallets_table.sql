CREATE TABLE wallets (
                         id SERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         balance DECIMAL(19, 2) NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES users(id)
);