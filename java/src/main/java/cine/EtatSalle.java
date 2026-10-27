package cine;

public enum EtatSalle {
    LIBRE("Libre et propre"),
    EN_SEANCE("En séance"),
    EN_NETTOYAGE("En nettoyage");

    private final String libelle;

    EtatSalle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}