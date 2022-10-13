package aima.core.environment.fichas;

import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.search.framework.GoalTest;

/**
 * @author Hector Toral
 * 
 */
public class FichasGoalTest implements GoalTest {

	public boolean isGoalState(Object state) {
		FichasBoard board = (FichasBoard) state;
		return board.isValid();
	}
}