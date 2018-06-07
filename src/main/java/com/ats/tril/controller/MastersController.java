package com.ats.tril.controller;

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
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.FinancialYears;

@Controller
@Scope("session")
public class MastersController {

	RestTemplate rest = new RestTemplate();

	@RequestMapping(value = "/addFinancialYear", method = RequestMethod.GET)
	public ModelAndView addDispachMode(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addFinancialYear");
		try {

			FinancialYears[] finYearList = rest.getForObject(Constants.url + "/getAllFinancialYears",
					FinancialYears[].class);
			model.addObject("finYearList", finYearList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/insertFinancialYear", method = RequestMethod.POST)
	public String insertFinancialYear(HttpServletRequest request, HttpServletResponse response) {

		try {
			String finYearId = request.getParameter("finYearId");
			
			System.out.println("finYearId"+finYearId);
			String finYear = request.getParameter("finYear");

			FinancialYears financialYears = new FinancialYears();
			if (finYearId == "" || finYearId == null)
				financialYears.setFinYearId(0);
			else
				financialYears.setFinYearId(Integer.parseInt(finYearId));
			financialYears.setFinYear(finYear);

			financialYears.setIsUsed(1);

			FinancialYears res = rest.postForObject(Constants.url + "/saveFinancialYears", financialYears,
					FinancialYears.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addFinancialYear";
	}

	@RequestMapping(value = "/editFinacialYear/{finYearId}", method = RequestMethod.GET)
	public ModelAndView editFinacialYear(@PathVariable int finYearId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addFinancialYear");
		try {
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("finYearId", finYearId);
			FinancialYears editFinancialYears = rest.postForObject(Constants.url + "/getFinancialYearsByFYId", map,
					FinancialYears.class);
			model.addObject("editYear", editFinancialYears);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteFinancialYear/{finYearId}", method = RequestMethod.GET)
	public String deleteLocation(@PathVariable int finYearId, HttpServletRequest request,
			HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("organizer/addOrganizer");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("finYearId", finYearId);
			ErrorMessage delete = rest.postForObject(Constants.url + "/deleteFinancialYears", map, ErrorMessage.class);
			System.out.println(delete);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addFinancialYear";
	}

}
