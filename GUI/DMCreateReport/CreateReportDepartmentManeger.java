package DMCreateReport;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Enums.Command;
import Enums.ObjectKind;
import Enums.ReqStatus;
import Enums.TableName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import logic.MessageToServer;
import logic.Request;
import StagesFunc.Stages;
import client.ChatClient;
/** 
* @author Tomer Dabun
* @author Lior Saghi
* @author Shay Feld
* @author Sivan Brecher
* @author Sapir Baron
* @author Coral Harel
* @version December 2020
*/
public class CreateReportDepartmentManeger implements Initializable {
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
    private Button btnRep=null;
	@FXML
	private Label lblTime= new Label();
    @FXML
    private RadioButton radioVisit;
    @FXML
    private  RadioButton radioCancel ;
    @FXML
  	private DatePicker dpFromDate = new DatePicker();
  	@FXML
  	private DatePicker dpToDate = new DatePicker();

    @FXML
    private Label lblFrom;

    @FXML
    private Label lblTo;
	@FXML
	private ComboBox<String> parkCombo;

	@FXML
	private Rectangle shpReportType;

	@FXML
	private Rectangle shpChooseDetails;
	@FXML
	private Label lblWelcome = new Label();
	
	
	private ObservableList<String>listPark;
	public static String chosenPark;
	public static String fromDate, toDate;
	public static int fromDay; 
	public static int fromMonth;
	public static int fromYear;
	public static int toDay; 
	public static int toMonth;
	public static int toYear;
	public static int fromCal;
	public static int toCal;
	public static String typeOfReport;
	private static int count;
	private String  from, to;

   
	//Instance methods ************************************************
	 public static void setPMReportDetails(int from1, int to1,String typeOfReport1) {
		fromCal = from1;
		toCal = to1;
		typeOfReport=typeOfReport1;
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
	 * This method get the selected report to the user choose to create, when press on the button "Create Report"
	 * If the user want to see the report- move to the screen with the report he choose, and add the report to the DB to future watch (if it's not already there)
	 * If the user want to create another report- save this report in the DB (if it's not already there) for later watch and return to the same screen. 
	 * @param event
	 * @throws Exception
	 */
	public void getReq(ActionEvent event)throws Exception
	{
		StringBuilder infoErrors = new StringBuilder();
		DMCheckReports.CheckReportsDepartmnetManager.setFlagCheckReport(false);
		if(dpFromDate.getValue()==null&&dpToDate.getValue()==null)
		{
			infoErrors.append("- Please choose dates \n");
		}
		else 
		{	
			if  (dpFromDate.getValue()==null) 
			{
				infoErrors.append("- Please choose date of begining. \n");
			}
			else if(dpToDate.getValue()==null) 
			{
				infoErrors.append("- Please choose date of ending.\n");
			}
			splitDate(dpFromDate.getValue(), dpToDate.getValue());
			if (fromCal>toCal) 
			{
				infoErrors.append("- The To date must be greater than the From date.\n");
			}
			if (infoErrors.length() > 0 ) {
		        Alert alert = new Alert(Alert.AlertType.WARNING);
		        alert.setTitle("Warning");
		        alert.setHeaderText("Required Fields Empty or Wrong");
		        alert.setContentText(infoErrors.toString());
	
		        alert.showAndWait();
			}
			else
			{
				chosenPark = parkCombo.getValue();
				fromDate = dpFromDate.getEditor().getText();
				toDate = dpToDate.getEditor().getText();
				if(chosenPark.equals("Hai Park"))
					chosenPark="HaiPark";
					 setPMReportDetails(fromCal,toCal,"Visit Report");
					 Alert alert = new Alert(AlertType.CONFIRMATION);
					 alert.setTitle("Your Report Create");
					 alert.setHeaderText("Do You want create another report or see reports details?");
					 alert.setContentText("Choose your option:");
					 ButtonType buttonTypeOne = new ButtonType("Create Another");
					 ButtonType buttonTypeTwo = new ButtonType("Watch Report");	
					 ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
					 alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
					 Optional<ButtonType> result = alert.showAndWait();
					 MessageToServer mts;
						String[] fromParts = fromDate.split("/");
						String fromDay = fromParts[0];
						String fromMonth = fromParts[1];
						String fromYear = fromParts[2];
						from = fromYear+"-"+fromMonth+"-"+fromDay;
						String[] toParts = toDate.split("/");
						String toDay = toParts[0];
						String toMonth = toParts[1];
						String toYear = toParts[2];
						to = toYear+"-"+toMonth+"-"+toDay;
					 if (result.get() == buttonTypeOne) 
					 {
						 stage.OpenStage(event, "/DMCreateReport/CreateReportDepatment","Create Report Depatment Manager");
					 }
					 else if (result.get() == buttonTypeTwo) 
					 {
						 if(radioVisit.isSelected()) 
						 {
							 stage.OpenStage(event, "/DMVisitReport/VisitReportDepartmentManager","Visit Report");
						 }
						 else if(radioCancel.isSelected()) 
						 {
							 stage.OpenStage(event, "/DMCancelReport/CancelReportDepartmnetManager","Cancel Report");
						 }
					 }
					 if (result.get() == buttonTypeOne || result.get() == buttonTypeTwo) 
					 {
						String chosenReport="";
						if(radioVisit.isSelected())
						{
							chosenReport = "VisitsReport";
						}
						else if(radioCancel.isSelected())
						{
							chosenReport = "CancelReport";
						}
				 
				 		mts = new MessageToServer();
						
						
						mts.addReportInfo("SELECT * FROM requests WHERE parkName= "+"\"" + chosenPark +"\" and type= \""+chosenReport+"\" and startDate= \""+from+"\" and endDate= \""+to+"\";");
						mts.toAccept();	//
						System.out.println(ChatClient.list);
						
						if (ChatClient.list.toString().equals("[]")) {
	
						
						 mts = new MessageToServer(Command.ReadLastRecord, TableName.requests, "null");
							mts.toAccept();

							if (ChatClient.objectType != ObjectKind.Request)
								count = 1000;
							else {
								count = ChatClient.request.getRequestID();
								count++;
								System.out.println(ChatClient.request.getRequestID());
							}
							System.out.println(count);
							//* *****Add*****	
							mts = new MessageToServer(Command.Add,TableName.requests,"NULL");	
							//Request(requestID, parkName,  type,startDate, endDate, request, requestDate, status)
							if(chosenPark.equals("All Parks"))
								chosenPark="All-Parks";
							if(chosenPark.equals("Hai Park"))
								chosenPark="HaiPark";		
							mts.addObject((new Request(count, chosenPark,chosenReport, from, to, "0", LocalDate.now().toString(),ReqStatus.Ready)));
						 
						 mts.toAccept();
					 }
			}
		}
	}
	}

	/**
	 * This method get two dates in a range and return days split to day, month, year.
	 * @param from
	 * @param to
	 */
	public void splitDate(LocalDate from, LocalDate to)
	{
		fromDay = from.getDayOfMonth(); 
		fromMonth = from.getMonthValue();
		fromYear = from.getYear();
		toDay = to.getDayOfMonth();
		toMonth = to.getMonthValue();
		toYear = to.getYear();
		fromCal = ((fromYear*10000)+(fromMonth*100)+fromDay);
		toCal = ((toYear*10000)+(toMonth*100)+toDay);	
	}

	/**
	 * This method set the parks combo box.
	 */
	public void setParkComboBox()
	{
		ArrayList<String>parks=new ArrayList<String>();
		parks.add("Carmel");
		parks.add("Sharon");
		parks.add("Hai Park");
		parks.add("All Parks");
		listPark=FXCollections.observableArrayList(parks);
		parkCombo.setItems(listPark);
	}
	
	/**
	 * This method checks if the one of the radio boxes is chosen. if yes, shows the date pickers.
	 */
	@FXML
	void getReportType(ActionEvent event) 
	{
		ToggleGroup group = new ToggleGroup();
		radioVisit.setToggleGroup(group);
		radioCancel.setToggleGroup(group);
		  
		radioVisit.setVisible(true);
		radioCancel.setVisible(true);
		dpFromDate.setVisible(radioVisit.isSelected() || radioCancel.isSelected());
		dpToDate.setVisible(radioVisit.isSelected() || radioCancel.isSelected());
		shpReportType.setVisible(true);
		shpChooseDetails.setVisible(radioVisit.isSelected() || radioCancel.isSelected());
		lblFrom.setVisible(radioVisit.isSelected() || radioCancel.isSelected());
		lblTo.setVisible(radioVisit.isSelected() || radioCancel.isSelected());
		btnRep.setVisible(radioVisit.isSelected() || radioCancel.isSelected());
	}
	
	/**
	 * This method initialize the screen with name, date.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		stage.setImage(images,"workerDetails");
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());
		stage.SetTime(lblTime);
		//radio
		ToggleGroup group = new ToggleGroup();
		radioVisit.setToggleGroup(group);
		radioCancel.setToggleGroup(group);
		//comboBox parks
		setParkComboBox();
	}
}
