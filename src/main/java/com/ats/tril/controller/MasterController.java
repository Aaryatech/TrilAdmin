package com.ats.tril.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class MasterController {
	
	
	
	RestTemplate rest = new RestTemplate();

	@RequestMapping(value = "/addCategory", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addCategory");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	
	@RequestMapping(value = "/addDepartment", method = RequestMethod.GET)
	public ModelAndView addDepartment(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addDepartment");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/addDispachMode", method = RequestMethod.GET)
	public ModelAndView addDispachMode(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addDispachMode");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/addDeliveryTerm", method = RequestMethod.GET)
	public ModelAndView addDeliveryTerm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addDeliveryTerm");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	
	@RequestMapping(value = "/addItemGroup", method = RequestMethod.GET)
	public ModelAndView addItemGroup(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addItemGroup");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	
	@RequestMapping(value = "/addItemSubGroup", method = RequestMethod.GET)
	public ModelAndView addItemSubGroup(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addItemSubGroup");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/addPaymentTerm", method = RequestMethod.GET)
	public ModelAndView addPaymentTerm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addPaymentTerm");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/addSubDepartment", method = RequestMethod.GET)
	public ModelAndView addSubDepartment(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addSubDepartment");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/addTaxForm", method = RequestMethod.GET)
	public ModelAndView addTaxForm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addTaxForm");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

}
