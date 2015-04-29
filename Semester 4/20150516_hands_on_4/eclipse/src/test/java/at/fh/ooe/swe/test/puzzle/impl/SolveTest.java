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

	@Before
	public void init() {
	}

	@Test
	public void test() throws NoSolutionExcption {
		List<Integer> container = createContainer(2 * 2);
		container.set(0, null);
		final Board<Integer> goal = new BoardImpl<>(2, container);
		final List<Integer> other = new ArrayList<>();
		other.add(null);
		other.add(4);
		other.add(2);
		other.add(3);
		final Board<Integer> initial = new BoardImpl<>(2, other);
		new SlidingPuzzleImpl<Integer>(initial).solve(goal);
	}
}
