package com.ats.tril.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.Category;
import com.ats.tril.model.EnquiryDetail;
import com.ats.tril.model.EnquiryHeader;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.Item;
import com.ats.tril.model.Vendor;

@Controller
@Scope("session")
public class EnquiryController {
	
	List<EnquiryDetail> addItemInEnquiryDetail = new ArrayList<EnquiryDetail>();
	
	RestTemplate rest = new RestTemplate();
	
	@RequestMapping(value = "/addEnquiry", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("enquiry/addEnquiry");
		try {
			addItemInEnquiryDetail = new ArrayList<EnquiryDetail>();
			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));
			
			model.addObject("vendorList", vendorList);
			
			GetItem[] item = rest.getForObject(Constants.url + "/getAllItems",  GetItem[].class); 
			List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(item));
			model.addObject("itemList", itemList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/addItmeInEnquiryList", method = RequestMethod.GET)
	@ResponseBody
	public List<EnquiryDetail> addItmeInEnquiryList(HttpServletRequest request, HttpServletResponse response) {

		
		try {
			
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			int qty = Integer.parseInt(request.getParameter("qty"));
			String enqItemDate =  request.getParameter("enqItemDate"); 
			String itemRemark = request.getParameter("itemRemark");
			String editIndex = request.getParameter("editIndex");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("itemId", itemId);
			GetItem  item = rest.postForObject(Constants.url + "/getItemByItemId",map, GetItem .class);
			  
			if(editIndex.equalsIgnoreCase("") || editIndex.equalsIgnoreCase(null))
			  {
					EnquiryDetail enquiryDetail = new EnquiryDetail();
					enquiryDetail.setItemId(itemId);
					enquiryDetail.setEnqQty(qty);
					enquiryDetail.setEnqRemark(itemRemark);
					enquiryDetail.setEnqDetailDate(enqItemDate);
					enquiryDetail.setEnqItemDesc(item.getItemCode());
					addItemInEnquiryDetail.add(enquiryDetail);
			  }
			 else
			  {
				 	int index = Integer.parseInt(editIndex); 
				 	addItemInEnquiryDetail.get(index).setItemId(itemId);
				 	addItemInEnquiryDetail.get(index).setEnqQty(qty);
				 	addItemInEnquiryDetail.get(index).setEnqRemark(itemRemark);
				 	addItemInEnquiryDetail.get(index).setEnqDetailDate(enqItemDate);
				 	addItemInEnquiryDetail.get(index).setEnqItemDesc(item.getItemCode()); 
			  }
			
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return addItemInEnquiryDetail;
	}
	
	@RequestMapping(value = "/deleteItemFromEnquiry", method = RequestMethod.GET)
	@ResponseBody
	public List<EnquiryDetail> deleteItemFromEnquiry(HttpServletRequest request, HttpServletResponse response) {

		
		try {
			
			int index = Integer.parseInt(request.getParameter("index"));  
			 
			addItemInEnquiryDetail.remove(index);
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return addItemInEnquiryDetail;
	}
	
	@RequestMapping(value = "/editItemInAddEnquiry", method = RequestMethod.GET)
	@ResponseBody
	public EnquiryDetail editItemInAddEnquiry(HttpServletRequest request, HttpServletResponse response) {

		EnquiryDetail edit = new EnquiryDetail();
		
		try {
			
			int index = Integer.parseInt(request.getParameter("index"));  
			 
			edit = addItemInEnquiryDetail.get(index);
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return edit;
	}
	
	@RequestMapping(value = "/insertEnquiry", method = RequestMethod.POST) 
	public String insertEnquiry(HttpServletRequest request, HttpServletResponse response) {

		 
		
		try {
			
			List<EnquiryHeader> enquiryHeaderList = new ArrayList<EnquiryHeader>();
			
			String[] vendId = request.getParameterValues("vendId");  
			String enqRemark = request.getParameter("enqRemark"); 
			String enqDate = request.getParameter("enqDate"); 
			
			String Date = DateConvertor.convertToYMD(enqDate);
			 
			 for(int i = 0 ; i<vendId.length ; i++)
			 {
				 
				 EnquiryHeader enquiryHeader = new EnquiryHeader();
				 enquiryHeader.setVendId(Integer.parseInt(vendId[i]));
				 enquiryHeader.setEnqRemark(enqRemark);
				 enquiryHeader.setEnqDate(Date);
				 enquiryHeader.setDelStatus(1);
				 enquiryHeader.setEnquiryDetailList(addItemInEnquiryDetail);
				 enquiryHeaderList.add(enquiryHeader);
				  
			 }
			 
			ErrorMessage res = rest.postForObject(Constants.url + "/saveEnquiryHeaderAndDetail", enquiryHeaderList, ErrorMessage.class);
			System.out.println(res);
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addEnquiry";
	}

}
