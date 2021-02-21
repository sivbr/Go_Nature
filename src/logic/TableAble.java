package logic;
/**
 * 
 * This interface is necessary for objects that we save in tables,
 * this interface make the connection.
 * @author Tomer Dabun
 * @author Lior Saghi
 * @author Shay Feld
 * @author Sivan Brecher
 * @author Sapir Baron
 * @author Coral Harel
 */
public interface TableAble 
{
	/**
	 * get the name of the fileds in sql of the object.
	 * @return
	 */
	public String toCategory();
	public String toString();
	
}
