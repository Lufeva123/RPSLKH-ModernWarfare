package game_throws;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * Class that represents the Blackhole throw
 * @author Luiz do Valle
 *
 */
public class Blackhole extends GameThrow {

	/**
	 * The color of this blackhole
	 */
	private Color color;
	/**
	 * The major axis of the blackhole
	 */
	private final int MINOR_AXIS = 80;
	/**
	 * The minor axis of the blackhole
	 */
	private final int MAJOR_AXIS = 100;
	
	/**
	 * Private constructor used in the builder pattern
	 * @param builder the builder used to build this Blackhole
	 */
	private Blackhole(Builder builder) {
		
		this.charRepresentation = 'h';
		
		this.color = builder.color;
		this.xCoord = builder.xCoord;
		this.yCoord = builder.yCoord;
		this.xSpeed = builder.xSpeed;
		this.ySpeed = builder.ySpeed;
		this.throwRectangle = new Rectangle2D.Double(xCoord, yCoord - MINOR_AXIS, MAJOR_AXIS, MINOR_AXIS);
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(color);
		
		int width = (int) throwRectangle.getWidth();
		int height = (int) throwRectangle.getHeight();
		
		g.drawOval(xCoord, yCoord - MINOR_AXIS, width, height);
		g.fillOval(xCoord, yCoord - MINOR_AXIS, width, height);
	}
	
	@Override
	public void increaseSize(double scalar) {
		
		int currentWidth = (int) throwRectangle.getWidth();
		int currentHeight = (int) throwRectangle.getHeight();
		
		int newWidth = (int) (currentWidth*scalar);
		int newHeight = (int) (currentHeight*scalar);
		
		this.throwRectangle = new Rectangle2D.Double(xCoord, yCoord, newWidth, newHeight);
		
	}
	
	/**
	 * Builder pattern used to create the Blackhole throw
	 * Builder pattern used because constructor would have too many parameters
	 * @author Luiz do Valle
	 *
	 */
	public static class Builder {
		
		private Color color = new Color(0, 0, 0);
		
		private int xCoord = 0;
		private int yCoord = 0;
		
		private int xSpeed = 0;
		private int ySpeed = 0;
		
		/**
		 * Method that sets the initial x coordinate (upper left) of this blackhole
		 * @param x the initial x coordinate
		 * @return the builder
		 */
		public Builder xCoord(int x) {
			
			this.xCoord = x;
			return this;
		}
		
		/**
		 * Method that sets the initial y coordinate (upper left) of this blackhole
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
		 * Method that builds the blackhole
		 */
		public Blackhole build() {
			
			return new Blackhole(this);
		}
	}
}
