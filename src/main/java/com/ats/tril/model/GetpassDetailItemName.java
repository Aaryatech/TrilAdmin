package com.ats.tril.model;

import java.util.Date;

public class GetpassDetailItemName {
	private int gpDetailId;
	private int gpId;
	private int gpItemId;
	private float gpQty;
	private int gpNoDays;
	private Date gpReturnDate;
	private int gpStatus;
	private int isUsed;
	private float gpRemQty;
	private float gpRetQty;
	private String itemCode;

	public int getGpDetailId() {
		return gpDetailId;
	}

	public void setGpDetailId(int gpDetailId) {
		this.gpDetailId = gpDetailId;
	}

	public int getGpId() {
		return gpId;
	}

	public void setGpId(int gpId) {
		this.gpId = gpId;
	}

	public int getGpItemId() {
		return gpItemId;
	}

	public void setGpItemId(int gpItemId) {
		this.gpItemId = gpItemId;
	}

	public float getGpQty() {
		return gpQty;
	}

	public void setGpQty(float gpQty) {
		this.gpQty = gpQty;
	}

	public int getGpNoDays() {
		return gpNoDays;
	}

	public void setGpNoDays(int gpNoDays) {
		this.gpNoDays = gpNoDays;
	}

	public Date getGpReturnDate() {
		return gpReturnDate;
	}

	public void setGpReturnDate(Date gpReturnDate) {
		this.gpReturnDate = gpReturnDate;
	}

	public int getGpStatus() {
		return gpStatus;
	}

	public void setGpStatus(int gpStatus) {
		this.gpStatus = gpStatus;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public float getGpRemQty() {
		return gpRemQty;
	}

	public void setGpRemQty(float gpRemQty) {
		this.gpRemQty = gpRemQty;
	}

	public float getGpRetQty() {
		return gpRetQty;
	}

	public void setGpRetQty(float gpRetQty) {
		this.gpRetQty = gpRetQty;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Override
	public String toString() {
		return "GetpassDetailItemName [gpDetailId=" + gpDetailId + ", gpId=" + gpId + ", gpItemId=" + gpItemId
				+ ", gpQty=" + gpQty + ", gpNoDays=" + gpNoDays + ", gpReturnDate=" + gpReturnDate + ", gpStatus="
				+ gpStatus + ", isUsed=" + isUsed + ", gpRemQty=" + gpRemQty + ", gpRetQty=" + gpRetQty + ", itemCode="
				+ itemCode + "]";
	}

}
