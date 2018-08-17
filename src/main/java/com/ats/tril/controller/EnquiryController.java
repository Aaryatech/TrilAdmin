package com.ats.tril.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.ats.tril.model.GetEnquiryDetail;
import com.ats.tril.model.GetEnquiryHeader;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.Item;
import com.ats.tril.model.Vendor;

@Controller
@Scope("session")
public class EnquiryController {
	
	List<EnquiryDetail> addItemInEnquiryDetail = new ArrayList<EnquiryDetail>();
	List<GetEnquiryDetail> addItemInEditEnquiryDetail = new ArrayList<GetEnquiryDetail>();
	GetEnquiryHeader editEnquiry = new GetEnquiryHeader();
	
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
			float qty = Float.parseFloat(request.getParameter("qty"));
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
					enquiryDetail.setItemCode(item.getItemCode()+"-"+item.getItemDesc());
					enquiryDetail.setDelStatus(1);
					addItemInEnquiryDetail.add(enquiryDetail);
			  }
			 else
			  {
				 	int index = Integer.parseInt(editIndex); 
				 	addItemInEnquiryDetail.get(index).setItemId(itemId);
				 	addItemInEnquiryDetail.get(index).setEnqQty(qty);
				 	addItemInEnquiryDetail.get(index).setEnqRemark(itemRemark);
				 	addItemInEnquiryDetail.get(index).setEnqDetailDate(enqItemDate);
				 	addItemInEnquiryDetail.get(index).setItemCode(item.getItemCode()+"-"+item.getItemDesc()); 
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

		return "redirect:/listOfEnquiry";
	}
	
	
	@RequestMapping(value = "/listOfEnquiry", method = RequestMethod.GET)
	public ModelAndView listOfEnquiry(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("enquiry/listOfEnquiry");
		try {
			
			Date date = new Date();
			SimpleDateFormat  sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat  display = new SimpleDateFormat("dd-MM-yyyy");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", sf.format(date));
			map.add("toDate", sf.format(date));
			 
			GetEnquiryHeader[] list = rest.postForObject(Constants.url + "/getEnquiryHeaderListBetweenDate",map, GetEnquiryHeader[].class);
			List<GetEnquiryHeader> enquiryList = new ArrayList<GetEnquiryHeader>(Arrays.asList(list));
			
			model.addObject("enquiryList", enquiryList);
			model.addObject("date", display.format(date));
			
			 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/getEnquiryListByDate", method = RequestMethod.GET)
	@ResponseBody
	public List<GetEnquiryHeader> getEnquiryListByDate(HttpServletRequest request, HttpServletResponse response) {

		List<GetEnquiryHeader> enquiryList = new ArrayList<GetEnquiryHeader>();
		try {
			
			 String fromDate = request.getParameter("fromDate");
			 String toDate = request.getParameter("toDate");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate",DateConvertor.convertToYMD(toDate));
			 
			GetEnquiryHeader[] list = rest.postForObject(Constants.url + "/getEnquiryHeaderListBetweenDate",map, GetEnquiryHeader[].class);
			 enquiryList = new ArrayList<GetEnquiryHeader>(Arrays.asList(list));
			 
			 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return enquiryList;
	}
	
	@RequestMapping(value = "/deleteEnquiry/{enqId}", method = RequestMethod.GET)
	public String deleteEnquiry(@PathVariable int enqId, HttpServletRequest request, HttpServletResponse response) {
 
		try {
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("enqId",enqId);
			
			ErrorMessage errorMessage = rest.postForObject(Constants.url + "/deleteEnquiryHeader",map,  ErrorMessage.class); 
			System.out.println(errorMessage);
			 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfEnquiry";
	}
	
	@RequestMapping(value = "/editEnquiry/{enqId}", method = RequestMethod.GET)
	public ModelAndView editEnquiry(@PathVariable int enqId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("enquiry/editEnquiry");
		try {
			addItemInEditEnquiryDetail = new ArrayList<GetEnquiryDetail>();
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("enqId",enqId);
			
			 editEnquiry = rest.postForObject(Constants.url + "/getEnquiryHeaderAndDetail",map, GetEnquiryHeader.class);
			addItemInEditEnquiryDetail = editEnquiry.getEnquiryDetailList();
			  
			GetItem[] item = rest.getForObject(Constants.url + "/getAllItems",  GetItem[].class); 
			List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(item));
			model.addObject("itemList", itemList);
			
			model.addObject("editEnquiry", editEnquiry);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/deleteItemFromEditEnquiry", method = RequestMethod.GET)
	@ResponseBody
	public List<GetEnquiryDetail> deleteItemFromEditEnquiry(HttpServletRequest request, HttpServletResponse response) {

		
		try {
			
			int index = Integer.parseInt(request.getParameter("index"));  
			 
			if(addItemInEditEnquiryDetail.get(index).getEnqDetailId()!=0)
				addItemInEditEnquiryDetail.get(index).setDelStatus(0);
			else
				addItemInEditEnquiryDetail.remove(index);
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return addItemInEditEnquiryDetail;
	}
	
	@RequestMapping(value = "/addItmeInEditEnquiryList", method = RequestMethod.GET)
	@ResponseBody
	public List<GetEnquiryDetail> addItmeInEditEnquiryList(HttpServletRequest request, HttpServletResponse response) {

		
		try {
			
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			float qty = Float.parseFloat(request.getParameter("qty")); 
			String enqItemDate =  request.getParameter("enqItemDate"); 
			String itemRemark = request.getParameter("itemRemark");
			String editIndex = request.getParameter("editIndex");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("itemId", itemId);
			GetItem  item = rest.postForObject(Constants.url + "/getItemByItemId",map, GetItem .class);
			  
			if(editIndex.equalsIgnoreCase("") || editIndex.equalsIgnoreCase(null))
			  {
					GetEnquiryDetail enquiryDetail = new GetEnquiryDetail();
					enquiryDetail.setItemId(itemId);
					enquiryDetail.setEnqQty(qty);
					enquiryDetail.setEnqRemark(itemRemark);
					enquiryDetail.setEnqDetailDate(enqItemDate);
					enquiryDetail.setItemCode(item.getItemCode()+"-"+item.getItemDesc());
					enquiryDetail.setDelStatus(1);
					addItemInEditEnquiryDetail.add(enquiryDetail);
			  }
			 else
			  {
				 	int index = Integer.parseInt(editIndex); 
				 	addItemInEditEnquiryDetail.get(index).setItemId(itemId);
				 	addItemInEditEnquiryDetail.get(index).setEnqQty(qty);
				 	addItemInEditEnquiryDetail.get(index).setEnqRemark(itemRemark);
				 	addItemInEditEnquiryDetail.get(index).setEnqDetailDate(enqItemDate);
				 	addItemInEditEnquiryDetail.get(index).setItemCode(item.getItemCode()+"-"+item.getItemDesc()); 
			  }
			
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return addItemInEditEnquiryDetail;
	}
	
	@RequestMapping(value = "/editItemInEditEnquiry", method = RequestMethod.GET)
	@ResponseBody
	public GetEnquiryDetail editItemInEditEnquiry(HttpServletRequest request, HttpServletResponse response) {

		GetEnquiryDetail edit = new GetEnquiryDetail();
		
		try {
			
			int index = Integer.parseInt(request.getParameter("index"));  
			 
			edit = addItemInEditEnquiryDetail.get(index);
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return edit;
	}
	
	@RequestMapping(value = "/submitEditEnquiry", method = RequestMethod.POST) 
	public String submitEditEnquiry(HttpServletRequest request, HttpServletResponse response) {

		 
		
		try {
			
			List<GetEnquiryHeader> enquiryHeaderList = new ArrayList<GetEnquiryHeader>();
			
			 
			String enqRemark = request.getParameter("enqRemark"); 
			String enqDate = request.getParameter("enqDate"); 
			
			String Date = DateConvertor.convertToYMD(enqDate);
			  
			editEnquiry.setEnqRemark(enqRemark);
			editEnquiry.setEnqDate(Date);
			editEnquiry.setDelStatus(1);
			editEnquiry.setEnquiryDetailList(addItemInEditEnquiryDetail);
			enquiryHeaderList.add(editEnquiry);
				  
			 System.out.println(enquiryHeaderList);
			 
			ErrorMessage res = rest.postForObject(Constants.url + "/saveEnquiryHeaderAndDetail", enquiryHeaderList, ErrorMessage.class);
			System.out.println(res);
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfEnquiry";
	}

}
