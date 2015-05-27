package at.fh.ooe.swe4.test.collections.provided;

import java.util.Comparator;

import at.fh.ooe.swe4.collections.api.SortedTreeSet;
import at.fh.ooe.swe4.collections.impl.BinarySearchTreeSet;

public class BSTSetTest extends SortedTreeSetTestBase {

	@Override
	protected <T> SortedTreeSet<T> createSet(Comparator<T> comparator) {
		return new BinarySearchTreeSet<T>(comparator);
	}
}