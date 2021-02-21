package logic;
/**
 * 
 * This class represent a Member of the company
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version January 2021
 * 
 */

public class Member extends Visitor implements TableAble {

	private int numberOfFamilyMembers;
	private String payType;
	private String memberNumber;
	private String organizationGroup;
	/**
	 * 
	 * Constructor for Build The Member
	 * @param ID
	 * @param firstName
	 * @param lastName
	 * @param mail
	 * @param phone
	 * @param numberOfFamilyMembers
	 * @param payType
	 * @param memberNumber
	 * @param organizationGroup
	 * @param online
	 */
	public Member(String ID, String firstName, String lastName, String mail, String phone, int numberOfFamilyMembers,
			String payType, String memberNumber, String organizationGroup, int online) {
		super(ID, firstName, lastName, mail, phone, online);
		this.numberOfFamilyMembers = numberOfFamilyMembers;
		this.payType = payType;
		this.memberNumber = memberNumber;
		this.organizationGroup = organizationGroup;
	}
	/**
	 *  Constructor for Build The Member From Visitor
	 * @param v
	 * @param numberOfFamilyMembers
	 * @param payType
	 * @param memberNumber
	 * @param organizationGroup
	 */
	public Member(Visitor v, int numberOfFamilyMembers, String payType, String memberNumber, String organizationGroup) {
		super(v);
		this.numberOfFamilyMembers = numberOfFamilyMembers;
		this.payType = payType;
		this.memberNumber = memberNumber;
		this.organizationGroup = organizationGroup;
	}
	/**
	 *  Constructor for Build The Member From Member - Demi Constructor
	 *@param m
	 */
	public Member(Member m) {
		super(m.getID(), m.getfirstName(), m.getlastName(), m.getMail(), m.getPhone(), m.getOnline());
		this.numberOfFamilyMembers = m.numberOfFamilyMembers;
		this.payType = m.payType;
		this.memberNumber = m.memberNumber;
		this.organizationGroup = m.organizationGroup;
	}
	/**
	 * Constructor for Build The Member From one String 
	 * @param objectString
	 */
	public Member(String objectString) {
		String[] info = objectString.split("\\s");
		setValues(info[0], info[1], info[2], info[3], info[4], Integer.parseInt(info[5]), info[6], info[7], info[8],
				Integer.parseInt(info[9]));
	}
	
	public void setValues(String ID, String firstName, String lastName, String mail, String phone,
			int numberOfFamilyMembers, String payType, String memberNumber, String organizationGroup, int online) {
		super.setID(ID);
		super.setfirstName(firstName);
		super.setlName(lastName);
		this.mail = mail;
		this.phone = phone;
		this.numberOfFamilyMembers = numberOfFamilyMembers;
		this.payType = payType;
		this.memberNumber = memberNumber;
		this.organizationGroup = organizationGroup;
		this.online = online;
	}

	public String getOrganizationGroup() {
		return organizationGroup;
	}

	public void setOrganizationGroup(String organizationGroup) {
		this.organizationGroup = organizationGroup;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	public Member() {
	}

	public int getNumberOfFamilyMembers() {
		return numberOfFamilyMembers;
	}

	public void setNumberOfFamilyMembers(int numberOfFamilyMembers) {
		this.numberOfFamilyMembers = numberOfFamilyMembers;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberID(String memberNumber) {
		this.memberNumber = memberNumber;
	}
	
	
	public String toString() {
		return String.format("%s %s %s %s %s %d %s %s %s %d\n", getID(), getfirstName(), getlastName(), getMail(),
				getPhone(), getNumberOfFamilyMembers(), getPayType(), getMemberNumber(), getOrganizationGroup(),
				getOnline());
	}
	/**
	 * Function that returns All the name of the fileds of the object.
	 * The functionOverride from the Interface TableAble
	 */
	@Override
	public String toCategory() {
		return String.format("%s %s %s %s %s %s %s %s %s %s\n", "ID", "firstName", "lastName", "mail", "phone",
				"numberOfFamilyMembers", "payType", "memberNumber", "organizationGroup", "online");
	}

}
