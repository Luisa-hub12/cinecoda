package cine.Film;

public class Film {
    private final String titre;
    private final int dureeMinutes;

    public Film(String titre, int dureeMinutes) {
        this.titre = titre;
        this.dureeMinutes = dureeMinutes;
    }

    public String getTitre() {
        return titre;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }
}