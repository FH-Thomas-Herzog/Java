package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.exception.InvalidBoardIndexException;
import at.fh.ooe.swe4.puzzle.impl.BoardListImpl;

/**
 * This test class tests the method {@link Board#setTile(int, int, Number)} This
 * test depends on proper function of the method<br>
 * {@link Board#getTile(int, int)} method which is used to get the added value
 * from the board
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
@RunWith(JUnit4.class)
public class SetTileTest extends AbstractTest {

	// -- Then --
	@Test(expected = InvalidBoardIndexException.class)
	public void invalidRowIndexUnderflow() {
		// -- Given --
		final int size = 10;
		final int rowIdx = 0;
		final int colIdx = 1;
		final Board<Integer> board = new BoardListImpl<>(size);

		// When
		board.setTile(rowIdx, colIdx, 1);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidColumnIndexUnderflow() {
		// -- Given --
		final int size = 10;
		final int rowIdx = 1;
		final int colIdx = 0;
		final Board<Integer> board = new BoardListImpl<>(size);

		// When
		board.setTile(rowIdx, colIdx, 1);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidRowIndexOverflow() {
		// -- Given --
		final int size = 10;
		final int rowIdx = size + 1;
		final int colIdx = 1;
		final Board<Integer> board = new BoardListImpl<>(size);

		// When
		board.setTile(rowIdx, colIdx, 1);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidColumnIndexOverflow() {
		// -- Given --
		final int size = 10;
		final int rowIdx = 1;
		final int colIdx = size + 1;
		final Board<Integer> board = new BoardListImpl<>(size);

		// When
		board.setTile(rowIdx, colIdx, 1);
	}

	/**
	 * Tests if all elements can be set on the board.
	 */
	public void valid() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer(size);
		container.set(0, null);
		final Board<Integer> board = new BoardListImpl<>(size);

		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				final int idx = (((i - 1) * size) + (j - 1));
				final Integer value = container.get(idx);

				// -- When --
				board.setTile(i, j, value);

				// -- Then --
				assertEquals(value, board.getTile(i, j));
			}
		}
	}
}
