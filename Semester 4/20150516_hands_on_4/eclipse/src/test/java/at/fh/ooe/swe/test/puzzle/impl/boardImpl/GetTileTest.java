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
 * This class test the method {@link Board#getTile(int, int)}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
@RunWith(JUnit4.class)
public class GetTileTest extends AbstractTest {

	private Board<Integer> board;
	private List<Integer> container;

	@Before
	public void init() {
		container = createContainer(CONTAINER_SIZE);
		container.set(0, null);
		board = new BoardImpl<Integer>(SIZE, container);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidRowIndexUnderflow() {
		board.getTile(0, 1);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidColumnIndexUnderflow() {
		board.getTile(1, 0);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidRowIndexOverflow() {
		board.getTile((board.size() + 1), 1);
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void invalidColumnIndexOverflow() {
		board.getTile(1, (board.size() + 1));
	}

	@Test
	public void validAllRowsAndColumns() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				final int idx = ((i * SIZE) + j);
				assertEquals(container.get(idx), board.getTile((i + 1), (j + 1)));
			}
		}
	}
}
