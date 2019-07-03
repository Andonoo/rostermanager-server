package shiftman.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the week's management system. Contains the week's roster, registered workers
 * and the main interactions between these two objects.
 */
public class RosterManager {
	private String _shopName;
	private WeeklyRoster _weeklyRoster = new WeeklyRoster();
	private Workers _registeredStaff = new Workers();

	/**
	 * Create new roster for given shop.
	 */
	public RosterManager(String shopName) {
		_shopName = shopName;
	}

	/**
	 * Requests the worker to be added to our registered staff.
	 */
	public void registerWorker(String givenName, String familyName) {
		_registeredStaff.addWorker(givenName, familyName);
	}

	/**
	 * Requests working hours to be set for the given day.
	 */
	public void setWorkingHours(String dayOfWeek, String startTime, String endTime)
			throws InvalidDayException, InvalidTimeException {
		_weeklyRoster.setWorkingHours(dayOfWeek, startTime, endTime);
	}

	/**
	 * Requests a shift to be added for the given day.
	 */
	public void addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers)
			throws InvalidDayException, InvalidTimeException {
		_weeklyRoster.addShift(dayOfWeek, startTime, endTime, minimumWorkers);
	}

	/**
	 * Finds the worker with the given name, requests that worker to be assigned to
	 * the given shift, and finally sets that worker to 'assigned'.
	 */
	public void assignStaff(String dayOfWeek, String startTime, String endTime, String givenName, String familyName,
			boolean isManager) throws ManagerAssignedException, InvalidDayException {
		Worker worker = _registeredStaff.findWorker(givenName, familyName);
		_weeklyRoster.assignStaff(dayOfWeek, startTime, endTime, worker, isManager);
		_registeredStaff.setAssigned(givenName, familyName);
	}

	/**
	 * Returns a formatted list of all registered staff.
	 */
	public List<String> getStaff() {
		return _registeredStaff.getStaff();
	}

	/**
	 * Returns a formatted list of all staff which are not assigned to any shifts.
	 */
	public List<String> getUnassignedStaff() {
		return _registeredStaff.getUnassignedStaff();
	}

	/**
	 * Returns a formatted list of all shifts which do not have managers assigned.
	 */
	public List<String> shiftsWithoutManagers() {
		return _weeklyRoster.shiftsWithoutManagers();
	}

	/**
	 * Returns a formatted list of all shifts which no not meet their minimum staff
	 * requirements.
	 */
	public List<String> understaffedShifts() {
		return _weeklyRoster.understaffedShifts();
	}

	/**
	 * Returns a formatted list of all shifts which exceed their minimum staff
	 * requirements.
	 */
	public List<String> overStaffedShifts() {
		return _weeklyRoster.overStaffedShifts();
	}

	/**
	 * Returns a formatted list of all shifts which the provided worker is assigned
	 * to.
	 */
	public List<String> getRosterForWorker(String workerName) {
		Worker worker = _registeredStaff.findWorker(workerName);
		return _weeklyRoster.getRosterForWorker(worker);
	}

	/**
	 * Returns a formatted list of all shifts which the provided worker is assigned
	 * to manage.
	 */
	public List<String> getShiftsManagedBy(String workerName) {
		Worker worker = _registeredStaff.findWorker(workerName);
		return _weeklyRoster.getShiftsManagedBy(worker);
	}

	/**
	 * Returns a formatted list which details the roster for the provided day.
	 */
	public List<String> getRosterForDay(String dayOfWeek) {
		List<String> daysRoster = new ArrayList<String>();
		daysRoster.add(_shopName);
		daysRoster.addAll(_weeklyRoster.getRosterForDay(dayOfWeek));
		if (daysRoster.size() > 2) {
			return daysRoster;
		} else {
			return new ArrayList<String>();
		}
	}
}
