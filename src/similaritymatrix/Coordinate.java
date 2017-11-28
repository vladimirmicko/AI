package similaritymatrix;

public class Coordinate implements Comparable<Object>{
	
	private Integer[] coordinate;
	private Double distance;

	public Coordinate() {
		
	}

	public Coordinate(Integer[] coordinate) {
		this.coordinate = coordinate;
	}

	public Coordinate(Integer[] coordinate, Double distance) {
		this.coordinate = coordinate;
		this.distance = distance;
	}

	public Integer[] getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Integer[] coordinate) {
		this.coordinate = coordinate;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	@Override
	public int compareTo(Object object) {
		Coordinate coordinate = (Coordinate) object;
		return Double.compare(this.distance, coordinate.distance);
	}

}
