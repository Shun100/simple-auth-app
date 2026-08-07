INSERT INTO users (username, password)
VALUES
    ('Alice', 'password'),
    ('Bob', 'password')
ON CONFLICT (username) DO NOTHING;