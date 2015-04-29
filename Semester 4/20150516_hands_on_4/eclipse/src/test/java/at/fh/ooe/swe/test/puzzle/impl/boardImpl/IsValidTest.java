package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;

/**
 * This test class tests the method {@link Board#isValid()}.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
@RunWith(JUnit4.class)
public class IsValidTest extends AbstractTest {

	@Test
	public void multipleNull() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		container.set(1, null);
		final Board<Integer> board = new BoardImpl<>(size, container);

		// -- When --
		final boolean result = board.isValid();

		// -- Then --
		assertFalse(result);
	}

	@Test
	public void multipleNumber() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		container.set(1, 1);
		container.set(2, 1);
		final Board<Integer> board = new BoardImpl<>(size, container);

		// -- When --
		final boolean result = board.isValid();

		// -- Then --
		assertFalse(result);
	}

	@Test
	public void noEmptyTile() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		final Board<Integer> board = new BoardImpl<>(size, container);

		// -- When --
		final boolean result = board.isValid();

		// -- Then --
		assertFalse(result);
	}

	@Test
	public void valid() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<>(size, container);

		// -- When --
		final boolean result = board.isValid();

		// -- Then --
		assertTrue(result);
	}
}
