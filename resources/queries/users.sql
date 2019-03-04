-- name: create-user
INSERT INTO users (email, username, role) VALUES (
  :email,
  :username,
  crypt(:password, gen_salt('bf', 8)),
  :role
);

-- name: get-user
SELECT id, email, username, role FROM users WHERE id = :id;

-- name: get-users
SELECT id, email, username, role FROM users ORDER BY id;

-- name: update-user
UPDATE users SET email = :email, username = :username, role = :role, updated_at = NOW() WHERE id = :id;

-- name: update-user-password
UPDATE users SET password = crypt(:password, gen_salt('bf', 8)) WHERE id = :id;

-- name: delete-user
DELETE FROM user WHERE id = :id;

-- name: login
SELECT id, email, username, role FROM users WHERE email = :email AND password = crypt(:password, password);
