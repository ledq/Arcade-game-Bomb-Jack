package mainApp;

import java.awt.Rectangle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;

/**
 * Class: BasicGameComponent
 * @author A_408
 * <br>Purpose: Draw the score board and number of lives
 * <br>For example:
 * <pre>
 *    BasicGameComponent exampleBasicGameComponent = new BasicGameComponent(hero);
 * </pre>
 */
public class BasicGameComponent extends Standable {
	private static final int LIFE_DIMENSION = 30;
	private Hero hero;
	
	/**
	 * ensures: initializes the hero to hero and calls the Drawable constructor with the dimensions and
	 *     position of the basic game component
	 * @param hero used to initialize the hero
	 */
	public BasicGameComponent(Hero hero) {
		super(0, 540, 555, 2);
		this.hero = hero;
	}
	
	/**
	 * ensures: the hero's lives are drawn on the screen
	 * @param g2
	 */
	private void drawLives(Graphics2D g2) {
		for (int i = 0; i < hero.getNumLives(); i++) {
			g2.setColor(hero.getColor());
			Rectangle life = new Rectangle(502 - i * 39, 550, LIFE_DIMENSION, LIFE_DIMENSION);
			g2.draw(life);
			g2.fill(life);
		}
	}
	
	/**
	 * ensures: the score board is drawn on the screen with the hero's current score
	 * @param g2
	 */
	private void drawScoreboard(Graphics2D g2) {
		Font scoreboardFont = new Font(Font.MONOSPACED, Font.PLAIN, 30);
		g2.setFont(scoreboardFont);
		g2.drawString("Score: " + hero.getScore(), 10, 575);
	}

	/**
	 * ensures: the methods to draw the line to separate the game and basic game component on the screen and 
	 *     calls the methods to draw the lives and score board
	 * @param g2
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		Rectangle gameBorder = new Rectangle(0, 540, 555, 2);
		g2.draw(gameBorder);
		g2.fill(gameBorder);
		drawLives(g2);
		drawScoreboard(g2);
	}

	/**
	 * ensures: does nothing since the isOnPlatform method in the Character class makes sure the character 
	 *     stands on the platform
	 */
	@Override
	public void collideWith(Hero hero) {
		//do nothing
	}

	/**
	 * ensures: does nothing since the isOnPlatform method in the Character class makes sure the character 
	 *     stands on the platform
	 */
	@Override
	public void collideWithEnemy(Enemy enemy) {
		//do nothing
	}

	/**
	 * ensures: does nothing since there is no special image for the basic game component
	 */
	@Override
	public void getImage() {
		//do nothing
	}

	/**
	 * ensures: the rectangle of the location of the game boundary are returned
	 */
	@Override
	public Rectangle getRectangle() {
		return new Rectangle(502, 550, 1, 1);
	}
}