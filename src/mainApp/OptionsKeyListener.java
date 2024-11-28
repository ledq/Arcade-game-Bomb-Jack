package mainApp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Class: OptionsKeyListener
 * @author A_408
 * <br>Purpose: Figure out what the game needs to do based on the text displayed next to the bomb for all screens where the user selects an option
 * <br>Restrictions: The bomb must be displayed at the same height as the text for the option
 * <br>For example:
 * <pre>
 *    OptionsKeyListener exampleOptionsKeyListener = new OptionsKeyListener(mainApp, frame, screen);
 * </pre>
 */
public class OptionsKeyListener implements KeyListener {
	private MainApp mainApp;
	private JFrame frame;
	private Screen screen;
	private int firstOptionY;
	private Bomb chooseOption;
	private ArrayList<String> screenOptions;
	private JComponent previousScreen = null;
	private KeyListener previousKeyListener = null;
	
	/**
	 * ensures: initializes the main app to mainApp, initializes the frame to frame, initializes the screen to screen, initializes the y value of the first option to firstOptionY, initializes the bomb that selects the options to chooseOption, and initializes the ArrayList of options to choose from to screenOptions
	 * @param mainApp the main app for the game
	 * @param frame the JFrame that the game is run in
	 * @param screen the screen that the OptionsKeyListener is used in
	 */
	public OptionsKeyListener(MainApp mainApp, JFrame frame, Screen screen) {
		this.mainApp = mainApp;
		this.frame = frame;
		this.screen = screen;
		this.firstOptionY = screen.getFirstOptionY();
		this.chooseOption = screen.getChooseOption();
		this.screenOptions = screen.getOptions();
	}
	
	/**
	 * ensures: the other constructor is called to initialize the main app, frame, and screen, the previous screen is initialized to previousScree, and the previous key listener is initialized to previousKeyListener
	 * @param mainApp the main app for the game
	 * @param frame the JFrame that the game is run in
	 * @param screen the screen that the OptionsKeyListener is used in
	 * @param previousScreen the previous screen that the previous options key listener was used in
	 * @param previousKeyListener the previous options key listener
	 */
	public OptionsKeyListener(MainApp mainApp, JFrame frame, Screen screen, JComponent previousScreen, KeyListener previousKeyListener) {
		this(mainApp, frame, screen);
		this.previousScreen = previousScreen;
		this.previousKeyListener = previousKeyListener;
	}
	
	/**
	 * ensures: does nothing because nothing should happen when a key is typed
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * ensures: the bomb for choosing the option is moved up or down when the up and down arrows are pressed if there are still other options in those directions, and it handles what should happen if the player selects each option
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (!(chooseOption.getPositionY() < firstOptionY)) {
				chooseOption.setPositionY(chooseOption.getPositionY() - 50);
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (!(chooseOption.getPositionY() + 30 > firstOptionY + (screenOptions.size() - 1) * 50)) {
				chooseOption.setPositionY(chooseOption.getPositionY() + 50);
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			int optionNumber = ((chooseOption.getPositionY() + (chooseOption.getHeight() / 2)) - (firstOptionY - 10)) / 50;
			frame.remove(screen);
			frame.removeKeyListener(this);
			if (screenOptions.get(optionNumber).equals("Play")) {
				mainApp.runApp();
			}
			else if (screenOptions.get(optionNumber).equals("High Scores")) {
				Screen highScores = new Screen(mainApp, frame, "high scores", screen, this);
				frame.add(highScores);
			}
			else if (screenOptions.get(optionNumber).equals("Help")) {
				Screen helpScreen = new Screen(mainApp, frame, "help", screen, this);
				frame.add(helpScreen);
			}
			else if (screenOptions.get(optionNumber).equals("Exit")) {
				frame.add(previousScreen);
				frame.addKeyListener(previousKeyListener);
			}
			else if (screenOptions.get(optionNumber).equals("Resume")) {
				frame.add(previousScreen);
				frame.addKeyListener(previousKeyListener);
				mainApp.resumeTimer();
			}
			else if (screenOptions.get(optionNumber).equals("Quit")) {
				mainApp.resetKeyListener();
				mainApp.startGame();
			}
		}
		frame.repaint();
		frame.validate();
	}

	/**
	 * ensures: does nothing because nothing should happen when a key is released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}
}