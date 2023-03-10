create table WORK_DAY
(
    ID                 BIGINT not null
        primary key,
    DATE               DATE,
    SHIFT_ID           BIGINT,
    SUBJECT_ID         BIGINT,
    TEACHER_TEACHER_ID BIGINT,
    WORKDAY_ID         BIGINT,
    constraint FKJW3NOIBOFCRD2EO4IE90T11KX
        foreign key (WORKDAY_ID) references SCHEDULE
);

