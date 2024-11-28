package mainApp;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class: Platform
 * @author A_408
 * <br>Purpose: Draws the platform on the screen
 * <br>For example:
 * <pre>
 *    Platform examplePlatform = new Platform(10, 10, 10);
 * </pre>
 */
public class Platform extends Standable {
	private static final int PLATFORM_HEIGHT = 10;
	
	private BufferedImage image;
	
	/**
	 * ensures: calls the super method to keep track of the x position, y position, width, and height
	 * @param positionX the x position of the platform
	 * @param positionY the y position of the platform
	 * @param width the width of the platform
	 */
	public Platform(int positionX, int positionY, int width) {
		super(positionX, positionY, width, PLATFORM_HEIGHT);
	}
	
	/**
	 * ensures: draws the image of the platform on the screen
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		g2.drawImage(image, super.getPositionX(), super.getPositionY(), super.getWidth(), PLATFORM_HEIGHT, null);
	}

	/**
	 * ensures: the image for the platform is found
	 */
	@Override
	public void getImage() {
		try {
			this.image =  ImageIO.read(getClass().getResourceAsStream("/platform/platform.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
