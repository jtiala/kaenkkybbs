-- name: create-post-query<!
INSERT INTO posts (message, thread, posted_by) VALUES (
  :message,
  :thread,
  :posted_by
);

-- name: get-posts-query
SELECT p.id, p.message, p.thread, p.created_at, p.updated_at, u.id AS user_id, u.username AS user_username, u.role AS user_role
FROM posts p
LEFT JOIN users u ON p.posted_by = u.id
ORDER BY p.created_at ASC;

-- name: get-post-query
SELECT p.id, p.message, p.thread, p.created_at, p.updated_at, u.id AS user_id, u.username AS user_username, u.role AS user_role
FROM posts p
LEFT JOIN users u ON p.posted_by = u.id
WHERE p.id = :id;

-- name: get-posts-by-thread-query
SELECT p.id, p.message, p.thread, p.created_at, p.updated_at, u.id AS user_id, u.username AS user_username, u.role AS user_role
FROM posts p
LEFT JOIN users u ON p.posted_by = u.id
WHERE p.thread = :thread ORDER BY p.created_at ASC;

-- name: update-post-query!
UPDATE posts SET message = :message, updated_at = NOW() WHERE id = :id;

-- name: delete-post-query!
DELETE FROM posts WHERE id = :id;
