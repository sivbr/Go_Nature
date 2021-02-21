package CTrackingOrders;

import java.awt.Insets;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import CHome.ClientHomeController;

//import com.sun.tools.javac.util.List;

import Enums.Command;
import Enums.ObjectKind;
import Enums.TableName;
import LogIn.ClientLogInController;
import client.ChatClient;
import client.ClientUI;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import logic.MessageToServer;
import logic.Order;
import logic.Park;
import logic.Possible_date;
import StagesFunc.Stages;
/** 
* @author Tomer Dabun
* @author Lior Saghi
* @author Shay Feld
* @author Sivan Brecher
* @author Sapir Baron
* @author Coral Harel
* @version December 2020
*/
public class ClientTrackingOrders implements Initializable {
	//Class variables *************************************************		
	private Stages stage = new Stages();
		
    @FXML
    private ImageView images;// = new ImageView(image)
    
	@FXML
	private Button btnExit=null;
	
    @FXML
    private Button btnMinimize=null;

    @FXML
    private Button btnLogOff=null;
	

	@FXML
	private Button btnHome=null;
	
	@FXML
	private Label lblWelcome = new Label();

	@FXML
	private Label lblTime= new Label();
	
	
	
	ObservableList<String> list ;
	@FXML
    ListView<String> lv = new ListView<>(list);
	
	/**
	 * Array which contains all previous orders of the client
	 */
	private String chosenOrder;
	
	/**
	 * Array which contains all previous orders of the client
	 */
	public static Order myHistory[] = null;
	
	//Instance methods ************************************************
	
	/**
	 * Creating an array of all the client previous orders 
	 * like "History" - in order to check it status and edit.
	 */
    private void setDetails() {
    	String valID = null;

		if(ClientLogInController.objectType==ObjectKind.Member) 
			valID = ChatClient.member.getID();
			else if(ClientLogInController.objectType==ObjectKind.Visitor)
				valID = ChatClient.visitor.getID();
					

		MessageToServer mts = new MessageToServer();
		try {
			mts.addReportInfo("SELECT * FROM orders WHERE personID = '"+valID+"'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mts.toAccept();	//

		//create orders array from report
		if (ChatClient.list.toString().equals("[]"))
			myHistory=null;
		else if (!ChatClient.list.toString().equals("[]")) {
		Order orders [] = new Order[ChatClient.list.size()];
		for(int i=0;i<orders.length;i++)
			orders[i]=new Order(ChatClient.list.get(i));
		myHistory = orders;		ArrayList<String> al = new ArrayList<String>();	
	
		
		for (Order order : myHistory) {
			al.add(order.stringToTrack());
		}

		list = FXCollections.observableArrayList(al);
		lv.setItems(list);
		 lv.setCellFactory(lv -> {
	            ListCell<String> cell = new ListCell<String>() {
	                @Override
	                protected void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    setText(item);
	                }
	            };
	            cell.setOnMouseClicked(e -> {
	                if (!cell.isEmpty()) {
	                	chosenOrder = lv.getSelectionModel().getSelectedItem();
	                	for (Order order : myHistory) {
	            			if(order.stringToTrack().compareTo(chosenOrder)==0) {
	            				ChatClient.order = order;
	            			    break;
	            			}

	                	}
	                	if (chosenOrder.contains("Update"))
	                		editWLupdate ();
	                	else if (chosenOrder.contains("Ordered")||chosenOrder.contains("Waiting List"))
	                		cancelOrder ();
	                	else if (chosenOrder.contains("tomorrow"))
	                			{
	                		editTomorrowCase ();
	                		
	                			}
	                	e.consume();
	                }
	            });
	            return cell;
		  });
		}
	}
    
    /**
     * In Case the client chose to cancel is order
     * his order will cancelled , if the order stayed at waiting list it will be deleted from the list
     * the function also sending update mail of the order status and checks 
     * if there is order in the waiting list that can place an order
     */
    
    public void cancelOrder ()
    {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Order Canceling ");
    	alert.setContentText("Would you like to cancel this order?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		if (chosenOrder.contains("Waiting List")&&(!chosenOrder.contains("Update")))
    		{
        		System.out.println("delete from waiting list order num :"+ ChatClient.order.getOrderNumber());
    			MessageToServer mts = new MessageToServer(Command.Delete,TableName.waiting_lists,String.valueOf(ChatClient.order.getOrderNumber()));	
    			mts.toAccept(); 
    		}
    		
    		else
    		{
    			String day = ChatClient.order.getVisitDay()< 10 ? "0"+ ChatClient.order.getVisitDay()+"" : ChatClient.order.getVisitDay()+"";
    			String month = ChatClient.order.getVisitMonth() < 10 ? "0"+ChatClient.order.getVisitMonth()+"" :ChatClient.order.getVisitMonth()+"";
    			String s = ChatClient.order.getVisitYear()+"-"+month	+"-"+day+"_"+ChatClient.order.getPark();
    			String date = ChatClient.order.getVisitYear()+""+"-"+month	+"-"+day;
    			MessageToServer mcs = new MessageToServer(Command.Read,TableName.Possible_dates,s);
    			mcs.toAccept();
    			
    			mcs = new MessageToServer(Command.Update,TableName.Possible_dates,s);	

				 int t =ChatClient.order.getVisitTime();
					switch(t)
				    	{
				    	case 8:{
							mcs.addInfo("time1", ChatClient.possible_date.getTime1()-ChatClient.order.getNumOfVisitors());
				    		break;
				    	}
				    	case 12:{	
				    		mcs.addInfo("time2", ChatClient.possible_date.getTime2()-ChatClient.order.getNumOfVisitors());
				    		break;
				    	}
				    	case 16:
				    	{
				    		mcs.addInfo("time3", ChatClient.possible_date.getTime3()-ChatClient.order.getNumOfVisitors());
				    		break;
				    	}
				    	
			} 
					mcs.toAccept();
					 mcs = new MessageToServer(Command.Read,TableName.Possible_dates,s);
	    			mcs.toAccept();
					
					if (ChatClient.possible_date.getTime1()==0 && ChatClient.possible_date.getTime2()==0&&ChatClient.possible_date.getTime3()==0)
					{
						 mcs = new MessageToServer(Command.Delete,TableName.Possible_dates,s);	
			    		 mcs.toAccept();
						
					}

    		}
    		MessageToServer mts = new MessageToServer(Command.Update,TableName.orders,String.valueOf(ChatClient.order.getOrderNumber()));	
    		mts.addInfo("status", "Cancelled");
    		mts.toAccept();    
    		
    		String email = null;
    		mts = new MessageToServer(Command.Read,TableName.orders,String.valueOf(ChatClient.order.getOrderNumber()));	
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
			String title = 	"Go-Nature System- Update for Order Number: "+ChatClient.order.getOrderNumber();
			StringBuilder message = new StringBuilder();
			message.append("Hi There! \n");
			message.append("This is a cancellation message for this order, "+"\n");
			message.append("As you requested. \n \n");
			message.append("We are hoping to see you in our parks soon ! :-) \n");



		    try {
				mts.addMailInfo(email ,title ,message.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if (ClientHomeController.mailFlag)
		    mts.toAccept();	 

		
	} 
    		//CHECK OTHER WAITING LIST REQUSET
    		Order o = ChatClient.order;
        	System.out.println("ordrrdor : "+ o.toString());

    		double availableTickets = 0;
			Park p1;
			MessageToServer mts = new MessageToServer(Command.Read,TableName.parks,o.getPark());
			mts.toAccept();
			p1 = new Park(ChatClient.park);
			String day = ChatClient.order.getVisitDay()< 10 ? "0"+ ChatClient.order.getVisitDay()+"" : ChatClient.order.getVisitDay()+"";
			String month = ChatClient.order.getVisitMonth() < 10 ? "0"+ChatClient.order.getVisitMonth()+"" :ChatClient.order.getVisitMonth()+"";
			String sdp = o.getVisitYear()+"-"+month+"-"+day+"_"+o.getPark();
			mts = new MessageToServer(Command.Read,TableName.Possible_dates,sdp);
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
    		MessageToServer mcs = new MessageToServer();
			try {
				String s = "SELECT orderNumber"
						+ " FROM waiting_lists"
						+ " WHERE orderNumber = (SELECT MIN(orderNumber) FROM waiting_lists"
						+ " WHERE ( parkName = 'val' AND visitTime = val1 AND visitDay = val2 AND visitMonth = val3"
						+ " AND visitYear = val4 AND numOfVisitors <= val5 ))";
				s=s.replaceFirst("val", ChatClient.order.getPark());
				s=s.replaceFirst("val1", ChatClient.order.getVisitTime()+"");
				s=s.replaceFirst("val2", ChatClient.order.getVisitDay()+"");
				s=s.replaceFirst("val3", ChatClient.order.getVisitMonth()+"");
				s=s.replaceFirst("val4", ChatClient.order.getVisitYear()+"");
				s=s.replaceFirst("val5", availableTickets+"");
				System.out.println(s);
				mcs.addReportInfo(s);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mcs.toAccept();	 
			
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
    		    if (ClientHomeController.mailFlag)
    		    mts.toAccept();	 

    		
    	} 
    	setDetails();   // Refreshing the ViewList

    	}
    
    /**
     * In case the client chose to edit an order for tomorrow 
     * he can approve he'll come or cancel it.
     */
    public void editTomorrowCase ()
    {
    	ArrayList<String> choices = new ArrayList<String>();
    	if(chosenOrder.contains("WaitingList"));
    	choices.add("confirm my visit");
    	choices.add("cancel my order");

    	ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
    	dialog.setTitle("Choice Dialog");
    	dialog.setHeaderText("What would you like to do?");
    	dialog.setContentText("I want to: ");

    	// Traditional way to get the response value.
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	    if(result.get().compareTo("confirm my visit")==0)
    	    {
    	    	  MessageToServer mts = new MessageToServer(Command.Update,TableName.orders,String.valueOf(ChatClient.order.getOrderNumber()));	
        		   mts.addInfo("status", "VisitConfirmed");
        		   mts.toAccept(); 
    	    	setDetails();

    	    }
    	    else
     		   cancelOrder();
    	    
    	}

    	
    }
    /**
     * In case the client chose to edit an order which pop out from waiting list 
     * he can approve he'll come or cancel it.
     */
    public void editWLupdate ()
    {
    	ArrayList<String> choices = new ArrayList<String>();
    	choices.add("approve my order");
    	choices.add("cancel my order");

    	ChoiceDialog<String> dialog = new ChoiceDialog<>("approve my order", choices);
    	dialog.setTitle("Choice Dialog");
    	dialog.setHeaderText("What would you like to do?");
    	dialog.setContentText("I want to : ");

    	// Traditional way to get the response value.
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	   if (result.get().compareTo("approve my order")==0) {
    		  MessageToServer mts = new MessageToServer(Command.Update,TableName.orders,String.valueOf(ChatClient.order.getOrderNumber()));	
       		   mts.addInfo("status", "Ordered");
       		   mts.toAccept();
       		 // Updade Capacity of this park at this time + date
    			MessageToServer mcs;
       		String day = ChatClient.order.getVisitDay()< 10 ? "0"+ ChatClient.order.getVisitDay()+"" : ChatClient.order.getVisitDay()+"";
 			String month = ChatClient.order.getVisitMonth() < 10 ? "0"+ChatClient.order.getVisitMonth()+"" :ChatClient.order.getVisitMonth()+"";
 			String s = ChatClient.order.getVisitYear()+"-"+month	+"-"+day+"_"+ChatClient.order.getPark();
 			String date = ChatClient.order.getVisitYear()+""+"-"+month	+"-"+day;


 			mcs = new MessageToServer(Command.Read,TableName.Possible_dates,s);
 			mcs.toAccept();
 			 if (ChatClient.objectType!=ObjectKind.Possible_date)
 			{

 				mcs = new MessageToServer(Command.Add,TableName.Possible_dates,"NULL");	
 				 int t =ChatClient.order.getVisitTime();
 					switch(t)
 				    	{
 				    	case 8:{
 							try {
								mcs.addObject((new Possible_date(s,date,ChatClient.order.getPark(),ChatClient.order.getNumOfVisitors(),0,0,0,1)));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
 				    		break;
 				    	}
 				    	case 12:{	
 							try {
								mcs.addObject((new Possible_date(s,date,ChatClient.order.getPark(),0,ChatClient.order.getNumOfVisitors(),0,0,1)));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
 				    		break;
 				    	}
 				    	case 16:
 				    	{
 							try {
								mcs.addObject((new Possible_date(s,date,ChatClient.order.getPark(),0,0,ChatClient.order.getNumOfVisitors(),0,1)));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
 				    		break;
 				    	}
 				    
 				    	}
 			}
 			
 			else
 		

 					{
 						mcs = new MessageToServer(Command.Update,TableName.Possible_dates,s);	

 						 int t =ChatClient.order.getVisitTime();
 							switch(t)
 						    	{
 						    	case 8:{
 									mcs.addInfo("time1", ChatClient.order.getNumOfVisitors()+ChatClient.possible_date.getTime1());
 						    		break;
 						    	}
 						    	case 12:{	
 						    		mcs.addInfo("time2", ChatClient.order.getNumOfVisitors()+ChatClient.possible_date.getTime2());
 						    		break;
 						    	}
 						    	case 16:
 						    	{
 						    		mcs.addInfo("time3", ChatClient.order.getNumOfVisitors()+ChatClient.possible_date.getTime3());
 						    		break;
 						    	}
 						    	
 					} 
 				
 				
 				
 			} 
 			mcs.toAccept();

    	       setDetails();
    	   }
    	   else
    		   cancelOrder();
    	}
    }

    
    
/**
 * event functions for screen buttons
 * @param event
 * @throws Exception
 */
    /*   event functions for screen buttons    */
	public void getExit(ActionEvent event) throws Exception {
		stage.outFromSystem(event,"Exit");		
	}
	
	public void getLogOff(ActionEvent event) throws Exception {
		stage.outFromSystem(event,"LogOff");		
	}

	public void getMinimize(ActionEvent event) throws Exception {
		stage.Minimize(event);		
	}

	
	public void getHome(ActionEvent event) throws Exception {
		stage.GoHome(event);
    }
	
	/**
	 * initialize 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		stage.setImage(images,"Client");
		stage.SetTime(lblTime);
		if(ClientLogInController.objectType==ObjectKind.Member) {
			lblWelcome.setText("Welcome, " + ChatClient.member.getfirstName() + " " +ChatClient.member.getlastName());
		}
		else
		{
			if(ClientLogInController.objectType==ObjectKind.Visitor) {
				lblWelcome.setText("Welcome, " + ChatClient.visitor.getfirstName() + " " +ChatClient.visitor.getlastName());
			}
			else {
				lblWelcome.setText("Welcome, Guest");
			}
			
		

	}
		

	if (ClientHomeController.myHistory!=null)
		setDetails();

	}
}
