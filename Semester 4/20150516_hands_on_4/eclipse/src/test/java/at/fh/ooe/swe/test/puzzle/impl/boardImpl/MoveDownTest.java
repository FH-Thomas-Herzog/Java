package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.exception.InvalidMoveException;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;

/**
 * This test class tests the method {@link Board#moveDown()}.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public class MoveDownTest extends AbstractTest {

	private List<Integer> container;

	private static final int SIZE = 4;
	private static final int CONTAINER_SIZE = (SIZE * SIZE);

	@Before
	public void init() {
		container = new ArrayList<Integer>(CONTAINER_SIZE);
		for (int i = 0; i < CONTAINER_SIZE; i++) {
			container.add(i + 1);
		}
	}

	@Test(expected = InvalidMoveException.class)
	public void invalidBoard() {
		Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		board.moveDown();
	}

	@Test
	public void alreadyOnBottomAllColumns() {
		int lastRow = (SIZE * (SIZE - 1));
		int oldIdx = lastRow;
		for (int i = 0; i < SIZE; i++) {
			try {
				int idx = lastRow + i;
				container.set(oldIdx, container.get(idx));
				container.set(idx, null);
				oldIdx = idx;
				Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
				board.moveDown();
				fail("Expected InvalidMoveException");
			} catch (InvalidMoveException e) {
			}
		}
	}

	@Test
	public void validAllColsFormTop() {
		int oldIdx = 0;
		for (int i = 0; i < SIZE; i++) {
			container.set(oldIdx, container.get(i));
			container.set(i, null);
			LOG.info("Prepared board:");
			LOG.info(container.toString());
			Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
			for (int j = 0; j < (SIZE - 1); j++) {
				board.moveDown();
			}
			LOG.info("Resulting board:");
			LOG.info(board.toString());
			oldIdx = i;
		}
	}
}
