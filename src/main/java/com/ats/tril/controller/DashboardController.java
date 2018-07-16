package com.ats.tril.controller;

import java.text.DateFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.Category;
import com.ats.tril.model.ConsumptionReportData;
import com.ats.tril.model.EnquiryDetail;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.indent.GetIndents;
import com.ats.tril.model.mrn.GetMrnHeader;
import com.ats.tril.model.po.GetPoHeader;

@Controller
@Scope("session")
public class DashboardController {

	RestTemplate rest = new RestTemplate();

	@RequestMapping(value = "/showPurchaseDashboard", method = RequestMethod.GET)
	public ModelAndView showPurchaseDashboard(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("dashboard/purchasedashboard");
		try {
			try {
				/*Date date = new Date();*/
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				/*DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String fromDate = df.format(date);
				String toDate = df.format(date);
               
				map.add("fromDate", fromDate);
				map.add("toDate",toDate);
				*/map.add("status", "0");
				GetIndents[] indentList1 = rest.postForObject(Constants.url + "/getIndentList", map, GetIndents[].class);

				List<GetIndents> indentListRes1 = new ArrayList<GetIndents>(Arrays.asList(indentList1));
				System.err.println(indentListRes1.toString());
				model.addObject("indentListRes1", indentListRes1);
				map = new LinkedMultiValueMap<String, Object>();
			/*	map.add("fromDate", fromDate);
				map.add("toDate",toDate);*/
				map.add("status", "2");
				GetIndents[] indentList2 = rest.postForObject(Constants.url + "/getIndentList", map, GetIndents[].class);

				List<GetIndents> indentListRes2 = new ArrayList<GetIndents>(Arrays.asList(indentList2));
				System.err.println(indentListRes2.toString());
				model.addObject("indentListRes2", indentListRes2);
				map = new LinkedMultiValueMap<String, Object>();
				/*map.add("fromDate", fromDate);
				map.add("toDate",toDate);*/
				map.add("status", "1");
				GetIndents[] indentList3 = rest.postForObject(Constants.url + "/getIndentList", map, GetIndents[].class);

				List<GetIndents> indentListRes3 = new ArrayList<GetIndents>(Arrays.asList(indentList3));
				System.err.println(indentListRes3.toString());
				model.addObject("indentListRes3", indentListRes3);
				
				List<GetPoHeader> headerList = new ArrayList<GetPoHeader>();
				try {
					
					 map = new LinkedMultiValueMap<String, Object>();
					map.add("poType", 1);
					map.add("status",1);
					headerList=rest.postForObject(Constants.url+"getPoHeaderDashList", map, List.class);
			     model.addObject("headerList", headerList);
					System.err.println(headerList.toString());
				}
				catch (Exception e) {
					
					e.printStackTrace();
				}
				
				 map = new LinkedMultiValueMap<String, Object>();
				 map.add("poType", 1);
				 map.add("fromDate","2018-07-01");
				 map.add("toDate", "2018-07-30");
				 
				 List<ConsumptionReportData> regularList=rest.postForObject(Constants.url+"getConsumptionData", map, List.class);
				 model.addObject("regularList", regularList);
				 map = new LinkedMultiValueMap<String, Object>();
				 map.add("poType", 2);
				 map.add("fromDate","2018-07-01");
				 map.add("toDate", "2018-07-30");
				 
				 List<ConsumptionReportData> jobWorkList=rest.postForObject(Constants.url+"getConsumptionData", map, List.class);
				model.addObject("jobWorkList", jobWorkList);
				 map = new LinkedMultiValueMap<String, Object>();
				 map.add("poType", 3);
				 map.add("fromDate","2018-07-01");
				 map.add("toDate", "2018-07-30");
				 
				 List<ConsumptionReportData>  generalList=rest.postForObject(Constants.url+"getConsumptionData", map, List.class);
				model.addObject("generalList", generalList);
				
				Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
				List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));

				model.addObject("categoryList", categoryList);
			}
				catch (Exception e) {
					e.printStackTrace();
				}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	@RequestMapping(value = "/showMrnForInspection", method = RequestMethod.GET)
	public ModelAndView showMrnForInspection(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("mrn/mrnInspectionDetail");
		try {

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			
			for (int i = 0; i < vendorList.size(); i++) {
				vendorList.get(i).setVendorDate(DateConvertor.convertToDMY(vendorList.get(i).getVendorDate()));
			}
			model.addObject("vendorList", vendorList);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			 map.add("status","0,1");
			 map.add("venId","0");
			List<GetMrnHeader> getMrnHeaderList=rest.postForObject(Constants.url+"getMrnHeaderList", map,  List.class);
            model.addObject("getMrnHeaderList", getMrnHeaderList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
}
