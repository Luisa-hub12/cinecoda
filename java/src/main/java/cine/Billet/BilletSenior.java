package cine.Billet;

public class BilletSenior extends Billet{
    @Override
    public double calculerPrix(double tarifDeBase) {
        return tarifDeBase * 0.70; // 30%
    }

    @Override
    public String getLibelle() {
        return "Billet sénior";
    }
}
