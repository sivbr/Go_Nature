package CNewOrder;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import COrderDetails.ClientOrderDetails;
import Enums.Command;
import Enums.ObjectKind;
import Enums.TableName;
import LogIn.ClientLogInController;
import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import logic.Member;
import logic.MessageToServer;
import logic.Order;
import logic.Prices;
import logic.Visitor;
import logic.Waiting_list;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
public class ClientNewOrderController implements Initializable {
	//Class variables *************************************************		
	private Stages stage = new Stages();
		
    @FXML
    private ImageView images;// = new ImageView(image)
    
    private boolean forwaitinglist= false;

	Date choosendate = Date.valueOf(LocalDate.now());
	
	@FXML
	private Button btnExit=null;
	
    @FXML
    private Button btnMinimize=null;

    @FXML
    private Button btnLogOff=null;

    @FXML
    private Button btnHome=null;
     
    @FXML
    private  ComboBox<String> cmbPark = new ComboBox <String>();

    @FXML
    private ComboBox<String> cmbTime;
    
    @FXML
    private DatePicker dpDate = new DatePicker();
    
    @FXML
    Spinner<Integer> numVisitors = new Spinner<Integer>();
    
    @FXML
    private ComboBox<String> groupOrganized;

    @FXML
    private Button btnSave;
    
    @FXML
    private Label lblWelcome;
    
    @FXML
    private Label dateOfToday;
    
	public ObservableList<String> list;
	public ObservableList<String> list2;
	
	private static int payNow=0;
	
	public  Map<LocalDate, ArrayList<Integer>> AvaDates = new HashMap<LocalDate, ArrayList<Integer>>();

    private int Capacity; // Capacity at the chosen park

	
	//Instance methods ************************************************
    /**
  	  * Creating a map of unavailable dates&hours  - the keys : dates , the values: hours
  	  * @return
  	  */
  	 public boolean setAvaDates() {
  		String park = ChatClient.order.getPark();
  		
  		MessageToServer mts = new MessageToServer(Command.Read,TableName.parks,park);
  		mts.toAccept();
  		Capacity = ((int)((int)(ChatClient.park.getMaxCapacity() - ChatClient.park.getOccasionalVisitors())  / 3)-ChatClient.order.getNumOfVisitors());
  		
  		MessageToServer mcs = new MessageToServer();
  		try {
  			String s = "SELECT date FROM possible_dates WHERE ( time1 > val AND parkName = '"+park+"' "+ ")";
  			s=s.replace("val", String.valueOf(Capacity));
  			mcs.addReportInfo(s);
  			
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		mcs.toAccept();	//
  		System.out.println(ChatClient.list);
  		if(ChatClient.list.contains(""))
  			System.out.println("yes");
  		else
  		{
  		for (String d : ChatClient.list) {
  		String date = d;
         LocalDate localDate = LocalDate.parse(date);
  		if (AvaDates.get(LocalDate.parse(date)) == null) {
  			AvaDates.put(LocalDate.parse(date), new ArrayList<Integer>());
  		}
  		AvaDates.get(LocalDate.parse(date)).add(8);
  		}
  		}
  		
  	
  		mcs = new MessageToServer();
  		try {
  			String s = "SELECT date FROM possible_dates WHERE ( time2 > val AND parkName = '"+park+"' "+ ")";
  			s=s.replace("val", String.valueOf(Capacity));
  			mcs.addReportInfo(s);
  			
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		mcs.toAccept();	//
  		if(ChatClient.list.contains(""))
  			System.out.println("yes");

  		else {
  		for (String d : ChatClient.list) {
  		String date = d;
         LocalDate localDate = LocalDate.parse(date);
  		if (AvaDates.get(LocalDate.parse(date)) == null) {
  			AvaDates.put(LocalDate.parse(date), new ArrayList<Integer>());
  		}
  		AvaDates.get(LocalDate.parse(date)).add(12);
  		}
  		}
  		mcs = new MessageToServer();
  		try {
  			String s = "SELECT date FROM possible_dates WHERE ( time3 > val AND parkName = '"+park+"' "+ ")";
  			s=s.replace("val", String.valueOf(Capacity));
  			mcs.addReportInfo(s);
  			
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		mcs.toAccept();	//
  		if(ChatClient.list.contains(""))
  			System.out.println("yes");

  		else {
  		for (String d : ChatClient.list) {
  		String date = d;
         LocalDate localDate = LocalDate.parse(date);
  		if (AvaDates.get(LocalDate.parse(date)) == null) {
  			AvaDates.put(LocalDate.parse(date), new ArrayList<Integer>());
  		}
  		AvaDates.get(LocalDate.parse(date)).add(16);
  		}
  		}
  		 if (AvaDates.isEmpty())
  	       return false;
  		 else
  			 return true;
  	//	} 
  		
  	} 
    /**
     * Checking all fields of the controller
     * will show a pop up messages/alerts in many cases , such as:
     * the client is not a guide and chose organization group
     * empty fields
     * @return
     */
    	private boolean CheckProblematicFields ()
    	{	
    		StringBuilder infoErrors = new StringBuilder();		
    		if  (dpDate.getValue()==null) 
    		{
    			infoErrors.append("- Please choose a date.\n");
    		}

    		if  (cmbPark.getSelectionModel().getSelectedItem()==null) 
    		{
    			infoErrors.append("- Please choose a park.\n");
    		}
    		
    		if  (cmbTime.getSelectionModel().getSelectedItem()==null) 
    		{
    			infoErrors.append("- Please choose an hour.\n");
    		}
    	
    		if  (groupOrganized.getSelectionModel().getSelectedItem()==null) 
    		{
    			infoErrors.append("- Please choose if it's organized group or not.\n");
    		}
    		else if (groupOrganized.getSelectionModel().getSelectedItem().equals("YES") && (ChatClient.member.getOrganizationGroup()==null ||ChatClient.member.getOrganizationGroup().equals("no")))
    		{
    			infoErrors.append("- You can't choose organized group because you are not rgister as a guide  .\n If you are, please call us : 03-8888032 \n");

    		}

    		
            if (infoErrors.length() > 0 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Required Fields Empty or Wrong");
                alert.setContentText(infoErrors.toString());

                alert.showAndWait();
                return true;
            }
            return false;
    	}
    	

    	

    	
    	
    /**
     * according to the date & time of the user selection
     * and based on the map of unavailable dates&hours
     * checking if the user can place an order or not.
     * @return
     */
    	@SuppressWarnings("unlikely-arg-type")
    	private boolean CheckChosenTime() {
    		String t=cmbTime.getSelectionModel().getSelectedItem().toString();
    		String park = cmbPark.getSelectionModel().getSelectedItem().toString();
            Integer avaTickets;
            String time = null;
           
    		int visitTime =0;
    		
    		switch(t)
    		  {
    			  case "08:00-12:00":
    			  {
    				  visitTime = 8;
    				    break;

    			  }
    			  case "12:00-16:00":
    			  {
    				  visitTime=12;
    				    break;

    			  }
    			  case "16:00-20:00":
    			  {
    				  visitTime = 16;
    				    break;

    			  }
    		  }
    		
    		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    	     String Chosen = (dpDate.getValue()).format(formatter);
    	     if(setAvaDates()) {
    	    	 
    		for(Entry<LocalDate, ArrayList<Integer>> arr : AvaDates.entrySet()) {
    			String s = arr.getKey().format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
    			
    			if((Chosen.compareTo(s)==0 && arr.getValue().contains(visitTime)))
    			return true;	
    			}
    	}
    	     if (visitTime==8)
    	       	 time="time1";
    	        else if(visitTime==12)
    	       	 time="time2";
    	        else if (visitTime==16)
    	       	 time = "time3";
    		     String str = "SELECT val FROM possible_dates WHERE ( date = '"+dpDate.getValue().toString()+"' AND parkName = '"+park+"' "+ ")";
    		    // str=str.replace("val2", dpDate.getValue().toString());
    		     str=str.replace("val", time);
    		     MessageToServer mts = new MessageToServer();
    				try {
    					mts.addReportInfo(str);
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				mts.toAccept();	//
    				if (!ChatClient.list.toString().equals("[]")) {
    				String capstr =ChatClient.list.get(0);
    				avaTickets = Integer.valueOf(capstr);
    				avaTickets = Integer.valueOf(Capacity) -avaTickets;
    				System.out.println(avaTickets);
    	     if((numVisitors.getValue()> avaTickets))
    	            return true;
    				}
    			return false;

    	}
    	
    /**
     * initialize groupOrganizedComboBox
     */
    	private void setgroupOrganizedComboBox() {
    		ArrayList<String> al = new ArrayList<String>();	
    		al.add("YES");
    		al.add("NO");

    		list = FXCollections.observableArrayList(al);
    		groupOrganized.setItems(list);
    		groupOrganized.promptTextProperty();

    	}
    	/**
    	 * initialize ParkName ComboBox
    	 */
    	public void setParkNameComboBox() {
    		ArrayList<String> al = new ArrayList<String>();	
    		
    		al.add("HaiPark");
    		al.add("Sharon");
    		al.add("Carmel");

    		list2 = FXCollections.observableArrayList(al);
    		cmbPark.setItems(list2);
    	}
    	
    	/**
    	 * initialize time/hours ComboBox
    	 */
    	private void setTimeComboBox() {
    		ArrayList<String> al = new ArrayList<String>();	
    		al.add("08:00-12:00");
    		al.add("12:00-16:00");
    		al.add("16:00-20:00");

    		
    		list = FXCollections.observableArrayList(al);
    		cmbTime.setItems(list);
    	}
    	
    	
    	/**
         * event functions for screen buttons
         * @param event
         * @throws Exception
         */
            /*   event functions for screen buttons    */
    	public void getExit(ActionEvent event) throws Exception {
    		stage.outFromSystem(event,"Exit");		
    	}
	
	public void getLogOff(ActionEvent event) throws Exception {
		stage.outFromSystem(event,"LogOff");		
	}

	public void getMinimize(ActionEvent event) throws Exception {
		stage.Minimize(event);		
	}

	
	public void getHome(ActionEvent event) throws Exception 
	{
		stage.GoHome(event);
	}
	
	/**
	 * In case client chose to save the order details (place an order)
	 * this function make sure there are no problems with the selected fields by calling other functions
	 * If it pass all the checking - the stage move to 'order details' window
	 * @param event
	 * @throws Exception
	 */
	public void getSave(ActionEvent event) throws Exception 
	{

		if (CheckProblematicFields());
		else if(CheckChosenTime()) {
			AlertFunc(event);
		}
		else
		{
			if(ChatClient.member.getOrganizationGroup()!=null)
			if(ChatClient.member.getOrganizationGroup().contains("YES")&&groupOrganized.getSelectionModel().getSelectedItem().toString().equals("YES"))
				askAboutPayment();
			SavingDetails();
			
	        stage.OpenStage(event, "/COrderDetails/OrderDetails","Order Details");
		}
	}
	/**
	 * In case the client is a guide - a pop up dialog will show 
	 * in order to ask about payment (advanced payment decrease the total price)
	 */
	public static void askAboutPayment() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Payment");
		alert.setHeaderText("Hey there , you can choose to pay now or in the park \n *advance payment give discount of "+
		String.valueOf(100-(Prices.getAdvancePaymentDiscount()*100)) + "%") ;
		alert.setContentText("Choose your option.");

		ButtonType buttonTypeThree = new ButtonType("Now");
		ButtonType buttonTypeCancel = new ButtonType("In the park", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll( buttonTypeThree, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == buttonTypeThree) {
			ChatClient.order.setPaid(1);
			payNow=1;
		
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
	}
	
	/**
	 * Showing an alert pop up In case the client chose unavailable date & time 
	 * the client will be able to choose if he wants to select a new date
	 * OR adding to waiting list
	 * @param event
	 * @throws Exception
	 */
	public void AlertFunc(ActionEvent event) throws Exception
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("The Chosen Date Is Not Available ");
		alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
		alert.setContentText("For waiting list press OK"+"\n"+"For pick new date press Another Date");

		ButtonType WaitingList = new ButtonType("Waiting List");
		ButtonType AnotherDate = new ButtonType("Another Date");
	

		alert.getButtonTypes().setAll( WaitingList, AnotherDate);

		Optional<ButtonType> result = alert.showAndWait();
		 if (result.get() == WaitingList) {
			 double ticketPrice = Prices.getTicketPrice();
				if(ClientLogInController.objectType==ObjectKind.Member) {
			    	if (ChatClient.member!=null)
			    	{
			    		
			    		if (ChatClient.order.getOrganizationGroup()==1) {{
			    			ticketPrice *=Prices.getOrderedGroupVisitDiscount();
			    		}
			    			if(ChatClient.order.getPaid()==1) {
			    				ticketPrice *= Prices.getAdvancePaymentDiscount();
			    		} }
			    		else {
			    			ticketPrice *= Prices.getOrderedPersonalVisitDiscount();
			    			ticketPrice *= Prices.getMemberDiscount();
			 
			    		}
			    	}
			    	}
			    	else {
					ticketPrice *= Prices.getOrderedPersonalVisitDiscount();
			    	}
				

			 forwaitinglist= true;
			    SavingDetails();
			    ChatClient.order.setTotalPayment(ChatClient.order.getNumOfVisitors()*ticketPrice);
				MessageToServer mcs;
				mcs = new MessageToServer(Command.Add,TableName.orders,"NULL");
				mcs.addObject(ChatClient.order);
				mcs.toAccept(); 
			    stage.OpenStage(event, "/CWaitingList/WaitingList","Waiting List");
			}
		 else if (result.get() == AnotherDate) {
			SavingDetails();
			stage.OpenStage(event, "/CNewDate/SelectNewDate","Select New Date");	
		
		}
	}
	
	
	
	/**
	 * Saving order details in a static variable in order to use it in other controllers
	 */
	public void SavingDetails () throws Exception
	{
		LocalDate date = dpDate.getValue();
		
		//variables for set order
		
		MessageToServer mts = new MessageToServer(Command.ReadLastRecord,TableName.orders,"null");	
		mts.toAccept();
		int orderNumber = ChatClient.order.getOrderNumber()+1;

		String personID;
		
		if (ClientLogInController.objectType==ObjectKind.Member)
		    personID = ChatClient.member.getID();
		else
		    personID = ChatClient.visitor.getID();
		
		String park = cmbPark.getSelectionModel().getSelectedItem().toString();
		int visitDay = date.getDayOfMonth();
		int visitMonth =date.getMonthValue();
		int visitYear = date.getYear();
		int visitTime =0;
		int numOfVisitors = numVisitors.getValue();
		int pay = payNow;
		double totalPayment  = 123;
		int organizationGroup;
		if (groupOrganized.getSelectionModel().getSelectedItem().toString().equals("YES"))
				organizationGroup = 1;
		else
			organizationGroup=0;
		String status;
		if (forwaitinglist)
			status="WaitingList";
		else
			status="Ordered";
		String t=cmbTime.getSelectionModel().getSelectedItem().toString();
	

		switch(t)
		  {
			  case "08:00-12:00":
			  {
				  visitTime = 8;
				    break;

			  }
			  case "12:00-16:00":
			  {
				  visitTime=12;
				    break;

			  }
			  case "16:00-20:00":
			  {
				  visitTime = 16;
				    break;

			  }
		  }
		
		
	
		
		ChatClient.order = new Order( orderNumber, personID,   park,  visitDay,  visitMonth,  visitYear
				, visitTime,  numOfVisitors,  totalPayment,  organizationGroup,pay,status,10 ,10,1555,1845);
	    System.out.println(ChatClient.order);

if (forwaitinglist) //saving order for waiting list
		{
		  ChatClient.waiting_list = new Waiting_list(orderNumber ,park, visitDay,  visitMonth,  visitYear,visitTime,numOfVisitors);
		    System.out.println(ChatClient.waiting_list);
			MessageToServer mcs = new MessageToServer(Command.Add,TableName.waiting_lists,"NULL");
			mcs.addObject(ChatClient.waiting_list);
			mcs.toAccept();  
		} 
	

	}
	
/**
 * setting date picker 
 */
public void datePickerSetting() {
	dpDate.getEditor().setEditable(false); //user not allowed to use keyboard
	
	   
	        // Create a day cell factory
	        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() 
	        {
	            public DateCell call(final DatePicker datePicker) 
	            {
	                return new DateCell() 
	                {
	                    @Override
	                    public void updateItem(LocalDate item, boolean empty) 
	                    {
	                        // Must call super
	                        super.updateItem(item, empty);
	 

	                        // Disable all past date cells
	                        if (item.isBefore(LocalDate.now())) 
	                        {
	                            this.setDisable(true);
	                        }
	                    }
	                };
	            }
	        };
	 
	        // Set the day cell factory to the DatePicker
	        dpDate.setDayCellFactory(dayCellFactory);
	}
	
	public static final LocalDate LOCAL_DATE (String dateString){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stage.setImage(images,"Client");
		stage.SetTime(dateOfToday);

		if(ClientLogInController.objectType==ObjectKind.Member) {
			lblWelcome.setText("Welcome, " + ChatClient.member.getfirstName() + " " +ChatClient.member.getlastName());
			if (ChatClient.member.getOrganizationGroup().contains("no")) {
				SpinnerValueFactory<Integer> n = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, ChatClient.member.getNumberOfFamilyMembers());
			this.numVisitors.setValueFactory(n);
			}
			else {
				SpinnerValueFactory<Integer> n = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15);
			this.numVisitors.setValueFactory(n);
			}
			
		}
		else
		{
			if(ClientLogInController.objectType==ObjectKind.Visitor) {
				lblWelcome.setText("Welcome, " + ChatClient.visitor.getfirstName() + " " +ChatClient.visitor.getlastName());
				SpinnerValueFactory<Integer> n = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15);
				this.numVisitors.setValueFactory(n);
			}
			else {
				lblWelcome.setText("Welcome");
				SpinnerValueFactory<Integer> n = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15);
				this.numVisitors.setValueFactory(n);
			}
		}
		
		setParkNameComboBox();		
		setTimeComboBox();
	    setgroupOrganizedComboBox();
	    datePickerSetting();
	    numVisitors.setEditable(false);
		
	    //edit existing
	    if(ClientOrderDetails.objectType != ObjectKind.Person) {
	    	cmbPark.setValue(ChatClient.order.getPark());
	    	switch(ChatClient.order.getVisitTime())
			  {
				  case 8:
				  {
					  cmbTime.setValue("08:00-12:00");
					    break;

				  }
				  case 12:
				  {
					  cmbTime.setValue("12:00-16:00");
					    break;

				  }
				  case 16:
				  {
					  cmbTime.setValue("12:00-16:00");
					    break;
				  }
			  }
	    	String day = ChatClient.order.getVisitDay()< 10 ? "0"+ ChatClient.order.getVisitDay()+"" : ChatClient.order.getVisitDay()+"";
			String month = ChatClient.order.getVisitMonth() < 10 ? "0"+ChatClient.order.getVisitMonth()+"" :ChatClient.order.getVisitMonth()+"";
			String year = String.valueOf(ChatClient.order.getVisitYear());
			dpDate.setValue(LOCAL_DATE(year+"-"+ month+"-"+day));
	    	numVisitors.getValueFactory().setValue(ChatClient.order.getNumOfVisitors());
	    	if(ChatClient.order.getOrganizationGroup() == 0)
	    		groupOrganized.setValue("NO");
	    	else
	    		groupOrganized.setValue("YES");

	    }
		// TODO Auto-generated method stub

	}

}