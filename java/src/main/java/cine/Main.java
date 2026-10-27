package cine;

import cine.Film.Copie35mm;
import cine.Film.FichierDCP;
import cine.Film.Film;
import cine.Projecteur.ProjecteurArgentique;
import cine.Projecteur.ProjecteurNumerique;
import cine.Salle.Salle;

import java.util.logging.Logger;

import java.time.LocalDate;
import java.util.List;

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
        var supportAvengers = new FichierDCP(avengers, LocalDate.of(2026, 10, 14));
        var rocky = new Film("I Play Rocky", 103);
        var supportRocky = new Copie35mm(rocky, 49);
        log.info(supportDune.toString());
        log.info(supportAvengers.toString());
        log.info(supportRocky.toString());
    }
}
