CREATE TABLE `external_ids` (
	`xid` char(10) not null primary key,
	`type` varchar(50)
	check (`type` in ('user', 'app', 'app_scoped_user'))
);

CREATE TABLE `users` (
	`id` int not null auto_increment primary key,
	`xid` char(10) not null,
	`name` varchar(100) not null,
	`salt` char(24) not null,
	`password` char(88) not null,
);

CREATE TABLE `apps` (
	`id` int not null auto_increment primary key,
	`xid` char(10) not null,
	`name` varchar(100) not null
);

CREATE TABLE `app_scoped_ids` (
	`user_id` int not null,
	`app_scoped_user_xid` char(10) not null,
	`app_id` int not null,
);
ALTER TABLE `app_scoped_ids` ADD PRIMARY KEY (`user_id`, `app_id`);

CREATE TABLE `access_tokens` (
	`id` int not null auto_increment primary key,
	`user_id` int not null,
	`app_id` int not null,
	`token_scoped_user_xid` char(10) not null,
	`salt` char(24) not null,
	`token` char(88) not null,
	`expires_at` timestamp
);

CREATE TABLE `something` (
	`id` int not null auto_increment primary key,
	`name` varchar(100) not null
);
