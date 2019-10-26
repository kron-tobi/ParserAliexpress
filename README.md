Before using it is necessary to create 2 tables in the database.
-------------------------------------------------------------------

CREATE TABLE PRODUCT (
    id integer PRIMARY KEY,
    date_price DATE NOT NULL,
    url varchar(100) NOT NULL,
    description varchar(100) NOT NULL,
    price numeric(8,2) NOT NULL,
    orders integer NOT NULL,
    reviews integer NOT NULL
)

CREATE TABLE URL_PRODUCT (
    id integer PRIMARY KEY,
    url varchar(100) NOT NULL,
    short_url varchar(50) NOT NULL
)