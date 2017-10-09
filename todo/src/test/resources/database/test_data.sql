/* define the schemas. */
CREATE TABLE IF NOT EXISTS todo (todo_id VARCHAR(36) PRIMARY KEY, todo_title VARCHAR(30), finished BOOLEAN, created_at TIMESTAMP);
DELETE FROM todo;

/* load the records. */
INSERT INTO todo (todo_id, todo_title, finished, created_at) VALUES ('cceae402-c5b1-440f-bae2-7bee19dc17fb', 'one', false, '2017-10-01 15:39:17.888');
INSERT INTO todo (todo_id, todo_title, finished, created_at) VALUES ('5dd4ba78-ff5b-423b-aa2a-a07118aeaf90', 'two', false, '2017-10-01 15:39:19.981');
INSERT INTO todo (todo_id, todo_title, finished, created_at) VALUES ('e3bdb9af-3dde-40b7-b5fb-4b388567ab45', 'three', false, '2017-10-01 15:39:28.437');
commit;