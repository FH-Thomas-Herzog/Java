package iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Stack;
import java.util.TreeSet;

import at.fh.ooe.swe4.collections.model.NMKTreeTreeNode;

/**
 * The iterator for the n-m-k Tree.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 22, 2015
 * @param <T>
 *            the {@link Comparable} type managed by the backed tree
 */
public class NMKTreeIterator<T> implements Iterator<T> {

	private IterateModel<T> currentModel = null;
	private final Stack<IterateModel<T>> unvisitedNodes = new Stack<>();

	/**
	 * The helper model for iterating over an n-m-k tree.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 24, 2015
	 * @param <T>
	 *            the {@link Comparable} type of the managed keys by the given
	 *            root node
	 */
	private static class IterateModel<T> {
		public final IterateModel<T> parent;
		public final Iterator<NMKTreeTreeNode<T>> childrenIt;
		public final Iterator<T> keyIt;

		/**
		 * Creates this model.<br>
		 * The iterators should be unique iterators and not referenced ones.
		 * 
		 * @param parent
		 *            the wrapped parent
		 * @param children
		 *            the native node children iterator
		 * @param keyIt
		 *            the native node key iterator
		 */
		public IterateModel(final IterateModel<T> parent,
				Iterator<NMKTreeTreeNode<T>> children, Iterator<T> keyIt) {
			super();
			Objects.requireNonNull(children, "Children iterator must be given");
			Objects.requireNonNull(keyIt, "Key iterator must be given");

			this.parent = parent;
			this.childrenIt = children;
			this.keyIt = keyIt;
		}
	}

	/**
	 * Creates an iterator instance and moves to first to visit node.
	 * 
	 * @param root
	 *            the root of the tree
	 */
	public NMKTreeIterator(final NMKTreeTreeNode<T> root) {
		super();
		pushUntilLowest(root, null);
		if (hasNext()) {
			currentModel = unvisitedNodes.pop();
		}
	}

	@Override
	public boolean hasNext() {
		return (!unvisitedNodes.isEmpty())
				|| (currentModel != null && (currentModel.keyIt.hasNext() || (currentModel.childrenIt
						.hasNext()))); // (currentModel.siblingIt.hasNext());
	}

	@Override
	public T next() {
		T value = null;
		/*-- no more nodes available --*/
		if (!hasNext()) {
			throw new NoSuchElementException(
					"No further elements are available");
		}
		/*-- current model has no more children --*/
		if (!currentModel.childrenIt.hasNext()) {
			// 1. keys still left
			if (currentModel.keyIt.hasNext()) {
				value = currentModel.keyIt.next();
			}
			// 2. notify parent if this is child
			// notify parent that current node was visited
			// there was a check for null parent, could be included in case of
			// error
			else {
				// marker for new parent node to visit
				boolean newParent = Boolean.FALSE;
				if (currentModel.parent.childrenIt.hasNext()) {
					currentModel.parent.childrenIt.next();
				} else {
					newParent = Boolean.TRUE;
				}
				// get next node
				currentModel = unvisitedNodes.pop();
				// if new parent we know that a child has been visited before
				if (newParent) {
					currentModel.childrenIt.next();
				}
				// get current value from new parent
				value = currentModel.keyIt.next();
				// if there are children search for their lowest node
				if (currentModel.childrenIt.hasNext()) {
					pushUntilLowest(currentModel.childrenIt.next(),
							currentModel);
					currentModel = unvisitedNodes.pop();
				}
			}
		}
		/*-- should now be new parent node --*/
		else {
			// we need to revisit this node if it has children set
			if (currentModel.childrenIt.hasNext()) {
				unvisitedNodes.push(currentModel);
			}
			// get lowest node of next child to visit
			pushUntilLowest(currentModel.childrenIt.next(), currentModel);
			// get lowest node
			currentModel = unvisitedNodes.pop();
		}
		// next key to provide
		return value;
	}

	// #####################################
	// Private section
	// #####################################
	/**
	 * Pushes the nodes to the unvisited {@link Stack} including the next
	 * neighbor of the lowest element if present.
	 * 
	 * @param root
	 *            the node to walk all way left to the lowest node
	 */
	private void pushUntilLowest(final NMKTreeTreeNode<T> root,
			final IterateModel<T> parent) {
		NMKTreeTreeNode<T> node = root;
		IterateModel<T> prev, model;
		prev = model = null;
		while (node != null) {
			final Iterator<T> keyIt = node.keyIterator();
			final Iterator<NMKTreeTreeNode<T>> childrenIt;
			// if there is a parent get its children
			if (node.getParent() != null) {
				childrenIt = node.getParent().childrenIterator();
				// = current no need to revisit
				childrenIt.next();
				// set provided parent. For first visited node
				prev = (prev == null) ? parent : prev;
			}
			// set empty iterator if no parent
			else {
				childrenIt = new TreeSet<NMKTreeTreeNode<T>>().iterator();
			}
			model = new IterateModel<T>(prev, node.childrenIterator(), keyIt);
			prev = model;
			unvisitedNodes.push(model);
			// walk left
			node = node.lowestChild();
		}
	}
}
