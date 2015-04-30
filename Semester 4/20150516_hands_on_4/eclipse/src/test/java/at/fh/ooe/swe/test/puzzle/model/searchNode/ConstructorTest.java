package at.fh.ooe.swe.test.puzzle.model.searchNode;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;
import at.fh.ooe.swe4.puzzle.model.SearchNode;

/**
 * This test class tests the provided constructors of the {@link SearchNode}
 * class.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 27, 2015
 */
@RunWith(JUnit4.class)
public class ConstructorTest extends AbstractTest {

	// -- Then --
	@Test(expected = IllegalArgumentException.class)
	public void boardNull() {
		// -- Given
		final Board<Integer> board = null;

		// -- When --
		new SearchNode<Integer>(board);
	}

	// -- Then --
	@Test(expected = IllegalArgumentException.class)
	public void invalidBoard() {
		// -- Given --
		final int size = 10;
		final Board<Integer> board = new BoardImpl<>(size, createContainer((int) Math.pow(size, 2)));

		// -- When --
		new SearchNode<Integer>(board);
	}

	@Test
	public void valid() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<>(size, container);

		// -- When --
		final SearchNode<Integer> node = new SearchNode<Integer>(board);

		// -- Then --
		assertNotNull(node);
		assertTrue(board.equals(node.getBoard()));
		assertEquals(0, node.getCostsFormStart());
	}
}
