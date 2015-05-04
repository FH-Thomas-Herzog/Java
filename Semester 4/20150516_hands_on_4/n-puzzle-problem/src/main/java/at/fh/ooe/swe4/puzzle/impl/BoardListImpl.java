package at.fh.ooe.swe4.puzzle.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang.StringUtils;

import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.exception.InvalidBoardIndexException;
import at.fh.ooe.swe4.puzzle.exception.InvalidMoveException;
import at.fh.ooe.swe4.puzzle.model.Position;

/**
 * This is the implementation of the {@link Board} interface.<br>
 * This class provides possibility to define the tile value type which needs to
 * implement {@link Comparable} interface.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 2, 2015
 * @param <T>
 *            the value type of the tile values
 */
public class BoardListImpl<T extends Comparable<T>> implements Board<T> {

	private final int size;
	private final List<T> container;

	/**
	 * The default constructor for this class which needs an size to be given.
	 * 
	 * @param size
	 *            the size of the board, for the rows and the columns
	 * @throws IllegalArgumentException
	 *             if size <=0
	 */
	public BoardListImpl(final int size) {
		super();
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be greater than 0");
		}
		this.size = size;
		final int containerSize = (int) Math.pow(size, 2);
		container = new ArrayList<T>(containerSize);
		// Init with null values
		for (int i = 0; i < containerSize; i++) {
			container.add(null);
		}
	}

	/**
	 * This constructor provides the size and container which contains the board
	 * values, where the container will be copied and cannot be modified from
	 * the outside.<br>
	 * The container must be a flat representation of the board.<br>
	 * It's size must be size*size which is the same as rows*columns.<br>
	 * E.g.: A value positioned at (3,2) will have the container index
	 * (3-1)*size + (2-1)
	 * 
	 * @param size
	 *            the size of the board
	 * @param container
	 *            the container holding the board values
	 * @throws IllegalArgumentException
	 *             <ul>
	 *             <li>size <= 0</li>
	 *             <li>container is null</li>
	 *             <li>container.size() != (size*size)</li>
	 *             </ul>
	 */
	public BoardListImpl(final int size, final List<T> container) {
		super();
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be greater than 0");
		}
		if (container == null) {
			throw new IllegalArgumentException("Container must not be null");
		}
		if (((int) Math.pow(size, 2)) != container.size()) {
			throw new IllegalArgumentException("Container size does not correspond to given board size");
		}
		this.size = size;
		this.container = new ArrayList<T>(container);
	}

	@Override
	public T getTile(int rowIdx, int colIdx) {
		return container.get(calculateContainerIdx(rowIdx, colIdx));
	}

	@Override
	public void setTile(int rowIdx, int colIdx, T value) {
		container.set(calculateContainerIdx(rowIdx, colIdx), value);
	}

	@Override
	public void setEmptyTile(int rowIdx, int colIdx) {
		setTile(rowIdx, colIdx, (T) null);
	}

	@Override
	public Position getEmptyTilePosition() {
		final int[] indices = IntStream.range(0, container.size())
										.filter(i -> container.get(i) == null)
										.toArray();
		if (indices.length != 0) {
			final int rowIdx = ((indices[0] / size) + 1);
			final int colIdx = (indices[0] - ((rowIdx - 1) * size) + 1);
			return new Position(rowIdx, colIdx);
		}
		return new Position(-1, -1);
	}

	@Override
	public Position getTilePosition(final T value) {
		// row iteration
		for (int i = 1; i <= size(); i++) {
			// column iteration
			for (int j = 1; j <= size(); j++) {
				final T tile = getTile(i, j);
				if (((tile == null) && (value == null)) || ((tile != null) && (tile.equals(value)))) {
					return new Position(i, j);
				}
			}
		}
		throw new NoSuchElementException("Tile with value '" + ((value == null) ? "null" : value.toString()) + "' does not exist on the board");
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isValid() {
		return (container.stream()
							.distinct()
							.count() == (container.size())) && (container.stream()
																			.filter(element -> element == null)
																			.count() == 1);
	}

	@Override
	public void shuffle() {
		// We need to ensure the parity of the shuffled board
		final boolean even = (calculateParity() % 2 == 0);
		boolean shuffledEven = !even;

		// fill list with the possible moves
		final List<Move> moves = new ArrayList<Move>(Move.values().length * 4);
		moves.addAll(Arrays.asList(Move.values()));
		moves.addAll(Arrays.asList(Move.values()));
		moves.addAll(Arrays.asList(Move.values()));
		moves.addAll(Arrays.asList(Move.values()));

		// as long as parity is not equal to original one
		while (even != shuffledEven) {
			// shuffle the possible moves
			Collections.shuffle(moves);
			moves.forEach(new Consumer<Move>() {
				@Override
				public void accept(Move direction) {
					try {
						// try to perform move
						move(direction);
					} catch (InvalidMoveException e) {
						// expected because random movements could try to move
						// empty tile out of the board
					}
				}
			});
			// recalculate the parity of the shuffled board
			shuffledEven = (calculateParity() % 2 == 0);
		}
	}

	@Override
	public void move(int rowIdx, int colIdx) {
		final Position position = getEmptyTilePosition();
		setTile(position.rowIdx, position.colIdx, container.get(calculateContainerIdx(rowIdx, colIdx)));
		setTile(rowIdx, colIdx, null);
	}

	@Override
	public void moveLeft() {
		move(Move.LEFT);
	}

	@Override
	public void moveRight() {
		move(Move.RIGHT);
	}

	@Override
	public void moveUp() {
		move(Move.UP);
	}

	@Override
	public void moveDown() {
		move(Move.DOWN);
	}

	@Override
	public void makeMoves(Iterable<Move> moves) {
		if (moves == null) {
			throw new InvalidMoveException("Cannot perform moves because iterable instance is null");
		}
		moves.iterator()
				.forEachRemaining(new Consumer<Move>() {
					@Override
					public void accept(Move t) {
						move(t);
					}
				});
	}

	// Private helper
	/**
	 * Check if the given indices are valid for this board.
	 * 
	 * @param rowIdx
	 *            the row index
	 * @param colIdx
	 *            the column index
	 * @throws InvalidBoardIndexException
	 *             if at least one of the indices is invalid
	 */
	private void checkForValidIndex(final int rowIdx, final int colIdx) {
		if ((rowIdx > size) || (rowIdx <= 0) || (colIdx > size) || (colIdx <= 0)) {
			throw new InvalidBoardIndexException("One of the indicies over or underlfows the border defined by size. rowIdx: " + rowIdx + " | colIdx: "
					+ colIdx);
		}
	}

	/**
	 * Calculates the index for the backed container.
	 * 
	 * @param rowIdx
	 *            the row index
	 * @param colIdx
	 *            the column index
	 * @return the calculated index
	 * @throws InvalidBoardIndexException
	 *             if at least one of the indexes is invalid
	 * @see BoardListImpl#checkForValidIndex(int, int)
	 */
	private int calculateContainerIdx(final int rowIdx, final int colIdx) {
		checkForValidIndex(rowIdx, colIdx);
		return ((rowIdx - 1) * size) + (colIdx - 1);
	}

	/**
	 * Performs a move operation into the intended direction.<br>
	 * The move operation can be performed when the following conditions fit.
	 * <ul>
	 * <li>the board is valid</li>
	 * <li>the new position is valid</li>
	 * <li>the given {@link Move} enumeration is supported</li>
	 * </ul>
	 * 
	 * @param direction
	 *            the direction to move to
	 * @throws InvalidMoveException
	 *             if the element cannot be moved to the intended direction
	 * @see BoardListImpl#calculateContainerIdx(int, int)
	 * @see BoardListImpl#getEmptyTilePosition()
	 */
	private void move(final Move direction) {
		if (direction == null) {
			throw new InvalidMoveException("Cannot perform move operation with null direction");
		}

		int rowIdxDif = 0;
		int colIdxDif = 0;
		switch (direction) {
		case UP:
			rowIdxDif = -1;
			break;
		case DOWN:
			rowIdxDif = 1;
			break;
		case LEFT:
			colIdxDif = -1;
			break;
		case RIGHT:
			colIdxDif = 1;
			break;
		default:
			throw new IllegalArgumentException("Direction enum '" + direction.name() + "' is not handled");
		}

		try {
			final Position oldPosition = getEmptyTilePosition();
			checkForValidIndex(oldPosition.rowIdx, oldPosition.colIdx);
			move((oldPosition.rowIdx + rowIdxDif), (oldPosition.colIdx + colIdxDif));
		} catch (InvalidBoardIndexException e) {
			throw new InvalidMoveException("Cannot move to the intended direction. direction: " + direction.name(), e);
		}
	}

	/**
	 * Compares this instance to another by comparing the size of the board.
	 */
	public int compareTo(Board<T> o) {
		return Integer.valueOf(size)
						.compareTo(o.size());
	}

	@Override
	public int calculateParity() {
		final Position position = getEmptyTilePosition();
		final int emptyTileIdx = calculateContainerIdx(position.rowIdx, position.colIdx);
		int parity = position.rowIdx;
		for (int i = 0; i < container.size(); i++) {
			// ignore empty tile
			if (i != emptyTileIdx) {
				for (int j = 0; j < i; j++) {
					// ignore empty tile
					if (j != emptyTileIdx) {
						parity += (container.get(j)
											.compareTo(container.get(i)) > 0) ? 1 : 0;
					}
				}
			}
		}
		return parity;
	}

	@Override
	public Board<T> clone() {
		return new BoardListImpl<T>(size, container);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((container == null) ? 0 : container.hashCode());
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
		BoardListImpl<T> other = (BoardListImpl<T>) obj;
		if (container == null) {
			if (other.container != null)
				return false;
		} else if (!container.equals(other.container))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final List<T> copy = new ArrayList<T>(container);
		final int length = copy.stream()
								.max(Comparator.comparing(item -> ((item != null) ? item.toString()
																						.length() : 0)))
								.get()
								.toString()
								.length();
		final StringBuilder sb = new StringBuilder(container.size() * length * 2);
		sb.append(System.lineSeparator());
		for (int i = 0; i < size(); i++) {
			final List<T> result = copy.stream()
										.limit(size())
										.collect(Collectors.toList());
			sb.append(result.stream()
							.map(new Function<T, String>() {
								public String apply(T t) {
									final String value;
									int l = ((length > 1) && (length % 2 != 0)) ? (length + 1) : length;
									if (t != null) {
										value = t.toString();
									} else {
										value = " ";
									}
									return new StringBuilder("[").append(StringUtils.center(value, l))
																	.append("]")
																	.toString();
								};
							})
							.collect(Collectors.joining(" ")))
				.append(System.lineSeparator());
			copy.removeAll(result);
		}
		return sb.toString();
	}
}
