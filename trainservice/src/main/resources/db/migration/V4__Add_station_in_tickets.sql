ALTER TABLE etrain.tickets
DROP COLUMN source_station,
DROP COLUMN destination_station,
ADD COLUMN source_station_id BIGINT,
ADD COLUMN destination_station_id BIGINT,
ADD CONSTRAINT fk_source FOREIGN KEY (source_station_id) REFERENCES etrain.stations(id),
ADD CONSTRAINT fk_destination FOREIGN KEY (destination_station_id) REFERENCES etrain.stations(id);