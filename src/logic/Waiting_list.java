package logic;
/**
 * 
 * The object represents a queue of unrealized orders due to space constraints. 
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 */
public class Waiting_list implements TableAble {

	private int orderNumber;
	private String parkName;
	private int visitTime;
	private int visitDay;
	private int visitMonth;
	private int visitYear;
	private int numOfVisitors;
	/**
	 * Regular Constructor
	 * @param orderNumber
	 * @param parkName
	 * @param visitDay
	 * @param visitMonth
	 * @param visitYear
	 * @param visitTime
	 * @param numOfVisitors
	 */
	public Waiting_list(int orderNumber, String parkName, int visitDay, int visitMonth, int visitYear, int visitTime,
			int numOfVisitors) {
		super();
		this.orderNumber = orderNumber;
		this.parkName = parkName;
		this.visitDay = visitDay;
		this.visitMonth = visitMonth;
		this.visitYear = visitYear;
		this.visitTime = visitTime;
		this.numOfVisitors = numOfVisitors;
	}
	/**
	 * Semi Constructor - build Waiting_list from Waiting_list 
	 * @param wl
	 */
	public Waiting_list(Waiting_list wl) {
		this.orderNumber = wl.orderNumber;
		this.parkName = wl.parkName;
		this.visitDay = wl.visitDay;
		this.visitMonth = wl.visitMonth;
		this.visitYear = wl.visitYear;
		this.visitTime = wl.visitTime;
		this.numOfVisitors = wl.numOfVisitors;
	}

	public Waiting_list(String objectString) {
		String[] info = objectString.split("\\s");
		setValues(Integer.parseInt(info[0]), info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]),
				Integer.parseInt(info[4]), Integer.parseInt(info[5]), Integer.parseInt(info[6]));
	}

	public void setValues(int orderNumber, String parkName, int visitDay, int visitMonth, int visitYear, int visitTime,
			int numOfVisitors) {
		this.orderNumber = orderNumber;
		this.parkName = parkName;
		this.visitDay = visitDay;
		this.visitMonth = visitMonth;
		this.visitYear = visitYear;
		this.visitTime = visitTime;
		this.numOfVisitors = numOfVisitors;
	}

	public Waiting_list() {
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public int getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(int visitTime) {
		this.visitTime = visitTime;
	}

	public int getVisitDay() {
		return visitDay;
	}

	public void setVisitDay(int visitDay) {
		this.visitDay = visitDay;
	}

	public int getVisitMonth() {
		return visitMonth;
	}

	public void setVisitMonth(int visitMonth) {
		this.visitMonth = visitMonth;
	}

	public int getVisitYear() {
		return visitYear;
	}

	public void setVisitYear(int visitYear) {
		this.visitYear = visitYear;
	}

	public int getNumOfVisitors() {
		return numOfVisitors;
	}

	public void setNumOfVisitors(int numOfVisitors) {
		this.numOfVisitors = numOfVisitors;
	}

	/**
	 * Function that returns All the name of the fileds of the object.
	 * The functionOverride from the Interface TableAble
	 */
	@Override
	public String toCategory() {
		return String.format("%s %s %s %s %s %s %s", "orderNumber", "parkName", "visitDay", "visitMonth", "visitYear",
				"visitTime", "numOfVisitors");
	}

	@Override
	public String toString() {
		return String.format("%d %s %d %d %d %d %d", orderNumber, parkName, visitDay, visitMonth, visitYear, visitTime,
				numOfVisitors);
	}

}
