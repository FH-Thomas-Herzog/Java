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
 * This class test the method {@link Board#getTile(int, int)}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
@RunWith(JUnit4.class)
public class GetTileTest extends AbstractTest {

	// -- Then --
	@Test(expected = InvalidBoardIndexException.class)
	public void invalidRowIndexUnderflow() {
		// -- Given --
		final int size = 10;
		final int rowIdx = 0;
		final int colIdx = 1;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);

		// -- When --
		board.getTile(rowIdx, colIdx);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidColumnIndexUnderflow() {
		// -- Given --
		final int size = 10;
		final int rowIdx = 1;
		final int colIdx = 0;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);

		// -- When --
		board.getTile(rowIdx, colIdx);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidRowIndexOverflow() {
		// -- Given --
		final int size = 10;
		final int rowIdx = size + 1;
		final int colIdx = 1;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);

		// -- When --
		board.getTile(rowIdx, colIdx);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidColumnIndexOverflow() {
		// -- Given --
		final int size = 10;
		final int rowIdx = 1;
		final int colIdx = size + 1;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);

		// -- When --
		board.getTile(rowIdx, colIdx);
	}

	/**
	 * Tests if all retrieved tiles of the board can be retrieved validly.
	 */
	@Test
	public void validAllRowsAndColumns() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);

		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				final int idx = (((i - 1) * size) + (j - 1));

				// -- When --
				final Integer value = board.getTile(i, j);

				// -- Then --
				assertEquals(container.get(idx), value);
			}
		}
	}
}
