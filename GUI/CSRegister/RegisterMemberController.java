package CSRegister;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Enums.Command;
import Enums.ObjectKind;
import Enums.Role;
import Enums.TableName;
import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.Member;
import logic.MessageToServer;
import logic.Payment_detail;
import logic.Visitor;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
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
public class RegisterMemberController implements Initializable {
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
    private Button btnClear=null;
    
    @FXML
    private Button btnSave=null;
    
    @FXML
    private RadioButton radioBtnCredit;

    @FXML
    private RadioButton radioBtnCash;
    
    @FXML
    private Rectangle shpCreditBackground;

    @FXML
    private Label lblCreditCardDetails;

    @FXML
    private Label lblCardNumber;

    @FXML
    private TextField txtC1;

    @FXML
    private TextField txtC2;

    @FXML
    private TextField txtC3;

    @FXML
    private TextField txtC4;

    @FXML
    private Label lblCreditID;

    @FXML
    private TextField txtCreditID;

    @FXML
    private Label lblExpireDate;

    @FXML
    private Label lblS1;

    @FXML
    private Label lblL3;

    @FXML
    private Label lblL2;

    @FXML
    private Label lblL1;

    @FXML
    private TextField txtCVV;

    @FXML
    private Label lblCVV;
    
    @FXML
    private Label lblStar1;

    @FXML
    private Label lblStar2;

    @FXML
    private Label lblStar3;

    @FXML
    private Label lblStar4;
    
    @FXML
    private Spinner<Integer> spinnerFamilyMembers;
    
    @FXML
    private TextField txtPhone;
    
    @FXML
    private TextField txtID;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtMail;
    
    @FXML
    private ComboBox<String> cmbPhone;
    
    @FXML
    private ComboBox<String> cmbMM;
    
    @FXML
    private ComboBox<String> cmbYY;
    
    @FXML
    private CheckBox chkGuide;
    
    @FXML
    private Label lblTime;

    @FXML
    private Label lblWelcome;
    
    ObservableList<String> list;

    ToggleGroup group;

    private static int currentYear;
    
    private boolean flagCredit, flagCreateMember,flagVisitor, flagError, flagError1;
    
    private static int mNumber;
	
    private static String CreditCardNumber, PhoneNumber,ExpireDate, guide, payType;
    
    private static MessageToServer mcs;
    
    Alert VaildId = new Alert(AlertType.NONE,  "ID number already member.",ButtonType.CLOSE);
    
    
    
	private Alert clearAlert = new Alert(AlertType.CONFIRMATION);
	//Instance methods ************************************************
	
	/**
	 * This method close the Client UI buttom
	 * @param event
	 * @throws Exception
	 */
	public void getExit(ActionEvent event) throws Exception {
		stage.outFromSystem(event,"Exit");		
	}
	
	public void getLogOff(ActionEvent event) throws Exception {
		stage.outFromSystem(event,"LogOff");		
	}

	public void getMinimize(ActionEvent event) throws Exception {
		stage.Minimize(event);		
	}
	
	/**
	 * This method take the worker to his home page when press button.
	 * @param event
	 * @throws Exception
	 */
	public void getHome(ActionEvent event) throws Exception 
	{
		//if(ChatClient.worker.getRole() == Role.ParkEmployee)
			//stage.OpenStage(event, "/PEHome/ParkEmployee","Park Employee");
		if (ChatClient.worker.getRole() == Role.CustomerService)
			stage.OpenStage(event, "/CSHome/CustomerService","Customer Service");
		else if (ChatClient.worker.getRole() == Role.ParkManager)
			stage.OpenStage(event, "/PMHome/ManagerHome","Manager Home");
		else if (ChatClient.worker.getRole() == Role.DepartmentManager)
			stage.OpenStage(event, "/DMHome/DepartmentManager","Department Manager");
	}
	/**
	 * This method checks if the user is already a member or a visitor. 
	 * If not, it checks if all the details are valid and if yes, add the member into the DB.
	 * If the detail are wrong- there is a popup.
	 * @param event
	 * @throws Exception
	 */
	public void getSave(ActionEvent event) throws Exception 
	{ 
		flagError=true;
		flagError1=false;
		flagVisitor = false;
		flagCreateMember = false;
		//Validations validations = new Validations();
		StringBuilder infoErrors = new StringBuilder();	
		
		if  (txtID.getText().isEmpty()) 
		{
			infoErrors.append("- Please enter ID number.\n");
		}
		else if(!(isValidIDNumber(txtID.getText())))
		{
			infoErrors.append("- Invalid ID number.\n");
			txtID.clear();
		}
		mcs = new MessageToServer(Command.Read,TableName.members,txtID.getText());
		mcs.toAccept();
		if(ChatClient.objectType==ObjectKind.Member )
		{
			VaildId.show();
			flagError=false;
		}	
		mcs = new MessageToServer(Command.Read,TableName.visitors,txtID.getText());
		mcs.toAccept();
		if(ChatClient.objectType==ObjectKind.Visitor && !flagVisitor)
		{
			Alert alert3 = new Alert(AlertType.CONFIRMATION);
			alert3.setTitle("NOTICE");
			alert3.setHeaderText("ID "+txtID.getText()+ " is a visitor");
			alert3.setContentText("If you want to make him a member press OK.\nElse press Cancel\n");
			Optional<ButtonType> result = alert3.showAndWait();
			if (result.get() == ButtonType.OK)// ... user chose OK
			{
				flagError=true;
				flagError=false;
				flagVisitor = true;
				Visitor v1;
				MessageToServer mts = new MessageToServer(Command.Read,TableName.visitors,txtID.getText());
				mts.toAccept();
				v1 = new Visitor(ChatClient.visitor);
				txtFirstName.setText(v1.getfirstName());
				txtLastName.setText(v1.getlastName());
				txtMail.setText(v1.getMail());
				txtID.setText(v1.getID());
				String visitorPhone = v1.getPhone();
				cmbPhone.setValue(visitorPhone.substring(0,3));
				txtPhone.setText(visitorPhone.substring(3,10));
				alert3.close();
			}
			else if (result.get() == ButtonType.CANCEL)// ... user chose CANCEL
			{
				flagVisitor = false;
				flagError = true;
				txtID.clear();
				alert3.close();
			}
		}
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
		if  (txtPhone.getText().isEmpty()) 
		{
			infoErrors.append("- Please enter phone number.\n");
		}
		else if(!(isValidPhoneNumber((txtPhone.getText())))) 
		{
			infoErrors.append("- Invalid phone number.\n");
		}
		if  (txtMail.getText().isEmpty()) 
		{
			infoErrors.append("- Please enter email.\n");
		}
		else if (!(isValidEmailAddress((txtMail.getText())))) 
		{
			infoErrors.append("- Invalid email address.\n");
		}
		
		if(!(radioBtnCredit.isSelected()) && !(radioBtnCash.isSelected()))
		{
			infoErrors.append("- Please select payment method.\n");
		}
		
		
        if (infoErrors.length() > 0 && flagError) {
        	flagCreateMember = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty or Wrong");
            alert.setContentText(infoErrors.toString());

            alert.showAndWait();
        }
        else
        {
        	PhoneNumber = new String (cmbPhone.getValue()+txtPhone.getText());
        	if(cmbPhone.getValue()==null)
        	{
        		PhoneNumber = "050"+txtPhone.getText();
        	}
			ArrayList<String> memberDetails =new ArrayList<String>();
			memberDetails.add(txtFirstName.getText());
			memberDetails.add(txtLastName.getText());
			memberDetails.add(txtID.getText());
			memberDetails.add(PhoneNumber);
			memberDetails.add(txtMail.getText());
			memberDetails.add(String.valueOf(spinnerFamilyMembers.getValue()));
			if(chkGuide.isSelected())
			{
				guide = "yes";
				memberDetails.add("G");
			}
			else
			{
				guide = "no";
			}
			System.out.println(memberDetails);
        }
		if(radioBtnCash.isSelected())
		{	
			payType = "cash";
			flagCreateMember=true;
			flagCredit = false;
		}
			
		else if(radioBtnCredit.isSelected())
		{	
			flagError1 = true;
			payType = "credit";
			flagCredit = true;
			StringBuilder cardErrors = new StringBuilder();		
			if  (txtC1.getText().isEmpty() && txtC2.getText().isEmpty() && txtC3.getText().isEmpty() && txtC4.getText().isEmpty()) 
			{
				cardErrors.append("- Please enter credit card number.\n");
			}
			else if(!(isValidCreditCard(txtC1.getText())) || !(isValidCreditCard(txtC2.getText())) || !(isValidCreditCard(txtC3.getText())) || !(isValidCreditCard(txtC4.getText())))
			{
				cardErrors.append("- Invalid credit card number.\n");
			}
			if(cmbMM.getValue()==null && cmbYY.getValue()==null)
			{
				cardErrors.append("- Please set expire date.\n");
			}
			if(txtCVV.getText().isEmpty())
			{
				cardErrors.append("- Please enter CVV number.\n");
			}
			else if(!(isValidCVV(txtCVV.getText())))
			{
				cardErrors.append("- Invalid CVV number.\n");
			} 
			if  (txtCreditID.getText().isEmpty()) 
			{
				cardErrors.append("- Please enter ID number.\n");
			}
			else if(!(isValidIDNumber(txtCreditID.getText())))
			{
				cardErrors.append("- Invalid ID number.\n");
			}
			
			
	        if (cardErrors.length() > 0 && flagError1) {
	        	flagCreateMember=false;
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Warning");
	            alert.setHeaderText("Required Fields Empty or Wrong");
	            alert.setContentText(cardErrors.toString());

	            alert.showAndWait();
	        }
	        else
	        {
				CreditCardNumber = new String (txtC1.getText()+txtC2.getText()+txtC3.getText()+txtC4.getText());
				ExpireDate = new String (cmbMM.getValue()+"/"+cmbYY.getValue());
				ArrayList<String> CreditCard =new ArrayList<String>();
				CreditCard.add(CreditCardNumber);
				CreditCard.add(ExpireDate);
				CreditCard.add(txtCVV.getText());
				CreditCard.add(txtCreditID.getText());
				flagCreateMember=true;
	        }
        }
		
		if(flagCreateMember || flagVisitor)
		{
			MessageToServer mts = new MessageToServer(Command.ReadLastRecord,TableName.members,"null");	
			mts = new MessageToServer();
			mts.addReportInfo("SELECT max(memberNumber) FROM go_nature.members");
			mts.toAccept();	
			//ChatClient.list- list of strings from report command		
			mNumber = Integer.parseInt(ChatClient.list.get(0))+1;
			 
	
		  	mts = new MessageToServer(Command.Add,TableName.members,"NULL");	
			// Member(String ID, String firstName, String lastName, String mail, String phone, int numberOfFamilyMembers,String payType, String memberNumber, String organizationGroup, int online)
			mts.addObject((new Member(txtID.getText(),txtFirstName.getText(),txtLastName.getText(),txtMail.getText(),PhoneNumber,spinnerFamilyMembers.getValue(),payType,mNumber+"",guide,0)));
			mts.toAccept();
			if(radioBtnCredit.isSelected())
			{
				if(flagCredit)
				{
					 mts = new MessageToServer(Command.Add,TableName.payment_details,"NULL");	
					// Payment_detail(String memberID, String cardNumber, String cardId, String cardValid, String cardCVC)
					mts.addObject((new Payment_detail(txtID.getText(),CreditCardNumber,txtCreditID.getText(),ExpireDate,txtCVV.getText())));
					mts.toAccept();
				}
			}
			
			Alert alert3 = new Alert(AlertType.CONFIRMATION);
			alert3.setTitle("CONFIRMATION");
			alert3.setHeaderText("Member "+mNumber+ " Add Successfully!");
			alert3.setContentText("To add another member press OK.\nTo go back to home page press Cancel\n");
			Optional<ButtonType> result = alert3.showAndWait();
			if (result.get() == ButtonType.OK)// ... user chose OK
			{
				stage.OpenStage(event, "/CSRegister/RegisterMember","Register New Member");
			}
			else if (result.get() == ButtonType.CANCEL)// ... user chose CANCEL
			{
				stage.GoHome(event);
			}
			
			
			//* *****Delete*****   
			mts = new MessageToServer(Command.Delete,TableName.visitors,txtID.getText());	
			mts.toAccept();
			
		}
        // No errors
	}
	

	/**
	 * This method clears all the details entered when press button clear
	 * @param event
	 * @throws Exception
	 */
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
			txtMail.clear();
			SpinnerValueFactory<Integer> familyMemberFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
			this.spinnerFamilyMembers.setValueFactory(familyMemberFactory);
		}
	}
	
	/**
	 * This method checks if the radiobutton "Credit" is selected.
	 * If yes, it's shows all the relevant txt boxes.
	 * If not, it's hide them.
	 * @param event
	 * @throws Exception
	 */
    @FXML
    void getPaymantMethod(ActionEvent event) throws Exception{
		btnSave.setVisible(true);
		btnClear.setVisible(true);
    	group = new ToggleGroup();
    	radioBtnCash.setToggleGroup(group);
    	radioBtnCredit.setToggleGroup(group);
    	
		shpCreditBackground.setVisible(radioBtnCredit.isSelected());
		lblCreditCardDetails.setVisible(radioBtnCredit.isSelected());	
		lblCardNumber.setVisible(radioBtnCredit.isSelected());
		txtC1.setVisible(radioBtnCredit.isSelected());
		txtC2.setVisible(radioBtnCredit.isSelected());	
		txtC3.setVisible(radioBtnCredit.isSelected());
		txtC4.setVisible(radioBtnCredit.isSelected());
		lblCreditID.setVisible(radioBtnCredit.isSelected());	
		txtCreditID.setVisible(radioBtnCredit.isSelected());
		lblExpireDate.setVisible(radioBtnCredit.isSelected());
		cmbMM.setVisible(radioBtnCredit.isSelected());
		cmbYY.setVisible(radioBtnCredit.isSelected());
		lblS1.setVisible(radioBtnCredit.isSelected());	
		lblL1.setVisible(radioBtnCredit.isSelected());
		lblL2.setVisible(radioBtnCredit.isSelected());
		lblL3.setVisible(radioBtnCredit.isSelected());	
		txtCVV.setVisible(radioBtnCredit.isSelected());
		lblCVV.setVisible(radioBtnCredit.isSelected());
		lblStar1.setVisible(radioBtnCredit.isSelected());	
		lblStar2.setVisible(radioBtnCredit.isSelected());
		lblStar3.setVisible(radioBtnCredit.isSelected());	
		lblStar4.setVisible(radioBtnCredit.isSelected());
    }
	
    /**
     * this method initials the combo box of phone prefix
     */
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
    
    /**
     * This method initials the combo box of months
     */
    private void setMonthComboBox() {
		ArrayList<String> al = new ArrayList<String>();	
		al.add("01");
		al.add("02");
		al.add("03");
		al.add("04");
		al.add("05");
		al.add("06");
		al.add("07");
		al.add("08");
		al.add("09");
		al.add("10");
		al.add("11");
		al.add("12");
		list = FXCollections.observableArrayList(al);
		cmbMM.setItems(list);
	}
    
    /**
     * This method initials the combo box of year. it's check the current year and add thr next 8 years.
     */
    private void setYearComboBox() {
		ArrayList<String> years = new ArrayList<String>();	
		
		currentYear = LocalDate.now().getYear();
		
		years.add(String.valueOf(currentYear-2000));
		years.add(String.valueOf(currentYear-2000+1));
		years.add(String.valueOf(currentYear-2000+2));
		years.add(String.valueOf(currentYear-2000+3));
		years.add(String.valueOf(currentYear-2000+4));
		years.add(String.valueOf(currentYear-2000+5));
		years.add(String.valueOf(currentYear-2000+6));
		years.add(String.valueOf(currentYear-2000+7));
		years.add(String.valueOf(currentYear-2000+8));
	
		list = FXCollections.observableArrayList(years);
		cmbYY.setItems(list);
	}
    
    /**
     * This method checks if the email is valid - in email format xxx@xxx.xxx
     * @param email
     * @return
     */
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
 }
    
	    /**
	     * This method checks if the phone number contains exactly 7 digits 0-9.
	     * @param phone
	     * @return
	     */
    public boolean isValidPhoneNumber(String phone) {
        String ePattern = "^[0-9]{7}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(phone);
        return m.matches();
 }
    
    /**
     * This method checks if the ID number contains exactly 9 digits 0-9.
     * @param id
     * @return
     */
    public boolean isValidIDNumber(String id) {
        String ePattern = "^[0-9]{9}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(id);
        return m.matches();
 }
    
    /**
     * This method checks if the credit card number is valid. xxxx-xxxx-xxxx-xxxx
     * @param credit_card_number
     * @return
     */
    public boolean isValidCreditCard(String credit_card_number) {
        String ePattern = "^[0-9]{4}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(credit_card_number);
        return m.matches();
 }
    
    /**
     * This method checks if the CVV contains exactly 3 digits 0-9.
     * @param cvv
     * @return
     */
    public boolean isValidCVV(String cvv) {
        String ePattern = "^[0-9]{3}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(cvv);
        return m.matches();
 }
	
    /*
     * This method check if the name contains only letters a-b A-b.
     */
    public boolean isValidName(String name) {
        String ePattern = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(name);
        return m.matches();
    }
    
	
	public void start(Stage primaryStage) throws Exception {	
		stage.OpenCenterStage(primaryStage,"RegisterMember","Register member");			
	}
	
	/*
	 * This method initialize the screen with name, date, and initialize the combo boxes and the spinner.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		stage.setImage(images,"worker");
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " +ChatClient.worker.getlastName());	
		stage.SetTime(lblTime);
		setPhoneComboBox();
		SpinnerValueFactory<Integer> familyMemberFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15);
		this.spinnerFamilyMembers.setValueFactory(familyMemberFactory);
		this.spinnerFamilyMembers.setEditable(false);
		setMonthComboBox();
		setYearComboBox();
	}
}
