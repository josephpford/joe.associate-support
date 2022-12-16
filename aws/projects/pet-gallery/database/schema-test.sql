drop database if exists pet_gallery_test;
create database pet_gallery_test;
use pet_gallery_test;

create table pet (
	pet_id int primary key auto_increment,
    `name` varchar(100) not null,
    image_url varchar(255) not null
);