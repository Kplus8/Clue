package experiment;
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

public class IntBoard {

	private Map<BoardCell, HashSet<BoardCell>> adjacencies;
	private BoardCell[][] grid;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;

	public IntBoard() {
		super();
		adjacencies = new HashMap<BoardCell, HashSet<BoardCell>>();
		grid = new BoardCell[4][4];
		calcAdjacencies();
	}

	/**
	 * Populates the Map adjacencies with adjacent points.
	 * Key corresponds to a BoardCell piece,
	 * Inside is a set of all adjacent BoardCell pieces.
	 */
	
	public void calcAdjacencies() {

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {

				int l = grid[i].length;
				HashSet<BoardCell> temp = new HashSet<BoardCell>();
				if (i - 1 >= 0) {
					temp.add(getCell(i - 1, j));
				}
				if (j - 1 >= 0) {
					temp.add(getCell(i, j - 1));
				}
				if (i + 1 <= l) {
					temp.add(getCell(i + 1, j));
				}
				if (j + 1 <= l) {
					temp.add(getCell(i, j + 1));
				}

				adjacencies.put(getCell(i, j), temp);
			}
		}

	}

	/**
	 * Returns the list of Adjacent cells as a set for a given cell.
	 * 
	 * To avoid instance errors, creates a new set from adjacencies consisting of 
	 * already existing BoardCell (uses getCell)
	 * 
	 * @param cell
	 * @return
	 */
	
	public Set<BoardCell> getAdjList(BoardCell cell) {

		Set<BoardCell> temp = new HashSet<BoardCell>();
		for (BoardCell curCell : adjacencies.get(cell)) {
			temp.add(getCell(curCell.getRow(), curCell.getColumn()));
		}
		return temp;

	}

	/**
	 * Calculates the possible targets given the starting cell and the "die roll"
	 * @param startCell
	 * @param pathLength
	 */
	
	public void calcTargets(BoardCell startCell, int pathLength) {

		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);

	}

	/**
	 * Recursive function called by calcTargets.
	 * Finds targets for the given cell.
	 * @param curCell
	 * @param numSteps
	 */
	
	public void findAllTargets(BoardCell curCell, int numSteps) {

		for (BoardCell cell_t : adjacencies.get(curCell)) {
			BoardCell cell = getCell(cell_t.getRow(), cell_t.getColumn());
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

	/**
	 * Returns the possible targets as a set.
	 * NOTE: Call calcTargets first, as this doesn't actually calculate them, it just returns the set calculated by calcTargets.
	 * @return
	 */
	
	public Set<BoardCell> getTargets() {

		return targets;

	}

	/**
	 * Returns a BoardCell given the row and column numbers.
	 * 
	 * To avoid instance errors, this method loops through adjacencies and tries to return
	 * a BoardCell from that map. If said BoardCell does not yet exist, returns a new BoardCell.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	
	public BoardCell getCell(int x, int y) {

		Set<BoardCell> keys = adjacencies.keySet();
		for (BoardCell cell : keys) {
			if (cell.equals(new BoardCell(x,y))) {
				return cell;
			}
		}
		return new BoardCell(x,y);

	}
}
