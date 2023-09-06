INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'gyeongnam@gmail.com', 'gyeongnam', '$2a$10$sBLW3YgStvQtnJ..g0HtPOVuEO.wK5AmQ5lJeUQ85Nzmum1n8ZJyK', '010-1234-5678', 'LOCAL', 'ROLE_USER');
INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'gangmin@gmail.com', 'gangmin', '$2a$10$A6iy8d7XnwDyY/JRD3Fiw.zg2pJmp5RjkMnj7I.5w/ISJ7TuQeiSe', '010-1234-5678', 'LOCAL', 'ROLE_USER');
INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'sukeun@gmail.com', 'sukeun', '$2a$10$pkkXP1aC5yTTbVKQifexKuUSfOB6J5kGpF4GV4KHAVPqkSAk5VUCO', '010-1234-5678', 'LOCAL', 'ROLE_USER');
INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'jaehwan@gmail.com', 'jaehwan', '$2a$10$mIFRje7wzppchnFVCpseK.FSKjLDFHv3A3Rr66NaFZf4o6z.UilDm', '010-1234-5678', 'LOCAL', 'ROLE_USER');
INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'gyuhee@gmail.com', 'gyuhee', '$2a$10$HZVBZXXkMbeN.tUwNXyb4Oo2Zwf6YvBOxQqQYL0wlaqHJ6r13sErm', '010-1234-5678', 'LOCAL', 'ROLE_USER');
INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'kitae@gmail.com', 'kitae', '$2a$10$ckGkXQ0e.NL4ExD.RjWvPuvC43cH1bopUz5EqasjB3KUZXByvwLj.', '010-1234-5678', 'LOCAL', 'ROLE_USER');
INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'kimberley@gmail.com', 'kimberley', '$2a$10$3ox6l7keiMC.WfTt4OF6LOR3B72rNndufpP6vtZmmfsSf6swy69Xm', '010-1234-5678', 'LOCAL', 'ROLE_USER');
INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'kimura@gmail.com', 'kimura', '$2a$10$f3w6d6BDyU.CLlxv5iZQT.wtmgPF5M./3zoUDDs9mdLfC9ZBwlj/y', '010-1234-5678', 'LOCAL', 'ROLE_USER');
INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'kimgyeongnam@gmail.com', 'kimgyeongnam', '$2a$10$sbfsWjySSsLrtBCCE2hOxuc3yxlmWTM9P8dsLTvYocYsHH6XDLfYm', '010-1234-5678', 'LOCAL', 'ROLE_USER');
INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'kimkardashian@gmail.com', 'kimkardashian', '$2a$10$OROngbiriYyWWnyZYbVRs.rwBX/lzZTasNP5MIFL0IGOc24PIbtZi', '010-1234-5678', 'LOCAL', 'ROLE_USER');
INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'kitaek@gmail.com', 'kitaek', '$2a$10$i/YvoDMqsrBYn5sz0prfIeDbz2Y9IeB26gHOQNZUdL7swpd2KWcm.', '010-1234-5678', 'LOCAL', 'ROLE_USER');
INSERT INTO users (created_date, modified_date, email, username, password, phone, provider, role) VALUES (NOW(), NOW(), 'kimora@gmail.com', 'kimora', '$2a$10$ev0CZouZFPF/a0.i7c6s2.PRZw/l/BCkY9vjuWlvJVvENySlnGQ3e', '010-1234-5678', 'LOCAL', 'ROLE_USER');

-- Feeds
INSERT INTO feeds (user_id, type, scope, title, content, created_date) VALUES (1, 'NORMAL', 'PUBLIC', '1번째 피드', '안녕하세요. 1번째 피드입니다.', NOW());
INSERT INTO feeds (user_id, type, scope, title, content, created_date) VALUES (2, 'QUESTION', 'FOLLOWING', '2번째 피드', '안녕하세요. 2번째 피드입니다.', NOW());
INSERT INTO feeds (user_id, type, scope ,title ,content ,created_date) VALUES(3, 'NORMAL', 'PUBLIC','3번째 피드','안녕하세요. 3번째 피드입니다.',NOW());

-- Images
INSERT INTO images(feed_id,imageURL) VALUES(1,'http://withus.com/image1.jpg');
INSERT INTO images(feed_id,imageURL) VALUES(2,'http://withus.com/image2.jpg');
INSERT INTO images(feed_id,imageURL) VALUES(3,'http://withus.com/image3.jpg');

-- Likes
INSERT INTO likes(user_id ,feed_id)VALUES(1 , 2);
INSERT INTO likes(user_id ,feed_id)VALUES(2 , 3);
INSERT INTO likes(user_id ,feed_id)VALUES(3 , 1);

-- Comments
INSERT INTO parent_comments (user_id, feed_id) VALUES (1, 2);
INSERT INTO parent_comments (user_id, feed_id) VALUES (2, 3);
INSERT INTO parent_comments (user_id, feed_id) VALUES (3, 1);

-- Follows
INSERT INTO follows (who_follow, follow_who) VALUES (1, 2);
INSERT INTO follows (who_follow, follow_who) VALUES (2, 3);
INSERT INTO follows (who_follow, follow_who) VALUES (3, 1);

-- Questions
INSERT INTO feed_questions (feed_id, deposit, status) VALUES (1, 3000, 'RESOLVING');
INSERT INTO feed_questions (feed_id, deposit, status) VALUES (2, 5000, 'RESOLVED');
INSERT INTO feed_questions (feed_id, deposit, status) VALUES (3, 4000, 'RESOLVING');

-- -- Categories
-- INSERT INTO categories (large, medium, small) VALUES ('LCategory 1', 'MCategory 1', 'SCategory 1');
-- INSERT INTO categories (large, medium, small) VALUES ('LCategory 2', 'MCategory 2', 'SCategory 2');
-- INSERT INTO categories (large, medium, small) VALUES ('LCategory 3', 'MCategory 3', 'SCategory 3');
--
-- -- FeedCategories
-- INSERT INTO feed_categories (feed_id, category_id) VALUES (1, 1);
-- INSERT INTO feed_categories (feed_id, category_id) VALUES (2, 2);
-- INSERT INTO feed_categories (feed_id, category_id) VALUES (3, 3);


