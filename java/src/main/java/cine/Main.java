package cine;

import cine.Billet.BilletEnfant;
import cine.Billet.BilletEtudiant;
import cine.Billet.BilletPleinTarif;
import cine.Film.Copie35mm;
import cine.Film.FichierDCP;
import cine.Film.Film;
import cine.Projecteur.ProjecteurArgentique;
import cine.Projecteur.ProjecteurNumerique;

import java.util.logging.Logger;

import java.time.LocalDate;
import java.util.Locale;

import java.util.stream.Collectors;

public class Main {
    private static Logger log;

    public static void main(String[] args) {
        log = ConsoleLogger.create("cine.scenario");

        log.info("=== 1. Création du cinéma CinéCoda ===");
        Cinema cineCoda = new Cinema("CinéCoda");
        var salle1 = cineCoda.ajouterSalle(1, 100, new ProjecteurNumerique("NUM-1"));
        var salle2 = cineCoda.ajouterSalle(2, 3, new ProjecteurArgentique("ARG-1"));
        var sallesLibres = cineCoda.getSallesLibres().stream()
                .map(s -> "Salle " + s.getNumero())
                .collect(Collectors.joining(", "));

        log.info(salle1.toString());
        log.info(salle2.toString());
        log.info("Salles libres : " + sallesLibres);

        log.info("");

        log.info("=== 2. Films et supports ===");
        var dune = new Film("Dune 3", 166);
        var supportDune = new FichierDCP(dune, LocalDate.of(2026, 10, 30));
        var avengers = new Film("Avengers: Doomsday", 119);
        var supportAvengers = new FichierDCP(avengers, LocalDate.of(2026,10,14));
        var rocky = new Film("I Play Rocky", 103);
        var supportRocky = new Copie35mm(rocky, 49);
        log.info(supportDune.toString());
        log.info(supportAvengers.toString());
        log.info(supportRocky.toString());

        log.info("");

        log.info("=== 3. Dune 3 en Salle 1 le 15/10/2026 (tarif de base 10,00 €) ===");
        var seanceDune = cineCoda.programmerSeance(1, supportDune, LocalDate.of(2026, 10, 15), 10.0);
        var billet1 = new BilletPleinTarif();
        var billet2 = new BilletEtudiant();
        var billet3 = new BilletEnfant();
        log.info(seanceDune.vendreBillet(billet1));
        log.info(seanceDune.vendreBillet(new BilletPleinTarif()));
        log.info(seanceDune.vendreBillet(billet2));
        log.info(seanceDune.vendreBillet(billet3));
        log.info("Places restantes : " + seanceDune.getPlacesRestantes());
        log.info("Taux de remplissage : " + (int) seanceDune.getTauxRemplissage() + " %");
        log.info(String.format(Locale.FRANCE, "Recette : %.2f €", seanceDune.getRecette())); // %.2f permet d'afficher 2 chiffres après la virgule, sinon on a 32.5

        log.info("");

        log.info("=== 4. Avengers: Doomsday en Salle 1 le 15/10/2026 (tarif de base 10,00 €) ===");
        var seanceAvengers = cineCoda.programmerSeance(1, supportAvengers, LocalDate.of(2026, 10, 15), 10.0);
        try {
            seanceAvengers.demarrer();
        } catch (CineCodaException e) {
            log.info(e.getMessage());
        }

        log.info("");

        log.info("=== 5. I Play Rocky en Salle 1 le 15/10/2026 (tarif de base 10,00 €) ===");
        var seanceRocky = cineCoda.programmerSeance(1, supportRocky, LocalDate.of(2026, 10, 15), 10.0);
        try {
            seanceRocky.demarrer();
        } catch (CineCodaException e) {
            log.info(e.getMessage());
        }
        log.info("État de la Salle " + salle1.getNumero() + " : " + salle1.getEtat());

        log.info("");

        log.info("=== 6. I Play Rocky en Salle 2 le 15/10/2026 (tarif de base 8,00 €) ===");
        var seanceRocky2 = cineCoda.programmerSeance(2, supportRocky, LocalDate.of(2026, 10, 15), 8.0);
        var billet4 = new BilletEtudiant();
        var billet5 = new BilletEtudiant();
        var billet6 = new BilletEtudiant();
        log.info(seanceRocky2.vendreBillet(billet4));
        log.info(seanceRocky2.vendreBillet(billet5));
        log.info(seanceRocky2.vendreBillet(billet6));
        try {
            seanceRocky2.vendreBillet(new BilletEtudiant());
        } catch (CineCodaException e) {
            log.info(e.getMessage());
        }
        log.info("Places restantes : " + seanceRocky2.getPlacesRestantes());
        log.info("Taux de remplissage : " + (int) seanceRocky2.getTauxRemplissage() + " %");
        log.info(seanceRocky2.demarrer());
        log.info(seanceRocky2.terminer());
        log.info("État de la Salle " + salle2.getNumero() + " : " + salle2.getEtat());
        sallesLibres = cineCoda.getSallesLibres().stream()
                        .map(s -> "Salle " + s.getNumero())
                        .collect(Collectors.joining(", "));
        log.info("Salles libres : " + sallesLibres);
        log.info("Projections de la copie de « " + supportRocky.getFilm().getTitre() + " » : " + supportRocky.getNombreProjections());

        log.info("");

        log.info("=== 7. I Play Rocky en Salle 2 le 15/10/2026 (tarif de base 8,00 €) ===");
        var seanceRocky3 = cineCoda.programmerSeance(2, supportRocky, LocalDate.of(2026, 10, 15), 8.0);
        try {
            seanceRocky3.demarrer();
        } catch (CineCodaException e) {
            log.info(e.getMessage());
        }
        log.info(salle2.terminerNettoyage());
    }
}
