package COrderDetails;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import CHome.ClientHomeController;
import Enums.Command;
import Enums.ObjectKind;
import Enums.TableName;
import LogIn.ClientLogInController;
import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Member;
import logic.Prices;
import logic.MessageToServer;
import logic.Order;
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
public class ClientOrderDetails implements Initializable {
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
    private Button btnAccept=null;

    @FXML
    private Button btnEdit=null;
    
    @FXML
    private Label CostumerDetails;
    
    @FXML
    private Label ParkName;
    
    @FXML
    private Label Date;
    
    @FXML
    private Label Time;
    
    @FXML
    private Label NumOfVisitors;
    
    @FXML
    private Label Email;
    
    @FXML
    private Label OriginalPrice;
    
    @FXML
    private Label Discount;
    
    @FXML
    private Label TotalPrice;
    
    @FXML
    private Label VisitorName;
    
    @FXML
    private Label dateOfToday;
    
    @FXML
    private Label lblTime;
    

    
    private double ticketPrice = Prices.getTicketPrice();
    
    private StringBuilder mailInvoice= new StringBuilder();

	private Alert editAlert = new Alert(AlertType.CONFIRMATION);
	
	public static ObjectKind objectType = ObjectKind.Person;
	//Instance methods ************************************************
    /**
     * setting data at the labels of the controller
     *
     */
    private void loadOrder() {
    String mail = null;
    String name = null;
    String time = null;
    int t =ChatClient.order.getVisitTime();
	switch(t)
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
    		
    	if (ClientLogInController.objectType==ObjectKind.Member) {
        	CostumerDetails.setText("Costumer Details: " + ChatClient.member.getfirstName()+ " "+ChatClient.member.getlastName());
			VisitorName.setText("Welcome, " + ChatClient.member.getfirstName()+ " "+ChatClient.member.getlastName());
	    	Email.setText("Email: " + ChatClient.member.getMail());
	    	name = ChatClient.member.getfirstName()+ " "+ChatClient.member.getlastName();
	    	mail = ChatClient.member.getMail();

    	}
    	else if (ClientLogInController.objectType==ObjectKind.Visitor) {
        	CostumerDetails.setText("Costumer Details: " + ChatClient.visitor.getfirstName()+ " "+ChatClient.visitor.getlastName());
			VisitorName.setText("Welcome, " + ChatClient.visitor.getfirstName()+ " "+ChatClient.visitor.getlastName());
	    	Email.setText("Email: " + ChatClient.visitor.getMail());
	    	name = ChatClient.visitor.getfirstName()+ " "+ChatClient.visitor.getlastName();
	        mail = ChatClient.visitor.getMail();

    	}
    	ParkName.setText("Park Name: " + ChatClient.order.getPark() );
    	Date.setText("Date : " + ChatClient.order.getVisitDay() + "/" +ChatClient.order.getVisitMonth()+ "/" +ChatClient.order.getVisitYear());
    	Time.setText("Hour : "+ time);
    	NumOfVisitors.setText("Number Of Visitors: " + ChatClient.order.getNumOfVisitors());
    	OriginalPrice.setText("Original Price: " + ChatClient.order.getNumOfVisitors()*Prices.getTicketPrice() +" (Each Ticket : "+Prices.getTicketPrice()+")" ) ;
    	Discount.setText("Discount: "+getDiscountInfo());
    	double totalPrice = ChatClient.order.getNumOfVisitors()*ticketPrice;
    	String price = String.format("%.2f", totalPrice) ;
    	TotalPrice.setText("Total Price: "+ price);
    	 
    	//Building Invoices details (Will send to the client mail)
    	
    	mailInvoice.append("Dear,"+name + "\n \n" +"Your Order To The Park "+ ChatClient.order.getPark() + " At " + ChatClient.order.getVisitDay() + "/" +ChatClient.order.getVisitMonth()+ "/" +ChatClient.order.getVisitYear() +" For : "+ time);
    	mailInvoice.append("\n"+"Has Been Placed" );
    	mailInvoice.append("\n"+"Number Of Visitors: " + ChatClient.order.getNumOfVisitors() );
    	mailInvoice.append("\n"+"Original Price: " + ChatClient.order.getNumOfVisitors()*Prices.getTicketPrice() +" (Each Ticket : "+Prices.getTicketPrice()+")" );
    	mailInvoice.append("\n"+"Discount: "+getDiscountInfo());
    	mailInvoice.append("\n"+"Total Price: "+ price );
    	mailInvoice.append("\n \n"+"Enjoy Your Visit !" );
    	mailInvoice.append("\n " + "Thank you." +"");



    }
    
    /**
     * Creating a string to show the client discounts
     * @return
     */
    public StringBuilder getDiscountInfo ()
    {
    	StringBuilder discounts = new StringBuilder();
    	Order o =ChatClient.order;
    	if(ClientLogInController.objectType==ObjectKind.Member) {
    	if (ChatClient.member!=null)
    	{
    		
    		if (o.getOrganizationGroup()==1) {{
    			discounts.append("Orderd Organized Group "+"("+String.valueOf((100-Prices.getOrderedGroupVisitDiscount()*100))+"%) ");
    			ticketPrice *=Prices.getOrderedGroupVisitDiscount();
    		}
    			if(ChatClient.order.getPaid()==1) {
    				discounts.append("+ Advance payment "+"("+String.valueOf((100-(Prices.getAdvancePaymentDiscount()*100))+"%) "));
    				ticketPrice *= Prices.getAdvancePaymentDiscount();
    		} }
    		else {
    			discounts.append("Orderd visit "+"("+ String.valueOf((100-(Prices.getOrderedPersonalVisitDiscount()*100))+"%) "
    					+ "+ Members " + "("+String.valueOf((100-(Prices.getMemberDiscount()*100))+"%) ")));
    			ticketPrice *= Prices.getOrderedPersonalVisitDiscount();
    			ticketPrice *= Prices.getMemberDiscount();
 
    		}
    	}
    	}
    	else {
			discounts.append("Orderd visit "+"("+ String.valueOf((100-(Prices.getOrderedPersonalVisitDiscount()*100))+"%) "));
		ticketPrice *= Prices.getOrderedPersonalVisitDiscount();
    	}
    	MessageToServer mts = new MessageToServer(Command.Read,TableName.parks,ChatClient.order.getPark());
    	mts.toAccept();
    	if(ChatClient.park.getDiscount()>0)
    	{
    		discounts.append("+ Special Park Discount : "+"("+ String.valueOf(ChatClient.park.getDiscount())
    		+"%) ");
    		ticketPrice *= (1-(ChatClient.park.getDiscount()/100));
    	}
	
		return discounts;

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

	
	public void getHome(ActionEvent event) throws Exception 
	{
		objectType = ObjectKind.Person;
		stage.GoHome(event);
	}
	
	 /**
	  * In case the client approve his order details - 
	  * saving details to DB and adding counters of the  park amount at 'possible_date' table
	  * @param event
	  * @throws Exception
	  */
	public void getAccept(ActionEvent event) throws Exception {
		ChatClient.order.setTotalPayment(ChatClient.order.getNumOfVisitors()*ticketPrice);
		MessageToServer mcs;
		mcs = new MessageToServer(Command.Add,TableName.orders,"NULL");
		mcs.addObject(ChatClient.order);
		mcs.toAccept(); 
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
						mcs.addObject((new Possible_date(s,date,ChatClient.order.getPark(),ChatClient.order.getNumOfVisitors(),0,0,0,1)));
			    		break;
			    	}
			    	case 12:{	
						mcs.addObject((new Possible_date(s,date,ChatClient.order.getPark(),0,ChatClient.order.getNumOfVisitors(),0,0,1)));
			    		break;
			    	}
			    	case 16:
			    	{
						mcs.addObject((new Possible_date(s,date,ChatClient.order.getPark(),0,0,ChatClient.order.getNumOfVisitors(),0,1)));
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
		
		String email = null;
		mcs = new MessageToServer(Command.Read,TableName.orders,ChatClient.order.getPark());	
		mcs.toAccept();	//
		mcs = new MessageToServer(Command.Read,TableName.members,ChatClient.order.getPersonID());	
		mcs.toAccept();	//
		if (ChatClient.objectType != ObjectKind.Member) {
		mcs = new MessageToServer(Command.Read,TableName.visitors,ChatClient.order.getPersonID());	
		mcs.toAccept();	//
		email = ChatClient.visitor.getMail();

		}
		else
		{
			
			email = ChatClient.member.getMail();
		} 
	    if (ClientHomeController.mailFlag) {
			mcs = new MessageToServer();
			mcs.addMailInfo(email, String.valueOf("Go-Nature System- Order Number: "+ChatClient.order.getOrderNumber()),mailInvoice.toString());
		    mcs.toAccept();	
	    }

		
		objectType = ObjectKind.Person;
		
		stage.OpenStage(event, "/CApproveOrder/ApproveOrder","Approve Order");
	}

	

	/**
	 * In case the user wants to edit his 'new order details' 
	 */
	public void getEdit(ActionEvent event) throws Exception {
		editAlert.setTitle("Edit your order");
		editAlert.setHeaderText("Are you sure you want to edit your order?");
		Optional<ButtonType> result = editAlert.showAndWait();

		if(result.isPresent() && result.get() == ButtonType.OK) {
			if(ClientLogInController.objectType==ObjectKind.Member) 
				objectType = ObjectKind.Member;
			else
				objectType = ObjectKind.Visitor;
			stage.OpenStage(event, "/CNewOrder/NewOrder","Edit Order");
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		stage.setImage(images,"Client");
		stage.SetTime(lblTime);
    	if (ClientLogInController.objectType==ObjectKind.Member)
			VisitorName.setText("Welcome, " + ChatClient.member.getfirstName()+ " "+ChatClient.member.getlastName());
    	else if (ClientLogInController.objectType==ObjectKind.Visitor) 
			VisitorName.setText("Welcome, " + ChatClient.visitor.getfirstName()+ " "+ChatClient.visitor.getlastName());
		loadOrder();	

	}
	
}
