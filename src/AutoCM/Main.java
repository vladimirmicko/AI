package AutoCM;

import similaritymatrix.Matrica;

public class Main {

	public static void main(String[] args) {
		System.out.println("Backpropagation ANN");
		AutoCM bp = new AutoCM("C:/MyDocuments/EclipseNeon/ArtificialIntelligence/Resources/GangDB.CSV", 27, 14,
				0.01, new int[] { 4, 4, 4 }, 0.5);

		System.out.println("\n\n---------------------------------------Training");
		bp.training(500);

		System.out.println(
				"\n\n---------------------------------------Testing unsupervised - auto-associative backpropagation");
		double diff = 0;
		for (int y = 0; y < bp.getTestingDataset().length; y++) {
			bp.classify(bp.getTestingDataset()[y]);
			System.out.println("" + y + ": " + bp.getTestingDataset()[y][0] + " " + bp.getTestingDataset()[y][1] + " "
					+ bp.getTestingDataset()[y][2] + " " + bp.getTestingDataset()[y][3] + "  --- " + " Est: "
					+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][0] + " "
					+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][1] + " "
					+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][2] + " "
					+ bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][3] + " \tDiff:"
					+ (bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][0] - bp.getTestingDataset()[y][0]) + " "
					+ (bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][1] - bp.getTestingDataset()[y][1]) + " "
					+ (bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][2] - bp.getTestingDataset()[y][2]) + " "
					+ (bp.getNodes()[bp.getNumberOfLayerNodes().length - 1][3] - bp.getTestingDataset()[y][3]));
		}
	}
}
