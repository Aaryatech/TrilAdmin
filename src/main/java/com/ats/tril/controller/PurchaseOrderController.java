package com.ats.tril.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
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
import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.DeliveryTerms;
import com.ats.tril.model.DispatchMode;
import com.ats.tril.model.EnquiryDetail;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.FinancialYears;
import com.ats.tril.model.GetEnquiryHeader;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.GetPoDetailList;
import com.ats.tril.model.GetPoHeaderList;
import com.ats.tril.model.PaymentTerms;
import com.ats.tril.model.PoDetail;
import com.ats.tril.model.TaxForm;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.doc.DocumentBean;
import com.ats.tril.model.doc.SubDocument;
import com.ats.tril.model.indent.GetIndentByStatus;
import com.ats.tril.model.indent.GetIntendDetail;
import com.ats.tril.model.indent.IndentTrans;
import com.ats.tril.model.po.PoHeader;

@Controller
@Scope("session")
public class PurchaseOrderController {

	RestTemplate rest = new RestTemplate();
	List<GetIntendDetail> intendDetailList = new ArrayList<>();
	PoHeader PoHeader = new PoHeader();
	List<GetIntendDetail> getIntendDetailforJsp = new ArrayList<>();

	@RequestMapping(value = "/addPurchaseOrder", method = RequestMethod.GET)
	public ModelAndView addPurchaseOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("purchaseOrder/addPurchaseOrder");
		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");

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

			TaxForm[] taxFormList = rest.getForObject(Constants.url + "/getAllTaxForms", TaxForm[].class);
			model.addObject("taxFormList", taxFormList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("status", "0,1,2");
			GetIndentByStatus[] inted = rest.postForObject(Constants.url + "/getIntendsByStatus", map,
					GetIndentByStatus[].class);
			List<GetIndentByStatus> intedList = new ArrayList<GetIndentByStatus>(Arrays.asList(inted));
			model.addObject("intedList", intedList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/geIntendDetailByIndId", method = RequestMethod.GET)
	@ResponseBody
	public List<GetIntendDetail> geIntendDetailByIndId(HttpServletRequest request, HttpServletResponse response) {

		try {

			int indIdForGetList = Integer.parseInt(request.getParameter("indId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("indId", indIdForGetList);
			GetIntendDetail[] indentTrans = rest.postForObject(Constants.url + "/getIntendsDetailByIntendId", map,
					GetIntendDetail[].class);
			intendDetailList = new ArrayList<GetIntendDetail>(Arrays.asList(indentTrans));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return intendDetailList;
	}

	@RequestMapping(value = "/submitList", method = RequestMethod.POST)
	public ModelAndView submitEditEnquiry(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("purchaseOrder/addPurchaseOrder");

		try {
			PoHeader = new PoHeader();
			List<PoDetail> poDetailList = new ArrayList<>();

			getIntendDetailforJsp = new ArrayList<>();

			int indId = Integer.parseInt(request.getParameter("indMId"));
			String[] checkbox = request.getParameterValues("select_to_approve");

			try {
				int vendIdTemp = Integer.parseInt(request.getParameter("vendIdTemp"));
				model.addObject("vendIdTemp", vendIdTemp);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				int quotationTemp = Integer.parseInt(request.getParameter("quotationTemp"));
				model.addObject("quotationTemp", quotationTemp);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				int poTypeTemp = Integer.parseInt(request.getParameter("poTypeTemp"));
				model.addObject("poTypeTemp", poTypeTemp);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				int payIdTemp = Integer.parseInt(request.getParameter("payIdTemp"));
				model.addObject("payIdTemp", payIdTemp);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				int deliveryIdTemp = Integer.parseInt(request.getParameter("deliveryIdTemp"));
				model.addObject("deliveryIdTemp", deliveryIdTemp);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				int dispatchModeTemp = Integer.parseInt(request.getParameter("dispatchModeTemp"));
				model.addObject("dispatchModeTemp", dispatchModeTemp);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				String quotationDateTemp = request.getParameter("quotationDateTemp");
				model.addObject("quotationDateTemp", quotationDateTemp);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				String poDateTemp = request.getParameter("poDateTemp");
				model.addObject("date", poDateTemp);
			} catch (Exception e) {
				// TODO: handle exception
			}

			float poBasicValue = 0;
			float discValue = 0;

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

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("status", "0,1,2");
			GetIndentByStatus[] inted = rest.postForObject(Constants.url + "/getIntendsByStatus", map,
					GetIndentByStatus[].class);
			List<GetIndentByStatus> intedList = new ArrayList<GetIndentByStatus>(Arrays.asList(inted));
			model.addObject("intedList", intedList);

			TaxForm[] taxFormList = rest.getForObject(Constants.url + "/getAllTaxForms", TaxForm[].class);
			model.addObject("taxFormList", taxFormList);

			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < intendDetailList.size(); i++) {
				for (int j = 0; j < checkbox.length; j++) {
					System.out.println(checkbox[j] + intendDetailList.get(i).getIndDId());
					if (Integer.parseInt(checkbox[j]) == intendDetailList.get(i).getIndDId()) {
						PoDetail poDetail = new PoDetail();
						poDetail.setIndId(intendDetailList.get(i).getIndMId());
						poDetail.setSchDays(intendDetailList.get(i).getIndItemSchd());
						poDetail.setItemCode(intendDetailList.get(i).getItemCode());
						poDetail.setItemId(intendDetailList.get(i).getItemId());
						poDetail.setIndedQty(intendDetailList.get(i).getIndQty());
						poDetail.setItemUom(intendDetailList.get(i).getIndItemUom());
						poDetail.setItemQty(
								Integer.parseInt(request.getParameter("poQty" + intendDetailList.get(i).getIndDId())));
						poDetail.setPendingQty(
								Integer.parseInt(request.getParameter("poQty" + intendDetailList.get(i).getIndDId())));
						poDetail.setDiscPer(
								Integer.parseInt(request.getParameter("disc" + intendDetailList.get(i).getIndDId())));
						poDetail.setItemRate(
								Float.parseFloat(request.getParameter("rate" + intendDetailList.get(i).getIndDId())));
						poDetail.setSchRemark(request.getParameter("indRemark" + intendDetailList.get(i).getIndDId()));
						poDetail.setSchDays(Integer
								.parseInt(request.getParameter("indItemSchd" + intendDetailList.get(i).getIndDId())));
						poDetail.setSchDate(intendDetailList.get(i).getIndItemSchddt());
						poDetail.setBalanceQty(Integer
								.parseInt(request.getParameter("balanceQty" + intendDetailList.get(i).getIndDId())));
						c.setTime(sdf.parse(poDetail.getSchDate()));
						c.add(Calendar.DAY_OF_MONTH, poDetail.getSchDays());
						poDetail.setSchDate(sdf.format(c.getTime()));
						poDetail.setIndId(intendDetailList.get(i).getIndMId());
						poDetail.setIndMNo(intendDetailList.get(i).getIndMNo());
						poDetail.setBasicValue(poDetail.getItemQty() * poDetail.getItemRate());
						poDetail.setDiscValue((poDetail.getDiscPer() / 100) * poDetail.getBasicValue());
						poDetail.setLandingCost(
								poDetail.getItemQty() * poDetail.getItemRate() - poDetail.getDiscValue());
						poBasicValue = poBasicValue + poDetail.getBasicValue();
						discValue = discValue + poDetail.getDiscValue();
						poDetailList.add(poDetail);

						intendDetailList.get(i).setPoQty(
								Integer.parseInt(request.getParameter("poQty" + intendDetailList.get(i).getIndDId())));
						intendDetailList.get(i).setIndFyr(Integer
								.parseInt(request.getParameter("balanceQty" + intendDetailList.get(i).getIndDId())));
						intendDetailList.get(i).setDisc(
								Float.parseFloat(request.getParameter("disc" + intendDetailList.get(i).getIndDId())));
						intendDetailList.get(i).setRate(
								Float.parseFloat(request.getParameter("rate" + intendDetailList.get(i).getIndDId())));
						intendDetailList.get(i)
								.setIndRemark(request.getParameter("indRemark" + intendDetailList.get(i).getIndDId()));
						intendDetailList.get(i).setIndItemSchd(Integer
								.parseInt(request.getParameter("indItemSchd" + intendDetailList.get(i).getIndDId())));
						getIntendDetailforJsp.add(intendDetailList.get(i));
						PoHeader.setIndNo(intendDetailList.get(i).getIndMNo());
					}

				}
			}
			System.out.println(poDetailList);

			PoHeader.setDiscValue(discValue);
			PoHeader.setPoBasicValue(poBasicValue);
			PoHeader.setPoDetailList(poDetailList);

			model.addObject("poDetailList", poDetailList);
			model.addObject("indId", indId);
			model.addObject("poHeader", PoHeader);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/calculatePurchaseHeaderValues", method = RequestMethod.GET)
	@ResponseBody
	public PoHeader calculatePurchaseHeaderValues(HttpServletRequest request, HttpServletResponse response) {

		try {
			float total = 0;

			float packPer = Float.parseFloat(request.getParameter("packPer"));
			float packValue = Float.parseFloat(request.getParameter("packValue"));
			float insuPer = Float.parseFloat(request.getParameter("insuPer"));
			float insuValue = Float.parseFloat(request.getParameter("insuValue"));
			float freightPer = Float.parseFloat(request.getParameter("freightPer"));
			float freightValue = Float.parseFloat(request.getParameter("freightValue"));
			float otherPer = Float.parseFloat(request.getParameter("otherPer"));
			float otherValue = Float.parseFloat(request.getParameter("otherValue"));
			float taxPer = Float.parseFloat(request.getParameter("taxPer"));
			int taxId = Integer.parseInt(request.getParameter("taxId"));

			if (packPer != 0) {
				PoHeader.setPoPackPer(packPer);
				PoHeader.setPoPackVal((packPer / 100) * PoHeader.getPoBasicValue());
			} else {
				PoHeader.setPoPackPer(0);
				PoHeader.setPoPackVal(packValue);
			}

			if (insuPer != 0) {
				PoHeader.setPoInsuPer(insuPer);
				PoHeader.setPoInsuVal((insuPer / 100) * PoHeader.getPoBasicValue());
			} else {
				PoHeader.setPoInsuPer(0);
				PoHeader.setPoInsuVal(insuValue);
			}

			if (freightPer != 0) {
				PoHeader.setPoFrtPer(freightPer);
				PoHeader.setPoFrtVal((freightPer / 100) * PoHeader.getPoBasicValue());
			} else {
				PoHeader.setPoFrtPer(0);
				PoHeader.setPoFrtVal(freightValue);
			}

			total = PoHeader.getPoBasicValue() + PoHeader.getPoPackVal() + PoHeader.getPoInsuVal()
					+ PoHeader.getPoFrtVal() - PoHeader.getDiscValue();
			PoHeader.setPoTaxId(taxId);
			PoHeader.setPoTaxPer(taxPer);
			PoHeader.setPoTaxValue((taxPer / 100) * total);

			if (otherPer != 0) {
				total = PoHeader.getPoBasicValue() + PoHeader.getPoPackVal() + PoHeader.getPoInsuVal()
						+ PoHeader.getPoFrtVal() - PoHeader.getDiscValue() + PoHeader.getPoTaxValue();
				PoHeader.setOtherChargeAfter((otherPer / 100) * total);
			} else if (otherValue != 0) {
				PoHeader.setOtherChargeAfter(otherValue);
			} else {
				PoHeader.setOtherChargeAfter(0);
			}

			for (int i = 0; i < PoHeader.getPoDetailList().size(); i++) {
				float divFactor = PoHeader.getPoDetailList().get(i).getBasicValue() / PoHeader.getPoBasicValue() * 100;
				PoHeader.getPoDetailList().get(i).setPackValue(divFactor * PoHeader.getPoPackVal() / 100);
				PoHeader.getPoDetailList().get(i).setInsu(divFactor * PoHeader.getPoInsuVal() / 100);
				PoHeader.getPoDetailList().get(i).setFreightValue(divFactor * PoHeader.getPoFrtVal() / 100);
				PoHeader.getPoDetailList().get(i).setTaxValue(divFactor * PoHeader.getPoTaxValue() / 100);
				PoHeader.getPoDetailList().get(i)
						.setOtherChargesAfter(divFactor * PoHeader.getOtherChargeAfter() / 100);
				PoHeader.getPoDetailList().get(i).setLandingCost(PoHeader.getPoDetailList().get(i).getBasicValue()
						- PoHeader.getPoDetailList().get(i).getDiscValue()
						+ PoHeader.getPoDetailList().get(i).getPackValue() + PoHeader.getPoDetailList().get(i).getInsu()
						+ PoHeader.getPoDetailList().get(i).getFreightValue()
						+ PoHeader.getPoDetailList().get(i).getTaxValue()
						+ PoHeader.getPoDetailList().get(i).getOtherChargesAfter());
			}

			System.out.println(PoHeader);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return PoHeader;
	}

	@RequestMapping(value = "/submitPurchaseOrder", method = RequestMethod.POST)
	public String submitPurchaseOrder(HttpServletRequest request, HttpServletResponse response) {

		try {

			int vendId = Integer.parseInt(request.getParameter("vendId"));
			String quotation = request.getParameter("quotation");
			int poType = Integer.parseInt(request.getParameter("poType"));
			int payId = Integer.parseInt(request.getParameter("payId"));
			int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
			int dispatchMode = Integer.parseInt(request.getParameter("dispatchMode"));
			String quotationDate = request.getParameter("quotationDate");
			String poDate = request.getParameter("poDate");
			String poNo = request.getParameter("poNo");

			String packRemark = request.getParameter("packRemark");
			String insuRemark = request.getParameter("insuRemark");
			String freghtRemark = request.getParameter("freghtRemark");
			String otherRemark = request.getParameter("otherRemark");
			// ----------------------------Inv No---------------------------------
			DocumentBean docBean = null;
			try {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("docId", 2);
				map.add("catId", poType);
				map.add("date", DateConvertor.convertToYMD(poDate));
				RestTemplate restTemplate = new RestTemplate();

				docBean = restTemplate.postForObject(Constants.url + "getDocumentData", map, DocumentBean.class);
				String indMNo = docBean.getSubDocument().getCategoryPrefix() + "";
				int counter = docBean.getSubDocument().getCounter();
				int counterLenth = String.valueOf(counter).length();
				counterLenth = 4 - counterLenth;
				StringBuilder code = new StringBuilder(indMNo + "");

				for (int i = 0; i < counterLenth; i++) {
					String j = "0";
					code.append(j);
				}
				code.append(String.valueOf(counter));

				PoHeader.setPoNo("" + code);

				docBean.getSubDocument().setCounter(docBean.getSubDocument().getCounter() + 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// ----------------------------Inv No---------------------------------
			PoHeader.setVendId(vendId);
			PoHeader.setVendQuation(quotation);
			PoHeader.setPoType(poType);
			PoHeader.setPaymentTermId(payId);
			PoHeader.setDeliveryId(deliveryId);
			PoHeader.setDispatchId(dispatchMode);
			PoHeader.setVendQuationDate(DateConvertor.convertToYMD(quotationDate));
			PoHeader.setPoDate(DateConvertor.convertToYMD(poDate));

			PoHeader.setOtherChargeAfterRemark(otherRemark);
			PoHeader.setPoFrtRemark(freghtRemark);
			PoHeader.setPoInsuRemark(insuRemark);
			PoHeader.setPoPackRemark(packRemark);
			PoHeader.setIndId(PoHeader.getPoDetailList().get(0).getIndId());
			PoHeader.setDelStatus(1);
			System.out.println(PoHeader);
			PoHeader save = rest.postForObject(Constants.url + "/savePoHeaderAndDetail", PoHeader, PoHeader.class);
			System.out.println(save);
			if (save != null) {
				try {

					SubDocument subDocRes = rest.postForObject(Constants.url + "/saveSubDoc", docBean.getSubDocument(),
							SubDocument.class);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (save != null) {
				for (int i = 0; i < getIntendDetailforJsp.size(); i++) {
					getIntendDetailforJsp.get(i)
							.setIndMDate(DateConvertor.convertToYMD(getIntendDetailforJsp.get(i).getIndMDate()));
					if (getIntendDetailforJsp.get(i).getIndFyr() == 0)
						getIntendDetailforJsp.get(i).setIndDStatus(2);
					else if (getIntendDetailforJsp.get(i).getIndFyr() > 0
							&& getIntendDetailforJsp.get(i).getIndFyr() < getIntendDetailforJsp.get(i).getIndQty())
						getIntendDetailforJsp.get(i).setIndDStatus(1);
					else
						getIntendDetailforJsp.get(i).setIndDStatus(0);
				}
				ErrorMessage errorMessage = rest.postForObject(Constants.url + "/updateIndendPendingQty",
						getIntendDetailforJsp, ErrorMessage.class);
				System.out.println(errorMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfPurachaseOrder";
	}

	@RequestMapping(value = "/listOfPurachaseOrder", method = RequestMethod.GET)
	public ModelAndView listOfPurachaseOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("purchaseOrder/purchaseOrderList");
		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat display = new SimpleDateFormat("dd-MM-yyyy");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", sf.format(date));
			map.add("toDate", sf.format(date));

			GetPoHeaderList[] list = rest.postForObject(Constants.url + "/getPoHeaderListBetweenDate", map,
					GetPoHeaderList[].class);
			List<GetPoHeaderList> poList = new ArrayList<GetPoHeaderList>(Arrays.asList(list));

			model.addObject("poList", poList);
			model.addObject("date", display.format(date));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/getPoListByDate", method = RequestMethod.GET)
	@ResponseBody
	public List<GetPoHeaderList> getPoListByDate(HttpServletRequest request, HttpServletResponse response) {

		List<GetPoHeaderList> poList = new ArrayList<GetPoHeaderList>();
		try {

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			GetPoHeaderList[] list = rest.postForObject(Constants.url + "/getPoHeaderListBetweenDate", map,
					GetPoHeaderList[].class);
			poList = new ArrayList<GetPoHeaderList>(Arrays.asList(list));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return poList;
	}

	@RequestMapping(value = "/deletePurchaseOrder/{poId}", method = RequestMethod.GET)
	public String deleteEnquiry(@PathVariable int poId, HttpServletRequest request, HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("poId", poId);
			GetPoHeaderList purchaseOrder = rest.postForObject(Constants.url + "/getPoHeaderAndDetailByHeaderId", map,
					GetPoHeaderList.class);

			map = new LinkedMultiValueMap<>();
			map.add("indId", purchaseOrder.getIndId());
			GetIntendDetail[] GetIntendDetail = rest.postForObject(Constants.url + "/getIntendsDetailByIntendId", map,
					GetIntendDetail[].class);
			List<GetIntendDetail> updateIntendQty = new ArrayList<>(Arrays.asList(GetIntendDetail));

			for (int i = 0; i < updateIntendQty.size(); i++) {
				for (int j = 0; j < purchaseOrder.getPoDetailList().size(); j++) {
					if (purchaseOrder.getPoDetailList().get(j).getItemId() == updateIntendQty.get(i).getItemId()) {
						updateIntendQty.get(i).setIndFyr(purchaseOrder.getPoDetailList().get(j).getItemQty()
								+ updateIntendQty.get(i).getIndFyr());
						break;
					}

				}
				updateIntendQty.get(i).setIndMDate(DateConvertor.convertToYMD(updateIntendQty.get(i).getIndMDate()));
				if (updateIntendQty.get(i).getIndFyr() == 0)
					updateIntendQty.get(i).setIndDStatus(2);
				else if (updateIntendQty.get(i).getIndFyr() > 0
						&& updateIntendQty.get(i).getIndFyr() < updateIntendQty.get(i).getIndQty())
					updateIntendQty.get(i).setIndDStatus(1);
				else
					updateIntendQty.get(i).setIndDStatus(0);

			}
			ErrorMessage errorMessage = rest.postForObject(Constants.url + "/updateIndendPendingQty", updateIntendQty,
					ErrorMessage.class);
			System.out.println(errorMessage);

			if (errorMessage.isError() == false) {
				map = new LinkedMultiValueMap<>();
				map.add("poId", poId);

				errorMessage = rest.postForObject(Constants.url + "/deletePo", map, ErrorMessage.class);
				System.out.println(errorMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfPurachaseOrder";
	}

	GetPoHeaderList getPoHeader = new GetPoHeaderList();
	List<GetIntendDetail> getIntendDetailListforEdit = new ArrayList<>();

	@RequestMapping(value = "/editPurchaseOrder/{poId}", method = RequestMethod.GET)
	public ModelAndView editPurchaseOrder(@PathVariable int poId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("purchaseOrder/editPurchaseOrder");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("poId", poId);
			getPoHeader = rest.postForObject(Constants.url + "/getPoHeaderAndDetailByHeaderId", map,
					GetPoHeaderList.class);
			model.addObject("getPoHeader", getPoHeader);

			map = new LinkedMultiValueMap<>();
			map.add("indId", getPoHeader.getIndId());
			GetIntendDetail[] GetIntendDetail = rest.postForObject(Constants.url + "/getIntendsDetailByIntendId", map,
					GetIntendDetail[].class);
			getIntendDetailListforEdit = new ArrayList<>(Arrays.asList(GetIntendDetail));

			for (int j = 0; j < getPoHeader.getPoDetailList().size(); j++) {
				for (int i = 0; i < getIntendDetailListforEdit.size(); i++) {
					if (getPoHeader.getPoDetailList().get(j).getItemId() == getIntendDetailListforEdit.get(i)
							.getItemId()) {
						getPoHeader.getPoDetailList().get(j)
								.setBalanceQty(getIntendDetailListforEdit.get(i).getIndFyr());
						getPoHeader.getPoDetailList().get(j)
								.setSchDate(getIntendDetailListforEdit.get(i).getIndItemSchddt());
					}
				}
			}

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

			TaxForm[] taxFormList = rest.getForObject(Constants.url + "/getAllTaxForms", TaxForm[].class);
			model.addObject("taxFormList", taxFormList);

			map = new LinkedMultiValueMap<>();
			map.add("status", "0,1,2");
			GetIndentByStatus[] inted = rest.postForObject(Constants.url + "/getIntendsByStatus", map,
					GetIndentByStatus[].class);
			List<GetIndentByStatus> intedList = new ArrayList<GetIndentByStatus>(Arrays.asList(inted));
			model.addObject("intedList", intedList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/changeItemRate", method = RequestMethod.GET)
	@ResponseBody
	public GetPoHeaderList changeItemRate(HttpServletRequest request, HttpServletResponse response) {

		try {
			float total = 0;
			float poBasicValue = 0;
			float poDiscValue = 0;
			float rate = Float.parseFloat(request.getParameter("rate"));
			float disc = Float.parseFloat(request.getParameter("disc"));
			int key = Integer.parseInt(request.getParameter("key"));
			int poQty = Integer.parseInt(request.getParameter("poQty"));
			int balanceQty = Integer.parseInt(request.getParameter("balanceQty"));

			getPoHeader.getPoDetailList().get(key).setItemRate(rate);
			getPoHeader.getPoDetailList().get(key).setDiscPer(disc);
			getPoHeader.getPoDetailList().get(key).setItemQty(poQty);
			getPoHeader.getPoDetailList().get(key).setPendingQty(poQty);
			getPoHeader.getPoDetailList().get(key).setBalanceQty(balanceQty);

			getPoHeader.getPoDetailList().get(key).setBasicValue(getPoHeader.getPoDetailList().get(key).getItemRate()
					* getPoHeader.getPoDetailList().get(key).getItemQty());
			getPoHeader.getPoDetailList().get(key)
					.setDiscValue((getPoHeader.getPoDetailList().get(key).getDiscPer() / 100)
							* getPoHeader.getPoDetailList().get(key).getBasicValue());

			for (int i = 0; i < getPoHeader.getPoDetailList().size(); i++) {
				poBasicValue = poBasicValue + getPoHeader.getPoDetailList().get(i).getBasicValue();
				poDiscValue = poDiscValue + getPoHeader.getPoDetailList().get(i).getDiscValue();
			}

			getPoHeader.setPoBasicValue(poBasicValue);
			getPoHeader.setDiscValue(poDiscValue);

			if (getPoHeader.getPoPackPer() != 0) {
				getPoHeader.setPoPackVal((getPoHeader.getPoPackPer() / 100) * getPoHeader.getPoBasicValue());
			}

			if (getPoHeader.getPoInsuPer() != 0) {
				getPoHeader.setPoInsuVal((getPoHeader.getPoInsuPer() / 100) * getPoHeader.getPoBasicValue());
			}

			if (getPoHeader.getPoFrtPer() != 0) {
				getPoHeader.setPoFrtVal((getPoHeader.getPoFrtPer() / 100) * getPoHeader.getPoBasicValue());
			}

			total = getPoHeader.getPoBasicValue() + getPoHeader.getPoPackVal() + getPoHeader.getPoInsuVal()
					+ getPoHeader.getPoFrtVal() - getPoHeader.getDiscValue();
			getPoHeader.setPoTaxValue((getPoHeader.getPoTaxPer() / 100) * total);

			for (int i = 0; i < getPoHeader.getPoDetailList().size(); i++) {
				float divFactor = getPoHeader.getPoDetailList().get(i).getBasicValue() / getPoHeader.getPoBasicValue()
						* 100;
				getPoHeader.getPoDetailList().get(i).setPackValue(divFactor * getPoHeader.getPoPackVal() / 100);
				getPoHeader.getPoDetailList().get(i).setInsu(divFactor * getPoHeader.getPoInsuVal() / 100);
				getPoHeader.getPoDetailList().get(i).setFreightValue(divFactor * getPoHeader.getPoFrtVal() / 100);
				getPoHeader.getPoDetailList().get(i).setTaxValue(divFactor * getPoHeader.getPoTaxValue() / 100);
				getPoHeader.getPoDetailList().get(i)
						.setOtherChargesAfter(divFactor * getPoHeader.getOtherChargeAfter() / 100);
				getPoHeader.getPoDetailList().get(i)
						.setLandingCost(getPoHeader.getPoDetailList().get(i).getBasicValue()
								- getPoHeader.getPoDetailList().get(i).getDiscValue()
								+ getPoHeader.getPoDetailList().get(i).getPackValue()
								+ getPoHeader.getPoDetailList().get(i).getInsu()
								+ getPoHeader.getPoDetailList().get(i).getFreightValue()
								+ getPoHeader.getPoDetailList().get(i).getTaxValue()
								+ getPoHeader.getPoDetailList().get(i).getOtherChargesAfter());
			}

			System.out.println("getPoHeader" + getPoHeader);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return getPoHeader;
	}

	@RequestMapping(value = "/calculatePurchaseHeaderValuesInEdit", method = RequestMethod.GET)
	@ResponseBody
	public GetPoHeaderList calculatePurchaseHeaderValuesInEdit(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			float total = 0;

			float packPer = Float.parseFloat(request.getParameter("packPer"));
			float packValue = Float.parseFloat(request.getParameter("packValue"));
			float insuPer = Float.parseFloat(request.getParameter("insuPer"));
			float insuValue = Float.parseFloat(request.getParameter("insuValue"));
			float freightPer = Float.parseFloat(request.getParameter("freightPer"));
			float freightValue = Float.parseFloat(request.getParameter("freightValue"));
			float otherPer = Float.parseFloat(request.getParameter("otherPer"));
			float otherValue = Float.parseFloat(request.getParameter("otherValue"));
			float taxPer = Float.parseFloat(request.getParameter("taxPer"));
			int taxId = Integer.parseInt(request.getParameter("taxId"));

			if (packPer != 0) {
				getPoHeader.setPoPackPer(packPer);
				getPoHeader.setPoPackVal((packPer / 100) * getPoHeader.getPoBasicValue());
			} else {
				getPoHeader.setPoPackPer(0);
				getPoHeader.setPoPackVal(packValue);
			}

			if (insuPer != 0) {
				getPoHeader.setPoInsuPer(insuPer);
				getPoHeader.setPoInsuVal((insuPer / 100) * getPoHeader.getPoBasicValue());
			} else {
				getPoHeader.setPoInsuPer(0);
				getPoHeader.setPoInsuVal(insuValue);
			}

			if (freightPer != 0) {
				getPoHeader.setPoFrtPer(freightPer);
				getPoHeader.setPoFrtVal((freightPer / 100) * getPoHeader.getPoBasicValue());
			} else {
				getPoHeader.setPoFrtPer(0);
				getPoHeader.setPoFrtVal(freightValue);
			}

			total = getPoHeader.getPoBasicValue() + getPoHeader.getPoPackVal() + getPoHeader.getPoInsuVal()
					+ getPoHeader.getPoFrtVal() - getPoHeader.getDiscValue();
			getPoHeader.setPoTaxId(taxId);
			getPoHeader.setPoTaxPer(taxPer);
			getPoHeader.setPoTaxValue((taxPer / 100) * total);

			if (otherPer != 0) {
				total = getPoHeader.getPoBasicValue() + getPoHeader.getPoPackVal() + getPoHeader.getPoInsuVal()
						+ getPoHeader.getPoFrtVal() - getPoHeader.getDiscValue() + getPoHeader.getPoTaxValue();
				getPoHeader.setOtherChargeAfter((otherPer / 100) * total);
			} else if (otherValue != 0) {
				getPoHeader.setOtherChargeAfter(otherValue);
			} else {
				getPoHeader.setOtherChargeAfter(0);
			}

			for (int i = 0; i < getPoHeader.getPoDetailList().size(); i++) {
				float divFactor = getPoHeader.getPoDetailList().get(i).getBasicValue() / getPoHeader.getPoBasicValue()
						* 100;
				getPoHeader.getPoDetailList().get(i).setPackValue(divFactor * getPoHeader.getPoPackVal() / 100);
				getPoHeader.getPoDetailList().get(i).setInsu(divFactor * getPoHeader.getPoInsuVal() / 100);
				getPoHeader.getPoDetailList().get(i).setFreightValue(divFactor * getPoHeader.getPoFrtVal() / 100);
				getPoHeader.getPoDetailList().get(i).setTaxValue(divFactor * getPoHeader.getPoTaxValue() / 100);
				getPoHeader.getPoDetailList().get(i)
						.setOtherChargesAfter(divFactor * getPoHeader.getOtherChargeAfter() / 100);
				getPoHeader.getPoDetailList().get(i)
						.setLandingCost(getPoHeader.getPoDetailList().get(i).getBasicValue()
								- getPoHeader.getPoDetailList().get(i).getDiscValue()
								+ getPoHeader.getPoDetailList().get(i).getPackValue()
								+ getPoHeader.getPoDetailList().get(i).getInsu()
								+ getPoHeader.getPoDetailList().get(i).getFreightValue()
								+ getPoHeader.getPoDetailList().get(i).getTaxValue()
								+ getPoHeader.getPoDetailList().get(i).getOtherChargesAfter());
			}

			System.out.println(getPoHeader);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return getPoHeader;
	}

	@RequestMapping(value = "/submitEditPurchaseOrder", method = RequestMethod.POST)
	public String submitEditPurchaseOrder(HttpServletRequest request, HttpServletResponse response) {

		try {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			int vendId = Integer.parseInt(request.getParameter("vendId"));
			String quotation = request.getParameter("quotation");
			int poType = Integer.parseInt(request.getParameter("poType"));
			int payId = Integer.parseInt(request.getParameter("payId"));
			int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
			int dispatchMode = Integer.parseInt(request.getParameter("dispatchMode"));
			String quotationDate = request.getParameter("quotationDate");
			String poDate = request.getParameter("poDate");
			String poNo = request.getParameter("poNo");

			String packRemark = request.getParameter("packRemark");
			String insuRemark = request.getParameter("insuRemark");
			String freghtRemark = request.getParameter("freghtRemark");
			String otherRemark = request.getParameter("otherRemark");

			getPoHeader.setVendId(vendId);
			getPoHeader.setVendQuation(quotation);
			getPoHeader.setPoType(poType);
			getPoHeader.setPaymentTermId(payId);
			getPoHeader.setDeliveryId(deliveryId);
			getPoHeader.setDispatchId(dispatchMode);
			getPoHeader.setVendQuationDate(DateConvertor.convertToYMD(quotationDate));
			getPoHeader.setPoDate(DateConvertor.convertToYMD(poDate));
			getPoHeader.setPoNo(poNo);
			getPoHeader.setOtherChargeAfterRemark(otherRemark);
			getPoHeader.setPoFrtRemark(freghtRemark);
			getPoHeader.setPoInsuRemark(insuRemark);
			getPoHeader.setPoPackRemark(packRemark);
			getPoHeader.setDelStatus(1);
			System.out.println(getPoHeader);

			for (int i = 0; i < getPoHeader.getPoDetailList().size(); i++) {
				getPoHeader.getPoDetailList().get(i).setSchRemark(request.getParameter("indRemark" + i));

				c.setTime(sdf.parse(getPoHeader.getPoDetailList().get(i).getSchDate()));
				getPoHeader.getPoDetailList().get(i)
						.setSchDays(Integer.parseInt(request.getParameter("indItemSchd" + i)));
				c.add(Calendar.DAY_OF_MONTH, getPoHeader.getPoDetailList().get(i).getSchDays());
				getPoHeader.getPoDetailList().get(i).setSchDate(sdf.format(c.getTime()));
			}
			PoHeader save = rest.postForObject(Constants.url + "/savePoHeaderAndDetail", getPoHeader, PoHeader.class);
			System.out.println(save);

			if (save != null) {
				for (int i = 0; i < getIntendDetailListforEdit.size(); i++) {
					for (int j = 0; j < getPoHeader.getPoDetailList().size(); j++) {
						if (getPoHeader.getPoDetailList().get(j).getItemId() == getIntendDetailListforEdit.get(i)
								.getItemId()) {
							getIntendDetailListforEdit.get(i)
									.setIndFyr(getPoHeader.getPoDetailList().get(j).getBalanceQty());
							break;
						}

					}
					getIntendDetailListforEdit.get(i)
							.setIndMDate(DateConvertor.convertToYMD(getIntendDetailListforEdit.get(i).getIndMDate()));
					if (getIntendDetailListforEdit.get(i).getIndFyr() == 0)
						getIntendDetailListforEdit.get(i).setIndDStatus(2);
					else if (getIntendDetailListforEdit.get(i).getIndFyr() > 0 && getIntendDetailListforEdit.get(i)
							.getIndFyr() < getIntendDetailListforEdit.get(i).getIndQty())
						getIntendDetailListforEdit.get(i).setIndDStatus(1);
					else
						getIntendDetailListforEdit.get(i).setIndDStatus(0);

				}
				ErrorMessage errorMessage = rest.postForObject(Constants.url + "/updateIndendPendingQty",
						getIntendDetailListforEdit, ErrorMessage.class);
				System.out.println(errorMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfPurachaseOrder";
	}

}
