CREATE TABLE IF NOT EXISTS SUBJECT
(
    ID            BIGINT auto_increment
        primary key,
    CREATED_BY    CHARACTER VARYING(255),
    CREATED_DATE  TIMESTAMP,
    DELETED       BOOLEAN,
    DESCRIPTION   CHARACTER VARYING(2000),
    MODIFIED_BY   CHARACTER VARYING(255),
    MODIFIED_DATE TIMESTAMP,
    NAME          CHARACTER VARYING(255),
    MODULE_ID     BIGINT not null,
    constraint FKKKVEBB515MWAFU9LGN2N6VJS0
        foreign key (MODULE_ID) references MODULE
);

INSERT INTO PUBLIC.SUBJECT (CREATED_BY, CREATED_DATE, DELETED, DESCRIPTION, MODIFIED_BY, MODIFIED_DATE, NAME, MODULE_ID) VALUES ('API app', '2023-03-09 18:19:58.448298', false, 'Dalyko 2 aprašymas', 'API app', '2023-03-09 18:19:58.448298', 'Dalykas2', 2);
INSERT INTO PUBLIC.SUBJECT (CREATED_BY, CREATED_DATE, DELETED, DESCRIPTION, MODIFIED_BY, MODIFIED_DATE, NAME, MODULE_ID) VALUES ('API app', '2023-03-09 18:19:58.457982', false, 'Dalyko 1 aprašymas', 'API app', '2023-03-09 18:19:58.457982', 'Dalykas1', 1);
INSERT INTO PUBLIC.SUBJECT (CREATED_BY, CREATED_DATE, DELETED, DESCRIPTION, MODIFIED_BY, MODIFIED_DATE, NAME, MODULE_ID) VALUES ('API app', '2023-03-09 18:19:58.458982', false, 'Dalyko 3 aprašymas', 'API app', '2023-03-09 18:19:58.458982', 'Dalykas3', 3);
