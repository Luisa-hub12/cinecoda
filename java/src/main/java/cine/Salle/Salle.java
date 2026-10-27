package cine.Salle;

import cine.CineCodaException;
import cine.EtatSalle;
import cine.Projecteur.Projecteur;

public class Salle {
    private final int numero;
    private final int capacite;
    private final Projecteur projecteur;
    private EtatSalle etat;

    public Salle(int numero, int capacite, Projecteur projecteur) {
        this.numero = numero;
        this.capacite = capacite;
        this.projecteur = projecteur;
        this.etat = EtatSalle.LIBRE;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacite() {
        return capacite;
    }

    public Projecteur getProjecteur() {
        return projecteur;
    }

    public EtatSalle getEtat() {
        return etat;
    }

    public boolean estLibre() {
        return this.etat == EtatSalle.LIBRE;
    }

    public void demarrerSeance() {
        if (this.etat != EtatSalle.LIBRE) {
            throw new CineCodaException("Impossible de démarrer la séance : la salle "
                    + numero + " n'est pas libre (État actuel : " + etat.getLibelle() + ").");
        }
        this.etat = EtatSalle.EN_SEANCE;
    }

    public void terminerSeance() {
        if (this.etat != EtatSalle.EN_SEANCE) {
            throw new CineCodaException("Impossible de terminer la séance : la salle "
                    + numero + " n'est pas en séance.");
        }
        this.etat = EtatSalle.EN_NETTOYAGE;
    }

    public void terminerNettoyage() {
        if (this.etat != EtatSalle.EN_NETTOYAGE) {
            throw new CineCodaException("Impossible de terminer le nettoyage : la salle "
                    + numero + " n'est pas en nettoyage (État actuel : " + etat.getLibelle() + ").");
        }
        this.etat = EtatSalle.LIBRE;
    }

    @Override
    public String toString() {
        return "Salle " + numero + " : " + capacite + " places, " + projecteur;
    }
}