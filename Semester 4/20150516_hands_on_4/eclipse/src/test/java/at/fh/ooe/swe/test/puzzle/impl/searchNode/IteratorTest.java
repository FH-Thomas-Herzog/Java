package at.fh.ooe.swe.test.puzzle.impl.searchNode;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;
import at.fh.ooe.swe4.puzzle.model.SearchNode;

/**
 * This test class tests the method {@link SearchNode#iterator()} which returns
 * an iterator instance.<br>
 * So this test class test the iterator implementation which has been
 * implemented as inner class.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 28, 2015
 */
@RunWith(JUnit4.class)
public class IteratorTest extends AbstractTest {

	private List<Integer> container;
	private Board<Integer> board;

	@Before
	public void init() {
		container = createContainer(CONTAINER_SIZE);
		container.set(0, null);
		board = new BoardImpl<>(SIZE, container);
	}

	@Test(expected = NoSuchElementException.class)
	public void overflow() {
		final SearchNode<Integer> node = new SearchNode<Integer>(board);
		final Iterator<SearchNode<Integer>> it = node.iterator();
		while (true) {
			it.next();
		}
	}

	@Test
	public void noPredecessors() {
		final SearchNode<Integer> node = new SearchNode<Integer>(board);
		final Iterator<SearchNode<Integer>> it = node.iterator();
		int count = 0;
		while (it.hasNext()) {
			it.next();
			count++;
		}
		assertEquals(1, count);
	}

	@Test
	public void valid() {
		final SearchNode<Integer> node = new SearchNode<Integer>(board);
		final List<SearchNode<Integer>> nodes = new ArrayList<SearchNode<Integer>>();
		nodes.add(node);
		SearchNode<Integer> tmp = node;
		int expectedCount = 1;
		for (int i = 0; i < 10; i++) {
			final SearchNode<Integer> predecessor = new SearchNode<Integer>(board);
			tmp.setPredecessor(predecessor);
			tmp = predecessor;
			nodes.add(predecessor);
			expectedCount++;
		}
		final Iterator<SearchNode<Integer>> it = node.iterator();
		int count = 0;
		while (it.hasNext()) {
			final SearchNode<Integer> itNode = it.next();
			assertEquals(itNode, nodes.get(count));
			count++;
		}
		assertEquals(expectedCount, count);
	}
}
