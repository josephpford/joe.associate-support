DROP KEYSPACE solar_farm;

CREATE KEYSPACE
    solar_farm
WITH REPLICATION =
    { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE solar_farm.panels_by_section_row_column (
    section text,
    row int,
    column int,
    id uuid,
    yearInstalled int,
    material text,
    isTracking boolean,
    PRIMARY KEY ((section, row, column))
);

CREATE TABLE solar_farm.panels_by_section (
    section text,
    row int,
    column int,
    id uuid,
    yearInstalled int,
    material text,
    isTracking boolean,
    PRIMARY KEY ((section), row, column)
);

CREATE TABLE solar_farm.panels_by_id (
    id uuid,
    section text,
    row int,
    column int,
    yearInstalled int,
    material text,
    isTracking boolean,
    PRIMARY KEY (id)
);

// panels_by_section_row_column

insert into solar_farm.panels_by_section_row_column
    (section, row, column, id, yearInstalled, material, isTracking)
values
    ('The Ridge', 1, 1, e5ba5bd8-3ccb-4a33-ab9f-522871314464, 2020, 'POLY_SI', false);

insert into solar_farm.panels_by_section_row_column
    (section, row, column, id, yearInstalled, material, isTracking)
values
    ('The Ridge', 1, 2, 4fc6d67b-d885-4739-bebc-9bdb18de55e8, 2020, 'POLY_SI', true);

insert into solar_farm.panels_by_section_row_column
    (section, row, column, id, yearInstalled, material, isTracking)
values
    ('The Ridge', 1, 3, 372a5d19-7e40-4665-8b4f-a9f380f61a36, 2020, 'POLY_SI', false);

// panels_by_section

insert into solar_farm.panels_by_section
    (section, row, column, id, yearInstalled, material, isTracking)
values
    ('The Ridge', 1, 1, e5ba5bd8-3ccb-4a33-ab9f-522871314464, 2020, 'POLY_SI', false);

insert into solar_farm.panels_by_section
    (section, row, column, id, yearInstalled, material, isTracking)
values
    ('The Ridge', 1, 2, 4fc6d67b-d885-4739-bebc-9bdb18de55e8, 2020, 'POLY_SI', true);

insert into solar_farm.panels_by_section
    (section, row, column, id, yearInstalled, material, isTracking)
values
    ('The Ridge', 1, 3, 372a5d19-7e40-4665-8b4f-a9f380f61a36, 2020, 'POLY_SI', false);

// panels_by_id

insert into solar_farm.panels_by_id
    (id, section, row, column, yearInstalled, material, isTracking)
values
    (e5ba5bd8-3ccb-4a33-ab9f-522871314464, 'The Ridge', 1, 1, 2020, 'POLY_SI', false);

insert into solar_farm.panels_by_id
    (id, section, row, column, yearInstalled, material, isTracking)
values
    (4fc6d67b-d885-4739-bebc-9bdb18de55e8, 'The Ridge', 1, 2, 2020, 'POLY_SI', true);

insert into solar_farm.panels_by_id
    (id, section, row, column, yearInstalled, material, isTracking)
values
    (372a5d19-7e40-4665-8b4f-a9f380f61a36, 'The Ridge', 1, 3, 2020, 'POLY_SI', false);
