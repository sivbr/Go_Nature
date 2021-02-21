package logic;
/**
 * 
 * This Object represent a regular visitor of the park
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 */
public class Visitor extends Person implements TableAble {
	////////////////////////////// Variables
	String mail;
	String phone;
	int online;
	
	/////////////////////////////////////////////////////CONSTRACTORS:
	/**
	 * Regular Constructor and make a the parent object
	 * @param ID
	 * @param firstName
	 * @param lastName
	 * @param mail
	 * @param phone
	 * @param online
	 */
	public Visitor(String ID, String firstName, String lastName, String mail, String phone, int online) 
	{
		super(ID,firstName,lastName);
		this.mail = mail;
		this.phone = phone;
		this.online=online;
	}
	
	/**
	 * Empty constructor
	 */
	public Visitor() {}
	
	/**
	 * Semi constructor - Build Visitor from Visitor
	 */
	public Visitor(Visitor v) 
	{
		super(v.getID(),v.getfirstName(),v.getlastName());
		this.mail = v.mail;
		this.phone = v.phone;
	}
	/**
	 *  Build Visitor from one String
	 */
	public Visitor (String objectString) {
		String[] info=objectString.split("\\s");
		setValues(info[0], info[1], info[2], info[3], info[4], Integer.parseInt(info[5]));
	}
	
	public void setValues(String ID, String firstName, String lastName, String mail, String phone, int online)
	{
		super.setID(ID);
		super.setfirstName(firstName);
		super.setlName(lastName);
		this.mail = mail;
		this.phone = phone;
		this.online=online;
	}
	@Override
	public String getID() {
		return super.getID();
	}
	public void setID(String ID) {
		super.setID(ID);
	}
	public String getfirstName() {
		return super.getfirstName();
	}
	public void setfirstName(String firstName) {
		super.setfirstName(firstName);
	}
	public String getlastName() {
		return super.getlastName();
	}
	public void setlastName(String lastName) {
		super.setlName(lastName);
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	public int getOnline() {
		return online;
	}


	public void setOnline(int online) {
		this.online = online;
	}
	
	/**
	 * Function that returns All the name of the fileds of the object.
	 * The functionOverride from the Interface TableAble
	 */
	@Override
	public String toCategory()
	{
		return String.format("%s %s %s %s %s %s\n","ID","firstName","lastName","mail","phone","online");
	}
	public String toString(){
		return String.format("%s %s %s %s %s %d\n",super.getID(),getfirstName(),getlastName(),mail,phone,getOnline());
	}
	
}
