# Expression de besoin fonctionnel Sikb

## But

Le but de cette application est de faciliter la gestion pour la FKBF et les clubs.

L'application devra fournir une variété de fonctionnalités essentielles à la gestion d'un club (affiliations, licences etc.). Le but est de réduire le temps et la complexité de ces tâches afin que les clubs se consacre au développement de leur club et de notre sport.

## Fonctionnalités

### MVP 1 (most valuable product)
*  gestion des utilisateurs (avec une gestion de droit/périmètre)
*  gestion de "mes informations" pour un club
*  gestion simplifiée des (ré) affiliations
*  gestion des actions/formation effectuer dans l'année
*  gestion des personnes (joueur, arbitre etc.)
*  gestion simplifiée des licences
*  gestion simplifiée des arbitres

### Autres fonctionnalités
*  recherche de personnes selon critères (mailing, export)
*  facturation licence
*  gestion du championnat
*  facturation championnat
*  gestion des formations, avoir le nombre de formations/formés par formateurs
*  etc.


## Fonctionnalités détaillées

### Gestion des utilisateurs

*  Il doit être possible de créer un compte de façon sécurisée pour un utilisateur
*  A la création de ce compte par un administrateur, l'utilisateur reçoit un mail avec un lien temporaire d'activation de son compte.
*  lorsque l'utilisateur clique sur ce lien il est redirigé vers une page pour choisir son mot de passe et confirmer la création de son compte. Possiblement accepter des conditions d'utilisation etc.
*  Ensuite l'utilisateur doit avoir la possibilité de se connecter de façon simple avec ses identifiants
*  une fois connecté, en fonction du type de compte (club, ligue, FKBF etc.) il doit avoir une version simplifiée en fonction de ses droits d'accès. Par exemple un club verra : "modifier mes informations" alors qu'une ligue aura accès à l'ensemble des clubs sous sa responsabilité.
*  lorsqu'un club accède à son espace utilisateur, il peut :
	*  voir/modifier ses informations
	*  s'affilier ou se ré-affilier
	*  Modifier les informations d'une personne (qui a vraiment le droit ? Périmètre ? Informations minimales si pas de licence dans le club ?)
	*  créer des licences pour ses adhérents

### gestion de droit/périmètre

Il existe plusieurs type de compte :
* Club
* Ligue
* FKBF
* etc.

Il existe un ensemble de droits :
* Voir l'ensemble des clubs
* Modifier un club
* Modifier un ensemble de club
* etc.

Voici la matrice des gestion des droits :
// TODO

### Gestion de "mes informations" pour un club

* Un club ne peut être créé que par un administrateur
* A sa création seul le nom du club est renseigné
* un club peux modifier l'ensemble de ses informations (cf [swagger](http://ec2-35-180-42-251.eu-west-3.compute.amazonaws.com:8080/sikb/swagger-ui/#/clubs/updateClub))

### Gestion simplifiée des (ré) affiliations

* Lors de sa première année le club peux s'affilier de devra remplir l'ensemble des champs requis (cf [swagger](http://ec2-35-180-42-251.eu-west-3.compute.amazonaws.com:8080/sikb/swagger-ui/#/affiliations/createAffiliation))
* lors des années suivantes, il pourra se ré-affilier et l'ensemble des champs sera déjà pré rempli. Il aura donc juste à mettre à jour si nécessaire.

### Gestion des actions/formation effectuer dans l'année

* Tout au long de l'année, un club doit avoir la possibilité de lister l'ensemble des actions effectuées (initiations, activités privées etc.)
* cette fonctionnalités permet aux clubs de suivre et d'avoir un historique de leur activité
* la consolidation des activités de tous les clubs est envoyé à la fédération internationale lors de l'affiliation à celle-ci afin d'avoir l'ensemble des actions aux connaissances de la FKBF menées sur le territoire français

### Gestion des personnes (joueur, arbitre etc.)

* Un club doit être en mesure de lister les personnes
* un club doit pouvoir créer et modifier des fiches de personnes (cf swagger)
* un contrôle contre les doublons (nom / prénom / date naissance ? ) Doit être effectué

### Gestion simplifiée des licences

* Il doit être possible de créer/modifier la licence d'une personne (cf swagger).
* il faut mettre un système de validation en place (manuel ou automatique)
* Il doit être possible de voir l'historique des licences d'une personne

### gestion simplifiée des formations (arbitre, formateur etc.)

* Il est possible de rajouter une formation à une personne avec sa date de validation
* il est possible de lister l'ensemble des formations d'une personne
* il est possible de rechercher toutes les personnes ayant passé une certaine formation.

## Sécurité

L'application devra permettre la gestion des informations personnelles et des transactions en accord avec la [RGPD](https://fr.wikipedia.org/wiki/R%C3%A8glement_g%C3%A9n%C3%A9ral_sur_la_protection_des_donn%C3%A9es).
