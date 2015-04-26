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

@RunWith(JUnit4.class)
public class IsValidTest extends AbstractTest {

	private List<Integer> container;
	private Board<Integer> board;
	private static final int SIZE = 40;
	private static final int CONTAINER_SIZE = (SIZE * SIZE);

	@Before
	public void init() {
		container = new ArrayList<Integer>(SIZE);
		container.add(null);
		for (int i = 0; i < (CONTAINER_SIZE - 1); i++) {
			container.add(i + 1);
		}
	}

	@Test
	public void multipleNull() {
		container.set(CONTAINER_SIZE - 1, null);
		board = new BoardImpl<Integer>(SIZE, container);
		assertFalse(board.isValid());
	}

	@Test
	public void multipleNumber() {
		container.set(CONTAINER_SIZE - 1, container.get(CONTAINER_SIZE / 2));
		board = new BoardImpl<Integer>(SIZE, container);
		assertFalse(board.isValid());
	}

	@Test
	public void valid() {
		board = new BoardImpl<Integer>(SIZE, container);
		assertTrue(board.isValid());
	}
}
