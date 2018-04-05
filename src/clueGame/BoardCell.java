package clueGame;

/**
 * Represents a single cell on the board
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */
public class BoardCell {
	private int row, column;
	private char initial;
	private DoorDirection dir = DoorDirection.NONE;

	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public BoardCell(int row, int column, char initial, DoorDirection dir) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.dir = dir;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "(" + row + ", " + column + "): " + initial + ", " + dir;
	}

	@Override
	public boolean equals(Object o) {
		BoardCell c = (BoardCell) o;
		return c.getRow() == this.getRow() && this.getColumn() == c.getColumn();
	}

	public DoorDirection getDoorDirection() {
		return dir;
	}

	public boolean isDoorway() {
		if (dir ==DoorDirection.NONE) {
			return false;
		} else {
			return true;
		}
	}

	public char getInitial() {
		return initial;
	}

	public boolean isWalkway() {
		if (initial == 'W') {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRoom() {
		return !isWalkway();
	}
}
