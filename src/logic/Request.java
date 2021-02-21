package logic;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import Enums.ReqStatus;
import PMCapacity.CapacityParkManagerController;

/**
 * 
 * This Object represent the request that send between the manager to the department manager and opposite
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 */
public class Request implements TableAble {
	private int requestID;
	private String parkName;
	private String startDate;
	private String endDate;
	private String type;
	private String request;
	private String requestDate;
	private ReqStatus status;


	public Request(int requestID, String parkName, String type, String startDate, String endDate, String request,
			String requestDate, ReqStatus status) {
		super();
		this.requestID = requestID;
		this.parkName = parkName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.request = request;
		this.requestDate = requestDate;
		this.status = status;
	}

	/**
	 * Semi Constructor - Build Request from Request
	 * @param r
	 */
	public Request(Request r) {
		this.requestID = r.requestID;
		this.parkName = r.parkName;
		this.startDate = r.startDate;
		this.endDate = r.endDate;
		this.type = r.type;
		this.request = r.request;
		this.requestDate = r.requestDate;
		this.status = r.status;
	}

	public Request(String objectString) {
		String[] info = objectString.split("\\s");
		setValues(Integer.parseInt(info[0]), info[1], info[2], info[3], info[4], info[5], info[6], ReqStatus.valueOf(info[7]));
	}

	public void setValues(int requestID, String parkName, String type, String startDate, String endDate, String request,
			String requestDate, ReqStatus status) {
		
		this.requestID = requestID;
		this.parkName = parkName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.request = request;
		this.requestDate = requestDate;
		this.status = status;
	}
	/**
	 * Empty Constructor
	 */
	public Request() {
	}



	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public ReqStatus getStatus() {
		return status;
	}

	public void setStatus(ReqStatus status) {
		this.status = status;
	}

	/**
	 * Return request report as string
	 * @return
	 */
	public String toStringAsRow() {

		return String.format("\t%d\t\t   %s\t\t\t%s\t\t %s\t\t %s\t\t %s\t %s\t%s", requestID, parkName, type,startDate,endDate,
				request, requestDate, status.toString());
	}
	public String toStringAsRowDiscount() 
	{
		LocalDate sd=LocalDate.parse(startDate);
		LocalDate td=LocalDate.parse(endDate);
		int DayF=sd.getDayOfMonth();
		int MonthF=sd.getMonthValue();
		int yearF=sd.getYear();
		String fromDate= DayF+"/"+ MonthF+"/"+yearF;
		int DayT=td.getDayOfMonth();
		int MonthT=td.getMonthValue();
		int yearT=td.getYear();
		String toDate= DayT+"/"+ MonthT+"/"+yearT;
		return String.format("\t%d\t\t   %s\t\t\t%s\t %s\t\t  %s\t %s\t\t %s\t%s", requestID, parkName, type,fromDate,
				toDate,request, requestDate, status.toString());
	}
	public String toStringCheckReports () {
		   
		return (String.valueOf(this.requestID)+"		  "+this.parkName+"			"+ this.requestDate + "		"+this.type+"		"+this.startDate);
	}
	
	/**
	 * Function that returns All the name of the fileds of the object.
	 * The functionOverride from the Interface TableAble
	 */
	@Override
	public String toCategory() {
		return String.format("%s %s %s %s %s %s %s %s", "requestID", "parkName","type", "startDate","endDate"
				, "request", "requestDate", "status");
	}

	@Override
	public String toString() {
		return String.format("%d %s %s %s %s %s %s %s", requestID, parkName, type,startDate,endDate,
				request, requestDate, status.toString());
	}

}
