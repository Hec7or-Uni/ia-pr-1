package aima.core.environment.fichas;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Hector Toral
 */
public class FichasFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new EPActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new EPResultFunction();
		}
		return _resultFunction;
	}

	private static class EPActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			FichasBoard board = (FichasBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(FichasBoard.L0)) {
				actions.add(FichasBoard.L0);
			}
			if (board.canMoveGap(FichasBoard.L1)) {
				actions.add(FichasBoard.L1);
			}
			if (board.canMoveGap(FichasBoard.L2)) {
				actions.add(FichasBoard.L2);
			}
			if (board.canMoveGap(FichasBoard.R0)) {
				actions.add(FichasBoard.R0);
			}
			if (board.canMoveGap(FichasBoard.R1)) {
				actions.add(FichasBoard.R1);
			}
			if (board.canMoveGap(FichasBoard.R2)) {
				actions.add(FichasBoard.R2);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			FichasBoard board = (FichasBoard) s;

			if (FichasBoard.L0.equals(a) && board.canMoveGap(FichasBoard.L0)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.L0();
				return newBoard;
			} else if (FichasBoard.L1.equals(a) && board.canMoveGap(FichasBoard.L1)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.L1();
				return newBoard;
			} else if (FichasBoard.L2.equals(a) && board.canMoveGap(FichasBoard.L2)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.L2();
				return newBoard;
			} else if (FichasBoard.R0.equals(a) && board.canMoveGap(FichasBoard.R0)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.R0();
				return newBoard;
			} else if (FichasBoard.R1.equals(a) && board.canMoveGap(FichasBoard.R1)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.R1();
				return newBoard;
			} else if (FichasBoard.R2.equals(a) && board.canMoveGap(FichasBoard.R2)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.R2();
				return newBoard;
			} 

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}