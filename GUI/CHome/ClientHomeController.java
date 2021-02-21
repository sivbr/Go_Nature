package CHome;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.*;
import java.util.Optional;
import java.util.ResourceBundle;

import Enums.Command;
import Enums.ObjectKind;
import Enums.TableName;
import LogIn.ClientLogInController;
import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.MessageToServer;
import logic.Order;
import logic.Park;
import logic.Possible_date;
import logic.Waiting_list;
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
public class ClientHomeController implements Initializable {
	//Class variables *************************************************		
	private Stages stage = new Stages();
	
    @FXML
    private ImageView images;// = new ImageView(image)
    
	@FXML
	private Button btnCreate = null;
	
	@FXML
	private Button btnFollow = null;
	
	@FXML
	private Button btnExit=null;
	
    @FXML
    private Button btnMinimize=null;

    @FXML
    private Button btnLogOff=null;
	
	@FXML
	private Label lblWelcome = new Label();
	
	@FXML
	private Label lblHeader= new Label();
	
	@FXML
	private Label lblTime= new Label();
	
	static boolean alertFlag = true;
	
	public static Order myHistory[] = null;
	
	public static boolean mailFlag=true;
	//Instance methods ************************************************
	
	/**
	 * check if the client has updates about his orders
	 * It may notify about order for tomorrow
	 *  It may notify about update from waiting list
	 */
		private void checkUpdates ()
		{
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
			myHistory = orders;
			for (Order or : myHistory) {
				if (or.getStatus().compareTo("WLupdate")==0)
				{
					ChatClient.order=or;
					if (alertWaitingListUpdate ()==false) {
				    mts = new MessageToServer(Command.Update,TableName.orders,String.valueOf(or.getOrderNumber()));	
					mts.addInfo("status", "NotifiedWL").addInfo("notificationHour", LocalTime.now().getHour())
					.addInfo("notificationMinute", LocalTime.now().getMinute());
					mts.toAccept();	
					}
				}
					 if (or.getStatus().compareTo("Tomorrow")==0) {
							ChatClient.order=or;
							if(alertTomorrow ()==false){
						    mts = new MessageToServer(Command.Update,TableName.orders,String.valueOf(or.getOrderNumber()));	
							mts.addInfo("status", "NotifiedTom").addInfo("notificationHour", LocalTime.now().getHour())
							.addInfo("notificationMinute", LocalTime.now().getMinute());
							mts.toAccept();	

					 }
				 }
			}
		}
	}
		
		
	
	
	public void getExit(ActionEvent event) throws Exception {
		stage.outFromSystem(event,"Exit");		
	}
	
	public void getLogOff(ActionEvent event) throws Exception {
		stage.outFromSystem(event,"LogOff");		
	}

	public void getMinimize(ActionEvent event) throws Exception {
		stage.Minimize(event);		
	}
	
	/**
	 * In case client chose to create an order 
	 * @param event
	 * @throws Exception
	 */
	public void btnCreateNewOrder(ActionEvent event) throws Exception 
	{
		if(ClientLogInController.objectType == ObjectKind.Member ||
				ClientLogInController.objectType == ObjectKind.Visitor)
			stage.OpenStage(event, "/CNewOrder/NewOrder","Create New Order");
		else
			stage.OpenStage(event, "/VRegister/VisitorDetails","Visitor Details");
	}
	
	/**
	 * In case client chose to track his previous orders
	 * @param event
	 * @throws Exception
	 */
	public void btnFollowOrders(ActionEvent event) throws Exception 
	{
		stage.OpenStage(event, "/CTrackingOrders/TrackingOrders","Tracking Orders");
	}
	
	
	/**
	 * showing pop up to clients with order which stayed in the waiting list
	 * and now they can place an order (need to confirm in one hour)
	 * 
	 * 
	 */
	public boolean alertWaitingListUpdate () {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("You've got an waiting list update!");
		alert.setHeaderText("We are happy to tell you that you can place your order: \n"
				+ "For : "+ ChatClient.order.getPark()+ " At " +ChatClient.order.getVisitDay() + "/" +ChatClient.order.getVisitMonth()+ "/" +ChatClient.order.getVisitYear() );
		alert.setContentText("You can confirm it now or in one hour");

		ButtonType buttonTypeConfirm = new ButtonType("Confirm");
		ButtonType buttonTypeCancel = new ButtonType("Later", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll( buttonTypeConfirm, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		
		   
		 if (result.get() == buttonTypeConfirm) { //in case the client still want this order
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

     		   return true;
		} else {
		    return false;// ... user chose CANCEL or closed the dialog
		}
	
	}
	
	/**
	 * showing pop up to clients with order for tomorrow (he need to confirm in two hours)
	 * 
	 */
	public boolean alertTomorrow ()
	{
		
		
			Alert aler2 = new Alert(AlertType.CONFIRMATION);
			aler2.setTitle("Your visit is tomorrow");
			aler2.setHeaderText("Hi there ! your visit is tomorrow "+"\n"
					+ "Are you comming For : "+ ChatClient.order.getPark()+ " At " +ChatClient.order.getVisitDay() + "/" +ChatClient.order.getVisitMonth()+ "/" +ChatClient.order.getVisitYear() + "?" );
			aler2.setContentText("You can confirm it now or in two hour");

			ButtonType buttonTypeConfirm = new ButtonType("Confirm Visit");
			ButtonType buttonTypeCancel = new ButtonType("Later", ButtonData.CANCEL_CLOSE);

			aler2.getButtonTypes().setAll( buttonTypeConfirm, buttonTypeCancel);

			Optional<ButtonType> result = aler2.showAndWait();
			
			   
			 if (result.get() == buttonTypeConfirm) { // in case the user confirm the visit tomorrow
			   MessageToServer mts = new MessageToServer(Command.Update,TableName.orders,String.valueOf(ChatClient.order.getOrderNumber()));	
      		   mts.addInfo("status", "VisitConfirmed");
      		   mts.toAccept();
      		   return true;
			} else { 
			   return false; // ... user chose CANCEL or closed the dialog
			}
	}
	

	
	 /**
	 * initialize stage
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub`
		stage.setImage(images,"ClientDetails");
		stage.SetTime(lblTime);

		if(ClientLogInController.objectType==ObjectKind.Member || ClientLogInController.objectType==ObjectKind.Visitor)
		checkUpdates ();
		else
			myHistory=null;

	
		
		if(ClientLogInController.objectType==ObjectKind.Member) {
			lblHeader.setText("Welcome, " + ChatClient.member.getfirstName());
			lblWelcome.setText("Hi, " + ChatClient.member.getfirstName() + " " +ChatClient.member.getlastName());
		}
		else
		{
			if(ClientLogInController.objectType==ObjectKind.Visitor) {
				lblHeader.setText("Welcome, " + ChatClient.visitor.getfirstName());
				lblWelcome.setText("Hi, " + ChatClient.visitor.getfirstName() + " " +ChatClient.visitor.getlastName());
			}
			else {
				lblHeader.setText("Welcome, Traveler");
				lblWelcome.setText("Hi, Guest");
				btnFollow.setVisible(false);
			}
			
		}	
	}
	
}
