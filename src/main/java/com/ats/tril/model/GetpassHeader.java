package com.ats.tril.model;

import java.util.Date;
import java.util.List;

public class GetpassHeader {
	private int gpId;
	private int gpNo;
	private String gpVendor;
	private int gpType;
	private Date gpReturnDate;
	private int gpStatus;
	private int isUsed;

	List<GetpassDetail> getpassDetail;

	public int getGpId() {
		return gpId;
	}

	public void setGpId(int gpId) {
		this.gpId = gpId;
	}

	public int getGpNo() {
		return gpNo;
	}

	public void setGpNo(int gpNo) {
		this.gpNo = gpNo;
	}

	public String getGpVendor() {
		return gpVendor;
	}

	public void setGpVendor(String gpVendor) {
		this.gpVendor = gpVendor;
	}

	public int getGpType() {
		return gpType;
	}

	public void setGpType(int gpType) {
		this.gpType = gpType;
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

	public List<GetpassDetail> getGetpassDetail() {
		return getpassDetail;
	}

	public void setGetpassDetail(List<GetpassDetail> getpassDetail) {
		this.getpassDetail = getpassDetail;
	}

	@Override
	public String toString() {
		return "GetpassHeader [gpId=" + gpId + ", gpNo=" + gpNo + ", gpVendor=" + gpVendor + ", gpType=" + gpType
				+ ", gpReturnDate=" + gpReturnDate + ", gpStatus=" + gpStatus + ", isUsed=" + isUsed
				+ ", getpassDetail=" + getpassDetail + "]";
	}

}
