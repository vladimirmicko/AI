package backpropagationunsupervised;

public class BpAnn {

	private String datasetFilename;
	private int datasetDimensionY = 150;
	private int datasetDimensionX = 7;
	private double testTraingRatio;

	private int[] numberOfLayerNodes;
	private double learningCoeffitient;
	private boolean supervised;


	private double[][] dataset;
	private double[][] trainingDataset;
	private double[][] testingDataset;

	private double[][][] weights;
	private double[][] nodes;
	private double[][] weightedSums;

	private int currentDatasetRow;
	private double[][] target;
	private double[][] errors;
	private double rowError;
	private double epochError;
	

	public BpAnn() {
	}

	public BpAnn(String datasetFilename, int datasetDimensionY, int datasetDimensionX, double testTraingRatio) {
		this.datasetFilename = datasetFilename;
		this.datasetDimensionY = datasetDimensionY;
		this.datasetDimensionX = datasetDimensionX;
		this.testTraingRatio = testTraingRatio;

		dataset = Utility.loadDatasetFromFile(datasetFilename, datasetDimensionY, datasetDimensionX);
		double[][][] sets = Utility.createTrainingAndTestingDataset(dataset, testTraingRatio);
		trainingDataset = sets[0];
		testingDataset = sets[1];
	}

	public BpAnn(String datasetFilename, int datasetDimensionY, int datasetDimensionX, double testTraingRatio,
			int[] numberOfLayerNodes, double learningCoeffitient, boolean supervised) {

		this(datasetFilename, datasetDimensionY, datasetDimensionX, testTraingRatio);
		this.numberOfLayerNodes = numberOfLayerNodes;
		this.learningCoeffitient = learningCoeffitient;
		this.supervised = supervised;

		this.populateNodesWithZeros();
		this.populateWeightedSumsWithZeros();
		this.populateWeightsWithRandom();
		populateErrorsWithZeros();
		
		if (supervised) target = createTarget(trainingDataset);
		else target = createTargetAA(trainingDataset);
	}

	public double[][] createTarget(double[][] data) {
		target = new double[trainingDataset.length][trainingDataset[0].length - numberOfLayerNodes[0]];
		for (int y = 0; y < trainingDataset.length; y++) {
			for (int x = numberOfLayerNodes[0]; x < trainingDataset[0].length; x++) {
				target[y][x - numberOfLayerNodes[0]] = trainingDataset[y][x];
			}
		}
		return target;
	}
	
	public double[][] createTargetAA(double[][] data) {
		target = new double[trainingDataset.length][numberOfLayerNodes[0]];
		for (int y = 0; y < trainingDataset.length; y++) {
			for (int x = 0; x < numberOfLayerNodes[0]; x++) {
				target[y][x] = trainingDataset[y][x];
			}
		}
		return target;
	}

	public void populateNodesWithZeros() {
		nodes = new double[numberOfLayerNodes.length][Utility.maxValueInArray(numberOfLayerNodes)];
		for (int y = 0; y < nodes.length; y++) {
			for (int x = 0; x < nodes[0].length; x++) {
				nodes[y][x] = 0;
			}
		}
	}

	public void populateWeightedSumsWithZeros() {
		weightedSums = new double[numberOfLayerNodes.length][Utility.maxValueInArray(numberOfLayerNodes)];
		for (int y = 0; y < weightedSums.length; y++) {
			for (int x = 0; x < weightedSums[0].length; x++) {
				weightedSums[y][x] = 0;
			}
		}
	}

	public void populateErrorsWithZeros() {
		errors = new double[numberOfLayerNodes.length][Utility.maxValueInArray(numberOfLayerNodes)];
		for (int y = 0; y < errors.length; y++) {
			for (int x = 0; x < errors[0].length; x++) {
				errors[y][x] = 0;
			}
		}
	}

	public void populateWeightsWithRandom() {
		weights = new double[numberOfLayerNodes.length][Utility.maxValueInArray(numberOfLayerNodes)][Utility
				.maxValueInArray(numberOfLayerNodes)];
		for (int z = 1; z < numberOfLayerNodes.length; z++) {
			for (int y = 0; y < weights[0].length; y++) {
				for (int x = 0; x < weights[0][0].length; x++) {
					weights[z][y][x] = Math.random() * 2 - 1;
				}
			}
		}
	}

	public void training(int nEpochs) {
		epochError=0;
		for (int epoch = 0; epoch < nEpochs; epoch++) {
			for (int y = 0; y < trainingDataset.length; y++) {
				currentDatasetRow = y;
				nodes[0] = trainingDataset[y];
				for (int layerNumber = 1; layerNumber < numberOfLayerNodes.length-1; layerNumber++) {
					calculateHiddenLayer(layerNumber);
				}
				calculateOutput();
				calculateErrors();
				epochError=epochError+rowError;
				backPropagate();
			}
			epochError=epochError/nEpochs;
			System.out.println("Error on epoch: "+epoch+" = "+epochError);
		}
		
	}
	
	

	public void classify(double[] row) {
		nodes[0] = row;
		for (int layerNumber = 1; layerNumber < numberOfLayerNodes.length-1; layerNumber++) {
			calculateHiddenLayer(layerNumber);
		}
		calculateOutput();
	}

	public void calculateHiddenLayer(int layerNumber) {
		double ws = 0;
		for (int x = 0; x < numberOfLayerNodes[layerNumber]; x++) {
			ws = 0;
			for (int px = 0; px < numberOfLayerNodes[layerNumber - 1]; px++) {
				ws = ws + nodes[layerNumber - 1][px] * weights[layerNumber][x][px];
			}
			weightedSums[layerNumber][x] = ws;
			nodes[layerNumber][x] = activationFunction(ws);
		}
	}

	public double activationFunction(double ws) {
		double value = 0;
		value = 1 / (1 + Math.pow(Math.E, -ws));
		return value;
	}

	public double derivative(double value) {
		value = activationFunction(value) - Math.pow(activationFunction(value), 2);
		return value;
	}

	public void calculateOutput() {
		double ws = 0;
		double sumOutputs = 0;
		int layerNumber = numberOfLayerNodes.length-1;
		for (int x = 0; x < numberOfLayerNodes[layerNumber]; x++) {
			ws = 0;
			for (int px = 0; px < numberOfLayerNodes[layerNumber - 1]; px++) {
				ws = ws + nodes[layerNumber - 1][px] * weights[layerNumber][x][px];
			}
			nodes[layerNumber][x] = ws;
			weightedSums[layerNumber][x] = ws;
			sumOutputs = sumOutputs + Math.pow(Math.E, nodes[layerNumber][x]);
		}

		if(supervised){
			for (int x = 0; x < numberOfLayerNodes[layerNumber]; x++) {
				nodes[layerNumber][x] = Math.pow(Math.E, nodes[layerNumber][x]) / sumOutputs;
			}
		}
	}

	public void calculateErrors() {
		rowError=0;
		int layerNumber = numberOfLayerNodes.length-1;
		for (int x = 0; x < numberOfLayerNodes[layerNumber]; x++) {
			errors[layerNumber][x] = derivative(weightedSums[layerNumber][x])
					* (target[currentDatasetRow][x] - nodes[layerNumber][x]);
			//System.out.println("Layer: "+layerNumber+", node: "+x+" Input: "+nodes[0][x]+" Target: "+target[currentDatasetRow][x]+" Node value: "+nodes[layerNumber][x]+" Error: "+errors[layerNumber][x]);
			rowError=rowError+Math.sqrt(Math.pow(target[currentDatasetRow][x] - nodes[layerNumber][x],2));
		}
		
		rowError=rowError/numberOfLayerNodes[layerNumber];
		//System.out.println("----------------"+rowError);

		for (layerNumber = numberOfLayerNodes.length-2; layerNumber > 0; layerNumber--) {
			for (int x = 0; x < numberOfLayerNodes[layerNumber]; x++) {
				errors[layerNumber][x] = 0;
				for (int nx = 0; nx < numberOfLayerNodes[layerNumber + 1]; nx++) {
					errors[layerNumber][x] = errors[layerNumber + 1][nx] * weights[layerNumber + 1][nx][x];
				}
				errors[layerNumber][x] = derivative(weightedSums[layerNumber][x]) * errors[layerNumber][x];
			}
		}
	}

	public void backPropagate() {
		for (int layerNumber = 1; layerNumber < numberOfLayerNodes.length; layerNumber++) {
			for (int x = 0; x < numberOfLayerNodes[layerNumber]; x++) {
				for (int px = 0; px < numberOfLayerNodes[layerNumber - 1]; px++) {
					weights[layerNumber][x][px] = weights[layerNumber][x][px]
							+ learningCoeffitient * errors[layerNumber][x] * nodes[layerNumber - 1][px];
				}
			}
		}
	}

	public String getDatasetFilename() {
		return datasetFilename;
	}

	public void setDatasetFilename(String datasetFilename) {
		this.datasetFilename = datasetFilename;
	}

	public int getMatrixDimensionY() {
		return datasetDimensionY;
	}

	public void setMatrixDimensionY(int matrixDimensionY) {
		this.datasetDimensionY = matrixDimensionY;
	}

	public int getMatrixDimensionX() {
		return datasetDimensionX;
	}

	public void setMatrixDimensionX(int matrixDimensionX) {
		this.datasetDimensionX = matrixDimensionX;
	}

	public double getTestTraingRatio() {
		return testTraingRatio;
	}

	public void setTestTraingRatio(double testTraingRatio) {
		this.testTraingRatio = testTraingRatio;
	}

	
	public double getLearningCoeffitient() {
		return learningCoeffitient;
	}

	public void setLearningCoeffitient(double learningCoeffitient) {
		this.learningCoeffitient = learningCoeffitient;
	}

	public double[][] getDataset() {
		return dataset;
	}

	public void setDataset(double[][] dataset) {
		this.dataset = dataset;
	}

	public double[][] getTrainingDataset() {
		return trainingDataset;
	}

	public void setTrainingDataset(double[][] trainingDataset) {
		this.trainingDataset = trainingDataset;
	}

	public double[][] getTestingDataset() {
		return testingDataset;
	}

	public void setTestingDataset(double[][] testingDataset) {
		this.testingDataset = testingDataset;
	}

	public double[][][] getWeights() {
		return weights;
	}

	public void setWeights(double[][][] weights) {
		this.weights = weights;
	}

	public double[][] getNodes() {
		return nodes;
	}

	public void setNodes(double[][] nodes) {
		this.nodes = nodes;
	}

	public int getDatasetDimensionY() {
		return datasetDimensionY;
	}

	public void setDatasetDimensionY(int datasetDimensionY) {
		this.datasetDimensionY = datasetDimensionY;
	}

	public int getDatasetDimensionX() {
		return datasetDimensionX;
	}

	public void setDatasetDimensionX(int datasetDimensionX) {
		this.datasetDimensionX = datasetDimensionX;
	}

	public int getCurrentDatasetRow() {
		return currentDatasetRow;
	}

	public void setCurrentDatasetRow(int currentDatasetRow) {
		this.currentDatasetRow = currentDatasetRow;
	}

	public double[][] getTarget() {
		return target;
	}

	public void setTarget(double[][] target) {
		this.target = target;
	}

	public double[][] getErrors() {
		return errors;
	}

	public void setErrors(double[][] errors) {
		this.errors = errors;
	}

	public double[][] getWeightedSums() {
		return weightedSums;
	}

	public void setWeightedSums(double[][] weightedSums) {
		this.weightedSums = weightedSums;
	}

	public double getError() {
		return rowError;
	}

	public void setError(double error) {
		this.rowError = error;
	}

	public int[] getNumberOfLayerNodes() {
		return numberOfLayerNodes;
	}

	public void setNumberOfLayerNodes(int[] numberOfLayerNodes) {
		this.numberOfLayerNodes = numberOfLayerNodes;
	}

	public boolean isSupervised() {
		return supervised;
	}

	public void setSupervised(boolean supervised) {
		this.supervised = supervised;
	}

	public double getRowError() {
		return rowError;
	}

	public void setRowError(double rowError) {
		this.rowError = rowError;
	}

	public double getEpochError() {
		return epochError;
	}

	public void setEpochError(double epochError) {
		this.epochError = epochError;
	}
	
}
