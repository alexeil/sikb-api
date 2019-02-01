DROP SCHEMA IF EXISTS sikb cascade;
CREATE SCHEMA sikb;

CREATE TABLE sikb.application
(
  "id"       serial PRIMARY KEY,
  "name"     text UNIQUE,
  "login"    text,
  "password" text
);

INSERT INTO sikb.application
values (1, 'admin', 'admin', 'admin');
INSERT INTO sikb.application
values (2, 'website', 'website', 'website');
INSERT INTO sikb.application
values (2, 'test', 'test', 'test');

CREATE TABLE sikb.user
(
  "id"                            serial PRIMARY KEY,
  "email"                         text UNIQUE,
  "password"                      text,
  "salt"                          text,
  "information"                   json,
  "activationToken"               text,
  "activationTokenExpirationDate" timestamp,
  "resetToken"                    text,
  "resetTokenExpirationDate"      timestamp,
  "accessToken"                   text,
  "enabled"                       boolean default false,
  "creationDate"                  timestamp,
  "modificationDate"              timestamp
);

CREATE TABLE sikb.club
(
  "id"               serial PRIMARY KEY,
  "name"             varchar(255),
  "shortName"        varchar(255),
  "logo"             varchar(255),
  "creationDate"     timestamp,
  "modificationDate" timestamp
);

CREATE TABLE sikb.affiliation
(
  "id"               serial PRIMARY KEY,
  "prefectureNumber" varchar(255),
  "prefectureCity"   varchar(255),
  "siretNumber"      varchar(255),
  "address"          varchar(255),
  "postalCode"       varchar(255),
  "city"             varchar(255),
  "phoneNumber"      varchar(255),
  "email"            varchar(255),
  "webSite"          varchar(255),

  "president"        varchar(255),
  "presidentSex"     varchar(255),
  "secretary"        varchar(255),
  "secretarySex"     varchar(255),
  "treasurer"        varchar(255),
  "treasurerSex"     varchar(255),
  "membersNumber"    integer,
  "electedDate"      date,

  "creationDate"     timestamp,
  "modificationDate" timestamp,
  "season"           varchar(8),
  "clubId"           integer REFERENCES sikb.club (id) ON DELETE CASCADE
);

CREATE TABLE sikb.season
(
  "id"        serial PRIMARY KEY,
  "name"      varchar(255),
  "shortName" varchar(255),
  "begin"     date,
  "end"       date
);

INSERT INTO sikb.season
values (1, 'Saison 2017/2018', '20172018', '2017-09-01', '2018-08-31');
INSERT INTO sikb.season
values (2, 'Saison 2018/2019', '20182019', '2018-09-01', '2019-08-31');

CREATE TABLE sikb.person
(
  "id"               serial PRIMARY KEY,
  "firstName"        varchar(255),
  "name"             varchar(255),
  "sex"              varchar(255),
  "birthDate"        date,
  "address"          varchar(255),
  "postalCode"       varchar(255),
  "city"             varchar(255),
  "phoneNumber"      varchar(255),
  "email"            varchar(255),
  "nationality"      varchar(255),
  "creationDate"     timestamp,
  "modificationDate" timestamp
);

CREATE TABLE sikb.typeLicence
(
  "id"                         serial PRIMARY KEY,
  "name"                       varchar(255),
  "medicalCertificateRequired" boolean
);

INSERT INTO sikb.typeLicence
values (1, 'Sénior Compétition', true);
INSERT INTO sikb.typeLicence
values (1, 'Sénior Loisir', true);
INSERT INTO sikb.typeLicence
values (1, 'Junior', true);
INSERT INTO sikb.typeLicence
values (1, 'Entraineur/Coach', false);
INSERT INTO sikb.typeLicence
values (1, 'Arbitre', true);
INSERT INTO sikb.typeLicence
values (1, 'Dirigeant', false);

CREATE TABLE sikb.licence
(
  "id"                 serial PRIMARY KEY,
  "season"             varchar(8),
  "medicalCertificate" varchar(255), -- à voir stocker un URL ou Bytes
  "formationsRequired" json,
  "types"              json,
  "clubId"             integer REFERENCES sikb.club (id),
  "personId"           integer REFERENCES sikb.person (id) ON DELETE CASCADE
);

CREATE TABLE sikb.formationType
(
  "id"   serial PRIMARY KEY,
  "name" varchar(255)
);

INSERT INTO sikb.formationType
values (1, 'Arbitre Niveau 1');
INSERT INTO sikb.formationType
values (2, 'Arbitre Niveau 2');
INSERT INTO sikb.formationType
values (3, 'Arbitre Niveau 3');
INSERT INTO sikb.formationType
values (4, 'Formateur Arbitre Niveau 1');
INSERT INTO sikb.formationType
values (5, 'Formateur Arbitre Niveau 2');
INSERT INTO sikb.formationType
values (6, 'Formateur Arbitre Niveau 3');


CREATE TABLE sikb.formation
(
  "id"             serial PRIMARY KEY,
  "trainedDate"    date,
  "personId"       integer REFERENCES sikb.person (id),
  "refereeLevelId" integer REFERENCES sikb.formationType (id)
);


-- Création de licence
-- 1) check si la personne existe
-- 1.1) oui, alors update personne + create new Licence
-- 1.2) non, create person & create Licence
-- 2) mise à jour des formations d'une peronne