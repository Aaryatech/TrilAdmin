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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.AprLogBook;
import com.ats.tril.model.Category;
import com.ats.tril.model.Company;
import com.ats.tril.model.ExportToExcel;
import com.ats.tril.model.PoStatusReportDetail;
import com.ats.tril.model.PoStatusReportHeader;
import com.ats.tril.model.Type;
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

@Controller
@Scope("session")
public class NewReportController {
	String fromDate, toDate;
	RestTemplate rest = new RestTemplate();
	List<Type> typeList;
	List<Category> categoryList;

	@RequestMapping(value = "/showPoStatusReport", method = RequestMethod.GET)
	public ModelAndView showPoStatusReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			Date date = new Date();
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			fromDate = df.format(date);
			toDate = df.format(date);
			System.out.println("From Date And :" + fromDate + "ToDATE" + toDate);

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			model = new ModelAndView("new_po_report/po_status_header");

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			categoryList = new ArrayList<Category>(Arrays.asList(category));
			model.addObject("categoryList", categoryList);

			model.addObject("typeId", 0);
			model.addObject("catId", 0);

		} catch (Exception e) {

			System.err.println("Exception in getIndents Indent" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	List<PoStatusReportHeader> poHeaderStatusList;

	@RequestMapping(value = "/getPoStatusReport", method = RequestMethod.POST)
	public ModelAndView getPoStatusReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			fromDate = request.getParameter("from_date");
			toDate = request.getParameter("to_date");
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			int catId = Integer.parseInt(request.getParameter("catId"));

			System.out.println("From Date And :" + fromDate + "ToDATE" + toDate);

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("typeId", typeId);
			map.add("catId", catId);

			PoStatusReportHeader[] poStatHeader = rest.postForObject(Constants.url + "/getPoStatusReportHeader", map,
					PoStatusReportHeader[].class);
			poHeaderStatusList = new ArrayList<>(Arrays.asList(poStatHeader));
			System.err.println("poHeS " + poHeaderStatusList.toString());
			model = new ModelAndView("new_po_report/po_status_header");

			model.addObject("poHeaderStatusList", poHeaderStatusList);

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

			// Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			// List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);

			// Category[] category = rest.getForObject(Constants.url +
			// "/getAllCategoryByIsUsed", Category[].class);
			// List<Category> categoryList = new
			// ArrayList<Category>(Arrays.asList(category));
			model.addObject("categoryList", categoryList);
			model.addObject("typeId", typeId);
			model.addObject("catId", catId);

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("Po No");
			rowData.add("Po Date");
			rowData.add("Indent No");
			rowData.add("Party Name");
			rowData.add("Item Desc");
			rowData.add("Po Qty");
			rowData.add("Recv Qty");
			rowData.add("Bal Qty");
			rowData.add("Rate");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < poHeaderStatusList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add((i + 1) + "");
				rowData.add(poHeaderStatusList.get(i).getPoNo());
				rowData.add("" + poHeaderStatusList.get(i).getPoDate());
				rowData.add("" + poHeaderStatusList.get(i).getIndentNo());
				rowData.add("" + poHeaderStatusList.get(i).getVendorCode());
				rowData.add("" + poHeaderStatusList.get(i).getItemCode());
				rowData.add("" + poHeaderStatusList.get(i).getPoQty());
				rowData.add("" + poHeaderStatusList.get(i).getRecvQty());
				rowData.add("" + poHeaderStatusList.get(i).getBalQty());
				rowData.add("" + poHeaderStatusList.get(i).getItemRate());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "PoStatusReportHeader");

		} catch (Exception e) {

			System.err.println("Exception in getIndents Indent" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	// getPoStatusReportDetail

	List<PoStatusReportDetail> poDetailStatusList;

	@RequestMapping(value = "/getPoStatusReportDetail/{poDetailId}/{itemCode}", method = RequestMethod.GET)
	public ModelAndView getPoStatusReportDetail(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int poDetailId, @PathVariable String itemCode) {

		ModelAndView model = null;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("poDetailId", poDetailId);

			PoStatusReportDetail[] poStatDetail = rest.postForObject(Constants.url + "/getPoStatusReportDetail", map,
					PoStatusReportDetail[].class);
			poDetailStatusList = new ArrayList<>(Arrays.asList(poStatDetail));
			System.err.println("poHeS " + poHeaderStatusList.toString());
			model = new ModelAndView("new_po_report/po_status_detail");

			model.addObject("poDetailStatusList", poDetailStatusList);

			model.addObject("itemCode", itemCode);

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("SR. No");
			rowData.add("Grn No");
			rowData.add("Date");
			rowData.add("Chalan Qty");
			rowData.add("Recv Qty");
			rowData.add("Accp Qty");
			rowData.add("Rate");
			rowData.add("Value");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < poDetailStatusList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				float value = poDetailStatusList.get(i).getItemRate() * poDetailStatusList.get(i).getAccepQty();

				rowData.add((i + 1) + "");
				rowData.add(poDetailStatusList.get(i).getMrnNo());
				rowData.add("" + poDetailStatusList.get(i).getMrnDate());
				rowData.add("" + poDetailStatusList.get(i).getChalanQty());
				rowData.add("" + poDetailStatusList.get(i).getRecvQty());
				rowData.add("" + poDetailStatusList.get(i).getAccepQty());
				rowData.add("" + poDetailStatusList.get(i).getItemRate());
				rowData.add("" + value);

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "PoStatusReportDetail_" + itemCode);

		} catch (Exception e) {

			System.err.println("Exception in getIndents Indent" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	// Header Pdf
	@RequestMapping(value = "/getPoStatusReportHeaderPdf", method = RequestMethod.GET)
	public void getPoStatusReportHeaderPdf(HttpServletRequest request, HttpServletResponse response)
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

			PdfPTable table = new PdfPTable(10);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 1f, 1f, 1f, 1.2f, 1.2f, 1.0f, 1.0f, 1.0f, 1.0f });
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

				hcell = new PdfPCell(new Phrase("PO No", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Po Date", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Indent No", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Party Name", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Item Desc", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Po Qty", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Recv Qty", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Bal Qty", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Rate", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;
				for (int k = 0; k < poHeaderStatusList.size(); k++) {

					index++;

					PdfPCell cell;

					cell = new PdfPCell(new Phrase("" + index, headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(poHeaderStatusList.get(k).getPoNo(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + poHeaderStatusList.get(k).getPoDate(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + poHeaderStatusList.get(k).getIndentNo(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + poHeaderStatusList.get(k).getVendorCode(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + poHeaderStatusList.get(k).getItemCode(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + df.format(poHeaderStatusList.get(k).getPoQty()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + df.format(poHeaderStatusList.get(k).getRecvQty()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + df.format(poHeaderStatusList.get(k).getBalQty()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + df.format(poHeaderStatusList.get(k).getItemRate()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

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

				Paragraph reportName = new Paragraph("Po Status Report Header", f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

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

	DecimalFormat df = new DecimalFormat("####0.00");

	@RequestMapping(value = "/getPoStatusReportDetailPdf", method = RequestMethod.GET)
	public void getPoStatusReportDetailPdf(HttpServletRequest request, HttpServletResponse response)
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
				table.setWidths(new float[] { 0.4f, 1f, 1f, 1.2f, 1.2f, 1.2f, 1.0f, 1.0f });
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

				hcell = new PdfPCell(new Phrase("Grn No", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Date", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Chalan Qty", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Recv QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Accp Qty", headFont1));
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

				int index = 0;
				for (int k = 0; k < poDetailStatusList.size(); k++) {

					index++;

					PdfPCell cell;

					cell = new PdfPCell(new Phrase("" + index, headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(poDetailStatusList.get(k).getMrnNo(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + poDetailStatusList.get(k).getMrnDate(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + df.format(poDetailStatusList.get(k).getChalanQty()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + df.format(poDetailStatusList.get(k).getRecvQty()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + df.format(poDetailStatusList.get(k).getAccepQty()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + df.format(poDetailStatusList.get(k).getItemRate()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					float value = poDetailStatusList.get(k).getItemRate() * poDetailStatusList.get(k).getAccepQty();

					cell = new PdfPCell(new Phrase("" + df.format(value), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

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

				Paragraph reportName = new Paragraph("Po Status Report Detail", f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

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

	@RequestMapping(value = "/showApprovalLogBook", method = RequestMethod.GET)
	public ModelAndView showApprovalLogBook(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			Date date = new Date();
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			fromDate = df.format(date);
			toDate = df.format(date);
			System.out.println("From Date And :" + fromDate + "ToDATE" + toDate);

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			model = new ModelAndView("new_po_report/apr_log_book");

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);

			model.addObject("typeId", 0);

		} catch (Exception e) {

			System.err.println("Exception in getIndents Indent" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	List<AprLogBook> aprLogBookList;

	@RequestMapping(value = "/getApprovalLogBook", method = RequestMethod.POST)
	public ModelAndView getApprovalLogBook(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			fromDate = request.getParameter("from_date");
			toDate = request.getParameter("to_date");
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			int docId = Integer.parseInt(request.getParameter("docId"));
			
			String typeIdList = new String();

			if (typeId == 0) {
				for (int i = 0; i < typeList.size(); i++) {
					typeIdList = typeIdList + "," + typeList.get(i).getTypeId();
				}
				typeIdList = typeIdList.substring(1, typeIdList.length());
				map.add("typeIdList", typeIdList);
			} else {
				map.add("typeIdList", typeId);
			}

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			map.add("docId", docId);

			AprLogBook[] aprLogBookArray = rest.postForObject(Constants.url + "/getAprLogBook", map,
					AprLogBook[].class);
			aprLogBookList = new ArrayList<>(Arrays.asList(aprLogBookArray));
			System.err.println("aprLogBookList " + aprLogBookList.toString());
			model = new ModelAndView("new_po_report/apr_log_book");

			model.addObject("aprLogBookList", aprLogBookList);

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

			model.addObject("typeList", typeList);

			model.addObject("typeId", typeId);
			model.addObject("docId", docId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
}
