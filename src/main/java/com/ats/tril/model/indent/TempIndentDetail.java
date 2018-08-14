package com.ats.tril.model.indent;

import java.util.Date;

public class TempIndentDetail {
	
	int itemId;
	String itemName;
	int qty;
	String uom;
	int curStock;
	int schDays;
	String date;
	
	String itemCode;
	
	String remark;
	
	int isDuplicate; //1 for yes 0 for no
	
	
	public int getIsDuplicate() {
		return isDuplicate;
	}

	public void setIsDuplicate(int isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getItemId() {
		return itemId;
	}
	
	public int getQty() {
		return qty;
	}
	public String getUom() {
		return uom;
	}
	public int getCurStock() {
		return curStock;
	}
	public int getSchDays() {
		return schDays;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setCurStock(int curStock) {
		this.curStock = curStock;
	}
	public void setSchDays(int schDays) {
		this.schDays = schDays;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Override
	public String toString() {
		return "TempIndentDetail [itemId=" + itemId + ", itemName=" + itemName + ", qty=" + qty + ", uom=" + uom
				+ ", curStock=" + curStock + ", schDays=" + schDays + ", date=" + date + ", itemCode=" + itemCode
				+ ", remark=" + remark + ", isDuplicate=" + isDuplicate + "]";
	}

	
	
}
