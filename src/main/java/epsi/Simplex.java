package epsi;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Simplex {
	private int nbVar;
	private List<Integer> varX;
	private List<List<Integer>> constraint;

	public Simplex(int nbVariable) {
		this.nbVar = nbVariable;
		this.varX = new ArrayList<>();
		this.constraint = new ArrayList<>();
	}

	public static void main(String[] args) {

		System.out.println("Hello World!");
	}
}
