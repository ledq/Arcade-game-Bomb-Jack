package mainApp;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Class: Enemy
 * @author A_408
 * <br>Purpose: Handles the collision when the hero collides with it, handles it's movement, stores its images, 
 *     and stores its movement speeds
 */
public class Enemy extends Character {
	public static final int ENEMY_DIMENSION = 40;
	
	private int speedX;
	private int speedY;
	private int maxSpeedX;
	private int maxSpeedY;
	private boolean isFlying;
	private BufferedImage right;
	private BufferedImage left;
	
	/**
	 * ensures: the super class's constructor is called to set the x  and y positions and x and y dimensions, 
	 *     initializes the x and y speeds to 0, initializes the max x speed to maxSpeedX, initializes the max y 
	 *     speed to maxSpeedY, and initializes isFlying to false
	 * @param positionX the x position of the top left corner
	 * @param positionY the y position of the top left corner
	 * @param dimensionX the x dimension
	 * @param dimensionY the y dimension
	 * @param maxSpeedX the maximum x speed
	 * @param maxSpeedY the maximum y speed
	 */
	public Enemy(int positionX, int positionY, int dimensionX, int dimensionY, int maxSpeedX, int maxSpeedY) {
		super(positionX, positionY, dimensionX, dimensionY);
		this.speedX = 0;
		this.speedY = 0;
		this.maxSpeedX = maxSpeedX;
		this.maxSpeedY = maxSpeedY;
		this.isFlying = false;
	}
	
	/**
	 * ensures: if the hero is higher than the enemy, the enemy is marked to remove, and the hero receives 20 
	 *     points. If the hero is lower than the enemy, the hero loses a life and the hero moves back to its 
	 *     starting position
	 * @param hero the hero
	 */
	@Override
	public void collideWith(Hero hero) {
		if (hero.getPositionY() + hero.getHeight() - 5 < this.getPositionY()) {
			this.markToRemove();
			hero.addPoints(20);
		}
		else {
			hero.removeLife();
			hero.resetPosition();
		}
	}
	
	/**
	 * ensures: the images are switched when the direction is changed  
	 */
	@Override
	public void checkDirection() {
		if(this.getVelX()>0) {
			super.setImageSwitch(true);
		}
		else if(this.getVelX()<0) {
			super.setImageSwitch(false);
		}
	}
	
	/**
	 * ensures: a new random x and y speed is chosen, and the the super class's move method is called to change 
	 *     the enemy's position
	 */
	@Override
	public void move() {
		this.setSpeedX();
		this.setSpeedY();
		super.move();
	}
	
	/**
	 * ensure: set the isFlying to true when the enemy goes up (getVelY()<0) and false if that is not the case and return isFlying 
	 */
	@Override
	public boolean getIsFlying() {
		if (super.getVelY()<0) {
			isFlying=true;
		}
		else {
			isFlying=false;
		}
		return isFlying;
	}
	
	/**
	 * ensures: the image for the enemy depending on how it is moving is drawn on the screen
	 * @param g2
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		this.checkDirection();
		BufferedImage currentImage =null;
		if (super.getImageSwitch()==false) {
			currentImage = this.left;
		}
		else{
			currentImage = this.right;
		}
		g2.drawImage(currentImage,super.getPositionX(),super.getPositionY(), ENEMY_DIMENSION, ENEMY_DIMENSION, null);	
	}
	
	/**
	 * ensures: does nothing because there is a different getImage() method for the enemy
	 */
	@Override
	public void getImage() {
		//do nothing
	}
	
	/**
	 * ensures: the move left and move right images for the enemy are found
	 * @param leftImageName the name of the left image
	 * @param rightImageName the name of the right image
	 */
	public void getImage(String leftImageName, String rightImageName) {
		try {
			left = ImageIO.read(getClass().getResourceAsStream(leftImageName));
			right = ImageIO.read(getClass().getResourceAsStream(rightImageName));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ensures: the x direction speed is returned
	 * @return the x direction speed
	 */
	public int getSpeedX() {
		return speedX;
	}
	
	/**
	 * ensures: the y direction speed is returned
	 * @return the y direction speed
	 */
	public int getSpeedY() {
		return speedY;
	}
	
	/**
	 * ensures: the max speed is returned
	 * @return the max speed
	 */
	public int getMaxSpeed() {
		return this.maxSpeedX;
	}
	
	/**
	 * ensures: the move left image is returned
	 * @return the move left image
	 */
	public BufferedImage getLeftImage() {
		return left;
	}
	
	/**
	 * ensures: the move right image is returned
	 * @return the move right image
	 */
	public BufferedImage getRightImage() {
		return right;
	}
	
	/**
	 * ensures: a new random x direction speed is found, and the x direction speed is set to that new x 
	 *     direction speed
	 */
	public void setSpeedX() {
		Random r = new Random();		
		speedX=r.nextInt(this.maxSpeedX)+1;
	}
	
	/**
	 * ensures: a new random y direction speed is found, and the y direction speed is set to that new y 
	 *     direction speed
	 */
	public void setSpeedY() {
		Random r = new Random();
		speedY=r.nextInt(this.maxSpeedY)+1;	
	}
}
