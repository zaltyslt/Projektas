create table PROGRAM
(
    ID            BIGINT  not null
        primary key,
    ACTIVE        BOOLEAN not null,
    CREATED_DATE  TIMESTAMP,
    DESCRIPTION   CHARACTER VARYING(2000),
    MODIFIED_DATE TIMESTAMP,
    PROGRAM_NAME  CHARACTER VARYING(200)
);

INSERT INTO PUBLIC.PROGRAM (ID, ACTIVE, CREATED_DATE, DESCRIPTION, MODIFIED_DATE, PROGRAM_NAME)
VALUES (1, true, '2023-03-10 06:13:45.335905', 'programos aprasas', '2023-03-10 06:13:45.335905', 'pirma programa');