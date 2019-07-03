package shiftman.server;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the shop's roster for one week. This is primarily the containment of a 'sub-roster'
 * for each day of the week. 
 */
public class WeeklyRoster {
	private Map<Days, DailyRoster> _dailyRosters = new EnumMap<Days, DailyRoster>(Days.class);

	public enum Days {
		Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
	}

	/**
	 * Creates the daily roster for each day of the weekend stores them in the
	 * EnumMap field _dailyRosters.
	 */
	public WeeklyRoster() {
		_dailyRosters.put(Days.Monday, new DailyRoster("Monday"));
		_dailyRosters.put(Days.Tuesday, new DailyRoster("Tuesday"));
		_dailyRosters.put(Days.Wednesday, new DailyRoster("Wednesday"));
		_dailyRosters.put(Days.Thursday, new DailyRoster("Thursday"));
		_dailyRosters.put(Days.Friday, new DailyRoster("Friday"));
		_dailyRosters.put(Days.Saturday, new DailyRoster("Saturday"));
		_dailyRosters.put(Days.Sunday, new DailyRoster("Sunday"));
	}

	/**
	 * Finds the daily roster for the provided day and requests that day's working
	 * hours to be set with the provided parameters
	 */
	public void setWorkingHours(String dayOfWeek, String startTime, String endTime)
			throws InvalidTimeException, InvalidDayException {
		try {
			_dailyRosters.get(Days.valueOf(dayOfWeek)).setWorkingHours(startTime, endTime);
		} catch (IllegalArgumentException e) {
			throw new InvalidDayException("ERROR: Day provided: " + dayOfWeek + " is invalid");
		}
	}

	/**
	 * Finds the daily roster for the provided day, and requests a shift be added to
	 * that day's roster with the provided parameters
	 */
	public void addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers)
			throws InvalidDayException, InvalidTimeException {
		try {
			_dailyRosters.get(Days.valueOf(dayOfWeek)).addShift(startTime, endTime, minimumWorkers);
		} catch (IllegalArgumentException e) {
			throw new InvalidDayException("ERROR: Day provided: " + dayOfWeek + " is invalid");
		}
	}

	/**
	 * Finds the daily roster for the provided day, and requests the provided worker
	 * to be assigned to the shift represented by the startTime and endTime
	 * parameters.
	 */
	public void assignStaff(String dayOfWeek, String startTime, String endTime, Worker worker, boolean isManager)
			throws ManagerAssignedException, InvalidDayException {
		try {
			_dailyRosters.get(Days.valueOf(dayOfWeek)).assignStaff(startTime, endTime, worker, isManager);
		} catch (IllegalArgumentException e) {
			throw new InvalidDayException("ERROR: Day provided: " + dayOfWeek + " is invalid");
		}
	}

	/**
	 * Iterates through each day of the week and concatenates their respective
	 * shifts without managers into one list of string which represents all the
	 * shifts without managers.
	 * 
	 * @return A list of strings containing shifts without managers
	 */
	public List<String> shiftsWithoutManagers() {
		List<String> withoutManagers = new ArrayList<String>();
		for (Days d : Days.values()) {
			withoutManagers.addAll(_dailyRosters.get(d).shiftsWithoutManagers());
		}
		return withoutManagers;
	}

	/**
	 * Iterates through each day of the week and concatenates their understaffed
	 * shifts into one list of string which represents all understaffed shifts.
	 * 
	 * @return A list of strings containing understaffed shifts
	 */
	public List<String> understaffedShifts() {
		List<String> understaffedShifts = new ArrayList<String>();
		for (Days d : Days.values()) {
			understaffedShifts.addAll(_dailyRosters.get(d).understaffedShifts());
		}
		return understaffedShifts;
	}

	/**
	 * Iterates through each day of the week and concatenates their overstaffed
	 * shifts into one list of string which represents all overstaffed shifts.
	 * 
	 * @return A list of strings containing overstaffed shifts
	 */
	public List<String> overStaffedShifts() {
		List<String> overStaffedShifts = new ArrayList<String>();
		for (Days d : Days.values()) {
			overStaffedShifts.addAll(_dailyRosters.get(d).overStaffedShifts());
		}
		return overStaffedShifts;
	}

	/**
	 * Iterates through each day of the week and concatenates the provided worker's
	 * shifts for those days. If the provided worker has no assigned shifts, returns
	 * an empty list.
	 * 
	 * @param worker
	 * @return List of strings containing worker's shifts (Empty list if worker has
	 *         no shifts)
	 */
	public List<String> getRosterForWorker(Worker worker) {
		List<String> workerShifts = new ArrayList<String>();
		workerShifts.add(worker.toStringReverse());
		for (Days d : Days.values()) {
			workerShifts.addAll(_dailyRosters.get(d).getRosterForWorker(worker));
		}
		if (workerShifts.size() > 1) {
			return workerShifts;
		} else {
			return new ArrayList<String>();
		}
	}

	/**
	 * Iterates through each day of the week and concatenates the provided manager's
	 * shifts for those days. If the provided manager has no assigned shifts,
	 * returns an empty list.
	 * 
	 * @return List of strings containing worker's shifts (Empty list if worker has
	 *         no shifts)
	 */
	public List<String> getShiftsManagedBy(Worker manager) {
		List<String> managedShifts = new ArrayList<String>();
		managedShifts.add(manager.toStringReverse());
		for (Days d : Days.values()) {
			managedShifts.addAll(_dailyRosters.get(d).getShiftsManagedBy(manager));
		}
		if (managedShifts.size() > 1) {
			return managedShifts;
		} else {
			return new ArrayList<String>();
		}
	}

	/**
	 * Returns a list of strings representing the provided day's roster.
	 */
	public List<String> getRosterForDay(String dayOfWeek) {
		return _dailyRosters.get(Days.valueOf(dayOfWeek)).toStrings();
	}
}
