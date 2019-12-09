package LaserAutoAssociative;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utility {
	
	public static double[][] loadRandom () {
		double matrix[][] = new double[100][4];
		for (int y = 0; y < Array.getLength(matrix); y++) {
			
//			AntiInflammatory
			double[] a = {random(1.4,4), random(30,100), random(120,400), random(80,150)};
//			Analgesic
//			double[] a = {random(2,3), random(50,100), random(200,400), random(3000,10000)};
//			Acupuncture
//			double[] a = {random(0.5,1), random(1,3), random(8,12), random(0,20)};

			matrix[y]=a;
		}
		
		printMatrix(matrix);
		return matrix;
	}
	

	public static double random (double from, double to) {
		double random=0;
		random = Math.random()*(to-from)+from;

		return random;
	}
	
	public static double[][] loadDatasetFromFile(String csvFile, int datasetDimensionY, int datasetDimensionX) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		double[][] dataset = new double[datasetDimensionY][datasetDimensionX];

		try {
			br = new BufferedReader(new FileReader(csvFile));
			int x = 0;
			int y = 0;
			while ((line = br.readLine()) != null) {
				String[] fileLine = line.split(cvsSplitBy);
				for (String item : fileLine) {
					if (item != null && !item.equals("")) {
						dataset[y][x] = Double.valueOf(item);
						x++;
					}
				}
				y++;
				x = 0;
			}
			return dataset;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataset;
	}

	public static void printMatrix(double[][] matrix) {
		for (int y = 0; y < matrix.length; y++) {
			System.out.print("" + y + ": ");
			for (int x = 0; x < matrix[y].length; x++) {
				System.out.print("" + matrix[y][x] + ", ");
			}
			System.out.println();
		}
	}

	public static double[][] copyMatrix(double[][] oldMatrix) {
		double[][] newMatrix = new double[oldMatrix.length][oldMatrix[0].length];

		for (int y = 0; y < oldMatrix.length; y++) {
			for (int x = 0; x < oldMatrix[0].length; x++) {
				newMatrix[y][x] = (double) oldMatrix[y][x];
			}
		}
		return newMatrix;
	}

	public static double[][] removeRowFromMatrix(double[][] oldMatrix, int row) {
		double[][] newMatrix = new double[oldMatrix.length - 1][oldMatrix[0].length];

		for (int y = 0; y < row; y++) {
			for (int x = 0; x < oldMatrix[0].length; x++) {
				newMatrix[y][x] = (double) oldMatrix[y][x];
			}
		}

		for (int y = row + 1; y < oldMatrix.length; y++) {
			for (int x = 0; x < oldMatrix[0].length; x++) {
				newMatrix[y - 1][x] = (double) oldMatrix[y][x];
			}
		}

		return newMatrix;
	}

	public static double[][][] createTrainingAndTestingDataset(double[][] dataset, double trainingTestRatio) {
		int trainingSetRows = (int) Math.floor(dataset.length * trainingTestRatio);
		int testingSetRows = dataset.length - trainingSetRows;
		// double[][] completeDataset = copyMatrix(dataset);
		double[][] completeDataset = dataset;
		double[][] trainingDataset = new double[trainingSetRows][dataset[0].length];
		double[][] testingDataset = new double[testingSetRows][dataset[0].length];

		int s=0;
		int testingPocetak = (int) Math.floor(dataset.length*trainingTestRatio);
		int testingKraj = dataset.length;
		for (int y = testingPocetak; y < testingKraj; y++) {
			int rowNumber = (int) Math.floor(Math.random() * completeDataset.length);
			testingDataset[s] = completeDataset[y-s];
			completeDataset = Utility.removeRowFromMatrix(completeDataset, y-s);
			s++;
		}
		trainingDataset = completeDataset;
		double[][][] result = new double[2][dataset.length][dataset[0].length];
		result[0] = trainingDataset;
		result[1] = testingDataset;
		return result;
	}

	public static int minValueInArray(int[] array) {
		int min = array[0];
		for (int i = 1; i < array.length; i++) {
		    if (array[i] < min) {
		        min = array[i];
		    }
		}
		return min;
	}
	
	public static int maxValueInArray(int[] array) {
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
		    if (array[i] > max) {
		        max = array[i];
		    }
		}
		return max;
	}
	
	public static int elementWithMaxValueInArray(double[] array) {
		double max = array[0];
		int element = 0;
		for (int i = 1; i < array.length; i++) {
		    if (array[i] > max) {
		        max = array[i];
		        element=i;
		    }
		}
		return element;
	}

}
