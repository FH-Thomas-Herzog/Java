package at.fh.ooe.swe4.puzzle.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.Board.Direction;

/**
 * This class represents the search node for the solver algorithm.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 30, 2015
 * @param <T>
 *            the {@link Comparable<T>} type which represents the board value
 */
public class SearchNode<T extends Comparable<T>> implements Comparable<SearchNode<T>>, Iterable<SearchNode<T>> {

	private int costsFormStart;
	private int manhattenDistance;
	private int fullCosts;
	private SearchNode<T> predecessor;
	private final Board<T> board;
	private Direction direction;

	/**
	 * Labda expression for calculating
	 */
	public static final BiFunction<Position, Position, Integer> CALC_MANHATTAN_DIST = (root, goal) -> (Math.abs((root.rowIdx - goal.rowIdx)) + Math.abs((root.colIdx - goal.colIdx)));

	/**
	 * Constructs this instance and calculates the Manhattan distance between
	 * given board an goal.
	 * 
	 * @param costsFormStart
	 *            the level the node represents
	 * @param predecessor
	 *            the predecessor can be null
	 * @param board
	 *            the board this node represents
	 * @param goal
	 *            the goal for calculating Manhattan distance
	 * @param direction
	 *            the direction performed to get to this state.
	 * @throws IllegalArgumentException
	 *             if board or goal is null
	 */
	public SearchNode(int costsFormStart, SearchNode<T> predecessor, Board<T> board, Board<T> goal, Direction direction) {
		super();
		if ((board == null)) {
			throw new IllegalArgumentException("Cannot configure this searchNode with a null board");
		}
		if ((goal == null)) {
			throw new IllegalArgumentException("Cannot calculate manhattan distance on nul goal");
		}
		this.costsFormStart = costsFormStart;
		this.predecessor = predecessor;
		this.board = board;
		this.direction = direction;
		this.manhattenDistance = calculateMahanttenDistance(goal);
		this.fullCosts = costsFormStart + manhattenDistance;
	}

	/**
	 * Calculates the Manhattan distance of all tiles on the board to their
	 * actual position of the current board game state.
	 * 
	 * @return the Manhattan distance of this board state.
	 * @throws IllegalArgumentException
	 *             <ul>
	 *             <li>the goal board is null</li>
	 *             <li>the goal board has not the same size as the initial board
	 *             </li>
	 *             <li>the goal board is invalid</li>
	 *             </ul>
	 * @throws NoSuchElementException
	 *             if the goal does not contain a value from the initial board
	 * @see SearchNode#CALC_MANHATTAN_DIST
	 */
	public int estimatedCostsToTarget() {
		return manhattenDistance;
	}

	private int calculateMahanttenDistance(final Board<T> goal) {
		if ((goal == null) || (board.size() != goal.size()) || (!goal.isValid())) {
			throw new IllegalArgumentException("The goal board is either null/invalid or has different size as the initial board");
		}
		int costs = 0;
		for (int i = 1; i <= board.size(); i++) {
			for (int j = 1; j <= board.size(); j++) {
				final T tile = board.getTile(i, j);
				// ignore empty tile
				if (tile != null) {
					final Position goalPosition = goal.getTilePosition(tile);
					costs += CALC_MANHATTAN_DIST.apply(new Position(i, j), goalPosition);
				}
			}
		}
		return costs;
	}

	public int estimatedTotalCosts() {
		return getCostsFormStart() + estimatedCostsToTarget();
	}

	/**
	 * Converts the node list started from this node to a list of
	 * {@link Direction} which represent the moves to make to get last node in
	 * the referenced node chain.
	 * 
	 * @return the list of moves
	 */
	public List<Direction> toMoves() {
		final List<Direction> moves = new ArrayList<Direction>();
		iterator().forEachRemaining(new Consumer<SearchNode<T>>() {
			public void accept(SearchNode<T> t) {
				if (t.getDirection() != null) {
					moves.add(t.getDirection());
				}
			}
		});
		Collections.reverse(moves);
		return moves;
	}

	// Iterator methods
	public Iterator<SearchNode<T>> iterator() {
		return new SearchNodeIterator(this);
	};

	/**
	 * This is the Iterator implementation for iterating over the connected
	 * search node list of the given search node.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Apr 26, 2015
	 */
	private class SearchNodeIterator implements Iterator<SearchNode<T>> {

		private SearchNode<T> node;

		public SearchNodeIterator(SearchNode<T> node) {
			super();
			this.node = node;
		}

		public boolean hasNext() {
			return node != null;
		}

		public SearchNode<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more search nodes are available");
			}
			final SearchNode<T> tmp = node;
			node = node.getPredecessor();
			return tmp;
		}
	}

	// Getter and Setter
	public SearchNode<T> getPredecessor() {
		return predecessor;
	}

	public int getCostsFormStart() {
		return costsFormStart;
	}

	public void setCostsFormStart(int costsFormStart) {
		this.costsFormStart = costsFormStart;
	}

	public void setPredecessor(SearchNode<T> predecessor) {
		this.predecessor = predecessor;
	}

	public Board<T> getBoard() {
		return board;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int compareTo(SearchNode<T> o) {
		return Integer.valueOf(fullCosts)
						.compareTo(o.fullCosts);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + fullCosts;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchNode<T> other = (SearchNode<T>) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (fullCosts != other.fullCosts)
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("costs: ")
			.append(costsFormStart)
			.append(System.lineSeparator());
		sb.append(board.toString());
		return sb.toString();
	}
}
