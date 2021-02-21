package CApproveOrder;

import java.net.URL;

import java.util.ResourceBundle;

import Enums.ObjectKind;
import LogIn.ClientLogInController;
import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
public class ClientApproveOrder implements Initializable {
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
    private Label OrderNumber;
    
    @FXML
    private Label Date;
    
    @FXML
    private Label NumOfVisitors;
	
    @FXML
    private Label VisitorName;
    
    @FXML
    private Label dateOfToday;
	

	
	//Instance methods ************************************************
	
    /**
     * setting data at the labels of the controller
     *
     */
    private void loadOrder() {
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
    		
    	
    	OrderNumber.setText("Order number: "+ChatClient.order.getOrderNumber());
        Date.setText("Date: " + ChatClient.order.getVisitDay() + "/" +ChatClient.order.getVisitMonth()+ "/" +ChatClient.order.getVisitYear() +" "+"on " + "Time: "+time);
        NumOfVisitors.setText("Number Of Visitors: "+ ChatClient.order.getNumOfVisitors());
        
        }
    
	/**
	 * This function exit the user from the program.
	 * 
	 * @param event
	 * @throws Exception
	 * @see StagesFunc.Stages#outFromSystem(ActionEvent event,String action)
	 */
	public void getExit(ActionEvent event) throws Exception {
		stage.outFromSystem(event,"Exit");		
	}
	
	/**
	 * This function log out the user from the program. 
	 * @param event
	 * @throws Exception
	 * @see StagesFunc.Stages#outFromSystem(ActionEvent event,String action)
	 */
	public void getLogOff(ActionEvent event) throws Exception {
		stage.outFromSystem(event,"LogOff");		
	}

	/**
	 * This function minimize the user from the screen.
	 * @param event
	 * @throws Exception
	 * @see StagesFunc.Stages#Minimize(ActionEvent event)
	 */
	public void getMinimize(ActionEvent event) throws Exception {
		stage.Minimize(event);		
	}

	/**
	 * This function return the user to his home screen.
	 * @param event
	 * @throws Exception
	 * @see StagesFunc.Stages#GoHome(ActionEvent event)
	 */
	public void getHome(ActionEvent event) throws Exception 
	{
		stage.GoHome(event);
	}
	
	   /**
			 * initialize stage
			 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		stage.setImage(images,"Client");
		loadOrder();
		stage.SetTime(dateOfToday);
		if(ClientLogInController.objectType==ObjectKind.Member) {
			VisitorName.setText("Welcome, " + ChatClient.member.getfirstName() + " " +ChatClient.member.getlastName());
		}
		else
		{
			if(ClientLogInController.objectType==ObjectKind.Visitor) {
				VisitorName.setText("Welcome, " + ChatClient.visitor.getfirstName() + " " +ChatClient.visitor.getlastName());
			}
			else {
				VisitorName.setText("Welcome, Guest");
			}
	}}
	
}
