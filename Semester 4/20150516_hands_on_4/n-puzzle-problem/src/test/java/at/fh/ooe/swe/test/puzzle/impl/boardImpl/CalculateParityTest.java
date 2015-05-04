package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.exception.InvalidBoardIndexException;
import at.fh.ooe.swe4.puzzle.impl.BoardListImpl;

/**
 * This test class tests the method {@link Board#calculateParity()}.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 30, 2015
 */
@RunWith(JUnit4.class)
public class CalculateParityTest extends AbstractTest {

	// -- Then --
	@Test(expected = InvalidBoardIndexException.class)
	public void invalidBoard() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardListImpl<>(size, container);

		// -- When --
		board.calculateParity();
	}

	@Test
	public void validEvenParity() {
		// -- Given --
		final int size = 2;
		final List<Integer> container = new ArrayList<>((int) Math.pow(size, 2));
		container.add(null);
		container.add(2);
		container.add(1);
		container.add(3);
		final Board<Integer> board = new BoardListImpl<>(size, container);

		// -- When --
		final int parity = board.calculateParity();

		// -- Then --
		assertTrue((parity % 2 == 0));
		assertEquals(2, parity);
	}

	@Test
	public void validOddParity() {
		// -- Given --
		final int size = 2;
		final List<Integer> container = new ArrayList<>((int) Math.pow(size, 2));
		container.add(null);
		container.add(3);
		container.add(2);
		container.add(1);
		final Board<Integer> board = new BoardListImpl<>(size, container);

		// -- When --
		final int parity = board.calculateParity();

		// -- Then --
		assertTrue((parity % 2 == 0));
		assertEquals(4, parity);
	}
}
