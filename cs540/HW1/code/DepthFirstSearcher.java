import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Depth-First Search (DFS)
 * 
 * You should fill the search() method of this class.
 */
public class DepthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public DepthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main depth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

//  function GRAPH-SEARCH(problem) returns a solution, or failure
//    initialize the frontier using the initial state of problem
//
		// Stack implementing the Frontier list
		LinkedList<State> stack = new LinkedList<State>();
    //getPlayerSquare() holds current (start) state
    stack.push(new State(maze.getPlayerSquare(), null, 0, 0));

//    initialize the explored set to be empty
		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
//    loop do:
		while (!stack.isEmpty()) {
//    if the frontier is empty then return failure
//      choose a leaf node and remove it from the frontier
//      if the node contains a goal state then return the corresponding solution
			// TODO return true if find a solution
      State s = stack.pop();
      if (s.isGoal(maze)) {
        // update maze if soln found
        s = s.getParent(); // we don't want to overwrite the 'G'
        while(s != null /* case where it is one step to the goal state */
               && s.getParent() != null) { // we don't want to overwrite the 'S'
          maze.setOneSquare(s.getSquare(), '.');
          s = s.getParent();
        }
        return true;
      }
//      add the node to the explored set
      explored[s.getX()][s.getY()] = true;
//      expand the chosen node, adding the resulting nodes to the frontier 
//        (only if not in the frontier or explored set)
      ArrayList<State> succ = s.getSuccessors(explored, maze);



			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

		}

		// TODO return false if no solution
    return false;
	}
}
