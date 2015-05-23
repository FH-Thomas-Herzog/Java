package at.fh.ooe.swe4.test.collections.binarySearchTreeSet.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe4.collections.api.AbstractSortedSet;
import at.fh.ooe.swe4.collections.impl.BinarySearchTreeSet.BinarySearchtreeIterator;
import at.fh.ooe.swe4.collections.model.BinaryTreeNode;
import at.fh.ooe.swe4.junit.test.suite.watcher.AbstractConsoleLoggingTest;

@RunWith(JUnit4.class)
public class IteratorTest extends AbstractConsoleLoggingTest {

	// -- Then --
	@Test(expected = NoSuchElementException.class)
	public void noNodes() {
		// -- Given --
		final Iterator<Integer> it = new BinarySearchtreeIterator<Integer>(null);

		// -- When --
		it.next();
	}

	// -- Then --
	@Test(expected = NoSuchElementException.class)
	public void noMoreNodes() {
		// -- Given --
		final Integer expected = 1;
		final BinaryTreeNode<Integer> node = new BinaryTreeNode<Integer>(expected);
		final Iterator<Integer> it = new BinarySearchtreeIterator<Integer>(node);

		// -- When --
		int i = 0;
		while (i < 5) {
			assertEquals(expected, it.next());
			i++;
		}
	}
}
