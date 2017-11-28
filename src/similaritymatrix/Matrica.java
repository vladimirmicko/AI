package similaritymatrix;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matrica {
	private Integer[][] matrix;
	private Double[][] similarityMatrix;
	private Integer matrixRows;
	private Integer matrixCols;
	private List<MatrixCell> matrixList;
	private List<Integer> nodes;
	private List<MinimumSpanningTreeEdge> minimumSpanningTree;


	public Matrica() {
		matrix = new Integer[100][100];
		similarityMatrix = new Double[100][100];
		matrixRows = 27;
		matrixCols = 14;
		matrixList = new ArrayList();
		nodes = new ArrayList();
		minimumSpanningTree=new ArrayList();
	}


	public void parse() {
		String csvFile = "C:/MyDocuments/EclipseNeon/ArtificialIntelligence/Resources/GangDB.CSV";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		matrixList = new ArrayList();

		try {
			br = new BufferedReader(new FileReader(csvFile));
			int x = 0;
			int y = 0;
			while ((line = br.readLine()) != null) {
				String[] fileLine = line.split(cvsSplitBy);
				for (String item : fileLine) {
					if (item != null && !item.equals("")) {
						matrix[y][x] = Integer.valueOf(item);
						matrixList.add(new MatrixCell(y,x,Double.valueOf(item)));
						x++;
					}
				}
				y++;
				x=0;
			}
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
	}
	
	
	public void printMatrix(){
		for(int y=0; y<matrixRows; y++){
			System.out.print(""+y+": ");
			for(int x=0; x<matrixCols; x++){
				System.out.print(matrix[y][x]);
			}
			System.out.println();
		}
	}
	
	public double globalSimilarityMatrix(){
		matrixList = new ArrayList();
		for(int x1=0; x1<matrixCols; x1++){
			for(int x2=x1+1; x2<matrixCols; x2++){
				//similarityMatrix[x1][x2]=simIJ(x1,x2);
				similarityMatrix[x1][x2]=normalizedSimIJ(x1,x2);
				matrixList.add(new MatrixCell(x1,x2,Double.valueOf(similarityMatrix[x1][x2])));
			}
		}
		
		
		double gsm=0;
		for(int y=0; y<matrixCols-1; y++){
			for(int x=y+1; x<matrixCols; x++){
				gsm=gsm+similarityMatrix[y][x];
			}
		}
		System.out.println("Global similarity matrix: " + gsm);
		return gsm;
	}
	
	public double simIJ(int i, int j){
		double simIj=0;
		for(int y=0; y<matrixRows; y++){
			simIj=simIj+matrix[y][i]*matrix[y][j];
		}
		return simIj;
	}
	
	
	
	public double normalizedSimIJ(int i, int j){
		double simIj=0;
		double maxValue=0;
		for(int y=0; y<matrixRows; y++){
			maxValue = Math.max(matrix[y][i], matrix[y][j])==0 ? 0.00001 : Math.max(matrix[y][i], matrix[y][j]);
			maxValue = maxValue*maxValue;
			simIj=simIj+(matrix[y][i]*matrix[y][j]/maxValue);
		}
		return simIj;
	}
	
	
	
	
	public void printSimilarityMatrix(){
		for(int y=0; y<matrixCols; y++){
			System.out.print(""+y+": ");
			for(int x=0; x<matrixCols; x++){
				System.out.print(" "+similarityMatrix[y][x]);
			}
			System.out.println();
		}
	}
	
	
	public void printMatrixList(){
		Integer y=0;
		for(MatrixCell mc : matrixList){
			System.out.println(""+y+": "+mc.getRow()+", "+mc.getCol()+", "+mc.getValue());
			y++;
		}
	}
	
	
	public void orderCells(){
		Collections.sort(matrixList);
	}
	
	
	public void createMst(){
		nodes=new ArrayList();
		minimumSpanningTree = new ArrayList();
		Collections.sort(matrixList);
		
		for(MatrixCell mc : matrixList){
			if(!(isNodeInTree(mc.getCol()) || isNodeInTree(mc.getRow()) )){
				minimumSpanningTree.add(new MinimumSpanningTreeEdge(mc.getCol(), mc.getRow(), mc.getValue()));
				nodes.add(mc.getCol());
				nodes.add(mc.getRow());
			}
		}
		
	}
	
	
	public boolean isNodeInTree(Integer nodeNumber){
		boolean isIn = false;
		for(Integer i : nodes){
			if(nodeNumber == i) isIn=true;
		}
		return isIn;
	}
	
	public void printMst(){
		int y=0;
		System.out.println("----------------------------------------- Minimum Spanning Tree");
		for(MinimumSpanningTreeEdge mstEdge : minimumSpanningTree){
			System.out.println(""+y+". "+mstEdge.getNodeStart()+", "+mstEdge.getNodeEnd()+", "+mstEdge.getDistance());
			y++;
		}
	}
	
}
