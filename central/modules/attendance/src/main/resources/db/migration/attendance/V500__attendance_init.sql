CREATE TABLE attendance.attendance_event (
    event_id        UUID         PRIMARY KEY,  
    employee_id     VARCHAR(32)  NOT NULL,      
    gate_station_id VARCHAR(32)  NOT NULL,     
    factory_id      VARCHAR(32)  NOT NULL,    
    direction       VARCHAR(5)   NOT NULL CHECK (direction IN ('ENTRY', 'EXIT')),
    mode            VARCHAR(7)   NOT NULL CHECK (mode IN ('ONLINE', 'OFFLINE')),
    occurred_at     TIMESTAMPTZ  NOT NULL,      -- kiosk time of the scan (F7)
    received_at     TIMESTAMPTZ  NOT NULL,      -- server time when recorded (F7)
    gate_outcome    VARCHAR(12)  NOT NULL CHECK (gate_outcome IN ('PENDING', 'OPENED', 'OPENED_LOCAL', 'NOT_OPENED', 'FAILED')),
    review_flag     BOOLEAN      NOT NULL DEFAULT FALSE   -- offline events are reviewed by security guards (E6)
);

CREATE INDEX ix_attendance_event_employee_time ON attendance.attendance_event (employee_id, occurred_at);
CREATE INDEX ix_attendance_event_factory_time  ON attendance.attendance_event (factory_id, occurred_at);
