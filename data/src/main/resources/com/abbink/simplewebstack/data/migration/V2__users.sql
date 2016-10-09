CREATE TABLE `external_ids` (
	`xid` char(10) NOT NULL PRIMARY KEY,
	`type` varchar(50) CONSTRAINT `type_options` CHECK (`type` IN ('user')),
);

CREATE TABLE `users` (
	`id` int NOT NULL auto_increment PRIMARY KEY,
	`xid` char(10) NOT NULL,
	`name` varchar(100) NOT NULL,
	`salt` char(24) NOT NULL,
	`password` char(88) NOT NULL,
);
