package logic;

import Enums.Role;
/**
 * 
 * This Object represent a worker of the park
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 */
public class Worker extends Person implements TableAble {

	private String workerID;
	private String password;
	private String workerNumber;
	private Role role;
	private String phone;
	private String mail;
	private String park;
	private int online;
	/**
	 * Regular Constructor
	 * @param workerNumber
	 * @param ID
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param mail
	 * @param role
	 * @param phone
	 * @param park
	 * @param online
	 */
	public Worker(String workerNumber, String ID, String password, String firstName, String lastName, String mail,
			Role role, String phone, String park, int online) {
		super(ID, firstName, lastName);
		this.workerNumber = workerNumber;
		this.password = password;
		this.role = role;
		this.phone = phone;
		this.mail = mail;
		this.park = park;
		this.online = online;
	}
	/**
	 * Semi Constructor = build Worker from Worker
	 * @param w
	 */
	public Worker(Worker w) {
		super(w.getID(), w.getfirstName(), w.getlastName());
		this.workerNumber = w.workerNumber;
		this.password = w.password;
		this.role = w.role;
		this.phone = w.phone;
		this.mail = w.mail;
		this.park = w.park;
		this.online = w.online;
	}
	/**
	 * build Worker from one String
	 * @param objectString
	 */
	public Worker (String objectString) {
		String[] info=objectString.split("\\s");
		setValues(info[0], info[1], info[2], info[3], info[4], info[5], Role.valueOf(info[6]), info[7],
				info[8], Integer.parseInt(info[9]));
	}
	public void setValues(String workerNumber, String ID, String password, String firstName, String lastName, String mail,
			Role role, String phone, String park, int online) {
		this.workerNumber = workerNumber;
		super.setID(ID);
		this.password = password;
		super.setfirstName(firstName);
		super.setlName(lastName);
		this.role = role;
		this.phone = phone;
		this.mail = mail;
		this.park = park;
		this.online = online;
	}

	public Worker() {
	}

	public String getWorkerID() {
		return workerID;
	}

	public void setWorkerID(String workerID) {
		this.workerID = workerID;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWorkerNumber() {
		return workerNumber;
	}

	public void setWorkerNumber(String workerNumber) {
		this.workerNumber = workerNumber;
	}

	public String getPark() {
		return park;
	}

	public void setPark(String park) {
		this.park = park;
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
	public String toCategory() {
		return String.format("%s %s %s %s %s %s %s %s %s %s\n", "workerNumber", "ID", "password", "firstName",
				"lastName", "mail", "role", "phone", "park", "online");
		// String mail, String ,"role, String phone, String park
	}

	public String toString() {
		return String.format("%s %s %s %s %s %s %s %s %s %d\n", getWorkerNumber(), getID(), getPassword(),
				getfirstName(), getlastName(), getMail(), getRole().toString(), getPhone(), getPark(), getOnline());
	}

}