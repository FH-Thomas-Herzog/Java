package at.fh.ooe.swe4.test.collections.binarySearchTreeSet.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import at.fh.ooe.swe4.collections.impl.BinarySearchTreeSet;
import at.fh.ooe.swe4.collections.model.BinaryTreeNode;

/**
 * This class provides the test data for the {@link BinarySearchTreeSet} tests.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 27, 2015
 */
public class BinarySearchTreeTestDataProducer {

	/**
	 * The provided test cases.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 27, 2015
	 */
	public static enum TestCase {
		TREE_1, TREE_2;
	}

	/**
	 * The model for holding the test data for a specific test case.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 27, 2015
	 */
	public static class TestData {

		public final List<Integer> orderedValues;
		public final List<Integer> insertOrderedValues;
		public final int levels;
		public final BinaryTreeNode<Integer> root;

		/**
		 * @param orderedValues
		 * @param insertOrderedValues
		 * @param root
		 * @param levels
		 */
		public TestData(final List<Integer> orderedValues,
				final List<Integer> insertOrderedValues,
				final BinaryTreeNode<Integer> root, final int levels) {
			super();
			this.orderedValues = orderedValues;
			this.insertOrderedValues = insertOrderedValues;
			this.root = root;
			this.levels = levels;
		}
	}

	/**
	 * Creates the test model for the given test case.
	 * 
	 * @param testCase
	 *            the test case to create test data for
	 * @return the model holding the test data for this test case
	 */
	public static TestData createTestData(final TestCase testCase) {
		final int size = 7;
		final List<Integer> orderedValues = new ArrayList<Integer>(size);
		final List<Integer> insertOrderedValues = new ArrayList<Integer>(size);
		IntStream.range(1, 8).forEachOrdered(new IntConsumer() {

			@Override
			public void accept(int value) {
				orderedValues.add(value);

			}
		});
		switch (testCase) {
		case TREE_1:
			// -- Test should be able to walk this tree
			// ------------- 5 ----------------
			// --------- 4 ----- 6 ------------
			// ----- 2 -------------- 7 -------
			// -- 1 --- 3 ---------------------
			// --------------------------------
			final BinaryTreeNode<Integer> l1 = new BinaryTreeNode<Integer>(1);
			final BinaryTreeNode<Integer> l2 = new BinaryTreeNode<Integer>(3);
			final BinaryTreeNode<Integer> l1l2 = new BinaryTreeNode<Integer>(2,
					l1, l2);
			final BinaryTreeNode<Integer> l0 = new BinaryTreeNode<Integer>(4,
					l1l2, null);
			final BinaryTreeNode<Integer> r1 = new BinaryTreeNode<Integer>(7);
			final BinaryTreeNode<Integer> r0 = new BinaryTreeNode<Integer>(6,
					null, r1);
			final BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(5,
					l0, r0);

			insertOrderedValues.add(5);
			insertOrderedValues.add(4);
			insertOrderedValues.add(6);
			insertOrderedValues.add(7);
			insertOrderedValues.add(2);
			insertOrderedValues.add(1);
			insertOrderedValues.add(3);
			return new TestData(orderedValues, insertOrderedValues, root, 4);
		case TREE_2:
			// -- Example in order
			// -- Test should be able to walk this tree
			// -------------- 4 ---------------
			// --------- 2 ------- 6 -----------
			// ----- 1 -----3---5------ 7 ------
			// ---------------------------------
			final BinaryTreeNode<Integer> _l1 = new BinaryTreeNode<Integer>(1);
			final BinaryTreeNode<Integer> _l2 = new BinaryTreeNode<Integer>(3);
			final BinaryTreeNode<Integer> _l1l2 = new BinaryTreeNode<Integer>(
					2, _l1, _l2);
			final BinaryTreeNode<Integer> _r1 = new BinaryTreeNode<Integer>(5);
			final BinaryTreeNode<Integer> _r2 = new BinaryTreeNode<Integer>(7);
			final BinaryTreeNode<Integer> _r1r2 = new BinaryTreeNode<Integer>(
					6, _r1, _r2);
			final BinaryTreeNode<Integer> _root = new BinaryTreeNode<Integer>(
					4, _l1l2, _r1r2);

			insertOrderedValues.add(4);
			insertOrderedValues.add(6);
			insertOrderedValues.add(5);
			insertOrderedValues.add(7);
			insertOrderedValues.add(2);
			insertOrderedValues.add(3);
			insertOrderedValues.add(1);

			return new TestData(orderedValues, insertOrderedValues, _root, 3);
		default:
			return null;
		}
	}
}
