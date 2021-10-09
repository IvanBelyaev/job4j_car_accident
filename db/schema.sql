CREATE TABLE accident_types (
  id serial primary key,
  name varchar(2000) NOT NULL
);

CREATE TABLE rules (
  id serial primary key,
  name varchar(2000) NOT NULL
);

CREATE TABLE accidents (
  id serial primary key,
  name varchar(2000) NOT NULL,
  text varchar(2000) NOT NULL,
  address varchar(2000) NOT NULL,
  type_id integer NOT NULL,
  FOREIGN KEY(type_id) REFERENCES accident_types(id)
);

CREATE TABLE accidents_rules (
    accident_id integer NOT NULL,
    rule_id integer NOT NULL,
    FOREIGN KEY(accident_id) REFERENCES accidents(id),
    FOREIGN KEY(rule_id) REFERENCES rules(id),
    PRIMARY KEY(accident_id, rule_id)
);

INSERT INTO accident_types (name) values
('Две машины'),
('Машина и человек'),
('Машина и велосипед');

INSERT INTO rules (name) values
('Статья 1'),
('Статья 2'),
('Статья 3');
