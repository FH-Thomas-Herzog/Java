package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe.test.api.AbstractTest;
import at.fh.ooe.swe4.puzzle.api.Board;
import at.fh.ooe.swe4.puzzle.impl.BoardImpl;
import at.fh.ooe.swe4.puzzle.model.Position;

/**
 * This test class test the method {@link Board#getTilePosition(Comparable)}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at> Apr 27, 2015
 */
@RunWith(JUnit4.class)
public class GetTilePositionTest extends AbstractTest {

	private List<Integer> container;

	@Before
	public void init() {
		container = createContainer(CONTAINER_SIZE);
	}

	@Test(expected = NoSuchElementException.class)
	public void noEmptyTile() {
		final Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		board.getTilePosition(null);
	}

	@Test(expected = NoSuchElementException.class)
	public void noSuchElement() {
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		board.getTilePosition(Integer.MAX_VALUE);
	}

	@Test
	public void validAllTiles() {
		container.set(0, null);
		final Board<Integer> board = new BoardImpl<Integer>(SIZE, container);
		// row iteration
		IntStream.range(1, (board.size() + 1)).forEach(new IntConsumer() {
			@Override
			public void accept(int i) {
				// column iteration
				IntStream.range(1, (board.size() + 1)).forEach(new IntConsumer() {
					@Override
					public void accept(int j) {
						final Integer value = board.getTile(i, j);
						final Position position = board.getTilePosition(value);
						assertEquals(i, position.rowIdx);
						assertEquals(j, position.colIdx);
					}
				});
				;
			}
		});
	}
}
