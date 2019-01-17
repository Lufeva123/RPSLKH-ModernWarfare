package arena;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.Timer;

import game_throws.GameThrow;
import judge.Judge;
import judge.ValidPlaysLibrary;
import signs.CollisionSign;
import signs.EndSign;
import signs.Sign;

/**
 * Class that displays the game and contains the main logic
 * Used because JPanel's double-buffering prevents images on the screen from flickering
 * 
 * System makes sure that Throws are initialized within the applet by calling the method keepThrowsInBounds()
 * @author Luiz do Valle
 *
 */
public class BattleArena extends JPanel {
	
	/**
	 * All the throws that are currently in the game
	 */
	private LinkedList<GameThrow> gameThrows;
	/**
	 * All messages that are currently on the screen 
	 */
	private LinkedList<Sign> signsToDisplay;
	/**
	 * The Judge instance used to decide result of collisions
	 */
	private Judge judge;
	/**
	 * The timer used to refresh the screen and move the throws on screen
	 */
	private Timer timer;
	/**
	 * The timer used to countdown to the end of the game if there is not any collisions during its interval
	 */
	private Timer endOfGameTimer;
	/**
	 * The timer that determines how long the game is paused after a collision
	 */
	private Timer collisionOutTimer;
	
	/**
	 * Constructor for the class that instantiates the class fields
	 * @param gameThrows the LinkedList of all the throws created
	 * @param delay how long it takes for the screen to refresh
	 * @param timeOut how long does it take for the game to end if there are not any collisions
	 * @param collisionOut how long the game should be paused after a collision
	 */
	public BattleArena(LinkedList<GameThrow> gameThrows, int delay, int timeOut, int collisionOut) {
		
		this.gameThrows = gameThrows;
		this.signsToDisplay = new LinkedList<>();
		this.judge = new Judge();
		
		//Setting layout to null allows absolute positioning, needed for placing the collisions signs on different places
		setLayout(new GridLayout());
		timer = new Timer(delay, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				moveThrows();
				keepThrowsInBounds();
				reactToCollissions();
				
				repaint();
			}
		});
		
		endOfGameTimer = new Timer(timeOut, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				timer.stop();
				timer.setRepeats(false);
				endOfGameTimer.stop();
				
				showEndOfGameSign();
				
				repaint();
				
			}
		});
		
		endOfGameTimer.setRepeats(false);
		
		collisionOutTimer = new Timer(collisionOut, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				signsToDisplay.clear();
				timer.start();
				endOfGameTimer.start();
				collisionOutTimer.stop();
				
			}
		});
	}
	
	/**
	 * Overriden method that sets the dimensions of this JPanel to that of that parent container
	 * @return
	 */
	@Override
	public Dimension getPreferredSize() {
		
		return new Dimension(getParent().getWidth(), getParent().getHeight());
	}
	
	/**
	 * Overriden method called every time the JPanel is updated/repainted
	 * Draws the GameThrows and messages that are currently in the game
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		for(GameThrow gameThrow : gameThrows) {
			
			gameThrow.draw(g);
		}
		
		for(Sign sign : signsToDisplay) {
			
			sign.draw(g);
		}
	}
	
	/**
	 * Method that starts the update and count down timers
	 */
	public void startTimers() {
		
		timer.start();
		endOfGameTimer.start();
	}
	
	/**
	 * Method that stops the update and countdown timers
	 */
	public void stopTimers() {
		
		timer.stop();
		endOfGameTimer.stop();
	}
	
	/**
	 * Method that moves all the throws that are still in the game
	 */
	private void moveThrows() {
		
		for(GameThrow gameThrow : gameThrows) {
			
			gameThrow.translate();
		}
	}
	
	/**
	 * Method that keeps the throws within the screen boundaries
	 */
	private void keepThrowsInBounds() {
		
		for(GameThrow gameThrow : gameThrows) {
			
			if(gameThrow.getRightXPos() < 0) {
				
				gameThrow.resetX(getWidth());
			
			} else if (gameThrow.getLeftXPos() > getWidth()) {
				
				gameThrow.resetX(0);
			}
			
			if(gameThrow.getBottomY() < 0) {
				
				gameThrow.resetY(getHeight());
				
			} else if(gameThrow.getTopY() > getHeight()) {
				
				gameThrow.resetY(0);
			}
		}
	}
	
	/**
	 * Method that checks whether there was a collision between two throws and performs the appropriate actions
	 * If two blackholes collide, the one that is later in the gameThrows LinkedList is destroyed
	 * 
	 * NOTE: In case that multiple throws are involved in the same collision, the system analyzes the collision
	 * between the GameThrows that are closest to each other in and closest to the beginning of the gameThrows LinkedList
	 * 
	 * NOTE: This method ignores collisions that result in ties to symbolize that nothing happens and keep
	 * the game from stopping too often
	 */
	private void reactToCollissions() {
		
		for(int i = 0; i < gameThrows.size(); i++) {
			
			GameThrow gameThrow1 = gameThrows.get(i);
			
			for(int j = i + 1; j < gameThrows.size(); j++) {
				
				GameThrow gameThrow2 = gameThrows.get(j);
				
				if(collision(gameThrow1, gameThrow2) && !isTie(gameThrow1, gameThrow2)) {
					
					endOfGameTimer.restart();
					endOfGameTimer.stop();
					timer.stop();
					collisionOutTimer.start();
					
					showCollisionSign(gameThrow1, gameThrow2);
					removeLoser(gameThrow1, gameThrow2);
					enlargeWinner(gameThrow1, gameThrow2);
					
					repaint();
				}
			}
		}
	}
	
	/**
	 * Method that checks whether the two throws collided
	 * @param gameThrow1 the first throw
	 * @param gameThrow2 the secondThrow
	 * @return true if there was a collision, false otherwise
	 */
	private boolean collision(GameThrow gameThrow1, GameThrow gameThrow2) {
		
		return gameThrow1.getBounds().intersects(gameThrow2.getBounds());
	}
	
	/**
	 * Method that checks if the collision of these two GameThrows results in a tie
	 * @param gameThrow1 the first throw
	 * @param gameThrow2 the second throw
	 * @return true if a tie occurs, false otherwise
	 */
	private boolean isTie(GameThrow gameThrow1, GameThrow gameThrow2) {
		
		String result = judge.whoLost(gameThrow1, gameThrow2);
		
		return result.equals("Tie");
		
	}
	
	private void removeLoser(GameThrow gameThrow1, GameThrow gameThrow2) {
		
		String loser = judge.whoLost(gameThrow1, gameThrow2);
		
		if(loser.equals("gameThrow1")) {
			
			gameThrows.remove(gameThrow1);
		
		} else if (loser.equals("gameThrow2")) {
			
			gameThrows.remove(gameThrow2);
		}
	}
	
	/**
	 * Method that enlarges the winner between the following two throws
	 * @param gameThrow1 the first throw
	 * @param gameThrow2 the second throw
	 */
	private void enlargeWinner(GameThrow gameThrow1, GameThrow gameThrow2) {
		
		String loser = judge.whoLost(gameThrow1, gameThrow2);
		
		if(loser.equals("gameThrow1")) {
			
			gameThrow2.increaseSize(1.25);
		
		} else if (loser.equals("gameThrow2")) {
			
			gameThrow1.increaseSize(1.25);
		}
	}
	
	/**
	 * Method that adds a CollisionSign associated with the given GameThrowsto the signsToDisplay LinkedList
	 * @param gameThrow1 the first throw
	 * @param gameThrow2 the second throw
	 */
	private void showCollisionSign(GameThrow gameThrow1, GameThrow gameThrow2) {
		
		Graphics2D g2D = (Graphics2D) getGraphics();
		FontRenderContext context = g2D.getFontRenderContext();
		
		int gameThrow1Index = ValidPlaysLibrary.getIndexOf(gameThrow1.getCharRepresentation());
		int gameThrow2Index = ValidPlaysLibrary.getIndexOf(gameThrow2.getCharRepresentation());
		
		String loser = judge.whoLost(gameThrow1, gameThrow2);
		String message;
		
		if(loser.equals("gameThrow2")) {
			
			message = ValidPlaysLibrary.getExplanationBasedOnPlays(gameThrow1Index, gameThrow2Index);
			
		} else if(loser.equals("gameThrow1")) {
			
			message = ValidPlaysLibrary.getExplanationBasedOnPlays(gameThrow2Index, gameThrow1Index);
			
		} else {
			
			message = "Tie";
		}
		
		
		int centerX = (gameThrow1.getLeftXPos() + gameThrow2.getRightXPos())/2;
		int centerY = (gameThrow1.getTopY() + gameThrow1.getBottomY())/2;
		
		Sign collisionSign = new CollisionSign(centerX, centerY, context, message);
		
		signsToDisplay.add(collisionSign);
		
	}
	
	/**
	 * Method that shows the EndSign in the middle of the screen when the game ends
	 */
	private void showEndOfGameSign() {
		
		Graphics2D g2D = (Graphics2D) getGraphics();
		FontRenderContext context = g2D.getFontRenderContext();
		
		Sign endSign = new EndSign(getWidth()/2, getHeight()/2, context);
		
		signsToDisplay.add(endSign);
	}
}
