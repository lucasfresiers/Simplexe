package epsi;

public class SimplexTableau {

	private double[][] tab;
	private int nbLigne;
	private int nbColonne;

	private Simplex simplex;

	public SimplexTableau(Simplex simplex) {
		this.simplex = simplex;
		nbLigne = simplex.getNbContraintes() + 3;
		nbColonne = simplex.getNbVariables() + 2 + simplex.getNbContraintes();
		this.tab = createTableau();
	}

	private double[][] createTableau() {
		double[][] tableau = new double[nbLigne][nbColonne];

		// Initialisation ligne Cj
		for (int i = 0; i < simplex.getNbVariables(); i++) {
			tableau[0][i + 2] = simplex.getFonctionEconomique().getCoefficients()[i];
		}

		// Initialisation ligne variable sans les ajoutées
		for (int i = 1; i < simplex.getNbContraintes() + 1; i++) {
			for (int j = 0; j < simplex.getNbVariables() + 2; j++) {
				if (j == 0) {
					tableau[i][j] = 0;
				} else if (j == 1) {
					tableau[i][j] = simplex.getContraintes().get(i-1).getCoefficients()[simplex.getNbVariables()];
				} else {
					tableau[i][j] = simplex.getContraintes().get(i-1).getCoefficients()[j - 2];
					System.out.println(simplex.getContraintes().get(i-1));
				}
			}
		}
		int tmp=1 + simplex.getNbVariables();
		for(int i = 1; i < simplex.getNbContraintes() + 1; i++) {
			tmp++;
			for(int j = 2 + simplex.getNbVariables(); j < this.nbColonne; j++) {
				tableau[i][tmp]=1;
			}
		}
//		int t = 0;
//		for (int i = 1; i < simplex.getNbContraintes(); i++) {
//			for (int j = 0; j < simplex.getNbContraintes(); j++) {
//				if (j == t) {
//					tableau[i][j + 2 + simplex.getNbVariables()] = 1;
//					t++;
//				} else {
//					tableau[i][j + 2 + simplex.getNbVariables()] = 0;
//				}
//			}
//		}
		return tableau;
	}

	public double[] getCjMoinsZj() {
		return tab[nbLigne];
	}

	public void afficheTableau() {
		for (int i = 0; i < nbLigne; i++) {
			for (int j = 0; j < nbColonne; j++) {
				System.out.println("Valeur tableau ligne :" + i + " | colonne :" + j + " valeur : [" + tab[i][j] + "]");
			}
		}
	}
	
	public void printTab() {
		for (int i = 0; i < tab.length; i++)
		{
			for (int j = 0; (tab[i] != null && j < tab[i].length); j++)
				System.out.print(tab[i][j] + " ");

			System.out.println();
		}
	}

}
