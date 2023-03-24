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

INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (2, 'teamsd@aa.lt', 1);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (3, 'TeamsII', 1);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (1, 'mail2@gool.com', 1);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (0, '800011111', 1);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (0, '800022222', 3);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (1, 'mail1@mail.lt', 3);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (2, 'teams@aa.lt', 3);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (3, 'TeamsI', 3);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (0, '800022222', 2);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (1, 'mail@h.lt', 2);
INSERT INTO PUBLIC.CONTACT (TYPE, CONTACT_BODY, TEACHER_ID) VALUES (2, 'teamsh@aa.lt', 2);
