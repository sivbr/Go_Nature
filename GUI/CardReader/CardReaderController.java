package CardReader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Enums.Command;
import Enums.ObjectKind;
import Enums.Role;
import Enums.TableName;
import LogIn.ClientLogInController;
import Server.EchoServer;
import Server.ServerUI;
import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.Invoice;
import logic.Member;
import logic.MessageToServer;
import logic.Order;
import logic.Park;
import logic.Prices;
import logic.Temporary;
import logic.Waiting_list;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
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
public class CardReaderController implements Initializable {
	//Class variables *************************************************		
	private Stages stage = new Stages();
	
	   @FXML
	    private ImageView images;

	    @FXML
	    private Button btnExit;

	    @FXML
	    private Button btnMinimize;

	    @FXML
	    private Label lblTime;

	    @FXML
	    private Label lblWelcome;

	    @FXML
	    private Button btnHome;

	    @FXML
	    private Button btnLogOff;
	    
		ObservableList<String> list ;

	    @FXML
	    private ListView<String> lv= new ListView<>(list);
	    
	    /**
		 * Variable for the selected invoice
		 */
		private String chosenOrder;
		
		/**
		 * Array which contains all invoices 
		 */
		public static Invoice invoicesDeclines[] = null;
	    
	//Instance methods ************************************************
		/**
		 * setting data of invoices & declines messages 
		 * so that the park employee can print it.
		 */
	
	    private void setDetails() {
	    	String park = null;
	    	String askSql = "";

			if(ClientLogInController.objectType!=ObjectKind.Worker) 
                return;				
			else if(ChatClient.worker.getRole() == Role.DepartmentManager)
			{
				park = "All";
				askSql = "SELECT * FROM invoices";
			}
			else
			{
				park = ChatClient.worker.getPark();
				askSql = "SELECT * FROM invoices WHERE parkName = \""+park+"\"";
			}

			MessageToServer mts = new MessageToServer();
			try {
				mts.addReportInfo(askSql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mts.toAccept();	//

			//create Invoices array from report
			if (ChatClient.list.toString().equals("[]"))
				invoicesDeclines=null;
			else if (!ChatClient.list.toString().equals("[]")) {
			Invoice invoice [] = new Invoice[ChatClient.list.size()];
			for(int i=0;i<invoice.length;i++)
				invoice[i]=new Invoice(ChatClient.list.get(i));
			invoicesDeclines = invoice;		
			ArrayList<String> al = new ArrayList<String>();	
			
			for (Invoice order : invoicesDeclines) {
				// al.add(order.stringToTrack());
				al.add(order.stringToTrack());
			}

			list = FXCollections.observableArrayList(al);
			lv.setItems(list);
			 lv.setCellFactory(lv -> {
		            ListCell<String> cell = new ListCell<String>() {
		                @Override
		                protected void updateItem(String item, boolean empty) {
		                    super.updateItem(item, empty);
		                    setText(item);
		                }
		            };
		            cell.setOnMouseClicked(e -> {
		                if (!cell.isEmpty()) {
		                	chosenOrder = lv.getSelectionModel().getSelectedItem();
		                	for (Invoice order : invoicesDeclines) {
		            			if(order.stringToTrack().compareTo(chosenOrder)==0) {
		            				ChatClient.invoice = order;
		            			    break;
		            			}

		                	}
		                	if (chosenOrder.contains("Decline"))
		                		showDecline ();
		                	else if (chosenOrder.contains("Invoice")&&(ChatClient.invoice.getOrderNumber()==-1))
		                		showInvoice ();
		                	else if (chosenOrder.contains("Invoice")&&(ChatClient.invoice.getOrderNumber()!=-1))
		                		showMailInvoiceMsg  ();
		                
		                	e.consume();
		                }
		            });
		            return cell;
			  });
			}
		}
	    
	    /**
	     * Show msg for visitors with reservation from go-nature site
	     * those customers got their bill in the mail
	     */
	    private void showMailInvoiceMsg ()
	    {
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Your Invoice is sent to your email");
	    	alert.setHeaderText(null);
	    	String minute = ChatClient.invoice.getMinute()< 10 ? "0"+ ChatClient.invoice.getMinute()+"" : ChatClient.invoice.getMinute()+"";
			String hour = ChatClient.invoice.getHour() < 10 ? "0"+ChatClient.invoice.getHour()+"" :ChatClient.invoice.getHour()+"";
	    	alert.setContentText(lblTime.getText() +"  "+ hour+":"+minute
	    	+"\n"
	    	+"Dear Travelers, " +"\n" +"Your invoice has been sent to your email"+"\n"+ "When you placed an order in our site" +"\n"
	    	+"\n"+"Thank you for your payment."+"\n"+
	    	    	"Enjoy your visit ! \n\n\n ");

	    	alert.showAndWait();
	    }
	    
	    /**
	     * In case the park is in full capacity ,
	     * visitors may get a decline msg
	     */
	    private void showDecline ()
	    {
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Sorry , the park is in full capacity");
	    	alert.setHeaderText(null);
	    	String minute = ChatClient.invoice.getMinute()< 10 ? "0"+ ChatClient.invoice.getMinute()+"" : ChatClient.invoice.getMinute()+"";
			String hour = ChatClient.invoice.getHour() < 10 ? "0"+ChatClient.invoice.getHour()+"" :ChatClient.invoice.getHour()+"";
	    	alert.setContentText(lblTime.getText() +"  "+ hour+":"+minute
	    	+"\n"
	    	+"Dear Travelers," +"\n" +"The park is in full capacity" +"\n"
	    	+"Due to ministry of health restrictions" +"\n" +"We are not able to get more visitors right now " +
	    			"\n"+"We are vey sorry. Hope to see you in our parks soon"
	                  +"\n \n"+"* We are recomending to order visit in our site \n\n\n");
	    	

	    	alert.showAndWait();
	    }
	    /**
	     * Showing full invoice for visitors whom paid in the park
	     */
	    private void showInvoice ()
	    {
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	
	    	alert.setHeaderText("Visitors Invoice number :  " + ChatClient.invoice.getInvoiceNumber());
	    	alert.setTitle("Invoice");
	    	String memberdiscount ="";
	    	if (ChatClient.invoice.getIsMember()==1)
		    	 memberdiscount = " + Members" + " ("+Prices.getMemberDiscount()*10 + "%"+")";

	    	String minute = ChatClient.invoice.getMinute()< 10 ? "0"+ ChatClient.invoice.getMinute()+"" : ChatClient.invoice.getMinute()+"";
			String hour = ChatClient.invoice.getHour() < 10 ? "0"+ChatClient.invoice.getHour()+"" :ChatClient.invoice.getHour()+"";
		 	alert.setContentText(lblTime.getText() +"  "+ hour+":"+minute
	    	    	+"\n" + "Park: " + ChatClient.invoice.getParkName()
	    	    	+"\n" + "Number Of Visitors: " + ChatClient.invoice.getNumOfVisitors()
	    	    	+"\n" + "Original Price: " + ChatClient.invoice.getOriginalPrice()
	    	    	+"\n" + "Dicounts: " +"Park Special Dicount" +" (" +ChatClient.invoice.getParkDiscount() + "% "+")" 
	    	    	+memberdiscount
	    	    	+"\n" + "Total Price: " + ChatClient.invoice.getTotalPrice()
	    	    	+"\n"+"Thank you for your payment."+"\n"+
	    	    	"Enjoy your visit ! \n\n\n ");
	
	    	ButtonType buttonTypeThree = new ButtonType("Print");
	    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

	    	alert.getButtonTypes().setAll(buttonTypeThree, buttonTypeCancel);

	    	Optional<ButtonType> result = alert.showAndWait();
	  
	    if (result.get() == buttonTypeThree) {
	    	    // ... user chose "Three"
	    	} else {
	    	    // ... user chose CANCEL or closed the dialog
	    	}
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
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		stage.setImage(images, "workerDetails");
		stage.SetTime(lblTime);
		lblWelcome.setText("Welcome, " + ChatClient.worker.getfirstName() + " " + ChatClient.worker.getlastName());
		setDetails();
		

	}

} 
