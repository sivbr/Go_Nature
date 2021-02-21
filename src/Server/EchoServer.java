// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import ocsf.server.*;
import java.sql.Connection;

import ServerChatDB.ServerChatDBController;
/**
 * We Reuse some of the code. and some of it create.
 * 
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version December 2020
 *
 *
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * 
 */

public class EchoServer extends AbstractServer 
{
	public static ServerChatDBController SCDC;

  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port - The port number to connect on.
   * @param con - The connection of the DB
   */
 //public static Visitor [] visitors=new Visitor[4];

 private Connection con; 
  public EchoServer(int port,Connection con ) 
  {
    super(port);
    this.con = con;
  }

  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   * and send them to ServerChatDB to Run the correct action 
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   * 
   */
  public void handleMessageFromClient  (Object msg, ConnectionToClient client)
  {   
	  ServerChatDB serverChatDB = new ServerChatDB(msg,client,con);
	  if (!serverChatDB.Activate())
	  {
		  System.out.println("No Such A Command");
		  this.sendToAllClients(null);
	  }
	  
	  
	  SCDC.MessageFromServer(serverChatDB.msgToServer());
	  String textToClient = serverChatDB.msgToClient();
	  this.sendToClient(textToClient,client);
	  
  }
   
    /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()  {
    System.out.println ("Server has stopped listening for connections.");
  }  

  /**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		SCDC.MessageFromServer("Server listening for connections on port "+ getPort());
	}


	@Override
	protected void clientConnected(ConnectionToClient client) {
		//System.out.println("clientConnected");
		String ip = client.getInetAddress().getHostAddress();
		String host_name = client.getInetAddress().getHostName();
		SCDC.MessageFromServer("Host Name : "+ host_name+ " | From IP: "+ ip.toString() );
		new Thread(()->{
			while(client.isAlive()) {
				try {
					client.join();
				} catch(InterruptedException e) {}
			}
		SCDC.MessageFromServer("Not Connected");
		}).start();
	}
	
}

//End of EchoServer class
