package mainApp;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class: MainApp
 * @author A_408
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * <br>Restrictions: None
 * <br>For example:
 * <pre>
 *    MainApp exampleMainApp = new MainApp();
 * </pre>
 */
public class MainApp {
	private static final int DELAY = 1;
	private static final Color THEME_COLOR = new Color(239, 228, 176);
	private static final int FRAME_WIDTH = 555;
	private static final int FRAME_HEIGHT = 625;
	
	private KeyListener keyListener = null;
	private GameAdvanceListener advanceListener;
	private Timer timer;
	private JFrame frame;
	private Hero hero;
	private Level currentLevel;
	private File highScores;
	private String playerNameString;	
	
	/**
	 * ensures: initializes frame to a new JFrame called "Bomb Jack," initializes hero to a new hero, initializes 
	 *     the current level to null, initializes the game advance listener to a new game advance listener, 
	 *     initializes the timer to a new timer with the delay DELAY, initializes the high scores text file to a 
	 *     text file with the correct name, and initializes the player's name to ""
	 */
	public MainApp() {
		System.out.println("Write your cool arcade game here!");
		frame = new JFrame("Bomb Jack");
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT); //each grid space is 60 by 60 with 50 px at the bottom for lives and scoreboard
		this.hero = new Hero();
		this.currentLevel = null;
		this.advanceListener = new GameAdvanceListener(currentLevel);
		timer = new Timer(DELAY, advanceListener);
		highScores = new File("high_scores/high_scores.txt");
		playerNameString = "";
	}

	/**
	 * ensures: the start screen is displayed and a key listener is added for the player to choose what to do
	 */
	public void startGame() {
		if (timer != null) {
			timer.stop();
		}
		if (currentLevel != null) {
			currentLevel.clear();
			frame.remove(currentLevel);
			frame.repaint();
			frame.validate();
		}
		Screen startScreen = new Screen(this, "start");
		frame.add(startScreen, BorderLayout.CENTER);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addKeyListener(new OptionsKeyListener(this, frame, startScreen));
	}
	
	/**
	 * ensures: the game is run and the hero can move
	 */
	public void runApp() {
		//set up level 1
		hero.resetGame();
		currentLevel = new Level(1, this.hero, this);
		currentLevel.setUpGame();
		frame.add(currentLevel);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if (keyListener == null) {
			keyListener = new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() == 'u') {
						File fileToLookFor = new File("levels/level" + (currentLevel.getLevelNumber() + 1) + ".txt");
						if (!fileToLookFor.exists()) {
							System.err.println("There are no more levels.");
							return;
						}
						currentLevel.clear();
						currentLevel.setLevelNumber(currentLevel.getLevelNumber() + 1);
						currentLevel.setUpGame();
						frame.add(currentLevel);
						frame.repaint();
						frame.validate();
					}
					
					if (e.getKeyChar() == 'd') {
						if (currentLevel.getLevelNumber() == 1) {
							System.err.println("You are already at level 1.");
							return;
						}
						currentLevel.clear();
						currentLevel.setLevelNumber(currentLevel.getLevelNumber() - 1);
						currentLevel.setUpGame();
						frame.add(currentLevel);
						frame.repaint();
						frame.validate();
					}
				}
	
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						timer.stop();
						Screen optionsScreen = new Screen(MainApp.this, frame, "options", (JComponent) currentLevel, (KeyListener) this);
						frame.remove(currentLevel);
						frame.removeKeyListener(this);
						frame.add(optionsScreen);
						frame.repaint();
						frame.validate();
					}		
					else {
						if (e.getKeyCode()==KeyEvent.VK_LEFT) {
							hero.moveLeft(true);
						}
						if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
							hero.moveRight(true);
						}
						if (e.getKeyCode()==KeyEvent.VK_UP) {
							hero.fly(true);
						}
					}
					frame.repaint();
				}
	
				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode()==KeyEvent.VK_LEFT) {
						hero.moveLeft(false);
					}
					if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
						hero.moveRight(false);
					}
					if (e.getKeyCode()==KeyEvent.VK_UP) {
						hero.fly(false);
					}
				}	
			};
			frame.addKeyListener(keyListener);
		}
		advanceListener.setCurrentLevel(currentLevel);
		timer.start();
	} // runApp
	
	/**
	 * ensures: the timer is started again after it is stopped
	 */
	public void resumeTimer() {
		timer.start();
	}
	
	/**
	 * ensures: the next level is found and displayed if it exists, and the game is over and the user won if the 
	 *     next level is not found
	 */
	public void nextLevel() {
		timer.stop();
		File fileToLookFor = new File("levels/level" + (currentLevel.getLevelNumber() + 1) + ".txt");
		if (!fileToLookFor.exists()) {
			this.finishedGame();
			System.out.println("There are no more levels.\n"
					+ "Score: " + hero.getScore());
			frame.remove(currentLevel);
			return;
		}
		currentLevel.clear();
		currentLevel.setLevelNumber(currentLevel.getLevelNumber() + 1);
		currentLevel.setUpGame();
		frame.add(currentLevel);
		frame.repaint();
		frame.validate();
		
		timer.start();
	}
	
	/**
	 * ensures: checks if the player has a new high score and adds it if they do and takes the player back to the 
	 *     start screen if they don't
	 */
	public void finishedGame() {
		timer.stop();
		Scanner scanner = null;
		File highScores = getHighScores();
		
		try {
			scanner = new Scanner(highScores);
		} catch (FileNotFoundException e) {
			System.err.println("That file does not exist.");
		}
		
		ArrayList<Integer> scores = new ArrayList<Integer>();
		ArrayList<String> playerNames = new ArrayList<String>();
		
		while(scanner.hasNext()) {
			String[] scoreInformation = scanner.nextLine().split(",");
			scores.add(Integer.parseInt(scoreInformation[0]));
			playerNames.add(scoreInformation[1]);
		}
		if (scores.size() < 10 || hero.getScore() > scores.get(scores.size() - 1)) {
			getPlayerName(scores, playerNames);
		}
		else {
			startGame();
		}
		scanner.close();
	}
	
	/**
	 * ensures: the key listener is reset to null so that duplicate key listeners aren't created if the user 
	 *     restarts the game again
	 */
	public void resetKeyListener() {
		keyListener = null;
	}
	
	/**
	 * ensures: the player's name is found so that they can be added to the list of high scores later
	 * @param scores the current high scores in the high scores file
	 * @param playerNames the names of the players who have high scores
	 */
	private void getPlayerName(ArrayList<Integer> scores, ArrayList<String> playerNames) {
		JFrame playerName = new JFrame("Enter your name");
		playerName.setSize(300, 150);
		JPanel getPlayerName = new JPanel();
		JLabel enterName = new JLabel("Enter your name: ");
		getPlayerName.add(enterName);
		JTextField playerNameText = new JTextField(15);
		getPlayerName.add(playerNameText);
		JButton enter = new JButton("Enter");
		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playerNameString = playerNameText.getText();
				if (playerNameString.equals("")) {
					playerNameString = "Player";
				}
				playerName.dispose();
				addScore(scores, playerNames);
			}
			
		});
		getPlayerName.add(enter);
		playerName.add(getPlayerName);
		
		playerName.setVisible(true);
	}
	
	/**
	 * ensures: the player's new high score and name are added in the correct place in the high score list based 
	 *     on how many points they have
	 * @param scores the current high scores in the high scores file
	 * @param playerNames the names of players who have high scores
	 */
	private void addScore(ArrayList<Integer> scores, ArrayList<String> playerNames) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(highScores);
		} catch (IOException e) {
			System.err.println("That file does not exist.");
		}
		try {
			fw.write("");
			boolean addedScore = false;
			if (scores.size() == 0) {
				fw.append(hero.getScore() + "," + playerNameString + "\n");
			}
			else {
				for (int i = 0; i < scores.size(); i++) {
					if (hero.getScore() > scores.get(i) && addedScore == false) {
						fw.append(hero.getScore() + "," + playerNameString + "\n");
						i--;
						addedScore = true;
						if (scores.size() == 10) {
							scores.remove(scores.size() - 1);
						}
					}
					else {
						fw.append(scores.get(i) + "," + playerNames.get(i) + "\n");
					}
				}
				if (addedScore == false) {
					fw.append(hero.getScore() + "," + playerNameString + "\n");
				}
			}
		} catch (IOException e) {
			System.err.println("Couldn't write to file.");
		}
		try {
			fw.close();
		} catch (IOException e) {
			System.err.println("Couldn't close file.");
		}
		startGame();
	}
	
	/**
	 * ensures: the highScores file is returned
	 * @return the highScores file
	 */
	public File getHighScores() {
		return highScores;
	}
	
	/**
	 * ensures: the theme color is returned
	 * @return the theme color
	 */
	public Color getThemeColor() {
		return THEME_COLOR;
	}
	
	/**
	 * ensures: the frame width is returned
	 * @return the frame width
	 */
	public int getFrameWidth() {
		return FRAME_WIDTH;
	}
	
	/**
	 * ensures: the frame height is returned
	 * @return the frame height
	 */
	public int getFrameHeight() {
		return FRAME_HEIGHT;
	}

	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.startGame();		
	} // main
}