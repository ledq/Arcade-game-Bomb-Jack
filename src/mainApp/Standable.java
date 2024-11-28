package mainApp;

/**
 * Class: Standable
 * @author A_408
 * <br>Purpose: An abstract class for all the objects that the characters can stand on to make sure they can't go through standable objects
 */
public abstract class Standable extends Drawable {
	/**
	 * ensures: the super constructor is called to set the x position, y position, x dimension, and y dimension
	 * @param positionX the x position of the top right corner
	 * @param positionY the y position of the top right corner
	 * @param dimensionX the x dimension
	 * @param dimensionY the y dimension
	 */
	public Standable(int positionX, int positionY, int dimensionX, int dimensionY) {
		super(positionX, positionY, dimensionX, dimensionY);
	}
	
	/**
	 * ensures: handles collisions with the hero
	 */
	@Override
	public void collideWith(Hero hero) {
		this.collideWithCharacter(hero);
	}
	
	/**
	 * ensures: handles collisions with the enemy
	 * @param enemy
	 */
	public void collideWithEnemy(Enemy enemy) {
		this.collideWithCharacter(enemy);
	}
	
	/**
	 * ensures: the character can't move into the standable object from the bottom, left, or right
	 * @param character the character that collided with the platform
	 */
	public void collideWithCharacter(Character character) {
		if (character.getPositionY() > this.getPositionY() && character.getPositionY() < (this.getPositionY() + this.getHeight())) {
			character.setPositionY(this.getPositionY() + this.getHeight());
		}
		else if (character.getPositionX() < (this.getPositionX() + this.getWidth()) && (character.getPositionX()  > (this.getPositionX() ))) {
			character.setPositionX(this.getPositionX() + this.getWidth());
		}
		else if ((character.getPositionX()+character.getWidth()>this.getPositionX())&&(character.getPositionX()+character.getWidth()<this.getPositionX()+this.getWidth())){
			character.setPositionX(this.getPositionX() - character.getWidth());
		}
	}
}
