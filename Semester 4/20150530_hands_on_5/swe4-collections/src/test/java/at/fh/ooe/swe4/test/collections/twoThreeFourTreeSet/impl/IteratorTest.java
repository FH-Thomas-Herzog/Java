package at.fh.ooe.swe4.test.collections.twoThreeFourTreeSet.impl;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe4.collections.impl.TwoThreeFourTreeSet;
import at.fh.ooe.swe4.collections.iterator.NMKTreeIterator;
import at.fh.ooe.swe4.collections.model.NMKTreeTreeNode;
import at.fh.ooe.swe4.junit.test.suite.watcher.AbstractConsoleLoggingTest;

/**
 * This class tests the {@link NMKTreeIterator} used by the
 * {@link TwoThreeFourTreeSet} implementation.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 27, 2015
 */
@RunWith(JUnit4.class)
public class IteratorTest extends AbstractConsoleLoggingTest {

	// -- Then --
	@Test(expected = NoSuchElementException.class)
	public void noNodes() {
		// -- Given --
		final Iterator<Integer> it = new NMKTreeIterator<Integer>(null);

		// -- When --
		it.next();
	}

	// -- Then --
	@Test(expected = NoSuchElementException.class)
	public void noMoreNodes() {
		// -- Given --
		final Integer expected = 1;
		final NMKTreeTreeNode<Integer> node = new NMKTreeTreeNode<Integer>(
				expected);
		final Iterator<Integer> it = new NMKTreeIterator<Integer>(node);

		// -- When --
		int i = 0;
		while (i < 5) {
			assertEquals(expected, it.next());
			i++;
		}
	}
}
