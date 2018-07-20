package com.ats.tril.model;
 

public class GetIssueDetail {
	 
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
	private String itemCode; 
	private String grpCode; 
	private String deptCode; 
	private String subDeptCode; 
	private String accHeadDesc;
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
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getGrpCode() {
		return grpCode;
	}
	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getSubDeptCode() {
		return subDeptCode;
	}
	public void setSubDeptCode(String subDeptCode) {
		this.subDeptCode = subDeptCode;
	}
	public String getAccHeadDesc() {
		return accHeadDesc;
	}
	public void setAccHeadDesc(String accHeadDesc) {
		this.accHeadDesc = accHeadDesc;
	}
	@Override
	public String toString() {
		return "GetIssueDetail [issueDetailId=" + issueDetailId + ", issueId=" + issueId + ", itemGroupId="
				+ itemGroupId + ", deptId=" + deptId + ", subDeptId=" + subDeptId + ", accHead=" + accHead + ", itemId="
				+ itemId + ", itemIssueQty=" + itemIssueQty + ", itemRequestQty=" + itemRequestQty + ", itemPendingQty="
				+ itemPendingQty + ", delStatus=" + delStatus + ", status=" + status + ", itemCode=" + itemCode
				+ ", grpCode=" + grpCode + ", deptCode=" + deptCode + ", subDeptCode=" + subDeptCode + ", accHeadDesc="
				+ accHeadDesc + "]";
	}
	
	

}
