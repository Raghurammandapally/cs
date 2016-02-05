import java.util.ArrayList;
import java.lang.RuntimeException;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the DFS but you will
	// definitely need it for AStar
	private int gValue;

	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	/**
	 * @param visited
	 *            explored[i][j] is true if (i,j) is already explored
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {
		// FILL THIS METHOD
    ArrayList<State> succ = new ArrayList<State>();

		// TODO check all four neighbors (up, right, down, left)
    int X = getX();
    int Y = getY();
    //left
    addToSuccList(maze, succ, X, Y-1);
    //down
    addToSuccList(maze, succ, X+1, Y);
    //right
    addToSuccList(maze, succ, X, Y+1);
    //up
    addToSuccList(maze, succ, X-1, Y);

		// TODO return all unvisited neighbors
    return succ;
		// TODO remember that each successor's depth and gValue are
		// +1 of this object.
	}

  private void addToSuccList(Maze maze, ArrayList<State> succ, int newX, int newY) {
    // 1) If the successor node is blank or goal, you should add it to the stack. Otherwise you shouldn't.
    //
	//public State(Square square, State parent, int gValue, int depth) {
    if( //explored[newX][newY] != '%' ) {
      maze.getSquareValue(newX, newY) != '%') {
      succ.add(new State(new Square(newX, newY), this, gValue + 1 , depth + 1));
    }
  }


	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X
				&& square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the DFS but you will need it in A-star
	 * search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}

  public String toString() {
    return square.toString();
  }

  public boolean equals(Object s) {
    State s2;
    if(s instanceof StateFValuePair) {
      s2 = ((StateFValuePair) s).getState();
    } else if(! (s instanceof State)){
      throw new RuntimeException();
    } else {
      s2 = (State) s;
    }
    return s2.getX() == getX() && s2.getY() == getY();
  }
}
