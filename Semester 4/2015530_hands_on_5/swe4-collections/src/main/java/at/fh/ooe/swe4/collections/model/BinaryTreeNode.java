package at.fh.ooe.swe4.collections.model;

import at.fh.ooe.swe4.collections.api.TreeNode;

/**
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 17, 2015
 * @param <T>
 */
public class BinaryTreeNode<T> implements TreeNode {

	public T element;
	public BinaryTreeNode<T> left;
	public BinaryTreeNode<T> right;

	public BinaryTreeNode(T element, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
		super();
		this.element = element;
		this.left = left;
		this.right = right;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BinaryTreeNode<T> other = (BinaryTreeNode<T>) obj;
		if (element == null) {
			if (other.element != null) {
				return false;
			}
		} else if (!element.equals(other.element))
			return false;
		return true;
	}

}
