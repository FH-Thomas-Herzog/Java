package at.fh.ooe.swe4.collections.impl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import at.fh.ooe.swe4.collections.api.AbstractSortedSet;
import at.fh.ooe.swe4.collections.api.SortedTreeSet;
import at.fh.ooe.swe4.collections.model.BinaryTreeNode;

/**
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 17, 2015
 * @param <T>
 */
public class BinarySearchTreeSet<T> extends
		AbstractSortedSet<T, BinaryTreeNode<T>> implements SortedTreeSet<T> {

	/**
	 * This is the iterator for the {@link BinarySearchTreeSet} class.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 17, 2015
	 * @param <T>
	 */
	public static class BinarySearchTreeIterator<T> implements Iterator<T> {

		private final Stack<BinaryTreeNode<T>> unvisitedNodes = new Stack<>();

		/**
		 * Initializes this iterator with the root node.
		 * 
		 * @param node
		 *            the root node of the backed tree
		 */
		public BinarySearchTreeIterator(final BinaryTreeNode<T> root) {
			super();
			BinaryTreeNode<T> node = root;
			while (node != null) {
				unvisitedNodes.push(node);
				node = node.left;
			}
		}

		@Override
		public boolean hasNext() {
			return !unvisitedNodes.isEmpty();
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException(
						"No further elemets are available");
			}
			final BinaryTreeNode<T> current = unvisitedNodes.pop();
			BinaryTreeNode<T> node = current.right;
			while (node != null) {
				unvisitedNodes.push(node);
				node = node.left;
			}
			return current.element;
		}

	}

	/**
	 * The level count of the backed tree
	 */
	private int levels = 0;

	/**
	 * Initialized with natural ordering which requires Comparable<T> interface
	 * implemented by the managed elements
	 */
	public BinarySearchTreeSet() {
		super();
	}

	/**
	 * Initializes this Instance with a custom Comparator<T> instance
	 * 
	 * @param comparator
	 *            the Comparator<T> instance to be used
	 */
	public BinarySearchTreeSet(Comparator<T> comparator) {
		super(comparator);
	}

	@Override
	public T get(T el) {
		BinaryTreeNode<T> node = root;
		while (node != null) {
			int res = compareElements(node.element, el);
			if (res == 0) {
				return node.element;
			}
			if (res > 0) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return null;
	}

	@Override
	public boolean add(T el) {
		boolean modified = Boolean.FALSE;
		int currentLevels = 0;
		/*-- Null not allowed --*/
		if (el != null) {
			// root already present
			if (root != null) {
				BinaryTreeNode<T> previous = null;
				BinaryTreeNode<T> node = root;
				while (node != null) {
					int compareResult = compareElements(el, node.element);
					// No duplicates allowed
					if (compareResult == 0) {
						previous = null;
						break;
					}
					previous = node;
					currentLevels++;
					// go to left
					if (compareResult < 0) {
						node = node.left;
					}
					// go to right
					else {
						node = node.right;
					}
				}
				// add new child if previous found
				if (previous != null) {
					currentLevels++;
					modified = Boolean.TRUE;
					size++;
					final BinaryTreeNode<T> newNode = new BinaryTreeNode<T>(el,
							null, null);
					if (compareElements(el, previous.element) < 0) {
						previous.left = newNode;
					} else {
						previous.right = newNode;
					}
				}
			}
			// add root element
			else {
				root = new BinaryTreeNode<T>(el, null, null);
				modified = Boolean.TRUE;
				currentLevels = 1;
				size++;
			}
			// set if new level reached
			if (levels < currentLevels) {
				levels = currentLevels;
			}
		}
		return modified;
	}

	@Override
	public T first() {
		if (root == null) {
			throw new NoSuchElementException(
					"Tree is empty therefore no first element available");
		}
		BinaryTreeNode<T> node = root;
		while (node.left != null) {
			node = node.left;
		}
		return node.element;
	}

	@Override
	public T last() {
		if (root == null) {
			throw new NoSuchElementException(
					"Tree is empty therefore no last element available");
		}
		BinaryTreeNode<T> node = root;
		while (node.right != null) {
			node = node.right;
		}
		return node.element;
	}

	@Override
	public Iterator<T> iterator() {
		return new BinarySearchTreeIterator<T>(root);
	}

	@Override
	public int height() {
		return levels;
	}

}
