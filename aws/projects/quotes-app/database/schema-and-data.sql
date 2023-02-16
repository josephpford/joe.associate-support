drop database if exists quotes;
create database quotes;
use quotes;

create table quote (
	quote_id int primary key auto_increment,
    phrase varchar(1024) not null,
    person varchar(128) not null,
    person_image_url varchar(512) null
);

insert into quote (phrase, person, person_image_url) values
	('Computers are useless. They can only give you answers.', 
		'Pablo Picasso', 'https://www.pablopicasso.net/images/Pablo%20Picasso%20Paintings.jpg'),
	('If you don''t like something, change it. If you can''t change it, change your attitude.', 
		'Maya Angelou', 'http://epmgaa.media.clients.ellingtoncms.com/img/photos/2017/02/14/maya_angelou_w_t750x550.jpg?d885fc46c41745b3b5de550c70336c1b382931d2');