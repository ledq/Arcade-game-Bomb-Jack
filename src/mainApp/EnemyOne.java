package mainApp;

/**
 * Class: EnemyOne
 * @author A_408
 * <br>Purpose: Used to handle EnemyOne's movement, like moving horizontally and not going off platforms or the 
 *         screen
 * <br>For example:
 * <pre>
 *    EnemyOne exampleEnemyOne = new EnemyOne(10, 10);
 * </pre>
 */
public class EnemyOne extends Enemy {
	private static final int MAX_SPEED = 4;
	
	private boolean isHit;

	/**
	 * ensures: the super class is called to set the x and y positions and x and y dimensions, the move left and 
	 *     move right images are found, and isHit is initialized to false
	 * @param positionX the x position of the center of the enemy
	 * @param positionY the y position of the center of the enemy
	 */
	public EnemyOne(int positionX, int positionY) {
		super(positionX - ENEMY_DIMENSION / 2, positionY - ENEMY_DIMENSION / 2, ENEMY_DIMENSION, ENEMY_DIMENSION, MAX_SPEED, MAX_SPEED);
		super.getImage("/monster/monster1_left.png", "/monster/monster1_right.png");
		isHit=false;
	}
	
	/**
	 * ensures: if isHit is true, the enemy is moving left, so the enemy's velocity is set to the negative speed. 
	 *     If isHit is false, the enemy is moving right, so the enemy's velocity is set to the speed. Then, the 
	 *     super method is called and updates the enemy's position based on the enemy's velocity
	 */
	@Override
	public void move() {
		if(this.isHit) {
			super.setVelX(-super.getSpeedX());
		}
		else {
			super.setVelX(super.getSpeedX());
		}
		
		super.move();
	}
	
	/**
	 * ensures: if the enemy hits the right border, isHit is set to true, so the enemy will move left. If the 
	 *     enemy hits the left border, isHit is set to false, so the enemy will move right
	 */
	@Override
	public void hit() {		
		if (super.getPositionX()>555-super.getWidth()){
			super.setPositionX(555-super.getWidth());
			isHit = true;
		}
		if (super.getPositionX()<0){
			super.setPositionX(0);
			isHit = false;
		}
	}
	
	/**
	 * ensures: if the enemy is about to fall off the right side of the standable object s, isHit is set to true, 
	 *     so the enemy will move left. If the enemy is about to fall off the left side of the standable object s, 
	 *     isHit is set to false, so the enemy will move right.
	 * @param s the standable object that the enemy is on
	 */
	private void hit(Drawable s) {
		if (super.getPositionX()+super.getWidth()>s.getPositionX()+s.getWidth()) {
			super.setPositionX(s.getPositionX() + s.getWidth() - super.getWidth());
			isHit = true;
		}
		if (super.getPositionX()<s.getPositionX()) {
			super.setPositionX(s.getPositionX());
			isHit = false;
		}
	}
	
	/**
	 * ensures: checks if the enemy is standing on the platform, s. If it is, isHit(s) is called to check if 
	 *     the enemy will fall off the platform
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
				this.hit(s);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ensures: the y direction speed isn't set because EnemyOne moves horizontally, not vertically
	 */
	@Override
	public void setSpeedY() {
		//do nothing since EnemyOne doesn't  move vertically
	}
}