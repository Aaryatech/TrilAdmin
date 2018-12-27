package com.ats.tril.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
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
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
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
import com.ats.tril.model.Company;
import com.ats.tril.model.ConsumptionReportData;
import com.ats.tril.model.ConsumptionReportWithCatId;
import com.ats.tril.model.EnquiryDetail;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.ExportToExcel;
import com.ats.tril.model.GetEnquiryHeader;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.MonthCategoryWiseMrnReport;
import com.ats.tril.model.PoDetail;
import com.ats.tril.model.Type;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.indent.GetIndents;
import com.ats.tril.model.login.User;
import com.ats.tril.model.mrn.GetMrnDetail;
import com.ats.tril.model.mrn.GetMrnHeader;
import com.ats.tril.model.mrn.MrnDetail;
import com.ats.tril.model.mrn.MrnHeader;
import com.ats.tril.model.po.GetPoHeader;
import com.ats.tril.model.po.PoHeader;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.bcel.internal.generic.ALOAD;

@Controller
@Scope("session")
public class DashboardController {

	RestTemplate rest = new RestTemplate();
	GetMrnHeader getMrnHeader=null;
	List<GetMrnDetail> getMrnDetailList=null;
	List<ConsumptionReportWithCatId> mrnReportList = new ArrayList<ConsumptionReportWithCatId>();
	List<ConsumptionReportWithCatId> issueReportList = new ArrayList<ConsumptionReportWithCatId>();
	List<Category> categoryList = new ArrayList<Category>( );
	List<ConsumptionReportWithCatId> mrnIssueReportList = new ArrayList<ConsumptionReportWithCatId>();
	
	String fromDateForPdf;
	String toDateForPdf;
	Company companyInfo = new Company();
	
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
				
				 mrnReportList = new ArrayList<ConsumptionReportWithCatId>();
				 issueReportList = new ArrayList<ConsumptionReportWithCatId>();
				  mrnIssueReportList = new ArrayList<ConsumptionReportWithCatId>();
				 
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
						 
				 			 
				 			
				 			  /*for(int i=0; i<mrnReportList.size() ; i++) {
				 				
				 				ConsumptionReportWithCatId obj = new ConsumptionReportWithCatId();
				 				
				 				obj.setTypeId(mrnReportList.get(i).getTypeId());
				 				obj.setTypeName(mrnReportList.get(i).getTypeName());
				 				obj.setConsumptionReportList(mrnReportList.get(i).getConsumptionReportList());
				 				mrnIssueReportList.add(obj);
				 			}
				 			
				 			 for(int i=0; i<mrnIssueReportList.size() ; i++) {
				 				
				 				for(int j=0; j<issueReportList.size() ; j++) {
				 					
				 					if(mrnIssueReportList.get(i).getTypeId()==issueReportList.get(j).getTypeId()) {
				 						
				 						for(int k=0; k<issueReportList.get(j).getConsumptionReportList().size() ; k++) {
				 							
				 							for(int m=0; m<mrnIssueReportList.get(i).getConsumptionReportList().size() ; m++) {
				 								
				 								if(issueReportList.get(j).getConsumptionReportList().get(k).getCatId()==mrnIssueReportList.get(i).getConsumptionReportList().get(m).getCatId()) {
				 									
				 									mrnIssueReportList.get(i).getConsumptionReportList().get(m).setYtd(issueReportList.get(j).getConsumptionReportList().get(k).getMonthlyValue());
				 									break;
				 									
				 								}
				 								
				 							}
				 						}
				 						
				 					}
				 					
				 				}
				 				
				 			} 
				 			System.out.println("mrnReportList " + mrnReportList);
				 			System.out.println("issueReportList " + issueReportList);
				 			System.out.println("mrnIssueReportList " + mrnIssueReportList);*/  
				 			
						  model.addObject("mrnReportList", mrnReportList);
						  model.addObject("issueReportList", issueReportList);
						  /*model.addObject("mrnIssueReportList", mrnIssueReportList);*/
						  
						  model.addObject("fromDate", DateConvertor.convertToYMD(fromDate));
						  model.addObject("toDate", toDate);
				
				Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
				categoryList = new ArrayList<Category>(Arrays.asList(category));

				model.addObject("categoryList", categoryList);
				 
				 
			}
			
			
				catch (Exception e) {
					e.printStackTrace();
				}
			
			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails",
					Company.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/listMrnConsumptionGraph", method = RequestMethod.GET)
	@ResponseBody
	public List<ConsumptionReportWithCatId> listMrnConsumptionGraph(HttpServletRequest request, HttpServletResponse response) {
   
		return mrnReportList;
	}
	
	@RequestMapping(value = "/consumptionMrnReportCategoryWiseExel", method = RequestMethod.GET)
	@ResponseBody
	public List<ConsumptionReportWithCatId> consumptionMrnReportCategoryWiseExel(HttpServletRequest request, HttpServletResponse response) {
 
		try {
			
			//------------------------ Export To Excel--------------------------------------
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();
 
				 
				rowData.add("SR. No");
				rowData.add("Type NAME");
				for (int i = 0; i < categoryList.size(); i++) {
					
					rowData.add(categoryList.get(i).getCatDesc() + " Monthly");
					rowData.add(categoryList.get(i).getCatDesc() + " YTD");
				}
				  
				expoExcel.setRowData(rowData);
			
			exportToExcelList.add(expoExcel);
			int index = 0;
			for (int i = 0; i < mrnReportList.size(); i++) {
				
				
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				index++;
				rowData.add((index)+"");
				rowData.add(mrnReportList.get(i).getTypeName()); 
				for(int j=0;j<mrnReportList.get(i).getConsumptionReportList().size();j++) {
					 
						rowData.add(""+mrnReportList.get(i).getConsumptionReportList().get(j).getMonthlyValue());
						rowData.add(""+mrnReportList.get(i).getConsumptionReportList().get(j).getYtd()); 
				 
				}
			 
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				 
			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "dashbordMrnList");
				  
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mrnReportList;
	}
	
	@RequestMapping(value = "/consumptionMrnReportCategoryWisePdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void consumptionMrnReportCategoryWisePdf(@PathVariable String fromDate,@PathVariable String toDate , HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
		Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f);
		DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
		String reportDate = DF.format(new Date());
        document.addHeader("Date: ", reportDate);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String timeStamp = dateFormat.format(cal.getTime());
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		
		int colmSize=(categoryList.size()*2)+4;
		PdfPTable table = new PdfPTable(colmSize);
		
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			float[] arry = new float[colmSize] ;
			arry[0]=0.4f;
			arry[1]=1.7f;
			
			
			for(int i=2; i < colmSize ;i++) {
				arry[i]=1.0f;
			}
			
			System.out.println("colmSize " + colmSize);
			System.out.println("arry " + Arrays.toString(arry)  + arry.length);
			
			table.setWidths(arry);
			Font headFont = new Font(FontFamily.TIMES_ROMAN,8, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
			Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);
			PdfPCell hcell = new PdfPCell();

			hcell.setPadding(4);
			hcell = new PdfPCell(new Phrase("SR.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Cat Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			
			for (int i = 0; i < categoryList.size(); i++) {
				
				hcell = new PdfPCell(new Phrase(categoryList.get(i).getCatDesc(), headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				hcell.setColspan(2); 
				table.addCell(hcell);
			}
			
			hcell = new PdfPCell(new Phrase("Total", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			hcell.setColspan(2); 
			table.addCell(hcell);
			 
		    hcell = new PdfPCell();
			hcell.setPadding(4);
			hcell = new PdfPCell(new Phrase(" ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(" ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			for (int i = 0; i < categoryList.size(); i++) {
				
				hcell = new PdfPCell(new Phrase("MONTHLY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);
				
				hcell = new PdfPCell(new Phrase("YTD", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);
			}
			
			hcell = new PdfPCell(new Phrase("MONTHLY", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("YTD", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			
 
			
			 
			int index = 0;
			if(!mrnReportList.isEmpty()) {
					for (int k = 0; k < mrnReportList.size(); k++) {
						 
							index++;
						
							float typeMonthlyTotal=0;
							float typeYtdTotal=0;
							
							PdfPCell cell;
							
							cell = new PdfPCell(new Phrase(""+index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

						
							cell = new PdfPCell(new Phrase(mrnReportList.get(k).getTypeName(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							
							for (int j = 0; j < mrnReportList.get(k).getConsumptionReportList().size(); j++) {
								 
									 
											cell = new PdfPCell(new Phrase(""+mrnReportList.get(k).getConsumptionReportList().get(j).getMonthlyValue(), headFont));
											cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
											cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
											cell.setPaddingRight(2);
											cell.setPadding(3);
											table.addCell(cell);
											typeMonthlyTotal=typeMonthlyTotal+mrnReportList.get(k).getConsumptionReportList().get(j).getMonthlyValue();
											
											cell = new PdfPCell(new Phrase(""+mrnReportList.get(k).getConsumptionReportList().get(j).getYtd(), headFont));
											cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
											cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
											cell.setPaddingRight(2);
											cell.setPadding(3);
											table.addCell(cell);
											typeYtdTotal=typeYtdTotal+mrnReportList.get(k).getConsumptionReportList().get(j).getYtd();
								 
							}
							
							cell = new PdfPCell(new Phrase(""+typeMonthlyTotal, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							 
							cell = new PdfPCell(new Phrase(""+typeYtdTotal, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							 
					}
					
					PdfPCell cell;
					cell = new PdfPCell(new Phrase("Total ", headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPadding(3);
					cell.setColspan(2);
					table.addCell(cell);
 
					float finalMonthly=0;
					float finalTotalYear=0;
					
					for (int i = 0; i < categoryList.size(); i++) {
						
						float catTotalMonthly=0;
						float catTotalYear=0;
						
						for (int k = 0; k < mrnReportList.size(); k++) {
							
							for (int j = 0; j < mrnReportList.get(k).getConsumptionReportList().size(); j++) {
								
								if(categoryList.get(i).getCatId()==mrnReportList.get(k).getConsumptionReportList().get(j).getCatId()) {
									catTotalMonthly=catTotalMonthly+mrnReportList.get(k).getConsumptionReportList().get(j).getMonthlyValue();
									catTotalYear=catTotalYear+mrnReportList.get(k).getConsumptionReportList().get(j).getYtd();
								} 
							}
							
						}
						
						cell = new PdfPCell(new Phrase(""+catTotalMonthly, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);
						 
						cell = new PdfPCell(new Phrase(""+catTotalYear, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);
						finalMonthly=finalMonthly+catTotalMonthly;
						finalTotalYear=finalTotalYear+catTotalYear;
						 
					}
					
					cell = new PdfPCell(new Phrase(""+finalMonthly , headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);
					 
					cell = new PdfPCell(new Phrase(""+finalTotalYear , headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);
					
				 
			}
			
			document.open();
			Paragraph company = new Paragraph(companyInfo.getCompanyName()+"\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			
			Paragraph heading1 = new Paragraph(
					companyInfo.getFactoryAdd(),f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2=new Paragraph("\n");
				document.add(ex2);
				
				Paragraph reportName=new Paragraph("MRN VALUEATION REPORT",f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				Paragraph headingDate=new Paragraph(" From Date: "   + DateConvertor.convertToDMY(fromDate)+"  To Date: "+DateConvertor.convertToDMY(toDate)+"",f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
			document.add(headingDate);
			
			  
			Paragraph ex3=new Paragraph("\n");
			document.add(ex3);
			table.setHeaderRows(1);
			document.add(table);
			
		
			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();
			// Atul Sir code to open a Pdf File
			if (file != null) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());

				if (mimeType == null) {

					mimeType = "application/pdf";

				}

				response.setContentType(mimeType);

				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}
			}

		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error" + ex.getMessage());

			ex.printStackTrace();

		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/consumptionMrnReportCategoryWise", method = RequestMethod.GET)
	@ResponseBody
	public List<ConsumptionReportWithCatId> consumptionMrnReportCategoryWise(HttpServletRequest request, HttpServletResponse response) {
 
		try {
			
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
				
			  
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>(); 
		 			map.add("fromDate", fromDate);
		 			map.add("toDate", toDate); 
		 			System.out.println(map);
		 			ConsumptionReportWithCatId[] consumptionReportWithCatId = rest.postForObject(Constants.url + "/getConsumptionMrnData",map, ConsumptionReportWithCatId[].class);
		 			mrnReportList = new ArrayList<ConsumptionReportWithCatId>(Arrays.asList(consumptionReportWithCatId));
				 
				  System.out.println(mrnReportList);
				  
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mrnReportList;
	}
	
	
	@RequestMapping(value = "/consumptionIssueReportCategoryWise", method = RequestMethod.GET)
	@ResponseBody
	public List<ConsumptionReportWithCatId> consumptionIssueReportCategoryWise(HttpServletRequest request, HttpServletResponse response) {

  
		try {
			
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
				
			  
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>(); 
					  map.add("fromDate", fromDate);
		 			map.add("toDate", toDate); 
		 			System.out.println(map);
		 			ConsumptionReportWithCatId[] consumptionReportWithCatId = rest.postForObject(Constants.url + "/getConsumptionIssueData",map, ConsumptionReportWithCatId[].class);
		 			issueReportList = new ArrayList<ConsumptionReportWithCatId>(Arrays.asList(consumptionReportWithCatId));
				 
				  System.out.println(issueReportList);  
	 
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return issueReportList;
	}
	
	@RequestMapping(value = "/mrnIssueMixDashboardReportCategoryWise", method = RequestMethod.GET)
	@ResponseBody
	public List<ConsumptionReportWithCatId> mrnIssueMixDashboardReportCategoryWise(HttpServletRequest request, HttpServletResponse response) {


		 mrnIssueReportList = new ArrayList<ConsumptionReportWithCatId>();
		
		try {
			
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
				
			  
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();  
				 map = new LinkedMultiValueMap<>(); 
	 			map.add("fromDate",  fromDate );
	 			map.add("toDate", toDate); 
	 			System.out.println(map);
	 			ConsumptionReportWithCatId[] consumptionReportWithCatId = rest.postForObject(Constants.url + "/getConsumptionMrnData",map, ConsumptionReportWithCatId[].class);
	 			List<ConsumptionReportWithCatId> mrnReportList = new ArrayList<ConsumptionReportWithCatId>(Arrays.asList(consumptionReportWithCatId));
			  
	 			ConsumptionReportWithCatId[] consumptionReportWithCatId1 = rest.postForObject(Constants.url + "/getConsumptionIssueData",map, ConsumptionReportWithCatId[].class);
	 			List<ConsumptionReportWithCatId>  issueReportList = new ArrayList<ConsumptionReportWithCatId>(Arrays.asList(consumptionReportWithCatId1));
			 
	 			
	 			
	 			for(int i=0; i<mrnReportList.size() ; i++) {
	 				
	 				ConsumptionReportWithCatId obj = new ConsumptionReportWithCatId();
	 				
	 				obj.setTypeId(mrnReportList.get(i).getTypeId());
	 				obj.setTypeName(mrnReportList.get(i).getTypeName());
	 				obj.setConsumptionReportList(mrnReportList.get(i).getConsumptionReportList());
	 				mrnIssueReportList.add(obj);
	 			}
	 			
	 			 for(int i=0; i<mrnIssueReportList.size() ; i++) {
	 				
	 				for(int j=0; j<issueReportList.size() ; j++) {
	 					
	 					if(mrnIssueReportList.get(i).getTypeId()==issueReportList.get(j).getTypeId()) {
	 						
	 						for(int k=0; k<issueReportList.get(j).getConsumptionReportList().size() ; k++) {
	 							
	 							for(int m=0; m<mrnIssueReportList.get(i).getConsumptionReportList().size() ; m++) {
	 								
	 								if(issueReportList.get(j).getConsumptionReportList().get(k).getCatId()==mrnIssueReportList.get(i).getConsumptionReportList().get(m).getCatId()) {
	 									
	 									mrnIssueReportList.get(i).getConsumptionReportList().get(m).setYtd(issueReportList.get(j).getConsumptionReportList().get(k).getMonthlyValue());
	 									break;
	 									
	 								}
	 								
	 							}
	 						}
	 						
	 					}
	 					
	 				}
	 				
	 			} 
				  
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mrnIssueReportList;
	}
	
	@RequestMapping(value = "/listIssueConsumptionGraph", method = RequestMethod.GET)
	@ResponseBody
	public List<ConsumptionReportWithCatId> listIssueConsumptionGraph(HttpServletRequest request, HttpServletResponse response) {
   
		return issueReportList;
	}
	
	@RequestMapping(value = "/consumptionIssueReportCategoryWiseExel", method = RequestMethod.GET)
	@ResponseBody
	public List<ConsumptionReportWithCatId> consumptionIssueReportCategoryWiseExel(HttpServletRequest request, HttpServletResponse response) {


		try {
			
			//------------------------ Export To Excel--------------------------------------
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();
 
				 
				rowData.add("SR. No");
				rowData.add("Type NAME");
				for (int i = 0; i < categoryList.size(); i++) {
					
					rowData.add(categoryList.get(i).getCatDesc() + " Monthly");
					rowData.add(categoryList.get(i).getCatDesc() + " YTD");
				}
				  
				expoExcel.setRowData(rowData);
			
			exportToExcelList.add(expoExcel);
			int index = 0;
			for (int i = 0; i < issueReportList.size(); i++) {
				
				
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				index++;
				rowData.add((index)+"");
				rowData.add(issueReportList.get(i).getTypeName()); 
				for(int j=0;j<issueReportList.get(i).getConsumptionReportList().size();j++) {
					 
						rowData.add(""+issueReportList.get(i).getConsumptionReportList().get(j).getMonthlyValue());
						rowData.add(""+issueReportList.get(i).getConsumptionReportList().get(j).getMonthlyValue()); 
				 
				}
			 
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				 
			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "dashbordIssueList");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return issueReportList;
	}
	
	@RequestMapping(value = "/mrnIssueReportDashboardCategoryWiseExel", method = RequestMethod.GET)
	@ResponseBody
	public List<ConsumptionReportWithCatId> mrnIssueReportDashboardCategoryWiseExel(HttpServletRequest request, HttpServletResponse response) {


		try {
			
			//------------------------ Export To Excel--------------------------------------
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();
 
				 
				rowData.add("SR. No");
				rowData.add("Type NAME");
				for (int i = 0; i < categoryList.size(); i++) {
					
					rowData.add(categoryList.get(i).getCatDesc() + " MRN");
					rowData.add(categoryList.get(i).getCatDesc() + " ISSUE");
				}
				  
				expoExcel.setRowData(rowData);
			
			exportToExcelList.add(expoExcel);
			int index = 0;
			for (int i = 0; i < mrnIssueReportList.size(); i++) {
				
				
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				index++;
				rowData.add((index)+"");
				rowData.add(mrnIssueReportList.get(i).getTypeName()); 
				for(int j=0;j<mrnIssueReportList.get(i).getConsumptionReportList().size();j++) {
					 
						rowData.add(""+mrnIssueReportList.get(i).getConsumptionReportList().get(j).getMonthlyValue());
						rowData.add(""+mrnIssueReportList.get(i).getConsumptionReportList().get(j).getYtd()); 
				 
				}
			 
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				 
			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "dashbordMrnIssueList");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return issueReportList;
	}
	
	@RequestMapping(value = "/consumptionIssueReportCategoryWisePdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void consumptionIssueReportCategoryWisePdf(@PathVariable String fromDate,@PathVariable String toDate , HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
		Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f);
		DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
		String reportDate = DF.format(new Date());
        document.addHeader("Date: ", reportDate);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String timeStamp = dateFormat.format(cal.getTime());
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		
		int colmSize=(categoryList.size()*2)+4;
		PdfPTable table = new PdfPTable(colmSize);
		
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			float[] arry = new float[colmSize] ;
			arry[0]=0.4f;
			arry[1]=1.7f;
			
			
			for(int i=2; i < colmSize ;i++) {
				arry[i]=1.0f;
			}
			
			System.out.println("colmSize " + colmSize);
			System.out.println("arry " + Arrays.toString(arry)  + arry.length);
			
			table.setWidths(arry);
			Font headFont = new Font(FontFamily.TIMES_ROMAN,8, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
			Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);
			PdfPCell hcell = new PdfPCell();

			hcell.setPadding(4);
			hcell = new PdfPCell(new Phrase("SR.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Cat Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			
			for (int i = 0; i < categoryList.size(); i++) {
				
				hcell = new PdfPCell(new Phrase(categoryList.get(i).getCatDesc(), headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				hcell.setColspan(2); 
				table.addCell(hcell);
			}
			
			hcell = new PdfPCell(new Phrase("Total", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			hcell.setColspan(2); 
			table.addCell(hcell);
			 
		    hcell = new PdfPCell();
			hcell.setPadding(4);
			hcell = new PdfPCell(new Phrase(" ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(" ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			for (int i = 0; i < categoryList.size(); i++) {
				
				hcell = new PdfPCell(new Phrase("MONTHLY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);
				
				hcell = new PdfPCell(new Phrase("YTD", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);
			}
			
			hcell = new PdfPCell(new Phrase("MONTHLY", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("YTD", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			 
			
			 
			int index = 0;
			if(!issueReportList.isEmpty()) {
					for (int k = 0; k < issueReportList.size(); k++) {
						 
							index++;
							float typeMonthlyTotal=0;
							float typeYtdTotal=0;
							PdfPCell cell;
							
							cell = new PdfPCell(new Phrase(""+index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

						
							cell = new PdfPCell(new Phrase(issueReportList.get(k).getTypeName(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							
							for (int j = 0; j < issueReportList.get(k).getConsumptionReportList().size(); j++) {
								 
									 
											cell = new PdfPCell(new Phrase(""+issueReportList.get(k).getConsumptionReportList().get(j).getMonthlyValue(), headFont));
											cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
											cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
											cell.setPaddingRight(2);
											cell.setPadding(3);
											table.addCell(cell);
											typeMonthlyTotal=typeMonthlyTotal+issueReportList.get(k).getConsumptionReportList().get(j).getMonthlyValue();
											
											cell = new PdfPCell(new Phrase(""+issueReportList.get(k).getConsumptionReportList().get(j).getYtd(), headFont));
											cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
											cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
											cell.setPaddingRight(2);
											cell.setPadding(3);
											table.addCell(cell);
											typeYtdTotal=typeYtdTotal+issueReportList.get(k).getConsumptionReportList().get(j).getYtd();
								 
							}
							
							cell = new PdfPCell(new Phrase(""+typeMonthlyTotal, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							 
							
							cell = new PdfPCell(new Phrase(""+typeYtdTotal, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							 
					
					}
					
					PdfPCell cell;
					cell = new PdfPCell(new Phrase("Total ", headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPadding(3);
					cell.setColspan(2);
					table.addCell(cell);
 
					float finalMonthly=0;
					float finalTotalYear=0;
					
					for (int i = 0; i < categoryList.size(); i++) {
						
						float catTotalMonthly=0;
						float catTotalYear=0;
						
						for (int k = 0; k < issueReportList.size(); k++) {
							
							for (int j = 0; j < issueReportList.get(k).getConsumptionReportList().size(); j++) {
								
								if(categoryList.get(i).getCatId()==issueReportList.get(k).getConsumptionReportList().get(j).getCatId()) {
									catTotalMonthly=catTotalMonthly+issueReportList.get(k).getConsumptionReportList().get(j).getMonthlyValue();
									catTotalYear=catTotalYear+issueReportList.get(k).getConsumptionReportList().get(j).getYtd();
								} 
							}
							
						}
						
						cell = new PdfPCell(new Phrase(""+catTotalMonthly, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);
						 
						cell = new PdfPCell(new Phrase(""+catTotalYear, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);
						finalMonthly=finalMonthly+catTotalMonthly;
						finalTotalYear=finalTotalYear+catTotalYear;
						 
					}
					
					cell = new PdfPCell(new Phrase(""+finalMonthly , headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);
					 
					cell = new PdfPCell(new Phrase(""+finalTotalYear , headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);
				 
			}
			
			document.open();
			Paragraph company = new Paragraph(companyInfo.getCompanyName()+"\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			
			Paragraph heading1 = new Paragraph(
					companyInfo.getFactoryAdd(),f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2=new Paragraph("\n");
				document.add(ex2);
				
				Paragraph reportName=new Paragraph("ISSUE VALUEATION REPORT",f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				Paragraph headingDate=new Paragraph(" From Date: "   + DateConvertor.convertToDMY(fromDate)+"  To Date: "+DateConvertor.convertToDMY(toDate)+"",f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
			document.add(headingDate);
			
			  
			Paragraph ex3=new Paragraph("\n");
			document.add(ex3);
			table.setHeaderRows(1);
			document.add(table);
			
		
			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();
			// Atul Sir code to open a Pdf File
			if (file != null) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());

				if (mimeType == null) {

					mimeType = "application/pdf";

				}

				response.setContentType(mimeType);

				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}
			}

		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error" + ex.getMessage());

			ex.printStackTrace();

		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/mrnIssueDashboardReportCategoryWisePdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void mrnIssueDashboardReportCategoryWisePdf(@PathVariable String fromDate,@PathVariable String toDate , HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
		Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f);
		DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
		String reportDate = DF.format(new Date());
        document.addHeader("Date: ", reportDate);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String timeStamp = dateFormat.format(cal.getTime());
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		
		int colmSize=(categoryList.size()*2)+4;
		PdfPTable table = new PdfPTable(colmSize);
		
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			float[] arry = new float[colmSize] ;
			arry[0]=0.4f;
			arry[1]=1.7f;
			
			
			for(int i=2; i < colmSize ;i++) {
				arry[i]=1.0f;
			}
			
			System.out.println("colmSize " + colmSize);
			System.out.println("arry " + Arrays.toString(arry)  + arry.length);
			
			table.setWidths(arry);
			Font headFont = new Font(FontFamily.TIMES_ROMAN,8, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
			Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);
			PdfPCell hcell = new PdfPCell();

			hcell.setPadding(4);
			hcell = new PdfPCell(new Phrase("SR.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Cat Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			
			for (int i = 0; i < categoryList.size(); i++) {
				
				hcell = new PdfPCell(new Phrase(categoryList.get(i).getCatDesc(), headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				hcell.setColspan(2); 
				table.addCell(hcell);
			}
			
			hcell = new PdfPCell(new Phrase("Total", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			hcell.setColspan(2); 
			table.addCell(hcell);
			 
		    hcell = new PdfPCell();
			hcell.setPadding(4);
			hcell = new PdfPCell(new Phrase(" ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(" ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			for (int i = 0; i < categoryList.size(); i++) {
				
				hcell = new PdfPCell(new Phrase("MRN", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);
				
				hcell = new PdfPCell(new Phrase("ISSUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);
			}
			
			hcell = new PdfPCell(new Phrase("MRN", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("ISSUE", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			 
			
			 
			int index = 0;
			if(!mrnIssueReportList.isEmpty()) {
					for (int k = 0; k < mrnIssueReportList.size(); k++) {
						 
							index++;
							float typeMonthlyTotal=0;
							float typeYtdTotal=0;
							PdfPCell cell;
							
							cell = new PdfPCell(new Phrase(""+index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

						
							cell = new PdfPCell(new Phrase(mrnIssueReportList.get(k).getTypeName(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							
							for (int j = 0; j < mrnIssueReportList.get(k).getConsumptionReportList().size(); j++) {
								 
									 
											cell = new PdfPCell(new Phrase(""+mrnIssueReportList.get(k).getConsumptionReportList().get(j).getMonthlyValue(), headFont));
											cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
											cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
											cell.setPaddingRight(2);
											cell.setPadding(3);
											table.addCell(cell);
											typeMonthlyTotal=typeMonthlyTotal+mrnIssueReportList.get(k).getConsumptionReportList().get(j).getMonthlyValue();
											
											cell = new PdfPCell(new Phrase(""+mrnIssueReportList.get(k).getConsumptionReportList().get(j).getYtd(), headFont));
											cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
											cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
											cell.setPaddingRight(2);
											cell.setPadding(3);
											table.addCell(cell);
											typeYtdTotal=typeYtdTotal+mrnIssueReportList.get(k).getConsumptionReportList().get(j).getYtd();
								 
							}
							
							cell = new PdfPCell(new Phrase(""+typeMonthlyTotal, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							 
							
							cell = new PdfPCell(new Phrase(""+typeYtdTotal, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							 
					
					}
					
					PdfPCell cell;
					cell = new PdfPCell(new Phrase("Total ", headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPadding(3);
					cell.setColspan(2);
					table.addCell(cell);
 
					float finalMonthly=0;
					float finalTotalYear=0;
					
					for (int i = 0; i < categoryList.size(); i++) {
						
						float catTotalMonthly=0;
						float catTotalYear=0;
						
						for (int k = 0; k < mrnIssueReportList.size(); k++) {
							
							for (int j = 0; j < mrnIssueReportList.get(k).getConsumptionReportList().size(); j++) {
								
								if(categoryList.get(i).getCatId()==mrnIssueReportList.get(k).getConsumptionReportList().get(j).getCatId()) {
									catTotalMonthly=catTotalMonthly+mrnIssueReportList.get(k).getConsumptionReportList().get(j).getMonthlyValue();
									catTotalYear=catTotalYear+mrnIssueReportList.get(k).getConsumptionReportList().get(j).getYtd();
								} 
							}
							
						}
						
						cell = new PdfPCell(new Phrase(""+catTotalMonthly, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);
						 
						cell = new PdfPCell(new Phrase(""+catTotalYear, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);
						finalMonthly=finalMonthly+catTotalMonthly;
						finalTotalYear=finalTotalYear+catTotalYear;
						 
					}
					
					cell = new PdfPCell(new Phrase(""+finalMonthly , headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);
					 
					cell = new PdfPCell(new Phrase(""+finalTotalYear , headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);
				 
			}
			
			document.open();
			Paragraph company = new Paragraph(companyInfo.getCompanyName()+"\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			
			Paragraph heading1 = new Paragraph(
					companyInfo.getFactoryAdd(),f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2=new Paragraph("\n");
				document.add(ex2);
				
				Paragraph reportName=new Paragraph("MRN/ISSUE VALUEATION REPORT",f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				Paragraph headingDate=new Paragraph(" From Date: "   + DateConvertor.convertToDMY(fromDate)+"  To Date: "+DateConvertor.convertToDMY(toDate)+"",f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
			document.add(headingDate);
			
			  
			Paragraph ex3=new Paragraph("\n");
			document.add(ex3);
			table.setHeaderRows(1);
			document.add(table);
			
		
			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();
			// Atul Sir code to open a Pdf File
			if (file != null) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());

				if (mimeType == null) {

					mimeType = "application/pdf";

				}

				response.setContentType(mimeType);

				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}
			}

		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error" + ex.getMessage());

			ex.printStackTrace();

		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
            
            Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	@RequestMapping(value = "/getMrnInspForVendor", method = RequestMethod.GET)
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
				map.add("status","1,2,0,3,4");
			}else {
				
				map.add("status",status);

			}
			 map.add("venId",vendorId);
			List<GetMrnHeader> getMrnHeaderList=rest.postForObject(Constants.url+"getMrnHeaderList", map,  List.class);
			System.err.println("Mrn Header List  " +getMrnHeaderList);
			
			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);
			
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
            
            Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);
            
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
			String remark2 = request.getParameter("remark2") ;
			String[] checkbox=request.getParameterValues("select_to_approve");
			getMrnHeader.setRemark2(remark2);
			
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
			
			String remark = request.getParameter("inspRemark");
			
             if(!getMrnDetailList.isEmpty()) {
            	 for(int i=0;i<getMrnDetailList.size();i++)
            	 {

            		 getMrnDetailList.get(i).setMrnDetailStatus(1);
            	 }
			getMrnHeader.setGetMrnDetailList(getMrnDetailList);
             }
             System.out.println(" final getMrnHeader -------- " + getMrnHeader);
			 List<MrnDetail> mrnDetailList = restTemp.postForObject(Constants.url + "/saveMrnData", getMrnDetailList, List.class);
			 
			 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			 map.add("mrnId", getMrnHeader.getMrnId());
			 map.add("remark", remark); 
			 
			 ErrorMessage errorMessage = rest.postForObject(Constants.url + "/updateInspectionRemark",
						map, ErrorMessage.class);

			System.err.println("mrnDetailList " + mrnDetailList.toString()); 
			 HttpSession session = request.getSession();
				User user = (User) session.getAttribute("userInfo");
				 map = new LinkedMultiValueMap<String, Object>();
				 map.add("docId", 3);
				 map.add("docTranId", getMrnHeader.getMrnId());
				 map.add("userId", user.getId());
				 
				 ErrorMessage resp = rest.postForObject(Constants.url + "/updateInspDateAndTime",
							map, ErrorMessage.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showMrnForInspection";
	}
}
