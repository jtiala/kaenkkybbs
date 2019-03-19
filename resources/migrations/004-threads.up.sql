CREATE TABLE threads (
  id SERIAL PRIMARY KEY,
  title VARCHAR NOT NULL,
  started_by INTEGER,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT fk_started_by
    FOREIGN KEY (started_by)
    REFERENCES users (id)
    ON DELETE SET NULL
);

INSERT INTO threads (title, started_by) VALUES (
  'Your fav kaenkkylaes in Oulu', 1
),(
  'Does pineapple belong in pizza?', 3
),(
  'Mayonnase in pizza - yay or nay?', 2
);
