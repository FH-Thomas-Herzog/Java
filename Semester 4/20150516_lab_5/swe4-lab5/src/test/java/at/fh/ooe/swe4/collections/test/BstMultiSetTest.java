package at.fh.ooe.swe4.collections.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe4.lab5.collections.BstMultiSet;
import at.fh.ooe.swe4.lab5.collections.SortedMultiSet;

@RunWith(JUnit4.class)
public class BstMultiSetTest {

	@Test
	public void addTest() {
		SortedMultiSet<Integer> bst = new BstMultiSet<>();
		assertEquals(0, bst.size());
		bst.add(5);
		assertEquals(1, bst.size());
		assertNull(bst.get(1));
		assertNotNull(bst.get(5));
		bst.add(2);
		assertEquals(2, bst.size());
	}

	@Test
	public void getTest() {
		SortedMultiSet<Integer> bst = new BstMultiSet<>();
		bst.add(5);
		bst.add(2);
		bst.add(4);

		assertEquals(5, bst.get(5)
							.intValue());
		assertEquals(2, bst.get(2)
							.intValue());
		assertEquals(4, bst.get(4)
							.intValue());
	}

	@Test(expected = NoSuchElementException.class)
	public void iteratorTest() {
		SortedMultiSet<Integer> bst = new BstMultiSet<>();
		bst.add(5);
		bst.add(2);
		bst.add(4);

		Iterator<Integer> it = bst.iterator();
		it.next();
		it.next();
		it.next();
		it.next();
	}
}
