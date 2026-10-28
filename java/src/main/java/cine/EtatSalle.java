package cine;

public enum EtatSalle {
    LIBRE("Libre et propre"),
    SEANCE("En séance"),
    NETTOYAGE("En nettoyage");

    private final String libelle;

    EtatSalle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}