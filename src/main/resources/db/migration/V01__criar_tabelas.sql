CREATE TABLE person(
	id integer GENERATED ALWAYS AS IDENTITY,
	name character varying (100) not null,
	login character varying (100) not null,
	password character varying (100) not null,
	PRIMARY KEY(id)
); 
 
CREATE TABLE role (
	id integer GENERATED ALWAYS AS IDENTITY,
	description varchar(50) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE person_role (
	person_id INTEGER NOT NULL,
	role_id INTEGER NOT NULL,
	PRIMARY KEY (person_id, role_id),
	CONSTRAINT fk_person FOREIGN KEY(person_id) REFERENCES person(id),	
	CONSTRAINT fk_role FOREIGN KEY(role_id) REFERENCES role(id)
);

CREATE TABLE post(
	id integer GENERATED ALWAYS AS IDENTITY,
	title character varying(255) not null,
	text character varying(4000) not null,
	publish_date date not null,
	last_update_date date,
	person_id integer,
	PRIMARY KEY(id),
	CONSTRAINT fk_person FOREIGN KEY(person_id) REFERENCES person(id)
);

CREATE TABLE comment(
	id integer GENERATED ALWAYS AS IDENTITY,
	comment character varying(500),
	person_id integer not null,
	post_id integer not null,
	comment_date date not null, 
	PRIMARY KEY(id),
	CONSTRAINT fk_person FOREIGN KEY(person_id) REFERENCES person(id),
	CONSTRAINT fk_post FOREIGN KEY(post_id) REFERENCES post(id)
);

insert into role(description) values ('ROLE_INSERT_POST');
insert into role(description) values ('ROLE_UPDATE_POST');
insert into role(description) values ('ROLE_DELETE_POST');
insert into role(description) values ('ROLE_SEARCH_POST');
insert into role(description) values ('ROLE_INSERT_COMMENT');
insert into role(description) values ('ROLE_UPDATE_COMMENT');
insert into role(description) values ('ROLE_DELETE_COMMENT');
insert into role(description) values ('ROLE_SEARCH_COMMENT');
insert into role(description) values ('ROLE_INSERT_PHOTO_GALERY');
insert into role(description) values ('ROLE_UPDATE_PHOTO_GALERY');
insert into role(description) values ('ROLE_DELETE_PHOTO_GALERY');
insert into role(description) values ('ROLE_SEARCH_PHOTO_GALERY');

--password = 'admin'
insert into person(name, login, password) values('Joao', 'joao', '$2a$10$Juomtp1dJ230l7SNj5YOt.a8hakcTBdwV4ktKDuYVepFn/KxaP/Ma');
insert into person(name, login, password) values('Maria', 'maria', '$2a$10$Juomtp1dJ230l7SNj5YOt.a8hakcTBdwV4ktKDuYVepFn/KxaP/Ma');

insert into person_role(person_id,role_id) values(1,1);
insert into person_role(person_id,role_id) values(1,2);
insert into person_role(person_id,role_id) values(1,3);
insert into person_role(person_id,role_id) values(1,4);
insert into person_role(person_id,role_id) values(1,5);
insert into person_role(person_id,role_id) values(1,6);
insert into person_role(person_id,role_id) values(1,7);
insert into person_role(person_id,role_id) values(1,8);

insert into person_role(person_id,role_id) values(2,1);
insert into person_role(person_id,role_id) values(2,2);
insert into person_role(person_id,role_id) values(2,3);
insert into person_role(person_id,role_id) values(2,4);
insert into person_role(person_id,role_id) values(2,5);
insert into person_role(person_id,role_id) values(2,6);
insert into person_role(person_id,role_id) values(2,7);
insert into person_role(person_id,role_id) values(2,8);

insert into post(title,text,publish_date,person_id) values('Titulo do primeiro Post de teste','Texto do Primeiro Post de teste',date(now()),1);
insert into post(title,text,publish_date,person_id) values('Titulo do segundo Post de teste','Texto do Segundo Post de teste',date(now()),2);