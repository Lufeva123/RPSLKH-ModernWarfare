package signs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Class that represents the message displayed when two throws collide
 * @author Luiz do Valle
 *
 */
public class CollisionSign implements Sign {
	
	/**
	 * The size of the message's font
	 */
	private static final int SIZE = 20;
	/**
	 * The font name
	 */
	private static final String FONT_TYPE = "Georgia";
	
	/**
	 * The message to be displayed
	 */
	private String message;
	/**
	 * The font used to draw the text on the screen
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
	 * Constructor that initializes class fields
	 * @param centerX the x coordinate of the geometrical center of the bounding box of this message
	 * @param centerY the y coordinate of the geometrical center of the bounding box of this message
	 * @param context the FontRenderContext used to render this message
	 * @param message the message to be displayed
	 */
	public CollisionSign(int centerX, int centerY, FontRenderContext context, String message) {
		
		this.centerX = centerX;
		this.centerY = centerY;
		
		this.context = context;
		this.message = message;
		
		this.font = new Font(FONT_TYPE, Font.BOLD, SIZE);
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		Rectangle2D bounds = font.getStringBounds(message, context);
		
		//Made height of sign twice that of the plays so that it covers the plays and attention is drawn to what is says
		int height = (int) bounds.getHeight();
		int width = (int) bounds.getWidth();
		
		int lowerLeftX = (int) (centerX - width/2);
		int lowerLeftY = (int) (centerY + height/2);
		
		//Message has a rectangle behind it to allow easier readability
		int rectHeight = (int) (height * 1.5);
		int rectWidth = width;
		
		int rectTopLeftX = lowerLeftX;
		int rectTopLeftY = lowerLeftY - height;
		
		
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(rectTopLeftX, rectTopLeftY, rectWidth, rectHeight);
		
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(message, lowerLeftX, lowerLeftY);
		
	}
}
