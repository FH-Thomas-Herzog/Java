package at.fh.ooe.swe4.test.collections.impl.parametrized;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import at.fh.ooe.swe4.collections.impl.BinarySearchTreeSet.BinarySearchtreeIterator;
import at.fh.ooe.swe4.collections.model.BinaryTreeNode;

@RunWith(Parameterized.class)
public class ITeratorValidTest {

	@Parameter
	public TestData testData;

	@Test
	public void valid() {
		// -- Given --
		final Iterator<Integer> it = new BinarySearchtreeIterator<Integer>(testData.root);

		// -- When --
		for (int i = 0; i < testData.values.size(); i++) {
			assertEquals(testData.values.get(i), it.next());
		}
	}

	private static enum TestCase {
		WITH_ASCENDING_ORDERED,
		WITH_DESCENDING_ORDERED,
		WITH_UNORDERED,
		WITH_NULL,
	}

	private static class TestData {
		private final List<Integer> values;
		private final BinaryTreeNode<Integer> root;

		public TestData(List<Integer> values, BinaryTreeNode<Integer> root) {
			super();
			this.values = values;
			this.root = root;
		}
	}

	@Parameters
	public static final List<Object[]> getTestParameters() {
		final Object[][] parameters = new Object[TestCase.values().length][1];
		int idx = 0;
		for (TestCase testCase : TestCase.values()) {
			parameters[idx][0] = createTestData(testCase);
			idx++;
		}
		return Arrays.asList(parameters);
	}

	private static TestData createTestData(final TestCase type) {
		final int size = 7;
		final List<Integer> values = new ArrayList<Integer>(size);
		switch (type) {
		case WITH_ASCENDING_ORDERED:
			IntStream.range(1, (size + 1))
						.forEachOrdered((int value) -> (values.add(value)));
			break;
		case WITH_DESCENDING_ORDERED:
			IntStream.range(1, (size + 1))
						.forEachOrdered((int value) -> (values.add(size - value + 1)));
			break;
		case WITH_UNORDERED:
			IntStream.range(1, (size + 1))
						.forEachOrdered((int value) -> (values.add(size - value + 1)));
			Collections.shuffle(values);
			break;
		case WITH_NULL:
			IntStream.range(1, (size + 1))
						.forEachOrdered((int value) -> (values.add(size - value + 1)));
			values.set((int) (size / 2), null);
			Collections.shuffle(values);
			break;
		default:
			break;
		}

		// -- Example in order
		// -- Test should be able to walk this tree
		// ------------- 5 ----------------
		// --------- 4 ----- 6 ------------
		// ----- 3 -------------- 7 -------
		// -- 1 --- 2 ---------------------
		// --------------------------------
		final BinaryTreeNode<Integer> l1 = new BinaryTreeNode<Integer>(values.get(0));
		final BinaryTreeNode<Integer> l2 = new BinaryTreeNode<Integer>(values.get(2));
		final BinaryTreeNode<Integer> l1l2 = new BinaryTreeNode<Integer>(values.get(1), l1, l2);
		final BinaryTreeNode<Integer> l0 = new BinaryTreeNode<Integer>(values.get(3), l1l2, null);
		final BinaryTreeNode<Integer> r1 = new BinaryTreeNode<Integer>(values.get(6));
		final BinaryTreeNode<Integer> r0 = new BinaryTreeNode<Integer>(values.get(5), null, r1);
		final BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(values.get(4), l0, r0);

		return new TestData(values, root);
	}
}
