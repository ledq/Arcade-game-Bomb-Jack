package mainApp;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Class: EnemyThree
 * @author A_408
 * <br>Purpose: Used to handle EnemyThree's invisible ability feature, like fading and disappearing in certain time period and storing images 
 * <br>For example:
 * <pre>
 *    EnemyThree exampleEnemyThree = new EnemyThree(10, 10, hero);
 * </pre>
 */
public class EnemyThree extends EnemyTwo {
	private static final int ENEMY_THREE_MOVE_SPEED = 1;
	private static final int ENEMY_THREE_FLY_SPEED = 1;
	
	private int timer;
	private int imageCode;
	private boolean disappear;
	private ArrayList<BufferedImage> imageSeriesLeft;
	private ArrayList<BufferedImage> imageSeriesRight;
	
	/**
	 * ensures: the super class is called to set the x and y positions and x and y dimensions, the move left and 
	 *     move right images are found, and this.disappear is initialized to false
	 * @param positionX the x position of the center of the enemy
	 * @param positionY the y position of the center of the enemy
	 * @param hero the hero
	 */

	public EnemyThree(int positionX, int positionY, Hero hero) {
		super(positionX, positionY, hero, ENEMY_THREE_MOVE_SPEED, ENEMY_THREE_FLY_SPEED);
		super.getImage("/monster/monster3_left.png","/monster/monster3_right.png");
		this.disappear = false;
	}
	
	/**
	 * ensures: the image for the enemy depending on how it is moving is drawn on the screen, and call the method to
	 *  set the appearing, fading, and disappearing time
	 * @param g2
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		this.fading(200,140,10);
		if (disappear==false) {
			super.drawOn(g2);
		}
		else {
			if (imageCode<=3&&imageCode>=0) {
				BufferedImage currentImage = null;
				if (super.getImageSwitch()==true) {
					currentImage = imageSeriesRight.get(imageCode);
				}
				else {
					currentImage = imageSeriesLeft.get(imageCode);
				}
				g2.drawImage(currentImage, super.getPositionX(),super.getPositionY() , super.getWidth(),super.getHeight(), null);
			}		
		}
	}
	
	/**
	 * ensures: set the timer in which the enemy is visible, invisible, and fading
	 * @param visibleTime the time length the image of the enemy is displayed
	 * @param invisibleTime the time length the image of the enemy is not displayed
	 * @param unitFadingTime the unit time for each fading image being displayed
	 */
	public void fading(int visibleTime, int invisibleTime, int unitFadingTime) {
		timer++;
		
		int timePoint2 = visibleTime+invisibleTime+unitFadingTime*4;
		int timePoint3 = visibleTime+invisibleTime+unitFadingTime*8;
		if (timer>visibleTime) {
			if (disappear==false) {
				disappear=true;
			}
			else {
				if (timer>visibleTime+unitFadingTime*(imageCode+1)&&(timer<timePoint2)&&imageCode<=3) {
					imageCode++;
				}
				if (timer>visibleTime+(invisibleTime+unitFadingTime*4)+(4-imageCode)*unitFadingTime&&timer<timePoint3&&imageCode>=0) {
					imageCode--;
				}	
				if(timer>visibleTime+(invisibleTime+unitFadingTime*8)) {
						disappear=false;
						timer=0;
						imageCode=0;
				}
			}
		}
	}
	
	/**
	 * ensures: the fading images are found and stored in the imageSeriesLeft and imageSeriesRight ArrayList 
	 */
	@Override
	public void getImage() {
		this.imageSeriesLeft = new ArrayList<BufferedImage>();
		this.imageSeriesRight = new ArrayList<BufferedImage>();
		try {
			for (int k=0;k<4;k++) {
				this.imageSeriesLeft.add(ImageIO.read(getClass().getResourceAsStream("/monster/monster3_left_"+(k+1)+".png")) );
				this.imageSeriesRight.add(ImageIO.read(getClass().getResourceAsStream("/monster/monster3_right_"+(k+1)+".png")));
			}

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
