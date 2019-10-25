Before using it is necessary to create 2 tables in the database.
-------------------------------------------------------------------

CREATE TABLE VIDEOCARDS (
    id_videocard integer PRIMARY KEY,
    date_price DATE NOT NULL,
    url_videocard varchar(100) NOT NULL,
    name_videocard varchar(100) NOT NULL,
    price_videocard numeric(8,2) NOT NULL,
    orders_videocard integer NOT NULL,
    reviews_videocard integer NOT NULL
)


CREATE TABLE url_all_products (
    id_url_product integer PRIMARY KEY,
    url_product varchar(100) NOT NULL,
    short_url_product varchar(50) NOT NULL
)