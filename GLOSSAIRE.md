# Glossaire - CinéCoda

Dans ce document, nous définissons le vocabulaire métier utilisé pour le cinéma CinéCoda, incluant les entités, comportements, règles et états dans le domaine du cinéma.

## <span style="color:green">Entités</span>

### Cinéma :

- C'est l'entité principale représentant le cinéma CinéCoda. Il a plusieurs salles, un nom (CinéCoda), ces salles appartiennent seulent à son cinéma, pas à d'autres cinémas, et gère la programmation des séances, et le bilan journalier des recettes.

### Salle :

- Elle possède un numéro unique, aucune autre salle ne peut avoir le même numéro dans le même cinéma, c'est l'espace où se déroulent les projections (numérique ou argentique), avec un certain nombre de places et un type de projecteur.
- Elle possède également un état qui reflète sa disponibilité pour les projections.
- Si une salle est en séance ou en nettoyage, elle n'est pas disponible pour programmer une nouvelle séance.

## Les états de la salle :

- **LIBRE** : la salle est disponible pour programmer une nouvelle séance.
- **SÉANCE** : une projection est en cours dans la salle.
- **NETTOYAGE** : la séance est terminée, la salle est en cours de nettoyage et n'est pas disponible pour programmer une nouvelle séance tant que le nettoyage n'est pas terminé.

### Projecteur :

- Le projecteur est une classe abstraite
- C'est un écran de projection qui est installé dans une salle, selon la salle ou il est il à deux type :
- <span style="color:red">**Numérique**</span> : il possède un numéro de série unique, il va diffuser des fichiers numériques DCP (Digital Cinema Package).
- <span style="color:red">**Argentique**</span> : il possède un numéro de série unique, il va diffuser des copies 35 mm pour la projection. C'est à dire qu'il projette des pellicules physiques 35mm.
- Le projecteur est identifié pour un numéro de série unique.
- Il n'existe pas de projecteur générique, chaque projecteur doit être soit numérique, soit argentique.

### Film :

- un film possède un titre et une durée (en minutes).

## Support de film ( INTERFACE) :

Le moyen physique ou numérique par lequel un film est projeté dans une salle.

- **FICHIER DCP** : c'est un support qui est lisible uniquement sur projecteur numérique. Il est protéger par sa clée de lecture qui est valable jusqu'a une date d'expiration précise, une fois cette date passée, le fichier ne peut plus être lu.
- **COPIE 35 MM** : c'est un support qui est lisible uniquement sur projecteur argentique. Elle à une usure physique à chaque projection, ce qui limite sa durée de vie, elle devient donc inutilisable au bout de **50 projections**.

### Séance :

- Pour démarrer une séance, il faut vérifier le support de film, s'assurer qu'il est compatible avec le projecteur de la salle et qu'il est en état de projection (fichier DCP non expiré pour le numérique, copie 35 mm non usée pour l'argentique).
- Une séance ne peut être programmée que si la salle est **LIBRE**.
- Une fois la séance démarrée, l'état de la salle passe à **SÉANCE**.
- Après la fin de la projection, l'état de la salle passe à **NETTOYAGE**.
- Une fois le nettoyage terminé, l'état de la salle revient à **LIBRE**.
- <span style="color:red">**Une séance projecte donc un film dans une salle précise, à une date donnée, avec un tarif de base (10€). Elle gère les billets vendus pour cette séance et contrôle les étapes de projection (démarrage, fin).**</span>
- Attention, on ne peut pas vendre plus de billets que la capacité de la salle, personne ne doit pouvoir modifier la capacité de la salle pendant la programmation des séances, car une salle de peut pas changer sa capacité.

### Billet ( CLASSE ABSTRAITE) :
Titre d'accès à une séance spécifique. Son prix final est calculé par polymorphisme selon le type de billet (adulte, enfant, réduit, etc.).

## Types de billets :

- **Adulte** : billet pour un spectateur adulte. Le prix final correspond au tarif de base (10€). On paie donc 100% du tarif de base.
- **Étudiant** : billet pour un spectateur étudiant. Le prix final est généralement réduit par rapport au tarif de base. On bénéficie d'une réduction de 25% sur le tarif de base(tarif de base * 0,75).
- **Enfant** : billet pour un spectateur enfant. Le prix est fixe à 5€.
<span style="color:red">**Attention** : Nous sommes en cours de réflexion pour faire un tarif sénior.</span>

## <span style="color:green">Comportement et règles métier (par rapport aux entités)</span>

### Rattachés au cinéma :
- **Programmer une séance (B1)** : on associe un film ou un support à une salle avec une date et un tarif. On refuse si la salle n'appartient pas au cinéma.
- **Consulter les salles libres (B8)** : on va filtrer et renvoyer la liste des salles actuellement à l'état `LIBRE`.
- **Consulter les séances du jour (B9)** : on va lister toutes les séances programmées à une date donnée, qu'elles aient pu démarrer ou non.
- **Calculer la recette du jour (B10)** : on fait la somme des recettes de toutes les séances programmées sur la date donnée.

### Rattachés à la Salle
- **Gestion du cycle de vie des états (B7)** :
  - On ne peut démarrer une séance que si la salle est `LIBRE`.
  - Quand la séance démarre, la salle passe `EN_SEANCE`.
  - À la fin de la séance, la salle passe obligatoirement en `NETTOYAGE`.
  - Le nettoyage doit être validé pour que la salle redevienne `LIBRE`.

### Rattachés à la Séance
- **Vendre un billet (B2)** : enregistre un billet vendu, on refuse la vente si le nombre de billets vendus atteint la capacité de la salle (salle complète).
- **Démarrer la séance (B3)** :
  1. On vérifie d'abord que la salle est `LIBRE` (sinon refus).
  2. On vérifie que le support est compatible avec le projecteur à la date de la séance (sinon refus avec motif clair).
  3. On change l'état de la salle en `EN_SEANCE`.
- **Terminer la séance (B3)** :
  1. On passe la salle en `NETTOYAGE`.
  2. On enregistre une projection sur le support utilisé.
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


### BONUS :