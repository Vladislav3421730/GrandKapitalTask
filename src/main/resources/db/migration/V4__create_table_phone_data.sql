
CREATE TABLE phone_data (
    id BIGINT PRIMARY KEY,
    user_id  BIGINT NOT NULL,
    phone VARCHAR(13) UNIQUE CHECK (CHAR_LENGTH(phone) = 13 AND phone = '^[0-9]{13}$') NOT NULL,
    CONSTRAINT fk_phone_data_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE
);
