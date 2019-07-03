package shiftman.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a collection of Worker objects for encapsulation purposes. 
 */
public class Workers {
	private List<Worker> _staff = new ArrayList<Worker>();
	private Map<Worker, Boolean> _staffAssignments = new HashMap<Worker, Boolean>();

	/**
	 * Creates a new Workers object
	 */
	public Workers() {
	}

	/**
	 * Creates a new worker and adds it to this collection of workers
	 */
	public void addWorker(String givenName, String familyName) {
		_staff.add(new Worker(givenName, familyName));
		_staffAssignments.put(_staff.get(_staff.size() - 1), false);
	}

	/**
	 * Adds the provided worker to this collection of workers
	 */
	public void addWorker(Worker worker) {
		_staff.add(worker);
		_staffAssignments.put(_staff.get(_staff.size() - 1), false);
	}

	/**
	 * Returns the worker associated with the provided name. Returns null if this
	 * worker cannot be found.
	 */
	public Worker findWorker(String givenName, String familyName) {
		for (Worker w : _staff) {
			if (w.isWorker(givenName, familyName)) {
				return w;
			}
		}
		return null;
	}

	/**
	 * Returns the worker associated with the provided name. Returns null if this
	 * worker cannot be found.
	 */
	public Worker findWorker(String fullName) {
		for (Worker w : _staff) {
			if (w.isWorker(fullName)) {
				return w;
			}
		}
		return null;
	}

	/**
	 * Returns a list of strings representing all workers in this collection of
	 * workers.
	 */
	public List<String> getStaff() {
		Collections.sort(_staff);
		List<String> staffList = new ArrayList<String>();
		for (Worker w : _staff) {
			staffList.add(w.toString());
		}
		return staffList;
	}

	/**
	 * Finds the worker with the provided name and sets them to be assigned
	 * (assigned to a shift).
	 */
	public void setAssigned(String givenName, String familyName) {
		_staffAssignments.put(findWorker(givenName, familyName), true);
	}

	/**
	 * Returns a list of strings representing all unassigned workers in this
	 * collection.
	 */
	public List<String> getUnassignedStaff() {
		Collections.sort(_staff);
		List<String> unassignedStaffList = new ArrayList<String>();
		for (Worker w : _staff) {
			if (!_staffAssignments.get(w)) {
				unassignedStaffList.add(w.toString());
			}
		}
		return unassignedStaffList;
	}

	/**
	 * Returns a formatted string representing all workers in this collection in the
	 * following format: [worker1FirstName worker1LastName, worker2FirstName
	 * worker2LastName ...].
	 */
	public String toList() {
		String workerList;
		Collections.sort(_staff);
		if (_staff.size() > 0) {
			workerList = "[";
			for (Worker w : _staff) {
				workerList = workerList + w.toString();
				workerList = workerList + ", ";
			}
			workerList = workerList.substring(0, workerList.length() - 2);
			workerList = workerList + "]";
		} else {
			workerList = "[No workers assigned]";
		}
		return workerList;
	}
}
