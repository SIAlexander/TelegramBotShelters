-- liquibase formatted sql

-- changeset asmokvin:1

CREATE table users
(
    id             BIGSERIAL PRIMARY KEY,
    chat_id        BIGINT
);