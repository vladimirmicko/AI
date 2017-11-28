package similaritymatrix;

public class MinimumSpanningTreeEdge {

	private Integer nodeStart;
	private Integer nodeEnd;
	private Double distance;

	
	public MinimumSpanningTreeEdge() {
	}
	

	public MinimumSpanningTreeEdge(Integer nodeStart, Integer nodeEnd, Double distance) {
		this.nodeStart = nodeStart;
		this.nodeEnd = nodeEnd;
		this.distance = distance;
	}



	public Integer getNodeStart() {
		return nodeStart;
	}


	public void setNodeStart(Integer nodeStart) {
		this.nodeStart = nodeStart;
	}


	public Integer getNodeEnd() {
		return nodeEnd;
	}


	public void setNodeEnd(Integer nodeEnd) {
		this.nodeEnd = nodeEnd;
	}


	public Double getDistance() {
		return distance;
	}


	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	
	


}
