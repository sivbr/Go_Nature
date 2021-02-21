package logic;

import java.util.ArrayList;
import Enums.Command;
import Enums.TableName;
import client.CardReaderConsole;
import client.ClientUI;


/**
 * 
 * This class helps to analyzed and send in the easiest way requests to the server
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version December 2020
 * 
 */
public class MessageToServer {

	
	private Command command; // Command - Read,ReadLastRecord,Update,Report,Add,Delete,SendMail;
	private String categoryKeyName; // Name of the category of the main key
	private TableName table; // The table we want to search in
	private String key; // The main key we search for
	private String stringLine = ""; // The String we use for convert the object to String and the opposite
	
	/**
	 * Standard Constructor for the Action Read,ReadLastRecord,Update,Add,Delete;
	 * @param command
	 * @param table
	 * @param key
	 */
	public MessageToServer(Command command, TableName table, String key) {
		this.command = command;
		this.table = table;
		this.key = key;
		stringLine = "";
		categoryKeyName = table.getCategory(table).split("\\s")[0];

	}

	public MessageToServer() {
		this.command = Command.Report;
	}

	public String getInfo() {

		return (this.stringLine);

	}
	/**
	 * Add Information for Send a Query to Server
	 * @param conditionForDB
	 * @return
	 * @throws Exception
	 */
	public MessageToServer addReportInfo(String conditionForDB) throws Exception {
		if (command != Command.Report)
			throw new Exception("If you want to Use Add Report Function - The command has to be Report");
		stringLine = conditionForDB;
		return this;
	}
	/**
	 * Add information to send a Mail to the server
	 * @param addressMail
	 * @param subject
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public MessageToServer addMailInfo(String addressMail, String subject, String msg) throws Exception {
		this.command = Command.SendMail;
		stringLine = addressMail + ";" + subject + ";" + msg;
		return this;
	}
	/**
	 * Add information for the Update standard query
	 * @param category
	 * @param setValue
	 * @return
	 */
	public MessageToServer addInfo(String category, String setValue) {

		stringLine = stringLine.trim();
		stringLine += " " + category + ";" + setValue;
		return this;
	}
	/**
	 * Add information for the Update standard query
	 * @param category
	 * @param setValue
	 * @return
	 */
	public MessageToServer addInfo(String category, int setValue) {
		return addInfo(category, setValue + "");

	}
	/**
	 * Add information for the Update standard query
	 * @param category
	 * @param setValue
	 * @return
	 */
	public MessageToServer addInfo(String category, double setValue) {
		return addInfo(category, setValue + "");

	}
	/**
	 * Add an Object for the Update standard query
	 * @param category
	 * @param setValue
	 * @return
	 */
	public MessageToServer editObject(TableAble obj) throws Exception {
		String[] values = (obj.toString()).split("\\s");
		String[] categorys = (obj.toCategory()).split("\\s");

		for (int i = 0; i < Math.min(values.length, categorys.length); i++) {
			stringLine += " " + categorys[i] + ";" + values[i];
			stringLine = stringLine.trim();
		}
		return this;

	}
	/**
	 * Add an Object for the Add standard query
	 * @param category
	 * @param setValue
	 * @return
	 */
	public MessageToServer addObject(TableAble obj) throws Exception {
		String[] values = (obj.toString()).split("\\s");
		if (command != Command.Add) throw new Exception("Command has to be Add. Use it without Arguments");
		for (int i = 0; i < values.length; i++) {

				stringLine += " " + values[i];
				stringLine = stringLine.trim();
		}
		return this;
		
	}

	/**
	 * Build A specific MessageToServer Object from a specific String
	 * @param msg
	 */
	
	public MessageToServer(String msg) {
		String[] words = msg.split("\\s");
		command = Command.valueOf(words[0]);
		if (command == Command.Report) {
			stringLine = msg.substring(7);
			return;
		}
		if (command == Command.SendMail) {
			stringLine = msg.substring(9);
			return;
		}

		table = TableName.valueOf(words[1]);
		key = words[2];

		categoryKeyName = words[3];

		int i = 4;

		while (i < words.length) {
			stringLine += " " + words[i++];
		}
		stringLine = stringLine.trim();

	}
		
	public void setValues(String msg) {
		String[] words = msg.split("\\s");
		command = Command.valueOf(words[0]);
		if (command == Command.Report) {
			stringLine = msg.substring(7);
			return;
		}
		if (command == Command.SendMail) {
			stringLine = msg.substring(9);
			return;
		}

		table = TableName.valueOf(words[1]);
		key = words[2];

		categoryKeyName = words[3];

		int i = 4;
		stringLine = "";
		while (i < words.length) {
			stringLine += " " + words[i++];
		}
		stringLine = stringLine.trim();

	}
	/**
	 * Build A specific String from the data of MessageToServer Object
	 * Send the string from the MainThread to the Server
	 * @param msg
	 */
	public void toAcceptThread() 
	{
		
	
			String message = "";
			if (command == Command.Report || command == Command.SendMail) {
				message += command + " ";
				message += stringLine;
				CardReaderConsole.chat2.accept(message);
				return;
			}
	
			message += command + " " + table + " " + key + " " + categoryKeyName;
	
			message += " " + stringLine;
			CardReaderConsole.chat2.accept(message);
		
	}
	/**
	 * Build A specific String from the data of MessageToServer Object
	 * Send the string from the ClientUI to the Server
	 * @param msg
	 */
	public void toAccept() {
		String message = "";
		if (command == Command.Report || command == Command.SendMail) {
			message += command + " ";
			message += stringLine;
			ClientUI.chat.accept(message);
			return;
		}

		message += command + " " + table + " " + key + " " + categoryKeyName;

		message += " " + stringLine;
		ClientUI.chat.accept(message);

	}

	

	public Command getCommand() {
		return command;
	}

	public String getTable() {
		return table.toString();
	}

	

	public String categoryKey() {
		return categoryKeyName;
	}



	public String getKey() {
		return key;
	}

	

}
