CREATE TABLE IF NOT EXISTS users (
   id BIGSERIAL PRIMARY KEY,
   created_at TIMESTAMP WITH TIME ZONE NOT NULL ,
   updated_at TIMESTAMP WITH TIME ZONE,
   last_name VARCHAR NOT NULL ,
   first_name VARCHAR NOT NULL ,
   middle_name VARCHAR,
   phone VARCHAR UNIQUE NOT NULL ,
   email VARCHAR UNIQUE NOT NULL ,
   birthdate DATE NOT NULL ,
   password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS wallets (
   number BIGSERIAL PRIMARY KEY,
   balance BIGINT CHECK (balance >= 0) NOT NULL,
   user_id BIGINT REFERENCES users ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sessions (
     id UUID PRIMARY KEY,
     user_id BIGINT REFERENCES users ON DELETE CASCADE,
     created_at TIMESTAMP WITH TIME ZONE NOT NULL,
     expiration_time TIMESTAMP WITH TIME ZONE NOT NULL,
     active BOOLEAN NOT NULL
);

CREATE TABLE transfers (
   id                UUID PRIMARY KEY,
   created_at        TIMESTAMP WITH TIME ZONE NOT NULL,
   sender_wallet_id  BIGINT NOT NULL REFERENCES wallets ON DELETE CASCADE ,
   receiver_wallet_id BIGINT NOT NULL REFERENCES wallets ON DELETE CASCADE ,
   amount            BIGINT NOT NULL,
   transfer_type     VARCHAR NOT NULL,
   transfer_status   VARCHAR NOT NULL
);
