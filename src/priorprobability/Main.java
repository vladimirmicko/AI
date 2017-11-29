package priorprobability;

import utility.Utility;

public class Main {

	public static void main(String[] args) {
		System.out.println("Prior probability");
		PriorProbability pp = new PriorProbability(
				"C:/MyDocuments/EclipseNeon/ArtificialIntelligence/Resources/rooms50.csv", 6, 50);

		System.out.println("\n\n---------------------------------------Dataset");
		Utility.printMatrix(pp.getDataset());
		
		System.out.println("\n\n---------------------------------------Wij matrix");
		pp.calculateWij();
		//pp.setWij(Utility.normalizeMatrix(pp.getWij()));
		pp.setWij(Utility.copyUperRightToLowerLeft(pp.getWij()));
		Utility.printMatrix(pp.getWij());
		//Utility.printMatrix(pp.getWij());
		
		
		System.out.println("\n\n---------------------------------------Xi matrix");
		pp.calculateXi(10);
		Utility.printArray(pp.getXi());
	}
}
