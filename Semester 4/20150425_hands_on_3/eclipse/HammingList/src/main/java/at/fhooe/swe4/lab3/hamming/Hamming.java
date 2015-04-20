package at.fhooe.swe4.lab3.hamming;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Provides class methods which are used to handle hamming numbers.
 * 
 * @author Thomas Herzog
 *
 */
public class Hamming {

	private static final BigInteger second = BigInteger.valueOf(2);
	private static final BigInteger three = BigInteger.valueOf(3);
	private static final BigInteger five = BigInteger.valueOf(5);

	/**
	 * Not meant to be instantiated
	 */
	private Hamming() {
		super();
	}

	/**
	 * Calculates the 'count' hamming numbers.
	 * 
	 * @param count
	 *            the count of to calculate hamming numbers
	 * @return the sorted list holding the hamming numbers
	 */
	public static List<BigInteger> calulcateHammingNumbers(final int count) {
		// At least one is in the hamming list
		if (count <= 1) {
			throw new IllegalArgumentException("The count must be at least one !!!");
		}
		// Avoid grow of ArrayList
		final List<BigInteger> list = new ArrayList<BigInteger>(count);
		// Keeps calculated elements sorted
		final NavigableSet<BigInteger> sortedSet = new TreeSet<BigInteger>();
		// ONE is initial value
		sortedSet.add(BigInteger.ONE);
		// As long as we need to calculate
		while (list.size() != count) {
			// Get the next hammming number and remove from set
			final BigInteger currentValue = sortedSet.pollFirst();
			// Add this element to result lsit
			list.add(currentValue);
			// calculate next hamming numbers
			sortedSet.add(currentValue.multiply(second));
			sortedSet.add(currentValue.multiply(three));
			sortedSet.add(currentValue.multiply(five));
			System.out.println("size: " + sortedSet.size());
		}
		// Returned list is implicitly sorted
		return list;
	}
}
