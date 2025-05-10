CREATE SCHEMA IF NOT EXISTS etrain;

CREATE TABLE IF NOT EXISTS etrain.trains (
    id SERIAL PRIMARY KEY,
    train_name VARCHAR(255) NOT NULL,
    departure_time VARCHAR(50),
    arrival_time VARCHAR(50),
    available_seats INT,
    fare DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS etrain.tickets (
    id SERIAL PRIMARY KEY,
    train_id BIGINT NOT NULL,
    number_of_seats INT,
    total_fare DOUBLE PRECISION,
    booking_status VARCHAR(50),
    FOREIGN KEY (train_id) REFERENCES etrain.trains(id)
);
