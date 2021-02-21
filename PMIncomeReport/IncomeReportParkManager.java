 package PMIncomeReport;

import java.awt.Insets;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import DMCreateReport.CreateReportDepartmentManeger;

//import com.sun.tools.javac.util.List;

import Enums.Command;
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
import logic.OrderDetails;
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
	private Label lblWelcome = new Label();

	@FXML
	private Label lblTime= new Label();
		
	ObservableList<String> list ;
	
    @FXML
    private Label lblDates;
    
	@FXML
    ListView<String> lv = new ListView<>(list);
	
	
	//Instance methods ************************************************
	
	/**
	 * This method close the Client UI buttom
	 * @param event
	 * @throws Exception
	 */
	
	private void setDetails() throws Exception {
		String date;
		ArrayList<String> al = new ArrayList<String>();	
		int month = PMCreateReport.CreateReportParkManeger.monthNumber;
		String yearString = PMCreateReport.CreateReportParkManeger.year;
		int year = Integer.parseInt(yearString);
		String park = ChatClient.worker.getPark();
		String income = "0";
		MessageToServer mts = new MessageToServer();
		mts.addReportInfo("SELECT * FROM orders");
		mts.toAccept();	//
				
		//ChatClient.list- list of strings from report command		
			
		//create orders array from report		
		Order orders[] = new Order[ChatClient.list.size()];
		for(int i=0;i<orders.length;i++)
		{
			orders[i]=new Order(ChatClient.list.get(i));
			if(orders[i].getStatus().equals("fulified") )
			{
				if (orders[i].getVisitMonth() == month && orders[i].getVisitYear() == year)
				{
					if (park.equals(orders[i].getPark())) 
					{
						al.add(orders[i].stringToIncomeReport());
					}
				}
			}
		}
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT SUM(totalPayment)\r\n" + "FROM go_nature.orders\r\n" + "WHERE status = \"fulified\" ");
		query.append(" and park = \""+park+"\"");
		query.append(" and visitMonth = "+month);
		query.append(" and visitYear = "+year);
		query.append(";");
		System.out.println(query);
		try {
			mts.addReportInfo(query.toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mts.toAccept();

		if (ChatClient.list.get(0).equals("null"))
		{
			income = "0";
		}
		else
		{
			income = ChatClient.list.get(0);
		}
		al.add("		Total Income:							"+income);
		list = FXCollections.observableArrayList(al);
		lv.setItems(list);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		date = (LocalDate.now().format(formatter));
		
		Request r1=new Request();
		r1.setRequestID(1000);
		r1.setParkName(park);
		r1.setFromID("01/"+month+"/"+year);
		r1.setToID("31/"+month+"/"+year);
		r1.setRequest("65");
		r1.setType("Income Report");
		r1.setStatus("Ready");
		r1.setRequestDate(date);
		r1.setSql("SELECT * FROM orders where visitMonth = \""+r1.getMonth(r1.getFromID())+"\";");

		
		mts = new MessageToServer(Command.Add,TableName.requests,"NULL");	
		mts.addObjectr1();
		mts.toAccept();

	}
    
    
    

	public void getExit(ActionEvent event) throws Exception {
		stage.outFromSystem(event,"Exit");		
	}
	
	public void getLogOff(ActionEvent event) throws Exception {
		stage.outFromSystem(event,"LogOff");		
	}

	public void getMinimize(ActionEvent event) throws Exception {
		stage.Minimize(event);		
	}

	
	public void getHome(ActionEvent event) throws Exception {
		stage.OpenStage(event, "/PMCreateReport/CreateReportParkManeger","Create Report");
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		String month = PMCreateReport.CreateReportParkManeger.month;
		String year = PMCreateReport.CreateReportParkManeger.year;
		String park = ChatClient.worker.getPark();
		stage.setImage(images,"worker");
		if(ChatClient.member != null)
			lblWelcome.setText("Hi, " + ChatClient.member.getfirstName() + " " +park);		
		stage.SetTime(lblTime);
		
		lblDates.setText(park+"			"+month+" "+year);
		try {
			setDetails();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
}
