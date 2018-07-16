package com.ats.tril.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.DeliveryTerms;
import com.ats.tril.model.DispatchMode;
import com.ats.tril.model.EnquiryDetail;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.FinancialYears;
import com.ats.tril.model.GetEnquiryHeader;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.PaymentTerms;
import com.ats.tril.model.PoDetail;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.indent.GetIndentByStatus;
import com.ats.tril.model.indent.GetIntendDetail;
import com.ats.tril.model.indent.IndentTrans;
import com.ats.tril.model.po.PoHeader;

@Controller
@Scope("session")
public class PurchaseOrderController {
	
	
	RestTemplate rest = new RestTemplate();
	List<GetIntendDetail> intendDetailList = new ArrayList<>();
	PoHeader PoHeader = new PoHeader();

	@RequestMapping(value = "/addPurchaseOrder", method = RequestMethod.GET)
	public ModelAndView addPurchaseOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("purchaseOrder/addPurchaseOrder");
		try {
			
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
 
			model.addObject("date", sf.format(date));
			
			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes)); 
			model.addObject("vendorList", vendorList);
			
			DispatchMode[] dispatchMode = rest.getForObject(Constants.url + "/getAllDispatchModesByIsUsed",
					DispatchMode[].class);
			List<DispatchMode> dispatchModeList = new ArrayList<DispatchMode>(Arrays.asList(dispatchMode));

			model.addObject("dispatchModeList", dispatchModeList);
			
			PaymentTerms[] paymentTermsLists = rest.getForObject(Constants.url + "/getAllPaymentTermsByIsUsed",
					PaymentTerms[].class);
			model.addObject("paymentTermsList", paymentTermsLists);
			
			DeliveryTerms[] deliveryTerms = rest.getForObject(Constants.url + "/getAllDeliveryTermsByIsUsed",
					DeliveryTerms[].class);
			List<DeliveryTerms> deliveryTermsList = new ArrayList<DeliveryTerms>(Arrays.asList(deliveryTerms));

			model.addObject("deliveryTermsList", deliveryTermsList);
			
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("status", "0,1,2");
			GetIndentByStatus[] inted = rest.postForObject(Constants.url + "/getIntendsByStatus",map,  GetIndentByStatus[].class); 
			List<GetIndentByStatus> intedList = new ArrayList<GetIndentByStatus>(Arrays.asList(inted));
			model.addObject("intedList", intedList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/geIntendDetailByIndId", method = RequestMethod.GET)
	@ResponseBody
	public List<GetIntendDetail> geIntendDetailByIndId(HttpServletRequest request, HttpServletResponse response) {

		
		
		try {
			
			int indIdForGetList = Integer.parseInt(request.getParameter("indId"));
			 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("indId", indIdForGetList);
			GetIntendDetail[] indentTrans = rest.postForObject(Constants.url + "/getIntendsDetailByIntendId",map,  GetIntendDetail[].class); 
			intendDetailList = new ArrayList<GetIntendDetail>(Arrays.asList(indentTrans));
			 
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return intendDetailList;
	}
	
	@RequestMapping(value = "/submitList", method = RequestMethod.POST) 
	public ModelAndView submitEditEnquiry(HttpServletRequest request, HttpServletResponse response) {

		 
		ModelAndView model = new ModelAndView("purchaseOrder/addPurchaseOrder");
		
		try {
			PoHeader = new PoHeader();
			List<PoDetail> poDetailList = new ArrayList<>();
			
			List<GetIntendDetail> getIntendDetailforJsp = new ArrayList<>();
			
			int indId =Integer.parseInt(request.getParameter("indMId"));
			String[] checkbox=request.getParameterValues("select_to_approve");
			
			try {
			int vendIdTemp =Integer.parseInt(request.getParameter("vendIdTemp"));
			model.addObject("vendIdTemp", vendIdTemp);
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
			int quotationTemp =Integer.parseInt(request.getParameter("quotationTemp"));
			model.addObject("quotationTemp", quotationTemp);
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
			int poTypeTemp =Integer.parseInt(request.getParameter("poTypeTemp"));
			model.addObject("poTypeTemp", poTypeTemp);
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
			int payIdTemp =Integer.parseInt(request.getParameter("payIdTemp"));
			model.addObject("payIdTemp", payIdTemp);
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
			int deliveryIdTemp =Integer.parseInt(request.getParameter("deliveryIdTemp"));
			model.addObject("deliveryIdTemp", deliveryIdTemp);
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
			int dispatchModeTemp =Integer.parseInt(request.getParameter("dispatchModeTemp"));
			model.addObject("dispatchModeTemp", dispatchModeTemp);
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
			String quotationDateTemp=request.getParameter("quotationDateTemp");
			model.addObject("quotationDateTemp", quotationDateTemp);
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
			String poDateTemp=request.getParameter("poDateTemp");
			model.addObject("date", poDateTemp);
			}catch (Exception e) {
				// TODO: handle exception
			}
			 
			 float poBasicValue = 0; 
			 float discValue = 0;
			 
			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes)); 
			model.addObject("vendorList", vendorList);
			
			DispatchMode[] dispatchMode = rest.getForObject(Constants.url + "/getAllDispatchModesByIsUsed",
					DispatchMode[].class);
			List<DispatchMode> dispatchModeList = new ArrayList<DispatchMode>(Arrays.asList(dispatchMode));

			model.addObject("dispatchModeList", dispatchModeList);
			
			PaymentTerms[] paymentTermsLists = rest.getForObject(Constants.url + "/getAllPaymentTermsByIsUsed",
					PaymentTerms[].class);
			model.addObject("paymentTermsList", paymentTermsLists);
			
			DeliveryTerms[] deliveryTerms = rest.getForObject(Constants.url + "/getAllDeliveryTermsByIsUsed",
					DeliveryTerms[].class);
			List<DeliveryTerms> deliveryTermsList = new ArrayList<DeliveryTerms>(Arrays.asList(deliveryTerms));

			model.addObject("deliveryTermsList", deliveryTermsList);
			
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("status", "0,1,2");
			GetIndentByStatus[] inted = rest.postForObject(Constants.url + "/getIntendsByStatus",map,  GetIndentByStatus[].class); 
			List<GetIndentByStatus> intedList = new ArrayList<GetIndentByStatus>(Arrays.asList(inted));
			model.addObject("intedList", intedList);
			
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < intendDetailList.size(); i++) 
			{
				for(int j=0;j<checkbox.length;j++) 
				{
					System.out.println(checkbox[j] + intendDetailList.get(i).getIndDId());
					if(Integer.parseInt(checkbox[j])==intendDetailList.get(i).getIndDId()) 
					{
						PoDetail poDetail = new PoDetail();
						poDetail.setIndId(intendDetailList.get(i).getIndMId());
						poDetail.setSchDays(intendDetailList.get(i).getIndItemSchd());
						poDetail.setItemCode(intendDetailList.get(i).getItemCode());
						poDetail.setItemId(intendDetailList.get(i).getItemId());
						poDetail.setIndedQty(intendDetailList.get(i).getIndQty()); 
						poDetail.setItemUom(intendDetailList.get(i).getIndItemUom()); 
						poDetail.setItemQty(Integer.parseInt(request.getParameter("poQty"+intendDetailList.get(i).getIndDId())));
						poDetail.setPendingQty(Integer.parseInt(request.getParameter("poQty"+intendDetailList.get(i).getIndDId())));
						poDetail.setDiscPer(Integer.parseInt(request.getParameter("disc"+intendDetailList.get(i).getIndDId())));
						poDetail.setItemRate(Float.parseFloat(request.getParameter("rate"+intendDetailList.get(i).getIndDId())));
						poDetail.setSchRemark(request.getParameter("indRemark"+intendDetailList.get(i).getIndDId())); 
						poDetail.setSchDays(Integer.parseInt(request.getParameter("indItemSchd"+intendDetailList.get(i).getIndDId())));
						poDetail.setSchDate(intendDetailList.get(i).getIndItemSchddt());
						poDetail.setBalanceQty(Integer.parseInt(request.getParameter("balanceQty"+intendDetailList.get(i).getIndDId())));
						c.setTime(sdf.parse(poDetail.getSchDate()));
						c.add(Calendar.DAY_OF_MONTH, poDetail.getSchDays());  
						poDetail.setSchDate(sdf.format(c.getTime()));
						poDetail.setIndId(intendDetailList.get(i).getIndMId());
						poDetail.setIndMNo(intendDetailList.get(i).getIndMNo());
						poDetail.setBasicValue(poDetail.getItemQty()*poDetail.getItemRate());
						poDetail.setDiscValue((poDetail.getDiscPer()/100)*poDetail.getBasicValue());
						poDetail.setLandingCost(poDetail.getItemQty()*poDetail.getItemRate());
						poBasicValue=poBasicValue+poDetail.getBasicValue();
						discValue = discValue + poDetail.getDiscValue();
						poDetailList.add(poDetail);
						
						intendDetailList.get(i).setPoQty(Integer.parseInt(request.getParameter("poQty"+intendDetailList.get(i).getIndDId())));
						intendDetailList.get(i).setPendingQty(Integer.parseInt(request.getParameter("balanceQty"+intendDetailList.get(i).getIndDId())));
						intendDetailList.get(i).setDisc(Float.parseFloat(request.getParameter("disc"+intendDetailList.get(i).getIndDId())));
						intendDetailList.get(i).setRate(Float.parseFloat(request.getParameter("rate"+intendDetailList.get(i).getIndDId())));
						intendDetailList.get(i).setIndRemark(request.getParameter("indRemark"+intendDetailList.get(i).getIndDId())); 
						intendDetailList.get(i).setIndItemSchd(Integer.parseInt(request.getParameter("indItemSchd"+intendDetailList.get(i).getIndDId())));
						getIntendDetailforJsp.add(intendDetailList.get(i));
					}
						 
				}
			}
			System.out.println(poDetailList);
			
			PoHeader.setDiscValue(discValue);
			PoHeader.setPoBasicValue(poBasicValue);
			PoHeader.setPoDetailList(poDetailList);
			model.addObject("poDetailList", poDetailList);
			model.addObject("indId", indId);
			model.addObject("poHeader", PoHeader);
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/calculatePurchaseHeaderValues", method = RequestMethod.GET)
	@ResponseBody
	public PoHeader calculatePurchaseHeaderValues(HttpServletRequest request, HttpServletResponse response) {
 
		try {
			float total = 0;
			
			float packPer =Float.parseFloat(request.getParameter("packPer"));
			float packValue =Float.parseFloat(request.getParameter("packValue"));
			float insuPer =Float.parseFloat(request.getParameter("insuPer"));
			float insuValue =Float.parseFloat(request.getParameter("insuValue"));
			float freightPer =Float.parseFloat(request.getParameter("freightPer"));
			float freightValue =Float.parseFloat(request.getParameter("freightValue"));
			float otherPer =Float.parseFloat(request.getParameter("otherPer"));
			float otherValue =Float.parseFloat(request.getParameter("otherValue"));
			
			if(packPer!=0)
			{
				PoHeader.setPoPackPer(packPer);
				PoHeader.setPoPackVal((packPer/100)*PoHeader.getPoBasicValue());
			}
			else if(packValue!=0)
			{
				PoHeader.setPoPackVal(packValue);
			}
			else
			{
				PoHeader.setPoPackVal(0);
			}
			
			if(insuPer!=0)
			{
				PoHeader.setPoInsuPer(insuPer);
				PoHeader.setPoInsuVal((insuPer/100)*PoHeader.getPoBasicValue());
			}
			else if(insuValue!=0)
			{
				PoHeader.setPoInsuVal(insuValue);
			}
			else
			{
				PoHeader.setPoInsuVal(0);
			}
			
			if(freightPer!=0)
			{
				PoHeader.setPoFrtPer(freightPer);
				PoHeader.setPoFrtVal((freightPer/100)*PoHeader.getPoBasicValue());
			}
			else if(freightValue!=0)
			{
				PoHeader.setPoFrtVal(freightValue);
			}
			else
			{
				PoHeader.setPoFrtVal(0);
			}
			 
			if(otherPer!=0)
			{
				total = PoHeader.getPoBasicValue()+PoHeader.getPoPackVal()+PoHeader.getPoInsuVal()+PoHeader.getPoFrtVal();
				PoHeader.setOtherChargeAfter((otherPer/100)*total);
			}
			else if(otherValue!=0)
			{
				PoHeader.setOtherChargeAfter(otherValue);
			}
			else
			{
				PoHeader.setOtherChargeAfter(0);
			}
			
			
			
			for(int i = 0 ; i<PoHeader.getPoDetailList().size();i++)
			{
				float divFactor = PoHeader.getPoDetailList().get(i).getBasicValue()/PoHeader.getPoBasicValue()*100;
				PoHeader.getPoDetailList().get(i).setPackValue(divFactor*PoHeader.getPoPackVal()/100);
				PoHeader.getPoDetailList().get(i).setInsu(divFactor*PoHeader.getPoInsuVal()/100);
				PoHeader.getPoDetailList().get(i).setFreightValue(divFactor*PoHeader.getPoFrtVal()/100);
				PoHeader.getPoDetailList().get(i).setOtherChargesAfter(divFactor*PoHeader.getOtherChargeAfter()/100);
				PoHeader.getPoDetailList().get(i).setLandingCost(PoHeader.getPoDetailList().get(i).getBasicValue()-PoHeader.getPoDetailList().get(i).getDiscValue()+PoHeader.getPoDetailList().get(i).getPackValue()+
						PoHeader.getPoDetailList().get(i).getInsu()+PoHeader.getPoDetailList().get(i).getFreightValue()+PoHeader.getPoDetailList().get(i).getOtherChargesAfter());
			}
			
			System.out.println(PoHeader);
			
			
			 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return PoHeader;
	}
	
	@RequestMapping(value = "/submitPurchaseOrder", method = RequestMethod.POST) 
	public String submitPurchaseOrder(HttpServletRequest request, HttpServletResponse response) {

		 
		 
		
		try {
			 
		 
				int vendId =Integer.parseInt(request.getParameter("vendId")); 
				String quotation =request.getParameter("quotation"); 
				int poType =Integer.parseInt(request.getParameter("poType")); 
				int payId =Integer.parseInt(request.getParameter("payId")); 
				int deliveryId =Integer.parseInt(request.getParameter("deliveryId")); 
				int dispatchMode =Integer.parseInt(request.getParameter("dispatchMode")); 
				String quotationDate=request.getParameter("quotationDate"); 
				String poDate=request.getParameter("poDate");
				String poNo=request.getParameter("poNo");
				
				String packRemark=request.getParameter("packRemark");
				String insuRemark=request.getParameter("insuRemark");
				String freghtRemark=request.getParameter("freghtRemark");
				String otherRemark=request.getParameter("otherRemark");
				
				PoHeader.setVendId(vendId);
				PoHeader.setVendQuation(quotation);
				PoHeader.setPoType(poType);
				PoHeader.setPaymentTermId(payId);
				PoHeader.setDeliveryId(deliveryId);
				PoHeader.setDispatchId(dispatchMode);
				PoHeader.setVendQuationDate(DateConvertor.convertToYMD(quotationDate));
				PoHeader.setPoDate(DateConvertor.convertToYMD(poDate));
				PoHeader.setPoNo(poNo);
				PoHeader.setOtherChargeAfterRemark(otherRemark);
				PoHeader.setPoFrtRemark(freghtRemark);
				PoHeader.setPoInsuRemark(insuRemark);
				PoHeader.setPoPackRemark(packRemark);
				PoHeader.setIndId(PoHeader.getPoDetailList().get(0).getIndId());
				PoHeader.setDelStatus(1);
				System.out.println(PoHeader);
				PoHeader save = rest.postForObject(Constants.url + "/savePoHeaderAndDetail",PoHeader, PoHeader.class); 
				System.out.println(save);
				 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addPurchaseOrder";
	}

}
