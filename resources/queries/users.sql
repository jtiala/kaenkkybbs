-- name: create-user-query<!
INSERT INTO users (email, username, role) VALUES (
  :email,
  :username,
  crypt(:password, gen_salt('bf', 8)),
  :role
);

-- name: get-users-query
SELECT id, email, username, role FROM users ORDER BY id;

-- name: get-user-query
SELECT id, email, username, role FROM users WHERE id=:id;

-- name: update-user-query!
UPDATE users SET email = :email, username = :username, role = :role, updated_at = NOW() WHERE id = :id;

-- name: update-user-password-query!
UPDATE users SET password = crypt(:password, gen_salt('bf', 8)) WHERE id = :id;

-- name: delete-user-query!
DELETE FROM user WHERE id = :id;

-- name: login-query
SELECT id, email, username, role FROM users WHERE email = :email AND password = crypt(:password, password);
