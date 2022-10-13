package aima.core.environment.cannibals;

import aima.core.search.framework.GoalTest;

/**
 * @author Héctor Toral
 */
public class CannibalsGoalTest implements GoalTest {
	CannibalsBoard goal = new CannibalsBoard(new int[] { 0, 0, 1, 3, 3 });

	public boolean isGoalState(Object state) {
		CannibalsBoard board = (CannibalsBoard) state;
		return board.equals(goal);
	}
}