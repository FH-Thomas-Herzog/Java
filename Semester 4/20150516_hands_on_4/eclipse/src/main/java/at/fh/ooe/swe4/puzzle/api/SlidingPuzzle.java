package at.fh.ooe.swe4.puzzle.api;

import java.util.List;

import at.fh.ooe.swe4.puzzle.api.Board.Direction;
import at.fh.ooe.swe4.puzzle.exception.NoSolutionExcption;

public interface SlidingPuzzle<T extends Comparable<?>> {

	/**
	 * Initializes this instance with the given initial {@link Board}.
	 * 
	 * @param initial
	 *            the initial board representing the start of the game
	 */
	public SlidingPuzzle<T> init(Board<T> initial);

	/**
	 * Tries to solve the game to get to the intended result
	 * 
	 * @param goal
	 *            the goal board representing the goal state
	 * @return the list of moves to resolve the game on the initial board
	 * @throws NoSolutionExcption
	 *             if the game cannot be resolved
	 */
	public List<Direction> solve(final Board<T> goal) throws NoSolutionExcption;

	/**
	 * Prints the given moves performed on the given board.
	 * 
	 * @param moves
	 *            the moves to be performed on the given board.
	 */
	public SlidingPuzzle<T> printMoves(List<Direction> moves);

}