package at.fh.ooe.swe4.puzzle.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.Board.Move;

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
	private Move direction;

	/**
	 * Labda expression for calculating
	 */
	public static final BiFunction<Position, Position, Integer> CALC_MANHATTAN_DIST = (root, goal) -> (Math.abs((root.rowIdx - goal.rowIdx)) + Math.abs((root.colIdx - goal.colIdx)));

	/**
	 * Constructs this instance and calculates the Manhattan distance between
	 * given board an goal and also the full costs.
	 * 
	 * @param costsFormStart
	 *            the level the node is in
	 * @param predecessor
	 *            the predecessor can be null
	 * @param board
	 *            the board this node represents
	 * @param goal
	 *            the goal for calculating Manhattan distance
	 * @param direction
	 *            the direction performed to get to this state, can be null
	 * @throws IllegalArgumentException
	 *             if board or goal is null or they are not of the same size
	 */
	public SearchNode(int costsFormStart, SearchNode<T> predecessor, Board<T> board, Board<T> goal, Move direction) {
		super();
		if ((board == null)) {
			throw new IllegalArgumentException("Cannot configure this searchNode with a null board");
		}
		if ((goal == null)) {
			throw new IllegalArgumentException("Cannot calculate manhattan distance on nul goal");
		}
		if (board.size() != goal.size()) {
			throw new IllegalArgumentException("The given board and gola must be of the same size");
		}
		this.costsFormStart = costsFormStart;
		this.predecessor = predecessor;
		this.board = board;
		this.direction = direction;
		this.manhattenDistance = calculateMahanttenDistance(goal);
		this.fullCosts = costsFormStart + manhattenDistance;
	}

	public int estimatedCostsToTarget() {
		return manhattenDistance;
	}

	/**
	 * Calculates the Manhattan distance of all tiles on the given board to the
	 * goal board.
	 * 
	 * @return the Manhattan distance of this board state compared to the goal
	 *         state.
	 * @throws NoSuchElementException
	 *             if the goal does not contain a value from the initial board
	 * @see SearchNode#CALC_MANHATTAN_DIST
	 * @see Board#getTile(int, int)
	 */
	private int calculateMahanttenDistance(final Board<T> goal) {
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

	/**
	 * Gets the estimated total costs.
	 * 
	 * @return the total cost of this node
	 */
	public int estimatedTotalCosts() {
		return getCostsFormStart() + estimatedCostsToTarget();
	}

	/**
	 * Converts the node list started from this node to a list of {@link Move}
	 * which represent the moves to make to get this board state to the intended
	 * goal state.
	 * 
	 * @return the list of moves to make to get to the goal state
	 */
	public List<Move> toMoves() {
		final List<Move> moves = new ArrayList<Move>();
		iterator().forEachRemaining(new Consumer<SearchNode<T>>() {
			public void accept(SearchNode<T> t) {
				// last node has no move set
				if (t.getMove() != null) {
					moves.add(t.getMove());
				}
			}
		});
		// must be reversed because chain is in wrong order
		Collections.reverse(moves);
		return moves;
	}

	// Iterator methods
	/**
	 * This is the Iterator implementation for iterating over the connected
	 * search node list of the given search node.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Apr 26, 2015
	 */
	private static final class SearchNodeIterator<T extends Comparable<T>> implements Iterator<SearchNode<T>> {

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
	public Iterator<SearchNode<T>> iterator() {
		return new SearchNodeIterator<T>(this);
	};

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

	public Move getMove() {
		return direction;
	}

	public void setMove(Move direction) {
		this.direction = direction;
	}

	/**
	 * Compares the two instance by their set full costs
	 */
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
		final StringBuilder sb = new StringBuilder((int) (Math.pow(board.size(), 2) * 2));
		sb.append(System.lineSeparator())
			.append("------------------------------------------------")
			.append(System.lineSeparator())
			.append("SearchNode content")
			.append(System.lineSeparator())
			.append("------------------------------------------------")
			.append(System.lineSeparator());
		sb.append("costsFromStart: ")
			.append(costsFormStart)
			.append(System.lineSeparator())
			.append("manhattanDistance: ")
			.append(manhattenDistance)
			.append(System.lineSeparator())
			.append("fullCosts: ")
			.append(fullCosts)
			.append(board.toString());
		return sb.toString();
	}
}
