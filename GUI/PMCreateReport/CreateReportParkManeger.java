package PMCreateReport;
import java.time.format.DateTimeFormatter;
//import java.awt.Label;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Enums.Command;
import Enums.ObjectKind;
import Enums.ReqStatus;
import Enums.TableName;
import client.ChatClient;
import client.ClientUI;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import logic.MessageToServer;
import logic.Park;
import logic.Request;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
public class CreateReportParkManeger implements Initializable {
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
    private Button btnHome;
    
    @FXML
    private Button btnReq;

    @FXML
   private Label dateOfToday;

	@FXML
	private ComboBox<String> monthCombo;
	
	@FXML
	private ComboBox<String> yearCombo;
	
	@FXML
	private Label lblWelcome= new Label();
	    
	@FXML
	private Label lblParkName= new Label();
	
	private ObservableList<String>listMonth;
	private ObservableList<String>listYear;
	private ObservableList<String>listMonthNumber;
	private ObservableList<String>listYearNumber;
	private ObservableList<String>listMonthUsage;
	private ObservableList<String>listYearUsage;
    @FXML
    private RadioButton radioNumber;
    
    @FXML
    private  RadioButton radioUsage ;
    
    @FXML
    private RadioButton radioIncome; 
    
    @FXML
	private ListView<String> listView;
    
    @FXML
    private Rectangle shpBackground;
	
	
	ObservableList<String> list ;
    private boolean flag1=false;
    private boolean flag2=false;
    private boolean flag3=false;
    public static String monthName;
	public static int month;
	public static int year;
	public static String typeOfReport;
	private static int currentYear;
	private static int count;

	//Instance methods ************************************************
	public static int getMonth() {
		return month;
	}
	
	public static int getYear() {
		return year;
	}
	
	public static String getTypeOfReport() {
		return typeOfReport;
	}
	
	   /**
     * This method initialize the combo boxes of month and year.
     * Checks the current year and get the last 7 years.
     */
    private void setComboBox() {
		ArrayList<String>months=new ArrayList<String>();
		ArrayList<String>years=new ArrayList<String>();
		months.add("January");
		months.add("Fabruary");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("Novenmber");
		months.add("December");
		
		listMonth=FXCollections.observableArrayList(months);
		monthCombo.setItems(listMonth);
		currentYear = LocalDate.now().getYear();
		
		years.add(String.valueOf(currentYear-7));
		years.add(String.valueOf(currentYear-6));
		years.add(String.valueOf(currentYear-5));
		years.add(String.valueOf(currentYear-4));
		years.add(String.valueOf(currentYear-3));
		years.add(String.valueOf(currentYear-2));
		years.add(String.valueOf(currentYear-1));
		years.add(String.valueOf(currentYear));
		listYear=FXCollections.observableArrayList(years);
		yearCombo.setItems(listYear);
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
	 * This method checks if one of the combo boxes is selected and if yes. shows all the relevant combo boxes.
	 * @param event
	 * @throws Exception
	 */
	public void chooseRadio(ActionEvent event) throws Exception 
	{
		if(radioIncome.isSelected() || radioNumber.isSelected() || radioUsage.isSelected()) 
		{
			monthCombo.setVisible(true);
			yearCombo.setVisible(true);
			shpBackground.setVisible(true);
			btnReq.setVisible(monthCombo.getValue()!=null && yearCombo.getValue()!=null);
		}
  
	}
	
	/**
	 * This method get the selected report to the user choose to create.
	 * If the user want to see the report- move to the screen with the report he choose, and add the report to the DB to future watch (if it's not already there)
	 * If the user want to create another report- save this report in the DB (if it's not already there) for later watch and return to the same screen. 
	 * This method 
	 * @param event
	 * @throws Exception
	 */
	public void getReq(ActionEvent event)throws Exception {
		MessageToServer mts = new MessageToServer();
		String monthName = monthCombo.getValue();
		month=getMonthNumber(monthName);
		String yearString = yearCombo.getValue();
		year = Integer.parseInt(yearString);
		String park = ChatClient.worker.getPark();
		String monthString;
		DMCheckReports.CheckReportsDepartmnetManager.setFlagCheckReport(false);
		
			 Alert alert = new Alert(AlertType.CONFIRMATION);
			 alert.setTitle("Your Report Create");
			 alert.setHeaderText("Do You want create another report or see reports details?");
			 alert.setContentText("Choose your option:");
			 ButtonType buttonTypeOne = new ButtonType("Create Another");
			 ButtonType buttonTypeTwo = new ButtonType("Watch Report");	
			 ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			 alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
			 Optional<ButtonType> result = alert.showAndWait();
			 if (month<10)
			 {
				 monthString = "0"+month;
			 }
			 else
			 {
				 monthString = ""+month;
			 }
			 if (result.get() == buttonTypeOne) 
			 {
				 stage.OpenStage(event, "/PMCreateReport/CreateReportParkManeger","Create Report Park Maneger");
			 }
			 else if (result.get() == buttonTypeTwo) 
			 {
				 if(radioNumber.isSelected()) 
				 {
					 stage.OpenStage(event, "/PMReports/ParkManagerReports","Park Manager Reports");
				 }
				 else if(radioUsage.isSelected()) 
				 {
					 stage.OpenStage(event, "/PMOccupancyReports/OccupancyReport","Occupancy Report");
				 }
				 else if (radioIncome.isSelected()) 
				 {
					 stage.OpenStage(event, "/PMIncomeReport/IncomeReportParkManager","Income Report Park Manager");
				 }
			 }
			 if(result.get() == buttonTypeOne || result.get() == buttonTypeTwo)
			 {
				 
				 String chosenReport="";
				 String startDate = yearString+"-"+monthString+"-01";
					if(radioNumber.isSelected())
					{
						chosenReport = "VisitorsReport";
					}
					else if(radioUsage.isSelected())
					{
						chosenReport = "OccupancyReport";
					}
					else if (radioIncome.isSelected()) 
					{
						chosenReport = "IncomeReport";
					}
			 
			 		mts = new MessageToServer();
					
					
					mts.addReportInfo("SELECT * FROM requests WHERE parkName= "+"\"" + park +"\" and type= \""+chosenReport+"\" and startDate= \""+startDate+"\";");
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
					 mts.addObject((new Request(count, park,chosenReport, startDate, "0", "1", LocalDate.now().toString(),ReqStatus.Ready)));
				 mts.toAccept();
			 }
		
	}
	}
	
	/**
	 * This method convert month name to the match number.
	 * @param month
	 * @return
	 */
	public static int getMonthNumber(String month) 
	{
		switch(month)
		{
			case "January":
			{
				return 1;
			}
			case "February":
			{
				return 2;
			}
			case "March":
			{
				return 3;
			}
			case "April":
			{
				return 4;
			}
			case "May":
			{
				return 5;
			}
			case "June":
			{
				return 6;
			}
			case "July":
			{
				return 7;
			}
			case "August":
			{
				return 8;
			}
			case "September":
			{
				return 9;
			}
			case "October":
			{
				return 10;
			}
			case "November":
			{
				return 11;
			}
			case "December":
			{
				return 12;
			}
			
		}
		return 0;
	}

	/**
	 * This method initialize the screen with name, date.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub
		stage.setImage(images,"workerDetails");
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());
		lblParkName.setText("Park name: "+ChatClient.worker.getPark());
    	stage.SetTime(dateOfToday);
		setComboBox();

		ToggleGroup group = new ToggleGroup();
		radioNumber.setToggleGroup(group);
		radioUsage.setToggleGroup(group);
		radioIncome.setToggleGroup(group);

	}
	
}
