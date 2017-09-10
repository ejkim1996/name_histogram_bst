package proj4;
/**
 * This class stores the data the year and
 * all the Name objects for the given year.
 * @author EJ Kim
 */

public class YearNames extends MyBST<Name> {
	private int year;
	
	public YearNames(int year) {
		this.year = year;
	}
	
	public int getYear() {
		return year;
	}

	/**
	 * Add a Name object to the MyArrayList if it
	 * isn't already in it.
	 * @param n Name object to add into the allNames MyArrayList
	 * @throws IllegalArgumentException if allNames MyArrayList already contains the Name object to add.
	 */
	public boolean add(Name n) {
		if (contains(n)) {
			throw new IllegalArgumentException("Name object " + n.getName() + "(" + n.getGender() + ") already exists in list for " + year);
		}
		else {
			return super.add(n);
		}
	}
	
	/**
	 * Goes through the set to see if there is a Name
	 * object with the name and gender parameters,
	 * and returns its count.
	 * @param name the name String to find the count value
	 * in a given year.
	 * @return the count value of a name in a given year.
	 */
	public int getCountByName(String name, String gender) {
		return traverseNameCount(root, new Name(name, gender, 0));
	}
	
	/**
	 * Finds the fraction of a given name in a given year.
	 * @param name the name String to find the fraction value
	 * of a given year.
	 * @return a double value which is the fraction of a name's
	 * count over the total count of all Name objects in a given
	 * year.
	 */
	public double getFractionByName(String name, String gender) {
		double totalCount = traverseTotalCount(root);
		//use getCountByName() method to get the count for the name.
		double nameCount = getCountByName(name, gender);
		double fraction = nameCount/totalCount;
		
		//go through the MyArrayList and add up all the count variables.
		return fraction;
	}
	
	/**
	 * Private method that returns the count of the Name object
	 * with the same name and gender Strings by using a binary search
	 * algorithm.
	 * @param node node with Name object to compare with specified name
	 * @param name specified Name object to find count variable for.
	 * @return the count variable for the Name object if it is in the set.
	 */
	private int traverseNameCount(Node<Name> node, Name name) {
		if (node == null) {
			return 0;
		}
		else if (name.compareToIgnoreCount(node.getData()) < 0) {
			return traverseNameCount(node.getLeft(), name);
		}
		else if (name.compareToIgnoreCount(node.getData()) > 0) {
			return traverseNameCount(node.getRight(), name);
		}
		else {
			return node.getData().getCount();
		}
	}
	
	/**
	 * Gets the total count by adding all the Node's count values
	 * as the set is traversed in order
	 * @param node the node to add count value from.
	 * @return the total count value of all Name objects in the tree.
	 */
	private int traverseTotalCount(Node<Name> node) {
		if (node != null) {
			return traverseTotalCount(node.getLeft()) +
					node.getData().getCount() +
					traverseTotalCount(node.getRight());
		}
		else {
			return 0;
		}
	}
	
	/**
	 * Overrides the toString method to give the total count of
	 * babies in a given year.
	 * @return a String detailing the number of babies in a given year.
	 */
	@Override
	public String toString() {
		int numOfBabies = 0;
		numOfBabies = traverseTotalCount(root);
		return "In " + year + ", there were " + numOfBabies + " babies.";
	}
	
	/**
	 * Search for a Name object in a given year's set
	 * @param n the Name object to look for.
	 * @return true if Name object is in the set of given year.
	 * false if it is not.
	 */
	public boolean contains(Name n) {
		if (n == null) {
			throw new NullPointerException("Can't check for null elements.");
		}
		return contains(n, root);
	}
	
	/**
	 * Private contains method for YearNames. Uses a binary search algorithm
	 * and compareToIgnoreCount to check if there is already a Name object
	 * with the same name and gender within the set.
	 * @param n Name object to check existence within tree
	 * @param node Node with Name object in the tree
	 * @return true if Name object is in the set of given year, false
	 * if it is not.
	 */
	private boolean contains(Name n, Node<Name> node) {
		if (node == null) {
			return false;
		}
		else if (n.compareToIgnoreCount(node.getData()) < 0) {
			return contains(n, node.getLeft());
		}
		else if (n.compareToIgnoreCount(node.getData()) > 0) {
			return contains(n, node.getRight());
		}
		else {
			return true;
		}
	}
}
