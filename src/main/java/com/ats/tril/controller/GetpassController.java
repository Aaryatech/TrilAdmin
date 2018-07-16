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
import com.ats.tril.model.EnquiryDetail;
import com.ats.tril.model.EnquiryHeader;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetEnquiryDetail;
import com.ats.tril.model.GetEnquiryHeader;
import com.ats.tril.model.item.GetItem;
import com.ats.tril.model.GetItemGroup;
import com.ats.tril.model.GetItemSubGrp;
import com.ats.tril.model.GetpassDetail;
import com.ats.tril.model.GetpassDetailItemName;
import com.ats.tril.model.GetpassHeader;
import com.ats.tril.model.GetpassHeaderItemName;
import com.ats.tril.model.GetpassItemVen;
import com.ats.tril.model.GetpassReturn;
import com.ats.tril.model.GetpassReturnDetail;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.item.ItemList;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
@Scope("session")
public class GetpassController {

	RestTemplate rest = new RestTemplate();
	List<GetpassDetail> addItemInGetpassDetail = new ArrayList<GetpassDetail>();
	List<GetpassReturnDetail> getpassReturnDetailList = new ArrayList<GetpassReturnDetail>();
	List<GetpassDetailItemName> getpassDetailItemName = new ArrayList<GetpassDetailItemName>();

	GetpassHeader editGetpassHeader = new GetpassHeader();
	GetpassReturn getpassReturn = new GetpassReturn();

	@RequestMapping(value = "/addGetpassHeader", method = RequestMethod.GET)
	public ModelAndView addGetpassHeader(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("getpass/addGetpassHeader");
		try {
			addItemInGetpassDetail = new ArrayList<GetpassDetail>();
			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

			Category[] categoryRes = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> catList = new ArrayList<Category>(Arrays.asList(categoryRes));

			model.addObject("catList", catList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/getItemIdByGroupId", method = RequestMethod.GET)
	@ResponseBody
	public List<GetItem> getItemIdByGroupId(HttpServletRequest request, HttpServletResponse response) {

		List<GetItem> itemList = new ArrayList<GetItem>();
		try {
			int grpId = Integer.parseInt(request.getParameter("grpId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("groupId", grpId);
			ItemList resList = rest.postForObject(Constants.url + "itemListByGroupId", map, ItemList.class);

			itemList = resList.getItems();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return itemList;
	}

	@RequestMapping(value = "/addItemInGetpassList", method = RequestMethod.GET)
	@ResponseBody
	public List<GetpassDetail> addItemInGetpassList(HttpServletRequest request, HttpServletResponse response) {

		try {

			int itemId = Integer.parseInt(request.getParameter("itemId"));
			int catId = Integer.parseInt(request.getParameter("catId"));
			int grpId = Integer.parseInt(request.getParameter("grpId"));
			float qty = Float.parseFloat(request.getParameter("qty"));

			String editIndex = request.getParameter("editIndex");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String Date = sf.format(date);

			System.out.println("Date" + Date);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("itemId", itemId);
			GetItem item = rest.postForObject(Constants.url + "/getItemByItemId", map, GetItem.class);

			if (editIndex.equalsIgnoreCase("") || editIndex.equalsIgnoreCase(null)) {
				GetpassDetail getpassDetail = new GetpassDetail();
				getpassDetail.setGpItemId(itemId);
				getpassDetail.setItemCode(item.getItemCode());
				getpassDetail.setGpQty(qty);
				getpassDetail.setIsUsed(1);
				getpassDetail.setGpDetailId(0);
				getpassDetail.setGpNoDays(0);
				getpassDetail.setGpReturnDate(Date);
				getpassDetail.setGpStatus(0);
				getpassDetail.setCatId(catId);
				getpassDetail.setGroupId(grpId);
				getpassDetail.setGpRemQty(0);
				getpassDetail.setGpRetQty(0);

				addItemInGetpassDetail.add(getpassDetail);
			} else {
				int index = Integer.parseInt(editIndex);
				addItemInGetpassDetail.get(index).setGpItemId(itemId);
				addItemInGetpassDetail.get(index).setGpQty(qty);
				addItemInGetpassDetail.get(index).setGpDetailId(0);

				addItemInGetpassDetail.get(index).setGpReturnDate(Date);
				addItemInGetpassDetail.get(index).setItemCode(item.getItemCode());
				addItemInGetpassDetail.get(index).setGpStatus(0);
				addItemInGetpassDetail.get(index).setCatId(catId);
				addItemInGetpassDetail.get(index).setGroupId(grpId);
				addItemInGetpassDetail.get(index).setIsUsed(1);
				addItemInGetpassDetail.get(index).setGpRemQty(0);
				addItemInGetpassDetail.get(index).setGpRetQty(0);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return addItemInGetpassDetail;
	}

	@RequestMapping(value = "/deleteItemFromGetpass", method = RequestMethod.GET)
	@ResponseBody
	public List<GetpassDetail> deleteItemFromGetpass(HttpServletRequest request, HttpServletResponse response) {

		try {

			int index = Integer.parseInt(request.getParameter("index"));

			addItemInGetpassDetail.remove(index);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return addItemInGetpassDetail;
	}

	@RequestMapping(value = "/editItemInAddGetpass", method = RequestMethod.GET)
	@ResponseBody
	public GetpassDetail editItemInAddGetpass(HttpServletRequest request, HttpServletResponse response) {

		GetpassDetail edit = new GetpassDetail();

		try {

			int index = Integer.parseInt(request.getParameter("index"));

			edit = addItemInGetpassDetail.get(index);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return edit;
	}

	@RequestMapping(value = "/insertGetpassNonreturnable", method = RequestMethod.POST)
	public String insertGetpassNonreturnable(HttpServletRequest request, HttpServletResponse response) {

		try {

			GetpassHeader getpassHeaderRes = new GetpassHeader();

			int vendId = Integer.parseInt(request.getParameter("vendId"));
			int gpNo = Integer.parseInt(request.getParameter("gpNo"));
			String gpDate = request.getParameter("gpDate");
			int stock = Integer.parseInt(request.getParameter("stock"));
			String sendingWith = request.getParameter("sendingWith");
			String remark1 = request.getParameter("remark1");
			int returnFor = Integer.parseInt(request.getParameter("returnFor"));

			String Date = DateConvertor.convertToYMD(gpDate);

			GetpassHeader getpassHeader = new GetpassHeader();
			getpassHeader.setGpVendor(vendId);
			getpassHeader.setGpNo(gpNo);
			getpassHeader.setGpReturnDate(Date);
			getpassHeader.setIsUsed(1);
			getpassHeader.setForRepair(returnFor);
			getpassHeader.setSendingWith(sendingWith);
			getpassHeader.setRemark1(remark1);
			getpassHeader.setRemark2("null");
			getpassHeader.setIsStockable(stock);
			getpassHeader.setGpType(0);
			getpassHeader.setGpDate(Date);

			getpassHeader.setGetpassDetail(addItemInGetpassDetail);

			System.out.println(getpassHeader);
			GetpassHeader res = rest.postForObject(Constants.url + "/saveGetPassHeaderDetail", getpassHeader,
					GetpassHeader.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addGetpassHeader";
	}

	@RequestMapping(value = "/listOfGetpass", method = RequestMethod.GET)
	public ModelAndView listOfGetpass(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("getpass/listOfGetpass");
		try {

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteGetpassHeader/{gpId}", method = RequestMethod.GET)
	public String deleteGetpassHeader(@PathVariable int gpId, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("gpId", gpId);

			ErrorMessage errorMessage = rest.postForObject(Constants.url + "/deleteGetpassHeader", map,
					ErrorMessage.class);
			System.out.println(errorMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfGetpass";
	}

	@RequestMapping(value = "/editGetpassHeader/{gpId}", method = RequestMethod.GET)
	public ModelAndView editGetpassHeader(@PathVariable int gpId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("getpass/editGetpass");
		try {
			addItemInGetpassDetail = new ArrayList<GetpassDetail>();

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

			Category[] categoryRes = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> catList = new ArrayList<Category>(Arrays.asList(categoryRes));

			model.addObject("catList", catList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("gpId", gpId);

			editGetpassHeader = rest.postForObject(Constants.url + "/getGetpassItemHeaderAndDetail", map,
					GetpassHeader.class);
			addItemInGetpassDetail = editGetpassHeader.getGetpassDetail();

			GetItem[] item = rest.getForObject(Constants.url + "/getAllItems", GetItem[].class);
			List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(item));
			model.addObject("itemList", itemList);

			model.addObject("editGetpassHeader", editGetpassHeader);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/submitEditGetpass", method = RequestMethod.POST)
	public String submitEditGetpass(HttpServletRequest request, HttpServletResponse response) {

		try {

			int vendId = Integer.parseInt(request.getParameter("vendId"));
			int gpNo = Integer.parseInt(request.getParameter("gpNo"));
			String gpDate = request.getParameter("gpDate");
			int stock = Integer.parseInt(request.getParameter("stock"));
			String sendingWith = request.getParameter("sendingWith");
			String remark1 = request.getParameter("remark1");
			int returnFor = Integer.parseInt(request.getParameter("returnFor"));

			String Date = DateConvertor.convertToYMD(gpDate);

			GetpassHeader getpassHeader = new GetpassHeader();
			getpassHeader.setGpVendor(vendId);
			getpassHeader.setGpNo(gpNo);
			getpassHeader.setGpReturnDate(Date);
			getpassHeader.setIsUsed(1);
			getpassHeader.setForRepair(returnFor);
			getpassHeader.setSendingWith(sendingWith);
			getpassHeader.setRemark1(remark1);
			getpassHeader.setRemark2("null");
			getpassHeader.setIsStockable(stock);
			getpassHeader.setGpType(0);
			getpassHeader.setGpDate(Date);

			getpassHeader.setGetpassDetail(addItemInGetpassDetail);

			System.out.println(getpassHeader);
			GetpassHeader res = rest.postForObject(Constants.url + "/saveGetPassHeaderDetail", getpassHeader,
					GetpassHeader.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfGatepass";
	}

	@RequestMapping(value = "/getGetpassNonByVendId", method = RequestMethod.GET)
	@ResponseBody
	public List<GetpassItemVen> getGetpassNonByVendId(HttpServletRequest request, HttpServletResponse response) {

		List<GetpassItemVen> passList = new ArrayList<GetpassItemVen>();
		try {

			String vendId = request.getParameter("vendId");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("vendId", vendId);

			GetpassItemVen[] list = rest.postForObject(Constants.url + "/getGetpassNonReturnable", map,
					GetpassItemVen[].class);
			passList = new ArrayList<GetpassItemVen>(Arrays.asList(list));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return passList;
	}

	@RequestMapping(value = "/addGetpassReturnable", method = RequestMethod.GET)
	public ModelAndView addGetpassReturnable(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("getpass/addGetpassReturnable");
		try {
			addItemInGetpassDetail = new ArrayList<GetpassDetail>();
			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

			Category[] categoryRes = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> catList = new ArrayList<Category>(Arrays.asList(categoryRes));

			model.addObject("catList", catList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/addItemInGetpassReturnableList", method = RequestMethod.GET)
	@ResponseBody
	public List<GetpassDetail> addItemInGetpassReturnableList(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int itemId = Integer.parseInt(request.getParameter("itemId"));
			int catId = Integer.parseInt(request.getParameter("catId"));
			int grpId = Integer.parseInt(request.getParameter("grpId"));
			float qty = Float.parseFloat(request.getParameter("qty"));
			int noOfDays = Integer.parseInt(request.getParameter("noOfDays"));
			String editIndex = request.getParameter("editIndex");

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String Date = sf.format(date);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("itemId", itemId);
			GetItem item = rest.postForObject(Constants.url + "/getItemByItemId", map, GetItem.class);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			try {

				c.setTime(sdf.parse(Date));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			c.add(Calendar.DAY_OF_MONTH, noOfDays);
			String newDate = sdf.format(c.getTime());

			System.out.println("Date after Addition: " + newDate);

			if (editIndex.equalsIgnoreCase("") || editIndex.equalsIgnoreCase(null)) {
				GetpassDetail getpassDetail = new GetpassDetail();
				getpassDetail.setGpItemId(itemId);
				getpassDetail.setItemCode(item.getItemCode());
				getpassDetail.setGpQty(qty);
				getpassDetail.setIsUsed(1);
				getpassDetail.setGpDetailId(0);
				getpassDetail.setGpNoDays(noOfDays);
				getpassDetail.setGpReturnDate(newDate);
				getpassDetail.setGpStatus(0);
				getpassDetail.setCatId(catId);
				getpassDetail.setGroupId(grpId);
				getpassDetail.setGpRemQty(0);
				getpassDetail.setGpRetQty(0);

				addItemInGetpassDetail.add(getpassDetail);
			} else {
				int index = Integer.parseInt(editIndex);
				addItemInGetpassDetail.get(index).setGpItemId(itemId);
				addItemInGetpassDetail.get(index).setGpQty(qty);
				addItemInGetpassDetail.get(index).setGpDetailId(0);

				addItemInGetpassDetail.get(index).setGpReturnDate(newDate);
				addItemInGetpassDetail.get(index).setItemCode(item.getItemCode());
				addItemInGetpassDetail.get(index).setGpStatus(0);
				addItemInGetpassDetail.get(index).setCatId(catId);
				addItemInGetpassDetail.get(index).setGroupId(grpId);
				addItemInGetpassDetail.get(index).setIsUsed(1);
				addItemInGetpassDetail.get(index).setGpRemQty(0);
				addItemInGetpassDetail.get(index).setGpRetQty(0);
				addItemInGetpassDetail.get(index).setGpNoDays(noOfDays);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return addItemInGetpassDetail;
	}

	@RequestMapping(value = "/insertGetpassReturnable", method = RequestMethod.POST)
	public String insertGetpassReturnable(HttpServletRequest request, HttpServletResponse response) {

		try {

			GetpassHeader getpassHeaderRes = new GetpassHeader();

			int vendId = Integer.parseInt(request.getParameter("vendId"));
			int gpNo = Integer.parseInt(request.getParameter("gpNo"));
			String gpDate = request.getParameter("gpDate");
			int stock = Integer.parseInt(request.getParameter("stock"));
			String sendingWith = request.getParameter("sendingWith");
			String remark1 = request.getParameter("remark1");
			int returnFor = Integer.parseInt(request.getParameter("returnFor"));
			int noOfDays = Integer.parseInt(request.getParameter("noOfDays"));

			String Date = DateConvertor.convertToYMD(gpDate);

			String oldDate = "2017-01-29";
			System.out.println("Date before Addition: " + oldDate);
			// Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			try {
				// Setting the date to the given date
				c.setTime(sdf.parse(gpDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// Number of Days to add
			c.add(Calendar.DAY_OF_MONTH, noOfDays);
			// Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());
			// Displaying the new Date after addition of Days
			System.out.println("Date after Addition: " + newDate);

			GetpassHeader getpassHeader = new GetpassHeader();
			getpassHeader.setGpVendor(vendId);
			getpassHeader.setGpNo(gpNo);
			getpassHeader.setGpReturnDate(newDate);
			getpassHeader.setIsUsed(1);
			getpassHeader.setForRepair(returnFor);
			getpassHeader.setSendingWith(sendingWith);
			getpassHeader.setRemark1(remark1);
			getpassHeader.setRemark2("null");
			getpassHeader.setIsStockable(stock);
			getpassHeader.setGpType(1);
			getpassHeader.setGpDate(Date);

			getpassHeader.setGetpassDetail(addItemInGetpassDetail);

			System.out.println(getpassHeader);
			GetpassHeader res = rest.postForObject(Constants.url + "/saveGetPassHeaderDetail", getpassHeader,
					GetpassHeader.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addGetpassHeader";
	}

	@RequestMapping(value = "/listOfGetpassReturnable", method = RequestMethod.GET)
	public ModelAndView listOfGetpassReturnable(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("getpass/listOfGetpassReturnable");
		try {

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/getGetpassRetByVendId", method = RequestMethod.GET)
	@ResponseBody
	public List<GetpassItemVen> getGetpassRetByVendId(HttpServletRequest request, HttpServletResponse response) {

		List<GetpassItemVen> passList = new ArrayList<GetpassItemVen>();
		try {

			String vendId = request.getParameter("vendId");
			String[] gpStatusList = request.getParameterValues("gpStatusList[]");

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < gpStatusList.length; i++) {
				sb = sb.append(gpStatusList[i] + ",");

			}
			String items = sb.toString();
			items = items.substring(0, items.length() - 1);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("vendId", vendId);
			map.add("gpStatusList", items);

			GetpassItemVen[] list = rest.postForObject(Constants.url + "/getGetpassReturnable", map,
					GetpassItemVen[].class);
			passList = new ArrayList<GetpassItemVen>(Arrays.asList(list));
			System.out.println("passList" + passList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return passList;
	}

	@RequestMapping(value = "/deleteGetpassHeaderReturnable/{gpId}", method = RequestMethod.GET)
	public String deleteGetpassHeaderReturnable(@PathVariable int gpId, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("gpId", gpId);

			ErrorMessage errorMessage = rest.postForObject(Constants.url + "/deleteGetpassHeader", map,
					ErrorMessage.class);
			System.out.println(errorMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/listOfGetpassReturnable";
	}

	@RequestMapping(value = "/editGetpassHeaderRet/{gpId}", method = RequestMethod.GET)
	public ModelAndView editGetpassHeaderRet(@PathVariable int gpId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("getpass/editGetpassReturnable");
		try {
			addItemInGetpassDetail = new ArrayList<GetpassDetail>();

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

			Category[] categoryRes = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> catList = new ArrayList<Category>(Arrays.asList(categoryRes));

			model.addObject("catList", catList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("gpId", gpId);

			editGetpassHeader = rest.postForObject(Constants.url + "/getGetpassItemHeaderAndDetail", map,
					GetpassHeader.class);
			addItemInGetpassDetail = editGetpassHeader.getGetpassDetail();

			GetItem[] item = rest.getForObject(Constants.url + "/getAllItems", GetItem[].class);
			List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(item));
			model.addObject("itemList", itemList);

			model.addObject("editGetpassHeader", editGetpassHeader);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/addGetpassReturn/{gpId}", method = RequestMethod.GET)
	public ModelAndView addGetpassReturn(@PathVariable int gpId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("getpass/return");
		try {
			addItemInGetpassDetail = new ArrayList<GetpassDetail>();

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("gpId", gpId);

			GetpassHeaderItemName getpassHeaderItemName = rest.postForObject(
					Constants.url + "/getGetpassItemHeaderAndDetailWithItemName", map, GetpassHeaderItemName.class);
			getpassDetailItemName = getpassHeaderItemName.getGetpassDetailItemNameList();
			model.addObject("getpassHeaderItemName", getpassHeaderItemName);
			model.addObject("getpassDetailItemName", getpassDetailItemName);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/insertGetpassReturn", method = RequestMethod.POST)
	public String insertGetpassReturn(HttpServletRequest request, HttpServletResponse response) {

		try {

			GetpassReturn getpassReturnRes = new GetpassReturn();

			int vendId = Integer.parseInt(request.getParameter("vendId"));
			int gpNo = Integer.parseInt(request.getParameter("gpNo"));
			int returnNo = Integer.parseInt(request.getParameter("returnNo"));
			String gpDate = request.getParameter("gpDate");

			String remark = request.getParameter("remark");

			String Date = DateConvertor.convertToYMD(gpDate);

			GetpassReturn getpassReturn = new GetpassReturn();
			getpassReturn.setVendorId(vendId);
			getpassReturn.setGpNo(gpNo);
			getpassReturn.setGpReturnDate(Date);
			getpassReturn.setIsUsed(1);
			getpassReturn.setRemark(remark);
			getpassReturn.setRemark1("null");
			getpassReturn.setReturnNo(returnNo);

			getpassReturn.setGetpassReturnDetailList(getpassReturnDetailList);

			System.out.println(getpassReturn);
			GetpassReturn res = rest.postForObject(Constants.url + "/saveGetPassReturnHeaderDetail", getpassReturn,
					GetpassReturn.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addGetpassReturn";
	}

}
