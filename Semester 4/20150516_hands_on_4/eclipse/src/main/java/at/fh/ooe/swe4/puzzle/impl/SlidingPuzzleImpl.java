package at.fh.ooe.swe4.puzzle.impl;

import java.util.List;

import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.Board.Direction;
import at.fh.ooe.swe4.puzzle.api.SlidingPuzzle;
import at.fh.ooe.swe4.puzzle.exception.NoSolutionExcption;

/**
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public class SlidingPuzzleImpl<T extends Number> implements SlidingPuzzle<T> {

	private Board<T> board;

	/**
	 * Default constructor which does not initializes this instance.<br>
	 * The {@link Board} instance to work on needs to be set via
	 * {@link SlidingPuzzleImpl#init(Board)}
	 * 
	 * @see SlidingPuzzleImpl#init(Board)
	 */
	public SlidingPuzzleImpl() {
		super();
	}

	/**
	 * Constructor which initializes the instance with the given {@link Board}.<br>
	 * If the board is null then this instance will not be initialized.
	 * 
	 * @param board
	 *            the board to work on with this instance
	 * @see SlidingPuzzleImpl#init(Board)
	 */
	public SlidingPuzzleImpl(final Board<T> board) {
		super();
		init(board);
	}

	@Override
	public void init(final Board<T> board) {
		this.board = board;
	}

	@Override
	public List<Direction> solve() throws NoSolutionExcption {
		return null;
	}

	@Override
	public void printMoves(final List<Direction> moves) {

	}

	@Override
	public void reset() {
		board = null;
	}
}
