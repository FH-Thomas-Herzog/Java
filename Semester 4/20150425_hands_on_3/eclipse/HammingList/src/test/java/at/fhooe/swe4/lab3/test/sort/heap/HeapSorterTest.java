package at.fhooe.swe4.lab3.test.sort.heap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fhooe.swe4.lab3.sort.heap.HeapSorter;

@RunWith(JUnit4.class)
public class HeapSorterTest {

	private HeapSorter sorter;

	@Test
	public void test_1() {
		sorter = new HeapSorter(new Integer[] { 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 });
		System.out.println(sorter.toString());
	}
}
