-- name: create-thread-query<!
INSERT INTO threads (title, started_by) VALUES (
  :title,
  :started_by
);

-- name: get-threads-query
SELECT t.id, t.title, t.created_at, t.updated_at, u.id AS user_id, u.username AS user_username, u.role AS user_role
FROM threads t
LEFT JOIN users u ON t.started_by = u.id
ORDER BY t.created_at DESC;

-- name: get-thread-query
SELECT t.id, t.title, t.created_at, t.updated_at, u.id AS user_id, u.username AS user_username, u.role AS user_role
FROM threads t
LEFT JOIN users u ON t.started_by = u.id
WHERE t.id = :id;

-- name: update-thread-query!
UPDATE threads SET title = :title, updated_at = NOW() WHERE id = :id;

-- name: delete-thread-query!
DELETE FROM threads WHERE id = :id;
