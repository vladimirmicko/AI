package LaserAutoAssociative;

import similaritymatrix.Matrica;

public class Main {
	// public BpAnn(String datasetFilename, int datasetDimensionY, int
	// datasetDimensionX, double testTraingRatio,
	// int[] numberOfLayerNodes, double learningCoeffitient, boolean supervised)

	public static void main(String[] args) {
		System.out.println("Backpropagation ANN");
		boolean supervised = false;

//		String filePath = "G:/Artificial Intelligence/Software/RomePrograms/ArtificialIntelligence-Rome/Resources/laser-01.csv";
		String filePath = "D:/MyDoc/Projects/ArtificialIntelligence/Resources/laser-01.csv";
//		BpAnn parameter:  String datasetFilename, int datasetDimensionY, int datasetDimensionX, double testTraingRatio,int[] numberOfLayerNodes, double learningCoeffitient, boolean supervised
//		BpAnn bp = new BpAnn(filePath, 12, 7, 0.75, new int[] { 7, 7, 7 }, 0.1, supervised);
		BpAnn bp = new BpAnn(filePath, 18, 4, 1, new int[] { 4, 4, 4 }, 0.1, supervised);

		
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
//			for (int y = 0;  y < bp.getTestingDataset().length; y++) {
			for (int y = 0;  y < 100; y++) {	
//				AntiInflammatory
				double[] a = {Utility.normEnergy(Utility.random(4.99,5.01)), Utility.normPower(Utility.random(30,100)), Utility.normIntensity(Utility.random(120,400)), Utility.normFrequency(Utility.random(80,150))};
//				Analgesic
//				double[] a = {Utility.normEnergy(Utility.random(2,3)), Utility.normPower(Utility.random(50,100)), Utility.normIntensity(Utility.random(200,400)), Utility.normFrequency(Utility.random(3000,10000))};
//				Acupuncture
//				double[] a = {random(0.5,1), random(1,3), random(8,12), random(0,20)};
//				double[] a = {Utility.normEnergy(Utility.random(0.5,1)), Utility.normPower(Utility.random(1,3)), Utility.normIntensity(Utility.random(8,12)), Utility.normFrequency(Utility.random(0,20))};
				bp.classify(a);
				
//				bp.classify(bp.getTestingDataset()[y]);
				System.out.println("" + y + ": " 
						+ "In: "+Utility.valEnergy(a[0])+"  "+Utility.valPower(a[1])+"  "+Utility.valIntensity(a[2])+"  "+Utility.valFrequency(a[3]) +"     Out: "
						+ Utility.valEnergy(bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][0]) + " "
						+ Utility.valPower(bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][1]) + " "
						+ Utility.valIntensity(bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][2]) + " "
						+ Utility.valFrequency(bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][3]));
			}
		}
		
//		if (!supervised) {
//			System.out.println("\n\n---------------------------------------Testing unsupervised - auto-associative backpropagation");
//			for (int y = 0; y < bp.getTestingDataset().length; y++) {
//				bp.classify(bp.getTestingDataset()[y]);
//				System.out.println("" + y + ": " 
//						+ bp.getTestingDataset()[y][0] + " " 
//						+ bp.getTestingDataset()[y][1] + " " 
//						+ bp.getTestingDataset()[y][2] + " " 
//						+ bp.getTestingDataset()[y][3] + " "
//						+ "  ---   "	+ " Estimation: " 
//						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][0] + " "
//						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][1] + " "
//						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][2] + " "
//						+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][3]);
//			}
//		}
	}
}
