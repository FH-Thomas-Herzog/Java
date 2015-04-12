package at.fhooe.swe4.lab3.sort.heap.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.fhooe.swe4.lab3.sort.api.Heap;
import at.fhooe.swe4.lab3.sort.api.Heap.HeapType;
import at.fhooe.swe4.lab3.sort.api.Sorter;
import at.fhooe.swe4.lab3.stat.StatisticsProvider;

/**
 * This is the heap sorter implementation of the Sorter interface.
 * 
 * @author Thomas Herzog
 *
 * @param <V>
 *            the values type of the to sort array or collection managed
 *            elements
 */
public class HeapSorter<V extends Comparable<V>> implements Sorter<V> {

	private final Heap<V> heap = new HeapArrayListImpl<V>();

	public HeapSorter() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public V[] sort(final V[] array, final SortType sorterType) {
		if (array == null) {
			throw new IllegalArgumentException("Cannot sort empty array");
		}
		return (array.length == 0) ? array : ((V[]) sort(Arrays.asList(array), sorterType).toArray());
	}

	@Override
	public List<V> sort(final List<V> list, final SortType sorterType) {
		if (sorterType == null) {
			throw new IllegalArgumentException("SorterType not defined");
		}
		if (list == null) {
			throw new IllegalArgumentException("Cannot sort null list");
		}
		heap.init(list, convertToHeapType(sorterType));
		final List<V> result = new ArrayList<V>();
		while (heap.hasNext()) {
			result.add(heap.dequeue());
		}
		return result;
	}

	@Override
	public StatisticsProvider getStatisitcs() {
		return heap.getStatisitcs();
	}

	/**
	 * Converts the sorter type to the corresponding heap type.
	 * 
	 * @param sortType
	 *            the sorter type to be converted
	 * @return the corresponding heap type
	 * @throws IllegalArgumentException
	 *             if the sorter type cannot be mapped to a corresponding heap
	 *             type
	 */
	private HeapType convertToHeapType(final SortType sortType) {
		switch (sortType) {
		case ASCENDING:
			return HeapType.MAX_HEAP;
		case DESCENDING:
			return HeapType.MIN_HEAP;
		default:
			throw new IllegalArgumentException("SortType cannot bemapped to corresponding HeapType !!!");
		}
	}
}
