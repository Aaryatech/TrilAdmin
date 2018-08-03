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
import com.ats.tril.model.EnquiryDetail;
import com.ats.tril.model.EnquiryHeader;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetEnquiryDetail;
import com.ats.tril.model.GetEnquiryHeader;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.doc.DocumentBean;
import com.ats.tril.model.doc.SubDocument;
import com.ats.tril.model.indent.GetIndentByStatus;
import com.ats.tril.model.indent.GetIntendDetail;

@Controller
@Scope("session")
public class EnqController {

	RestTemplate rest = new RestTemplate();
	List<GetIntendDetail> intendDetailList = new ArrayList<>();
	EnquiryHeader enquiryHeader = new EnquiryHeader();
	List<EnquiryDetail> enqDetailList = null;
	List<GetIntendDetail> getIntendDetailforJsp = new ArrayList<>();
	GetEnquiryHeader editEnquiry = new GetEnquiryHeader();
	List<GetEnquiryDetail> detailList = new ArrayList<GetEnquiryDetail>();

	@RequestMapping(value = "/showAddEnq", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request, HttpServletResponse response) {
		enqDetailList = new ArrayList<>();
		ModelAndView model = new ModelAndView("enquiry/addEnq");
		try {

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

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

	@RequestMapping(value = "/geIntendDetailByIndIdForEnquiry", method = RequestMethod.GET)
	@ResponseBody
	public List<GetIntendDetail> geIntendDetailByIndIdForEnquiry(HttpServletRequest request,
			HttpServletResponse response) {

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

	@RequestMapping(value = "/submitEnqList", method = RequestMethod.POST)
	public ModelAndView submitEnqList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("enquiry/addEnq");

		try {
			enqDetailList = new ArrayList<>();
			enquiryHeader = new EnquiryHeader();

			getIntendDetailforJsp = new ArrayList<>();

			int indId = Integer.parseInt(request.getParameter("indMId"));
			String[] checkbox = request.getParameterValues("select_to_approve");

			try {
				int vendIdTemp = Integer.parseInt(request.getParameter("vendIdTemp"));
				model.addObject("vendIdTemp", vendIdTemp);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				String enqDateTemp = request.getParameter("enqDate");
				model.addObject("enqDateTemp", enqDateTemp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * try { indId = Integer.parseInt(request.getParameter("indId"));
			 * model.addObject("indIdTemp", indId); } catch (Exception e) {
			 * e.printStackTrace(); }
			 */

			try {
				String enqRemarkTemp = request.getParameter("enqRemark");
				model.addObject("enqRemarkTemp", enqRemarkTemp);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));
			model.addObject("vendorList", vendorList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("status", "0,1,2");
			GetIndentByStatus[] inted = rest.postForObject(Constants.url + "/getIntendsByStatus", map,
					GetIndentByStatus[].class);
			List<GetIndentByStatus> intedList = new ArrayList<GetIndentByStatus>(Arrays.asList(inted));
			model.addObject("intedList", intedList);

			for (int i = 0; i < intendDetailList.size(); i++) {
				for (int j = 0; j < checkbox.length; j++) {
					System.out.println(checkbox[j] + intendDetailList.get(i).getIndDId());
					if (Integer.parseInt(checkbox[j]) == intendDetailList.get(i).getIndDId()) {
						EnquiryDetail enqDetail = new EnquiryDetail();
						enqDetail.setIndId(intendDetailList.get(i).getIndMId());
						enqDetail.setIndNo(intendDetailList.get(i).getIndMNo());
						enqDetail.setItemCode(intendDetailList.get(i).getItemCode());
						enqDetail.setItemId(intendDetailList.get(i).getItemId());
						enqDetail.setEnqUom(intendDetailList.get(i).getIndItemUom());
						enqDetail.setEnqRemark(intendDetailList.get(i).getIndRemark());
						enqDetail.setEnqDetailDate(intendDetailList.get(i).getIndMDate());
						enqDetail.setEnqItemDesc(intendDetailList.get(i).getIndItemDesc());
						enqDetail.setDelStatus(1);
						enqDetail.setEnqQty(
								Integer.parseInt(request.getParameter("enqQty" + intendDetailList.get(i).getIndDId())));

						enqDetailList.add(enqDetail);

						enquiryHeader.setIndNo(intendDetailList.get(i).getIndMNo());
					}

				}
			}
			System.out.println("enqDetailList" + enqDetailList);

			model.addObject("enqDetailList", enqDetailList);
			model.addObject("indId", indId);
			model.addObject("enquiryHeader", enquiryHeader);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/insertEnquiryByIndent", method = RequestMethod.POST)
	public String insertEnquiryByIndent(HttpServletRequest request, HttpServletResponse response) {

		try {

			List<EnquiryHeader> enquiryHeaderList = new ArrayList<EnquiryHeader>();

			String[] vendId = request.getParameterValues("vendId");
			String enqRemark = request.getParameter("enqRemark");
			String enqDate = request.getParameter("enqDate");

			int indId = Integer.parseInt(request.getParameter("indId"));

			String Date = DateConvertor.convertToYMD(enqDate);
			List<SubDocument> docList = new ArrayList<SubDocument>();
			for (int i = 0; i < vendId.length; i++) {

				EnquiryHeader enquiryHeader = new EnquiryHeader();

				DocumentBean docBean = null;
				try {

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
					map.add("docId", 8);
					map.add("catId", 1);
					map.add("date", DateConvertor.convertToYMD(enqDate));
					RestTemplate restTemplate = new RestTemplate();

					docBean = restTemplate.postForObject(Constants.url + "getDocumentData", map, DocumentBean.class);
					String indMNo = docBean.getSubDocument().getCategoryPrefix() + "";
					int counter = docBean.getSubDocument().getCounter();
					int counterLenth = String.valueOf(counter).length();
					counterLenth = 4 - counterLenth;
					StringBuilder code = new StringBuilder(indMNo + "");

					for (int k = 0; k < counterLenth; k++) {
						String j = "0";
						code.append(j);
					}
					code.append(String.valueOf(counter));

					enquiryHeader.setEnqNo("" + code);

					docBean.getSubDocument().setCounter(docBean.getSubDocument().getCounter() + 1);
					docList.add(docBean.getSubDocument());
				} catch (Exception e) {
					e.printStackTrace();
				}
				enquiryHeader.setVendId(Integer.parseInt(vendId[i]));
				enquiryHeader.setEnqRemark(enqRemark);
				enquiryHeader.setEnqDate(Date);
				enquiryHeader.setDelStatus(1);
				enquiryHeader.setEnqStatus(1);
				System.out.println("indId" + indId);
				if (indId == intendDetailList.get(i).getIndMId()) {
					enquiryHeader.setIndNo(intendDetailList.get(i).getIndMNo());
					enquiryHeader.setIndId(intendDetailList.get(i).getIndMId());

				}
				enquiryHeader.setEnquiryDetailList(enqDetailList);
				enquiryHeaderList.add(enquiryHeader);

			}

			ErrorMessage res = rest.postForObject(Constants.url + "/saveEnquiryHeaderAndDetail", enquiryHeaderList,
					ErrorMessage.class);
			if (res.isError() == false) {
				try {
					for (int l = 0; l < docList.size(); l++) {
						SubDocument subDocRes = rest.postForObject(Constants.url + "/saveSubDoc", docList.get(l),
								SubDocument.class);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfEnq";
	}

	@RequestMapping(value = "/listOfEnq", method = RequestMethod.GET)
	public ModelAndView listOfEnquiry(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("enquiry/listOfEnq");
		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat display = new SimpleDateFormat("dd-MM-yyyy");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", sf.format(date));
			map.add("toDate", sf.format(date));

			GetEnquiryHeader[] list = rest.postForObject(Constants.url + "/getEnquiryHeaderListBetweenDate", map,
					GetEnquiryHeader[].class);
			List<GetEnquiryHeader> enquiryList = new ArrayList<GetEnquiryHeader>(Arrays.asList(list));

			model.addObject("enquiryList", enquiryList);
			model.addObject("date", display.format(date));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/getEnqListByDate", method = RequestMethod.GET)
	@ResponseBody
	public List<GetEnquiryHeader> getEnqListByDate(HttpServletRequest request, HttpServletResponse response) {

		List<GetEnquiryHeader> enquiryList = new ArrayList<GetEnquiryHeader>();
		try {

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			int status = Integer.parseInt(request.getParameter("status"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("status", status);

			GetEnquiryHeader[] list = rest.postForObject(Constants.url + "/getEnqHeaderListBetweenDate", map,
					GetEnquiryHeader[].class);
			enquiryList = new ArrayList<GetEnquiryHeader>(Arrays.asList(list));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return enquiryList;
	}

	@RequestMapping(value = "/deleteEnq/{enqId}", method = RequestMethod.GET)
	public String deleteEnquiry(@PathVariable int enqId, HttpServletRequest request, HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("enqId", enqId);

			ErrorMessage errorMessage = rest.postForObject(Constants.url + "/deleteEnquiryHeader", map,
					ErrorMessage.class);
			System.out.println(errorMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfEnq";
	}

	@RequestMapping(value = "/editEnq/{enqId}", method = RequestMethod.GET)
	public ModelAndView editEnquiry(@PathVariable int enqId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("enquiry/editEnq");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("enqId", enqId);

			GetEnquiryHeader editEnquiry = rest.postForObject(Constants.url + "/getEnquiryHeaderAndDetail", map,
					GetEnquiryHeader.class);
			detailList = editEnquiry.getEnquiryDetailList();

			System.out.println("detailList" + detailList);

			model.addObject("editEnquiry", editEnquiry);
			model.addObject("detailList", detailList);

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

			MultiValueMap<String, Object> map2 = new LinkedMultiValueMap<>();
			map2.add("status", "0,1,2");
			GetIndentByStatus[] inted = rest.postForObject(Constants.url + "/getIntendsByStatus", map2,
					GetIndentByStatus[].class);
			List<GetIndentByStatus> intedList = new ArrayList<GetIndentByStatus>(Arrays.asList(inted));
			model.addObject("intedList", intedList);

			int indIdForGetList = editEnquiry.getIndId();
			System.out.println("indIdForGetList" + indIdForGetList);
			MultiValueMap<String, Object> map1 = new LinkedMultiValueMap<String, Object>();
			map1.add("indId", indIdForGetList);
			GetIntendDetail[] indentTrans = rest.postForObject(Constants.url + "/getIntendsDetailByIntendId", map1,
					GetIntendDetail[].class);
			intendDetailList = new ArrayList<GetIntendDetail>(Arrays.asList(indentTrans));
			model.addObject("intendDetailList", intendDetailList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/submitEditEnq", method = RequestMethod.POST)
	public String submitEditEnq(HttpServletRequest request, HttpServletResponse response) {

		try {

			List<GetEnquiryHeader> enquiryHeaderList = new ArrayList<GetEnquiryHeader>();

			String enqRemark = request.getParameter("enqRemark");
			String enqDate = request.getParameter("enqDate");

			String Date = DateConvertor.convertToYMD(enqDate);

			editEnquiry.setEnqRemark(enqRemark);
			editEnquiry.setEnqDate(Date);
			editEnquiry.setDelStatus(1);
			editEnquiry.setEnquiryDetailList(detailList);
			enquiryHeaderList.add(editEnquiry);

			System.out.println(enquiryHeaderList);

			ErrorMessage res = rest.postForObject(Constants.url + "/saveEnquiryHeaderAndDetail", enquiryHeaderList,
					ErrorMessage.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfEnquiry";
	}
}
