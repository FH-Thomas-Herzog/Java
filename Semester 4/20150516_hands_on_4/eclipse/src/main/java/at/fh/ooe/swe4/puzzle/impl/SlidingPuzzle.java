package at.fh.ooe.swe4.puzzle.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.Board.Move;
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

	private boolean started = Boolean.FALSE;

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
		private List<Move> moves;
		private Logger log;
		private Level level;

		private boolean started = Boolean.FALSE;

		/**
		 * Constructor which gets the current {@link SlidingPuzzle} instance and
		 * resulting {@link SearchNode} instance, where no solution has been
		 * found if the node is null.
		 * 
		 * @param node
		 *            the {@link SearchNode} instance representing the result.
		 *            If null no result has been found.
		 * @param slider
		 *            the {@link SlidingPuzzle} instance which gets returned on
		 *            method call {@link SolutionHandler#end()}
		 */
		public SolutionHandler(SearchNode<T> node, SlidingPuzzle<T> slider) {
			super();
			this.node = node;
			this.slider = slider;
		}

		/**
		 * Starts the solution handling by creation the {@link Move} list.
		 * 
		 * @return the current instance
		 * @throws IllegalStateException
		 *             if the solution handler is already started
		 * @throws NoSolutionExcption
		 *             if the set node is null
		 */
		public SolutionHandler<T> start() throws NoSolutionExcption {
			if (started) {
				throw new IllegalStateException("The solution handler needs to be end before started again");
			}
			started = Boolean.TRUE;
			if (node == null) {
				throw new NoSolutionExcption("No solution found");
			}
			final Iterator<SearchNode<T>> it = node.iterator();
			while (it.hasNext()) {
				start = it.next();
			}
			moves = node.toMoves();
			// init with default logging
			this.log = Logger.getLogger(this.getClass());
			this.level = Level.INFO;
			return this;
		}

		/**
		 * Ability to provide a custom {@link Logger} instance and {@link Level}
		 * 
		 * @param log
		 *            the {@link Logger} instance used for logging
		 * @param level
		 *            the {@link Level} instance defining the to use log level
		 * @return the current instance
		 * @throws IllegalStateException
		 *             if the solution handler hasn't been started yet
		 */
		public SolutionHandler<T> registerLogger(final Logger log, final Level level) {
			checkForStarted();
			this.log = log;
			this.level = level;
			return this;
		}

		/**
		 * Prints the resulting moves via the {@link Logger}
		 * 
		 * @return the current instance
		 * @throws IllegalStateException
		 *             if the solution handler hasn't been started yet
		 */
		public SolutionHandler<T> printMoves() {
			checkForStarted();
			log.log(level, "Resulting moves:");
			for (int i = 0; i < moves.size(); i++) {
				log.log(level, new StringBuilder().append(String.format("%1$-" + ((moves.size() / 10) + 1) + "s", (i + 1)))
													.append(": ")
													.append(moves.get(i)
																	.name())
													.toString());
			}
			return this;
		}

		/**
		 * Performs the moves on the initial board which has been found via the
		 * given node.
		 * 
		 * @return the current instance
		 * @throws IllegalStateException
		 *             if the solution handler hasn't been started yet
		 * @throws InvalidMoveException
		 *             if at least one of the resulting moves is invalid
		 * @see Board#makeMoves(Iterable)
		 */
		public SolutionHandler<T> performMoves() {
			checkForStarted();
			final Board<T> board = start.getBoard()
										.clone();
			start.getBoard()
					.makeMoves(moves);
			log.log(level, "Initial state:");
			// .append(board.toString())
			String[] boardStrings = null;
			boardStrings = StringUtils.split(board.toString(), System.lineSeparator());
			for (String string : boardStrings) {
				log.log(level, string);
			}
			log.log(level, "Initial state after movements:");
			boardStrings = StringUtils.split(start.getBoard()
													.toString(), System.lineSeparator());
			for (String string : boardStrings) {
				log.log(level, string);
			}
			return this;
		}

		@Deprecated
		public List<Move> getMoves() {
			return node.toMoves();
		}

		/**
		 * Fills the given list with the resulting moves.
		 * 
		 * @param moves
		 *            the list to fill resulting moves in
		 * @return the current instance
		 * @throws IllegalArgumentException
		 *             if the given list if null
		 * @throws IllegalStateException
		 *             if the solution handler hasn't been started yet
		 */
		public SolutionHandler<T> fillMoves(final List<Move> moves) {
			checkForStarted();
			if (moves == null) {
				throw new IllegalArgumentException("Given moves list must not be null");
			}
			moves.addAll(this.moves);
			return this;
		}

		/**
		 * Ends the solution handling by reseting all members but the given node
		 * or slider.<br>
		 * This instance can be restarted by calling method
		 * {@link SolutionHandler#start}
		 * 
		 * @return the related {@link SlidingPuzzle} instance
		 * @throws IllegalStateException
		 *             if the solution handler hasn't been started yet
		 */
		public SlidingPuzzle<T> end() {
			checkForStarted();
			started = Boolean.FALSE;
			this.level = null;
			this.log = null;
			this.moves = null;
			return slider;
		}

		/**
		 * Checks if the {@link SolutionHandler} instance has been started
		 * 
		 * @throws IllegalStateException
		 *             if the instance hasn't been started but it is tried to
		 *             perform an action on it.
		 */
		private void checkForStarted() {
			if (!started) {
				throw new IllegalStateException("The solution handler needs to be started before the solution can be handled");
			}
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
	 * @throws IllegalStateException
	 *             if the slider has already been started
	 */

	public SlidingPuzzle<T> start(final Board<T> initial) {
		if (started) {
			throw new IllegalStateException("The slider instance need to be end before restarted again.");
		}
		if ((initial == null) || (!initial.isValid())) {
			throw new IllegalArgumentException("Cannot init this instance with an null or invalid board.");
		}
		this.started = Boolean.TRUE;
		this.board = initial;
		this.queue = new PriorityQueue<SearchNode<T>>();
		this.closed = new HashSet<SearchNode<T>>();
		return this;
	}

	/**
	 * Resets this instance by setting all container to null.
	 * 
	 * @throws IllegalStateException
	 *             if the solution handler hasn't been started yet
	 */
	public SlidingPuzzle<T> end() {
		checkForStarted();
		this.started = Boolean.FALSE;
		this.board = null;
		this.goal = null;
		this.queue = null;
		this.closed = null;
		return this;
	}

	/**
	 * Solves the set initial board and tries to calculate the moves to be made
	 * to transform the initial board to the goal board.
	 * 
	 * @param goal
	 *            the goal board to resolve the initial board to
	 * @return the {@link SolutionHandler} instance which is responsible for
	 *         handling the result
	 * @throws NoSolutionExcption
	 *             <ul>
	 *             <li>goal is null</li>
	 *             <li>goal is invalid</li>
	 *             <li>goal has different size</li>
	 *             </ul>
	 */
	public SolutionHandler<T> solve(final Board<T> goal) throws NoSolutionExcption {
		checkForStarted();
		// goal must represent valid board
		if ((goal == null) || (!goal.isValid()) || ((goal.size() != board.size()))) {
			throw new NoSolutionExcption("Cannot solve the board if the goal board is either null, invalid or of differen size");
		}
		this.goal = goal;

		// validate parity of the two boards
		if (!isResolvable()) {
			return new SolutionHandler<T>(null, this);
		}

		// we found the solution right away
		if (board.equals(goal)) {
			return new SolutionHandler<T>(new SearchNode<>(0, null, board, goal, null), this);
		}

		queue.add(new SearchNode<>(0, null, board, goal, null));
		// search as long nodes are left and solution hasn't been found
		while (!queue.isEmpty()) {
			final SearchNode<T> current = queue.poll();
			// get successors of current node by performing moves on it
			final List<SearchNode<T>> successors = performMoves(current);
			// handle found successors
			for (SearchNode<T> successor : successors) {
				// we found the solution
				if (successor.getBoard()
								.equals(goal)) {
					return new SolutionHandler<T>(successor, this);
				}
				// Add to queue if not present in closed
				else if (!closed.contains(successor)) {
					queue.add(successor);
				}
			}
			// remember investigated node
			closed.add(current);
		}
		// no solution found should never occur
		throw new IllegalStateException("Solution should have been found but wasn't. Maybe parity check failed");
	}

	// Private helper
	/**
	 * Tries to perform all possible moves on the current node board
	 * 
	 * @param parent
	 *            the parent which is the predecessor of the successor
	 * @return the list of found successors
	 */
	private List<SearchNode<T>> performMoves(final SearchNode<T> parent) {
		final List<SearchNode<T>> succesors = new ArrayList<SearchNode<T>>(Move.values().length);
		for (Move direction : Move.values()) {
			// Get board to move empty tile on
			final Board<T> tmp = parent.getBoard()
										.clone();
			try {
				// perform the moves
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
				// Add found successor in case of valid move
				succesors.add(new SearchNode<T>(parent.getCostsFormStart() + 1, parent, tmp, goal, direction));
			} catch (InvalidMoveException e) {
				// do nothing on invalid move
			}
		}

		return succesors;
	}

	/**
	 * Answers the question if the set board is possible to be resolved to the
	 * set goal board.<br>
	 * It is if the parity is either even or odd on both boards.
	 * 
	 * @return true if the set board is possible to be resolved to the set goal
	 *         board.
	 */
	private boolean isResolvable() {
		final int sourceParity = board.calculateParity() % 2;
		final int targetParity = goal.calculateParity() % 2;
		return (((sourceParity != 0) && (targetParity != 0)) || ((sourceParity == 0) && (targetParity == 0)));
	}

	/**
	 * Checks if the {@link SlidingPuzzle} instance has been started
	 * 
	 * @throws IllegalStateException
	 *             if the instance hasn't been started but it is tried to
	 *             perform an action on it.
	 */
	private void checkForStarted() {
		if (!started) {
			throw new IllegalStateException("The slider needs to be started before the solution can be handled");
		}
	}
}
