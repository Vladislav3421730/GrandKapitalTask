
CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


CREATE TABLE users (
    id   BIGINT PRIMARY KEY DEFAULT nextval('user_id_seq'),
    name VARCHAR(500) NOT NULL,
    date_of_birth DATE NOT NULL,
    password VARCHAR(500) CHECK ( CHAR_LENGTH(password) > 8 ) NOT NULL
);
