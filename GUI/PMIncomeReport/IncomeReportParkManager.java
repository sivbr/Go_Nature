 package PMIncomeReport;

 import java.awt.Insets;
 import java.net.URL;
 import java.text.DecimalFormat;
 import java.time.LocalDate;
 import java.time.format.DateTimeFormatter;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Locale;
 import java.util.Optional;
 import java.util.ResourceBundle;
 import DMCheckReports.CheckReportsDepartmnetManager;
 //import com.sun.tools.javac.util.List;
 import DMVisitReport.VisitReportDepartmentManager;
 import Enums.Command;
 import Enums.ObjectKind;
 import Enums.OrderStatus;
 import Enums.ReqStatus;
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
 import logic.Summary_day;
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
public class IncomeReportParkManager implements Initializable {
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
    private Label lblPark= new Label();
	
	@FXML
	private Label lblWelcome = new Label();

	@FXML
	private Label lblTime= new Label();
		
	ObservableList<String> list ;
	
    @FXML
    private Label lblDates;
    
	@FXML
	public static ArrayList <Order> orderList= new ArrayList <Order>() ;
	@FXML
	private ListView<String> lv;
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
		IncomeReportParkManager.flagCheckReport2 = flagCheckReport2;
	}
	
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
	 * This method get the list of orders that fulfilled in selected month and year, and sums the payment.
	 * @throws Exception
	 */
	private void setDetails() throws Exception {

		String park;
		int month;
		String monthName;
		int year;
		String monthString;
		ArrayList<String> req = new ArrayList<String>();
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
		
		String typeOfReports=CreateReportParkManeger.getTypeOfReport();
		double income = 0;
		MessageToServer mts = new MessageToServer();
		String q = "SELECT * FROM  orders  WHERE status = \"" + OrderStatus.Fulfilled.toString() + "\""+" AND visitMonth="+"\""+month+"\" AND visitYear="+"\""+year+"\" AND park="+"\"" +park+"\";";
		System.out.println("#$#"+q);
		mts.addReportInfo(q);
		mts.toAccept();	
		lblDates.setText(park + "      "+monthName+"  "+year);
		orderList.clear();
		String check;
		Order reports[] = new Order[ChatClient.list.size()];
		for(int i=0;i<reports.length;i++)
		{
			if(ChatClient.list.get(i).equals(""))
			{
				break;
			}
			reports[i]=new Order(ChatClient.list.get(i));
			req.add(reports[i].stringToIncomeReport());		
			orderList.add(reports[i]);
			income += reports[i].getTotalPayment();
			

		}
		String incomeString;
		DecimalFormat df = new DecimalFormat("#.##");
		incomeString = df.format(income);
		req.add("		Total Income:							"+incomeString);
		ObservableList<String> items =FXCollections.observableArrayList (req);
		lv.setItems(items);
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
			stage.OpenStage(event, "/PMCreateReport/CreateReportParkManeger","Create Report Park Maneger");
		}		
	}
	
	/**
	 * This method initialize the screen with name, date, and initialize the list.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		stage.setImage(images,"worker");
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());		
		lblPark.setText("Park: " + ChatClient.worker.getPark());
		stage.SetTime(lblTime);
		
		try {
			setDetails();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
}
