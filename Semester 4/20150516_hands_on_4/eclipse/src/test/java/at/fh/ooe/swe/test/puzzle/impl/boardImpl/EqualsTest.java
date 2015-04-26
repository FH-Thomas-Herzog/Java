package at.fh.ooe.swe.test.puzzle.impl.boardImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.Before;

import at.fh.ooe.swe.test.api.AbstractTest;

public class EqualsTest extends AbstractTest {

	private List<Integer> container;

	@Before
	public void init() {
		container = new ArrayList<Integer>(CONTAINER_SIZE);
		IntStream.range(0, (CONTAINER_SIZE + 1)).forEach(new IntConsumer() {
			@Override
			public void accept(int value) {
				container.add(value);
			}
		});
	}
}
