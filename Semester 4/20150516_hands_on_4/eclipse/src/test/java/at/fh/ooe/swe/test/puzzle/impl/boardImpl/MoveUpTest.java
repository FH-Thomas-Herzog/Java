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
 * This test class tests the method {@link Board#moveUp()}.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public class MoveUpTest extends AbstractTest {

	// -- Then --
	@Test(expected = InvalidMoveException.class)
	public void invalidBoard() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);

		// -- When --
		board.moveUp();
	}

	@Test
	public void alreadyOnTopAllColumns() {
		final int size = 10;
		int oldIdx = 0;
		for (int i = 0; i < size; i++) {
			try {
				// -- Given --
				final List<Integer> container = createContainer((int) Math.pow(size, 2));
				container.set(oldIdx, container.get(i));
				container.set(i, null);
				oldIdx = i;
				final Board<Integer> board = new BoardListImpl<>(size, container);

				// -- When --
				board.moveUp();

				// -- Then -
				fail("Expected InvalidMoveException");
			} catch (InvalidMoveException e) {
				// Should throw exception
			}
		}
	}

	@Test
	public void validAllColsFormBottom() {
		// -- Given --
		final int size = 10;
		int lastRow = (size * (size - 1));
		for (int i = 1; i <= size; i++) {
			int idx = lastRow + (i - 1);
			List<Integer> container = createContainer((int) Math.pow(size, 2));
			container.set(idx, null);
			Board<Integer> board = new BoardListImpl<>(size, container);

			// -- When --
			for (int j = 1; j < size; j++) {
				board.moveUp();
			}

			// -- Then --
			final Position emptyTilePosition = board.getEmptyTilePosition();
			assertEquals(1, emptyTilePosition.rowIdx);
			assertEquals(i, emptyTilePosition.colIdx);
		}
	}
}
