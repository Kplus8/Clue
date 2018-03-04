package tests;

/**
 * 
 * @author Jim DeBlock
 * @author Graham Kitchenka
 *
 */

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class FileTests {

	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	private static Board board;

	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("data\\CTest_ClueLayout.csv",
				"data\\CTest_ClueLegend.txt");
		board.initialize();
	}

	@Test
	public void testLegend() {

	}

	@Test
	public void testRows() {

	}

	@Test
	public void testColumns() {

	}

	@Test
	public void testDoorways() {

	}

}
