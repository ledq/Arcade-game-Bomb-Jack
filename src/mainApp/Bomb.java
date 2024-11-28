package mainApp;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class: Bomb
 * @author A_408
 * <br>Purpose: Draw the image of the bomb on the screen and mark the bomb to remove when the hero collides
 *         with it
 * <br>For example:
 * <pre>
 *    Bomb exampleBomb = new Bomb(10, 10);
 * </pre>
 */
public class Bomb extends Drawable {
	private static final int BOMB_WIDTH = 31;
	private static final int BOMB_HEIGHT = 38;
	
	private BufferedImage bomb1;
	private BufferedImage bomb2;
	
	/**
	 * ensures: the constructor in the Drawable class is called to keep track of the bomb's position and
	 *     dimensions
	 * @param positionX the x position of the center of the bomb
	 * @param positionY the y position of the center of the bomb
	 */
	public Bomb(int positionX, int positionY) {
		super(positionX - BOMB_WIDTH / 2, positionY - BOMB_HEIGHT / 2, BOMB_WIDTH, BOMB_HEIGHT);
	}
	
	/**
	 * ensures: when a hero collides with the bomb, the bomb is marked to remove and 10 points are added to the 
	 *     hero's score
	 */
	@Override
	public void collideWith(Hero hero) {
		this.markToRemove();
		hero.addPoints(10);
	}
	
	/**
	 * ensures: the images for the bomb are found
	 */
	@Override
	public void getImage() {
		try {
			bomb1 = ImageIO.read(getClass().getResourceAsStream("/bomb/bomb1.png"));
			bomb2 = ImageIO.read(getClass().getResourceAsStream("/bomb/bom2.png"));	
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ensures: the image for the bomb is drawn on the screen, and the fuses move
	 * @param g2
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		BufferedImage currentImage = null;
		
		if (super.getImageSwitch()==true) {
			currentImage = bomb1;
		}
		else {
			currentImage = bomb2;
		}
		this.timeCounter(10);
		
		g2.drawImage(currentImage,super.getPositionX(),super.getPositionY(), BOMB_WIDTH, BOMB_HEIGHT,  null);
	}
}