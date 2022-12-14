drop database if exists pet_gallery;
create database pet_gallery;
use pet_gallery;

create table pet (
	pet_id int primary key auto_increment,
    `name` varchar(100) not null,
    image_url varchar(255) not null
);