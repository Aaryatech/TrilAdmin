package com.ats.tril.model;

import java.util.Date;



public class PoStatusReportDetail {
	
	
	private int mrnDetailId;
	
	private String mrnNo;
	private String mrnDate;
	
	private float chalanQty;
	private float recvQty;
	private float accepQty;
	private float itemRate;
	private float basicValue;
	public int getMrnDetailId() {
		return mrnDetailId;
	}
	public void setMrnDetailId(int mrnDetailId) {
		this.mrnDetailId = mrnDetailId;
	}
	public String getMrnNo() {
		return mrnNo;
	}
	public void setMrnNo(String mrnNo) {
		this.mrnNo = mrnNo;
	}
	
	public String getMrnDate() {
		return mrnDate;
	}
	public void setMrnDate(String mrnDate) {
		this.mrnDate = mrnDate;
	}
	public float getChalanQty() {
		return chalanQty;
	}
	public void setChalanQty(float chalanQty) {
		this.chalanQty = chalanQty;
	}
	public float getRecvQty() {
		return recvQty;
	}
	public void setRecvQty(float recvQty) {
		this.recvQty = recvQty;
	}
	public float getAccepQty() {
		return accepQty;
	}
	public void setAccepQty(float accepQty) {
		this.accepQty = accepQty;
	}
	public float getItemRate() {
		return itemRate;
	}
	public void setItemRate(float itemRate) {
		this.itemRate = itemRate;
	}
	public float getBasicValue() {
		return basicValue;
	}
	public void setBasicValue(float basicValue) {
		this.basicValue = basicValue;
	}
	@Override
	public String toString() {
		return "PoStatusReportDetail [mrnDetailId=" + mrnDetailId + ", mrnNo=" + mrnNo + ", mrnDate=" + mrnDate
				+ ", chalanQty=" + chalanQty + ", recvQty=" + recvQty + ", accepQty=" + accepQty + ", itemRate="
				+ itemRate + ", basicValue=" + basicValue + "]";
	}
	
	
	/*
	SELECT t_mrn_header.mrn_no,t_mrn_header.mrn_date,
t_mrn_detail.mrn_detail_id,
t_mrn_detail.chalan_qty,t_mrn_detail.mrn_qty as recv_qty,t_mrn_detail.approve_qty as accep_qty,
po_detail.item_rate,po_detail.basic_value
FROM t_mrn_header,t_mrn_detail,po_detail
WHERE t_mrn_header.mrn_id=t_mrn_detail.mrn_id AND t_mrn_detail.po_detail_id=po_detail.po_detail_id
AND po_detail.po_detail_id=1298
	 */

}
