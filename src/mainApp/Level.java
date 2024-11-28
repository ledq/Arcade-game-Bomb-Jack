package mainApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Class: Level
 * @author A_408
 * <br>Purpose: Set up the level, update the positions of the characters, and remove bombs and enemies
 * <br>Restrictions: Can't have multiple heros and can only have three types of enemies
 * <br>For example:
 * <pre>
 *    Level exampleLevel = new Level(1, hero, mainApp);
 * </pre>
 */
public class Level extends JComponent {
	private int levelNumber;
	private MainApp mainApp;
	private ArrayList<Standable> standables;
	private ArrayList<Drawable> objectsToRemove;
	private ArrayList<Drawable> stationaryObjects;
	private ArrayList<Enemy> enemies;
	private Hero hero;
	private BufferedImage backgroundImage;
	
	/**
	 * ensures: initializes the level number to levelNumber, initializes the main app to mainApp, initializes standables to a new ArrayList of Standables, initializes objectsToRemove to a new ArrayList of Drawables, initializes stationaryObjects to a new ArrayList of Drawables, initializes enemies to a new ArrayList of enemies, initializes the hero to hero, and finds the background image
	 * @param levelNumber the level's number
	 * @param hero the hero
	 * @param mainApp the mainApp
	 */
	public Level(int levelNumber, Hero hero, MainApp mainApp) {
		this.levelNumber = levelNumber;
		this.mainApp = mainApp;
		standables = new ArrayList<Standable>();
		objectsToRemove = new ArrayList<Drawable>();
		stationaryObjects = new ArrayList<Drawable>();
		enemies = new ArrayList<Enemy>();
		this.hero = hero;
		this.getImage();
	}
	
	/**
	 * ensures: objects are created and added to the ArrayLists based on information from the text file
	 */
	public void setUpGame() {
		BasicGameComponent livesAndScoreboard = new BasicGameComponent(hero);
		stationaryObjects.add(livesAndScoreboard);
		standables.add(livesAndScoreboard);
		
		String levelFileName = "levels/level" + levelNumber + ".txt";
		Scanner scanner = null;
		File levelFile = new File(levelFileName);
		
		try {
			scanner = new Scanner(levelFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		int lineNumber = 0;
		while (scanner.hasNext()) {
			char[] levelLine = scanner.nextLine().toCharArray();
			for (int i = 0; i < levelLine.length; i++) {
				if (levelLine[i] == 'b') {
					Bomb bomb = new Bomb(i * 60 + 30, lineNumber * 60 + 30);
					stationaryObjects.add(bomb);
				}
				if (levelLine[i] == 'B' || levelLine[i] == 'p') {
					int positionX = i * 60;
					int positionY = lineNumber * 60;
					int width = 0;
					while (i < levelLine.length && (levelLine[i] == 'p' || levelLine[i] == 'B')) {
						width += 60;
						if (levelLine[i] == 'B') {
							Bomb bomb = new Bomb(i * 60 + 30, lineNumber * 60 + 30);
							stationaryObjects.add(bomb);
						}
						i++;
					}
					i--;
					Platform platform = new Platform(positionX, positionY, width);
					standables.add(platform);
					stationaryObjects.add(platform);
				}
				else if (levelLine[i] == 'e') {
					EnemyOne enemy1 = new EnemyOne(i * 60 + 30, lineNumber * 60 + 40);
					enemies.add(enemy1);
				}
				else if (levelLine[i] == 'E') {
					EnemyTwo enemy2 = new EnemyTwo(i * 60 + 30, lineNumber * 60 + 40, hero);
					enemies.add(enemy2);
				}
				else if (levelLine[i] == 'f') {
					EnemyThree enemy3 = new EnemyThree(i * 60 + 30, lineNumber * 60 + 40, hero);
					enemies.add(enemy3);
				}
				else if (levelLine[i] == 'h') {
					hero.setStartingPos(i * 60 + 30, lineNumber * 60 + 40); // center position of the hero
					stationaryObjects.add(hero);
				}
			}
			lineNumber++;
		}
		scanner.close();
	}
	
	/**
	 * ensures: the characters move and the hero collects bombs and kills enemies whenever the timer advances one tick. It also checks if the hero won or lost the game
	 */
	public void update() {
		for (Drawable drawable : getObjectsToDraw()) {
			if (hero.intersects(drawable)) {
				handleCollision(drawable);
			}
		}
		for (Character character : getCharacters()) {
			if (character.checkIfFalling(standables)) {
				character.updateFalling();
			}
			character.move();
		}
		for (Enemy enemy : enemies) {
			for (Standable standable : standables) {
				if (enemy.intersects(standable)) {
					standable.collideWithEnemy(enemy);	
				}
			}
		}
		if (hero.checkIfLost()) {
			System.out.println("Game Over\n"
					+ "Score: " + hero.getScore());
			mainApp.finishedGame();
		}
		if (checkIfWon()) {
			hero.addPoints(15);
			System.out.println("You Won\n"
					+ "Score: " + hero.getScore());
			mainApp.nextLevel();
		}
		hero.move();
		removeObjects();
		repaint();
	}
	
	/**
	 * ensures: an ArrayList with all the drawable objects in the game is returned
	 * @return an ArrayList with all the drawable objects in the game
	 */
	private ArrayList<Drawable> getObjectsToDraw() {
		ArrayList<Drawable> objectToDraw = new ArrayList<Drawable>();
		objectToDraw.addAll(stationaryObjects);
		objectToDraw.addAll(enemies);
		return objectToDraw;
	}
	
	/**
	 * ensures: an ArrayList with all the characters in the game is returned
	 * @return an ArrayList with all the characters in the game
	 */
	private ArrayList<Character> getCharacters() {
		ArrayList<Character> characters = new ArrayList<Character>();
		characters.addAll(enemies);
		characters.add(hero);
		return characters;
	}
	
	/**
	 * ensures: the method for what each object should do when the hero collides with it is called
	 * @param drawable the object that the hero collided with
	 */
	private void handleCollision(Drawable drawable) {
		drawable.collideWith(hero);
		repaint();
	}
	
	/**
	 * ensures: the objects that were marked to remove are removed
	 */
	private void removeObjects() {
		for (Drawable object : getObjectsToDraw()) {
			if (object.getRemove()) {
				objectsToRemove.add(object);
			}
		}
		for (Drawable objectToRemove : objectsToRemove) {
			if (stationaryObjects.contains(objectToRemove)) {
				stationaryObjects.remove(stationaryObjects.indexOf(objectToRemove));
			}
			else {
				enemies.remove(enemies.indexOf(objectToRemove));
			}
		}
		objectsToRemove.clear();
	}
	
	/**
	 * ensures: returns true if the hero collected all the bombs and false if the hero hasn't collected all the bombs
	 * @return true if the hero collected all the bombs and false if the hero didn't collect all the bombs yet
	 */
	private boolean checkIfWon() {
		if (stationaryObjects.size() - standables.size() == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * ensures: all the objects in the ArrayLists are removed
	 */
	public void clear() {
		standables.clear();
		enemies.clear();
		stationaryObjects.clear();
		this.repaint();
	}
	
	/**
	 * ensures: all the objects are drawn on the screen
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(backgroundImage, 0, 0, mainApp.getFrameWidth(), mainApp.getFrameHeight(),null);
		
		for (Drawable objectT : this.getObjectsToDraw()) {
			objectT.drawOn(g2);
		}
	}
	
	/**
	 * ensures: the background image is found
	 */
	public void getImage() {
		try {
			this.backgroundImage = ImageIO.read(getClass().getResourceAsStream("/background/background1_2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ensures: the level number is returned
	 * @return the level number
	 */
	public int getLevelNumber() {
		return levelNumber;
	}
	
	/**
	 * ensures: the level number is set to the new level number
	 * @param number the new level number
	 */
	public void setLevelNumber(int number) {
		levelNumber = number;
	}
 }