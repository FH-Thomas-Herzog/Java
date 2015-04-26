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

@RunWith(JUnit4.class)
public class CnstructorTest extends AbstractTest {

	@Test(expected = IllegalArgumentException.class)
	public void defaultNegativeSize() {
		new BoardImpl<Integer>(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void defaultZeroSize() {
		new BoardImpl<Integer>(0);
	}

	public void defaultConstructor() {
		Board<Integer> board = new BoardImpl<Integer>(1);
		assertNotNull(board);
	}

	@Test(expected = IllegalArgumentException.class)
	public void copyNegativSize() {
		new BoardImpl<Integer>(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void copyZeroSize() {
		new BoardImpl<Integer>(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void copyNotCorresponding() {
		final List<Integer> container = new ArrayList<Integer>(10);
		new BoardImpl<Integer>(9, container);
	}

	@Test(expected = IllegalArgumentException.class)
	public void copyNullContainer() {
		new BoardImpl<Integer>(9, null);
	}

	public void copy() {
		final List<Integer> container = new ArrayList<Integer>(10);
		final Board<Integer> board = new BoardImpl<Integer>(10, container);
		assertNotNull(board);
	}

}
