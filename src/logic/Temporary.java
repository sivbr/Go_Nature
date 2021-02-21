package logic;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import Enums.Command;
import Enums.ObjectKind;
import Enums.TableName;
import client.CardReaderChat;
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
public class Temporary {
	public static long nextKey=1;
	private long key;
	private LocalTime arrivalTime;
	private LocalTime leftTime;
	private double bill;
	private int amountOfPeople;
	private int isOrganizedGroup = 0;
	private ObjectKind type = null;
	private int orderId = -1;
	private String parkName = "";
	
	
	public Temporary(LocalTime arrivalTime, LocalTime leftTime, double bill, int amountOfPeople,String parkName) {
		super();
		this.parkName = parkName;
		this.arrivalTime = arrivalTime;
		this.leftTime = leftTime;
		this.bill = bill;
		this.amountOfPeople = amountOfPeople;
		this.key = nextKey;
		nextKey++;
	}
	
	public Temporary() {}
	
	public Temporary(Temporary temp) {
		super();
		this.parkName = temp.parkName;
		this.arrivalTime = temp.arrivalTime;
		this.leftTime = temp.leftTime;
		this.bill = temp.bill;
		this.amountOfPeople = temp.amountOfPeople;
		this.orderId = temp.orderId;
		this.isOrganizedGroup = temp.isOrganizedGroup;
		this.type = temp.type;
		this.key = temp.key;
		
	}
	
	public static Temporary random(LocalTime nowTime,LocalDate date)
	{
		MessageToServer mts;
		Random rand = new Random();
		LocalTime []t = randomTime(nowTime);
		Park park = randomPark();
		// its not a loop , its only for finish or break in the middle
		while(((rand.nextInt(100)+1)%4)==0)
		{
			mts = new MessageToServer();
			
			try {
				mts.addReportInfo("SELECT * "+
								  "FROM members "+
								  "WHERE ID NOT IN (SELECT personID "+
								  			   "FROM orders "+
								  			   "WHERE visitDay = "+date.getDayOfMonth()+")" );
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			mts.toAcceptThread();
			if(CardReaderChat.getObject == false) break;
			if(CardReaderChat.list.toString().contains("[]") == true) break;
			Member []members = new Member[CardReaderChat.list.size()];
			for(int i=0;i<members.length;i++)
			{
				members[i] = new Member(CardReaderChat.list.get(i));
			}
			int index = rand.nextInt(5000) % members.length;
			Temporary temp = new Temporary(t[0],t[1],-1,1+ rand.nextInt(1000)% members[index].getNumberOfFamilyMembers(), park.getName());
						temp.setOrderId(-1);
						temp.setOrganizedGroup(0);
						temp.setType(ObjectKind.Member);
			return temp;
		}
		
		int visitorNumber = rand.nextInt(10)+1;
		Temporary temp = new Temporary(t[0],t[1],-1,visitorNumber, park.getName());
					temp.setOrderId(-1);
					temp.setOrganizedGroup(0);
					temp.setType(ObjectKind.Temporary);
		return temp;
	}
	private static Park randomPark()
	{
		Random rand = new Random();
		int index;
		MessageToServer mts = new MessageToServer();
		try {
			mts.addReportInfo("SELECT * FROM parks");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mts.toAcceptThread();
		Park []parks = new Park[CardReaderChat.list.size()];
		for(int i=0;i<parks.length;i++)
		{
			parks[i] = new Park(CardReaderChat.list.get(i));
		}
		index = rand.nextInt(100)%parks.length;
		
		return parks[index];
	}
	private static LocalTime[] randomTime(LocalTime nowTime)
	{
		Random rand = new Random();
		LocalTime []t = new LocalTime[2];
		LocalTime arrival;
		LocalTime left;
		arrival = nowTime.plusMinutes(rand.nextInt(60));
		left = arrival.plusMinutes(60 + rand.nextInt(60));
		t[0] = arrival;
		t[1] = left;
		return t; 
		
	}
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public LocalTime getLeftTime() {
		return leftTime;
	}
	public void setLeftTime(LocalTime leftTime) {
		this.leftTime = leftTime;
	}
	public double getBill() {
		return bill;
	}
	public void setBill(double bill) {
		this.bill = bill;
	}
	public int getAmountOfPeople() {
		return amountOfPeople;
	}
	public void setAmountOfPeople(int amountOfPeople) {
		this.amountOfPeople = amountOfPeople;
	}
	public int isOrganizedGroup() {
		return isOrganizedGroup;
	}
	public void setOrganizedGroup(int isOrganizedGroup) {
		this.isOrganizedGroup = isOrganizedGroup;
	}
	public ObjectKind getType() {
		return type;
	}
	
	public static long getNextKey() {
		return nextKey;
	}

	public int getIsOrganizedGroup() {
		return isOrganizedGroup;
	}

	public void setType(ObjectKind type) {
		this.type = type;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public long getKey() {
		return key;
	}
	public void setKey(long key) {
		this.key = key;
	}
	
	
}
