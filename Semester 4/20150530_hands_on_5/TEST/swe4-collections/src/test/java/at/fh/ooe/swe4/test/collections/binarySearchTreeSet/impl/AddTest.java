package at.fh.ooe.swe4.test.collections.binarySearchTreeSet.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe4.collections.api.SortedTreeSet;
import at.fh.ooe.swe4.collections.impl.BinarySearchTreeSet;
import at.fh.ooe.swe4.junit.test.suite.watcher.AbstractConsoleLoggingTest;

/**
 * This test class tests the {@link BinarySearchTreeSet#add(Object)} method.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 27, 2015
 */
@RunWith(JUnit4.class)
public class AddTest extends AbstractConsoleLoggingTest {

	private final SortedTreeSet<Integer> set = new BinarySearchTreeSet<Integer>();

	@Test
	public void addNull() {
		// -- Given --
		final Integer i = null;

		// -- When | Then --
		assertFalse(set.add(i));
	}

	@Test
	public void testLevel4() {
		// -- Given --
		final int height = 4;

		// -- When --
		set.add(1);
		set.add(2);
		set.add(3);
		set.add(4);

		// -- Then --
		assertEquals(height, set.height());
	}

	@Test
	public void testLevel5() {
		// -- Given --
		final int height = 5;

		// -- When --
		// 1. First level
		set.add(5);
		set.add(10);
		set.add(15);

		// 2 levels after split of root
		set.add(1);
		// 3. Add elements to 2 level
		set.add(2);
		set.add(11);
		set.add(12);

		// -- Then --
		assertEquals(height, set.height());
	}

	@Test
	public void testLevel6() {
		// -- Given --
		final int height = 6;

		// -- When --
		// 1. first level
		set.add(5);
		set.add(10);
		set.add(15);

		// 2. 2 level after split
		set.add(1);
		// 3. New keys for child '5'
		set.add(2);
		set.add(3);
		// 4. new keys for child '15'
		set.add(11);
		set.add(12);
		// 5. 3 level after split
		set.add(13);

		// -- Then --
		assertEquals(height, set.height());
	}
}
