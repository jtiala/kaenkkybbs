-- name: create-user
INSERT INTO users (email, username, role) VALUES(
  :email,
  :username,
  crypt(:password, gen_salt('bf', 8)),
  :role
);

-- name: login
SELECT id, email, username, role FROM users WHERE email = :email AND password = crypt(:password, password);
