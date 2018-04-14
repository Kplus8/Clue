package clueGame;

import java.awt.*;
import java.io.FileNotFoundException;

/**
 * Represents a single cell on the board
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */
public class BoardCell {
	private int column, row;
	private char initial;
	private boolean dispName = false;
	private DoorDirection dir = DoorDirection.NONE;

	public BoardCell(int row, int column) {
		this.column = column;
		this.row = row;
	}

	public BoardCell(int row, int column, char initial, DoorDirection dir) {
		this.column = column;
		this.row = row;
		this.initial = initial;
		this.dir = dir;
	}

	public BoardCell(int row, int column, char initial, DoorDirection dir,
			boolean dispName) {
		this.column = column;
		this.row = row;
		this.initial = initial;
		this.dir = dir;
		this.dispName = dispName;
	}

	public int getRow() {
		return row;
	}

	/**
	 * Draws this cell
	 */

	public void draw(Graphics g) {

		final int MULT = 25;

		if (isWalkway()) {

			g.setColor(Color.YELLOW);
			g.fillRect(column * MULT, row * MULT, MULT, MULT);
			g.setColor(Color.BLACK);
			g.drawRect(column * MULT, row * MULT, MULT, MULT);

		} else if (isDoorway()) {

			g.setColor(Color.GRAY);
			g.fillRect(column * MULT, row * MULT, MULT, MULT);
			g.setColor(Color.BLUE);
			switch (dir) {
			case UP:
				g.fillRect(column * MULT, row * MULT, MULT, MULT - 20);
				break;
			case DOWN:
				g.fillRect(column * MULT, row * MULT + 20, MULT, MULT - 20);
				break;
			case LEFT:
				g.fillRect(column * MULT, row * MULT, MULT - 20, MULT);
				break;
			case RIGHT:
				g.fillRect(column * MULT + 20, row * MULT, MULT - 20, MULT);
				break;
			}

		} else { // is a room

			g.setColor(Color.GRAY);
			g.fillRect(column * MULT, row * MULT, MULT, MULT);

			if (dispName) {
				g.setColor(Color.BLUE);
				try {
					g.drawString(Board.getInstance().getRoom(initial).toUpperCase(), column
							* MULT, row * MULT);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		}

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
		if (dir == DoorDirection.NONE) {
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
