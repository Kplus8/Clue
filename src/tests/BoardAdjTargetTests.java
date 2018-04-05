package tests;

import java.util.Set;

//Doing a static import allows to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

/**
 * Tests that adjacencies and targets are calculated correctly.
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */
public class BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance(); // Board is singleton, get the only instance
		board.setConfigFiles("data\\Map.csv", "data\\ClueRooms.txt"); // set the file names to use my config files
		board.initialize(); // Initialize will load BOTH config files
	}

	/**
	 * Ensure that player does not move around within room
	 * These cells are ORANGE on the planning spreadsheet
	 */
	@Test
	public void testAdjacenciesInsideRooms() {
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		
		// Test one that has walkway underneath
		testList = board.getAdjList(5, 0);
		assertEquals(0, testList.size());
	}

	/**
	 * Ensure that the adjacency list from a doorway is only the walkway.
	 * NOTE: This test could be merged with door direction test.
	 * These tests are PURPLE on the planning spreadsheet
	 */
	@Test
	public void testAdjacencyRoomExit() {
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(12, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(12, 7)));
		
		//TEST DOORWAY DOWN
		testList = board.getAdjList(15, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(16, 3)));
		
		//TEST DOORWAY UP
		testList = board.getAdjList(19, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 5)));
	}
	
	/**
	 * Test adjacency at entrance to rooms
	 * These tests are GREEN in planning spreadsheet
	 */
	@Test
	public void testAdjacencyDoorways() {
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(18, 5);
		assertTrue(testList.contains(board.getCellAt(17, 5)));
		assertTrue(testList.contains(board.getCellAt(18, 6)));
		assertTrue(testList.contains(board.getCellAt(18, 4)));
		assertTrue(testList.contains(board.getCellAt(19, 5)));
		assertEquals(4, testList.size());
		
		// Test beside a door direction DOWN
		testList = board.getAdjList(6, 3);
		assertTrue(testList.contains(board.getCellAt(6, 2)));
		assertTrue(testList.contains(board.getCellAt(6, 4)));
		assertTrue(testList.contains(board.getCellAt(7, 3)));
		assertTrue(testList.contains(board.getCellAt(5, 3)));
		assertEquals(4, testList.size());
		
		// Test beside a door direction LEFT
		testList = board.getAdjList(9, 16);
		assertTrue(testList.contains(board.getCellAt(9, 15)));
		assertTrue(testList.contains(board.getCellAt(9, 17)));
		assertTrue(testList.contains(board.getCellAt(8, 16)));
		assertTrue(testList.contains(board.getCellAt(10, 16)));
		assertEquals(4, testList.size());
		
		// Test beside a door direction UP
		testList = board.getAdjList(18, 5);
		assertTrue(testList.contains(board.getCellAt(18, 4)));
		assertTrue(testList.contains(board.getCellAt(18, 6)));
		assertTrue(testList.contains(board.getCellAt(17, 5)));
		assertTrue(testList.contains(board.getCellAt(19, 5)));
		assertEquals(4, testList.size());
	}
	
	/**
	 * Test a variety of walkway scenarios
	 * These tests are LIGHT BLUE on the planning spreadsheet
	 */
	@Test
	public void testAdjacencyWalkways()	{
		// Test on top edge of board, three walkway pieces
		Set<BoardCell> testList = board.getAdjList(0, 14);
		assertTrue(testList.contains(board.getCellAt(0, 15)));
		assertTrue(testList.contains(board.getCellAt(0, 13)));
		assertTrue(testList.contains(board.getCellAt(1, 14)));
		assertEquals(3, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(17, 0);
		assertTrue(testList.contains(board.getCellAt(17, 1)));
		assertTrue(testList.contains(board.getCellAt(16, 0)));
		assertTrue(testList.contains(board.getCellAt(18, 0)));
		assertEquals(3, testList.size());

		// Test on bottom edge, two walkway pieces
		testList = board.getAdjList(23, 7);
		assertTrue(testList.contains(board.getCellAt(23, 6)));
		assertTrue(testList.contains(board.getCellAt(22, 7)));
		assertEquals(2, testList.size());
		
		// Test on walkway next to  door that is not in the needed direction to enter
		testList = board.getAdjList(21, 15);
		assertFalse(testList.contains(board.getCellAt(21, 16)));
		assertTrue(testList.contains(board.getCellAt(20, 15)));
		assertTrue(testList.contains(board.getCellAt(21, 14)));
		assertTrue(testList.contains(board.getCellAt(22, 15)));
		assertEquals(3, testList.size());
	}
	
	/**
	 * Tests of just walkways, 1 step, includes on edge of board and beside room
	 * Have already tested adjacency lists on all four edges, will only test two edges here
	 * These are LIGHT PURPLE on the planning spreadsheet
	 */
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(5, 15, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 16)));
		assertTrue(targets.contains(board.getCellAt(5, 14)));	
		assertTrue(targets.contains(board.getCellAt(6, 15)));
		assertTrue(targets.contains(board.getCellAt(4, 15)));	
		
		board.calcTargets(13, 21, 1);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 20)));			
	}
	
	/**
	 * Tests of just walkways, 2 steps
	 * These are LIGHT PURPLE on the planning spreadsheet
	 */
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(5, 15, 2);
		Set<BoardCell> targets= board.getTargets();
		assertTrue(targets.contains(board.getCellAt(4, 14)));
		assertTrue(targets.contains(board.getCellAt(6, 14)));
		assertTrue(targets.contains(board.getCellAt(3, 15)));
		assertTrue(targets.contains(board.getCellAt(7, 15)));
		assertTrue(targets.contains(board.getCellAt(5, 17)));
		assertTrue(targets.contains(board.getCellAt(6, 16)));
		
		board.calcTargets(13, 21, 2);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 19)));		
	}
	
	/**
	 * Tests of just walkways, 4 steps
	 * These are LIGHT PURPLE on the planning spreadsheet
	 */
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(13, 21, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 17)));	
	}
	
	/**
	 * Tests of just walkways plus one door, 6 steps
	 * These are LIGHT PURPLE on the planning spreadsheet
	 */
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(13, 21, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 15)));
		assertTrue(targets.contains(board.getCellAt(14, 16)));	
		assertTrue(targets.contains(board.getCellAt(12, 16)));
	}	
	
	/**
	 * Test getting into a room
	 * These are LIGHT PURPLE on the planning spreadsheet
	 */
	@Test 
	public void testTargetsIntoRoom() {
		// One room is exactly 2 away
		board.calcTargets(7, 9, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 8)));
		assertTrue(targets.contains(board.getCellAt(7, 7)));
		assertTrue(targets.contains(board.getCellAt(8, 8)));
		assertTrue(targets.contains(board.getCellAt(8, 10)));
		assertTrue(targets.contains(board.getCellAt(7, 11)));
	}
	
	/**
	 * Test getting into room, doesn't require all steps
	 * These are LIGHT PURPLE on the planning spreadsheet
	 */
	@Test
	public void testTargetsIntoRoomShortcut() {
		board.calcTargets(7, 9, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 8)));
		assertTrue(targets.contains(board.getCellAt(7, 6)));
		assertTrue(targets.contains(board.getCellAt(8, 7)));
		assertTrue(targets.contains(board.getCellAt(9, 8)));
		assertTrue(targets.contains(board.getCellAt(7, 8)));
		assertTrue(targets.contains(board.getCellAt(7, 10)));
		assertTrue(targets.contains(board.getCellAt(8, 9)));
		assertTrue(targets.contains(board.getCellAt(7, 12)));
		assertTrue(targets.contains(board.getCellAt(8, 11)));
	}

	/**
	 * Test getting out of a room
	 * These are LIGHT PURPLE on the planning spreadsheet
	 */
	@Test
	public void testRoomExit() {
		// Take one step, essentially just the adj list
		board.calcTargets(6, 8, 1);
		Set<BoardCell> targets= board.getTargets();
		
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 8)));
		
		// Take two steps
		board.calcTargets(6, 8, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(8, 8)));
		assertTrue(targets.contains(board.getCellAt(7, 9)));
		assertTrue(targets.contains(board.getCellAt(7, 7)));
	}
}
