package at.fhooe.swe4.lab3.sort.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.fhooe.swe4.lab3.sort.api.AbstractCodeStatisticsProvider;
import at.fhooe.swe4.lab3.sort.api.Sorter;
import at.fhooe.swe4.lab3.sort.api.Sorter.SortType;

/**
 * This is the heap sorter implementation of the Sorter interface.
 * 
 * @author Thomas Herzog
 *
 * @param <V>
 *            the values type of the to sort array or collection managed
 *            elements
 */
public class HeapSorter<V extends Comparable<V>> extends AbstractCodeStatisticsProvider implements Sorter<V> {

	public HeapSorter() {
		super();
	}

	@Override
	public V[] sort(final V[] array, final SortType sorterType) {
		final List<V> result = sort(Arrays.asList(array), sorterType);
		return (V[]) result.toArray();
	}

	@Override
	public List<V> sort(final List<V> list, final SortType sorterType) {
		if (sorterType == null) {
			throw new IllegalArgumentException("SorterType not defined");
		}
		final Heap<V> heap = new HeapArrayListImpl<V>();
		heap.init(list, sorterType.heapType);
		final List<V> result = new ArrayList<V>();
		while (heap.hasNext()) {
			result.add(heap.dequeue());
		}
		return result;
	}
}
