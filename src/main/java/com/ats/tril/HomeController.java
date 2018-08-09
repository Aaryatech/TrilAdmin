package com.ats.tril;

import java.io.IOException;
import java.net.Inet4Address;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.model.Category;
import com.ats.tril.model.GetCurrStockRol;
import com.ats.tril.model.GetCurrentStock;
import com.ats.tril.model.GetSubDept;
import com.ats.tril.model.StockHeader;
import com.ats.tril.model.indent.GetIndents;
import com.ats.tril.model.po.GetPoHeader;
import com.ats.tril.model.po.PoHeader;
 

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
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
		
		model.addAttribute("serverTime", formattedDate );
		
		return "login";
	}
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
              
				StockHeader stockHeader = restTemp.getForObject(Constants.url + "/getCurrentRunningMonthAndYear",StockHeader.class);
				
				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				 
				String fromDate=stockHeader.getYear()+"-"+stockHeader.getMonth()+"-"+"01";
				String toDate=sf.format(date);
				
				map = new LinkedMultiValueMap<String, Object>();
				map.add("fromDate", fromDate);
	 			map.add("toDate", toDate);
	 			GetCurrStockRol[] getCurrentStock = restTemp.postForObject(Constants.url + "/getItemsLessThanROLForDashB", map, GetCurrStockRol[].class);

				List<GetCurrStockRol> lowReorderItemList = new ArrayList<GetCurrStockRol>(Arrays.asList(getCurrentStock));
				System.err.println(lowReorderItemList.toString());
				mav.addObject("lowReorderItemList", lowReorderItemList);
				mav.addObject("categoryList", categoryList);
			
				}
				catch (Exception e) {
					e.printStackTrace();
				}
		
		return mav;
	}
	@RequestMapping("/loginProcess")
	public ModelAndView helloWorld(HttpServletRequest request, HttpServletResponse res) throws IOException {

		String name = request.getParameter("username");
		String password = request.getParameter("password");

		ModelAndView mav = new ModelAndView("login");

		res.setContentType("text/html");
		try {
			System.out.println("Login Process " + name);
			System.out.println("password " + password);

			if (name.equalsIgnoreCase("") || password.equalsIgnoreCase("") || name == null || password == null) {

				mav = new ModelAndView("login");
			} else {

				/*RestTemplate rest = new RestTemplate();
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("userMob", name);
				map.add("password", password);
				LoginResponseExh loginResponse = rest.postForObject(Constants.url + "/loginExhibitor", map,
						LoginResponseExh.class);
				System.out.println("loginResponse" + loginResponse);*/

				if (name.equals("Tester") && password.equals("1234")) {
					mav = new ModelAndView("home");
					HttpSession session = request.getSession();
					try {
				/*	Date date = new Date();*/
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
/*					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
*/					/*String fromDate = df.format(date);
					String toDate = df.format(date);
                   
					map.add("fromDate", fromDate);
					map.add("toDate",toDate);*/
					map.add("status", "0,1");
					GetIndents[] indentList = restTemp.postForObject(Constants.url + "/getIndentList", map, GetIndents[].class);

					List<GetIndents> indentListRes = new ArrayList<GetIndents>(Arrays.asList(indentList));
					System.err.println(indentListRes.toString());
					mav.addObject("indentListRes", indentListRes);
					
					StockHeader stockHeader = restTemp.getForObject(Constants.url + "/getCurrentRunningMonthAndYear",StockHeader.class);
					Category[] category = restTemp.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
					List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));
                    System.err.println("categoryList:  "+categoryList.toString());
					
					Date date = new Date();
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					 
					String fromDate=stockHeader.getYear()+"-"+stockHeader.getMonth()+"-"+"01";
					String toDate=sf.format(date);
					
					map = new LinkedMultiValueMap<String, Object>();
					map.add("fromDate", fromDate);
		 			map.add("toDate", toDate);
		 			GetCurrStockRol[] getCurrentStock = restTemp.postForObject(Constants.url + "/getItemsLessThanROLForDashB", map, GetCurrStockRol[].class);

					List<GetCurrStockRol> lowReorderItemList = new ArrayList<GetCurrStockRol>(Arrays.asList(getCurrentStock));
					System.err.println(lowReorderItemList.toString());
					mav.addObject("lowReorderItemList", lowReorderItemList);
					mav.addObject("categoryList", categoryList);
					}
					catch (Exception e) {
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
	
	@RequestMapping(value = "/getPoListRes", method = RequestMethod.GET)
	public @ResponseBody List<GetPoHeader> getPoList(HttpServletRequest request,
			HttpServletResponse response) {

		List<GetPoHeader> headerList = new ArrayList<GetPoHeader>();
		try {
			int poType=Integer.parseInt(request.getParameter("poType"));
			int status=Integer.parseInt(request.getParameter("status"));
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("poType", poType);
			map.add("status",status);
			headerList=restTemp.postForObject(Constants.url+"getPoHeaderDashList", map, List.class);
	     
			System.err.println(headerList.toString());
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		return headerList;
	}
}
