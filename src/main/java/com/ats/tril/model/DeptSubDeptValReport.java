package com.ats.tril.model;

public class DeptSubDeptValReport {
	
	private int subDeptId;
	private String subDeptCode;

	private int deptId;
	private String deptCode;

   
	private float issueQty;
	
	private float issueQtyValue;
	
	private float issueLandingValue;

	public int getSubDeptId() {
		return subDeptId;
	}

	public void setSubDeptId(int subDeptId) {
		this.subDeptId = subDeptId;
	}

	public String getSubDeptCode() {
		return subDeptCode;
	}

	public void setSubDeptCode(String subDeptCode) {
		this.subDeptCode = subDeptCode;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public float getIssueQty() {
		return issueQty;
	}

	public void setIssueQty(float issueQty) {
		this.issueQty = issueQty;
	}

	public float getIssueQtyValue() {
		return issueQtyValue;
	}

	public void setIssueQtyValue(float issueQtyValue) {
		this.issueQtyValue = issueQtyValue;
	}

	public float getIssueLandingValue() {
		return issueLandingValue;
	}

	public void setIssueLandingValue(float issueLandingValue) {
		this.issueLandingValue = issueLandingValue;
	}

	@Override
	public String toString() {
		return "DeptSubDeptValReport [subDeptId=" + subDeptId + ", subDeptCode=" + subDeptCode + ", deptId=" + deptId
				+ ", deptCode=" + deptCode + ", issueQty=" + issueQty + ", issueQtyValue=" + issueQtyValue
				+ ", issueLandingValue=" + issueLandingValue + "]";
	}


}
