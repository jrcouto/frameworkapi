CREATE TABLE person(
	id integer GENERATED ALWAYS AS IDENTITY,
	name character varying (100) not null,
	login character varying (20) not null,
	password character varying (20) not null,
	PRIMARY KEY(id)
); 

CREATE TABLE post(
	id integer GENERATED ALWAYS AS IDENTITY,
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

insert into post(text,publish_date) values('Teste', now()); 