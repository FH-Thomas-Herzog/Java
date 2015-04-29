package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;
import at.fh.ooe.swe4.puzzle.model.Position;

/**
 * This test class test the method {@link Board#getTilePosition(Comparable)}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at> Apr 27, 2015
 */
@RunWith(JUnit4.class)
public class GetTilePositionTest extends AbstractTest {

	// -- Then --
	@Test(expected = NoSuchElementException.class)
	public void noEmptyTile() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardImpl<>(size, container);

		// -- When --
		board.getTilePosition(null);
	}

	// -- Then --
	@Test(expected = NoSuchElementException.class)
	public void noSuchElement() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardImpl<>(size, container);

		// -- When --
		board.getTilePosition(Integer.MAX_VALUE);
	}

	/**
	 * Tests if each tile an be retrieved via tested method.
	 */
	@Test
	public void validAllTiles() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardImpl<>(size, container);

		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				final Integer value = container.get(((i - 1) * size) + (j - 1));

				// -- When --
				final Position position = board.getTilePosition(value);

				// -- Then --
				assertEquals(i, position.rowIdx);
				assertEquals(j, position.colIdx);
			}
		}
	}
}
