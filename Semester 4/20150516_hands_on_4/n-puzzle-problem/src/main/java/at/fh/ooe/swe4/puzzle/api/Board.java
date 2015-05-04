package at.fh.ooe.swe4.puzzle.api;

import java.util.NoSuchElementException;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import at.fh.ooe.swe4.puzzle.exception.InvalidBoardIndexException;
import at.fh.ooe.swe4.puzzle.exception.InvalidMoveException;
import at.fh.ooe.swe4.puzzle.model.Position;

/**
 * 
 * This is the interface specification for the puzzle board.<br>
 * The indices on a board start with 1 and not with 0.<br>
 * E.g.:<br>
 * A board with the dimension 4X4 are build as follows.
 * <ul>
 * <li><b>upper left corner:</b> (1,1)</li>
 * <li><b>lower right corner:</b> (4,4)</li>
 * </ul>
 * 
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 * @param <T>
 *            the value type of the values on the board
 */
public interface Board<T extends Comparable<T>> extends Comparable<Board<T>>, Cloneable {

	/**
	 * Specifies the directions the empty tile can be moved.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Apr 26, 2015
	 */
	public static enum Move {
		UP, DOWN, LEFT, RIGHT;
	}

	/**
	 * Returns the tile at the position (rowIdx, colIdx).
	 * 
	 * @param rowIdx
	 *            the row index
	 * @param colIdx
	 *            the column index.
	 * @return the found tile
	 * @throws InvalidBoardIndexException
	 *             if the indices are invalid
	 */
	public T getTile(int rowIdx, int colIdx);

	/**
	 * Sets an tile on the given position (rowIdx, colIdx).
	 * 
	 * @param rowIdx
	 *            the row index
	 * @param colIdx
	 *            the column index
	 * @param value
	 *            the value to be set on the defined position
	 * @throws InvalidBoardIndexException
	 *             if the indices are invalid
	 */
	public void setTile(int rowIdx, int colIdx, T value);

	/**
	 * Sets an empty tile on the given position (rowIdx, colIdx).
	 * 
	 * @param rowIdx
	 *            the row index
	 * @param colIdx
	 *            the column index
	 * @throws InvalidBoardIndexException
	 *             if the indices are invalid
	 */
	public void setEmptyTile(int rowIdx, int colIdx);

	/**
	 * Gets position of the empty tile (rowIdx, colIdx).<br>
	 * If multiple empty tiles are present on this board then this method will
	 * return the first occurrence of an empty tile.<br>
	 * If no empty tile is present the returned position instance will contain
	 * invalid indices.
	 * 
	 * @return the position model with the indices, it will have the indices set
	 *         to (-1, -1) if no empty tile could not be found
	 */
	public Position getEmptyTilePosition();

	/**
	 * Gets the Position of the tile with the given value.
	 * 
	 * @param value
	 *            the value to be searched on this board.
	 * @return the found tile position
	 * @throws NoSuchElementException
	 *             if the value could not be found on the board
	 */
	public Position getTilePosition(final T value);

	/**
	 * Gets the size of the board N where the board will have the dimensions N x
	 * N
	 * 
	 * @return the size of this board
	 */
	public int size();

	/**
	 * Answers the question if this board is a valid board.<br>
	 * A board is invalid if one of the following conditions fit
	 * <ul>
	 * <li>no empty tile present</li>
	 * <li>tiles with duplicates values are present</li>
	 * </ul>
	 * 
	 * @return true if this board is valid, false otherwise
	 */
	public boolean isValid();

	/**
	 * Shuffles the board tiles by performing random moves on the board.<br>
	 * It is ensured that the parity is kept (odd/even parity will be kept).
	 */
	public void shuffle();

	/**
	 * Moves the empty tile to the given position by switching the value on the
	 * given position with the empty tile position.<br>
	 * This method does not ensure that the move is a valid one, which means it
	 * is not ensured that the tile is only moved one position in any direction.
	 * 
	 * @param rowIdx
	 *            the row index
	 * @param colIdx
	 *            the column index
	 * @throws InvalidBoardIndexException
	 *             if either the indices or the board is invalid
	 */
	public void move(int rowIdx, int colIdx);

	/**
	 * Moves the empty tile on step left.
	 * 
	 * @throws InvalidMoveException
	 *             if the empty tile is already placed on the outer left column
	 */
	public void moveLeft();

	/**
	 * Moves the empty tile on step left.
	 * 
	 * @throws InvalidMoveException
	 *             if the empty tile is already placed on the outer right column
	 */
	public void moveRight();

	/**
	 * Moves the empty tile on step right.
	 * 
	 * @throws InvalidMoveException
	 *             if the empty tile is already placed on the top row
	 */
	public void moveUp();

	/**
	 * Moves the empty tile on step down.
	 * 
	 * @throws InvalidMoveException
	 *             if the empty tile is already placed on the bottom row
	 */
	public void moveDown();

	/**
	 * Performs all of the moves of the empty tile defined by the given Iterable
	 * instance.
	 * 
	 * @param moves
	 *            the Iterable instance holding the move positions for the empty
	 *            tile
	 * @throws InvalidMoveException
	 *             if the empty tile is tried to be moved out of the board
	 */
	public void makeMoves(Iterable<Move> moves);

	/**
	 * Calculates the parity of this board.<br>
	 * The parity is build as follows: <br>
	 * <sum_of_ordered_pairs> + <row_idx_empty_tile>
	 * 
	 * @return the parity of this board
	 * @throws IllegalStateException
	 *             if this board is invalid
	 */
	public int calculateParity();

	// Force overwrite of clone
	/**
	 * Performs an deep copy of the current instance.
	 * 
	 * @return the copied instance
	 */
	public Board<T> clone();
}
