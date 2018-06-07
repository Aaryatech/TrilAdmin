package com.ats.tril.model;

public class TaxForm {
	private int taxId;

	private float taxPer;

	private String taxDesc;

	private int isUsed;

	private int createdIn;

	private int deletedIn;

	public int getTaxId() {
		return taxId;
	}

	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}

	public float getTaxPer() {
		return taxPer;
	}

	public void setTaxPer(float taxPer) {
		this.taxPer = taxPer;
	}

	public String getTaxDesc() {
		return taxDesc;
	}

	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public int getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(int createdIn) {
		this.createdIn = createdIn;
	}

	public int getDeletedIn() {
		return deletedIn;
	}

	public void setDeletedIn(int deletedIn) {
		this.deletedIn = deletedIn;
	}

	@Override
	public String toString() {
		return "TaxForm [taxId=" + taxId + ", taxPer=" + taxPer + ", taxDesc=" + taxDesc + ", isUsed=" + isUsed
				+ ", createdIn=" + createdIn + ", deletedIn=" + deletedIn + "]";
	}

}
