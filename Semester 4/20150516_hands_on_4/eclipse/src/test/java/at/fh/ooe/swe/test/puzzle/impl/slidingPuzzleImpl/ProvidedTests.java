package at.fh.ooe.swe.test.puzzle.impl.slidingPuzzleImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.Board.Direction;
import at.fh.ooe.swe4.puzzle.exception.NoSolutionExcption;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;
import at.fh.ooe.swe4.puzzle.impl.SlidingPuzzle;
import at.fh.ooe.swe4.puzzle.impl.SlidingPuzzle.SolutionHandler;
import at.fh.ooe.swe4.puzzle.model.Position;

/**
 * This test class contains the provided tests for testing the method
 * {@link SlidingPuzzle#solve(at.fh.ooe.swe4.puzzle.api.Board)}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 1, 2015
 */
@RunWith(JUnit4.class)
public class ProvidedTests extends AbstractTest {

	private Board<Integer> goal;

	@Before
	public void init() {
		goal = new BoardImpl<>(3);
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
	public void solveSimplePuzzleTest1() throws NoSolutionExcption {
		// -- Given --
		final Board<Integer> initial = new BoardImpl<>(3);
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
		final List<Direction> moves = solver.start(initial)
											.solve(goal)
											.start()
											.getMoves();

		// -- Then --
		initial.makeMoves(moves);
		final Position emptyTilePosition = initial.getEmptyTilePosition();
		assertEquals(1, moves.size());
		assertTrue(emptyTilePosition.rowIdx == 3 && emptyTilePosition.colIdx == 3);
	}

	@Test
	public void solveSimplePuzzleTest2() throws NoSolutionExcption {
		// -- Given --
		final Board<Integer> initial = new BoardImpl<>(3);
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
		final List<Direction> moves = solver.start(initial)
											.solve(goal)
											.start()
											.getMoves();

		// -- Then --
		assertEquals(2, moves.size());
		// after first move
		initial.makeMoves(Arrays.asList(moves.get(0)));
		Position emptyTilePosition = null;
		emptyTilePosition = initial.getEmptyTilePosition();
		assertTrue(emptyTilePosition.rowIdx == 3 && emptyTilePosition.colIdx == 2);
		// after second move
		initial.makeMoves(Arrays.asList(moves.get(1)));
		emptyTilePosition = initial.getEmptyTilePosition();
		assertTrue(emptyTilePosition.rowIdx == 3 && emptyTilePosition.colIdx == 3);
	}

	@Test
	public void solveComplexPuzzleTest1() throws NoSolutionExcption {
		// -- Given --
		// 8 2 7
		// 1 4 6
		// 3 5 X
		final Board<Integer> initial = new BoardImpl<>(3);
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
		final List<Direction> moves = solver.start(initial)
											.solve(goal)
											.start()
											.getMoves();
		initial.makeMoves(moves);

		// -- Then --
		assertEquals(goal, initial);
	}

	@Test
	public void solveRandomPuzzlesTest2() throws NoSolutionExcption {
		// -- Given --
		final int size = 3;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(container.size() - 1, null);
		final Board<Integer> initial = new BoardImpl<>(3, container);
		final SlidingPuzzle<Integer> solver = new SlidingPuzzle<Integer>();
		SolutionHandler<Integer> solutionHandler = null;
		for (int k = 0; k < 50; k++) {
			initial.shuffle();
			System.out.println(initial.toString() + System.lineSeparator());
			// -- When --
			solutionHandler = solver.start(initial)
									.solve(goal);
			final List<Direction> moves = solutionHandler.start()
															.getMoves();
			solutionHandler.end();
			solver.end();
			initial.makeMoves(moves);

			// -- Then --
			assertEquals(goal, initial);
			LOG.info("Run: " + k);
		}
	}
}
