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
import com.ats.tril.model.Damage;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetDamage;
import com.ats.tril.model.GetItemGroup; 

@Controller
@Scope("session")
public class DamageController {
	
	RestTemplate rest = new RestTemplate();
	List<Damage> damageList = new ArrayList<Damage>();
	GetDamage editDamage = new GetDamage();
	
	@RequestMapping(value = "/addDamage", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request, HttpServletResponse response) {
		 damageList = new ArrayList<Damage>();
		
		ModelAndView model = new ModelAndView("damage/addDamage");
		try {
			GetItemGroup[] itemGroupList = rest.getForObject(Constants.url + "/getAllItemGroupByIsUsed",
					GetItemGroup[].class);
			model.addObject("itemGroupList", itemGroupList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/addItmeInDamageList", method = RequestMethod.GET)
	@ResponseBody
	public List<Damage> addItmeInDamageList(HttpServletRequest request, HttpServletResponse response) {

		 
		try {
			
			String itemName = request.getParameter("itemName");
			int itemId = Integer.parseInt(request.getParameter("itemId")); 
			String reason = request.getParameter("reason"); 
			int qty = Integer.parseInt(request.getParameter("qty"));  
			float value = Float.parseFloat(request.getParameter("value"));
			
			Damage damage = new Damage();
			damage.setItemId(itemId);
			damage.setItemName(itemName);
			damage.setQty(qty);
			damage.setValue(value);
			damage.setReason(reason);
			damage.setDelStatus(1);
			damageList.add(damage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return damageList;
	}
	
	@RequestMapping(value = "/deleteDamageFromList", method = RequestMethod.GET)
	@ResponseBody
	public List<Damage> deleteDamageFromList(HttpServletRequest request, HttpServletResponse response) {

		 
		try {
			 
			int index = Integer.parseInt(request.getParameter("index")); 
			 
			damageList.remove(index);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return damageList;
	}
	
	@RequestMapping(value = "/submitDamageList", method = RequestMethod.POST)
	public String submitDamageList(HttpServletRequest request, HttpServletResponse response) {
		 
		try {
			String date = request.getParameter("date"); 
			
			for(int i = 0 ; i<damageList.size() ; i++)
			{
				damageList.get(i).setDate(date);
			}
			
			System.out.println(damageList);
			
			ErrorMessage res = rest.postForObject(Constants.url + "/saveDamage",damageList,
					ErrorMessage.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addDamage";
	}
	
	@RequestMapping(value = "/getDamageList", method = RequestMethod.GET)
	public ModelAndView getDamageList(HttpServletRequest request, HttpServletResponse response) {
	 
		ModelAndView model = new ModelAndView("damage/getDamageList");
		try {
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat show = new SimpleDateFormat("dd-MM-yyyy");
			
			Date date = new Date();
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,Object>();
			map.add("fromDate", sf.format(date));
			map.add("toDate", sf.format(date));
			GetDamage[] getDamage = rest.postForObject(Constants.url + "/getDamageList",map,
					GetDamage[].class);
			List<GetDamage> getDamagelist = new ArrayList<GetDamage>(Arrays.asList(getDamage));
			model.addObject("getDamagelist", getDamagelist);
			
			model.addObject("date", show.format(date)); 
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/getDamageMaterialListByDate", method = RequestMethod.GET)
	@ResponseBody
	public List<GetDamage> getDamageMaterialListByDate(HttpServletRequest request, HttpServletResponse response) {
	 
		List<GetDamage> getDamagelist = new ArrayList<GetDamage>();
		try {
			
			 String fromDate = request.getParameter("fromDate");
			 String toDate = request.getParameter("toDate");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,Object>();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			GetDamage[] getDamage = rest.postForObject(Constants.url + "/getDamageList",map,
					GetDamage[].class);
			getDamagelist = new ArrayList<GetDamage>(Arrays.asList(getDamage));
			 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return getDamagelist;
	}
	
	@RequestMapping(value = "/editDamageItem/{damageId}", method = RequestMethod.GET)
	public ModelAndView deleteDamageItem(@PathVariable int damageId, HttpServletRequest request, HttpServletResponse response) {
		 
		ModelAndView model = new ModelAndView("damage/editDamageItem");
		editDamage = new GetDamage();
		try {
			 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,Object>();
			map.add("damageId", damageId);
			editDamage = rest.postForObject(Constants.url + "/getDamageById",map,
					GetDamage.class);
			editDamage.setDate(DateConvertor.convertToYMD(editDamage.getDate()));
			model.addObject("editDamage", editDamage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/submitEditDamageList", method = RequestMethod.POST)
	public String submitEditDamageList(HttpServletRequest request, HttpServletResponse response) {
		 
		try {
			
			 
			String date = request.getParameter("date"); 
			String reason = request.getParameter("reason"); 
			int qty = Integer.parseInt(request.getParameter("qty"));  
			float value = Float.parseFloat(request.getParameter("value"));
			 
			editDamage.setDate(date);
			editDamage.setReason(reason);
			editDamage.setQty(qty);
			editDamage.setValue(value); 
			ErrorMessage res = rest.postForObject(Constants.url + "/saveDamageSingle",editDamage,
					ErrorMessage.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/getDamageList";
	}

}