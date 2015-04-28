package at.fh.ooe.swe.test.puzzle.impl.searchNode;

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

	@Test(expected = IllegalArgumentException.class)
	public void boardNull() {
		new SearchNode<Integer>((Board<Integer>) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidBoard() {
		final Board<Integer> board = new BoardImpl<Integer>(SIZE, createContainer(CONTAINER_SIZE));
		new SearchNode<Integer>(board);
	}

	@Test
	public void valid() {
		final List<Integer> container = createContainer(CONTAINER_SIZE);
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		final SearchNode<Integer> node = new SearchNode<Integer>(board);
		assertNotNull(node);
		assertTrue(board.equals(node.getBoard()));
		assertEquals(0, node.getCostsFormStart());
	}
}
