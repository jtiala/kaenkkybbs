-- name: all-posts
SELECT * FROM posts;

-- name: post-by-id
SELECT * FROM posts WHERE id=:id;
