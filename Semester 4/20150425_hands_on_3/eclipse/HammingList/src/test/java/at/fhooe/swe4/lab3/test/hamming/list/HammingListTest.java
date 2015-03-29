package at.fhooe.swe4.lab3.test.hamming.list;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fhooe.swe4.lab3.hamming.list.HammingList;

@RunWith(JUnit4.class)
public class HammingListTest {

	private HammingList hl;

	@Before
	public void beforeTest() {
		hl = new HammingList();
	}

	@Test
	public void test_primes_are_invalid() {
		final int[] primes = { 7, 11, 13, 17, 19, 23, 29, 31 };
		for (int prime : primes) {
			try {
				hl.init(prime).calculate();
				fail("The current prime should not be part of the hamming list. prime=" + prime);
			} catch (IllegalStateException e) {
				// DO nothing because expected.
			}
		}
	}

	@Test
	public void test_heavy_number() {
		final long startMillis = System.currentTimeMillis();
		final long n = 288325195312500000L;
		
		hl.init(n).calculate().print();
		
		final long endMillis = System.currentTimeMillis();
		final long diffMillis = endMillis - startMillis;
		System.out.println("Spend time in millis: " + diffMillis);
	}
}
