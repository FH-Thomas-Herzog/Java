package at.fh.ooe.swe4.collections.impl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Stack;
import java.util.TreeSet;

import at.fh.ooe.swe4.collections.api.SortedSet;
import at.fh.ooe.swe4.collections.api.SortedTreeSet;
import at.fh.ooe.swe4.collections.model.TreeNode;

/**
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 16, 2015
 * @param <T>
 */
public class TwoThreeFourTreeSet<T extends Comparable<T>> implements SortedTreeSet<T> {

	/**
	 * The iterator for the 2-3-4 tree.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 22, 2015
	 * @param <T>
	 *            the {@link Comparable} type managed by the backed tree
	 */
	public static class TwoThreeFourTreeSetIterator<T extends Comparable<T>> implements Iterator<T> {

		private Iterator<T> keyIterator = new TreeSet<T>().iterator();
		private final Stack<TreeNode<T>> unvisitedNodes = new Stack<>();

		/**
		 * Creates an constructor for the tree represented by the provided root
		 * node.
		 * 
		 * @param root
		 *            the root of the tree
		 */
		public TwoThreeFourTreeSetIterator(final TreeNode<T> root) {
			super();
			TreeNode<T> node = root;
			TreeNode<T> prev = null;
			while (node != null) {
				unvisitedNodes.push(node);
				prev = node;
				node = node.lowestChild();
				if (node == null) {
					this.keyIterator = prev.keyIterator();
				}
			}
		}

		@Override
		public boolean hasNext() {
			return (!unvisitedNodes.isEmpty()) || (keyIterator.hasNext());
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No further elements are available");
			}
			// elements are still left
			if (keyIterator.hasNext()) {
				return keyIterator.next();
			}
			// visit next node
			else {
				final TreeNode<T> current = unvisitedNodes.pop();
				TreeNode<T> node = current.lowestChild();
				while (node != null) {
					unvisitedNodes.push(node);
					node = node.lowestChild();
				}
				// get current key iterator
				this.keyIterator = current.keyIterator();
				// next key to provide
				return keyIterator.next();
			}
		}
	}

	private TreeNode<T> root = null;
	private int size = 0;
	private int height = 0;

	@Override
	public boolean contains(T o) {
		if (root == null) {
			return Boolean.FALSE;
		}
		// if (o == null) {
		// return findNearest(root, (T) null).keys.first() == null;
		// }
		// return findNearest(root, (T) null).keys.first()
		// .equals(o);
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return new TwoThreeFourTreeSetIterator<T>(root);
	}

	@Override
	public boolean add(T e) {
		return false;
	}

	@Override
	public T get(T el) {
		if (root == null) {
			return null;
		}
		final TreeNode<T> node = findNearest(root, el);

		return null;
	}

	@Override
	public Comparator<T> comparator() {
		return null;
	}

	@Override
	public T first() {
		if (size() == 0) {
			throw new NoSuchElementException("Tree is empty");
		}
		TreeNode<T> node = root;
		while (node.lowestChild() != null) {
			node = node.lowestChild();
		}
		return node.lowestKey();
	}

	@Override
	public T last() {
		if (size() == 0) {
			throw new NoSuchElementException("Tree is empty");
		}
		TreeNode<T> node = root;
		while (node.highestChild() != null) {
			node = node.highestChild();
		}
		return node.highestKey();
	}

	@Override
	public int height() {
		return height;
	}

	// Private section
	private TreeNode<T> findNearest(final TreeNode<T> parent, final T element) {
		// Node not found
		if (parent == null) {
			throw new IllegalStateException("This method should return at least the nearest element");
		}
		// current holds searched element
		if (parent.lowestKey()
					.compareTo(element) == 0) {
			return parent;
		}
		// else further search needed
		TreeNode<T> previous = null;
		// for (TreeNode<T> node : parent.children) {
		// // previous holds next lower element
		// if (node.keys.first()
		// .compareTo(element) > 0) {
		// break;
		// }
		// previous = node;
		// }

		// nodes are left
		if (previous != null) {
			// need further search
			return findNearest(previous, element);
		}
		return parent;
	}

	//
	// public Map<Boundary, SortedSet<TreeNode<T>>> splitElements() {
	// if (childrenSize != 4) {
	// throw new
	// IllegalStateException("There must be exactly 4 elements managed by this node");
	// }
	// final Map<Boundary, SortedSet<T>> map = new HashMap<Boundary,
	// SortedSet<T>>();
	// map.put(Boundary.LOWER, new TreeSet<T>());
	// map.put(Boundary.UPPER, new TreeSet<T>());
	// elements.stream()
	// .forEachOrdered(new Consumer<T>() {
	// @Override
	// public void accept(final T t) {
	// final Boundary boundary;
	// if (map.get(Boundary.LOWER)
	// .size() < 2) {
	// boundary = Boundary.LOWER;
	// } else {
	// boundary = Boundary.UPPER;
	// }
	// map.get(boundary)
	// .add(t);
	// }
	// });
	// return map;
	// }

	@Override
	public int size() {
		return size;
	}

	@Override
	public T[] toArray(T[] array) {
		Objects.requireNonNull(array, "Cannot convert to array when null is given");

		final Iterator<T> it = iterator();
		int idx = 0;
		while ((it.hasNext()) && (idx < array.length)) {
			array[idx] = it.next();
			idx++;
		}
		return array;
	}

}
