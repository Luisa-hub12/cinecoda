package cine.Billet;

public abstract class Billet {
    public abstract double calculerPrix(double tarifDeBase);
    public abstract String getLibelle();

    public String formaterVente(double tarifDeBase) {
        return String.format(java.util.Locale.FRANCE, "%s vendu : %.2f €", getLibelle(), calculerPrix(tarifDeBase));
    }
}
