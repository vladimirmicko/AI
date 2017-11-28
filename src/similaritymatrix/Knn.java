package similaritymatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knn {

	private Integer[][] trainingMatrix;
	private Integer numberOfClasses;
	private Integer numberOfRows;
	private Integer numberOfColumns;
	List<Coordinate> orderedCoordinateList;
	
	
	public Knn() {
	}
	
	public Knn(Integer[][] trainingMatrix, Integer numberOfRows, Integer numberOfColumns) {
		this.trainingMatrix = trainingMatrix;
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
		this.orderedCoordinateList = new ArrayList<Coordinate>();
		
		numberOfClasses = 0;
		
		for(int xxx=0; xxx<numberOfRows; xxx++){
			if(trainingMatrix[xxx][numberOfColumns] > numberOfClasses) numberOfClasses=trainingMatrix[xxx][numberOfColumns];
		}
		
	}
	
	
	
	public Integer classify(Integer[] coordinate, int k){
		orderedCoordinateList = new ArrayList<Coordinate>();
		for(int xxx=0; xxx<numberOfRows; xxx++){
			Coordinate listCoordinate = new Coordinate(trainingMatrix[xxx], distance(coordinate, trainingMatrix[xxx]));
			orderedCoordinateList.add(listCoordinate);
		}
		
		Collections.sort(orderedCoordinateList);
		//Collections.reverse(orderedCoordinateList);
		
		return orderedCoordinateList.get(0).getCoordinate()[numberOfColumns];
	}
	
	
	public void printOrderedCoordinateList(){
		Integer y=0;
		for(Coordinate coordinate : orderedCoordinateList){
			System.out.println(""+y+": "+coordinate.getCoordinate()[0]+", "+coordinate.getCoordinate()[1]+", class:"+coordinate.getCoordinate()[2]+", distance:"+coordinate.getDistance());
			y++;
		}
	}
	
	
	public double distance(Integer[] coordinate1, Integer[] coordinate2){
		double sum=0;
		double distance=0;
		for(int x=0; x<numberOfColumns; x++){
			sum=sum+Math.pow(coordinate1[x]-coordinate2[x],2);
		}
		distance=Math.sqrt(sum);
		
		return distance;
	}

	public Integer[][] getTrainingMatrix() {
		return trainingMatrix;
	}

	public void setTrainingMatrix(Integer[][] trainingMatrix) {
		this.trainingMatrix = trainingMatrix;
	}

	public Integer getNumberOfClasses() {
		return numberOfClasses;
	}

	public void setNumberOfClasses(Integer numberOfClasses) {
		this.numberOfClasses = numberOfClasses;
	}
	
	

	
	
}
