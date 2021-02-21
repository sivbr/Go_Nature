package PEHome;

import java.net.URL;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.ResourceBundle;

import Enums.Command;
import Enums.Role;
import Enums.TableName;
import client.ChatClient;
import client.ClientUI;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Arc;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.MessageToServer;
import logic.Visitor;
import logic.Waiting_list;
import logic.Worker;
import Enums.Role;
import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
public class ParkEmployeeHomeController implements Initializable {
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
    private Button btnInDe=null;
	
	@FXML
	private Label lblWelcome = new Label();
	
	@FXML
	private Label lblPark= new Label();
	
	@FXML
	private Label lblTime= new Label();
	
	@FXML
	private Label lblTotalCapcity=new Label();
	
	@FXML
	private Arc arc=new Arc();
	private String parkName;
	//Instance methods ************************************************
	
	/**
	 * This method update the capacity in the park according to park name and present the capacity to park employee
	 * @param event
	 * @throws Exception
	 */
	private void setCapcity() {
		
			if(parkName.toString().equals("Sharon"))
			{
				MessageToServer mts = new MessageToServer(Command.Read,TableName.parks,"Sharon");
				mts.toAccept();
				arc.setLength((double)ChatClient.park.getCurrentAllVisitors()/(double)ChatClient.park.getMaxCapacity()*360);
				String s1=String.valueOf(ChatClient.park.getCurrentAllVisitors());
				System.out.println(s1.toString());
				String s2=String.valueOf(ChatClient.park.getMaxCapacity());
				lblTotalCapcity.setText( s1.toString() + "/ " + s2.toString());	
			}
			else if(parkName.toString().equals("Carmel"))
			{
				MessageToServer mts = new MessageToServer(Command.Read,TableName.parks,"Carmel");
				mts.toAccept();
				arc.setLength((double)ChatClient.park.getCurrentAllVisitors()/(double)ChatClient.park.getMaxCapacity()*360);
				String s1=String.valueOf(ChatClient.park.getCurrentAllVisitors());
				System.out.println(s1.toString());
				String s2=String.valueOf(ChatClient.park.getMaxCapacity());
				lblTotalCapcity.setText( s1.toString() + "/ " + s2.toString());	
			}
			else if(parkName.toString().equals("HaiPark"))
			{
				MessageToServer mts = new MessageToServer(Command.Read,TableName.parks,"HaiPark");
				mts.toAccept();
				arc.setLength((double)ChatClient.park.getCurrentAllVisitors()/(double)ChatClient.park.getMaxCapacity()*360);
				String s1=String.valueOf(ChatClient.park.getCurrentAllVisitors());
				System.out.println(s1.toString());
				String s2=String.valueOf(ChatClient.park.getMaxCapacity());
				lblTotalCapcity.setText( s1.toString() + "/ " + s2.toString());	
			} 
			
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
	 * This function show the card reader invoice and decline
	 * @param event
	 * @throws Exception
	 */
	public void getInDe(ActionEvent event) throws Exception {
		stage.OpenStage(event,"/CardReader/CardReader","Card Reader");		
	}
	
	
	/**
	 * This method refresh the capacity of in each park
	 * @param event
	 * @throws Exception
	 */
	public void setRef(ActionEvent event) throws Exception {
		try {
			setCapcity() ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		stage.setImage(images,"worker");
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());	
		lblPark.setText("Park Name: " + ChatClient.worker.getPark());
		stage.SetTime(lblTime);
		parkName=ChatClient.worker.getPark().toString();
		try {
			setCapcity() ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
