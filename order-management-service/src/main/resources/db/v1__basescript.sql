CREATE TABLE PRODUCT_MASTER (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(30),
    Remarks VARCHAR(50)
);

INSERT INTO PRODUCT_MASTER (product_name, Remarks, price_per_grm_inr)
VALUES 
('Almonds', 'Rich in protein', 1.2),
('Cashews', 'Creamy and crunchy', 1.5),
('Walnuts', 'Good for the brain', 1.8),
('Pistachios', 'High in fiber', 1.6),
('Raisins', 'Natural sweetener', 0.9),
('Dates', 'Energy booster', 1.0),
('Figs', 'Rich in iron', 1.1);

commit;

CREATE TABLE ORDER_MASTER (
    order_id SERIAL PRIMARY KEY,
    ORDER_PRICE_INR DECIMAL(10, 2) NOT NULL,
    order_type VARCHAR(3) CHECK (order_type IN ('IN', 'OUT')),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE PRODUCTS_ORDERS_MAPPING (
    product_order_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    QUANTITY_PRICE_INR DECIMAL(10, 2) NOT NULL,
    REMARKS	VARCHAR(50),
    FOREIGN KEY (order_id) REFERENCES ORDER_MASTER(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES PRODUCT_MASTER(product_id)
);

CREATE TABLE STOCK_MONITOR (
    stock_id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL,
    current_stock INTEGER NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
        REFERENCES PRODUCT_MASTER (product_id)
        ON DELETE CASCADE
);
