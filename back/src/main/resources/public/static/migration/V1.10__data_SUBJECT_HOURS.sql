CREATE TABLE IF NOT EXISTS SUBJECT_HOURS
(
    ID            BIGINT auto_increment
        primary key,
    CREATED_DATE  TIMESTAMP,
    HOURS         INTEGER                not null,
    MODIFIED_DATE TIMESTAMP,
    SUBJECT       BIGINT,
    SUBJECT_NAME  CHARACTER VARYING(255) not null,
    DELETED BOOLEAN
);

INSERT INTO PUBLIC.SUBJECT_HOURS (CREATED_DATE, HOURS, MODIFIED_DATE, SUBJECT, SUBJECT_NAME, DELETED) VALUES ('2023-03-10 06:13:45.331908', 80, '2023-03-10 06:13:45.331908', 1, 'Dalykas2', false);