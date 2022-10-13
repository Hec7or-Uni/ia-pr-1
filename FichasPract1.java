package aima.gui.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.fichas.FichasBoard;
import aima.core.environment.fichas.FichasFunctionFactory;
import aima.core.environment.fichas.FichasGoalTest;
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

public class FichasPract1 {
	static FichasBoard inicio = new FichasBoard();

	public static void main(String[] args) {
		try {
			fichasSearch(new BreadthFirstSearch(new GraphSearch()), inicio, "BFS-G", true);
			fichasSearch(new BreadthFirstSearch(new TreeSearch()), inicio, "BFS-T", true);
			fichasSearch(new DepthFirstSearch(new GraphSearch()), inicio, "DFS-G", true);
			fichasSearch(new DepthFirstSearch(new TreeSearch()), inicio, "DFS-T", false);
			fichasSearch(new DepthLimitedSearch(13), inicio, "DLS", true);
			fichasSearch(new IterativeDeepeningSearch(), inicio, "IDS", true);
			fichasSearch(new UniformCostSearch(new GraphSearch()), inicio, "UCS-G", true);			
			fichasSearch(new UniformCostSearch(new TreeSearch()), inicio, "UCS-T", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void fichasSearch(Search search, Object initialState, String message, Boolean exec) {
		if (exec) {
			try {
				Problem problem = new Problem(inicio,
					FichasFunctionFactory.getActionsFunction(),
					FichasFunctionFactory.getResultFunction(),
					new FichasGoalTest()
				);

				long ti = System.currentTimeMillis();
				SearchAgent agent = new SearchAgent(problem, search);
				ti = System.currentTimeMillis() - ti;
				
				System.out.println(String.format("Fichas %s -->", message));
				printInstrumentation(agent.getInstrumentation());
				System.out.println(String.format("Tiempo:%dmls", ti));
				System.out.println("SOLUCIÓN:");
				System.out.println("GOAL STATE");
				System.out.println("\t+---+---+---+---+---+---+---+");
				System.out.println("\t| V | V | V |   | B | B | B |");
				System.out.println("\t+---+---+---+---+---+---+---+");
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
		
		System.out.println("INITIAL STATE");
		System.out.println(state);
		for (Action action : actions) {
			state = resultFunction.result(state, action);
			System.out.println(action.toString());
			System.out.println(state);
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