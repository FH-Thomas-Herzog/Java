package at.fh.ooe.swe.test.puzzle.model.searchNode;

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

	@Before
	public void init() {
	}

	@Test
	public void notEqualNull() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<>(size, container);
		final SearchNode<Integer> node = new SearchNode<>(board);

		// -- When --
		final boolean result = node.equals(null);

		// -- Then --
		assertFalse(result);
	}

	@Test
	public void notEqualDifferentInstance() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<>(size, container);
		final SearchNode<Integer> node = new SearchNode<>(board);

		// -- When --
		final boolean result = node.equals(new Integer(0));

		// -- Then --
		assertFalse(result);
	}

	@Test
	public void notEqualNotSameBoard() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<>(size, container);
		final SearchNode<Integer> node = new SearchNode<>(board);
		final List<Integer> otherContainer = createContainer((int) Math.pow(size, 2));
		otherContainer.set(size - 1, null);
		final Board<Integer> other = new BoardImpl<>(size, otherContainer);

		// -- When --
		final boolean result = node.equals(new SearchNode<Integer>(other));

		// -- Then --
		assertFalse(result);
	}

	@Test
	public void equal() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<>(size, container);
		final SearchNode<Integer> node = new SearchNode<>(board);
		final Board<Integer> other = new BoardImpl<>(size, container);

		// -- When --
		final boolean result = node.equals(new SearchNode<Integer>(other));

		// -- Then --
		assertTrue(result);
	}
}
