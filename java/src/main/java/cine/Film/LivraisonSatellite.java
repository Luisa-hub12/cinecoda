package cine.Film;

import cine.Projecteur.Projecteur;
import cine.Projecteur.ProjecteurNumerique;

import java.time.LocalDate;

public class LivraisonSatellite implements SupportFilm{
    private final Film film;
    private int nombreProjections;

    public LivraisonSatellite(Film film) {
        this.film = film;
        this.nombreProjections = 0;
    }

    @Override
    public Film getFilm() {
        return film;
    }

    @Override
    public boolean estLisiblePar(Projecteur projecteur, LocalDate dateSeance) {
        return (projecteur instanceof ProjecteurNumerique) && (nombreProjections < 3);
    }

    @Override
    public void enregistrerProjection() {
        this.nombreProjections++;
    }

    @Override
    public String getRaisonIncompatibilite(Projecteur projecteur, LocalDate dateSeance) {
        return "";
    }
}
