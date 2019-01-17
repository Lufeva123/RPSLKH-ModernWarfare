package judge;

import game_throws.GameThrow;

/**
 * Class that determines which (if any) throw lost the collision
 * Taken and adapted from RPSLK assignment
 * @author Luiz do Valle
 *
 */
public class Judge {

	/**
	 * Instance of the RuleBook used to retrieve the game rules
	 */
	private RuleBook ruleBook;

	/**
	 * Default constructor for the class
	 */
	public Judge() {

		ruleBook = new RuleBook();
	}

	/**
	 * Method that determines who won the round, if anyone
	 * 
	 * @param gameThrow1 the first play
	 * @param gameThrow2 the second play
	 * @return "gameThrow2" for a gameThrow1 win, "Tie" for a tie, and "gameThrow1" for a gameThrow2 win
	 */
	public String whoLost(GameThrow gameThrow1, GameThrow gameThrow2) {
		
		char firstThrowChar = gameThrow1.getCharRepresentation();
		char otherThrowChar = gameThrow2.getCharRepresentation();
		
		if(firstThrowChar == 'h') {
			
			return "gameThrow2";
		
		} else if (otherThrowChar == 'h') {
			
			return "gameThrow1";
			
		} 
			
		int gameThrow1Index = ValidPlaysLibrary.getIndexOf(firstThrowChar);
		int gameThrow2Index = ValidPlaysLibrary.getIndexOf(otherThrowChar);
		int result = ruleBook.getRuleAt(gameThrow1Index, gameThrow2Index);
		
		if(result == -1) {
			
			return "gameThrow1";
			
		} else if (result == 1) {
			
			return "gameThrow2";
			
		} else {
			
			return "Tie";
		}
	}
}
