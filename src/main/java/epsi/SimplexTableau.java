package epsi;

import java.util.ArrayList;
import java.util.List;

public class SimplexTableau {

	private double[][] tab;
	private int nbLigne;
	private int nbColonne;

	private Simplex simplex;

	public SimplexTableau(Simplex simplex) {
		this.simplex = simplex;
		nbLigne = simplex.getNbContraintes() + 3;
		nbColonne = simplex.getNbVariables() + 2 + simplex.getNbContraintes();
		createTableau();
	}

	private void createTableau() {
		tab = new double[nbLigne][nbColonne];

		for (int i = 0; i < this.nbLigne; i++) {
			for (int j = 0; j < this.nbColonne; j++) {
				tab[i][j] = 0;
			}
		}
		// Initialisation ligne Cj
		for (int i = 0; i < simplex.getNbVariables(); i++) {
			tab[0][i + 2] = simplex.getFonctionEconomique().getCoefficients()[i];
		}

		// Initialisation ligne variable sans les ajoutées
		for (int i = 1; i < simplex.getNbContraintes() + 1; i++) {
			for (int j = 0; j < simplex.getNbVariables() + 2; j++) {
				if (j == 0) {
					tab[i][j] = 0;
				} else if (j == 1) {
					tab[i][j] = simplex.getContraintes().get(i - 1).getCoefficients()[simplex.getNbVariables()];
				} else {
					tab[i][j] = simplex.getContraintes().get(i - 1).getCoefficients()[j - 2];
				}
			}
		}
		int tmp = 1 + simplex.getNbVariables();
		for (int i = 1; i < simplex.getNbContraintes() + 1; i++) {
			tmp++;
			for (int j = 2 + simplex.getNbVariables(); j < this.nbColonne; j++) {
				tab[i][tmp] = 1;
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
		cjMoinsZj();
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
		String str="";
		for(int b = 0;b<8*(2+this.getSimplex().getNbContraintes()+this.getSimplex().getNbVariables());b++){
			System.out.print("-");
		}
		System.out.println();
		for (int i = 0; i < tab.length; i++) {
			if(i==1) {
				for(int b = 0;b<8*(2+this.getSimplex().getNbContraintes()+this.getSimplex().getNbVariables());b++){
					System.out.print("-");
				}
				System.out.println();
			}
			if(this.nbLigne-3<i) {
				for(int b = 0;b<8*(2+this.getSimplex().getNbContraintes()+this.getSimplex().getNbVariables());b++){
					System.out.print("-");
				}
				System.out.println();	
			}
			for (int j = 0; (tab[i] != null && j < tab[i].length); j++) {
				 str= "| "+String.format("%.2f", tab[i][j]);
				int taille = str.length();
				taille=8-taille;
				
				for(int k=0;k<taille;k++) {
					str+=" ";
				}
				System.out.print(str);

			}
			System.out.println();
		}
	}

	public double produitScalaireParColonne(int idx) {
		double res = 0.0;
		for (int i = 1; i < this.simplex.getNbContraintes() + 1; i++) {
			res += this.tab[i][0] * this.tab[i][idx];
		}

		return res;
	}

	public void cjMoinsZj() {
		for (int i = 1; i < this.simplex.getNbContraintes() + 1; i++) {
			for (int j = 2; j < this.nbColonne ; j++) {
				this.tab[this.nbLigne - 1][j] = this.tab[0][j] - this.tab[this.nbLigne - 2][j];
			}
		}
	}

	public double[][] getTab() {
		return tab;
	}

	public void setTab(double[][] tab) {
		this.tab = tab;
	}

	public int getNbLigne() {
		return nbLigne;
	}

	public void setNbLigne(int nbLigne) {
		this.nbLigne = nbLigne;
	}

	public int getNbColonne() {
		return nbColonne;
	}

	public void setNbColonne(int nbColonne) {
		this.nbColonne = nbColonne;
	}

	public Simplex getSimplex() {
		return simplex;
	}

	public void setSimplex(Simplex simplex) {
		this.simplex = simplex;
	}

	public int varEntrante() {
		double max = 0;
		int index = 0;
		for (int j = 2; j < this.nbColonne; j++) {
			if (max < this.tab[this.nbLigne - 1][j]) {
				max = this.tab[this.nbLigne - 1][j];
				index = j;
			}
		}
		return index;
	}

	public int varSortante() {
		double min = 32000;
		int idx = 0;
		for (int i = 1; i < simplex.getNbContraintes() + 1; i++) {
			double r = this.tab[i][1] / this.tab[i][varEntrante()];
			if (r < min && r > 0) {
				idx = i;
				min = r;
			}
		}
		return idx;
	}

	public void diviserLignePivot(int lignePivot, int varEntrante) {
		this.tab[lignePivot][0] = this.tab[0][varEntrante];
		double oldValue=this.tab[lignePivot][varEntrante];
		for (int j = 1; j < this.nbColonne; j++) {
			
			this.tab[lignePivot][j] = this.tab[lignePivot][j] / oldValue;
		}
	}

	public void normaliserLigneParPivot(int ligneARetrancher,int varEntrante, int varSortante) {
		double multiplicateurLignePivot = this.tab[ligneARetrancher][varEntrante];
		for (int i = 1; i < this.nbColonne; i++) {
			this.tab[ligneARetrancher][i]=this.tab[ligneARetrancher][i]-(multiplicateurLignePivot*this.tab[varSortante][i]);
		}
	}
	
	public void normaliserTableauEntier() {
		List<Integer> ligneANormaliser = new ArrayList<>();
		for(int i=1;i<this.nbLigne-2;i++) {
			ligneANormaliser.add(i);
		}
		int varEntrante = varEntrante();
		int varSortante = varSortante();
		ligneANormaliser.remove((Object)varSortante);
		diviserLignePivot(varSortante, varEntrante);
		for(Integer nbLigne : ligneANormaliser) {
			normaliserLigneParPivot(nbLigne, varEntrante, varSortante);
		}
		for(int j = 2 ; j<this.getNbColonne();j++) {
			this.tab[this.nbLigne-2][j]=produitScalaireParColonne(j);
		}
		cjMoinsZj();
		this.tab[this.nbLigne-2][1]=this.produitScalaireParColonne(1);
	}
	
	
	public boolean isNormalisationFinish() {
		for(int j=2;j<this.nbColonne;j++) {
			if(this.tab[this.nbLigne-1][j]>0) {
				return false;
			}
		}
		return true;
	}

}
