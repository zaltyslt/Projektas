create table SUBJECT_HOURS
(
    ID            BIGINT     auto_increment            not null
        primary key,
    CREATED_DATE  TIMESTAMP,
    HOURS         INTEGER                not null,
    MODIFIED_DATE TIMESTAMP,
    SUBJECT       BIGINT,
    SUBJECT_NAME  CHARACTER VARYING(255) not null
);

INSERT INTO PUBLIC.SUBJECT_HOURS (CREATED_DATE, HOURS, MODIFIED_DATE, SUBJECT, SUBJECT_NAME) VALUES ('2023-03-10 06:13:45.331908', 80, '2023-03-10 06:13:45.331908', null, 'Dalykas2');
