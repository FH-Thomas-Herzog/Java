package at.fh.ooe.swe4.puzzle.api;

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
public interface Board<T extends Number> extends Comparable<Board<T>>, Cloneable {

	/**
	 * Specifies the directions the empty tile can be moved.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Apr 26, 2015
	 */
	public static enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	/**
	 * Returns the tile at the position i, j.
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
	 * Sets an tile on the given position.
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
	 * Sets an empty tile on the given position.
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
	 * Gets the column index of the empty tile.<br>
	 * If multiple empty tiles are present on this board then this method will
	 * return the first occurrence of an empty tile.
	 * 
	 * @return the position model with the indices, it will have the indices set
	 *         to -1 if the empty tile could not be found
	 */
	public Position getEmptyTilePosition();

	/**
	 * Gets the size of the board, where the column index is equals to the row
	 * index.
	 * 
	 * @return the size of this board
	 */
	public int size();

	/**
	 * Answers the question if this board is a valid board.
	 * 
	 * @return true if this board is valid, false otherwise
	 */
	public boolean isValid();

	/**
	 * Shuffles all of the tiles on the boards, which means they get randomly
	 * repositioned on the board.
	 */
	public void shuffle();

	/**
	 * Moves the empty tile to the given position by switching the value on the
	 * given position with the empty tile position.
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
	 * Performs all of the moves of the empty tile defined by the given iterable
	 * instance.
	 * 
	 * @param moves
	 *            the iterable instance holding the move positions for the empty
	 *            tile
	 * @throws InvalidMoveException
	 *             if the empty tile is tried to be moved out of the board
	 */
	public void makesMoves(Iterable<Direction> moves);

	// Force overwrite of clone
	public Board<T> clone();
}
