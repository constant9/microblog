DROP DATABASE IF EXISTS microblog;
CREATE DATABASE IF NOT EXISTS microblog;
USE microblog;
SET GLOBAL time_zone = '+2:00';
DROP TABLE IF EXISTS users,posts,votes;

CREATE TABLE `users`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime NOT NULL,
  `subject` varchar(50) NOT NULL,
  `text` varchar(255) NOT NULL,
  `update_date` datetime NOT NULL,
  `creator_id` int(11) NOT NULL,
  `vote_positive_score` int(11) default 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_users` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE INDEX IX_vote_positive_score ON posts (vote_positive_score);

CREATE TABLE `votes` (
  `creation_date` datetime NOT NULL,
  `score` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`post_id`,`user_id`),
  CONSTRAINT `FK_posts_id` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `FK_users_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE INDEX IX_post_id_score ON votes (post_id,score);