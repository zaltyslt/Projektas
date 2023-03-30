CREATE TABLE IF NOT EXISTS SUBJECT_CLASSROOMS
(
    SUBJECT_ID   BIGINT not null,
    CLASSROOM_ID BIGINT not null,
    primary key (SUBJECT_ID, CLASSROOM_ID),
    constraint FKFBROTNNN49J6SG4AID8LKAGG4
        foreign key (SUBJECT_ID) references SUBJECT,
    constraint FKTAMTR7A4M4JJ881WU02BNQHFF
        foreign key (CLASSROOM_ID) references CLASSROOM
);

INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (1, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (2, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (3, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (4, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (5, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (6, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (7, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (8, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (9, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (10, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (11, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (12, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (13, 1);
INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (14, 1);
-- INSERT INTO PUBLIC.SUBJECT_CLASSROOMS (SUBJECT_ID, CLASSROOM_ID) VALUES (15, 1);


