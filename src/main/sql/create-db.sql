DROP SCHEMA IF EXISTS epers_woe;
CREATE SCHEMA epers_woe;

USE epers_woe;

CREATE TABLE raza (
  id int NOT NULL AUTO_INCREMENT,
  idRaza int not null UNIQUE AUTO_INCREMENT,
  nombre VARCHAR(255) NOT NULL UNIQUE,
  clases VARCHAR(255) NOT NULL,
  peso int NOT NULL,
  alt int NOT NULL,
  energiaI int NOT NULL,
  urlFoto VARCHAR(255) NOT NULL,
  cantP int NOT NULL,
  PRIMARY KEY (id)
);
