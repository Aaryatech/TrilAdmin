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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import com.ats.tril.model.ConsumptionReportWithCatId;
import com.ats.tril.model.indent.GetIndents;
import com.ats.tril.model.mrn.GetMrnDetail;
import com.ats.tril.model.mrn.GetMrnHeader;
import com.ats.tril.model.yearlyreport.YearlyMrnIssueData;
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
public class YearlyReportController {

	RestTemplate rest = new RestTemplate();
	GetMrnHeader getMrnHeader = null;
	List<GetMrnDetail> getMrnDetailList = null;
	List<ConsumptionReportWithCatId> mrnReportList = new ArrayList<ConsumptionReportWithCatId>();
	List<ConsumptionReportWithCatId> issueReportList = new ArrayList<ConsumptionReportWithCatId>();
	List<Category> categoryList = new ArrayList<Category>();
	List<ConsumptionReportWithCatId> mrnIssueReportList = new ArrayList<ConsumptionReportWithCatId>();

	List<YearlyMrnIssueData> reportList = null;

	String fromDateForPdf;
	String toDateForPdf;
	Company companyInfo = new Company();

	@RequestMapping(value = "/showMrnIssueYearlyReport", method = RequestMethod.GET)
	public ModelAndView showPurchaseDashboard(HttpServletRequest request, HttpServletResponse response) {

		System.err.println("Hello---------------------");

		ModelAndView model = new ModelAndView("report/yearlyMrnIssueReport");
		try {
			try {

				Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
				categoryList = new ArrayList<Category>(Arrays.asList(category));

				model.addObject("categoryList", categoryList);

			} catch (Exception e) {
				e.printStackTrace();
			}

			companyInfo = rest.getForObject(Constants.url + "getCompanyDetails", Company.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	// AJAX-------------
	@RequestMapping(value = "/mrnIssueYearlyReport", method = RequestMethod.GET)
	@ResponseBody
	public List<YearlyMrnIssueData> mrnIssueYearlyReport(HttpServletRequest request, HttpServletResponse response) {

		List<YearlyMrnIssueData> reportList = new ArrayList<>();

		String fromToYear = "";
		int catId = -1;

		try {

			fromToYear = request.getParameter("fromToYear");
			catId = Integer.parseInt(request.getParameter("catId"));

			System.err.println("YEAR -------- " + fromToYear);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromToYear", fromToYear);
			map.add("catId", catId);

			YearlyMrnIssueData[] dataList = rest.postForObject(Constants.url + "/getMrnAndIssueYearlyReport", map,
					YearlyMrnIssueData[].class);

			reportList = new ArrayList<YearlyMrnIssueData>(Arrays.asList(dataList));

			System.err.println("REPORT --------------------- " + reportList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reportList;
	}

	@RequestMapping(value = "/getMrnIssueYearlyReport", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getMrnIssueYearlyReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;

		model = new ModelAndView("report/yearlyMrnIssueReport");

		reportList = new ArrayList<>();

		String fromToYear = "";
		int catId = -1;

		try {

			fromToYear = request.getParameter("fromToYear");
			try {
				System.err.println("JSP CAT ID------------ " + request.getParameter("catId"));
				catId = Integer.parseInt(request.getParameter("catId"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.err.println("CAT ---------- " + catId);
			System.err.println("YEAR -------- " + fromToYear);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromToYear", fromToYear);
			map.add("catId", catId);

			YearlyMrnIssueData[] dataList = rest.postForObject(Constants.url + "/getMrnAndIssueYearlyReport", map,
					YearlyMrnIssueData[].class);

			reportList = new ArrayList<YearlyMrnIssueData>(Arrays.asList(dataList));

			model.addObject("reportList", reportList);
			model.addObject("fromToYear", fromToYear);
			model.addObject("catId", catId);

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			categoryList = new ArrayList<Category>(Arrays.asList(category));

			model.addObject("categoryList", categoryList);

			System.err.println("REPORT --------------------- " + reportList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	// ----PDF---------
	@RequestMapping(value = "/mrnIssueYearlyReportPdf/{fromToYear}/{catId}", method = RequestMethod.GET)
	public void mrnIssueYearlyReportPdf(@PathVariable String fromToYear, @PathVariable String catId,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {

		BufferedOutputStream outStream = null;
		try {

			Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f);
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date());
			document.addHeader("Date: ", reportDate);
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();

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

			int colmSize = (categoryList.size() * 2) + 4;
			PdfPTable table = new PdfPTable(colmSize);
			PdfPTable table2 = new PdfPTable(colmSize);

			try {
				table.setWidthPercentage(100);
				float[] arry = new float[colmSize];
				arry[0] = 0.4f;
				arry[1] = 1.7f;

				for (int i = 2; i < colmSize; i++) {
					arry[i] = 1.0f;
				}

				System.out.println("colmSize " + colmSize);
				System.out.println("arry " + Arrays.toString(arry) + arry.length);

				table.setWidths(arry);
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

				hcell = new PdfPCell(new Phrase("Cat Name", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				for (int i = 0; i < 6; i++) {

					hcell = new PdfPCell(new Phrase(reportList.get(i).getDateStr(), headFont1));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(BaseColor.PINK);
					hcell.setColspan(2);
					table.addCell(hcell);
				}

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

				for (int i = 0; i < 6; i++) {

					hcell = new PdfPCell(new Phrase("MRN", headFont1));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(BaseColor.PINK);
					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase("ISSUE", headFont1));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(BaseColor.PINK);
					table.addCell(hcell);
				}

				int index = 0;
				if (reportList != null) {

					for (int k = 0; k < reportList.get(0).getTypeWiseAmount().size(); k++) {

						index++;
						PdfPCell cell;

						cell = new PdfPCell(new Phrase("" + index, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(
								new Phrase(reportList.get(0).getTypeWiseAmount().get(k).getTypeName(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						for (int i = 0; i < 6; i++) {

							for (int j = 0; j < reportList.get(i).getTypeWiseAmount().size(); j++) {

								if (reportList.get(0).getTypeWiseAmount().get(k).getTypeId() == reportList.get(i)
										.getTypeWiseAmount().get(j).getTypeId()) {

									cell = new PdfPCell(
											new Phrase("" + reportList.get(i).getTypeWiseAmount().get(j).getMrnAmount(),
													headFont));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setPaddingRight(2);
									cell.setPadding(3);
									table.addCell(cell);

									cell = new PdfPCell(new Phrase(
											"" + reportList.get(i).getTypeWiseAmount().get(j).getIssueAmount(),
											headFont));
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

				// -----------table end------------------------

				// ----------table 2 start------------------

				table2.setWidthPercentage(100);
				float[] arry2 = new float[colmSize];
				arry2[0] = 0.4f;
				arry2[1] = 1.7f;

				for (int i = 2; i < colmSize; i++) {
					arry2[i] = 1.0f;
				}

				System.out.println("colmSize " + colmSize);
				System.out.println("arry " + Arrays.toString(arry2) + arry2.length);

				table2.setWidths(arry2);

				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase("SR.", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table2.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Cat Name", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table2.addCell(hcell);

				for (int i = 6; i < 12; i++) {

					hcell = new PdfPCell(new Phrase(reportList.get(i).getDateStr(), headFont1));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(BaseColor.PINK);
					hcell.setColspan(2);
					table2.addCell(hcell);
				}

				hcell = new PdfPCell();
				hcell.setPadding(4);
				hcell = new PdfPCell(new Phrase(" ", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table2.addCell(hcell);

				hcell = new PdfPCell(new Phrase(" ", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table2.addCell(hcell);

				for (int i = 6; i < 12; i++) {

					hcell = new PdfPCell(new Phrase("MRN", headFont1));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(BaseColor.PINK);
					table2.addCell(hcell);

					hcell = new PdfPCell(new Phrase("ISSUE", headFont1));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setBackgroundColor(BaseColor.PINK);
					table2.addCell(hcell);
				}

				int index2 = 0;
				if (reportList != null) {

					for (int k = 0; k < reportList.get(0).getTypeWiseAmount().size(); k++) {

						index2++;
						PdfPCell cell;

						cell = new PdfPCell(new Phrase("" + index, headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPadding(3);
						table2.addCell(cell);

						cell = new PdfPCell(
								new Phrase(reportList.get(0).getTypeWiseAmount().get(k).getTypeName(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table2.addCell(cell);

						for (int i = 6; i < 12; i++) {

							for (int j = 0; j < reportList.get(i).getTypeWiseAmount().size(); j++) {

								if (reportList.get(0).getTypeWiseAmount().get(k).getTypeId() == reportList.get(i)
										.getTypeWiseAmount().get(j).getTypeId()) {

									cell = new PdfPCell(
											new Phrase("" + reportList.get(i).getTypeWiseAmount().get(j).getMrnAmount(),
													headFont));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setPaddingRight(2);
									cell.setPadding(3);
									table2.addCell(cell);

									cell = new PdfPCell(new Phrase(
											"" + reportList.get(i).getTypeWiseAmount().get(j).getIssueAmount(),
											headFont));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setPaddingRight(2);
									cell.setPadding(3);
									table2.addCell(cell);

								}

							}

						}

					}

				}

				// --------table 2 end----------------------

				document.open();
				Paragraph company = new Paragraph(companyInfo.getCompanyName() + "\n", f);
				company.setAlignment(Element.ALIGN_CENTER);
				document.add(company);

				Paragraph heading1 = new Paragraph(companyInfo.getFactoryAdd(), f1);
				heading1.setAlignment(Element.ALIGN_CENTER);
				document.add(heading1);
				Paragraph ex2 = new Paragraph("\n");
				document.add(ex2);

				Paragraph reportName = new Paragraph("MRN/ISSUE VALUEATION YEARLY REPORT", f1);
				reportName.setAlignment(Element.ALIGN_CENTER);
				document.add(reportName);

				int year = Integer.parseInt(fromToYear);

				Paragraph headingDate = new Paragraph("" + year + " - " + (year + 1), f1);
				headingDate.setAlignment(Element.ALIGN_CENTER);
				document.add(headingDate);

				String category = "";
				int cat = Integer.parseInt(catId);
				if (cat == -1) {
					category = "All Category";
				} else {
					if (categoryList != null) {
						for (int i = 0; i < categoryList.size(); i++) {
							if (cat == categoryList.get(i).getCatId()) {
								category = categoryList.get(i).getCatDesc();
								break;
							}
						}
					}
				}

				Paragraph headingCategory = new Paragraph("" + category, f1);
				headingCategory.setAlignment(Element.ALIGN_CENTER);
				document.add(headingCategory);

				Paragraph ex3 = new Paragraph("\n");
				document.add(ex3);
				table.setHeaderRows(1);
				document.add(table);

				// -----------NEW PAGE-----------
				document.newPage();

				document.add(company);
				document.add(heading1);
				document.add(ex2);
				document.add(reportName);
				document.add(headingDate);
				document.add(headingCategory);
				document.add(ex3);

				table2.setHeaderRows(1);
				document.add(table2);

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

}
