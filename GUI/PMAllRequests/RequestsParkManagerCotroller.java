package PMAllRequests;

import java.net.URL;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.control.ButtonBar.ButtonData;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Order;
import logic.Request;
import javafx.event.ActionEvent;
import StagesFunc.Stages;

public class RequestsParkManagerCotroller implements Initializable {
	//Class variables *************************************************	
	private Stages stage = new Stages();
	
	@FXML
	private ImageView images;
	
	@FXML
	private Button btnExit=null;
	
    @FXML
    private Button btnMinimize=null;

    @FXML
    private Button btnLogOff=null;

    @FXML
    private Button btnHome=null;
    
	@FXML
	private Label lblTime= new Label();

	@FXML
	private Label lblWelcome= new Label();
	
	@FXML
	private Label lblParkName= new Label();
	
	@FXML
	private ListView<String> listView;
	
	ObservableList<String> list ;
	private ArrayList<String> reqs = new ArrayList<String>();
	private String chosenReq;
	//Instance methods ************************************************

	/**
	 * This method insert details to requests list view that park manager send to department manager and show the status request
	 * @throws Exception
	 */
	public void set() throws Exception {
		ArrayList<String> req = new ArrayList<String>();
		String parkName = ChatClient.worker.getPark().toString();
		
		MessageToServer mts = new MessageToServer();
		mts.addReportInfo("SELECT * FROM requests WHERE parkName="+"\""+parkName+"\";");
		mts.toAccept();	//
				
		Request requests[] = new Request[ChatClient.list.size()];
		for(int i=0;i<requests.length;i++)
		{
			requests[i]=new Request(ChatClient.list.get(i));
			if(!(requests[i].getType().contains("Report")))
			{
				/*If the request type is update discount*/
				if(requests[i].getType().equals("UpdateDiscount"))
					req.add(requests[i].toStringAsRowDiscount());
				else
					req.add(requests[i].toStringAsRow());
			}
		}
		ObservableList<String> items =FXCollections.observableArrayList (req);
		listView.setItems(items);
  
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
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub
		stage.setImage(images,"worker");
		stage.SetTime(lblTime);
		//lblTime.setVisible(false);
		//listView.setVisible(false);
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());
		lblParkName.setText( "Park name: "+ChatClient.worker.getPark());
		try {
			set();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
