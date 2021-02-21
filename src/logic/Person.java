package logic;


/**
 * 
 * This Object is The Parents Of all the human objects
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 */
public class Person  {
	
	private String ID;
	private String firstName;
	private String lastName;
	
	public Person(String iD, String firstName, String lastName) {
		super();
		ID = iD;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	protected Person() {}
	
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getfirstName() {
		return firstName;
	}
	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getlastName() {
		return lastName;
	}
	public void setlName(String lastName) {
		this.lastName = lastName;
	}
	
	
	
	@Override
	public String toString() {
		return String.format("%s %s %s\n",ID,firstName,lastName);
	}
	
}
