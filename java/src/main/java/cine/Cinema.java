package cine;


import cine.Film.SupportFilm;
import cine.Projecteur.Projecteur;
import cine.Salle.Salle;
import cine.Seance.Seance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Cinema {
    private final String nom;
    private final List<Salle> salles;
    private final List<Seance> seances;

    public Cinema(String nom) {
        this.nom = nom;
        this.salles = new ArrayList<>();
        this.seances = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    // La salle est créée PAR le cinéma (Composition).
    public Salle ajouterSalle(int numero, int capacite, Projecteur projecteur) {
        for (Salle s : salles) {
            if (s.getNumero() == numero) {
                throw new CineCodaException("La salle n° " + numero + " existe déjà. Veuillez utiliser un autre numéro.");
            }
        }
        Salle salle = new Salle(numero, capacite, projecteur);
        salles.add(salle);
        return salle;
    }

    // B1 : Programmer une séance
    public Seance programmerSeance(int numeroSalle, SupportFilm support, LocalDate date, double tarifBase) {
        Salle salleTrouvee = null;
        for (Salle s : salles) {
            if (s.getNumero() == numeroSalle) {
                salleTrouvee = s;
                break;
            }
        }
        if (salleTrouvee == null) {
            throw new CineCodaException("Programmation refusée : le cinéma " + nom + " n'a pas de salle " + numeroSalle);
        }

        Seance seance = new Seance(salleTrouvee, support, date, tarifBase);
        seances.add(seance);
        return seance;
    }

    // B8 : Salles libres
    public List<Salle> getSallesLibres() {
        List<Salle> libres = new ArrayList<>();
        for (Salle s : salles) {
            if (s.estLibre()) {
                libres.add(s);
            }
        }
        return libres;
    }


    // B9 : Séances programmées un jour donné
    public List<Seance> getSeancesDuJour(LocalDate date) {
        List<Seance> result = new ArrayList<>();
        for (Seance s : seances) {
            if (s.getDate().equals(date)) {
                result.add(s);
            }
        }
        return result;
    }

    // B10 : Recette du jour
    public double getRecetteDuJour(LocalDate date) {
        double recetteTotale = 0.0;
        for (Seance s : getSeancesDuJour(date)) {
            recetteTotale += s.getRecette();
        }
        return recetteTotale;
    }

    public List<Salle> getSalles() {
        return Collections.unmodifiableList(salles);
    }
}