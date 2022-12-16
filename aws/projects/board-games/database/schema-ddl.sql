drop database if exists board_games;
create database board_games;
use board_games;

create table category (
	category_id int primary key auto_increment,
    `name` varchar(150) not null
);

create table board_game (
	board_game_id int primary key auto_increment,
    `name` varchar(150) not null,
    image_url varchar(250) not null,
    rating decimal(4,2) not null,
    minimum_players int not null default 1,
    maximum_players int not null,
    in_print bit default 0
);

create table board_game_category (
	board_game_id int not null,
    category_id int not null,
    constraint fk_board_game_category_category_id
		foreign key (category_id)
        references category(category_id),
    constraint fk_board_game_category_board_game_id
		foreign key (board_game_id)
        references board_game(board_game_id)
);

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default(1)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

-- data

insert into app_role (`name`)
values
	('USER'),
	('ADMIN');

-- passwords are set to "P@ssw0rd!"
insert into app_user (username, password_hash, enabled)
values
	('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
	('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

insert into app_user_role
values
	(1, 2),
	(2, 1);

insert into category (`name`)
values
	('Miniatures'),
	('Euro'),
	('Dudes on a Map'),
	('Strategy'),
	('Tactics'),
	('Worker Placement'),
	('Engine Builder'),
	('Living Card Game');

insert into board_game (`name`, image_url, rating, minimum_players, maximum_players, in_print)
values
	('Omicron Protocol', 'https://images.squarespace-cdn.com/content/v1/5ac836cf5cfd7949f0670e85/1606609211466-7HN18H67Z4GCQNNFLRV3/04_CoreMinis.png',7.0,1,4,1),
	('Risk', 'https://cf.geekdo-images.com/Oem1TTtSgxOghRFCoyWRPw__itemrep/img/9F-zB7QO9QubnP8-64HnA60AbB8=/fit-in/246x300/filters:strip_icc()/pic4916782.jpg',9.01,2,6, 0);

insert into board_game_category(board_game_id, category_id)
values
	(1, 1),
	(1, 3),
	(1, 5),
	(2, 3),
	(2, 4);
