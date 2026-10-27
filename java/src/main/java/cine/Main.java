package cine;

import java.util.logging.Logger;

import java.time.LocalDate;
import java.util.List;

public class Main {
    private static Logger log;

    public static void main(String[] args) {
        log = ConsoleLogger.create("cine.scenario");

        System.out.println("=================================================");
        System.out.println("      DEMONSTRATION SYSTEME CINEMA CINÉCODA       ");
        System.out.println("=================================================\n");

        // Initialisation du Cinéma
        Cinema cineCoda = new Cinema("CinéCoda");
    }
}
