CREATE TABLE posts (
       id int PRIMARY KEY,
       title VARCHAR(256) NOT NULL
);

INSERT INTO posts VALUES(1, 'Clojure thread');
INSERT INTO posts VALUES(2, 'ClojureScript thread');
INSERT INTO posts VALUES(3, 'JavaScript thread');
