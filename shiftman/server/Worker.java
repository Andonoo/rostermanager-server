package shiftman.server;

/**
 * Basic representation of a worker to be assigned to shifts during the week. Contains the details
 * about the worker and allows the comparison of this worker to a string representing 
 * a worker's name. 
 */
public class Worker implements Comparable<Worker> {
	private String _givenName;
	private String _familyName;
	private String _fullName;

	/**
	 * Creates a new Worker object with the provided name
	 */
	public Worker(String givenName, String familyName) {
		_givenName = givenName;
		_familyName = familyName;
		_fullName = givenName + " " + familyName;
	}

	/**
	 * Returns a boolean base on whether this worker object represents the same
	 * worker represented by the parameters.
	 */
	public boolean isWorker(String givenName, String familyName) {
		if (_givenName.equals(givenName) && _familyName.contentEquals(familyName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a boolean base on whether this worker object represents the same
	 * worker represented by the parameters.
	 */
	public boolean isWorker(String fullName) {
		if (fullName.equals(_fullName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a string representation of this worker in the form 'John Doe'.
	 */
	public String toString() {
		return (_givenName + " " + _familyName);
	}

	/**
	 * Returns a string representation of this worker in the form 'Doe, John'.
	 */
	public String toStringReverse() {
		return (_familyName + ", " + _givenName);
	}

	/**
	 * Compares this worker with the provided base on the String compareTo of their
	 * family names.
	 */
	public int compareTo(Worker w) {
		return _familyName.compareToIgnoreCase(w._familyName);
	}
}
