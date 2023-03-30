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
                                                                                                                                                                              //note this is hachCode, not random numbers sequence
INSERT INTO PUBLIC.TEACHER (CREATED_DATE_TIME, F_NAME, HASH_CODE, IS_ACTIVE, L_NAME, MODIFIED_DATE_AND_TIME, WORK_HOURS_PER_WEEK, SHIFT_ID) VALUES ('2023-03-09 18:19:58.476970', 'Jonas', 31901049, true, 'Jonaitis', '2023-03-09 18:19:58.545927', 10, 1);
INSERT INTO PUBLIC.TEACHER (CREATED_DATE_TIME, F_NAME, HASH_CODE, IS_ACTIVE, L_NAME, MODIFIED_DATE_AND_TIME, WORK_HOURS_PER_WEEK, SHIFT_ID) VALUES ('2023-03-09 21:28:03.793325', 'Petras', 31901081, true, 'Petraitis', '2023-03-09 21:14:58.412917', 20, 2);
INSERT INTO PUBLIC.TEACHER (CREATED_DATE_TIME, F_NAME, HASH_CODE, IS_ACTIVE, L_NAME, MODIFIED_DATE_AND_TIME, WORK_HOURS_PER_WEEK, SHIFT_ID) VALUES ('2023-03-09 21:29:03.793325', 'Antanas', 31901113, true, 'Antanaitis', '2023-03-09 21:14:16.699002', 30, 1);
