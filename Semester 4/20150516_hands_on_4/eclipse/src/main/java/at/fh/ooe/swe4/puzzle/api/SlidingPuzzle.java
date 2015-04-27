package at.fh.ooe.swe4.puzzle.api;

import java.util.List;

import at.fh.ooe.swe4.puzzle.api.Board.Direction;
import at.fh.ooe.swe4.puzzle.exception.NoSolutionExcption;

public interface SlidingPuzzle<V extends Comparable> {

	/**
	 * Initializes this instance with the given initial {@link Board}.
	 * 
	 * @param initial
	 *            the initial board representing the start of the game
	 */
	public abstract void init(Board<V> initial);

	/**
	 * Tries to solve the game to get to the intended result
	 * 
	 * @param goal
	 *            the goal board representing the goal state
	 * @return the list of moves to resolve the game on the initial board
	 * @throws NoSolutionExcption
	 *             if the game cannot be resolved
	 */
	public abstract List<Direction> solve(final Board<V> goal) throws NoSolutionExcption;

	/**
	 * Prints the given moves performed on the given board.
	 * 
	 * @param moves
	 *            the moves to be performed on the given board.
	 */
	public abstract void printMoves(List<Direction> moves);

	/**
	 * Reset this instance
	 */
	public abstract void reset();

}