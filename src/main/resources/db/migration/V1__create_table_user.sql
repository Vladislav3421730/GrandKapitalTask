
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    date_of_birth DATE NOT NULL,
    password VARCHAR(500) CHECK ( CHAR_LENGTH(password) > 8 ) NOT NULL
);
