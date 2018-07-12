package com.ats.tril.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.model.DeliveryTerms;
import com.ats.tril.model.DispatchMode;
import com.ats.tril.model.FinancialYears;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.PaymentTerms;
import com.ats.tril.model.Vendor;

@Controller
@Scope("session")
public class PurchaseOrderController {
	
	
	RestTemplate rest = new RestTemplate();

	@RequestMapping(value = "/addPurchaseOrder", method = RequestMethod.GET)
	public ModelAndView addPurchaseOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("purchaseOrder/addPurchaseOrder");
		try {
			
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");

			FinancialYears[] finYearList = rest.getForObject(Constants.url + "/getAllFinancialYears",
					FinancialYears[].class);
			model.addObject("finYearList", finYearList);
			model.addObject("date", sf.format(date));
			
			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes)); 
			model.addObject("vendorList", vendorList);
			
			DispatchMode[] dispatchMode = rest.getForObject(Constants.url + "/getAllDispatchModesByIsUsed",
					DispatchMode[].class);
			List<DispatchMode> dispatchModeList = new ArrayList<DispatchMode>(Arrays.asList(dispatchMode));

			model.addObject("dispatchModeList", dispatchModeList);
			
			PaymentTerms[] paymentTermsLists = rest.getForObject(Constants.url + "/getAllPaymentTermsByIsUsed",
					PaymentTerms[].class);
			model.addObject("paymentTermsList", paymentTermsLists);
			
			DeliveryTerms[] deliveryTerms = rest.getForObject(Constants.url + "/getAllDeliveryTermsByIsUsed",
					DeliveryTerms[].class);
			List<DeliveryTerms> deliveryTermsList = new ArrayList<DeliveryTerms>(Arrays.asList(deliveryTerms));

			model.addObject("deliveryTermsList", deliveryTermsList);
			
			GetItem[] item = rest.getForObject(Constants.url + "/getAllItems",  GetItem[].class); 
			List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(item));
			model.addObject("itemList", itemList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

}
