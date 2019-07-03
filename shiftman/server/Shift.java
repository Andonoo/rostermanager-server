package shiftman.server;

/**
 * This class creates an object representing one shift. It also stores basic information about this
 * shift and allows basic tests of the shift relating to this information. 
 */
public class Shift implements Comparable<Shift> {
	private TimePeriod _shiftTime = new TimePeriod();
	private int _minimumWorkers;
	private Workers _shiftWorkers = new Workers();
	private Worker _manager;
	private int workerCount = 0;
	private String _day;

	/**
	 * Creates a new shift object with the provided parameters.
	 */
	public Shift(String day, String startTime, String endTime, String minimumWorkers) throws InvalidTimeException {
		_shiftTime.setTimes(startTime, endTime);
		_minimumWorkers = Integer.parseInt(minimumWorkers);
		_day = day;
	}

	/**
	 * Tests whether this shift overlaps with the provided shift.
	 * 
	 * @param compShift - Shift being compared
	 * @return True if they overlap, false otherwise
	 */
	public boolean testOverlap(Shift compShift) {
		return _shiftTime.testOverlap(compShift._shiftTime);
	}

	/**
	 * Tests if this shift is equal to the one represented by the input parameters.
	 */
	public boolean isShift(String startTime, String endTime) {
		if (_shiftTime.isPeriod(startTime, endTime)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Assigns the provided worker or manager to this shift. If they are not a
	 * manager, adding one to the worker count.
	 */
	public void assignStaff(Worker worker, boolean isManager) throws ManagerAssignedException {
		if (isManager) {
			if (_manager == null) {
				_manager = worker;
			} else {
				throw new ManagerAssignedException("ERROR: " + _manager.toString() + " already manages this shift");
			}
		} else {
			_shiftWorkers.addWorker(worker);
			workerCount++;
		}
	}

	/**
	 * Returns a description of this string in the format: 'day HH:MM'.
	 */
	public String shiftDescription() {
		return _day + _shiftTime.toString();
	}

	/**
	 * Returns true if this shift has a manager, false otherwise.
	 */
	public boolean hasManager() {
		if (_manager == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns a comparison which determines the natural order of shifts. Returns an
	 * int < 0 if this object proceeds the provided, 0 if they are equal, or > 0 if
	 * this object follows the provided.
	 */
	@Override
	public int compareTo(Shift shift) {
		return _shiftTime.compareTo(shift._shiftTime);
	}

	/**
	 * Tests whether this shift is overstaff, understaffed or staffed correctly.
	 * Returns a negative number if the shift is understaffed, 0 if it is correctly
	 * staffed or a positive number if the shift is overstaffed.
	 * 
	 * @return int representing the shift's staffing.
	 */
	public int testStaffing() {
		if (workerCount < _minimumWorkers) {
			return -1;
		} else if (workerCount > _minimumWorkers) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Returns a string to represent this shift in the format: day[HH:MM]
	 */
	public String toString() {
		return _day + _shiftTime.toFormattedString();
	}

	/**
	 * Returns a string describing this shift in the format: day[starttime-endtime]
	 * Manager: family name, given name [list of worker names separated by ", "]
	 */
	public String getFormattedDescription() {
		if (_manager == null) {
			return _day + _shiftTime.toFormattedString() + " [No manager assigned]" + " "
					+ _shiftWorkers.toList();
		} else {
			return _day + _shiftTime.toFormattedString() + " Manager:" + _manager.toStringReverse() + " "
					+ _shiftWorkers.toList();
		}
	}

	/**
	 * Tests it this shift is within the provided working hours
	 * 
	 * @param TimePeriod object containing working hours
	 * @return boolean, true if this shift is within the working hours
	 */
	public boolean isWithin(TimePeriod _workingHours) {
		if (_shiftTime.isWithin(_workingHours)) {
			return true;
		} else {
			return false;
		}
	}
}
