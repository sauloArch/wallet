CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              amount DECIMAL(19, 2) NOT NULL,
                              type VARCHAR(50) NOT NULL,
                              timestamp TIMESTAMP NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES users(id)
);