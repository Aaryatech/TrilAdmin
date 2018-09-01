package com.ats.tril.model;
 

public class GetCurrentStock {
	
	 
	private int itemId; 
	private String itemCode; 
	private float openingStock; 
	private float approveQty; 
	private float issueQty; 
	private float returnIssueQty; 
	private float damageQty; 
	private float gatepassQty; 
	private float gatepassReturnQty;
	private float rolLevel;
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public float getOpeningStock() {
		return openingStock;
	}
	public void setOpeningStock(float openingStock) {
		this.openingStock = openingStock;
	}
	public float getApproveQty() {
		return approveQty;
	}
	public void setApproveQty(float approveQty) {
		this.approveQty = approveQty;
	}
	public float getIssueQty() {
		return issueQty;
	}
	public void setIssueQty(float issueQty) {
		this.issueQty = issueQty;
	}
	public float getReturnIssueQty() {
		return returnIssueQty;
	}
	public void setReturnIssueQty(float returnIssueQty) {
		this.returnIssueQty = returnIssueQty;
	}
	public float getDamageQty() {
		return damageQty;
	}
	public void setDamageQty(float damageQty) {
		this.damageQty = damageQty;
	}
	public float getGatepassQty() {
		return gatepassQty;
	}
	public void setGatepassQty(float gatepassQty) {
		this.gatepassQty = gatepassQty;
	}
	public float getGatepassReturnQty() {
		return gatepassReturnQty;
	}
	public void setGatepassReturnQty(float gatepassReturnQty) {
		this.gatepassReturnQty = gatepassReturnQty;
	}
	
	public float getRolLevel() {
		return rolLevel;
	}
	public void setRolLevel(float rolLevel) {
		this.rolLevel = rolLevel;
	}
	@Override
	public String toString() {
		return "GetCurrentStock [itemId=" + itemId + ", itemCode=" + itemCode + ", openingStock=" + openingStock
				+ ", approveQty=" + approveQty + ", issueQty=" + issueQty + ", returnIssueQty=" + returnIssueQty
				+ ", damageQty=" + damageQty + ", gatepassQty=" + gatepassQty + ", gatepassReturnQty="
				+ gatepassReturnQty + ", rolLevel=" + rolLevel + "]";
	}
	
	

}
