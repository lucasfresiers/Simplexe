package epsi;

public class SimplexTableau {
	
	private double[][] tableau;
	
	public SimplexTableau(int nbLigne, int nbColonne, double[] entrees) {
		this.tableau = createTableau(nbLigne, nbColonne, entrees);
	}

	private double[][] createTableau(int nbLigne, int nbColonne, double[] entrees) {
		double[][] tableau = new double[nbLigne][nbColonne];
		return tableau;
	}

}
