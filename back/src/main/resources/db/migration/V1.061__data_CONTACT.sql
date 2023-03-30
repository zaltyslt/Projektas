CREATE TABLE IF NOT EXISTS CONTACT
(
    CONTACT_ID   BIGINT auto_increment
        primary key,
    TYPE         SMALLINT not null,
    CONTACT_BODY CHARACTER VARYING(255),
    TEACHER_ID   BIGINT   not null,
    constraint FKK18S44KMACPSN4BQ7BAJ6JH3L
        foreign key (TEACHER_ID) references TEACHER
            on delete cascade
);

INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (2, 'vardas.pavarde@stud.vtmc.lt', 1);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (2, 'vardas.pavarde@stud.vtmc.lt', 2);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (2, 'vardas.pavarde@stud.vtmc.lt', 3);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (2, 'vardas.pavarde@stud.vtmc.lt', 4);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (2, 'vardas.pavarde@stud.vtmc.lt', 5);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (2, 'vardas.pavarde@stud.vtmc.lt', 6);
-- INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (2, 'vardas.pavarde@stud.vtmc.lt', 7);


