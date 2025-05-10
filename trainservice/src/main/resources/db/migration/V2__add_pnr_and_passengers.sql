ALTER TABLE etrain.tickets
ADD COLUMN pnr_number VARCHAR(20),
ADD COLUMN train_name VARCHAR(255),
ADD COLUMN source_station VARCHAR(255),
ADD COLUMN destination_station VARCHAR(255),
ADD COLUMN journey_date DATE,
ADD COLUMN transaction_id VARCHAR(255);

CREATE TABLE etrain.passengers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    age INT,
    gender VARCHAR(20),
    seat_number VARCHAR(20),
    ticket_id BIGINT,
    CONSTRAINT fk_ticket FOREIGN KEY (ticket_id) REFERENCES etrain.tickets(id)
);
