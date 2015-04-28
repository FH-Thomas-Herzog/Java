package at.fh.ooe.swe4.puzzle.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.Board.Direction;
import at.fh.ooe.swe4.puzzle.api.SlidingPuzzle;
import at.fh.ooe.swe4.puzzle.exception.InvalidMoveException;
import at.fh.ooe.swe4.puzzle.exception.NoSolutionExcption;
import at.fh.ooe.swe4.puzzle.model.SearchNode;

/**
 * This class is the solver for the game.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public class SlidingPuzzleImpl<T extends Comparable<?>> implements SlidingPuzzle<T> {

	private Board<T> board;
	private Queue<SearchNode<T>> queue;
	private SortedSet<SearchNode<T>> closed;
	private Set<SearchNode<T>> openSet;

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

	/**
	 * Initializes the solver with the given board.<br>
	 * Keep in mind if the board is null or invalid the
	 * 
	 * @param initial
	 *            the board to be resolved
	 * @return the current instance
	 */
	@Override
	public SlidingPuzzle<T> init(final Board<T> initial) {
		if ((initial == null) || (!initial.isValid())) {
			throw new IllegalArgumentException("Cannot init this instance with an null or invalid board.");
		}
		reset();
		if (initial != null) {
			board = initial;
			queue = new PriorityQueue<SearchNode<T>>((int) Math.pow(initial.size(), 2));
			closed = new TreeSet<SearchNode<T>>();
			openSet = new HashSet<SearchNode<T>>((int) Math.pow(initial.size(), 2));
		}
		return this;
	}

	@Override
	public List<Direction> solve(final Board<T> goal) throws NoSolutionExcption {
		if ((board == null) || (!board.isValid())) {
			throw new NoSolutionExcption("Cannot solve on uninitialized/invalid board Solver instance. Please call init this instance with an valid board");
		}
		if ((goal == null) || (goal.size() != board.size()) || (!goal.isValid())) {
			throw new NoSolutionExcption("Cannot solve the board if the goal board is either null, invalid or of differen size");
		}

		final SearchNode<T> target = new SearchNode<T>(goal);
		final SearchNode<T> node = new SearchNode<T>(board);
		openSet.add(node);
		queue.add(node);
		// search as long nodes are left
		while (!queue.isEmpty()) {
			final SearchNode<T> current = queue.poll();
			// we found the solution
			if (current.equals(target)) {
				reset();
				return buildResult(current);
			}
			// perform all possible moves
			final List<SearchNode<T>> successors = performMoves(current);
			// handle found successors
			for (SearchNode<T> successor : successors) {
				// we found the solution
				if (successor.equals(target)) {
					reset();
					return buildResult(successor);
				}
				// Get cost from start of parent
				final Iterator<SearchNode<T>> iterator = current.iterator();
				final Iterable<SearchNode<T>> iterable = () -> iterator;
				final int costFromStartToParent = StreamSupport.stream(iterable.spliterator(), Boolean.FALSE).mapToInt(item -> item.getCostsFormStart()).sum();
				// build sum for current predecessor
				final int currentCosts = costFromStartToParent + current.estimatedCostsToTarget(goal);
				/* Determine what to to with the successors */
				// If present in one container then set the costs
				if ((!queue.contains(successor)) || (!closed.contains(successor))) {
					successor.setCostsFormStart(currentCosts);
					successor.setPredecessor(current);
					queue.add(successor);
				}
				// if in open queue then set the costs
				else if (queue.contains(successor)) {
					queue.stream().filter(n -> (n.equals(successor))).collect(Collectors.toList()).get(0).setCostsFormStart(currentCosts);
				}
				// if in closed and costs are lower then already set then add to
				// open queue and remove from closed
				else {
					final long count = closed.stream().filter(n -> (n.equals(successor))).count();
					final SearchNode<T> enclosedNode = (count == 1) ? closed.stream().filter(n -> (n.equals(successor))).collect(Collectors.toList()).get(0)
							: null;
					if ((enclosedNode != null) && (currentCosts < enclosedNode.getCostsFormStart())) {
						enclosedNode.setCostsFormStart(currentCosts);
						enclosedNode.setPredecessor(current);
						queue.add(enclosedNode);
						closed.remove(enclosedNode);
					}
				}
			}
			// remember investigated node
			closed.add(current);
		}
		throw new NoSolutionExcption("Could not find a solution for the given board");
	}

	@Override
	public SlidingPuzzle<T> printMoves(final List<Direction> moves) {
		return this;
	}

	// Private helper
	private List<SearchNode<T>> performMoves(final SearchNode<T> node) {
		final List<SearchNode<T>> succesors = new ArrayList<SearchNode<T>>(Direction.values().length);
		for (Direction direction : Direction.values()) {
			SearchNode<T> current = node.clone();
			try {
				switch (direction) {
				case UP:
					current.getBoard().moveUp();
					break;
				case DOWN:
					current.getBoard().moveDown();
					break;
				case LEFT:
					current.getBoard().moveLeft();
					break;
				case RIGHT:
					current.getBoard().moveRight();
					break;
				default:
					throw new UnsupportedOperationException("Direction with name '" + direction.name() + "' cannot not handeled");
				}
				current.setDirection(direction);
				succesors.add(current);
			} catch (InvalidMoveException e) {
				// do nothing on invalid move
			}
		}

		return succesors;
	}

	/**
	 * Builds the result out of the given target which represents the resolved
	 * game board.
	 * 
	 * @param target
	 *            representing the resolved game board with the predecessors
	 *            set.
	 * @return the list of direction moves to be made to resolve the initial
	 *         board.
	 */
	private List<Direction> buildResult(final SearchNode<T> target) {
		return null;
	}

	/**
	 * Resets this instance by setting all container to null.
	 */
	private void reset() {
		queue = null;
		closed = null;
		openSet = null;
		board = null;
	}
}
