package at.fh.ooe.swe.test.puzzle.model.searchNode;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardListImpl;
import at.fh.ooe.swe4.puzzle.model.SearchNode;

/**
 * This test class contains the provided test which have been modified to fit
 * actual implementation but the semantics has been kept.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 1, 2015
 */
@RunWith(JUnit4.class)
public class FHProvidedTest extends AbstractTest {

	private Board<Integer> goal;

	@Before
	public void init() {
		goal = new BoardListImpl<>(3);
		goal.setTile(1, 1, 1);
		goal.setTile(1, 2, 2);
		goal.setTile(1, 3, 3);
		goal.setTile(2, 1, 4);
		goal.setTile(2, 2, 5);
		goal.setTile(2, 3, 6);
		goal.setTile(3, 1, 7);
		goal.setTile(3, 2, 8);
		goal.setTile(3, 3, null);
	}

	@Test
	public void test1() {
		// -- Given --
		final Board<Integer> initial = new BoardListImpl<>(3);
		initial.setTile(1, 1, 1);
		initial.setTile(1, 2, 2);
		initial.setTile(1, 3, 3);
		initial.setTile(2, 1, 4);
		initial.setTile(2, 2, 5);
		initial.setTile(2, 3, 6);
		initial.setTile(3, 1, 7);
		initial.setTile(3, 2, 8);
		initial.setTile(3, 3, null);
		SearchNode<Integer> node = new SearchNode<>(0, null, initial, goal, null);

		// -- When --
		final int costs = node.estimatedCostsToTarget();

		// -- Then --
		assertEquals(0, costs);
	}

	@Test
	public void test2() {
		// -- Given --
		final Board<Integer> initial = new BoardListImpl<>(3);
		initial.setTile(1, 1, 1);
		initial.setTile(1, 2, 2);
		initial.setTile(1, 3, 3);
		initial.setTile(2, 1, 4);
		initial.setTile(2, 2, null);
		initial.setTile(2, 3, 6);
		initial.setTile(3, 1, 7);
		initial.setTile(3, 2, 8);
		initial.setTile(3, 3, 5);
		SearchNode<Integer> node = new SearchNode<>(0, null, initial, goal, null);

		// -- When --
		final int costs = node.estimatedCostsToTarget();

		// -- Then --
		assertEquals(2, costs);
	}

	@Test
	public void test3() {
		// -- Given --
		final Board<Integer> initial = new BoardListImpl<>(3);
		initial.setTile(1, 1, 1);
		initial.setTile(1, 2, null);
		initial.setTile(1, 3, 3);
		initial.setTile(2, 1, 4);
		initial.setTile(2, 2, 5);
		initial.setTile(2, 3, 6);
		initial.setTile(3, 1, 7);
		initial.setTile(3, 2, 8);
		initial.setTile(3, 3, 2);
		final SearchNode<Integer> node = new SearchNode<>(0, null, initial, goal, null);

		// -- When --
		final int costs = node.estimatedCostsToTarget();

		// -- Then --
		assertEquals(3, costs);
	}
}
