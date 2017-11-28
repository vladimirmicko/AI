package similaritymatrix;

public class MatrixCell implements Comparable<Object> {

	private Integer row;
	private Integer col;
	private Double value;

	
	public MatrixCell(Integer row, Integer col, Double value) {
		super();
		this.row = row;
		this.col = col;
		this.value = value;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getCol() {
		return col;
	}

	public void setCol(Integer col) {
		this.col = col;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public int compareTo(Object object) {
		MatrixCell cell = (MatrixCell) object;
		return Double.compare(this.value, cell.value);
	}
}
