package cine;

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

        log.info(salle1.toString());
        log.info(salle2.toString());

        var sallesLibres = cineCoda.getSallesLibres().stream()
                .map(s -> "Salle " + s.getNumero())
                .collect(Collectors.joining(", "));
        log.info("Salles libres : " + sallesLibres);
    }
}
