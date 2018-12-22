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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.model.Category;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetSubDept;
import com.ats.tril.model.Type; 
import com.ats.tril.model.doc.SubDocument; 

@Controller
@Scope("session")
public class DocumentController {
	
	RestTemplate rest = new RestTemplate();
	
	@RequestMapping(value = "/indentLimitSetting", method = RequestMethod.GET)
	public ModelAndView indentLimitSetting(HttpServletRequest request, HttpServletResponse response) {
		
		 
		ModelAndView model = new ModelAndView("document/indentLimitSetting");
		try {

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category)); 
			model.addObject("categoryList", categoryList);

			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);
			
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("docId",1);
			map.add("date",sf.format(date));
			
			SubDocument[] subDoc = rest.postForObject(Constants.url + "/getIndentDocumentForSetting", map,
					SubDocument[].class);
			List<SubDocument> list = new ArrayList<SubDocument>(Arrays.asList(subDoc));
			model.addObject("list", list);
			 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/updateEnabledAndDisabled", method = RequestMethod.GET)
	public @ResponseBody ErrorMessage updateEnabledAndDisabled(HttpServletRequest request,
			HttpServletResponse response) {

		ErrorMessage errorMessage = new ErrorMessage();
		try {

			 

			int subDocId = Integer.parseInt(request.getParameter("subDocId"));
			int enabled = Integer.parseInt(request.getParameter("enabled")); 
			float limitValue = Float.parseFloat(request.getParameter("limitValue"));
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>(); 
			map.add("subDocId", subDocId);
			map.add("enabled", enabled);
			map.add("limitValue", limitValue);
			
			RestTemplate restTemplate = new RestTemplate(); 
			errorMessage = restTemplate.postForObject(Constants.url + "/updateEnabledAndDisabled", map, ErrorMessage.class);

		} catch (Exception e) {
 

			e.printStackTrace();
		}
		return errorMessage;
	}

}
