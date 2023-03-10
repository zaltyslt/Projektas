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

