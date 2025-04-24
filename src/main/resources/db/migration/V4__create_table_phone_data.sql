CREATE SEQUENCE phone_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

CREATE TABLE phone_data (
    id BIGINT PRIMARY KEY DEFAULT nextval('phone_data_id_seq'),
    user_id  BIGINT NOT NULL,
    phone VARCHAR(13) UNIQUE CHECK (CHAR_LENGTH(phone) = 13) NOT NULL,
    CONSTRAINT fk_phone_data_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE
);
