package StagesFunc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Enums.Command;
import Enums.ObjectKind;
import Enums.Role;
import Enums.TableName;
import LogIn.ClientLogInController;
import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Member;
import logic.MessageToServer;
import logic.Visitor;
import logic.Worker;

/** 
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 *
 */
public class Stages {
	//private MessageToServer update;
	private Image image;
	public static boolean ScreenUp = false;
	/**
	 * This function open the main stages.
	 * @param primaryStage
	 * @param FilePath
	 * @param Title
	 * @throws Exception
	 */
	public void OpenCenterStage(Stage primaryStage,String FilePath,String Title) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource(FilePath +".fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource(FilePath +".css").toExternalForm());
		primaryStage.setTitle(Title);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	/**This function responsible to open old stage and open new after making event.
	 * @param event
	 * @param FilePath
	 * @param Title
	 * @throws Exception
	 */
	public void OpenStage(ActionEvent event,String FilePath,String Title) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(FilePath +".fxml").openStream());
		
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource(FilePath +".css").toExternalForm());
		primaryStage.setTitle(Title);

		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
	/**
	 * This function return the user to his own main screen.
	 * @param event
	 * @throws Exception
	 * role - get the role of worker if the user is worker.
	 */
	public void GoHome(ActionEvent event) throws Exception{
	
		if(ChatClient.worker.getWorkerNumber() != null) {
			Role role = ChatClient.worker.getRole();
			switch(role) {
			case ParkEmployee:
				OpenStage(event, "/PEHome/ParkEmployee","Park Employee");
				break;
			case CustomerService:
				OpenStage(event, "/CSHome/CustomerService","Customer Service");
				break;
			case ParkManager:
				OpenStage(event, "/PMHome/ManagerHome","Manager Home");
				break;
			case DepartmentManager:
				OpenStage(event, "/DMHome/DepartmentManager","Department Manager");
			}
		}
		else
			OpenStage(event,"/CHome/ClientHome","Client Home");
	}
	
	/**
	 * this function return return the local date.
	 * @param lbl
	 * formatter - get the local time with pattern (dd/MM/yyyy).
	 */
	public void SetTime(Label lbl) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		lbl.setText(LocalDate.now().format(formatter));
	}


	/**
	 * This function take out the user from the system with log off or exit.
	 * @param event
	 * @param action
	 * @throws Exception
	 */
	public void outFromSystem(ActionEvent event,String action) throws Exception {
		
		
		if(ClientLogInController.objectType == ObjectKind.Member) {
			onlineUpdate(TableName.members, ChatClient.member.getID(), "0");
			ChatClient.member = new Member();
			
		} 
		else if (ClientLogInController.objectType == ObjectKind.Visitor) {
			onlineUpdate(TableName.visitors, ChatClient.visitor.getID(), "0");
			ChatClient.visitor = new Visitor();
		}
		else if (ClientLogInController.objectType == ObjectKind.Worker) {
			onlineUpdate(TableName.workers,ChatClient.worker.getWorkerNumber(),"0");
			ChatClient.worker = new Worker();
		}
		if(action.equals("LogOff"))
			OpenStage(event,"/LogIn/ClientLogIn","Log In");
		else if(action.equals("Exit")) {
			System.out.println("exit Go_Nature Tool");
			System.exit(0);	
		}
	}
	
	/**
	 * This function change the user online status inside the DB.
	 * @param tabel
	 * @param key
	 * @param status
	 * update - sending query to MessageToServer
	 */
	public void onlineUpdate(TableName tabel,String key,String status) {
		MessageToServer update = new MessageToServer(Command.Update,tabel,key);
		update.addInfo("online", status);
		update.toAccept();
	}
	
	/**
	 * This function put image in the user screen according to type of user.
	 * @param images
	 * @param status
	 */
	public void setImage(ImageView images,String status) {
		if(status.equals("Client")) {
			image = new Image("file:img\\client.png");
			images.setImage(image);
		} 
		else if(status.equals("ClientDetails")) {
			image = new Image("file:img\\clientDetails.png");
			images.setImage(image);
		}
		else if(status.equals("worker")){
			image = new Image("file:img\\workSpace.jpeg");
			images.setImage(image);
		}
		else if(status.equals("workerDetails")){
			image = new Image("file:img\\workspaceDetails.png");
			images.setImage(image);
		}
		else {
			image = new Image("file:img\\new.jpg");
			images.setImage(image);
		}
			
	}
	
	/**
	 * This function minimize the user screen.
	 * @param event
	 * stage - get the stage that in use.
	 */
	public void Minimize(ActionEvent event) {
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}
}
