CREATE TABLE IF NOT EXISTS PROGRAM_SUBJECT_HOURS_LIST
(
    PROGRAM_ID            BIGINT not null,
    SUBJECT_HOURS_LIST_ID BIGINT not null
        constraint UK_SBWVQY3DSHRB4CFE7L0RI0UGA
            unique,
    constraint FKFSRYFLJ79VDPD0O9Y457HL8QK
        foreign key (SUBJECT_HOURS_LIST_ID) references SUBJECT_HOURS,
    constraint FKKL6TY5MU07HS2P10PL45J1GPN
        foreign key (PROGRAM_ID) references PROGRAM
);

-- INSERT INTO PUBLIC.PROGRAM_SUBJECT_HOURS_LIST (PROGRAM_ID, SUBJECT_HOURS_LIST_ID) VALUES (1, 1);
