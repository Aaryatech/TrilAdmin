package com.ats.trl.model.report;

public class QuotReport {
	private int enqDetId;
	private int enqId;

	private String enqDate;

	private String enqRemark;

	private String headerRemark;

	private float enqQty;
	private String enqNo;

	private String itemDesc;
	private String itemCode;

	public int getEnqDetId() {
		return enqDetId;
	}

	public void setEnqDetId(int enqDetId) {
		this.enqDetId = enqDetId;
	}

	public int getEnqId() {
		return enqId;
	}

	public void setEnqId(int enqId) {
		this.enqId = enqId;
	}

	public String getEnqDate() {
		return enqDate;
	}

	public void setEnqDate(String enqDate) {
		this.enqDate = enqDate;
	}

	public String getEnqRemark() {
		return enqRemark;
	}

	public void setEnqRemark(String enqRemark) {
		this.enqRemark = enqRemark;
	}

	public String getHeaderRemark() {
		return headerRemark;
	}

	public void setHeaderRemark(String headerRemark) {
		this.headerRemark = headerRemark;
	}

	public float getEnqQty() {
		return enqQty;
	}

	public void setEnqQty(float enqQty) {
		this.enqQty = enqQty;
	}

	public String getEnqNo() {
		return enqNo;
	}

	public void setEnqNo(String enqNo) {
		this.enqNo = enqNo;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Override
	public String toString() {
		return "QuotReport [enqDetId=" + enqDetId + ", enqId=" + enqId + ", enqDate=" + enqDate + ", enqRemark="
				+ enqRemark + ", headerRemark=" + headerRemark + ", enqQty=" + enqQty + ", enqNo=" + enqNo
				+ ", itemDesc=" + itemDesc + ", itemCode=" + itemCode + "]";
	}

}
