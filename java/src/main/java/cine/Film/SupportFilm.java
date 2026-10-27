package cine.Film;

import cine.Projecteur.Projecteur;

import java.time.LocalDate;

public interface SupportFilm {
    Film getFilm();
    boolean estLisiblePar(Projecteur projecteur, LocalDate dateSeance);
    void enregistrerProjection();
    String getRaisonIncompatibilite(Projecteur projecteur, LocalDate dateSeance);
}