package at.fhooe.swe4.lab3.hamming;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides class methods which are used to handle hamming numbers.
 * 
 * @author Thomas Herzog
 *
 */
public class Hamming {

	/**
	 * Not meant to be instantiated
	 */
	private Hamming() {
		super();
	}

	/**
	 * Calculates the hamming numbers to the given count.
	 * 
	 * @param count
	 *            the count of hamming numbers to calculate
	 * @return the sorted list containing the hamming numbers
	 * @throws IllegalArgumentException
	 *             if count <= 0
	 */
	public static List<BigInteger> calulcateHammingNumbers(final int count) {
		// At least one is in the hamming list
		if (count <= 1) {
			throw new IllegalArgumentException("The count must be at least one !!!");
		}
		final List<BigInteger> list = new ArrayList<BigInteger>();
		list.add(BigInteger.ONE);
		// The allowed factors
		final BigInteger second = BigInteger.valueOf(2);
		final BigInteger three = BigInteger.valueOf(3);
		final BigInteger five = BigInteger.valueOf(5);

		// As long as all of the intended numbers have been calculated
		// Calculate for 4 more elements because otherwise some numbers would be
		// missing
		for (int i = 0; ((list.size() - 4) < count); i++) {
			BigInteger secondMult = list.get(i).multiply(second);
			BigInteger threeMult = list.get(i).multiply(three);
			BigInteger fiveMult = list.get(i).multiply(five);
			// Avoid duplicates of 2 * x
			if (!list.contains(secondMult)) {
				list.add(secondMult);
			}
			// Avoid duplicates of 3 * x
			if (!list.contains(threeMult)) {
				list.add(threeMult);
			}
			// Avoid duplicates of 5 * x
			if (!list.contains(fiveMult)) {
				list.add(fiveMult);
			}
		}
		Collections.sort(list);
		// Remove the elements which are to much
		for (int i = (list.size() - 1); i >= count; i--) {
			list.remove(list.get(i));
		}
		return list;
	}

}
