-- name: create-post-query<!
INSERT INTO posts (message, thread, posted_by) VALUES (
  :message,
  :thread,
  :posted_by
);

-- name: get-posts-query
SELECT message, thread, posted_by, created_at, updated_at FROM posts ORDER BY created_at ASC;

-- name: get-post-query
SELECT message, thread, posted_by, created_at, updated_at FROM posts WHERE id = :id;

-- name: get-posts-by-thread-query
SELECT message, thread, posted_by, created_at, updated_at FROM posts WHERE thread = :thread ORDER BY created_at ASC;

-- name: update-post-query!
UPDATE posts SET message = :message, updated_at = NOW() WHERE id = :id;

-- name: delete-post-query!
DELETE FROM posts WHERE id = :id;
