package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;

/**
 * This test class tests the provided constructor of the class {@link BoardImpl}.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
@RunWith(JUnit4.class)
public class CnstructorTest extends AbstractTest {

	// -- Then --
	@Test(expected = IllegalArgumentException.class)
	public void defaultNegativeSize() {
		// -- Given --
		final int size = -1;

		// -- When --
		new BoardImpl<>(size);
	}

	// -- Then --
	@Test(expected = IllegalArgumentException.class)
	public void defaultZeroSize() {
		// -- Given --
		final int size = 0;

		// -- Given | When --
		new BoardImpl<>(size);
	}

	// -- Then --
	@Test(expected = IllegalArgumentException.class)
	public void copyNotCorresponding() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = new ArrayList<Integer>((int) Math.pow((size - 1), 2));

		// -- When
		new BoardImpl<>(size, container);
	}

	// -- Then --
	@Test(expected = IllegalArgumentException.class)
	public void copyNullContainer() {
		// -- Given --
		final int size = 9;
		final List<Integer> container = null;

		// -- Given --
		new BoardImpl<>(size, container);
	}

	@Test
	public void defaultConstructor() {
		// -- Given --
		final int size = 1;

		// -- When --
		Board<Integer> board = new BoardImpl<>(size);

		// -- Then --
		assertNotNull(board);
	}

	@Test
	public void copyConstructor() {
		// -- Given --
		final int size = 10;
		final List<Integer> container = createContainer((int) Math.pow(size, 2));

		// -- When--
		final Board<Integer> board = new BoardImpl<>(size, container);

		// -- Then --
		assertNotNull(board);
	}

}
