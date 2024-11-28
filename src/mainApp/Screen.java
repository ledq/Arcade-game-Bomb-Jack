package mainApp;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Class: Screen
 * @author A_408
 * <br>Purpose: Draw each type of screen in the JFrame
 * <br>For example:
 * <pre>
 *    Screen exampleScreen = new Screen(mainApp, frame, "type");
 * </pre>
 */
public class Screen extends JComponent{
	private MainApp mainApp;
	private String type;
	private ArrayList<String> screenOptions;
	private Bomb chooseOption;
	private int firstOptionY;
	private int chooseOptionX;
	
	/**
	 * ensures: initializes the main app to mainApp, initializes the type of screen to type, initializes the 
	 *     screen options ArrayList to a new ArrayList of Strings, and adds options and sets the first option 
	 *     and bomb positions based on the type of screen
	 * @param mainApp the main app
	 * @param type the type of screen
	 */
	public Screen(MainApp mainApp, String type) {
		this.mainApp = mainApp;
		this.type = type;
		screenOptions = new ArrayList<String>();
		if (type.equals("start")) {
			screenOptions.add("Play");
			screenOptions.add("High Scores");
			screenOptions.add("Help");
			firstOptionY = 300;
			chooseOptionX = 210;
		}
		else if (type.equals("high scores") || type.equals("help")) {
			screenOptions.add("Exit");
			firstOptionY = 565;
			chooseOptionX = 423;
		}
		else if (type.equals("options")) {
			screenOptions.add("Resume");
			screenOptions.add("Help");
			screenOptions.add("Quit");
			firstOptionY = 200;
			chooseOptionX = 210;
		}
		this.chooseOption = new Bomb(chooseOptionX, firstOptionY - 10);
	}
	
	/**
	 * ensures: the other constructor is called to initialize the main app and type and makes sure the key listener 
	 *     for the high scores, help, and options screen keeps track of the previous screen and key listener
	 * @param mainApp the main app
	 * @param frame the frame that displays the screens
	 * @param type the type of screen
	 * @param previousScreen the previous screen
	 * @param previousKeyListener the key listener for the previous screen
	 */
	public Screen(MainApp mainApp, JFrame frame, String type, JComponent previousScreen, KeyListener previousKeyListener) {
		this(mainApp, type);
		if (type.equals("high scores") || type.equals("help") || type.equals("options")) {
			frame.addKeyListener(new OptionsKeyListener(mainApp, frame, this, previousScreen, previousKeyListener));
		}
	}
	
	/**
	 * ensures: draws the correct screen in the frame based on the type of screen
	 */
	private void drawOn(Graphics2D g2) {
		if (type.equals("start")) {
			drawStartScreen(g2);
		}
		else if (type.equals("high scores")) {
			drawHighScoresScreen(g2);
		}
		else if (type.equals("help")) {
			drawHelpScreen(g2);
		}
		else if (type.equals("options")) {
			drawOptionsScreen(g2);
		}
		else {
			System.err.println("Invalid screen type");
		}
	}
	
	/**
	 * ensures: draws the start screen
	 * @param g2 the graphics object
	 */
	private void drawStartScreen(Graphics2D g2) {
		Rectangle startScreenBackground = new Rectangle(0, 0, 555, 590);
		g2.setColor(mainApp.getThemeColor());
		g2.draw(startScreenBackground);
		g2.fill(startScreenBackground);
		g2.setColor(Color.RED);
		Font titleFont = new Font(Font.MONOSPACED, Font.BOLD, 90);
		g2.setFont(titleFont);
		g2.drawString("Bomb Jack!", 10, 200);
		
		g2.setColor(Color.BLACK);
		Font startScreenOptionsFont = new Font(Font.MONOSPACED, Font.PLAIN, 30);
		g2.setFont(startScreenOptionsFont);
		for (int i = 0; i < screenOptions.size(); i++) {
			g2.drawString(screenOptions.get(i), 232, (i * 50) + firstOptionY);
		}
		
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		g2.drawString("Use the arrow keys to choose an option.", 42, 500);
		g2.drawString("Then press enter.", 155, 540);
		
		chooseOption.drawOn(g2);
	}
	
	/**
	 * ensures: draws the help screen
	 * @param g2 the graphics object
	 */
	private void drawHelpScreen(Graphics2D g2) {
		Rectangle startScreenBackground = new Rectangle(0, 0, 555, 590);
		g2.setColor(mainApp.getThemeColor());
		g2.draw(startScreenBackground);
		g2.fill(startScreenBackground);
		
		//screen title
		g2.setColor(Color.RED);
		g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		g2.drawString("Help", 200, 55);
		
		//controls section title
		g2.setColor(Color.RED);
		g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
		g2.drawString("Controls", 175, 100);
		
		//help information
		g2.setColor(Color.BLACK);
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		g2.drawString("Move left and right:", 20, 135);
		g2.drawString("Fly:", 20, 165);
		g2.drawString("Go to next level:", 20, 195);
		g2.drawString("Go to previous level:", 20, 225);
		g2.drawString("Pause: ", 20, 255);
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		g2.drawString("Escape", 450, 257);
		drawKey(g2, "<", 450, 135);
		drawKey(g2, ">", 480, 135);
		drawKey(g2, "^", 465, 165);
		drawKey(g2, "U", 465, 195);
		drawKey(g2, "D", 465, 225);
		
		//how to play section title
		g2.setColor(Color.RED);
		g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
		g2.drawString("How to Play", 137, 305);
		
		//how to play
		g2.setColor(Color.BLACK);
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		g2.drawString("    Collect all the bombs to complete the", 20, 340);
		g2.drawString("level. The hero can kill enemies by", 20, 370);
		g2.drawString("colliding with them when the hero is", 20, 400);
		g2.drawString("higher than the enemy, but if the enemy", 20, 430);
		g2.drawString("is higher than the hero, the hero dies.", 20, 460);
		g2.drawString("You lose the game when the hero loses all", 20, 490);
		g2.drawString("three lives.", 20, 520);
		
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		for (int i = 0; i < screenOptions.size(); i++) {
			g2.drawString(screenOptions.get(i), 450, (i * 50) + firstOptionY);
		}
		
		chooseOption.drawOn(g2);
	}
	
	/**
	 * ensures: draws the key graphics on the help screen
	 * @param g2 the graphics object
	 * @param text the text for the key
	 * @param positionX the x position of the key
	 * @param positionY the y position of the key
	 */
	private void drawKey(Graphics2D g2, String text, int positionX, int positionY) {
		g2.setColor(Color.BLACK);
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		Rectangle keyOutline = new Rectangle(positionX - 1, positionY - 15, 20, 20);
		g2.draw(keyOutline);
		g2.drawString(text, positionX + 5, positionY);
	}
	
	/**
	 * ensures: draws the options screen
	 * @param g2 the graphics object
	 */
	private void drawOptionsScreen(Graphics2D g2) {
		Rectangle startScreenBackground = new Rectangle(0, 0, 555, 590);
		g2.setColor(new Color(0, 0, 0, 200));
		g2.draw(startScreenBackground);
		g2.fill(startScreenBackground);
		
		//screen title
		g2.setColor(Color.RED);
		g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		g2.drawString("Options", 175, 55);
		
		g2.setColor(Color.WHITE);
		Font startScreenOptionsFont = new Font(Font.MONOSPACED, Font.PLAIN, 30);
		g2.setFont(startScreenOptionsFont);
		for (int i = 0; i < screenOptions.size(); i++) {
			g2.drawString(screenOptions.get(i), 232, (i * 50) + firstOptionY);
		}
		
		chooseOption.drawOn(g2);
	}
	
	/**
	 * ensures: draws the high scores screen
	 * @param g2 the graphics object
	 */
	private void drawHighScoresScreen(Graphics2D g2) {
		Rectangle startScreenBackground = new Rectangle(0, 0, 555, 590);
		g2.setColor(mainApp.getThemeColor());
		g2.draw(startScreenBackground);
		g2.fill(startScreenBackground);
		
		//screen title
		g2.setColor(Color.RED);
		g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		g2.drawString("High Scores", 107, 55);

		Scanner scanner = null;
		File highScores = mainApp.getHighScores();
		
		try {
			scanner = new Scanner(highScores);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

		g2.setColor(Color.BLACK);
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		
		g2.drawString("Place Score Name", 80, 100);
		int lineNumber = 0;
		while (scanner.hasNext()) {
			String[] scoreInformation = scanner.nextLine().split(",");
			String place = String.format("%1$-6s", (lineNumber + 1));
			String score = String.format("%1$-6s", scoreInformation[0]);
			String name = String.format("%1$-20s", scoreInformation[1]);
			g2.drawString(place + score + name.substring(0, 20), 80, lineNumber * 30 + 130);
			lineNumber++;
		}
		scanner.close();
		
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		for (int i = 0; i < screenOptions.size(); i++) {
			g2.drawString(screenOptions.get(i), 450, (i * 50) + firstOptionY);
		}
		
		chooseOption.drawOn(g2);
	}
	
	/**
	 * ensures: the y position of the first option is returned
	 * @return the y position of the first option
	 */
	public int getFirstOptionY() {
		return this.firstOptionY;
	}
	
	/**
	 * ensures: the bomb for choosing the option is returned
	 * @return the bomb for choosing the option
	 */
	public Bomb getChooseOption() {
		return this.chooseOption;
	}
	
	/**
	 * ensures: the list of options for the screen is returned
	 * @return the list of options for the screen
	 */
	public ArrayList<String> getOptions() {
		return screenOptions;
	}
	
	/**
	 * ensures: the screen is drawn in the frame
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOn(g2);
	}
}