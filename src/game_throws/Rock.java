package game_throws;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import game_throws.Lizard.Builder;

/**
 * Class that represents the Rock throw
 * @author Luiz do Valle
 *
 */
public class Rock extends GameThrow {

	/**
	 * The name of the throw, e.g. Paper
	 */
	private String throwName;
	/**
	 * The font corresponding to this throw
	 */
	private String txtFont;
	/**
	 * The size of this throw's font
	 */
	private int txtSize;
	/**
	 * The context used to draw this throw on the screen
	 */
	private FontRenderContext fontRenderContext;
	/**
	 * The color of this throw's text
	 */
	private Color color;
	
	/**
	 * Private constructor used in the builder pattern
	 * @param builder the builder used to build this throw
	 */
	private Rock(Builder builder) {
		
		this.charRepresentation = 'r';
		
		this.throwName = builder.txtName;
		this.txtFont = builder.txtFont;
		this.txtSize = builder.txtSize;
		this.fontRenderContext = builder.fontRenderContext;
		this.color = builder.color;
		this.xCoord = builder.xCoord;
		this.yCoord = builder.yCoord;
		this.xSpeed = builder.xSpeed;
		this.ySpeed = builder.ySpeed;
	}
	
	@Override
	public void draw(Graphics g) {
		
		Font throwFont = new Font(txtFont, Font.BOLD, txtSize);
		this.throwRectangle = throwFont.getStringBounds(throwName, fontRenderContext);
		g.setFont(throwFont);
		g.setColor(color);
		g.drawString(throwName, xCoord, yCoord);
	}
	
	@Override
	public void increaseSize(double scalar) {
		
		
		this.txtSize *= scalar;
		
	}

	/**
	 * Builder pattern used to create the Rock throw
	 * Builder pattern used because constructor would have too many parameters
	 * @author Luiz do Valle
	 *
	 */
	public static class Builder {
		
		private String txtName =  "ROCK";
		private String txtFont = "Rockwell Extra Bold";
		private int txtSize = 20;
		private FontRenderContext fontRenderContext;
		private Color color = new Color(139, 69, 19);
		
		private int xCoord = 0;
		private int yCoord = 0;
		
		private int xSpeed = 0;
		private int ySpeed = 0;
		
		/**
		 * Method that sets the initial x coordinate (lower left) of this rock
		 * @param x the initial x coordinate
		 * @return the builder
		 */
		public Builder xCoord(int x) {
			
			this.xCoord = x;
			return this;
		}
		
		/**
		 * Method that sets the initial y coordinate (lower left) of this rock
		 * @param y the initial y coordinate
		 * @return the builder
		 */
		public Builder yCoord(int y) {
			
			this.yCoord = y;
			return this;
		}
		
		/**
		 * Method that sets the speed in the x direction
		 * @param speed the speed in the x direction
		 * @return the builder
		 */
		public Builder xSpeed(int speed) {
			
			this.xSpeed = speed;
			return this;
		}
		
		/**
		 * Method that sets the speed in the y direction
		 * @param speed the speed in the y direction
		 * @return the builder
		 */
		public Builder ySpeed(int speed) {
			
			this.ySpeed = speed;
			return this;
		}
		
		/**
		 * Method that sets the FontRenderContext used to draw
		 * this throw
		 * @param fontRenderContext the FontRenderContext used to draw this throw
		 * @return the builder
		 */
		public Builder fontRenderContext(FontRenderContext fontRenderContext) {
			
			this.fontRenderContext = fontRenderContext;
			return this;
		}
		
		/**
		 * Method that builds the Rock
		 * @return new Rock
		 */
		public Rock build() {
			
			return new Rock(this);
		}
	}
	
}
