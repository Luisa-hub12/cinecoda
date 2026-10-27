package cine.Film;

import cine.Projecteur.Projecteur;
import cine.Projecteur.ProjecteurArgentique;

import java.time.LocalDate;

public class Copie35mm implements SupportFilm {
    private final Film film;
    private int nombreProjections;

    public Copie35mm(Film film) {
        this(film, 0);
    }

    public Copie35mm(Film film, int nombreProjections) {
        this.film = film;
        this.nombreProjections = nombreProjections;
    }

    @Override
    public Film getFilm() {
        return film;
    }

    public int getNombreProjections() {
        return nombreProjections;
    }

    @Override
    public boolean estLisiblePar(Projecteur projecteur, LocalDate dateSeance) {
        return (projecteur instanceof ProjecteurArgentique)
                && (nombreProjections < 50);
    }

    @Override
    public void enregistrerProjection() {
        this.nombreProjections++;
    }

    @Override
    public String getRaisonIncompatibilite(Projecteur projecteur, LocalDate dateSeance) {
        if (!(projecteur instanceof ProjecteurArgentique)) {
            return "La copie 35 mm nécessite un projecteur argentique.";
        }
        if (nombreProjections >= 50) {
            return "La copie 35 mm est usée (" + nombreProjections + " projections effectuees / 50 max).";
        }
        return "Support compatible.";
    }

    @Override
    public String toString() {
        return film + " : copie 35 mm, " + nombreProjections + " projections";
    }
}