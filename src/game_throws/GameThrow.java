package game_throws;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * Abstract class that serves as the blueprint for all the game throws
 * Abstract class used instead of interface because there are many fields and method implementations common
 * to all of the game throws which could be abstracted to this class instead of repeated in every other one
 * @author Luiz do Valle
 *
 */
public abstract class GameThrow {

	/**
	 * The char representing the throw
	 * Used by the Judge, ValidPlays, and RuleBook classes to efficiently find the rule governing the 
	 * interaction between this throw and another one
	 */
	protected char charRepresentation;
	
	/**
	 * The x coordinate of the bottom left corner of the bounding box that
	 * encompasses this throw
	 */
	protected int xCoord;
	/**
	 * The y coordinate of the bottom left corner of the bounding box that
	 * encompasses this throw
	 */
	protected int yCoord;
	/**
	 * The bouding box of this throw
	 */
	protected Rectangle2D throwRectangle;
	
	/**
	 * The amount by which this throw moves horizontally
	 * If positive, moves to the right. if negative, moves to the left
	 */
	protected int xSpeed;
	/**
	 * The amount by which this throw moves vertically
	 * If positive moves down. If negative moves up
	 */
	protected int ySpeed;
	
	/**
	 * Method that draws the given throw on the screen
	 * @param g the Graphics instance used to draw this throw
	 */
	public abstract void draw(Graphics g);
	
	/**
	 * Method used to increase the relative size of the throw on the screen by the given scalar
	 * @param scalar factor used to increase the throw's relative size
	 */
	public abstract void increaseSize(double scalar);
	
	/**
	 * Method used to move the throw on the screen based on the speedX and speedY fields
	 */
	public void translate() {
		
		xCoord += xSpeed;
		yCoord += ySpeed;
		
	}
	
	/**
	 * Method that returns the x position of the bottom left corner of this throw's bounding box
	 * @return the x position of the bottom left corner of this throw's bounding box
	 */
	public int getLeftXPos() {
		
		return xCoord;
	}
	
	/**
	 * Method that returns the x position of the bottom right corner of this throw's bounding box
	 * @return the x position of the right corner of this throw's bounding box
	 */
	public int getRightXPos() {
		
		return (int) (xCoord + throwRectangle.getWidth());
	}

	/**
	 * Method that returns the y position of the top left corner of this throw's bounding box
	 * @return the y position of the top left corner of this throw's bounding box
	 */
	public int getTopY() {
		
		return yCoord - (int) throwRectangle.getHeight();
	}

	/**
	 * Method that returns the y position of the bottom left corner of this throw's bounding box
	 * @return the y position of the bottom left corner of this throw's bounding box
	 */
	public int getBottomY() {
		
		return yCoord;
	}

	/**
	 * Method that resets the x position of this throw
	 * If the new position is zero (left side of screen), the throw is placed with its right corner just on the screen's border
	 * If the new position is not zero, it is expected that it represents the right boundary of the screen, and so the throw's
	 * left side is placed just outside the right boundary of the screen
	 * @param newX the new X value 
	 */
	public void resetX(int newX) {
		
		if(newX == 0) {
			
			this.xCoord = -(int)throwRectangle.getWidth();
		
		} else {
			
			this.xCoord = newX;
		}
	}
	
	/**
	 * Method that resets the y position of this throw
	 * If the new position is zero (top of screen), the throw is placed with its bottom corner just on the screen's top border
	 * if the new position is not zero, it is expected that it represents the bottom boundary of the screen, and so the throw's
	 * upper side is placed just outside the bottom boundary of the screen
	 * @param newY the new Y value 
	 */
	public void resetY(int newY) {
		
		if(newY == 0) {
			
			this.yCoord = newY;
		
		} else {
			
			this.yCoord = newY + (int)throwRectangle.getHeight();
		}	
	}

	/**
	 * Method that returns the bounding box of the throw at its current position
	 * @return the Rectangle2D representing the throw at that instant
	 */
	public Rectangle2D getBounds() {
		
		return new Rectangle2D.Double(xCoord, yCoord, throwRectangle.getWidth(), throwRectangle.getHeight());
	}
	
	/**
	 * Method that returns the char representation of this throw
	 * @return the char representation of the throw
	 */
	public char getCharRepresentation() {
		
		return charRepresentation;
		
	}
}
