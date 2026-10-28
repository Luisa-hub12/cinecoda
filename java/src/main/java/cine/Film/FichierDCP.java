package cine.Film;

import cine.Projecteur.Projecteur;
import cine.Projecteur.ProjecteurNumerique;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            return "la clé de lecture de « "
                    + film.getTitre()
                    + " » a expiré le "
                    +dateExpirationCle.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "Support compatible.";
    }

    @Override
    public String toString() {
        return film + " : fichier numérique, clé valable jusqu'au " + dateExpirationCle.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}