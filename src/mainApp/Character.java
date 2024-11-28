package mainApp;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Class: Character
 * @author A_408
 * <br>Purpose: An abstract class used for character movement, like moving up, left, and right and making sure that the character 
 *     falls when it is supposed to and doesn't move off the screen
 */
public abstract class Character extends Drawable{
	private static final int FALL_SPEED = 3;
	
	private boolean isFalling;
	private int velX;
	private int velY;
	
	/**
	 * ensures: calls the super constructor and passes it the x and y positions, the width, and the height. It 
	 *     also initializes isFalling to false and the x and y velocities to 0
	 * @param positionX the x position of the top left corner of the character
	 * @param positionY the y position of the top left corner of the character
	 * @param dimensionX the x dimension of the character
	 * @param dimensionY the y dimension of the character
	 */
	public Character(int positionX, int positionY, int dimensionX, int dimensionY) {
		super(positionX, positionY, dimensionX, dimensionY);
		this.isFalling = false;
		this.velX = 0;
		this.velY = 0;
	}

	/**
	 * ensures: the movement of the character is determined by their directions
	 */
	public abstract void checkDirection();
	
	/**
	 * ensures: the character moves to a new x and/or y position by setting the x and y positions to new x and y 
	 *     positions. It also calls the hit method to handle and check if the character's new position is off the 
	 *     screen
	 */
	public void move() {
		super.setPositionX(super.getPositionX()+velX);
		super.setPositionY(super.getPositionY()+velY);
		this.hit();
	}
	
	/**
	 * ensures: the character doesn't move off the screen. If it does, the character's position is set so that the 
	 *     character is touching the screen boundary
	 */
	public void hit() {
		if (super.getPositionX() > 555 - super.getWidth()){
			super.setPositionX(555 - super.getWidth());
		}
		if (super.getPositionX() < 0){
			super.setPositionX(0);
		}
		if (this.getPositionY() < 0) {
			super.setPositionY(0);
		}	
	}
	
	/**
	 * ensures: checks if the character is intersecting a drawable object and returns true if it is, false if it 
	 *     isn't
	 * @param drawable the object to check if the character is intersecting it
	 * @return true if the character is intersecting the object, false if the character isn't
	 */
	public boolean intersects(Drawable drawable) {
		Rectangle drawableRectangle = drawable.getRectangle();
		return drawableRectangle.intersects(new Rectangle(super.getPositionX(), super.getPositionY(), super.getWidth(), super.getHeight()));
	}
	
	/**
	 * ensures: checks if the character is falling and returns true if the character is and false if the character 
	 *     isn't
	 * @param standables the list of objects that the character could stand on
	 * @return true if the character is falling, false if the character isn't falling
	 */
	
	public boolean checkIfFalling(ArrayList<Standable> standables) {
		if (this.getIsFlying()==true) {
			this.isFalling=false;
			return false;
		}
		for (Drawable s : standables) {
			if (this.isOnPlatform(s)) {
				this.isFalling = false;
				return isFalling;
			}
			else {
				this.isFalling = true;
			}
		}
		return isFalling;
	}
	
	/**
	 * ensures: checks if the character is flying and returns true if the character is and false if the character 
	 *     isn't
	 * @return true if the character is flying, false if the character isn't flying
	 */
	public abstract boolean getIsFlying();
	
	/**
	 * ensures: checks if the character is standing on the platform, s
	 * @param s the platform used to check if the character is standing on it
	 * @return true if the character is standing on the platform, false if the character isn't standing on the 
	 *     platform
	 */
	public boolean isOnPlatform(Drawable s) {
		//Check y values
		if (((super.getPositionY() + super.getHeight()) < (s.getPositionY() + s.getHeight())) && ((super.getPositionY() + super.getHeight()) >= (s.getPositionY() -4))) {
			//check x values
			if ((super.getPositionX() >= s.getPositionX() - super.getWidth()) && ((super.getPositionX() + super.getWidth()) < (s.getPositionX() + s.getWidth() + super.getWidth()))) {
				super.setPositionY(s.getPositionY() - super.getHeight());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ensures: the character's y position is updated to a lower y position based on the fall speed if the 
	 *     character is falling
	 */
	public void updateFalling() {
		if (isFalling) {
			super.setPositionY(super.getPositionY() + FALL_SPEED);
		}
	}
	
	/**
	 * ensures: the character's image is drawn on the screen
	 */
	public abstract void drawOn(Graphics2D g2);
	
	/**
	 * ensures: the character's x velocity is returned
	 * @return the character's x velocity
	 */
	public int getVelX() {
		return this.velX;
	}
	
	/**
	 * ensures: the character's y velocity is returned
	 * @return the character's y velocity
	 */
	public int getVelY() {
		return this.velY;
	}
	
	/**
	 * ensures: true is returned if the character is falling and false is returned if the character isn't falling
	 * @return true if the character is falling, false if the character isn't falling
	 */
	public boolean getIsFalling() {
		return this.isFalling;
	}
	
	/**
	 * ensures: the character's x velocity is set to the new x velocity
	 * @param velX the new x velocity
	 */
	public void setVelX(int velX) {
		this.velX=velX;
	}
	
	/**
	 * ensures: the character's y velocity is set to the new y velocity
	 * @param velY the new y velocity
	 */
	public void setVelY(int velY) {
		this.velY=velY;
	}
}