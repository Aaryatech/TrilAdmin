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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.model.Category;
import com.ats.tril.model.DeliveryTerms;
import com.ats.tril.model.Dept;
import com.ats.tril.model.DispatchMode;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetSubDept;
import com.ats.tril.model.SubDept;

@Controller
@Scope("session")
public class MasterController {

	RestTemplate rest = new RestTemplate();

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

			Category category = new Category();

			if (catId == "" || catId == null)
				category.setCatId(0);
			else
				category.setCatId(Integer.parseInt(catId));
			category.setCatDesc(catDesc);
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
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));

			model.addObject("deparmentList", deparmentList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
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
			List<GetSubDept> getSubDeptList = new ArrayList<GetSubDept>(Arrays.asList(getSubDept));

			model.addObject("subDeptList", getSubDeptList);

			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));

			model.addObject("deparmentList", deparmentList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
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

}
