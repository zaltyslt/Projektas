CREATE TABLE IF NOT EXISTS TEACHER
(
    TEACHER_ID             BIGINT auto_increment
        primary key,
    CREATED_DATE_TIME      TIMESTAMP,
    F_NAME                 CHARACTER VARYING(30),
    HASH_CODE              INTEGER,
    IS_ACTIVE              BOOLEAN,
    L_NAME                 CHARACTER VARYING(255),
    MODIFIED_DATE_AND_TIME TIMESTAMP,
    WORK_HOURS_PER_WEEK    INTEGER,
    SHIFT_ID               BIGINT,
    constraint FKBU8J41NGVGG6VW7CHLMBTB1Q2
        foreign key (SHIFT_ID) references SHIFT
);

INSERT INTO PUBLIC.TEACHER (CREATED_DATE_TIME, F_NAME, HASH_CODE, IS_ACTIVE, L_NAME, MODIFIED_DATE_AND_TIME, WORK_HOURS_PER_WEEK, SHIFT_ID) VALUES ('2023-03-09 00:00:00.000000', 'Vaclav', 31901049, true, 'Z.', '2023-03-09 00:00:00.000000', 54, 3);
INSERT INTO PUBLIC.TEACHER (CREATED_DATE_TIME, F_NAME, HASH_CODE, IS_ACTIVE, L_NAME, MODIFIED_DATE_AND_TIME, WORK_HOURS_PER_WEEK, SHIFT_ID) VALUES ('2023-03-09 00:00:00.000000', 'Jaroslav', 31901049, true, 'G.', '2023-03-09 00:00:00.000000', 40, 1);
INSERT INTO PUBLIC.TEACHER (CREATED_DATE_TIME, F_NAME, HASH_CODE, IS_ACTIVE, L_NAME, MODIFIED_DATE_AND_TIME, WORK_HOURS_PER_WEEK, SHIFT_ID) VALUES ('2023-03-09 00:00:00.000000', 'Tomas', 31901049, true, 'T.', '2023-03-09 00:00:00.000000', 54, 3);
INSERT INTO PUBLIC.TEACHER (CREATED_DATE_TIME, F_NAME, HASH_CODE, IS_ACTIVE, L_NAME, MODIFIED_DATE_AND_TIME, WORK_HOURS_PER_WEEK, SHIFT_ID) VALUES ('2023-03-09 00:00:00.000000', 'Rita', 31901049, true, 'D.', '2023-03-09 00:00:00.000000', 54, 3);
INSERT INTO PUBLIC.TEACHER (CREATED_DATE_TIME, F_NAME, HASH_CODE, IS_ACTIVE, L_NAME, MODIFIED_DATE_AND_TIME, WORK_HOURS_PER_WEEK, SHIFT_ID) VALUES ('2023-03-09 00:00:00.000000', 'Giedrius', 31901049, true, 'G.', '2023-03-09 00:00:00.000000', 40, 1);
INSERT INTO PUBLIC.TEACHER (CREATED_DATE_TIME, F_NAME, HASH_CODE, IS_ACTIVE, L_NAME, MODIFIED_DATE_AND_TIME, WORK_HOURS_PER_WEEK, SHIFT_ID) VALUES ('2023-03-09 00:00:00.000000', 'Pranas', 31901049, true, 'S.', '2023-03-09 00:00:00.000000', 40, 2);
-- INSERT INTO PUBLIC.TEACHER (CREATED_DATE_TIME, F_NAME, HASH_CODE, IS_ACTIVE, L_NAME, MODIFIED_DATE_AND_TIME, WORK_HOURS_PER_WEEK, SHIFT_ID) VALUES ('2023-03-09 00:00:00.000000', 'Viktor', 31901049, true, 'M.', '2023-03-09 00:00:00.000000', 54, 3);


