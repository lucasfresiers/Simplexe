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
		s.NB_VARIABLES = s.readNbVariables();
		s.readVariables(s.NB_VARIABLES);
		s.NB_CONTRAINTES = s.readNbContraintes();
		s.readContraintes(s.NB_CONTRAINTES, s.NB_VARIABLES);
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
	
	public int varEntrante(int[] cjMoinsZj) {
		int max = 0;
		int index = 0;
		for (int i = 0; i < cjMoinsZj.length; i++) {
			if (max < cjMoinsZj[i]) {
				max = cjMoinsZj[i];
				index = i;
			}
		}
		return index;
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
