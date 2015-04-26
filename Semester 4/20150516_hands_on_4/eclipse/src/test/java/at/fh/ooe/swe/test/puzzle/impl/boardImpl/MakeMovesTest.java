/**
 * 
 */
package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.Board.Direction;
import at.fh.ooe.swe4.puzzle.exception.InvalidMoveException;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;

/**
 * This test class tests the method {@link Board#makesMoves(Iterable)}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
@RunWith(JUnit4.class)
public class MakeMovesTest extends AbstractTest {

	private List<Integer> container;

	@Before
	public void init() {
		container = createContainer(CONTAINER_SIZE);
	}

	@Test(expected = InvalidMoveException.class)
	public void nullIterable() {
		Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		board.makesMoves((Iterable<Direction>) null);
	}

	@Test(expected = InvalidMoveException.class)
	public void containsNull() {
		Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		final List<Direction> moves = new ArrayList<Direction>(Direction.values().length + 1);
		for (Direction direction : Direction.values()) {
			moves.add(direction);
		}
		moves.add(null);
		board.makesMoves(moves);
	}

	@Test(expected = InvalidMoveException.class)
	public void invalidBoard() {
		Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		board.makesMoves(Arrays.asList(new Direction[] { Direction.DOWN }));
	}

	@Test(expected = InvalidMoveException.class)
	public void borderOverflow() {
		container.set(0, null);
		Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		final List<Direction> moves = new ArrayList<Direction>();
		for (int i = 0; i < SIZE; i++) {
			moves.add(Direction.RIGHT);
		}
		board.makesMoves(moves);
	}

	@Test
	public void validAllFields() {
		container.set(0, null);
		Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		final List<Direction> moves = new ArrayList<Direction>();
		boolean invert = Boolean.FALSE;
		for (int i = 0; i < SIZE; i++) {
			final Direction direction;
			if (invert) {
				direction = Direction.LEFT;
				invert = Boolean.FALSE;
			} else {
				direction = Direction.RIGHT;
				invert = Boolean.TRUE;
			}
			for (int j = 0; j < (SIZE - 1); j++) {
				moves.add(direction);
			}
			if (i < (SIZE - 1)) {
				moves.add(Direction.DOWN);
			}
		}
		LOG.info(moves.toString());
		LOG.info("Prepared board:");
		LOG.info(container.toString());
		board.makesMoves(moves);
		LOG.info("Resulting board");
		LOG.info(board.toString());
	}
}
