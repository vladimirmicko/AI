package characterrecognition;

import similaritymatrix.Matrica;

public class Main {
	// public BpAnn(String datasetFilename, int datasetDimensionY, int
	// datasetDimensionX, double testTraingRatio,
	// int[] numberOfLayerNodes, double learningCoeffitient, boolean supervised)

	public static void main(String[] args) {
		System.out.println("Backpropagation ANN");
		boolean supervised = true;
		BpAnn bp = new BpAnn("G:/Eclipse - STS/ArtificialIntelligence/Resources/DigitsWithTargets.csv", 1797, 65,
				0.5, new int[] { 64, 64, 32, 1 }, 0.01, supervised);
		System.out.println("\n\nTesting dataset");
		Utility.printMatrix(bp.getTestingDataset());
		
		System.out.println("\n\nTraining dataset");
		Utility.printMatrix(bp.getTrainingDataset());

		System.out.println("\n\n---------------------------------------Training");
		bp.training(1500);

		
		if (supervised) {
			System.out.println("\n\n---------------------------------------Testing supervised backpropagation");
			int numberOfHits = 0;
			for (int y = 0; y < bp.getTestingDataset().length-1; y++) {
				bp.classify(bp.getTestingDataset()[y]);

				//int targetClass = Utility.elementWithMaxValueInArray(bp.getTestingTarget()[y]);
				//int selectedClass = Utility.elementWithMaxValueInArray(bp.getNodes()[bp.getNumberOfLayerNodes().length - 1]);
				int targetClass = (int)Math.round(bp.getTestingTarget()[y][0]);
				int selectedClass = (int)Math.round(bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][0]);
				if (targetClass == selectedClass)
					numberOfHits++;
				System.out.println("" + y + ": " + " Target: " + targetClass + "   Estimation: " + selectedClass);
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
						+ bp.getTestingDataset()[y][3] + "  ---   "	+ " Estimation: " 
						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][0] + " "
						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][1] + " "
						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][2] + " "
						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][3]);
			}
		}
	}
}
