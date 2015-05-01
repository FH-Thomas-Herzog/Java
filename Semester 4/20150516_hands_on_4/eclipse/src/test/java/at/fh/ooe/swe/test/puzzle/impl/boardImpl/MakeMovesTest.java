/**
 * 
 */
package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.api.Board.Direction;
import at.fh.ooe.swe4.puzzle.exception.InvalidMoveException;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;
import at.fh.ooe.swe4.puzzle.model.Position;

/**
 * This test class tests the method {@link Board#makeMoves(Iterable)}.<br>
 * This test class depends on proper functionality of the method
 * {@link Board#getEmptyTilePosition()}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
@RunWith(JUnit4.class)
public class MakeMovesTest extends AbstractTest {

	// -- Then --
	@Test(expected = InvalidMoveException.class)
	public void nullIterable() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		Board<Integer> board = new BoardImpl<>(size, container);

		// -- When --
		board.makeMoves((Iterable<Direction>) null);
	}

	// -- Then --
	@Test(expected = InvalidMoveException.class)
	public void containsNull() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		Board<Integer> board = new BoardImpl<>(size, container);
		final List<Direction> moves = new ArrayList<Direction>(Direction.values().length + 1);
		for (Direction direction : Direction.values()) {
			moves.add(direction);
		}
		moves.add(null);

		// -- When --
		board.makeMoves(moves);
	}

	// -- Then --
	@Test(expected = InvalidMoveException.class)
	public void invalidBoard() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		Board<Integer> board = new BoardImpl<>(size, container);

		// -- When --
		board.makeMoves(Arrays.asList(new Direction[] { Direction.DOWN }));
	}

	// -- Then -
	@Test(expected = InvalidMoveException.class)
	public void borderOverflow() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<>(size, container);
		final List<Direction> moves = new ArrayList<Direction>();
		for (int i = 0; i < size; i++) {
			moves.add(Direction.RIGHT);
		}

		// -- When --
		board.makeMoves(moves);
	}

	@Test
	public void validAllFieldsRowPerRowLeftToRight() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<>(size, container);
		final List<Direction> moves = new ArrayList<Direction>();
		boolean invert = Boolean.FALSE;
		for (int i = 0; i <= size; i++) {
			final Direction direction;
			if (invert) {
				direction = Direction.LEFT;
				invert = Boolean.FALSE;
			} else {
				direction = Direction.RIGHT;
				invert = Boolean.TRUE;
			}
			for (int j = 0; j < (size - 1); j++) {
				moves.add(direction);
			}
			if (i < (size - 1)) {
				moves.add(Direction.DOWN);
			}
		}

		// -- When --
		board.makeMoves(moves);

		// -- Then --
		final Position emptyTilePosition = board.getEmptyTilePosition();
		assertEquals(size, emptyTilePosition.rowIdx);
		assertEquals(size, emptyTilePosition.colIdx);
	}
}
