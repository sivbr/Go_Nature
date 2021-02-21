package logic;

/**
 * 
 * This class represent the orders of the customers
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version December 2020
 * 
 */

public class Order implements TableAble {
	private String personID;
	private int orderNumber;
	private String park;
	private int visitDay;
	private int visitMonth;
	private int visitYear;
	private int visitTime;
	private int numOfVisitors;
	private double totalPayment;
	private int organizationGroup;
	private int paid;
	private String status;
	private int notificationHour;
	private int notificationMinute;
	private int enterTime;
	private int exitTime;
	
	/**
	 * Regular constructor for Order
	 * @param orderNumber
	 * @param personID
	 * @param park
	 * @param visitDay
	 * @param visitMonth
	 * @param visitYear
	 * @param visitTime
	 * @param numOfVisitors
	 * @param totalPayment
	 * @param organizationGroup
	 * @param paid
	 * @param status
	 * @param notificationHour
	 * @param notificationMinute
	 * @param enterTime
	 * @param exitTime
	 */
	public Order(int orderNumber, String personID, String park, int visitDay, int visitMonth, int visitYear,
			int visitTime, int numOfVisitors, double totalPayment, int organizationGroup, int paid, String status,
			int notificationHour, int notificationMinute, int enterTime, int exitTime) {
		super();
		this.orderNumber = orderNumber;
		this.personID = personID;
		this.park = park;
		this.visitDay = visitDay;
		this.visitMonth = visitMonth;
		this.visitYear = visitYear;
		this.visitTime = visitTime;
		this.numOfVisitors = numOfVisitors;
		this.totalPayment = totalPayment;
		this.organizationGroup = organizationGroup;
		this.paid = paid;
		this.status = status;
		this.notificationHour = notificationHour;
		this.notificationMinute = notificationMinute;
		this.enterTime = enterTime;
		this.exitTime = exitTime;
	}
	/**
	 * Get Order and Build Order - Demi Constructor
	 * @param o
	 */
	public Order(Order o) {
		this.orderNumber = o.orderNumber;
		this.personID = o.personID;
		this.park = o.park;
		this.visitDay = o.visitDay;
		this.visitMonth = o.visitMonth;
		this.visitYear = o.visitYear;
		this.visitTime = o.visitTime;
		this.numOfVisitors = o.numOfVisitors;
		this.totalPayment = o.totalPayment;
		this.organizationGroup = o.organizationGroup;
		this.paid = o.paid;
		this.status = o.status;
		this.notificationHour = o.notificationHour;
		this.notificationMinute = o.notificationMinute;
		this.enterTime = o.enterTime;
		this.exitTime = o.exitTime;

	}
	/**
	 * Constructor for Build The Order From one String 
	 * @param objectString
	 */
	public Order(String ObjectString) {
		String[] info = ObjectString.split("\\s");
		setValues(Integer.parseInt(info[0]), info[1], info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]),
				Integer.parseInt(info[5]), Integer.parseInt(info[6]), Integer.parseInt(info[7]),
				Double.parseDouble(info[8]), Integer.parseInt(info[9]), Integer.parseInt(info[10]), info[11],
				Integer.parseInt(info[12]), Integer.parseInt(info[13]), Integer.parseInt(info[14]),
				Integer.parseInt(info[15]));
	}
	/**
	 * Get The Values and Set in the variable of the object
	 * @param orderNumber
	 * @param personID
	 * @param park
	 * @param visitDay
	 * @param visitMonth
	 * @param visitYear
	 * @param visitTime
	 * @param numOfVisitors
	 * @param totalPayment
	 * @param organizationGroup
	 * @param paid
	 * @param status
	 * @param notificationHour
	 * @param notificationMinute
	 * @param enterTime
	 * @param exitTime
	 */
	public void setValues(int orderNumber, String personID, String park, int visitDay, int visitMonth, int visitYear,
			int visitTime, int numOfVisitors, double totalPayment, int organizationGroup, int paid, String status,
			int notificationHour, int notificationMinute, int enterTime, int exitTime) {
		this.orderNumber = orderNumber;
		this.personID = personID;
		this.park = park;
		this.visitDay = visitDay;
		this.visitMonth = visitMonth;
		this.visitYear = visitYear;
		this.visitTime = visitTime;
		this.numOfVisitors = numOfVisitors;
		this.totalPayment = totalPayment;
		this.organizationGroup = organizationGroup;
		this.paid = paid;
		this.status = status;
		this.notificationHour = notificationHour;
		this.notificationMinute = notificationMinute;
		this.enterTime = enterTime;
		this.exitTime = exitTime;
	}
	/**
	 * Empty constructor
	 */
	public Order() {
	}
	
	
	
	/**
	 * Creating String to show order details at the tracking orders controller
	 * @return
	 */

	public String stringToTrack() {
		String day = this.getVisitDay() < 10 ? "0" + String.valueOf(this.getVisitDay())
				: String.valueOf(this.getVisitDay());
		String month = this.visitMonth < 10 ? "0" + String.valueOf(this.getVisitMonth())
				: String.valueOf(this.getVisitMonth());
		String date = day + "/" + month + "/" + this.getVisitYear();
		String time = null;
		String status = null;

		switch (this.getVisitTime()) {
		case 8: {
			time = "08:00-12:00";
			break;
		}
		case 12: {
			time = "12:00-16:00";
			break;
		}
		case 16: {
			time = "16:00-20:00";
			break;
		}
		}

		switch (this.status) {
		case "Tomorrow": {
			status = "Your visit is tomorrow! please confirm/cancel";
			break;
		}
		case "NotifiedTom": {
			status = "Your visit is tomorrow! please confirm/cancel";
			break;
		}
		case "WLupdate": {
			status = "Waiting List Update - press to confirm order";
			break;
		}
		case "NotifiedWL": {
			status = "Waiting List Update - press to confirm order";
			break;
		}
		
		case "WaitingList": {
			status = "Waiting List";
			break;
		}
		
		case "VisitConfirmed": {
			status = "You Confirmed the visit tomorrow";
		}


		default: {
			status = this.status;
			break;

		}

		}
		return (String.valueOf(this.orderNumber) + "		" + this.park + "     " + date + "		" + time + "	"
				+ String.valueOf(this.numOfVisitors) + "	  	   " + status);
	}
	
	/**
	 * Return string to cancel report
	 * @return
	 */
	public String stringToCancelReport () {
		String day = this.getVisitDay() < 10 ? "0"+String.valueOf(this.getVisitDay()) : String.valueOf(this.getVisitDay());
		String month = this.visitMonth < 10 ? "0"+String.valueOf(this.getVisitMonth()) : String.valueOf(this.getVisitMonth());
		String date = day + "/" +month+ "/" +this.getVisitYear();
		String price = null;
		String time = null;
    	switch(this.getVisitTime())
    	{
	    	case 8:{
	    		time="08:00-12:00";
	    		break;
	    	}
	    	case 12:{	
	    		time="12:00-16:00";
	    		break;
	    	}
	    	case 16:
	    	{
	    		time="16:00-20:00";
	    		break;
	    	}
    	}
    	
    	String priceString = String.valueOf(this.totalPayment);
    	if(priceString.length() <= 4)
    	{
    		price =this.totalPayment+"	   ";
    	}
    	else {
    		price =this.totalPayment+"";
		}
 
   
		return (String.valueOf(this.orderNumber)+"	"+this.personID+"		"+this.park+"		"+date+"	"+time+"		"+ String.valueOf(this.numOfVisitors) +"		"+ price+"		"+this.status);
	}


	/**
	 * Return string to income report
	 * @return
	 */
	public String stringToIncomeReport () {
		String day = this.getVisitDay() < 10 ? "0"+String.valueOf(this.getVisitDay()) : String.valueOf(this.getVisitDay());
		String month = this.visitMonth < 10 ? "0"+String.valueOf(this.getVisitMonth()) : String.valueOf(this.getVisitMonth());
		String date = day + "/" +month+ "/" +this.getVisitYear();
   
		return ("	"+String.valueOf(this.orderNumber)+"			   	   "+date+"					"+ this.totalPayment);
	}
	
	
	public int getNotificationHour() {
		return notificationHour;
	}

	public void setNotificationHour(int notificationHour) {
		this.notificationHour = notificationHour;
	}

	public int getNotificationMinute() {
		return notificationMinute;
	}

	public void setNotificationMinute(int notificationMinute) {
		this.notificationMinute = notificationMinute;
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPark() {
		return park;
	}

	public void setPark(String park) {
		this.park = park;
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

	public int getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(int visitTime) {
		this.visitTime = visitTime;
	}

	public int getNumOfVisitors() {
		return numOfVisitors;
	}

	public void setNumOfVisitors(int numOfVisitors) {
		this.numOfVisitors = numOfVisitors;
	}

	public double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public int getOrganizationGroup() {
		return organizationGroup;
	}

	public void setOrganizationGroup(int organizationGroup) {
		this.organizationGroup = organizationGroup;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getEnterTime() {
		return enterTime;
	}
	
	public void setEnterTime(int enterTime) {
		this.enterTime = enterTime;
	}

	public int getExitTime() {
		return exitTime;
	}

	public void setExitTime(int exitTime) {
		this.exitTime = exitTime;
	}

	/**
	 * Function that returns All the name of the fileds of the object.
	 * The functionOverride from the Interface TableAble
	 */
	@Override
	public String toCategory() {
		return String.format("%s %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s", "orderNumber", "personID", "park", "visitDay",
				"visitMonth", "visitYear", "visitTime", "numOfVisitors", "totalPayment", "organizationGroup", "paid",
				"status", "notificationHour", "notificationMinute", "enterTime", "exitTime");
	}

	@Override
	public String toString() {
		return String.format("%d %s %s %d %d %d %d %d %.2f %d %d %s %d %d %d %d", orderNumber, personID, park, visitDay,
				visitMonth, visitYear, visitTime, numOfVisitors, totalPayment, organizationGroup, paid, status,
				notificationHour, notificationMinute, enterTime, exitTime);
	}

}