CREATE TABLE products
(
  id      INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  barcode INT(255),
  price   DOUBLE              NOT NULL,
  name    VARCHAR(250)        NOT NULL
);
CREATE TABLE consignments
(
  id                INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  number_in_package INT(11)             NOT NULL,
  actual_number     INT(11),
  product_id        INT(11)             NOT NULL,
  CONSTRAINT consignments_products_id_fk FOREIGN KEY (product_id) REFERENCES products (id)
);
CREATE INDEX consignments_products_id_fk
ON consignments (product_id);

CREATE TABLE orders
(
  id          INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  total_price DOUBLE,
  time        DATETIME
);

CREATE TABLE order_product
(
  order_id   INT(11),
  product_id INT(11),
  CONSTRAINT order_product_orders_id_fk FOREIGN KEY (order_id) REFERENCES orders (id),
  CONSTRAINT order_product_products_id_fk FOREIGN KEY (product_id) REFERENCES products (id)
);
CREATE INDEX order_product_orders_id_fk ON order_product (order_id);
CREATE INDEX order_product_products_id_fk ON order_product (product_id);