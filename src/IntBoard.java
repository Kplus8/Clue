import java.util.Map;
import java.util.Set;
import experiment.BoardCell;

/**
 * 
 * @author Jim DeBlock
 * @author Graham Kitchenka
 *
 */

public class IntBoard {

	private Map<BoardCell, Set<BoardCell>> adjacenices;

	public IntBoard() {
		super();
		calcAdjacencies();
	}

	public void calcAdjacencies() {

	}

	public Set<BoardCell> getAdjList(BoardCell cell) {

		return null;
		
	}

	public void calcTargets(BoardCell startCell, int pathLength) {

	}

	public Set<BoardCell> getTargets() {

		return null;
		
	}
	
	public BoardCell getCell(int x, int y) {
		
		return new BoardCell(0, 0);
		
	}

}
