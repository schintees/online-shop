CREATE TABLE product_category(
    id                  UUID            PRIMARY KEY,
    name                VARCHAR(50)     NOT NULL,
    description         VARCHAR(255),
    CONSTRAINT uq_product_category_name UNIQUE (name)
);

CREATE TABLE supplier(
    id                  UUID            PRIMARY KEY,
    name                VARCHAR(50)     NOT NULL,
    CONSTRAINT uq_supplier_name UNIQUE (name)
);

CREATE TABLE product (
    id                  UUID            PRIMARY KEY,
    name                VARCHAR(50)     NOT NULL,
    description         VARCHAR(255)    NOT NULL,
    price               DECIMAL         NOT NULL,
    weight              DOUBLE PRECISION NOT NULL,
    category_id         UUID            NOT NULL,
    supplier_id         UUID            NOT NULL,
    image_url           VARCHAR(255),
    CONSTRAINT fk_product_product_category FOREIGN KEY (category_id) REFERENCES product_category (id),
    CONSTRAINT fk_product_supplier FOREIGN KEY (supplier_id) REFERENCES supplier (id),
    CONSTRAINT uq_product_name UNIQUE (name)
);

CREATE TABLE location(
    id                  UUID            PRIMARY KEY,
    name                VARCHAR(50)     NOT NULL,
    country             VARCHAR(50)     NOT NULL,
    city                VARCHAR(50)     NOT NULL,
    county              VARCHAR(50)     NOT NULL,
    street_address      VARCHAR(50)     NOT NULL,
    CONSTRAINT uq_location_name UNIQUE (name)
);

CREATE TABLE revenue(
    id                  UUID            PRIMARY KEY,
    location_id         UUID            NOT NULL,
    date                DATE            NOT NULL,
    sum                 DECIMAL         NOT NULL,
    CONSTRAINT fk_revenue_location FOREIGN KEY (location_id) REFERENCES location (id)
);

CREATE TABLE customer(
    id                  UUID            PRIMARY KEY,
    first_name          VARCHAR(50)     NOT NULL,
    last_name           VARCHAR(50)     NOT NULL,
    username            VARCHAR(50)     NOT NULL,
    password            VARCHAR(50)     NOT NULL,
    email_address       VARCHAR(50)     NOT NULL,
    CONSTRAINT uq_customer_name UNIQUE (username)
);

CREATE TABLE orders(
    id                  UUID            PRIMARY KEY,
    shipped_from_id     UUID            NOT NULL,
    customer_id         UUID            NOT NULL,
    created_at          TIMESTAMP       NOT NULL,
    country             VARCHAR(50)     NOT NULL,
    city                VARCHAR(50)     NOT NULL,
    county              VARCHAR(50)     NOT NULL,
    street_address      VARCHAR(50)     NOT NULL,
    CONSTRAINT fk_order_shipped_from FOREIGN KEY (shipped_from_id) REFERENCES location (id),
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE order_detail(
    id                  UUID            PRIMARY KEY,
    order_id            UUID            NOT NULL,
    product_id          UUID            NOT NULL,
    quantity            INT             NOT NULL,
    CONSTRAINT fk_order_detail_order FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_order_detail_product FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT uq_order_detail_order_product UNIQUE (order_id,product_id)
);

CREATE TABLE stock(
    id                  UUID            PRIMARY KEY,
    product_id          UUID            NOT NULL,
    location_id         UUID            NOT NULL,
    quantity            INT             NOT NULL,
    CONSTRAINT fk_stock_product FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_stock_location FOREIGN KEY (location_id) REFERENCES location (id),
    CONSTRAINT uq_stock_product_location UNIQUE (product_id,location_id)
);