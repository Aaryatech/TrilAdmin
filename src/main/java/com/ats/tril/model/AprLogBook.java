package com.ats.tril.model;

public class AprLogBook {

	
	private int logId;
	private int docType;
	private String reqDate;
	private String app1Date;
	private String app2Date;
	
	private String apr1User;
	private String apr2User;
	
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
	public String getApp1Date() {
		return app1Date;
	}
	public void setApp1Date(String app1Date) {
		this.app1Date = app1Date;
	}
	public String getApp2Date() {
		return app2Date;
	}
	public void setApp2Date(String app2Date) {
		this.app2Date = app2Date;
	}
	public String getApr1User() {
		return apr1User;
	}
	public void setApr1User(String apr1User) {
		this.apr1User = apr1User;
	}
	public String getApr2User() {
		return apr2User;
	}
	public void setApr2User(String apr2User) {
		this.apr2User = apr2User;
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
	
	@Override
	public String toString() {
		return "AprLogBook [logId=" + logId + ", docType=" + docType + ", reqDate=" + reqDate + ", app1Date=" + app1Date
				+ ", app2Date=" + app2Date + ", apr1User=" + apr1User + ", apr2User=" + apr2User + ", docNo=" + docNo
				+ ", docDate=" + docDate + "]";
	}
	
}
