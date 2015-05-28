package at.fh.ooe.swe4.test.collections.binarySearchTreeSet.impl;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe4.collections.impl.BinarySearchTreeSet;
import at.fh.ooe.swe4.collections.impl.BinarySearchTreeSet.BinarySearchTreeIterator;
import at.fh.ooe.swe4.collections.model.BinaryTreeNode;
import at.fh.ooe.swe4.junit.test.suite.watcher.AbstractConsoleLoggingTest;

/**
 * This class tests the {@link BinarySearchTreeIterator} used by the
 * {@link BinarySearchTreeSet} implementation.
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
		final Iterator<Integer> it = new BinarySearchTreeIterator<Integer>(null);

		// -- When --
		it.next();
	}

	// -- Then --
	@Test(expected = NoSuchElementException.class)
	public void noMoreNodes() {
		// -- Given --
		final Integer expected = 1;
		final BinaryTreeNode<Integer> node = new BinaryTreeNode<Integer>(
				expected);
		final Iterator<Integer> it = new BinarySearchTreeIterator<Integer>(node);

		// -- When --
		int i = 0;
		while (i < 5) {
			assertEquals(expected, it.next());
			i++;
		}
	}
}
