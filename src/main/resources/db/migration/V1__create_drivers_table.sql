CREATE TABLE drivers
(
    id             UUID PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    license_number VARCHAR(255) NOT NULL UNIQUE,
    active         BOOLEAN      NOT NULL
);

COMMENT ON TABLE drivers IS 'Drivers available for transport orders';
COMMENT ON COLUMN drivers.license_number IS 'Unique driver license number';
