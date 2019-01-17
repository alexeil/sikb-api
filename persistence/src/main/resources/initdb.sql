DROP SCHEMA IF EXISTS sikb;
CREATE SCHEMA sikb;

DROP TABLE IF EXISTS sikb.sikb;

CREATE TABLE sikb.club (
                        "id" serial PRIMARY KEY,
                        "name" varchar(255),
                        "shortName" varchar(255),
                        "logo" varchar(255)
                        )

CREATE TABLE sikb.affiliation (
                                "id" serial PRIMARY KEY,
                                "associationName" varchar(255),
                                "prefectureNumber" varchar(255),
                                "siretNumber" varchar(255),
                                address varchar(255),
                                "postalCode" varchar(255),
                                "city" varchar(255),
                                "phoneNumber" varchar(255),
                                "email" varchar(255),
                                "webSite" varchar(255)
                              )