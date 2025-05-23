--liquibase formatted sql

--changeset azmeev:1
CREATE TABLE BANK_USERS (
    ID bigint not null primary key generated by default as identity,
    LOGIN varchar(256) not null,
    PASSWORD text not null,
    NAME varchar(256) not null,
    BIRTHDATE date not null
);
--changeset azmeev:2
CREATE TABLE BANK_CURRENCY (
    ID bigint not null primary key generated by default as identity,
    TITLE varchar(256) not null,
    NAME varchar(256) not null
);
--changeset azmeev:3
CREATE TABLE BANK_ACCOUNTS (
    ID bigint not null primary key generated by default as identity,
    USER_ID bigint not null references BANK_USERS(ID),
    CURRENCY_ID bigint not null references BANK_CURRENCY(ID),
    VALUE numeric not null
);