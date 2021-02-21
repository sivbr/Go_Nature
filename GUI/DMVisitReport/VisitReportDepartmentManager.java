package DMVisitReport;
import java.time.format.DateTimeFormatter;
import java.net.URL;
import java.util.ResourceBundle;

import DMCheckReports.CheckReportsDepartmnetManager;
import DMVisitReport.VisitReportDepartmentManager.OrderTable;

import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import Enums.Command;
import Enums.ObjectKind;
import Enums.OrderStatus;
import Enums.ReqStatus;
import Enums.Role;
import Enums.TableName;
import PMCreateReport.CreateReportParkManeger;
import client.ChatClient;
import client.ClientUI;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.MessageToServer;
import logic.Order;
import logic.Park;
import logic.Request;
import logic.Summary_day;
import sun.reflect.generics.tree.Tree;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import client.ChatClient;
import javafx.event.ActionEvent;
import StagesFunc.Stages;

public class VisitReportDepartmentManager implements Initializable {
	
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
    private Button btnBack=null;
    
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
    private AnchorPane anchorPane = new AnchorPane();
   
    @FXML
    private TreeTableView<OrderTable> visitTable;

    @FXML
    private TreeTableColumn<OrderTable, String> colDate;

    @FXML
    private TreeTableColumn<OrderTable, String> colTime;

    @FXML
    private TreeTableColumn<OrderTable, Number> colSingles;

    @FXML
    private TreeTableColumn<OrderTable, Number> colMembers;

    
    @FXML
    private TreeTableColumn<OrderTable, Number> colGroups;

	public static String park, from, to;
	public static int fromCal, toCal;
	private LocalDate from1, from2, to2;
	TreeItem<OrderTable> o8;
	TreeItem<OrderTable> o12;
	TreeItem<OrderTable> o16;
	TreeItem<OrderTable> root1;
	TreeItem<OrderTable> dateRoot;

	ObservableList<String> list;
	public static ArrayList<Order> ordersList = new ArrayList<Order>();
	public static String choosenMonthNumber;
	public static String choosenYearNumber;
	public static String choosenTypeOfReport;
	public static String choosenParkReport;
	public static boolean flagCheckReport2 = false;
	
	//Instance methods ************************************************

	public static boolean isFlagCheckReport2() {
		return flagCheckReport2;
	}

	public static void setFlagCheckReport2(boolean flagCheckReport2) {
		VisitReportDepartmentManager.flagCheckReport2 = flagCheckReport2;
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
	 * This method move the screen of the chart of the current report.
	 * @param event
	 * @throws Exception
	 */
	public void getChart(ActionEvent event) throws Exception {
		
		stage.OpenStage(event, "/DMVisitReportChart/VisitChartDepartmnetManager", "Visit Report Chart");
	}

	/**
	 * This method set the Tree Table View with the count of all the fulfilled sort by hours and dates and visitor type,
	 * using specific park or all parks.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void set() throws Exception {

		if(CheckReportsDepartmnetManager.flagCheckReport )
		{
			park = DMCheckReports.CheckReportsDepartmnetManager.park;
			from = DMCheckReports.CheckReportsDepartmnetManager.from;
			to = DMCheckReports.CheckReportsDepartmnetManager.to;
			from2 = LocalDate.parse(from);
			to2 = LocalDate.parse(to);
			if(park.equals("All-Parks"))
				park = "All Parks";
			fromCal = DMCheckReports.CheckReportsDepartmnetManager.fromCal;
			toCal = DMCheckReports.CheckReportsDepartmnetManager.toCal; 
			CheckReportsDepartmnetManager.setFlagCheckReport(false);
			flagCheckReport2 = true;
		}
		else
		{
			park = DMCreateReport.CreateReportDepartmentManeger.chosenPark;
			from = DMCreateReport.CreateReportDepartmentManeger.fromDate;
			to = DMCreateReport.CreateReportDepartmentManeger.toDate;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
			from2 = LocalDate.parse(from, formatter);
			to2 = LocalDate.parse(to, formatter);
			fromCal = DMCreateReport.CreateReportDepartmentManeger.fromCal;
			toCal = DMCreateReport.CreateReportDepartmentManeger.toCal;
			flagCheckReport2 = false;
		}
		from1 = from2;

		lblDetails.setText("Park: " + DMCreateReport.CreateReportDepartmentManeger.chosenPark + "         " + "Dates: "
				+ from + " - " + to);

		int fromDay, fromMonth, fromYear;
		int count8members, count12members, count16members;
		int count8singles, count12singles, count16singles;
		int count8groups, count12groups, count16groups;
		int countGroups, countSingles, countMembers;
		int countAllGroups, countAllSingles, countAllMembers;
		int showDayFlag = 0;
		countAllGroups = 0;
		countAllSingles = 0;
		countAllMembers = 0;

		MessageToServer mts;
		root1 = new TreeItem<>(new OrderTable("date", "time", 0, 0, 0));
		
		while (from2.isBefore(to2) || from2.equals(to2)) {
			fromDay = from2.getDayOfMonth();
			fromMonth = from2.getMonthValue();
			fromYear = from2.getYear();
			count8members = 0;
			count12members = 0;
			count16members = 0;
			count8singles = 0;
			count12singles = 0;
			count16singles = 0;
			count8groups = 0;
			count12groups = 0;
			count16groups = 0;
			countSingles = 0;
			countGroups = 0;
			countMembers = 0;
			showDayFlag = 0;
			mts = new MessageToServer();
			StringBuilder s = new StringBuilder();
			s.append("SELECT * FROM orders");
			s.append(" WHERE visitDay = " + fromDay + "  AND visitMonth =" + fromMonth + " AND visitYear = " + fromYear
					+ " and status = \"" + OrderStatus.Fulfilled.toString() + "\"");
			if (!park.equals("All Parks"))
				s.append(" and park = \"" + park + "\"");
			s.append(";");
			mts.addReportInfo(s.toString());
			mts.toAccept();
			if (!ChatClient.list.toString().equals("[]")) {
				Order orders[] = new Order[ChatClient.list.size()];
				for (int j = 0; j < orders.length; j++)
					orders[j] = new Order(ChatClient.list.get(j));
				System.out.printf("order len: %d", orders.length);
				for (int j = 0; j < orders.length; j++) {
					mts = new MessageToServer(Command.Read, TableName.members, orders[j].getPersonID());
					mts.toAccept();
					System.out.println(s);

					if (orders[j].getVisitTime() == 8) {
						if (orders[j].getOrganizationGroup() == 1) {
							count8groups += orders[j].getNumOfVisitors();
							showDayFlag = 1;
						}

						if (orders[j].getNumOfVisitors() == 1) {
							count8singles++;
							showDayFlag = 1;
						}

						if (ChatClient.objectType == ObjectKind.Member) {
							count8members += orders[j].getNumOfVisitors();
							showDayFlag = 1;
						}
					}

					else {
						if (orders[j].getVisitTime() == 12) {
							if (orders[j].getOrganizationGroup() == 1) {
								count12groups += orders[j].getNumOfVisitors();
								showDayFlag = 1;
							}

							if (orders[j].getNumOfVisitors() == 1) {
								count12singles++;
								showDayFlag = 1;
							}

							if (ChatClient.objectType == ObjectKind.Member) {
								count12members += orders[j].getNumOfVisitors();
								showDayFlag = 1;
							}

						} else {
							if (orders[j].getVisitTime() == 16) {
								if (orders[j].getOrganizationGroup() == 1) {
									count16groups += orders[j].getNumOfVisitors();
									showDayFlag = 1;
								}

								if (orders[j].getNumOfVisitors() == 1) {
									count16singles++;
									showDayFlag = 1;
								}

								if (ChatClient.objectType == ObjectKind.Member) {
									count16members += orders[j].getNumOfVisitors();
									showDayFlag = 1;
								}
							}
						}
					}
					System.out.printf(
							"\nday=%d, month=%d, year=%d, 8members=%d, 12members=%d, 16members=%d, 8groups%d, 12groups%d, 16groups%d, 8singles=%d, 12singles=%d, 16singles=%d\n",
							fromDay, fromMonth, fromYear, count8members, count12members, count16members, count8groups,
							count12groups, count16groups, count8singles, count12singles, count16singles);

				}

				if (showDayFlag == 1) {
					countSingles = count8singles + count12singles + count16singles;
					countMembers = count8members + count12members + count16members;
					countGroups = count8groups + count12groups + count16groups;
					countAllMembers += countMembers;
					countAllGroups += countGroups;
					countAllSingles += countSingles;
					
					TreeItem<OrderTable> o8 = new TreeItem<>(
							new OrderTable("", "8:00", count8singles, count8members, count8groups));
					TreeItem<OrderTable> o12 = new TreeItem<>(
							new OrderTable("", "12:00", count12singles, count12members, count12groups));
					TreeItem<OrderTable> o16 = new TreeItem<>(
							new OrderTable("", "16:00", count16singles, count16members, count16groups));
					dateRoot = new TreeItem<>(new OrderTable(fromDay + "/" + fromMonth + "/" + fromYear, "",
							countSingles, countMembers, countGroups));
					if (count8singles > 0 || count8members > 0 || count8groups > 0)
						dateRoot.getChildren().add(o8);
					if (count12singles > 0 || count12members > 0 || count12groups > 0)
						dateRoot.getChildren().add(o12);
					if (count16singles > 0 || count16members > 0 || count16groups > 0)
						dateRoot.getChildren().add(o16);

					if (root1.getChildren().isEmpty())
						root1.getChildren().addAll(dateRoot);
					else
						root1.getChildren().add(dateRoot);
				}
			}
			from2 = from2.plusDays(1);
		}
		dateRoot = new TreeItem<>(new OrderTable("Total:" , "",
				countAllSingles, countAllMembers, countAllGroups));
		if (root1.getChildren().isEmpty())
			root1.getChildren().addAll(dateRoot);
		else
			root1.getChildren().add(dateRoot);
		
		colDate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<OrderTable, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<OrderTable, String> arg0) {
						return arg0.getValue().getValue().getDateProperty();
					}
				});
		colTime.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<OrderTable, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<OrderTable, String> arg0) {
						return arg0.getValue().getValue().getTimeProperty();
					}
				});
		colSingles.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<OrderTable, Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(CellDataFeatures<OrderTable, Number> arg0) {
						return arg0.getValue().getValue().getSinglesProperty();
					}
				});
		colMembers.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<OrderTable, Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(CellDataFeatures<OrderTable, Number> arg0) {
						return arg0.getValue().getValue().getMembersProperty();
					}
				});
		colGroups.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<OrderTable, Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(CellDataFeatures<OrderTable, Number> arg0) {
						return arg0.getValue().getValue().getGroupsProperty();
					}
				});
		OrderTable order1 = new OrderTable("", "", countAllSingles, countAllMembers, countAllGroups);
		root1.setValue(order1);

		visitTable.setRoot(root1);
		visitTable.setShowRoot(false);

	}
	
	/**
	 * This class is a class of property vars, in order to set them in the Tree Tabel View. 
	 * @author 
	 *
	 */

	class OrderTable {
		SimpleStringProperty dateProperty;
		SimpleStringProperty timeProperty;
		SimpleIntegerProperty singlesProperty;
		SimpleIntegerProperty membersProperty;
		SimpleIntegerProperty groupsProperty;

		public OrderTable(String date, String time, Integer singles, Integer members, Integer groups) {
			this.dateProperty = new SimpleStringProperty(date);
			this.timeProperty = new SimpleStringProperty(time);
			this.singlesProperty = new SimpleIntegerProperty(singles);
			this.membersProperty = new SimpleIntegerProperty(members);
			this.groupsProperty = new SimpleIntegerProperty(groups);
		}

		public SimpleStringProperty getDateProperty() {
			return dateProperty;
		}

		public SimpleStringProperty getTimeProperty() {
			return timeProperty;
		}

		public SimpleIntegerProperty getSinglesProperty() {
			return singlesProperty;
		}

		public SimpleIntegerProperty getMembersProperty() {
			return membersProperty;
		}

		public SimpleIntegerProperty getGroupsProperty() {
			return groupsProperty;
		}
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
