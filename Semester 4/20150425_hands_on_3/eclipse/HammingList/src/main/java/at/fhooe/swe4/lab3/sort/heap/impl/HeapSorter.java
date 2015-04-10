package at.fhooe.swe4.lab3.sort.heap.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.fhooe.swe4.lab3.sort.api.Heap;
import at.fhooe.swe4.lab3.sort.api.Heap.HeapType;
import at.fhooe.swe4.lab3.sort.api.Sorter;
import at.fhooe.swe4.lab3.stat.DefaultStatisticsProviderImpl;
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

	private final StatisticsProvider statProvider = new DefaultStatisticsProviderImpl(super.toString());

	public HeapSorter() {
		super();
	}

	@SuppressWarnings("unchecked")
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
		heap.init(list, convertToHeapType(sorterType));
		final List<V> result = new ArrayList<V>();
		while (heap.hasNext()) {
			result.add(heap.dequeue());
		}
		statProvider.takeOver("heap-sorter", heap.getStatisitcs());
		return result;
	}

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

	@Override
	public StatisticsProvider getStatisitcs() {
		return statProvider.endContext();
	}
}
