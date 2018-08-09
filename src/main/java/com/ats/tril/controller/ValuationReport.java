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
import com.ats.tril.model.GetCurrentStock;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.ItemValuationList;
 

@Controller
@Scope("session")
public class ValuationReport {

	
	RestTemplate rest = new RestTemplate();
	String fromDate;
	String toDate;
	
	@RequestMapping(value = "/stockBetweenDateWithCatId", method = RequestMethod.GET)
	public ModelAndView itemValueationReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/stockBetweenDateWithCatId");
		try {
			
			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));

			model.addObject("categoryList", categoryList); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/getStockBetweenDateWithCatId", method = RequestMethod.GET)
	@ResponseBody
	public List<GetCurrentStock> getStockBetweenDateWithCatId(HttpServletRequest request, HttpServletResponse response) {

		List<GetCurrentStock> getStockBetweenDate = new ArrayList<>();
		
		try {
		 
			fromDate = request.getParameter("fromDate");
			toDate = request.getParameter("toDate");
			int catId = Integer.parseInt(request.getParameter("catId"));
			 
			
			SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
			
			Date date = dd.parse(fromDate);
			  Calendar calendar = Calendar.getInstance();
			  calendar.setTime(date);
			   
			 String firstDate = "01"+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
			 
			 System.out.println(DateConvertor.convertToYMD(firstDate) + DateConvertor.convertToYMD(fromDate));
			 
			 if(DateConvertor.convertToYMD(firstDate).compareTo(DateConvertor.convertToYMD(fromDate))<0)
			 {
				calendar.add(Calendar.DATE, -1);
				String previousDate = yy.format(new Date(calendar.getTimeInMillis())); 
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate",DateConvertor.convertToYMD(firstDate));
	 			map.add("toDate",previousDate); 
	 			map.add("catId", catId);
	 			System.out.println(map);
	 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatId",map,GetCurrentStock[].class); 
	 			List<GetCurrentStock> diffDateStock = new ArrayList<>(Arrays.asList(getCurrentStock));
	 			
	 			calendar.add(Calendar.DATE, 1);
				String addDay = yy.format(new Date(calendar.getTimeInMillis()));
	 			map = new LinkedMultiValueMap<>();
				map.add("fromDate",addDay);
	 			map.add("toDate",DateConvertor.convertToYMD(toDate)); 
	 			map.add("catId", catId);
	 			System.out.println(map);
	 			GetCurrentStock[] getCurrentStock1 = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatId",map,GetCurrentStock[].class); 
	 			 getStockBetweenDate = new ArrayList<>(Arrays.asList(getCurrentStock1));
	 			 
	 			 for(int i = 0 ; i< getStockBetweenDate.size() ; i++)
	 			 {
	 				 for(int j = 0 ; j< diffDateStock.size() ; j++)
		 			 {
	 					 if(getStockBetweenDate.get(i).getItemId()==diffDateStock.get(j).getItemId())
	 					 {
	 						getStockBetweenDate.get(i).setOpeningStock(diffDateStock.get(j).getOpeningStock()+diffDateStock.get(j).getApproveQty()-diffDateStock.get(j).getIssueQty()
							 +diffDateStock.get(j).getReturnIssueQty()-diffDateStock.get(j).getDamageQty()-diffDateStock.get(j).getGatepassQty()
							 +diffDateStock.get(j).getGatepassReturnQty());
	 						break;
	 					 }
		 			 }
	 			 }
			 }
			 else
			 {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate",DateConvertor.convertToYMD(fromDate));
	 			map.add("toDate",DateConvertor.convertToYMD(toDate)); 
	 			map.add("catId", catId);
	 			System.out.println(map);
	 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatId",map,GetCurrentStock[].class); 
	 			getStockBetweenDate = new ArrayList<>(Arrays.asList(getCurrentStock));
			 }
			  
			  
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getStockBetweenDate;
	}
	
	@RequestMapping(value = "/valueationReportDetail/{itemId}/{openingStock}", method = RequestMethod.GET)
	public ModelAndView valueationReportDetail(@PathVariable int itemId,@PathVariable int openingStock, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/valueationReportDetail");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("itemId", itemId);
			GetItem  item = rest.postForObject(Constants.url + "/getItemByItemId",map, GetItem .class);
			model.addObject("item", item);
			
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			ItemValuationList[] itemValuation = rest.postForObject(Constants.url + "/valueationReportDetail",map, ItemValuationList[].class);
			List<ItemValuationList> itemValuationList = new ArrayList<ItemValuationList>(Arrays.asList(itemValuation));

			model.addObject("itemValuationList", itemValuationList);
			model.addObject("openingStock", openingStock);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	
}
