package shiftman.server;

/**
 * Represents a period of time supplied as a string for the sake of simplified formatting and testing
 * of this period of time. 
 */
public class TimePeriod implements Comparable<TimePeriod> {
	private String _startTime;
	private String _endTime;
	private Integer _startHour;
	private Integer _startMinute;
	private Integer _endHour;
	private Integer _endMinute;
	private int _startAbsolute;
	private int _endAbsolute;

	public TimePeriod() {
	}

	/**
	 * Sets the start and end times for this TimePeriod object. Then formats these
	 * start and end times, and tests to ensure that these times are valid.
	 */
	public void setTimes(String startTime, String endTime) throws InvalidTimeException {
		_startTime = startTime;
		_endTime = endTime;
		format();
		testValidity();
	}

	/**
	 * Tests the validity of this TimePeriod object i.e if it's start time is before
	 * it's end time, and if it starts and ends within one day.
	 * 
	 * @throws InvalidTimeException
	 */
	private void testValidity() throws InvalidTimeException {
		if (_startAbsolute >= _endAbsolute) {
			throw new InvalidTimeException("ERROR: Start time cannot be later than or equal to end time");
		} else if (_endAbsolute > 1439 || _startAbsolute < 0) {
			throw new InvalidTimeException("ERROR: Shift cannot span outside the hours of 00:00 and 23:59");
		}
	}

	/**
	 * Tests if the time period represented by this object is within the time period
	 * represented by the provided TimePeriod object.
	 * 
	 * @return Returns true if it is, false otherwise.
	 */
	public boolean isWithin(TimePeriod compTimeInfo) {
		if (_startAbsolute >= compTimeInfo._startAbsolute && _endAbsolute <= compTimeInfo._endAbsolute) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Initializes additional fields by extracting information from _startTime and
	 * _endTime strings.
	 */
	private void format() {
		_startHour = Integer.parseInt(_startTime.substring(0, 2));
		_startMinute = Integer.parseInt(_startTime.substring(3, 5));
		_endHour = Integer.parseInt(_endTime.substring(0, 2));
		_endMinute = Integer.parseInt(_endTime.substring(3, 5));
		_startAbsolute = 60 * _startHour.intValue() + _startMinute.intValue();
		_endAbsolute = 60 * _endHour.intValue() + _endMinute.intValue();
	}

	/**
	 * Tests if the time period represented by this object overlaps with the time
	 * period represented by the provided object.
	 */
	public boolean testOverlap(TimePeriod compTimeInfo) {
		if (!(compTimeInfo._endAbsolute < _startAbsolute || compTimeInfo._startAbsolute > _endAbsolute)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determines whether this time period object represents the same time period
	 * (shift) as that represented by the parameters.
	 */
	public boolean isPeriod(String startTime, String endTime) {
		if (_startTime.equals(startTime) && _endTime.contentEquals(endTime)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a string representation of this time period in the form HH:MM-HH:MM
	 */
	public String toString() {
		return _startTime + "-" + _endTime;
	}

	/**
	 * Returns a string representation of this time period in the form [HH:MM-HH:MM]
	 */
	public String toFormattedString() {
		return "[" + _startTime + "-" + _endTime + "]";
	}

	/**
	 * Compares this TimePeriod with the provided TimePeriod. Returns -1 if this
	 * TimePeriod naturally proceeds the provided, 0 if they are equal or 1 if this
	 * TimePeriod naturally follows the provided.
	 */
	@Override
	public int compareTo(TimePeriod ti) {
		if (_startAbsolute < ti._startAbsolute) {
			return -1;
		} else if (_startAbsolute > ti._startAbsolute) {
			return 1;
		} else {
			return 0;
		}
	}
}
