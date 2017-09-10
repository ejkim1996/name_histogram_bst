package proj4;
/**
 * Generic Node class to be used as building blocks of a binary
 * search tree.
 * @author EJ Kim
 */

public class Node<E extends Comparable> implements Comparable<Node<E>>{
	//declare variables
	private E data;
	private Node<E> left;
	private Node<E> right;
	
	//getters and setters
	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public Node<E> getLeft() {
		return left;
	}

	public void setLeft(Node<E> left) {
		this.left = left;
	}

	public Node<E> getRight() {
		return right;
	}

	public void setRight(Node<E> right) {
		this.right = right;
	}

	/**
	 * Default constructor.
	 */
	public Node() {	}
	
	/**
	 * Constructor that takes in a data parameter and sets
	 * this.data to data
	 * @param data the data to set this.data to.
	 */
	public Node(E data) {
		this.data = data;
	}
	
	/**
	 * Constructor that takes in a data, left, and right parameters
	 * and sets them to their counterparts.
	 * @param data data to be set as the Node's data
	 * @param left the node to be set as the Node's left node.
	 * @param right the node to be set as the Node's right node.
	 */
	public Node(E data, Node<E> left, Node<E> right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

	/**
	 * Overrides the compareTo method.
	 * @param o Node that has the data to compare to with calling node's data
	 */
	@Override
	public int compareTo(Node<E> o) {
		return this.data.compareTo(o.getData());
	}
	
	/**
	 * Overrides the toString method to give users info on
	 * data, left node and right node.
	 */
	@Override
	public String toString() {
		return "This node has data: " + data +
				" left node: " + left + 
				" right node: " + right;
	}
}
