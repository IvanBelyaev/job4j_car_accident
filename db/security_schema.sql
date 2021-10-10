CREATE TABLE authorities (
  id serial primary key,
  authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE users (
  id serial primary key,
  username VARCHAR(50) NOT NULL unique,
  password VARCHAR(100) NOT NULL,
  enabled boolean default true,
  authority_id int not null references authoritiesc(id)
);

insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$sOlchXROmocEP82VQY5Q1ed9kkp8ps..gmwN4H1E27WZQ/Djk0p16',
(select id from authorities where authority = 'ROLE_ADMIN')),
('user', true, '$2a$10$ybieMrRv1aMqrn7yNW2rBuE9HZtpDxUY5kiE.cpBXDP6KGcC6leEe',
(select id from authorities where authority = 'ROLE_USER'));