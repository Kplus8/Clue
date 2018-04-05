package tests;

/**
 * 
 * @author Jim DeBlock
 * @author Graham Kitchenka
 *
 */

import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class FileTests {

	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 22;
	public static final int NUM_DOORS = 11;
	private static Board board;

	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("data" + File.separatorChar + "Map.csv", "data" + File.separatorChar + "ClueRooms.txt");
		board.initialize();
	}

	/**
	 * Tests to make sure that the legend file is loaded correctly.
	 */

	@Test
	public void testLegend() {

		Map<Character, String> legend = board.getLegend();
		assertEquals(legend.size(), LEGEND_SIZE);

		assertEquals(legend.get('G'), "Game room");
		assertEquals(legend.get('L'), "Living room");
		assertEquals(legend.get('O'), "Office");
		assertEquals(legend.get('B'), "Bedroom");
		assertEquals(legend.get('N'), "Den");
		assertEquals(legend.get('D'), "Dining room");
		assertEquals(legend.get('K'), "Kitchen");
		assertEquals(legend.get('H'), "Greenhouse");
		assertEquals(legend.get('T'), "Theater");
		assertEquals(legend.get('X'), "Closet");
		assertEquals(legend.get('W'), "Walkway");

	}

	/**
	 * Checks for correct row dimension.
	 */

	@Test
	public void testRows() {

		assertEquals(NUM_ROWS, board.getNumRows());

	}

	/**
	 * Checks for correct column dimension.
	 */

	@Test
	public void testColumns() {

		assertEquals(NUM_COLUMNS, board.getNumCols());

	}

	/**
	 * Tests for number of doors, as well as making sure that there is at lest
	 * one of each direction door.
	 */

	@Test
	public void testDoorways() {

		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumCols(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway()) {
					numDoors++;
				}
			}
		Assert.assertEquals(NUM_DOORS, numDoors);

		BoardCell room = board.getCellAt(12, 6);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(5, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(9, 17);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(19, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(3, 3);
		assertFalse(room.isDoorway());
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(7, 7);
		assertFalse(cell.isDoorway());

	}

	/**
	 * Tests to determine if rooms are loaded correctly.
	 */

	@Test
	public void testRooms() {

		// Tests some rooms
		assertEquals('G', board.getCellAt(0, 0).getInitial());
		assertEquals('L', board.getCellAt(10, 2).getInitial());
		assertEquals('B', board.getCellAt(23, 13).getInitial());
		assertEquals('N', board.getCellAt(23, 21).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(7, 7).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(12, 12).getInitial());

	}

}
