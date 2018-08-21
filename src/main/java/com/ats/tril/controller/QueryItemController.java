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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.doc.DocumentBean;

@Controller
@Scope("session")
public class QueryItemController {
	
	RestTemplate rest = new RestTemplate();
	@RequestMapping(value = "/getQueryItemList", method = RequestMethod.GET)
	public ModelAndView getItemList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("showqueryitems");
		try {

			GetItem[] item = rest.getForObject(Constants.url + "/getAllItems",  GetItem[].class); 
			List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(item));
			model.addObject("itemList", itemList);

		} catch (Exception e) {
			
			System.err.println("Exception in getting getQueryItemList" +e.getMessage());
			
			e.printStackTrace();
		}

		return model;
	}
	
	//
	@RequestMapping(value = "/getQueryItemDetail", method = RequestMethod.POST)
	public ModelAndView getIndentQueryItems(HttpServletRequest request, HttpServletResponse response) {

	int docId = Integer.parseInt(request.getParameter("docType"));
	int itemId = Integer.parseInt(request.getParameter("itemId"));

	String date = request.getParameter("date");
	ModelAndView model=null;
	try {
	System.err.println("item Id  "+itemId);
	DocumentBean docBean = null;
	 model = new ModelAndView("showqueryitems");
	if (date == "" || date==null) {
		Date currDate = new Date();
		//date = new SimpleDateFormat("yyyy-MM-dd").format(currDate);
	}

	MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
	map.add("docId", docId);
	map.add("date", DateConvertor.convertToYMD(date));

	RestTemplate restTemplate = new RestTemplate();

	docBean = restTemplate.postForObject(Constants.url + "getDocumentInfo", map, DocumentBean.class);
	System.err.println("Doc" + docBean.toString());
	
	}catch (Exception e) {
		
		System.err.println("Exce in /getQueryItemDetail   "+e.getMessage());
		
		e.printStackTrace();
	}
	//
	return model;
	
	}
	
}
