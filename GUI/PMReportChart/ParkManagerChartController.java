package PMReportChart;
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
import Enums.Role;
import Enums.TableName;
import PMCreateReport.CreateReportParkManeger;
import PMReports.ParkManagerReportsController;
import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

public class ParkManagerChartController implements Initializable {
	// Class variables *************************************************

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
    private Button btnBack;

    @FXML
    private Label lblTime;
    
    @FXML
	private Label lblWelcome= new Label();
    
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @FXML
    private LineChart<?,?>chart;
    
    @FXML
   	private Label lblDetails= new Label();
    
    public static boolean flagCheckReport2 = false;
    

	// Instance methods ************************************************
	public static boolean isFlagCheckReport2() {
		return flagCheckReport2;
	}

	public static void setFlagCheckReport2(boolean flagCheckReport2) {
		ParkManagerChartController.flagCheckReport2 = flagCheckReport2;
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
	public void getBack(ActionEvent event) throws Exception {
		stage.OpenStage(event, "/PMReports/ParkManagerReports","Park Manager Reports");
		
	}
	/**
	 * This method get the date that the user want report
	 * @param event
	 * @throws Exception
	 */ 
	public void set() throws Exception {	
		String park;
		int month;
		String monthName;
		int year;

			park = PMReports.ParkManagerReportsController.park;
			month=PMReports.ParkManagerReportsController.month;
			year = PMReports.ParkManagerReportsController.year;
			
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month-1 );
		c.set(Calendar.DAY_OF_MONTH, 1);  
		monthName = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH).toString();
		
		lblDetails.setText(park + "      "+monthName+"  "+year);

	}
	/**
	 * This method create the chart according to user choice
	 * @param event
	 * @throws Exception
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
		try {
			set();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String park;
		int month;
		String monthName;
		int year;
		if (ChatClient.worker.getRole() == Role.DepartmentManager)
		{
			park = CheckReportsDepartmnetManager.park;
			month=CheckReportsDepartmnetManager.month;
			monthName=CheckReportsDepartmnetManager.monthName;
			year = CheckReportsDepartmnetManager.year;
		}
		else
		{
			monthName=CreateReportParkManeger.monthName;
			park = ChatClient.worker.getPark().toString();
			month=CreateReportParkManeger.month;
			year = CreateReportParkManeger.year;
		}
		String typeOfReports=CreateReportParkManeger.getTypeOfReport();
		XYChart.Series members=new XYChart.Series<>();
		XYChart.Series group=new XYChart.Series<>();
		XYChart.Series visitors=new XYChart.Series<>();
		members.setName("Members");
		group.setName("Pepole from Groups");
		visitors.setName("Regular Visitors");
		LocalDate date =  LocalDate.of(year, month, 1);
		for(int i=1; i<date.lengthOfMonth();i++)
		{
			boolean flag = false;
			for(int j=0;j<ParkManagerReportsController.summaryList.size();j++)
				{
				if(ParkManagerReportsController.summaryList.get(j).getDay() == i)
					{
					members.getData().add(new XYChart.Data<>(i +"",ParkManagerReportsController.summaryList.get(j).getMembers()));
					group.getData().add(new XYChart.Data<>(i +"",ParkManagerReportsController.summaryList.get(j).getGroup()));
					visitors.getData().add(new XYChart.Data<>(i +"",ParkManagerReportsController.summaryList.get(j).getVisitors()));
						flag = true;
						break;
						
					}
				}
			if (flag == false)
			{
				members.getData().add(new XYChart.Data<>(i +"",0));
				group.getData().add(new XYChart.Data<>(i +"",0));
				visitors.getData().add(new XYChart.Data<>(i +"",0));
			}
		}
		
		
		chart.getData().addAll(members,group,visitors);
		
		lblTime.setVisible(false);
		
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());
		///set();
	}
}
