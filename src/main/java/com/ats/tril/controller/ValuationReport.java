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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.formula.functions.Subtotal;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
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
import com.ats.tril.model.ConsumptionReportWithCatId;
import com.ats.tril.model.Dept;
import com.ats.tril.model.DeptSubDeptValReport;
import com.ats.tril.model.ExportToExcel;
import com.ats.tril.model.GetCurrentStock;
import com.ats.tril.model.GetEnquiryHeader;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.GetSubDept;
import com.ats.tril.model.IndentStatusPendingReport;
import com.ats.tril.model.IndentStatusReport;
import com.ats.tril.model.IssueAndMrnGroupWise;
import com.ats.tril.model.IssueAndMrnItemWise;
import com.ats.tril.model.IssueDeptWise;
import com.ats.tril.model.IssueMonthWiseList;
import com.ats.tril.model.IssueReportItemwise;
import com.ats.tril.model.ItemValuationList;
import com.ats.tril.model.MonthCategoryWiseMrnReport;
import com.ats.tril.model.MonthItemWiseMrnReport;
import com.ats.tril.model.MonthSubDeptWiseIssueReport;
import com.ats.tril.model.MonthWiseIssueReport;
import com.ats.tril.model.MrnMonthWiseList;
import com.ats.tril.model.SettingValue;
import com.ats.tril.model.StockValuationCategoryWise;
import com.ats.tril.model.Type;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.doc.DocumentBean;
import com.ats.trl.model.report.IndentRepItemwise;
import com.ats.trl.model.report.ItemEnqAgQuotReport;
import com.ats.trl.model.report.MrnCatwiseReport;
import com.ats.trl.model.report.QuotReport;
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
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

@Controller
@Scope("session")
public class ValuationReport {

	RestTemplate rest = new RestTemplate();
	String fromDate;
	String toDate;
	int typeId;
	int isDev;
	int year;
	int deptId;
	int subDeptId;
	int catId;
	Company companyInfo = new Company();
	DecimalFormat df = new DecimalFormat("####0.00");

	List<IssueMonthWiseList> listGlobal;
	List<Dept> deparmentList;

	@RequestMapping(value = "/stockBetweenDateWithCatId", method = RequestMethod.GET)
	public ModelAndView itemValueationReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/stockBetweenDateWithCatId");
		try {

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));

			model.addObject("categoryList", categoryList);
			List<GetCurrentStock> getStockBetweenDate = new ArrayList<>();

			if (request.getParameter("fromDate") == null || request.getParameter("toDate") == null
					|| request.getParameter("catId") == null) {

			} else {

				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");
				catId = Integer.parseInt(request.getParameter("catId"));

				SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

				Date date = dd.parse(fromDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				String firstDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);

				System.out.println(DateConvertor.convertToYMD(firstDate) + DateConvertor.convertToYMD(fromDate));

				if (DateConvertor.convertToYMD(firstDate).compareTo(DateConvertor.convertToYMD(fromDate)) < 0) {
					calendar.add(Calendar.DATE, -1);
					String previousDate = yy.format(new Date(calendar.getTimeInMillis()));
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("fromDate", DateConvertor.convertToYMD(firstDate));
					map.add("toDate", previousDate);
					map.add("catId", catId);
					System.out.println(map);
					GetCurrentStock[] getCurrentStock = rest.postForObject(
							Constants.url + "/getStockBetweenDateWithCatId", map, GetCurrentStock[].class);
					List<GetCurrentStock> diffDateStock = new ArrayList<>(Arrays.asList(getCurrentStock));

					calendar.add(Calendar.DATE, 1);
					String addDay = yy.format(new Date(calendar.getTimeInMillis()));
					map = new LinkedMultiValueMap<>();
					map.add("fromDate", addDay);
					map.add("toDate", DateConvertor.convertToYMD(toDate));
					map.add("catId", catId);
					System.out.println(map);
					GetCurrentStock[] getCurrentStock1 = rest.postForObject(
							Constants.url + "/getStockBetweenDateWithCatId", map, GetCurrentStock[].class);
					getStockBetweenDate = new ArrayList<>(Arrays.asList(getCurrentStock1));

					for (int i = 0; i < getStockBetweenDate.size(); i++) {
						for (int j = 0; j < diffDateStock.size(); j++) {
							if (getStockBetweenDate.get(i).getItemId() == diffDateStock.get(j).getItemId()) {
								getStockBetweenDate.get(i).setOpeningStock(diffDateStock.get(j).getOpeningStock()
										+ diffDateStock.get(j).getApproveQty() - diffDateStock.get(j).getIssueQty()
										+ diffDateStock.get(j).getReturnIssueQty() - diffDateStock.get(j).getDamageQty()
										- diffDateStock.get(j).getGatepassQty()
										+ diffDateStock.get(j).getGatepassReturnQty());
								getStockBetweenDate.get(i)
										.setOpStockValue(diffDateStock.get(j).getOpStockValue()
												+ diffDateStock.get(j).getApprovedQtyValue()
												- diffDateStock.get(j).getIssueQtyValue()
												- diffDateStock.get(j).getDamagValue());

								break;
							}
						}
					}
				} else {
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("fromDate", DateConvertor.convertToYMD(fromDate));
					map.add("toDate", DateConvertor.convertToYMD(toDate));
					map.add("catId", catId);
					System.out.println(map);
					GetCurrentStock[] getCurrentStock = rest.postForObject(
							Constants.url + "/getStockBetweenDateWithCatId", map, GetCurrentStock[].class);
					getStockBetweenDate = new ArrayList<>(Arrays.asList(getCurrentStock));
				}
				model.addObject("list", getStockBetweenDate);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
				model.addObject("catId", catId);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	List<IndentStatusPendingReport> indentStatusPandingReportListPdf = new ArrayList<IndentStatusPendingReport>();

 	@RequestMapping(value = "/indentStatusPandingReport", method = RequestMethod.GET)
	public ModelAndView indentStatusPandingReport(HttpServletRequest request, HttpServletResponse response) {

 		ModelAndView model = new ModelAndView("valuationReport/indentStatusPandingReport");
		try {

 			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);

 			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category)); 
			model.addObject("categoryList", categoryList);

 			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails",
					Company.class);

 			if(request.getParameter("fromDate")==null || request.getParameter("toDate")==null) {

 			}
			else {

 				 fromDate = request.getParameter("fromDate");
				 toDate = request.getParameter("toDate");
				 int typeId = Integer.parseInt(request.getParameter("typeId"));
				 int catId = Integer.parseInt(request.getParameter("catId"));
				 int sts = Integer.parseInt(request.getParameter("sts"));

 				 MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("fromDate",DateConvertor.convertToYMD(fromDate));
		 			map.add("toDate",DateConvertor.convertToYMD(toDate));  
		 			map.add("typeId",typeId); 
		 			map.add("catId",catId);
		 			if(sts==0) {
		 				map.add("detailStatus","0,1");
		 				map.add("headerStatus","0,1");
		 			}else {
		 				map.add("detailStatus","7,9");
		 				map.add("headerStatus","9,8,7,6");
		 			}

 		 			IndentStatusPendingReport[] indentStatusReport = rest.postForObject(Constants.url + "/indentStatusPandingReport",map, IndentStatusPendingReport[].class);
					List<IndentStatusPendingReport> list = new ArrayList<IndentStatusPendingReport>(Arrays.asList(indentStatusReport));

 					model.addObject("indentStatusReport", list);
					model.addObject("fromDate", fromDate);
					model.addObject("toDate", toDate);
					model.addObject("typeId", typeId);
					model.addObject("catId", catId);
					model.addObject("sts", sts);
					indentStatusPandingReportListPdf=list;
			}


 		} catch (Exception e) {
			e.printStackTrace();
		}

 		return model;
	}

 	@RequestMapping(value = "/indentStatusPendingReportPDF", method = RequestMethod.GET)
	public void indentStatusPendingReportPDF(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		
		Document document = new Document(PageSize.A4);
		document.setMargins(5f, 5f, 2f, 2f);
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

 		PdfPTable table = new PdfPTable(10);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] {1.0f, 1.7f, 1.7f, 5.0f,1.7f,1.0f,1.7f,1.7f,1.7f,1.7f});
			Font headFont = new Font(FontFamily.TIMES_ROMAN,8, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
			Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.GRAY);

 			PdfPCell hcell = new PdfPCell();

 			hcell.setPadding(4);
			hcell = new PdfPCell(new Phrase("SR", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

 			hcell = new PdfPCell(new Phrase("INDENT NO", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

 			hcell = new PdfPCell(new Phrase("INDENT DATE", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

 			hcell = new PdfPCell(new Phrase("ITEM DESC", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

 			hcell = new PdfPCell(new Phrase("SCH DATE", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

 			hcell = new PdfPCell(new Phrase("DAYS", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

 			hcell = new PdfPCell(new Phrase("INDENT QTY", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

 			hcell = new PdfPCell(new Phrase("BAL QTY", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

 			hcell = new PdfPCell(new Phrase("RATE", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

 			hcell = new PdfPCell(new Phrase("TOTAL", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);


 			int index = 0;
			float total = 0;
			if(!indentStatusPandingReportListPdf.isEmpty()) {
					for (int k = 0; k < indentStatusPandingReportListPdf.size(); k++) {

 							index++;

 							PdfPCell cell;

 							cell = new PdfPCell(new Phrase(""+index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);


 							cell = new PdfPCell(new Phrase(indentStatusPandingReportListPdf.get(k).getIndMNo(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

 							cell = new PdfPCell(new Phrase(""+indentStatusPandingReportListPdf.get(k).getIndMDate(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

 							cell = new PdfPCell(new Phrase(""+indentStatusPandingReportListPdf.get(k).getItemCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell); 

 							cell = new PdfPCell(new Phrase(""+indentStatusPandingReportListPdf.get(k).getIndItemSchddt(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

 							cell = new PdfPCell(new Phrase(""+indentStatusPandingReportListPdf.get(k).getExcessDays(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

 							cell = new PdfPCell(new Phrase(""+indentStatusPandingReportListPdf.get(k).getIndQty(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

 							cell = new PdfPCell(new Phrase(""+indentStatusPandingReportListPdf.get(k).getBalQty(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

 							cell = new PdfPCell(new Phrase(""+indentStatusPandingReportListPdf.get(k).getRate(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

 							cell = new PdfPCell(new Phrase(""+df.format(indentStatusPandingReportListPdf.get(k).getRate()*indentStatusPandingReportListPdf.get(k).getBalQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

 							total=total+(indentStatusPandingReportListPdf.get(k).getRate()*indentStatusPandingReportListPdf.get(k).getBalQty());

 					}
			}

 			PdfPCell cell;

 			cell = new PdfPCell(new Phrase("Total", headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(3);
			cell.setColspan(9);
			table.addCell(cell);

 			cell = new PdfPCell(new Phrase(""+df.format(total), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

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

 				Paragraph headingDate=new Paragraph("Indent Status Pending Report, From Date: " + fromDate+"  To Date: "+toDate+"",f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);

 			document.add(headingDate);

 			Paragraph ex3=new Paragraph("\n");
			document.add(ex3);
			table.setHeaderRows(1);
			document.add(table);


 			int totalPages = writer.getPageNumber();				int totalPages1 = writer.getPageNumber();


 			System.out.println("Page no " + totalPages1);				System.out.println("Page no " + totalPages);
 			
 			
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
	 	
	@RequestMapping(value = "/getStockBetweenDateWithCatId", method = RequestMethod.GET)
	@ResponseBody
	public List<GetCurrentStock> getStockBetweenDateWithCatId(HttpServletRequest request,
			HttpServletResponse response) {

		List<GetCurrentStock> getStockBetweenDate = new ArrayList<>();

		try {

			fromDate = request.getParameter("fromDate");
			toDate = request.getParameter("toDate");
			catId = Integer.parseInt(request.getParameter("catId"));

			SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

			Date date = dd.parse(fromDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			String firstDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);

			System.out.println(DateConvertor.convertToYMD(firstDate) + DateConvertor.convertToYMD(fromDate));

			if (DateConvertor.convertToYMD(firstDate).compareTo(DateConvertor.convertToYMD(fromDate)) < 0) {
				calendar.add(Calendar.DATE, -1);
				String previousDate = yy.format(new Date(calendar.getTimeInMillis()));
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(firstDate));
				map.add("toDate", previousDate);
				map.add("catId", catId);
				System.out.println(map);
				GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatId",
						map, GetCurrentStock[].class);
				List<GetCurrentStock> diffDateStock = new ArrayList<>(Arrays.asList(getCurrentStock));

				calendar.add(Calendar.DATE, 1);
				String addDay = yy.format(new Date(calendar.getTimeInMillis()));
				map = new LinkedMultiValueMap<>();
				map.add("fromDate", addDay);
				map.add("toDate", DateConvertor.convertToYMD(toDate));
				map.add("catId", catId);
				System.out.println(map);
				GetCurrentStock[] getCurrentStock1 = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatId",
						map, GetCurrentStock[].class);
				getStockBetweenDate = new ArrayList<>(Arrays.asList(getCurrentStock1));

				for (int i = 0; i < getStockBetweenDate.size(); i++) {
					for (int j = 0; j < diffDateStock.size(); j++) {
						if (getStockBetweenDate.get(i).getItemId() == diffDateStock.get(j).getItemId()) {
							getStockBetweenDate.get(i).setOpeningStock(diffDateStock.get(j).getOpeningStock()
									+ diffDateStock.get(j).getApproveQty() - diffDateStock.get(j).getIssueQty()
									+ diffDateStock.get(j).getReturnIssueQty() - diffDateStock.get(j).getDamageQty()
									- diffDateStock.get(j).getGatepassQty()
									+ diffDateStock.get(j).getGatepassReturnQty());
							getStockBetweenDate.get(i).setOpStockValue(diffDateStock.get(j).getOpStockValue()
									+ diffDateStock.get(j).getApprovedQtyValue()
									- diffDateStock.get(j).getIssueQtyValue() - diffDateStock.get(j).getDamagValue());

							break;
						}
					}
				}
			} else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));
				map.add("catId", catId);
				System.out.println(map);
				GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatId",
						map, GetCurrentStock[].class);
				getStockBetweenDate = new ArrayList<>(Arrays.asList(getCurrentStock));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return getStockBetweenDate;
	}

	@RequestMapping(value = "/valueationReportDetail/{itemId}", method = RequestMethod.GET)
	public ModelAndView valueationReportDetail(@PathVariable int itemId, 
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/valueationReportDetail");
		try {
			
			String openingStock = request.getParameter("stock"); 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("itemId", itemId);
			GetItem item = rest.postForObject(Constants.url + "/getItemByItemId", map, GetItem.class);
			model.addObject("item", item);

			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			// map.add("catId", catId);
			ItemValuationList[] itemValuation = rest.postForObject(Constants.url + "/valueationReportDetail", map,
					ItemValuationList[].class);
			List<ItemValuationList> itemValuationList = new ArrayList<ItemValuationList>(Arrays.asList(itemValuation));

			System.out.println(openingStock);
			model.addObject("itemValuationList", itemValuationList);
			model.addObject("openingStock", openingStock);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	List<StockValuationCategoryWise> stockCategoryWiseListForPdf = new ArrayList<StockValuationCategoryWise>();

	@RequestMapping(value = "/stockValueationReportCategoryWise", method = RequestMethod.GET)
	public ModelAndView stockValueationReportCategoryWise(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/stockValueationReportCategoryWise");
		try {
			List<StockValuationCategoryWise> categoryWiseReport = new ArrayList<StockValuationCategoryWise>();
			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);

			if (request.getParameter("fromDate") == null || request.getParameter("toDate") == null
					|| request.getParameter("typeId") == null) {

				SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				fromDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
				toDate = dd.format(date);
				typeId = 0;
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", yy.format(date));
				map.add("typeId", typeId);
				StockValuationCategoryWise[] stockValuationCategoryWise = rest.postForObject(
						Constants.url + "/stockValueationReport", map, StockValuationCategoryWise[].class);
				categoryWiseReport = new ArrayList<StockValuationCategoryWise>(
						Arrays.asList(stockValuationCategoryWise));

				model.addObject("categoryWiseReport", categoryWiseReport);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", dd.format(date));
				stockCategoryWiseListForPdf = categoryWiseReport;
			} else {
				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");
				typeId = Integer.parseInt(request.getParameter("typeId"));

				SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

				Date date = dd.parse(fromDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				String firstDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);

				System.out.println(DateConvertor.convertToYMD(firstDate) + DateConvertor.convertToYMD(fromDate));

				if (DateConvertor.convertToYMD(firstDate).compareTo(DateConvertor.convertToYMD(fromDate)) < 0) {
					calendar.add(Calendar.DATE, -1);
					String previousDate = yy.format(new Date(calendar.getTimeInMillis()));
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("fromDate", DateConvertor.convertToYMD(firstDate));
					map.add("toDate", previousDate);
					map.add("typeId", typeId);
					StockValuationCategoryWise[] stockValuationCategoryWise = rest.postForObject(
							Constants.url + "/stockValueationReport", map, StockValuationCategoryWise[].class);
					List<StockValuationCategoryWise> diffDateStock = new ArrayList<StockValuationCategoryWise>(
							Arrays.asList(stockValuationCategoryWise));

					calendar.add(Calendar.DATE, 1);
					String addDay = yy.format(new Date(calendar.getTimeInMillis()));
					map = new LinkedMultiValueMap<>();
					map.add("fromDate", addDay);
					map.add("toDate", DateConvertor.convertToYMD(toDate));
					map.add("typeId", typeId);
					System.out.println(map);
					StockValuationCategoryWise[] stockValuationCategoryWise1 = rest.postForObject(
							Constants.url + "/stockValueationReport", map, StockValuationCategoryWise[].class);
					categoryWiseReport = new ArrayList<StockValuationCategoryWise>(
							Arrays.asList(stockValuationCategoryWise1));

					for (int i = 0; i < categoryWiseReport.size(); i++) {
						for (int j = 0; j < diffDateStock.size(); j++) {
							if (categoryWiseReport.get(i).getCatId() == diffDateStock.get(j).getCatId()) {
								categoryWiseReport.get(i).setOpeningStock(diffDateStock.get(j).getOpeningStock()
										+ diffDateStock.get(j).getApproveQty() - diffDateStock.get(j).getIssueQty()
										- diffDateStock.get(j).getDamageQty());
								categoryWiseReport.get(i)
										.setOpStockValue(diffDateStock.get(j).getOpStockValue()
												+ diffDateStock.get(j).getApprovedQtyValue()
												- diffDateStock.get(j).getIssueQtyValue()
												- diffDateStock.get(j).getDamageValue());

								break;
							}
						}
					}
				} else {
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("fromDate", DateConvertor.convertToYMD(fromDate));
					map.add("toDate", DateConvertor.convertToYMD(toDate));
					map.add("typeId", typeId);
					System.out.println(map);
					StockValuationCategoryWise[] stockValuationCategoryWise1 = rest.postForObject(
							Constants.url + "/stockValueationReport", map, StockValuationCategoryWise[].class);
					categoryWiseReport = new ArrayList<StockValuationCategoryWise>(
							Arrays.asList(stockValuationCategoryWise1));
				}

				model.addObject("categoryWiseReport", categoryWiseReport);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
				model.addObject("typeId", typeId);
				stockCategoryWiseListForPdf = categoryWiseReport;

			}

			// ----------------exel-------------------------

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("CATEGORY");
			rowData.add("OP QTY");
			rowData.add("OP VALUE");
			rowData.add("MRN QTY");
			rowData.add("MRN VALUE");
			rowData.add("ISSUE QTY");
			rowData.add("ISSUE VALUE");
			rowData.add("DAMAGE QTY");
			rowData.add("DAMAGE VALUE");
			rowData.add("C\\L QTY");
			rowData.add("C\\L VALUE");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < categoryWiseReport.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");
				rowData.add(categoryWiseReport.get(i).getCatDesc());
				rowData.add("" + categoryWiseReport.get(i).getOpeningStock());
				rowData.add("" + categoryWiseReport.get(i).getOpStockValue());
				rowData.add("" + categoryWiseReport.get(i).getApproveQty());
				rowData.add("" + categoryWiseReport.get(i).getApprovedQtyValue());
				rowData.add("" + categoryWiseReport.get(i).getIssueQty());
				rowData.add("" + categoryWiseReport.get(i).getIssueQtyValue());
				rowData.add("" + categoryWiseReport.get(i).getDamageQty());
				rowData.add("" + categoryWiseReport.get(i).getDamageValue());

				float closingQty = categoryWiseReport.get(i).getOpeningStock()
						+ categoryWiseReport.get(i).getApproveQty() - categoryWiseReport.get(i).getIssueQty()
						- categoryWiseReport.get(i).getDamageQty();

				float closingValue = categoryWiseReport.get(i).getOpStockValue()
						+ categoryWiseReport.get(i).getApprovedQtyValue() - categoryWiseReport.get(i).getIssueQtyValue()
						- categoryWiseReport.get(i).getDamageValue();

				rowData.add("" + closingQty);
				rowData.add("" + closingValue);

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "CategoryWiseStockValue");

			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/stockValuetionReportCategoryWisePDF/{type}", method = RequestMethod.GET)
	public void stockValuetionReportCategoryWisePDF(@PathVariable String type, HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A4);
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

			PdfPTable table = new PdfPTable(12);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 1.7f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("CATEGORY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("OP QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("OP VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MRN QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MRN VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("DAMAGE QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("DAMAGE VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("C/L QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("C/L VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				float totalOpQty = 0;
				float totalOpValue = 0;
				float totalMrnQty = 0;
				float totalMrnValue = 0;
				float totalIssueQty = 0;
				float totalIssueValue = 0;
				float totalDamageQty = 0;
				float totalDamageValue = 0;
				float totalClQty = 0;
				float totalClValue = 0;

				int index = 0;
				if (!stockCategoryWiseListForPdf.isEmpty()) {
					for (int k = 0; k < stockCategoryWiseListForPdf.size(); k++) {

						if (stockCategoryWiseListForPdf.get(k).getOpeningStock() > 0
								|| stockCategoryWiseListForPdf.get(k).getOpStockValue() > 0
								|| stockCategoryWiseListForPdf.get(k).getApproveQty() > 0
								|| stockCategoryWiseListForPdf.get(k).getApprovedQtyValue() > 0
								|| stockCategoryWiseListForPdf.get(k).getIssueQty() > 0
								|| stockCategoryWiseListForPdf.get(k).getIssueQtyValue() > 0
								|| stockCategoryWiseListForPdf.get(k).getDamageQty() > 0
								|| stockCategoryWiseListForPdf.get(k).getDamageValue() > 0) {

							index++;

							PdfPCell cell;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(stockCategoryWiseListForPdf.get(k).getCatDesc(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(
									"" + df.format(stockCategoryWiseListForPdf.get(k).getOpeningStock()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalOpQty = totalOpQty + stockCategoryWiseListForPdf.get(k).getOpeningStock();

							cell = new PdfPCell(new Phrase(
									"" + df.format(stockCategoryWiseListForPdf.get(k).getOpStockValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalOpValue = totalOpValue + stockCategoryWiseListForPdf.get(k).getOpStockValue();

							cell = new PdfPCell(new Phrase(
									"" + df.format(stockCategoryWiseListForPdf.get(k).getApproveQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalMrnQty = totalMrnQty + stockCategoryWiseListForPdf.get(k).getApproveQty();

							cell = new PdfPCell(
									new Phrase("" + df.format(stockCategoryWiseListForPdf.get(k).getApprovedQtyValue()),
											headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalMrnValue = totalMrnValue + stockCategoryWiseListForPdf.get(k).getApprovedQtyValue();

							cell = new PdfPCell(new Phrase(
									"" + df.format(stockCategoryWiseListForPdf.get(k).getIssueQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalIssueQty = totalIssueQty + stockCategoryWiseListForPdf.get(k).getIssueQty();

							cell = new PdfPCell(new Phrase(
									"" + df.format(stockCategoryWiseListForPdf.get(k).getIssueQtyValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalIssueValue = totalIssueValue + stockCategoryWiseListForPdf.get(k).getIssueQtyValue();

							cell = new PdfPCell(new Phrase(
									"" + df.format(stockCategoryWiseListForPdf.get(k).getDamageQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalDamageQty = totalDamageQty + stockCategoryWiseListForPdf.get(k).getDamageQty();

							cell = new PdfPCell(new Phrase(
									"" + df.format(stockCategoryWiseListForPdf.get(k).getDamageValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalDamageValue = totalDamageValue + stockCategoryWiseListForPdf.get(k).getDamageValue();

							float closingQty = stockCategoryWiseListForPdf.get(k).getOpeningStock()
									+ stockCategoryWiseListForPdf.get(k).getApproveQty()
									- stockCategoryWiseListForPdf.get(k).getIssueQty()
									- stockCategoryWiseListForPdf.get(k).getDamageQty();

							float closingValue = stockCategoryWiseListForPdf.get(k).getOpStockValue()
									+ stockCategoryWiseListForPdf.get(k).getApprovedQtyValue()
									- stockCategoryWiseListForPdf.get(k).getIssueQtyValue()
									- stockCategoryWiseListForPdf.get(k).getDamageValue();

							cell = new PdfPCell(new Phrase("" + df.format(closingQty), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalClQty = totalClQty + closingQty;

							cell = new PdfPCell(new Phrase("" + df.format(closingValue), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalClValue = totalClValue + closingValue;
						}
					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total ", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalOpQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalOpValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalMrnQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalMrnValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalIssueQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalIssueValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalDamageQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalDamageValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalClQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalClValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph reportName = new Paragraph("STOCK VALUEATION REPORT", f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				Paragraph typeName = new Paragraph("Type : " + type, f1);
				typeName.setAlignment(Element.ALIGN_CENTER);
				document.add(typeName);

				Paragraph headingDate = new Paragraph("From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/listForStockValuetioinGraph", method = RequestMethod.GET)
	public @ResponseBody List<StockValuationCategoryWise> listForStockValuetioinGraph(HttpServletRequest request,
			HttpServletResponse response) {

		return stockCategoryWiseListForPdf;
	}

	List<GetCurrentStock> itemWiseStockValuetionListForPDF = new ArrayList<>();

	@RequestMapping(value = "/stockSummaryWithCatId/{catId}/{type}/{catDesc}", method = RequestMethod.GET)
	public ModelAndView stockSummaryWithCatId(@PathVariable int catId, @PathVariable String type,
			@PathVariable String catDesc, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/stockSummaryWithCatId");
		List<GetCurrentStock> getStockBetweenDate = new ArrayList<>();

		try {

			SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");

			Date date = dd.parse(fromDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			String firstDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);

			System.out.println(DateConvertor.convertToYMD(firstDate) + DateConvertor.convertToYMD(fromDate));

			if (DateConvertor.convertToYMD(firstDate).compareTo(DateConvertor.convertToYMD(fromDate)) < 0) {
				calendar.add(Calendar.DATE, -1);
				String previousDate = yy.format(new Date(calendar.getTimeInMillis()));
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(firstDate));
				map.add("toDate", previousDate);
				map.add("catId", catId);
				map.add("typeId", typeId);
				System.out.println(map);
				GetCurrentStock[] getCurrentStock = rest.postForObject(
						Constants.url + "/getStockBetweenDateWithCatIdAndTypeId", map, GetCurrentStock[].class);
				List<GetCurrentStock> diffDateStock = new ArrayList<>(Arrays.asList(getCurrentStock));

				calendar.add(Calendar.DATE, 1);
				String addDay = yy.format(new Date(calendar.getTimeInMillis()));
				map = new LinkedMultiValueMap<>();
				map.add("fromDate", addDay);
				map.add("toDate", DateConvertor.convertToYMD(toDate));
				map.add("catId", catId);
				map.add("typeId", typeId);
				System.out.println(map);
				GetCurrentStock[] getCurrentStock1 = rest.postForObject(
						Constants.url + "/getStockBetweenDateWithCatIdAndTypeId", map, GetCurrentStock[].class);
				getStockBetweenDate = new ArrayList<>(Arrays.asList(getCurrentStock1));

				for (int i = 0; i < getStockBetweenDate.size(); i++) {
					for (int j = 0; j < diffDateStock.size(); j++) {
						if (getStockBetweenDate.get(i).getItemId() == diffDateStock.get(j).getItemId()) {
							getStockBetweenDate.get(i).setOpeningStock(diffDateStock.get(j).getOpeningStock()
									+ diffDateStock.get(j).getApproveQty() - diffDateStock.get(j).getIssueQty()
									+ diffDateStock.get(j).getReturnIssueQty() - diffDateStock.get(j).getDamageQty()
									- diffDateStock.get(j).getGatepassQty()
									+ diffDateStock.get(j).getGatepassReturnQty());
							getStockBetweenDate.get(i).setOpStockValue(diffDateStock.get(j).getOpStockValue()
									+ diffDateStock.get(j).getApprovedQtyValue()
									- diffDateStock.get(j).getIssueQtyValue() - diffDateStock.get(j).getDamagValue());

							break;
						}
					}
				}
			} else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));
				map.add("catId", catId);
				map.add("typeId", typeId);
				System.out.println(map);
				GetCurrentStock[] getCurrentStock = rest.postForObject(
						Constants.url + "/getStockBetweenDateWithCatIdAndTypeId", map, GetCurrentStock[].class);
				getStockBetweenDate = new ArrayList<>(Arrays.asList(getCurrentStock));
			}
			itemWiseStockValuetionListForPDF = getStockBetweenDate;
			model.addObject("list", getStockBetweenDate);
			model.addObject("type", type);
			model.addObject("catDesc", catDesc);
			// ----------------exel-------------------------

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("ITEM NAME");
			rowData.add("OP QTY");
			rowData.add("OP VALUE");
			rowData.add("MRN QTY");
			rowData.add("MRN VALUE");
			rowData.add("ISSUE QTY");
			rowData.add("ISSUE VALUE");
			rowData.add("DAMAGE QTY");
			rowData.add("DAMAGE VALUE");
			rowData.add("C\\L QTY");
			rowData.add("C\\L VALUE");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			int k = 0;
			for (int i = 0; i < getStockBetweenDate.size(); i++) {
				if (getStockBetweenDate.get(i).getOpeningStock() > 0 || getStockBetweenDate.get(i).getOpStockValue() > 0
						|| getStockBetweenDate.get(i).getApproveQty() > 0
						|| getStockBetweenDate.get(i).getApprovedQtyValue() > 0
						|| getStockBetweenDate.get(i).getIssueQty() > 0
						|| getStockBetweenDate.get(i).getApprovedQtyValue() > 0
						|| getStockBetweenDate.get(i).getIssueQty() > 0
						|| getStockBetweenDate.get(i).getIssueQtyValue() > 0
						|| getStockBetweenDate.get(i).getDamageQty() > 0
						|| getStockBetweenDate.get(i).getDamagValue() > 0) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					k++;
					rowData.add((k) + "");
					rowData.add(getStockBetweenDate.get(i).getItemCode());
					rowData.add("" + getStockBetweenDate.get(i).getOpeningStock());
					rowData.add("" + getStockBetweenDate.get(i).getOpStockValue());
					rowData.add("" + getStockBetweenDate.get(i).getApproveQty());
					rowData.add("" + getStockBetweenDate.get(i).getApprovedQtyValue());
					rowData.add("" + getStockBetweenDate.get(i).getIssueQty());
					rowData.add("" + getStockBetweenDate.get(i).getIssueQtyValue());
					rowData.add("" + getStockBetweenDate.get(i).getDamageQty());
					rowData.add("" + getStockBetweenDate.get(i).getDamagValue());

					float closingQty = getStockBetweenDate.get(i).getOpeningStock()
							+ getStockBetweenDate.get(i).getApproveQty() - getStockBetweenDate.get(i).getIssueQty()
							- getStockBetweenDate.get(i).getDamageQty();

					float closingValue = getStockBetweenDate.get(i).getOpStockValue()
							+ getStockBetweenDate.get(i).getApprovedQtyValue()
							- getStockBetweenDate.get(i).getIssueQtyValue()
							- getStockBetweenDate.get(i).getDamagValue();

					rowData.add("" + closingQty);
					rowData.add("" + closingValue);

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
				}

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "ItemWiseStockValue");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/stockValuetionReportItemWisePDF/{type}/{catDesc}", method = RequestMethod.GET)
	public void stockValuetionReportItemWisePDF(@PathVariable String type, @PathVariable String catDesc,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A3);
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

			float totalOpQty = 0;
			float totalOpValue = 0;
			float totalMrnQty = 0;
			float totalMrnValue = 0;
			float totalIssueQty = 0;
			float totalIssueValue = 0;
			float totalDamageQty = 0;
			float totalDamageValue = 0;
			float totalClQty = 0;
			float totalClValue = 0;

			PdfPTable table = new PdfPTable(12);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 4.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ITEM NAME", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("OP QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("OP VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MRN QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MRN VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("DAMAGE QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("DAMAGE VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("C/L QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("C/L VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!itemWiseStockValuetionListForPDF.isEmpty()) {
					for (int k = 0; k < itemWiseStockValuetionListForPDF.size(); k++) {

						if (itemWiseStockValuetionListForPDF.get(k).getOpeningStock() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getOpStockValue() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getApproveQty() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getApprovedQtyValue() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getIssueQty() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getIssueQtyValue() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getDamageQty() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getDamagValue() > 0) {

							index++;

							PdfPCell cell;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(
									new Phrase(itemWiseStockValuetionListForPDF.get(k).getItemCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseStockValuetionListForPDF.get(k).getOpeningStock()),
									headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalOpQty = totalOpQty + itemWiseStockValuetionListForPDF.get(k).getOpeningStock();

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseStockValuetionListForPDF.get(k).getOpStockValue()),
									headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalOpValue = totalOpValue + itemWiseStockValuetionListForPDF.get(k).getOpStockValue();

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseStockValuetionListForPDF.get(k).getApproveQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalMrnQty = totalMrnQty + itemWiseStockValuetionListForPDF.get(k).getApproveQty();

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseStockValuetionListForPDF.get(k).getApprovedQtyValue()),
									headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalMrnValue = totalMrnValue
									+ itemWiseStockValuetionListForPDF.get(k).getApprovedQtyValue();

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseStockValuetionListForPDF.get(k).getIssueQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalIssueQty = totalIssueQty + itemWiseStockValuetionListForPDF.get(k).getIssueQty();

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseStockValuetionListForPDF.get(k).getIssueQtyValue()),
									headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalIssueValue = totalIssueValue
									+ itemWiseStockValuetionListForPDF.get(k).getIssueQtyValue();

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseStockValuetionListForPDF.get(k).getDamageQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalDamageQty = totalDamageQty + itemWiseStockValuetionListForPDF.get(k).getDamageQty();

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseStockValuetionListForPDF.get(k).getDamagValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalDamageValue = totalDamageValue
									+ itemWiseStockValuetionListForPDF.get(k).getDamagValue();

							float closingQty = itemWiseStockValuetionListForPDF.get(k).getOpeningStock()
									+ itemWiseStockValuetionListForPDF.get(k).getApproveQty()
									- itemWiseStockValuetionListForPDF.get(k).getIssueQty()
									- itemWiseStockValuetionListForPDF.get(k).getDamageQty();

							float closingValue = itemWiseStockValuetionListForPDF.get(k).getOpStockValue()
									+ itemWiseStockValuetionListForPDF.get(k).getApprovedQtyValue()
									- itemWiseStockValuetionListForPDF.get(k).getIssueQtyValue()
									- itemWiseStockValuetionListForPDF.get(k).getDamagValue();

							totalClQty = totalClQty + closingQty;
							totalClValue = totalClValue + closingValue;

							cell = new PdfPCell(new Phrase("" + df.format(closingQty), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase("" + df.format(closingValue), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
						}
					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total ", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalOpQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalOpValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalMrnQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalMrnValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalIssueQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalIssueValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalDamageQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalDamageValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalClQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalClValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph reportName = new Paragraph("STOCK VALUEATION REPORT ITEM WISE", f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				Paragraph typeName = new Paragraph("Category : " + catDesc + ", Type : " + type, f1);
				typeName.setAlignment(Element.ALIGN_CENTER);
				document.add(typeName);

				Paragraph headingDate = new Paragraph("From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/stockValuetionReportItemWisePDFOnlyClStock/{type}/{catDesc}", method = RequestMethod.GET)
	public void stockValuetionReportItemWisePDFOnlyClStock(@PathVariable String type, @PathVariable String catDesc,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A4);
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

			float totalOpQty = 0;
			float totalOpValue = 0;
			float totalMrnQty = 0;
			float totalMrnValue = 0;
			float totalIssueQty = 0;
			float totalIssueValue = 0;
			float totalDamageQty = 0;
			float totalDamageValue = 0;
			float totalClQty = 0;
			float totalClValue = 0;

			PdfPTable table = new PdfPTable(4);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.6f, 6.0f, 1.0f, 1.0f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ITEM NAME", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("C/L QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("C/L VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!itemWiseStockValuetionListForPDF.isEmpty()) {
					for (int k = 0; k < itemWiseStockValuetionListForPDF.size(); k++) {

						if (itemWiseStockValuetionListForPDF.get(k).getOpeningStock() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getOpStockValue() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getApproveQty() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getApprovedQtyValue() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getIssueQty() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getIssueQtyValue() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getDamageQty() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getDamagValue() > 0) {

							index++;

							PdfPCell cell;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(
									new Phrase(itemWiseStockValuetionListForPDF.get(k).getItemCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							float closingQty = itemWiseStockValuetionListForPDF.get(k).getOpeningStock()
									+ itemWiseStockValuetionListForPDF.get(k).getApproveQty()
									- itemWiseStockValuetionListForPDF.get(k).getIssueQty()
									- itemWiseStockValuetionListForPDF.get(k).getDamageQty();

							float closingValue = itemWiseStockValuetionListForPDF.get(k).getOpStockValue()
									+ itemWiseStockValuetionListForPDF.get(k).getApprovedQtyValue()
									- itemWiseStockValuetionListForPDF.get(k).getIssueQtyValue()
									- itemWiseStockValuetionListForPDF.get(k).getDamagValue();

							totalClQty = totalClQty + closingQty;
							totalClValue = totalClValue + closingValue;

							cell = new PdfPCell(new Phrase("" + df.format(closingQty), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase("" + df.format(closingValue), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
						}
					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total ", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalClQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalClValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph reportName = new Paragraph("STOCK VALUEATION REPORT ITEM WISE", f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				Paragraph typeName = new Paragraph("Category : " + catDesc + ", Type : " + type, f1);
				typeName.setAlignment(Element.ALIGN_CENTER);
				document.add(typeName);

				Paragraph headingDate = new Paragraph("From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/stockValuetionReportItemWisePDFOnlyOpStock/{type}/{catDesc}", method = RequestMethod.GET)
	public void stockValuetionReportItemWisePDFOnlyOpStock(@PathVariable String type, @PathVariable String catDesc,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A4);
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

			float totalOpQty = 0;
			float totalOpValue = 0;

			PdfPTable table = new PdfPTable(4);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.6f, 6.0f, 1.0f, 1.0f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ITEM NAME", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("OP QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("OP VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!itemWiseStockValuetionListForPDF.isEmpty()) {
					for (int k = 0; k < itemWiseStockValuetionListForPDF.size(); k++) {

						if (itemWiseStockValuetionListForPDF.get(k).getOpeningStock() > 0
								|| itemWiseStockValuetionListForPDF.get(k).getOpStockValue() > 0) {

							index++;

							PdfPCell cell;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(
									new Phrase(itemWiseStockValuetionListForPDF.get(k).getItemCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseStockValuetionListForPDF.get(k).getOpeningStock()),
									headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalOpQty = totalOpQty + itemWiseStockValuetionListForPDF.get(k).getOpeningStock();

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseStockValuetionListForPDF.get(k).getOpStockValue()),
									headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalOpValue = totalOpValue + itemWiseStockValuetionListForPDF.get(k).getOpStockValue();

						}
					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total ", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalOpQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalOpValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph reportName = new Paragraph("STOCK VALUEATION REPORT ITEM WISE", f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				Paragraph typeName = new Paragraph("Category : " + catDesc + ", Type : " + type, f1);
				typeName.setAlignment(Element.ALIGN_CENTER);
				document.add(typeName);

				Paragraph headingDate = new Paragraph("From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<StockValuationCategoryWise> categoryWiseIssueAndMrnForPdf = new ArrayList<StockValuationCategoryWise>();

	@RequestMapping(value = "/issueAndMrnReportCategoryWise", method = RequestMethod.GET)
	public ModelAndView issueAndMrnReportCategoryWise(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/issueAndMrnReportCategoryWise");
		try {
			List<StockValuationCategoryWise> categoryWiseReport = new ArrayList<StockValuationCategoryWise>();
			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);

			if (request.getParameter("fromDate") == null || request.getParameter("toDate") == null
					|| request.getParameter("typeId") == null || request.getParameter("isDev") == null) {

				SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				fromDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
				toDate = dd.format(date);
				typeId = 0;
				isDev = -1;

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", yy.format(date));
				map.add("typeId", typeId);
				map.add("isDev", isDev);
				System.out.println(map);
				StockValuationCategoryWise[] stockValuationCategoryWise1 = rest.postForObject(
						Constants.url + "/issueAndMrnCatWiseReport", map, StockValuationCategoryWise[].class);
				categoryWiseReport = new ArrayList<StockValuationCategoryWise>(
						Arrays.asList(stockValuationCategoryWise1));

				model.addObject("categoryWiseReport", categoryWiseReport);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", dd.format(date));
			} else {
				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");
				typeId = Integer.parseInt(request.getParameter("typeId"));
				isDev = Integer.parseInt(request.getParameter("isDev"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));
				map.add("typeId", typeId);
				map.add("isDev", isDev);
				System.out.println(map);
				StockValuationCategoryWise[] stockValuationCategoryWise1 = rest.postForObject(
						Constants.url + "/issueAndMrnCatWiseReport", map, StockValuationCategoryWise[].class);
				categoryWiseReport = new ArrayList<StockValuationCategoryWise>(
						Arrays.asList(stockValuationCategoryWise1));

				model.addObject("categoryWiseReport", categoryWiseReport);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
				model.addObject("typeId", typeId);
				model.addObject("isDevelompent", isDev);

			}

			// ----------------exel-------------------------

			categoryWiseIssueAndMrnForPdf = categoryWiseReport;

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("CATEGORY NAME");
			rowData.add("MRN QTY");
			rowData.add("MRN VALUE");
			rowData.add("ISSUE QTY");
			rowData.add("ISSUE VALUE");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			int k = 0;
			for (int i = 0; i < categoryWiseReport.size(); i++) {
				if (categoryWiseReport.get(i).getApproveQty() > 0 || categoryWiseReport.get(i).getApprovedQtyValue() > 0
						|| categoryWiseReport.get(i).getIssueQty() > 0
						|| categoryWiseReport.get(i).getApprovedQtyValue() > 0) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					k++;
					rowData.add((k) + "");
					rowData.add(categoryWiseReport.get(i).getCatDesc());
					rowData.add("" + categoryWiseReport.get(i).getApproveQty());
					rowData.add("" + categoryWiseReport.get(i).getApprovedQtyValue());
					rowData.add("" + categoryWiseReport.get(i).getIssueQty());
					rowData.add("" + categoryWiseReport.get(i).getIssueQtyValue());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
				}

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "CategoryWiseMrnAndIssue");

			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/listForIssueAndMrnGraphCategoryWise", method = RequestMethod.GET)
	public @ResponseBody List<StockValuationCategoryWise> listForIssueAndMrnGraph(HttpServletRequest request,
			HttpServletResponse response) {

		return categoryWiseIssueAndMrnForPdf;
	}

	@RequestMapping(value = "/issueAndMrnCategoryWisePDF/{type}/{isDev}", method = RequestMethod.GET)
	public void issueAndMrnCategoryWisePDF(@PathVariable String type, @PathVariable String isDev,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A4);
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

			float totalMrnQty = 0;
			float totalMrnValue = 0;
			float totalIssueQty = 0;
			float totalIssueValue = 0;

			PdfPTable table = new PdfPTable(6);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("CATEGORY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MRN QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MRN VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!categoryWiseIssueAndMrnForPdf.isEmpty()) {
					for (int k = 0; k < categoryWiseIssueAndMrnForPdf.size(); k++) {

						if (categoryWiseIssueAndMrnForPdf.get(k).getOpeningStock() > 0
								|| categoryWiseIssueAndMrnForPdf.get(k).getOpStockValue() > 0
								|| categoryWiseIssueAndMrnForPdf.get(k).getApproveQty() > 0
								|| categoryWiseIssueAndMrnForPdf.get(k).getApprovedQtyValue() > 0
								|| categoryWiseIssueAndMrnForPdf.get(k).getIssueQty() > 0
								|| categoryWiseIssueAndMrnForPdf.get(k).getIssueQtyValue() > 0
								|| categoryWiseIssueAndMrnForPdf.get(k).getDamageQty() > 0
								|| categoryWiseIssueAndMrnForPdf.get(k).getDamageValue() > 0) {

							index++;

							PdfPCell cell;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(
									new Phrase(categoryWiseIssueAndMrnForPdf.get(k).getCatDesc(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(
									"" + df.format(categoryWiseIssueAndMrnForPdf.get(k).getApproveQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalMrnQty = totalMrnQty + categoryWiseIssueAndMrnForPdf.get(k).getApproveQty();

							cell = new PdfPCell(new Phrase(
									"" + df.format(categoryWiseIssueAndMrnForPdf.get(k).getApprovedQtyValue()),
									headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalMrnValue = totalMrnValue + categoryWiseIssueAndMrnForPdf.get(k).getApprovedQtyValue();

							cell = new PdfPCell(new Phrase(
									"" + df.format(categoryWiseIssueAndMrnForPdf.get(k).getIssueQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalIssueQty = totalIssueQty + categoryWiseIssueAndMrnForPdf.get(k).getIssueQty();

							cell = new PdfPCell(new Phrase(
									"" + df.format(categoryWiseIssueAndMrnForPdf.get(k).getIssueQtyValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalIssueValue = totalIssueValue + categoryWiseIssueAndMrnForPdf.get(k).getIssueQtyValue();

						}
					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalMrnQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalMrnValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalIssueQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalIssueValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph reportName = new Paragraph("RECEIPT/ISSUE VALUEATION REPORT", f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				Paragraph typeName = new Paragraph("Type : " + type + ",  Is Dev :" + isDev, f1);
				typeName.setAlignment(Element.ALIGN_CENTER);
				document.add(typeName);

				Paragraph headingDate = new Paragraph("From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<IssueAndMrnGroupWise> issueAndMrnGroupWiseListForPdf = new ArrayList<IssueAndMrnGroupWise>();

	@RequestMapping(value = "/issueAndMrnReportGroupWise/{catId}/{type}/{isDevName}/{catDesc}", method = RequestMethod.GET)
	public ModelAndView issueAndMrnReportGroupWise(@PathVariable int catId, @PathVariable String type,
			@PathVariable String isDevName, @PathVariable String catDesc, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/issueAndMrnReportGroupWise");
		List<IssueAndMrnGroupWise> groupWiseList = new ArrayList<IssueAndMrnGroupWise>();

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("catId", catId);
			map.add("typeId", typeId);
			map.add("isDev", isDev);
			System.out.println(map);
			IssueAndMrnGroupWise[] issueAndMrnGroupWise = rest.postForObject(
					Constants.url + "/issueAndMrnGroupWisReportByCatId", map, IssueAndMrnGroupWise[].class);
			groupWiseList = new ArrayList<IssueAndMrnGroupWise>(Arrays.asList(issueAndMrnGroupWise));

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("GROUP NAME");
			rowData.add("MRN QTY");
			rowData.add("MRN VALUE");
			rowData.add("ISSUE QTY");
			rowData.add("ISSUE VALUE");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			int k = 0;
			for (int i = 0; i < groupWiseList.size(); i++) {
				if (groupWiseList.get(i).getApproveQty() > 0 || groupWiseList.get(i).getApprovedQtyValue() > 0
						|| groupWiseList.get(i).getIssueQty() > 0 || groupWiseList.get(i).getApprovedQtyValue() > 0) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					k++;
					rowData.add((k) + "");
					rowData.add(groupWiseList.get(i).getGrpCode());
					rowData.add("" + groupWiseList.get(i).getApproveQty());
					rowData.add("" + groupWiseList.get(i).getApprovedQtyValue());
					rowData.add("" + groupWiseList.get(i).getIssueQty());
					rowData.add("" + groupWiseList.get(i).getIssueQtyValue());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
				}

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "GroupWiseMrnAndIssue");

			issueAndMrnGroupWiseListForPdf = groupWiseList;

			model.addObject("list", groupWiseList);
			model.addObject("type", type);
			model.addObject("isDevName", isDevName);
			model.addObject("catDesc", catDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/listForIssueAndMrnGraphGroupWise", method = RequestMethod.GET)
	public @ResponseBody List<IssueAndMrnGroupWise> listForIssueAndMrnGraphGroupWise(HttpServletRequest request,
			HttpServletResponse response) {

		return issueAndMrnGroupWiseListForPdf;
	}

	@RequestMapping(value = "/issueAndMrnGroupWisePDF/{type}/{isDevName}/{catDesc}", method = RequestMethod.GET)
	public void issueAndMrnGroupWisePDF(@PathVariable String type, @PathVariable String isDevName,
			@PathVariable String catDesc, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A4);
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

			float totalMrnQty = 0;
			float totalMrnValue = 0;
			float totalIssueQty = 0;
			float totalIssueValue = 0;

			PdfPTable table = new PdfPTable(6);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("GROUP", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MRN QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MRN VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!issueAndMrnGroupWiseListForPdf.isEmpty()) {
					for (int k = 0; k < issueAndMrnGroupWiseListForPdf.size(); k++) {

						if (issueAndMrnGroupWiseListForPdf.get(k).getApproveQty() > 0
								|| issueAndMrnGroupWiseListForPdf.get(k).getApprovedQtyValue() > 0
								|| issueAndMrnGroupWiseListForPdf.get(k).getIssueQty() > 0
								|| issueAndMrnGroupWiseListForPdf.get(k).getIssueQtyValue() > 0) {

							index++;

							PdfPCell cell;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(
									new Phrase(issueAndMrnGroupWiseListForPdf.get(k).getGrpCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(
									"" + df.format(issueAndMrnGroupWiseListForPdf.get(k).getApproveQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalMrnQty = totalMrnQty + issueAndMrnGroupWiseListForPdf.get(k).getApproveQty();

							cell = new PdfPCell(new Phrase(
									"" + df.format(issueAndMrnGroupWiseListForPdf.get(k).getApprovedQtyValue()),
									headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalMrnValue = totalMrnValue + issueAndMrnGroupWiseListForPdf.get(k).getApprovedQtyValue();

							cell = new PdfPCell(new Phrase(
									"" + df.format(issueAndMrnGroupWiseListForPdf.get(k).getIssueQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalIssueQty = totalIssueQty + issueAndMrnGroupWiseListForPdf.get(k).getIssueQty();

							cell = new PdfPCell(
									new Phrase("" + df.format(issueAndMrnGroupWiseListForPdf.get(k).getIssueQtyValue()),
											headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalIssueValue = totalIssueValue
									+ issueAndMrnGroupWiseListForPdf.get(k).getIssueQtyValue();
						}
					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalMrnQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalMrnValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalIssueQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalIssueValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph reportName = new Paragraph("RECEIPT/ISSUE VALUEATION REPORT GROUP WISE", f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				Paragraph typeName = new Paragraph(
						"Category: " + catDesc + ", Type : " + type + ",  Is Dev :" + isDevName, f1);
				typeName.setAlignment(Element.ALIGN_CENTER);
				document.add(typeName);

				Paragraph headingDate = new Paragraph("From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<IssueAndMrnItemWise> itemWiseIssueAndMrnListForPdf = new ArrayList<IssueAndMrnItemWise>();

	@RequestMapping(value = "/issueAndMrnReportItemWise/{groupId}/{type}/{isDevName}/{catDesc}/{grpCode}", method = RequestMethod.GET)
	public ModelAndView issueAndMrnReportItemWise(@PathVariable int groupId, @PathVariable String type,
			@PathVariable String isDevName, @PathVariable String catDesc, @PathVariable String grpCode,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/issueAndMrnReportItemWise");
		List<IssueAndMrnItemWise> itemWiseList = new ArrayList<IssueAndMrnItemWise>();

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("groupId", groupId);
			map.add("typeId", typeId);
			map.add("isDev", isDev);
			System.out.println(map);
			IssueAndMrnItemWise[] issueAndMrnGroupWise = rest.postForObject(
					Constants.url + "/issueAndMrnItemWiseReportByGroupId", map, IssueAndMrnItemWise[].class);
			itemWiseList = new ArrayList<IssueAndMrnItemWise>(Arrays.asList(issueAndMrnGroupWise));

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("ITEM NAME");
			rowData.add("MRN QTY");
			rowData.add("MRN VALUE");
			rowData.add("ISSUE QTY");
			rowData.add("ISSUE VALUE");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			int k = 0;
			for (int i = 0; i < itemWiseList.size(); i++) {
				if (itemWiseList.get(i).getApproveQty() > 0 || itemWiseList.get(i).getApprovedQtyValue() > 0
						|| itemWiseList.get(i).getIssueQty() > 0 || itemWiseList.get(i).getApprovedQtyValue() > 0) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					k++;
					rowData.add((k) + "");
					rowData.add(itemWiseList.get(i).getItemCode());
					rowData.add("" + itemWiseList.get(i).getApproveQty());
					rowData.add("" + itemWiseList.get(i).getApprovedQtyValue());
					rowData.add("" + itemWiseList.get(i).getIssueQty());
					rowData.add("" + itemWiseList.get(i).getIssueQtyValue());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
				}

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "ItemWiseMrnAndIssue");

			model.addObject("list", itemWiseList);
			model.addObject("type", type);
			model.addObject("isDevName", isDevName);
			model.addObject("catDesc", catDesc);
			model.addObject("grpCode", grpCode);
			itemWiseIssueAndMrnListForPdf = itemWiseList;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	// issueAndMrnItemWiseReportByCatId Sac

	@RequestMapping(value = "/issueAndMrnItemWiseReportByCatId/{catId}/{type}/{isDevName}/{catDesc}", method = RequestMethod.GET)
	public ModelAndView issueAndMrnItemWiseReportByCatId(@PathVariable int catId, @PathVariable String type,
			@PathVariable String isDevName, @PathVariable String catDesc, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/issueAndMrnReportItemWise");
		List<IssueAndMrnItemWise> itemWiseList = new ArrayList<IssueAndMrnItemWise>();

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("catId", catId);
			map.add("typeId", typeId);
			map.add("isDev", isDev);
			System.out.println(map);
			IssueAndMrnItemWise[] issueAndMrnGroupWise = rest.postForObject(
					Constants.url + "/issueAndMrnItemWiseReportByCatId", map, IssueAndMrnItemWise[].class);
			itemWiseList = new ArrayList<IssueAndMrnItemWise>(Arrays.asList(issueAndMrnGroupWise));

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("ITEM NAME");
			rowData.add("MRN QTY");
			rowData.add("MRN VALUE");
			rowData.add("ISSUE QTY");
			rowData.add("ISSUE VALUE");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			int k = 0;
			for (int i = 0; i < itemWiseList.size(); i++) {
				if (itemWiseList.get(i).getApproveQty() > 0 || itemWiseList.get(i).getApprovedQtyValue() > 0
						|| itemWiseList.get(i).getIssueQty() > 0 || itemWiseList.get(i).getApprovedQtyValue() > 0) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					k++;
					rowData.add((k) + "");
					rowData.add(itemWiseList.get(i).getItemCode());
					rowData.add("" + itemWiseList.get(i).getApproveQty());
					rowData.add("" + itemWiseList.get(i).getApprovedQtyValue());
					rowData.add("" + itemWiseList.get(i).getIssueQty());
					rowData.add("" + itemWiseList.get(i).getIssueQtyValue());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
				}

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "ItemWiseMrnAndIssueByCatId");

			model.addObject("list", itemWiseList);
			model.addObject("type", type);
			model.addObject("isDevName", isDevName);
			model.addObject("catDesc", catDesc);
			model.addObject("grpCode", "-");
			itemWiseIssueAndMrnListForPdf = itemWiseList;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/issueAndMrnItemWisePDF/{type}/{isDevName}/{catDesc}/{grpCode}", method = RequestMethod.GET)
	public void issueAndMrnItemWisePDF(@PathVariable String type, @PathVariable String isDevName,
			@PathVariable String catDesc, @PathVariable String grpCode, HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A4);
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

			float totalMrnQty = 0;
			float totalMrnValue = 0;
			float totalIssueQty = 0;
			float totalIssueValue = 0;

			PdfPTable table = new PdfPTable(6);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ITEM", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MRN QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MRN VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!itemWiseIssueAndMrnListForPdf.isEmpty()) {
					for (int k = 0; k < itemWiseIssueAndMrnListForPdf.size(); k++) {

						if (itemWiseIssueAndMrnListForPdf.get(k).getApproveQty() > 0
								|| itemWiseIssueAndMrnListForPdf.get(k).getApprovedQtyValue() > 0
								|| itemWiseIssueAndMrnListForPdf.get(k).getIssueQty() > 0
								|| itemWiseIssueAndMrnListForPdf.get(k).getIssueQtyValue() > 0) {

							index++;

							PdfPCell cell;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(
									new Phrase(itemWiseIssueAndMrnListForPdf.get(k).getItemCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseIssueAndMrnListForPdf.get(k).getApproveQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalMrnQty = totalMrnQty + itemWiseIssueAndMrnListForPdf.get(k).getApproveQty();

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseIssueAndMrnListForPdf.get(k).getApprovedQtyValue()),
									headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalMrnValue = totalMrnValue + itemWiseIssueAndMrnListForPdf.get(k).getApprovedQtyValue();

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseIssueAndMrnListForPdf.get(k).getIssueQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalIssueQty = totalIssueQty + itemWiseIssueAndMrnListForPdf.get(k).getIssueQty();

							cell = new PdfPCell(new Phrase(
									"" + df.format(itemWiseIssueAndMrnListForPdf.get(k).getIssueQtyValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							totalIssueValue = totalIssueValue + itemWiseIssueAndMrnListForPdf.get(k).getIssueQtyValue();

						}
					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalMrnQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalMrnValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalIssueQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(totalIssueValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph reportName = new Paragraph("RECEIPT/ISSUE VALUEATION REPORT ITEM WISE", f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				Paragraph typeName = new Paragraph("Category : " + catDesc + ", Group : " + grpCode + ", Type : " + type
						+ ", Is Dev :" + isDevName, f1);
				typeName.setAlignment(Element.ALIGN_CENTER);
				document.add(typeName);

				Paragraph headingDate = new Paragraph("From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<IssueDeptWise> deptWiselistGlobal = null;
	String catIds = new String();
	List<DeptSubDeptValReport> deptSubDeptList;// Sac

	@RequestMapping(value = "/issueReportDeptWise", method = RequestMethod.GET)
	public ModelAndView issueReportDeptWise(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/issueReportDeptWise");
		try {
			List<IssueDeptWise> deptWiselist = new ArrayList<IssueDeptWise>();
			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));

			model.addObject("deparmentList", deparmentList);
			model.addObject("typeList", typeList);

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));
			catIds = new String();
			model.addObject("categoryList", categoryList);

			if (request.getParameter("fromDate") == null || request.getParameter("toDate") == null
					|| request.getParameter("typeId") == null || request.getParameter("isDev") == null
					|| request.getParameter("deptId") == null) {

				SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				fromDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
				toDate = dd.format(date);
				typeId = 0;
				isDev = -1;
				deptId = 0;
				for (int i = 0; i < categoryList.size(); i++) {
					catIds = catIds + categoryList.get(i).getCatId() + ",";
				}
				map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", yy.format(date));
				map.add("typeId", typeId);
				map.add("isDev", isDev);
				map.add("deptId", deptId);
				map.add("catIds", catIds.substring(0, catIds.length() - 1));
				System.out.println(map);
				IssueDeptWise[] IssueDeptWise = rest.postForObject(Constants.url + "/issueDepartmentWiseReport", map,
						IssueDeptWise[].class);
				deptWiselist = new ArrayList<IssueDeptWise>(Arrays.asList(IssueDeptWise));

				model.addObject("deptWiselist", deptWiselist);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
				deptWiselistGlobal = deptWiselist;
			} else {
				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");
				typeId = Integer.parseInt(request.getParameter("typeId"));
				isDev = Integer.parseInt(request.getParameter("isDev"));
				deptId = Integer.parseInt(request.getParameter("deptId"));
				catId = Integer.parseInt(request.getParameter("catId"));

				map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));
				map.add("typeId", typeId);
				map.add("isDev", isDev);
				map.add("deptId", deptId);
				if (catId == 0) {
					for (int i = 0; i < categoryList.size(); i++) {
						catIds = catIds + categoryList.get(i).getCatId() + ",";
					}
					map.add("catIds", catIds.substring(0, catIds.length() - 1));
				} else {
					map.add("catIds", catId);
				}

				System.out.println(map);
				IssueDeptWise[] IssueDeptWise = rest.postForObject(Constants.url + "/issueDepartmentWiseReport", map,
						IssueDeptWise[].class);
				deptWiselist = new ArrayList<IssueDeptWise>(Arrays.asList(IssueDeptWise));

				deptWiselistGlobal = deptWiselist;
				model.addObject("deptWiselist", deptWiselist);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
				model.addObject("typeId", typeId);
				model.addObject("isDevelompent", isDev);
				model.addObject("deptId", deptId);
				model.addObject("isDevelompent", isDev);
				model.addObject("catId", catId);

			}
			// ------------------------ Export To
			// Excel--------------------------------------
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("DEPARMENT NAME");
			rowData.add("ISSUE QTY");
			rowData.add("ISSUE VALUE");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < deptWiselist.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");
				rowData.add(deptWiselist.get(i).getDeptCode());
				rowData.add("" + deptWiselist.get(i).getIssueQty());
				rowData.add("" + deptWiselist.get(i).getIssueQtyValue());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "DeptWiseConsumption(Issues)");
			// ------------------------------------END------------------------------------------
			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

			// sachin

			/*
			 * fromDate = request.getParameter("fromDate"); toDate =
			 * request.getParameter("toDate"); //typeId =
			 * Integer.parseInt(request.getParameter("typeId")); isDev
			 * =Integer.parseInt(request.getParameter("isDev")); deptId =
			 * Integer.parseInt(request.getParameter("deptId")); MultiValueMap<String,
			 * Object> map = new LinkedMultiValueMap<>();
			 * map.add("fromDate",DateConvertor.convertToYMD(fromDate));
			 * map.add("toDate",DateConvertor.convertToYMD(toDate)); map.add("typeId",
			 * typeId); map.add("isDev", isDev); map.add("deptId", deptId); if(catId==0) {
			 * for(int i=0;i<categoryList.size();i++) {
			 * catIds=catIds+categoryList.get(i).getCatId()+","; } map.add("catIds",
			 * catIds.substring(0, catIds.length()-1)); } else{ map.add("catIds", catId); }
			 * System.out.println(map);
			 */
			DeptSubDeptValReport[] IssueDeptWise = rest.postForObject(Constants.url + "/getDeptSubDeptValIssueReport",
					map, DeptSubDeptValReport[].class);
			deptSubDeptList = new ArrayList<DeptSubDeptValReport>(Arrays.asList(IssueDeptWise));

			List<Integer> deptIds = new ArrayList<>();
			for (int i = 0; i < deptSubDeptList.size(); i++) {
				deptIds.add(deptSubDeptList.get(i).getDeptId());

			}

			Set<Integer> uniqDetSet = new HashSet<Integer>(deptIds);
			deptIds = new ArrayList<>();
			deptIds.addAll(uniqDetSet);
			System.err.println("Dept Ids " + deptIds);
			List<ExportToExcel> exportToExcelList2 = new ArrayList<ExportToExcel>();
			System.err.println("deptSubDeptList " + deptSubDeptList.toString());
			ExportToExcel expoExcel2 = new ExportToExcel();
			List<String> rowData2 = new ArrayList<String>();

			rowData2.add("SR. No");
			rowData2.add("DEPARMENT NAME");
			rowData2.add("SUB DEPARMENT NAME");
			rowData2.add("ISSUE VALUE");

			expoExcel2.setRowData(rowData2);
			exportToExcelList2.add(expoExcel2);

			int srNo = 0;
			float grandTotal = 0;
			for (int j = 0; j < deptIds.size(); j++) {
				float subDeptTot = 0;

				String deptCode = new String();
				int count = 0;

				for (int i = 0; i < deptSubDeptList.size(); i++) {

					if (deptIds.get(j) == deptSubDeptList.get(i).getDeptId()) {
						deptCode = deptSubDeptList.get(i).getDeptCode();
						subDeptTot = subDeptTot + deptSubDeptList.get(i).getIssueQtyValue();
						if (deptSubDeptList.get(i).getIssueQtyValue() > 0) {

							count = count + 1;
							expoExcel2 = new ExportToExcel();
							rowData2 = new ArrayList<String>();

							if (count == 1) {
								srNo = srNo + 1;
								rowData2.add("" + srNo);
								rowData2.add("" + deptCode);

							} else {

								rowData2.add("");
								rowData2.add("");
							}

							/*
							 * if(j==0) { rowData2.add((j + 1) + "");
							 * 
							 * rowData2.add(deptSubDeptList.get(i).getDeptCode());
							 * 
							 * }else {
							 */
							// rowData2.add("");//now c
							// rowData2.add("");
							// }
							rowData2.add(deptSubDeptList.get(i).getSubDeptCode());

							rowData2.add("" + deptSubDeptList.get(i).getIssueQtyValue());

							expoExcel2.setRowData(rowData2);
							exportToExcelList2.add(expoExcel2);
						}

					}

				}
				expoExcel2 = new ExportToExcel();
				rowData2 = new ArrayList<String>();

				if (count == 0) {
					System.err.println("count ==0");
					rowData2.add("");
					rowData2.add("");
					rowData2.add("");
					rowData2.add("");

				} else {
					rowData2.add("");
					rowData2.add("Sub Total");
					rowData2.add("");
					rowData2.add("" + subDeptTot);
				}

				// if(subDeptTot>0) {
				// rowData2.add((srNo) + "");
				grandTotal = grandTotal + subDeptTot;
				// rowData2.add(deptCode);

				expoExcel2.setRowData(rowData2);
				exportToExcelList2.add(expoExcel2);

				/*
				 * expoExcel2 = new ExportToExcel(); rowData2 = new ArrayList<String>();
				 * 
				 * rowData2.add("");
				 * 
				 * rowData2.add("");
				 * 
				 * rowData2.add(""); rowData2.add("");
				 * 
				 * expoExcel2.setRowData(rowData2); exportToExcelList2.add(expoExcel2);
				 */
				// }

			}

			expoExcel2 = new ExportToExcel();
			rowData2 = new ArrayList<String>();

			rowData2.add("");

			rowData2.add("");

			rowData2.add("Grand Total");
			rowData2.add("" + grandTotal);

			expoExcel2.setRowData(rowData2);
			exportToExcelList2.add(expoExcel2);
			session = request.getSession();
			session.setAttribute("exportExcelList2", exportToExcelList2);
			session.setAttribute("excelName2", "DeptSubDept(Issues)");
			// end
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/getDeptSubDeptIsuuePdf", method = RequestMethod.GET)
	public void getDeptSubDeptIsuuePdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A4);
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

			PdfPTable table = new PdfPTable(4);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 1.2f, 1.2f, 1.2f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Department Name", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Sub Department Name", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Issue Value", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				List<Integer> deptIds = new ArrayList<>();
				for (int i = 0; i < deptSubDeptList.size(); i++) {
					deptIds.add(deptSubDeptList.get(i).getDeptId());

				}

				Set<Integer> uniqDetSet = new HashSet<Integer>(deptIds);
				deptIds = new ArrayList<>();
				deptIds.addAll(uniqDetSet);
				System.err.println("Dept Ids " + deptIds);
				float grandTotal = 0;
				int srNo = 0;
				for (int j = 0; j < deptIds.size(); j++) {
					float subDeptTot = 0;
					// srNo = srNo + 1;
					String deptCode = new String();

					int count = 0;

					for (int i = 0; i < deptSubDeptList.size(); i++) {

						if (deptIds.get(j) == deptSubDeptList.get(i).getDeptId()) {
							subDeptTot = subDeptTot + deptSubDeptList.get(i).getIssueQtyValue();
							deptCode = deptSubDeptList.get(i).getDeptCode();

							if (deptSubDeptList.get(i).getIssueQtyValue() > 0) {
								PdfPCell cell;
								count = count + 1;

								if (count == 1) {
									srNo = srNo + 1;
									cell = new PdfPCell(new Phrase("" + srNo, headFont));

									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setPadding(3);
									table.addCell(cell);

									cell = new PdfPCell(new Phrase("" + deptCode, headFont));

									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setPaddingRight(2);
									cell.setPadding(3);
									table.addCell(cell);

								} else {

									cell = new PdfPCell(new Phrase("", headFont));

									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setPadding(3);
									table.addCell(cell);

									cell = new PdfPCell(new Phrase("", headFont));

									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell.setPaddingRight(2);
									cell.setPadding(3);
									table.addCell(cell);

								}

								cell = new PdfPCell(new Phrase("" + deptSubDeptList.get(i).getSubDeptCode(), headFont));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setPaddingRight(2);
								cell.setPadding(3);
								table.addCell(cell);

								cell = new PdfPCell(new Phrase(
										"" + df.format(deptSubDeptList.get(i).getIssueQtyValue()), headFont));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setPaddingRight(2);
								cell.setPadding(3);
								table.addCell(cell);
							} // end of if subDeptTot>0
						} // end of if

					} // end of inner for

					PdfPCell cell;

					if (count == 0) {

						cell = new PdfPCell(new Phrase("", headFont));

						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("", headFont));

						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("", headFont));

						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("", headFont));

						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

					} else {

						cell = new PdfPCell(new Phrase("", headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("Sub Toatal", headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("", headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + df.format(subDeptTot), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

					}

					grandTotal = grandTotal + subDeptTot;
				}

				Company companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

				document.open();

				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph reportName = new Paragraph("Dept Sub Dept Issue Report", f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				Paragraph headingDate = new Paragraph("From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
				document.add(ex3);
				table.setHeaderRows(1);
				document.add(table);

				Paragraph gt = new Paragraph("Grand Total " + df.format(grandTotal));
				gt.setAlignment(Element.ALIGN_RIGHT);
				document.add(gt);

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getIssueReportDeptWise", method = RequestMethod.GET)
	public @ResponseBody List<IssueDeptWise> getIssueReportDeptWise(HttpServletRequest request,
			HttpServletResponse response) {

		List<IssueDeptWise> deptWiselist = new ArrayList<IssueDeptWise>();

		try {

			if (request.getParameter("fromDate") == null || request.getParameter("toDate") == null
					|| request.getParameter("typeId") == null || request.getParameter("isDev") == null
					|| request.getParameter("deptId") == null) {

			} else {
				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");
				typeId = Integer.parseInt(request.getParameter("typeId"));
				isDev = Integer.parseInt(request.getParameter("isDev"));
				deptId = Integer.parseInt(request.getParameter("deptId"));
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));
				map.add("typeId", typeId);
				map.add("isDev", isDev);
				map.add("deptId", deptId);
				if (catId == 0) {
					for (int i = 0; i < categoryList.size(); i++) {
						catIds = catIds + categoryList.get(i).getCatId() + ",";
					}
					map.add("catIds", catIds.substring(0, catIds.length() - 1));
				} else {
					map.add("catIds", catId);
				}
				System.out.println(map);
				IssueDeptWise[] IssueDeptWise = rest.postForObject(Constants.url + "/issueDepartmentWiseReport", map,
						IssueDeptWise[].class);
				deptWiselist = new ArrayList<IssueDeptWise>(Arrays.asList(IssueDeptWise));

				deptWiselistGlobal = deptWiselist;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptWiselist;
	}

	@RequestMapping(value = "/issueReportSubDeptWise/{deptId}/{typeName}/{isDevName}/{deptName}", method = RequestMethod.GET)
	public ModelAndView issueReportSubDeptWise(@PathVariable int deptId, @PathVariable String typeName,
			@PathVariable String isDevName, @PathVariable String deptName, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/issueReportSubDeptWise");
		try {
			List<IssueDeptWise> deptWiselist = new ArrayList<IssueDeptWise>();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("typeId", typeId);
			map.add("isDev", isDev);
			map.add("deptId", deptId);
			if (catId == 0) {

				map.add("catIds", catIds.substring(0, catIds.length() - 1));
			} else {
				map.add("catIds", catId);
			}
			System.out.println(map);
			IssueDeptWise[] IssueDeptWise = rest.postForObject(Constants.url + "/issueSubDepartmentWiseReport", map,
					IssueDeptWise[].class);
			deptWiselist = new ArrayList<IssueDeptWise>(Arrays.asList(IssueDeptWise));

			model.addObject("deptWiselist", deptWiselist);
			model.addObject("deptId", deptId);
			model.addObject("typeName", typeName);
			model.addObject("isDevName", isDevName);
			model.addObject("deptName", deptName);
			deptWiselistGlobal = deptWiselist;
			// ------------------------ Export To
			// Excel--------------------------------------
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("SUB-DEPARMENT NAME");
			rowData.add("ISSUE QTY");
			rowData.add("ISSUE VALUE");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < deptWiselist.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");
				rowData.add(deptWiselist.get(i).getDeptCode());
				rowData.add("" + deptWiselist.get(i).getIssueQty());
				rowData.add("" + deptWiselist.get(i).getIssueQtyValue());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "SubDeptWiseConsumption(Issues)");
			// ------------------------------------END------------------------------------------

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/issueReportSubDeptWiseReport", method = RequestMethod.GET)
	public @ResponseBody List<IssueDeptWise> issueReportSubDeptWiseReport(HttpServletRequest request,
			HttpServletResponse response) {
		List<IssueDeptWise> deptWiselist = new ArrayList<IssueDeptWise>();

		try {
			deptId = Integer.parseInt(request.getParameter("deptId"));
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("typeId", typeId);
			map.add("isDev", isDev);
			map.add("deptId", deptId);
			if (catId == 0) {

				map.add("catIds", catIds.substring(0, catIds.length() - 1));
			} else {
				map.add("catIds", catId);
			}
			System.out.println(map);
			IssueDeptWise[] IssueDeptWise = rest.postForObject(Constants.url + "/issueSubDepartmentWiseReport", map,
					IssueDeptWise[].class);
			deptWiselist = new ArrayList<IssueDeptWise>(Arrays.asList(IssueDeptWise));
			deptWiselistGlobal = deptWiselist;
			System.err.println(deptWiselistGlobal.toString());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return deptWiselist;
	}

	@RequestMapping(value = "/issueReportItemWise/{subDeptId}/{typeName}/{isDevName}/{deptName}/{subDeptCode}", method = RequestMethod.GET)
	public ModelAndView issueReportItemWise(@PathVariable int subDeptId, @PathVariable String typeName,
			@PathVariable String isDevName, @PathVariable String deptName, @PathVariable String subDeptCode,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/issueReportItemWise");
		try {
			List<IssueDeptWise> itemWiselist = new ArrayList<IssueDeptWise>();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("typeId", typeId);
			map.add("isDev", isDev);
			map.add("subDept", subDeptId);
			if (catId == 0) {

				map.add("catIds", catIds.substring(0, catIds.length() - 1));
			} else {
				map.add("catIds", catId);
			}
			System.out.println(map);
			IssueDeptWise[] IssueDeptWise = rest.postForObject(Constants.url + "/issueItemWiseReportBySubDept", map,
					IssueDeptWise[].class);
			itemWiselist = new ArrayList<IssueDeptWise>(Arrays.asList(IssueDeptWise));
			deptWiselistGlobal = itemWiselist;
			model.addObject("itemWiselist", itemWiselist);
			model.addObject("typeName", typeName);
			model.addObject("isDevName", isDevName);
			model.addObject("deptName", deptName);
			model.addObject("subDeptCode", subDeptCode);
			// ------------------------ Export To
			// Excel--------------------------------------
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("ITEM NAME");
			rowData.add("ISSUE QTY");
			rowData.add("ISSUE VALUE");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < itemWiselist.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");
				rowData.add(itemWiselist.get(i).getDeptCode());
				rowData.add("" + itemWiselist.get(i).getIssueQty());
				rowData.add("" + itemWiselist.get(i).getIssueQtyValue());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "ItemWiseConsumption(Issues)");
			// ------------------------------------END------------------------------------------

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/issueReportDeptWisePDF/{typeName}/{isDev}", method = RequestMethod.GET)
	public void showProdByOrderPdf(@PathVariable String typeName, @PathVariable String isDev,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A4);
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

			float issueQty = 0;
			float issueValue = 0;

			PdfPTable table = new PdfPTable(4);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 1.7f, 1.0f, 0.9f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR.NO.", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("DEPARTMENT NAME", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!deptWiselistGlobal.isEmpty()) {
					for (int k = 0; k < deptWiselistGlobal.size(); k++) {
						if (deptWiselistGlobal.get(k).getIssueQty() > 0
								|| deptWiselistGlobal.get(k).getIssueQtyValue() > 0) {

							index++;

							PdfPCell cell;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(deptWiselistGlobal.get(k).getDeptCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(
									new Phrase("" + df.format(deptWiselistGlobal.get(k).getIssueQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							issueQty = issueQty + deptWiselistGlobal.get(k).getIssueQty();

							cell = new PdfPCell(
									new Phrase("" + df.format(deptWiselistGlobal.get(k).getIssueQtyValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							issueValue = issueValue + deptWiselistGlobal.get(k).getIssueQtyValue();

						}
					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(issueQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(issueValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph report = new Paragraph("Department Wise Consumption(Issues) Report ", f1);
				report.setAlignment(Element.ALIGN_CENTER);
				document.add(report);

				Paragraph type = new Paragraph("Type : " + typeName + ", Is Dev : " + isDev, f1);
				type.setAlignment(Element.ALIGN_CENTER);
				document.add(type);

				Paragraph headingDate = new Paragraph(" From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/issueReportSubDeptWisePDF/{typeName}/{isDevName}/{deptName}", method = RequestMethod.GET)
	public void issueReportSubDeptWisePDF(@PathVariable String typeName, @PathVariable String isDevName,
			@PathVariable String deptName, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A4);
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

			float issueQty = 0;
			float issueValue = 0;

			PdfPTable table = new PdfPTable(4);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 1.7f, 1.0f, 0.9f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR.NO.", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("DEPARTMENT NAME", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!deptWiselistGlobal.isEmpty()) {
					for (int k = 0; k < deptWiselistGlobal.size(); k++) {

						if (deptWiselistGlobal.get(k).getIssueQty() > 0
								|| deptWiselistGlobal.get(k).getIssueQtyValue() > 0) {

							index++;

							PdfPCell cell;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(deptWiselistGlobal.get(k).getDeptCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(
									new Phrase("" + df.format(deptWiselistGlobal.get(k).getIssueQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							issueQty = issueQty + deptWiselistGlobal.get(k).getIssueQty();

							cell = new PdfPCell(
									new Phrase("" + df.format(deptWiselistGlobal.get(k).getIssueQtyValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							issueValue = issueValue + deptWiselistGlobal.get(k).getIssueQtyValue();
						}

					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(issueQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(issueValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph report = new Paragraph("Sub-Department Wise Consumption(Issues) Report", f1);
				report.setAlignment(Element.ALIGN_CENTER);
				document.add(report);

				Paragraph type = new Paragraph(
						"Department : " + deptName + ", Type : " + typeName + ", Is Dev : " + isDevName, f1);
				type.setAlignment(Element.ALIGN_CENTER);
				document.add(type);

				Paragraph headingDate = new Paragraph("From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/issueReportItemWisePDF/{typeName}/{isDevName}/{subDeptName}", method = RequestMethod.GET)
	public void issueReportItemWisePDF(@PathVariable String typeName, @PathVariable String isDevName,
			@PathVariable String subDeptName, HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A4);
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

			float issueQty = 0;
			float issueValue = 0;

			PdfPTable table = new PdfPTable(4);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 4.9f, 1.3f, 1.3f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR.", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ITEM NAME", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ISSUE VALUE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!deptWiselistGlobal.isEmpty()) {
					for (int k = 0; k < deptWiselistGlobal.size(); k++) {

						if (deptWiselistGlobal.get(k).getIssueQty() > 0
								|| deptWiselistGlobal.get(k).getIssueQtyValue() > 0) {

							index++;

							PdfPCell cell;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(deptWiselistGlobal.get(k).getDeptCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(
									new Phrase("" + df.format(deptWiselistGlobal.get(k).getIssueQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							issueQty = issueQty + deptWiselistGlobal.get(k).getIssueQty();

							cell = new PdfPCell(
									new Phrase("" + df.format(deptWiselistGlobal.get(k).getIssueQtyValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							issueValue = issueValue + deptWiselistGlobal.get(k).getIssueQtyValue();
						}

					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(issueQty), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(issueValue), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph report = new Paragraph("Item Wise Consumption(Issues) Report", f1);
				report.setAlignment(Element.ALIGN_CENTER);
				document.add(report);

				Paragraph type = new Paragraph(" Sub-Dept: " + subDeptName + ", Type : "
						+ typeName + ", Is Dev : " + isDevName, f1);
				type.setAlignment(Element.ALIGN_CENTER);
				document.add(type);

				Paragraph headingDate = new Paragraph("From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/issueMonthWieReport", method = RequestMethod.GET)
	public ModelAndView issueMonthWieReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/issueMonthWieReport");
		try {
			List<IssueMonthWiseList> list = new ArrayList<IssueMonthWiseList>();
			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);

			if (request.getParameter("typeId") == null || request.getParameter("isDev") == null) {

				typeId = 0;
				isDev = -1;

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				/*
				 * map.add("fromDate",DateConvertor.convertToYMD(fromDate));
				 * map.add("toDate",DateConvertor.convertToYMD(toDate));
				 */
				
				Date date = new Date();
				LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				 year  = localDate.getYear();
				
				System.out.println("year"+year);
				
				
				map.add("typeId", typeId);
				map.add("isDev", isDev);
				map.add("isDev", isDev);
				map.add("year", year);
				System.out.println(map);
				IssueMonthWiseList[] issueMonthWiseList = rest
						.postForObject(Constants.url + "/issueMonthWiseReportByDept", map, IssueMonthWiseList[].class);
				list = new ArrayList<IssueMonthWiseList>(Arrays.asList(issueMonthWiseList));

				System.out.println("list " + list);

				for (int i = 0; i < list.size(); i++) {

					model.addObject("month" + i, list.get(i));
				}
				listGlobal = list;
				model.addObject("list", list);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
				model.addObject("typeId", typeId);
				model.addObject("isDevelompent", isDev);

				Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
				deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));
				model.addObject("deparmentList", deparmentList);
			} else {
				/*
				 * fromDate = request.getParameter("fromDate"); toDate =
				 * request.getParameter("toDate");
				 */
				typeId = Integer.parseInt(request.getParameter("typeId"));
				isDev = Integer.parseInt(request.getParameter("isDev"));
				year = Integer.parseInt(request.getParameter("Year"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				/*
				 * map.add("fromDate",DateConvertor.convertToYMD(fromDate));
				 * map.add("toDate",DateConvertor.convertToYMD(toDate));
				 */
				map.add("typeId", typeId);
				map.add("isDev", isDev);
				map.add("year", year);
				System.out.println(map);
				IssueMonthWiseList[] issueMonthWiseList = rest
						.postForObject(Constants.url + "/issueMonthWiseReportByDept", map, IssueMonthWiseList[].class);
				list = new ArrayList<IssueMonthWiseList>(Arrays.asList(issueMonthWiseList));

				System.out.println("list " + list);

				for (int i = 0; i < list.size(); i++) {

					model.addObject("month" + i, list.get(i));
				}
				listGlobal = list;
				model.addObject("list", list);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
				model.addObject("typeId", typeId);
				model.addObject("isDevelompent", isDev);

				Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
				deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));
				model.addObject("deparmentList", deparmentList);

			}

			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

			// ------------------------ Export To
			// Excel--------------------------------------
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			/*
			 * rowData.add("SR. No"); rowData.add("DEPARMENT NAME");
			 * rowData.add("APR ISSUE QTY"); rowData.add("APR ISSUE VALUE");
			 * rowData.add("MAY ISSUE QTY"); rowData.add("MAY ISSUE VALUE");
			 * rowData.add("JUNE ISSUE QTY"); rowData.add("JUNE ISSUE VALUE");
			 * rowData.add("JULY ISSUE QTY"); rowData.add("JULY ISSUE VALUE");
			 * rowData.add("AUGUST ISSUE QTY"); rowData.add("AUGUST ISSUE VALUE");
			 * rowData.add("SEPTEMBR ISSUE QTY"); rowData.add("SEPTEMBR ISSUE VALUE");
			 * rowData.add("OCTOMBER ISSUE QTY"); rowData.add("OCTOMBER ISSUE VALUE");
			 * rowData.add("NOVEMBER ISSUE QTY"); rowData.add("NOVEMBER ISSUE VALUE");
			 * rowData.add("DECEMBER ISSUE QTY"); rowData.add("DECEMBER ISSUE VALUE");
			 * rowData.add("JANUARY ISSUE QTY"); rowData.add("JANUARY ISSUE VALUE");
			 * rowData.add("FEBRUARY ISSUE QTY"); rowData.add("FEBRUARY ISSUE VALUE");
			 * rowData.add("MARCH ISSUE QTY"); rowData.add("MARCH ISSUE VALUE");
			 */

			rowData.add("SR. No");
			rowData.add("DEPARMENT NAME");
			rowData.add("APR ISSUE VALUE");
			rowData.add("MAY ISSUE VALUE");
			rowData.add("JUNE ISSUE VALUE");
			rowData.add("JULY ISSUE VALUE");
			rowData.add("AUGUST ISSUE VALUE");
			rowData.add("SEPTEMBR ISSUE VALUE");
			rowData.add("OCTOMBER ISSUE VALUE");
			rowData.add("NOVEMBER ISSUE VALUE");
			rowData.add("DECEMBER ISSUE VALUE");
			rowData.add("JANUARY ISSUE VALUE");
			rowData.add("FEBRUARY ISSUE VALUE");
			rowData.add("MARCH ISSUE VALUE");

			expoExcel.setRowData(rowData);

			exportToExcelList.add(expoExcel);
			for (int i = 0; i < deparmentList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");
				rowData.add(deparmentList.get(i).getDeptCode() + " " + deparmentList.get(i).getDeptDesc());
				for (int k = 0; k < list.size(); k++) {
					List<MonthWiseIssueReport> monthList = list.get(k).getMonthList();

					for (int j = 0; j < monthList.size(); j++) {
						if (monthList.get(j).getDeptId() == deparmentList.get(i).getDeptId()) {
							// rowData.add(""+monthList.get(j).getIssueQty());
							rowData.add("" + monthList.get(j).getIssueQtyValue());
						}
					}

				}

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "MonthWiseConsumption(Issues)");
			// ------------------------------------END------------------------------------------

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/listForIssueGraphDeptWise", method = RequestMethod.GET)
	public @ResponseBody List<IssueMonthWiseList> listForIssueGraphDeptWise(HttpServletRequest request,
			HttpServletResponse response) {

		return listGlobal;
	}

	@RequestMapping(value = "/getDeptListForGraph", method = RequestMethod.GET)
	public @ResponseBody List<Dept> getDeptListForGraph(HttpServletRequest request, HttpServletResponse response) {

		return deparmentList;
	}

	@RequestMapping(value = "/issueMonthWieReportPdf/{typeName}/{isDevName}", method = RequestMethod.GET)
	public void issueMonthWieReportPdf(@PathVariable String typeName, @PathVariable String isDevName,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
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

			PdfPTable table = new PdfPTable(15);
			try {

				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 1.7f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
						1.0f, 1.0f, 1.0f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);
				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR.", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Department Name", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("APR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);
				hcell = new PdfPCell(new Phrase("MAY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);
				hcell = new PdfPCell(new Phrase("JUN", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);
				hcell = new PdfPCell(new Phrase("JUL", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);
				hcell = new PdfPCell(new Phrase("AUG", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("SEP", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("OCT", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("NOV", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("DEC", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JAN", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("FEB", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MAR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				float aprTotal = 0;
				float mayTotal = 0;
				float junTotal = 0;
				float julTotal = 0;
				float augTotal = 0;
				float sepTotal = 0;
				float octTotal = 0;
				float novTotal = 0;
				float decTotal = 0;
				float janTotal = 0;
				float febTotal = 0;
				float marTotal = 0;

				int index = 0;
				if (!deparmentList.isEmpty()) {
					for (int k = 0; k < deparmentList.size(); k++) {

						float issueQtyValue = 0;

						index++;

						PdfPCell cell;

						cell = new PdfPCell(new Phrase("" + index, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(
								deparmentList.get(k).getDeptCode() + " " + deparmentList.get(k).getDeptDesc(),
								headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						for (int j = 0; j < listGlobal.size(); j++) {

							List<MonthWiseIssueReport> monthList = listGlobal.get(j).getMonthList();

							for (int l = 0; l < monthList.size(); l++) {
								if (monthList.get(l).getDeptId() == deparmentList.get(k).getDeptId()) {

									cell = new PdfPCell(
											new Phrase("" + df.format(monthList.get(l).getIssueQtyValue()), headFont));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setPaddingRight(2);
									cell.setPadding(3);
									table.addCell(cell);
									issueQtyValue = issueQtyValue + monthList.get(l).getIssueQtyValue();

									if (monthList.get(l).getMonthNo() == 1) {
										janTotal = janTotal + monthList.get(l).getIssueQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 2) {
										febTotal = febTotal + monthList.get(l).getIssueQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 3) {
										marTotal = marTotal + monthList.get(l).getIssueQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 4) {
										aprTotal = aprTotal + monthList.get(l).getIssueQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 5) {
										mayTotal = mayTotal + monthList.get(l).getIssueQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 6) {
										junTotal = junTotal + monthList.get(l).getIssueQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 7) {
										julTotal = julTotal + monthList.get(l).getIssueQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 8) {
										augTotal = augTotal + monthList.get(l).getIssueQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 9) {
										sepTotal = sepTotal + monthList.get(l).getIssueQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 10) {
										octTotal = octTotal + monthList.get(l).getIssueQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 11) {
										novTotal = novTotal + monthList.get(l).getIssueQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 12) {
										decTotal = decTotal + monthList.get(l).getIssueQtyValue();
									}
								}
							}
						}

						cell = new PdfPCell(new Phrase("" + df.format(issueQtyValue), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPadding(3);
						table.addCell(cell);

					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(aprTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(mayTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + df.format(junTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + df.format(julTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + df.format(augTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(sepTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(octTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(novTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(decTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(janTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(febTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(marTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format((marTotal + aprTotal + mayTotal + junTotal + julTotal
						+ augTotal + sepTotal + octTotal + novTotal + decTotal + febTotal + janTotal)), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph report = new Paragraph("Month Wise Consumption(Issues)", f1);
				report.setAlignment(Element.ALIGN_CENTER);
				document.add(report);

				Paragraph type = new Paragraph("Type : " + typeName + ", Is Dev :" + isDevName, f1);
				type.setAlignment(Element.ALIGN_CENTER);
				document.add(type);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<IssueMonthWiseList> subDeptWiselistForPdf = new ArrayList<IssueMonthWiseList>();
	List<GetSubDept> subDeptList = new ArrayList<>();
	int deptIdForPdf = 0;

	@RequestMapping(value = "/issueMonthSubDeptWieReportByDeptId/{deptId}/{typeName}/{isDevName}/{deptName}", method = RequestMethod.GET)
	public ModelAndView issueMonthSubDeptWieReportByDeptId(@PathVariable int deptId, @PathVariable String typeName,
			@PathVariable String isDevName, @PathVariable String deptName, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/issueReportMonthSubDeptWise");
		try {
			List<IssueMonthWiseList> subDeptWiselist = new ArrayList<IssueMonthWiseList>();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("typeId", typeId);
			map.add("isDev", isDev);
			map.add("deptId", deptId);
			map.add("year", year);
			System.out.println(map);
			IssueMonthWiseList[] issueMonthWiseList = rest.postForObject(
					Constants.url + "/issueMonthSubDeptWiseReportByDeptId", map, IssueMonthWiseList[].class);
			subDeptWiselist = new ArrayList<IssueMonthWiseList>(Arrays.asList(issueMonthWiseList));
			subDeptWiselistForPdf = subDeptWiselist;
			model.addObject("list", subDeptWiselist);

			GetSubDept[] getSubDept = rest.getForObject(Constants.url + "/getAllSubDept", GetSubDept[].class);
			subDeptList = new ArrayList<GetSubDept>(Arrays.asList(getSubDept));

			model.addObject("subDeptList", subDeptList);

			model.addObject("deptId", deptId);
			model.addObject("type", typeName);
			model.addObject("isDevName", isDevName);
			model.addObject("deptName", deptName);
			deptIdForPdf = deptId;
			// ------------------------ Export To
			// Excel--------------------------------------
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();
			;

			/*
			 * rowData.add("SR. No"); rowData.add("SUB DEPARMENT NAME");
			 * rowData.add("APR ISSUE QTY"); rowData.add("APR ISSUE VALUE");
			 * rowData.add("MAY ISSUE QTY"); rowData.add("MAY ISSUE VALUE");
			 * rowData.add("JUNE ISSUE QTY"); rowData.add("JUNE ISSUE VALUE");
			 * rowData.add("JULY ISSUE QTY"); rowData.add("JULY ISSUE VALUE");
			 * rowData.add("AUGUST ISSUE QTY"); rowData.add("AUGUST ISSUE VALUE");
			 * rowData.add("SEPTEMBR ISSUE QTY"); rowData.add("SEPTEMBR ISSUE VALUE");
			 * rowData.add("OCTOMBER ISSUE QTY"); rowData.add("OCTOMBER ISSUE VALUE");
			 * rowData.add("NOVEMBER ISSUE QTY"); rowData.add("NOVEMBER ISSUE VALUE");
			 * rowData.add("DECEMBER ISSUE QTY"); rowData.add("DECEMBER ISSUE VALUE");
			 * rowData.add("JANUARY ISSUE QTY"); rowData.add("JANUARY ISSUE VALUE");
			 * rowData.add("FEBRUARY ISSUE QTY"); rowData.add("FEBRUARY ISSUE VALUE");
			 * rowData.add("MARCH ISSUE QTY"); rowData.add("MARCH ISSUE VALUE");
			 */

			rowData.add("SR. No");
			rowData.add("SUB DEPARMENT NAME");
			rowData.add("APR ISSUE VALUE");
			rowData.add("MAY ISSUE VALUE");
			rowData.add("JUNE ISSUE VALUE");
			rowData.add("JULY ISSUE VALUE");
			rowData.add("AUGUST ISSUE VALUE");
			rowData.add("SEPTEMBR ISSUE VALUE");
			rowData.add("OCTOMBER ISSUE VALUE");
			rowData.add("NOVEMBER ISSUE VALUE");
			rowData.add("DECEMBER ISSUE VALUE");
			rowData.add("JANUARY ISSUE VALUE");
			rowData.add("FEBRUARY ISSUE VALUE");
			rowData.add("MARCH ISSUE VALUE");

			expoExcel.setRowData(rowData);

			exportToExcelList.add(expoExcel);
			int index = 0;
			for (int i = 0; i < subDeptList.size(); i++) {

				if (deptIdForPdf == subDeptList.get(i).getDeptId()) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					index++;
					rowData.add((index) + "");
					rowData.add(subDeptList.get(i).getSubDeptCode() + " " + subDeptList.get(i).getSubDeptDesc());
					for (int k = 0; k < subDeptWiselist.size(); k++) {
						List<MonthSubDeptWiseIssueReport> monthList = subDeptWiselist.get(k).getMonthSubDeptList();

						for (int j = 0; j < monthList.size(); j++) {
							if (monthList.get(j).getSubDeptId() == subDeptList.get(i).getSubDeptId()) {
								// rowData.add(""+monthList.get(j).getIssueQty());
								rowData.add("" + monthList.get(j).getIssueQtyValue());
							}
						}

					}

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
				}
			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "SubDeptMonthWiseConsumption(Issues)");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/listForIssueMonthGraphSubDeptWise", method = RequestMethod.GET)
	public @ResponseBody List<IssueMonthWiseList> listForIssueMonthGraphSubDeptWise(HttpServletRequest request,
			HttpServletResponse response) {

		return subDeptWiselistForPdf;
	}

	@RequestMapping(value = "/getSubDeptListForGraph", method = RequestMethod.GET)
	public @ResponseBody List<GetSubDept> getSubDeptListForGraph(HttpServletRequest request,
			HttpServletResponse response) {

		return subDeptList;
	}

	@RequestMapping(value = "/issueMonthSubDeptWiseReportPdf/{typeName}/{isDevName}/{deptName}", method = RequestMethod.GET)
	public void issueMonthSubDeptWiseReportPdf(@PathVariable String typeName, @PathVariable String isDevName,
			@PathVariable String deptName, HttpServletRequest request, HttpServletResponse response)
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

			float aprTotal = 0;
			float mayTotal = 0;
			float junTotal = 0;
			float julTotal = 0;
			float augTotal = 0;
			float sepTotal = 0;
			float octTotal = 0;
			float novTotal = 0;
			float decTotal = 0;
			float janTotal = 0;
			float febTotal = 0;
			float marTotal = 0;

			PdfPTable table = new PdfPTable(15);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 1.7f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
						1.0f, 1.0f, 1.0f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);
				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR.", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Sub Department Name", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("APR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MAY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JUN", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JUL", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("AUG", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("SEP", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("OCT", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("NOV", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("DEC", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JAN", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("FEB", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MAR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Total", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!subDeptWiselistForPdf.isEmpty()) {
					for (int k = 0; k < subDeptList.size(); k++) {
						if (deptIdForPdf == subDeptList.get(k).getDeptId()) {
							index++;
							float issueQtyValue = 0;
							PdfPCell cell;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(
									subDeptList.get(k).getSubDeptCode() + " " + subDeptList.get(k).getSubDeptDesc(),
									headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							for (int j = 0; j < subDeptWiselistForPdf.size(); j++) {

								List<MonthSubDeptWiseIssueReport> monthList = subDeptWiselistForPdf.get(j)
										.getMonthSubDeptList();

								for (int l = 0; l < monthList.size(); l++) {
									if (monthList.get(l).getSubDeptId() == subDeptList.get(k).getSubDeptId()) {

										cell = new PdfPCell(
												new Phrase("" + monthList.get(l).getIssueQtyValue(), headFont));
										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
										cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
										cell.setPaddingRight(2);
										cell.setPadding(3);
										table.addCell(cell);
										issueQtyValue = issueQtyValue + monthList.get(l).getIssueQtyValue();

										if (monthList.get(l).getMonthNo() == 1) {
											janTotal = janTotal + monthList.get(l).getIssueQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 2) {
											febTotal = febTotal + monthList.get(l).getIssueQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 3) {
											marTotal = marTotal + monthList.get(l).getIssueQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 4) {
											aprTotal = aprTotal + monthList.get(l).getIssueQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 5) {
											mayTotal = mayTotal + monthList.get(l).getIssueQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 6) {
											junTotal = junTotal + monthList.get(l).getIssueQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 7) {
											julTotal = julTotal + monthList.get(l).getIssueQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 8) {
											augTotal = augTotal + monthList.get(l).getIssueQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 9) {
											sepTotal = sepTotal + monthList.get(l).getIssueQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 10) {
											octTotal = octTotal + monthList.get(l).getIssueQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 11) {
											novTotal = novTotal + monthList.get(l).getIssueQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 12) {
											decTotal = decTotal + monthList.get(l).getIssueQtyValue();
										}
									}
								}
							}

							cell = new PdfPCell(new Phrase("" + df.format(issueQtyValue), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPadding(3);
							table.addCell(cell);

						}
					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(aprTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(mayTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(junTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(julTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(augTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(sepTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(octTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(novTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(decTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(janTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(febTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(marTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format((marTotal + aprTotal + mayTotal + junTotal + julTotal
						+ augTotal + sepTotal + octTotal + novTotal + decTotal + febTotal + janTotal)), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph headingDate = new Paragraph("Sub Dept Month Wise Consumption(Issues)", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph type = new Paragraph(
						"Department: " + deptName + ", Type : " + typeName + ", Is Dev : " + isDevName, f1);
				type.setAlignment(Element.ALIGN_CENTER);
				document.add(type);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<IssueMonthWiseList> issueItemWiselistForPdf = new ArrayList<IssueMonthWiseList>();
	List<GetItem> itemListforPdf = new ArrayList<>();

	@RequestMapping(value = "/issueMonthItemWieReportBySubDeptId/{subDeptId}", method = RequestMethod.GET)
	public ModelAndView issueMonthItemWieReportBySubDeptId(@PathVariable int subDeptId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/issueReportMonthItemWise");
		try {
			List<IssueMonthWiseList> subDeptWiselist = new ArrayList<IssueMonthWiseList>();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("typeId", typeId);
			map.add("isDev", isDev);
			map.add("subDeptId", subDeptId);
			map.add("year", year);
			System.out.println(map);
			IssueMonthWiseList[] issueMonthWiseList = rest.postForObject(
					Constants.url + "/issueMonthItemWiseReportBySubDeptId", map, IssueMonthWiseList[].class);
			subDeptWiselist = new ArrayList<IssueMonthWiseList>(Arrays.asList(issueMonthWiseList));
			issueItemWiselistForPdf = subDeptWiselist;
			model.addObject("list", subDeptWiselist);

			GetItem[] item = rest.getForObject(Constants.url + "/getAllItems", GetItem[].class);
			List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(item));
			model.addObject("itemList", itemList);
			itemListforPdf = itemList;
			// ------------------------ Export To
			// Excel--------------------------------------
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			/*
			 * rowData.add("SR. No"); rowData.add("ITEM NAME");
			 * rowData.add("APR ISSUE QTY"); rowData.add("APR ISSUE VALUE");
			 * rowData.add("MAY ISSUE QTY"); rowData.add("MAY ISSUE VALUE");
			 * rowData.add("JUNE ISSUE QTY"); rowData.add("JUNE ISSUE VALUE");
			 * rowData.add("JULY ISSUE QTY"); rowData.add("JULY ISSUE VALUE");
			 * rowData.add("AUGUST ISSUE QTY"); rowData.add("AUGUST ISSUE VALUE");
			 * rowData.add("SEPTEMBR ISSUE QTY"); rowData.add("SEPTEMBR ISSUE VALUE");
			 * rowData.add("OCTOMBER ISSUE QTY"); rowData.add("OCTOMBER ISSUE VALUE");
			 * rowData.add("NOVEMBER ISSUE QTY"); rowData.add("NOVEMBER ISSUE VALUE");
			 * rowData.add("DECEMBER ISSUE QTY"); rowData.add("DECEMBER ISSUE VALUE");
			 * rowData.add("JANUARY ISSUE QTY"); rowData.add("JANUARY ISSUE VALUE");
			 * rowData.add("FEBRUARY ISSUE QTY"); rowData.add("FEBRUARY ISSUE VALUE");
			 * rowData.add("MARCH ISSUE QTY"); rowData.add("MARCH ISSUE VALUE");
			 */

			rowData.add("SR. No");
			rowData.add("ITEM NAME");
			rowData.add("APR ISSUE VALUE");
			rowData.add("MAY ISSUE VALUE");
			rowData.add("JUNE ISSUE VALUE");
			rowData.add("JULY ISSUE VALUE");
			rowData.add("AUGUST ISSUE VALUE");
			rowData.add("SEPTEMBR ISSUE VALUE");
			rowData.add("OCTOMBER ISSUE VALUE");
			rowData.add("NOVEMBER ISSUE VALUE");
			rowData.add("DECEMBER ISSUE VALUE");
			rowData.add("JANUARY ISSUE VALUE");
			rowData.add("FEBRUARY ISSUE VALUE");
			rowData.add("MARCH ISSUE VALUE");

			expoExcel.setRowData(rowData);

			exportToExcelList.add(expoExcel);
			int index = 0;
			for (int i = 0; i < itemList.size(); i++) {

				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				index++;
				rowData.add((index) + "");
				rowData.add(itemList.get(i).getItemCode() + " " + itemList.get(i).getItemDesc());
				for (int k = 0; k < subDeptWiselist.size(); k++) {
					List<MonthSubDeptWiseIssueReport> monthList = subDeptWiselist.get(k).getMonthSubDeptList();

					for (int j = 0; j < monthList.size(); j++) {
						if (monthList.get(j).getSubDeptId() == itemList.get(i).getItemId()) {
							// rowData.add(""+monthList.get(j).getIssueQty());
							rowData.add("" + monthList.get(j).getIssueQtyValue());
						}
					}

				}

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "ItemIssueMonthWiseConsumption(Issues)");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/issueMonthItemWiseReportPdf", method = RequestMethod.GET)
	public void issueMonthItemWiseReportPdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A3);
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

			PdfPTable table = new PdfPTable(14);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 3.0f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f,
						0.6f, 0.6f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 6, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);
				PdfPCell hcell = new PdfPCell();

				/*
				 * hcell.setPadding(4); hcell = new PdfPCell(new Phrase("SR.", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("Item Name", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("APR", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell); hcell = new PdfPCell(new Phrase("MAY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell); hcell = new PdfPCell(new Phrase("JUN", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell); hcell = new PdfPCell(new Phrase("JUL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell); hcell = new PdfPCell(new Phrase("AUG", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell); hcell = new PdfPCell(new Phrase("SEP", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("OCT", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("NOV", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("DEC", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("JAN", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("FEB", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("MAR", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); hcell.setColspan(2);
				 * table.addCell(hcell);
				 * 
				 * 
				 * 
				 * hcell = new PdfPCell(); hcell.setPadding(4); hcell = new PdfPCell(new
				 * Phrase(" ", headFont1)); hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase(" ", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell); hcell = new
				 * PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("QTY", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 * 
				 * hcell = new PdfPCell(new Phrase("VAL", headFont1));
				 * hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * hcell.setBackgroundColor(BaseColor.PINK); table.addCell(hcell);
				 */

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Item Name", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("APR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MAY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JUN", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JUL", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("AUG", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("SEP", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("OCT", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("NOV", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("DEC", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JAN", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("FEB", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MAR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!issueItemWiselistForPdf.isEmpty()) {
					for (int k = 0; k < itemListforPdf.size(); k++) {

						index++;

						PdfPCell cell;

						cell = new PdfPCell(new Phrase("" + index, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(
								itemListforPdf.get(k).getItemCode() + " " + itemListforPdf.get(k).getItemDesc(),
								headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						for (int j = 0; j < issueItemWiselistForPdf.size(); j++) {

							List<MonthSubDeptWiseIssueReport> monthList = issueItemWiselistForPdf.get(j)
									.getMonthSubDeptList();

							for (int l = 0; l < monthList.size(); l++) {
								if (monthList.get(l).getSubDeptId() == itemListforPdf.get(k).getItemId()) {
									/*
									 * cell = new PdfPCell(new Phrase(""+monthList.get(l).getIssueQty(), headFont));
									 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); cell.setPaddingRight(2);
									 * cell.setPadding(3); table.addCell(cell);
									 */
									cell = new PdfPCell(new Phrase("" + monthList.get(l).getIssueQtyValue(), headFont));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setPaddingRight(2);
									cell.setPadding(3);
									table.addCell(cell);
								}
							}
						}

					}

				}

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph headingDate = new Paragraph("Item Month Wise Consumption(Issues)", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<MrnMonthWiseList> mrnCategoryMonthWiseListForPdf = new ArrayList<MrnMonthWiseList>();
	List<Category> categoryList = new ArrayList<Category>();

	@RequestMapping(value = "/mrnMonthCategoryWieReport", method = RequestMethod.GET)
	public ModelAndView mrnMonthCategoryWieReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/mrnMonthCategoryWieReport");
		try {
			List<MrnMonthWiseList> list = new ArrayList<MrnMonthWiseList>();
			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);

			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));
			model.addObject("deparmentList", deparmentList);

			if (request.getParameter("typeId") == null || request.getParameter("isDev") == null) {

				typeId = 0;
				isDev = -1;
				deptId = 0;
				subDeptId = 0;

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				/*
				 * map.add("fromDate",DateConvertor.convertToYMD(fromDate));
				 * map.add("toDate",DateConvertor.convertToYMD(toDate));
				 */
				Date date = new Date();
				LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				 year  = localDate.getYear();
				 int month  = localDate.getMonthValue();
				 System.out.println("year"+year);
				 System.out.println("month"+month);
				 if(month<4)
				 {
					 year=year-1;
					
					 System.out.println("year"+year);	 
				 }
				
				
				
				
				map.add("typeId", typeId);
				map.add("year", year);
				map.add("deptId", deptId);
				map.add("subDeptId", subDeptId);
				if (isDev == -1) {
					map.add("isDev", "0,1");
				} else {
					map.add("isDev", isDev);
				}
				System.out.println(map);
				MrnMonthWiseList[] mrnMonthWiseList = rest.postForObject(Constants.url + "/mrnMonthCategoryWiseReport",
						map, MrnMonthWiseList[].class);
				list = new ArrayList<MrnMonthWiseList>(Arrays.asList(mrnMonthWiseList));

				model.addObject("list", list);
				model.addObject("typeId", typeId);
				model.addObject("isDevelompent", isDev);
				model.addObject("deptId", deptId);
				model.addObject("subDeptId", subDeptId);

				/*
				 * Category[] category = rest.getForObject(Constants.url +
				 * "/getAllCategoryByIsUsed", Category[].class); List<Category> categoryList =
				 * new ArrayList<Category>(Arrays.asList(category));
				 * model.addObject("categoryList", categoryList);
				 */
			} else {
				/*
				 * fromDate = request.getParameter("fromDate"); toDate =
				 * request.getParameter("toDate");
				 */
				typeId = Integer.parseInt(request.getParameter("typeId"));
				isDev = Integer.parseInt(request.getParameter("isDev"));
				deptId = Integer.parseInt(request.getParameter("deptId"));
				subDeptId = Integer.parseInt(request.getParameter("subDeptId"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				/*
				 * map.add("fromDate",DateConvertor.convertToYMD(fromDate));
				 * map.add("toDate",DateConvertor.convertToYMD(toDate));
				 */
				map.add("typeId", typeId);

				map.add("deptId", deptId);
				map.add("subDeptId", subDeptId);
				map.add("year", year);
				if (isDev == -1) {
					map.add("isDev", "0,1");
				} else {
					map.add("isDev", isDev);
				}
				System.out.println(map);
				MrnMonthWiseList[] mrnMonthWiseList = rest.postForObject(Constants.url + "/mrnMonthCategoryWiseReport",
						map, MrnMonthWiseList[].class);
				list = new ArrayList<MrnMonthWiseList>(Arrays.asList(mrnMonthWiseList));

				model.addObject("list", list);
				model.addObject("typeId", typeId);
				model.addObject("isDevelompent", isDev);
				model.addObject("deptId", deptId);
				model.addObject("subDeptId", subDeptId);

			}

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			categoryList = new ArrayList<Category>(Arrays.asList(category));
			model.addObject("categoryList", categoryList);

			mrnCategoryMonthWiseListForPdf = list;
			// ------------------------ Export To
			// Excel--------------------------------------
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();
			;

			/*
			 * rowData.add("SR. No"); rowData.add("CAT NAME"); rowData.add("APR ISSUE QTY");
			 * rowData.add("APR ISSUE VALUE"); rowData.add("MAY ISSUE QTY");
			 * rowData.add("MAY ISSUE VALUE"); rowData.add("JUNE ISSUE QTY");
			 * rowData.add("JUNE ISSUE VALUE"); rowData.add("JULY ISSUE QTY");
			 * rowData.add("JULY ISSUE VALUE"); rowData.add("AUGUST ISSUE QTY");
			 * rowData.add("AUGUST ISSUE VALUE"); rowData.add("SEPTEMBR ISSUE QTY");
			 * rowData.add("SEPTEMBR ISSUE VALUE"); rowData.add("OCTOMBER ISSUE QTY");
			 * rowData.add("OCTOMBER ISSUE VALUE"); rowData.add("NOVEMBER ISSUE QTY");
			 * rowData.add("NOVEMBER ISSUE VALUE"); rowData.add("DECEMBER ISSUE QTY");
			 * rowData.add("DECEMBER ISSUE VALUE"); rowData.add("JANUARY ISSUE QTY");
			 * rowData.add("JANUARY ISSUE VALUE"); rowData.add("FEBRUARY ISSUE QTY");
			 * rowData.add("FEBRUARY ISSUE VALUE"); rowData.add("MARCH ISSUE QTY");
			 * rowData.add("MARCH ISSUE VALUE");
			 */

			rowData.add("SR. No");
			rowData.add("CAT NAME");
			rowData.add("APR ISSUE VALUE");
			rowData.add("MAY ISSUE VALUE");
			rowData.add("JUNE ISSUE VALUE");
			rowData.add("JULY ISSUE VALUE");
			rowData.add("AUGUST ISSUE VALUE");
			rowData.add("SEPTEMBR ISSUE VALUE");
			rowData.add("OCTOMBER ISSUE VALUE");
			rowData.add("NOVEMBER ISSUE VALUE");
			rowData.add("DECEMBER ISSUE VALUE");
			rowData.add("JANUARY ISSUE VALUE");
			rowData.add("FEBRUARY ISSUE VALUE");
			rowData.add("MARCH ISSUE VALUE");

			expoExcel.setRowData(rowData);

			exportToExcelList.add(expoExcel);
			int index = 0;
			for (int i = 0; i < categoryList.size(); i++) {

				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				index++;
				rowData.add((index) + "");
				rowData.add(categoryList.get(i).getCatDesc());
				for (int k = 0; k < list.size(); k++) {
					List<MonthCategoryWiseMrnReport> monthList = list.get(k).getMonthList();

					for (int j = 0; j < monthList.size(); j++) {
						if (monthList.get(j).getCatId() == categoryList.get(i).getCatId()) {
							// rowData.add(""+monthList.get(j).getApproveQty());
							rowData.add("" + monthList.get(j).getApprovedQtyValue());
						}
					}

				}

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "MrnCategoryMonthWiseList");
			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/listForMrnGraphCategoryMonthWise", method = RequestMethod.GET)
	public @ResponseBody List<MrnMonthWiseList> listForMrnGraphCategoryMonthWise(HttpServletRequest request,
			HttpServletResponse response) {

		return mrnCategoryMonthWiseListForPdf;
	}

	@RequestMapping(value = "/getCatListForGraph", method = RequestMethod.GET)
	public @ResponseBody List<Category> getCatListForGraph(HttpServletRequest request, HttpServletResponse response) {

		return categoryList;
	}

	@RequestMapping(value = "/mrnCategoryMonthWiseReportPdf/{typeName}/{isDevName}/{deptName}/{subDeptName}", method = RequestMethod.GET)
	public void mrnCategoryMonthWiseReportPdf(@PathVariable String typeName, @PathVariable String isDevName,
			@PathVariable String deptName, @PathVariable String subDeptName, HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException {
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

			float aprTotal = 0;
			float mayTotal = 0;
			float junTotal = 0;
			float julTotal = 0;
			float augTotal = 0;
			float sepTotal = 0;
			float octTotal = 0;
			float novTotal = 0;
			float decTotal = 0;
			float janTotal = 0;
			float febTotal = 0;
			float marTotal = 0;

			PdfPTable table = new PdfPTable(15);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 1.7f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
						1.0f, 1.0f, 1.0f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);
				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Cat Name", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("APR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MAY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JUN", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JUL", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("AUG", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("SEP", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("OCT", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("NOV", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("DEC", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JAN", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("FEB", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MAR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("TOTAL", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!mrnCategoryMonthWiseListForPdf.isEmpty()) {
					for (int k = 0; k < categoryList.size(); k++) {

						index++;

						float mrnQtyValue = 0;

						PdfPCell cell;

						cell = new PdfPCell(new Phrase("" + index, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(categoryList.get(k).getCatDesc(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						for (int j = 0; j < mrnCategoryMonthWiseListForPdf.size(); j++) {

							List<MonthCategoryWiseMrnReport> monthList = mrnCategoryMonthWiseListForPdf.get(j)
									.getMonthList();

							for (int l = 0; l < monthList.size(); l++) {
								if (monthList.get(l).getCatId() == categoryList.get(k).getCatId()) {

									cell = new PdfPCell(
											new Phrase("" + monthList.get(l).getApprovedQtyValue(), headFont));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setPaddingRight(2);
									cell.setPadding(3);
									table.addCell(cell);

									mrnQtyValue = mrnQtyValue + monthList.get(l).getApprovedQtyValue();

									if (monthList.get(l).getMonthNo() == 1) {
										janTotal = janTotal + monthList.get(l).getApprovedQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 2) {
										febTotal = febTotal + monthList.get(l).getApprovedQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 3) {
										marTotal = marTotal + monthList.get(l).getApprovedQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 4) {
										aprTotal = aprTotal + monthList.get(l).getApprovedQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 5) {
										mayTotal = mayTotal + monthList.get(l).getApprovedQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 6) {
										junTotal = junTotal + monthList.get(l).getApprovedQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 7) {
										julTotal = julTotal + monthList.get(l).getApprovedQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 8) {
										augTotal = augTotal + monthList.get(l).getApprovedQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 9) {
										sepTotal = sepTotal + monthList.get(l).getApprovedQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 10) {
										octTotal = octTotal + monthList.get(l).getApprovedQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 11) {
										novTotal = novTotal + monthList.get(l).getApprovedQtyValue();
									}
									if (monthList.get(l).getMonthNo() == 12) {
										decTotal = decTotal + monthList.get(l).getApprovedQtyValue();
									}
								}
							}
						}

						cell = new PdfPCell(new Phrase("" + df.format(mrnQtyValue), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPadding(3);
						table.addCell(cell);

					}

				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(aprTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(mayTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + df.format(junTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + df.format(julTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + df.format(augTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(sepTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(octTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(novTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(decTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(janTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(febTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(marTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format((marTotal + aprTotal + mayTotal + junTotal + julTotal
						+ augTotal + sepTotal + octTotal + novTotal + decTotal + febTotal + janTotal)), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph headingDate = new Paragraph("Mrn Category Month Wise Report ", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph type = new Paragraph("Type: " + typeName + ", Is Dev: " + isDevName + ", Department: "
						+ deptName + ", Sub-Dept: " + subDeptName, f1);
				type.setAlignment(Element.ALIGN_CENTER);
				document.add(type);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<MrnMonthWiseList> mrnItemMonthWiseListForPdf = new ArrayList<MrnMonthWiseList>();

	@RequestMapping(value = "/mrnMonthItemWiseReportBycatId/{catId}/{typeName}/{isDevName}/{deptName}/{subDeptName}/{catDesc}", method = RequestMethod.GET)
	public ModelAndView mrnMonthItemWiseReportBycatId(@PathVariable int catId, @PathVariable String typeName,
			@PathVariable String isDevName, @PathVariable String deptName, @PathVariable String subDeptName,
			@PathVariable String catDesc, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/mrnMonthItemWiseReport");
		try {
			List<MrnMonthWiseList> list = new ArrayList<MrnMonthWiseList>();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("typeId", typeId);
			map.add("deptId", deptId);
			map.add("subDeptId", subDeptId);
			map.add("catId", catId);
			map.add("year", year);
			
			if (isDev == -1) {
				map.add("isDev", "0,1");
			} else {
				map.add("isDev", isDev);
			}
			System.out.println(map);
			MrnMonthWiseList[] mrnMonthWiseList = rest.postForObject(Constants.url + "/mrnMonthItemWiseReport", map,
					MrnMonthWiseList[].class);
			list = new ArrayList<MrnMonthWiseList>(Arrays.asList(mrnMonthWiseList));

			mrnItemMonthWiseListForPdf = list;

			model.addObject("list", list);
			model.addObject("catId", catId);
			GetItem[] item = rest.getForObject(Constants.url + "/getAllItems", GetItem[].class);
			List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(item));
			model.addObject("itemList", itemList);
			model.addObject("typeName", typeName);
			model.addObject("isDevName", isDevName);
			model.addObject("deptName", deptName);
			model.addObject("subDeptName", subDeptName);
			model.addObject("catDesc", catDesc);

			itemListforPdf = itemList;
			deptIdForPdf = catId;
			// ------------------------ Export To
			// Excel--------------------------------------
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			/*
			 * rowData.add("SR. No"); rowData.add("Item NAME");
			 * rowData.add("APR ISSUE QTY"); rowData.add("APR ISSUE VALUE");
			 * rowData.add("MAY ISSUE QTY"); rowData.add("MAY ISSUE VALUE");
			 * rowData.add("JUNE ISSUE QTY"); rowData.add("JUNE ISSUE VALUE");
			 * rowData.add("JULY ISSUE QTY"); rowData.add("JULY ISSUE VALUE");
			 * rowData.add("AUGUST ISSUE QTY"); rowData.add("AUGUST ISSUE VALUE");
			 * rowData.add("SEPTEMBR ISSUE QTY"); rowData.add("SEPTEMBR ISSUE VALUE");
			 * rowData.add("OCTOMBER ISSUE QTY"); rowData.add("OCTOMBER ISSUE VALUE");
			 * rowData.add("NOVEMBER ISSUE QTY"); rowData.add("NOVEMBER ISSUE VALUE");
			 * rowData.add("DECEMBER ISSUE QTY"); rowData.add("DECEMBER ISSUE VALUE");
			 * rowData.add("JANUARY ISSUE QTY"); rowData.add("JANUARY ISSUE VALUE");
			 * rowData.add("FEBRUARY ISSUE QTY"); rowData.add("FEBRUARY ISSUE VALUE");
			 * rowData.add("MARCH ISSUE QTY"); rowData.add("MARCH ISSUE VALUE");
			 */

			rowData.add("SR. No");
			rowData.add("Item NAME");
			rowData.add("APR ISSUE VALUE");
			rowData.add("MAY ISSUE VALUE");
			rowData.add("JUNE ISSUE VALUE");
			rowData.add("JULY ISSUE VALUE");
			rowData.add("AUGUST ISSUE VALUE");
			rowData.add("SEPTEMBR ISSUE VALUE");
			rowData.add("OCTOMBER ISSUE VALUE");
			rowData.add("NOVEMBER ISSUE VALUE");
			rowData.add("DECEMBER ISSUE VALUE");
			rowData.add("JANUARY ISSUE VALUE");
			rowData.add("FEBRUARY ISSUE VALUE");
			rowData.add("MARCH ISSUE VALUE");

			expoExcel.setRowData(rowData);

			exportToExcelList.add(expoExcel);
			int index = 0;
			for (int i = 0; i < itemList.size(); i++) {

				if (itemList.get(i).getCatId() == catId) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					index++;
					rowData.add((index) + "");
					rowData.add(itemList.get(i).getItemCode() + " " + itemList.get(i).getItemDesc());
					for (int k = 0; k < list.size(); k++) {
						List<MonthItemWiseMrnReport> monthList = list.get(k).getItemWiseMonthList();

						for (int j = 0; j < monthList.size(); j++) {
							if (monthList.get(j).getItemId() == itemList.get(i).getItemId()) {
								// rowData.add(""+monthList.get(j).getApproveQty());
								rowData.add("" + monthList.get(j).getApprovedQtyValue());
							}
						}

					}

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);
				}
			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "MrnItemMonthWiseList");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/mrnItemMonthWiseReportPdf/{typeName}/{catDesc}/{isDevName}/{deptName}/{subDeptName}", method = RequestMethod.GET)
	public void mrnItemMonthWiseReportPdf(@PathVariable String typeName, @PathVariable String catDesc,
			@PathVariable String isDevName, @PathVariable String deptName, @PathVariable String subDeptName,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
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

			float aprTotal = 0;
			float mayTotal = 0;
			float junTotal = 0;
			float julTotal = 0;
			float augTotal = 0;
			float sepTotal = 0;
			float octTotal = 0;
			float novTotal = 0;
			float decTotal = 0;
			float janTotal = 0;
			float febTotal = 0;
			float marTotal = 0;

			PdfPTable table = new PdfPTable(15);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 3.0f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f,
						0.8f, 0.8f, 0.8f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);
				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR.", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Item Name", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("APR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MAY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JUN", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JUL", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("AUG", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("SEP", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("OCT", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("NOV", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("DEC", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("JAN", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("FEB", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MAR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("TOTAL", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!mrnItemMonthWiseListForPdf.isEmpty()) {
					for (int k = 0; k < itemListforPdf.size(); k++) {
						if (deptIdForPdf == itemListforPdf.get(k).getCatId()) {
							index++;

							PdfPCell cell;

							float mrnQtyValue = 0;

							cell = new PdfPCell(new Phrase("" + index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(
									itemListforPdf.get(k).getItemCode() + " " + itemListforPdf.get(k).getItemDesc(),
									headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);

							for (int j = 0; j < mrnItemMonthWiseListForPdf.size(); j++) {

								List<MonthItemWiseMrnReport> monthList = mrnItemMonthWiseListForPdf.get(j)
										.getItemWiseMonthList();

								for (int l = 0; l < monthList.size(); l++) {
									if (monthList.get(l).getItemId() == itemListforPdf.get(k).getItemId()) {

										cell = new PdfPCell(
												new Phrase("" + monthList.get(l).getApprovedQtyValue(), headFont));
										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
										cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
										cell.setPaddingRight(2);
										cell.setPadding(3);
										table.addCell(cell);

										mrnQtyValue = mrnQtyValue + monthList.get(l).getApprovedQtyValue();

										if (monthList.get(l).getMonthNo() == 1) {
											janTotal = janTotal + monthList.get(l).getApprovedQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 2) {
											febTotal = febTotal + monthList.get(l).getApprovedQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 3) {
											marTotal = marTotal + monthList.get(l).getApprovedQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 4) {
											aprTotal = aprTotal + monthList.get(l).getApprovedQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 5) {
											mayTotal = mayTotal + monthList.get(l).getApprovedQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 6) {
											junTotal = junTotal + monthList.get(l).getApprovedQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 7) {
											julTotal = julTotal + monthList.get(l).getApprovedQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 8) {
											augTotal = augTotal + monthList.get(l).getApprovedQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 9) {
											sepTotal = sepTotal + monthList.get(l).getApprovedQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 10) {
											octTotal = octTotal + monthList.get(l).getApprovedQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 11) {
											novTotal = novTotal + monthList.get(l).getApprovedQtyValue();
										}
										if (monthList.get(l).getMonthNo() == 12) {
											decTotal = decTotal + monthList.get(l).getApprovedQtyValue();
										}
									}
								}
							}

							cell = new PdfPCell(new Phrase("" + df.format(mrnQtyValue), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPadding(3);
							table.addCell(cell);

						}
					}
				}

				PdfPCell cell;

				cell = new PdfPCell(new Phrase("Total", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(aprTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(mayTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + df.format(junTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + df.format(julTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + df.format(augTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(sepTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(octTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(novTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(decTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(janTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(febTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format(marTotal), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + df.format((marTotal + aprTotal + mayTotal + junTotal + julTotal
						+ augTotal + sepTotal + octTotal + novTotal + decTotal + febTotal + janTotal)), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPadding(3);
				table.addCell(cell);

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph headingDate = new Paragraph("Mrn Item Month Wise Report ", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				Paragraph type = new Paragraph("Category: " + catDesc + ", Type: " + typeName + ", Department: "
						+ deptName + ", Sub-Dept: " + subDeptName, f1);
				type.setAlignment(Element.ALIGN_CENTER);
				document.add(type);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<IndentStatusReport> indentStatusReportListForPdf = new ArrayList<IndentStatusReport>();

	@RequestMapping(value = "/indentStatusReport", method = RequestMethod.GET)
	public ModelAndView indentStatusReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/indentStatusReport");
		try {

			if (request.getParameter("fromDate") == null || request.getParameter("toDate") == null) {

				SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				fromDate = "01" + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
				toDate = dd.format(date);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", yy.format(date));
				IndentStatusReport[] indentStatusReport = rest.postForObject(Constants.url + "/indentStatusReport", map,
						IndentStatusReport[].class);
				List<IndentStatusReport> list = new ArrayList<IndentStatusReport>(Arrays.asList(indentStatusReport));

				model.addObject("indentStatusReport", list);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", dd.format(date));
				indentStatusReportListForPdf = list;
			} else {

				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));

				IndentStatusReport[] indentStatusReport = rest.postForObject(Constants.url + "/indentStatusReport", map,
						IndentStatusReport[].class);
				List<IndentStatusReport> list = new ArrayList<IndentStatusReport>(Arrays.asList(indentStatusReport));

				model.addObject("indentStatusReport", list);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
				indentStatusReportListForPdf = list;
			}

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("FROM DATE-");
			rowData.add(fromDate);
			rowData.add("TO DATE");
			rowData.add(toDate);

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("INDENT NO");
			rowData.add("INDENT DATE");
			rowData.add("ITEM DESC");
			rowData.add("INDENT QTY");
			rowData.add("SCH DATE");
			rowData.add("EXPRESS DAYS");
			rowData.add("REMARK");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			int k = 0;
			for (int i = 0; i < indentStatusReportListForPdf.size(); i++) {

				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				k++;
				rowData.add((k) + "");
				rowData.add(indentStatusReportListForPdf.get(i).getIndMNo());
				rowData.add("" + indentStatusReportListForPdf.get(i).getIndMDate());
				rowData.add("" + indentStatusReportListForPdf.get(i).getItemCode());
				rowData.add("" + indentStatusReportListForPdf.get(i).getIndQty());
				rowData.add("" + indentStatusReportListForPdf.get(i).getIndItemSchddt());
				rowData.add("" + indentStatusReportListForPdf.get(i).getExcessDays());
				rowData.add("" + indentStatusReportListForPdf.get(i).getRemark());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "indentSttatusList");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/indentStatusReportPDF", method = RequestMethod.GET)
	public void indentStatusReportPDF(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
			Document document = new Document(PageSize.A4);
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

			PdfPTable table = new PdfPTable(8);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 1.0f, 1.7f, 1.7f, 5.0f, 1.7f, 1.7f, 1.7f, 2.0f });
				Font headFont = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
				Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
				Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.GRAY);

				PdfPCell hcell = new PdfPCell();

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("INDENT NO", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("INDENT DATE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ITEM DESC", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("INDENT QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("SCH DATE", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("EXPRESS DAYS", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("REMARK", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				if (!indentStatusReportListForPdf.isEmpty()) {
					for (int k = 0; k < indentStatusReportListForPdf.size(); k++) {

						index++;

						PdfPCell cell;

						cell = new PdfPCell(new Phrase("" + index, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(indentStatusReportListForPdf.get(k).getIndMNo(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(
								new Phrase("" + indentStatusReportListForPdf.get(k).getIndMDate(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(
								new Phrase("" + indentStatusReportListForPdf.get(k).getItemCode(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + indentStatusReportListForPdf.get(k).getIndQty(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(
								new Phrase("" + indentStatusReportListForPdf.get(k).getIndItemSchddt(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(
								new Phrase("" + indentStatusReportListForPdf.get(k).getExcessDays(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + indentStatusReportListForPdf.get(k).getRemark(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

					}
				}

				document.open();
				Paragraph company = new Paragraph("Trambak Rubber Industries Limited\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(
						"Address:  S. D. Aphale(General Manager) Flat No. 02, Maruti Building,\n Maharaj Nagar, Tagore Nagar NSK- 6, Nashik Road, Nashik - 422101, Maharashtra, India	",
						f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph headingDate = new Paragraph(
						"Indent Status Report, From Date: " + fromDate + "  To Date: " + toDate + "", f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);

				document.add(headingDate);

				Paragraph ex3 = new Paragraph("\n");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// issueListItemwiseReport

	@RequestMapping(value = "/mrnCatwiseReport", method = RequestMethod.GET)
	public ModelAndView mrnCatwiseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/mrnReport");
		try {

			Category[] categoryRes = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> catList = new ArrayList<Category>(Arrays.asList(categoryRes));

			model.addObject("catList", catList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	List<MrnCatwiseReport> mrnCatwiseList = new ArrayList<MrnCatwiseReport>();

	@RequestMapping(value = "/getMrnReportCatwise", method = RequestMethod.GET)
	@ResponseBody
	public List<MrnCatwiseReport> getMrnReportCatwise(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
		try {

			String catIdList = request.getParameter("catIdList");

			catIdList = catIdList.substring(1, catIdList.length() - 1);
			catIdList = catIdList.replaceAll("\"", "");

			System.out.println(catIdList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("catIdList", catIdList);

			MrnCatwiseReport[] list = rest.postForObject(Constants.url + "/getMrnReportCatwise", map,
					MrnCatwiseReport[].class);
			mrnCatwiseList = new ArrayList<MrnCatwiseReport>(Arrays.asList(list));

			System.out.println("mrnCatwiseList===================" + mrnCatwiseList.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("Categoty");
			rowData.add("Item Description");
			rowData.add("MRN No.");
			rowData.add("Date");

			rowData.add("QTY");
			rowData.add("Reamaining QTY");
			rowData.add("Item Rate");
			rowData.add("Value");
			rowData.add("Days");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < mrnCatwiseList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");
				rowData.add(mrnCatwiseList.get(i).getCatDesc());
				rowData.add("" + mrnCatwiseList.get(i).getItemDesc());
				rowData.add("" + mrnCatwiseList.get(i).getMrnNo());
				rowData.add("" + mrnCatwiseList.get(i).getMrnDate());
				rowData.add("" + mrnCatwiseList.get(i).getMrnQty());
				rowData.add("" + mrnCatwiseList.get(i).getRemainingQty());
				rowData.add("" + mrnCatwiseList.get(i).getItemRate());

				float value = mrnCatwiseList.get(i).getMrnQty() * mrnCatwiseList.get(i).getItemRate();
				;
				rowData.add("" + value);
				rowData.add("" + mrnCatwiseList.get(i).getDays());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "QuotReport");
			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

		} catch (

		Exception e) {
			e.printStackTrace();
		}

		return mrnCatwiseList;
	}

	@RequestMapping(value = "/quotationReport", method = RequestMethod.GET)
	public ModelAndView quotationReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/quotationReport");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	List<QuotReport> quotwiseList = new ArrayList<QuotReport>();

	@RequestMapping(value = "/getQuotReportBetDAte", method = RequestMethod.GET)
	@ResponseBody
	public List<QuotReport> getQuotReportBetDAte(HttpServletRequest request, HttpServletResponse response) {

		/* System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj"); */
		try {

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			String statusList = request.getParameter("status");

			map.add("status", statusList);

			QuotReport[] list = rest.postForObject(Constants.url + "/getQuotReport", map, QuotReport[].class);
			quotwiseList = new ArrayList<QuotReport>(Arrays.asList(list));
			System.out.println("====" + quotwiseList.toString());
			// ----------------exel-------------------------

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("Quotation No");
			rowData.add("Date");
			rowData.add("Item Description");
			rowData.add("QTY");
			rowData.add("Item Remark");
			rowData.add("Header Remark");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < quotwiseList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");
				rowData.add(quotwiseList.get(i).getEnqNo());
				rowData.add("" + quotwiseList.get(i).getEnqDate());
				rowData.add("" + quotwiseList.get(i).getItemDesc());
				rowData.add("" + quotwiseList.get(i).getEnqQty());
				rowData.add("" + quotwiseList.get(i).getEnqRemark());
				rowData.add("" + quotwiseList.get(i).getHeaderRemark());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "QuotReport");
			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return quotwiseList;
	}

	@RequestMapping(value = "/showQuotationReportPdf/{fromDate}/{toDate}", method = RequestMethod.GET)
	public void showQuotationReportPdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showQuotationReportPdf");
		Document document = new Document(PageSize.A4.rotate());

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(8);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1.0f, 3.2f, 3.2f, 3.2f, 6.0f, 3.2f, 3.2f, 3.2f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Quotation No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Date ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Code ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Desc ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Qty ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Remark", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Header Remark", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (QuotReport work : quotwiseList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getEnqNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getEnqDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemCode(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getEnqQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getEnqRemark(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getHeaderRemark(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}
			document.open();
			Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);

			Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
			heading1.setAlignment(Element.ALIGN_CENTER);
			document.add(heading1);
			Paragraph ex2 = new Paragraph("\n");
			document.add(ex2);

			Paragraph reportName = new Paragraph("Quotation Report", f1);
			reportName.setAlignment(Element.ALIGN_CENTER);
			document.add(reportName);

			Paragraph ex3 = new Paragraph("\n");
			document.add(ex3);
			table.setHeaderRows(1);
			document.add(table);

			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();

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

			System.out.println("Pdf Generation Error: " + ex.getMessage());

			ex.printStackTrace();

		}

	}

	@RequestMapping(value = "/showMRNCatwiseReportPdf", method = RequestMethod.GET)
	public void showMRNCatwiseReportPdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showMRNCatwiseReportPdf");
		Document document = new Document(PageSize.A4);

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(11);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1.2f, 3.2f, 3.2f, 7.0f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Category", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Code ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Desc ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("MRN N0. ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Qty ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Remaining Qty ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Rate", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Value", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Days", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (MrnCatwiseReport work : mrnCatwiseList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCatDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemCode(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getMrnNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getMrnDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getMrnQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getRemainingQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemRate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				float value = work.getItemRate() * work.getMrnQty();

				cell = new PdfPCell(new Phrase("" + value, headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("" + work.getDays(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}
			document.open();
			Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);

			Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
			heading1.setAlignment(Element.ALIGN_CENTER);
			document.add(heading1);
			Paragraph ex2 = new Paragraph("\n");
			document.add(ex2);

			Paragraph reportName = new Paragraph("MRN Categorywise Report", f1);
			reportName.setAlignment(Element.ALIGN_CENTER);
			document.add(reportName);

			Paragraph ex3 = new Paragraph("\n");
			document.add(ex3);
			table.setHeaderRows(1);
			document.add(table);

			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();

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

			System.out.println("Pdf Generation Error: " + ex.getMessage());

			ex.printStackTrace();

		}

	}

	@RequestMapping(value = "/enqAgainstQuotReport", method = RequestMethod.GET)
	public ModelAndView enqAgainstQuotReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/enqAgQuotReport");
		try {

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	List<ItemEnqAgQuotReport> itemEnqAgQuotReportList = new ArrayList<ItemEnqAgQuotReport>();

	@RequestMapping(value = "/getItemEnqAgReportBetDAte", method = RequestMethod.GET)
	@ResponseBody
	public List<ItemEnqAgQuotReport> getItemEnqAgReportBetDAte(HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
		try {

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			int status = Integer.parseInt(request.getParameter("status"));

			String vendorIdList = request.getParameter("vendorIdList");
			vendorIdList = vendorIdList.substring(1, vendorIdList.length() - 1);
			vendorIdList = vendorIdList.replaceAll("\"", "");

			System.out.println(vendorIdList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			map.add("status", status);
			map.add("vendorIdList", vendorIdList);

			ItemEnqAgQuotReport[] list = rest.postForObject(Constants.url + "/getEnqAgQuotReport", map,
					ItemEnqAgQuotReport[].class);
			itemEnqAgQuotReportList = new ArrayList<ItemEnqAgQuotReport>(Arrays.asList(list));

			System.out.println("itemEnqAgQuotReportList" + itemEnqAgQuotReportList.toString());
			// ----------------exel-------------------------

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("Enq No");
			rowData.add("Date");
			rowData.add("Quotation No");
			rowData.add("Item Description");
			rowData.add("QTY");
			rowData.add("Enq Remark");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < itemEnqAgQuotReportList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");
				rowData.add(itemEnqAgQuotReportList.get(i).getEnqNo());
				rowData.add("" + itemEnqAgQuotReportList.get(i).getEnqDate());
				rowData.add("" + itemEnqAgQuotReportList.get(i).getIndNo());
				rowData.add("" + itemEnqAgQuotReportList.get(i).getItemDesc());
				rowData.add("" + itemEnqAgQuotReportList.get(i).getEnqQty());
				rowData.add("" + itemEnqAgQuotReportList.get(i).getEnqRemark());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "ItemEnqAgQuotReport");
			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return itemEnqAgQuotReportList;
	}

	@RequestMapping(value = "/showEnqAgQuotReportPdf", method = RequestMethod.GET)
	public void showEnqAgQuotReportPdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showEnqAgQuotReportPdf");
		Document document = new Document(PageSize.A4.rotate());

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(8);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1.0f, 1.2f, 1.2f, 1.2f, 1.2f, 6.0f, 1.2f, 3.2f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Enq No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Ind No. ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Code ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Desc ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Qty ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Remark", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (ItemEnqAgQuotReport work : itemEnqAgQuotReportList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getEnqNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getEnqDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIndNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemCode(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getEnqQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getEnqRemark(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}
			document.open();
			Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);

			Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
			heading1.setAlignment(Element.ALIGN_CENTER);
			document.add(heading1);
			Paragraph ex2 = new Paragraph("\n");
			document.add(ex2);

			Paragraph reportName = new Paragraph("Item Enquiry Report", f1);
			reportName.setAlignment(Element.ALIGN_CENTER);
			document.add(reportName);

			Paragraph ex3 = new Paragraph("\n");
			document.add(ex3);
			table.setHeaderRows(1);
			document.add(table);

			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();

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

			System.out.println("Pdf Generation Error: " + ex.getMessage());

			ex.printStackTrace();

		}

	}

	@RequestMapping(value = "/issueListItemwiseReport", method = RequestMethod.GET)
	public ModelAndView issueListItemwiseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/issueListItemwiseReport");
		try {

			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);

			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));
			model.addObject("deparmentList", deparmentList);

			Category[] categoryRes = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> catList = new ArrayList<Category>(Arrays.asList(categoryRes));

			model.addObject("catList", catList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/getAllCatByAjax", method = RequestMethod.GET)
	@ResponseBody
	public List<Category> getAllCatByAjax(HttpServletRequest request, HttpServletResponse response) {

		Category[] categoryRes = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
		List<Category> catList = new ArrayList<Category>(Arrays.asList(categoryRes));

		return catList;
	}

	@RequestMapping(value = "/getAllTypeByAjax", method = RequestMethod.GET)
	@ResponseBody
	public List<Type> getAllTypeByAjax(HttpServletRequest request, HttpServletResponse response) {

		Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
		List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));

		return typeList;
	}

	@RequestMapping(value = "/getAllDeptByAjax", method = RequestMethod.GET)
	@ResponseBody
	public List<Dept> getAllDeptByAjax(HttpServletRequest request, HttpServletResponse response) {
		Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
		List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));

		return deparmentList;
	}

	List<IssueReportItemwise> issueList1 = new ArrayList<IssueReportItemwise>();

	@RequestMapping(value = "/getIssBetDate", method = RequestMethod.GET)
	@ResponseBody
	public List<IssueReportItemwise> getIssBetDate(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
		try {

			System.out.println("4444444444");

			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			String catIdList = request.getParameter("catIdList");
			String typeIdList = request.getParameter("typeIdList");
			int appStatus = Integer.parseInt(request.getParameter("appStatus"));
			int deptId = Integer.parseInt(request.getParameter("deptId"));
			int subDeptId = Integer.parseInt(request.getParameter("subDeptId"));

			System.out.println("++++++++++++++" + catId);

			catIdList = catIdList.substring(1, catIdList.length() - 1);
			catIdList = catIdList.replaceAll("\"", "");

			typeIdList = typeIdList.substring(1, typeIdList.length() - 1);
			typeIdList = typeIdList.replaceAll("\"", "");

			System.out.println(catIdList);
			System.out.println(typeIdList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("typeId", typeIdList);
			map.add("catIdList", catIdList);
			map.add("deptId", deptId);

			map.add("appStatus", appStatus);
			map.add("subDeptId", subDeptId);

			IssueReportItemwise[] list = rest.postForObject(Constants.url + "/getIssueHeaderDetailItemwiseReport", map,
					IssueReportItemwise[].class);
			issueList1 = new ArrayList<IssueReportItemwise>(Arrays.asList(list));

			System.out.println("issueList1===================" + issueList1.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("Issue No");
			rowData.add("Date");
			rowData.add("Category");
			rowData.add("Dept Description");
			rowData.add("SubDept Description");
			rowData.add("Item Description");
			rowData.add("QTY");
			rowData.add("Status");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < issueList1.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");
				rowData.add("" + issueList1.get(i).getIssueNo());
				rowData.add("" + issueList1.get(i).getIssueDate());
				rowData.add("" + issueList1.get(i).getCatDesc());
				rowData.add("" + issueList1.get(i).getDeptDesc());
				rowData.add("" + issueList1.get(i).getSubDeptDesc());
				rowData.add("" + issueList1.get(i).getItemDesc());
				rowData.add("" + issueList1.get(i).getItemIssueQty());
				rowData.add("" + issueList1.get(i).getStatus());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "IssueReportItemwise");

			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return issueList1;
	}

	@RequestMapping(value = "/showIssueListReportPdf", method = RequestMethod.GET)
	public void showIssueListReportPdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showIssueListReportPdf");
		Document document = new Document(PageSize.A4.rotate());

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(10);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1.0f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.0f, 9.0f, 3.2f, 3.2f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Issue No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Category ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Dept Desc ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Sub dept Desc ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Item Code ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Desc ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Qty ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("App Status", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (IssueReportItemwise work : issueList1) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIssueNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIssueDate(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getCatDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getDeptDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getSubDeptDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemCode(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemIssueQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				String status = "";

				if (work.getStatus() == 0) {
					status = "Pending for Approval1";

				} else if (work.getStatus() == 1) {
					status = "Pending for Approval2";
				} else if (work.getStatus() == 2) {
					status = "Closed";
				}
				cell = new PdfPCell(new Phrase(status + "", headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

			}
			document.open();
			Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);

			Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
			heading1.setAlignment(Element.ALIGN_CENTER);
			document.add(heading1);
			Paragraph ex2 = new Paragraph("\n");
			document.add(ex2);

			Paragraph reportName = new Paragraph("Itemwise Issue List", f1);
			reportName.setAlignment(Element.ALIGN_CENTER);
			document.add(reportName);

			Paragraph ex3 = new Paragraph("\n");
			document.add(ex3);
			table.setHeaderRows(1);
			document.add(table);

			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();

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

			System.out.println("Pdf Generation Error: " + ex.getMessage());

			ex.printStackTrace();

		}

	}

	@RequestMapping(value = "/showIndentItemwiseReport", method = RequestMethod.GET)
	public ModelAndView showIndentItemwiseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/indentReportItemwise");
		try {

			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);

			Category[] categoryRes = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> catList = new ArrayList<Category>(Arrays.asList(categoryRes));

			model.addObject("catList", catList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	List<IndentRepItemwise> indentRepList = new ArrayList<IndentRepItemwise>();

	@RequestMapping(value = "/getIndentReportBetDate", method = RequestMethod.GET)
	@ResponseBody
	public List<IndentRepItemwise> getIndentReportBetDate(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
		try {

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			String catIdList = request.getParameter("catIdList");
			String typeIdList = request.getParameter("typeIdList");
			String appStatus = request.getParameter("appStatus");

			System.out.println("++++++++++++++" + catId);

			System.out.println("");

			catIdList = catIdList.substring(1, catIdList.length() - 1);
			catIdList = catIdList.replaceAll("\"", "");

			typeIdList = typeIdList.substring(1, typeIdList.length() - 1);
			typeIdList = typeIdList.replaceAll("\"", "");

			System.out.println(catIdList);
			System.out.println(typeIdList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("typeIdList", typeIdList);
			map.add("catIdList", catIdList);

			if (appStatus.contains("-1")) {
				map.add("statusList", "0,1,2");
			} else {
				map.add("statusList", appStatus);
			}

			IndentRepItemwise[] list = rest.postForObject(Constants.url + "/getIndentItemwiseReport", map,
					IndentRepItemwise[].class);
			indentRepList = new ArrayList<IndentRepItemwise>(Arrays.asList(list));

			System.out.println("indentRepList===================" + indentRepList.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("Indent No");
			rowData.add("Date");
			rowData.add("Item Description");

			rowData.add("Indent QTY");
			rowData.add("Excess Days");
			rowData.add("Remark");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < indentRepList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");
				rowData.add("" + indentRepList.get(i).getIndMNo());
				rowData.add("" + indentRepList.get(i).getIndItemSchddt());
				rowData.add("" + indentRepList.get(i).getIndItemDesc());
				rowData.add("" + indentRepList.get(i).getIndQty());

				rowData.add("" + indentRepList.get(i).getExcessDays());
				rowData.add("" + indentRepList.get(i).getIndReamrk());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "indentRepList");

			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return indentRepList;
	}

	@RequestMapping(value = "/showIndentListReportPdf", method = RequestMethod.GET)
	public void showIndentListReportPdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showIndentListReportPdf");
		Document document = new Document(PageSize.A4.rotate());

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(7);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1.0f, 3.2f, 3.2f, 6.0f, 3.2f, 3.2f, 3.2f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.DARK_GRAY);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Indent No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Desc ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Indent Qty ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Excess Days", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Remark", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			int index = 0;
			for (IndentRepItemwise work : indentRepList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIndMNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIndItemSchddt(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIndItemDesc(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIndQty(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getExcessDays(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getIndReamrk(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);
				String status = "";

			}
			document.open();
			Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);

			Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
			heading1.setAlignment(Element.ALIGN_CENTER);
			document.add(heading1);
			Paragraph ex2 = new Paragraph("\n");
			document.add(ex2);

			Paragraph reportName = new Paragraph("Indent Report", f1);
			reportName.setAlignment(Element.ALIGN_CENTER);
			document.add(reportName);

			Paragraph ex3 = new Paragraph("\n");
			document.add(ex3);
			table.setHeaderRows(1);
			document.add(table);

			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();

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

			System.out.println("Pdf Generation Error: " + ex.getMessage());

			ex.printStackTrace();

		}

	}
	
	
	
List<StockValuationCategoryWise> abcAnalysisReport = new ArrayList<StockValuationCategoryWise>();
	
	@RequestMapping(value = "/abcAnalysisReport", method = RequestMethod.GET)
	public ModelAndView abcAnalysisReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/abcAnalysisReport");
		try {
			
			
			 abcAnalysisReport = new ArrayList<StockValuationCategoryWise>();
			/*Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);*/
			
			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));
			
			System.out.println("categoryList size " + categoryList.size());
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map = new LinkedMultiValueMap<String, Object>();
			map.add("name", "A"); 
			System.out.println("map " + map);
			SettingValue a = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
			
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("name", "B"); 
			System.out.println("map " + map);
			SettingValue b = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("name", "C"); 
			System.out.println("map " + map);
			SettingValue c = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
			
			if(request.getParameter("fromDate")==null || request.getParameter("toDate")==null || request.getParameter("typeId")==null) {
				
				SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
				Date date = new Date();
				  Calendar calendar = Calendar.getInstance();
				  calendar.setTime(date);
				   
				 
				 fromDate =  "01"+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
				 toDate = dd.format(date);
				 typeId=0;
				 
				 for(int i=0;i<categoryList.size();i++) {
					 
					 	map = new LinkedMultiValueMap<>();
						map.add("fromDate",DateConvertor.convertToYMD(fromDate));
			 			map.add("toDate",yy.format(date)); 
			 			map.add("typeId",0);
			 			map.add("catId",categoryList.get(i).getCatId());
			 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatIdAndTypeId",map, GetCurrentStock[].class);
			 			List<GetCurrentStock> list = new ArrayList<GetCurrentStock>(Arrays.asList(getCurrentStock));
			 			
			 			StockValuationCategoryWise StockValuationCategoryWise =new StockValuationCategoryWise();
			 			StockValuationCategoryWise.setCatDesc(categoryList.get(i).getCatDesc());
			 			StockValuationCategoryWise.setCatId(categoryList.get(i).getCatId());
			 			
			 			float clsValueA=0;
			 			float clsValueB=0;
			 			float clsValueC=0;
			 			
			 			for(int j=0 ; j<list.size() ; j++) {
			 				
			 				float clsValue=0;
			 				clsValue =  list.get(j).getOpStockValue()+list.get(j).getApprovedQtyValue()-list.get(j).getIssueQtyValue()-list.get(j).getDamagValue() ;
	 						
			 				 if(clsValue>=Float.parseFloat(a.getValue())) {
			 					clsValueA=clsValueA+clsValue;
			 				 }else if(clsValue>=Float.parseFloat(c.getValue()) && clsValue<Float.parseFloat(a.getValue())) {
			 					clsValueB=clsValueB+clsValue;
			 				 }else{
			 					clsValueC=clsValueC+clsValue;
				 			}
			 				 
			 			}
			 			
			 			StockValuationCategoryWise.setOpStockValue(clsValueA);
		 				StockValuationCategoryWise.setApprovedQtyValue(clsValueB);
		 				StockValuationCategoryWise.setIssueQtyValue(clsValueC);
		 				abcAnalysisReport.add(StockValuationCategoryWise);
				 }
				  
					
					model.addObject("categoryWiseReport", abcAnalysisReport);
					model.addObject("fromDate", fromDate);
					model.addObject("toDate", dd.format(date));
					
					System.out.println("categoryWiseReport count " + abcAnalysisReport.size());
					 
			}
			else {
				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");
				//typeId = Integer.parseInt(request.getParameter("typeId"));
				
				
				SimpleDateFormat yy = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy");
				
				Date date = dd.parse(fromDate);
				  Calendar calendar = Calendar.getInstance();
				  calendar.setTime(date);
				   
				 String firstDate = "01"+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
				 
				 System.out.println(DateConvertor.convertToYMD(firstDate) + DateConvertor.convertToYMD(fromDate));
				 
				 if(DateConvertor.convertToYMD(firstDate).compareTo(DateConvertor.convertToYMD(fromDate))<0)
				 {
					 for(int k=0;k<categoryList.size();k++) {
							calendar.add(Calendar.DATE, -1);
							String previousDate = yy.format(new Date(calendar.getTimeInMillis())); 
							 map = new LinkedMultiValueMap<>();
							map.add("fromDate",DateConvertor.convertToYMD(firstDate));
				 			map.add("toDate",previousDate); 
				 			map.add("catId", categoryList.get(k).getCatId());
				 			map.add("typeId", 0);
				 			System.out.println(map);
				 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatIdAndTypeId",map,GetCurrentStock[].class); 
				 			List<GetCurrentStock> diffDateStock = new ArrayList<>(Arrays.asList(getCurrentStock));
				 			
				 			calendar.add(Calendar.DATE, 1);
							String addDay = yy.format(new Date(calendar.getTimeInMillis()));
				 			map = new LinkedMultiValueMap<>();
							map.add("fromDate",addDay);
				 			map.add("toDate",DateConvertor.convertToYMD(toDate)); 
				 			map.add("catId", categoryList.get(k).getCatId());
				 			map.add("typeId", 0);
				 			System.out.println(map);
				 			GetCurrentStock[] getCurrentStock1 = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatIdAndTypeId",map,GetCurrentStock[].class); 
				 			 List<GetCurrentStock> list = new ArrayList<>(Arrays.asList(getCurrentStock1));
				 			 
				 			 for(int i = 0 ; i< list.size() ; i++)
				 			 {
				 				 for(int j = 0 ; j< diffDateStock.size() ; j++)
					 			 {
				 					 if(list.get(i).getItemId()==diffDateStock.get(j).getItemId())
				 					 {
				 						list.get(i).setOpeningStock(diffDateStock.get(j).getOpeningStock()+diffDateStock.get(j).getApproveQty()-diffDateStock.get(j).getIssueQty()
										 +diffDateStock.get(j).getReturnIssueQty()-diffDateStock.get(j).getDamageQty()-diffDateStock.get(j).getGatepassQty()
										 +diffDateStock.get(j).getGatepassReturnQty());
				 						list.get(i).setOpStockValue(diffDateStock.get(j).getOpStockValue()+diffDateStock.get(j).getApprovedQtyValue()-diffDateStock.get(j).getIssueQtyValue()-diffDateStock.get(j).getDamagValue());
				 						
				 						break;
				 					 }
					 			 }
				 			 }
				 			 
				 			StockValuationCategoryWise StockValuationCategoryWise =new StockValuationCategoryWise();
				 			StockValuationCategoryWise.setCatDesc(categoryList.get(k).getCatDesc());
				 			StockValuationCategoryWise.setCatId(categoryList.get(k).getCatId());
				 			
				 			float clsValueA=0;
				 			float clsValueB=0;
				 			float clsValueC=0;
				 			
				 			for(int j=0 ; j<list.size() ; j++) {
				 				
				 				float clsValue=0;
				 				clsValue =  list.get(j).getOpStockValue()+list.get(j).getApprovedQtyValue()-list.get(j).getIssueQtyValue()-list.get(j).getDamagValue() ;
		 						
				 				if(clsValue>=Float.parseFloat(a.getValue())) {
				 					clsValueA=clsValueA+clsValue;
				 				 }else if(clsValue>=Float.parseFloat(c.getValue()) && clsValue<Float.parseFloat(a.getValue())) {
				 					clsValueB=clsValueB+clsValue;
				 				 }else{
				 					clsValueC=clsValueC+clsValue;
					 			}
				 				 
				 			}
				 			
				 			StockValuationCategoryWise.setOpStockValue(clsValueA);
			 				StockValuationCategoryWise.setApprovedQtyValue(clsValueB);
			 				StockValuationCategoryWise.setIssueQtyValue(clsValueC);
			 				abcAnalysisReport.add(StockValuationCategoryWise);
					 }
				 }
				 else
				 {
					 for(int k=0;k<categoryList.size();k++) {
						  
						 map = new LinkedMultiValueMap<>();
						 map.add("fromDate",DateConvertor.convertToYMD(fromDate));
				 			map.add("toDate",DateConvertor.convertToYMD(toDate)); 
				 			map.add("catId", categoryList.get(k).getCatId());
				 			map.add("typeId", 0);
				 			System.out.println(map);
				 			GetCurrentStock[] getCurrentStock1 = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatIdAndTypeId",map,GetCurrentStock[].class); 
				 			 List<GetCurrentStock> list = new ArrayList<>(Arrays.asList(getCurrentStock1));
				 			 
				 			StockValuationCategoryWise StockValuationCategoryWise =new StockValuationCategoryWise();
				 			StockValuationCategoryWise.setCatDesc(categoryList.get(k).getCatDesc());
				 			StockValuationCategoryWise.setCatId(categoryList.get(k).getCatId());
				 			
				 			float clsValueA=0;
				 			float clsValueB=0;
				 			float clsValueC=0;
				 			
				 			for(int j=0 ; j<list.size() ; j++) {
				 				
				 				float clsValue=0;
				 				clsValue =  list.get(j).getOpStockValue()+list.get(j).getApprovedQtyValue()-list.get(j).getIssueQtyValue()-list.get(j).getDamagValue() ;
		 						
				 				if(clsValue>=Float.parseFloat(a.getValue())) {
				 					clsValueA=clsValueA+clsValue;
				 				 }else if(clsValue>=Float.parseFloat(c.getValue()) && clsValue<Float.parseFloat(a.getValue())) {
				 					clsValueB=clsValueB+clsValue;
				 				 }else{
				 					clsValueC=clsValueC+clsValue;
					 			}
				 				 
				 			}
				 			
				 			StockValuationCategoryWise.setOpStockValue(clsValueA);
			 				StockValuationCategoryWise.setApprovedQtyValue(clsValueB);
			 				StockValuationCategoryWise.setIssueQtyValue(clsValueC);
			 				abcAnalysisReport.add(StockValuationCategoryWise);
					 }
				 }
				 
				model.addObject("categoryWiseReport", abcAnalysisReport);
				model.addObject("fromDate", fromDate);
				model.addObject("toDate", toDate);
				model.addObject("typeId", typeId);
				  
			}
			
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/abcAnalysisCategoryWiseReportPdf", method = RequestMethod.GET)
	public void abcAnalysisCategoryWiseReportPdf( HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
		Document document = new Document(PageSize.A4);
		DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
		String reportDate = DF.format(new Date());
        document.addHeader("Date: ", reportDate);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		 
			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails",
						Company.class);
		   
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
		
		float issueQty=0;
		float issueValue=0;
	
		PdfPTable table = new PdfPTable(5);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] {0.9f, 7.0f, 2.3f, 2.3f, 2.3f});
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
			Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.GRAY);

			PdfPCell hcell = new PdfPCell();
			
			hcell.setPadding(4);
			hcell = new PdfPCell(new Phrase("SR.NO.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("ITEM NAME", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("CLASS A", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("CLASS B", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("CLASS C", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			 
			
			float total = 0;
			
			int index = 0;
			if(!abcAnalysisReport.isEmpty()) {
					for (int k = 0; k < abcAnalysisReport.size(); k++) {
						
						 
						  
							index++;
						
							PdfPCell cell;
							
							cell = new PdfPCell(new Phrase(""+index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

						
							cell = new PdfPCell(new Phrase(abcAnalysisReport.get(k).getCatDesc(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
						
							cell = new PdfPCell(new Phrase(""+df.format(abcAnalysisReport.get(k).getOpStockValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							
							cell = new PdfPCell(new Phrase(""+df.format(abcAnalysisReport.get(k).getApprovedQtyValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							
							cell = new PdfPCell(new Phrase(""+df.format(abcAnalysisReport.get(k).getIssueQtyValue()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							
							 
					
					}
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
				 
			 
					Paragraph report=new Paragraph("ABC Analysis Report  ",f1);
					report.setAlignment(Element.ALIGN_CENTER);
					document.add(report);
				 
					
				Paragraph headingDate=new Paragraph("From Date: " + fromDate+"  To Date: "+toDate+"",f1);
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
	 
	List<GetCurrentStock> abcAnalysisItemWiseLisPdf = new ArrayList<>();
	@RequestMapping(value = "/abcAnalysisItemWiseReport/{catId}/{catDesc}", method = RequestMethod.GET)
	public ModelAndView abcAnalysisItemWiseReport(@PathVariable("catId") int catId,@PathVariable("catDesc") String catDesc, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("valuationReport/abcAnalysisItemWiseReport");
		try {
			 
			List<GetCurrentStock> getStockBetweenDate = new ArrayList<>();
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
	 			map.add("typeId", typeId);
	 			System.out.println(map);
	 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatIdAndTypeId",map,GetCurrentStock[].class); 
	 			List<GetCurrentStock> diffDateStock = new ArrayList<>(Arrays.asList(getCurrentStock));
	 			
	 			calendar.add(Calendar.DATE, 1);
				String addDay = yy.format(new Date(calendar.getTimeInMillis()));
	 			map = new LinkedMultiValueMap<>();
				map.add("fromDate",addDay);
	 			map.add("toDate",DateConvertor.convertToYMD(toDate)); 
	 			map.add("catId", catId);
	 			map.add("typeId", typeId);
	 			System.out.println(map);
	 			GetCurrentStock[] getCurrentStock1 = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatIdAndTypeId",map,GetCurrentStock[].class); 
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
	 			abcAnalysisItemWiseLisPdf=getStockBetweenDate;
			 }
			 else
			 {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("fromDate",DateConvertor.convertToYMD(fromDate));
	 			map.add("toDate",DateConvertor.convertToYMD(toDate)); 
	 			map.add("catId", catId);
	 			map.add("typeId", typeId);
	 			System.out.println(map);
	 			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getStockBetweenDateWithCatIdAndTypeId",map,GetCurrentStock[].class); 
	 			getStockBetweenDate = new ArrayList<>(Arrays.asList(getCurrentStock));
	 			abcAnalysisItemWiseLisPdf=getStockBetweenDate;
			 }
			 model.addObject("getStockBetweenDate", getStockBetweenDate);
			 
			 MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map = new LinkedMultiValueMap<String, Object>();
				map.add("name", "A"); 
				System.out.println("map " + map);
				SettingValue a = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
				
				
				map = new LinkedMultiValueMap<String, Object>();
				map.add("name", "B"); 
				System.out.println("map " + map);
				SettingValue b = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
				
				map = new LinkedMultiValueMap<String, Object>();
				map.add("name", "C"); 
				System.out.println("map " + map);
				SettingValue c = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
			 
				 model.addObject("a", a);
				 model.addObject("b", b);
				 model.addObject("c", c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/abcAnalysisItemWiseReportPdf/{classType}", method = RequestMethod.GET)
	public void abcAnalysisItemWiseReportPdf(@PathVariable int classType,  HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		try {
		Document document = new Document(PageSize.A4);
		DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
		String reportDate = DF.format(new Date());
        document.addHeader("Date: ", reportDate);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		 MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map = new LinkedMultiValueMap<String, Object>();
			map.add("name", "A"); 
			System.out.println("map " + map);
			SettingValue a = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
			
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("name", "B"); 
			System.out.println("map " + map);
			SettingValue b = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("name", "C"); 
			System.out.println("map " + map);
			SettingValue c = rest.postForObject(Constants.url + "/getSettingValue", map, SettingValue.class);
			
			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails",
						Company.class);
		   
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
		
		float issueQty=0;
		float issueValue=0;
	
		PdfPTable table = new PdfPTable(4);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] {0.9f, 7.0f, 2.3f, 2.3f});
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
			Font f = new Font(FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLUE);
			Font f1 = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.GRAY);

			PdfPCell hcell = new PdfPCell();
			
			hcell.setPadding(4);
			hcell = new PdfPCell(new Phrase("SR.NO.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("ITEM NAME", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("QTY", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("VALUATION", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);
			 
			
			float total = 0;
			
			int index = 0;
			if(!abcAnalysisItemWiseLisPdf.isEmpty()) {
					for (int k = 0; k < abcAnalysisItemWiseLisPdf.size(); k++) {
						
						 
						
						float closingValue = abcAnalysisItemWiseLisPdf.get(k).getOpStockValue()+abcAnalysisItemWiseLisPdf.get(k).getApprovedQtyValue()-
								abcAnalysisItemWiseLisPdf.get(k).getIssueQtyValue()-abcAnalysisItemWiseLisPdf.get(k).getDamagValue();
						
						if(closingValue>=Float.parseFloat(a.getValue()) && closingValue>0 && classType==1) {
                            
							index++;
						
							PdfPCell cell;
							
							cell = new PdfPCell(new Phrase(""+index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

						
							cell = new PdfPCell(new Phrase(abcAnalysisItemWiseLisPdf.get(k).getItemCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
						
							cell = new PdfPCell(new Phrase(""+df.format(abcAnalysisItemWiseLisPdf.get(k).getOpeningStock()+abcAnalysisItemWiseLisPdf.get(k).getApproveQty()-
									abcAnalysisItemWiseLisPdf.get(k).getIssueQty()-abcAnalysisItemWiseLisPdf.get(k).getDamageQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							
							cell = new PdfPCell(new Phrase(""+df.format(closingValue), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							
							total=total+closingValue;
							 
						}else if(closingValue<Float.parseFloat(a.getValue()) && closingValue>=Float.parseFloat(c.getValue()) && closingValue>0 && classType==2) {
                            
							index++;
						
							PdfPCell cell;
							
							cell = new PdfPCell(new Phrase(""+index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

						
							cell = new PdfPCell(new Phrase(abcAnalysisItemWiseLisPdf.get(k).getItemCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
						
							cell = new PdfPCell(new Phrase(""+df.format(abcAnalysisItemWiseLisPdf.get(k).getOpeningStock()+abcAnalysisItemWiseLisPdf.get(k).getApproveQty()-
									abcAnalysisItemWiseLisPdf.get(k).getIssueQty()-abcAnalysisItemWiseLisPdf.get(k).getDamageQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							
							cell = new PdfPCell(new Phrase(""+df.format(closingValue), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							total=total+closingValue;
							 
						}else if(closingValue<Float.parseFloat(c.getValue()) && closingValue>0 && classType==3){
							
							index++;
							
							PdfPCell cell;
							
							cell = new PdfPCell(new Phrase(""+index, headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPadding(3);
							table.addCell(cell);

						
							cell = new PdfPCell(new Phrase(abcAnalysisItemWiseLisPdf.get(k).getItemCode(), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
						
							cell = new PdfPCell(new Phrase(""+df.format(abcAnalysisItemWiseLisPdf.get(k).getOpeningStock()+abcAnalysisItemWiseLisPdf.get(k).getApproveQty()-
									abcAnalysisItemWiseLisPdf.get(k).getIssueQty()-abcAnalysisItemWiseLisPdf.get(k).getDamageQty()), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							
							cell = new PdfPCell(new Phrase(""+df.format(closingValue), headFont));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setPaddingRight(2);
							cell.setPadding(3);
							table.addCell(cell);
							total=total+closingValue;
							
						}
					
					}
			}
			
			PdfPCell cell;
			
			cell = new PdfPCell(new Phrase("Total", headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPadding(3);
			cell.setColspan(2);
			table.addCell(cell);
 
			cell = new PdfPCell(new Phrase(""+df.format(total), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);
			   
			  
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
				 
				if(classType==1) {
					Paragraph report=new Paragraph("ABC Analysis Report Class A ",f1);
					report.setAlignment(Element.ALIGN_CENTER);
					document.add(report);
				}else if(classType==2){
				
					Paragraph report=new Paragraph("ABC Analysis Report Class B ",f1);
					report.setAlignment(Element.ALIGN_CENTER);
					document.add(report);
					
				}else {
					
					Paragraph report=new Paragraph("ABC Analysis Report Class C ",f1);
					report.setAlignment(Element.ALIGN_CENTER);
					document.add(report);
					
				}
				
				 
				Paragraph headingDate=new Paragraph("From Date: " + fromDate+"  To Date: "+toDate+"",f1);
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


}
