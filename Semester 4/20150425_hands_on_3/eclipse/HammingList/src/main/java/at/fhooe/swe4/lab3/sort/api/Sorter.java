package at.fhooe.swe4.lab3.sort.api;

import java.util.List;

import at.fhooe.swe4.lab3.sort.heap.Heap.HeapType;
import at.fhooe.swe4.lab3.stat.StatisticsProvider;

/**
 * This interface specifies the sorter functionalities.
 * 
 * @author Thomas Herzog
 *
 * @param <V>
 *            the values type of the collections or array elements
 */
public interface Sorter<V extends Comparable<V>> {
	/**
	 * This enumeration specifies the sort order for a heap sort instance.
	 * 
	 * @author Thomas Herzog
	 *
	 */
	public static enum SortType {
		/**
		 * WIll result an ascending ordered result
		 */
		DESCENDING(HeapType.MAX_HEAP), /**
		 * WIll result an descending ordered
		 * result
		 */
		ASCENDING(HeapType.MIN_HEAP);

		public final HeapType heapType;

		private SortType(HeapType heapType) {
			this.heapType = heapType;
		}
	}

	/**
	 * Sorts the given array.
	 * 
	 * @param array
	 *            the array to be sorted
	 * @param sorterType
	 *            the type of the sorting
	 * @return the sorted array
	 * @see SortType
	 */
	public V[] sort(V[] array, SortType sorterType);

	/**
	 * Sorts the given list5.
	 * 
	 * @param list
	 *            the list to be sorted
	 * @param sorterType
	 *            the type of the sorting
	 * @return the sorted array
	 * @see SortType
	 */
	public List<V> sort(List<V> list, SortType sorterType);

	/**
	 * Gets the statistics of the current instance
	 * 
	 * @return the current statistics
	 */
	public StatisticsProvider getStatisitcs();
}
