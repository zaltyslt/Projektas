create table SOME_THING
(
    ID             BIGINT  not null
        primary key,
    CREATED_DATE   TIMESTAMP,
    IS_ACTIVE      BOOLEAN,
    MODIFIED_DATE  TIMESTAMP,
    NAME           CHARACTER VARYING(255),
    SCHOOL_YEAR    CHARACTER VARYING(255),
    STUDENT_AMOUNT INTEGER not null,
    PROGRAM_GROUP  BIGINT,
    SHIFT_GROUP    BIGINT,
    constraint FK2OFC6VRD8XPAYCXPYJDTEDYA2
        foreign key (SHIFT_GROUP) references SHIFT,
    constraint FKSV36YD8W4RNVPGRV5DRYK002E
        foreign key (PROGRAM_GROUP) references PROGRAM
);

INSERT INTO PUBLIC.SOME_THING (ID, IS_ACTIVE , MODIFIED_DATE, NAME, SCHOOL_YEAR, STUDENT_AMOUNT,PROGRAM_GROUP,SHIFT_GROUP) VALUES (1, TRUE, '2023-03-10 06:13:45.331908', 'Grupė 1', '2023', 50, 1, 1);

