CREATE TABLE payment_master (
    id SERIAL PRIMARY KEY,
    transaction_id VARCHAR(255),
    payment_method VARCHAR(50),
    total_amount DOUBLE PRECISION,
    created_at TIMESTAMP
);

CREATE TABLE payment_upi (
    id SERIAL PRIMARY KEY,
    master_id BIGINT,
    upi_id VARCHAR(255)
);

CREATE TABLE payment_card (
    id SERIAL PRIMARY KEY,
    master_id BIGINT,
    card_number VARCHAR(255),
    expiry_month VARCHAR(10),
    expiry_year VARCHAR(10),
    cvv VARCHAR(10),
    card_holder_name VARCHAR(255)
);
