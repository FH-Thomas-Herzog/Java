package at.fhooe.swe4.lab3.test.sort.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fhooe.swe4.lab3.sort.api.Sorter.SortType;
import at.fhooe.swe4.lab3.sort.heap.HeapSorter;

@RunWith(JUnit4.class)
public class HeapSorterTest {

	private HeapSorter<Integer> sorter;
	List<Integer> result;

	@Test
	public void test_1() {
		System.out.println("Start test");
		final List<Integer> values = new ArrayList<Integer>(50);
		final Random r = new Random(12345124352134L);
		for (int i = 0; i < 10; i++) {
			values.add((r.nextInt(1000000000) + 1));
		}
		sorter = new HeapSorter<Integer>();
		result = sorter.sort(values, SortType.ASCENDING);
		for (int i = 0; i < result.size(); i++) {
			if ((i + 1) < result.size()) {
				System.out.println("idx-" + i + " " + result.get(i));
				if (result.get(i) > result.get(i + 1)) {
					System.out.print("idx-" + (i + 1) + " " + "[" + result.get(i + 1) + "]");
					throw new IllegalStateException("Illegal state");
				}
			}
		}
		System.out.println("\n\n");
		result = sorter.sort(values, SortType.DESCENDING);
		// for (Integer integer : result) {
		// System.out.print(integer + ", ");
		// }
		System.out.println();
		System.out.println();
		System.out.println(sorter.getStatisitcs().toString());
	}
}
