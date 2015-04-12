package at.fhooe.swe4.lab3.test.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import at.fhooe.swe4.lab3.sort.api.Sorter.SortType;

public class AbstractSorterTest<T extends Comparable<T>> {

	// protected helper
	protected void assertSorted(final List<T> values, final SortType sorterType) {
		for (int i = 0; i < (values.size() - 1); i++) {
			compare(values.get(i), values.get(i + 1), sorterType);
		}
	}

	protected boolean compare(final T v1, final T v2, final SortType sorterType) {
		switch (sorterType) {
		case ASCENDING:
			return v1.compareTo(v2) <= 0;
		case DESCENDING:
			return v1.compareTo(v2) >= 0;
		default:
			return Boolean.FALSE;
		}
	}

	protected List<Integer> createRandomIntegerList(final int size, final int range) {
		final List<Integer> values = new ArrayList<Integer>(size);
		final Random r = new Random(12345124352134L);
		for (int i = 0; i < size; i++) {
			values.add((r.nextInt(range) + 1));
		}
		return values;
	}
}
