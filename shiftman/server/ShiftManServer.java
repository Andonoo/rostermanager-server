package shiftman.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the ShiftMan interface. Primarily creates and calls methods on the week's roster 
 * manager. Also handles the catching of exceptions and returns appropriate error messages. 
 */
public class ShiftManServer implements ShiftMan {
	private RosterManager _shopRoster;

	public ShiftManServer() {
	}

	/**
	 * Creates a new RosterManager for the week.
	 */
	@Override
	public String newRoster(String shopName) {
		_shopRoster = new RosterManager(shopName);
		return "";
	}

	/**
	 * Sets the working hours of the current roster.
	 */
	@Override
	public String setWorkingHours(String dayOfWeek, String startTime, String endTime) {
		if (_shopRoster == null) {
			return "ERROR: You must first create a roster";
		} else {
			try {
				_shopRoster.setWorkingHours(dayOfWeek, startTime, endTime);
			} catch (InvalidDayException e) {
				return e.getMessage();
			} catch (InvalidTimeException e) {
				return e.getMessage();
			}
			return "";
		}
	}

	/**
	 * Registers a worker with the current roster.
	 */
	@Override
	public String registerStaff(String givenname, String familyName) {
		if (_shopRoster == null) {
			return "ERROR: You must first create a roster"; 
		} else {
			_shopRoster.registerWorker(givenname, familyName);
			return "";
		}
	}

	/**
	 * Adds a shift to the current roster.
	 */
	@Override
	public String addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers) {
		if (_shopRoster == null) {
			return "ERROR: no roster has been created";
		} else {
			try {
				_shopRoster.addShift(dayOfWeek, startTime, endTime, minimumWorkers);
			} catch (InvalidDayException e) {
				return e.getMessage();
			} catch (InvalidTimeException e) {
				return e.getMessage();
			}
			return "";
		}
	}

	/**
	 * Assigns a worker to a shift in the current roster.
	 */
	@Override
	public String assignStaff(String dayOfWeek, String startTime, String endTime, String givenName, String familyName,
			boolean isManager) {
		if (_shopRoster == null) {
			return "ERROR: no roster has been created";
		} else {
			try {
				_shopRoster.assignStaff(dayOfWeek, startTime, endTime, givenName, familyName, isManager);
			} catch (ManagerAssignedException e) {
				return e.getMessage();
			} catch (InvalidDayException e) {
				return e.getMessage();
			}
			return "";
		}
	}

	/**
	 * Returns a list of strings representing all staff registered with the current
	 * roster.
	 */
	@Override
	public List<String> getRegisteredStaff() {
		if (_shopRoster == null) {
			List<String> errorString = new ArrayList<String>();
			errorString.add("ERROR: no roster has been created");
			return errorString;
		} else {
			return _shopRoster.getStaff();
		}
	}

	/**
	 * Returns a list of strings representing all unassigned staff registered with
	 * the current roster.
	 */
	@Override
	public List<String> getUnassignedStaff() {
		if (_shopRoster == null) {
			List<String> errorString = new ArrayList<String>();
			errorString.add("ERROR: no roster has been created");
			return errorString;
		} else {
			return _shopRoster.getUnassignedStaff();
		}
	}

	/**
	 * Returns a list of strings representing the shifts in the current roster which
	 * do not have managers assigned.
	 */
	@Override
	public List<String> shiftsWithoutManagers() {
		if (_shopRoster == null) {
			List<String> errorString = new ArrayList<String>();
			errorString.add("ERROR: no roster has been created");
			return errorString;
		} else {
			return _shopRoster.shiftsWithoutManagers();
		} 
	}

	/**
	 * Returns a list of strings representing the shifts in the current roster which
	 * do not have their minimum number of workers assigned.
	 */
	@Override
	public List<String> understaffedShifts() {
		if (_shopRoster == null) {
			List<String> errorString = new ArrayList<String>();
			errorString.add("ERROR: no roster has been created");
			return errorString;
		} else {
			return _shopRoster.understaffedShifts();
		}
	}

	/**
	 * Returns a list of strings representing the shifts in the current roster which
	 * have more than their minimum number of workers assigned.
	 */
	@Override
	public List<String> overstaffedShifts() {
		if (_shopRoster == null) {
			List<String> errorString = new ArrayList<String>();
			errorString.add("ERROR: no roster has been created");
			return errorString;
		} else {
			return _shopRoster.overStaffedShifts();
		}
	}

	/**
	 * Returns a list of strings representing the provided worker's assigned shifts
	 * in the current roster.
	 */
	@Override
	public List<String> getRosterForWorker(String workerName) {
		if (_shopRoster == null) {
			List<String> errorString = new ArrayList<String>();
			errorString.add("ERROR: no roster has been created");
			return errorString;
		} else { 
			return _shopRoster.getRosterForWorker(workerName);
		}
	}

	/**
	 * Returns a list of strings representing the shifts managed by the provided
	 * worker in the current roster.
	 */
	@Override
	public List<String> getShiftsManagedBy(String managerName) {
		if (_shopRoster == null) {
			List<String> errorString = new ArrayList<String>();
			errorString.add("ERROR: no roster has been created");
			return errorString;
		} else { 
			return _shopRoster.getShiftsManagedBy(managerName);
		}
	}

	/**
	 * Returns a list of strings representing the sub-roster for the given day
	 * within the current roster.
	 */
	@Override
	public List<String> getRosterForDay(String dayOfWeek) {
		if (_shopRoster == null) {
			List<String> errorString = new ArrayList<String>();
			errorString.add("ERROR: no roster has been created");
			return errorString;
		} else { 
			return _shopRoster.getRosterForDay(dayOfWeek);
		}
	}

	@Override
	public String displayRoster() {
		return "";
	}

	@Override
	public String reportRosterIssues() {
		return "";
	}
}
