package mainApp;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Class: Drawable
 * @author A_408
 * <br>Purpose: An abstract class for all the objects to be drawn on the screen that keeps track of the
 *     position, dimensions, and which objects should be removed when the screen is updated
 * <br>Restrictions: the objects can only have the same information used for drawing rectangles with graphics
 *     2D: x and y positions, width, and height
 */
public abstract class Drawable {
	private int positionX;
	private int positionY;
	private int dimensionX;
	private int dimensionY;
	private Rectangle rectangle;
	private boolean remove;
	private int timeCount;
	private boolean imageSwitch;
	
	/**
	 * ensures: initializes the x position to positionX, the y position to positionY, the x dimension to 
	 *     dimensionX, the y dimension to dimensionY, the rectangle boundary to a new rectangle with the x and 
	 *     y position and dimensions, remove to false (so the object won't be removed when the screen is
	 *     updated), and gets the image of the drawable object
	 * @param positionX
	 * @param positionY
	 * @param dimensionX
	 * @param dimensionY
	 */
	public Drawable(int positionX, int positionY, int dimensionX, int dimensionY) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.dimensionX = dimensionX;
		this.dimensionY = dimensionY;
		this.rectangle = new Rectangle(positionX, positionY, dimensionX, dimensionY);
		this.getImage();
		this.remove = false;
	}
	
	/**
	 * ensures: the collision is handled for when the hero collides with each type of object
	 * @param hero
	 */
	public abstract void collideWith(Hero hero);
	
	/**
	 * ensures: remove is set to true so that the object is removed when the screen updates
	 */
	public void markToRemove() {
		remove = true;
	}
	
	/**
	 * TODO: Add JavaDocs
	 * @param time
	 */
	public void timeCounter(int time) {
		timeCount++;
		if(timeCount>time) {
			if(imageSwitch==false) {
				imageSwitch=true;
			}
			else {
				imageSwitch=false;
			}
			timeCount=0;
		}
		
	}
	
	/**
	 * ensures: the Drawable object is drawn based on each type of object
	 * @param g2
	 */
	public abstract void drawOn(Graphics2D g2);
	
	/**
	 * ensures: the image for the drawable object is found
	 */
	public abstract void getImage();
	
	/**
	 * ensures: the rectangle boundary for the drawable object is returned
	 * @return the rectangle boundary for the drawable object
	 */
	public Rectangle getRectangle() {
		return rectangle;
	}
	
	/**
	 * ensures: the x position of the drawable object is returned
	 * @return the x position of the drawable object
	 */
	public int getPositionX() {
		return positionX;
	}
	
	/**
	 * ensures: the y position of the drawable object is returned
	 * @return the y position of the drawable object
	 */
	public int getPositionY() {
		return positionY;
	}
	
	/**
	 * ensures: the width of the drawable object is returned
	 * @return the width of the drawable object
	 */
	public int getWidth() {
		return dimensionX;
	}
	
	/**
	 * ensures: the height of the drawable object is returned
	 * @return the height of the drawable object
	 */
	public int getHeight() {
		return dimensionY;
	}
	
	/**
	 * ensures: remove is returned, so true if the hero collided with it and it needs to be removed, false 
	 *     otherwise
	 * @return true if the hero collided with it and it needs to be removed, false otherwise
	 */
	public boolean getRemove() {
		return remove;
	}
	
	/**
	 * ensures: imageSwitch is returned, so true if the image needs to be switched (like moving left image to 
	 *     moving right image), false otherwise
	 * @return true if the image needs to be switched (like moving left image to moving right image), false 
	 *     otherwise
	 */
	public boolean getImageSwitch() {
		return imageSwitch;
	}
	
	/**
	 * ensures: positionX is updated to the new x position and the rectangle boundary is updated to have the 
	 *     new x position
	 * @param positionX the new x position
	 */
	public void setPositionX(int positionX) {
		this.positionX = positionX;
		this.rectangle = new Rectangle(positionX, positionY, dimensionX, dimensionY);
	}
	
	/**
	 * ensures: positionY is updated to the new y position and the rectangle boundary is updated to have the 
	 *     new y position
	 * @param positionY the new y position
	 */
	public void setPositionY(int positionY) {
		this.positionY = positionY;
		this.rectangle = new Rectangle(positionX, positionY, dimensionX, dimensionY);
	}
	
	/**
	 * ensures: sets imageSwitch to true if the image needs to be changed to a new image (like moving left 
	 *     image to moving right image) and false if the image stays the same
	 * @param ans if the image needs to be switched
	 */
	public void setImageSwitch(boolean ans) {
		imageSwitch = ans;
	}
}