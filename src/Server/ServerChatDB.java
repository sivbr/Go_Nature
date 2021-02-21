package Server;

import logic.MessageToServer;
import ocsf.server.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Enums.ObjectKind;
import client.ClientUI;
import javafx.application.Platform;

/**
 * The purpose of the class is to more easily write the message from the client.
 * Simpler communication between DB and SERVER. And receiving messages for one
 * of the server server clients more easily.
 *
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version December 2020
 */

public class ServerChatDB {
	/**
	 * 
	 * tableName - the name of the table in DB we want to make the action msg - the
	   full message of the client to server.
	 * command - the command of the client to server, there are few commands in the Enum department, 
	   like Read, Add, Delete ,etc.. 
	 * words - is the message from client divided by spaces to string array
	 * client - the detail of the client that send the message
	 * con - the object that represent the MySQL connection 
	 * toClientString - the answer to the client that we want to send
	 * toServerString - the description of the action that the server do, we want to present it on the server screen.
	 * mts - an object that use the get message from the client. 
	   that object is the most of the vital data analyzed and organized to variables.   
	 * infoMail - static variable to transfer the detail of the mail message to the relevant department.
	 */
	private String tableName;
	private String command;
	private String[] words;
	private ConnectionToClient client;
	private Connection con;
	private String toClientString;
	private String toServerString;
	private MessageToServer mts;
	public static String infoMail[];

	/**
	 * Constructs an instance of the echo server.
	 * 
	 * @param port The port number to connect on.
	 * @param port The Client address to connect on.
	 * @param the DB connection object
	 * return the message of the client to be MessageToServer Object
	 * 
	 */
	public ServerChatDB(Object msg, ConnectionToClient client, Connection con) {
		words = ((String) msg).split("\\s");
		command = words[0];
		this.client = client;
		this.con = con;
		this.mts = new MessageToServer(msg.toString());

	}

	/**
	 * The Activate function - activate the connection between the DB and the Server
	   according to the kind of the Command.
	 * Update the comments for the server and client according to the result of the process.
	 * 
	 * @return The function returns whether one of the operations occurred. 
	 * 
	 */
	public boolean Activate() {
		switch (mts.getCommand()) {
		case Update: {
			if (updateTheDB()) {
				toServerString = "Message received, Update: " + words[2] + " from " + client;
				toClientString = "Message The ID:" + words[2] + " Of table \"" + words[1] + "\" Changed to \""
						+ words[5] + "\"";
			} else {
				toServerString = "There is No Change";
				toClientString = "Message Change Of \"" + words[1] + "\" Failed";
			}
			return true;
		}
		case Read: {
			System.out.println("Message received, ID : " + words[1] + "| from " + client);
			toClientString = ReadTheDB();
			if (toClientString.equals("Message " + mts.getTable() + " Error")) {
				toServerString = "Not found the Item " + words[2] + " in table " + words[1];
			} else {
				toServerString = "Message received, Read: " + words[1] + " from " + client;
			}

			return true;
		}
		case Add: {
			System.out.println("Add Successfully to " + mts.getTable() + "!");
			toClientString = AddToDB();
			if (toClientString.equals("Message The item is already in the table " + mts.getTable() + "!")) {
				toServerString = "The item is already in the table " + mts.getTable() + "!";
			} else {
				toServerString = "Add Successfully to " + mts.getTable() + "!";
			}

			return true;
		}
		case Delete: {
			System.out.println("Message received, ID : " + words[1] + "| from " + client);
			toClientString = DeleteFromDB();
			toServerString = "Delete Successfully from " + words[1] + "!";
			return true;
		}
		case Report: {
			//System.out.println("Message received, Report from " + words[4] + " table | from " + client);
			toClientString = makeReport();
			toServerString = "Report";
			return true;
		}
		case ReadLastRecord: {
			System.out.println("Message received, ID : " + words[1] + "| from " + client);
			toClientString = ReadLastRecordDB();
			if (toClientString.equals("Message " + mts.getTable() + " Error")) {
				toServerString = "Not found the Item";
			} else {
				toServerString = "Message received, Read: " + words[1] + " from " + client;
			}

			return true;
		}
		case SendMail: {
			String info[] = words[1].split(";");
			System.out.println("Send mail to : " + info[0] + "| from " + client);
			toClientString = sendMail();
			toServerString = "Mail send successfully to " + info[0] + "!" + " from " + client;
			return true;
		}

		}
		return false;

	}

	
	/**
	 * 
	 * The function updates the information  
	   in the DB according to the "mts" (MessageToServer Object) info.
	 * 
	 * 
	 * 
	 * @return the function returns - if the DB has changed
	 */
	private boolean updateTheDB() {
		Statement stmt;
		try {
			stmt = con.createStatement();
			// getInfo - String that look like "categorys;setvalue categorys;setvalue categorys;setvalue .."
			String[] categorys = mts.getInfo().split("\\s");
			int count = 0;
			// We disassemble each pair in a string in a loop
			while (count < categorys.length) {
				String[] data = categorys[count].split(";");
				String queryString = "UPDATE " + mts.getTable() + " SET " + data[0] + " = \"" + data[1] + "\" where "
						+ mts.categoryKey() + " = \"" + mts.getKey() + "\";";
				stmt.executeUpdate(queryString);
				count++;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 
	 * Specific query for finding the last record in a table.
	 * The information is come from "mts" (MessageToServer Object).
	 * 
	 * 
	 * @return An answer to the client.
	 */
	private String ReadLastRecordDB() {

		Statement stmt;
		try {
			stmt = con.createStatement();
			String queryString = "SELECT * FROM " + mts.getTable() + " ORDER BY " + mts.categoryKey() + " DESC LIMIT 1";
			ResultSet rs = stmt.executeQuery(queryString);
			String text = ObjectKind.tableToString(mts.getTable());
			if (rs.next()) {
				int i = 1;
				try {
					while (true) {
						text += " " + rs.getString(i++);
					}
				} catch (Exception e) {
					rs.close();
					return text;
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
			return "Message " + mts.getTable() + " Error";
		}
		return "Message " + mts.getTable() + " Error";

	}

	/**
	 * The function search for the information
	   in the DB according to "mts" (MessageToServer Object) info.
	 * 
	 * @return the variable that we want to send back but as a string or if the
	           function can't find - it return the name of the Object and error
	 */
	private String ReadTheDB() {
		Statement stmt;
		try {
			stmt = con.createStatement();
			String queryString = "SELECT * FROM " + mts.getTable() + " where " + mts.categoryKey() + " = \""
					+ mts.getKey() + "\";";
			ResultSet rs = stmt.executeQuery(queryString);
			String text = ObjectKind.tableToString(mts.getTable());

			if (rs.next()) {
				int columnIndex = 1;
				try {
					while (true) {
						text += " " + rs.getString(columnIndex++);
					}
				} catch (SQLException e) {
					rs.close();
					return text;
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
			return "Message " + mts.getTable() + " Error";
		}
		return "Message " + mts.getTable() + " Error";

	}
	/**
	 * The function Add to the DB an Object according to "mts" (MessageToServer Object) info.
	 * @return An answer to the client
	 */
	private String AddToDB() {
		Statement stmt;
		try {
			stmt = con.createStatement();
			//getInfo - String look like "value1 value2 value3 .."
			String toQuery = mts.getInfo();
			toQuery = "'" + toQuery + "'";
			toQuery = toQuery.replace(" ", "','");

			String queryString = "INSERT INTO " + mts.getTable() + " VALUES (" + toQuery + ")";
			stmt.executeUpdate(queryString);
			return "Message Add Successfully to " + mts.getTable() + "!";

		} catch (SQLException e) {

			e.printStackTrace();
			return "Message The item is already in the table " + mts.getTable() + "!";
		}

	}
	/**
	 * The function Delete a record from DB according to "mts" (MessageToServer Object) info.
	 * @return An answer to the client.
	 */
	private String DeleteFromDB() {
		Statement stmt;
		try {
			stmt = con.createStatement();
			String queryString = "DELETE FROM " + mts.getTable() + " WHERE " + mts.categoryKey() + " = \""
					+ mts.getKey() + "\";";
			stmt.executeUpdate(queryString);
			return "Message " + mts.getKey() + " Deleted from " + mts.getTable() + "!";

		} catch (SQLException e) {

			e.printStackTrace();
			return "Message Problem To Delete!";
		}

	}
	/**
	 * The function return a result of a query according to "mts" (MessageToServer Object) info.
	 * The answer send to the client in special format.
	 * @return An answer to the client.
	 */
	private String makeReport() {
		Statement stmt;
		try {
			stmt = con.createStatement();
			String queryString = mts.getInfo();
			ResultSet rs = stmt.executeQuery(queryString);
			String text = "Report";

			while (rs.next()) {
				int columnIndex = 1;
				try {
					while (true) {
						text += " " + rs.getString(columnIndex);
						columnIndex++;
					}
				} catch (SQLException e) {
					text += " ;";
				}

			}

			rs.close();
			return text;
		} catch (SQLException e) {
			return "Message Problem to make report!";
			
		}

	}
	/** The function Sending a mail to customer email according to "mts" (MessageToServer Object) info.
	 * The function using a static function from another class for sending the mail. 
	* 
	* @return  A message that does not change to the client.
	*/
	private String sendMail() {
		String text = mts.getInfo();
		infoMail = new String[3];
		infoMail = text.split(";");

		Platform.runLater(() -> {
			ServerMail.sendMail(infoMail[0], infoMail[1], infoMail[2]);
		});
		
			return "Message Mail send Successfully to " + infoMail[0] + "!";
	}

	/**
	 * This getter is relevant after "Activate" function.
	 * 
	 * @return send back a comment for client
	 */
	public String msgToClient() {
		return toClientString;
	}

	/**
	 * This getter is relevant after "Activate" function.
	 *
	 * 
	 * @return send back a comment for Server
	 */
	public String msgToServer() {
		return toServerString;
	}

}
