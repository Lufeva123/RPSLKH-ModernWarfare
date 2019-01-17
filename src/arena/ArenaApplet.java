package arena;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.util.LinkedList;

import javax.swing.Timer;

import game_throws.Blackhole;
import game_throws.GameThrow;
import game_throws.Lizard;
import game_throws.Paper;
import game_throws.Rock;
import game_throws.Scissors;
import game_throws.Spock;

/**
 * Class that reads the requirements passed by the user to the html to create the throws and display area
 * Based on the Courseworks example by Professor Kender
 * 
 * TEST:
 * 
 * Test case ID: Comprehensive Test
 * Test case designed by: Luiz do Valle
 * Test Summary: Tests whether the throws are created correctly, move in the nine directions specified, are kept
 * in bounds, and whether collisions are detected and dealt with appropriately
 * Pre-Conditions: The Applet has the correct base class specified
 * Test Data: All the different throws allowed spread out across the screen in a way that different collisions and movement occur. Throws are
 * set to exploit the full range of motion
 * Expected Result: The throws will be displayed appropriately according to their type and the information provided in the
 * html file. The throws will move correctly across the screen and the system will detect when every collision occurs, causing a message
 * explaining the collision to appear, the losing throw to be erased and the winning throw to increase in size
 * Actual Result: The results matched expectations
 * Post-condition: No post-condition required
 * 
 * @author Luiz do Valle
 *
 */
public class ArenaApplet extends Applet {
	
	/**
	 * The JPanel used to display the game (JPanel used because it has default double-buffering, which prevents
	 * flickering)
	 */
	private BattleArena battleArea;
	
	/**
	 * Method called once the applet starts for the first time
	 */
	public void init() {
		
		LinkedList<GameThrow> gameThrows = createThrows();
		
		int delay = Integer.parseInt(getParameter("delay"));
		int timeOut = Integer.parseInt(getParameter("timeout"));
		int collisionOut = Integer.parseInt(getParameter("collisionOut"));
		
		battleArea = new BattleArena(gameThrows, delay, timeOut, collisionOut);
		
		add(battleArea, BorderLayout.CENTER);
		
	}
	
	/**
	 * Method called when the applet resumes after being paused
	 */
	public void start() {
		
		battleArea.startTimers();
	}
	
	/**
	 * Method called when the applet is paused
	 */
	public void stop() {
		
		battleArea.stopTimers();
	}
	
	/**
	 * Method called when the applet is destroyed
	 */
	public void destroy() {
		
		
	}
	
	/**
	 * Helper method that uses the relevant parameters in the html to create the throws the user requested
	 * @return LinkedList of GameThrows containing all the requested throws
	 */
	private LinkedList<GameThrow> createThrows() {
		
		LinkedList<GameThrow> gameThrows = new LinkedList<>();
		
		Graphics2D g2D = (Graphics2D) getGraphics();
		FontRenderContext throwContext = g2D.getFontRenderContext();
		
		String[] allowedThrows = getParameter("throws").split(",");
		String[] xCoords = getParameter("xCoords").split(",");
		String[] yCoords = getParameter("yCoords").split(",");
		String[] speedXs = getParameter("speedXs").split(",");
		String[] speedYs = getParameter("speedYs").split(",");
		
		for(int i = 0; i < allowedThrows.length; i++) {
			
			String gameThrow = allowedThrows[i];
			int xCoord = Integer.parseInt(xCoords[i]);
			int yCoord = Integer.parseInt(yCoords[i]);
			int speedX = Integer.parseInt(speedXs[i]);
			int speedY = Integer.parseInt(speedYs[i]);
			
			if(gameThrow.equals("Rock")) {
				
				gameThrows.add(new Rock.Builder().xCoord(xCoord).yCoord(yCoord).
						xSpeed(speedX).ySpeed(speedY).fontRenderContext(throwContext).build());
			
			} else if(gameThrow.equals("Paper")) {
				
				gameThrows.add(new Paper.Builder().xCoord(xCoord).yCoord(yCoord).
						xSpeed(speedX).ySpeed(speedY).fontRenderContext(throwContext).build());
				
			} else if(gameThrow.equals("Scissors")) {
				
				gameThrows.add(new Scissors.Builder().xCoord(xCoord).yCoord(yCoord).
						xSpeed(speedX).ySpeed(speedY).fontRenderContext(throwContext).build());
				
			} else if(gameThrow.equals("Lizard")) {
				
				gameThrows.add(new Lizard.Builder().xCoord(xCoord).yCoord(yCoord).
						xSpeed(speedX).ySpeed(speedY).fontRenderContext(throwContext).build());
			
			} else if(gameThrow.equals("Spock")) {
				
				gameThrows.add(new Spock.Builder().xCoord(xCoord).yCoord(yCoord).
						xSpeed(speedX).ySpeed(speedY).fontRenderContext(throwContext).build());
			
			} else if(gameThrow.equals("Blackhole")) {
				
				gameThrows.add(new Blackhole.Builder().xCoord(xCoord).yCoord(yCoord).
						xSpeed(speedX).ySpeed(speedY).build());
			}
		}
		
		return gameThrows;
	}
}
