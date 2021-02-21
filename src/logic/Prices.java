package logic;

/**
 * Class for all the common prices and discounts of the system
 * Such as : members \ organized group \ advance payment discounts....
 *
 */
public class Prices {

	private static double ticketPrice = 20;
	private static double memberDiscount = 0.8;
	private static double orderedPersonalVisitDiscount = 0.85;
	private static double orderedGroupVisitDiscount = 0.75;
	private static double UnplanedGroupVisitDiscount = 0.9;
	private static double AdvancePaymentDiscount = 0.88;
	private static double defultDiscount=7;
	
	public static double getDefultDiscount() {
		return defultDiscount;
	}
	public static double getTicketPrice() {
		return ticketPrice;
	}
	public static void setTicketPrice(double ticketPrice) {
		Prices.ticketPrice = ticketPrice;
	}
	public static double getMemberDiscount() {
		return memberDiscount;
	}
	public static void setMemberDiscount(double memberDiscount) {
		Prices.memberDiscount = memberDiscount;
	}
	public static double getOrderedPersonalVisitDiscount() {
		return orderedPersonalVisitDiscount;
	}
	public static void setOrderedPersonalVisitDiscount(double orderedPersonalVisitDiscount) {
		Prices.orderedPersonalVisitDiscount = orderedPersonalVisitDiscount;
	}
	public static double getOrderedGroupVisitDiscount() {
		return orderedGroupVisitDiscount;
	}
	public static void setOrderedGroupVisitDiscount(double orderedGroupVisitDiscount) {
		Prices.orderedGroupVisitDiscount = orderedGroupVisitDiscount;
	}
	public static double getUnplanedGroupVisitDiscount() {
		return UnplanedGroupVisitDiscount;
	}
	public static void setUnplanedGroupVisitDiscount(double unplanedGroupVisitDiscount) {
		UnplanedGroupVisitDiscount = unplanedGroupVisitDiscount;
	}
	public static double getAdvancePaymentDiscount() {
		return AdvancePaymentDiscount;
	}
	public static void setAdvancePaymentDiscount(double advancePaymentDiscount) {
		AdvancePaymentDiscount = advancePaymentDiscount;
	}



}
