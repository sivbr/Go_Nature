package client;
import logic.Invoice;
import logic.MessageToServer;
import logic.Order;
import logic.Park;
import logic.Prices;
import logic.Summary_day;
import logic.Temporary;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import Enums.Command;
import Enums.InvoiceKind;
import Enums.ObjectKind;
import Enums.OrderStatus;
import Enums.TableName;
/**
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 *
 */
public class CardReader 
{
	/**
	 * There are 3 static flags
	 * isTest - The flag variable is responsible for whether we are currently on the test, in case people enter and leave the park without waiting for their turn.
	   In case not everyone will enter in turn.
	 * stopFlag - Stops the main loop of the program and ends the program.
	 * insertRandom - If the flag is flying, the program randomly puts people in the park
	 */
	static public boolean isTest = false; 
	static public boolean stopFlag = false; 
	static public boolean insertRandom  = true;
	
	private  ArrayList<Temporary> tempEnteranceList ;
	private  ArrayList<Temporary> tempCurrentList ;
	public static boolean tempt = true;
	public static Temporary staticTemp = new Temporary();
	RunTimeFunctions runTimeFunctions ;
	MessageToServer mts;
	
	/**
	 * CardReader Constructor - init all the variable of the class and Resets the Park counters.
	 */
	public CardReader()
	{
		tempEnteranceList = new ArrayList<Temporary>();
		tempCurrentList = new ArrayList<Temporary>();
		runTimeFunctions = new RunTimeFunctions();
		mts = new MessageToServer ();
		try {
			mts.addReportInfo("SELECT * FROM invoices");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		mts.toAcceptThread();
		if(CardReaderChat.list.toString().equals("[]") == false)
		{
			for(int i=0;i<CardReaderChat.list.size();i++)
			{
				Invoice invoice = new Invoice(CardReaderChat.list.get(i).toString());
				mts = new MessageToServer(Command.Delete,TableName.invoices,invoice.getInvoiceNumber()+"");
				mts.toAcceptThread();
			}
		}
		
		mts = new MessageToServer();
		try {
			mts.addReportInfo("SELECT * FROM parks");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mts.toAcceptThread();
		if(CardReaderChat.list.toString().equals("[]") == true) return;
		for(int i=0;i<CardReaderChat.list.size();i++)
		{
			Park park = new Park(CardReaderChat.list.get(i));
			park.setCurrentAllVisitors(0);
			park.setCurrentOccasionalVisitors(0);
			mts = new MessageToServer(Command.Update,TableName.parks,park.getName());
			try {
				mts.editObject(park);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mts.toAcceptThread();
		}
		
	}
	
	/**
	 * The main loop of the CardReader - The program brings in the people and updates all the counters.
	   In addition, it activates all other realTime functions such as sending messages, alerts and more.
	 * @throws Exception
	 */
	public void activate() throws Exception
	   { 	
		
		 int flag = 0;	 
		 while(stopFlag == false)
		 {
			 TimeUnit.SECONDS.sleep(2);
			 
			 LocalTime time = LocalTime.now();
			 LocalDate date = LocalDate.now();
			 
			 
			 switch(time.getHour())
			 {
			 case 8:
			 	{
			 		 if(flag != 0) break;
			 		 if(getTypeFromSQL(TableName.members, date,8)== false)
			 		 {
			 			 System.out.println("\nthere is an Exception in the getTypeFromSQL\n");
			 		 }
					 if(CardReaderChat.list.toString().equals("[]")== false)
						 addIntoEnteranceList(ObjectKind.Member);
					 if(getTypeFromSQL(TableName.visitors, date,8)== false)
			 		 {
			 			 System.out.println("\nthere is an Exception in the getTypeFromSQL\n");
			 		 }
					 if(CardReaderChat.list.toString().equals("[]")== false)
						 addIntoEnteranceList(ObjectKind.Visitor);
					 flag++;
					 break;
			 	}
			 case 12:
			 {
				 if(flag != 1) break;
				 if(getTypeFromSQL(TableName.members, date,12)== false)
		 		 {
		 			 System.out.println("\nthere is an Exception in the getTypeFromSQL\n");
		 		 }
				 if(CardReaderChat.list.toString().equals("[]")== false)
					 addIntoEnteranceList(ObjectKind.Member);
				 if(getTypeFromSQL(TableName.visitors, date,12)== false)
		 		 {
		 			 System.out.println("\nthere is an Exception in the getTypeFromSQL\n");
		 		 }
				 if(CardReaderChat.list.toString().equals("[]")== false)
					 addIntoEnteranceList(ObjectKind.Visitor);
				 flag++;
				 break;
			 }

			 case 16:
			 {
				 if(flag != 2) break;
				 if(getTypeFromSQL(TableName.members, date,21)== false)
		 		 {
		 			 System.out.println("\nthere is an Exception in the getTypeFromSQL\n");
		 		 }
				 if(CardReaderChat.list.toString().equals("[]")== false)
					 addIntoEnteranceList(ObjectKind.Member);
				 if(getTypeFromSQL(TableName.visitors, date,21)== false)
		 		 {
		 			 System.out.println("\nthere is an Exception in the getTypeFromSQL\n");
		 		 }
				 if(CardReaderChat.list.toString().equals("[]")== false)
					 addIntoEnteranceList(ObjectKind.Visitor);
				 flag=0;
				 break;
			 }
			 default:
			 {
				 
				 break;
			 }
			
		 }
			 
			 for(int index = 0;index< tempEnteranceList.size();index++)
			 {	
				 if(tempEnteranceList.toString().contains("[]") == true) break;
				 int resultTest = time.compareTo(tempEnteranceList.get(index).getArrivalTime());
				 if(isTest == true)
				 {
					 resultTest = -resultTest;
				 }
				 
				 if( resultTest > 0 )
				 {
					 Temporary temp = new Temporary(tempEnteranceList.get(index));
					 tempEnteranceList.remove(index);
					 mts = new MessageToServer(Command.Read,TableName.parks,temp.getParkName());
					 mts.toAcceptThread();
					 insertACustomerToThePark(temp,date,time);
				 }
			 }
			 for(int index = 0;index< tempCurrentList.size();index++)
			 {	
				 int resultTest = time.compareTo(tempCurrentList.get(index).getLeftTime());
				 if(isTest == true)
				 {
					 resultTest = -resultTest;
				 }
				if(resultTest > 0) 
				{
					Temporary temp = new Temporary(tempCurrentList.get(index));
					tempCurrentList.remove(index);
					getOutACustomerToThePark(temp);
					
				}
			 }
			 if (insertRandom == true && time.getHour()<18 && time.getHour()>7){
				 
				 for(int i = 0 ; i< 5 ;i++){
						 tempEnteranceList.add(Temporary.random(time, date));
				 }
			 }
			 if(time.getMinute() ==1 && time.getHour()==7)
			 {
				 runTimeFunctions.setDiscountForToday(date);
			 }
			 if(time.getMinute() % 20 ==0 )
			 {
				runTimeFunctions.setTomorrowNoifications();
				runTimeFunctions.checkNotifiedOrders();
				runTimeFunctions.checkWaitingList();
			 }

		 }
	}
	
	/**
	 * The function sends a query to receive by customer type 
	 * all relevant orders for a specific time and date.
	 * @param theTable
	 * @param date
	 * @param hour
	 * @return
	 */
	public boolean getTypeFromSQL(TableName theTable,LocalDate date,int hour)
	{
		MessageToServer mts = new MessageToServer();
		 try {
			mts.addReportInfo("SELECT * " +
					 		   "FROM orders " +
					 		   "WHERE visitDay = "+date.getDayOfMonth()+" AND visitMonth = "+date.getMonthValue()+" AND visitYear = "+ date.getYear()+ " AND visitTime = "+ hour+" AND "+
					 		   "personID IN ( SELECT ID "+
					 		   				 "From "+ theTable.toString() +" )");
			mts.toAcceptThread();
			return true;
		 } catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This function insert the orders to the entrance list.
	 * get an object to know which kind of customer is enter.
	 * @param type
	 * @return
	 */
	public boolean addIntoEnteranceList(ObjectKind type)
	{
		Random rand = new Random(); 
		MessageToServer mts;
		Order [] orders = new Order[CardReaderChat.list.size()];
		 for(int i= 0; i<CardReaderChat.list.size();i++)
		 {
			 orders[i] = new Order(CardReaderChat.list.get(i));
			 // Customers with orders that Not coming to the Park
			 if(rand.nextInt(8) == 5)
			 {
				 mts = new MessageToServer(Command.Update,TableName.orders,orders[i].getOrderNumber()+"");
				 mts.addInfo("status",OrderStatus.Expired.toString());
				 mts.toAcceptThread();
				 continue;
			 }
			 
			 LocalTime tempTime = LocalTime.now();
			 Temporary temp = new Temporary(tempTime.plusMinutes(rand.nextInt(120)),
					   			  tempTime.plusMinutes(120+ rand.nextInt(120)),
					              orders[i].getTotalPayment(),
					              orders[i].getNumOfVisitors()==1?1:(rand.nextInt(300)%orders[i].getNumOfVisitors()+1), 
					              orders[i].getPark());
			 temp.setOrderId(orders[i].getOrderNumber());
			 temp.setOrganizedGroup(orders[i].getOrganizationGroup());
			 temp.setType(type);
			 //temp.setType(type);
			 
			 // UPDATE THE COUNTERS !
			 tempEnteranceList.add(temp);
		 }
		 return true;
	}
	
	/**
	 * The function takes out people who want to get out of the park, simulating the spending of 
	   the people in the certificate reader.
	 * @param temp
	 */
	public void getOutACustomerToThePark(Temporary temp)
	{
		mts = new MessageToServer(Command.Read,TableName.parks,temp.getParkName());
		mts.toAcceptThread();
		if(temp.getOrderId()==-1)
		{
			CardReaderChat.park.setCurrentOccasionalVisitors(CardReaderChat.park.getCurrentOccasionalVisitors()-temp.getAmountOfPeople());
		}
		CardReaderChat.park.setCurrentAllVisitors(CardReaderChat.park.getCurrentAllVisitors() - temp.getAmountOfPeople());
		mts = new MessageToServer(Command.Update,TableName.parks,temp.getParkName());
		try {
			mts.editObject(CardReaderChat.park);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		mts.toAcceptThread();
	}
	
	/**
	 * This function insert the customer that want to enter to the park and updates the counters.
	 * Simulates the entry of the people in the certificate reader.
	 * @param temp
	 * @param date
	 * @param time
	 * @throws Exception
	 */
	public void insertACustomerToThePark(Temporary temp,LocalDate date,LocalTime time) throws Exception
	{
		//Update Park
		 
		 if(temp.getType() == ObjectKind.Temporary)
		 {
			 /* ONLY VISITOR WITHOUT A RESERVATION
			  * 
			  */
			 if(CardReaderChat.park.getCurrentOccasionalVisitors()+temp.getAmountOfPeople()>CardReaderChat.park.getOccasionalVisitors()) 
			 {
				 Invoice invoice =  new Invoice(temp);
				 invoice.setKind(InvoiceKind.Decline);
				 mts = new MessageToServer(Command.Add,TableName.invoices,"NULL");
				 mts.addObject(invoice);
				 mts.toAcceptThread();
				 return;
			 }
			 CardReaderChat.park.setCurrentAllVisitors(CardReaderChat.park.getCurrentAllVisitors()+temp.getAmountOfPeople());
			 CardReaderChat.park.setCurrentOccasionalVisitors(CardReaderChat.park.getCurrentOccasionalVisitors()+temp.getAmountOfPeople());
			 mts = new MessageToServer(Command.Update,TableName.parks,CardReaderChat.park.getName());
			 try {
				mts.editObject(CardReaderChat.park);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			 mts.toAcceptThread();
			 temp.setBill((double)(1 - (double)((double)CardReaderChat.park.getDiscount()/100))*Prices.getTicketPrice()*temp.getAmountOfPeople());
			 mts = new MessageToServer(Command.Read,TableName.summary_days,date+"_"+temp.getParkName());
			 mts.toAcceptThread();
			 if(CardReaderChat.getObject == false)
			 {
				 mts = new MessageToServer(Command.Add,TableName.summary_days,"NULL");
				 try {
					mts.addObject(new Summary_day(date+"_"+temp.getParkName(),temp.getParkName(),CardReaderChat.park.getMaxCapacity(),0,0,0,0,0,0,0,0,date.getDayOfMonth(),date.getMonthValue(),date.getYear()));
					mts.toAcceptThread();
					mts = new MessageToServer(Command.Read,TableName.summary_days,date+"_"+temp.getParkName());
					mts.toAcceptThread();
				 } catch (Exception e) {
					
					e.printStackTrace();
				}
			 }
			 CardReaderChat.summary_day.setTotalVisitors(CardReaderChat.summary_day.getTotalVisitors()+temp.getAmountOfPeople());
			 CardReaderChat.summary_day.setIncome((int)((int)CardReaderChat.summary_day.getIncome()+(int)temp.getBill()));
			 CardReaderChat.summary_day.setVisitors(CardReaderChat.summary_day.getVisitors()+temp.getAmountOfPeople());
			 CardReaderChat.summary_day.setGroup(CardReaderChat.summary_day.getGroup());
			 divededTimes(temp);
			 mts = new MessageToServer(Command.Update,TableName.summary_days,CardReaderChat.summary_day.getDate_park());
			 
				try {
					mts.editObject(CardReaderChat.summary_day);
				} catch (Exception e) {
				
					e.printStackTrace();
				}
			 mts.toAcceptThread();

			 Invoice invoice =  new Invoice(temp);
			 invoice.setKind(InvoiceKind.Invoice);
			 invoice.setOriginalPrice((int)Prices.getTicketPrice()*temp.getAmountOfPeople());
			 invoice.setParkDiscount(CardReaderChat.park.getDiscount());
			 mts = new MessageToServer(Command.Add,TableName.invoices,"NULL");
			 mts.addObject(invoice);
			 mts.toAcceptThread();
			 
			 
		 }
		 else
		 {
			
			 if(temp.getOrderId() == -1)
			 {
				 /* ONLY MEMBER WITHOUT A RESERVATION
				  * 
				  */
				 if(CardReaderChat.park.getCurrentOccasionalVisitors()+temp.getAmountOfPeople()>CardReaderChat.park.getOccasionalVisitors()) 
				 {
					 Invoice invoice =  new Invoice(temp);
					 invoice.setKind(InvoiceKind.Decline);
					 mts = new MessageToServer(Command.Add,TableName.invoices,"NULL");
					 mts.addObject(invoice);
					 mts.toAcceptThread();
					 return;
				 }
				 temp.setBill((double)(1 - (double)((double)CardReaderChat.park.getDiscount()/100))*Prices.getTicketPrice()*temp.getAmountOfPeople()*Prices.getMemberDiscount());
				 CardReaderChat.park.setCurrentOccasionalVisitors(CardReaderChat.park.getCurrentOccasionalVisitors()+temp.getAmountOfPeople());
				 CardReaderChat.park.setCurrentAllVisitors(CardReaderChat.park.getCurrentAllVisitors()+temp.getAmountOfPeople());
				 mts = new MessageToServer(Command.Update,TableName.parks,CardReaderChat.park.getName());
				 try {
					mts.editObject(CardReaderChat.park);
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				 mts.toAcceptThread();
				 mts = new MessageToServer(Command.Read,TableName.summary_days,date +"_"+temp.getParkName() );
				 mts.toAcceptThread();
				 CardReaderChat.summary_day.setTotalVisitors(CardReaderChat.summary_day.getTotalVisitors()+temp.getAmountOfPeople());
				 CardReaderChat.summary_day.setIncome((int)((int)CardReaderChat.summary_day.getIncome()+(int)temp.getBill()));
				 CardReaderChat.summary_day.setMembers(CardReaderChat.summary_day.getMembers()+temp.getAmountOfPeople());
				 divededTimes(temp);
				 mts = new MessageToServer(Command.Update,TableName.summary_days,date +"_"+temp.getParkName() );
				 try {
					mts.editObject(CardReaderChat.summary_day);
				} catch (Exception e) {
					e.printStackTrace();
				}
				 mts.toAcceptThread();

				 Invoice invoice =  new Invoice(temp);
				 invoice.setKind(InvoiceKind.Invoice);
				 invoice.setOriginalPrice((int)Prices.getTicketPrice()*temp.getAmountOfPeople());
				 invoice.setParkDiscount(CardReaderChat.park.getDiscount());
				 mts = new MessageToServer(Command.Add,TableName.invoices,"NULL");
				 mts.addObject(invoice);
				 mts.toAcceptThread();
				 
			 }
			 else
			 {
				 /* VISITOR AND MEMBERS WITH A RESERVATION
				  * 
				  */
				 CardReaderChat.park.setCurrentAllVisitors(CardReaderChat.park.getCurrentAllVisitors()+temp.getAmountOfPeople());
				 mts = new MessageToServer(Command.Update,TableName.parks,CardReaderChat.park.getName());
				 try {
					mts.editObject(CardReaderChat.park);
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				 mts.toAcceptThread();
				 mts = new MessageToServer(Command.Read,TableName.summary_days,date +"_"+temp.getParkName() );
				 mts.toAcceptThread();
				 CardReaderChat.summary_day.setTotalVisitors(CardReaderChat.summary_day.getTotalVisitors()+temp.getAmountOfPeople());
				 CardReaderChat.summary_day.setIncome((int)((int)CardReaderChat.summary_day.getIncome()+(int)temp.getBill()));
				 if(temp.getType() == ObjectKind.Member)
				 {
					 CardReaderChat.summary_day.setMembers(CardReaderChat.summary_day.getMembers()+temp.getAmountOfPeople());
				 }
				 else
				 {
					 CardReaderChat.summary_day.setVisitors(CardReaderChat.summary_day.getVisitors()+temp.getAmountOfPeople());
				 }
				 divededTimes(temp);
				 mts = new MessageToServer(Command.Update,TableName.summary_days,date +"_"+temp.getParkName() );
				 try {
					mts.editObject(CardReaderChat.summary_day);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				 mts.toAcceptThread();
				 mts = new MessageToServer(Command.Read,TableName.orders,temp.getOrderId()+"");
				 mts.toAcceptThread();
				 CardReaderChat.order.setEnterTime(temp.getArrivalTime().getHour()*100+temp.getArrivalTime().getMinute());
				 CardReaderChat.order.setExitTime(temp.getLeftTime().getHour()*100+temp.getLeftTime().getMinute());
				 CardReaderChat.order.setStatus(OrderStatus.Fulfilled.toString());
				 divededTimes(temp);
				 mts = new MessageToServer(Command.Update,TableName.orders,temp.getOrderId()+"");
				 try {
					mts.editObject(CardReaderChat.order);
				} catch (Exception e) {
					e.printStackTrace();
				}
				 mts.toAcceptThread();
				 
				 Invoice invoice =  new Invoice(temp);
				 invoice.setKind(InvoiceKind.Invoice);
				 invoice.setOriginalPrice((int)Prices.getTicketPrice()*temp.getAmountOfPeople());
				 invoice.setParkDiscount(CardReaderChat.park.getDiscount());
				 mts = new MessageToServer(Command.Add,TableName.invoices,"NULL");
				 mts.addObject(invoice);
				 mts.toAcceptThread();
				 
				 
			 }
			
		 }
		 //crc.getInvoice(temp,date,time);
		 tempCurrentList.add(temp);
	}
	
	/**
	 * Auxiliary function for sorting the entry time to the relevant type.
	 * @param temp
	 */
	private void divededTimes(Temporary temp)
	{
		if(temp.getArrivalTime().getHour()>=8 && temp.getArrivalTime().getHour()<12)
		{
			CardReaderChat.summary_day.setTime1(CardReaderChat.summary_day.getTime1()+temp.getAmountOfPeople());
			return;
		}
		if(temp.getArrivalTime().getHour()>=12 && temp.getArrivalTime().getHour()<16)
		{
			CardReaderChat.summary_day.setTime2(CardReaderChat.summary_day.getTime2()+temp.getAmountOfPeople());
			return;
		}
		
		CardReaderChat.summary_day.setTime3(CardReaderChat.summary_day.getTime3()+temp.getAmountOfPeople());
		
	}
	
}
