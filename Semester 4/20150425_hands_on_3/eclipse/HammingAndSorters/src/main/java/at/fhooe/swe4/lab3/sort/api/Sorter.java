package at.fhooe.swe4.lab3.sort.api;

import java.util.List;

import at.fhooe.swe4.lab3.stat.api.StatisticsProvider;

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
		 * Will result in an ascending ordered result
		 */
		DESCENDING,
		/**
		 * Will result in an descending ordered result
		 */
		ASCENDING;

		/**
		 * Compares the two comparable instances.
		 * <ul>
		 * <li>
		 * {@link SortType#DESCENDING} performs an x < 0 comparision</li>
		 * <li>{@link SortType#ASCENDING} performs an x > 0 comparision</li>
		 * </ul>
		 * 
		 * @param left
		 *            the instance which invokes the comparesTo method
		 * @param right
		 *            the parameter for lefts compareTomethod invocation
		 * @return the proper result for the specified heap type
		 */
		public <T extends Comparable<T>> boolean compare(T left, T right) {
			switch (this) {
			case DESCENDING:
				return left.compareTo(right) > 0;
			case ASCENDING:
				return left.compareTo(right) <= 0;
			default:
				throw new IllegalStateException("This enum is not handled here but should. enum=" + this.name());
			}
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
	 * @throws IllegalArgumentException
	 *             if the array is null, or the {@link SortType} is null
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
	 * @throws IllegalArgumentException
	 *             if the list is null, or the {@link SortType} is null
	 */
	public List<V> sort(List<V> list, SortType sorterType);

	/**
	 * Gets the statistics of the current instance
	 * 
	 * @return the current statistics
	 */
	public StatisticsProvider getStatisitcs();
}
