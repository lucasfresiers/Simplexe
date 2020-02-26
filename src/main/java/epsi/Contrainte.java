package epsi;

public class Contrainte {
    private TypeContrainte constraintType;
    private double[] coefficients;

    public Contrainte(double[] coeffs) {
        this.coefficients = coeffs;
    }

    public TypeContrainte getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(TypeContrainte constraintType) {
        this.constraintType = constraintType;
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();

        for(int i = 0; i < this.coefficients.length - 1; i++) {
            b.append(this.coefficients[i]).append("x").append(i+1);
            if(i != this.coefficients.length - 2)
                b.append(" + ");
        }

        String sign = "=";

        if(this.constraintType == TypeContrainte.GreaterThanEquals)
            sign = ">=";

        if(this.constraintType == TypeContrainte.LessThanEquals)
            sign = "<=";

        b.append(" ").append(sign).append(" ").append(this.coefficients[this.coefficients.length - 1]);

        return b.toString();
    }
}
