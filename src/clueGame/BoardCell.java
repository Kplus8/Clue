package clueGame;

/**
 * 
 * @author Jim DeBlock
 * @author Graham Kitchenka
 *
 */

public class BoardCell {

	private int row, column;

	public BoardCell(int row, int column) {

		this.row = row;
		this.column = column;

	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "(" + row + ", " + column + ")";
	}

	@Override
	public boolean equals(Object o) {
		BoardCell c = (BoardCell) o;
		return c.getRow() == this.getRow() && this.getColumn() == c.getColumn();
	}

	public Object getDoorDirection() {

		return null;
	}

	public boolean isDoorway() {

		return false;
	}

	public Object getInitial() {

		return null;
	}

	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
}
