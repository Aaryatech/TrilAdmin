package com.ats.tril.model;

import java.util.Date;

public class GetpassDetail {
	private int gpDetailId;
	private int gpId;
	private int gpItemId;
	private float gpQty;
	private int gpNoDays;
	private Date gpReturnDate;
	private int gpStatus;
	private int isUsed;

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

	@Override
	public String toString() {
		return "GetpassDetail [gpDetailId=" + gpDetailId + ", gpId=" + gpId + ", gpItemId=" + gpItemId + ", gpQty="
				+ gpQty + ", gpNoDays=" + gpNoDays + ", gpReturnDate=" + gpReturnDate + ", gpStatus=" + gpStatus
				+ ", isUsed=" + isUsed + "]";
	}

}
