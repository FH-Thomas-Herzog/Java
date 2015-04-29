package at.fh.ooe.swe.test.puzzle.impl.searchNode;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;
import at.fh.ooe.swe4.puzzle.model.SearchNode;

@RunWith(JUnit4.class)
public class EqualsTest extends AbstractTest {

	private SearchNode<Integer> node;
	private SearchNode<Integer> predecessor;
	private List<Integer> container;
	private Board<Integer> board;

	@Before
	public void init() {
		container = createContainer(CONTAINER_SIZE);
		container.set(0, null);
		board = new BoardImpl<>(SIZE, container);
		node = new SearchNode<Integer>(board);
		predecessor = new SearchNode<Integer>(board);
		node.setPredecessor(predecessor);
	}

	@Test
	public void notEqualNull() {
		assertFalse(node.equals(null));
	}

	@Test
	public void notEqualDifferentInstance() {
		assertFalse(node.equals(new Integer(0)));
	}

	@Test
	public void notEqualNotSameBoard() {
		Collections.shuffle(container);
		final Board<Integer> other = new BoardImpl<>(SIZE, container);
		assertFalse(node.equals(new SearchNode<Integer>(other)));
	}

	@Test
	public void equal() {
		final SearchNode<Integer> otherNode = new SearchNode<Integer>(board);
		otherNode.setPredecessor(predecessor);
		assertTrue(node.equals(otherNode));
	}
}
