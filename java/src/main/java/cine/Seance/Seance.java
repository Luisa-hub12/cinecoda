package cine.Seance;

import cine.Billet.Billet;
import cine.CineCodaException;
import cine.Film.SupportFilm;
import cine.Salle.Salle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Seance {
    private final Salle salle;
    private final SupportFilm support;
    private final LocalDate date;
    private final double tarifDeBase;
    private final List<Billet> billetsVendus;
    private boolean demarree;
    private boolean terminee;

    public Seance(Salle salle, SupportFilm support, LocalDate date, double tarifDeBase) {
        this.salle = salle;
        this.support = support;
        this.date = date;
        this.tarifDeBase = tarifDeBase;
        this.billetsVendus = new ArrayList<>();
        this.demarree = false;
        this.terminee = false;
    }

    public Salle getSalle() {
        return salle;
    }

    public SupportFilm getSupport() {
        return support;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTarifDeBase() {
        return tarifDeBase;
    }

    // B2 : Vendre un billet
    public String vendreBillet(Billet billet) {
        if (billetsVendus.size() >= salle.getCapacite()) {
            throw new CineCodaException("Vente refusée : la séance est complète (" + salle.getCapacite() + " places)");
        }
        billetsVendus.add(billet);
        return String.format(Locale.FRANCE, "%s vendu : %.2f €", billet.getLibelle(), billet.calculerPrix(tarifDeBase));
    }

    // B3 : Démarre la séance
    public String demarrer() {
        if (demarree) {
            throw new CineCodaException("La séance a déjà été démarrée.");
        }
        if (!salle.estLibre()) {
            throw new CineCodaException("Démarrage refusé : la Salle " + salle.getNumero()
                    + " n'est pas libre (état : " + salle.getEtat() + ")");
        }
        if (!support.estLisiblePar(salle.getProjecteur(), date)) {
            String raison = support.getRaisonIncompatibilite(salle.getProjecteur(), date);
            throw new CineCodaException("Démarrage refusé : " + raison);
        }

        salle.demarrerSeance();
        this.demarree = true;
        return "Séance démarrée";
    }

    // B3 : Termine la séance
    public String terminer() {
        if (!demarree) {
            throw new CineCodaException("Impossible de terminer une séance non démarrée.");
        }
        if (terminee) {
            throw new CineCodaException("La séance est déjà terminée.");
        }
        salle.terminerSeance();
        support.enregistrerProjection();
        this.terminee = true;
        return "Séance terminée";
    }

    // B4 : Nombre de places restantes
    public int getPlacesRestantes() {
        return salle.getCapacite() - billetsVendus.size();
    }

    // B5 : Taux de remplissage
    public double getTauxRemplissage() {
        if (salle.getCapacite() == 0) return 0.0;
        return ((double) billetsVendus.size() / salle.getCapacite()) * 100.0;
    }

    // B6 : Recette de la séance (Polymorphisme sur Billet)
    public double getRecette() {
        double total = 0.0;
        for (Billet billet : billetsVendus) {
            total += billet.calculerPrix(tarifDeBase);
        }
        return total;
    }

    public List<Billet> getBilletsVendus() {
        return Collections.unmodifiableList(billetsVendus);
    }

    @Override
    public String toString() {
        return String.format(Locale.FRANCE, "%s, Salle %d : %d billet(s), recette %.2f €",
                support.getFilm().getTitre(),
                salle.getNumero(),
                billetsVendus.size(),
                getRecette());
    }
}