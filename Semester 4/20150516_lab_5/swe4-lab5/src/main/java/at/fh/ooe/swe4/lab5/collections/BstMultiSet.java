package at.fh.ooe.swe4.lab5.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 16, 2015
 * @param <T>
 */
public class BstMultiSet<T extends Comparable<T>> implements SortedMultiSet<T> {

	private static class Node<T extends Comparable<T>> {
		public final T el;
		public Node<T> left, right;

		public Node(T el, Node<T> left, Node<T> right) {
			super();
			this.el = el;
			this.left = left;
			this.right = right;
		}
	}

	private static class BstSetIterator<T extends Comparable<T>> implements Iterator<T> {

		private final Stack<Node<T>> unvisitedNodes = new Stack<>();

		public BstSetIterator(final Node<T> root) {
			super();
			Node<T> node = root;
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
				throw new NoSuchElementException("No further elements are available");
			}
			Node<T> current = unvisitedNodes.pop();
			Node<T> node = current.right;
			while (node != null) {
				unvisitedNodes.push(node);
				node = node.right;
			}
			return current.el;
		}

	}

	private Node<T> root = null;
	private int size = 0;

	public BstMultiSet() {
		super();
	}

	@Override
	public Iterator<T> iterator() {
		return new BstSetIterator<T>(root);
	}

	@Override
	public void add(T el) {
		root = addRecursive(root, el);
	}

	@Override
	public boolean contains(T el) {
		return get(el) != null;
	}

	@Override
	public T get(T el) {
		Node<T> node = root;
		while (node != null) {
			int res = node.el.compareTo(el);
			if (res == 0) {
				return node.el;
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
	public int size() {
		return size;
	}

	private Node<T> addRecursive(final Node<T> parent, final T el) {
		if (parent == null) {
			size++;
			return new Node<T>(el, null, null);
		} else if (el.compareTo(parent.el) < 0) {
			parent.left = addRecursive(parent.left, el);
		} else {
			parent.right = addRecursive(parent.right, el);
		}
		return parent;
	}

	private void traverseInOrder(final Node<T> node, final StringBuilder sb) {
		if (node != null) {
			traverseInOrder(node.left, sb);
			if (sb.length() > 1) {
				sb.append(",");
			}
			sb.append(node.el);
			traverseInOrder(node.right, sb);
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		traverseInOrder(root, sb);
		sb.append("]");
		return sb.toString();
	}
}
