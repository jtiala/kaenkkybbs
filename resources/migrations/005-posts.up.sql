CREATE TABLE posts (
  id SERIAL PRIMARY KEY,
  message VARCHAR NOT NULL,
  thread INTEGER NOT NULL,
  posted_by INTEGER NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT fk_thread
    FOREIGN KEY (thread)
    REFERENCES threads (id),
  CONSTRAINT fk_posted_by
    FOREIGN KEY (posted_by)
    REFERENCES users (id)
);

INSERT INTO posts (message, thread, posted_by) VALUES (
  'Looking for recommendations!', 1, 1
), (
  'Da Mario, they have the best salad bar ever. 5/5.', 1, 2
), (
  'DaMax - simply the best', 1, 3
), (
  'IMHO pizza ain''t pizza without pineapple', 2, 2
), (
  '^ This.', 2, 1
), (
  'Why would anyone not love it?', 3, 3
), (
  'YAY!', 3, 1
);
