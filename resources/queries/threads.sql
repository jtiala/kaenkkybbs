-- name: create-thread
INSERT INTO threads (title, started_by) VALUES (
  :title,
  :started_by,
);

-- name: get-thread
SELECT id, title, started_by, created_at, updated_at FROM threads WHERE id = :id;

-- name: get-threads
SELECT id, title, started_by, created_at, updated_at FROM threads ORDER BY created_at DESC;

-- name: update-thread
UPDATE threads SET title = :title, updated_at = NOW() WHERE id = :id;

-- name: delete-thread
DELETE FROM threads WHERE id = :id;
