package at.fh.ooe.swe4.puzzle.api;

import java.util.List;

import at.fh.ooe.swe4.puzzle.api.Board.Direction;
import at.fh.ooe.swe4.puzzle.exception.NoSolutionExcption;

public interface SlidingPuzzle<V extends Number> {

	/**
	 * Initializes this instance with the given {@link Board}.<br>
	 * If this board is null this instance will not be initialized.
	 * 
	 * @param board
	 *            the board to work on
	 */
	public abstract void init(Board<V> board);

	/**
	 * Solves the board.
	 * 
	 * @return the list of Directions which represents the moves.
	 */
	public abstract List<Direction> solve() throws NoSolutionExcption;

	/**
	 * Prints the given moves performed on the given board.
	 * @param moves
	 *            the moves to be performed on the given board.
	 */
	public abstract void printMoves(List<Direction> moves);

	/**
	 * Reset this instance
	 */
	public abstract void reset();

}