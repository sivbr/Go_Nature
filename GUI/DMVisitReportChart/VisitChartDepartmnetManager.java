 package DMVisitReportChart;

import java.awt.Insets;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.script.Bindings;

import com.sun.org.apache.bcel.internal.generic.NEW;

import DMCreateReport.CreateReportDepartmentManeger;

//import com.sun.tools.javac.util.List;

import Enums.Command;
import Enums.ObjectKind;
import Enums.OrderStatus;
import Enums.TableName;
import client.ChatClient;
import client.ClientUI;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import StagesFunc.Stages;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration; 

/** 
* @author Tomer Dabun
* @author Lior Saghi
* @author Shay Feld
* @author Sivan Brecher
* @author Sapir Baron
* @author Coral Harel
* @version December 2020
*/
public class VisitChartDepartmnetManager implements Initializable {
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
	
	@FXML
	private CategoryAxis x;
	
	@FXML
	private NumberAxis y;
	
	@FXML
	private StackedBarChart<String, Number> visitChart;
	
	
	
	
	
	//Instance methods ************************************************
	
	/**
	 * This method create chart of all the orders that fulfilled for range of date. sort by visitor type and hours,
	 * using specific park or all parks.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void setDetails() throws Exception {
		int member8Cnt, member12Cnt, member16Cnt;
		int group8Cnt, group12Cnt, group16Cnt;
		int single8Cnt, single12Cnt, single16Cnt;
		String park, from, to;
		
		park = DMVisitReport.VisitReportDepartmentManager.park;
		from = DMVisitReport.VisitReportDepartmentManager.from;
		to = DMVisitReport.VisitReportDepartmentManager.to;
		if(park.equals("All-Parks"))
		{
			park = "All Parks";
		}
		
		x = new CategoryAxis();    

		x.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("8:00-12:00", "12:00-16:00", "16:00-20:00"))); 
		x.setLabel("Hours");  

		//Defining the y axis 
		NumberAxis y = new NumberAxis(); 
		y.setLabel("Number of visitors");
		
		//Creating the Bar chart        
		visitChart.setTitle("Park: "+park+"    Dates: "+from+" - "+to); 
		
		
		member8Cnt = getQuery(park, "members", 8);
		member12Cnt = getQuery(park, "members", 12);
		member16Cnt = getQuery(park, "members", 16);
		
		group8Cnt = getQuery(park, "groups", 8);
		group12Cnt = getQuery(park, "groups", 12);
		group16Cnt = getQuery(park, "groups", 16);
		
		single8Cnt = getQuery(park, "singles", 8);
		single12Cnt = getQuery(park, "singles", 12);
		single16Cnt = getQuery(park, "singles", 16);
		
		System.out.println("member 8: "+member8Cnt+" member 12: "+member12Cnt+" member 16: "+member16Cnt);
		System.out.println("group 8: "+group8Cnt+" group 12: "+group12Cnt+" group 16: "+group16Cnt);
		System.out.println("single 8: "+single8Cnt+" single 12: "+single12Cnt+" single 16: "+single16Cnt);

	      //Prepare XYChart.Series objects by setting data 
	      XYChart.Series<String, Number> series1 = new XYChart.Series<>();  
	      series1.setName("Members"); 
	      series1.getData().add(new XYChart.Data<>("8:00-12:00", member8Cnt)); 
	      series1.getData().add(new XYChart.Data<>("12:00-16:00", member12Cnt));  
	      series1.getData().add(new XYChart.Data<>("16:00-20:00", member16Cnt)); 
	         
	      XYChart.Series<String, Number> series2 = new XYChart.Series<>(); 
	      series2.setName("Groups"); 
	      series2.getData().add(new XYChart.Data<>("8:00-12:00", group8Cnt)); 
	      series2.getData().add(new XYChart.Data<>("12:00-16:00", group12Cnt)); 
	      series2.getData().add(new XYChart.Data<>("16:00-20:00", group16Cnt)); 
	     
	      XYChart.Series<String, Number> series3 = new XYChart.Series<>(); 
	      series3.setName("Singles"); 
	      series3.getData().add(new XYChart.Data<>("8:00-12:00", single8Cnt)); 
	      series3.getData().add(new XYChart.Data<>("12:00-16:00", single12Cnt)); 
	      series3.getData().add(new XYChart.Data<>("16:00-20:00", single16Cnt));  
	         
	      //Setting the data to bar chart
	      
	      visitChart.setCategoryGap(100);      
	      visitChart.getData().addAll(series1, series2, series3);
	      if(DMVisitReport.VisitReportDepartmentManager.flagCheckReport2)
	      {
	    	  DMVisitReport.VisitReportDepartmentManager.setFlagCheckReport2(false);
	    	  DMCheckReports.CheckReportsDepartmnetManager.setFlagCheckReport(true);
	      }
	      else
	      {
	    	  DMCheckReports.CheckReportsDepartmnetManager.setFlagCheckReport(false);
	      }
	         
	}
    
	/**
	 * This method checks all the fulfilled orders using the parameters and count them.
	 * @param park
	 * @param user
	 * @param visitTime
	 * @return
	 */
	private int getQuery(String park, String user, int visitTime)
	{
		int fromCal, toCal;
		
		fromCal = DMVisitReport.VisitReportDepartmentManager.fromCal;
		toCal = DMVisitReport.VisitReportDepartmentManager.toCal;
		System.out.println(" fromcal: "+fromCal + " toCal: "+toCal+ " park: "+park);
		StringBuilder query = new StringBuilder();
		query.append("SELECT SUM(numOfVisitors)\r\n" + "FROM go_nature.orders\r\n"+ " WHERE status = \"" + OrderStatus.Fulfilled.toString() + "\"");
		if(!park.equals("All Parks"))
		{
			query.append(" and park = \""+park+"\"");
		}
		query.append(" and visitTime = "+visitTime);
		query.append(" and (visitYear*10000)+(visitMonth*100)+visitDay<="+toCal+" and (visitYear*10000)+(visitMonth*100)+visitDay>="+fromCal);
		if(user.equals("members"))
		{
			query.append(" and EXISTS (SELECT ID FROM go_nature.members WHERE members.ID = orders.personID)"); 
		}
		else if(user.equals("groups"))
		{
			query.append(" and organizationGroup = 1");
		}
		else if(user.equals("singles"))
		{
			query.append(" and numOfVisitors = 1");
		}
		query.append(";");
		System.out.println(query);
		MessageToServer mts = new MessageToServer();
		try {
			mts.addReportInfo(query.toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mts.toAccept();

		if (ChatClient.list.get(0).equals("null"))
		{
			return 0;
		}
		return Integer.parseInt(ChatClient.list.get(0));
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
		stage.OpenStage(event, "/DMVisitReport/VisitReportDepartmentManager","Visit Report");	
	}
	
	/**
	 * This method initialize the screen with name, date, and initialize the chart.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		stage.setImage(images,"worker");
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());		
		stage.SetTime(lblTime);
		                           
		try {
			setDetails();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    }
	         
}
