package ServerChatDB;


import Server.EchoServer;
import Server.ServerUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
/**
 * The purpose of the class is to show the server actions with
 * UI that show in live the messages that the server send and get.
 *
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version December 2020
 */
public class ServerChatDBController  {
	//Class variables *************************************************	
	@FXML
	private Button btnExit = null;

	@FXML
	private Label lbllist;
	
	@FXML
	private TextArea Message;
	
	public ServerChatDBController() {
		super();
		EchoServer.SCDC = this;
	}
	
	
	
	 //Instance methods ************************************************
		/**
		 * This method show any messages from the server
		 * @param msg
		 */
	public void MessageFromServer(String msg) {
		System.out.println(msg);
		Platform.runLater(() -> {
			this.Message.appendText(msg +"\n");
		});
		
	}

	/**
	 * REUSE
	 * The function upload the the port screen
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {	
		EchoServer.SCDC = this;
		Parent root = FXMLLoader.load(getClass().getResource("/ServerChatDB/ServerChatDBController.fxml"));
		root.setStyle("-fx-background-image: url('file:img/ServerBackground.jpg')");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/ServerChatDB/ServerChatDBController.css").toExternalForm());
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();	
		ServerUI.runServer();
		
	}
	
	
	
	
	
	/**
	 * This method close the server
	 * @param event
	 * @throws Exception
	 */
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Go_Nature Server Tool");
		System.exit(0);			
	}
	
	

	

}