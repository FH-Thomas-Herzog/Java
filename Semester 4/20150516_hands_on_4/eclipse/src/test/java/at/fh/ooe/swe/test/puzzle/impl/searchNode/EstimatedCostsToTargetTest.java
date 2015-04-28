package at.fh.ooe.swe.test.puzzle.impl.searchNode;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;
import at.fh.ooe.swe4.puzzle.model.Position;
import at.fh.ooe.swe4.puzzle.model.SearchNode;

/**
 * This test class tests the method
 * {@link SearchNode#estimatedCostsToTarget(Board)}.<br>
 * This test class depends on proper functionality of the method
 * {@link Board#getTilePosition(Comparable)}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 27, 2015
 */
@RunWith(JUnit4.class)
public class EstimatedCostsToTargetTest extends AbstractTest {

	private SearchNode<Integer> node;
	private List<Integer> container;
	private Board<Integer> board;

	@Before
	public void init() {
		container = createContainer(CONTAINER_SIZE);
		container.set(0, null);
		board = new BoardImpl<Integer>(SIZE, container);
		node = new SearchNode<Integer>(board);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullGoalBaord() {
		node.estimatedCostsToTarget(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void goalOfDifferentSize() {
		final int size = SIZE + 1;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> goal = new BoardImpl<Integer>(size, container);
		node.estimatedCostsToTarget(goal);
	}

	@Test(expected = IllegalArgumentException.class)
	public void goalInvalid() {
		final List<Integer> container = createContainer(CONTAINER_SIZE);
		final Board<Integer> goal = new BoardImpl<Integer>(SIZE, container);
		node.estimatedCostsToTarget(goal);
	}

	@Test
	public void validZeroDistance() {
		final Board<Integer> goal = new BoardImpl<Integer>(board.size(), container);
		assertEquals(0, node.estimatedCostsToTarget(goal));
	}

	@Test
	public void validShuffledGoal() {
		IntStream.range(0, 10).forEach(new IntConsumer() {
			@Override
			public void accept(int iterationCount) {
				final List<Integer> goalContainer = new ArrayList<Integer>(container);
				Collections.shuffle(goalContainer);
				final Board<Integer> goal = new BoardImpl<Integer>(board.size(), goalContainer);
				int costs = 0;
				for (int i = 1; i <= board.size(); i++) {
					for (int j = 1; j <= board.size(); j++) {
						final Integer value = board.getTile(i, j);
						if (value != null) {
							final Position position = goal.getTilePosition(value);
							// uses same algorithm as SearchNode via static
							// final lambda member variable
							costs += SearchNode.CALC_MANHATTAN_DIST.apply(new Position(i, j), position);
						}
					}
				}
				assertEquals(costs, node.estimatedCostsToTarget(goal));
			}
		});
	}
}
