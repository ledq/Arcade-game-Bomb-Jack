package mainApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class: GameAdvanceListener
 * @author A_408
 * <br>Purpose: Used with a timer to update the game after every tick
 * <br>For example:
 * <pre>
 *    GameAdvanceListener exampleGameAdvanceListener = new GameAdvanceListener(currentLevel);
 * </pre>
 */
public class GameAdvanceListener implements ActionListener {
	private Level level;

	/**
	 * ensures: initializes the level to level
	 * @param level the level that the game advance listener needs to update
	 */
	public GameAdvanceListener(Level level) {
		this.level = level;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		advanceOneTick();
	}

	/**
	 * ensures: the update method in the level class is called to check for collisions and move characters for every tick
	 */
	public void advanceOneTick() {
		this.level.update();
	}
	
	/**
	 * ensures: the level is set to the new current level
	 * @param level the new current level
	 */
	public void setCurrentLevel(Level level) {
		this.level = level;
	}
}
