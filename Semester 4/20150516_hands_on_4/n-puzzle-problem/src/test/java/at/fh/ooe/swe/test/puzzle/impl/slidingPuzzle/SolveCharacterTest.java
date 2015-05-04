package at.fh.ooe.swe.test.puzzle.impl.slidingPuzzle;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

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
public class SolveCharacterTest extends AbstractTest {

	private Board<Character> goal3;
	private Board<Character> goal4;

	@Before
	public void init() {
		// 3 x 3 board
		final List<Character> container3 = createContainerWithChars((int) Math.pow(3, 2));
		container3.set(container3.size() - 1, null);
		goal3 = new BoardListImpl<>(3, container3);

		// 4 x 4 board
		final List<Character> container4 = createContainerWithChars((int) Math.pow(4, 2));
		container4.set(container4.size() - 1, null);
		goal4 = new BoardListImpl<>(4, container4);
	}

	@Test
	public void solveSimplePuzzleTest1() throws NoSolutionExcption {
		// -- Given --
		final int size = 3;
		final Board<Character> initial = new BoardListImpl<>(size);
		initial.setTile(1, 1, 'a');
		initial.setTile(1, 2, 'b');
		initial.setTile(1, 3, 'c');
		initial.setTile(2, 1, 'd');
		initial.setTile(2, 2, 'e');
		initial.setTile(2, 3, 'f');
		initial.setTile(3, 1, 'g');
		initial.setTile(3, 2, null);
		initial.setTile(3, 3, 'h');
		SlidingPuzzle<Character> solver = new SlidingPuzzle<>();

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
		final Board<Character> initial = new BoardListImpl<>(size);
		initial.setTile(1, 1, 'a');
		initial.setTile(1, 2, 'b');
		initial.setTile(1, 3, 'c');
		initial.setTile(2, 1, 'd');
		initial.setTile(2, 2, 'e');
		initial.setTile(2, 3, 'f');
		initial.setTile(3, 1, null);
		initial.setTile(3, 2, 'g');
		initial.setTile(3, 3, 'h');
		SlidingPuzzle<Character> solver = new SlidingPuzzle<>();

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
		final Board<Character> initial = new BoardListImpl<>(size);
		initial.setTile(1, 1, 'h');
		initial.setTile(1, 2, 'b');
		initial.setTile(1, 3, 'g');
		initial.setTile(2, 1, 'a');
		initial.setTile(2, 2, 'd');
		initial.setTile(2, 3, 'f');
		initial.setTile(3, 1, 'c');
		initial.setTile(3, 2, 'e');
		initial.setTile(3, 3, null);
		SlidingPuzzle<Character> solver = new SlidingPuzzle<>();

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
		final List<Character> container = createContainerWithChars((int) Math.pow(size, 2));
		container.set(container.size() - 1, null);
		final Board<Character> initial = new BoardListImpl<>(size, container);
		final SlidingPuzzle<Character> solver = new SlidingPuzzle<>();
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
		final Board<Character> initial = goal4.clone();
		final SlidingPuzzle<Character> solver = new SlidingPuzzle<>();

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
		final Board<Character> initial = goal4.clone();
		final SlidingPuzzle<Character> solver = new SlidingPuzzle<>();

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

	/**
	 * Creates a container with the given size and sets char values c in order
	 * from ordinal(c) to ordinal(size).<br>
	 * <b>This container will contain no null element.<b>
	 * 
	 * @param size
	 *            the size of the container
	 * @return the created container
	 */
	protected List<Character> createContainerWithChars(final int size) {
		final List<Character> container = new ArrayList<>(size);
		IntStream.range(0, size)
					.forEachOrdered(new IntConsumer() {
						@Override
						public void accept(int value) {
							container.add(new Character((char) (97 + value)));
						}
					});

		return container;
	}
}
