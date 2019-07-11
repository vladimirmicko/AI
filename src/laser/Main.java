package laser;

import similaritymatrix.Matrica;

public class Main {
	// public BpAnn(String datasetFilename, int datasetDimensionY, int
	// datasetDimensionX, double testTraingRatio,
	// int[] numberOfLayerNodes, double learningCoeffitient, boolean supervised)

	public static void main(String[] args) {
		System.out.println("Backpropagation ANN");
		boolean supervised = false;
		BpAnn bp = new BpAnn("G:/Artificial Intelligence/Software/RomePrograms/ArtificialIntelligence-Rome/Resources/laser-01.csv", 60, 4,
				0.5, new int[] { 4, 4, 4 }, 0.1, supervised);
		// System.out.println("\n\nTesting dataset");
		// Utility.printMatrix(bp.getTestingDataset());
		
		//System.out.println("\n\nTraining dataset");
		//Utility.printMatrix(bp.getTrainingDataset());

		System.out.println("\n\n---------------------------------------Training");
		bp.training(10000);

		
		if (supervised) {
			System.out.println("\n\n---------------------------------------Testing supervised backpropagation");
			int numberOfHits = 0;
			for (int y = 0; y < bp.getTestingDataset().length; y++) {
				bp.classify(bp.getTestingDataset()[y]);

				int targetClass = Utility.elementWithMaxValueInArray(new double[] { bp.getTestingDataset()[y][4],
						bp.getTestingDataset()[y][5], bp.getTestingDataset()[y][6] });
				int selectedClass = Utility
						.elementWithMaxValueInArray(bp.getNodes()[bp.getNumberOfLayerNodes().length - 1]);
				if (targetClass == selectedClass)
					numberOfHits++;
				System.out.println("" + y + ": " + bp.getTestingDataset()[y][4] + " " + bp.getTestingDataset()[y][5]
						+ " " + bp.getTestingDataset()[y][6] + "  ---   " + " Target: " + targetClass
						+ "   Estimation: " + selectedClass + "     SoftMax: "
						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][0] + " "
						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][1] + " "
						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][2]);
			}
			System.out.println("Number of rows: " + bp.getTestingDataset().length);
			System.out.println("Number of hits: " + numberOfHits);
			System.out.println("Percent success: " + 100 * numberOfHits / bp.getTestingDataset().length + "%");
		}

		if (!supervised) {
			System.out.println("\n\n---------------------------------------Testing unsupervised - auto-associative backpropagation");
			for (int y = 0; y < bp.getTestingDataset().length; y++) {
				bp.classify(bp.getTestingDataset()[y]);
				System.out.println("" + y + ": " 
						+ bp.getTestingDataset()[y][0] + " " 
						+ bp.getTestingDataset()[y][1] + " " 
						+ bp.getTestingDataset()[y][2] + " " 
						+ bp.getTestingDataset()[y][3] + " "
						+ "  ---   "	+ " Estimation: " 
						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][0] + " "
						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][1] + " "
						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][2] + " "
						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][3]);
			}
		}
	}
}
