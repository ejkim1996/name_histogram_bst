package proj4;
/**
 * This class will be used to create Name objects with
 * name, gender, and count data. It implements the Comparable<>
 * interface so the Name objects can be sorted.
 * @author EJ Kim
 *
 */
public class Name implements Comparable<Name>{
	private String name;
	private String gender;
	private int count;
	
	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public int getCount() {
		return count;
	}

	/**
	 * constructor that creates Name object with specified name, gender, and count.
	 * @param name name of the Name object to be created.
	 * @param gender gender of the Name object to be created.
	 * @param count count of the Name object to be created.
	 * @throws IllegalArgumentException if name, gender, or count parameters are invalid.
	 */
	public Name(String name, String gender, int count) {
		//throw an IllegalArgumentException if the name parameter is an empty string.
		if (name.equals("")) {
			throw new IllegalArgumentException("Cannot have empty string for a name.");
		}
		else {
			this.name = name; //set the name variable to the input if it is not an empty string.
		}
		
		//if the gender parameter is "f" or "m", ignoring case, set the gender variable to the input.
		if (gender.equalsIgnoreCase("f") || gender.equalsIgnoreCase("m")) {
			this.gender = gender;
		}
		else {
			//throw an IllegalArgumentException if the parameter isn't "f" or "m", ignoring case.
			throw new IllegalArgumentException("Invalid gender of " + gender+ " with name " + name + " and count " + count);
		}
		
		//throw an IllegalArgumentException if the count parameter is negative.
		if (count < 0) {
			throw new IllegalArgumentException("Negative count of " + count + " for name " + name + " and gender " + gender);
		}
		else {
			this.count = count; //set the count variable to the input if it is not negative.
		}
	}

	/**
	 * Overrides the compareTo() method in the Comparable<> interface
	 * Uses the Name objects' name as the primary key, count as the secondary,
	 * and gender as the tertiary key
	 * @param n the Name object to be compared
	 * @return an int. negative, 0, or positive when the Name object on which the
	 * method is called is smaller, equal, or bigger than the compared Name object
	 */
	@Override
	public int compareTo(Name n) {
		//if the names of the Name objects are NOT equal, compare them by name.
		if (!name.equalsIgnoreCase(n.name)) {
			return name.compareToIgnoreCase(n.name);
		}
		
		//if the names of the Name objects are equal and the counts are NOT equal, compare them by count.
		else if (!gender.equalsIgnoreCase(n.gender)) {
			return gender.compareToIgnoreCase(n.gender);
		}
		else {
			return count - n.count;
		}
		
		//if the names and counts of the Name objects are equal, compare them by gender.
	}
	
	/**
	 * A compareTo method that ignores count of Name objects.
	 * Name objects' name is the primary key, gender is secondary, and count is
	 * ignored.
	 * @param n Name object to be compared.
	 * @return an int. negative, 0, or positive when the Name object on which the
	 * method is called is smaller, equal, or bigger than the compared Name object
	 */
	public int compareToIgnoreCount(Name n) {
		if (!name.equalsIgnoreCase(n.name)) {
			return name.compareToIgnoreCase(n.name);
		}
		else {
			return gender.compareToIgnoreCase(n.gender);
		}
	}
	
	
	/**
	 * Overrides the toString() method that checks the gender
	 * of the Name object and return a String with appropriate
	 * name and count info.
	 * @return a String that details the name, count, and gender
	 * of the Name object.
	 */
	@Override
	public String toString() {
		if (gender.equalsIgnoreCase("f")) {
			return "There were " + this.count + " female babies with the name " + this.name + ".";
		}
		else {
			return "There were " + this.count + " male babies with the name " + this.name + ".";
		}
	}

	/**
	 * Overrides the equals method.
	 * @param obj the object to compare with the Name object this
	 * method is called upon.
	 * @return true if name, gender, and count of two Name objects
	 * are identical. false if otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Name other = (Name) obj;
		if (count != other.count)
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equalsIgnoreCase(other.gender))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}

	/**
	 * Identical to the equals method above but
	 * ignores count.
	 * @param obj the object to compare with the Name object this
	 * method is called upon
	 * @return true when an Object on which the method is called on has the
	 * same name and gender. false if otherwise.
	 */
	public boolean equalsIgnoreCount(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Name other = (Name) obj;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equalsIgnoreCase(other.gender))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}
}
