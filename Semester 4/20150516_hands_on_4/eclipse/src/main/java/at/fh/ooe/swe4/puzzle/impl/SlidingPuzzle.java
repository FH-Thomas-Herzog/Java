package at.fh.ooe.swe4.puzzle.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.Board.Direction;
import at.fh.ooe.swe4.puzzle.exception.InvalidMoveException;
import at.fh.ooe.swe4.puzzle.exception.NoSolutionExcption;
import at.fh.ooe.swe4.puzzle.model.SearchNode;

/**
 * This class is the solver for the game.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 * @param <T>
 *            The value type of the board tiles
 */
public class SlidingPuzzle<T extends Comparable<T>> {

	private Board<T> board;
	private Board<T> goal;
	private Queue<SearchNode<T>> queue;
	private Set<SearchNode<T>> closed;
	private Set<SearchNode<T>> open;

	/**
	 * This is the solution handler which handles the solution result
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 1, 2015
	 * @param <T>
	 *            The value type of the board tiles
	 */
	public static final class SolutionHandler<T extends Comparable<T>> {
		private final SearchNode<T> node;
		private final SlidingPuzzle<T> slider;
		private SearchNode<T> start;
		private List<Direction> moves;
		private Logger log;
		private Level level;

		public SolutionHandler(SearchNode<T> node, SlidingPuzzle<T> slider) {
			super();
			this.node = node;
			this.slider = slider;
			this.log = Logger.getLogger(this.getClass());
			this.level = Level.INFO;
		}

		public SolutionHandler<T> start() throws NoSolutionExcption {
			if (node == null) {
				throw new NoSolutionExcption("No solution found");
			}
			final Iterator<SearchNode<T>> it = node.iterator();
			while (it.hasNext()) {
				start = it.next();
			}
			moves = node.toMoves();
			return this;
		}

		public SolutionHandler<T> registerLogger(final Logger log, final Level level) {
			this.log = log;
			this.level = level;
			return this;
		}

		public SolutionHandler<T> printMoves() {
			final StringBuilder sb = new StringBuilder(100);
			sb.append("Resulting moves for initial board to get to goal state")
				.append(System.lineSeparator())
				.append("------------------------------------------------------")
				.append(System.lineSeparator());
			for (int i = 0; i < moves.size(); i++) {
				sb.append(i)
					.append(": ")
					.append(moves.get(i)
									.name())
					.append(System.lineSeparator());
			}
			sb.append("------------------------------------------------------")
				.append(System.lineSeparator());
			log.log(level, sb.toString());
			return this;
		}

		public SolutionHandler<T> performMoves() {
			final StringBuilder sb = new StringBuilder(1000);
			final Board<T> board = start.getBoard()
										.clone();
			board.makeMoves(moves);
			sb.append("Performing moves on initial board")
				.append(System.lineSeparator())
				.append("----------------------------------")
				.append(System.lineSeparator())
				.append("Initial state:")
				.append(System.lineSeparator())
				.append("----------------------------------")
				.append(System.lineSeparator())
				.append(start.getBoard()
								.toString())
				.append("----------------------------------")
				.append(System.lineSeparator())
				.append("----------------------------------")
				.append(System.lineSeparator())
				.append("State after performed moves:")
				.append(System.lineSeparator())
				.append("----------------------------------")
				.append(System.lineSeparator())
				.append(board.toString())
				.append("----------------------------------")
				.append(System.lineSeparator());

			log.log(level, sb.toString());
			return this;
		}

		public List<Direction> getMoves() {
			return node.toMoves();
		}

		public SlidingPuzzle<T> end() {
			return slider;
		}
	}

	/**
	 * Default constructor which does not initializes this instance.<br>
	 * The {@link Board} instance to work on needs to be set via
	 * {@link SlidingPuzzleImpl#init(Board)}
	 * 
	 * @see SlidingPuzzleImpl#init(Board)
	 */
	public SlidingPuzzle() {
		super();
	}

	/**
	 * Initializes the solver with the given board.<br>
	 * Keep in mind if the board is null or invalid the
	 * 
	 * @param initial
	 *            the board to be resolved
	 * @return the current instance
	 */

	public SlidingPuzzle<T> start(final Board<T> initial) {
		if ((initial == null) || (!initial.isValid())) {
			throw new IllegalArgumentException("Cannot init this instance with an null or invalid board.");
		}
		end();
		if (initial != null) {
			board = initial;
		}
		return this;
	}

	/**
	 * Resets this instance by setting all container to null.
	 */
	public SlidingPuzzle<T> end() {
		board = null;
		goal = null;
		queue = new PriorityQueue<SearchNode<T>>();
		closed = new HashSet<SearchNode<T>>();
		open = new HashSet<SearchNode<T>>();
		return this;
	}

	public SolutionHandler<T> solve(final Board<T> goal) throws NoSolutionExcption {
		if ((board == null) || (!board.isValid())) {
			throw new NoSolutionExcption("Cannot solve on uninitialized/invalid board Solver instance. Please call init this instance with an valid board");
		}
		if ((goal == null) || (goal.size() != board.size()) || (!goal.isValid())) {
			throw new NoSolutionExcption("Cannot solve the board if the goal board is either null, invalid or of differen size");
		}
		if (!isResolvable(goal)) {
			return new SolutionHandler<T>(null, this);
		}

		// we found the solution
		if (board.equals(goal)) {
			return new SolutionHandler<T>(new SearchNode<>(0, null, board, goal, null), this);
		}

		this.goal = goal;
		final SearchNode<T> node = new SearchNode<>(0, null, board, goal, null);
		SearchNode<T> previous = null;
		queue.add(node);
		open.add(node);
		// search as long nodes are left and solution hasn't been found
		while (!queue.isEmpty()) {
			final SearchNode<T> current = queue.poll();
			// perform all possible moves
			final List<SearchNode<T>> successors = performMoves(current);
			// handle found successors
			for (SearchNode<T> successor : successors) {
				// we found the solution
				if (successor.getBoard()
								.equals(goal)) {
					return new SolutionHandler<T>(successor, this);
				}

				if ((successor.equals(previous)) && (previous.estimatedTotalCosts() > successor.estimatedTotalCosts())) {
					queue.remove(previous);
					queue.add(successor);
				}
				/* Determine what to to with the successors */
				// Add to queue if not present in closed
				if (!closed.contains(successor)) {
					queue.add(successor);
				}
			}
			// remember investigated node and previous visited
			previous = current;
			closed.add(current);
		}
		return new SolutionHandler<T>(null, this);
	}

	// Private helper
	private List<SearchNode<T>> performMoves(final SearchNode<T> parent) {
		final List<SearchNode<T>> succesors = new ArrayList<SearchNode<T>>(Direction.values().length);
		for (Direction direction : Direction.values()) {
			final Board<T> tmp = parent.getBoard()
										.clone();
			try {
				switch (direction) {
				case UP:
					tmp.moveUp();
					break;
				case DOWN:
					tmp.moveDown();
					break;
				case LEFT:
					tmp.moveLeft();
					break;
				case RIGHT:
					tmp.moveRight();
					break;
				default:
					throw new UnsupportedOperationException("Direction with name '" + direction.name() + "' cannot not handled");
				}
				succesors.add(new SearchNode<T>(parent.getCostsFormStart() + 1, parent, tmp, goal, direction));
			} catch (InvalidMoveException e) {
				// do nothing on invalid move
			}
		}

		return succesors;
	}

	/**
	 * Answers the question if the source board is possible to be resolved to
	 * the given target board.<br>
	 * It is if the parity is either even or odd on both boards.
	 * 
	 * @param target
	 *            the target board which represent the intended state.
	 * 
	 * @return true if the source board is possible to be resolved to the target
	 *         board.
	 */
	public boolean isResolvable(final Board<T> target) {
		final int sourceParity = board.calculateParity() % 2;
		final int targetParity = target.calculateParity() % 2;
		return (((sourceParity != 0) && (targetParity != 0)) || ((sourceParity == 0) && (targetParity == 0)));
	}

}
