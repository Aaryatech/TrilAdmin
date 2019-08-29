package com.ats.tril.model.yearlyreport;

public class TypeWiseAmount {

	private int typeId;
	private String typeName;
	private int catId;
	private float mrnAmount;
	private float issueAmount;
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public float getMrnAmount() {
		return mrnAmount;
	}
	public void setMrnAmount(float mrnAmount) {
		this.mrnAmount = mrnAmount;
	}
	public float getIssueAmount() {
		return issueAmount;
	}
	public void setIssueAmount(float issueAmount) {
		this.issueAmount = issueAmount;
	}
	@Override
	public String toString() {
		return "TypeWiseAmount [typeId=" + typeId + ", typeName=" + typeName + ", catId=" + catId + ", mrnAmount="
				+ mrnAmount + ", issueAmount=" + issueAmount + "]";
	}
	

}
