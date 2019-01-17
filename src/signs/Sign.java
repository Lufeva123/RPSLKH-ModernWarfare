package signs;

import java.awt.Graphics;

import javax.swing.JLabel;

/**
 * Interface that serves as the blueprint for messages displayed on the screen
 * @author Luiz do Valle
 *
 */
public interface Sign {
	
	/**
	 * Draws the message on the screen using the given Graphics instance
	 * @param g Graphics instance
	 */
	public abstract void draw(Graphics g);
}
