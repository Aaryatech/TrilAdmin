package com.ats.tril.model;

import java.util.Date;

public class AprLogBook {

	
	private int logId;
	private int docTranId;
	public int getDocTranId() {
		return docTranId;
	}
	public void setDocTranId(int docTranId) {
		this.docTranId = docTranId;
	}

	private int docType;
	private String reqDate;
	private String aprDate1;
	private String aprDate2;
	
	private String aprUser1;
	private String aprUser2;
	
	
	private String docNo;
	private String docDate;
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public int getDocType() {
		return docType;
	}
	public void setDocType(int docType) {
		this.docType = docType;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getDocDate() {
		return docDate;
	}
	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}
	
	
	
	
	public String getAprDate1() {
		return aprDate1;
	}
	public void setAprDate1(String aprDate1) {
		this.aprDate1 = aprDate1;
	}
	public String getAprDate2() {
		return aprDate2;
	}
	public void setAprDate2(String aprDate2) {
		this.aprDate2 = aprDate2;
	}
	public String getAprUser1() {
		return aprUser1;
	}
	public void setAprUser1(String aprUser1) {
		this.aprUser1 = aprUser1;
	}
	public String getAprUser2() {
		return aprUser2;
	}
	public void setAprUser2(String aprUser2) {
		this.aprUser2 = aprUser2;
	}
	
	@Override
	public String toString() {
		return "AprLogBook [logId=" + logId + ", docTranId=" + docTranId + ", docType=" + docType + ", reqDate="
				+ reqDate + ", aprDate1=" + aprDate1 + ", aprDate2=" + aprDate2 + ", aprUser1=" + aprUser1
				+ ", aprUser2=" + aprUser2 + ", docNo=" + docNo + ", docDate=" + docDate + "]";
	}
	
}
