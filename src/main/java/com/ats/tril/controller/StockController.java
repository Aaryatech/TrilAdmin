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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.GetCurrentStock;
import com.ats.tril.model.StockDetail;
import com.ats.tril.model.StockHeader; 

@Controller
@Scope("session")
public class StockController {
	
	List<GetCurrentStock> stockListForMonthEnd = new ArrayList<>();
	StockHeader stockHeader = new StockHeader();
	
	RestTemplate rest = new RestTemplate();
	
	@RequestMapping(value = "/getCurrentStock", method = RequestMethod.GET)
	public ModelAndView getCurrentStock(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("stock/getCurrentStock");
		try {
		 
			String fromDate,toDate,monthName;
			StockHeader stockHeader = rest.getForObject(Constants.url + "/getCurrentRunningMonthAndYear",StockHeader.class);
			
			String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		     
			 System.out.println(stockHeader);
			 
			 if(stockHeader.getStockHeaderId()!=0)
			 {
				 Date date = new Date();
				 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				 
				 fromDate=stockHeader.getYear()+"-"+stockHeader.getMonth()+"-"+"01";
				 toDate=sf.format(date);
				 monthName=monthNames[stockHeader.getMonth()-1];
				 System.out.println("monthName" + monthName);
			 }
			 else
			 {
				 Date date = new Date();
				 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				 
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(date);
				 int year = cal.get(Calendar.YEAR);
				 int month = cal.get(Calendar.MONTH);
				  
				 fromDate=year+"-"+(month+1)+"-"+"01";
				 toDate=sf.format(date);
				 
				 monthName=monthNames[(month)];
			 }
			 System.out.println("fromDate" + fromDate);
			 System.out.println("toDate" + toDate);
			 
			 
			 MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			 map.add("fromDate", fromDate);
 			 map.add("toDate", toDate);
			 
 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getCurrentStock",map,GetCurrentStock[].class);
 			
 			List<GetCurrentStock> stockList = new ArrayList<>(Arrays.asList(getCurrentStock));
 			
 			System.out.println(stockList);
 			model.addObject("stockList", stockList);
 			model.addObject("fromDate",DateConvertor.convertToDMY(fromDate));
 			model.addObject("toDate", DateConvertor.convertToDMY(toDate));
 			model.addObject("monthName", monthName);
			  
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/monthEndStock", method = RequestMethod.GET)
	public ModelAndView monthEndStock(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("stock/monthEndStock");
		try {
		 
			String fromDate,toDate,monthName;
			 stockHeader = rest.getForObject(Constants.url + "/getCurrentRunningMonthAndYear",StockHeader.class);
			
			String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		    
			 
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			fromDate=stockHeader.getYear()+"-"+stockHeader.getMonth()+"-"+"01";
			monthName =  monthNames[stockHeader.getMonth()-1];
			 Date today = sf.parse(fromDate);  

		        Calendar calendar = Calendar.getInstance();  
		        calendar.setTime(today);  
		        calendar.add(Calendar.MONTH, 1);  
		        calendar.set(Calendar.DAY_OF_MONTH, 1);  
		        calendar.add(Calendar.DATE, -1);  

		        Date lastDayOfMonth = calendar.getTime();  

		         
		        System.out.println("Today            : " + sf.format(today));  
		        System.out.println("Last Day of Month: " + sf.format(lastDayOfMonth));  
				 
			
			toDate=sf.format(lastDayOfMonth);
			 
			 System.out.println("fromDate" + fromDate);
			 System.out.println("toDate" + toDate);
			 System.out.println("monthName" + monthName);
			 
			 MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			 map.add("fromDate", fromDate);
 			 map.add("toDate", toDate);
			 
 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getCurrentStock",map,GetCurrentStock[].class);
 			
 			stockListForMonthEnd = new ArrayList<>(Arrays.asList(getCurrentStock));
 			
 			System.out.println(stockListForMonthEnd);
 			model.addObject("stockList", stockListForMonthEnd);
 			model.addObject("fromDate",DateConvertor.convertToDMY(fromDate));
 			model.addObject("toDate", DateConvertor.convertToDMY(toDate));
 			model.addObject("monthName", monthName);
 			
 			Date date = new Date();
 			
 			int isMonthEnd=0;
 			 
 			if(sf.format(date).compareTo(toDate)>0) 
 				isMonthEnd=1;
 			model.addObject("isMonthEnd", isMonthEnd);
			  
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/submitMonthEnd", method = RequestMethod.POST)
	public String submitMonthEnd(HttpServletRequest request, HttpServletResponse response) {

		 try {
		  
			 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,Object>();
			 map.add("stockId", stockHeader.getStockHeaderId());
			 
			 StockDetail[] stockDetail = rest.postForObject(Constants.url + "/getStockDetailByStockId",map,StockDetail[].class); 
			 List<StockDetail> stockDetailList = new ArrayList<>(Arrays.asList(stockDetail));
 			 
			 for(int i = 0 ; i<stockDetailList.size() ; i++)
			 {
				 for(int j = 0 ; j<stockListForMonthEnd.size() ; j++)
				 {
					 if(stockDetailList.get(i).getItemId() == stockListForMonthEnd.get(j).getItemId())
					 {
						 stockDetailList.get(i).setApprovedQty(stockListForMonthEnd.get(j).getApproveQty());
						 stockDetailList.get(i).setApprovedQtyValue(stockListForMonthEnd.get(j).getApprovedQtyValue());
						 stockDetailList.get(i).setIssueQty(stockListForMonthEnd.get(j).getIssueQty());
						 stockDetailList.get(i).setIssueQtyValue(stockListForMonthEnd.get(j).getIssueQtyValue());
						 stockDetailList.get(i).setReturnIssueQty(stockListForMonthEnd.get(j).getReturnIssueQty());
						 stockDetailList.get(i).setDamageQty(stockListForMonthEnd.get(j).getDamageQty());
						 stockDetailList.get(i).setDamageValue(stockListForMonthEnd.get(j).getDamagValue());
						 stockDetailList.get(i).setGatepassQty(stockListForMonthEnd.get(j).getGatepassQty());
						 stockDetailList.get(i).setGatepassReturnQty(stockListForMonthEnd.get(j).getGatepassReturnQty());
						 stockDetailList.get(i).setClosingQty(stockListForMonthEnd.get(j).getOpeningStock()+stockListForMonthEnd.get(j).getApproveQty()-stockListForMonthEnd.get(j).getIssueQty()
								 +stockListForMonthEnd.get(j).getReturnIssueQty()-stockListForMonthEnd.get(j).getDamageQty()-stockListForMonthEnd.get(j).getGatepassQty()
								 +stockListForMonthEnd.get(j).getGatepassReturnQty());
						 stockDetailList.get(i).setCloasingValue(stockListForMonthEnd.get(j).getOpStockValue()+stockListForMonthEnd.get(j).getApprovedQtyValue()-stockListForMonthEnd.get(j).getIssueQtyValue()-stockListForMonthEnd.get(j).getDamagValue());
						 
					 }
				 }
			 }
			 
			 stockHeader.setStatus(1);
			 stockHeader.setStockDetailList(stockDetailList);
			 
			 StockHeader update = rest.postForObject(Constants.url + "/insertStock",stockHeader,StockHeader.class); 
			 System.out.println(update);
			 
			 if(update!=null)
			 {
				 List<StockDetail> insertNewList = new ArrayList<>();
				 String lastDate = request.getParameter("toDate");
				  SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
				  SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
				  int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
				  Date date = dd.parse(lastDate);
				   
				  String nextDate = dd.format(date.getTime() + MILLIS_IN_DAY);
				   
				  date = dd.parse(nextDate);
				  Calendar calendar = Calendar.getInstance();
				  calendar.setTime(date); 
				  System.out.println("next Date  "+yy.format(calendar.getTime()));
				  
				 StockHeader newEntry = new StockHeader();
				 newEntry.setDate(yy.format(calendar.getTime()));
				 newEntry.setMonth(calendar.get(Calendar.MONTH)+1);
				 newEntry.setYear(calendar.get(Calendar.YEAR));
				 newEntry.setDelStatus(1);
				 
				 for(int j = 0 ; j<stockListForMonthEnd.size() ; j++)
				 {
					 StockDetail stockDetail1 = new StockDetail();
					 stockDetail1.setItemId(stockListForMonthEnd.get(j).getItemId());
					 stockDetail1.setDelStatus(1);
					 stockDetail1.setOpStockQty(stockListForMonthEnd.get(j).getOpeningStock()+stockListForMonthEnd.get(j).getApproveQty()-stockListForMonthEnd.get(j).getIssueQty()
							 +stockListForMonthEnd.get(j).getReturnIssueQty()-stockListForMonthEnd.get(j).getDamageQty()-stockListForMonthEnd.get(j).getGatepassQty()
							 +stockListForMonthEnd.get(j).getGatepassReturnQty());
					 stockDetail1.setOpStockValue(stockListForMonthEnd.get(j).getOpStockValue()+stockListForMonthEnd.get(j).getApprovedQtyValue()-stockListForMonthEnd.get(j).getIssueQtyValue()-stockListForMonthEnd.get(j).getDamagValue());
					 
					 insertNewList.add(stockDetail1);
				 }
				 newEntry.setStockDetailList(insertNewList);
				 System.out.println("newEntry" + newEntry);
				 StockHeader insert = rest.postForObject(Constants.url + "/insertStock",newEntry,StockHeader.class); 
				 System.out.println(insert);
				  
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/monthEndStock";
	}
	
	@RequestMapping(value = "/stockBetweenDate", method = RequestMethod.GET)
	public ModelAndView stockBetweenDate(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("stock/stockBetweenDate");
		try {
			List<GetCurrentStock> getStockBetweenDate = new ArrayList<>();
			 
			if( request.getParameter("fromDate")==null || request.getParameter("toDate")==null) {
				 
			}
			else {
				String fromDate = request.getParameter("fromDate");
				String toDate = request.getParameter("toDate");
				 
				
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
		 			 System.out.println(map);
		 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getCurrentStock",map,GetCurrentStock[].class); 
		 			List<GetCurrentStock> diffDateStock = new ArrayList<>(Arrays.asList(getCurrentStock));
		 			
		 			 calendar.add(Calendar.DATE, 1);
					  String addDay = yy.format(new Date(calendar.getTimeInMillis()));
		 			map = new LinkedMultiValueMap<>();
					 map.add("fromDate",addDay);
		 			 map.add("toDate",DateConvertor.convertToYMD(toDate)); 
		 			 System.out.println(map);
		 			GetCurrentStock[] getCurrentStock1 = rest.postForObject(Constants.url + "/getCurrentStock",map,GetCurrentStock[].class); 
		 			 getStockBetweenDate = new ArrayList<GetCurrentStock>(Arrays.asList(getCurrentStock1));
		 			 
		 			 for(int i = 0 ; i< getStockBetweenDate.size() ; i++)
		 			 {
		 				 for(int j = 0 ; j< diffDateStock.size() ; j++)
			 			 {
		 					 if(getStockBetweenDate.get(i).getItemId()==diffDateStock.get(j).getItemId())
		 					 {
		 						getStockBetweenDate.get(i).setOpeningStock(diffDateStock.get(j).getOpeningStock()+diffDateStock.get(j).getApproveQty()-diffDateStock.get(j).getIssueQty()
								 +diffDateStock.get(j).getReturnIssueQty()-diffDateStock.get(j).getDamageQty()-diffDateStock.get(j).getGatepassQty()
								 +diffDateStock.get(j).getGatepassReturnQty());
		 						getStockBetweenDate.get(i).setOpStockValue(diffDateStock.get(j).getOpStockValue()+diffDateStock.get(j).getApprovedQtyValue()-diffDateStock.get(j).getIssueQtyValue()-diffDateStock.get(j).getDamagValue());
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
		 			 System.out.println(map);
		 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getCurrentStock",map,GetCurrentStock[].class); 
		 			getStockBetweenDate = new ArrayList<GetCurrentStock>(Arrays.asList(getCurrentStock));
				 }
				 
				 
				 model.addObject("fromDate", fromDate);
					model.addObject("toDate", toDate);
					model.addObject("stockList", getStockBetweenDate);
			}
			
			System.out.println(getStockBetweenDate);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/getStockBetweenDate", method = RequestMethod.GET) 
	public ModelAndView getStockBetweenDate(HttpServletRequest request, HttpServletResponse response) {

		List<GetCurrentStock> getStockBetweenDate = new ArrayList<>();
		ModelAndView model = new ModelAndView("stock/stockBetweenDate");
		try {
		 
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			 
			
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
	 			 System.out.println(map);
	 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getCurrentStock",map,GetCurrentStock[].class); 
	 			List<GetCurrentStock> diffDateStock = new ArrayList<>(Arrays.asList(getCurrentStock));
	 			
	 			 calendar.add(Calendar.DATE, 1);
				  String addDay = yy.format(new Date(calendar.getTimeInMillis()));
	 			map = new LinkedMultiValueMap<>();
				 map.add("fromDate",addDay);
	 			 map.add("toDate",DateConvertor.convertToYMD(toDate)); 
	 			 System.out.println(map);
	 			GetCurrentStock[] getCurrentStock1 = rest.postForObject(Constants.url + "/getCurrentStock",map,GetCurrentStock[].class); 
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
	 						getStockBetweenDate.get(i).setOpStockValue(diffDateStock.get(j).getOpStockValue()+diffDateStock.get(j).getApprovedQtyValue()-diffDateStock.get(j).getIssueQtyValue()-diffDateStock.get(j).getDamagValue());
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
	 			 System.out.println(map);
	 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getCurrentStock",map,GetCurrentStock[].class); 
	 			getStockBetweenDate = new ArrayList<>(Arrays.asList(getCurrentStock));
			 }
			 
			 
			 model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
 			 
			  
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addObject("getStockBetweenDate", getStockBetweenDate);
		
		return model;
	}

}
