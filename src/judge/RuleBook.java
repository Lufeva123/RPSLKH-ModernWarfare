package judge;

/**
 * Class that contains the encoded rules of the game
 * Take and adapted from the RPSLK assignment
 * @author Luiz do Valle
 *
 */
public class RuleBook {

	/**
	 * Array that holds the relationship between different throws
	 */
	private int[][] rules;

	/**
	 * Default constructor for the class
	 */
	public RuleBook() {

		initializeRules();
	}

	/**
	 * Method that initializes the rules array with the game rules
	 * 
	 * Creates an n x n array representing the rules of the game where n is the
	 * number of moves, the row number represents the index of the player's throw in
	 * the validPlays array and the column number the computer's so, if the
	 * validPlays array is initialized as [r, p, s, k, l]:
	 * 
	 * r = 0 row/column p = 1 row/column s = 2 row/column k = 3 row/column l = 4
	 * row/column
	 * 
	 * r p s k l = computer r[ 0 -1 1 -1 1] r p s p[ 1 0 -1 1 -1] r[ 0 -1 1] s[-1 1
	 * 0 -1 1] or p[ 1 0 -1] l[ 1 -1 1 0 -1] s[-1 1 0] k[-1 1 -1 1 0] = player
	 * 
	 * a 1 represents a player win a 0 represents a tie a -1 represents a computer
	 * win
	 */
	public void initializeRules() {

		int size = ValidPlaysLibrary.getValidPlaysSize();

		rules = new int[size][size];

		initializePlayerWin();
		initializeTie();
		initializeComputerWin();
	}

	/**
	 * Method that returns the rule at the row playerPlay and column computerPlay
	 * 
	 * @param gameThrow1 the index of the first throw in the ValidPlaysLibrary
	 * @param gameThrow2 the index of the second throw in the ValidPlayslibrary
	 * @return 1 for a player victory, 0 for a tie, and -1 for a computer victory
	 */
	public int getRuleAt(int gameThrow1, int gameThrow2) {

		return rules[gameThrow1][gameThrow2];
	}

	/**
	 * Fills an n x n array like so [0 ] [ 0 ] [ 0 ] [ 0 ] [ 0]
	 */
	private void initializeTie() {

		fillDiagonals(0, 0, 0);

	}

	/**
	 * Fills an n x n array like so [ 1 1] [1 1 ] [ 1 1] [1 1 ] [ 1 1 ]
	 */
	private void initializePlayerWin() {

		int size = rules.length;

		int numDiagonals = (size - 1) / 2;

		int initialRow = 1;
		int initialColumn = 0;
		int value = 1;

		for (int diagonal = 0; diagonal < numDiagonals; diagonal++) {

			fillDiagonals(initialRow, initialColumn, value);
			initialRow += 2;
		}

	}

	/**
	 * Fills an n x n array like so [ -1 -1 ] [ -1 -1] [-1 -1 ] [ -1 -1] [-1 -1 ]
	 */
	private void initializeComputerWin() {

		int size = rules.length;

		int numDiagonals = (size - 1) / 2;

		int initialRow = 2;
		int initialColumn = 0;
		int value = -1;

		for (int diagonal = 0; diagonal < numDiagonals; diagonal++) {

			fillDiagonals(initialRow, initialColumn, value);
			initialRow += 2;
		}
	}

	/**
	 * Method that fills the diagonal that starts at the given row and column with the given value
	 * @param initialRow the first row of the diagonal
	 * @param initialColumn the first column of the diagonal
	 * @param value the value to be used to fill the diagonal
	 */
	private void fillDiagonals(int initialRow, int initialColumn, int value) {

		int size = rules.length;

		int row = initialRow;
		int column = initialColumn;

		while (column < size) {

			if (row >= size) {

				row -= size;

			}

			rules[row][column] = value;

			row++;
			column++;
		}
	}
}
