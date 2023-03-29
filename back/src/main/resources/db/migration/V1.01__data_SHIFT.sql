CREATE TABLE IF NOT EXISTS SHIFT
(
    ID                  BIGINT auto_increment
--         not null
        primary key,
    CREATED_DATE        TIMESTAMP,
    END_INT_ENUM        INTEGER not null,
    IS_ACTIVE           BOOLEAN not null,
    MODIFIED_DATE       TIMESTAMP,
    NAME                CHARACTER VARYING(255),
    SHIFT_ENDING_TIME   CHARACTER VARYING(255),
    SHIFT_STARTING_TIME CHARACTER VARYING(255),
    START_INT_ENUM      INTEGER not null
);

-- INSERT INTO PUBLIC.SHIFT (CREATED_DATE, END_INT_ENUM, IS_ACTIVE, MODIFIED_DATE, NAME, SHIFT_ENDING_TIME, SHIFT_STARTING_TIME, START_INT_ENUM) VALUES ('2023-03-09 18:19:58.471973', 5, true, '2023-03-09 18:19:58.471973', 'Pamaina1', '12:45', '08:00', 1);
-- INSERT INTO PUBLIC.SHIFT (CREATED_DATE, END_INT_ENUM, IS_ACTIVE, MODIFIED_DATE, NAME, SHIFT_ENDING_TIME, SHIFT_STARTING_TIME, START_INT_ENUM) VALUES ('2023-03-09 18:19:58.472973', 5, true, '2023-03-09 18:19:58.472973', 'Pamaina2', '14:35', '08:00', 1);
-- INSERT INTO PUBLIC.SHIFT (CREATED_DATE, END_INT_ENUM, IS_ACTIVE, MODIFIED_DATE, NAME, SHIFT_ENDING_TIME, SHIFT_STARTING_TIME, START_INT_ENUM) VALUES ('2023-03-09 18:19:58.472973', 5, true, '2023-03-09 18:19:58.472973', 'Pamaina3', '15:30', '08:00', 1);
