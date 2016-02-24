/****************************************************************
 * studPlayer.java
 * Implements MiniMax search with A-B pruning and iterative deepening search (IDS). The static board
 * evaluator (SBE) function is simple: the # of stones in studPlayer's
 * mancala minue the # in opponent's mancala.
 * -----------------------------------------------------------------------------------------------------------------
 * Licensing Information: You are free to use or extend these projects for educational purposes provided that
 * (1) you do not distribute or publish solutions, (2) you retain the notice, and (3) you provide clear attribution to UW-Madison
 *
 * Attribute Information: The Mancala Game was developed at UW-Madison.
 *
 * The initial project was developed by Chuck Dyer(dyer@cs.wisc.edu) and his TAs.
 *
 * Current Version with GUI was developed by Fengan Li(fengan@cs.wisc.edu).
 * Some GUI componets are from Mancala Project in Google code.
 */


import java.lang.Integer;
import java.util.List;
import java.util.LinkedList;
import java.lang.Math;

//################################################################
// studPlayer class
//################################################################

public class wfunkhouserPlayer extends Player {

    class treeReturn {
	public int sbe;
	public int move;
	
        public treeReturn(int mySbe, int myMove) {
	    sbe = mySbe;
	    move = myMove;
	}
    }

    /*Use IDS search to find the best move. The step starts from 1 and increments by step 1.
     *Note that the search can be interrupted by time limit.
     
     update data member _move_ after each iteration of IDS

     start max_depth at 1, and increase 1 at each iteration until interrupted

     implementation:
     1. Minimax search
     2. Alpha-beta pruning
     3. Time management with iterative-deepening search (IDS)
     4. A static board evaluation (SBE) function

     */
    public void move(GameState state)
    {
	boolean print = false;
	int d = 1;
	while(true) {
	    if(print) {
		System.out.println("Current state: " + state);
	    }
	    move = maxAction(state, d);
	    if(print) {
		System.out.println(" Depth: " + d);
		System.out.println(" Best move: " + move);
		GameState s = new GameState(state);
		System.out.println(" SBE: " + maxAction(s, 0, d, Integer.MIN_VALUE, Integer.MAX_VALUE).sbe);
	    }
	    d += 1;
        }
    }

    private List<Integer> getActions(GameState state) {
	// make a list of all valid actions for this state, and return it
	
	List<Integer> l = new LinkedList<Integer>();
	for(int i = 0; i < 6; i++) {
	    if(state.stoneCount(i) > 0) {
		l.add(i);
	    }
	}
	return l;
    }

    //Return best move for max player. Note that this is a wrapper function created for ease to use.
    /*
     * o @GameState state: The game state for the current player
     * o @int maxDepth: The maximum depth you can search in the seapprch tree
     * o @return: Return the best step that leads to the maximum SBE value

     * function ALPHA-BETA-SEARCH(state) returns an action
     *   v = MAX-VALUE(state, -infinity, +infinity)
     *   return the action in ACTIONS(state) with value v
     *   

     */
    public int maxAction(GameState state, int maxDepth)
    {
	return maxAction(state, 0, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE).move;
    }

    //return best move for min player. Note that this is a wrapper function created for ease to use.
    //
    //This function is similar to public int maxAction(GameState state, int maxDepth)
    //except this function returns the best step for the Min Player. 
    public int minAction(GameState state, int maxDepth)
    {
	return 0;
    }

    //return best move for max player
    /*
     * o @GameState state: The game state we are currently searching
     * o @int currentDepth: The current depth of the game state we are searching
     * o @int maxDepth: The maximum depth we can search. When current depth
     * equals maxDepth, we should stop searching and call the SBE function to evaluate
     * the game state
     * o @int alpha: This variable is for alpha-beta pruning, which should be selfexplanatory
     * o @int beta: This variable is similar to alpha
     * o @return: Return the best step that leads to the child that gives the maximum
     * SBE value; return the step with the smallest index in the case of ties


     (deleted MIN-VALUE fn)

     * function MAX-VALUE(state, alpha, beta) returns a utility value
     *   if TERMINAL-TEST(state) then return UTILITY(state)
     *   v = -infinity
     *   for each a in ACTIONS(state) do
     *     v = MAX(v, MIN-VALUE(RESULT(s, a), alpha, beta))
     *     if v >= beta then return v
     *     alpha = MAX(alpha, v)
     *   return v

     */
    public treeReturn maxAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta)
    {
	int step = -1;
	if(currentDepth == maxDepth || state.gameOver()) {
	    return new treeReturn(sbe(state), step);
	}
	int v = Integer.MIN_VALUE;
	List<Integer> l = getActions(state);
	for(Integer i : l) {
	    GameState newState = new GameState(state);
	    //* @returns true if the player gets to move again, false otherwise.
	    boolean ret = newState.applyMove(i);

	    treeReturn t = (ret ?
			    maxAction(newState, currentDepth + 1, maxDepth, alpha, beta) :
			    minAction(newState, currentDepth + 1, maxDepth, alpha, beta)
			    );
			    			
	    if(t.sbe > v) {
		step = i;
		v = t.sbe;
	    }

	    if(v >= beta) {
		return new treeReturn(v, step);
	    }
	    alpha = Math.max(alpha, v);
	}
	return new treeReturn(v, step);
    }

    //return best move for min player
    /*
     * function MIN-VALUE(state, alpha, beta) returns a utility value
     *   if TERMINAL-TEST(state) then return UTILITY(state)
     *   v = infinity
     *   for each a in ACTIONS(state) do
     *     v = MIN(v, MAX-VALUE(RESULT(s,a), alpha, beta))
     *     if v <= alpha then return v
     *     beta = MIN(beta, v)
     *   return v
     */
    public treeReturn minAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta)
    {
	int step = -1;
	if(currentDepth == maxDepth || state.gameOver()) {
	    return new treeReturn(sbe(state), step);
	}
	int v = Integer.MAX_VALUE;
	List<Integer> l = getActions(state);
	for(Integer i : l) {
	    GameState newState = new GameState(state);
	    //* @returns true if the player gets to move again, false otherwise.
	    boolean ret = newState.applyMove(i);

	    treeReturn t = (ret ?
			    minAction(newState, currentDepth + 1, maxDepth, alpha, beta) :
			    maxAction(newState, currentDepth + 1, maxDepth, alpha, beta)
			    );
			    			
	    if(t.sbe < v) {
		step = i;
		v = t.sbe;
	    }

	    if(v <= alpha) {
		return new treeReturn(v, step);
	    }
	    beta = Math.min(beta, v);
	}
	return new treeReturn(v, step);


	//state.rotate();
	//treeReturn t = maxAction(state, currentDepth, maxDepth, alpha, beta);
	//return new treeReturn(-t.sbe, t.move);
    }

    //the sbe function for game state. Note that in the game state, the bins for current player are always in the bottom row.

    //You may implement a simple SBE method as follows: Return the number of stones in the mancala of the
    //current player minus the number in the mancala of the opponent
    /**
     * stoneCount(int bin):
     * an accesor for the number of stones in the bin where the bin numbers are
     * indexed in the following way:<pre>
     -----------------------------------------
     |    | 12 |  1 | 10 |  9 |  8 |  7 |    |
     | 13 |-----------------------------|  6 |
     |    |  0 |  1 |  2 |  3 |  4 |  5 |    |
     -----------------------------------------</pre>
     * @param bin the bin to querry for number of stones
     * @return the number of stones in the bin
     */
    private int sbe(GameState state)
    {
	return state.stoneCount(6) - state.stoneCount(13);
    }


}
