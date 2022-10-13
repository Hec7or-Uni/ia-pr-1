package aima.gui.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.TreeSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.*;

/*
 * @author Héctor Toral
 * 
 */

public class EightPuzzlePract1 {
	static EightPuzzleBoard boardWithThreeMoveSolution = new EightPuzzleBoard(
			new int[] { 1, 2, 5, 3, 4, 0, 6, 7, 8 });

	static EightPuzzleBoard random1 = new EightPuzzleBoard(new int[] { 1, 4, 2,
			7, 5, 8, 3, 0, 6 });

	static EightPuzzleBoard extreme = new EightPuzzleBoard(new int[] { 0, 8, 7,
			6, 5, 4, 3, 2, 1 });

	public static void main(String[] args) {
		System.out.format("%15s|%11s|%11s|%11s|%11s|%11s\n", "Problema", "Profundidad", "Expand", "Q.Size", "MaxQS", "tiempo");
		try {
			eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), boardWithThreeMoveSolution, "BFS-G-3", true, "");
			eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), boardWithThreeMoveSolution, "BFS-T-3", true, "");
			eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), boardWithThreeMoveSolution, "DFS-G-3", true, "");
			eightPuzzleSearch(new DepthFirstSearch(new TreeSearch()), boardWithThreeMoveSolution, "DFS-T-3", false, "(2)");
			eightPuzzleSearch(new DepthLimitedSearch(3), boardWithThreeMoveSolution, "DLS-3", true, "");
			eightPuzzleSearch(new IterativeDeepeningSearch(), boardWithThreeMoveSolution, "IDS-3", true, "");
			eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), boardWithThreeMoveSolution, "UCS-G-3", true, "");			
			eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), boardWithThreeMoveSolution, "UCS-T-3", true, "");

			eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), random1, "BFS-G-9", true, "");
			eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), random1, "BFS-T-9", true, "");
			eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), random1, "DFS-G-9", true, "");
			eightPuzzleSearch(new DepthFirstSearch(new TreeSearch()), random1, "DFS-T-9", false, "(2)");
			eightPuzzleSearch(new DepthLimitedSearch(9), random1, "DLS-9", true, "");
			eightPuzzleSearch(new IterativeDeepeningSearch(), random1, "IDS-9", true, "");
			eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), random1, "UCS-G-9", true, "");			
			eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), random1, "UCS-T-9", true, "");
			
			eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), extreme, "BFS-G-30", true, "");
			eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), extreme, "BFS-T-30", false, "(2)");
			eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), extreme, "DFS-G-30", true, "");
			eightPuzzleSearch(new DepthFirstSearch(new TreeSearch()), extreme, "DFS-T-30", false, "(2)");
			eightPuzzleSearch(new DepthLimitedSearch(30), extreme, "DLS-30", false, "(1)");
			eightPuzzleSearch(new IterativeDeepeningSearch(), extreme, "IDS-30", false, "(1)");
			eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), extreme, "UCS-G-30", true, "");			
			eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), extreme, "UCS-T-30", false, "(2)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void eightPuzzleSearch(Search search, Object initialState, String message, Boolean exec, String code) {
		if (exec) {
			int depth, expandedNodes, queueSize, maxQueueSize;
			
			try {
				Problem problem = new Problem(initialState, 
						EightPuzzleFunctionFactory.getActionsFunction(), 
						EightPuzzleFunctionFactory.getResultFunction(),
						new EightPuzzleGoalTest());
				long ti = System.currentTimeMillis();
				
				SearchAgent agent = new SearchAgent(problem, search);
				
				ti = System.currentTimeMillis() - ti;
				
				String pathcostM =agent.getInstrumentation().getProperty("pathCost");
				if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
				else depth = 0;
				if (agent.getInstrumentation().getProperty("nodesExpanded")==null) expandedNodes= 0;
				else expandedNodes = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));
				if (agent.getInstrumentation().getProperty("queueSize")==null) queueSize=0;
				else queueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize"));
				if (agent.getInstrumentation().getProperty("maxQueueSize")==null) maxQueueSize= 0;
				else maxQueueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));

				System.out.format("%15s|%11s|%11s|%11s|%11s|%11s\n", message, depth, expandedNodes, queueSize, maxQueueSize, ti);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.format("%15s|%11s|%11s|%11s|%11s|%11s\n", message, "---", "---", "---", "---", code);
		}
	}
}