package VRegister;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Enums.Command;
import Enums.ObjectKind;
import Enums.TableName;
import LogIn.ClientLogInController;
import client.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import logic.MessageToServer;
import logic.Visitor;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
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
public class VisitorDetailsController implements Initializable {
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
    private Button btnHome;

    @FXML
    private Button btnClear;
    
    @FXML
    private Button btnSave;

	@FXML
	private Label lblTime= new Label();
	
	@FXML
	private Label lblHeader= new Label();
	
	 @FXML
	 private TextField txtPhone;
	 
	 @FXML
	 private TextField txtID;

	 @FXML
	 private TextField txtLastName;
	
	 @FXML
	 private TextField txtFirstName;
	
	 @FXML
	 private TextField txtEmail;
	 
	 @FXML
	 private ComboBox<String> cmbPhone;
	 
	 ObservableList<String> list;

	 private Alert foundMember = new Alert(AlertType.NONE,  "This id already member",ButtonType.CLOSE);
	 private Alert foundVisitor = new Alert(AlertType.NONE,  "This id already visitor",ButtonType.CLOSE);
	 private Alert clearAlert = new Alert(AlertType.CONFIRMATION);

	 private Visitor v1= new Visitor();
	//Instance methods ************************************************
	
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
	
	public void getSave(ActionEvent event) throws Exception 
	{
		StringBuilder infoErrors = new StringBuilder();		
		if  (txtFirstName.getText().isEmpty()) 
		{
			infoErrors.append("- Please enter first name.\n");
		}
		else if(!(isValidName(txtFirstName.getText())))
		{
			infoErrors.append("- Invalid first name.\n");
		}
		if  (txtLastName.getText().isEmpty()) 
		{
			infoErrors.append("- Please enter last name.\n");
		}
		else if(!(isValidName(txtLastName.getText())))
		{
			infoErrors.append("- Invalid last name.\n");
		}
		if  (txtID.getText().isEmpty()) 
		{
			infoErrors.append("- Please enter ID number.\n");
		}
		else if(!(isValidIDNumber(txtID.getText())))
		{
			infoErrors.append("- Invalid ID number.\n");
		}
		if  (txtPhone.getText().isEmpty()) 
		{
			infoErrors.append("- Please enter phone number.\n");
		}
		else if(!(isValidPhoneNumber((txtPhone.getText())))) 
		{
			infoErrors.append("- Invalid phone number.\n");
		}
		if  (txtEmail.getText().isEmpty()) 
		{
			infoErrors.append("- Please enter email.\n");
		}
		else if (!(isValidEmailAddress((txtEmail.getText())))) 
		{
			infoErrors.append("- Invalid email address.\n");
		}
		
        if (infoErrors.length() > 0 ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty or Wrong");
            alert.setContentText(infoErrors.toString());

            alert.showAndWait();
        }
        else 
        {
        	MessageToServer mcs;
        	String PhoneNumber = new String (cmbPhone.getValue()+txtPhone.getText());
        	if(cmbPhone.getValue()==null)
        	{
        		PhoneNumber = "050"+txtPhone.getText();
        	}
        	
        	mcs = new MessageToServer(Command.Read,TableName.members,txtID.getText());
			mcs.toAccept();
			
			if(ChatClient.objectType != ObjectKind.Member) {
				mcs = new MessageToServer(Command.Read,TableName.visitors,txtID.getText());
				mcs.toAccept();
				if(ChatClient.objectType != ObjectKind.Visitor) {
					
		        	ChatClient.visitor = new Visitor (txtID.getText(),txtFirstName.getText(),txtLastName.getText(),
		        			txtEmail.getText(), PhoneNumber, 1);
		        	v1 = ChatClient.visitor;
					mcs = new MessageToServer(Command.Add,TableName.visitors,null);
					mcs.addObject(ChatClient.visitor);
					mcs.toAccept();
					ChatClient.visitor = v1;
					
					ClientLogInController.objectType=ObjectKind.Visitor;
					stage.OpenStage(event, "/CNewOrder/NewOrder","Create New Order");
				}
				else
					foundVisitor.show();
			}
			else
				foundMember.show();
        }

        // No errors
		
	}
	
	public void getClear(ActionEvent event) throws Exception 
	{
		clearAlert.setTitle("Clear all fields");
		clearAlert.setHeaderText("Are you sure you want to clear all fields?");
		Optional<ButtonType> result = clearAlert.showAndWait();

		if(result.isPresent() && result.get() == ButtonType.OK) {
			txtFirstName.clear();
			txtLastName.clear();
			txtID.clear();
			txtPhone.clear();
			txtEmail.clear();
			}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		stage.setImage(images,"ClientDetails");
		stage.SetTime(lblTime);
		lblHeader.setText("Welcome, Traveler");
		setPhoneComboBox();
	}
	
    private void setPhoneComboBox() {
		ArrayList<String> al = new ArrayList<String>();	
		al.add("050");
		al.add("052");
		al.add("053");
		al.add("054");
		al.add("055");
		al.add("057");
		al.add("058");
		list = FXCollections.observableArrayList(al);
		cmbPhone.setItems(list);
	}
	
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
	    
    public boolean isValidPhoneNumber(String phone) {
        String ePattern = "^[0-9]{7}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(phone);
        return m.matches();
    }
    
    public boolean isValidIDNumber(String id) {
    	String ePattern = "^[0-9]{9}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(id);
        return m.matches();
    }
	
    public boolean isValidName(String name) {
        String ePattern = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(name);
        return m.matches();
    }
}
