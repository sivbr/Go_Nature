package Enums;

public enum ObjectKind {

	Visitor,
	SystemMessage,
	Order,
	Message,
	Person,
	Worker,
	Member,
	Report,
	Park,
	Request,
	Waiting_list,
	Payment_detail,
	Possible_date,
	Summary_day,
	Invoice,
	FirstTime,
	Temporary;
	
	
	public static ObjectKind tableToObject(TableName tblName)
	{
		String temp;
		temp = tblName.toString().substring(1, tblName.toString().length()-1);
		temp = tblName.toString().substring(0, 1).toUpperCase() + temp;
		return ObjectKind.valueOf(temp);
	}
	public static ObjectKind tableToObject(String tblName)
	{
		String temp;
		temp = tblName.toString().substring(1, tblName.toString().length()-1);
		temp = tblName.toString().substring(0, 1).toUpperCase() + temp;
		return ObjectKind.valueOf(temp);
	}
	public static String tableToString(String tblName)
	{
		String str;
		str = tblName.toString().substring(1, tblName.toString().length()-1);
		str = tblName.toString().substring(0, 1).toUpperCase() + str;
		return str;
	}

	
	
}