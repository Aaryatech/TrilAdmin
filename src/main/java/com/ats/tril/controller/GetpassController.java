package com.ats.tril.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.model.Category;
import com.ats.tril.model.EnquiryDetail;
import com.ats.tril.model.item.GetItem;
import com.ats.tril.model.GetItemGroup;
import com.ats.tril.model.GetItemSubGrp;
import com.ats.tril.model.GetpassDetail;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.item.ItemList;

@Controller
@Scope("session")
public class GetpassController {

	RestTemplate rest = new RestTemplate();
	List<GetpassDetail> addItemInGetpassDetail = new ArrayList<GetpassDetail>();

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
			String gpReturnDate = request.getParameter("gpReturnDate");

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
				getpassDetail.setGpReturnDate(gpReturnDate);
				getpassDetail.setGpStatus(0);
				getpassDetail.setCatId(catId);
				getpassDetail.setGroupId(grpId);

				addItemInGetpassDetail.add(getpassDetail);
			} else {
				int index = Integer.parseInt(editIndex);
				addItemInGetpassDetail.get(index).setGpItemId(itemId);
				addItemInGetpassDetail.get(index).setGpQty(qty);
				addItemInGetpassDetail.get(index).setGpDetailId(0);

				addItemInGetpassDetail.get(index).setGpReturnDate(gpReturnDate);
				addItemInGetpassDetail.get(index).setItemCode(item.getItemCode());
				addItemInGetpassDetail.get(index).setGpStatus(0);
				addItemInGetpassDetail.get(index).setCatId(catId);
				addItemInGetpassDetail.get(index).setGroupId(grpId);
				addItemInGetpassDetail.get(index).setIsUsed(1);

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

}
