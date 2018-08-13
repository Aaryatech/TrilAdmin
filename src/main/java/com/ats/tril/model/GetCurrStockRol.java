package com.ats.tril.model;


public class GetCurrStockRol {
	
	private int itemId;

	private String itemCode;
	
	private String itemName;
	
	private String itemUom;
		
	private int catId;
	
	private int itemMaxLevel;
	
	private int openingStock;

	private int approveQty;

	private int issueQty;
	
	private int returnIssueQty;
	
	private int damageQty;
	
	private int gatepassQty;
	
	private int gatepassReturnQty;
	
	private int rolLevel;

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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemUom() {
		return itemUom;
	}

	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public int getItemMaxLevel() {
		return itemMaxLevel;
	}

	public void setItemMaxLevel(int itemMaxLevel) {
		this.itemMaxLevel = itemMaxLevel;
	}

	public int getOpeningStock() {
		return openingStock;
	}

	public void setOpeningStock(int openingStock) {
		this.openingStock = openingStock;
	}

	public int getApproveQty() {
		return approveQty;
	}

	public void setApproveQty(int approveQty) {
		this.approveQty = approveQty;
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

	public int getRolLevel() {
		return rolLevel;
	}

	public void setRolLevel(int rolLevel) {
		this.rolLevel = rolLevel;
	}

	@Override
	public String toString() {
		return "GetCurrStockRol [itemId=" + itemId + ", itemCode=" + itemCode + ", itemName=" + itemName + ", itemUom="
				+ itemUom + ", catId=" + catId + ", itemMaxLevel=" + itemMaxLevel + ", openingStock=" + openingStock
				+ ", approveQty=" + approveQty + ", issueQty=" + issueQty + ", returnIssueQty=" + returnIssueQty
				+ ", damageQty=" + damageQty + ", gatepassQty=" + gatepassQty + ", gatepassReturnQty="
				+ gatepassReturnQty + ", rolLevel=" + rolLevel + "]";
	}

	
	
}