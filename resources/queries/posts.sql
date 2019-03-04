-- name: create-post
INSERT INTO posts (message, thread, posted_by) VALUES (
  :message,
  :thread,
  :posted_by
);

-- name: get-post
SELECT message, thread, posted_by, created_at, updated_at FROM posts WHERE id = :id;

-- name: get-posts-by-thread
SELECT message, thread, posted_by, created_at, updated_at FROM posts ORDER BY thread = :thread;

-- name: update-post
UPDATE posts SET message = :message, updated_at = NOW() WHERE id = :id;

-- name: delete-post
DELETE FROM posts WHERE id = :id;
