package at.fhooe.swe4.lab3.test.sort.quick;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fhooe.swe4.lab3.sort.api.Sorter.SortType;
import at.fhooe.swe4.lab3.sort.quick.QuickSorter;
import at.fhooe.swe4.lab3.test.sort.api.AbstractSorterTest;

/**
 * This class tests the {@link QuickSorter} implementation
 * 
 * @author Thomas Herzog
 *
 */
@RunWith(JUnit4.class)
public class QuickSorterTest extends AbstractSorterTest<Integer> {

	private QuickSorter<Integer> sorter;

	@Before
	public void before() {
		sorter = new QuickSorter<Integer>();
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
		sorter.sort(new Integer[0], SortType.ASCENDING);
	}

	@Test
	public void test_empty_list() {
		sorter.sort(new ArrayList<Integer>(), SortType.ASCENDING);
	}

	@Test
	public void test_sort_ascending() {
		int count = 1;
		final int factor = 10;
		final int repeation = 6;
		for (int i = 0; i < repeation; i++) {
			count *= factor;
			System.out.println("------------------------------------------------------------");
			System.out.println(new StringBuilder("Sorting list with '").append(count).append("'").append("elements").toString());
			System.out.println("------------------------------------------------------------");
			final List<Integer> result = sorter.sort(createRandomIntegerList(count, 1000), SortType.ASCENDING);
			assertSorted(result, SortType.ASCENDING);
			System.out.println("------------------------------------------------------------");
		}
		System.out.println(sorter.getStatisitcs().toString());
	}

	@Test
	public void test_sort_descending() {
		int count = 1;
		final int factor = 10;
		final int repeation = 6;
		for (int i = 0; i < repeation; i++) {
			count *= factor;
			System.out.println("------------------------------------------------------------");
			System.out.println(new StringBuilder("Sorting list with '").append(count).append("'").append("elements").toString());
			System.out.println("------------------------------------------------------------");
			final List<Integer> result = sorter.sort(createRandomIntegerList(count, 1000), SortType.DESCENDING);
			assertSorted(result, SortType.DESCENDING);
			System.out.println("------------------------------------------------------------");
		}
		System.out.println(sorter.getStatisitcs().toString());
	}
}