 package DMCancelReport;

import java.awt.Insets;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import DMCheckReports.CheckReportsDepartmnetManager;
import DMVisitReport.VisitReportDepartmentManager;

//import com.sun.tools.javac.util.List;

import Enums.Command;
import Enums.ObjectKind;
import Enums.OrderStatus;
import Enums.ReqStatus;
import Enums.TableName;
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
public class CancelReportDepartmnetManager implements Initializable {
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
    private Button btnBack=null;
	
	@FXML
	private Label lblWelcome = new Label();

	@FXML
	private Label lblTime= new Label();
		
	ObservableList<String> list ;
	
    @FXML
    private Label lblDates;
    
	@FXML
    ListView<String> lv = new ListView<>(list);
	
	public static boolean flagCheckReport2 = false;
	 private String  from, from1, to, to1;
	 
	//Instance methods ************************************************
		public static boolean isFlagCheckReport2() {
			return flagCheckReport2;
		}
		
		public static void setFlagCheckReport2(boolean flagCheckReport2) {
			CancelReportDepartmnetManager.flagCheckReport2 = flagCheckReport2;
		}
	 	/**
	 	 * This method set the details of the orders that cancelled or expired, in specific parks or in all parks.
	 	 * @throws Exception
	 	 */
		@SuppressWarnings("unlikely-arg-type")
		private void setDetails() throws Exception {
			
			String park;
			int fromCal;
			int toCal ;
			
			if (CheckReportsDepartmnetManager.flagCheckReport)
			{
				park = CheckReportsDepartmnetManager.park;
				fromCal=CheckReportsDepartmnetManager.fromCal;	
				toCal = CheckReportsDepartmnetManager.toCal;
				from = CheckReportsDepartmnetManager.from;
				to = CheckReportsDepartmnetManager.to;
				if (park.equals("All-Parks"))
					park = "All Parks";
				CheckReportsDepartmnetManager.setFlagCheckReport(false);
				flagCheckReport2 = true;
			}
			else
			{
				park = DMCreateReport.CreateReportDepartmentManeger.chosenPark;
				fromCal = DMCreateReport.CreateReportDepartmentManeger.fromCal;
				toCal = DMCreateReport.CreateReportDepartmentManeger.toCal;
				from = DMCreateReport.CreateReportDepartmentManeger.fromDate;
				to = DMCreateReport.CreateReportDepartmentManeger.toDate;
				String[] fromParts = from.split("/");
				String fromDay = fromParts[0];
				String fromMonth = fromParts[1];
				String fromYear = fromParts[2];
				from1 = fromYear+"-"+fromMonth+"-"+fromDay;
				String[] toParts = to.split("/");
				String toDay = toParts[0];
				String toMonth = toParts[1];
				String toYear = toParts[2];
				to1 = toYear+"-"+toMonth+"-"+toDay;
				if (park.equals("Hai Park"))
				{
					park = "HaiPark";
				}
				flagCheckReport2 = false;
			}
			System.out.println("park: "+park+" start: "+from+" end: "+to+ " fromcal: "+fromCal+" tocal: "+toCal);
			lblDates.setText("Park: "+park+"         "+"Dates: "+from+" - "+to);
			ArrayList<String> al = new ArrayList<String>();	
			
			MessageToServer mts = new MessageToServer();
			mts.addReportInfo("SELECT * FROM orders");
			mts.toAccept();	//
					
			//ChatClient.list- list of strings from report command		
				
			//create orders array from report		
			Order orders[] = new Order[ChatClient.list.size()];
			for(int i=0;i<orders.length;i++)
			{
				orders[i]=new Order(ChatClient.list.get(i));
				if(orders[i].getStatus().equals(OrderStatus.Cancelled.toString()) || orders[i].getStatus().equals(OrderStatus.Expired.toString()))
				{
					int orderDateCal = ((orders[i].getVisitYear()*10000)+(orders[i].getVisitMonth()*100)+orders[i].getVisitDay());
					if (orderDateCal >= fromCal && orderDateCal <= toCal )
					{
						if (park.equals("All Parks"))
						{
							al.add(orders[i].stringToCancelReport());
							System.out.println("888888"+orders[i]);
						}
						else if (park.equals(orders[i].getPark())) 
						{
							al.add(orders[i].stringToCancelReport());
						}
					}
				}
			}
			list = FXCollections.observableArrayList(al);
			lv.setItems(list);
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
		if(flagCheckReport2)
		{
			stage.OpenStage(event, "/DMCheckReports/CheckReportsDepartmnetManager","Check Reports");
		}
		else
		{
			stage.OpenStage(event, "/DMCreateReport/CreateReportDepatment","Create Report Depatment Manager");
		}	
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
