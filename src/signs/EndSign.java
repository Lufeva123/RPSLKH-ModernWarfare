package signs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Class that represents the message displayed when the game ends
 * @author Luiz do Valle
 *
 */
public class EndSign implements Sign {

	/**
	 * The end-game message to be displayed
	 */
	private static final String MESSAGE = "END OF GAME";
	/**
	 * The font type to be used in this message
	 */
	private static final String FONT_TYPE = "Arial";
	/**
	 * The size of this message's text
	 */
	private static final int SIZE = 50;
	
	/**
	 * Font reference used to create this message
	 */
	private Font font;
	/**
	 * The FontRenderContext used to render this message
	 */
	private FontRenderContext context;
	
	/**
	 * The x coordinate of the geometrical center of the bounding box of this message
	 */
	private int centerX;
	/**
	 * The y coordinate of the geometrical center of the bounding box of this message
	 */
	private int centerY;
	
	/**
	 * Constructor used to initialize the class fields
	 * @param centerX the x coordinate of the geometrical center of the bounding box of this message
	 * @param centerY the y coordinate of the geometrical center of the bounding box of this message
	 * @param context the FontRenderContext used to render this message
	 */
	public EndSign(int centerX, int centerY, FontRenderContext context) {
		
		this.font = new Font(FONT_TYPE, Font.BOLD, SIZE);
		
		this.centerX = centerX;
		this.centerY = centerY;
		
		this.context = context;
		
	}

	@Override
	public void draw(Graphics g) {
		
		Rectangle2D bounds = font.getStringBounds(MESSAGE, context);
		int lowerLeftX = (int) (centerX - bounds.getWidth()/2);
		int lowerLeftY = (int) (centerY + bounds.getHeight()/2);
		
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(MESSAGE, lowerLeftX, lowerLeftY);
		
	}
	
	
}
