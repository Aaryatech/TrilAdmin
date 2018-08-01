package com.ats.tril.model;
 

public class StockDetail {
	
	 
	private int stockDetailId; 
	private int stockHeaderId; 
	private int itemId; 
	private int opStockQty; 
	private int approvedQty; 
	private int issueQty; 
	private int returnIssueQty; 
	private int damageQty; 
	private int gatepassQty; 
	private int gatepassReturnQty; 
	private int closingQty; 
	private int delStatus;
	public int getStockDetailId() {
		return stockDetailId;
	}
	public void setStockDetailId(int stockDetailId) {
		this.stockDetailId = stockDetailId;
	}
	public int getStockHeaderId() {
		return stockHeaderId;
	}
	public void setStockHeaderId(int stockHeaderId) {
		this.stockHeaderId = stockHeaderId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getOpStockQty() {
		return opStockQty;
	}
	public void setOpStockQty(int opStockQty) {
		this.opStockQty = opStockQty;
	}
	public int getApprovedQty() {
		return approvedQty;
	}
	public void setApprovedQty(int approvedQty) {
		this.approvedQty = approvedQty;
	}
	public int getIssueQty() {
		return issueQty;
	}
	public void setIssueQty(int issueQty) {
		this.issueQty = issueQty;
	}
	public int getReturnIssueQty() {
		return returnIssueQty;
	}
	public void setReturnIssueQty(int returnIssueQty) {
		this.returnIssueQty = returnIssueQty;
	}
	public int getDamageQty() {
		return damageQty;
	}
	public void setDamageQty(int damageQty) {
		this.damageQty = damageQty;
	}
	public int getGatepassQty() {
		return gatepassQty;
	}
	public void setGatepassQty(int gatepassQty) {
		this.gatepassQty = gatepassQty;
	}
	public int getGatepassReturnQty() {
		return gatepassReturnQty;
	}
	public void setGatepassReturnQty(int gatepassReturnQty) {
		this.gatepassReturnQty = gatepassReturnQty;
	}
	public int getClosingQty() {
		return closingQty;
	}
	public void setClosingQty(int closingQty) {
		this.closingQty = closingQty;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	@Override
	public String toString() {
		return "StockDetail [stockDetailId=" + stockDetailId + ", stockHeaderId=" + stockHeaderId + ", itemId=" + itemId
				+ ", opStockQty=" + opStockQty + ", approvedQty=" + approvedQty + ", issueQty=" + issueQty
				+ ", returnIssueQty=" + returnIssueQty + ", damageQty=" + damageQty + ", gatepassQty=" + gatepassQty
				+ ", gatepassReturnQty=" + gatepassReturnQty + ", closingQty=" + closingQty + ", delStatus=" + delStatus
				+ "]";
	}
	
	

}
