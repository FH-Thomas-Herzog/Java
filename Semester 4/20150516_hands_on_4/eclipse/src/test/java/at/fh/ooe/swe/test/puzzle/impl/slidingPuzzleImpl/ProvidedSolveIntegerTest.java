package at.fh.ooe.swe.test.puzzle.impl.slidingPuzzleImpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.Board.Move;
import at.fh.ooe.swe4.puzzle.exception.NoSolutionExcption;
import at.fh.ooe.swe4.puzzle.impl.BoardListImpl;
import at.fh.ooe.swe4.puzzle.impl.SlidingPuzzle;

/**
 * This test class contains the provided tests for testing the method
 * {@link SlidingPuzzle#solve(at.fh.ooe.swe4.puzzle.api.Board)}.<br>
 * Therefore that theses test resolving enough no further tests this method need
 * to implemented.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 1, 2015
 */
@RunWith(JUnit4.class)
public class ProvidedSolveIntegerTest extends AbstractTest {

	private Board<Integer> goal3;
	private Board<Integer> goal4;

	@Before
	public void init() {
		// 3 x 3 board
		final List<Integer> container3 = createContainer((int) Math.pow(3, 2));
		container3.set(container3.size() - 1, null);
		goal3 = new BoardListImpl<>(3, container3);

		// 4 x 4 board
		final List<Integer> container4 = createContainer((int) Math.pow(4, 2));
		container4.set(container4.size() - 1, null);
		goal4 = new BoardListImpl<>(4, container4);
	}

	@Test
	public void solveSimplePuzzleTest1() throws NoSolutionExcption {
		// -- Given --
		final int size = 3;
		final Board<Integer> initial = new BoardListImpl<>(size);
		initial.setTile(1, 1, 1);
		initial.setTile(1, 2, 2);
		initial.setTile(1, 3, 3);
		initial.setTile(2, 1, 4);
		initial.setTile(2, 2, 5);
		initial.setTile(2, 3, 6);
		initial.setTile(3, 1, 7);
		initial.setTile(3, 2, null);
		initial.setTile(3, 3, 8);
		SlidingPuzzle<Integer> solver = new SlidingPuzzle<>();

		// -- When --
		final List<Move> moves = new ArrayList<>();
		solver.start(initial)
				.solve(goal3)
				.start()
				.fillMoves(moves)
				.printMoves()
				.performMoves()
				.end()
				.end();

		// -- Then --
		assertEquals(1, moves.size());
		assertEquals(goal3, initial);
	}

	@Test
	public void solveSimplePuzzleTest2() throws NoSolutionExcption {
		// -- Given --
		final int size = 3;
		final Board<Integer> initial = new BoardListImpl<>(size);
		initial.setTile(1, 1, 1);
		initial.setTile(1, 2, 2);
		initial.setTile(1, 3, 3);
		initial.setTile(2, 1, 4);
		initial.setTile(2, 2, 5);
		initial.setTile(2, 3, 6);
		initial.setTile(3, 1, null);
		initial.setTile(3, 2, 7);
		initial.setTile(3, 3, 8);
		SlidingPuzzle<Integer> solver = new SlidingPuzzle<>();

		// -- When --
		final List<Move> moves = new ArrayList<>();
		solver.start(initial)
				.solve(goal3)
				.start()
				.fillMoves(moves)
				.printMoves()
				.performMoves()
				.end()
				.end();

		// -- Then --
		assertEquals(2, moves.size());
		assertEquals(goal3, initial);
	}

	@Test
	public void solveComplexPuzzleTest1() throws NoSolutionExcption {
		// -- Given --
		final int size = 3;
		// 8 2 7
		// 1 4 6
		// 3 5 X
		final Board<Integer> initial = new BoardListImpl<>(size);
		initial.setTile(1, 1, 8);
		initial.setTile(1, 2, 2);
		initial.setTile(1, 3, 7);
		initial.setTile(2, 1, 1);
		initial.setTile(2, 2, 4);
		initial.setTile(2, 3, 6);
		initial.setTile(3, 1, 3);
		initial.setTile(3, 2, 5);
		initial.setTile(3, 3, null);
		SlidingPuzzle<Integer> solver = new SlidingPuzzle<>();

		// -- When --
		solver.start(initial)
				.solve(goal3)
				.start()
				.printMoves()
				.performMoves()
				.end()
				.end();

		// -- Then --
		assertEquals(goal3, initial);
	}

	@Test
	public void solveRandomPuzzlesTest2() throws NoSolutionExcption {
		// -- Given --
		final int size = 3;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(container.size() - 1, null);
		final Board<Integer> initial = new BoardListImpl<>(size, container);
		final SlidingPuzzle<Integer> solver = new SlidingPuzzle<Integer>();
		for (int k = 0; k < 50; k++) {
			initial.shuffle();

			// -- When --
			final List<Move> moves = new ArrayList<>();
			solver.start(initial)
					.solve(goal3)
					.start()
					.fillMoves(moves)
					.printMoves()
					.performMoves()
					.end()
					.end();

			// -- Then --
			assertEquals(goal3, initial);
		}
	}

	@Test
	public void solveSimplePuzzleTest_4x4() throws NoSolutionExcption {
		// -- Given --
		final Board<Integer> initial = goal4.clone();
		final SlidingPuzzle<Integer> solver = new SlidingPuzzle<Integer>();

		// -- When --
		initial.moveLeft();
		final List<Move> moves = new ArrayList<>();
		solver.start(initial)
				.solve(goal4)
				.start()
				.fillMoves(moves)
				.printMoves()
				.performMoves()
				.end()
				.end();

		// -- Then --
		assertEquals(1, moves.size());
		assertEquals(goal4, initial);
	}

	@Test
	public void solveComplexPuzzleTest_4x4() throws NoSolutionExcption {
		// -- Given --
		final Board<Integer> initial = goal4.clone();
		final SlidingPuzzle<Integer> solver = new SlidingPuzzle<>();

		// -- When --
		initial.moveLeft();
		initial.moveLeft();
		initial.moveUp();
		initial.moveLeft();
		initial.moveUp();
		initial.moveUp();
		initial.moveRight();
		initial.moveDown();
		initial.moveLeft();

		final List<Move> moves = new ArrayList<>();
		solver.start(initial)
				.solve(goal4)
				.start()
				.fillMoves(moves)
				.printMoves()
				.performMoves()
				.end()
				.end();

		// -- Then --
		assertEquals(9, moves.size());
		assertEquals(goal4, initial);
	}
}
