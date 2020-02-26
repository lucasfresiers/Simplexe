package epsi;

public class FonctionEconomique {
    private double[] coefficients;

    public FonctionEconomique(double[] coeffs) {
        this.coefficients = coeffs;
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();

        for(int i = 0; i < this.coefficients.length; i++) {
            b.append(this.coefficients[i]).append("x").append(i+1);
            if(i != this.coefficients.length - 1)
                b.append(" + ");
        }

        return b.toString();
    }
}
