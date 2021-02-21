package logic;

import java.time.LocalDate;

import Enums.InvoiceKind;
import Enums.ObjectKind;

/**
 * 
 * This class represent the payment details of the customers
 * 
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version December 2020
 * 
 */

public class Invoice implements TableAble {
	private int invoiceNumber;
	private InvoiceKind kind;
	private String parkName;
	private int hour;
	private int minute;
	private int numOfVisitors;
	private int originalPrice;
	private double parkDiscount;
	private int isMember;
	private int isPaid;
	private int isOrganized;
	private int orderNumber;
	private double totalPrice;

	/**
	 * Regular constructor
	 * 
	 * @param invoiceNumber
	 * @param kind
	 * @param parkName
	 * @param hour
	 * @param minute
	 * @param numOfVisitors
	 * @param originalPrice
	 * @param parkDiscount
	 * @param isMember
	 * @param isPaid
	 * @param isOrganized
	 * @param orderNumber
	 * @param totalPrice
	 * 
	 */
	public Invoice(int invoiceNumber, InvoiceKind kind, String parkName, int hour, int minute, int numOfVisitors,
			int originalPrice, double parkDiscount, int isMember, int isPaid, int isOrganized, int orderNumber,
			double totalPrice) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.kind = kind;
		this.parkName = parkName;
		this.hour = hour;
		this.minute = minute;
		this.numOfVisitors = numOfVisitors;
		this.originalPrice = originalPrice;
		this.parkDiscount = parkDiscount;
		this.isMember = isMember;
		this.isPaid = isPaid;
		this.isOrganized = isOrganized;
		this.orderNumber = orderNumber;
		this.totalPrice = totalPrice;
	}

	/**
	 * this constructor is semi - constructor, build Invoice from Invoice
	 * 
	 * @param pd
	 */
	public Invoice(Invoice in) {
		this.invoiceNumber = in.invoiceNumber;
		this.kind = in.kind;
		this.parkName = in.parkName;
		this.hour = in.hour;
		this.minute = in.minute;
		this.numOfVisitors = in.numOfVisitors;
		this.originalPrice = in.originalPrice;
		this.parkDiscount = in.parkDiscount;
		this.isMember = in.isMember;
		this.isPaid = in.isPaid;
		this.isOrganized = in.isOrganized;
		this.orderNumber = in.orderNumber;
		this.totalPrice = in.totalPrice;
	}
	/**
	 * this constructor is semi - constructor, build Invoice from Temporary
	 * 
	 * @param pd
	 */
	public Invoice(Temporary temp)
	{
		this.invoiceNumber = (int)temp.getKey();
		this.parkName = temp.getParkName();
		this.hour = temp.getArrivalTime().getHour();
		this.minute = temp.getArrivalTime().getMinute();
		this.numOfVisitors = temp.getAmountOfPeople();
		if(temp.getType() == ObjectKind.Member)
		{
			this.isMember = 1;
		}
		else
		{
			this.isMember = 0;
		}
		this.isOrganized = temp.getIsOrganizedGroup();
		this.orderNumber = temp.getOrderId();
		this.totalPrice = temp.getBill();
		
	}

	/**
	 * Constructor that build Invoice from one String
	 * 
	 * @param objectString
	 */
	public Invoice(String objectString) {
		String[] info = objectString.split("\\s");
		setValues(Integer.parseInt(info[0]), InvoiceKind.valueOf(info[1]), info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]),
				Integer.parseInt(info[5]), Integer.parseInt(info[6]), Double.parseDouble(info[7]),
				Integer.parseInt(info[8]), Integer.parseInt(info[9]), Integer.parseInt(info[10]),
				Integer.parseInt(info[11]), Double.parseDouble(info[12]));
	}

	public void setValues(int invoiceNumber, InvoiceKind kind, String parkName, int hour, int minute, int numOfVisitors,
			int originalPrice, double parkDiscount, int isMember, int isPaid, int isOrganized, int orderNumber,
			double totalPrice) {
		this.invoiceNumber = invoiceNumber;
		this.kind = kind;
		this.parkName = parkName;
		this.hour = hour;
		this.minute = minute;
		this.numOfVisitors = numOfVisitors;
		this.originalPrice = originalPrice;
		this.parkDiscount = parkDiscount;
		this.isMember = isMember;
		this.isPaid = isPaid;
		this.isOrganized = isOrganized;
		this.orderNumber = orderNumber;
		this.totalPrice = totalPrice;
	}

	/**
	 * empty constructor
	 */
	public Invoice() {
	}

	
	/**
	 * 
	 * @return
	 */
	public String stringToTrack() {
		String day = LocalDate.now().getDayOfMonth()< 10 ? "0" + String.valueOf(LocalDate.now().getDayOfMonth())
				: String.valueOf(LocalDate.now().getDayOfMonth());
		String month = LocalDate.now().getMonthValue() < 10 ? "0" + String.valueOf(LocalDate.now().getMonthValue())
				: String.valueOf(LocalDate.now().getMonthValue());
		String date = day + "/" + month + "/" + LocalDate.now().getYear();
		
		String minute = this.getMinute()< 10 ? "0"+ this.getMinute()+"" : this.getMinute()+"";
		String hour =this.getHour() < 10 ? "0"+this.getHour()+"" :this.getHour()+"";
		String time = hour+":"+minute;
		
		
		 return (String.valueOf(this.invoiceNumber) + "		" +this.kind+"		" +date+
				 "		" +time+ "		" + this.parkName) ;
		}
	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public InvoiceKind getKind() {
		return kind;
	}

	public void setKind(InvoiceKind kind) {
		this.kind = kind;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getNumOfVisitors() {
		return numOfVisitors;
	}

	public void setNumOfVisitors(int numOfVisitors) {
		this.numOfVisitors = numOfVisitors;
	}

	public int getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(int originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getParkDiscount() {
		return parkDiscount;
	}

	public void setParkDiscount(double parkDiscount) {
		this.parkDiscount = parkDiscount;
	}

	public int getIsMember() {
		return isMember;
	}

	public void setIsMember(int isMember) {
		this.isMember = isMember;
	}

	public int getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(int isPaid) {
		this.isPaid = isPaid;
	}

	public int getIsOrganized() {
		return isOrganized;
	}

	public void setIsOrganized(int isOrganized) {
		this.isOrganized = isOrganized;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return String.format("%d %s %s %d %d %d %d %.2f %d %d %d %d %.2f", invoiceNumber, kind, parkName, hour, minute,
				numOfVisitors, originalPrice, parkDiscount, isMember, isPaid, isOrganized, orderNumber, totalPrice);
	}

	/**
	 * Function that returns All the name of the fileds of the object. The
	 * functionOverride from the Interface TableAble
	 */
	@Override
	public String toCategory() {
		return String.format("%s %s %s %s %s %s %s %s %s %s %s %s %s", "invoiceNumber", "kind", "parkName", "hour",
				"minute", "numOfVisitors", "originalPrice", "parkDiscount", "isMember", "isPaid", "isOrganized",
				"orderNumber", "totalPrice");
	}

}