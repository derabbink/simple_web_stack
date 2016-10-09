ALTER TABLE `external_ids`
RENAME TO `external_ids_OLD`;

CREATE TABLE `external_ids` (
	`xid` char(10) NOT NULL PRIMARY KEY,
	`type` varchar(50) CONSTRAINT `type_options` CHECK (`type` IN ('user', 'app', 'app_scoped_user')),
);

INSERT INTO `external_ids` (`xid`, `type`)
SELECT `xid`, `type` FROM `external_ids_OLD`;

DROP TABLE `external_ids_OLD`;


CREATE TABLE `apps` (
	`id` int NOT NULL auto_increment PRIMARY KEY,
	`xid` char(10) NOT NULL,
	`name` varchar(100) NOT NULL,
	`enabled` int(1) NOT NULL,
	`redirect_uri` varchar(250) NOT NULL,
);

CREATE TABLE `app_roles` (
	`app_id` int NOT NULL,
	`user_id` int NOT NULL,
	`role` varchar(50) CONSTRAINT `role_options` CHECK (`role` IN ('admin')),
);
ALTER TABLE `app_roles` ADD PRIMARY KEY (`user_id`, `app_id`);

CREATE TABLE `app_scoped_ids` (
	`user_id` int NOT NULL,
	`app_scoped_user_xid` char(10) NOT NULL,
	`app_id` int NOT NULL,
);
ALTER TABLE `app_scoped_ids` ADD PRIMARY KEY (`user_id`, `app_id`);

CREATE TABLE `access_tokens` (
	`id` int NOT NULL auto_increment PRIMARY KEY,
	`token_scoped_user_xid` char(10) NOT NULL,
	`salt` char(24) NOT NULL, -- 16 bytes, base64 encoded
	`token` char(88) NOT NULL, -- SHA512 hash, base64 encoded
	`app_scoped_user_xid` char(10) NOT NULL,
	`expires_at` timestamp
);
