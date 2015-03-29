package at.fhooe.swe4.lab3.test.sort.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fhooe.swe4.lab3.sort.api.Sorter.SortType;
import at.fhooe.swe4.lab3.sort.heap.HeapSorter;

@RunWith(JUnit4.class)
public class HeapSorterTest {

	private HeapSorter sorter;
	List<Integer> result;

	@Test
	public void test_1() {
		final List<Integer> values = new ArrayList<Integer>(
				Arrays.asList(new Integer[] { 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 }));
		sorter = new HeapSorter<Integer>();
		result = sorter.sort(values, SortType.ASCENDING);
		for (Integer integer : result) {
			System.out.print(integer + ", ");
		}
		System.out.println("\n\n");
		result = sorter.sort(values, SortType.DESCENDING);
		for (Integer integer : result) {
			System.out.print(integer + ", ");
		}
	}
}
