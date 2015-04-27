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

/**
 * This test class tests the method {@link Board#equals(Object)}.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at> Apr 27, 2015
 */
@RunWith(JUnit4.class)
public class EqualsTest extends AbstractTest {

	private Board<Integer> board;
	private List<Integer> container;

	@Before
	public void init() {
		container = createContainer(CONTAINER_SIZE);
		board = new BoardImpl<Integer>(SIZE, container);
	}

	@Test
	public void notEqualNull() {
		assertFalse(board.equals(null));
	}

	@Test
	public void notEqualNotSameInstance() {
		assertFalse(board.equals(new ArrayList<Integer>()));
	}

	@Test
	public void notEqualDifferentSize() {
		final int size = SIZE + 10;
		final List<Integer> otherContainer = createContainer((int) Math.pow(size, 2));
		final Board<Integer> other = new BoardImpl<Integer>(size, otherContainer);
		assertFalse(board.equals(other));
	}

	@Test
	public void notEqualContainerElements() {
		container.set(0, container.get(SIZE - 1));
		final Board<Integer> other = new BoardImpl<Integer>(SIZE, container);
		assertFalse(board.equals(other));
	}

	@Test
	public void isEqual() {
		final Board<Integer> other = new BoardImpl<Integer>(board.size(), container);
		assertTrue(board.equals(other));
	}
}
