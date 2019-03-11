package com.ats.trl.model.report;

public class IndentRepItemwise {
	private int indDId;

	private int itemId;

	private int indMId;

	private String indMNo;

	private String indMDate;

	private int indMType;

	private int indDStatus;

	private String catDesc;

	private String itemCode;

	private float indQty;

	private String indItemDesc;

	private String excessDays;

	private String indItemSchddt;

	private String indReamrk;

	public int getIndDId() {
		return indDId;
	}

	public void setIndDId(int indDId) {
		this.indDId = indDId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getIndMId() {
		return indMId;
	}

	public void setIndMId(int indMId) {
		this.indMId = indMId;
	}

	public String getIndMNo() {
		return indMNo;
	}

	public void setIndMNo(String indMNo) {
		this.indMNo = indMNo;
	}

	public String getIndMDate() {
		return indMDate;
	}

	public void setIndMDate(String indMDate) {
		this.indMDate = indMDate;
	}

	public int getIndMType() {
		return indMType;
	}

	public void setIndMType(int indMType) {
		this.indMType = indMType;
	}

	public int getIndDStatus() {
		return indDStatus;
	}

	public void setIndDStatus(int indDStatus) {
		this.indDStatus = indDStatus;
	}

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public float getIndQty() {
		return indQty;
	}

	public void setIndQty(float indQty) {
		this.indQty = indQty;
	}

	public String getIndItemDesc() {
		return indItemDesc;
	}

	public void setIndItemDesc(String indItemDesc) {
		this.indItemDesc = indItemDesc;
	}

	public String getExcessDays() {
		return excessDays;
	}

	public void setExcessDays(String excessDays) {
		this.excessDays = excessDays;
	}

	public String getIndItemSchddt() {
		return indItemSchddt;
	}

	public void setIndItemSchddt(String indItemSchddt) {
		this.indItemSchddt = indItemSchddt;
	}
	
	

	public String getIndReamrk() {
		return indReamrk;
	}

	public void setIndReamrk(String indReamrk) {
		this.indReamrk = indReamrk;
	}

	@Override
	public String toString() {
		return "IndentRepItemwise [indDId=" + indDId + ", itemId=" + itemId + ", indMId=" + indMId + ", indMNo="
				+ indMNo + ", indMDate=" + indMDate + ", indMType=" + indMType + ", indDStatus=" + indDStatus
				+ ", catDesc=" + catDesc + ", itemCode=" + itemCode + ", indQty=" + indQty + ", indItemDesc="
				+ indItemDesc + ", excessDays=" + excessDays + ", indItemSchddt=" + indItemSchddt + ", indReamrk="
				+ indReamrk + "]";
	}
}
