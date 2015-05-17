package at.fh.ooe.swe4.test.collections.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import at.fh.ooe.swe4.collections.api.SortedTreeSet;
import at.fh.ooe.swe4.collections.impl.BinarySearchTreeSet;
import at.fh.ooe.swe4.junit.test.suite.watcher.AbstractConsoleLoggingTest;

@RunWith(Parameterized.class)
public class SortedTreeSetTest extends AbstractConsoleLoggingTest {

	private SortedTreeSet<Integer> instance;

	private final Class<? extends SortedTreeSet<Integer>> clazz;
	private final Comparator<Integer> comparator;

	@Parameters
	public static Collection<Object[]> prepareParameters() {
		return Arrays.asList(new Object[][] { { BinarySearchTreeSet.class, null } });
	}

	/**
	 * Constructor for parameter injection.
	 * 
	 * @param clazz
	 *            The implementation class of the {@link SortedTreeSet}
	 *            interface
	 * @param comparator
	 *            and comparator instance to be used by the instance
	 */
	public SortedTreeSetTest(final Class<SortedTreeSet<Integer>> clazz, final Comparator<Integer> comparator) {
		super();
		this.clazz = clazz;
		this.comparator = comparator;
	}

	/**
	 * Initializes an instance for the injected class.
	 */
	@Before
	public void before() {
		try {
			if (comparator == null) {
				instance = clazz.newInstance();
			} else {
				instance = clazz.getConstructor(Comparator.class)
								.newInstance(comparator);
			}
		} catch (Throwable e) {
			throw new IllegalStateException("Could not prepare test");
		}
	}

	@Test
	public void addDuplicates() {
		// -- Given --
		final Integer value = 1;

		// -- When --
		instance.add(value);
		instance.add(value);
		instance.add(value);

		// -- Then --
		assertEquals(1, instance.size());
		assertTrue(Arrays.equals(new Integer[] { 1 }, instance.toArray(new Integer[instance.size()])));
	}

	@Test
	public void addNull() {
		// -- Given --
		final Integer value = null;

		// -- When --
		instance.add(value);

		// -- Then --
		assertEquals(1, instance.size());
		assertTrue(Arrays.equals(new Integer[] { null }, instance.toArray(new Integer[instance.size()])));
	}

	@Test
	public void addAscendingSorted() {
		// -- Given --
		final int expectedSize = 10;
		final Integer[] expectedElements = new Integer[expectedSize];
		for (int i = 0; i < expectedSize; i++) {
			expectedElements[i] = i + 1;

			// -- When --
			instance.add(expectedElements[i]);
		}

		// -- Then --
		assertEquals(expectedSize, instance.size());
		assertTrue(Arrays.equals(expectedElements, instance.toArray(new Integer[instance.size()])));
	}

	@Test
	public void addDescendingSorted() {
		// -- Given --
		final int expectedSize = 10;
		final List<Integer> expectedElements = new ArrayList<>(expectedSize);
		for (int i = expectedSize; i > 0; i--) {
			final int value = i + 1;
			expectedElements.add(value);

			// -- When --
			instance.add(value);
		}
		Collections.sort(expectedElements);

		// -- Then --
		assertEquals(expectedSize, instance.size());
		assertTrue(Arrays.equals(expectedElements.toArray(new Integer[expectedSize]), instance.toArray(new Integer[instance.size()])));
	}

	@Test
	public void addRandomly() {
		// -- Given --
		final int expectedSize = 10;
		final Random rand = new Random();
		final Set<Integer> tmpSet = new HashSet<>();
		while (tmpSet.size() < 10) {
			final int value = rand.nextInt(1000) + 1;
			tmpSet.add(value);
		}
		final List<Integer> expectedElements = new ArrayList<Integer>(tmpSet);
		Collections.sort(expectedElements);

		// -- When --
		for (Integer value : tmpSet) {
			instance.add(value);
		}

		// -- Then --
		assertEquals(expectedSize, instance.size());
		assertTrue(Arrays.equals(expectedElements.toArray(new Integer[expectedSize]), instance.toArray(new Integer[instance.size()])));
	}
}
