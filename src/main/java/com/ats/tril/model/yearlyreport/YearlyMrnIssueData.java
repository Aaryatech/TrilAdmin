package com.ats.tril.model.yearlyreport;

import java.util.List;

public class YearlyMrnIssueData {
	
	private int month;
	private String year;
	private String dateStr;
	
	List<TypeWiseAmount> typeWiseAmount;

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public List<TypeWiseAmount> getTypeWiseAmount() {
		return typeWiseAmount;
	}

	public void setTypeWiseAmount(List<TypeWiseAmount> typeWiseAmount) {
		this.typeWiseAmount = typeWiseAmount;
	}

	@Override
	public String toString() {
		return "YearlyMrnIssueData [month=" + month + ", year=" + year + ", dateStr=" + dateStr + ", typeWiseAmount="
				+ typeWiseAmount + "]";
	}
	
	

}
