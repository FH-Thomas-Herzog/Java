package at.fh.ooe.swe.test.puzzle.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.SlidingPuzzle;
import at.fh.ooe.swe4.puzzle.exception.NoSolutionExcption;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;
import at.fh.ooe.swe4.puzzle.impl.SlidingPuzzleImpl;

/**
 * This test class test the method
 * {@link SlidingPuzzle#solve(at.fh.ooe.swe4.puzzle.api.Board)}.<br>
 * This test class depends on proper functionality of the constructor and init
 * method
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 28, 2015
 */
@RunWith(JUnit4.class)
public class SolveTest extends AbstractTest {

	private Board<Integer> goal;
	private List<Integer> container;

	@Before
	public void init() {
		container = createContainer(CONTAINER_SIZE);
		container.set(0, null);
		goal = new BoardImpl<Integer>(SIZE, container);
	}

	@Test
	public void test() throws NoSolutionExcption {
		goal.toString();
		final List<Integer> other = new ArrayList<Integer>(container);
		other.set(0, other.get(SIZE - 1));
		other.set(SIZE - 1, null);
		final Board<Integer> initial = new BoardImpl<Integer>(SIZE, other);
		new SlidingPuzzleImpl<Integer>(initial).solve(goal);
	}
}
