package aima.core.environment.cannibals;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Héctor Toral
 */
public class CannibalsFunctionFactory {
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
			CannibalsBoard board = (CannibalsBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(CannibalsBoard.Move1C)) {
				actions.add(CannibalsBoard.Move1C);
			}
			if (board.canMoveGap(CannibalsBoard.Move2C)) {
				actions.add(CannibalsBoard.Move2C);
			}
			if (board.canMoveGap(CannibalsBoard.Move1M)) {
				actions.add(CannibalsBoard.Move1M);
			}
			if (board.canMoveGap(CannibalsBoard.Move2M)) {
				actions.add(CannibalsBoard.Move2M);
			}
			if (board.canMoveGap(CannibalsBoard.Move1M1C)) {
				actions.add(CannibalsBoard.Move1M1C);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			CannibalsBoard board = (CannibalsBoard) s;

			if (CannibalsBoard.Move1C.equals(a) && board.canMoveGap(CannibalsBoard.Move1C)) {
				CannibalsBoard newBoard = new CannibalsBoard(board);
				newBoard.move1C();
				return newBoard;
			} else if (CannibalsBoard.Move2C.equals(a) && board.canMoveGap(CannibalsBoard.Move2C)) {
				CannibalsBoard newBoard = new CannibalsBoard(board);
				newBoard.move2C();
				return newBoard;
			} else if (CannibalsBoard.Move1M.equals(a) && board.canMoveGap(CannibalsBoard.Move1M)) {
				CannibalsBoard newBoard = new CannibalsBoard(board);
				newBoard.move1M();
				return newBoard;
			} else if (CannibalsBoard.Move2M.equals(a) && board.canMoveGap(CannibalsBoard.Move2M)) {
				CannibalsBoard newBoard = new CannibalsBoard(board);
				newBoard.move2M();
				return newBoard;
			} else if (CannibalsBoard.Move1M1C.equals(a) && board.canMoveGap(CannibalsBoard.Move1M1C)) {
				CannibalsBoard newBoard = new CannibalsBoard(board);
				newBoard.move1M1C();
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}