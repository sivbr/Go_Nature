package PMCapacity;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Enums.Command;
import Enums.ObjectKind;
import Enums.ReqStatus;
import Enums.TableName;
import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.MessageToServer;
import logic.Order;
import logic.Park;
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
public class CapacityParkManagerController implements Initializable {
	// Class variables *************************************************
	private Stages stage = new Stages();
	public static int count = 1000;

	@FXML
	private ImageView images;// = new ImageView(image)

	@FXML
	private Button btnExit = null;

	@FXML
	private Button btnMinimize = null;

	@FXML
	private Button btnLogOff = null;

	@FXML
	private Button btnHome;

	@FXML
	private Button btnSave;
	@FXML
	private RadioButton radio1;
	@FXML
	private RadioButton radio2;
	@FXML
	private RadioButton radio3;
	@FXML
	private DatePicker dpFromDate = new DatePicker();
	@FXML
	private DatePicker dpToDate = new DatePicker();

	@FXML
	private TextField NewField;
	@FXML
	private TextField NewField2;
	@FXML
	private TextField NewField3;
	@FXML
	private Label updatedLabel;
	@FXML
	private Label OccasionalLabel;

    @FXML
    private Label lblTime;
    
	@FXML
	private Label lblWelcome = new Label();

	@FXML
	private Label lblParkName = new Label();

	private boolean flag1 = false;
	private boolean flag2 = false;
	private boolean flag3 = false;
	private String s1="";
	private String p1="";

	// Instance methods ************************************************

	/**
	 * This method update the capacity in the park according to park name and present the capacity to park manager
	 * @param event
	 * @throws Exception
	 */
	private void setCapcity() {
		String parkName;
		parkName = ChatClient.worker.getPark().toString();
		if (parkName.toString().equals("Sharon")) {
			MessageToServer mts = new MessageToServer(Command.Read, TableName.parks, "Sharon");
			mts.toAccept();
			s1 = String.valueOf(ChatClient.park.getCurrentAllVisitors());
			String s2 = String.valueOf(ChatClient.park.getMaxCapacity());
			updatedLabel.setText(s1.toString() + "/" + s2.toString());
			 p1 = String.valueOf(ChatClient.park.getCurrentOccasionalVisitors());	
			String p2 = String.valueOf(ChatClient.park.getOccasionalVisitors());
			OccasionalLabel.setText(p1.toString() + "/" + p2.toString());
		} else if (parkName.toString().equals("Carmel")) {
			MessageToServer mts = new MessageToServer(Command.Read, TableName.parks, "Carmel");
			mts.toAccept();
			s1 = String.valueOf(ChatClient.park.getCurrentAllVisitors());
			String s2 = String.valueOf(ChatClient.park.getMaxCapacity());
			updatedLabel.setText(s1.toString() + "/" + s2.toString());
			 p1 = String.valueOf(ChatClient.park.getCurrentOccasionalVisitors());
			 System.out.println(p1);
			String p2 = String.valueOf(ChatClient.park.getOccasionalVisitors());
			OccasionalLabel.setText(p1.toString() + "/" + p2.toString());
		} else if (parkName.toString().equals("HaiPark")) {
			MessageToServer mts = new MessageToServer(Command.Read, TableName.parks, "HaiPark");
			mts.toAccept();
			s1 = String.valueOf(ChatClient.park.getCurrentAllVisitors());
			String s2 = String.valueOf(ChatClient.park.getMaxCapacity());
			updatedLabel.setText(s1.toString() + "/" + s2.toString());
			 p1 = String.valueOf(ChatClient.park.getCurrentOccasionalVisitors());
			String p2 = String.valueOf(ChatClient.park.getOccasionalVisitors());
			OccasionalLabel.setText(p1.toString() + "/" + p2.toString());
		}
	}
	/**
	 * This method check if park manager request is valid-if the request contain only numbers
	 * @param event
	 * @throws Exception
	 */
	public boolean isValidRequest(String request) {
		String ePattern = "^[0-9]{1,4}$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(request);
		return m.matches();
	}
	/**
	 * This method check if park manager request is valid-if the update discount request contain only valid percentage
	 * @param event
	 * @throws Exception
	 */
	public boolean isValidDiscount(String request) {
		String ePattern = "^[0-9]{1,2}$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(request);
		return m.matches();
	}
	/**
	 * This method check if park manager request is valid-if the update discount request is not between dates that was request already
	 * @param event
	 * @throws Exception
	 */
	public boolean isValidDate(String startDate,String toDate) throws Exception 
	{ 
		String parkName=ChatClient.worker.getPark();
		LocalDate sd=LocalDate.parse(startDate);
		LocalDate td=LocalDate.parse(toDate);
		MessageToServer mts = new MessageToServer();
		mts.addReportInfo("SELECT * FROM requests WHERE parkName= "+"\"" + parkName +"\" and type= \"UpdateDiscount\" and status!= \""+ReqStatus.Rejected+"\";");
		mts.toAccept();	//
		System.out.println(ChatClient.list);
		Request requests[] = new Request[ChatClient.list.size()];
		if (!ChatClient.list.toString().equals("[]")) {
		for(int i=0;i<requests.length;i++)
			requests[i]=new Request(ChatClient.list.get(i));
		
		for(int i=0;i<requests.length;i++)
		{
			LocalDate fromDate1=LocalDate.parse(requests[i].getStartDate());
			LocalDate toDate1=LocalDate.parse(requests[i].getEndDate());
			
			/*Compare the dates that park manger insert to the dates in the sql table*/
			if(sd.isBefore(toDate1)&&sd.isAfter(fromDate1))
				return false;
			if(td.isBefore(toDate1)&&td.isAfter(fromDate1))
				return false;
			if(sd.equals(fromDate1)||sd.equals(toDate1)||td.equals(fromDate1)||td.equals(toDate1))
				return false;
		}
		}
		return true;
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
	 * This method work when the user click on send request button
	 * @param event
	 * @throws Exception
	 */
	public void getSave(ActionEvent event) throws Exception {
		String date = lblTime.getText();
		String parkName = ChatClient.worker.getPark().toString();
		String date_parkName = date + "_" + parkName;
		System.out.println(date_parkName);
		StringBuilder infoErrors = new StringBuilder();
		s1 = String.valueOf(ChatClient.park.getCurrentAllVisitors());
		p1 = String.valueOf(ChatClient.park.getCurrentOccasionalVisitors());
		MessageToServer mts = new MessageToServer();
		mts.addReportInfo("SELECT * FROM requests");
		mts.toAccept();
		
		/*If the request type is update capacity*/
		if (flag1 == true) 
		{
			MessageToServer mts4 = new MessageToServer();
			mts4.addReportInfo("SELECT * FROM requests WHERE requestDate="+"\""+date+"\""+" AND parkName="+"\""+parkName+"\" AND type="+"\""+"OccasionalCapcity"+"\";");
			mts4.toAccept();
			/*Check if the field is empty*/
			if (NewField.getText().isEmpty()) {
				infoErrors.append("- Please enter your updated capacity request.\n");
			/*Check if the request contain only numbers*/
			} else if (!(isValidRequest(NewField.getText()))) {
				infoErrors.append("- Invalid request.Please insert numbers only\n");
			}//else if(s1.compareTo(NewField.getText())>0) 
				//infoErrors.append("- Invalid request\n");
			
			/*Allow to park manager send request one time in day*/
			else if (!(ChatClient.list.get(0).isEmpty()))
				infoErrors.append("- You sent request to DM today and the status is Waiting\n");
			
			MessageToServer mts1 = new MessageToServer(Command.ReadLastRecord,TableName.requests,"null");	
			mts1.toAccept();
			/*Initialize the request id to start with 1000 number request*/
			if (ChatClient.objectType != ObjectKind.Request)
				count = 1000;

			else {
				/*In each request the request id change*/
				count = ChatClient.request.getRequestID();
				count++;
			}
			/*If there was error show pop up message*/
			if (infoErrors.length() > 0) 
			{
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Required Fields Empty or Wrong");
				alert.setContentText(infoErrors.toString());
				alert.showAndWait();
			}
			/*If there is no errors*/
			else
				{	
					mts4 = new MessageToServer(Command.Add,TableName.requests,"NULL");	
			
					try {
						mts4.addObject(new Request(count, parkName,"UpdateCapcity", "----------", "----------",NewField.getText().toString(),date.toString() ,ReqStatus.Waiting));
					} catch (Exception e) {
						e.printStackTrace();
					}
					mts4.toAccept();
					/*Pop up message that ask the user if he want send another request or go back tp home page*/
					 Alert alert2 = new Alert(AlertType.CONFIRMATION);
					 alert2.setTitle("CONFIRMATION");
					 alert2.setHeaderText("Your request sand to department manager");
					 alert2.setContentText("Request Number: "+count+"\nFor sand another request press OK.\nFor go back to home page press Cancel");
					 Optional<ButtonType> result = alert2.showAndWait();
					 if (result.get() == ButtonType.OK)// ... user chose OK
						stage.OpenStage(event, "/PMCapacity/CapacityParkManager","Capacity Park Manager");
					 else if(result.get() == ButtonType.CANCEL)// ... user chose CANCEL
						stage.OpenStage(event, "/PMHome/ManagerHome","Manager Home");
				}

		}
		/*If the request type is occasional capacity*/
		if (flag2 == true) {
			MessageToServer mts4 = new MessageToServer();
			mts4.addReportInfo("SELECT * FROM requests WHERE requestDate=" + "\"" + date + "\"" + " AND parkName="
					+ "\"" + parkName + "\" AND type=" + "\"" + "OccasionalCapcity" + "\";");
			mts4.toAccept();
			/*Check if the field is empty*/
			if (NewField2.getText().isEmpty()) {
				infoErrors.append("- Please enter your updated capacity request.\n");
			/*Check if the request contain only numbers*/
			} else if (!(isValidRequest(NewField2.getText()))) {
				infoErrors.append("- Invalid request.Please insert numbers only\n");
			}//else if(p1.compareTo(NewField2.getText())>0) 
				//infoErrors.append("- Invalid request.\n");
			/*Allow to park manager send request one time in day*/
			else if (!(ChatClient.list.get(0).isEmpty()))
				infoErrors.append("- You sent request to DM today and the status is Waiting\n");
			
			MessageToServer mts1 = new MessageToServer(Command.ReadLastRecord,TableName.requests,"null");	
			mts1.toAccept();
			/*Initialize the request id to start with 1000 number request*/
			if (ChatClient.objectType != ObjectKind.Request)
				count = 1000;
			/*In each request the request id change*/
			else {
				count = ChatClient.request.getRequestID();
				count++;
			}
			/*If there was error show pop up message*/
			if (infoErrors.length() > 0)
			{
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Required Fields Empty or Wrong");
				alert.setContentText(infoErrors.toString());
				alert.showAndWait();
			}
			/*If there is no errors*/
			else
			{
				 mts4 = new MessageToServer(Command.Add, TableName.requests, "NULL");
				try {
			
					mts4.addObject(new Request(count,  parkName.toString(),"OccasionalCapcity","----------","---------", NewField2.getText().toString(), date.toString(),ReqStatus.Waiting));
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				mts4.toAccept();
				/*Pop up message that ask the user if he want send another request or go back tp home page*/
				Alert alert2 = new Alert(AlertType.CONFIRMATION);
				alert2.setTitle("CONFIRMATION");
				alert2.setHeaderText("Your request sand to department manager");
				alert2.setContentText("Request Number: " + count
						+ "\nFor sand another request press OK.\nFor go back to home page press Cancel");
				Optional<ButtonType> result = alert2.showAndWait();
				if (result.get() == ButtonType.OK)// ... user chose OK
					stage.OpenStage(event, "/PMCapacity/CapacityParkManager", "Capacity Park Manager");
				else if (result.get() == ButtonType.CANCEL)// ... user chose CANCEL
					stage.OpenStage(event, "/PMHome/ManagerHome", "Manager Home");
			}
	
		}
		/*If the request type is update discount*/
		if (flag3 == true) 
		{
			MessageToServer mts4 = new MessageToServer();
			mts4.addReportInfo("SELECT * FROM requests WHERE requestDate=" + "\"" + date + "\"" + " AND parkName="
				+ "\"" + parkName + "\" AND type=" + "\"" + "UpdateDiscount" + "\";");
			mts4.toAccept();
			
		
			if (NewField3.getText().isEmpty() && dpFromDate.getValue() == null && dpToDate.getValue() == null)
				infoErrors.append("- Please fill all the fields.\n");
			else 
			{
				if (NewField3.getText().isEmpty()) {
					infoErrors.append("- Please enter your updated discount request.\n");
				} else if (dpFromDate.getValue() == null) {
					infoErrors.append("- Please ente date of begining \n");
				} else if (dpToDate.getValue() == null) {
					infoErrors.append("- Please enter date of ending .\n");
				} else if (!(isValidDiscount(NewField3.getText()))) {
					infoErrors.append("- Invalid request.Please insert numbers between 0-99 only\n");
				}else  if (!(ChatClient.list.get(0).isEmpty())) {
					infoErrors.append("- You sent request to DM today and the status is Waiting\n");
				}else if(dpFromDate.getValue().compareTo(dpToDate.getValue())>0) { 
					infoErrors.append("- Invalid dates\n");}
				else if(!(isValidDate(dpFromDate.getValue().toString(),dpToDate.getValue().toString())))
					infoErrors.append("- You sent request to DM in this dates\n");
			
			}
			MessageToServer mts1 = new MessageToServer(Command.ReadLastRecord,TableName.requests,"null");	
			mts1.toAccept();
			
			if (ChatClient.objectType != ObjectKind.Request)
				count = 1000;
			else 
			{
				count = ChatClient.request.getRequestID();
				count++;
				System.out.println(ChatClient.request.getRequestID());
			}
			
			if (infoErrors.length() > 0)
			{
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Required Fields Empty or Wrong");
				alert.setContentText(infoErrors.toString());
				alert.showAndWait();
			}
			else
			{
		
				 mts4 = new MessageToServer(Command.Add, TableName.requests, "NULL");
				 try {
					mts4.addObject(new Request(count, parkName,"UpdateDiscount",dpFromDate.getValue().toString() ,dpToDate.getValue().toString(), NewField3.getText(),date,ReqStatus.Waiting));
				 } catch (Exception e) {
					 e.printStackTrace();
				 }
				 mts4.toAccept();
					Alert alert3 = new Alert(AlertType.CONFIRMATION);
					alert3.setTitle("CONFIRMATION");
					alert3.setHeaderText("Your request sand to department manager");
					alert3.setContentText("Request Number: " + count
							+ "\nFor sand another request press OK.\nFor go back to home page press Cancel");
					Optional<ButtonType> result = alert3.showAndWait();
					if (result.get() == ButtonType.OK)// ... user chose OK
						stage.OpenStage(event, "/PMCapacity/CapacityParkManager", "Capacity Park Manager");
					else if (result.get() == ButtonType.CANCEL)// ... user chose CANCEL
						stage.OpenStage(event, "/PMHome/ManagerHome", "Manager Home");
			}

		}

	}
	/**
	 * This method set which request type is selected
	 * @param event
	 * @throws Exception
	 */
	public void chooseRadio(ActionEvent event) throws Exception {
		/*The flags indicate which request type selected*/
		if (radio1.isSelected()) {
			flag1 = true;
			flag2 = false;
			flag3 = false;
			NewField.setVisible(true);
			NewField2.setVisible(false);
			NewField3.setVisible(false);
		}
		if (radio2.isSelected()) {
			flag2 = true;
			flag1 = false;
			flag3 = false;
			NewField.setVisible(false);
			NewField2.setVisible(true);
			NewField3.setVisible(false);
		}
		if (radio3.isSelected()) {
			flag3 = true;
			flag1 = false;
			flag2 = false;
			NewField.setVisible(false);
			NewField2.setVisible(false);
			NewField3.setVisible(true);
			dpFromDate.setDisable(false);
			dpToDate.setDisable(false);

		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		stage.setImage(images, "workerDetails");
		stage.SetTime(lblTime);
		setCapcity();
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " + ChatClient.worker.getlastName());
		lblParkName.setText("Park name: " + ChatClient.worker.getPark());
		ToggleGroup group = new ToggleGroup();
		radio1.setToggleGroup(group);
		radio2.setToggleGroup(group);
		radio3.setToggleGroup(group);
		NewField.setVisible(false);
		NewField2.setVisible(false);
		NewField3.setVisible(false);
		dpFromDate.setDisable(true);
		dpToDate.setDisable(true);

	}

}
