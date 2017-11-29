package priorprobability;

import backpropagation.Utility;

public class PriorProbability {

	private String datasetFilename;
	private int datasetDimensionY;
	private int datasetDimensionX;

	private double[][] dataset;
	private double[][] wij;
	private double[] bi;
	private double bias;

	private double[] externalInput;
	private double[] neti;
	private double[] di;
	private double[] xi;
	private double gg;

	public PriorProbability() {
	}

	public PriorProbability(String datasetFilename, int datasetDimensionY, int datasetDimensionX) {
		this.datasetFilename = datasetFilename;
		this.datasetDimensionY = datasetDimensionY;
		this.datasetDimensionX = datasetDimensionX;

		dataset = Utility.loadDatasetFromFile(datasetFilename, datasetDimensionY, datasetDimensionX);
		wij = new double[dataset[0].length][dataset[0].length];
		bi = new double[dataset[0].length];
		bias = 0;
		externalInput = new double[] { 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 };
	}

	public void calculateWij() {
		wij = new double[dataset[0].length][dataset[0].length];
		double v1, v2, v3, v4;
		double b1, b2;
		double[][] x = dataset;
		for (int i = 0; i < dataset[0].length; i++) {
			b1 = 0;
			b2 = 0;
			for (int j = i; j < dataset[0].length; j++) {
				v1 = 0;
				v2 = 0;
				v3 = 0;
				v4 = 0;
				for (int k = 0; k < dataset.length; k++) {
					v1 = v1 + (x[k][i] * (1 - x[k][j]));
					v2 = v2 + ((1 - x[k][i]) * x[k][j]);
					v3 = v3 + (x[k][i] * x[k][j]);
					v4 = v4 + ((1 - x[k][i]) * (1 - x[k][j]));
					b1 = b1 + (1 - x[k][i]);
					b2 = b2 + x[k][i];
				}
				double numerator = v1 * v2;
				double denominator = v3 * v4;
//				if (Math.abs(numerator) < 0.0001 && numerator>0)
//					numerator = 0.0001;
//				if (Math.abs(numerator) < 0.0001 && numerator<0)
//					numerator = -0.0001;
//				if (Math.abs(denominator) < 0.0001 && denominator>0)
//					denominator = 0.0001;
//				if (Math.abs(denominator) < 0.0001 && denominator<0)
//					denominator = -0.0001;

				wij[i][j] = -Math.log((numerator) / (denominator));
			}
//			if (Math.abs(b1) < 0.0001 && b1>0)
//				b1 = 0.0001;
//			if (Math.abs(b1) < 0.0001 && b1<0)
//				b1 = -0.0001;
//			if (Math.abs(b2) < 0.0001 && b2>0)
//				b2 = 0.0001;
//			if (Math.abs(b2) < 0.0001 && b2<0)
//				b2 = -0.0001;
			bi[i] = -Math.log((b1) / (b2));
		}
		bias = Utility.sumOfArray(bi);
	}

	public void calculateXi(int end) {
		double[] xip = new double[dataset[0].length];
		xi = new double[dataset[0].length];
		neti = new double[dataset[0].length];
		di = new double[dataset[0].length];
		xi = Utility.populateArrayWithZeros(xi);
		for (int n = 0; n < end; n++) {
			
			for (int i = 0; i < dataset[0].length; i++) {
				neti[i] = 0;
				for (int j = 0; j < dataset[0].length; j++) {
					neti[i] = neti[i] + xi[j] * wij[i][j];
				}
				neti[i] = neti[i] + externalInput[i] + bi[i];
			}
			
			for (int i = 0; i < dataset[0].length; i++) {
				if (neti[i] > 0)
					di[i] = neti[i] * (1 - xi[i]);
				else
					di[i] = neti[i] * xi[i];
				
				System.arraycopy(xi, 0, xip, 0, dataset[0].length);
				
				if (externalInput[i] == 0) {
					xi[i] = xi[i] + di[i];
				} else {
					xi[i] = externalInput[i];
				}
			}
			
			gg = 0;
			for (int i = 0; i < dataset[0].length; i++) {
				for (int j = 0; j < dataset[0].length; j++) {
					gg = gg + xip[i] * xip[j] * wij[i][j];
				}
			}
			gg = gg + bias;
			gg = gg - Utility.sumOfArray(externalInput);
			System.out.println("" + n + ". G= " + gg);
		}
	}

	public String getDatasetFilename() {
		return datasetFilename;
	}

	public void setDatasetFilename(String datasetFilename) {
		this.datasetFilename = datasetFilename;
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

	public double[][] getDataset() {
		return dataset;
	}

	public void setDataset(double[][] dataset) {
		this.dataset = dataset;
	}

	public double[][] getWij() {
		return wij;
	}

	public void setWij(double[][] wij) {
		this.wij = wij;
	}

	public double[] getExternalInput() {
		return externalInput;
	}

	public void setExternalInput(double[] externalInput) {
		this.externalInput = externalInput;
	}

	public double[] getNeti() {
		return neti;
	}

	public void setNeti(double[] neti) {
		this.neti = neti;
	}

	public double[] getDi() {
		return di;
	}

	public void setDi(double[] di) {
		this.di = di;
	}

	public double[] getXi() {
		return xi;
	}

	public void setXi(double[] xi) {
		this.xi = xi;
	}

	public double getGg() {
		return gg;
	}

	public void setGg(double gg) {
		this.gg = gg;
	}

	public double[] getBi() {
		return bi;
	}

	public void setBi(double[] bi) {
		this.bi = bi;
	}

}
