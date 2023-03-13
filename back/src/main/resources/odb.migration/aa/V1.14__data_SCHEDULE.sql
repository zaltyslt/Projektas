create table SCHEDULE
(
    ID            BIGINT  not null
        primary key,
    ACTIVE        BOOLEAN not null,
    CREATED_DATE  TIMESTAMP,
    DATE_FROM     DATE    not null,
    DATE_UNTIL    DATE    not null,
    MODIFIED_DATE TIMESTAMP,
    SCHOOL_YEAR   CHARACTER VARYING(255),
    SEMESTER      CHARACTER VARYING(255),
    GROUP_ID      BIGINT
);

