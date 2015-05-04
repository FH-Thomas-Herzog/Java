package at.fh.ooe.swe4.puzzle.model;

/**
 * Holds the position information of an tile on a board.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public class Position {

	public final int rowIdx;
	public final int colIdx;

	public Position(int row, int column) {
		super();
		this.rowIdx = row;
		this.colIdx = column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + colIdx;
		result = prime * result + rowIdx;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (colIdx != other.colIdx)
			return false;
		if (rowIdx != other.rowIdx)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder("rowIdx: ").append(rowIdx)
											.append(" | ")
											.append("colIdx: ")
											.append(colIdx)
											.toString();
	}
}
