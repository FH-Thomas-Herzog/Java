package at.fh.ooe.swe.test.puzzle.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.exception.InvalidBoardIndexException;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;

@RunWith(JUnit4.class)
public class BoardImplTest extends AbstractTest {

	// CompareTo Tests
	@Test
	public void compareToEqualSize() {
		final Board<Integer> board = new BoardImpl<Integer>(4);
		final Board<Integer> other = new BoardImpl<Integer>(board.size());
		assertEquals(0, board.compareTo(other));
	}

	@Test
	public void compareToLowerSize() {
		final Board<Integer> board = new BoardImpl<Integer>(4);
		final Board<Integer> other = new BoardImpl<Integer>(board.size() + 1);
		assertEquals(-1, board.compareTo(other));
	}

	@Test
	public void compareToGreaterSize() {
		final Board<Integer> board = new BoardImpl<Integer>(4);
		final Board<Integer> other = new BoardImpl<Integer>(board.size() - 1);
		assertEquals(1, board.compareTo(other));
	}

	@Test(expected = InvalidBoardIndexException.class)
	public void simpleIsValid() {
		Board<Integer> board = new BoardImpl<Integer>(3);
		board.setTile(1, 1, 1);
		board.setTile(1, 2, 2);
		board.setTile(1, 3, 3);
		board.setTile(2, 1, 4);
		board.setTile(2, 2, 5);
		board.setTile(2, 3, 6);
		board.setTile(3, 1, 7);
		board.setTile(3, 2, 8);
		board.setTile(3, 3, 0);

		assertTrue(board.isValid());
	}

	@Test
	public void simpleIsNotValid() {
		Board<Integer> board = new BoardImpl<Integer>(3);
		board.setTile(1, 1, 1);
		board.setTile(1, 2, 2);
		board.setTile(1, 3, 3);
		board.setTile(2, 1, 4);
		board.setTile(2, 2, 5);
		board.setTile(2, 3, 6);
		board.setTile(3, 1, 7);
		board.setTile(3, 2, 1);
		board.setTile(3, 3, 0);

		assertTrue(!board.isValid());
	}

	@Test
	public void simpleIsNotValid2() {
		Board<Integer> board = new BoardImpl<Integer>(3);
		board.setTile(1, 1, 8);
		board.setTile(1, 2, 2);
		board.setTile(1, 3, 0);
		board.setTile(2, 1, 7);
		board.setTile(2, 2, 5);
		board.setTile(2, 3, 4);
		board.setTile(3, 1, 3);
		board.setTile(3, 2, 1);
		board.setTile(3, 3, 6);

		assertTrue(board.isValid());
	}
}
