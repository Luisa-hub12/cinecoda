# Glossaire Métier - CinéCoda

Ce document recense les entités, états, règles et comportements identifiés dans le domaine métier de CinéCoda.

---

## 1. Entités Métier

### Cinéma
L'établissement principal (`CinéCoda`). Il détient et gère ses propres salles par composition (une salle n'existe pas sans son cinéma). Il est responsable de la programmation des séances et du bilan journalier.

### Salle
Espace de projection appartenant à un cinéma. Elle possède un numéro unique, une capacité d'accueil (nombre fixe de places assises) et un projecteur dédié. Elle possède un état qui évolue au fil de la journée.

### État de la salle (Énumération)
Représente la disponibilité d'une salle à un instant donné :
- **`LIBRE`** : la salle est propre et prête à accueillir des spectateurs pour une nouvelle séance.
- **`EN_SEANCE`** (ou `SEANCE`) : une projection est en cours, la salle est occupée.
- **`NETTOYAGE`** : la projection est terminée, la salle est en cours de ménage et ne peut pas accueillir de nouvelle séance tant que le nettoyage n'est pas validé.

### Projecteur (Classe abstraite)
Équipement de projection installé dans une salle, identifié par un numéro de série. Il n'existe pas de projecteur générique :
- **Projecteur numérique** : diffuse des fichiers numériques DCP.
- **Projecteur argentique** : projette des pellicules physiques 35 mm.

### Film
L'œuvre cinématographique, caractérisée par un titre et une durée en minutes.

### Support de film (Interface)
Le moyen physique ou dématérialisé par lequel un film est livré au cinéma :
- **Fichier DCP** : support numérique lisible uniquement sur projecteur numérique. Il est protégé par une clé de lecture valable jusqu'à une date d'expiration précise.
- **Copie 35 mm** : pellicule physique lisible uniquement sur projecteur argentique. Elle subit une usure physique à chaque projection et devient inutilisable à partir de 50 projections.

### Séance
Projection d'un support de film dans une salle précise, à une date donnée, avec un tarif de base. Elle gère les billets vendus et contrôle les étapes de projection (démarrage, fin).

### Billet (Classe abstraite)
Titre d'accès à une séance spécifique. Son prix final est calculé par polymorphisme à partir du tarif de base de la séance :
- **Billet plein tarif** : paie 100 % du tarif de base.
- **Billet étudiant** : bénéficie d'une réduction de 25 % (tarif de base × 0,75).
- **Billet enfant** : tarif fixe de 5,00 €.

---

## 2. Comportements et Règles Métier (rattachés aux entités)

### Rattachés au Cinéma
- **Programmer une séance (B1)** : associe un film/support à une salle, une date et un tarif. Refuse si la salle n'appartient pas au cinéma.
- **Consulter les salles libres (B8)** : filtre et renvoie la liste des salles actuellement à l'état `LIBRE`.
- **Consulter les séances du jour (B9)** : liste toutes les séances programmées à une date donnée, qu'elles aient pu démarrer ou non.
- **Calculer la recette du jour (B10)** : fait la somme des recettes de toutes les séances programmées ce jour-là.

### Rattachés à la Salle
- **Gestion du cycle de vie des états (B7)** :
  - On ne peut démarrer une séance que si la salle est `LIBRE`.
  - Quand la séance démarre, la salle passe `EN_SEANCE`.
  - À la fin de la séance, la salle passe obligatoirement en `NETTOYAGE`.
  - Le nettoyage doit être validé pour que la salle redevienne `LIBRE`.

### Rattachés à la Séance
- **Vendre un billet (B2)** : enregistre un billet vendu. Refuse la vente si le nombre de billets vendus atteint la capacité de la salle (salle complète).
- **Démarrer la séance (B3)** :
  1. Vérifie d'abord que la salle est `LIBRE` (sinon refus).
  2. Vérifie que le support est compatible avec le projecteur à la date de la séance (sinon refus avec motif clair).
  3. Change l'état de la salle en `EN_SEANCE`.
- **Terminer la séance (B3)** :
  1. Passe la salle en `NETTOYAGE`.
  2. Enregistre une projection sur le support utilisé.
- **Calculer les places restantes (B4)** : capacité de la salle − nombre de billets vendus.
- **Calculer le taux de remplissage (B5)** : (billets vendus / capacité) × 100, exprimé en pourcentage.
- **Calculer la recette de la séance (B6)** : somme des prix calculés de tous les billets vendus.

### Rattachés aux Supports de film
- **Vérifier la lisibilité / compatibilité** :
  - Un fichier DCP vérifie que le projecteur est numérique ET que la date de la séance ne dépasse pas la date d'expiration de sa clé.
  - Une copie 35 mm vérifie que le projecteur est argentique ET que son compteur de projections est strictement inférieur à 50.
- **Enregistrer une projection (B11)** :
  - Incrémente le compteur d'usure pour une copie 35 mm.
  - Ne fait rien pour un fichier DCP (pas d'usure physique).
