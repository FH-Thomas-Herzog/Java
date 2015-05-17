package at.fh.ooe.swe4.collections.impl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import at.fh.ooe.swe4.collections.api.SortedSet;
import at.fh.ooe.swe4.collections.model.TreeNode;

/**
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 16, 2015
 * @param <T>
 */
public class TwoThreeFourTreeSet<T extends Comparable<T>> implements SortedSet<T> {

	private TreeNode<T> root = null;
	private int size = 0;

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(T o) {
		if (root == null) {
			return Boolean.FALSE;
		}
		if (o == null) {
			return findNearest(root, (T) null).elements.first() == null;
		}
		return findNearest(root, (T) null).elements.first()
													.equals(o);
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
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
		for (T current : node.elements) {
			if (current.compareTo(el) == 0) {
				return current;
			}
		}
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
		while (!node.children.isEmpty()) {
			node = node.children.first();
		}
		return node.elements.first();
	}

	@Override
	public T last() {
		if (size() == 0) {
			throw new NoSuchElementException("Tree is empty");
		}
		TreeNode<T> node = root;
		while (!node.children.isEmpty()) {
			node = node.children.last();
		}
		return node.elements.last();
	}

	// Private section
	private TreeNode<T> findNearest(final TreeNode<T> parent, final T element) {
		// Node not found
		if (parent == null) {
			throw new IllegalStateException("This method should return at least the nearest element");
		}
		// current holds searched element
		if (parent.elements.first()
							.compareTo(element) == 0) {
			return parent;
		}
		// else further search needed
		TreeNode<T> previous = null;
		for (TreeNode<T> node : parent.children) {
			// previous holds next lower element
			if (node.elements.first()
								.compareTo(element) > 0) {
				break;
			}
			previous = node;
		}

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
}
