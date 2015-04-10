package at.fhooe.swe4.lab3.sort.quick;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.fhooe.swe4.lab3.sort.api.Sorter;
import at.fhooe.swe4.lab3.stat.StatisticsProvider;

/**
 * This is the Sorter implementation for the quicksort algorithm
 * 
 * @author Thomas Herzog
 *
 * @param <V>
 *            the values type of the to sort elements
 */
public class QuickSorter<V extends Comparable<V>> implements Sorter<V> {
	public QuickSorter() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public V[] sort(final V[] array, final SortType sorterType) {
		final List<V> result = sort(Arrays.asList(array), sorterType);
		return (V[]) result.toArray();
	}

	@Override
	public List<V> sort(List<V> list, SortType sorterType) {
		if (sorterType == null) {
			throw new IllegalArgumentException("SorterType not defined");
		}
		quicksort(list, 0, (list.size() - 1));
		if (SortType.DESCENDING.equals(sorterType)) {
			Collections.reverse(list);
		}
		return list;
	}

	/**
	 * Performs a quicksort in ascending order.
	 * 
	 * @param values
	 *            the values to be sorted
	 * @param start
	 *            the start index
	 * @param end
	 *            the end index
	 */
	private void quicksort(final List<V> values, final int start, final int end) {
		int i = start;
		int k = end;

		if ((end - start) >= 1) {
			V pivot = values.get(start);
			while (k > i) {
				while ((values.get(i).compareTo(pivot) <= 0) && (i <= end) && (k > i)) {
					i++;
				}
				while ((values.get(k).compareTo(pivot) > 0) && (k >= start) && (k >= i)) {
					k--;
				}
				if (k > i) {
					swap(values, i, k);
				}
			}
			swap(values, start, k);
			quicksort(values, start, k - 1);
			quicksort(values, k + 1, end);
		}
	}

	/**
	 * Swaps the elements at the indexes
	 * 
	 * @param values
	 *            the array list where to swap elements
	 * @param i
	 *            the first index
	 * @param j
	 *            the second index
	 */
	private void swap(final List<V> values, final int i, final int j) {
		final V tmp = values.get(i);
		values.set(i, values.get(j));
		values.set(j, tmp);
	}

	@Override
	public StatisticsProvider getStatisitcs() {
		// TODO Auto-generated method stub
		return null;
	}
}
