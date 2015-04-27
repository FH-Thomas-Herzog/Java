package at.fh.ooe.swe4.puzzle.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.exception.InvalidBoardIndexException;
import at.fh.ooe.swe4.puzzle.exception.InvalidMoveException;
import at.fh.ooe.swe4.puzzle.model.Position;

public class BoardImpl<T extends Comparable> implements Board<T> {

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
	public BoardImpl(final int size) {
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
	 * values.<br>
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
	 *             if the size does not correspond to the container size
	 */
	public BoardImpl(final int size, final List<T> container) {
		super();
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be greater than 0");
		}
		if (container == null) {
			throw new IllegalArgumentException("Container must not be null");
		}
		if ((size * size) != container.size()) {
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
		final int[] indices = IntStream.range(0, (int) Math.pow(size, 2)).filter(i -> container.get(i) == null)
				.toArray();
		if (indices.length > 0) {
			final int rowIdx = ((indices[0] / size) + 1);
			final int colIdx = (indices[0] - ((rowIdx - 1) * size) + 1);
			return new Position(rowIdx, colIdx);
		} else {
			return new Position(-1, -1);
		}
	}

	@Override
	public Position getTilePosition(final T value) {
		for (int i = 1; i <= size(); i++) {
			for (int j = 1; j <= size(); j++) {
				final T tile = getTile(i, j);
				if (((tile == null) && (value == null)) || ((tile != null) && (tile.equals(value)))) {
					return new Position(i, j);
				}
			}
		}
		throw new NoSuchElementException("Tile with value '" + ((value == null) ? "null" : value.toString())
				+ "' does not exist on the board");
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isValid() {
		return (container.stream().distinct().count() == ((int) Math.pow(size, 2)))
				&& (container.stream().filter(element -> element == null).count() == 1);
	}

	@Override
	public void shuffle() {
		Collections.shuffle(container);
	}

	@Override
	public void move(int rowIdx, int colIdx) {
		if (!isValid()) {
			throw new InvalidMoveException("Cannot perfom move of empty tile on invalid board");
		}
		final Position position = getEmptyTilePosition();
		final int idx = calculateContainerIdx(rowIdx, colIdx);
		final int emptyTileIdx = calculateContainerIdx(position.rowIdx, position.colIdx);
		container.set(emptyTileIdx, container.get(idx));
		container.set(idx, null);
	}

	@Override
	public void moveLeft() {
		move(Direction.LEFT);
	}

	@Override
	public void moveRight() {
		move(Direction.RIGHT);
	}

	@Override
	public void moveUp() {
		move(Direction.UP);
	}

	@Override
	public void moveDown() {
		move(Direction.DOWN);
	}

	@Override
	public void makesMoves(Iterable<Direction> moves) {
		if (moves == null) {
			throw new InvalidMoveException("Cannot perform moves because iterable instance is null");
		}
		moves.iterator().forEachRemaining(new Consumer<Direction>() {
			@Override
			public void accept(Direction t) {
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
			throw new InvalidBoardIndexException(
					"One of the indicies over or underlfows the border defined by size. rowIdx: " + rowIdx
							+ " | colIdx: " + colIdx);
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
	 * @see BoardImpl#checkForValidIndex(int, int)
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
	 * <li>the given direction is supported</li>
	 * </ul>
	 * 
	 * @param direction
	 *            the direction to move to
	 * @throws InvalidMoveException
	 *             if the element cannot be moved to the intended direction
	 * @see BoardImpl#calculateContainerIdx(int, int)
	 * @see BoardImpl#getEmptyTilePosition()
	 */
	private void move(final Direction direction) {
		if (!isValid()) {
			throw new InvalidMoveException("Cannot perform move on an invalid board");
		}
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
			final int oldIdx = calculateContainerIdx(oldPosition.rowIdx, oldPosition.colIdx);

			final Position newPosition = new Position((oldPosition.rowIdx + rowIdxDif),
					(oldPosition.colIdx + colIdxDif));
			final int newIdx = calculateContainerIdx(newPosition.rowIdx, newPosition.colIdx);

			container.set(oldIdx, container.get(newIdx));
			container.set(newIdx, null);
		} catch (InvalidBoardIndexException e) {
			throw new InvalidMoveException("Cannot move to the intended direction. direction: " + direction.name(), e);
		}
	}

	public int compareTo(Board<T> o) {
		return Integer.valueOf(this.size).compareTo(o.size());
	}

	@Override
	public String toString() {
		return container.toString();
	}

	@Override
	public Board<T> clone() {
		return new BoardImpl<T>(size, container);
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
		BoardImpl<T> other = (BoardImpl<T>) obj;
		if (container == null) {
			if (other.container != null)
				return false;
		} else if (!container.equals(other.container))
			return false;
		return true;
	}

}
