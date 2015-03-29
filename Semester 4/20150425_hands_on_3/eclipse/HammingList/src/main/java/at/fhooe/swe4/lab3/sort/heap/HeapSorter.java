package at.fhooe.swe4.lab3.sort.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import at.fhooe.swe4.lab3.sort.api.AbstractSorter;

public class HeapSorter<V extends Comparable<V>> extends AbstractSorter<V, List<V>> {

	private int valueCount;

	/**
	 * Initializes this instance with an array of values. Copies the given
	 * values to a backed container.
	 * 
	 * @param originialValues
	 *            the values to be sorted by this instance
	 */
	public HeapSorter(final V[] originialValues) {
		super(new ArrayList<V>());
		transform(originialValues);
	}

	/**
	 * Initializes this instance with an Iterable of values. Copies the given
	 * values to a backed container.
	 * 
	 * @param originialValues
	 *            the values to be sorted by this instance
	 */
	public HeapSorter(final Iterable<V> originialValues) {
		super(new ArrayList<V>());
		transform(originialValues);
	}

	@Override
	protected void transform(final V[] originalArrayValues) {
		// TODO: Transform into backed list
		valueCount = ((originalArrayValues == null) || (originalArrayValues.length == 0)) ? 0 : originalArrayValues.length;
		if (valueCount > 0) {
			container = new ArrayList<V>(originalArrayValues.length);
			container.addAll(Arrays.asList(originalArrayValues));
		}
	}

	@Override
	protected void transform(final Iterable<V> originalIterableValues) {
		valueCount = 0;
		if ((originalIterableValues != null) && (originalIterableValues.iterator().hasNext())) {
			container = new ArrayList<V>(100);
			final Iterator<V> it = originalIterableValues.iterator();
			while (it.hasNext()) {
				valueCount++;
				container.add(it.next());
			}
		} else {
			container = new ArrayList<V>(0);
		}
	}

	@Override
	public V[] sortArray() {
		final List<V> result = sortList();
		return (V[]) result.toArray();
	}

	@Override
	public List<V> sortList() {
		// TODO Sort the container and return and sorted flat representation of
		// the
		// backed values on the container structure.
		return null;
	}

	@Override
	public V[] reverseSortArray() {
		return (V[]) reversSortList().toArray();
	}

	@Override
	public List<V> reversSortList() {
		final List<V> result = sortList();
		Collections.reverse(result);
		return result;
	}

	private static int parent(final int i) {
		return (i - 1) / 2;
	}

	private static int left(final int i) {
		return (i * 2) + 1;
	}

	private static int right(final int i) {
		return (i * 2) + 2;
	}

	@Override
	public String toString() {
		final int new_line_count = 10;
		final StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[size=").append(this.valueCount).append("]\n");
		sb.append("idx[0 - ").append(new_line_count).append("]: ");
		for (int i = 0; i < container.size(); i++) {
			sb.append(container.get(i));
			if ((i + 1) < container.size()) {
				sb.append(", ");
			}
			if ((i > 0) && (i % new_line_count == 0)) {
				final int idxEnd = ((i + new_line_count) < container.size()) ? (i + new_line_count) : (container.size() - 1);
				sb.append(System.getProperty("line.separator"));
				sb.append("idx[").append(i + 1).append(" - ").append(idxEnd).append("]: ");
			}
		}
		return sb.toString();
	}
}
