package CNewDate;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import com.sun.glass.ui.Accessible.EventHandler;

import client.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import logic.MessageToServer;
import CNewOrder.ClientNewOrderController;
import Enums.Command;
import Enums.ObjectKind;
import Enums.TableName;
import LogIn.ClientLogInController;
import StagesFunc.Stages;

public class SelectNewDateController implements Initializable {

	private Stages stage = new Stages();
		
    @FXML
    private ImageView images;// = new ImageView(image)
   
	@FXML
	private DatePicker dpChooseDate = new DatePicker();
	
	@FXML
	private ComboBox<String> hourCombo;
	
	@FXML
	private Button btnExit=null;
	
    @FXML
    private Button btnMinimize=null;

    @FXML
    private Button btnLogOff=null;
	
    @FXML
	
    private Button btnSave = null;
	
    @FXML
	private Button btnHome = null;
    
    @FXML
    private Label lblWelcome;
    
    @FXML
    private Label lblTime;
	
	ObservableList<String>listHours;
	
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
	 * setting the hour combo Box - under conditions
	 */
	public void ComboBoxHour() {
		
	    boolean condition= AvaDates.containsKey(dpChooseDate.getValue());

	    ArrayList<String>hours=new ArrayList<String>();
		if ((!condition)||(!(AvaDates.get(dpChooseDate.getValue()).contains(8))))	
		hours.add("08:00-12:00");
		if ((!condition)||(!(AvaDates.get(dpChooseDate.getValue()).contains(12))))	
		hours.add("12:00-16:00");
		if ((!condition)||(!(AvaDates.get(dpChooseDate.getValue()).contains(16))))	
		hours.add("16:00-20:00");
		listHours=FXCollections.observableArrayList(hours);
		hourCombo.setItems(listHours);
		}

	
	
	
	/**
	 * setting date picker : red cells are unavailable  , green cells are available
	 */
public void datePickerSetting() {
	dpChooseDate.getEditor().setDisable(true);
	dpChooseDate.setEditable(true);
	ArrayList<Integer> d = new ArrayList <Integer> ();
	d.add(8);
	d.add(12);
	d.add(16);

        
	         
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
	 
	                        
	                   
	                            this.setTextFill(Color.GREEN);
	                            
	                            
	                 
	                            
	                            for(Entry<LocalDate, ArrayList<Integer>> arr : AvaDates.entrySet()) {
	                            	if (item.compareTo(arr.getKey()) == 0 && arr.getValue().containsAll(d)  ) 
	    	                        {
	    	                        	this.setTextFill(Color.RED);
	    	                            this.setDisable(true);
	    	                        }
	                            }
	                          
	                        
	                         
	                        // Disable all past dates cells
	                        if (item.isBefore(LocalDate.now())) 
	                        {
	                        	this.setTextFill(Color.RED);
	                            this.setDisable(true);
	                        }
	                    }
	                };
	            }
	        };
	 
	        // Set the day cell factory to the DatePicker
	        dpChooseDate.setDayCellFactory(dayCellFactory);
	    	dpChooseDate.getEditor().setEditable(false);	

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
	 
	   public void getHome(ActionEvent event)throws Exception {
		   stage.GoHome(event);
	    }

	   /**
		 * Saving order details in a static variable in order to use it in other controllers
		 */
	    public void getSave(ActionEvent event)throws Exception {
	    	
	    	if (CheckProblematicFields ());
	    	
	    	else
	    	{
	    	if(ChatClient.order.getOrganizationGroup()==1)
	    		ClientNewOrderController.askAboutPayment();
	    	
	    	String t=hourCombo.getSelectionModel().getSelectedItem().toString();
	    	int visitTime = 0;

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
	    	
	    	ChatClient.order.setVisitTime(visitTime);
	    	ChatClient.order.setVisitDay(dpChooseDate.getValue().getDayOfMonth());
	    	ChatClient.order.setVisitMonth(dpChooseDate.getValue().getMonthValue());
	    	ChatClient.order.setVisitYear(dpChooseDate.getValue().getYear());
	    	stage.OpenStage(event, "/COrderDetails/OrderDetails","Order Details");
	    	}


	    }
	    /**
	     * checking if there are empty fields
	     * @return
	     */
	    private boolean CheckProblematicFields ()
		{	
		
	    
	    StringBuilder infoErrors = new StringBuilder();		
		if  (dpChooseDate.getValue()==null) 
		{
			infoErrors.append("- Please choose a date.\n");
		}

		
		if  (hourCombo.getSelectionModel().getSelectedItem()==null) 
		{
			infoErrors.append("- Please choose an hour.\n");
		}
	
		
       if (infoErrors.length() > 0 ) {
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("We Found Some Problems");
           alert.setHeaderText("We Found Some Problems :");
           alert.setContentText(infoErrors.toString());

           alert.showAndWait();
           return true;
       }
       return false;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		stage.setImage(images,"ClientDetails");
		stage.SetTime(lblTime);
		
		setAvaDates() ;
		datePickerSetting();
		if(ClientLogInController.objectType==ObjectKind.Member) 
			lblWelcome.setText("Welcome, " + ChatClient.member.getfirstName() + " " +ChatClient.member.getlastName());
		else if(ClientLogInController.objectType==ObjectKind.Visitor)
			lblWelcome.setText("Welcome, " + ChatClient.visitor.getfirstName() + " " +ChatClient.visitor.getlastName());
		else 
			lblWelcome.setText("Welcome, Guest");
			
	}

		

}
