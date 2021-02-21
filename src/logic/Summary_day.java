package logic;

/**
 * 
 * This Object represent the history and statistic data union of one day and one park
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 */
public class Summary_day implements TableAble {
	private String date_park;
	private String parkName;
	private int maxCapacity;
	private int totalVisitors;
	private int visitors;
	private int members;
	private int peopleFromOrg;
	private int income;
	private int time1;
	private int time2;
	private int time3;
	private int day;
	private int month;
	private int year;
	/**
	 * Regular Constructor
	 * @param date_park
	 * @param parkName
	 * @param maxCapacity
	 * @param totalVisitors
	 * @param visitors
	 * @param members
	 * @param group
	 * @param income
	 * @param time1
	 * @param time2
	 * @param time3
	 * @param day
	 * @param month
	 * @param year
	 */
	public Summary_day(String date_park, String parkName, int maxCapacity, int totalVisitors, int visitors, int members,
			int group, int income, int time1, int time2, int time3, int day, int month, int year) {
		super();
		this.date_park = date_park;
		this.parkName = parkName;
		this.maxCapacity = maxCapacity;
		this.totalVisitors = totalVisitors;
		this.visitors = visitors;
		this.members = members;
		this.peopleFromOrg = group;
		this.income = income;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	/**
	 * Empty Constructor
	 */
	public Summary_day() {
	}
	/**
	 * Semi Constructor - build Summary_day from Summary_day
	 * @param sd
	 */
	public Summary_day(Summary_day sd) {
		this.date_park = sd.date_park;
		this.parkName = sd.parkName;
		this.maxCapacity = sd.maxCapacity;
		this.totalVisitors = sd.totalVisitors;
		this.visitors = sd.visitors;
		this.members = sd.members;
		this.peopleFromOrg = sd.peopleFromOrg;
		this.income = sd.income;
		this.time1 = sd.time1;
		this.time2 = sd.time2;
		this.time3 = sd.time3;
		this.day = sd.day;
		this.month = sd.month;
		this.year = sd.year;
	}
	/**
	 * Build a Summary_day from one String
	 * @param objectString
	 */
	public Summary_day(String objectString) {
		String[] info = objectString.split("\\s");
		setValues(info[0], info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]),
				Integer.parseInt(info[5]), Integer.parseInt(info[6]), Integer.parseInt(info[7]),
				Integer.parseInt(info[8]), Integer.parseInt(info[9]), Integer.parseInt(info[10]),
				Integer.parseInt(info[11]), Integer.parseInt(info[12]), Integer.parseInt(info[13]));
	}

	public void setValues(String date_park, String parkName, int maxCapacity, int totalVisitors, int visitors,
			int members, int group, int income, int time1, int time2, int time3, int day, int month, int year) {
		this.date_park = date_park;
		this.parkName = parkName;
		this.maxCapacity = maxCapacity;
		this.totalVisitors = totalVisitors;
		this.visitors = visitors;
		this.members = members;
		this.peopleFromOrg = group;
		this.income = income;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public String getDate_park() {
		return date_park;
	}

	public void setDate_park(String date_park) {
		this.date_park = date_park;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getTotalVisitors() {
		return totalVisitors;
	}

	public void setTotalVisitors(int totalVisitors) {
		this.totalVisitors = totalVisitors;
	}

	public int getVisitors() {
		return visitors;
	}

	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}

	public int getMembers() {
		return members;
	}

	public void setMembers(int members) {
		this.members = members;
	}

	public int getGroup() {
		return peopleFromOrg;
	}

	public void setGroup(int group) {
		this.peopleFromOrg = group;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
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

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * Return visitors number report as string
	 * @return
	 */
	public String toStringAsRow() {
  
		return String.format("  %d/%d/%d \t\t\t %s\t\t\t\t%d\t\t\t\t\t%d\t\t\t\t\t\t%d ", day, month, year,parkName,visitors, members, peopleFromOrg);
	}
	/**
	 * Return occupancy report as string
	 * @return
	 */
	public String toStringAsRowOccupancy() {
		  
		return String.format("  %d/%d/%d \t\t %s\t\t\t\t %d\t\t\t\t\t%d\t\t", day, month, year,parkName,totalVisitors,maxCapacity);
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %d %d %d %d %d %d %d %d %d %d %d %d", date_park, parkName, maxCapacity,
				totalVisitors, visitors, members, peopleFromOrg, income, time1, time2, time3, day, month, year);
	}

	/**
	 * Function that returns All the name of the fileds of the object.
	 * The functionOverride from the Interface TableAble
	 */
	@Override
	public String toCategory() {
		return String.format("%s %s %s %s %s %s %s %s %s %s %s %s %s %s", "date_park", "parkName", "maxCapacity",
				"totalVisitors", "visitors", "members", "peopleFromOrg", "income", "time1", "time2", "time3", "day", "month",
				"year");
	}

}
