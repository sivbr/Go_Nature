package PMOccupancyReports;
import java.time.format.DateTimeFormatter;
import java.net.URL;
import java.util.ResourceBundle;

import DMCheckReports.CheckReportsDepartmnetManager;

import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Enums.Command;
import Enums.ObjectKind;
import Enums.ReqStatus;
import Enums.Role;
import Enums.TableName;
import PMCreateReport.CreateReportParkManeger;
import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.MessageToServer;
import logic.Order;
import logic.Park;
import logic.Request;
import logic.Summary_day;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import client.ChatClient;
import javafx.event.ActionEvent;
import StagesFunc.Stages;

public class PMOccupancyReportsController implements Initializable {
	
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
    private Button btnChart=null;
    @FXML
    private Button btnHome;
    
    @FXML
    private Label lblTime;
    
    @FXML
	private Label lblWelcome= new Label();
    
    @FXML
   	private Label lblDetails= new Label();
   
    @FXML
	private ListView<String> listView;
    ObservableList<String> list ;
    
	public static ArrayList <Summary_day> summaryList= new ArrayList <Summary_day>() ;
	public static String choosenMonthNumber;
	public static String choosenYearNumber;
	public static String choosenTypeOfReport;
	public static String choosenParkReport;
	private static int count;
	public static boolean flagCheckReport2 = false;

	//Instance methods ************************************************
	public static boolean isFlagCheckReport2() {
		return flagCheckReport2;
	}

	public static void setFlagCheckReport2(boolean flagCheckReport2) {
		PMOccupancyReportsController.flagCheckReport2 = flagCheckReport2;
	}
	
	/**
	 * This method set the chosen month year and report type
	 * @param event
	 * @throws Exception
	 */
	public static void setPMReportDetails(String chossenMonthNumber1, String chossenYearNumber1,String chossenTypeOfReport1,String parkName1) 
	{
		choosenMonthNumber = chossenMonthNumber1;
		choosenYearNumber =  chossenYearNumber1;
		choosenTypeOfReport=chossenTypeOfReport1;
		choosenParkReport=parkName1;
	}
	public static String getChoosenMonthNumber() {
		return choosenMonthNumber;
	}
	
	public static String getChoosenYearNumber() {
		return choosenYearNumber;
	}
	
	public static String getChoosenTypeOfReport() {
		return choosenTypeOfReport;
	}
	
	public static String getChoosenParkReport() {
		return choosenParkReport;
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
	 * This method returns the user to the previous screen when press back button.
	 * @param event
	 * @throws Exception
	 */
	public void getBack(ActionEvent event) throws Exception 
	{
		if(flagCheckReport2)
		{
			stage.OpenStage(event, "/DMCheckReports/CheckReportsDepartmnetManager","Check Reports");
		}
		else
		{
			stage.OpenStage(event, "/PMCreateReport/CreateReportParkManeger","Create Report Park Maneger");
		}		
	}
	/**
	 * This method insert the user to chart view of the report when he click on the button
	 * @param event
	 * @throws Exception
	 */
	public void getChart(ActionEvent event) throws Exception{
		stage.OpenStage(event, "/PMReportChart/ParkManagerChart","Manager Home");
	}
	
	/**
	 * This method get the dates of report and set the occupancy reports details in the list view
	 * @param event
	 * @throws Exception
	 */
	public void set() throws Exception {
		
		String park;
		String monthName;
		int month;
		int year;
		if (CheckReportsDepartmnetManager.flagCheckReport)
		{
			park = CheckReportsDepartmnetManager.park;
			month=CheckReportsDepartmnetManager.month;
			year = CheckReportsDepartmnetManager.year;
			CheckReportsDepartmnetManager.setFlagCheckReport(false);
			flagCheckReport2 = true;
		}
		else
		{
			park = ChatClient.worker.getPark().toString();
			month=CreateReportParkManeger.month;
			year = CreateReportParkManeger.getYear();
			flagCheckReport2 = false;
		}
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month-1 );
		c.set(Calendar.DAY_OF_MONTH, 1);  
		monthName = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH).toString();
		ArrayList<String> req = new ArrayList<String>();

		
		String occupancyStatus="";
		MessageToServer mts = new MessageToServer();
		if (ChatClient.worker.getRole() == Role.ParkManager)
			mts.addReportInfo("SELECT * FROM summary_days WHERE parkName="+"\""+park+"\";");
		else if (ChatClient.worker.getRole() == Role.DepartmentManager)
			mts.addReportInfo("SELECT * FROM summary_days");
		mts.toAccept();	
		lblDetails.setText(park + "      "+monthName+"  "+year);
		summaryList.clear();
		Summary_day reports[] = new Summary_day[ChatClient.list.size()];
		for(int i=0;i<reports.length;i++)
		{
			reports[i]=new Summary_day(ChatClient.list.get(i));
			if(reports[i].getMonth()==month&&reports[i].getYear()==year) 
			{
				
				if(reports[i].getTotalVisitors()<reports[i].getMaxCapacity())
					 occupancyStatus="Not fully booked";
				else
					 occupancyStatus= "Fully booked";
				req.add(reports[i].toStringAsRowOccupancy() +"\t\t" +occupancyStatus);
			}	
					
				summaryList.add(reports[i]);
		}
		
		ObservableList<String> items =FXCollections.observableArrayList (req);
		listView.setItems(items);
	}
		
	/**
	 * This method initialize the screen with name, date, and initialize the Tree Table View.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub
		stage.setImage(images,"worker");
		stage.SetTime(lblTime);
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());
		try {
			set();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		///set();
	}
}
