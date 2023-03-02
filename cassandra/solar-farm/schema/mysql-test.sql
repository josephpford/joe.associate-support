drop database if exists solar_farm_test ;
create database solar_farm_test;
use solar_farm_test;

create table solar_panel (
	id varchar(36),
    section varchar(150),
    `row` int,
    `column` int,
    year_installed int,
    material varchar(150),
    is_tracking boolean,
    CONSTRAINT uq_solar_panel UNIQUE (section, `row`, `column`)
);

delimiter //
create procedure set_known_good_state()
begin
    truncate table solar_panel;

    insert into solar_panel
        (id, section, `row`, `column`, year_installed, material, is_tracking)
    values
        ('e5ba5bd8-3ccb-4a33-ab9f-522871314464', 'The Ridge', 1, 1, 2020, 'POLY_SI', false),
        ('4fc6d67b-d885-4739-bebc-9bdb18de55e8', 'The Ridge', 1, 2, 2020, 'POLY_SI', false),
        ('372a5d19-7e40-4665-8b4f-a9f380f61a36', 'The Ridge', 1, 3, 2020, 'POLY_SI', false);

end //
delimiter ;

call set_known_good_state();
