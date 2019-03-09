package com.ats.trl.model.report;

public class QuotReport {
	private int enqId;

	private String enqDate;

	private String enqRemark;

	private String headerRemark;

	private float enqQty;
	private String enqNo;

	private String itemDesc;

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

	@Override
	public String toString() {
		return "QuotReport [enqId=" + enqId + ", enqDate=" + enqDate + ", enqRemark=" + enqRemark + ", headerRemark="
				+ headerRemark + ", enqQty=" + enqQty + ", enqNo=" + enqNo + ", itemDesc=" + itemDesc + "]";
	}

}
