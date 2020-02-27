package epsi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Simplex {
	private int nbVar;
	private List<Integer> varX;
	private List<Contrainte> constraints;
	private FonctionEconomique Z;
	private int NB_CONTRAINTES;
	private int NB_VARIABLES;

	public Simplex(int nbVariable) {
		this.nbVar = nbVariable;
		this.varX = new ArrayList<>();
		this.constraints = new ArrayList<>();

	}

	public static void main(String[] args) {
		Simplex s = new Simplex(3);
		System.out.println("Hello World!");
//		s.NB_VARIABLES = s.readNbVariables();
//		s.readVariables(s.NB_VARIABLES);
//		s.NB_CONTRAINTES = s.readNbContraintes();
//		s.readContraintes(s.NB_CONTRAINTES, s.NB_VARIABLES);
//		
		s.NB_VARIABLES=2;
		s.NB_CONTRAINTES=3;
		double [] coef = {3,2};
		s.Z = new FonctionEconomique(coef) ;
		double [] coef1 = {2,1,18};
		double [] coef2 = {2,3,42};
		double [] coef3 = {3,1,24};
		Contrainte x1 = new Contrainte(coef1);
		Contrainte x2= new Contrainte(coef2);
		Contrainte x3  = new Contrainte(coef3);
		s.constraints.add(x1);
		s.constraints.add(x2);
		s.constraints.add(x3);
		SimplexTableau st = new SimplexTableau(s);
		st.printTab();
		System.out.println("Variable entrante valeur "+ st.getTab()[st.getNbLigne()-1][st.varEntrante()]);
		System.out.println("Variable sortante ligne " + st.varSortante());
		int varEntrante = st.varEntrante();
		int varSortante = st.varSortante();
		st.diviserLignePivot(st.varSortante(),st.varEntrante());
		st.normaliserLigneParPivot(1,varEntrante,varSortante);
		st.normaliserLigneParPivot(2,varEntrante,varSortante);
		for(int j = 2 ; j<st.getNbColonne();j++) {
			st.getTab()[st.getNbLigne()-2][j]=st.produitScalaireParColonne(j);
		}
		st.cjMoinsZj();
		st.printTab();
		System.out.println("2EME TAB");
		 varEntrante = st.varEntrante();
		 varSortante = st.varSortante();
		st.diviserLignePivot(st.varSortante(),st.varEntrante());
		st.normaliserLigneParPivot(3,varEntrante,varSortante);
		st.normaliserLigneParPivot(2,varEntrante,varSortante);
		for(int j = 2 ; j<st.getNbColonne();j++) {
			st.getTab()[st.getNbLigne()-2][j]=st.produitScalaireParColonne(j);
		}
		st.cjMoinsZj();
		st.printTab();
		System.out.println("3EME TAB");
		 varEntrante = st.varEntrante();
		 varSortante = st.varSortante();
		st.diviserLignePivot(st.varSortante(),st.varEntrante());
		st.normaliserLigneParPivot(3,varEntrante,varSortante);
		st.normaliserLigneParPivot(1,varEntrante,varSortante);
		for(int j = 2 ; j<st.getNbColonne();j++) {
			st.getTab()[st.getNbLigne()-2][j]=st.produitScalaireParColonne(j);
		}
		st.cjMoinsZj();
		st.printTab();
	}
	
	
	public void readVariables(int nbVariables) {
		System.out.println("Construction de Z");
		
		double[] tab = new double[nbVariables];
		
		for (int i = 0; i < nbVariables; i++) {
				tab[i] = this.readCoeff("x" + (i+1));
		}
		Z = new FonctionEconomique(tab);
		System.out.println(Z.toString());
	}
	
	public double readCoeff(String x) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez " + x + ":");
		double coeff = Double.valueOf(sc.nextLine());
		return coeff;
	}
	
	public void readContraintes(int nbContraintes, int nbVariables) {
		System.out.println("Construction des contraintes");
		
		for (int i = 0; i < nbContraintes; i++) {
			double[] tab = new double[nbVariables+1];
			for (int j = 0; j < nbVariables+1; j++) {
				if (j == nbVariables) {
					tab[j] = readCoeff("Q");
				} else {
					tab[j] = readCoeff("x"+ (j+1));
				}
			}
			constraints.add(new Contrainte(tab));
			
		}
		
		System.out.println("Fin des contraintes");
		for (Contrainte contrainte : constraints) {
			contrainte.setConstraintType(TypeContrainte.LessThanEquals);
			System.out.println(contrainte.toString());
		}
	}
	

	public Integer readNbVariables() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez le nombre de variables: ");
		Integer nbVariables = Integer.valueOf(sc.nextLine());
		return nbVariables;
	}
	
	public Integer readNbContraintes() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez le nombre de contraintes: ");
		Integer nbContraintes = Integer.valueOf(sc.nextLine());
		return nbContraintes;
	}
	
	public int getNbVariables() {
		return this.NB_VARIABLES;
	}
	
	public int getNbContraintes() {
		return this.NB_CONTRAINTES;
	}
	
	public FonctionEconomique getFonctionEconomique() {
		return this.Z;
	}
	
	public List<Contrainte> getContraintes() {
		return this.constraints;
	}

}
