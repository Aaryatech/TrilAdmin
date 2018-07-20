package com.ats.tril.model;
 

public class IssueDetail {
	
	 
	private int issueDetailId; 
	private int issueId; 
	private int itemGroupId; 
	private int deptId; 
	private int subDeptId; 
	private int accHead; 
	private int itemId; 
	private int itemIssueQty; 
	private int itemRequestQty; 
	private int itemPendingQty; 
	private int delStatus; 
	private int status;
	
	private String itemName;
	private String groupName;
	private String deptName;
	private String subDeptName;
	private String accName; 
	
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getSubDeptName() {
		return subDeptName;
	}
	public void setSubDeptName(String subDeptName) {
		this.subDeptName = subDeptName;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public int getIssueDetailId() {
		return issueDetailId;
	}
	public void setIssueDetailId(int issueDetailId) {
		this.issueDetailId = issueDetailId;
	}
	public int getIssueId() {
		return issueId;
	}
	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}
	public int getItemGroupId() {
		return itemGroupId;
	}
	public void setItemGroupId(int itemGroupId) {
		this.itemGroupId = itemGroupId;
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
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getItemIssueQty() {
		return itemIssueQty;
	}
	public void setItemIssueQty(int itemIssueQty) {
		this.itemIssueQty = itemIssueQty;
	}
	public int getItemRequestQty() {
		return itemRequestQty;
	}
	public void setItemRequestQty(int itemRequestQty) {
		this.itemRequestQty = itemRequestQty;
	}
	public int getItemPendingQty() {
		return itemPendingQty;
	}
	public void setItemPendingQty(int itemPendingQty) {
		this.itemPendingQty = itemPendingQty;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "IssueDetail [issueDetailId=" + issueDetailId + ", issueId=" + issueId + ", itemGroupId=" + itemGroupId
				+ ", deptId=" + deptId + ", subDeptId=" + subDeptId + ", accHead=" + accHead + ", itemId=" + itemId
				+ ", itemIssueQty=" + itemIssueQty + ", itemRequestQty=" + itemRequestQty + ", itemPendingQty="
				+ itemPendingQty + ", delStatus=" + delStatus + ", status=" + status + ", itemName=" + itemName
				+ ", groupName=" + groupName + ", deptName=" + deptName + ", subDeptName=" + subDeptName + ", accName="
				+ accName + "]";
	}
	
	

}
