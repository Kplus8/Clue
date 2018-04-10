package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Collections;
import java.util.Stack;

/**
 * Represents the state of the game board as well as other game components - e.g. the deck and players
 *
 * @author Jim DeBlock
 * @author Graham Kitchenka
 * @author Brandon Verkamp
 */
public class Board {
	public static final int MAX_BOARD_SIZE = 50;
	public static final int NUM_PLAYERS = 6;

	private int numRows;
	private int numCols;
	private static BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, HashSet<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private Stack<Card> deck;
	private Card[] chosenCards;
	private Player[] players;
	private Card[] suggestedCards;

	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {}

	/**
	 * Obtain the Board instance
	 *
	 * @return The Board singleton
	 */
	public static Board getInstance() {
		return theInstance;
	}

	/**
	 * Sets the legend and layout files
	 * 
	 * @param layout
	 * @param legend
	 */
	public void setConfigFiles(String layout, String legend) {
		boardConfigFile = layout;
		roomConfigFile = legend;

		playerConfigFile = "data" + File.separatorChar + "people.txt";
		weaponConfigFile = "data" + File.separatorChar + "weapons.txt";
	}

	/**
	 * Calculates the number of cols and rows, reads in the legend file, and
	 * reads in the map file.
	 */
	public void initialize() {
		// get numRows and numCols
		try {
			readRowsCols();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		legend = new HashMap<>(); // "diamond" syntax is convenient if the key/value type of the collection is changed
		board = new BoardCell[numRows][numCols];

		try {
			loadRoomConfig(); // read in legend file
			loadBoardConfig(); // reads in board file
		} catch (FileNotFoundException | BadConfigFormatException e) { //FIXME technically bad form (catch different exceptions separately)
			e.printStackTrace();
		}

		adjMatrix = new HashMap<>();
		calcAdjacencies();
	}

	/**
	 * Reads in legend file, populating Legend Map
	 * 
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		Scanner sc = new Scanner(new File(roomConfigFile));

		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] parts = line.split(", ");
			if(!(parts[2].equals("Card") || parts[2].equals("Other"))) {
				sc.close();
				throw new BadConfigFormatException("Unrecognized type in Legend file: " +
						roomConfigFile + ", " + parts[2]);
			}
			legend.put(line.charAt(0), parts[1]);
		}
		
		sc.close();
	}

	/**
	 * Reads in Map file, populating board.
	 * 
	 * @throws FileNotFoundException
	 */
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		Scanner sc = new Scanner(new File(boardConfigFile));
		int row = 0;

		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] parts = line.split(",");

			if(parts.length != numCols) {
				sc.close();
				throw new BadConfigFormatException("Mismatched column length. "
						+ boardConfigFile);
			}

			for(int column = 0; column < parts.length; column++) {
				if(!legend.keySet().contains(parts[column].charAt(0))) {
					sc.close();
					throw new BadConfigFormatException("Unrecognized initial. "
							+ boardConfigFile + ", " + parts[column].charAt(0));
				}

				if(parts[column].length() == 1) {
					board[row][column] = new BoardCell(row, column,
							parts[column].charAt(0), DoorDirection.NONE);
				} else {
					if(parts[column].substring(1).equals("U")) {
						board[row][column] = new BoardCell(row, column,
								parts[column].charAt(0), DoorDirection.UP);
					} else if(parts[column].substring(1).equals("D")) {
						board[row][column] = new BoardCell(row, column,
								parts[column].charAt(0), DoorDirection.DOWN);
					} else if(parts[column].substring(1).equals("L")) {
						board[row][column] = new BoardCell(row, column,
								parts[column].charAt(0), DoorDirection.LEFT);
					} else if(parts[column].substring(1).equals("R")) {
						board[row][column] = new BoardCell(row, column,
								parts[column].charAt(0), DoorDirection.RIGHT);
					} else {
						board[row][column] = new BoardCell(row, column,
								parts[column].charAt(0), DoorDirection.NONE);
					}
				}
			}

			++row;
		}

		sc.close();

		if(row != numRows) {
			throw new BadConfigFormatException("Mismatched column length.");
		}
	}

	/**
	 * Calculates number of cols and rows.
	 * 
	 * @throws FileNotFoundException
	 */
	public void readRowsCols() throws FileNotFoundException {

		numCols = 0;
		numRows = 0;

		Scanner sc = new Scanner(new File(boardConfigFile));
		while(sc.hasNextLine()) {
			String[] parts = sc.nextLine().split(",");
			numRows++;
			numCols = parts.length;
		}

		sc.close();
	}

	/**
	 * @return The legend Map
	 */
	public Map<Character, String> getLegend() {
		return legend;
	}

	/**
	 * @return Number of rows
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * @return Number of cols
	 */
	public int getNumCols() {
		return numCols;
	}

	/**
	 * @param x
	 * @param y
	 * @return The cell at pos (x, y)
	 */
	public BoardCell getCellAt(int x, int y) {
		return board[x][y];
	}

	/**
	 * Creates adjMatrix for all BoardCells.
	 */

	public void calcAdjacencies() {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				int l = board[i].length;
				int w = board.length;
				if(getCellAt(i, j).getInitial() == 'W' || getCellAt(i, j).isDoorway()) {
					HashSet<BoardCell> temp = new HashSet<>();
					BoardCell bc;

					if(!getCellAt(i, j).isDoorway()) {
						if(i + 1 < w) {
							bc = getCellAt(i + 1, j);
							if(bc.isDoorway()) {
								if(bc.getDoorDirection() == DoorDirection.UP) {
									temp.add(bc);
								}
							} else {
								if(bc.getInitial() == 'W') {
									temp.add(bc);
								}
							}
						}
						if(j - 1 >= 0) {
							bc = getCellAt(i, j - 1);
							if(bc.isDoorway()) {
								if(bc.getDoorDirection() == DoorDirection.RIGHT) {
									temp.add(bc);
								}
							} else {
								if(bc.getInitial() == 'W') {
									temp.add(bc);
								}
							}
						}
						if(i - 1 >= 0) {
							bc = getCellAt(i - 1, j);
							if(bc.isDoorway()) {
								if(bc.getDoorDirection() == DoorDirection.DOWN) {
									temp.add(bc);
								}
							} else {
								if(bc.getInitial() == 'W') {
									temp.add(bc);
								}
							}
						}
						if(j + 1 < l) {
							bc = getCellAt(i, j + 1);
							if(bc.isDoorway()) {
								if(bc.getDoorDirection() == DoorDirection.LEFT) {
									temp.add(bc);
								}
							} else {
								if(bc.getInitial() == 'W') {
									temp.add(bc);
								}
							}
						}
					} else {
						if(getCellAt(i, j).getDoorDirection() == DoorDirection.LEFT) {
							temp.add(getCellAt(i, j - 1));
						} else if(getCellAt(i, j).getDoorDirection() == DoorDirection.RIGHT) {
							temp.add(getCellAt(i, j + 1));
						} else if(getCellAt(i, j).getDoorDirection() == DoorDirection.UP) {
							temp.add(getCellAt(i - 1, j));
						} else if(getCellAt(i, j).getDoorDirection() == DoorDirection.DOWN) {
							temp.add(getCellAt(i + 1, j));
						}
					}
					
					adjMatrix.put(getCellAt(i, j), temp);
				} else {
					adjMatrix.put(getCellAt(i, j), new HashSet<>());
				}
			}
		}
	}

	/**
	 * @param x
	 * @param y
	 * @return The AdjList for pos (x, y)
	 */
	public Set<BoardCell> getAdjList(int x, int y) {
		return adjMatrix.get(getCellAt(x, y));
	}

	/**
	 * Generates targets for pos (x, y)
	 * 
	 * @param x
	 * @param y
	 * @param pathLength
	 *            TODO what is this for?
	 */
	public void calcTargets(int x, int y, int pathLength) {
		targets = new HashSet<>();
		visited = new HashSet<>();
		BoardCell startCell = getCellAt(x, y);
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}

	/**
	 * Recursive method that calulcates targets. Called by calcTargets, no need
	 * to call directly.
	 * 
	 * @param curCell
	 *            The player's current cell
	 * @param numSteps
	 *            The number of steps to take (TODO: Is this correct?)
	 */
	private void findAllTargets(BoardCell curCell, int numSteps) {
		for(BoardCell cell_t : adjMatrix.get(curCell)) {
			BoardCell cell = getCellAt(cell_t.getRow(), cell_t.getColumn());
			if(!visited.contains(cell)) {
				visited.add(cell);
				if(numSteps == 1) {
					if(cell.getInitial() == 'W' || cell.isDoorway()) {
						targets.add(cell);
					}
				} else {
					if(cell.isDoorway()) {
						targets.add(cell);
					}
					findAllTargets(cell, numSteps - 1);
				}
				visited.remove(cell);
			}
		}
	}

	/**
	 * @return A collection of valid targets
	 */
	public Set<BoardCell> getTargets() {
		return targets;
	}

	/**
	 * Loads people and the deck
	 */
	public void loadConfigFiles() {
		try {
			loadPeople(); // load people
			loadDeck(); // load deck
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads in people from the file
	 * 
	 * @throws FileNotFoundException
	 */
	private void loadPeople() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(playerConfigFile));
		players = new Player[NUM_PLAYERS];
		int i = 0;

		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] parts = line.split(", ");
			players[i] = new Player(parts[0], parts[1]);
			i++;
		}

		sc.close();
	}

	/**
	 * Loads deck from files
	 * 
	 * @throws FileNotFoundException
	 */
	private void loadDeck() throws FileNotFoundException {
		deck = new Stack<>();

		// gets weapons
		Scanner sc = new Scanner(new File(weaponConfigFile));
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			deck.push(new Card(line, CardType.WEAPON));
		}
		sc.close();

		// gets rooms
		Scanner rSc = new Scanner(new File(roomConfigFile));
		while(rSc.hasNextLine()) {
			String line = rSc.nextLine();
			String[] parts = line.split(", ");
			if(parts[2].equals("Card")) {
				deck.push(new Card(parts[1], CardType.ROOM));
			}
		}
		rSc.close();

		// gets players
		for(Player p : players) {
			deck.push(new Card(p.getName(), CardType.PERSON));
		}
	}

	/**
	 * Deals out cards to each player
	 */
	public void dealCards() {
		deck = randomize(deck);
		int i = 0;
		
		while(deck.size() != 0) {
			players[i].giveCard(deck.pop());
			i++;
			if(i == NUM_PLAYERS) {
				i = 0;
			}
		}
	}

	/**
	 * Randomizes the deck, as well as choosing one card of each type
	 * 
	 * NOT YET IMPLEMENTED!
	 * currently removes 3 cards from the deck to simulate choosing the 3 cards
	 * and returns the unrandomized deck.
	 * 
	 * @param deck
	 * @return randomized deck
	 */
	private Stack<Card> randomize(Stack<Card> deck) {
		Card c;
		c = deck.pop();
		c = deck.pop();
		c = deck.pop();
		return deck;
	}
	
	/**
	 * @return deck
	 */
	public Stack<Card> getDeck() {
		return deck;
	}

	/**
	 * @return players
	 */
	public Player[] getPeople() {
		return players;
	}

	public void selectAnswer() {
		return;
	}

	public Card handleSuggestions() {
		return null;
	}

	public boolean checkAccusation() {
		return false;
	}
	
	public boolean makeSuggestion(Card c1, Card c2, Card c3) {
		return false;
	}
}
