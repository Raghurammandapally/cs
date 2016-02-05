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

/*
 * function UNIFORM-COST-SEARCH(problem) returns a solution, or failure
 *   node <- a node with STATE = problem.INITIAL-STATE, PATH-COST = 0
 *   frontier <- a priority queue ordered by PATH-COST, with node as the only element
 *   explored <- an empty set
 *   loop do
 *      if EMPTY?(frontier) then return failure
 *      node <- POP(frontier) // chooses the lowest-cost node in frontier
 *      if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
 *      add node.STATE to explored
 *      for each action in problem.ACTIONS(node.STATE) do
 *          child <- CHILD-NODE(problem, node, action)
 *          if child.STATE is not in explored or frontier then
 *             frontier <- INSERT(child, frontier)
 *          else if child.STATE is in frontier with higher PATH-COST then
 *             replace that frontier node with child
*/

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

			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
/////////////////////////////////////////////
      noOfNodesExpanded++;

      if(s.getDepth() > maxDepthSearched) {
        maxDepthSearched = s.getDepth();
      }
/////////////////////////////////////////////

      if (s.isGoal(maze)) {
        //cost = s.getGValue();
			// TODO update the maze if a solution found
        s = s.getParent(); // we don't want to overwrite the 'G'
        cost = 1;
        while(s != null /* case where it is one step to the goal state */
               && s.getParent() != null) { // we don't want to overwrite the 'S'
          maze.setOneSquare(s.getSquare(), '.');
          s = s.getParent();
          cost++;
        }
        return true;
      }
//      add the node to the explored set
      explored[s.getX()][s.getY()] = true;
//      expand the chosen node, adding the resulting nodes to the frontier 
//        (only if not in the frontier or explored set)
      ArrayList<State> succ = s.getSuccessors(explored, maze);


      for (State st : succ) {
        if(!stack.contains(st) && !explored[st.getX()][st.getY()]) {
          stack.push(st);
        }
      }

			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
/////////////////////////////////////////////
      if(stack.size() > maxSizeOfFrontier) {
        maxSizeOfFrontier = stack.size();
      }
/////////////////////////////////////////////

		}

		// TODO return false if no solution
    return false;
	}
}
