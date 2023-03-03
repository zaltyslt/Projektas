CREATE TABLE classroom (
  id BIGINT NOT NULL,
   classroom_name VARCHAR(255),
   description VARCHAR(255),
   building_id BIGINT,
   created_date TIMESTAMP,
   modified_date TIMESTAMP,
   CONSTRAINT pk_classroom PRIMARY KEY (id)
);

ALTER TABLE classroom ADD CONSTRAINT FK_CLASSROOM_ON_BUILDING FOREIGN KEY (building_id) REFERENCES building (id);