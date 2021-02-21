package client;

import java.time.LocalDate;
import java.time.LocalTime;

import Enums.Command;
import Enums.ObjectKind;
import Enums.ReqStatus;
import Enums.TableName;
import logic.MessageToServer;
import logic.Order;
import logic.Park;
import logic.Prices;
import logic.Request;

public class RunTimeFunctions {
	
	public static boolean mailFlag = true;
	/**
	 * ask the DB for all orders which got notification 
	 * (in order to make sure client responded to it)
	 * clients whom did'nt respond will get notify by mail about automatic cancellation order
	 */
	public void checkNotifiedOrders()
	{
		MessageToServer mts = new MessageToServer();
		try {
			mts.addReportInfo("SELECT * FROM orders WHERE ( status = 'NotifiedWL' OR status = 'NotifiedTom' )");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mts.toAccept();	
		//create orders array from report
		if (!ChatClient.list.toString().equals("[]")) {
		Order orders[] = new Order[ChatClient.list.size()];
		for(int i=0;i<orders.length;i++)
			orders[i]=new Order(ChatClient.list.get(i));
		
		for (Order o : orders)
		{ 
			int availableTickets = 0;
			Park p1;
			mts = new MessageToServer(Command.Read,TableName.parks,o.getPark());
			mts.toAccept();
			p1 = new Park(ChatClient.park);
			String date_park = o.getVisitYear()+"-"+o.getVisitMonth()+"-"+o.getVisitDay()+"_"+o.getPark();
			mts = new MessageToServer(Command.Read,TableName.Possible_dates,date_park);
			mts.toAccept();
			if (ChatClient.objectType==ObjectKind.Possible_date)
			{
				if(o.getVisitTime()==8)
				 availableTickets = ((int)((p1.getMaxCapacity()-p1.getOccasionalVisitors())/3)-ChatClient.possible_date.getTime1());
				else if(o.getVisitTime()==12)
					 availableTickets = ((int)((p1.getMaxCapacity()-p1.getOccasionalVisitors())/3)-ChatClient.possible_date.getTime2());
				else if(o.getVisitTime()==16)
					 availableTickets = ((int)((p1.getMaxCapacity()-p1.getOccasionalVisitors())/3)-ChatClient.possible_date.getTime3());

			}
			int t = LocalTime.now().getHour()*100;
			System.out.println(t);
			if (o.getNotificationHour() == 22 || o.getNotificationHour() == 23|| o.getNotificationHour() == 24)
			switch (t)
			{
			
				case 0:{
					t=2400;
					break;
				}
				
				case 100:{
					t=2500;
					break;
				}
				case 200:{
					t=2600;
					break;
				}
				
			}
			int now = t + LocalTime.now().getMinute();
			int notify = o.getNotificationHour()*100 + o.getNotificationMinute() ;
		//	System.out.println((now - notify));
					 
			if (o.getStatus().contains("NotifiedWL"))
				if ((now - notify)>100)
				{
					mts = new MessageToServer(Command.Update,TableName.orders,o.getOrderNumber()+"");	
					mts.addInfo("status", "Cancelled");
					mts.toAccept();	
					
					
					
					
					//CHECK OTHER WAITING LIST REQUSET
			    		MessageToServer mcs = new MessageToServer();
						try {
							String s = "SELECT orderNumber"
									+ " FROM waiting_lists"
									+ " WHERE orderNumber = (SELECT MIN(orderNumber) FROM waiting_lists"
									+ " WHERE ( parkName = 'val' AND visitTime = val1 AND visitDay = val2 AND visitMonth = val3"
									+ " AND visitYear = val4 AND numOfVisitors <= val5 ))";
							s=s.replaceFirst("val", o.getPark());
							s=s.replaceFirst("val1", o.getVisitTime()+"");
							s=s.replaceFirst("val2", o.getVisitDay()+"");
							s=s.replaceFirst("val3", o.getVisitMonth()+"");
							s=s.replaceFirst("val4", o.getVisitYear()+"");
							s=s.replaceFirst("val5", availableTickets+"");
							System.out.println(s);
							mcs.addReportInfo(s);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mcs.toAccept();	 
			    		
			    		
						if (!ChatClient.list.toString().equals("[]")) {
			    			String s = null;
			    			s= ChatClient.list.get(0);
			    			MessageToServer  m = new MessageToServer(Command.Update,TableName.orders,s);			
			    			m.addInfo("status", "WLupdate");
			    			m.toAccept();	 
			    		    m = new MessageToServer(Command.Delete,TableName.waiting_lists,s);	
			    			m.toAccept();
			    			
			    			String email = null;
			    			mts = new MessageToServer(Command.Read,TableName.orders,s);	
			    			mts.toAccept();	//
			    			mts = new MessageToServer(Command.Read,TableName.members,ChatClient.order.getPersonID());	
			    			mts.toAccept();	//
			    			if (ChatClient.objectType != ObjectKind.Member) {
			    			mts = new MessageToServer(Command.Read,TableName.visitors,ChatClient.order.getPersonID());	
			    			mts.toAccept();	//
			    			email = ChatClient.visitor.getMail();

			    			}
			    			else
			    			{
			    				
			    				email = ChatClient.member.getMail();
			    			} 
			    			String title = 	"Go-Nature System- Update for Order Number: "+s;
			    			StringBuilder message = new StringBuilder();
			    			message.append("Hi There! \n");
			    			message.append("After waiting a while...  \n");
			    			message.append("We are happy to tell you you can place your order \n");
			    			message.append("If you are still intrested - Please confirm your order at our system  \n \n");
			    			message.append("Have a nice day ! :-) \n");



			    		    try {
			    				mts.addMailInfo("brechersivan@gmail.com" ,title ,message.toString());
			    			} catch (Exception e) {
			    				// TODO Auto-generated catch block
			    				e.printStackTrace();
			    			}
			    		    if (mailFlag)
			    		  mts.toAccept();	 

			    		
			    	
			    		
			    	} 
						
				}
			    if (o.getStatus().contains("NotifiedTom"))
				if ((now - notify)>200)
				{
					mts = new MessageToServer(Command.Update,TableName.orders,o.getOrderNumber()+"");	
					mts.addInfo("status", "Cancelled");
					mts.toAccept();	
					
					//Sending Cancellation mail
					 availableTickets = 0;
					
					 mts = new MessageToServer(Command.Read,TableName.parks,o.getPark());
					mts.toAccept();
					p1 = new Park(ChatClient.park);
					String day = ChatClient.order.getVisitDay()< 10 ? "0"+ ChatClient.order.getVisitDay()+"" : ChatClient.order.getVisitDay()+"";
					String month = ChatClient.order.getVisitMonth() < 10 ? "0"+ChatClient.order.getVisitMonth()+"" :ChatClient.order.getVisitMonth()+"";
					date_park = o.getVisitYear()+"-"+month+"-"+day+"_"+o.getPark();

					mts = new MessageToServer(Command.Read,TableName.Possible_dates,date_park);
					mts.toAccept();
					if (ChatClient.objectType==ObjectKind.Possible_date)
					{
						if(o.getVisitTime()==8)
							 availableTickets = (p1.getMaxCapacity()-p1.getOccasionalVisitors()/3)-ChatClient.possible_date.getTime1();
							else if(o.getVisitTime()==12)
								 availableTickets = (p1.getMaxCapacity()-p1.getOccasionalVisitors()/3)-ChatClient.possible_date.getTime2();
							else if(o.getVisitTime()==16)
								 availableTickets = (p1.getMaxCapacity()-p1.getOccasionalVisitors()/3)-ChatClient.possible_date.getTime3();

					}

	    			String email = null;
	    			mts = new MessageToServer(Command.Read,TableName.orders,p1.getName());	
	    			mts.toAccept();	//
	    			mts = new MessageToServer(Command.Read,TableName.members,ChatClient.order.getPersonID());	
	    			mts.toAccept();	//
	    			if (ChatClient.objectType != ObjectKind.Member) {
	    			mts = new MessageToServer(Command.Read,TableName.visitors,ChatClient.order.getPersonID());	
	    			mts.toAccept();	//
	    			email = ChatClient.visitor.getMail();

	    			}
	    			else
	    			{
	    				
	    				email = ChatClient.member.getMail();
	    			} 
	    			String title = 	"Go-Nature System- Update for Order Number: "+o.getOrderNumber()+"";
	    			StringBuilder message = new StringBuilder();
	    			message.append("Hi There! \n");
	    			message.append("Unfortunately we didn't get from you a confirimation of your visit tomorrow  \n");
	    			message.append("Your order is cancelled \n");
	    			message.append("We are hoping to see you in our parks soon ! :-) \n");



	    		    try {
	    				mts.addMailInfo(email ,title ,message.toString());
	    			} catch (Exception e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    		    if (mailFlag)
	    		   mts.toAccept();	 

	    		
	    	} 
			
					
					//CHECK OTHER WAITING LIST REQUSET
		    		 mts = new MessageToServer();
					try {
						String s;
						 s = "SELECT orderNumber"
								+ " FROM waiting_lists"
								+ " WHERE orderNumber = (SELECT MIN(orderNumber) FROM waiting_lists"
								+ " WHERE ( parkName = 'val' AND visitTime = val1 AND visitDay = val2 AND visitMonth = val3"
								+ " AND visitYear = val4 AND numOfVisitors <= val5 ))";
						s=s.replaceFirst("val", o.getPark());
						s=s.replaceFirst("val1", o.getVisitTime()+"");
						s=s.replaceFirst("val2", o.getVisitDay()+"");
						s=s.replaceFirst("val3", o.getVisitMonth()+"");
						s=s.replaceFirst("val4", o.getVisitYear()+"");
						s=s.replaceFirst("val5", availableTickets+"");
						mts.addReportInfo(s);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mts.toAccept();	 
					//In Case There Is a client who is waiting to this park at this date & time
		    		
					if (!ChatClient.list.toString().contains("[]")) {
		    			String s = null;
		    			s= ChatClient.list.get(0);
		    			MessageToServer  m = new MessageToServer(Command.Update,TableName.orders,s);			
		    			m.addInfo("status", "WLupdate");
		    			m.toAccept();	 
		    		    m = new MessageToServer(Command.Delete,TableName.waiting_lists,s);	
		    			m.toAccept();
		    			
		    			
		    			String email = null;
		    			mts = new MessageToServer(Command.Read,TableName.orders,s);	
		    			mts.toAccept();	//
		    			mts = new MessageToServer(Command.Read,TableName.members,ChatClient.order.getPersonID());	
		    			mts.toAccept();	//
		    			if (ChatClient.objectType != ObjectKind.Member) {
		    			mts = new MessageToServer(Command.Read,TableName.visitors,ChatClient.order.getPersonID());	
		    			mts.toAccept();	//
		    			email = ChatClient.visitor.getMail();

		    			}
		    			else
		    			{
		    				
		    				email = ChatClient.member.getMail();
		    			} 
		    			String title = 	"Go-Nature System- Update for Order Number: "+s;
		    			StringBuilder message = new StringBuilder();
		    			message.append("Hi There! \n");
		    			message.append("After waiting a while...  \n");
		    			message.append("We are happy to tell you you can place your order \n");
		    			message.append("If you are still intrested - Please confirm your order at our system  \n \n");
		    			message.append("Have a nice day ! :-) \n");



		    		    try {
		    				mts.addMailInfo(email ,title ,message.toString());
		    			} catch (Exception e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			}
		    		    if (mailFlag)
		    		    mts.toAccept();	   		
		    	} 
			}	
		}
	}
	
	/**
	 * ask the DB for all orders for today which are still in the waiting list
	 * the function will ask by SQL command to change those orders status to 'Expired'
	 * the clients  will get notify by mail
	 */
	public void checkWaitingList()
	{
		String email = null;
		MessageToServer mts = new MessageToServer();
		try {
			mts.addReportInfo("SELECT orderNumber FROM waiting_lists WHERE ( visitDay = "+LocalDate.now().getDayOfMonth()
					+" AND visitMonth = "+LocalDate.now().getMonthValue()+" AND visitYear = "+LocalDate.now().getYear()+" )");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mts.toAccept();	//

	
		//create orders array from report
		 if (!ChatClient.list.toString().equals("[]")) {
		String orders [] = new String[ChatClient.list.size()];
		for(int i=0;i<orders.length;i++)
			orders[i]=new String(ChatClient.list.get(i));
		
		for (String or : orders) {
			MessageToServer m = new MessageToServer(Command.Delete,TableName.waiting_lists,or);	
			m.toAccept();
			mts = new MessageToServer(Command.Update,TableName.orders,or);	
			mts.addInfo("status", "Expired");
			mts.toAccept();	
			mts = new MessageToServer(Command.Read,TableName.orders,or);	
			mts.toAccept();	//
			mts = new MessageToServer(Command.Read,TableName.members,ChatClient.order.getPersonID());	
			mts.toAccept();	//
			if (ChatClient.objectType != ObjectKind.Member) {
			mts = new MessageToServer(Command.Read,TableName.visitors,ChatClient.order.getPersonID());	
			mts.toAccept();	//
			email = ChatClient.visitor.getMail();

			}
			else
			{
				
				email = ChatClient.member.getMail();
			} 
			String title = 	"Go-Nature System- Update for Order Number: "+or;
			StringBuilder message = new StringBuilder();
			message.append("Hi There! \n");
			message.append("Unfortunately your order expired  \n");
			message.append("The status has been changed at our system  \n");
			message.append("Hope to see you in one of ours parks ! :-) \n");
			


		    try {
				email = ChatClient.member.getMail();
				mts.addMailInfo(email,title ,message.toString());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if (mailFlag)
		    mts.toAccept();	
			}
		 }
	}
	
	/**
	 * ask the DB for all orders for tomorrow
	 * clients with order for tomorrow will get notify by mail and by pop up
	 */
	public void setTomorrowNoifications() {
		
		LocalDate tomorrow = LocalDate.now();
		tomorrow = tomorrow.plusDays(1);
		
		 String askSQL = "SELECT orderNumber FROM orders WHERE ( visitDay = "+tomorrow.getDayOfMonth()
					+" AND visitMonth = "+tomorrow.getMonthValue()+" AND visitYear = "+tomorrow.getYear()+" AND status = 'Ordered' )";
		System.out.println(askSQL);
		
		MessageToServer mts = new MessageToServer();
		try {
			mts.addReportInfo(askSQL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mts.toAccept();	//

	
		//create orders array from report
		if (ChatClient.list.toString().equals("[]"));
		else if (!ChatClient.list.toString().equals("[]")) {
		String orders [] = new String[ChatClient.list.size()];
		for(int i=0;i<orders.length;i++)
			orders[i]=new String(ChatClient.list.get(i));
		
		for (String or : orders) {
			String email =null;
			mts = new MessageToServer(Command.Update,TableName.orders,or);	
			mts.addInfo("status", "Tomorrow");
			mts.toAccept();	//
			
			mts = new MessageToServer(Command.Read,TableName.orders,or);	
			mts.toAccept();	//
			mts = new MessageToServer(Command.Read,TableName.members,ChatClient.order.getPersonID());	
			mts.toAccept();	//
			if (ChatClient.objectType != ObjectKind.Member) {
			mts = new MessageToServer(Command.Read,TableName.visitors,ChatClient.order.getPersonID());	
			mts.toAccept();	//
			email = ChatClient.visitor.getMail();

			}
			else
			{
				
				email = ChatClient.member.getMail();
			} 
			String title = 	"Go-Nature System- Update for Order Number: "+or;
			StringBuilder message = new StringBuilder();
			message.append("Hi There! \n");
			message.append("We hope you didn't forget your visit tomorrow \n");
			message.append("Please confirm your visit at our system  \n");
			message.append("Enjoy Your Visit ! :-) \n");



		    try {
				email = ChatClient.member.getMail();
				mts.addMailInfo(email,title ,message.toString());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if (mailFlag)
		   mts.toAccept();	
			}
		}
	}
	/**
	 * This function set discount for all parks every day according to department manager 
	 * @param todayDate
	 * @throws Exception
	 */
	public void setDiscountForToday(LocalDate todayDate) throws Exception {
		double discount=7;
		MessageToServer mts = new MessageToServer();
		mts.addReportInfo("SELECT * FROM parks");
		mts.toAcceptThread();				
		Park parks[] = new Park[CardReaderChat.list.size()];
	for(int j=0;j<parks.length;j++) 
			parks[j]=new Park(CardReaderChat.list.get(j));
		for(int j=0;j<parks.length;j++) {
			
			String parkName = parks[j].getName();
			
			 mts = new MessageToServer();
			mts.addReportInfo("SELECT * FROM requests WHERE parkName= " + "\"" + parkName
					+ "\" and type= \"UpdateDiscount\" and status= \"" + ReqStatus.Approved + "\";");
			mts.toAcceptThread(); //
			Request requests[] = new Request[CardReaderChat.list.size()];
			if (!CardReaderChat.list.toString().equals("[]")) 
			{
				for (int i = 0; i < requests.length; i++)
					requests[i] = new Request(CardReaderChat.list.get(i));

				for (int i = 0; i < requests.length; i++) 
				{
					LocalDate fromDate1 = LocalDate.parse(requests[i].getStartDate());
					LocalDate toDate1 = LocalDate.parse(requests[i].getEndDate());

					/* Compare the dates that park manger insert to the dates in the sql table */
					if ((todayDate.isBefore(toDate1) && todayDate.isAfter(fromDate1))||todayDate.equals(fromDate1) || todayDate.equals(toDate1))
						discount= Double.parseDouble(requests[i].getRequest());
					else
						discount=Prices.getDefultDiscount();
				}
			}
			else
				discount=Prices.getDefultDiscount();

			mts = new MessageToServer(Command.Update,TableName.parks,parkName);	
			mts.addInfo("discount", discount);
			mts.toAcceptThread();		
		}
		
	}
	
}
