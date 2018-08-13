package com.ats.tril.model;


public class GetPODetail {
	
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
	
	private String freightValue;
	
	private float otherChargesAfter;
	
	private float landingCost;

	private String itemCode;
	private String itemName;
	private String uom;
	private String poNo;
	
	private int receivedQty;
	
	private int chalanQty;
	
	
	
	
	public int getChalanQty() {
		return chalanQty;
	}
	public void setChalanQty(int chalanQty) {
		this.chalanQty = chalanQty;
	}
	public int getReceivedQty() {
		return receivedQty;
	}
	public void setReceivedQty(int receivedQty) {
		this.receivedQty = receivedQty;
	}
	public int getPoDetailId() {
		return poDetailId;
	}
	public int getPoId() {
		return poId;
	}
	public int getIndId() {
		return indId;
	}
	public int getVendId() {
		return vendId;
	}
	public int getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getItemUom() {
		return itemUom;
	}
	public int getItemQty() {
		return itemQty;
	}
	public float getItemRate() {
		return itemRate;
	}
	public int getMrnQty() {
		return mrnQty;
	}
	public int getPendingQty() {
		return pendingQty;
	}
	public int getIndedQty() {
		return indedQty;
	}
	public float getDiscPer() {
		return discPer;
	}
	public float getDiscValue() {
		return discValue;
	}
	public int getSchDays() {
		return schDays;
	}
	public String getSchDate() {
		return schDate;
	}
	public String getSchRemark() {
		return schRemark;
	}
	public int getStatus() {
		return status;
	}
	public float getBasicValue() {
		return basicValue;
	}
	public float getPackValue() {
		return packValue;
	}
	public float getInsu() {
		return insu;
	}
	public float getOtherChargesBefor() {
		return otherChargesBefor;
	}
	public float getTaxValue() {
		return taxValue;
	}
	public String getFreightValue() {
		return freightValue;
	}
	public float getOtherChargesAfter() {
		return otherChargesAfter;
	}
	public float getLandingCost() {
		return landingCost;
	}
	public String getItemCode() {
		return itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public String getUom() {
		return uom;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoDetailId(int poDetailId) {
		this.poDetailId = poDetailId;
	}
	public void setPoId(int poId) {
		this.poId = poId;
	}
	public void setIndId(int indId) {
		this.indId = indId;
	}
	public void setVendId(int vendId) {
		this.vendId = vendId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	public void setItemRate(float itemRate) {
		this.itemRate = itemRate;
	}
	public void setMrnQty(int mrnQty) {
		this.mrnQty = mrnQty;
	}
	public void setPendingQty(int pendingQty) {
		this.pendingQty = pendingQty;
	}
	public void setIndedQty(int indedQty) {
		this.indedQty = indedQty;
	}
	public void setDiscPer(float discPer) {
		this.discPer = discPer;
	}
	public void setDiscValue(float discValue) {
		this.discValue = discValue;
	}
	public void setSchDays(int schDays) {
		this.schDays = schDays;
	}
	public void setSchDate(String schDate) {
		this.schDate = schDate;
	}
	public void setSchRemark(String schRemark) {
		this.schRemark = schRemark;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setBasicValue(float basicValue) {
		this.basicValue = basicValue;
	}
	public void setPackValue(float packValue) {
		this.packValue = packValue;
	}
	public void setInsu(float insu) {
		this.insu = insu;
	}
	public void setOtherChargesBefor(float otherChargesBefor) {
		this.otherChargesBefor = otherChargesBefor;
	}
	public void setTaxValue(float taxValue) {
		this.taxValue = taxValue;
	}
	public void setFreightValue(String freightValue) {
		this.freightValue = freightValue;
	}
	public void setOtherChargesAfter(float otherChargesAfter) {
		this.otherChargesAfter = otherChargesAfter;
	}
	public void setLandingCost(float landingCost) {
		this.landingCost = landingCost;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	@Override
	public String toString() {
		return "GetPODetail [poDetailId=" + poDetailId + ", poId=" + poId + ", indId=" + indId + ", vendId=" + vendId
				+ ", itemId=" + itemId + ", itemDesc=" + itemDesc + ", itemUom=" + itemUom + ", itemQty=" + itemQty
				+ ", itemRate=" + itemRate + ", mrnQty=" + mrnQty + ", pendingQty=" + pendingQty + ", indedQty="
				+ indedQty + ", discPer=" + discPer + ", discValue=" + discValue + ", schDays=" + schDays + ", schDate="
				+ schDate + ", schRemark=" + schRemark + ", status=" + status + ", basicValue=" + basicValue
				+ ", packValue=" + packValue + ", insu=" + insu + ", otherChargesBefor=" + otherChargesBefor
				+ ", taxValue=" + taxValue + ", freightValue=" + freightValue + ", otherChargesAfter="
				+ otherChargesAfter + ", landingCost=" + landingCost + ", itemCode=" + itemCode + ", itemName="
				+ itemName + ", uom=" + uom + ", poNo=" + poNo + ", receivedQty=" + receivedQty + ", chalanQty="
				+ chalanQty + "]";
	}
	
	
}
