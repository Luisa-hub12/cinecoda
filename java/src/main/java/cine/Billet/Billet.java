package cine.Billet;

public abstract class Billet {
    public abstract double calculerPrix(double tarifDeBase);
}

@Override
public String toString() {
    return billet + "vendu :" + tarifDeBase;
}
