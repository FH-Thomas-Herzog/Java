package at.fhooe.swe4.lab3.test.sort.heap;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fhooe.swe4.lab3.sort.api.Sorter.SortType;
import at.fhooe.swe4.lab3.sort.heap.impl.HeapSorter;
import at.fhooe.swe4.lab3.test.sort.api.AbstractSorterTest;

/**
 * This tests test the HeapSorter implementation
 * 
 * @author Thomas Herzog
 *
 */
@RunWith(JUnit4.class)
public class HeapSorterTest extends AbstractSorterTest<Integer> {

	private HeapSorter<Integer> sorter;

	@Before
	public void before() {
		sorter = new HeapSorter<Integer>();
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_null_array() {
		sorter.sort((Integer[]) null, SortType.ASCENDING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_null_list() {
		sorter.sort((List<Integer>) null, SortType.ASCENDING);
	}

	@Test
	public void test_empty_array() {
		System.out.println("public void test_empty_array()");
		final Integer[] result = sorter.sort(new Integer[0], SortType.ASCENDING);
		assertTrue("Resulting array should be empty", result.length == 0);
		System.out.println(sorter.getStatisitcs().toString());
	}

	@Test
	public void test_empty_list() {
		System.out.println("public void test_empty_list()");
		final List<Integer> result = sorter.sort(new ArrayList<Integer>(), SortType.ASCENDING);
		assertTrue("Resulting list should be empty", result.isEmpty());
		System.out.println(sorter.getStatisitcs().toString());
	}

	@Test
	public void test_already_ascending_sorted() {
		System.out.println("public void test_already_ascending_sorted()");
		final List<Integer> list = new ArrayList<Integer>(1000);
		for (int i = 0; i < 1000; i++) {
			list.add(i + 1);
		}
		final List<Integer> result = sorter.sort(list, SortType.ASCENDING);
		assertSorted(result, SortType.ASCENDING);
		System.out.println(sorter.getStatisitcs().toString());
	}

	@Test
	public void test_already_descending_sorted() {
		System.out.println("public void test_already_descending_sorted() ");
		final List<Integer> list = new ArrayList<Integer>(1000);
		for (int i = 1000; i > 0; i--) {
			list.add(i + 1);
		}
		final List<Integer> result = sorter.sort(list, SortType.DESCENDING);
		assertSorted(result, SortType.DESCENDING);
		System.out.println(sorter.getStatisitcs().toString());
	}

	@Test
	public void test_sort_ascending() {
		System.out.println("public void test_sort_ascending() ");
		int count = 1;
		final int factor = 10;
		final int repeation = 6;
		for (int i = 0; i < repeation; i++) {
			count *= factor;
			final List<Integer> result = sorter.sort(createRandomIntegerList(count, 1000), SortType.ASCENDING);
			assertSorted(result, SortType.ASCENDING);
		}
		System.out.println(sorter.getStatisitcs().toString());
	}

	@Test
	public void test_sort_descending() {
		System.out.println("public void test_sort_descending() ");
		int count = 1;
		final int factor = 10;
		final int repeation = 6;
		for (int i = 0; i < repeation; i++) {
			count *= factor;
			final List<Integer> result = sorter.sort(createRandomIntegerList(count, 1000), SortType.DESCENDING);
			assertSorted(result, SortType.DESCENDING);
		}
		System.out.println(sorter.getStatisitcs().toString());
	}

	@Test
	public void test_sort_ascending_time_measure() {
		System.out.println("public void test_sort_ascending_time_measure() ");
		int count = 1000000;
		long max = 0;
		long min = Long.MAX_VALUE;
		for (int i = 0; i < 20; i++) {
			long startMilis = System.currentTimeMillis();
			final List<Integer> list = createRandomIntegerList(count, System.currentTimeMillis(), 100000);
			final List<Integer> result = sorter.sort(list, SortType.ASCENDING);
			final long resultMillis = System.currentTimeMillis() - startMilis;
			System.out.println(new StringBuilder().append(i + 1).append(": ").append(resultMillis).append(" millis")
					.toString());
			if (resultMillis < min) {
				min = resultMillis;
			}
			if (resultMillis > max) {
				max = resultMillis;
			}
			assertSorted(result, SortType.ASCENDING);
		}
		System.out.println("-------------------");
		System.out.println("min:   " + min + " millis");
		System.out.println("max:   " + max + " millis");
		System.out.println("range: " + (max - min) + " millis");
		System.out.println("-------------------");
	}
}
