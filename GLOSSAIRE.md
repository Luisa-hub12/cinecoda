# Glossaire - CinéCoda

Dans ce document, nous définissons le vocabulaire métier utilisé pour le cinéma CinéCoda, incluant les entités, comportements, règles et états dans le domaine du cinéma.

## Entités

### Cinéma :

- C'est l'entité principale représentant le cinéma CinéCoda. Il a plusieurs salles, un nom (CinéCoda), ces salles appartiennent seulent à son cinéma, pas à d'autres cinémas, et gère la programmation des séances, la vente des billets et le bilan journalier des recettes.

### Salle :

- Elle possède un numéro unique, aucune autre salle ne peut avoir le même numéro dans le même cinéma, c'est l'espace où se déroulent les projections (numérique ou argentique), avec un certain nombre de places et un type de projecteur.
- Elle possède également un état qui reflète sa disponibilité pour les projections.
- Si une salle est en séance ou en nettoyage, elle n'est pas disponible pour programmer une nouvelle séance.

## Les états de la salle :

- **LIBRE** : la salle est disponible pour programmer une nouvelle séance.
- **SÉANCE** : une projection est en cours dans la salle.
- **NETTOYAGE** : la séance est terminée, la salle est en cours de nettoyage et n'est pas disponible pour programmer une nouvelle séance tant que le nettoyage n'est pas terminé.