package com.ats.tril.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ats.tril.model.Vendor;
import com.ats.tril.model.indent.IndentReport;
import com.ats.tril.model.mrn.GetMrnHeader;
import com.ats.tril.model.mrn.MrnReport;
@Controller
public class MrnReportController {
	
	@RequestMapping(value = "/showMrnRport", method = RequestMethod.GET)
	public ModelAndView showMrnRpoert(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			RestTemplate rest = new RestTemplate();
			 model = new ModelAndView("mrn/report/mrnReport");
			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	
	
	
	
	 List<MrnReport> mrnReportList;
	@RequestMapping(value = "/getMrnReportList", method = RequestMethod.GET)
	@ResponseBody
	public List<MrnReport> getMrnReportList(HttpServletRequest request, HttpServletResponse response) {

		try {

			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();


			mrnReportList=new ArrayList<MrnReport>();
			
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			//String[] grnTypeList = request.getParameterValues("grn_type_list");
			
			String selectedVendor = request.getParameter("vendor_list");
			
			String selectedGrnType = request.getParameter("grn_type_list");

			String selectedStatus = request.getParameter("status_list");
			

			selectedGrnType = selectedGrnType.substring(1, selectedGrnType.length() - 1);
			selectedGrnType = selectedGrnType.replaceAll("\"", "");

			List<String> grnTypeList = new ArrayList<String>();
			grnTypeList = Arrays.asList(selectedGrnType);
			
			if(grnTypeList.contains("-1")) {
				
				map.add("grnTypeList", "3"+","+"1"+","+"2"+","+"4");
			}
			else {
				
				map.add("grnTypeList", selectedGrnType);
			}
			
			
			
			selectedVendor = selectedVendor.substring(1, selectedVendor.length() - 1);
			selectedVendor = selectedVendor.replaceAll("\"", "");
			List<String> vendorList = new ArrayList<String>();
			vendorList = Arrays.asList(selectedVendor);
			if(vendorList.contains("-1")) {
				
				map.add("vendorIdList", "-1");
			}
			else {
				
				map.add("vendorIdList", selectedVendor);
			}
			
			selectedStatus = selectedStatus.substring(1, selectedStatus.length() - 1);
			selectedStatus = selectedStatus.replaceAll("\"", "");
			List<String> statusList = new ArrayList<String>();
			statusList = Arrays.asList(selectedStatus);
			
			if(statusList.contains("-1")) {
				
				map.add("statusList", "0"+","+"1"+","+"2"+","+"3");
			}
			else {
				
				map.add("statusList", selectedStatus);
			}
			
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			
			MrnReport[] mrnReport = restTemplate.postForObject(Constants.url + "/getMrnHeadReport", map,
					MrnReport[].class);

			mrnReportList = new ArrayList<MrnReport>(Arrays.asList(mrnReport));

			System.err.println("Mrn  Report  " +mrnReportList.toString());
			
		}
		catch (Exception e) {
			
			System.err.println("Exception in Mrn Header Report List  " +e.getMessage());
			
			e.printStackTrace();
	
		}
		
		return mrnReportList;
		
	}
	
}
