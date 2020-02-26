package epsi;

public class SimplexTableau {
	
	private double[][] tableau;
	private int nbLigne;
	private int nbColonne;
	
	private Simplex simplex;
	
	public SimplexTableau(Simplex simplex) {
		this.simplex = simplex;
		nbLigne = simplex.getNbContraintes() + 3;
		nbColonne = simplex.getNbVariables() + 2 + simplex.getNbContraintes();
		this.tableau = createTableau();
	}

	private double[][] createTableau() {
		double[][] tableau = new double[nbLigne][nbColonne];
		return tableau;
	}
	
	public double[] getCjMoinsZj() {
		return tableau[nbLigne];
	}

}
