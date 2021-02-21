 package DMCheckReports;

import java.awt.Insets;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


//import com.sun.tools.javac.util.List;

import Enums.Command;
import Enums.Role;
import Enums.TableName;
import PMCreateReport.CreateReportParkManeger;
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
import logic.Request;
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
public class CheckReportsDepartmnetManager implements Initializable {
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
    private Label lblDates;
    
	@FXML
    ListView<String> lv = new ListView<>(list);
	
	@FXML
	private Button btnWatch;
	
	public static String monthName;
	public static int month, year,day;
	public static String park;
	public static String typeOfReport;
	public static boolean flagParkManager,flagCheckReport=false;
	public static int fromCal, toCal;
	public static String from, to;
	//Instance methods ************************************************
	
	
	public static void setFlagCheckReport(boolean flagCheckReport) {
		CheckReportsDepartmnetManager.flagCheckReport = flagCheckReport;
	}

	public static boolean isFlagCheckReport() {
		return flagCheckReport;
	}

	public static String getMonthName() {
		return monthName;
	}

	public static int getMonth() {
		return month;
	}

	public static int getYear() {
		return year;
	}

	public static String getPark() {
		return park;
	}
	
	/**
	 * This method close the Client UI buttom
	 * @param event
	 * @throws Exception
	 */
	
	
	/**
	 * This method set the details of existing reports, created by department manager and park manager.
	 * Park manager can see only the report her created. Department manager can see all the reports.
	 */
	private void setDetails() throws Exception {
		ArrayList<String> al = new ArrayList<String>();	
		if (ChatClient.worker.getRole() != Role.DepartmentManager)
		{
			flagParkManager = true;
			park = ChatClient.worker.getPark();
		}

		StringBuilder q = new StringBuilder();
		q.append("SELECT * FROM go_nature.requests ");
		if (ChatClient.worker.getRole() != Role.DepartmentManager)
		{
			q.append(" WHERE parkName = \""+park+"\"" );
		}
		q.append(";");
		System.out.println(q);
		MessageToServer mts = new MessageToServer();
		mts.addReportInfo(q.toString());
		mts.toAccept();	//
				
		//ChatClient.list- list of strings from report command		
			
		//create orders array from report		
		Request req[] = new Request[ChatClient.list.size()];
		for(int i=0;i<req.length;i++)
		{
			if(ChatClient.list.get(i).equals(""))
			{
				break;
			}
			req[i]=new Request(ChatClient.list.get(i));
			if(req[i].getType().contains("Report"))
			{
				if(ChatClient.worker.getRole() != Role.DepartmentManager)
				{
					if(req[i].getRequest().equals("1"))
					{
						al.add(req[i].toStringCheckReports());
					}
				}
				else
				{
					al.add(req[i].toStringCheckReports());
				}
						
			}
		}
		list = FXCollections.observableArrayList(al);
		lv.setItems(list);
	}
	/**
	 * This method move the user to watch the report he chose from the list. 
	 * @param event
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@FXML
    void getWatch(ActionEvent event) throws Exception 
	{
		if(lv.getSelectionModel().getSelectedItem()==null)
		{
			 Alert Error = new Alert(AlertType.NONE,  "Please select a report to watch.",ButtonType.CLOSE);
			 Error.show();
		}
		else
		{
		String [] reqLine = lv.getSelectionModel().getSelectedItem().split("\t");
		String reqId = reqLine[0];
		flagCheckReport = true;
		// * *****Read*****
		Request r1;
		MessageToServer mts = new MessageToServer(Command.Read,TableName.requests,reqId);
		mts.toAccept();
		r1 = new Request(ChatClient.request);
		System.out.println( "5555   "+r1.getStartDate());
		park = r1.getParkName();
		from = r1.getStartDate();
		String [] dateParts = from.split("-");
		month = Integer.parseInt(dateParts[1]);
		year = Integer.parseInt(dateParts[0]);
		park = r1.getParkName();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month-1 );
		c.set(Calendar.DAY_OF_MONTH, 1);  
		monthName = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH).toString();
		if(r1.getRequest().equals("1"))
		{
			if(r1.getType().equals("IncomeReport"))
			{
				System.out.println(from+" "+r1.getEndDate()+" "+(CreateReportParkManeger.getMonthNumber(monthName))+" "+r1.getParkName());
				stage.OpenStage(event, "/PMIncomeReport/IncomeReportParkManager","Income Report Park Manager");
			}
			if(r1.getType().equals("VisitorsReport"))
			{
				System.out.println(from+" "+monthName+" "+park);
				stage.OpenStage(event, "/PMReports/ParkManagerReports","Park Manager Reports");
			}
			if(r1.getType().equals("OccupancyReport"))
			{
				System.out.println(r1.getStartDate()+" "+r1.getEndDate()+" "+(CreateReportParkManeger.getMonthNumber(monthName))+" "+r1.getParkName());
				stage.OpenStage(event, "/PMOccupancyReports/OccupancyReport","Occupancy Report");
			}	
			
		}
		else if(r1.getRequest().equals("0"))
		{	
			to = r1.getEndDate();
			day = Integer.parseInt(dateParts[2]);
			fromCal = (year*10000)+(month*100)+day;
			String [] dateParts2 = r1.getEndDate().split("-");
			toCal = (Integer.parseInt(dateParts2[0])*10000)+(Integer.parseInt(dateParts2[1])*100)+Integer.parseInt(dateParts2[2]);
			if(r1.getType().equals("VisitsReport"))
			{
				System.out.println("fromCal: "+fromCal+" toCal: "+toCal);
				System.out.println(r1.getStartDate()+" "+r1.getEndDate()+" "+(CreateReportParkManeger.getMonthNumber(monthName))+" "+r1.getParkName());
				stage.OpenStage(event, "/DMVisitReport/VisitReportDepartmentManager","Visits Report");
			}
			else if(r1.getType().equals("CancelReport"))
			{
				
				System.out.println(r1.getStartDate()+" "+r1.getEndDate()+" "+(CreateReportParkManeger.getMonthNumber(monthName))+" "+r1.getParkName());
				stage.OpenStage(event, "/DMCancelReport/CancelReportDepartmnetManager","Create Report Depatment Manager");
			}
		}
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
	 * This method initialize the screen with name, date, and initialize the list.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stage.setImage(images,"worker");
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());		
		stage.SetTime(lblTime);
		
		try {
			setDetails();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
