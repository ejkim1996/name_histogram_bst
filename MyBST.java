package proj4;
/**
 * Generic MyBST class that uses natural ordering to create
 * a binary search tree.
 * @author EJ Kim
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class MyBST<E extends Comparable> {
	//declare variables
	protected Node<E> root;
	private ArrayList<E> arrayListForBST;
	private boolean found;
	
	/**
	 * Default constructor. Constructs a new, empty tree,
	 * sorted according to the natural ordering of its elements.
	 */
	public MyBST() {
		root = null;
	}
	
	/**
	 * Wrapper method for the add method. Adds specified element to the set
	 * if it is not already in the set. If the element is already in the set,
	 * the set is unchanged and returns false.
	 * @param e the element to be added
	 * @return true if element was added, false if it wasn't.
	 */
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException("Can't add a null element to the BST");
		}
		//if set is empty, we set the element as the root of the set
		if (isEmpty()) {
			root = new Node<>(e);
			return true;
		}
		//if root is not null, we call the private add method.
		return add(e, root);
	}
	
	/**
	 * Private add method. Using the binary search mechanism it traverses
	 * the set and adds the element where it should be if it is not already
	 * in the set.
	 * @param e element to be added
	 * @param node the node that holds data to compare to with the element
	 * @return true if element was added, false if it wasn't
	 */
	private boolean add(E e, Node<E> node) {
		//if element data is smaller than node data,
		//and its left child is null, the left child
		//becomes a node with the element data. if the left
		//child isn't null we compare the data with the
		//left child's data.
		if (e.compareTo(node.getData()) < 0) {
			if (node.getLeft() == null) {
				node.setLeft(new Node<>(e));
				return true;
			}
			else {
				return add(e, node.getLeft());
			}
		}
		//if element data is bigger than node data,
		//and its right child is null, the right child
		//becomes a node with the element data. if the right
		//child isn't null we compare the data with the
		//right child's data.
		else if (e.compareTo(node.getData()) > 0) {
			if (node.getRight() == null) {
				node.setRight(new Node<>(e));
				return true;
			}
			else {
				return add(e, node.getRight());
			}
		}
		//this occurs when a node has data that is equal to the element.
		return false;
	}
	
	/**
	 * Wrapper method for contains method. Checks if specified
	 * element is in the set.
	 * @param o Object to check for
	 * @return true if object is within the set, false if it isn't.
	 */
	public boolean contains(Object o) {
		if (o == null) {
			throw new NullPointerException("Can't check for null elements.");
		}
		E data = (E)o;
		return contains(data, root);
	}
	
	/**
	 * Private contains method. Using the binary search algorithm
	 * the set is traversed to find a match within it.
	 * @param data
	 * @param node
	 * @return
	 */
	private boolean contains(E data, Node<E> node) {
		//if root is null or if we reach a leaf and it is not a match
		//the element is not in the set
		if (node == null) {
			return false;
		}
		else if (data.compareTo(node.getData()) < 0) {
			return contains(data, node.getLeft());
		}
		else if (data.compareTo(node.getData()) > 0) {
			return contains(data, node.getRight());
		}
		//element is in the set if compareTo returns 0
		else {
			return true;
		}
	}
	
	/**
	 * Wrapper method for first. Returns the first/lowest
	 * element in the set
	 * @return the lowest element in the set
	 */
	public E first() {
		return first(root);
	}
	
	/**
	 * Private first method. Traverses down the left children
	 * until a node doesn't have a left child.
	 * @param node the node to check left child.
	 * @return the lowest element in the set
	 */
	private E first(Node<E> node) {
		if (node.getLeft() == null) {
			return node.getData();
		}
		return first(node.getLeft());
	}
	
	/**
	 * check whether the set is empty or not
	 * @return true is set is empty, false if it isn't.
	 */
	boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * returns an iterator over the elements in the set
	 * in ascending order
	 * @return an iterator over the elements in the set
	 * in ascending order
	 */
	Iterator<E> iterator() {
		populateArrayListFromBST(root);
		return new MyBSTIterator();
	}
	
	/**
	 * Wrapper method for last. Returns the last/highest
	 * element in the set
	 * @return the highest element in the set
	 */
	public E last() {
		return last(root);
	}
	
	/**
	 * Private last method. Traverses down the right children
	 * until a node doesn't have a right child.
	 * @param node the node to check right child.
	 * @return the highest element in the set
	 */
	private E last(Node<E> node) {
		if (node.getRight() == null) {
			return node.getData();
		}
		return last(node.getRight());
	}
	
	/**
	 * removes specified element from the set if it is present.
	 * @param o Object to be removed
	 * @return true if the set had the specified element, false if it doesn't
	 */
	public boolean remove(Object o) {
		if (o == null) {
			throw new NullPointerException("Set does not have null elements");
		}
		E data = (E)o;
		remove(data, root);
		return found;
	}
	
	/**
	 * Private remove method. Finds the node to be removed
	 * and removes it.
	 * @param data data to check against nodes
	 * @param node the node to check against data
	 * @return the node that needs to be connected to the parent node
	 * of the removed node 
	 */
	private Node<E> remove(E data, Node<E> node) {
		if (node == null) {
			found = false;
		}
		else if (data.compareTo(node.getData()) < 0) {
			node.setLeft(remove(data, node.getLeft()));
		}
		else if (data.compareTo(node.getData()) > 0) {
			node.setRight(remove(data, node.getRight()));
		}
		else {
			node = remove(node);
			found = true;
		}
		return node;
	}
	
	/**
	 * Private remove method that removes the node based
	 * on whether it has no children, 1 child or 2 children.
	 * @param node that will be removed
	 * @return the node that needs to be connected to the parent node
	 * of the removed node
	 */
	private Node<E> remove(Node<E> node) {
		E data;
		if (node.getLeft() == null) {
			return node.getRight();
		}
		if (node.getRight() == null) {
			return node.getLeft();
		}
		else {
			data = getPredecessor(node.getLeft());
			node.setData(data);
			node.setLeft(remove(data, node.getLeft()));
			return node;
		}
		
	}
	
	/**
	 * gets the predecessor of the specified node and returns
	 * its data. This version finds the rightmost node of the left
	 * subtree.
	 * @param node the node to find the predecessor of
	 * @return the data of the predecessor node.
	 */
	private E getPredecessor(Node<E> node) {
		if (node.getLeft() == null) {
			//throw exception
			return node.getData();
		}
		else {
			Node<E> current = node.getLeft();
			while (current.getRight() != null) {
				current = current.getRight();
			}
			return current.getData();
		}
	}
	
	/**
	 * populate the arrayListForBST ArrayList
	 * with items in the set in order. Used by
	 * MyBSTIterator
	 * @param node the node to traverse and check
	 */
	private void populateArrayListFromBST(Node<E> node) {
		if (node == null) {
			return;
		}
		else {
			populateArrayListFromBST(node.getLeft());
			arrayListForBST.add(node.getData());
			populateArrayListFromBST(node.getRight());
		}
	}
	
	/**
	 * Nested private class that implements the Iterator<E> interface
	 * @author EJ Kim
	 *
	 */
	private class MyBSTIterator implements Iterator<E> {
		int cursor;       // index of next element to return
	    int lastRet = -1; // index of last element returned; -1 if no such element

	    /**
	     * Check whether there is a next element in the iteration
	     * @return true if there is a next element, false if otherwise
	     */
	    public boolean hasNext() {
	        return cursor != arrayListForBST.size();
	    }

	    /**
	     * Returns the next element in the iteration
	     * @return the next element in the iteration
	     */
	    public E next() {
	        int i = cursor;
	        if (i >= arrayListForBST.size())
	            throw new NoSuchElementException();
	        cursor = i + 1;
	        lastRet = i;
	        return arrayListForBST.get(lastRet);
	    }

	    /**
	     * Unsupported for MyBSTIterator.
	     */
	    public void remove() {
	        throw new UnsupportedOperationException("remove() is not implemented.");
	    }

	    /**
	     * Given action is performed for each remaining element until all elements have been
	     * processed or there is an exception
	     * @param consumer the action to be performed
	     */
	    @Override
	    public void forEachRemaining(Consumer<? super E> consumer) {	    	
	    	Objects.requireNonNull(consumer);
	        final int size = arrayListForBST.size();
	        int i = cursor;
	        if (i >= size) {
	            return;
	        }
	        while (i != size) {
	            consumer.accept(arrayListForBST.get(i++));
	        }
	        cursor = i;
	        lastRet = i - 1;
	    }
	}
}



