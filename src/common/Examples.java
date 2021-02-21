package common;


public class Examples {

	//If you have more then 1 command you can use the same mts (like mts=) and not create a new one.
	
	
	/*
	 * *****Read*****
	Park p1;
	MessageToServer mts = new MessageToServer(Command.Read,TableName.visitors,"Carmel");
	mts.toAccept();
	p1 = new Park(ChatClient.park);
	*/
	
	
	/*
	 * *****Read Last Record*****
	  	MessageToServer mts = new MessageToServer(Command.ReadLastRecord,TableName.orders,"null");	
		mts.toAccept();
	 *  
	 */
	
	
	/*
	 * *****Add*****
	MessageToServer mts = new MessageToServer(Command.Add,TableName.visitors,"NULL");	
	mts.addObject((new Visitor("test45654","test2","test3","test4","test5",1)));
	mts.toAccept();
	
	*/
	
	
	/*
	 * *****Update*****  
	 //"222" - is the pk you want to change
	MessageToServer  mts = new MessageToServer(Command.Update,TableName.visitors,"222");	
	
	//you can change all fields by this command
	mts.editObject((new Visitor("id","firstName","fgh","test4","test5",0)));
	mts.toAccept();	
		
		
	//or one field each time		
	mts.addInfo("firstName", "lala");
	mts.addInfo("lastName", "land");
	mts.addInfo("mail", "lala-land@gmasil");
	mts.addInfo("online", "0");
	mts.toAccept();
	
	*/
	
	
	/*
	* *****Delete*****   
	MessageToServer mts = new MessageToServer(Command.Delete,TableName.visitors,"test45654");	
	mts.toAccept();;
	*/
	
	
	
	/*
	 * *****Report***** 
	 *
			MessageToServer mts = new MessageToServer();
			mts.addReportInfo("SELECT * FROM orders");
			mts.toAccept();	
					
			//ChatClient.list- list of strings from report command		
				
			//create orders array from report		
			Order orders[] = new Order[ChatClient.list.size()];
			for(int i=0;i<orders.length;i++)
				orders[i]=new Order(ChatClient.list.get(i));
	 */
	
	
	
	/*
	 * *****Send Mail***** 
	 *
	MessageToServer mts = new MessageToServer();
	mts.addMailInfo("liorsaghi@gmail.com", "subjectTest", "test msg");
	mts.toAccept();	
	
	*/
}
