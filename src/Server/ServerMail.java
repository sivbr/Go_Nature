package Server;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * This class is responsible to send mail to the current user/customer.
 * 
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version - Final
 */
public class ServerMail {
	/**
	 * 
	 */
	public static String userName = "gonatureteam12@gmail.com";
	public static String password ="GoNatureTeam12";
	
	public static void main(String[] args) {
		sendMail("liorsaghi@gmail.com", "You have an alert from the Go Nature system", "Coming Soon in new version....");
	}

	public static boolean sendMail(String addressMail, String subject, String msg) {
	      String host = "smtp.gmail.com"; 
	  
	      // Getting system properties 
		  	Properties properties = new Properties();
		  	properties.put("mail.smtp.auth", true);
		  	properties.put("mail.smtp.starttls.enable", true);
		  	properties.put("mail.smtp.host", "smtp.gmail.com");
		  	properties.put("mail.smtp.port", "587");
		  	// Setting up mail server 
		  	properties.setProperty("mail.smtp.host", host); 
	  
		  	// creating session object to get properties 
		  	Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
		  		protected PasswordAuthentication getPasswordAuthentication() {
		  			return new PasswordAuthentication(userName, password);
		  		}
		  	});
	      try 
	      { 
	         // MimeMessage object. 
	         MimeMessage message = new MimeMessage(session); 
	         // adding sender name. 
	         message.setFrom("Go_Nature");
	         // Set To Field: adding recipient's email to from field. 
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressMail)); 

	         // Set Subject: subject of the email 
	         message.setSubject(subject); 
	  
	         // set body of the email. 
	         message.setText(msg); 
	  
	         // Send email. 
	         Transport.send(message);
	         return true;
	      } 
	      catch (MessagingException mex)  
	      { 
	         mex.printStackTrace(); 
	      }
	      return false;
	}
}
