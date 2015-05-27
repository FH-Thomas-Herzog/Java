package at.fh.ooe.swe4.test.collections.twoThreeFourTreeSet.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.fh.ooe.swe4.collections.impl.TwoThreeFourTreeSet;
import at.fh.ooe.swe4.collections.model.NMKTreeTreeNode;

/**
 * This class provides the test data for the {@link TwoThreeFourTreeSet} tests,
 * where dedicated trees are build via the {@link NMKTreeTreeNode} model.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 27, 2015
 */
public class TwoThreeFourTreeSetDataProducer {

	/**
	 * USed for the paramter build for the parameterized test.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 27, 2015
	 */
	public static enum TestCase {
		TREE_1, TREE_2;
	}

	/**
	 * Model class for holding the test case specific test data.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 27, 2015
	 */
	public static class TestData {

		public final List<Integer> orderedValues;
		public final List<Integer> insertOrderedValues;
		public final int levels;
		public final NMKTreeTreeNode<Integer> root;

		/**
		 * @param orderedValues
		 * @param insertOrderedValues
		 * @param root
		 * @param levels
		 */
		public TestData(final List<Integer> orderedValues,
				final List<Integer> insertOrderedValues,
				final NMKTreeTreeNode<Integer> root, final int levels) {
			super();
			this.orderedValues = orderedValues;
			this.insertOrderedValues = insertOrderedValues;
			this.root = root;
			this.levels = levels;
		}
	}

	/**
	 * Creates the test data for the given test case
	 * 
	 * @param testCase
	 *            the test case to produce test data for
	 * @return the model instance holding the test case test data
	 */
	public static TestData createTestData(final TestCase testCase) {
		final int size = 10;
		List<Integer> orderedValues = new ArrayList<Integer>(size);
		switch (testCase) {
		case TREE_1:
			// -- Test should be able to walk this tree
			// ----------------- 6 ----------------------------
			// -------- 3 ---------------- 8 ------------------
			// -------/ -- \ -------- / -- | ---- \ -----------
			// -- 1-2 ---- 4-5 --- 7 ---- 9-10 --- 12-13 ------
			// ------------------------------------------------
			final NMKTreeTreeNode<Integer> l1 = new NMKTreeTreeNode<Integer>();
			l1.addKey(1);
			l1.addKey(2);
			orderedValues.add(1);
			orderedValues.add(2);

			final NMKTreeTreeNode<Integer> l2 = new NMKTreeTreeNode<Integer>();
			l2.addKey(4);
			l2.addKey(5);
			orderedValues.add(4);
			orderedValues.add(5);

			final NMKTreeTreeNode<Integer> r1 = new NMKTreeTreeNode<Integer>(7);
			orderedValues.add(7);

			final NMKTreeTreeNode<Integer> r2 = new NMKTreeTreeNode<Integer>();
			r2.addKey(9);
			r2.addKey(10);
			orderedValues.add(9);
			orderedValues.add(10);

			final NMKTreeTreeNode<Integer> r3 = new NMKTreeTreeNode<Integer>();
			r2.addKey(12);
			r2.addKey(13);
			orderedValues.add(12);
			orderedValues.add(13);

			final NMKTreeTreeNode<Integer> l1l2 = new NMKTreeTreeNode<Integer>(
					3);
			l1l2.addChild(l1);
			l1l2.addChild(l2);
			orderedValues.add(3);

			final NMKTreeTreeNode<Integer> r1r2 = new NMKTreeTreeNode<Integer>(
					8);
			r1r2.addChild(r1);
			r1r2.addChild(r2);
			orderedValues.add(8);

			final NMKTreeTreeNode<Integer> root = new NMKTreeTreeNode<Integer>(
					6);
			root.addChild(l1l2);
			root.addChild(r1r2);
			orderedValues.add(6);

			Collections.sort(orderedValues);

			return new TestData(orderedValues, orderedValues, root, 3);
		case TREE_2:
			// -- Test should be able to walk this tree
			// ----------------------- 12 -------------------------------------
			// ---------- 4-8 -------------------- 16-20 ----------------------
			// --- / - | ---- \ --------------- / - | -------- \ -------------
			// 1-2-3 - 5-6-7 - 9-10-11 -- 13-14-15 - 17-18-19 - 21-22-23 ------
			// ----------------------------------------------------------------
			final NMKTreeTreeNode<Integer> _l1 = new NMKTreeTreeNode<Integer>();
			_l1.addKey(1);
			_l1.addKey(2);
			_l1.addKey(3);
			orderedValues.add(1);
			orderedValues.add(2);
			orderedValues.add(3);

			final NMKTreeTreeNode<Integer> _l2 = new NMKTreeTreeNode<Integer>();
			_l2.addKey(5);
			_l2.addKey(6);
			_l2.addKey(7);
			orderedValues.add(5);
			orderedValues.add(6);
			orderedValues.add(7);

			final NMKTreeTreeNode<Integer> _l3 = new NMKTreeTreeNode<Integer>();
			_l3.addKey(9);
			_l3.addKey(10);
			_l3.addKey(11);
			orderedValues.add(9);
			orderedValues.add(10);
			orderedValues.add(11);

			final NMKTreeTreeNode<Integer> _r1 = new NMKTreeTreeNode<Integer>();
			_r1.addKey(13);
			_r1.addKey(14);
			_r1.addKey(15);
			orderedValues.add(13);
			orderedValues.add(14);
			orderedValues.add(15);

			final NMKTreeTreeNode<Integer> _r2 = new NMKTreeTreeNode<Integer>();
			_r2.addKey(17);
			_r2.addKey(18);
			_r2.addKey(19);
			orderedValues.add(17);
			orderedValues.add(18);
			orderedValues.add(19);

			final NMKTreeTreeNode<Integer> _r3 = new NMKTreeTreeNode<Integer>();
			_r3.addKey(21);
			_r3.addKey(22);
			_r3.addKey(23);
			orderedValues.add(21);
			orderedValues.add(22);
			orderedValues.add(23);

			final NMKTreeTreeNode<Integer> _l1l2l3 = new NMKTreeTreeNode<Integer>();
			_l1l2l3.addChild(_l1);
			_l1l2l3.addChild(_l2);
			_l1l2l3.addChild(_l3);
			_l1l2l3.addKey(4);
			_l1l2l3.addKey(8);
			orderedValues.add(4);
			orderedValues.add(8);

			final NMKTreeTreeNode<Integer> _r1r2r3 = new NMKTreeTreeNode<Integer>();
			_r1r2r3.addChild(_r1);
			_r1r2r3.addChild(_r2);
			_r1r2r3.addChild(_r3);
			_r1r2r3.addKey(16);
			_r1r2r3.addKey(20);
			orderedValues.add(16);
			orderedValues.add(20);

			final NMKTreeTreeNode<Integer> _root = new NMKTreeTreeNode<Integer>();
			_root.addChild(_l1l2l3);
			_root.addChild(_r1r2r3);
			_root.addKey(12);
			orderedValues.add(12);

			Collections.sort(orderedValues);

			return new TestData(orderedValues, orderedValues, _root, 3);
		default:
			return null;
		}
	}
}
