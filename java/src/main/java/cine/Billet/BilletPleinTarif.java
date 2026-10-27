package cine.Billet;

public class BilletPleinTarif extends Billet {
    @Override
    public double calculerPrix(double tarifDeBase) {
        return tarifDeBase;
    }
}