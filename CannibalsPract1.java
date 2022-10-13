package aima.gui.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.cannibals.CannibalsBoard;
import aima.core.environment.cannibals.CannibalsFunctionFactory;
import aima.core.environment.cannibals.CannibalsGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.TreeSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.uninformed.*;

/*
 * @author Héctor Toral
 * 
 */

public class CannibalsPract1 {
	static CannibalsBoard inicio = new CannibalsBoard();

	public static void main(String[] args) {
		try {
			canibalSearch(new BreadthFirstSearch(new GraphSearch()), inicio, "BFS-G", true);
			canibalSearch(new BreadthFirstSearch(new TreeSearch()), inicio, "BFS-T", true);
			canibalSearch(new DepthFirstSearch(new GraphSearch()), inicio, "DFS-G", true);
			canibalSearch(new DepthFirstSearch(new TreeSearch()), inicio, "DFS-T", false);
			canibalSearch(new DepthLimitedSearch(13), inicio, "DLS", true);
			canibalSearch(new IterativeDeepeningSearch(), inicio, "IDS", true);
			canibalSearch(new UniformCostSearch(new GraphSearch()), inicio, "UCS-G", true);			
			canibalSearch(new UniformCostSearch(new TreeSearch()), inicio, "UCS-T", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void canibalSearch(Search search, Object initialState, String message, Boolean exec) {
		if (exec) {
			try {
				Problem problem = new Problem(inicio,
					CannibalsFunctionFactory.getActionsFunction(),
					CannibalsFunctionFactory.getResultFunction(),
					new CannibalsGoalTest()
				);

				long ti = System.currentTimeMillis();
				SearchAgent agent = new SearchAgent(problem, search);
				ti = System.currentTimeMillis() - ti;
				
				System.out.println(String.format("Misioneros y canibales %s -->", message));
				printInstrumentation(agent.getInstrumentation());
				System.out.println(String.format("Tiempo:%dmls", ti));
				System.out.println("SOLUCIÓN:");
				System.out.println("GOAL STATE");
				String finalState = "RIBERA-IZQ                  --RIO-- BOTE M M M C C C RIBERA-DCHA";
				System.out.println(finalState);
				System.out.println("CAMINO ENCONTRADO");

				executeActions(agent.getActions(), problem);
				System.out.println("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(String.format("Misioneros y canibales %s --> Ejecución omitida\n", message));
		}
	}
	
	public static void executeActions(List<Action> actions, Problem problem) {
		Object initialState = problem.getInitialState();
		ResultFunction resultFunction = problem.getResultFunction();
		Object state = initialState;
		System.out.format("%20s  %65s\n", "INITIAL STATE",state);
		for (Action action : actions) {
			state = resultFunction.result(state, action);
			System.out.format("%20s  %65s\n", action.toString(), state);
		}
	}

	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}
	}
}