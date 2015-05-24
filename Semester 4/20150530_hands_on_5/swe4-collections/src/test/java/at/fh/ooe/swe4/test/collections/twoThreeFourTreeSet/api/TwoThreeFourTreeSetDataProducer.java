package at.fh.ooe.swe4.test.collections.twoThreeFourTreeSet.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.fh.ooe.swe4.collections.model.NMKTreeTreeNode;

public class TwoThreeFourTreeSetDataProducer {

	public static enum TestCase {
		TREE_1;
	}

	public static class TestData {
		public final List<Integer> orderedValues;
		public final List<Integer> insertOrderedValues;
		public final int levels;
		public final NMKTreeTreeNode<Integer> root;

		public TestData(final List<Integer> orderedValues, final List<Integer> insertOrderedValues, final NMKTreeTreeNode<Integer> root, final int levels) {
			super();
			this.orderedValues = orderedValues;
			this.insertOrderedValues = insertOrderedValues;
			this.root = root;
			this.levels = levels;
		}
	}

	public static TestData createTestData(final TestCase type) {
		final int size = 10;
		List<Integer> orderedValues = new ArrayList<Integer>(size);
		switch (type) {
		case TREE_1:
			// -- Test should be able to walk this tree
			// ----------------- 6 ----------------------------
			// -------- 3 ---------------- 8 ------------------
			//      /       \        /     |        \
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
			
			final NMKTreeTreeNode<Integer> l1l2 = new NMKTreeTreeNode<Integer>(3);
			l1l2.addChild(l1);
			l1l2.addChild(l2);
			orderedValues.add(3);

			final NMKTreeTreeNode<Integer> r1r2 = new NMKTreeTreeNode<Integer>(8);
			r1r2.addChild(r1);
			r1r2.addChild(r2);
			orderedValues.add(8);

			final NMKTreeTreeNode<Integer> root = new NMKTreeTreeNode<Integer>(6);
			root.addChild(l1l2);
			root.addChild(r1r2);
			orderedValues.add(6);

			Collections.sort(orderedValues);

			return new TestData(orderedValues, orderedValues, root, 3);
		default:
			return null;

		}
	}
}
