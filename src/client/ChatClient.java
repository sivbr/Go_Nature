// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.ChatIF;
import javafx.collections.ObservableList;
import logic.Order;
import logic.Park;
import logic.Payment_detail;
import logic.Possible_date;
import logic.Request;
import logic.Summary_day;
import logic.Visitor;
import logic.Waiting_list;
import logic.Worker;
import logic.Invoice;
import logic.Member;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Enums.InvoiceKind;
import Enums.ObjectKind;
import Enums.ReqStatus;
import Enums.Role;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client. Most Of It REUSE
 * 
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * 
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version December 2020
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************
	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;
	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	public static Visitor visitor = new Visitor();
	public static Member member = new Member();
	public static Worker worker = new Worker();
	public static Order order = new Order();
	public static Park park = new Park();
	public static Payment_detail payment_detail = new Payment_detail();
	public static Request request = new Request();
	public static Waiting_list waiting_list = new Waiting_list();
	public static Possible_date possible_date = new Possible_date();
	public static Summary_day summary_day = new Summary_day();
	public static Invoice invoice = new Invoice();
	public static String[] stringArray;
	public static String[] info;
	public static String comment;
	public static ArrayList<String> list = new ArrayList<String>();
	public static boolean awaitResponse = false;
	public static boolean getObject = false;
	public static ObjectKind objectType;
	public static ArrayList<LocalDate> AvaDates = new ArrayList<LocalDate>();
	public static Map<LocalDate, ArrayList<Integer>> map = new HashMap<LocalDate, ArrayList<Integer>>();
	public static ObservableList<Order> MyHistory;
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server. Divided the
	 * information to diffrent mession according to the command from the server
	 * 
	 * @param msg The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {
		System.out.println("--> handleMessageFromServer");
		if (msg == null) {
			System.out.println("the server returns null !\nTHIS IS A problemtic situation - CALL 911");
			return;
		}

		String st;
		st = msg.toString();
		info = st.split("\\s");

		objectType = ObjectKind.valueOf(info[0]);
		switch (objectType) {
		case Visitor: {
			visitor.setValues(info[1], info[2], info[3], info[4], info[5], Integer.parseInt(info[6]));
			getObject = true;
			break;
		}
		case Member: {
			member.setValues(info[1], info[2], info[3], info[4], info[5], Integer.parseInt(info[6]), info[7], info[8],
					info[9], Integer.parseInt(info[10]));
			getObject = true;
			break;
		}
		case Worker: {
			worker.setValues(info[1], info[2], info[3], info[4], info[5], info[6], Role.valueOf(info[7]), info[8],
					info[9], Integer.parseInt(info[10]));
			getObject = true;
			break;
		}
		case Order: {
			order.setValues(Integer.parseInt(info[1]), info[2], info[3], Integer.parseInt(info[4]),
					Integer.parseInt(info[5]), Integer.parseInt(info[6]), Integer.parseInt(info[7]),
					Integer.parseInt(info[8]), Double.parseDouble(info[9]), Integer.parseInt(info[10]),
					Integer.parseInt(info[11]), info[12], Integer.parseInt(info[13]), Integer.parseInt(info[14]),
					Integer.parseInt(info[15]), Integer.parseInt(info[16]));
			getObject = true;
			break;
		}
		case Park: {
			park.setValues(info[1], info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]),
					Integer.parseInt(info[5]), Integer.parseInt(info[6]), Double.parseDouble(info[7]));
			getObject = true;
			break;
		}
		case Payment_detail: {
			payment_detail.setValues(info[1], info[2], info[3], info[4], info[5]);
			getObject = true;
			break;
		}

		case Request: {
			request.setValues(Integer.parseInt(info[1]), info[2], info[3], info[4], info[5], info[6], info[7],
					ReqStatus.valueOf(info[8]));
			getObject = true;
			break;
		}

		case Waiting_list: {
			waiting_list.setValues(Integer.parseInt(info[1]), info[2], Integer.parseInt(info[3]),
					Integer.parseInt(info[4]), Integer.parseInt(info[5]), Integer.parseInt(info[6]),
					Integer.parseInt(info[7]));
			getObject = true;
			break;
		}

		case Possible_date: {
			possible_date.setValues(info[1], info[2], info[3], Integer.parseInt(info[4]), Integer.parseInt(info[5]),
					Integer.parseInt(info[6]), Integer.parseInt(info[7]), Integer.parseInt(info[8]));
			getObject = true;
			break;
		}

		case Summary_day: {
			summary_day.setValues(info[1], info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]),
					Integer.parseInt(info[5]), Integer.parseInt(info[6]), Integer.parseInt(info[7]),
					Integer.parseInt(info[8]), Integer.parseInt(info[9]), Integer.parseInt(info[10]),
					Integer.parseInt(info[11]), Integer.parseInt(info[12]), Integer.parseInt(info[13]),
					Integer.parseInt(info[14]));
			getObject = true;
			break;
		}

		case Invoice: {
			invoice.setValues(Integer.parseInt(info[1]), InvoiceKind.valueOf(info[2]), info[3], Integer.parseInt(info[4]),
					Integer.parseInt(info[5]), Integer.parseInt(info[6]), Integer.parseInt(info[7]),
					Double.parseDouble(info[8]), Integer.parseInt(info[9]), Integer.parseInt(info[10]),
					Integer.parseInt(info[11]), Integer.parseInt(info[12]), Double.parseDouble(info[13]));
			getObject = true;
			break;
		}

		case Report: {
			if (list.isEmpty() == false)
				list.clear();
			comment = msg.toString().substring(6);
			System.out.println(comment);
			stringArray = comment.split(";");
			for (int i = 0; i < stringArray.length; i++) {
				list.add(stringArray[i].trim());
			}
			getObject = true;
			break;
		}

		case Message: {
			comment = st.substring(8);
			System.out.println(comment);
			getObject = false;
			break;
		}

		default:
			comment = "ChatClient problem";
			System.out.println(comment);
			getObject = false;
			break;

		}

		awaitResponse = false;
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(String message) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
			quit();
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class
