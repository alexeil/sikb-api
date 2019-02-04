INSERT INTO sikb.application
values (1, 'admin', 'admin', 'admin');
INSERT INTO sikb.application
values (2, 'website', 'website', 'website');
INSERT INTO sikb.application
values (3, 'test', 'test', 'test');

INSERT INTO sikb.season
values ('20172018', 'Saison 2017/2018', '2017-09-01', '2018-08-31');
INSERT INTO sikb.season
values ('20182019', 'Saison 2018/2019', '2018-09-01', '2019-08-31');


INSERT INTO sikb.licencetype
values (1, 'Sénior Compétition', true);
INSERT INTO sikb.licencetype
values (2, 'Sénior Loisir', true);
INSERT INTO sikb.licencetype
values (3, 'Junior', true);
INSERT INTO sikb.licencetype
values (4, 'Entraineur/Coach', false);
INSERT INTO sikb.licencetype
values (5, 'Arbitre', true);
INSERT INTO sikb.licencetype
values (6, 'Dirigeant', false);


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