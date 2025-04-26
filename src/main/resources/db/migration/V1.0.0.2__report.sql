CREATE TABLE report (
    id UUID NOT NULL PRIMARY KEY,
    reporter_id UUID,
    date_time TIMESTAMP,
    description VARCHAR(255)
);
