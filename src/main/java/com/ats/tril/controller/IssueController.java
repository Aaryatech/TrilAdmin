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
import com.ats.tril.model.Dept;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetIssueDetail;
import com.ats.tril.model.GetIssueHeader;
import com.ats.tril.model.GetItemGroup;
import com.ats.tril.model.GetSubDept;
import com.ats.tril.model.GetpassHeader;
import com.ats.tril.model.IssueDetail;
import com.ats.tril.model.IssueHeader;
import com.ats.tril.model.item.GetItem;
import com.ats.tril.model.item.ItemList; 

@Controller
@Scope("session")
public class IssueController {
	
RestTemplate rest = new RestTemplate();
List<IssueDetail> issueDetailList = new ArrayList<IssueDetail>();
	
	@RequestMapping(value = "/addIssueItem", method = RequestMethod.GET)
	public ModelAndView addIssueItem(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("issue/addIssueItem");
		try {
			 issueDetailList = new ArrayList<IssueDetail>();
			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept)); 
			model.addObject("deparmentList", deparmentList);
			
			GetItemGroup[] itemGroupList = rest.getForObject(Constants.url + "/getAllItemGroupByIsUsed",
					GetItemGroup[].class);
			model.addObject("itemGroupList", itemGroupList);
			
			AccountHead[] accountHead = rest.getForObject(Constants.url + "/getAllAccountHeadByIsUsed", AccountHead[].class);
			List<AccountHead> accountHeadList = new ArrayList<AccountHead>(Arrays.asList(accountHead));

			model.addObject("accountHeadList", accountHeadList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/getSubDeptList", method = RequestMethod.GET)
	@ResponseBody
	public List<GetSubDept> getSubDeptListByDeptId(HttpServletRequest request, HttpServletResponse response) {

		List<GetSubDept> subDeptList = new ArrayList<GetSubDept>();
		try {
			int deptId = Integer.parseInt(request.getParameter("deptId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("deptId", deptId);
			GetSubDept[] GetSubDept = rest.postForObject(Constants.url + "getSubDeptListByDeptId", map, GetSubDept[].class);

			subDeptList = new ArrayList<>(Arrays.asList(GetSubDept));
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return subDeptList;
	}
	
	@RequestMapping(value = "/addItmeInIssueList", method = RequestMethod.GET)
	@ResponseBody
	public List<IssueDetail> addItmeInIssueList(HttpServletRequest request, HttpServletResponse response) {

		
		try {
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			int qty = Integer.parseInt(request.getParameter("qty"));
			int groupId = Integer.parseInt(request.getParameter("groupId"));
			int deptId = Integer.parseInt(request.getParameter("deptId"));
			int subDeptId = Integer.parseInt(request.getParameter("subDeptId"));
			int acc = Integer.parseInt(request.getParameter("acc"));
			String editIndex =  request.getParameter("editIndex") ;
			String itemName =  request.getParameter("itemName") ;
			String groupName =  request.getParameter("groupName") ;
			String deptName =  request.getParameter("deptName") ;
			String subDeptName =  request.getParameter("subDeptName") ;
			String accName =  request.getParameter("accName") ;

			 if(editIndex.equalsIgnoreCase("") || editIndex.equalsIgnoreCase(null))
			 {
				 IssueDetail issueDetail = new IssueDetail();
				 issueDetail.setItemId(itemId);
				 issueDetail.setItemIssueQty(qty);
				 issueDetail.setItemGroupId(groupId);
				 issueDetail.setDeptId(deptId);
				 issueDetail.setSubDeptId(subDeptId);
				 issueDetail.setAccHead(acc);
				 issueDetail.setItemName(itemName);
				 issueDetail.setGroupName(groupName);
				 issueDetail.setDeptName(deptName);
				 issueDetail.setSubDeptName(subDeptName);
				 issueDetail.setAccName(accName);
				 issueDetail.setDelStatus(1);
				 issueDetailList.add(issueDetail);
			 }
			 else
			 {
				 int index = Integer.parseInt(editIndex);
				 issueDetailList.get(index).setItemId(itemId);
				 issueDetailList.get(index).setItemIssueQty(qty);
				 issueDetailList.get(index).setItemGroupId(groupId);
				 issueDetailList.get(index).setDeptId(deptId);
				 issueDetailList.get(index).setSubDeptId(subDeptId);
				 issueDetailList.get(index).setAccHead(acc);
				 issueDetailList.get(index).setItemName(itemName);
				 issueDetailList.get(index).setGroupName(groupName);
				 issueDetailList.get(index).setDeptName(deptName);
				 issueDetailList.get(index).setSubDeptName(subDeptName);
				 issueDetailList.get(index).setAccName(accName);
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return issueDetailList;
	}
	
	@RequestMapping(value = "/editItemInIssueList", method = RequestMethod.GET)
	@ResponseBody
	public IssueDetail editItemInIssueList(HttpServletRequest request, HttpServletResponse response) {

		IssueDetail issueDetail = new IssueDetail();
		
		try {
			int index = Integer.parseInt(request.getParameter("index"));
			 
			issueDetail = issueDetailList.get(index);
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return issueDetail;
	}
	
	@RequestMapping(value = "/deleteItemFromIssueList", method = RequestMethod.GET)
	@ResponseBody
	public List<IssueDetail> deleteItemFromIssueList(HttpServletRequest request, HttpServletResponse response) {
 
		
		try {
			int index = Integer.parseInt(request.getParameter("index"));
			 
			issueDetailList.remove(index);
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return issueDetailList;
	}
	
	@RequestMapping(value = "/submitIssueReciept", method = RequestMethod.POST)
	public String submitIssueReciept(HttpServletRequest request, HttpServletResponse response) {

		try {

			 
			String issueNo = request.getParameter("issueNo"); 
			String issueDate = request.getParameter("issueDate");
		  
			 IssueHeader issueHeader = new IssueHeader();
		 
			 issueHeader.setIssueDate(DateConvertor.convertToYMD(issueDate));
			 issueHeader.setIssueNo(issueNo);
			 issueHeader.setDeleteStatus(1);
			 issueHeader.setIssueDetailList(issueDetailList);
  
			System.out.println(issueHeader);
			IssueHeader res = rest.postForObject(Constants.url + "/saveIssueHeaderAndDetail", issueHeader,
					IssueHeader.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/issueList";
	}
	
	@RequestMapping(value = "/issueList", method = RequestMethod.GET)
	public ModelAndView issueList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("issue/issueList");
		try {
			
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat disply = new SimpleDateFormat("dd-MM-yyyy");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromDate", sf.format(date));
			map.add("toDate", sf.format(date));
			
			IssueHeader[] IssueHeader = rest.postForObject(Constants.url + "/getIssueHeaderList",map, IssueHeader[].class);
			List<IssueHeader> issueHeaderList = new ArrayList<IssueHeader>(Arrays.asList(IssueHeader)); 
			model.addObject("issueHeaderList", issueHeaderList);
			
			model.addObject("date", disply.format(date));
			
		 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/getIssueDateBetweenDate", method = RequestMethod.GET)
	@ResponseBody
	public List<IssueHeader> getIssueDateBetweenDate(HttpServletRequest request, HttpServletResponse response) {
 
		List<IssueHeader> issueHeaderList = new ArrayList<>();
		
		try {
			String fromDate = request.getParameter("fromDate") ;
			String toDate = request.getParameter("toDate") ;
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromDate",DateConvertor.convertToYMD(fromDate) );
			map.add("toDate",DateConvertor.convertToYMD(toDate) );
			
			IssueHeader[] IssueHeader = rest.postForObject(Constants.url + "/getIssueHeaderList",map, IssueHeader[].class);
			 issueHeaderList = new ArrayList<IssueHeader>(Arrays.asList(IssueHeader)); 
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return issueHeaderList;
	}
	
	@RequestMapping(value = "/deleteIssueHeader/{issueId}", method = RequestMethod.GET) 
	public String deleteIssueHeader(@PathVariable int issueId, HttpServletRequest request, HttpServletResponse response) {
 
		 
		
		try {
			 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("issueId",issueId); 
			ErrorMessage errorMessage = rest.postForObject(Constants.url + "/deleteHeaderAndDetail",map, ErrorMessage.class); 
			System.out.println(errorMessage);
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "redirect:/issueList";
	}
	
	List<GetIssueDetail> issueDetailEditList = new ArrayList<>();
	GetIssueHeader getIssueHeader = new GetIssueHeader();
	
	
	@RequestMapping(value = "/editIssueHeader/{issueId}", method = RequestMethod.GET) 
	public ModelAndView editIssueHeader(@PathVariable int issueId, HttpServletRequest request, HttpServletResponse response) {
 
		ModelAndView model = new ModelAndView("issue/editIssueItem");
		
		try {
			 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("issueId",issueId); 
			 getIssueHeader = rest.postForObject(Constants.url + "/getIssueHeaderAndDetailById",map, GetIssueHeader.class); 
			model.addObject("getIssueHeader", getIssueHeader);
			
			 issueDetailEditList = getIssueHeader.getIssueDetailList();
			
			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept)); 
			model.addObject("deparmentList", deparmentList);
			
			GetItemGroup[] itemGroupList = rest.getForObject(Constants.url + "/getAllItemGroupByIsUsed",
					GetItemGroup[].class);
			model.addObject("itemGroupList", itemGroupList);
			
			AccountHead[] accountHead = rest.getForObject(Constants.url + "/getAllAccountHeadByIsUsed", AccountHead[].class);
			List<AccountHead> accountHeadList = new ArrayList<AccountHead>(Arrays.asList(accountHead));

			model.addObject("accountHeadList", accountHeadList);
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return model;
	}
	
	@RequestMapping(value = "/addItmeInEditIssueList", method = RequestMethod.GET)
	@ResponseBody
	public List<GetIssueDetail> addItmeInEditIssueList(HttpServletRequest request, HttpServletResponse response) {

		
		try {
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			int qty = Integer.parseInt(request.getParameter("qty"));
			int groupId = Integer.parseInt(request.getParameter("groupId"));
			int deptId = Integer.parseInt(request.getParameter("deptId"));
			int subDeptId = Integer.parseInt(request.getParameter("subDeptId"));
			int acc = Integer.parseInt(request.getParameter("acc"));
			String editIndex =  request.getParameter("editIndex") ;
			String itemName =  request.getParameter("itemName") ;
			String groupName =  request.getParameter("groupName") ;
			String deptName =  request.getParameter("deptName") ;
			String subDeptName =  request.getParameter("subDeptName") ;
			String accName =  request.getParameter("accName") ;

			 if(editIndex.equalsIgnoreCase("") || editIndex.equalsIgnoreCase(null))
			 {
				 GetIssueDetail issueDetail = new GetIssueDetail();
				 issueDetail.setItemId(itemId);
				 issueDetail.setItemIssueQty(qty);
				 issueDetail.setItemGroupId(groupId);
				 issueDetail.setDeptId(deptId);
				 issueDetail.setSubDeptId(subDeptId);
				 issueDetail.setAccHead(acc);
				 issueDetail.setItemCode(itemName);
				 issueDetail.setGrpCode(groupName);
				 issueDetail.setDeptCode(deptName);
				 issueDetail.setSubDeptCode(subDeptName);
				 issueDetail.setAccHeadDesc(accName);
				 issueDetail.setDelStatus(1);
				 issueDetailEditList.add(issueDetail);
			 }
			 else
			 {
				 int index = Integer.parseInt(editIndex);
				 issueDetailEditList.get(index).setItemId(itemId);
				 issueDetailEditList.get(index).setItemIssueQty(qty);
				 issueDetailEditList.get(index).setItemGroupId(groupId);
				 issueDetailEditList.get(index).setDeptId(deptId);
				 issueDetailEditList.get(index).setSubDeptId(subDeptId);
				 issueDetailEditList.get(index).setAccHead(acc);
				 issueDetailEditList.get(index).setItemCode(itemName);
				 issueDetailEditList.get(index).setGrpCode(groupName);
				 issueDetailEditList.get(index).setDeptCode(deptName);
				 issueDetailEditList.get(index).setSubDeptCode(subDeptName);
				 issueDetailEditList.get(index).setAccHeadDesc(accName);
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return issueDetailEditList;
	}
	
	@RequestMapping(value = "/editItemInEditIssueList", method = RequestMethod.GET)
	@ResponseBody
	public GetIssueDetail editItemInEditIssueList(HttpServletRequest request, HttpServletResponse response) {

		GetIssueDetail issueDetail = new GetIssueDetail();
		
		try {
			int index = Integer.parseInt(request.getParameter("index"));
			 
			issueDetail = issueDetailEditList.get(index);
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return issueDetail;
	}
	
	@RequestMapping(value = "/deleteItemFromEditIssueList", method = RequestMethod.GET)
	@ResponseBody
	public List<GetIssueDetail> deleteItemFromEditIssueList(HttpServletRequest request, HttpServletResponse response) {
 
		
		try {
			int index = Integer.parseInt(request.getParameter("index"));
			 
			if(issueDetailEditList.get(index).getIssueDetailId()==0)
			{
				issueDetailEditList.remove(index);
			}
			else
			{
				issueDetailEditList.get(index).setDelStatus(0);
			}
			
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return issueDetailEditList;
	}
	
	@RequestMapping(value = "/submitEditIssueReciept", method = RequestMethod.POST)
	public String submitEditIssueReciept(HttpServletRequest request, HttpServletResponse response) {

		try {

			 
			String issueNo = request.getParameter("issueNo"); 
			String issueDate = request.getParameter("issueDate");
		  
			 
			getIssueHeader.setIssueDate(DateConvertor.convertToYMD(issueDate));
			getIssueHeader.setIssueNo(issueNo);
			getIssueHeader.setDeleteStatus(1);
			getIssueHeader.setIssueDetailList(issueDetailEditList); 
			System.out.println(getIssueHeader);
			IssueHeader res = rest.postForObject(Constants.url + "/saveIssueHeaderAndDetail", getIssueHeader,
					IssueHeader.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/issueList";
	}

}
