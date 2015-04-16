package at.fhooe.swe4.lab3.test.hamming;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fhooe.swe4.lab3.hamming.Hamming;

/**
 * This is the test for the calculating of the hamming numbers.
 * 
 * @author Thomas Herzog
 *
 */
@RunWith(JUnit4.class)
public class HammingTest {

	@Test(expected = IllegalArgumentException.class)
	public void test_invalid_count_negativ() {
		Hamming.calulcateHammingNumbers(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_invalid_count_zeor() {
		Hamming.calulcateHammingNumbers(0);
	}

	@Test
	public void test_caluclation() {
		int count = 1;
		final int factor = 10;
		final int repeation = 6;
		for (int i = 0; i < repeation; i++) {
			count *= factor;
			final long startMillis = System.currentTimeMillis();
			final List<BigInteger> result = Hamming.calulcateHammingNumbers(count);
			final long diffMillis = System.currentTimeMillis() - startMillis;
			System.out.println("----------------------------------------------------------------");
			System.out.println(new StringBuilder("Spend time in millis: '").append(diffMillis).append("' for '").append(count).append("' hamming numbers")
					.toString());
			for (int j = 0; (j < result.size()) && (j < 20); j++) {
				System.out.println(new StringBuilder().append(j + 1).append(": ").append(result.get(j)).toString());
			}
			System.out.println("----------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------");
		}
	}
}
