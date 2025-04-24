CREATE SEQUENCE email_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

CREATE TABLE email_data (
    id BIGINT PRIMARY KEY DEFAULT nextval('email_data_id_seq'),
    user_id  BIGINT NOT NULL,
    email VARCHAR(200) UNIQUE CHECK (POSITION('@' IN email) > 1) NOT NULL,
    CONSTRAINT fk_email_data_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE
);
