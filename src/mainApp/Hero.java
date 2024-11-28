package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class: Hero
 * @author A_408
 * <br>Purpose: Moves the hero, changes the hero's picture when the hero moves in different directions, and keeps 
 *     track of the hero's lives and score
 * <br>For example:
 * <pre>
 * 	  Hero exampleHero = new Hero();
 * </pre>
 */
public class Hero extends Character {
	private static final int DEFAULT_X = 100;
	private static final int DEFAULT_Y = 100;
	private static final int STARTING_NUM_LIVES = 3;
	private static final Color HERO_COLOR = Color.BLACK;
	private static final int HERO_DIMENSION = 40;
	private static final int COLLIDING_WIDTH=33;
	private static final int MOVE_SPEED = 1;
	private static final int FLY_SPEED = 1;
	
	private int startingPositionX;
	private int startingPositionY;
	private int numLives;
	private int score;
	//direction
	private boolean isLeft;
	private boolean isRight;
	private boolean isFlying;
	// image
	private BufferedImage leftStandUp,leftStandDown,leftFly,leftWalk,rightStandUp,
	rightStandDown,rightFly,rightWalk,leftFlyUp,rightFlyUp;
	private String direction;
	private String status;
	
	/**
	 * ensures: calls the super class's constructor to set the x and y positions to the default x and y positions 
	 *     and the x and y dimensions of the hero, initializes the starting x and y positions to the default x and 
	 *     y positions, initializes the number of lives to the starting number of lives, initializes the score to 
	 *     0, initializes isLeft, isRight, and isFlying to false, initializes direction to "right," and initializes 
	 *     status to stand.
	 */
	public Hero() {
		super(DEFAULT_X, DEFAULT_Y, HERO_DIMENSION, HERO_DIMENSION);
		startingPositionX = DEFAULT_X;
		startingPositionY = DEFAULT_Y;
		this.numLives = STARTING_NUM_LIVES;
		this.score = 0;
		isLeft = false;
		isRight= false;
		isFlying = false;
		direction = "right";
		status = "stand";	
	}
	
	/**
	 * ensures: the hero's starting position and current position are set to posX and posY
	 * @param posX the hero's starting x position
	 * @param posY the hero's starting y position
	 */
	public void setStartingPos(int posX, int posY) {
		super.setPositionX(posX - HERO_DIMENSION / 2);
		super.setPositionY(posY - HERO_DIMENSION / 2);
		startingPositionX = posX;
		startingPositionY = posY;
	}
	
	/**
	 * ensures: does nothing because the hero shouldn't do anything when it collides with itself
	 */
	@Override
	public void collideWith(Hero hero) {
		//do nothing
	}
	
	/**
	 * ensures: checks and sets the direction so that the right movement image for the hero is drawn on the screen. 
	 * It also makes the hero's movement smoother
	 */
	@Override
	public void checkDirection() {
		if (isLeft==true&&isRight==false) {
			direction = "left";
			status = "walk";
			super.setVelX(-MOVE_SPEED);
		}
		else if (isRight==true&&isLeft==false){
			direction = "right";
			status = "walk";
			super.setVelX(MOVE_SPEED); 
			
		}
		else {
			super.setVelX(0);
			status = "stand";
		}
		if (isFlying==true) {
			status = "fly";
			super.setVelY(-FLY_SPEED);
		}
		else {
			super.setVelY(0);
			if (super.getIsFalling()==true) {
				status="fall";
			}
		}                        
	}
	
	/**
	 * ensures: the direction is checked to set the information needed to find the hero's movement picture and 
	 * moves the hero
	 */
	@Override
	public void move() {
		this.checkDirection();
		super.move();	
	}
	
	/**
	 * ensures: the number of points, pointsToAdd, is added to the score
	 * @param pointsToAdd the number of points to add
	 */
	public void addPoints(int pointsToAdd) {
		score += pointsToAdd;
	}
	
	/**
	 * ensures: a life is removed from the hero
	 */
	public void removeLife() {
		numLives--;
	}
	
	/**
	 * ensures: the hero is moved back to its starting position
	 */
	public void resetPosition() {
		super.setPositionX(startingPositionX - HERO_DIMENSION / 2);
		super.setPositionY(startingPositionY - HERO_DIMENSION / 2);
	}

	/**
	 * ensures: checks if the hero lost (the hero doesn't have any lives left)
	 * @return
	 */
	public boolean checkIfLost() {
		if (this.numLives == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * ensures: resets the game by giving the hero all its lives back, setting the score to 0, and setting 
	 * isFlying to false
	 */
	public void resetGame() {
		this.numLives = STARTING_NUM_LIVES;
		this.score = 0;
		isFlying = false;
	}

// Calling in MainApp:
//********************	
	/**
	 * ensures: isLeft is set to true if the hero is moving left and false if the hero is moving right
	 * @param ans if the hero is moving left or not
	 */
	public void moveLeft(boolean ans) {
		this.isLeft=ans;
	}
	
	/**
	 * ensures: isRight is set to true if the hero is moving right and false if the hero is moving left
	 * @param ans if the hero is moving right or not
	 */
	public void moveRight(boolean ans) {
		this.isRight=ans;
	}

	/**
	 * ensures: isFlying is set to true if the hero is flying, the up arrow is pressed, and false if the hero is not flying
	 * @param ans if the hero is flying or not
	 */
	public void fly(boolean ans) {
		this.isFlying=ans;
	}
//**********************	

	/**
	 * ensures: the image for the hero depending on how it is moving is drawn on the screen
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		BufferedImage currentImage = null;
		switch(direction+status) {
		case("right"+"stand"):
			if (getImageSwitch()==true) {
				currentImage=rightStandUp;
			}
			else {
			currentImage = rightStandDown;
			}
			break;
		case("right"+"walk"):
			if(getImageSwitch()==true) {
				currentImage=rightStandDown;
			}
			else {
			currentImage= rightWalk;
			}
			break;
		case("right"+"fly"):
			if (getImageSwitch()==true) {
				currentImage=rightFly;
			}
			else {
				currentImage = rightFlyUp;
			}
			break;
		case("right"+"fall"):
			currentImage=rightFlyUp;
			break;
			
		case("left"+"stand"):
			if(getImageSwitch()==true) {
				currentImage=leftStandUp;
			}
			else {
			currentImage = leftStandDown;
			}
			break;
		case("left"+"walk"):
			if(getImageSwitch()==true) {
				currentImage=leftStandDown;				
			}
			else {
			currentImage= leftWalk;
			}
			break;
		case("left"+"fly"):
			if (getImageSwitch()==true) {
				currentImage=leftFly;
			}
			else {
				currentImage=leftFlyUp;
			}
			break;
		case("left"+"fall"):
			currentImage=leftFlyUp;
			break;
		}
		this.timeCounter(10);
		g2.drawImage(currentImage, super.getPositionX(), super.getPositionY(),super.getWidth(),super.getHeight(), null);
	}
	
	/**
	 * ensures: the hero's movement images are found and stored in the correct instance variables
	 */
	@Override
	public void getImage() {
		try {
			//left
			leftStandDown = ImageIO.read(getClass().getResourceAsStream("/hero/hero_left_stand_down.png"));
			leftStandUp = ImageIO.read(getClass().getResourceAsStream("/hero/hero_left_stand_up.png"));
			leftFly = ImageIO.read(getClass().getResourceAsStream("/hero/hero_left_fly.png"));
			leftFlyUp = ImageIO.read(getClass().getResourceAsStream("/hero/hero_left_fly_up.png"));
			leftWalk = ImageIO.read(getClass().getResourceAsStream("/hero/hero_left_walk.png"));
			//right
			rightStandDown = ImageIO.read(getClass().getResourceAsStream("/hero/hero_right_stand_down.png"));
			rightStandUp = ImageIO.read(getClass().getResourceAsStream("/hero/hero_right_stand_up.png"));
			rightFly = ImageIO.read(getClass().getResourceAsStream("/hero/hero_right_fly.png"));
			rightFlyUp = ImageIO.read(getClass().getResourceAsStream("/hero/hero_right_fly_up.png"));
			rightWalk = ImageIO.read(getClass().getResourceAsStream("/hero/hero_right_walk.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ensures: the hero's color is returned
	 * @return the hero's color
	 */
	public Color getColor() {
		return HERO_COLOR;
	}
	
	/**
	 * ensures: the hero's colliding width is returned
	 * @return the hero's colliding width
	 */
	public int getCollidingWidth() {
		return COLLIDING_WIDTH;
	}
	
	/**
	 * ensures: the number of lives that the hero has is returned
	 * @return the number of lives that the hero has
	 */
	public int getNumLives() {
		return numLives;
	}
	
	/**
	 * ensures: the hero's score is returned
	 * @return the hero's score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * ensures: returns true if the hero is flying and false if the hero is not flying
	 * @return true if the hero is flying and false if the hero is not flying
	 */
	public boolean getIsFlying() {
		return isFlying;
	}
}