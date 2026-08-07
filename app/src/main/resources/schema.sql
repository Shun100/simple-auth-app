CREATE TABLE IF NOT EXISTS issues (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    summary VARCHAR(256) NOT NULL,
    description VARCHAR(256) NOT NUll
);

CREATE TABLE IF NOT EXISTS users (
    username varchar(50) PRIMARY KEY,
    password varchar(500) NOT NULL
);