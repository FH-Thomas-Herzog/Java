package at.fh.ooe.swe4.puzzle.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.exception.InvalidBoardIndexException;
import at.fh.ooe.swe4.puzzle.model.Position;

public class BoardImpl<T extends Number> implements Board<T> {

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
		container = new ArrayList<T>(this.size * this.size);
		// Init with null values
		for (int i = 0; i < this.size; i++) {
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

	public T getTile(int rowIdx, int colIdx) {
		return container.get(calculateContainerIdx(rowIdx, colIdx));
	}

	public void setTile(int rowIdx, int colIdx, T value) {
		container.set(calculateContainerIdx(rowIdx, colIdx), value);
	}

	public void setEmptyTile(int rowIdx, int colIdx) {
		setTile(rowIdx, colIdx, (T) null);
	}

	public int getEmptyTileColumn() {
		int idx = -1;
		for (int i = 0; i < container.size(); i++) {
			if (container.get(i) == null) {
				idx = i;
				break;
			}
		}
		return (idx != -1) ? ((idx - ((idx / size) * size)) + 1) : idx;
	}

	public int size() {
		return size;
	}

	public boolean isValid() {
		return new HashSet<T>(container).size() == (size * size);
	}

	public void shuffle() {
		// TODO Auto-generated method stub

	}

	public void move(int i, int j) {
		// TODO Auto-generated method stub

	}

	public void moveLeft() {
		// TODO Auto-generated method stub

	}

	public void moveRight() {
		// TODO Auto-generated method stub

	}

	public void moveUp() {
		// TODO Auto-generated method stub

	}

	public void moveDown() {
		// TODO Auto-generated method stub

	}

	public void makesMoves(Iterable<Position> moves) {
		// TODO Auto-generated method stub

	}

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
	 * @see BoardImpl#checkForValidIndex(int, int)
	 */
	private int calculateContainerIdx(final int rowIdx, final int colIdx) {
		checkForValidIndex(rowIdx, colIdx);
		return ((rowIdx - 1) * size) + (colIdx - 1);
	}

	public int compareTo(Board o) {
		return Integer.valueOf(this.size).compareTo(o.size());
	}
}
