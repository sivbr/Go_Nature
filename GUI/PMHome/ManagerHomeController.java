package PMHome;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Enums.Command;
import Enums.ObjectKind;
import Enums.TableName;
import PMCreateReport.CreateReportParkManeger;
import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Arc;
import logic.MessageToServer;
import logic.Visitor;
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
public class ManagerHomeController implements Initializable {
	//Class variables *************************************************		
	private Stages stage = new Stages();
	//private static int count=0;
		
    @FXML
    private ImageView images;// = new ImageView(image)
    
	@FXML
	private Button btnExit=null;
	
    @FXML
    private Button btnMinimize=null;

    @FXML
    private Button btnLogOff=null;
	
	@FXML
	private Button btnCapacity;

	@FXML
	private Button btnRegister;
	
	@FXML
	private Button btnRefresh;

	@FXML
	private Button btnReport;
	
	@FXML
	private Button btnCheckReports;
	
	@FXML
	private Button btnCheckRequsts;
	
    @FXML
    private Button btnInDe=null;
	
	@FXML
	private Label lblWelcome= new Label();
	
	@FXML
	private Label lblTime= new Label();
	
	@FXML
	private Label lblParkName= new Label();
	
	@FXML
	private Label CurrentLabel2= new Label();
	
	@FXML
	private TextArea textAreaWaiting = new TextArea();
	
	@FXML
	private TextArea textAreaExa = new TextArea();
	
	@FXML
	private Label lblTotalCapcity=new Label();
	
	@FXML
	private Arc arc=new Arc();
	//Instance methods ************************************************
	
	/**
	 * This method update the capacity in the park according to park name and present the capacity to park manager
	 * @param event
	 * @throws Exception
	 */
	 private void setCapcity() throws Exception {
		 String parkName;
		 parkName=ChatClient.worker.getPark().toString();
			System.out.println(parkName);
			
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
		 * This method sign in to the Create Report screen
		 * @param event
		 * @throws Exception
		 */
		public void createReport(ActionEvent event) throws Exception {
			stage.OpenStage(event, "/PMCreateReport/CreateReportParkManeger","Create Report Park Maneger");
		}
		/**
		 * This method sign in to the Register Member screen
		 * @param event
		 * @throws Exception
		 */
		public void registerMember(ActionEvent event) throws Exception {
			stage.OpenStage(event, "/CSRegister/RegisterMember","Register New Member");
		}
		/**
		 * This method move to set capacity screen
		 * @param event
		 * @throws Exception
		 */
		public void setCapacity(ActionEvent event) throws Exception {
			stage.OpenStage(event, "/PMCapacity/CapacityParkManager","Capacity Park Manager");
		}
		/**
		 * This method sign in to the Check Reports screen
		 * @param event
		 * @throws Exception
		 */ 
		public void checkReports(ActionEvent event) throws Exception {
			stage.OpenStage(event, "/DMCheckReports/CheckReportsDepartmnetManager","Check Reports");
		}
		/**
		 * This method sign in to the check Reports screen-in this screen the park manager can see the requests and the status
		 * @param event
		 * @throws Exception
		 */ 
		public void checkRequests(ActionEvent event) throws Exception {
			MessageToServer mts = new MessageToServer();
			mts = new MessageToServer(Command.ReadLastRecord,TableName.requests,"null");	
			mts.toAccept();
			if (ChatClient.objectType != ObjectKind.Request) 
			{
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Required Fields Empty or Wrong");
				alert.setContentText("Requests not created");
				alert.showAndWait();
			}
			else
				stage.OpenStage(event, "/PMAllRequests/RequestsParkManager","Requests Park Manager");
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
				e.printStackTrace();
			}		
		}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub
		
		stage.setImage(images,"worker");
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());
		lblParkName.setText("Park name: "+ChatClient.worker.getPark());
		stage.SetTime(lblTime);
		textAreaWaiting.setText("Request");
		try {
			setCapcity() ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
