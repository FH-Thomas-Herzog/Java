package at.fh.ooe.swe4.puzzle.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.Board.Direction;
import at.fh.ooe.swe4.puzzle.api.SlidingPuzzle;
import at.fh.ooe.swe4.puzzle.exception.NoSolutionExcption;
import at.fh.ooe.swe4.puzzle.model.SearchNode;

/**
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public class SlidingPuzzleImpl<T extends Comparable> implements SlidingPuzzle<T> {

	private Board<T> board;
	private Queue<SearchNode<T>> queue;
	private Set<SearchNode<T>> closedSet;

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
	public void init(final Board<T> initial) {
		this.board = initial;
		if (initial != null) {
			queue = new PriorityQueue<SearchNode<T>>(initial.size());
			closedSet = new LinkedHashSet<SearchNode<T>>(initial.size());
		}
	}

	@Override
	public List<Direction> solve(final Board<T> goal) throws NoSolutionExcption {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printMoves(final List<Direction> moves) {

	}

	@Override
	public void reset() {
		board = null;
		queue = null;
		closedSet = null;
	}
}
