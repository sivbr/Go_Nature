package logic;

/**
 * 
 * This class represent a park of the company
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 * @version December 2020
 * 
 */

public class Park implements TableAble {
	private String name;
	private String city;
	private int maxCapacity;
	private int occasionalVisitors;
	private int currentAllVisitors;
	private int currentOccasionalVisitors;
	private double discount;
	
	/**
	 * Regular constructor for Park
	 * @param name
	 * @param city
	 * @param maxCapacity
	 * @param occasionalVisitors
	 * @param currentAllVisitors
	 * @param currentOccasionalVisitors
	 * @param discount
	 */
	

	public Park(String name, String city, int maxCapacity, int occasionalVisitors, int currentAllVisitors,
			int currentOccasionalVisitors, double discount) {
		super();
		this.name = name;
		this.city = city;
		this.maxCapacity = maxCapacity;
		this.occasionalVisitors = occasionalVisitors;
		this.currentAllVisitors = currentAllVisitors;
		this.currentOccasionalVisitors = currentOccasionalVisitors;
		this.discount=discount;
	}
	
	/**
	 * Constructor for Build The Park From Park - Demi Constructor
	 * @param p
	 */
	public Park(Park p) {
		this.name = p.name;
		this.city = p.city;
		this.maxCapacity = p.maxCapacity;
		this.occasionalVisitors = p.occasionalVisitors;
		this.currentAllVisitors = p.currentAllVisitors;
		this.currentOccasionalVisitors = p.currentOccasionalVisitors;
		this.discount=p.discount;
	}
	/** Constructor for Build The Park From one String 
	 * @param objectString
	 */
	public Park (String objectString) {
		String[] info=objectString.split("\\s");
		setValues(info[0], info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]),
				Integer.parseInt(info[4]), Integer.parseInt(info[5]), Double.parseDouble(info[6]));
	}
	public void setValues(String name, String city, int maxCapacity, int occasionalVisitors, int currentAllVisitors,
			int currentOccasionalVisitors, double discount) {
		this.name = name;
		this.city = city;
		this.maxCapacity = maxCapacity;
		this.occasionalVisitors = occasionalVisitors;
		this.currentAllVisitors = currentAllVisitors;
		this.currentOccasionalVisitors = currentOccasionalVisitors;
		this.discount=discount;
	}


	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getCurrentOccasionalVisitors() {
		return currentOccasionalVisitors;
	}

	public void setCurrentOccasionalVisitors(int currentOccasionalVisitors) {
		this.currentOccasionalVisitors = currentOccasionalVisitors;
	}

	public Park() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getOccasionalVisitors() {
		return occasionalVisitors;
	}

	public void setOccasionalVisitors(int occasionalVisitors) {
		this.occasionalVisitors = occasionalVisitors;
	}

	public int getCurrentAllVisitors() {
		return currentAllVisitors;
	}

	public void setCurrentAllVisitors(int currentAllVisitors) {
		this.currentAllVisitors = currentAllVisitors;
	}

	@Override
	public String toString() {
		return String.format("%s %s %d %d %d %d %.2f", name, city, maxCapacity, occasionalVisitors, currentAllVisitors, currentOccasionalVisitors,discount);
	}

	/**
	 * Function that returns All the name of the fileds of the object.
	 * The functionOverride from the Interface TableAble
	 */
	@Override
	public String toCategory() {
		return String.format("%s %s %s %s %s %s %s", "name", "city", "maxCapacity", "occasionalVisitors",
				"currentAllVisitors", "currentOccasionalVisitors","discount");
	}

}