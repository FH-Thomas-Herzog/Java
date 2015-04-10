package at.fhooe.swe4.lab3.test.sort.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fhooe.swe4.lab3.sort.api.Sorter;
import at.fhooe.swe4.lab3.sort.api.Sorter.SortType;
import at.fhooe.swe4.lab3.sort.heap.impl.HeapSorter;
import at.fhooe.swe4.lab3.sort.quick.QuickSorter;

@RunWith(JUnit4.class)
public class HeapSorterTest {

	private HeapSorter<Integer> sorter;
	List<Integer> result;

	@Test
	public void test_1() {
		System.out.println("Start test");

		sorter = new HeapSorter<Integer>();
		final List<Integer> values = createList(1000, 1000);
		result = sorter.sort(values, SortType.ASCENDING);
		for (int i = 0; i < result.size(); i++) {
			if (((i + 1) < result.size()) && (result.get(i) > result.get(i + 1))) {
				Assert.fail("Not sorted");
			}
			System.out.println("idx-" + i + " " + result.get(i));
		}
	}

	@Test
	public void test_quick() {
		Sorter<Integer> sorter = new QuickSorter();
		final List<Integer> values = createList(20, 1000);
		result = sorter.sort(values, SortType.ASCENDING);
		for (int i = 0; i < result.size(); i++) {
			if (((i + 1) < result.size()) && (result.get(i) > result.get(i + 1))) {
				Assert.fail("Not sorted " + result.get(i + 1));
			}
			System.out.println("idx-" + i + " " + result.get(i));
		}
	}

	private List<Integer> createList(final int size, final int range) {
		final List<Integer> values = new ArrayList<Integer>(50);
		final Random r = new Random(12345124352134L);
		for (int i = 0; i < size; i++) {
			values.add((r.nextInt(range) + 1));
		}
		return values;
	}
}
