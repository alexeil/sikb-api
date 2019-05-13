DROP SCHEMA IF EXISTS sikb cascade;
CREATE SCHEMA sikb;

CREATE TABLE sikb.application
(
  "id"       serial PRIMARY KEY,
  "name"     text UNIQUE,
  "login"    text,
  "password" text
);

CREATE TABLE sikb.user
(
  "id"                            serial PRIMARY KEY,
  "email"                         text UNIQUE,
  "password"                      text,
  "salt"                          text,
  "information"                   jsonb,
  "activationToken"               text,
  "activationTokenExpirationDate" timestamp,
  "resetToken"                    text,
  "resetTokenExpirationDate"      timestamp,
  "accessToken"                   text,
  "profile"                       jsonb,
  "enabled"                       boolean default false,
  "creationDate"                  timestamp,
  "modificationDate"              timestamp
);



CREATE TABLE sikb.profileType
(
  "id"              serial PRIMARY KEY,
  "name"            text UNIQUE,
  "functionalities" varchar(20) array
);



CREATE TABLE sikb.club
(
  "id"               serial PRIMARY KEY,
  "name"             varchar(255),
  "shortName"        varchar(255),
  "logoKey"          varchar(255) unique,
  "logoData"         bytea,
  "creationDate"     timestamp,
  "modificationDate" timestamp
);

CREATE TABLE sikb.season
(
  "id"               varchar(8) PRIMARY KEY,
  "description"      varchar(255),
  "begin"            date,
  "end"              date,
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

  "status"           varchar(20),
  "comment"          text,

  "creationDate"     timestamp,
  "modificationDate" timestamp,
  "season"           varchar(8) REFERENCES sikb.season (id),
  "clubId"           integer REFERENCES sikb.club (id) ON DELETE CASCADE,
  CONSTRAINT unique_affiliation_for_club_season UNIQUE ("clubId", "season")
);



CREATE TABLE sikb.person
(
  "id"                                  serial PRIMARY KEY,
  "firstName"                           varchar(255),
  "name"                                varchar(255),
  "sex"                                 varchar(255),
  "birthDate"                           date,
  "address"                             varchar(255),
  "postalCode"                          varchar(255),
  "city"                                varchar(255),
  "phoneNumber"                         varchar(255),
  "email"                               varchar(255),
  "nationality"                         varchar(255),
  "formations"                          jsonb,
  "photoKey"                            varchar(255) unique,
  "photoData"                           bytea,
  "medicalCertificateKey"               varchar(255) unique,
  "medicalCertificateData"              bytea,
  "medicalCertificateBeginValidityDate" date,
  "creationDate"                        timestamp,
  "modificationDate"                    timestamp
);

CREATE TABLE sikb.licenceType
(
  "id"                         serial PRIMARY KEY,
  "name"                       varchar(255),
  "medicalCertificateRequired" boolean,
  "creationDate"               timestamp,
  "modificationDate"           timestamp
);

CREATE TABLE sikb.licence
(
  "id"               serial PRIMARY KEY,
  "number"           varchar(255) unique,
  "formationsNeed"   integer[],
  "types"            integer[],
  "season"           varchar(8) REFERENCES sikb.season (id),
  "clubId"           integer REFERENCES sikb.club (id),
  "personId"         integer REFERENCES sikb.person (id) ON DELETE CASCADE,
  "creationDate"     timestamp,
  "modificationDate" timestamp,
  CONSTRAINT unique_licence_for_club_season_person UNIQUE ("clubId", "season", "personId")
);

CREATE TABLE sikb.formationType
(
  "id"               serial PRIMARY KEY,
  "name"             varchar(255),
  "creationDate"     timestamp,
  "modificationDate" timestamp
);

CREATE TABLE sikb.formation
(
  "id"               serial PRIMARY KEY,
  "trainedDate"      date,
  "personId"         integer REFERENCES sikb.person (id),
  "refereeLevelId"   integer REFERENCES sikb.formationType (id),
  "creationDate"     timestamp,
  "modificationDate" timestamp
);



CREATE TABLE sikb.team
(
  "id"               serial PRIMARY KEY,
  "name"             varchar(255),
  "season"           varchar(8) REFERENCES sikb.season (id),
  "clubId"           integer REFERENCES sikb.club (id),
  "teamMembers"      jsonb,
  "creationDate"     timestamp,
  "modificationDate" timestamp
);

