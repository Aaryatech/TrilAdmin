package com.ats.tril.model;

public class GetpassDetail {
	private int gpDetailId;
	private int gpId;

	private int gpItemId;
	private float gpQty;
	private int gpNoDays;
	private String gpReturnDate;
	private int gpStatus;
	private int isUsed;
	private String itemCode;
	private int catId;
	private int groupId;

	private float gpRemQty;
	private float gpRetQty;

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

	public String getGpReturnDate() {
		return gpReturnDate;
	}

	public void setGpReturnDate(String gpReturnDate) {
		this.gpReturnDate = gpReturnDate;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
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

	@Override
	public String toString() {
		return "GetpassDetail [gpDetailId=" + gpDetailId + ", gpId=" + gpId + ", gpItemId=" + gpItemId + ", gpQty="
				+ gpQty + ", gpNoDays=" + gpNoDays + ", gpReturnDate=" + gpReturnDate + ", gpStatus=" + gpStatus
				+ ", isUsed=" + isUsed + ", itemCode=" + itemCode + ", catId=" + catId + ", groupId=" + groupId
				+ ", gpRemQty=" + gpRemQty + ", gpRetQty=" + gpRetQty + "]";
	}

}
