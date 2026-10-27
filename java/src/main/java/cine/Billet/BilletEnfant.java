package cine.Billet;

public class BilletEnfant extends Billet {
    @Override
    public double calculerPrix(double tarifDeBase) {
        return 5.0; // 5 € fixe
    }

    @Override
    public String getLibelle() {
        return "Billet enfant";
    }
}