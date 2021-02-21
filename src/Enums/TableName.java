package Enums;

import logic.Member;
import logic.Order;
import logic.Park;
import logic.Payment_detail;
import logic.Request;
import logic.Visitor;
import logic.Waiting_list;
import logic.Worker;
import logic.Possible_date;
import logic.Summary_day;
import logic.Invoice;

public enum TableName {

	visitors, orders, workers, members, parks, requests, waiting_lists, payment_details, Possible_dates, summary_days, invoices;

	public String getCategory(TableName tblName) {
		switch (tblName) {
		case visitors:
			return (new Visitor()).toCategory();

		case orders:
			return (new Order()).toCategory();

		case workers:
			return (new Worker()).toCategory();

		case parks:
			return (new Park()).toCategory();

		case members:
			return (new Member()).toCategory();

		case requests:
			return (new Request()).toCategory();

		case waiting_lists:
			return (new Waiting_list()).toCategory();

		case payment_details:
			return (new Payment_detail()).toCategory();

		case Possible_dates:
			return (new Possible_date()).toCategory();

		case summary_days:
			return (new Summary_day()).toCategory();

		case invoices:
			return (new Invoice()).toCategory();
		}

		return null;
	}

	public static TableName objToTable(ObjectKind objKind) {
		String temp;
		temp = objKind.toString().toLowerCase() + "s";
		System.out.println(temp);
		return TableName.valueOf(temp);
	}

	public static TableName objToTable(String objKind) {
		String temp;
		temp = objKind.toString().toLowerCase() + "s";
		System.out.println(temp);
		return TableName.valueOf(temp);
	}
}