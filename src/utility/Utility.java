package utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utility {

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

	public static void printArray(double[] array) {
		for (int x = 0; x < array.length; x++) {
			System.out.print("" + array[x] + ", ");
		}
		System.out.println();
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

		for (int y = 0; y < testingSetRows; y++) {
			int rowNumber = (int) Math.floor(Math.random() * completeDataset.length);
			testingDataset[y] = completeDataset[rowNumber];
			completeDataset = Utility.removeRowFromMatrix(completeDataset, rowNumber);
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

	public static double maxValueInMatrix(double[][] matrix) {
		double max = Math.abs(matrix[0][0]);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; i < matrix[0].length; i++) {
				if (Math.abs(matrix[i][j]) > max) {
					max = Math.abs(matrix[i][j]);
				}
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
				element = i;
			}
		}
		return element;
	}

	public static double[][] copyUperRightToLowerLeft(double[][] wij) {
		for (int i = 0; i < wij[0].length; i++) {
			for (int j = i; j < wij[0].length; j++) {
				wij[j][i] = wij[i][j];
			}
		}
		return wij;
	}

	public static double[][] populateMatrixWithZeros(double[][] matrix) {
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				matrix[y][x] = 0;
			}
		}
		return matrix;
	}

	public static double[] populateArrayWithZeros(double[] array) {
		for (int x = 0; x < array.length; x++) {
			array[x] = 0;
		}
		return array;
	}

	public static double sumOfArray(double[] array) {
		double sum = 0;
		for (int x = 0; x < array.length; x++) {
			sum = sum + array[x];
		}
		return sum;
	}

	public static double[][] normalizeMatrix(double[][] matrix) {
		double max = maxValueInMatrix(matrix);
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				matrix[y][x] = ((matrix[y][x]/max)+1)/2;
			}
		}
		return matrix;
	}
}
