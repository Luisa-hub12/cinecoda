package cine.Projecteur;

public abstract class Projecteur {
    private final String numeroSerie;

    public Projecteur(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }
}
