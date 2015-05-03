package at.fh.ooe.swe.test.puzzle.model.searchNode;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardListImpl;
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
		new SearchNode<>(0, null, null, board, null);
	}

	// -- Then --
	@Test(expected = IllegalArgumentException.class)
	public void goalNull() {
		// -- Given
		final Board<Integer> board = null;

		// -- When --
		new SearchNode<>(0, null, board, null, null);
	}

	@Test
	public void valid() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardListImpl<>(size, container);
		final List<Integer> goalContainer = createContainer((int) Math.pow(size, 2));
		goalContainer.set(0, null);
		final Board<Integer> goal = new BoardListImpl<>(size, goalContainer);

		// -- When --
		final SearchNode<Integer> node = new SearchNode<>(0, null, board, goal, null);

		// -- Then --
		assertNotNull(node);
		assertTrue(board.equals(node.getBoard()));
		assertEquals(0, node.getCostsFormStart());
	}
}
