package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.exception.InvalidMoveException;
import at.fh.ooe.swe4.puzzle.impl.BoardListImpl;
import at.fh.ooe.swe4.puzzle.model.Position;

/**
 * This test class tests the method {@link Board#moveLeft()}.<br>
 * This test class depends on proper functionality of method
 * {@link Board#getEmptyTilePosition()}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public class MoveLeftTest extends AbstractTest {

	// -- Then --
	@Test(expected = InvalidMoveException.class)
	public void invalidBoard() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);

		// -- When --
		board.moveLeft();
	}

	@Test
	public void alreadyLeftAllRows() {
		// -- Given --
		final int size = 10;
		for (int i = 1; i <= size; i++) {
			try {
				int idx = (size * (i - 1));
				final List<Integer> container = createContainer((int) Math.pow(size, 2));
				container.set(idx, null);
				Board<Integer> board = new BoardListImpl<>(size, container);

				// -- When --
				board.moveLeft();

				// -- Then --
				fail("Expected InvalidMoveException");
			} catch (InvalidMoveException e) {
				// should thorw exception
			}
		}
	}

	@Test
	public void validAllRowsFromRight() {
		// -- Given --
		final int size = 10;
		int oldIdx = ((size * size) - 1);
		for (int i = size; i > 0; i--) {
			int idx = (size * i) - 1;
			final List<Integer> container = createContainer((int) Math.pow(size, 2));
			container.set(idx, null);
			final Board<Integer> board = new BoardListImpl<>(size, container);

			// -- When --
			for (int j = 0; j < (size - 1); j++) {
				board.moveLeft();
			}

			// -- Then --
			final Position emptytilePosition = board.getEmptyTilePosition();
			assertEquals(i, emptytilePosition.rowIdx);
			assertEquals(1, emptytilePosition.colIdx);
		}
	}
}
