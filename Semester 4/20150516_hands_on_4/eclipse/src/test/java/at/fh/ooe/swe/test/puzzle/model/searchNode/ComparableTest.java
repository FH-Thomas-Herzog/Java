package at.fh.ooe.swe.test.puzzle.model.searchNode;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;
import at.fh.ooe.swe4.puzzle.model.SearchNode;

/**
 * This test class tests the method {@link SearchNode#compareTo(SearchNode)}.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 28, 2015
 */
@RunWith(JUnit4.class)
public class ComparableTest extends AbstractTest {

	@Test
	public void validAllDifferentCosts() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<>(size, container);
		final List<SearchNode<Integer>> orderedNodes = new ArrayList<SearchNode<Integer>>();
		IntStream.range(0, 10)
					.forEachOrdered(new IntConsumer() {

						@Override
						public void accept(int value) {
							final SearchNode<Integer> node = new SearchNode<>(value, null, board, board, null);
							orderedNodes.add(node);
						}
					});
		final List<SearchNode<Integer>> shuffledNodes = new ArrayList<SearchNode<Integer>>(orderedNodes);
		Collections.shuffle(shuffledNodes);
		Collections.sort(shuffledNodes, SearchNode::compareTo);

		// -- When --
		IntStream.range(0, shuffledNodes.size())
					.forEachOrdered(new IntConsumer() {

						@Override
						public void accept(int i) {
							// - Then --
							assertEquals(shuffledNodes.get(i)
														.getCostsFormStart(), i);
						}
					});
	}

}
