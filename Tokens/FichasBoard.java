package aima.core.environment.fichas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Hector Toral
 */
public class FichasBoard {

	public static Action L0 = new DynamicAction("MoveL0");
	public static Action R0 = new DynamicAction("MoveR0");
	public static Action L1 = new DynamicAction("MoveL1");
	public static Action R1 = new DynamicAction("MoveR1");
	public static Action L2 = new DynamicAction("MoveL2");
	public static Action R2 = new DynamicAction("MoveR2");

	private int[] state;

	//
	// PUBLIC METHODS
	//

	public FichasBoard() {
		state = new int[] { 1, 1, 1, -1, 0, 0, 0 };
	}

	public FichasBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public FichasBoard(FichasBoard copyBoard) {
		this(copyBoard.getState());
	}

	public int[] getState() {
		return state;
	}

	public void L0() {
		setValues(-1);
	}
	
	public void L1() {
		setValues(-2);
	}
	
	public void L2() {
		setValues(-3);
	}
	
	public void R0() {
		setValues(1);
	}
	
	public void R1() {
		setValues(2);
	}
	
	public void R2() {
		setValues(3);
	}

	public boolean canMoveGap(Action where) {
		boolean retVal = true;
		
		if (where.equals(L0)) {
			retVal = state[0] != -1;
		} else if (where.equals(L1)) {
			retVal = state[0] != -1 && state[1] != -1;
		} else if (where.equals(L2)) {
			retVal = state[0] != -1 && state[1] != -1 && state[2] != -1;
		} else if (where.equals(R0)) {
			retVal = state[6] != -1;
		} else if (where.equals(R1)) {
			retVal = state[6] != -1 && state[5] != -1;
		} else if (where.equals(R2)) {
			retVal = state[6] != -1 && state[5] != -1 && state[4] != -1;
		} 
		
		return retVal;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(state);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FichasBoard other = (FichasBoard) obj;
		return Arrays.equals(state, other.state);
	}

	@Override
	public String toString() {
		String frame = "+---+---+---+---+---+---+---+\n";
		String retVal;
		retVal = "| " + p(0) + " | " + p(1) + " | " + p(2) + " | " + p(3) + " | " + p(4) + " | " + p(5) + " | " + p(6) + " |\n";
		return frame + retVal + frame ;
	}
	
	private String p(int i) {
		if (state[i] == 1) {
			return "B";
		} else if (state[i] == 0) {
			return "V";
		} else {
			return " ";
		}
	}
	
	public boolean isValid() {
		boolean B = state[0] == 0 && state[1] == 0 && state[2] == 0;
		boolean V = state[4] == 1 && state[5] == 1 && state[6] == 1;
		return B && V;
	}
	
	private int locateSpace() {
		int i = 0;
		while (i < state.length) {
			if (state[i] == -1) break;
			i++;
		}
		return i;
	}
	
	private void setValues(int x) {
		int s = locateSpace();
		int ficha  = state[s+x];
		state[s+x] = state[s];
		state[s]   = ficha;
	}
}