package com.ats.tril.controller;

import java.text.DateFormat;
import java.text.ParseException;
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
import org.springframework.validation.BindingResult;
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
import com.ats.tril.model.Dept;
import com.ats.tril.model.ErrorMessage;
import com.ats.tril.model.GetCurrentStock;
import com.ats.tril.model.GetItemGroup;
import com.ats.tril.model.GetSubDept;
import com.ats.tril.model.StockHeader;
import com.ats.tril.model.doc.DocumentBean;
import com.ats.tril.model.doc.SubDocument;
import com.ats.tril.model.indent.GetIndent;
import com.ats.tril.model.indent.Indent;
import com.ats.tril.model.indent.IndentTrans;
import com.ats.tril.model.indent.TempIndentDetail;
import com.ats.tril.model.item.GetItem;
import com.ats.tril.model.item.ItemList;

@Controller
@Scope("session")
  
public class IndentController {

	RestTemplate rest = new RestTemplate();

	@RequestMapping(value = "/showIndent", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			tempIndentList = new ArrayList<TempIndentDetail>();
			model = new ModelAndView("indent/addindent");
			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));

			model.addObject("categoryList", categoryList);

			AccountHead[] accountHead = rest.getForObject(Constants.url + "/getAllAccountHeadByIsUsed",
					AccountHead[].class);
			List<AccountHead> accountHeadList = new ArrayList<AccountHead>(Arrays.asList(accountHead));

			model.addObject("accountHeadList", accountHeadList);

			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));

			model.addObject("deparmentList", deparmentList);
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			model.addObject("date", dateFormat.format(date));
			
			StockHeader stockHeader = rest.getForObject(Constants.url + "/getCurrentRunningMonthAndYear",StockHeader.class);
			
			date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			 
			String fromDate=stockHeader.getYear()+"-"+stockHeader.getMonth()+"-"+"01";
			String toDate=sf.format(date);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			 map.add("fromDate", fromDate);
			 map.add("toDate", toDate); 
			GetCurrentStock[] getCurrentStock = rest.postForObject(Constants.url + "/getCurrentStock",map,GetCurrentStock[].class); 
			List<GetCurrentStock> stockList = new ArrayList<>(Arrays.asList(getCurrentStock));
			
			System.out.println("stockList " + stockList);
		} catch (Exception e) {

			System.err.println("Exception in showing add Indent" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/getSubDeptListByDeptId", method = RequestMethod.GET)
	public @ResponseBody List<GetSubDept> getSubDeptListByDeptId(HttpServletRequest request,
			HttpServletResponse response) {

		List<GetSubDept> subDeptList = new ArrayList<GetSubDept>();
		try {

			int deptId = 0;

			String deptIdS = request.getParameter("deptId");

			deptId = Integer.parseInt(deptIdS);

			System.out.println("deptId Id " + deptId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			// MultiValueMap<String, Object> mapItemList = new LinkedMultiValueMap<String,
			// Object>();

			map.add("deptId", deptId);

			RestTemplate restTemplate = new RestTemplate();

			subDeptList = restTemplate.postForObject(Constants.url + "getSubDeptListByDeptId", map, List.class);

		} catch (Exception e) {

			System.err.println("Exce in FrCurStock Cont @items by Cat Id Ajax call " + e.getMessage());

			e.printStackTrace();
		}
		return subDeptList;
	}

	@RequestMapping(value = "/getgroupListByCatId", method = RequestMethod.GET)
	public @ResponseBody List<GetItemGroup> getgroupListByCatId(HttpServletRequest request,
			HttpServletResponse response) {
		System.err.println("In get group by cat Id ");
		List<GetItemGroup> itemGrpList = new ArrayList<GetItemGroup>();
		try {

			int catId = 0;

			String catIds = request.getParameter("catId");

			catId = Integer.parseInt(catIds);

			System.out.println("catIds Id " + catIds);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			// MultiValueMap<String, Object> mapItemList = new LinkedMultiValueMap<String,
			// Object>();

			map.add("catId", catId);

			RestTemplate restTemplate = new RestTemplate();

			itemGrpList = restTemplate.postForObject(Constants.url + "getgroupListByCatId", map, List.class);

		} catch (Exception e) {

			System.err.println(
					"Exce in getgroupListByCatId Cont @IndentController by Cat Id Ajax call " + e.getMessage());

			e.printStackTrace();
		}
		return itemGrpList;
	}

	List<GetItem> itemList = new ArrayList<GetItem>();

	@RequestMapping(value = "/itemListByGroupId", method = RequestMethod.GET)
	public @ResponseBody List<GetItem> itemListByGroupId(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("In get group by cat Id ");
		try {

			int grpId = 0;

			String grpIds = request.getParameter("grpId");

			grpId = Integer.parseInt(grpIds);

			System.out.println("grpId Id " + grpId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			// MultiValueMap<String, Object> mapItemList = new LinkedMultiValueMap<String,
			// Object>();

			map.add("groupId", grpId);

			RestTemplate restTemplate = new RestTemplate();

			ItemList resList = restTemplate.postForObject(Constants.url + "itemListByGroupId", map, ItemList.class);

			itemList = resList.getItems();

		} catch (Exception e) {

			System.err.println(
					"Exce in getgroupListByCatId Cont @IndentController by Cat Id Ajax call " + e.getMessage());

			e.printStackTrace();
		}
		return itemList;
	}

	public String incrementDate(String date, int day) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));

		} catch (ParseException e) {
			System.out.println("Exception while incrementing date " + e.getMessage());
			e.printStackTrace();
		}
		c.add(Calendar.DATE, day); // number of days to add
		date = sdf.format(c.getTime());

		return date;

	}

	List<TempIndentDetail> tempIndentList = new ArrayList<TempIndentDetail>();

	// getIndentDetail
	@RequestMapping(value = "/getIndentDetail", method = RequestMethod.GET)
	public @ResponseBody List<TempIndentDetail> getIndentDetail(HttpServletRequest request,
			HttpServletResponse response) {
		System.err.println("In get group by cat Id ");

		try {
			
			
			int key = Integer.parseInt(request.getParameter("key"));
			
			
			if(key==-1) {
				
				System.err.println("Add Call Indent");
			String itemName = request.getParameter("itemName");
			String remark = request.getParameter("remark");

			int itemId = Integer.parseInt(request.getParameter("itemId"));

			System.err.println("Item Id " + itemId);

			int qty = Integer.parseInt(request.getParameter("qty"));

			int schDay = Integer.parseInt(request.getParameter("schDay"));
			String indDate = request.getParameter("indentDate");
			TempIndentDetail detail = new TempIndentDetail();

			String uom = null;
			String itemCode = null;

			for (int i = 0; i < itemList.size(); i++) {

				if (itemList.get(i).getItemId() == itemId) {

					uom = itemList.get(i).getItemUom();
					itemCode = itemList.get(i).getItemCode();

					break;
				}
			}

			// String calculatedDate = incrementDate(deliveryDate, itemShelfLife);

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			Date tempDate = sdf.parse(indDate);
			System.err.println("Temp Date " + tempDate);
			Calendar c = Calendar.getInstance();
			c.setTime(tempDate); // Now use today date.//before new Date() now tempDate
			c.add(Calendar.DATE, schDay); // Adding days
			String date = sdf.format(c.getTime());
			System.out.println(date);

			// Date d=LocalDate.now().plusDays(schDay);
			detail.setCurStock(0);
			detail.setItemId(itemId);
			detail.setItemName(itemName);
			detail.setQty(qty);
			detail.setSchDays(schDay);
			detail.setDate(date);
			detail.setUom(uom);
			detail.setItemCode(itemCode);
			detail.setRemark(remark);
			tempIndentList.add(detail);
			}
			
			else {
				System.err.println("remove call Indent");
				tempIndentList.remove(key);
			}
		} catch (Exception e) {

			System.err.println("Exce in getIndentDetail Cont @IndentController by Ajax call " + e.getMessage());

			e.printStackTrace();
		}
		return tempIndentList;
	}
	@RequestMapping(value = "/getInvoiceNo", method = RequestMethod.GET)
	@ResponseBody
	public DocumentBean getInvoiceNo(HttpServletRequest request, HttpServletResponse response) {
            
		String invNo="-";
		DocumentBean docBean=null;
		try {
			int catId = Integer.parseInt(request.getParameter("catId"));
			int docId = Integer.parseInt(request.getParameter("docId"));
			String date = request.getParameter("date");
			
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("docId",docId);
			map.add("catId", catId);
			map.add("date", DateConvertor.convertToYMD(date));
			RestTemplate restTemplate = new RestTemplate();

			docBean = restTemplate.postForObject(Constants.url + "getDocumentData", map, DocumentBean.class);
			System.err.println("Doc"+docBean.toString());
			String indMNo=docBean.getSubDocument().getCategoryPrefix()+"";
			int counter=docBean.getSubDocument().getCounter();
			int counterLenth = String.valueOf(counter).length();
			counterLenth = 4 - counterLenth;
			StringBuilder code = new StringBuilder(indMNo);

			for (int i = 0; i < counterLenth; i++) {
				String j = "0";
				code.append(j);
			}
			code.append(String.valueOf(counter));
			invNo=""+code;
			docBean.setCode(invNo);
			System.err.println("invNo"+invNo);
		}catch (Exception e) {
			e.printStackTrace();
		}

		return docBean;
	}
	@RequestMapping(value = "/saveIndent", method = RequestMethod.POST)
	public String saveIndent(HttpServletRequest request, HttpServletResponse response) {

		try {

			System.err.println("Inside saveIndent");

			int catId = Integer.parseInt(request.getParameter("ind_cat"));

			String indNo = request.getParameter("indent_no");

			int indType = Integer.parseInt(request.getParameter("indent_type"));

			String indDate = request.getParameter("indent_date");
			System.err.println("indeDate " + indDate);

			int accHead = Integer.parseInt(request.getParameter("acc_head"));

			int isMachineSpe = Integer.parseInt(request.getParameter("machine_specific"));

			int dept = 0;
			int subDept = 0;

			if (isMachineSpe == 1) {
				System.err.println("It is Machine Specific");
				dept = Integer.parseInt(request.getParameter("dept"));
				subDept = Integer.parseInt(request.getParameter("sub_dept"));

				System.err.println("dept " + dept + "sub Dept  " + subDept);

			}

			System.err.println("dept " + dept + "sub Dept  " + subDept);

			int isDev = Integer.parseInt(request.getParameter("is_dev"));
			int isMonthly = Integer.parseInt(request.getParameter("is_monthly"));

			Indent indent = new Indent();
			DocumentBean docBean=null;
			try {
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("docId", 1);
				map.add("catId", catId);
				map.add("date", DateConvertor.convertToYMD(indDate));
				RestTemplate restTemplate = new RestTemplate();

				 docBean = restTemplate.postForObject(Constants.url + "getDocumentData", map, DocumentBean.class);
				String indMNo=docBean.getSubDocument().getCategoryPrefix()+"";
				int counter=docBean.getSubDocument().getCounter();
				int counterLenth = String.valueOf(counter).length();
				counterLenth = 4 - counterLenth;
				StringBuilder code = new StringBuilder(indMNo+"");

				for (int i = 0; i < counterLenth; i++) {
					String j = "0";
					code.append(j);
				}
				code.append(String.valueOf(counter));
				
				indent.setIndMNo(""+code);
				
				docBean.getSubDocument().setCounter(docBean.getSubDocument().getCounter()+1);
			}catch (Exception e) {
				e.printStackTrace();
			}
			indent.setAchdId(accHead);
			indent.setCatId(catId);
			indent.setIndIsdev(isDev);
			indent.setIndIsmonthly(isMonthly);
			indent.setIndMDate(DateConvertor.convertToYMD(indDate));
			
			indent.setIndMStatus(0);
			indent.setIndMType(indType);
			indent.setIndRemark("default remark");

			indent.setDeptId(dept);
			indent.setSubDeptId(subDept);
			
			indent.setDelStatus(Constants.delStatus);
			

			List<IndentTrans> indTrasList = new ArrayList<IndentTrans>();
			for (int i = 0; i < tempIndentList.size(); i++) {

				IndentTrans transDetail = new IndentTrans();
				TempIndentDetail detail = tempIndentList.get(i);

				transDetail.setIndDStatus(0);

				transDetail.setIndItemCurstk(detail.getCurStock());
				transDetail.setIndItemDesc(detail.getItemName());
				transDetail.setIndItemSchd(detail.getSchDays());
				transDetail.setIndItemSchddt(DateConvertor.convertToSqlDate(detail.getDate()));
				transDetail.setIndItemUom(detail.getUom());
				transDetail.setIndMDate(indent.getIndMDate());
				transDetail.setIndMNo(indent.getIndMNo());
				transDetail.setIndQty(detail.getQty());
				transDetail.setIndRemark(detail.getRemark());
				transDetail.setItemId(detail.getItemId());
				transDetail.setIndFyr(detail.getQty());

				indTrasList.add(transDetail);

			}
			indent.setIndentTrans(indTrasList);
			System.err.println("Indent = " + indent.toString());

			RestTemplate restTemp = new RestTemplate();
if(indTrasList.size()>0) {
			Indent indRes = restTemp.postForObject(Constants.url + "/saveIndentAndTrans", indent, Indent.class);
			 if(indRes!=null)
	          {
	        		try {
	        			
	        			SubDocument subDocRes = restTemp.postForObject(Constants.url + "/saveSubDoc", docBean.getSubDocument(), SubDocument.class);

	        		
	        		}catch (Exception e) {
						e.printStackTrace();
					}
	          }
			System.err.println("indRes " + indRes.toString());
}
		} catch (Exception e) {

			System.err.println("Exception in @saveIndent  Indent" + e.getMessage());
			e.getCause();
			e.printStackTrace();
		}
		System.err.println("Inside last Saveindent");

		return "redirect:/showIndent";
	}

	// getIndents //show fromDate toDate and status

	List<GetIndent> indentList = new ArrayList<GetIndent>();
	String fromDate, toDate;

	@RequestMapping(value = "/getIndents", method = RequestMethod.GET)
	public ModelAndView getIndents(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			// String fromDate,toDate;

			if (request.getParameter("from_date") == null || request.getParameter("to_date") == null) {
				Date date = new Date();
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				fromDate = df.format(date);
				toDate = df.format(date);
				System.out.println("From Date And :" + fromDate + "ToDATE" + toDate);

				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));

				System.out.println("inside if ");
			} else {
				fromDate = request.getParameter("from_date");
				toDate = request.getParameter("to_date");

				System.out.println("inside Else ");

				System.out.println("fromDate " + fromDate);

				System.out.println("toDate " + toDate);

				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));

			}
			map.add("status", 0);

			model = new ModelAndView("indent/viewindent");
			GetIndent[] indents = rest.postForObject(Constants.url + "/getIndents", map, GetIndent[].class);

			indentList = new ArrayList<GetIndent>();

			indentList = new ArrayList<GetIndent>(Arrays.asList(indents));

			System.out.println("Indent List using /getIndents   " + indentList.toString());

			model.addObject("indentList", indentList);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("Exception in getIndents Indent" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	// editIndent edit Indent Header

	@RequestMapping(value = "/editIndent/{indMId}", method = RequestMethod.GET)
	public ModelAndView editIndent(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("indMId") int indMId) {

		ModelAndView model = null;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			System.err.println("from date in edit Indent " + fromDate + "toDate  " + toDate);

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			map.add("status", 0);

			GetIndent[] indents = rest.postForObject(Constants.url + "/getIndents", map, GetIndent[].class);

			indentList = new ArrayList<GetIndent>();

			indentList = new ArrayList<GetIndent>(Arrays.asList(indents));

			GetIndent getIndent = new GetIndent();

			for (int i = 0; i < indentList.size(); i++) {

				if (indentList.get(i).getIndMId() == indMId) {
					getIndent = indentList.get(i);
					break;
				}
			}

			model = new ModelAndView("indent/editIndentHeader");
			Category[] category = rest.getForObject(Constants.url + "/getAllCategoryByIsUsed", Category[].class);
			List<Category> categoryList = new ArrayList<Category>(Arrays.asList(category));

			model.addObject("categoryList", categoryList);

			AccountHead[] accountHead = rest.getForObject(Constants.url + "/getAllAccountHeadByIsUsed",
					AccountHead[].class);
			List<AccountHead> accountHeadList = new ArrayList<AccountHead>(Arrays.asList(accountHead));

			model.addObject("accountHeadList", accountHeadList);

			Dept[] Dept = rest.getForObject(Constants.url + "/getAllDeptByIsUsed", Dept[].class);
			List<Dept> deparmentList = new ArrayList<Dept>(Arrays.asList(Dept));

			model.addObject("deparmentList", deparmentList);
			model.addObject("indent", getIndent);
			
			model.addObject("isDept", getIndent.getDeptId());
			map = new LinkedMultiValueMap<String, Object>();

			map.add("indMId", indMId);

			map.add("delStatus", Constants.delStatus);

			IndentTrans[] indDetail = rest.postForObject(Constants.url + "/getIndentDetailByIndMId", map,
					IndentTrans[].class);

			List<IndentTrans> indDetailList = new ArrayList<IndentTrans>(Arrays.asList(indDetail));

			System.err.println("Indent Detail List  " + indDetailList.toString());
			model.addObject("indDetailList", indDetailList);

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

		} catch (Exception e) {

			System.err.println("Exception in showing editIndent/{indMId}" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	// editIndentProcess
	// indentId

	@RequestMapping(value = "/editIndentProcess", method = RequestMethod.POST)
	public String editIndentProcess(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("Inside editIndentProcess ");
		ModelAndView model = null;
		int indentId = 0;
		try {

			indentId = Integer.parseInt(request.getParameter("indentId"));

			int accHead = Integer.parseInt(request.getParameter("acc_head"));

			int isMachineSpe = Integer.parseInt(request.getParameter("machine_specific"));

			int dept = 0;
			int subDept = 0;

			if (isMachineSpe == 1) {
				System.err.println("It is Machine Specific");
				dept = Integer.parseInt(request.getParameter("dept"));
				subDept = Integer.parseInt(request.getParameter("sub_dept"));
			}

			System.err.println("dept " + dept + "sub Dept  " + subDept);

			int isDev = Integer.parseInt(request.getParameter("is_dev"));
			int isMonthly = Integer.parseInt(request.getParameter("is_monthly"));

			// build an update query to update indent
			// editIndentHeader return type ErrorMessage;

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			
			map.add("achdId", accHead);
			map.add("deptId", dept);
			map.add("subDeptId", subDept);
			map.add("indIsdev", isDev);
			map.add("indIsmonthly", isMonthly);
			map.add("indMId", indentId);

			ErrorMessage editIndentHeaderResponse = rest.postForObject(Constants.url + "/editIndentHeader", map,
					ErrorMessage.class);
			System.err.println("editIndentHeaderResponse " + editIndentHeaderResponse.toString());

		}

		catch (Exception e) {
			System.err.println("Exception in editIndentProcess " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/editIndent/" + indentId;

	}

	// updateIndDetail

	




	@RequestMapping(value = "/updateIndDetail", method = RequestMethod.GET)
	public @ResponseBody List<IndentTrans> updateIndDetail(HttpServletRequest request, HttpServletResponse response
			/*@PathVariable("indDId") int indDId, @PathVariable("indMId") int indentId, @PathVariable("qty") int qty*/) {
	MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		ModelAndView model = null;
		List<IndentTrans> indDetailList =  new ArrayList<IndentTrans>();
		try {

			int indQty =Integer.parseInt(request.getParameter("qty"));
			
			int indDId =Integer.parseInt(request.getParameter("indDId"));

			
			int indentId =Integer.parseInt(request.getParameter("indMId"));
			
			
if(indQty>0) {
	System.err.println("It is Edit call indQty >0");

			// build an update query to update indent
			// editIndentHeader return type ErrorMessage;
			map = new LinkedMultiValueMap<String, Object>();

			map.add("indDId", indDId);
			map.add("indQty", indQty);

			ErrorMessage editIndentDetailResponse = rest.postForObject(Constants.url + "/editIndentDetail", map,
					ErrorMessage.class);
			System.err.println("editIndentDetailResponse " + editIndentDetailResponse.toString());

}else {
	
	System.err.println("In Else indent qt It is Delete call");
	map = new LinkedMultiValueMap<String, Object>();

	map.add("indDId", indDId);
	map.add("delStatus", 0);

	ErrorMessage editIndentDetailResponse = rest.postForObject(Constants.url + "/delteIndentDetailItem", map,
			ErrorMessage.class);
	System.err.println("editIndentDetailResponse " + editIndentDetailResponse.toString());
	
	
}
		 map = new LinkedMultiValueMap<String, Object>();

		map = new LinkedMultiValueMap<String, Object>();

		map.add("indMId", indentId);

		map.add("delStatus", Constants.delStatus);

		IndentTrans[] indDetail = rest.postForObject(Constants.url + "/getIndentDetailByIndMId", map,
				IndentTrans[].class);

		 indDetailList = new ArrayList<IndentTrans>(Arrays.asList(indDetail));
		 System.err.println("Ind detail after update call  "  +indDetailList.toString());

		}

		catch (Exception e) {
			System.err.println("Exception in updateIndDetail Ajax Call  " + e.getMessage());
			e.printStackTrace();
		}
		
		return indDetailList;
		//return "redirect:/editIndent/" + indentId;

	}
	/*@RequestMapping(value = "/updateIndDetail/{indDId}/{indMId}/{qty}", method = RequestMethod.GET)
	public String updateIndDetail(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("indDId") int indDId, @PathVariable("indMId") int indentId, @PathVariable("qty") int qty) {

		ModelAndView model = null;
		try {

			// int indQty =Integer.parseInt(request.getParameter("indQty"+indDId));

			// build an update query to update indent
			// editIndentHeader return type ErrorMessage;

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("indDId", indDId);
			map.add("indQty", qty);

			ErrorMessage editIndentDetailResponse = rest.postForObject(Constants.url + "/editIndentDetail", map,
					ErrorMessage.class);
			System.err.println("editIndentDetailResponse " + editIndentDetailResponse.toString());

		}

		catch (Exception e) {
			System.err.println("Exception in updateIndDetail " + e.getMessage());
			e.printStackTrace();
		}

		return "redirect:/editIndent/" + indentId;

		
	}*/
	
	
	
	//deleteIndentHeader action of indent list action 1
	

	// showEditViewIndentDetail show indentdetail from indent header list action
	// button Cancelled given with Indent Header Edit

	/*
	 * @RequestMapping(value = "/showEditViewIndentDetail/{indMId}", method =
	 * RequestMethod.GET) public ModelAndView
	 * showEditViewIndentDetail(HttpServletRequest request, HttpServletResponse
	 * response,@PathVariable("indMId") int indMId) {
	 * 
	 * ModelAndView model = null; try {
	 * 
	 * GetIndent getIndent=new GetIndent(); for(int i=0;i<indentList.size();i++) {
	 * 
	 * if(indentList.get(i).getIndMId()==indMId) { getIndent=indentList.get(i);
	 * break; } }
	 * 
	 * model = new ModelAndView("indent/editIndentDetail");
	 * 
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
	 * Object>();
	 * 
	 * map.add("indMId", indMId); map.add("delStatus",0);
	 * 
	 * IndentTrans[] indDetail = rest.postForObject(Constants.url +
	 * "/getIndentDetailByIndMId",map, IndentTrans[].class);
	 * 
	 * List<IndentTrans> indDetailList = new
	 * ArrayList<IndentTrans>(Arrays.asList(indDetail));
	 * 
	 * System.err.println("Indent Detail List  " +indDetailList.toString());
	 * model.addObject("indDetailList", indDetailList); model.addObject("indent",
	 * getIndent);
	 * 
	 * } catch (Exception e) {
	 * 
	 * System.err.println("Exception in showEditViewIndentDetail " +e.getMessage());
	 * e.printStackTrace(); }
	 * 
	 * return model; }
	 */

}

