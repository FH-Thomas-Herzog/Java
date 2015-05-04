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
import at.fh.ooe.swe4.puzzle.impl.BoardListImpl;
import at.fh.ooe.swe4.puzzle.model.Position;
import at.fh.ooe.swe4.puzzle.model.SearchNode;

/**
 * This test class tests the method {@link SearchNode#estimatedCostsToTarget()}.<br>
 * This test class depends on proper functionality of the method
 * {@link Board#getTilePosition(Comparable)}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 27, 2015
 */
@RunWith(JUnit4.class)
public class EstimatedCostsToTargetTest extends AbstractTest {

	// -- Then --
	@Test(expected = IllegalArgumentException.class)
	public void nullGoalBaord() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardListImpl<>(size, container);
		final SearchNode<Integer> node = new SearchNode<>(0, null, board, null, null);

		// -- When --
		node.estimatedCostsToTarget();
	}

	// -- Then --
	@Test(expected = IllegalArgumentException.class)
	public void goalOfDifferentSize() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardListImpl<>(size, container);
		final int goalSize = size + 1;
		final List<Integer> goalContainer = createContainer((int) Math.pow(goalSize, 2));
		goalContainer.set(0, null);
		final Board<Integer> goal = new BoardListImpl<>(goalSize, goalContainer);
		final SearchNode<Integer> node = new SearchNode<>(0, null, board, goal, null);

		// -- When --
		node.estimatedCostsToTarget();
	}

	@Test
	public void validZeroDistance() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardListImpl<>(size, container);
		final Board<Integer> goal = new BoardListImpl<>(size, container);
		final SearchNode<Integer> node = new SearchNode<>(0, null, board, goal, null);

		// -- When --
		final int cost = node.estimatedCostsToTarget();

		// -- Then --
		assertEquals(0, cost);
	}

	@Test
	public void validShuffledGoal() {
		IntStream.range(0, 10)
					.forEach(new IntConsumer() {
						@Override
						public void accept(int iterationCount) {
							// -- Given --
							final int size = 10;
							final List<Integer> container = createContainer((int) Math.pow(size, 2));
							container.set(0, null);
							final Board<Integer> board = new BoardListImpl<>(size, container);
							final List<Integer> goalContainer = new ArrayList<Integer>(container);
							Collections.shuffle(goalContainer);
							final Board<Integer> goal = new BoardListImpl<>(board.size(), goalContainer);
							final SearchNode<Integer> node = new SearchNode<>(0, null, board, goal, null);
							int costs = 0;
							for (int i = 1; i <= board.size(); i++) {
								for (int j = 1; j <= board.size(); j++) {
									final Integer value = board.getTile(i, j);
									if (value != null) {
										final Position position = goal.getTilePosition(value);
										// uses same algorithm as SearchNode via
										// static
										// final lambda member variable
										costs += SearchNode.CALC_MANHATTAN_DIST.apply(new Position(i, j), position);
									}
								}
							}

							// -- When --
							final int cost = node.estimatedCostsToTarget();

							// -- Then --
							assertEquals(costs, cost);
						}
					});
	}
}
