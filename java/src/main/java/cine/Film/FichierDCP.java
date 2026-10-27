package cine.Film;

import cine.Projecteur.Projecteur;
import cine.Projecteur.ProjecteurNumerique;

import java.time.LocalDate;

public class FichierDCP implements SupportFilm {
    private final Film film;
    private final LocalDate dateExpirationCle;

    public FichierDCP(Film film, LocalDate dateExpirationCle) {
        this.film = film;
        this.dateExpirationCle = dateExpirationCle;
    }

    @Override
    public Film getFilm() {
        return film;
    }

    @Override
    public boolean estLisiblePar(Projecteur projecteur, LocalDate dateSeance) {
        return (projecteur instanceof ProjecteurNumerique)
                && (!dateSeance.isAfter(dateExpirationCle));
    }

    @Override
    public void enregistrerProjection() {
        // Aucune usure physique pour un fichier numérique
    }

    @Override
    public String getRaisonIncompatibilite(Projecteur projecteur, LocalDate dateSeance) {
        if (!(projecteur instanceof ProjecteurNumerique)) {
            return "Le fichier DCP nécessite un projecteur numérique.";
        }
        if (dateSeance.isAfter(dateExpirationCle)) {
            return "La clé de lecture du DCP a expiré le " + dateExpirationCle + ".";
        }
        return "Support compatible.";
    }

    @Override
    public String toString() {
        return film + " : fichier numérique, clé valable jusqu'au " + dateExpirationCle.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}