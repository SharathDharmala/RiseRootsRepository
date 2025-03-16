-- V1__Create_user_table.sql

-- V1__Create_user_table.sql

CREATE TABLE rr_users (
    id BIGINT PRIMARY KEY DEFAULT nextval('rr_users_id_seq'::regclass),
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT true,
	accountnonlocked BOOLEAN  NOT NULL DEFAULT true
);

drop table rr_users;

CREATE SEQUENCE rr_users_id_seq
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  CACHE 1;

CREATE SEQUENCE user_role_id_seq
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  CACHE 1;
  
CREATE TABLE user_roles (
    user_id BIGINT PRIMARY KEY DEFAULT nextval('user_role_id_seq'::regclass),
    roles VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES rr_users(id) ON DELETE CASCADE
);

CREATE SEQUENCE user_role_id_seq
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  CACHE 1;