package com.ats.tril;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.model.Category;
import com.ats.tril.model.Company;
import com.ats.tril.model.ExportToExcel;
import com.ats.tril.model.GetCurrStockRol;
import com.ats.tril.model.GetCurrentStock;
import com.ats.tril.model.GetPODetail;
import com.ats.tril.model.GetSubDept;
import com.ats.tril.model.MonthItemWiseMrnReport;
import com.ats.tril.model.StockHeader;
import com.ats.tril.model.Type;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.accessright.ModuleJson;
import com.ats.tril.model.doc.DocumentBean;
import com.ats.tril.model.indent.GetIndents;
import com.ats.tril.model.login.UserResponse;
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

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	Company companyInfo = new Company();
	DecimalFormat df = new DecimalFormat("####0.00");

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	RestTemplate restTemp = new RestTemplate();

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "login";
	}

	List<GetCurrStockRol> lowReorderItemList = new ArrayList<>();

	@RequestMapping(value = "/showStoreDashboard", method = RequestMethod.GET)
	public ModelAndView showStoreDashboard(HttpServletRequest request, HttpServletResponse res) {
		ModelAndView mav = new ModelAndView("home");

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("status", "0,1");
			GetIndents[] indentList = restTemp.postForObject(Constants.url + "/getIndentList", map, GetIndents[].class);

			List<GetIndents> indentListRes = new ArrayList<GetIndents>(Arrays.asList(indentList));
			System.err.println(indentListRes.toString());
			mav.addObject("indentListRes", indentListRes);
			Category[] category = restTemp.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));

			StockHeader stockHeader = restTemp.getForObject(Constants.url + "/getCurrentRunningMonthAndYear",
					StockHeader.class);

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			String fromDate = stockHeader.getYear() + "-" + stockHeader.getMonth() + "-" + "01";
			String toDate = sf.format(date);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			GetCurrStockRol[] getCurrentStock = restTemp.postForObject(Constants.url + "/getItemsLessThanROLForDashB",
					map, GetCurrStockRol[].class);

			lowReorderItemList = new ArrayList<GetCurrStockRol>(Arrays.asList(getCurrentStock));
			System.err.println(lowReorderItemList.toString());
			mav.addObject("lowReorderItemList", lowReorderItemList);
			mav.addObject("categoryList", categoryList);

			Type[] type = restTemp.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			mav.addObject("typeList", typeList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/exportToExcelForStoreDash", method = RequestMethod.GET)
	@ResponseBody
	public List<ExportToExcel> exportToExcelForStoreDash(HttpServletRequest request, HttpServletResponse response) {
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
		try {

			int catId = Integer.parseInt(request.getParameter("catId"));
			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No");
			rowData.add("Item Code");
			rowData.add("Item Name");
			rowData.add("UOM");
			rowData.add("Current Stock");
			rowData.add("ROL Qty");
			rowData.add("Max Lvl");
			rowData.add("Min Lvl");
			// {lowReorderItemList.openingStock+lowReorderItemList.approveQty-lowReorderItemList.issueQty+lowReorderItemList.returnIssueQty-lowReorderItemList.damageQty-lowReorderItemList.gatepassQty+lowReorderItemList.gatepassReturnQty}"
			// /></td>

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			int cnt = 1;
			for (int i = 0; i < lowReorderItemList.size(); i++) {

				if (lowReorderItemList.get(i).getCatId() == catId) {

					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					cnt = cnt + i;
					rowData.add("" + (cnt));
					rowData.add("" + lowReorderItemList.get(i).getItemCode());
					rowData.add("" + lowReorderItemList.get(i).getItemName());
					rowData.add("" + lowReorderItemList.get(i).getItemUom());
					rowData.add("" + (lowReorderItemList.get(i).getOpeningStock()
							+ lowReorderItemList.get(i).getApproveQty() - lowReorderItemList.get(i).getIssueQty()
							+ lowReorderItemList.get(i).getReturnIssueQty() - lowReorderItemList.get(i).getDamageQty()
							- lowReorderItemList.get(i).getGatepassQty()
							+ lowReorderItemList.get(i).getGatepassReturnQty()));
					rowData.add("" + lowReorderItemList.get(i).getRolLevel());
					rowData.add("" + lowReorderItemList.get(i).getItemMaxLevel());
					rowData.add("" + lowReorderItemList.get(i).getItemMinLevel());

					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "GetCurrStockRol");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exportToExcelList;
	}

	@RequestMapping("/loginProcess")
	public ModelAndView helloWorld(HttpServletRequest request, HttpServletResponse res) throws IOException {

		String name = request.getParameter("username");
		String password = request.getParameter("password");

		ModelAndView mav = new ModelAndView("login");

		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		HttpSession session = request.getSession();

		try {
			System.out.println("Login Process " + name);

			if (name.equalsIgnoreCase("") || password.equalsIgnoreCase("") || name == null || password == null) {

				mav = new ModelAndView("login");
			} else {

				RestTemplate restTemplate = new RestTemplate();

				UserResponse userObj = restTemplate.getForObject(
						Constants.url + "/login?username=" + name + "&password=" + password, UserResponse.class);

				String loginResponseMessage = "";

				if (userObj.getErrorMessage().isError() == false) {

					session.setAttribute("UserDetail", userObj);
					UserResponse userResponse = (UserResponse) session.getAttribute("UserDetail");
					session.setAttribute("userInfo", userResponse.getUser());

					mav = new ModelAndView("welcome");
					session.setAttribute("userName", name);

					loginResponseMessage = "Login Successful";
					mav.addObject("loginResponseMessage", loginResponseMessage);

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
					int userId = userObj.getUser().getId();
					map.add("usrId", userId);
					System.out.println("Before web service");
					try {
						ParameterizedTypeReference<List<ModuleJson>> typeRef = new ParameterizedTypeReference<List<ModuleJson>>() {
						};
						ResponseEntity<List<ModuleJson>> responseEntity = restTemplate.exchange(
								Constants.url + "getRoleJson", HttpMethod.POST, new HttpEntity<>(map), typeRef);

						List<ModuleJson> newModuleList = responseEntity.getBody();

						// System.err.println("new Module List " +newModuleList.toString());

						session.setAttribute("newModuleList", newModuleList);
						session.setAttribute("sessionModuleId", 0);
						session.setAttribute("sessionSubModuleId", 0);

						/* Date date = new Date(); */
						map = new LinkedMultiValueMap<String, Object>();
						/*
						 * DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						 */ /*
							 * String fromDate = df.format(date); String toDate = df.format(date);
							 * 
							 * map.add("fromDate", fromDate); map.add("toDate",toDate);
							 */
						/*
						 * map.add("status", "0,1"); GetIndents[] indentList =
						 * restTemp.postForObject(Constants.url + "/getIndentList", map,
						 * GetIndents[].class);
						 * 
						 * List<GetIndents> indentListRes = new
						 * ArrayList<GetIndents>(Arrays.asList(indentList));
						 * System.err.println(indentListRes.toString()); mav.addObject("indentListRes",
						 * indentListRes);
						 * 
						 * StockHeader stockHeader = restTemp.getForObject(Constants.url +
						 * "/getCurrentRunningMonthAndYear",StockHeader.class); Category[] category =
						 * restTemp.getForObject(Constants.url + "/getAllCategoryByIsUsed",
						 * Category[].class); List<Category> categoryList = new
						 * ArrayList<Category>(Arrays.asList(category));
						 * System.err.println("categoryList:  "+categoryList.toString());
						 * 
						 * Date date = new Date(); SimpleDateFormat sf = new
						 * SimpleDateFormat("yyyy-MM-dd");
						 * 
						 * String fromDate=stockHeader.getYear()+"-"+stockHeader.getMonth()+"-"+"01";
						 * String toDate=sf.format(date);
						 * 
						 * map = new LinkedMultiValueMap<String, Object>(); map.add("fromDate",
						 * fromDate); map.add("toDate", toDate); GetCurrStockRol[] getCurrentStock
						 * =restTemp.postForObject(Constants.url + "/getItemsLessThanROLForDashB", map,
						 * GetCurrStockRol[].class);
						 * 
						 * List<GetCurrStockRol> lowReorderItemList = new
						 * ArrayList<GetCurrStockRol>(Arrays.asList(getCurrentStock));
						 * System.err.println(lowReorderItemList.toString());
						 * mav.addObject("lowReorderItemList", lowReorderItemList);
						 * mav.addObject("categoryList", categoryList);
						 * 
						 * 
						 * 
						 * Type[] type = restTemp.getForObject(Constants.url + "/getAlltype",
						 * Type[].class); List<Type> typeList = new
						 * ArrayList<Type>(Arrays.asList(type));
						 * 
						 * mav.addObject("typeList", typeList);
						 */

					} catch (Exception e) {
						e.printStackTrace();
					}
					return mav;
				} else {

					mav = new ModelAndView("login");
					System.out.println("Invalid login credentials");

				}

			}
		} catch (Exception e) {
			System.out.println("HomeController Login API Excep:  " + e.getMessage());
			e.printStackTrace();

		}

		return mav;

	}

	List<GetPoHeader> headerList;

	@RequestMapping(value = "/getPoListRes", method = RequestMethod.GET)
	public @ResponseBody List<GetPoHeader> getPoList(HttpServletRequest request, HttpServletResponse response) {

		headerList = new ArrayList<GetPoHeader>();
		try {
			int poType = Integer.parseInt(request.getParameter("poType"));
			int status = Integer.parseInt(request.getParameter("status"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("poType", poType);
			map.add("status", status);
			headerList = restTemp.postForObject(Constants.url + "getPoHeaderDashList", map, List.class);

			System.err.println(headerList.toString());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return headerList;
	}

	@RequestMapping(value = "/showAddMrn/{poType}/{vendorId}/{poId}/{poNo}", method = RequestMethod.GET)
	public ModelAndView showAddMrn(HttpServletRequest request, HttpServletResponse response, @PathVariable int poType,
			@PathVariable int vendorId, @PathVariable int poId, @PathVariable String poNo) {

		ModelAndView model = null;
		try {

			// poIdList = new String();
			// poDetailList = new ArrayList<GetPODetail>();

			// poDetailList = null;
			model = new ModelAndView("mrn/showAddMrn");
			RestTemplate rest = new RestTemplate();

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

			model.addObject("poType", poType);

			model.addObject("vendorId", vendorId);

			model.addObject("poId", poId);
			model.addObject("poNo", poNo);

			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			List<Type> typeList = new ArrayList<Type>(Arrays.asList(type));
			model.addObject("typeList", typeList);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			model.addObject("date", dateFormat.format(date));
			System.err.println("Inside show Add Mrn /showAddMrn/{poType}/{vendorId}/{poId} in HomeController");

		} catch (Exception e) {

			System.err.println("Exception in showing  showAddMrn/{poType}/{vendorId}/{poId}" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		System.out.println("User Logout");

		session.invalidate();
		return "redirect:/";
	}

	@ExceptionHandler(LoginFailException.class)
	public String redirectToLogin() {
		System.out.println("HomeController Login Fail Excep:");

		return "login";
	}

	@RequestMapping(value = "/sessionTimeOut", method = RequestMethod.GET)
	public String sessionTimeOut(HttpSession session) {
		System.out.println("User Logout");

		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/setSubModId", method = RequestMethod.GET)
	public @ResponseBody void setSubModId(HttpServletRequest request, HttpServletResponse response) {
		int subModId = Integer.parseInt(request.getParameter("subModId"));
		int modId = Integer.parseInt(request.getParameter("modId"));
		/*
		 * System.out.println("subModId " + subModId); System.out.println("modId " +
		 * modId);
		 */
		HttpSession session = request.getSession();
		session.setAttribute("sessionModuleId", modId);
		session.setAttribute("sessionSubModuleId", subModId);
		session.removeAttribute("exportExcelList");
	}

	@RequestMapping(value = "/showStoreDashBoardPdf/{catId}", method = RequestMethod.GET)
	public void mrnItemMonthWiseReportPdf(@PathVariable int catId, HttpServletRequest request,
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

			PdfPTable table = new PdfPTable(8);
			try {
				System.out.println("Inside PDF Table try");
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 0.4f, 3.0f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f, 1.2f });
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

				hcell = new PdfPCell(new Phrase("Item Code", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Item Name", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("UOM", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Current Stock", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("ROL Qty", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("MAx Level", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Min Level", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				table.addCell(hcell);

				int index = 0;

				for (int i = 0; i < lowReorderItemList.size(); i++) {

					if (lowReorderItemList.get(i).getCatId() == catId) {
						index++;
						System.out.println("------------------------" + index);
						PdfPCell cell;

						cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setPadding(3);
						cell.setPaddingRight(2);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + lowReorderItemList.get(i).getItemCode(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + lowReorderItemList.get(i).getItemName(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + lowReorderItemList.get(i).getItemUom(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						// {lowReorderItemList.openingStock+lowReorderItemList.approveQty-lowReorderItemList.issueQty+lowReorderItemList.returnIssueQty-lowReorderItemList.damageQty-lowReorderItemList.gatepassQty+lowReorderItemList.gatepassReturnQty}"
						// /></td>

						cell = new PdfPCell(new Phrase("" + (lowReorderItemList.get(i).getOpeningStock()
								+ lowReorderItemList.get(i).getApproveQty() - lowReorderItemList.get(i).getIssueQty()
								+ lowReorderItemList.get(i).getReturnIssueQty()
								- lowReorderItemList.get(i).getDamageQty() + lowReorderItemList.get(i).getGatepassQty()
								+ lowReorderItemList.get(i).getGatepassReturnQty()), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + lowReorderItemList.get(i).getRolLevel(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + lowReorderItemList.get(i).getItemMaxLevel(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + lowReorderItemList.get(i).getItemMinLevel(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setPaddingRight(2);
						cell.setPadding(3);
						table.addCell(cell);

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

				Paragraph headingDate = new Paragraph("Mrn Item Month Wise Report ", f1);
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

}
