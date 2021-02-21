package DMHome;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Enums.Command;
import Enums.ObjectKind;
import Enums.TableName;
import PMCreateReport.CreateReportParkManeger;
import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import logic.MessageToServer;
import logic.Order;
import logic.Waiting_list;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import logic.Park;
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
public class DepartmentManagerHomeController implements Initializable {
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
	private Button btnRefresh;
	
	@FXML
	private Button btnCheck;

	@FXML
	private Button btnRegister;

	@FXML
	private Button btnReport;
	
    @FXML
    private Button btnExamination = null;
    
    @FXML
    private Button btnInDe=null;
    
	@FXML
	private Label lblWelcome = new Label();
	
	@FXML
	private Label lblTime= new Label();
	
	@FXML
	private Label lblTotalCapcity= new Label();
	@FXML
	private Label lblTotalCapcity1= new Label();
	@FXML
	private Label lblTotalCapcity2= new Label();
	
	

	//Instance methods ************************************************
	
	/**
	 * This method update the capacity in the park according to park name and present the capacity to department manager
	 * @param event
	 * @throws Exception
	 */
	 private void setCapcity() throws Exception 
	 {
		 MessageToServer park1 = new MessageToServer(Command.Read,TableName.parks,"Carmel");	
		 park1.toAccept();
		 String s1=String.valueOf(ChatClient.park.getCurrentAllVisitors());
		 String s2=String.valueOf(ChatClient.park.getMaxCapacity());
		 lblTotalCapcity.setText( s1.toString() + "/ " + s2.toString());	
	
		 MessageToServer park2 = new MessageToServer(Command.Read,TableName.parks,"HaiPark");	
		 park2.toAccept();
		 String s3=String.valueOf(ChatClient.park.getCurrentAllVisitors());
		 String s4=String.valueOf(ChatClient.park.getMaxCapacity());
		 lblTotalCapcity1.setText( s3.toString() + "/ " + s4.toString());
	

		 MessageToServer park3 = new MessageToServer(Command.Read,TableName.parks,"Sharon");	
		 park3.toAccept();
		 String s5=String.valueOf(ChatClient.park.getCurrentAllVisitors());
		 String s6=String.valueOf(ChatClient.park.getMaxCapacity());
		 lblTotalCapcity2.setText(s5.toString() + "/ " + s6.toString());
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
	 * This method sign in to the Create Report screen
	 * @param event
	 * @throws Exception
	 */
	public void createReport(ActionEvent event) throws Exception {
		stage.OpenStage(event, "/DMCreateReport/CreateReportDepatment","Create Report Depatment Manager");
	}
	
	/**
	 * This method sign in to the Register Member screen
	 * @param event
	 * @throws Exception
	 */
	public void registerMember(ActionEvent event) throws Exception {
		stage.OpenStage(event, "/CSRegister/RegisterMember","Register New Member");
	}//error go to customer service problem
	
	/**
	 * This method sign in to the Check Reports screen
	 * @param event
	 * @throws Exception
	 */ 
	public void checkReports(ActionEvent event) throws Exception {		
		stage.OpenStage(event, "/DMCheckReports/CheckReportsDepartmnetManager","Check Reports");
	}
	
	/**
	 * This method sign in to the Examination Reports screen-in this screen the department manager can see the requests of all park manager
	 * @param event
	 * @throws Exception
	 */ 
	public void examinationReports(ActionEvent event) throws Exception {
		MessageToServer mts = new MessageToServer();
		mts = new MessageToServer(Command.ReadLastRecord,TableName.requests,"null");	
		mts.toAccept();
		/*if there is no request-show pop up message*/
		if (ChatClient.objectType != ObjectKind.Request) 
		{

			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Required Fields Empty or Wrong");
			alert.setContentText("Requests not created");
			alert.showAndWait();
		}
		else
			stage.OpenStage(event, "/DMExamination/Examination","Examination");
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// TODO Auto-generated method stub
		stage.setImage(images,"worker");
		stage.SetTime(lblTime);
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());
		try {
			setCapcity();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
