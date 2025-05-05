DROP TABLE IF EXISTS health;

CREATE TABLE health
(
    id INT PRIMARY KEY,
    up BOOLEAN
);

INSERT INTO health
    (id, up)
VALUES (1, true);

CREATE TABLE IF NOT EXISTS authorized_users (
  id SERIAL PRIMARY KEY,
  email VARCHAR NOT NULL,
  created_at TIMESTAMPTZ DEFAULT now()
);

CREATE UNIQUE INDEX IF NOT EXISTS authorized_users_email_idx
  ON authorized_users(email);
