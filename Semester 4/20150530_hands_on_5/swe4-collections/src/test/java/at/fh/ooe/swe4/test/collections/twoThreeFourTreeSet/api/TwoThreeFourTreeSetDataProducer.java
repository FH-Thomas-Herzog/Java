package at.fh.ooe.swe4.test.collections.twoThreeFourTreeSet.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.fh.ooe.swe4.collections.model.TreeNode;

public class TwoThreeFourTreeSetDataProducer {

	public static enum TestCase {
		TREE_1,
		TREE_2;
	}

	public static class TestData {
		public final List<Integer> orderedValues;
		public final List<Integer> insertOrderedValues;
		public final int levels;
		public final TreeNode<Integer> root;

		public TestData(final List<Integer> orderedValues, final List<Integer> insertOrderedValues, final TreeNode<Integer> root, final int levels) {
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
		final List<Integer> insertOrderedValues = new ArrayList<Integer>(size);
		switch (type) {
		case TREE_1:
			// -- Test should be able to walk this tree
			// ----------------- 6 ----------------------------
			// -------- 3 ------------- 8 ---------------------
			// -- 1-2 ---- 4-5 --- 7 ------ 9-10-11 -----------
			// ------------------------------------------------
			final TreeNode<Integer> l1 = new TreeNode<Integer>();
			l1.addKey(1);
			l1.addKey(2);

			final TreeNode<Integer> l2 = new TreeNode<Integer>();
			l2.addKey(4);
			l2.addKey(5);

			final TreeNode<Integer> r1 = new TreeNode<Integer>(7);

			final TreeNode<Integer> r2 = new TreeNode<Integer>();
			r2.addKey(9);
			r2.addKey(10);
			r2.addKey(11);

			final TreeNode<Integer> l1l2 = new TreeNode<Integer>(3);
			l1l2.addChild(l1);
			l1l2.addChild(l2);

			final TreeNode<Integer> r1r2 = new TreeNode<Integer>(8);
			r1r2.addChild(r1);
			r1r2.addChild(r2);

			final TreeNode<Integer> root = new TreeNode<Integer>(6);
			r1r2.addChild(l1l2);
			r1r2.addChild(r1r2);

			insertOrderedValues.add(17);
			insertOrderedValues.add(10);
			insertOrderedValues.add(5);
			insertOrderedValues.add(7);
			insertOrderedValues.add(12);
			insertOrderedValues.add(15);
			insertOrderedValues.add(10);
			insertOrderedValues.add(19);
			insertOrderedValues.add(25);
			insertOrderedValues.add(30);

			orderedValues = new ArrayList<>(insertOrderedValues);
			Collections.sort(orderedValues);

			return new TestData(orderedValues, insertOrderedValues, root, 4);
		case TREE_2:
			// -- Test should be able to walk this tree
			// ----------------------- 13 -----------------------------
			// ---------- 9 ------------------------ 17 ---------------
			// -- 5-7-8 ---- 10-11-12 ----14-15-16 ----- 18-19-20 -----
			// --------------------------------------------------------
			final TreeNode<Integer> _l1 = new TreeNode<Integer>();
			_l1.addKey(5);
			_l1.addKey(7);
			_l1.addKey(8);

			final TreeNode<Integer> _l2 = new TreeNode<Integer>();
			_l2.addKey(10);
			_l2.addKey(11);
			_l2.addKey(12);

			final TreeNode<Integer> _r1 = new TreeNode<Integer>();
			_r1.addKey(14);
			_r1.addKey(15);
			_r1.addKey(16);

			final TreeNode<Integer> _r2 = new TreeNode<Integer>();
			_r2.addKey(18);
			_r2.addKey(19);
			_r2.addKey(20);

			final TreeNode<Integer> _l1l2 = new TreeNode<Integer>(9);
			_l1l2.addChild(_l1);
			_l1l2.addChild(_l2);

			final TreeNode<Integer> _r1r2 = new TreeNode<Integer>(17);
			_r1r2.addChild(_r1);
			_r1r2.addChild(_r2);

			final TreeNode<Integer> _root = new TreeNode<Integer>(17);
			_root.addChild(_l1l2);
			_root.addChild(_r1r2);

			insertOrderedValues.add(17);
			insertOrderedValues.add(10);
			insertOrderedValues.add(5);
			insertOrderedValues.add(7);
			insertOrderedValues.add(12);
			insertOrderedValues.add(15);
			insertOrderedValues.add(10);
			insertOrderedValues.add(19);
			insertOrderedValues.add(25);
			insertOrderedValues.add(30);

			orderedValues = new ArrayList<>(insertOrderedValues);
			Collections.sort(orderedValues);

			return new TestData(orderedValues, insertOrderedValues, _root, 4);
		default:
			return null;

		}
	}
}
