package logic;
/**
 * the object use for checking which days are available.
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 *
 */
public class Possible_date implements TableAble {
	private String date_parkName;
	private String date;
	private String parkName;
	private int time1;
	private int time2;
	private int time3;
	private int maxCapacity;
	private int available;
	/**
	 * Regular Constructor for Possible_date
	 * 
	 * @param date_parkName
	 * @param date
	 * @param parkName
	 * @param time1
	 * @param time2
	 * @param time3
	 * @param maxCapacity
	 * @param available
	 */
	public Possible_date(String date_parkName, String date, String parkName, int time1, int time2, int time3,
			int maxCapacity, int available) {
		super();
		this.date_parkName = date_parkName;
		this.date = date;
		this.parkName = parkName;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
		this.maxCapacity = maxCapacity;
		this.available = available;
	}
	/**
	 * Empty constructor
	 */
	public Possible_date() {
	}
	/**
	 * semi Constructor - build Possible_date from Possible_date
	 * @param pd
	 */
	public Possible_date(Possible_date pd) {
		this.date_parkName = pd.date_parkName;
		this.date = pd.date;
		this.parkName = pd.parkName;
		this.time1 = pd.time1;
		this.time2 = pd.time2;
		this.time3 = pd.time3;
		this.maxCapacity = pd.maxCapacity;
		this.available = pd.available;
	}
	/**
	 * build Possible_date from one String
	 * @param objectString
	 */
	public Possible_date(String objectString) {
		String[] info = objectString.split("\\s");
		setValues(info[0], info[1], info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]),
				Integer.parseInt(info[5]), Integer.parseInt(info[6]), Integer.parseInt(info[7]));
	}

	public void setValues(String date_parkName, String date, String parkName, int time1, int time2, int time3,
			int maxCapacity, int available) {
		this.date_parkName = date_parkName;
		this.date = date;
		this.parkName = parkName;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
		this.maxCapacity = maxCapacity;
		this.available = available;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTime1() {
		return time1;
	}

	public void setTime1(int time1) {
		this.time1 = time1;
	}

	public int getTime2() {
		return time2;
	}

	public void setTime2(int time2) {
		this.time2 = time2;
	}

	public int getTime3() {
		return time3;
	}

	public void setTime3(int time3) {
		this.time3 = time3;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public String getDate_parkName() {
		return date_parkName;
	}

	public void setDate_parkName(String date_parkName) {
		this.date_parkName = date_parkName;
	}


	@Override
	public String toString() {
		return String.format("%s %s %s %d %d %d %d %d", date_parkName, date, parkName, time1, time2, time3, maxCapacity,
				available);
	}

	/**
	 * Function that returns All the name of the fileds of the object.
	 * The functionOverride from the Interface TableAble
	 */
	@Override
	public String toCategory() {
		return String.format("%s %s %s %s %s %s %s %s", "date_parkName", "date", "parkName", "time1", "time2", "time3",
				"maxCapacity", "available");
	}

}
