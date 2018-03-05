package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Jim DeBlock
 * @author Graham Kitchenka
 *
 */

public class Board {

	public static final int MAX_BOARD_SIZE = 50;
	
	private int numRows;
	private int numCols;
	private int numColumns;
	private static BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private Set<BoardCell> visited;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void setConfigFiles(String string, String string2) {

		
	}

	public void initialize() {

		
	}

	public Map<Character, String> getLegend() {

		return new HashMap<Character, String>();
	}

	public int getNumRows() {
		return numRows;
	}
	
	public int getNumCols() {
		return numCols;
	}
	
	public void calcAdjacencies() {

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {

				int l = board[i].length;
				HashSet<BoardCell> temp = new HashSet<BoardCell>();
				if (i - 1 >= 0) {
					temp.add(getCellAt(i - 1, j));
				}
				if (j - 1 >= 0) {
					temp.add(getCellAt(i, j - 1));
				}
				if (i + 1 <= l) {
					temp.add(getCellAt(i + 1, j));
				}
				if (j + 1 <= l) {
					temp.add(getCellAt(i, j + 1));
				}

				adjMatrix.put(getCellAt(i, j), temp);
			}
		}

	}
	
	public BoardCell getCellAt(int x, int y) {

		Set<BoardCell> keys = adjMatrix.keySet();
		for (BoardCell cell : keys) {
			if (cell.equals(new BoardCell(x,y))) {
				return cell;
			}
		}
		return new BoardCell(x,y);

	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {

		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);

	}
	
	public void findAllTargets(BoardCell curCell, int numSteps) {

		for (BoardCell cell_t : adjMatrix.get(curCell)) {
			BoardCell cell = getCellAt(cell_t.getRow(), cell_t.getColumn());
			if (!visited.contains(cell)) {

				visited.add(cell);
				if (numSteps == 1) {
					targets.add(cell);
				} else {
					findAllTargets(cell, numSteps - 1);
				}
				visited.remove(cell);

			}
		}

	}
	
}
