package com.ats.tril.model;

import java.util.Date;


public class PoStatusReportHeader {

	private int poDetailId;
	
	private int itemId;
	private String itemCode;
	private String itemDesc;
	private float poQty;
	private float balQty;
	private float recvQty;
	private float itemRate;
	
	private String poNo;
	private String poDate;
	
	private String indentNo;
	
	private String vendorCode;
	private String vendorName;
	public int getPoDetailId() {
		return poDetailId;
	}
	public void setPoDetailId(int poDetailId) {
		this.poDetailId = poDetailId;
	}
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
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public float getPoQty() {
		return poQty;
	}
	public void setPoQty(float poQty) {
		this.poQty = poQty;
	}
	public float getBalQty() {
		return balQty;
	}
	public void setBalQty(float balQty) {
		this.balQty = balQty;
	}
	public float getRecvQty() {
		return recvQty;
	}
	public void setRecvQty(float recvQty) {
		this.recvQty = recvQty;
	}
	public float getItemRate() {
		return itemRate;
	}
	public void setItemRate(float itemRate) {
		this.itemRate = itemRate;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	
	public String getIndentNo() {
		return indentNo;
	}
	public void setIndentNo(String indentNo) {
		this.indentNo = indentNo;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	@Override
	public String toString() {
		return "PoStatusReportHeader [poDetailId=" + poDetailId + ", itemId=" + itemId + ", itemCode=" + itemCode
				+ ", itemDesc=" + itemDesc + ", poQty=" + poQty + ", balQty=" + balQty + ", recvQty=" + recvQty
				+ ", itemRate=" + itemRate + ", poNo=" + poNo + ", poDate=" + poDate + ", indentNo=" + indentNo
				+ ", vendorCode=" + vendorCode + ", vendorName=" + vendorName + "]";
	}
	
	
}
