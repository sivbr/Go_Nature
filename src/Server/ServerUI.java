package Server;

import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ServerChatDB.ServerChatDBController;


/**
 * 
 * ReUse the code of the Connection between the DB to server
 * ReUse the code of the server starting listening to client
 * We combined the two to boot at the time the server upload
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version December 2020
 *
 */
public class ServerUI extends Application {
	/**
	* The default port to listen on.
	*/
	final public static int DEFAULT_PORT = 5555;
	
	
	/**Reuse
	 * start to upload the server
	 * @param args
	 * @throws Exception
	 */
	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	
	
	/**Reuse
	 *start the first port screen
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {			  		
		ServerChatDBController aFrame = new ServerChatDBController(); // create ServerChatDB

		aFrame.start(primaryStage);
		
	}
	
	/**start to run the Server
	 * Reuse with the combination of the DB Connection  
	 * @param p
	 */
	public static void runServer()
	{
		 int port = 0; //Port to listen on
		 	
	        try
	        {
	        	 port = DEFAULT_PORT; //Set port to DEFAULT_PORT
	          
	        }
	        catch(Throwable t)
	        {
	        	System.out.println("ERROR - Could not connect!"); // there is a problem to set the port
	        }

	        try 
			{
	            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	            System.out.println("Driver definition succeed");
	        } catch (Exception ex) {
	        	/* handle the error*/
	        	 System.out.println("Driver definition failed");
	        	 }
	        
	        try 
	        {
	        	// make the connection between the server and the DB
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/go_nature?serverTimezone=IST","root","Aa123456");
	            System.out.println("SQL connection succeed");
	            EchoServer sv = new EchoServer(port,conn);
		        
		        try 
		        {
		          sv.listen(); //Start listening for Clients connections
		        } 
		        catch (Exception ex) 
		        {
		          System.out.println("ERROR - Could not listen for clients!"); //there is a problem listening to Clients 
		        }
	            
	     	} catch (SQLException ex) 
	     	    {
	     		/*
	     		 * there is a problem to connect the DB
	     		 * 
	     		 */
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	            }
	}
	

}
