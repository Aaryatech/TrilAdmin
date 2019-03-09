package com.ats.tril.model;

import java.util.Date;

public class IssueReportItemwise {
	
	private int itemId;
	private int issueId;

	private String issueNo;

	private String itemCode;
	private String itemDesc;

	private int itemCategory;

	private String issueDate;

	private int status;

	private int deptId;

	private int subDeptId;

	private int accHead;

	private float itemIssueQty;

	private float itemRequestQty;

	private float itemPendingQty;

	private String deptDesc;
	private String subDeptDesc;

	private String catDesc;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	 
	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getSubDeptId() {
		return subDeptId;
	}

	public void setSubDeptId(int subDeptId) {
		this.subDeptId = subDeptId;
	}

	public int getAccHead() {
		return accHead;
	}

	public void setAccHead(int accHead) {
		this.accHead = accHead;
	}

	public float getItemIssueQty() {
		return itemIssueQty;
	}

	public void setItemIssueQty(float itemIssueQty) {
		this.itemIssueQty = itemIssueQty;
	}

	public float getItemRequestQty() {
		return itemRequestQty;
	}

	public void setItemRequestQty(float itemRequestQty) {
		this.itemRequestQty = itemRequestQty;
	}

	public float getItemPendingQty() {
		return itemPendingQty;
	}

	public void setItemPendingQty(float itemPendingQty) {
		this.itemPendingQty = itemPendingQty;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public String getSubDeptDesc() {
		return subDeptDesc;
	}

	public void setSubDeptDesc(String subDeptDesc) {
		this.subDeptDesc = subDeptDesc;
	}

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	@Override
	public String toString() {
		return "IssueReportItemwise [itemId=" + itemId + ", issueId=" + issueId + ", issueNo=" + issueNo + ", itemCode="
				+ itemCode + ", itemDesc=" + itemDesc + ", itemCategory=" + itemCategory + ", issueDate=" + issueDate
				+ ", status=" + status + ", deptId=" + deptId + ", subDeptId=" + subDeptId + ", accHead=" + accHead
				+ ", itemIssueQty=" + itemIssueQty + ", itemRequestQty=" + itemRequestQty + ", itemPendingQty="
				+ itemPendingQty + ", deptDesc=" + deptDesc + ", subDeptDesc=" + subDeptDesc + ", catDesc=" + catDesc
				+ "]";
	}

}
