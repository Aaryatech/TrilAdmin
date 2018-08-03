package com.ats.tril.model;

public class GetEnquiryDetail {

	private int enqDetailId;
	private int enqId;
	private String enqDetailDate;
	private int indId;
	private int vendId;
	private String vendorName;
	private int itemId;
	private String itemCode;
	private int enqQty;
	private String enqUom;
	private String enqItemDesc;
	private String enqRemark;
	private String indNo;
	private int delStatus;

	public int getEnqDetailId() {
		return enqDetailId;
	}

	public void setEnqDetailId(int enqDetailId) {
		this.enqDetailId = enqDetailId;
	}

	public int getEnqId() {
		return enqId;
	}

	public void setEnqId(int enqId) {
		this.enqId = enqId;
	}

	public String getEnqDetailDate() {
		return enqDetailDate;
	}

	public void setEnqDetailDate(String enqDetailDate) {
		this.enqDetailDate = enqDetailDate;
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

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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

	public int getEnqQty() {
		return enqQty;
	}

	public void setEnqQty(int enqQty) {
		this.enqQty = enqQty;
	}

	public String getEnqUom() {
		return enqUom;
	}

	public void setEnqUom(String enqUom) {
		this.enqUom = enqUom;
	}

	public String getEnqItemDesc() {
		return enqItemDesc;
	}

	public void setEnqItemDesc(String enqItemDesc) {
		this.enqItemDesc = enqItemDesc;
	}

	public String getEnqRemark() {
		return enqRemark;
	}

	public void setEnqRemark(String enqRemark) {
		this.enqRemark = enqRemark;
	}

	public String getIndNo() {
		return indNo;
	}

	public void setIndNo(String indNo) {
		this.indNo = indNo;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "GetEnquiryDetail [enqDetailId=" + enqDetailId + ", enqId=" + enqId + ", enqDetailDate=" + enqDetailDate
				+ ", indId=" + indId + ", vendId=" + vendId + ", vendorName=" + vendorName + ", itemId=" + itemId
				+ ", itemCode=" + itemCode + ", enqQty=" + enqQty + ", enqUom=" + enqUom + ", enqItemDesc="
				+ enqItemDesc + ", enqRemark=" + enqRemark + ", indNo=" + indNo + ", delStatus=" + delStatus + "]";
	}

}
