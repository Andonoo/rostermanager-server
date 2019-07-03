package shiftman.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the roster for a day of the week. Contains the day's collection of shifts and stores
 * information regarding the assignment of workers and managers to shifts during this day.
 */
public class DailyRoster {
	private TimePeriod _workingHours = new TimePeriod();
	private String _day;
	private Shifts _shifts = new Shifts();
	private Map<Worker, Shifts> _workerShifts = new HashMap<Worker, Shifts>();
	private Map<Worker, Shifts> _managerShifts = new HashMap<Worker, Shifts>();

	/**
	 * Creates a DailyRoster object for the provided day
	 * 
	 * @param day Day of this roster
	 */
	public DailyRoster(String day) {
		_day = day;
	}

	/**
	 * Sets the working hours for this day's roster
	 */
	public void setWorkingHours(String startTime, String endTime) throws InvalidTimeException {
		_workingHours.setTimes(startTime, endTime);
	}

	/**
	 * Adds a shift to this day's roster with the following parameters.
	 */
	public void addShift(String startTime, String endTime, String minimumWorkers) throws InvalidTimeException {
		_shifts.addShift(_day, startTime, endTime, minimumWorkers, _workingHours);
	}

	/**
	 * Returns this day's shifts without managers
	 */
	public List<String> shiftsWithoutManagers() {
		return _shifts.shiftsWithoutManagers();
	}

	/**
	 * Returns this day's understaffed shifts
	 */
	public List<String> understaffedShifts() {
		return _shifts.understaffedShifts();
	}

	/**
	 * Returns a formatting list of strings containing this day's overstaffed shifts
	 */
	public List<String> overStaffedShifts() {
		return _shifts.overStaffedShifts();
	}

	/**
	 * Requests for the provided worker to be added to the shift represented by the
	 * parameters. Registers that worker's assignment.
	 */
	public void assignStaff(String startTime, String endTime, Worker worker, boolean isManager)
			throws ManagerAssignedException {
		Shift assignedShift = _shifts.assignStaff(startTime, endTime, worker, isManager);
		registerAssignment(assignedShift, worker, isManager);
	}

	/**
	 * Registers the assignment of a worker or manager to a shift in our
	 * _workerShift and _managerShift HashMaps. 
	 */
	public void registerAssignment(Shift shift, Worker worker, boolean isManager) {
		if (!isManager) {
			if (_workerShifts.get(worker) == null) {
				_workerShifts.put(worker, new Shifts());
			}
			Shifts tempShifts = _workerShifts.get(worker);
			tempShifts.addShift(shift);
			_workerShifts.put(worker, tempShifts);
		} else {
			if (_managerShifts.get(worker) == null) {
				_managerShifts.put(worker, new Shifts());
			}
			Shifts tempShifts = _managerShifts.get(worker);
			tempShifts.addShift(shift);
			_managerShifts.put(worker, tempShifts);
		}
	}

	/**
	 * Returns a list representing this days roster as a list of strings where: The
	 * first entry has the day of week followed by the working hours: 'day
	 * HH:MM-HH:MM' The remaining entries contain individual descriptions of each
	 * shift as provided by the Shift class's getDescription method.
	 * 
	 * @return Formatted list of string representing this day's roster
	 */
	public List<String> toStrings() {
		List<String> daysRoster = new ArrayList<String>();
		daysRoster.add(_day + " " + _workingHours.toString());
		daysRoster.addAll(_shifts.toDescriptions());
		return daysRoster;
	}

	/**
	 * Returns a formatted list of strings representing today's shifts which the
	 * provided worker is assigned to. Each entry contains one shift in the
	 * following format: day[HH:MM-HH:MM].
	 */
	public List<String> getRosterForWorker(Worker worker) {
		Shifts tempShifts = _workerShifts.get(worker);
		List<String> workerShifts = new ArrayList<String>();
		if (tempShifts == null) {
			return workerShifts;
		} else {
			tempShifts.sort();
			workerShifts.addAll(tempShifts.toStringList());
			return workerShifts;
		}
	}

	/**
	 * Returns a formatted list of strings representing today's shifts which the
	 * provided manager is assigned to. Each entry contains one shift in the
	 * following format: day[HH:MM-HH:MM].
	 */
	public List<String> getShiftsManagedBy(Worker worker) {
		Shifts tempShifts = _managerShifts.get(worker);
		List<String> managedShifts = new ArrayList<String>();
		if (tempShifts == null) {
			return managedShifts;
		} else {
			tempShifts.sort();
			managedShifts.addAll(tempShifts.toStringList());
			return managedShifts;
		}
	}
}
