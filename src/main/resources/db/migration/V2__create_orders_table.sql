CREATE TABLE orders
(
    id            UUID PRIMARY KEY,
    status        VARCHAR(20)  NOT NULL,
    origin        VARCHAR(255) NOT NULL,
    destination   VARCHAR(255) NOT NULL,
    driver_id     UUID REFERENCES drivers (id),
    document_path VARCHAR(255),
    image_path    VARCHAR(255),
    created_at    TIMESTAMP    NOT NULL,
    updated_at    TIMESTAMP
);

CREATE INDEX idx_orders_driver ON orders (driver_id);
CREATE INDEX idx_orders_status ON orders (status);

COMMENT ON TABLE orders IS 'Transport orders assigned to drivers';
COMMENT ON COLUMN orders.driver_id IS 'Driver assigned to the order';
COMMENT ON COLUMN orders.status IS 'Current status of the transport order';