CREATE TABLE IF NOT EXISTS work_day
(
    id                 BIGINT auto_increment
        primary key,
    date               date,
    subject_id         BIGINT,
    teacher_teacher_id BIGINT,
    schedule_id        BIGINT,
    classroom_id       BIGINT,
    lesson_start       VARCHAR(255),
    lesson_end         VARCHAR(255),
    online             BOOLEAN,
    CONSTRAINT pk_workday PRIMARY KEY (id)
);

ALTER TABLE work_day
    ADD CONSTRAINT FK_WORKDAY_ON_CLASSROOM FOREIGN KEY (classroom_id) REFERENCES classroom (id);

ALTER TABLE work_day
    ADD CONSTRAINT FK_WORKDAY_ON_SCHEDULE FOREIGN KEY (schedule_id) REFERENCES schedule (id);

ALTER TABLE work_day
    ADD CONSTRAINT FK_WORKDAY_ON_SUBJECT FOREIGN KEY (subject_id) REFERENCES subject (id);

ALTER TABLE work_day
    ADD CONSTRAINT FK_WORKDAY_ON_TEACHER_TEACHER FOREIGN KEY (teacher_teacher_id) REFERENCES teacher (teacher_id);

INSERT INTO work_day ( DATE, SUBJECT_ID, TEACHER_TEACHER_ID, SCHEDULE_ID, CLASSROOM_ID, LESSON_START, LESSON_END, ONLINE)
VALUES ('2023-03-22', 1, 1, 1, 1, '09:40', '08:00',FALSE);