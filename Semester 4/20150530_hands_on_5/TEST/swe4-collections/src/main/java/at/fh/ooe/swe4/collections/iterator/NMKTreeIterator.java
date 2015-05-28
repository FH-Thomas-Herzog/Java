package at.fh.ooe.swe4.collections.iterator;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Stack;

import at.fh.ooe.swe4.collections.model.NMKTreeNode;

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
	 *            the type of the managed nodes
	 */
	private static class IterateModel<T> {

		public final NMKTreeNode<T> current;
		public final IterateModel<T> parent;
		public final Stack<NMKTreeNode<T>> children = new Stack<>();
		public final Iterator<T> keyIt;

		/**
		 * Creates a iterate model instance
		 * 
		 * @param parent
		 *            the parent of this model
		 * @param current
		 *            the current node which is backed by this model
		 */
		public IterateModel(final IterateModel<T> parent,
				final NMKTreeNode<T> current) {
			super();
			Objects.requireNonNull(current, "Current node must be given");

			this.parent = parent;
			this.current = current;
			this.keyIt = current.keyIterator();
			// set all children on stack in proper order
			final Iterator<NMKTreeNode<T>> it = current.childrenIterator();
			while (it.hasNext()) {
				children.add(it.next());
			}
			Collections.reverse(children);
		}
	}

	/**
	 * Creates an iterator instance and moves to first to visit node.
	 * 
	 * @param root
	 *            the root of the tree
	 */
	public NMKTreeIterator(final NMKTreeNode<T> root) {
		super();
		pushUntilLowest(root, null);
		if (hasNext()) {
			currentModel = unvisitedNodes.pop();
		}
	}

	@Override
	public boolean hasNext() {
		return (!unvisitedNodes.isEmpty())
				|| (currentModel != null && (currentModel.keyIt.hasNext() || (!currentModel.children
						.isEmpty())));
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
		if (currentModel.children.isEmpty()) {
			// keys still left
			if (currentModel.keyIt.hasNext()) {
				value = currentModel.keyIt.next();
			}
			// no children and no keys means next node ins tack
			else {
				// Remove this node from parent, because was completely visited
				// before
				currentModel.parent.children.remove(currentModel.current);

				// get next node
				currentModel = unvisitedNodes.pop();

				// get current value from new node
				value = currentModel.keyIt.next();

				// if there are children
				if (!currentModel.children.isEmpty()) {
					// need to revisit if keys still left
					if (currentModel.keyIt.hasNext()) {
						unvisitedNodes.push(currentModel);
					}
					// inform parent that this node does not need to be visited
					// again if no further keys are available
					else if (currentModel.parent != null) {
						currentModel.parent.children
								.remove(currentModel.current);
					}

					// go to lowest node
					pushUntilLowest(currentModel.children.pop(), currentModel);

					// get lowest node from stack
					currentModel = unvisitedNodes.pop();
				}
			}
		}
		/*-- get on further children --*/
		else {
			// we need to revisit this node if it has children set
			if (!currentModel.children.isEmpty()) {
				unvisitedNodes.push(currentModel);
			} else if (currentModel.parent != null) {
				currentModel.parent.children.remove(currentModel.current);
			}
			// get lowest node of next child to visit
			pushUntilLowest(currentModel.children.pop(), currentModel);
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
	private void pushUntilLowest(final NMKTreeNode<T> root,
			final IterateModel<T> parent) {
		NMKTreeNode<T> node = root;
		IterateModel<T> prev, model;
		prev = model = null;
		while (node != null) {
			// if there is a parent get its children
			if (node.getParent() != null) {
				// set provided parent. For first visited node
				prev = (prev == null) ? parent : prev;
			}
			// create current model
			model = new IterateModel<T>(prev, node);
			// remember parent model of next visited node
			prev = model;
			unvisitedNodes.push(model);
			// walk left
			node = node.lowestChild();
		}
	}
}
