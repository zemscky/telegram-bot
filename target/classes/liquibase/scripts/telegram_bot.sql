-- liquibase formatted sql

-- changeset zemscky:1
CREATE TABLE notification_task
(
    id          BIGSERIAL primary key,
    chatId      BIGSERIAL    not null,
    messageText VARCHAR(255) not null,
    date        DATE         not null
)

