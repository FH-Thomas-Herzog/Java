package at.fhooe.swe4.lab3.test.sort.api;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import at.fhooe.swe4.lab3.sort.api.Sorter.SortType;

/**
 * The abstract test implementation which provides utitlites and assertation
 * methods.
 * 
 * @author Thomas Herzog
 *
 * @param <T>
 *            the Comparable type
 */
public class AbstractSorterTest<T extends Comparable<T>> {

	/**
	 * Asserts if the given list is sorted properly.
	 * 
	 * @param values
	 *            the values to be asserted
	 * @param sorterType
	 *            the sort type
	 */
	protected void assertSorted(final List<T> values, final SortType sorterType) {
		for (int i = 0; i < (values.size() - 1); i++) {
			assertTrue(values.get(i).toString() + " - " + values.get(i + 1).toString(),
					compare(values.get(i), values.get(i + 1), sorterType));
		}
	}

	/**
	 * Compares the to values depending on the sort type.
	 * 
	 * @param v1
	 *            the first value
	 * @param v2
	 *            the second value
	 * @param sorterType
	 *            the sorter type
	 * @return true if the values fit the condition.
	 */
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

	/**
	 * Defines no seed therefore will produce always the same integer values if
	 * the range and size are equal to a former creation.
	 * 
	 * @param size
	 *            the list size
	 * @param range
	 *            the range for the random numbers
	 * @return the list containing the random integer values
	 * @see AbstractSorterTest#createRandomIntegerList(int, long, int)
	 */
	protected List<Integer> createRandomIntegerList(final int size, final int range) {
		return createRandomIntegerList(size, 12345124352134L, range);
	}

	/**
	 * Generates a list with random integers from 1 to range.
	 * 
	 * @param size
	 *            the size of the resulted list
	 * @param seed
	 *            the seed to be used for the Random instance
	 * @param range
	 *            the range for the random integer values
	 * @return the list containing the random integer values
	 */
	protected List<Integer> createRandomIntegerList(final int size, final long seed, final int range) {
		final List<Integer> values = new ArrayList<Integer>(size);
		final Random randomGenerator = new Random(seed);
		for (int i = 0; i < size; i++) {
			values.add((randomGenerator.nextInt(range) + 1));
		}
		return values;
	}
}
