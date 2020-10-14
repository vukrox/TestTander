CREATE DATABASE testtaskdb ENCODING 'UTF-8';

CREATE TABLE IF NOT EXISTS testtaskdb (
  FIELD  INTEGER NOT NULL
);

DELETE FROM testtaskdb;

SELECT field FROM testtaskdb;

INSERT INTO testtaskdb (field) VALUES (?)