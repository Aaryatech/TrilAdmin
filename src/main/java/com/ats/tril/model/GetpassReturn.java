package com.ats.tril.model;

import java.util.Date;
import java.util.List;

public class GetpassReturn {

	private int returnId;
	private int returnNo;

	private int gpId;
	private int gpNo;
	private int vendorId;

	private String gpReturnDate;
	private String remark;
	private String remark1;
	private int status;
	private int isUsed;

	List<GetpassReturnDetail> getpassReturnDetailList;

	public int getReturnId() {
		return returnId;
	}

	public void setReturnId(int returnId) {
		this.returnId = returnId;
	}

	public int getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(int returnNo) {
		this.returnNo = returnNo;
	}

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

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getGpReturnDate() {
		return gpReturnDate;
	}

	public void setGpReturnDate(String gpReturnDate) {
		this.gpReturnDate = gpReturnDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public List<GetpassReturnDetail> getGetpassReturnDetailList() {
		return getpassReturnDetailList;
	}

	public void setGetpassReturnDetailList(List<GetpassReturnDetail> getpassReturnDetailList) {
		this.getpassReturnDetailList = getpassReturnDetailList;
	}

	@Override
	public String toString() {
		return "GetpassReturn [returnId=" + returnId + ", returnNo=" + returnNo + ", gpId=" + gpId + ", gpNo=" + gpNo
				+ ", vendorId=" + vendorId + ", gpReturnDate=" + gpReturnDate + ", remark=" + remark + ", remark1="
				+ remark1 + ", status=" + status + ", isUsed=" + isUsed + ", getpassReturnDetailList="
				+ getpassReturnDetailList + "]";
	}

}
