package cine.Projecteur;

public class ProjecteurNumerique extends Projecteur {
    public ProjecteurNumerique(String numeroSerie) {
        super(numeroSerie);
    }

    @Override
    public String toString() {
        return "projecteur numérique";
    }
}