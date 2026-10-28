package cine.Billet;

public class BilletEtudiant extends Billet {
    @Override
    public double calculerPrix(double tarifDeBase) {
        return tarifDeBase * 0.75; // -25%
    }

    @Override
    public String getLibelle() {
        return "Billet étudiant";
    }

}