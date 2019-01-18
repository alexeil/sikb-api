DROP SCHEMA IF EXISTS sikb cascade ;
CREATE SCHEMA sikb;

DROP TABLE IF EXISTS sikb.sikb;

CREATE TABLE sikb.club (
                         "id" serial PRIMARY KEY,
                         "name" varchar(255),
                         "shortName" varchar(255),
                         "logo" varchar(255),
                         "creationDate" timestamp,
                         "modificationDate" timestamp
);

CREATE TABLE sikb.affiliation (
                                "id" serial PRIMARY KEY,
                                "prefectureNumber" varchar(255),
                                "prefectureCity" varchar(255),
                                "siretNumber" varchar(255),
                                address varchar(255),
                                "postalCode" varchar(255),
                                "city" varchar(255),
                                "phoneNumber" varchar(255),
                                "email" varchar(255),
                                "webSite" varchar(255),

                                "president" varchar(255),
                                "presidentSex" varchar(255),
                                "secretary" varchar(255),
                                "secretarySex" varchar(255),
                                "treasurer" varchar(255),
                                "treasurerSex" varchar(255),
                                "membersNumber" integer,
                                "electedDate" date,

                                "creationDate" timestamp,
                                "modificationDate" timestamp,
                                "season" varchar(8),
                                "clubId" integer REFERENCES sikb.club(id) ON DELETE CASCADE
);