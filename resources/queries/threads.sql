-- name: create-thread-query<!
INSERT INTO threads (title, started_by) VALUES (
  :title,
  :started_by
);

-- name: get-threads-query
SELECT id, title, started_by, created_at, updated_at FROM threads ORDER BY created_at DESC;

-- name: get-thread-query
SELECT id, title, started_by, created_at, updated_at FROM threads WHERE id = :id;

-- name: update-thread-query!
UPDATE threads SET title = :title, updated_at = NOW() WHERE id = :id;

-- name: delete-thread-query!
DELETE FROM threads WHERE id = :id;
