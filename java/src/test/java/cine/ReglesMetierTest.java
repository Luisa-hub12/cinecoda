package cine;

import cine.Billet.BilletPleinTarif;
import cine.Billet.BilletSenior;
import cine.Film.Copie35mm;
import cine.Film.FichierDCP;
import cine.Film.Film;
import cine.Film.LivraisonSatellite;
import cine.Projecteur.ProjecteurArgentique;
import cine.Projecteur.ProjecteurNumerique;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReglesMetierTest {

    static final LocalDate DATE = LocalDate.of(2026, 10, 15);

    @Test
    void demarrage_refuse_si_cle_expiree() {
        var cinema = new Cinema("Test");
        cinema.ajouterSalle(1, 10, new ProjecteurNumerique("NUM-1"));
        var support = new FichierDCP(new Film("Avengers", 119), LocalDate.of(2026, 10, 14));

        var seance = cinema.programmerSeance(1, support, DATE, 10.0);

        assertThrows(CineCodaException.class, seance::demarrer);
    }

    @Test
    void demarrage_refuse_si_support_incompatible() {
        var cinema = new Cinema("Test");
        cinema.ajouterSalle(1, 10, new ProjecteurNumerique("NUM-1"));
        var support = new Copie35mm(new Film("Rocky", 103), 0);

        var seance = cinema.programmerSeance(1, support, DATE, 10.0);

        assertThrows(CineCodaException.class, seance::demarrer);
    }

    @Test
    void demarrage_refuse_si_copie_usee() {
        var cinema = new Cinema("Test");
        cinema.ajouterSalle(1, 10, new ProjecteurArgentique("ARG-1"));
        var support = new Copie35mm(new Film("Rocky", 103), 50);

        var seance = cinema.programmerSeance(1, support, DATE, 8.0);

        assertThrows(CineCodaException.class, seance::demarrer);
    }

    @Test
    void vente_refusee_si_salle_pleine() {
        var cinema = new Cinema("Test");
        cinema.ajouterSalle(1, 1, new ProjecteurNumerique("NUM-1"));
        var support = new FichierDCP(new Film("Dune", 166), LocalDate.of(2026, 10, 30));

        var seance = cinema.programmerSeance(1, support, DATE, 10.0);
        seance.vendreBillet(new BilletPleinTarif()); // salle pleine (1 place)

        assertThrows(CineCodaException.class, () -> seance.vendreBillet(new BilletPleinTarif()));
    }

    @Test
    void programmation_refusee_si_salle_inexistante() {
        var cinema = new Cinema("Test");
        var support = new FichierDCP(new Film("Dune", 166), LocalDate.of(2026, 10, 30));

        assertThrows(CineCodaException.class,
                () -> cinema.programmerSeance(99, support, DATE, 10.0));
    }

    @Test
    void billet_senior_applique_reduction_30_pourcent() {
        var billet = new BilletSenior();
        assertEquals(7.0, billet.calculerPrix(10.0));
    }
    @Test
    void livraison_satellite_expiree_apres_3_projections() {
        var support = new LivraisonSatellite(new Film("Dune", 166));
        support.enregistrerProjection();
        support.enregistrerProjection();
        support.enregistrerProjection();
        assertFalse(support.estLisiblePar(new ProjecteurNumerique("NUM-1"), LocalDate.of(2026, 10, 15)));
    }

}
