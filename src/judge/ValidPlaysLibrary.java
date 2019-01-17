package judge;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Generated;

/**
 * Information Holder class used to keep track of the allowed plays
 * and relationships between them
 * Taken and adapted from the RPSLK assignment
 * 
 * NOTE:Blackhole ('h') is not included in the rules below because it
 * does not conform with the same rules as the other throws and is
 * therefore treated separately
 * 
 * @author Luiz do Valle
 *
 */
public class ValidPlaysLibrary {

	/*
	 * MUST be initialized in the form (x, +, -, +, -,...) where x is any throw, +
	 * is a throw that defeats x and - is a throw that loses to x. 
	 * This is important for the implementation of the RuleBook class,
	 * which uses this to
	 * automatically create the rules, and the rest of the ValidPlays class
	 */
	private static char[] validPlays = { 'r', 'p', 's', 'k', 'l'};

	// Same order as validPlays array
	private static String[] descriptions = { "Rock", "Paper", "Scissors", "Spock", "Lizard"};

	/*
	 * If validPlays is {r, p, s, k, l}, this array MUST be initialized according to this rule:
	 * 		  
	 *        0  1  2  3  4	  0  1  2  3  4
	 *  	- r, p, s, k, l | r, p, s, k, l 
	 *  
	 *  	{{"r -> s", "r -> l"}, {"p -> k", "p -> r"}, {"s -> l", "s -> p"}, {"k -> r", "k -> s"}, {"l -> p", "l -> k"}}
	 *  
	 *  which in this case translates to
	 * 	
	 * { { "crushes", "crushes" }, {"disproves", "covers"}, {"decapitates", "cuts"}, {"vaporizes", "smashes"}, {"eats", "poisons"}}
	 * 
	 * for {r, p, s}
	 * {{"crushes"}, {"covers"}, {"cuts"}}
	 */
	private static String[][] actions = {{ "crushes", "crushes" }, {"disproves", "covers"}, {"decapitates", "cuts"}, {"vaporizes", "smashes"}, {"eats", "poisons"}};

	/**
	 * Method that returns the long name of a valid play
	 * 
	 * @param index position of the play in the ValidPlaysLibrary
	 * @return element of descriptions
	 */
	public static String getPlayDescription(int index) {

		return descriptions[index];
	}

	/**
	 * Method that returns the play in the given position of the validPlays array
	 * 
	 * @param index position of the play in the ValidPlaysLibrary
	 * @return valid play at the position
	 */
	public static char getPlayAt(int index) {

		return validPlays[index];
	}

	/**
	 * Method that returns the index of the given play in the validPlays array
	 * 
	 * @param play the play to retrieve the index of
	 * @return index of valid play, or -1 if the play does not exist
	 */
	public static int getIndexOf(char play) {

		int index = -1;

		for (int i = 0; i < validPlays.length; i++) {

			char validPlay = getPlayAt(i);

			if (play == validPlay) {

				index = i;
			}
		}

		return index;
	}

	/**
	 * Method that returns the size of the validPlays array
	 * 
	 * @return size of the validPlays array
	 */
	public static int getValidPlaysSize() {

		return validPlays.length;
	}
	
	/**
	 * Method that forms an explanation for why a play defeats another
	 * 
	 * Looks for how what is the index of the losing play RELATIVE to the WINNING PLAY,
	 * taking advantage of the special ordering of the validPlays and actions array.
	 * 
	 *  Example: validPlays is {r, p, s, k, l} and the winningPlay is l and the losingPlay k
	 *  	 
	 *  	  0  1  2  3  4	  0  1  2  3  4
	 *  	- r, p, s, k, l | r, p, s, k, l 
	 *  
	 *  	- Uses the special ordering of the validPLays array
	 *  
	 *  	- k is the second play in the order above (left to right) that loses to l, so its distance from l is 1 (p is 0)
	 *  
	 *  	-The method uses the position of the winning play and the relative position of the losing play to 
	 *  retrieve the action corresponding to that scenario.
	 *  
	 *  	-In the case above, Lizard 'poisons' Spock
	 *  
	 * @param winningPlay
	 * @param losingPlay
	 * @return a String explaining why a play defeats another
	 */
	public static String getExplanationBasedOnPlays(int winningPlay, int losingPlay) {

		if(winningPlay == -1) {
			
			return "Blackhole destroys everything";
		}
		
		int distanceFromWin = -1;
		
		int initial = winningPlay;
		
		int size = getValidPlaysSize();
		
		while(initial != losingPlay) {
			
			initial += 2;
			
			if (initial >= size) {
				
				initial -= size;
			}
			
			distanceFromWin++;
		}
		
		String action = actions[winningPlay][distanceFromWin];
		String winningPlayDescription = getPlayDescription(winningPlay);
		String losingPlayDescription = getPlayDescription(losingPlay);
		
		String explanation = String.format("%s %s %s", winningPlayDescription, action, losingPlayDescription);
		
		return explanation;
	}
	
	public static String getAllExplanations() {
		
		int size = getValidPlaysSize();
		
		StringBuilder explanations = new StringBuilder();
		
		for (int row = 0; row < size; row++) {
			
			int losingPlayIndex = row;
					
			for (int column = 0; column < actions[row].length; column++) {
				
				losingPlayIndex += 2;
				
				if (losingPlayIndex >= size) {
					
					losingPlayIndex -= size;
				}
				
				String explanation = getExplanationBasedOnPlays(row, losingPlayIndex);
				explanations.append(explanation + "\n");
			}
		}
		
		return explanations.toString();
	}
}
