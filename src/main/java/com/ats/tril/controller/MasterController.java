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
import com.ats.tril.model.AccountHead;
import com.ats.tril.model.Category;
import com.ats.tril.model.DeliveryTerms;
import com.ats.tril.model.Dept;
import com.ats.tril.model.DispatchMode;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetItem;
import com.ats.tril.model.GetItemGroup;
import com.ats.tril.model.GetItemSubGrp;
import com.ats.tril.model.GetSubDept;
import com.ats.tril.model.Item;
import com.ats.tril.model.ItemGroup;
import com.ats.tril.model.SubDept;
import com.ats.tril.model.Type;
import com.ats.tril.model.Uom;

@Controller
@Scope("session")
public class MasterController {

	RestTemplate rest = new RestTemplate();
	List<Dept> deparmentList = new ArrayList<>();
	List<GetSubDept> getSubDeptList = new ArrayList<>();
	List<GetItem> itemList = new ArrayList<>();
	List<Uom> uomList = new ArrayList<Uom>();
	
	@RequestMapping(value = "/getAllType", method = RequestMethod.GET)
	public List<Type> getAllType(HttpServletRequest request, HttpServletResponse response) {

		List<Type> typeList = new ArrayList<Type>();
		try {

			Type[] type = rest.getForObject(Constants.url + "/getAlltype", Type[].class);
			typeList = new ArrayList<Type>(Arrays.asList(type));
 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return typeList;
	}
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addCategory");
		try {

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));

			model.addObject("categoryList", categoryList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/insertCategory", method = RequestMethod.POST)
	public String insertCategory(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/addEmployee");
		try {

			String catId = request.getParameter("catId");
			String catDesc = request.getParameter("catDesc");
			String catPrefix = request.getParameter("catPrefix");
			int monthlyLimit = Integer.parseInt(request.getParameter("monthlyLimit"));
			int yearlyLimit = Integer.parseInt(request.getParameter("yearlyLimit"));

			Category category = new Category();

			if (catId == "" || catId == null)
				category.setCatId(0);
			else
				category.setCatId(Integer.parseInt(catId));
			category.setCatDesc(catDesc);
			category.setCatPrefix(catPrefix);
			category.setMonthlyLimit(monthlyLimit);
			category.setYearlyLimit(yearlyLimit);
			category.setIsUsed(1);
			category.setCreatedIn(1);

			System.out.println("category" + category);

			Category res = rest.postForObject(Constants.url + "/saveCategory", category, Category.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addCategory";
	}

	@RequestMapping(value = "/editCategory/{catId}", method = RequestMethod.GET)
	public ModelAndView editCategory(@PathVariable int catId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addCategory");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("catId", catId);

			Category res = rest.postForObject(Constants.url + "/getCatByCatId", map, Category.class);
			model.addObject("editCategory", res);

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));

			model.addObject("categoryList", categoryList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteCategory/{catId}", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable int catId, HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/empDetail");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("catId", catId);
			map.add("deletedIn", 0);
			ErrorMessage res = rest.postForObject(Constants.url + "/deleteCategory", map, ErrorMessage.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addCategory";
	}

	@RequestMapping(value = "/addDepartment", method = RequestMethod.GET)
	public ModelAndView addDepartment(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addDepartment");
		try {

			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));

			model.addObject("deparmentList", deparmentList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/checkDeptCodeExist", method = RequestMethod.GET)
	@ResponseBody
	public int checkDeptCodeExist(HttpServletRequest request, HttpServletResponse response) {
 
		 int exist = 0 ;
		 
		try {

			String deptCode = request.getParameter("deptCode");
			 
			 for(int i = 0 ; i < deparmentList.size() ; i++)
			 {
				 if(deparmentList.get(i).getDeptCode().equals(deptCode.trim()))
				 {
					 exist = 1;
					 break;
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exist;
	}

	@RequestMapping(value = "/insertDepartment", method = RequestMethod.POST)
	public String insertDepartment(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/addEmployee");
		try {

			String deptId = request.getParameter("deptId");
			String deptCode = request.getParameter("deptCode");
			String deptDesc = request.getParameter("deptDesc");

			Dept dept = new Dept();

			if (deptId == "" || deptId == null)
				dept.setDeptId(0);
			else
				dept.setDeptId(Integer.parseInt(deptId));
			dept.setDeptCode(deptCode);
			dept.setDeptDesc(deptDesc);
			dept.setIsUsed(1);
			dept.setDeptCreatedIn(1);

			System.out.println("dept" + dept);

			Dept res = rest.postForObject(Constants.url + "/saveDept", dept, Dept.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addDepartment";
	}

	@RequestMapping(value = "/editDepartment/{deptId}", method = RequestMethod.GET)
	public ModelAndView editDepartment(@PathVariable int deptId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addDepartment");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("deptId", deptId);

			Dept res = rest.postForObject(Constants.url + "/getDeptByDeptId", map, Dept.class);
			model.addObject("editDept", res);

			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));

			model.addObject("deparmentList", deparmentList);
			model.addObject("isEdit", 1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteDepartment/{deptId}", method = RequestMethod.GET)
	public String deleteDepartment(@PathVariable int deptId, HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/empDetail");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("deptId", deptId);
			map.add("deptDeletedIn", 0);
			ErrorMessage res = rest.postForObject(Constants.url + "/deleteDept", map, ErrorMessage.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addDepartment";
	}

	@RequestMapping(value = "/addDispachMode", method = RequestMethod.GET)
	public ModelAndView addDispachMode(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addDispachMode");
		try {

			DispatchMode[] dispatchMode = rest.getForObject(Constants.url + "/getAllDispatchModesByIsUsed",
					DispatchMode[].class);
			List<DispatchMode> dispatchModeList = new ArrayList<DispatchMode>(Arrays.asList(dispatchMode));

			model.addObject("dispatchModeList", dispatchModeList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/insertDispachMode", method = RequestMethod.POST)
	public String insertDispachMode(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/addEmployee");
		try {

			String dispModeId = request.getParameter("dispModeId");
			String dispModeDesc = request.getParameter("dispModeDesc");

			DispatchMode dispatchMode = new DispatchMode();

			if (dispModeId == "" || dispModeId == null)
				dispatchMode.setDispModeId(0);
			else
				dispatchMode.setDispModeId(Integer.parseInt(dispModeId));
			dispatchMode.setDispModeDesc(dispModeDesc);
			dispatchMode.setIsUsed(1);
			dispatchMode.setCreatedIn(1);

			System.out.println("dispatchMode" + dispatchMode);

			DispatchMode res = rest.postForObject(Constants.url + "/saveDispatchMode", dispatchMode,
					DispatchMode.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addDispachMode";
	}

	@RequestMapping(value = "/editDispatchMode/{dispModeId}", method = RequestMethod.GET)
	public ModelAndView editDispatchMode(@PathVariable int dispModeId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addDispachMode");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("dispModeId", dispModeId);

			DispatchMode res = rest.postForObject(Constants.url + "/getDispatchModeByTermId", map, DispatchMode.class);
			model.addObject("editDispatchMode", res);

			DispatchMode[] dispatchMode = rest.getForObject(Constants.url + "/getAllDispatchModesByIsUsed",
					DispatchMode[].class);
			List<DispatchMode> dispatchModeList = new ArrayList<DispatchMode>(Arrays.asList(dispatchMode));

			model.addObject("dispatchModeList", dispatchModeList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteDispatchMode/{dispModeId}", method = RequestMethod.GET)
	public String deleteDispatchMode(@PathVariable int dispModeId, HttpServletRequest request,
			HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/empDetail");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("dispModeId", dispModeId);
			map.add("deletedIn", 0);
			ErrorMessage res = rest.postForObject(Constants.url + "/deleteDispatchMode", map, ErrorMessage.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addDispachMode";
	}

	@RequestMapping(value = "/addDeliveryTerm", method = RequestMethod.GET)
	public ModelAndView addDeliveryTerm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addDeliveryTerm");
		try {

			DeliveryTerms[] deliveryTerms = rest.getForObject(Constants.url + "/getAllDeliveryTermsByIsUsed",
					DeliveryTerms[].class);
			List<DeliveryTerms> deliveryTermsList = new ArrayList<DeliveryTerms>(Arrays.asList(deliveryTerms));

			model.addObject("deliveryTermsList", deliveryTermsList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/insertDeliveryTerm", method = RequestMethod.POST)
	public String insertDeliveryTerm(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/addEmployee");
		try {

			String deliveryTermId = request.getParameter("deliveryTermId");
			String deliveryDesc = request.getParameter("deliveryDesc");

			DeliveryTerms deliveryTerms = new DeliveryTerms();

			if (deliveryTermId == "" || deliveryTermId == null)
				deliveryTerms.setDeliveryTermId(0);
			else
				deliveryTerms.setDeliveryTermId(Integer.parseInt(deliveryTermId));
			deliveryTerms.setDeliveryDesc(deliveryDesc);
			deliveryTerms.setIsUsed(1);
			deliveryTerms.setCreatedIn(1);

			System.out.println("deliveryTerms" + deliveryTerms);

			DeliveryTerms res = rest.postForObject(Constants.url + "/saveDeliveryTerms", deliveryTerms,
					DeliveryTerms.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addDeliveryTerm";
	}

	@RequestMapping(value = "/editDeliveryTerms/{deliveryTermId}", method = RequestMethod.GET)
	public ModelAndView editDeliveryTerms(@PathVariable int deliveryTermId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addDeliveryTerm");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("deliveryTermId", deliveryTermId);

			DeliveryTerms res = rest.postForObject(Constants.url + "/getDeliveryTermsByTermId", map,
					DeliveryTerms.class);
			model.addObject("editDeliveryTerms", res);

			DeliveryTerms[] deliveryTerms = rest.getForObject(Constants.url + "/getAllDeliveryTermsByIsUsed",
					DeliveryTerms[].class);
			List<DeliveryTerms> deliveryTermsList = new ArrayList<DeliveryTerms>(Arrays.asList(deliveryTerms));

			model.addObject("deliveryTermsList", deliveryTermsList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteDeliveryTerms/{deliveryTermId}", method = RequestMethod.GET)
	public String deleteDeliveryTerms(@PathVariable int deliveryTermId, HttpServletRequest request,
			HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/empDetail");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("deliveryTermId", deliveryTermId);
			map.add("deletedIn", 0);
			ErrorMessage res = rest.postForObject(Constants.url + "/deleteDeliveryTerms", map, ErrorMessage.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addDeliveryTerm";
	}

	@RequestMapping(value = "/addSubDepartment", method = RequestMethod.GET)
	public ModelAndView addSubDepartment(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addSubDepartment");
		try {

			GetSubDept[] getSubDept = rest.getForObject(Constants.url + "/getAllSubDept", GetSubDept[].class);
			getSubDeptList = new ArrayList<GetSubDept>(Arrays.asList(getSubDept));

			model.addObject("subDeptList", getSubDeptList);

			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));

			model.addObject("deparmentList", deparmentList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/checkSubDeptCodeExist", method = RequestMethod.GET)
	@ResponseBody
	public int checkSubDeptCodeExist(HttpServletRequest request, HttpServletResponse response) {
 
		 int exist = 0 ;
		 
		try {

			String subGroupCode = request.getParameter("subGroupCode");
			 
			 for(int i = 0 ; i < getSubDeptList.size() ; i++)
			 {
				 if(getSubDeptList.get(i).getSubDeptCode().equals(subGroupCode.trim()))
				 {
					 exist = 1;
					 break;
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exist;
	}

	@RequestMapping(value = "/insertsubDept", method = RequestMethod.POST)
	public String insertsubDept(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/addEmployee");
		try {
			String subDeptId = request.getParameter("subDeptId");
			String subGroupCode = request.getParameter("subGroupCode");
			String subDeptDesc = request.getParameter("subDeptDesc");
			int deptId = Integer.parseInt(request.getParameter("deptId"));

			SubDept insert = new SubDept();

			if (subDeptId == "" || subDeptId == null)
				insert.setSubDeptId(0);
			else
				insert.setSubDeptId(Integer.parseInt(subDeptId));
			insert.setSubDeptCode(subGroupCode);
			insert.setSubDeptDesc(subDeptDesc);
			insert.setDeptId(deptId);
			insert.setIsUsed(1);
			insert.setCreatedIn(1);

			System.out.println("dispatchMode" + insert);

			SubDept res = rest.postForObject(Constants.url + "/saveSubDept", insert, SubDept.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addSubDepartment";
	}

	@RequestMapping(value = "/editSubDept/{subDeptId}", method = RequestMethod.GET)
	public ModelAndView editSubDept(@PathVariable int subDeptId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addSubDepartment");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("subDeptId", subDeptId);

			GetSubDept res = rest.postForObject(Constants.url + "/getSubDeptBySubDeptId", map, GetSubDept.class);
			model.addObject("editSubDept", res);

			GetSubDept[] getSubDept = rest.getForObject(Constants.url + "/getAllSubDept", GetSubDept[].class);
			List<GetSubDept> getSubDeptList = new ArrayList<GetSubDept>(Arrays.asList(getSubDept));

			model.addObject("subDeptList", getSubDeptList);

			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));

			model.addObject("deparmentList", deparmentList);
			model.addObject("isEdit", 1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteSubDept/{subDeptId}", method = RequestMethod.GET)
	public String deleteSubDept(@PathVariable int subDeptId, HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/empDetail");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("subDeptId", subDeptId);
			map.add("deletedIn", 0);
			ErrorMessage res = rest.postForObject(Constants.url + "/deleteSubDept", map, ErrorMessage.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addSubDepartment";
	}
	
	@RequestMapping(value = "/addAccountHead", method = RequestMethod.GET)
	public ModelAndView addAccountHead(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addAccountHead");
		try {

			 
			AccountHead[] accountHead = rest.getForObject(Constants.url + "/getAllAccountHeadByIsUsed", AccountHead[].class);
			List<AccountHead> accountHeadList = new ArrayList<AccountHead>(Arrays.asList(accountHead));

			model.addObject("accountHeadList", accountHeadList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/insertAccountHead", method = RequestMethod.POST)
	public String insertAccountHead(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/addEmployee");
		try {
			String accHeadId = request.getParameter("accHeadId"); 
			String accHeadDesc = request.getParameter("accHeadDesc"); 

			AccountHead insert = new AccountHead();

			if (accHeadId == "" || accHeadId == null)
				insert.setAccHeadId(0);
			else
				insert.setAccHeadId(Integer.parseInt(accHeadId));
			insert.setAccHeadDesc(accHeadDesc); 
			insert.setIsUsed(1);
			insert.setCreatedIn(1);

			System.out.println("AccountHead" + insert);

			AccountHead res = rest.postForObject(Constants.url + "/saveAccountHead", insert, AccountHead.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addAccountHead";
	}

	@RequestMapping(value = "/editAccountHead/{accHeadId}", method = RequestMethod.GET)
	public ModelAndView editAccountHead(@PathVariable int accHeadId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addAccountHead");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("accHeadId", accHeadId);

			AccountHead res = rest.postForObject(Constants.url + "/getAccontHeadByAccHeadId", map, AccountHead.class);
			model.addObject("editAccountHead", res);

			AccountHead[] accountHead = rest.getForObject(Constants.url + "/getAllAccountHeadByIsUsed", AccountHead[].class);
			List<AccountHead> accountHeadList = new ArrayList<AccountHead>(Arrays.asList(accountHead));

			model.addObject("accountHeadList", accountHeadList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteAccountHead/{accHeadId}", method = RequestMethod.GET)
	public String deleteAccountHead(@PathVariable int accHeadId, HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/empDetail");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("accHeadId", accHeadId);
			map.add("deletedIn", 0);
			ErrorMessage res = rest.postForObject(Constants.url + "/deleteAccountHead", map, ErrorMessage.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addAccountHead";
	}
	
	
	@RequestMapping(value = "/addItem", method = RequestMethod.GET)
	public ModelAndView addItem(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addItem");
		try {
			
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			model.addObject("date", sf.format(date));

			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));
			model.addObject("categoryList", categoryList);
			
			Uom[] uom = rest.getForObject(Constants.url + "/getAllUoms", Uom[].class);
			uomList = new ArrayList<Uom>(Arrays.asList(uom));
			model.addObject("uomList", uomList);
			
			GetItem[] item = rest.getForObject(Constants.url + "/getAllItems",  GetItem[].class); 
			itemList = new ArrayList<GetItem>(Arrays.asList(item));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	/*@RequestMapping(value = "/checkItemCodeExist", method = RequestMethod.GET)
	@ResponseBody
	public int checkItemCodeExist(HttpServletRequest request, HttpServletResponse response) {
 
		 int exist = 0 ;
		 
		try {

			String itemCode = request.getParameter("itemCode");
			 
			 for(int i = 0 ; i < itemList.size() ; i++)
			 {
				 if(itemList.get(i).getItemCode().equals(itemCode.trim()))
				 {
					 exist = 1;
					 break;
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exist;
	}*/
	
	@RequestMapping(value = "/getgroupIdByCatId", method = RequestMethod.GET)
	@ResponseBody
	public List<GetItemGroup> getgroupIdByCatId(HttpServletRequest request, HttpServletResponse response) {
 
		List<GetItemGroup> getItemGroupList = new ArrayList<GetItemGroup>();
		try {

			int catId = Integer.parseInt(request.getParameter("catId"));
			System.out.println(catId);
			MultiValueMap<String, Object>	map = new LinkedMultiValueMap<String,Object>();
			map.add("catId", catId);
			GetItemGroup[] itemGroupList = rest.postForObject(Constants.url + "/getgroupListByCatId",map,
					GetItemGroup[].class);
			getItemGroupList = new ArrayList<>(Arrays.asList(itemGroupList));
			System.out.println(getItemGroupList);
			 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return getItemGroupList;
	}
	
	@RequestMapping(value = "/getSubGroupIdByGroupId", method = RequestMethod.GET)
	@ResponseBody
	public List<GetItemSubGrp> getSubGroupIdByGroupId(HttpServletRequest request, HttpServletResponse response) {
 
		List<GetItemSubGrp> getItemSubGrpList = new ArrayList<GetItemSubGrp>();
		try {
			int grpId = Integer.parseInt(request.getParameter("grpId"));
			
			MultiValueMap<String, Object>	map = new LinkedMultiValueMap<String,Object>();
			map.add("grpId", grpId);
			GetItemSubGrp[] itemSubGroupList = rest.postForObject(Constants.url + "/getSubGroupByGroupId",map,
					GetItemSubGrp[].class);
			getItemSubGrpList = new ArrayList<>(Arrays.asList(itemSubGroupList));
			 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getItemSubGrpList;
	}
	
	@RequestMapping(value = "/insertItem", method = RequestMethod.POST)
	public String insertItem(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/addEmployee");
		try {
			String itemId = request.getParameter("itemId"); 
			String itemCode = request.getParameter("itemCode"); 
			String itemDesc = request.getParameter("itemDesc"); 
			String uom = request.getParameter("uom"); 
			String itemDate = request.getParameter("itemDate");
			int opQty = Integer.parseInt(request.getParameter("opQty")); 
			float opRate = Float.parseFloat(request.getParameter("opRate"));
			int clQty = Integer.parseInt(request.getParameter("clQty")); 
			float clRate = Float.parseFloat(request.getParameter("clRate"));
			int minLevel = Integer.parseInt(request.getParameter("minLevel")); 
			int maxLevel = Integer.parseInt(request.getParameter("maxLevel")); 
			int rodLevel = Integer.parseInt(request.getParameter("rodLevel")); 
			float itemWeight = Float.parseFloat(request.getParameter("itemWeight")); 
			String itemLocation = request.getParameter("itemLocation"); 
			String itemAbc = request.getParameter("itemAbc"); 
			String itemLife = request.getParameter("itemLife"); 
			String itemSchd = request.getParameter("itemSchd"); 
			int isCritical = Integer.parseInt(request.getParameter("isCritical")); 
			int isCapital = Integer.parseInt(request.getParameter("isCapital")); 
			int itemCon = Integer.parseInt(request.getParameter("itemCon")); 
			
			int catId = Integer.parseInt(request.getParameter("catId")); 
			int grpId = Integer.parseInt(request.getParameter("grpId"));  
			int subGrpId = Integer.parseInt(request.getParameter("subGrpId")); 

			Item insert = new Item();

			if(itemId.equalsIgnoreCase("") || itemId.equalsIgnoreCase(null))
				insert.setItemId(0);
			else
				insert.setItemId(Integer.parseInt(itemId));
			insert.setItemCode(itemCode);
			insert.setItemDesc(itemDesc);
			insert.setItemDate(DateConvertor.convertToYMD(itemDate));
			 
			for(int i = 0 ; i<uomList.size() ; i++)
			{
				if(Integer.parseInt(uom)==uomList.get(i).getUomId())
				{
					insert.setItemUom(uomList.get(i).getUom());
					break;
				}
			}
			 
			insert.setItemUom2(uom);
			insert.setItemOpQty(opQty);
			insert.setItemOpRate(opRate);
			insert.setItemClQty(clQty);
			insert.setItemClRate(clRate);
			insert.setItemMinLevel(minLevel);
			insert.setItemMaxLevel(maxLevel);
			insert.setItemRodLevel(rodLevel);
			insert.setItemWt(itemWeight);
			insert.setItemLocation(itemLocation);
			insert.setItemAbc(itemAbc);
			insert.setItemLife(itemLife);
			insert.setItemSchd(itemSchd);
			insert.setItemIsCritical(isCritical);
			insert.setItemIsCapital(isCapital);
			insert.setIsUsed(1);
			insert.setCreatedIn(1);
			insert.setItemIsCons(itemCon);
			insert.setCatId(catId);
			insert.setGrpId(grpId);
			insert.setSubGrpId(subGrpId);
			
			System.out.println("insert" + insert);

			Item res = rest.postForObject(Constants.url + "/saveItem", insert, Item.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/getItemList";
	}
	 
	@RequestMapping(value = "/getItemList", method = RequestMethod.GET)
	public ModelAndView getItemList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/getItemList");
		try {

			GetItem[] item = rest.getForObject(Constants.url + "/getAllItems",  GetItem[].class); 
			List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(item));
			model.addObject("itemList", itemList);
			 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/editItem/{itemId}", method = RequestMethod.GET)
	public ModelAndView editItem(@PathVariable int itemId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/addItem");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("itemId", itemId);
			GetItem  item = rest.postForObject(Constants.url + "/getItemByItemId",map, GetItem .class);
			 model.addObject("editItem", item);
			 
			 System.out.println(item);
			 
			 Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
				List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));
				model.addObject("categoryList", categoryList);
				
				 map = new LinkedMultiValueMap<String,Object>();
				map.add("catId", item.getCatId());
				GetItemGroup[] itemGroupList = rest.postForObject(Constants.url + "/getgroupListByCatId",map,
						GetItemGroup[].class);
				List<GetItemGroup> getItemGroupList = new ArrayList<>(Arrays.asList(itemGroupList));
				model.addObject("getItemGroupList", getItemGroupList);
				
				map = new LinkedMultiValueMap<String,Object>();
				map.add("grpId", item.getGrpId());
				GetItemSubGrp[] itemSubGroupList = rest.postForObject(Constants.url + "/getSubGroupByGroupId",map,
						GetItemSubGrp[].class);
				List<GetItemSubGrp> getItemSubGrpList = new ArrayList<>(Arrays.asList(itemSubGroupList));
				model.addObject("getItemSubGrpList", getItemSubGrpList);
				
				Uom[] uom = rest.getForObject(Constants.url + "/getAllUoms", Uom[].class);
				uomList = new ArrayList<Uom>(Arrays.asList(uom));
				model.addObject("uomList", uomList);
				
				model.addObject("isEdit", 1);
				
				model.addObject("date",item.getItemDate());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/deleteItem/{itemId}", method = RequestMethod.GET)
	public String deleteItem(@PathVariable int itemId, HttpServletRequest request, HttpServletResponse response) {

		 
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("itemId", itemId);
			map.add("deletedIn", 0);
			ErrorMessage  errorMessage = rest.postForObject(Constants.url + "/deleteItem",map, ErrorMessage .class);
			System.out.println(errorMessage);
			 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/getItemList";
	}
	
	
	@RequestMapping(value = "/getNextItemCode", method = RequestMethod.GET)
	@ResponseBody
	public ErrorMessage getNextItemCode(HttpServletRequest request, HttpServletResponse response) {
 
		  
		 ErrorMessage errorMessage = new ErrorMessage();
		try {

			int grpId = Integer.parseInt(request.getParameter("grpId"));
			 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("grpId", grpId);
			ItemGroup itemGroup = rest.postForObject(Constants.url + "/getItemGroupByGrpId", map, ItemGroup.class);
			 
			map = new LinkedMultiValueMap<String,Object>();
			map.add("str", itemGroup.getGrpCode());
			errorMessage = rest.postForObject(Constants.url + "/getNextItemCode",map,
					ErrorMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return errorMessage;
	}
	
	/*@RequestMapping(value = "/checkItemCodeExist", method = RequestMethod.GET)
	@ResponseBody
	public ErrorMessage checkItemCodeExist(HttpServletRequest request, HttpServletResponse response) {
 
		 //int exist = 0 ;
		 ErrorMessage errorMessage = new ErrorMessage();
		try {

			String itemCode = request.getParameter("itemCode");
			 
			for(int i = 0 ; i < itemList.size() ; i++)
			 {
				 if(itemList.get(i).getItemCode().equals(itemCode.trim()))
				 {
					 exist = 1;
					 break;
				 }
			 }
			
			MultiValueMap<String, Object>	map = new LinkedMultiValueMap<String,Object>();
			map.add("str", itemCode);
			errorMessage = rest.postForObject(Constants.url + "/getNextVendorNo",map,
					ErrorMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return errorMessage;
	}*/

}
