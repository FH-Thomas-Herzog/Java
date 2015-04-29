/**
 * 
 */
package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;
import at.fh.ooe.swe4.puzzle.model.Position;

/**
 * This test class tests the method {@link Board#getEmptyTilePosition()}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
@RunWith(JUnit4.class)
public class GetEmptyTilePositionTest extends AbstractTest {

	@Test
	public void multipleEmptyTiles() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		container.set(1, null);
		final Board<Integer> board = new BoardImpl<>(size, container);

		// -- When --
		final Position position = board.getEmptyTilePosition();

		// -- Then --
		assertEquals(new Position(1, 1), position);
	}

	@Test
	public void noEmptyTiles() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardImpl<>(size, container);

		// -- When --
		final Position position = board.getEmptyTilePosition();

		// -- Then --
		assertEquals(new Position(-1, -1), position);
	}

	/**
	 * Moves the empty tile over the whole board and checks if the position is
	 * properly determined from each set position.
	 */
	@Test
	public void validAllRowsAndColumns() {
		// -- Given --
		final int size = 10;
		int oldIdx = 0;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				final int idx = ((i * size) + j);
				final List<Integer> container = createContainer((int) Math.pow(size, 2));
				container.set(oldIdx, container.get(idx));
				container.set(idx, null);
				oldIdx = idx;
				final Board<Integer> board = new BoardImpl<>(size, container);

				// -- When --
				final Position position = board.getEmptyTilePosition();

				// -- Then --
				assertEquals(new Position((i + 1), (j + 1)), position);
			}
		}
	}
}
