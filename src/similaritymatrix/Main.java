package similaritymatrix;

public class Main {

	public static void main(String[] args) {
		Matrica matrica = new Matrica();
		matrica.parse();
		matrica.printMatrix();
		
		System.out.println("----------------------Similarity matrix");
		matrica.globalSimilarityMatrix();
		matrica.printSimilarityMatrix();
		
		System.out.println("----------------------Matrix list");
		matrica.printMatrixList();
		

		System.out.println("----------------------Ordered Matrix list");
		matrica.orderCells();
		matrica.printMatrixList();
		
		System.out.println("----------------------Minimum spanning tree");
		matrica.createMst();
		matrica.printMst();
		
		
		System.out.println("----------------------KNN");
		Integer[][] trainingMatrix = new Integer[100][100];
		trainingMatrix[0][0]=5;
		trainingMatrix[0][1]=3;
		trainingMatrix[0][2]=1;
		
		trainingMatrix[1][0]=4;
		trainingMatrix[1][1]=3;
		trainingMatrix[1][2]=2;
		
		trainingMatrix[2][0]=6;
		trainingMatrix[2][1]=3;
		trainingMatrix[2][2]=3;
		
		trainingMatrix[3][0]=7;
		trainingMatrix[3][1]=5;
		trainingMatrix[3][2]=2;
		
		trainingMatrix[4][0]=8;
		trainingMatrix[4][1]=6;
		trainingMatrix[4][2]=1;
		
		Knn knn = new Knn(trainingMatrix, 5, 2);
		Integer ccc[]={9,3};
		Integer knnClass = knn.classify(ccc, 1);
		knn.printOrderedCoordinateList();
		System.out.println("Number of classes: "+knn.getNumberOfClasses());
		System.out.println("Resulting class: "+knnClass);
	}

}
