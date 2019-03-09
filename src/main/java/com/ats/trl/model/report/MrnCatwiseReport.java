package com.ats.trl.model.report;

import java.util.Date;

public class MrnCatwiseReport {

	private int mrnDetailId;

	private String mrnNo;

	private String mrnDate;

	private float mrnQty;

	private float remainingQty;

	private String itemCode;
	private String itemDesc;
	private String catDesc;
	private float itemRate;
	private String days;


	public int getMrnDetailId() {
		return mrnDetailId;
	}

	public void setMrnDetailId(int mrnDetailId) {
		this.mrnDetailId = mrnDetailId;
	}

	public String getMrnNo() {
		return mrnNo;
	}

	public void setMrnNo(String mrnNo) {
		this.mrnNo = mrnNo;
	}

	public String getMrnDate() {
		return mrnDate;
	}

	public void setMrnDate(String mrnDate) {
		this.mrnDate = mrnDate;
	}

	public float getMrnQty() {
		return mrnQty;
	}

	public void setMrnQty(float mrnQty) {
		this.mrnQty = mrnQty;
	}

	public float getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(float remainingQty) {
		this.remainingQty = remainingQty;
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

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	public float getItemRate() {
		return itemRate;
	}

	public void setItemRate(float itemRate) {
		this.itemRate = itemRate;
	}
	
	

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "MrnCatwiseReport [mrnDetailId=" + mrnDetailId + ", mrnNo=" + mrnNo + ", mrnDate=" + mrnDate
				+ ", mrnQty=" + mrnQty + ", remainingQty=" + remainingQty + ", itemCode=" + itemCode + ", itemDesc="
				+ itemDesc + ", catDesc=" + catDesc + ", itemRate=" + itemRate + ", days=" + days + "]";
	}

}
