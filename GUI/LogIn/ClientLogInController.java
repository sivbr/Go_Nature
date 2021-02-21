package LogIn;

import java.net.URL;
import java.util.ResourceBundle;
import Enums.Command;
import Enums.ObjectKind;
import Enums.Role;
import Enums.TableName;
import client.ChatClient;
import client.ClientUI;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Member;
import logic.MessageToServer;
import logic.Order;
import logic.Possible_date;
import logic.Request;
import logic.Visitor;
import logic.Waiting_list;
import logic.Worker;
import StagesFunc.Stages;

/**
 * The purpose of the class is to be create client UI and show the
 * find the vistor and edit them.
 *
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version December 2020
 */
public  class ClientLogInController implements Initializable {
	//Class variables *************************************************
	private Stages stage = new Stages();
	public static ObjectKind objectType;
	//Image image = new Image("file:login2.jpeg");
	
    @FXML
    private ImageView images;// = new ImageView(image)
    
	//background	
	
	//shay	
	
	@FXML
	private TextField txtUser;
	
	@FXML
	private TextField txtPass;
	
	@FXML
	private Button btnLogIn = null;
	
	@FXML
	private Button btnExit = null;
	@FXML
	private CheckBox cbWorker = null;
	@FXML
	private Button btnTraveler = null;
	
	@FXML
    private FontAwesomeIcon fontLock = new FontAwesomeIcon();
	
	Alert NoId = new Alert(AlertType.NONE,  "Please enter Id",ButtonType.CLOSE);
	Alert InValidId = new Alert(AlertType.NONE,  "ID Not Found",ButtonType.CLOSE);
	Alert InValidIdW = new Alert(AlertType.NONE,  "Worker Nubmer Not Found",ButtonType.CLOSE);
	Alert InValidPass = new Alert(AlertType.NONE,  "The password is wrong",ButtonType.CLOSE);
	Alert AlreadyInside = new Alert(AlertType.NONE,  "The user already connected",ButtonType.CLOSE);
	
	private Visitor v1 = new Visitor();
	private Member m1 = new Member();
	private Worker w1 = new Worker();
	
	private Image image;
	
	private boolean onlineFlag = true;
	//Instance methods ************************************************
		/**Help to get out the text from the ID text filed
		 * @return the correct string
		 */
	private String getID() {
		return txtUser.getText();
	}
	
	private String getPass() {
		return txtPass.getText();
	}
		
	/**
	 * This method send the visitor ID to the server and if there is a match ->upload the Visitor-Personal-Information Screen.
	 * @param press on the send button
	 * @throws Exception
	 */
	public void btnLogIn(ActionEvent event) throws Exception {
		String id,pass;	
		
		id=getID();
		if(id.trim().isEmpty())
		{
			NoId.show();	
		}
		else
		{
			MessageToServer mcs;
			if(cbWorker.isSelected()) {
				pass=getPass();
				mcs = new MessageToServer(Command.Read,TableName.workers,id);
				mcs.toAccept();
				if(ChatClient.objectType != ObjectKind.Worker)
					InValidIdW.show();
				else if(ChatClient.worker.getPassword().equals(pass) == false ) {
					InValidPass.show();
				}
				else if(ChatClient.worker.getOnline() == 1 && onlineFlag == true) {
					AlreadyInside.show();
				}
				else {
					w1 = ChatClient.worker;
					stage.onlineUpdate(TableName.workers, w1.getWorkerNumber(), "1");
					w1.setOnline(1);
					ChatClient.worker = w1;
					objectType=ObjectKind.Worker;
					if(ChatClient.worker.getRole() == Role.ParkEmployee)
						stage.OpenStage(event, "/PEHome/ParkEmployee","Park Employee");
					else if (ChatClient.worker.getRole() == Role.CustomerService)
						stage.OpenStage(event, "/CSHome/CustomerService","Customer Service");
					else if (ChatClient.worker.getRole() == Role.ParkManager)
						stage.OpenStage(event, "/PMHome/ManagerHome","Manager Home");
					else if (ChatClient.worker.getRole() == Role.DepartmentManager)
						stage.OpenStage(event, "/DMHome/DepartmentManager","Department Manager");
				}
			}		 
			else {
				mcs = new MessageToServer(Command.Read,TableName.members,id);
				mcs.toAccept();
				
				if(ChatClient.objectType != ObjectKind.Member ) {
					
					String queryString = "SELECT * FROM " + TableName.members + " where memberNumber = \"" + id + "\";";
					mcs = new MessageToServer();
					mcs.addReportInfo(queryString);
					mcs.toAccept();
					if(ChatClient.list.toString().equals("[]") == false){
						Member m = new Member(ChatClient.list.get(0));
						ChatClient.member = m;
						m1 = ChatClient.member;
						stage.onlineUpdate(TableName.members, m1.getID(), "1");
						m1.setOnline(1);
						ChatClient.member = m1;
						objectType=ObjectKind.Member;
						stage.OpenStage(event, "/CHome/ClientHome","Client Home");
						
					}
					else {
						mcs = new MessageToServer(Command.Read,TableName.visitors,id);
						mcs.toAccept();
						if(ChatClient.objectType != ObjectKind.Visitor) {
							InValidId.show();
						}
						else if(ChatClient.visitor.getOnline() == 1 && onlineFlag == true) {
							AlreadyInside.show();
						}
						else
						{
							v1 = ChatClient.visitor;
							stage.onlineUpdate(TableName.visitors, v1.getID(), "1");
							v1.setOnline(1);
							ChatClient.visitor = v1;
							objectType=ObjectKind.Visitor;
							stage.OpenStage(event, "/CHome/ClientHome","Client Home");
						}	
					}			
				}
				else if(ChatClient.member.getOnline() == 1 && onlineFlag == true) {
					AlreadyInside.show();
				}
				else {
					m1 = ChatClient.member;
					stage.onlineUpdate(TableName.members, m1.getID(), "1");
					m1.setOnline(1);
					ChatClient.member = m1;
					objectType=ObjectKind.Member;
					stage.OpenStage(event, "/CHome/ClientHome","Client Home");

				}
			}					
			
		}
	}
	
	public void btnTraveler(ActionEvent event) throws Exception {
		objectType=ObjectKind.FirstTime;
		stage.OpenStage(event, "/CHome/ClientHome","Client Home");
	}

	
	public void cbClick(ActionEvent event) throws Exception {
		if(cbWorker.isSelected()) {
			txtPass.setVisible(true);
			fontLock.setVisible(true);
		}
		else {
			txtPass.setVisible(false);
			fontLock.setVisible(false);
		}
	}
	
/**
 * Reuse - upload the LogIn screen
 * @param primaryStage
 * @throws Exception
 */
	public void start(Stage primaryStage) throws Exception {	
		stage.OpenCenterStage(primaryStage,"/LogIn/ClientLogIn","Log In");			
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
	 * This function minimize the user from the screen.
	 * @param event
	 * @throws Exception
	 * @see StagesFunc.Stages#Minimize(ActionEvent event)
	 */
	public void getMinimize(ActionEvent event) throws Exception {
		stage.Minimize(event);		
	}

	/**
	 * This method help when we create the messages
	 * @param message
	 */
	public  void display(String message) {
		System.out.println("Message");
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtPass.setVisible(false);
		fontLock.setVisible(false);
		image = new Image("file:img\\gif.gif");
		images.setImage(image);		
	}
	
}
