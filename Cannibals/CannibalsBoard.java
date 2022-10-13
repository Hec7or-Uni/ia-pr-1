package aima.core.environment.cannibals;

import java.util.Arrays;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;

/**
 * @author Héctor Toral
 */
public class CannibalsBoard {

	public static Action Move1C   = new DynamicAction("M1C");
	public static Action Move2C   = new DynamicAction("M2C");
	public static Action Move1M   = new DynamicAction("M1M");
	public static Action Move2M   = new DynamicAction("M2M");
	public static Action Move1M1C = new DynamicAction("M1M1C");

	private int[] state;
	
	private int MAX = 3;	// Max number of cannibals and missionaries

	//
	// PUBLIC METHODS
	//

	public CannibalsBoard() {
		state = new int[] { 3, 3, 0, 0, 0 };
	}

	public CannibalsBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public CannibalsBoard(CannibalsBoard copyBoard) {
		this(copyBoard.getState());
	}

	public int[] getState() {
		return state;
	}

	// Movements functions
	
	public void move1C() {
		if (isBoatX())	setState(getCannibalsL()-1, getMissionariesL());
		else 			setState(getCannibalsL()+1, getMissionariesL());
	}

	public void move2C() {
		if (isBoatX())	setState(getCannibalsL()-2, getMissionariesL());
		else 			setState(getCannibalsL()+2, getMissionariesL());
	}

	public void move1M() {
		if (isBoatX())	setState(getCannibalsL(), getMissionariesL()-1);
		else 			setState(getCannibalsL(), getMissionariesL()+1);
	}

	public void move2M() {
		if (isBoatX())	setState(getCannibalsL(), getMissionariesL()-2);
		else 			setState(getCannibalsL(), getMissionariesL()+2);
	}
	
	public void move1M1C() {
		if (isBoatX())	setState(getCannibalsL()-1, getMissionariesL()-1);
		else 			setState(getCannibalsL()+1, getMissionariesL()+1);
	}

	public boolean canMoveGap(Action where) {
		boolean retVal = true;
		int Cx = getCannibalsL();
		int Mx = getMissionariesL();
		int Cy = getCannibalsR();
		int My = getMissionariesR();
		
		if (!isBoatX()) {
			int temp = Cx;
			Cx = Cy;
			Cy = temp;
			
			int temp2 = Mx;
			Mx = My;
			My = temp2;
		}
		
		if (where.equals(Move1C))		retVal =   canMove1C(Cx, Mx, Cy, My);
		else if (where.equals(Move2C))	retVal =   canMove2C(Cx, Mx, Cy, My);
		else if (where.equals(Move1M))	retVal =   canMove1M(Cx, Mx, Cy, My);
		else if (where.equals(Move2M))	retVal =   canMove2M(Cx, Mx, Cy, My);	
		else if (where.equals(Move1M1C))retVal = canMove1M1C(Cx, Mx, Cy, My);
		
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
		CannibalsBoard other = (CannibalsBoard) obj;
		return Arrays.equals(state, other.state);
	}
	
	private String write(String Type, Boolean Side) {
		String sec = "";
		int numW = 0;
		
		if (Side) {
			if (Type == "M")		numW = getMissionariesL();
			else if (Type == "C")	numW = getCannibalsL();
		} else {
			if (Type == "M")		numW = getMissionariesR();
			else if (Type == "C")	numW = getCannibalsR();
		}
		
		// Write white spaces
		for (int i = 0; i <= MAX - numW; i++)
			sec += "  ";
		// Write components
		for (int i = 0; i < numW ; i++)
			sec += (Type + " ");
		
		return sec;
	}
	
	@Override
	public String toString() {
		String retVal;
		String boat = (isBoatX()) ? " BOTE --RIO--      " : "      --RIO-- BOTE ";
		retVal = "RIBERA-IZQ" + write("M", true) + write("C", true) + boat + write("M", false) + write("C", false) + "RIBERA-DCHA";
		
		return retVal;
	}

	//
	// PRIVATE METHODS
	//
	
	// Possible movements
	
	private boolean canMove1C(int Cx, int Mx, int Cy, int My) {
		boolean disp = Cx >= 1;
		boolean cond = My >= Cy + 1 || My == 0;
		return disp && cond;
	}
	
	private boolean canMove2C(int Cx, int Mx, int Cy, int My) {
		boolean disp = Cx >= 2;
		boolean cond = My >= Cy + 2 || My == 0;
		return disp && cond;
	}
	
	private boolean canMove1M(int Cx, int Mx, int Cy, int My) {
		boolean disp = Mx >= 1;
		boolean cond = (Mx >= Cx + 1 || Mx == 1) && My >= Cy - 1;
		return disp && cond;
	}
	
	private boolean canMove2M(int Cx, int Mx, int Cy, int My) {
		boolean disp = Mx >= 2;
		boolean cond = (Mx >= Cx + 2 || Mx == 2) && My >= Cy - 2;
		return disp && cond;
	}
	
	private boolean canMove1M1C(int Cx, int Mx, int Cy, int My) {
		boolean disp = Cx >= 1 && Mx >= 1;
		boolean cond = My >= Cy;
		return disp && cond;
	}
	
	// Getters
	
	private boolean isBoatX() {
		return (state[2] == 0) ? true : false;
	}
	
	private int getCannibalsL() {
		return state[0];
	}
	
	private int getCannibalsR() {
		return state[3];
	}
	
	private int getMissionariesL() {
		return state[1];
	}
	
	private int getMissionariesR() {
		return state[4];
	}
	
	// Setters
	
	private void setState(int Cx, int Mx) {
		state[0] = Cx;
		state[1] = Mx;
		state[2] = (isBoatX()) ? 1 : 0;
		state[3] = MAX - Cx;
		state[4] = MAX - Mx;
	}
}