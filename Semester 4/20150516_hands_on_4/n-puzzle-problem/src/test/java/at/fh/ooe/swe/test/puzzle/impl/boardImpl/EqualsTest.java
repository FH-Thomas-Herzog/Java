package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardListImpl;

/**
 * This test class tests the method {@link Board#equals(Object)}.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at> Apr 27, 2015
 */
@RunWith(JUnit4.class)
public class EqualsTest extends AbstractTest {

	@Test
	public void notEqualNull() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);
		final Board<Integer> other = null;

		// -- When --
		final boolean result = board.equals(other);

		// -- Then --
		assertFalse(result);
	}

	@Test
	public void notEqualNotSameInstance() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);
		final String other = null;

		// -- When --
		final boolean result = board.equals(other);

		// -- Then --
		assertFalse(result);
	}

	@Test
	public void notEqualDifferentSize() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);
		final int otherSize = 5;
		final List<Integer> otherContainer = createContainer((int) Math.pow(otherSize, 2));
		final Board<Integer> otherBoard = new BoardListImpl<>(otherSize, otherContainer);

		// -- When --
		final boolean result = board.equals(otherBoard);

		// -- Then --
		assertFalse(result);
	}

	@Test
	public void notEqualContainerElements() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);
		final List<Integer> otherContainer = createContainer((int) Math.pow(size, 2));
		Collections.shuffle(otherContainer);
		final Board<Integer> otherBoard = new BoardListImpl<>(size, otherContainer);

		// -- When --
		final boolean result = board.equals(otherBoard);

		// -- Then --
		assertFalse(result);
	}

	@Test
	public void isEqual() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);
		final Board<Integer> otherBoard = new BoardListImpl<>(size, container);

		// -- When --
		final boolean result = board.equals(otherBoard);

		// -- Then --
		assertTrue(result);
	}
}
