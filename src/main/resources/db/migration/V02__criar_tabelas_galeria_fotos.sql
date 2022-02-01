CREATE TABLE photo_galery(
	id integer GENERATED ALWAYS AS IDENTITY,
	title character varying (255) not null,
	person_id integer not null,
	PRIMARY KEY(id),
	CONSTRAINT fk_person FOREIGN KEY(person_id) REFERENCES person(id)
); 
 
CREATE TABLE photo_galery_list (
	photo_galery_id integer NULL,
	photo_path character varying(255),
	PRIMARY KEY (photo_galery_id, photo_path),
	CONSTRAINT fk_photo_galery FOREIGN KEY(photo_galery_id) REFERENCES photo_galery(id)
);

insert into photo_galery(title, person_id) values('Galeria de fotos 1',1);
insert into photo_galery_list(photo_galery_id,photo_path) values(1,'/fotos/1.jpeg');
insert into photo_galery_list(photo_galery_id,photo_path) values(1,'/fotos/2.jpeg');

insert into role(description) values ('ROLE_INSERT_PHOTO_GALERY');
insert into role(description) values ('ROLE_UPDATE_PHOTO_GALERY');
insert into role(description) values ('ROLE_DELETE_PHOTO_GALERY');
insert into role(description) values ('ROLE_SEARCH_PHOTO_GALERY');

insert into person_role(person_id,role_id) values(1,9);
insert into person_role(person_id,role_id) values(1,10);
insert into person_role(person_id,role_id) values(1,11);
insert into person_role(person_id,role_id) values(1,12);

insert into person_role(person_id,role_id) values(2,9);
insert into person_role(person_id,role_id) values(2,10);
insert into person_role(person_id,role_id) values(2,11);
insert into person_role(person_id,role_id) values(2,12);
