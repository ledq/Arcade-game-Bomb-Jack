package mainApp;

/**
 * Class: EnemyTwo
 * @author A_408
 * <br>Purpose: Used to handle EnemyTwo's movement, like tracking the hero and falling
 * <br>For example:
 * <pre>
 *    EnemyTwo exampleEnemyTwo = new EnemyTwo(10, 10, hero);
 * </pre>
 */
public class EnemyTwo extends Enemy {
	private static final int ENEMY_DIMENSION = 40;
	private static final int ENEMY_TWO_MOVE_SPEED = 2;
	private static final int ENEMY_TWO_FLY_SPEED = 2;
	
	private Hero hero;

	/**
	 * ensures: the super class is called to set the x and y positions and x and y dimensions, x and y max speeds are set to the enemy two x and y max speeds. Also, the move left and move right images are found, and the hero is initialized to hero.
	 * @param positionX the x position of the center of the enemy
	 * @param positionY the y position of the center of the enemy
	 * @param hero the hero
	 */
	public EnemyTwo(int positionX, int positionY, Hero hero) {
		super(positionX - ENEMY_DIMENSION / 2, positionY - ENEMY_DIMENSION / 2, ENEMY_DIMENSION, ENEMY_DIMENSION, ENEMY_TWO_MOVE_SPEED, ENEMY_TWO_FLY_SPEED);
		super.getImage("/monster/monster2_left.png", "/monster/monster2_right.png");
		this.hero = hero;
	}
	
	/**
	 * ensures: the super class is called to set the x and y positions and x and y dimensions, x and y max speeds are set to maxSpeedX and maxSpeedY. Also, the move left and move right images are found, and the hero is initialized to hero.
	 * @param positionX the x position of the center of the enemy
	 * @param positionY the y position of the center of the enemy
	 * @param hero the hero
	 * @param maxSpeedX the max x speed
	 * @param maxSpeedY the max y speed
	 */
	public EnemyTwo(int positionX, int positionY, Hero hero, int maxSpeedX, int maxSpeedY) {
		super(positionX - ENEMY_DIMENSION / 2, positionY - ENEMY_DIMENSION / 2, ENEMY_DIMENSION, ENEMY_DIMENSION, maxSpeedX, maxSpeedY);
		super.getImage("/monster/monster2_left.png", "/monster/monster2_right.png");
		this.hero = hero;
	}
	
	/**
	 * ensures: the enemy moves towards the hero
	 */
	@Override
	public void move() {
		this.tracker();
		super.move();
	}
	
	/**
	 * ensures: the x and y velocities of the enemy are set so that the enemy moves toward the hero
	 */
	public void tracker() {
		int heroXPosition = hero.getPositionX();
		if (heroXPosition > super.getPositionX()+5) {
			super.setVelX(super.getSpeedX());
		}
		else if (heroXPosition < super.getPositionX()-5) {
			super.setVelX(-super.getSpeedX());
		}
		else {
			super.setVelX(0);
		}
		
		int heroYPosition = hero.getPositionY();
		if (heroYPosition > super.getPositionY()+8) {
			super.setVelY(0);
		}
		else if (heroYPosition < super.getPositionY()-8) {
			super.setVelY(-super.getSpeedY());
		}
	}
	
	/**
	 * ensures: checks if the enemy is standing on the platform, s
	 * @param s the platform used to check if the character is standing on it
	 * @return true if the character is standing on the platform, false if the character isn't standing on the 
	 *     platform
	 */
	@Override
	public boolean isOnPlatform(Drawable s) {
		//Check y values
		if (((super.getPositionY() + super.getHeight()) < (s.getPositionY() + s.getHeight())) && ((super.getPositionY() + super.getHeight()) >= (s.getPositionY() - 4))) {
			//check x values
			if ((super.getPositionX() >= s.getPositionX() - super.getWidth()) && ((super.getPositionX() + super.getWidth()) < (s.getPositionX() + s.getWidth() + super.getWidth()))) {
				super.setPositionY(s.getPositionY() - super.getHeight());
				return true;
			}
		}
		return false;
	}
}
