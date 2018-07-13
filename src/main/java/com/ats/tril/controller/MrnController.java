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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.model.GetPODetail;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.po.PoHeader;
import com.sun.org.apache.bcel.internal.util.SyntheticRepository;

@Controller
@Scope("session")
public class MrnController {

	RestTemplate rest = new RestTemplate();

	@RequestMapping(value = "/showAddMrn", method = RequestMethod.GET)
	public ModelAndView showAddMrn(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("mrn/showAddMrn");

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

			System.err.println("Inside show Add Mrn ");

		} catch (Exception e) {

			System.err.println("Exception in showing showAddMrn" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = { "/getPOHeaderList" }, method = RequestMethod.GET)
	public @ResponseBody List<PoHeader> getPOHeaderList(HttpServletRequest request, HttpServletResponse response) {

		List<PoHeader> poHeadList = new ArrayList<PoHeader>();
		
		try {

			int vendorId = 0;

			vendorId = Integer.parseInt(request.getParameter("vendorId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vendId", vendorId);
			map.add("delStatus", Constants.delStatus);
			map.add("statusList", "1,2");

			PoHeader[] poHeadRes = rest.postForObject(Constants.url + "/getPOHeaderList",map, PoHeader[].class);
			poHeadList = new ArrayList<PoHeader>(Arrays.asList(poHeadRes));

			System.err.println("PO header List Using Ajax Call  " + poHeadList.toString());

		} catch (Exception e) {

			System.err.println("Exception in getting PO Header List By Ajax Call " + e.getMessage());
			e.printStackTrace();
		}

		return poHeadList;
	}
	
	
	@RequestMapping(value = { "/getPODetailList" }, method = RequestMethod.GET)
	public @ResponseBody List<GetPODetail> getPODetails(HttpServletRequest request, HttpServletResponse response) {

		
		
	List<GetPODetail> poDetailList = new ArrayList<GetPODetail>();
		
		try {

			String poIdList = request.getParameter("poIds");
			System.err.println("Po  Id List  " +poIdList);

			
			poIdList = poIdList.substring(1, poIdList.length() - 1);
			poIdList = poIdList.replaceAll("\"", "");
			
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("poIdList", poIdList);

			GetPODetail[] poDetailRes= rest.postForObject(Constants.url + "/getPODetailList",map, GetPODetail[].class);
			poDetailList = new ArrayList<GetPODetail>(Arrays.asList(poDetailRes));

			System.err.println("PO Details List Using Ajax Call  " + poDetailList.toString());

		} catch (Exception e) {

			System.err.println("Exception in getting PO Detail List @getPODetailList By Ajax Call " + e.getMessage());
			e.printStackTrace();
		}

		return poDetailList;
		
}
}
