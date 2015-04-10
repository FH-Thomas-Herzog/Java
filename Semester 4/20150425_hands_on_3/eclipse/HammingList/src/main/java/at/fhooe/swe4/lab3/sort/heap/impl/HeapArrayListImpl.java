package at.fhooe.swe4.lab3.sort.heap.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.fhooe.swe4.lab3.sort.api.Heap;
import at.fhooe.swe4.lab3.stat.CodeStatistics;
import at.fhooe.swe4.lab3.stat.StatisticsProvider;
import at.fhooe.swe4.lab3.stat.DefaultStatisticsProviderImpl;

/**
 * This is the ArrayList implementation of the heap.
 * 
 * @author Thomas Herzog
 *
 * @param <V>
 *            the value type of the heap managed elements
 */
public class HeapArrayListImpl<V extends Comparable<V>> implements Heap<V> {

	private int heapCounter = 0;
	public HeapType heapType;
	public List<V> container = new ArrayList<V>();

	public StatisticsProvider statProvider = new DefaultStatisticsProviderImpl("heap-array-list-impl");

	/**
	 * Empty constructor
	 */
	public HeapArrayListImpl() {
		super();
	}

	/**
	 * Initializes the heap with the given array
	 * 
	 * @param array
	 *            the array providing the elements for the heap
	 * @param heapType
	 *            the type of the heap
	 * @see HeapType
	 */
	public HeapArrayListImpl(final V[] array, final HeapType heapType) {
		super();
		init(array, heapType);
	}

	/**
	 * Initializes the heap with the given iterable
	 * 
	 * @param list
	 *            the iterable providing the elements for the heap
	 * @param heapType
	 *            the type of the heap
	 * @see HeapType
	 */
	public HeapArrayListImpl(final Iterable<V> list, final HeapType heapType) {
		super();
		init(list, heapType);
	}

	@Override
	public void init(final V[] originalArrayValues, final HeapType heapType) {
		heapCounter++;
		this.heapType = heapType;
		container.clear();
		int size = ((originalArrayValues == null) || (originalArrayValues.length == 0)) ? 0 : originalArrayValues.length;
		if (size > 0) {
			container = new ArrayList<V>(originalArrayValues.length);
			statProvider.initContext("heap." + heapCounter);
			final CodeStatistics stat = statProvider.getCtx().newStatistic("init(array)");
			for (V value : originalArrayValues) {
				enqueue(value);
			}
		}
	}

	@Override
	public void init(final Iterable<V> originalIterableValues, final HeapType heapType) {
		this.heapType = heapType;
		container.clear();
		if ((originalIterableValues != null) && (originalIterableValues.iterator().hasNext())) {
			container = new ArrayList<V>(100);
			final Iterator<V> it = originalIterableValues.iterator();
			while (it.hasNext()) {
				enqueue(it.next());
			}
		} else {
			container = new ArrayList<V>(0);
		}
	}

	@Override
	public void enqueue(final V value) {
		container.add(value);
		upHeap(container);
	}

	@Override
	public V dequeue() {
		final V value = container.get(0);
		container.set(0, container.get(container.size() - 1));
		downHeap(container);
		container.remove(container.size() - 1);
		return value;
	}

	@Override
	public boolean hasNext() {
		return container.size() > 0;
	}

	@Override
	public int size() {
		return container.size();
	}

	private void upHeap(final List<V> container) {
		final CodeStatistics stat = statProvider.getCtx().byKey("upHeap()", Boolean.TRUE);

		int i = container.size() - 1;
		stat.incIdx();
		V tmp = container.get(i);
		while ((i != 0) && (heapType.compare(container.get(parent(i)), tmp))) {
			stat.incIf().incIf().incIdx().incIdx().incIdx();
			container.set(i, container.get(parent(i)));
			i = parent(i);
		}
		stat.incIdx();
		container.set(i, tmp);
	}

	private void downHeap(final List<V> container) {
		final CodeStatistics stat = statProvider.getCtx().byKey("downHeap()", Boolean.TRUE);
		int idx = 0;
		int largeIdx;
		V tmp = container.get(0);
		stat.incIdx();
		while (idx < (container.size() / 2)) {
			int leftIdx = left(idx);
			int rightIdx = right(idx);
			if ((rightIdx < container.size()) && (heapType.compare(container.get(leftIdx), container.get(rightIdx)))) {
				largeIdx = rightIdx;
			} else {
				largeIdx = leftIdx;
			}
			if (!heapType.compare(tmp, container.get(largeIdx))) {
				break;
			}
			container.set(idx, container.get(largeIdx));
			idx = largeIdx;
			stat.incIf().incIf().incIf().incIf().incIf().incIf().incIdx().incIdx().incIdx().incIdx();
		}
		container.set(idx, tmp);
		stat.incIdx();
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

	public List<V> toList() {
		return new ArrayList<V>(container);
	}

	@SuppressWarnings("unchecked")
	public V[] toArray() {
		return (V[]) container.toArray();
	}

	@Override
	public StatisticsProvider getStatisitcs() {
		return statProvider.endContext();
	}

	@Override
	public String toString() {
		final int new_line_count = 10;
		final StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[size=").append(container.size()).append("]\n");
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
