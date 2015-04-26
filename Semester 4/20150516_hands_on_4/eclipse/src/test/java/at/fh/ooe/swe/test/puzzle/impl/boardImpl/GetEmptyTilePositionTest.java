/**
 * 
 */
package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
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

	private Board<Integer> board;
	private List<Integer> container;

	private static final int SIZE = 40;
	private static final int CONTAINER_SIZE = (SIZE * SIZE);

	@Before
	public void init() {
		container = new ArrayList<Integer>(CONTAINER_SIZE);
		for (int i = 0; i < CONTAINER_SIZE; i++) {
			container.add(i + 1);
		}
	}

	@Test
	public void multipleEmptyTiles() {
		container.set(0, null);
		container.set((container.size() / 2), null);
		board = new BoardImpl<Integer>(SIZE, container);
		assertEquals(new Position(1, 1), board.getEmptyTilePosition());
	}

	@Test
	public void noEmptyTiles() {
		board = new BoardImpl<Integer>(SIZE, container);
		assertEquals(new Position(-1, -1), board.getEmptyTilePosition());
	}

	@Test
	public void validAllRowsAndColumns() {
		int oldIdx = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				final int idx = ((i * SIZE) + j);
				container.set(oldIdx, container.get(idx));
				container.set(idx, null);
				oldIdx = idx;
				board = new BoardImpl<Integer>(SIZE, container);
				assertEquals(new Position((i + 1), (j + 1)), board.getEmptyTilePosition());
			}
		}
	}
}
