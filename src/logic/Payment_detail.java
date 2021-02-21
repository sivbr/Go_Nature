package logic;

/**
 * 
 * This class represent the payment details of the customers
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version December 2020
 * 
 */

public class Payment_detail implements TableAble  {
	private String memberID;
	private String cardNumber;
	private String cardId;
	private String cardValid;
	private String cardCVC;
	
	/**
	 *  Regular constructor
	 * @param memberID
	 * @param cardNumber
	 * @param cardId
	 * @param cardValid
	 * @param cardCVC
	 */
	public Payment_detail(String memberID, String cardNumber, String cardId, String cardValid, String cardCVC) {
		super();
		this.memberID = memberID;
		this.cardNumber = cardNumber;
		this.cardId = cardId;
		this.cardValid = cardValid;
		this.cardCVC = cardCVC;
	}
	/**
	 * this constructor is semi - constructor, build Payment_detail from Payment_detail
	 * @param pd
	 */
	public Payment_detail(Payment_detail pd) 
	{
		this.memberID = pd.memberID;
		this.cardNumber = pd.cardNumber;
		this.cardId = pd.cardId;
		this.cardValid = pd.cardValid;
		this.cardCVC = pd.cardCVC;
	}
	/**
	 * Constructor that build Payment_detail from one String
	 * @param objectString
	 */
	public Payment_detail (String objectString) {
		String[] info=objectString.split("\\s");
		setValues(info[0], info[1], info[2], info[3], info[4]);
	}
	
	public void setValues(String memberID, String cardNumber, String cardId, String cardValid, String cardCVC)
	{
		this.memberID = memberID;
		this.cardNumber = cardNumber;
		this.cardId = cardId;
		this.cardValid = cardValid;
		this.cardCVC = cardCVC;
	}
	/**
	 * empty constructor
	 */
	public Payment_detail() {}
	
	public String getMemberID() {
		return memberID;
	}


	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}


	public String getCardNumber() {
		return cardNumber;
	}


	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}


	public String getCardId() {
		return cardId;
	}


	public void setCardId(String cardId) {
		this.cardId = cardId;
	}


	public String getCardValid() {
		return cardValid;
	}


	public void setCardValid(String cardValid) {
		this.cardValid = cardValid;
	}


	public String getCardCVC() {
		return cardCVC;
	}


	public void setCardCVC(String cardCVC) {
		this.cardCVC = cardCVC;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s %s %s %s %s", memberID,cardNumber,cardId,cardValid,cardCVC);
	}


	/**
	 * Function that returns All the name of the fileds of the object.
	 * The functionOverride from the Interface TableAble
	 */
	@Override
	public String toCategory() {
		return String.format("%s %s %s %s %s", "memberID","cardNumber","cardID","cardValid","cardCVC");
	}
	
	
	
	
}