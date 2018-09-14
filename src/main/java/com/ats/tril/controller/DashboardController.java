package com.ats.tril.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.Category;
import com.ats.tril.model.ConsumptionReportData;
import com.ats.tril.model.ConsumptionReportWithCatId;
import com.ats.tril.model.EnquiryDetail;
import com.ats.tril.model.GetEnquiryHeader;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.PoDetail;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.indent.GetIndents;
import com.ats.tril.model.mrn.GetMrnDetail;
import com.ats.tril.model.mrn.GetMrnHeader;
import com.ats.tril.model.mrn.MrnDetail;
import com.ats.tril.model.mrn.MrnHeader;
import com.ats.tril.model.po.GetPoHeader;
import com.ats.tril.model.po.PoHeader;
import com.sun.org.apache.bcel.internal.generic.ALOAD;

@Controller
@Scope("session")
public class DashboardController {

	RestTemplate rest = new RestTemplate();
	GetMrnHeader getMrnHeader=null;
	List<GetMrnDetail> getMrnDetailList=null;
	
	@RequestMapping(value = "/showPurchaseDashboard", method = RequestMethod.GET)
	public ModelAndView showPurchaseDashboard(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("dashboard/purchasedashboard");
		try {
			try {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			
				map.add("status", "0");
				GetIndents[] indentList1 = rest.postForObject(Constants.url + "/getIndentList", map, GetIndents[].class);

				List<GetIndents> indentListRes1 = new ArrayList<GetIndents>(Arrays.asList(indentList1));
				System.err.println(indentListRes1.toString());
				model.addObject("indentListRes1", indentListRes1);
				map = new LinkedMultiValueMap<String, Object>();
			
				map.add("status", "1");
				GetIndents[] indentList2 = rest.postForObject(Constants.url + "/getIndentList", map, GetIndents[].class);

				List<GetIndents> indentListRes2 = new ArrayList<GetIndents>(Arrays.asList(indentList2));
				System.err.println(indentListRes2.toString());
				model.addObject("indentListRes2", indentListRes2);
				map = new LinkedMultiValueMap<String, Object>();
			
				/*map.add("status", "1");//get from enquiry table
				GetIndents[] indentList3 = rest.postForObject(Constants.url + "/getIndentList", map, GetIndents[].class);

				List<GetIndents> indentListRes3 = new ArrayList<GetIndents>(Arrays.asList(indentList3));
				System.err.println(indentListRes3.toString());*/
				 map = new LinkedMultiValueMap<>();
				map.add("fromDate", "1");
				map.add("toDate","1");
				map.add("status", 0);

				GetEnquiryHeader[] list = rest.postForObject(Constants.url + "/getEnqHeaderListBetweenDate", map,
						GetEnquiryHeader[].class);
			   List<GetEnquiryHeader>	enquiryList = new ArrayList<GetEnquiryHeader>(Arrays.asList(list));

				model.addObject("indentListRes3", enquiryList);
				System.err.println(enquiryList.toString());

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
				
				/*Calendar c = Calendar.getInstance();   
			    c.set(Calendar.DAY_OF_MONTH, 1);
			    DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
			    //System.out.println(df.format(c.getTime())); 
			    
			    Calendar cal = Calendar.getInstance();
			    cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); 
			    Date lastDayOfMonth = cal.getTime();
				
				Calendar gc = new GregorianCalendar();
		        gc.set(Calendar.MONTH, month);
		        gc.set(Calendar.DAY_OF_MONTH, 1);
		        Date monthStart = gc.getTime();
		        gc.add(Calendar.MONTH, 1);
		        gc.add(Calendar.DAY_OF_MONTH, -1);
		        Date monthEnd = gc.getTime();
		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		        System.out.println("Calculated month start date : " + format.format(monthStart));
		        System.out.println("Calculated month end date : " + format.format(monthEnd));
				
				Date[] dates = new Date[2];

			    Calendar start = Calendar.getInstance();
			    Calendar end = Calendar.getInstance();
			    
			    Date date = new Date();

			    start.setTime(date);
			    start.set(Calendar.DAY_OF_MONTH, start.getActualMinimum(Calendar.DAY_OF_MONTH));
			    start.set(Calendar.HOUR_OF_DAY, 0);
			    start.set(Calendar.MINUTE, 0);
			    start.set(Calendar.SECOND, 0);

			    end.setTime(date);
			    end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
			    end.set(Calendar.HOUR_OF_DAY, 23);
			    end.set(Calendar.MINUTE, 59);
			    end.set(Calendar.SECOND, 59);

			    //System.out.println("start "+ start.getTime());
			    //System.out.println("end   "+ end.getTime());

			    dates[0] = start.getTime();
			    dates[1] = end.getTime();
			    
			    System.out.println("start "+ start.getTime());
			    System.out.println("end   "+ end.getTime());
			    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			    
				 map = new LinkedMultiValueMap<String, Object>();
				 map.add("poType", 1);
				 map.add("fromDate",df.format(dates[0]));
				 map.add("toDate", df.format(dates[1]));
				 
				 List<ConsumptionReportData> regularList=rest.postForObject(Constants.url+"getConsumptionData", map, List.class);
				 model.addObject("regularList", regularList);
				 map = new LinkedMultiValueMap<String, Object>();
				 map.add("poType", 2);
				 map.add("fromDate",df.format(dates[0]));
				 map.add("toDate", df.format(dates[1]));
				 
				 List<ConsumptionReportData> jobWorkList=rest.postForObject(Constants.url+"getConsumptionData", map, List.class);
				model.addObject("jobWorkList", jobWorkList);
				System.err.println(jobWorkList);
				 map = new LinkedMultiValueMap<String, Object>();
				 map.add("poType", 3);
				 map.add("fromDate",df.format(dates[0]));
				 map.add("toDate", df.format(dates[1]));
				 
				 List<ConsumptionReportData>  generalList=rest.postForObject(Constants.url+"getConsumptionData", map, List.class);
				model.addObject("generalList", generalList);*/
				
				List<ConsumptionReportWithCatId> mrnReportList = new ArrayList<ConsumptionReportWithCatId>();
				List<ConsumptionReportWithCatId> issueReportList = new ArrayList<ConsumptionReportWithCatId>();
				
				SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
				Date date = new Date();
				  Calendar calendar = Calendar.getInstance();
				  calendar.setTime(date);
				   
				 String fromDate = "01"+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
				 String toDate = yy.format(date);
				 
						 map = new LinkedMultiValueMap<>(); 
				 			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				 			map.add("toDate", toDate); 
				 			System.out.println(map);
				 			ConsumptionReportWithCatId[] consumptionReportWithCatId = rest.postForObject(Constants.url + "/getConsumptionMrnData",map, ConsumptionReportWithCatId[].class);
				 			mrnReportList = new ArrayList<ConsumptionReportWithCatId>(Arrays.asList(consumptionReportWithCatId));
						  
				 			ConsumptionReportWithCatId[] consumptionReportWithCatId1 = rest.postForObject(Constants.url + "/getConsumptionIssueData",map, ConsumptionReportWithCatId[].class);
				 			issueReportList = new ArrayList<ConsumptionReportWithCatId>(Arrays.asList(consumptionReportWithCatId1));
						 
				 			
						  model.addObject("mrnReportList", mrnReportList);
						  model.addObject("issueReportList", issueReportList);
						  
						  model.addObject("fromDate", DateConvertor.convertToYMD(fromDate));
						  model.addObject("toDate", toDate);
				
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

		ModelAndView model = new ModelAndView("mrn/mrnInspectionHeader");
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
	@RequestMapping(value = "/getMrnInspForVendor", method = RequestMethod.POST)
	public ModelAndView getMrnInspForVendor(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("mrn/mrnInspectionHeader");
		try {
			int vendorId=Integer.parseInt(request.getParameter("vendId"));
			int status=Integer.parseInt(request.getParameter("mrn_status"));

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			
			for (int i = 0; i < vendorList.size(); i++) {
				vendorList.get(i).setVendorDate(DateConvertor.convertToDMY(vendorList.get(i).getVendorDate()));
			}
			model.addObject("vendorList", vendorList);
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			
			if(status==-1) {
				map.add("status","1,2,0");
			}else {
				
				map.add("status",status);

			}
			 map.add("venId",vendorId);
			List<GetMrnHeader> getMrnHeaderList=rest.postForObject(Constants.url+"getMrnHeaderList", map,  List.class);
			System.err.println("Mrn Header List  " +getMrnHeaderList);
            model.addObject("getMrnHeaderList", getMrnHeaderList);
            model.addObject("vendorId", vendorId);
            model.addObject("status", status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/getMrnDetail/{mrnId}", method = RequestMethod.GET)
	public ModelAndView getMrnDetail(@PathVariable("mrnId")int mrnId,HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("mrn/mrnInspectionDetail");
		 getMrnHeader=new GetMrnHeader();
		try {
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			 map.add("mrnId",mrnId);
		
			 getMrnHeader=rest.postForObject(Constants.url+"getMrnHeader", map,  GetMrnHeader.class);
			
            model.addObject("getMrnHeader", getMrnHeader);
            System.out.println("getMrnHeader -------- " + getMrnHeader);
            
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	
	@RequestMapping(value = "/submitMrnInspectionList", method = RequestMethod.POST) 
	public ModelAndView submitMrnInspectionList(HttpServletRequest request, HttpServletResponse response) {

		 
		ModelAndView model = new ModelAndView("mrn/mrnInspectionDetail");
		
		try {
		 getMrnDetailList = new ArrayList<>();
			
			int mrnId =Integer.parseInt(request.getParameter("mrnId"));
			String[] checkbox=request.getParameterValues("select_to_approve");
			
			
			for (int i = 0; i < getMrnHeader.getGetMrnDetailList().size(); i++) 
			{
				for(int j=0;j<checkbox.length;j++) 
				{
					if(Integer.parseInt(checkbox[j])==getMrnHeader.getGetMrnDetailList().get(i).getMrnDetailId()) 
					{
						 getMrnHeader.getGetMrnDetailList().get(i).setApproveQty(Float.parseFloat(request.getParameter("approveQty"+getMrnHeader.getGetMrnDetailList().get(i).getMrnDetailId())));
						 getMrnHeader.getGetMrnDetailList().get(i).setRejectQty(Float.parseFloat(request.getParameter("rejectQty"+getMrnHeader.getGetMrnDetailList().get(i).getMrnDetailId())));
						 getMrnHeader.getGetMrnDetailList().get(i).setRemainingQty(Float.parseFloat(request.getParameter("approveQty"+getMrnHeader.getGetMrnDetailList().get(i).getMrnDetailId())));
						 getMrnDetailList.add(getMrnHeader.getGetMrnDetailList().get(i));
					}
						 
				}
			}
			
			System.out.println("getMrnDetailList -------- " + getMrnDetailList);
			model.addObject("getMrnDetailList", getMrnDetailList);
			model.addObject("mrnId", mrnId);
			model.addObject("getMrnHeader", getMrnHeader);
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/submitMrnInspection", method = RequestMethod.POST) 
	public String submitMrnInspection(HttpServletRequest request, HttpServletResponse response) {

		try {
			
			RestTemplate restTemp = new RestTemplate();
             if(!getMrnDetailList.isEmpty()) {
            	 for(int i=0;i<getMrnDetailList.size();i++)
            	 {

            		 getMrnDetailList.get(i).setMrnDetailStatus(1);
            	 }
			getMrnHeader.setGetMrnDetailList(getMrnDetailList);
             }
             System.out.println(" final getMrnHeader -------- " + getMrnHeader);
			 List<MrnDetail> mrnDetailList = restTemp.postForObject(Constants.url + "/saveMrnData", getMrnDetailList, List.class);

			System.err.println("mrnDetailList " + mrnDetailList.toString()); 
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showMrnForInspection";
	}
}
