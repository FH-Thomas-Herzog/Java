package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.exception.InvalidBoardIndexException;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;

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

	private List<Integer> container;
	private Board<Integer> board;

	@Before
	public void init() {
		container = createContainer(CONTAINER_SIZE);
		container.set(0, null);
		board = new BoardImpl<Integer>(SIZE);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidRowIndexUnderflow() {
		board.setTile(0, 1, 1);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidColumnIndexUnderflow() {
		board.setTile(1, 0, 1);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidRowIndexOverflow() {
		board.setTile((board.size() + 1), 1, 1);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidColumnIndexOverflow() {
		board.setTile(1, (board.size() + 1), 1);
	}

	public void valid() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				final int idx = ((i * SIZE) + j);
				final Integer value = container.get(idx);
				board.setTile((i + 1), (j + 1), value);
				assertEquals(value, board.getTile((i + 1), (j + 1)));
			}
		}
	}
}
