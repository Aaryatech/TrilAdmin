package com.ats.tril.model;
 

public class GetPoDetailList {
	 
	private int poDetailId; 
	private int poId; 
	private int indId; 
	private int vendId; 
	private int itemId; 
	private String itemDesc;  
	private String itemUom; 
	private int itemQty; 
	private float itemRate; 
	private int mrnQty; 
	private int pendingQty; 
	private int indedQty; 
	private float discPer; 
	private float discValue; 
	private int schDays; 
	private String schDate; 
	private String schRemark; 
	private int status; 
	private float basicValue; 
	private float packValue; 
	private float insu; 
	private float otherChargesBefor; 
	private float taxValue; 
	private float freightValue; 
	private float otherChargesAfter; 
	private float landingCost; 
	private String itemCode;
	private int balanceQty;
	public int getPoDetailId() {
		return poDetailId;
	}
	public void setPoDetailId(int poDetailId) {
		this.poDetailId = poDetailId;
	}
	public int getPoId() {
		return poId;
	}
	public void setPoId(int poId) {
		this.poId = poId;
	}
	public int getIndId() {
		return indId;
	}
	public void setIndId(int indId) {
		this.indId = indId;
	}
	public int getVendId() {
		return vendId;
	}
	public void setVendId(int vendId) {
		this.vendId = vendId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getItemUom() {
		return itemUom;
	}
	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
	}
	public int getItemQty() {
		return itemQty;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	public float getItemRate() {
		return itemRate;
	}
	public void setItemRate(float itemRate) {
		this.itemRate = itemRate;
	}
	public int getMrnQty() {
		return mrnQty;
	}
	public void setMrnQty(int mrnQty) {
		this.mrnQty = mrnQty;
	}
	public int getPendingQty() {
		return pendingQty;
	}
	public void setPendingQty(int pendingQty) {
		this.pendingQty = pendingQty;
	}
	public int getIndedQty() {
		return indedQty;
	}
	public void setIndedQty(int indedQty) {
		this.indedQty = indedQty;
	}
	public float getDiscPer() {
		return discPer;
	}
	public void setDiscPer(float discPer) {
		this.discPer = discPer;
	}
	public float getDiscValue() {
		return discValue;
	}
	public void setDiscValue(float discValue) {
		this.discValue = discValue;
	}
	public int getSchDays() {
		return schDays;
	}
	public void setSchDays(int schDays) {
		this.schDays = schDays;
	}
	public String getSchDate() {
		return schDate;
	}
	public void setSchDate(String schDate) {
		this.schDate = schDate;
	}
	public String getSchRemark() {
		return schRemark;
	}
	public void setSchRemark(String schRemark) {
		this.schRemark = schRemark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getBasicValue() {
		return basicValue;
	}
	public void setBasicValue(float basicValue) {
		this.basicValue = basicValue;
	}
	public float getPackValue() {
		return packValue;
	}
	public void setPackValue(float packValue) {
		this.packValue = packValue;
	}
	public float getInsu() {
		return insu;
	}
	public void setInsu(float insu) {
		this.insu = insu;
	}
	public float getOtherChargesBefor() {
		return otherChargesBefor;
	}
	public void setOtherChargesBefor(float otherChargesBefor) {
		this.otherChargesBefor = otherChargesBefor;
	}
	public float getTaxValue() {
		return taxValue;
	}
	public void setTaxValue(float taxValue) {
		this.taxValue = taxValue;
	}
	public float getFreightValue() {
		return freightValue;
	}
	public void setFreightValue(float freightValue) {
		this.freightValue = freightValue;
	}
	public float getOtherChargesAfter() {
		return otherChargesAfter;
	}
	public void setOtherChargesAfter(float otherChargesAfter) {
		this.otherChargesAfter = otherChargesAfter;
	}
	public float getLandingCost() {
		return landingCost;
	}
	public void setLandingCost(float landingCost) {
		this.landingCost = landingCost;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public int getBalanceQty() {
		return balanceQty;
	}
	public void setBalanceQty(int balanceQty) {
		this.balanceQty = balanceQty;
	}
	@Override
	public String toString() {
		return "GetPoDetailList [poDetailId=" + poDetailId + ", poId=" + poId + ", indId=" + indId + ", vendId="
				+ vendId + ", itemId=" + itemId + ", itemDesc=" + itemDesc + ", itemUom=" + itemUom + ", itemQty="
				+ itemQty + ", itemRate=" + itemRate + ", mrnQty=" + mrnQty + ", pendingQty=" + pendingQty
				+ ", indedQty=" + indedQty + ", discPer=" + discPer + ", discValue=" + discValue + ", schDays="
				+ schDays + ", schDate=" + schDate + ", schRemark=" + schRemark + ", status=" + status + ", basicValue="
				+ basicValue + ", packValue=" + packValue + ", insu=" + insu + ", otherChargesBefor="
				+ otherChargesBefor + ", taxValue=" + taxValue + ", freightValue=" + freightValue
				+ ", otherChargesAfter=" + otherChargesAfter + ", landingCost=" + landingCost + ", itemCode=" + itemCode
				+ ", balanceQty=" + balanceQty + "]";
	}
	
	

}
