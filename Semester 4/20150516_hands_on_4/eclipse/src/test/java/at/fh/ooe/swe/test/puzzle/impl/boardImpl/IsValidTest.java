package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;

/**
 * This test class tests the method {@link Board#isValid()}.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
@RunWith(JUnit4.class)
public class IsValidTest extends AbstractTest {

	private List<Integer> container;

	@Before
	public void init() {
		container = createContainer(CONTAINER_SIZE);
	}

	@Test
	public void multipleNull() {
		container.set(0, null);
		container.set(CONTAINER_SIZE - 1, null);
		Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		assertFalse(board.isValid());
	}

	@Test
	public void multipleNumber() {
		container.set(CONTAINER_SIZE - 1, container.get(CONTAINER_SIZE / 2));
		Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		assertFalse(board.isValid());
	}

	@Test
	public void noEmptyTile() {
		Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		assertFalse(board.isValid());
	}

	@Test
	public void valid() {
		container.set(0, null);
		Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		assertTrue(board.isValid());
	}
}
