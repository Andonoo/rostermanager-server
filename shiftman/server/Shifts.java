package shiftman.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class serves to encapsulate a collection of Shift objects. 
 */
public class Shifts {
	private List<Shift> _shifts = new ArrayList<Shift>();

	/**
	 * Create a shift object with given parameters. Adds it to list of shifts, test
	 * if it is valid, and remove it if it is not. Returns the created shift.
	 */
	public Shift addShift(String day, String startTime, String endTime, String minimumWorkers, TimePeriod workingHours)
			throws InvalidTimeException {
		Shift _newShift = new Shift(day, startTime, endTime, minimumWorkers);
		try {
			_shifts.add(_newShift);
			testOverlap();
			testInWorkingHours(workingHours);
		} catch (InvalidTimeException e) {
			_shifts.remove(_newShift);
			_newShift = null;
			throw e;
		}
		return _newShift;
	}

	/**
	 * Adds the provided shift to this collection.
	 */
	public void addShift(Shift shift) {
		_shifts.add(shift);
	}

	/**
	 * Tests if the recently added shift overlaps with any existing shifts. If not,
	 * throwing an invalid time exception.
	 */
	public void testOverlap() throws InvalidTimeException {
		if (_shifts.size() > 1) {
			for (int i = 0; i < _shifts.size() - 1; i++) {
				if (_shifts.get(_shifts.size() - 1).testOverlap(_shifts.get(i))) {
					throw new InvalidTimeException("ERROR: Shift overlaps with existing shift(s)");
				}
			}
		}
	}

	/**
	 * Tests if the recently added shift is within this day's working hours. If not,
	 * throwing an invalid time exception.
	 */
	private void testInWorkingHours(TimePeriod workingHours) throws InvalidTimeException {
		if (!_shifts.get(_shifts.size() - 1).isWithin(workingHours)) {
			throw new InvalidTimeException("ERROR: Shift is not within working hours");
		}
	}

	/**
	 * Find the shift represented by the given parameters and assign the provided
	 * worker to that shift.
	 */
	public Shift assignStaff(String startTime, String endTime, Worker worker, boolean isManager)
			throws ManagerAssignedException {
		for (Shift s : _shifts) {
			if (s.isShift(startTime, endTime)) {
				s.assignStaff(worker, isManager);
				return s;
			}
		}
		return null;
	}

	/**
	 * Return a sorted list of strings which represents all shifts in this collection which
	 * have more workers assigned than their minimum.
	 */
	public List<String> overStaffedShifts() {
		Collections.sort(_shifts);
		List<String> overStaffedShifts = new ArrayList<String>();
		for (Shift s : _shifts) {
			if (s.testStaffing() > 0) {
				overStaffedShifts.add(s.toString());
			}
		}
		return overStaffedShifts;
	}

	/**
	 * Return a sorted list of strings which represents all shifts in this collection which
	 * have less workers assigned than their minimum.
	 */
	public List<String> understaffedShifts() {
		sort();
		List<String> underStaffedShifts = new ArrayList<String>();
		for (Shift s : _shifts) {
			if (s.testStaffing() < 0) {
				underStaffedShifts.add(s.toString());
			}
		}
		return underStaffedShifts;
	}

	/**
	 * Returns a list of strings where each entry in the list is the description of
	 * a shift in this collection.
	 */
	public List<String> toDescriptions() {
		List<String> daysRoster = new ArrayList<String>();
		sort();
		for (Shift s : _shifts) {
			daysRoster.add(s.getFormattedDescription());
		}
		return daysRoster;
	}

	/**
	 * Returns a sorted list of strings where each string represents a shift in this
	 * collection without an assigned manager.
	 */
	public List<String> shiftsWithoutManagers() {
		sort();
		List<String> withoutManagers = new ArrayList<String>();
		for (Shift s : _shifts) {
			if (!s.hasManager()) {
				withoutManagers.add(s.toString());
			}
		}
		return withoutManagers;
	}

	/**
	 * Sorts this collection by the natural order of a Shift.
	 */
	public void sort() {
		Collections.sort(_shifts);
	}

	/**
	 * Returns a sorted list of strings where each string is the string representation of a
	 * Shift in this collection.
	 */
	public List<String> toStringList() {
		List<String> shiftList = new ArrayList<String>();
		sort();
		for (Shift s : _shifts) {
			shiftList.add(s.toString());
		}
		return shiftList;
	}
}