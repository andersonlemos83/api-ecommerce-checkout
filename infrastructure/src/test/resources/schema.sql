ALTER SESSION SET CONTAINER = XEPDB1;

CREATE TABLESPACE TS_ECOMMERCE_CHECKOUT_DAT DATAFILE 'TS_ECOMMERCE_CHECKOUT_DAT.dat' SIZE 20M AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED;

CREATE USER ECOMMERCE_CHECKOUT_OWNER IDENTIFIED BY ECOMMERCE_CHECKOUT_OWNER;

ALTER USER ECOMMERCE_CHECKOUT_OWNER DEFAULT TABLESPACE TS_ECOMMERCE_CHECKOUT_DAT;
ALTER USER ECOMMERCE_CHECKOUT_OWNER QUOTA UNLIMITED ON TS_ECOMMERCE_CHECKOUT_DAT;

GRANT CONNECT, RESOURCE TO ECOMMERCE_CHECKOUT_OWNER;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE VIEW, CREATE PROCEDURE TO ECOMMERCE_CHECKOUT_OWNER;