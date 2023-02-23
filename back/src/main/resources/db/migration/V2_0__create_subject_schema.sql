CREATE TABLE SUBJECT (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
	CREATED_BY CHARACTER VARYING(255),
	CREATED_DATE TIMESTAMP,
	DESCRIPTION CHARACTER VARYING(255),
	MODIFIED_BY CHARACTER VARYING(255),
	MODIFIED_DATE TIMESTAMP,
	NAME CHARACTER VARYING(255),
	DELETED BOOLEAN,
	MODULE_ID BIGINT,

	CONSTRAINT SUBJECT_PK PRIMARY KEY (ID)
)