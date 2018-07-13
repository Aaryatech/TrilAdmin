package com.ats.tril.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.xml.DocumentDefaultsDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.tril.common.Constants;
import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.GetPODetail;
import com.ats.tril.model.Vendor;
import com.ats.tril.model.indent.Indent;
import com.ats.tril.model.mrn.MrnDetail;
import com.ats.tril.model.mrn.MrnHeader;
import com.ats.tril.model.po.PoHeader;
import com.sun.org.apache.bcel.internal.util.SyntheticRepository;

@Controller
@Scope("session")
public class MrnController {

	RestTemplate rest = new RestTemplate();

	@RequestMapping(value = "/showAddMrn", method = RequestMethod.GET)
	public ModelAndView showAddMrn(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			poIdList = new String();
			poDetailList = new ArrayList<GetPODetail>();
			model = new ModelAndView("mrn/showAddMrn");

			Vendor[] vendorRes = rest.getForObject(Constants.url + "/getAllVendorByIsUsed", Vendor[].class);
			List<Vendor> vendorList = new ArrayList<Vendor>(Arrays.asList(vendorRes));

			model.addObject("vendorList", vendorList);

			System.err.println("Inside show Add Mrn ");

		} catch (Exception e) {

			System.err.println("Exception in showing showAddMrn" + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = { "/getPOHeaderList" }, method = RequestMethod.GET)
	public @ResponseBody List<PoHeader> getPOHeaderList(HttpServletRequest request, HttpServletResponse response) {

		List<PoHeader> poHeadList = new ArrayList<PoHeader>();

		try {

			int vendorId = 0;

			vendorId = Integer.parseInt(request.getParameter("vendorId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("vendId", vendorId);
			map.add("delStatus", Constants.delStatus);
			map.add("statusList", "1,2");

			PoHeader[] poHeadRes = rest.postForObject(Constants.url + "/getPOHeaderList", map, PoHeader[].class);
			poHeadList = new ArrayList<PoHeader>(Arrays.asList(poHeadRes));

			System.err.println("PO header List Using Ajax Call  " + poHeadList.toString());

		} catch (Exception e) {

			System.err.println("Exception in getting PO Header List By Ajax Call " + e.getMessage());
			e.printStackTrace();
		}

		return poHeadList;
	}

	String poIdList = new String();
	List<GetPODetail> poDetailList = new ArrayList<GetPODetail>();

	@RequestMapping(value = { "/getPODetailList" }, method = RequestMethod.GET)
	public @ResponseBody List<GetPODetail> getPODetails(HttpServletRequest request, HttpServletResponse response) {

		GetPODetail[] poDetailRes;
		try {

			if (poIdList.isEmpty()) {

				poIdList = request.getParameter("poIds");
				System.err.println("Po  Id List  " + poIdList);

				poIdList = poIdList.substring(1, poIdList.length() - 1);
				poIdList = poIdList.replaceAll("\"", "");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map.add("poIdList", poIdList);

				poDetailRes = rest.postForObject(Constants.url + "/getPODetailList", map, GetPODetail[].class);
				poDetailList = new ArrayList<GetPODetail>(Arrays.asList(poDetailRes));

			} // end of if poIdList is Empty

			else {
				int qty = Integer.parseInt(request.getParameter("qty"));

				int poDId = Integer.parseInt(request.getParameter("poDId"));

				if (poDetailList.size() > 0) {
					
				//	if(qty>0) {

					System.err.println("Inside poDlist.size >0 ");

					for (int i = 0; i < poDetailList.size(); i++) {

						if (poDetailList.get(i).getPoDetailId() == poDId) {
							System.err.println("Inside poDId matched  ");

							poDetailList.get(i).setReceivedQty(qty);

						} else {

							System.err.println("Po Detaol ID Not matched ");
						}
					}
				//	}
				}

			} // end of else
			System.err.println("PO Details List Using Ajax Call  " + poDetailList.toString());

		} catch (Exception e) {

			System.err.println("Exception in getting PO Detail List @getPODetailList By Ajax Call " + e.getMessage());
			e.printStackTrace();
		}

		return poDetailList;

	}

	// getTempPoDetail

	@RequestMapping(value = { "/getTempPoDetail" }, method = RequestMethod.GET)
	public @ResponseBody List<GetPODetail> getTempPoDetail(HttpServletRequest request, HttpServletResponse response) {

		try {
			System.err.println("inside /getTempPoDetail");
		} catch (Exception e) {
			
			System.err.println("Exception in getTempPoDetail " + e.getMessage());
			e.printStackTrace();
		}
		
	return poDetailList;
	
	}
	
	
	//insertMrnProcess final Mrn Insert Call Ajax;
	
	@RequestMapping(value = { "/insertMrnProcess" }, method = RequestMethod.GET)
	public @ResponseBody int insertMrnProcess(HttpServletRequest request, HttpServletResponse response) {

		try {
			System.err.println("inside /insertMrnProcess");
			
			int grnType = Integer.parseInt(request.getParameter("grn_type"));
			int vendorId = Integer.parseInt(request.getParameter("vendor_id"));
			
			String grnNo = request.getParameter("grn_no");
			String grnDate = request.getParameter("grn_date");
			
			String gateEntryNo = request.getParameter("gate_entry_no");
			String gateEntryDate = request.getParameter("gate_entry_date");
			
			String chalanNo = request.getParameter("chalan_no");
			String chalanDate = request.getParameter("chalan_date");
			
			String billNo = request.getParameter("bill_no");
			String billDate = request.getParameter("bill_date");
			
			String lrDate=request.getParameter("lorry_date");
			String lrNo=request.getParameter("lorry_no");
			String transport=request.getParameter("transport");
			String lorryRemark=request.getParameter("lorry_remark");
			
			MrnHeader mrnHeader=new MrnHeader();
			
			List<MrnDetail> mrnDetailList=new ArrayList<MrnDetail>();
			
			mrnHeader.setBillDate(DateConvertor.convertToYMD(billDate));
			mrnHeader.setBillNo(billNo);
			mrnHeader.setDelStatus(Constants.delStatus);
			mrnHeader.setDocDate(DateConvertor.convertToYMD(chalanDate));
			mrnHeader.setDocNo(chalanNo);
			mrnHeader.setGateEntryDate(DateConvertor.convertToYMD(gateEntryDate));
			mrnHeader.setGateEntryNo(gateEntryNo);
			mrnHeader.setLrDate(DateConvertor.convertToYMD(lrDate));
			mrnHeader.setLrNo(lrNo);
			mrnHeader.setMrnDate(DateConvertor.convertToYMD(grnDate));
			mrnHeader.setMrnNo("default MRN NO");
			mrnHeader.setMrnStatus(0);
			mrnHeader.setMrnType(grnType);
			mrnHeader.setRemark1(lorryRemark);
			mrnHeader.setRemark2("def");
			mrnHeader.setTransport(transport);
			mrnHeader.setUserId(1);
			mrnHeader.setVendorId(vendorId);
			
			for(GetPODetail detail: poDetailList) {
				
				MrnDetail mrnDetail=new MrnDetail();
				
				mrnDetail.setIndentQty(detail.getIndedQty());
				
				mrnDetail.setPoQty(detail.getItemQty());
				
				mrnDetail.setMrnQty(detail.getReceivedQty());
				
				mrnDetail.setItemId(detail.getItemId());
				
				mrnDetail.setPoId(detail.getPoId());
				
				mrnDetail.setPoNo(detail.getPoNo());
				
				mrnDetail.setMrnDetailStatus(0);
				
				mrnDetail.setBatchNo("Default Batch KKKK-00456");
				mrnDetail.setDelStatus(Constants.delStatus);
				mrnDetailList.add(mrnDetail);
			}
			
			mrnHeader.setMrnDetailList(mrnDetailList);
			
			
			System.err.println("Mrn Header   " +mrnHeader.toString());
			
			RestTemplate restTemp = new RestTemplate();

			MrnHeader mrnHeaderRes = restTemp.postForObject(Constants.url + "/saveMrnHeadAndDetail", mrnHeader, MrnHeader.class);

			System.err.println("mrnHeaderRes " + mrnHeaderRes.toString());
			
			
		} catch (Exception e) {
			
			System.err.println("Exception in insertMrnProcess " + e.getMessage());
			e.printStackTrace();
			
		}
		
	return 1;
	
	}
	
}
