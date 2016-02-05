import java.util.ArrayList;
import java.util.PriorityQueue;
import java.lang.Math;
import java.util.Iterator;
import java.util.Comparator;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
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

// * function UNIFORM-COST-SEARCH(problem) returns a solution, or failure
// *   node <- a node with STATE = problem.INITIAL-STATE, PATH-COST = 0
    State start = new State(maze.getPlayerSquare(), null, 0, 0);
// *   frontier <- a priority queue ordered by PATH-COST, with node as the only element
		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>(); // poll() to remove; offer() to add
    frontier.add(new StateFValuePair(start,f(start)));


// *   explored <- an empty set
		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
// *   loop do
		while (!frontier.isEmpty()) {
// *      if EMPTY?(frontier) then return failure
// *      node <- POP(frontier) // chooses the lowest-cost node in frontier
      StateFValuePair s = frontier.poll();
      noOfNodesExpanded++;
      if(s.getState().getDepth() > maxDepthSearched) {
        maxDepthSearched = s.getState().getDepth();
      }
// *      if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
      
      if(s.getState().isGoal(maze)) {
        cost = s.getState().getGValue();
        State st = s.getState();
        st = st.getParent();
        while(st != null && st.getParent() != null) {
          maze.setOneSquare(st.getSquare(), '.');
          st = st.getParent();
        }
        return true;
      }
// *      add node.STATE to explored
      explored[s.getState().getX()][s.getState().getY()] = true;
// *      for each action in problem.ACTIONS(node.STATE) do
// *          child <- CHILD-NODE(problem, node, action)
      ArrayList<State> succ = s.getState().getSuccessors(explored, maze);
      for (State st : succ) {
// *          if child.STATE is not in explored or frontier then
// *             frontier <- INSERT(child, frontier)
        Iterator<StateFValuePair> itr = frontier.iterator();
        StateFValuePair next = null;
        boolean found = false;
        while(itr.hasNext()) {
          next = itr.next();
          if(st.equals(next)) {
            found = true;
            break;
          }
        }

        if(!found && !explored[st.getX()][st.getY()]) {
          frontier.offer(new StateFValuePair(st, f(st)));
        }
// *          else if child.STATE is in frontier with higher PATH-COST then
// *             replace that frontier node with child
        // found == true --> next != null
        else if(found && next.getState().getGValue() > st.getGValue()) {
          boolean removed = frontier.remove(st);
          if(!removed) {
            throw new RuntimeException();
          } else {
            frontier.offer(new StateFValuePair(st, f(st)));
          }
        }

      }

      if(frontier.size() > maxSizeOfFrontier) {
        maxSizeOfFrontier = frontier.size();
      }


			// TODO return true if a solution has been found
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs
		}

		// TODO return false if no solution
    return false;
	}

  private double f(State s) {
    //f(n) = g(n)+ h(n). 
    return s.getGValue() + h(s);
  }

  private double h(State s) {
    //For A* search use as the heuristic
    //function, h, the City-Block distance from the current position to the goal position. That is, if the current
    //position is (u, v) and the goal position is (p, q), the City-Block distance is |u-p| + |v-q|. In case of ties,
    //use the priority order: U, R, D, L. That is, first pop U, then R, etc. Assume all moves have cost 1. 
    Square g = maze.getGoalSquare();
    return Math.abs(s.getX() - g.X) + Math.abs(s.getY() - g.Y);
  }
    
}
