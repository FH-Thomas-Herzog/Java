package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.exception.InvalidMoveException;
import at.fh.ooe.swe4.puzzle.impl.BoardListImpl;
import at.fh.ooe.swe4.puzzle.model.Position;

/**
 * This test class tests the method {@link Board#moveDown()}.<br>
 * This test class depends on proper functionality of the method
 * {@link Board#getEmptyTilePosition()}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public class MoveDownTest extends AbstractTest {

	// -- Then --
	@Test(expected = InvalidMoveException.class)
	public void invalidBoard() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);

		board.moveDown();
	}

	@Test
	public void alreadyOnBottomAllColumns() {
		// -- Given --
		final int size = 10;
		int lastRow = (size * (size - 1));
		for (int i = 1; i <= size; i++) {
			try {
				int idx = lastRow + (i - 1);
				final List<Integer> container = createContainer((int) Math.pow(size, 2));
				container.set(idx, null);
				Board<Integer> board = new BoardListImpl<>(size, container);

				// -- When --
				board.moveDown();

				// -- Then --
				fail("Expected InvalidMoveException");
			} catch (InvalidMoveException e) {
				// Should throw exception
			}
		}
	}

	@Test
	public void validAllColsFromTop() {
		// -- Given --
		final int size = 10;
		for (int i = 1; i < size; i++) {
			final List<Integer> container = createContainer((int) Math.pow(size, 2));
			int idx = (i - 1);
			container.set(idx, null);
			final Board<Integer> board = new BoardListImpl<>(size, container);

			// -- When --
			for (int j = 1; j < size; j++) {
				board.moveDown();
			}

			// -- Then --
			final Position emptyTilePosition = board.getEmptyTilePosition();
			assertEquals(size, emptyTilePosition.rowIdx);
			assertEquals(i, emptyTilePosition.colIdx);
		}
	}
}
