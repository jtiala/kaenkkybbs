CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  email VARCHAR NOT NULL,
  username VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  role role DEFAULT 'user',
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Insert example data
INSERT INTO users (email, username, password, role) VALUES (
  'kaenkkykeisari@example.com',
  'Kaenkkykeisari',
  crypt('kaenkky', gen_salt('bf', 8)),
  'admin'
), (
  'ananas666@example.com',
  'ananas666',
  crypt('ananas', gen_salt('bf', 8)),
  'moderator'
), (
  'pizzakuski@example.com',
  'PizzaKuski',
  crypt('pizza', gen_salt('bf', 8)),
  'user'
);
