<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/css/datepicker.css" />
	 <style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
	background-color: #fefefe;
	margin: auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
	height: 80%;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

#overlay {
	position: fixed;
	display: none;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(101, 113, 119, 0.5);
	z-index: 2;
	cursor: pointer;
}

#text {
	position: absolute;
	top: 50%;
	left: 50%;
	font-size: 25px;
	color: white;
	transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
}
.bg-overlay {
    background: linear-gradient(rgba(0,0,0,.7), rgba(0,0,0,.7)), url("${pageContext.request.contextPath}/resources/images/smart.jpeg");
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center center;
    color: #fff;
    height:auto;
    width:auto;
    padding-top: 10px;
    padding-left:20px;
}
</style>

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<body>
	  
    <c:url var="getInvoiceNo" value="/getInvoiceNo" />
	<c:url var="geIntendDetailByIndId" value="/geIntendDetailByIndId"></c:url>
	<c:url var="updateRmQty0" value="/updateRmQty0"></c:url>
	<c:url var="getRmCategory" value="/getRmCategory" />
	<c:url var="getRmListByCatId" value="/getRmListByCatId" />
	<c:url var="getRmRateAndTax" value="/getRmRateAndTax" />

	<c:url var="calculatePurchaseHeaderValues" value="/calculatePurchaseHeaderValues" />


	<!-- BEGIN Sidebar -->
	<div id="sidebar" class="navbar-collapse collapse">

		<jsp:include page="/WEB-INF/views/include/navigation.jsp"></jsp:include>

		<div id="sidebar-collapse" class="visible-lg">
			<i class="fa fa-angle-double-left"></i>
		</div>
		<!-- END Sidebar Collapse Button -->
	</div>
	<!-- END Sidebar -->



	<!-- BEGIN Content -->
	<div id="main-content">
		<!-- BEGIN Page Title -->
		<div class="page-title">
			<div>
				<h1>
					<i class="fa fa-file-o"></i>Purchase Order
				</h1>
				<!-- <h4>Bill for franchises</h4> -->
			</div>
		</div>
		<!-- END Page Title -->

		<!-- BEGIN Breadcrumb -->
		<div id="breadcrumbs">
			<ul class="breadcrumb">
				<li><i class="fa fa-home"></i> <a
					href="${pageContext.request.contextPath}/home">Home</a> <span
					class="divider"><i class="fa fa-angle-right"></i></span></li>
				<li class="active">Purchase Order</li>
			</ul>
		</div>
		<!-- END Breadcrumb -->
		
		<!-- BEGIN Main Content -->
		<div class="box">
			<div class="box-title">
				<h3>
					<i class="fa fa-bars"></i>Direct Purchase Order
				</h3>

			</div>

				<div class=" box-content">
					 
		<div>
			<form id="submitPurchaseOrder"
				action="${pageContext.request.contextPath}/submitPurchaseOrder"
				method="post">
			<div class="box-content">
			<div class="col-md-2" >PO Type</div>
									<div class="col-md-3">
										<select name="poType" id="poType"   class="form-control chosen" onchange="getInvoiceNo()"  tabindex="6" required>
										<c:choose>
											<c:when test="${poTypeTemp==1}">
												<option value="" >Select PO Type</option>
												<option value="1" selected>Regular</option>
												<option value="2">Job Work</option>
												<option value="3">General</option>
												<option value="4">Other</option>
											</c:when>
											<c:when test="${poTypeTemp==2}">
												<option value="" >Select PO Type</option>
												<option value="1" >Regular</option>
												<option value="2" selected>Job Work</option>
												<option value="3">General</option>
												<option value="4">Other</option>
											</c:when>
											<c:when test="${poTypeTemp==3}">
												<option value="" >Select PO Type</option>
												<option value="1">Regular</option>
												<option value="2">Job Work</option>
												<option value="3" selected>General</option>
												<option value="4">Other</option>
											</c:when>
											<c:when test="${poTypeTemp==4}">
												<option value="4" selected>Other</option>
											</c:when>
											<c:otherwise>
											<option value="" >Select PO Type</option>
											<option value="1">Regular</option>
											<option value="2">Job Work</option>
											<option value="3">General</option>
											<option value="4">Other</option> 
											</c:otherwise>
										</c:choose>
										
										</select>
									</div>
				
				 
				</div><br/>
				
				<div class="box-content">
				
				<div class="col-md-2">PO No.  </div>
				<div class="col-md-3"><input type="text" id="poNo" name="poNo" value="1" readonly class="form-control" >
				</div>
						<div class="col-md-2">PO Date</div> 
						<div class="col-md-3">
						<input type="text" id="poDate" name="poDate" value="${date}" class="form-control date-picker" onblur="getInvoiceNo()" required>
							
						</div>
									 
									</div><br/>
									
				<div class="box-content">
				<div class="col-md-2" >Vendor Name</div>
									<div class="col-md-10">
										<select name="vendId" id="vendId"   class="form-control chosen" tabindex="6" required>
										<option value=""  >Select Vendor</option>
										 
											 <c:forEach items="${vendorList}" var="vendorList" >
											<c:choose>
									 			<c:when test="${vendorList.vendorId==vendIdTemp}">
							  						<option value="${vendorList.vendorId}" selected><c:out value="${vendorList.vendorName}"/></option>
 												</c:when>
 												<c:otherwise>
 													<option value="${vendorList.vendorId}"  ><c:out value="${vendorList.vendorName}"/></option>
 												</c:otherwise>
 												</c:choose>	 
												</c:forEach>
						

										</select>
									</div>
									
				 
			</div><br/>
			
			<div class="box-content">
			<div class="col-md-2">Vendor Quotation</div>
				<div class="col-md-3">
					<input type="text" placeholder="Enter Quotation No"  value="${quotationTemp}" name="quotation" id="quotation" class="form-control" required>
				</div>
									
									<div class="col-md-2" >Quotation Ref. Date</div>
									<div class="col-md-3">
										 <input type="text"   placeholder="Select Quotation Date" value="${quotationDateTemp}" name="quotationDate" id="quotationDate" class="form-control date-picker" required>
									</div>
									</div><br/>
									
								
			<div class="box-content">
				<div class="col-md-2" >Payment Terms</div>
									<div class="col-md-3">
										<select name="payId" id="payId"    class="form-control chosen" tabindex="6" required>
										<option value="">Select Pay Terms</option>
											 <c:forEach items="${paymentTermsList}" var="paymentTermsList" >
											 <c:choose>
											 	<c:when test="${paymentTermsList.pymtTermId==payIdTemp}">
											 		<option value="${paymentTermsList.pymtTermId}" selected><c:out value="${paymentTermsList.pymtDesc}"/></option> 
											 	</c:when>
											 	<c:otherwise>
											 		<option value="${paymentTermsList.pymtTermId}"><c:out value="${paymentTermsList.pymtDesc}"/></option> 
											 	</c:otherwise>
											 </c:choose>
							   
												</c:forEach>
						 
										</select>
									</div>
									
									 <div class="col-md-2" >Select Delivery</div>
									<div class="col-md-3">
										<select name="deliveryId" id="deliveryId"    class="form-control chosen" tabindex="6" required>
										<option value="" >Select </option>
											 <c:forEach items="${deliveryTermsList}" var="deliveryTermsList" >
											 <c:choose>
											 	<c:when test="${deliveryTermsList.deliveryTermId==deliveryIdTemp}">
											 	 <option value="${deliveryTermsList.deliveryTermId}" selected><c:out value="${deliveryTermsList.deliveryDesc}"/></option>
											 	</c:when>
											 	<c:otherwise>
											 	 <option value="${deliveryTermsList.deliveryTermId}"><c:out value="${deliveryTermsList.deliveryDesc}"/></option>
											 	</c:otherwise>
											 </c:choose>
							  
 											 </c:forEach>
						 
										</select>
									</div> 
				</div><br/>
								<div class="box-content">
								
									<div class="col-md-2" >Select Dispatch Mode</div>
									<div class="col-md-3">
									
										<select name="dispatchMode"  id="dispatchMode"   class="form-control chosen" tabindex="6" required>
										<option value="" >Select </option>
									 <c:forEach items="${dispatchModeList}" var="dispatchModeList" >
									 <c:choose>
									 	<c:when test="${dispatchModeList.dispModeId==dispatchModeTemp}">
									 		 <option value="${dispatchModeList.dispModeId}" selected><c:out value="${dispatchModeList.dispModeDesc}"/></option>
									 	</c:when>
									 	<c:otherwise>
									 	 	<option value="${dispatchModeList.dispModeId}"><c:out value="${dispatchModeList.dispModeDesc}"/></option>
									 	</c:otherwise>
									 </c:choose>
							  
 													 
												</c:forEach>
										 </select>
									</div>
								</div><br/>
								 
								
			
				
					<div class="box-content">
								<div class="col-md-2" >Select Intend No.</div>
									<div class="col-md-3">
										<select name="indId" id="indId" class="form-control chosen" tabindex="6" required>
										<option value="" >Select  </option>
									 <c:forEach items="${intedList}" var="intedList" >
									 <c:choose>
									 	<c:when test="${intedList.indMId==indId}">
									 		<option value="${intedList.indMId}" selected> ${intedList.indMNo} &nbsp;&nbsp; ${intedList.indMDate}</option>
									 	</c:when>
									 	<c:otherwise>
									 		<option value="${intedList.indMId}"> ${intedList.indMNo} &nbsp;&nbsp; ${intedList.indMDate}</option>
									 	</c:otherwise>
									 </c:choose>
							   
 													 
												</c:forEach>
										</select>
									</div>	
									<div class="col-md-1"></div>
									<div class="col-md-2"><input type="button" class="btn btn-info" value="Get Item From Intend "  id="myBtn"></div>
									
									 
					</div>
			 		<br/>
									
									<hr/>
									
									  
			 
			 
				<div class=" box-content">
					<div class="row">
								<div style="overflow:scroll;height:35%;width:100%;overflow:auto">
									<table width="100%" border="0"class="table table-bordered table-striped fill-head "
										style="width: 100%" id="table_grid2">
										<thead>
											<tr>
										<th>Sr.No.</th>
										<th>Item Name </th>
										<th>UOM</th>
										<th>Intend QTY</th> 
										<th>PO QTY</th>
										<th>Balance QTY</th>
										<th>Rate</th>
										<th>Disc</th>
										<th>Sch Days</th>
										<th>Remark</th>

									</tr>
										</thead>
										<tbody>
										
										<c:forEach items="${poDetailList}" var="poDetailList"
													varStatus="count">
													 
													<tr>
													  
														<td><c:out value="${count.index+1}" /></td>
  
																<td align="left"><c:out value="${poDetailList.itemCode}" /></td>
																<td align="left"><c:out value="${poDetailList.itemUom}" /></td>
																<td align="right"><c:out value="${poDetailList.indedQty}" /></td>
																<td align="right"><c:out value="${poDetailList.itemQty}" /></td>
													  			<td align="right"><c:out value="${poDetailList.balanceQty}" /></td>
													  			<td align="right"><c:out value="${poDetailList.itemRate}" /></td>
													  			<td align="right"><c:out value="${poDetailList.discPer}" /></td>
													  			<td align="right"><c:out value="${poDetailList.schDays}" /></td>
													  			<td align="left"><c:out value="${poDetailList.schRemark}" /></td> 
																</tr>
												</c:forEach>
 
										</tbody>
									</table>
								</div>
							</div>
 
		</div>
		 			 <br/>
		 			 <hr/>
		 			 <div class="box-content"> 
		 			 
									<div class="col-md-2">Packing Charges %</div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" onchange="calculation()" type="text" value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" name="packPer" id="packPer" class="form-control" required>
										</div>
									<div class="col-md-2">Packing Charges Value <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" onchange="calculation()" type="text"  name="packValue" id="packValue" class="form-control"
										value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
									<div class="col-md-2">Remark</div>
										<div class="col-md-2">
											<input   type="text" name="packRemark" id="packRemark" class="form-control" value="NA"  >
										</div>
							
							
							</div><br>
							<div class="box-content">
									<div class="col-md-2">Insurance Charges %</div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" onchange="calculation()" type="text" value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" name="insuPer" id="insuPer" class="form-control" required>
										</div>
									<div class="col-md-2">Insurance Charges Value <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" onchange="calculation()" name="insuValue" id="insuValue" class="form-control"
										value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
									<div class="col-md-2">Remark</div>
										<div class="col-md-2">
											<input   type="text" name="insuRemark" id="insuRemark" class="form-control" value="NA"  >
										</div>
							
							</div><br>
						
						<div class="box-content"> 
							 
						 	<div class="col-md-2">Freight Charges %</div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" onchange="calculation()" type="text" value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" name="freightPer" id="freightPer" class="form-control" required>
										</div>
									<div class="col-md-2">Freight Charges Value <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" onchange="calculation()" name="freightValue" id="freightValue" class="form-control"
										value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
									<div class="col-md-2">Remark</div>
										<div class="col-md-2">
											<input   type="text" name="freghtRemark" id="freghtRemark" class="form-control" value="NA"  >
										</div>
							 </div><br>
						   
							<div class="box-content">
								 
									<div class="col-md-2">Select Tax Percentage</div>
										<div class="col-md-2">
										<select name="taxPer" id="taxPer"  onchange="calculation()"  class="form-control chosen" tabindex="6" required>
										<option value="0" selected >0</option>
											 <c:forEach items="${taxFormList}" var="taxFormList" >
											 
											 	 <option value="${taxFormList.taxId}"><c:out value="${taxFormList.taxPer}"/></option>
											 	  
 											 </c:forEach>
						 
										</select>
											 </div>
									<div class="col-md-2">Tax Value <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" onchange="calculation()" name="taxValue" id="taxValue" class="form-control"
										value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" readonly>
										</div>
									<div class="col-md-2">Remark</div>
										<div class="col-md-2">
											<input   type="text" name="taxRemark" id="taxRemark" class="form-control" value="NA"  >
										</div>
							
							</div><br>
							
							<div class="box-content">
								 
									<div class="col-md-2">Other Charges %</div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" onchange="calculation()" value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" name="otherPer" id="otherPer" class="form-control" required>
										</div>
									<div class="col-md-2">Other Charges Value <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" onchange="calculation()" name="otherValue" id="otherValue" class="form-control"
										value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
									<div class="col-md-2">Remark</div>
										<div class="col-md-2">
											<input   type="text" name="otherRemark" id="otherRemark" class="form-control" value="NA"  >
										</div>
							
							</div><br>
							    
							 <div class="box-content">
								 
									<div class="col-md-2">Basic value</div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px"  type="text" value="${poHeader.poBasicValue}" pattern="[+-]?([0-9]*[.])?[0-9]+" 
											name="poBasicValue" id="poBasicValue" class="form-control" readonly>
										</div>
									<div class="col-md-2">Disc Value</div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" value="${poHeader.discValue}"   name="discValue" id="discValue" class="form-control"
										value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" readonly>
										</div>
									 
									 <div class="col-md-2">Final Value</div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" value="${poHeader.poBasicValue-poHeader.discValue}"   name="finalValue" id="finalValue" class="form-control"
										value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" readonly>
										</div>
							
							</div><br>
							   
							 <br>
			
			<div class="row">
						<div class="col-md-12" style="text-align: center">
						 
						 <c:choose>
							<c:when test="${poDetailList.size()>0}"> 
								<input type="submit" class="btn btn-info" value="Submit" onclick="check()"  >
							</c:when>
							<c:otherwise>
								<input type="submit" class="btn btn-info" value="Submit" onclick="check()" disabled>
							</c:otherwise>
						</c:choose>  
							 
						</div>
					</div>
				
			</form>
			
			 <form id="submitList"
				action="${pageContext.request.contextPath}/submitList"
				method="post">
			<div id="myModal" class="modal">
					<input   type="hidden" value="0" name="indMId" id="indMId"    >
					<input   type="hidden" value="0" name="vendIdTemp" id="vendIdTemp"    >
					<input   type="hidden" value="0" name="quotationTemp" id="quotationTemp"    >
					<input   type="hidden" value="0" name="poTypeTemp" id="poTypeTemp"    >
					<input   type="hidden" value="0" name="quotationDateTemp" id="quotationDateTemp"    >
					<input   type="hidden" value="0" name="payIdTemp" id="payIdTemp"    >
					<input   type="hidden" value="0" name="deliveryIdTemp" id="deliveryIdTemp"    >
					<input   type="hidden" value="0" name="dispatchModeTemp" id="dispatchModeTemp"    >
					<input   type="hidden" value="0" name="poDateTemp" id="poDateTemp"    >
					
										      
					<div class="modal-content" style="color: black;">
						<span class="close" id="close">&times;</span>
						<h3 style="text-align: center;">Select Item From Intend</h3>
							<div class=" box-content">
							<div class="row">
								<div style="overflow:scroll;height:70%;width:100%;overflow:auto">
									<table width="100%" border="0"class="table table-bordered table-striped fill-head "
										style="width: 100%" id="table_grid1">
										<thead>
											<tr>
										<th align="left"><input type="checkbox" id="allCheck" onClick="selectAll(this)" onchange="requiredAll()"/> Select All</th>
										<th>Sr.No.</th>
										<th>Item Name </th>
										<th>UOM</th>
										<th>Intend QTY</th> 
										<th>PO QTY</th>
										<th>Balance QTY</th>
										<th>Rate</th>
										<th>Disc</th>
										<th>Sch Days</th>
										<th>Remark</th>

									</tr>
										</thead>
										<tbody>
 
										</tbody>
									</table>
								</div>
							</div> 
							 
						</div><br>
						<div class="row">
						<div class="col-md-12" style="text-align: center">
							<input type="submit" class="btn btn-info" value="Submit" onclick="checkIndId()">


						</div>
					</div>
 
					</div>

				</div>
				</form>
			</div>
		</div>
	</div>
	<footer>
				<p>2018 © TRAMBAK RUBBER</p>
			</footer>
	</div>
	<!-- END Main Content -->

	

	<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
		class="fa fa-chevron-up"></i></a>
 
<!--   <script>
    /*
//  jquery equivalent
jQuery(document).ready(function() {
   jQuery(".main-table").clone(true).appendTo('#table-scroll .faux-table').addClass('clone');
   jQuery(".main-table.clone").clone(true).appendTo('#table-scroll .faux-table').addClass('clone2');
 });
*/
(function() {
  var fauxTable = document.getElementById("faux-table");
  var mainTable = document.getElementById("table_grid");
  var clonedElement = table_grid.cloneNode(true);
  var clonedElement2 = table_grid.cloneNode(true);
  clonedElement.id = "";
  clonedElement2.id = "";
  fauxTable.appendChild(clonedElement);
  fauxTable.appendChild(clonedElement2);
})();

</script> -->


	<!--basic scripts-->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="${pageContext.request.contextPath}/resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')
	</script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-cookie/jquery.cookie.js"></script>

	<!--page specific plugin scripts-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/chosen-bootstrap/chosen.jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-inputmask/bootstrap-inputmask.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-tags-input/jquery.tagsinput.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-pwstrength/jquery.pwstrength.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-duallistbox/duallistbox/bootstrap-duallistbox.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/dropzone/downloads/dropzone.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/clockface/js/clockface.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/date.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/daterangepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-switch/static/js/bootstrap-switch.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/ckeditor/ckeditor.js"></script>

	<!--flaty scripts-->
	<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>
		
		
		
		<script type="text/javascript">
 
var specialKeys = new Array();
specialKeys.push(8); //Backspace
function IsNumeric(e) {
    var keyCode = e.which ? e.which : e.keyCode
    var ret = ((keyCode >= 48 && keyCode <= 57) || specialKeys.indexOf(keyCode) != -1 || keyCode==9);
   // document.getElementById("error").style.display = ret ? "none" : "inline";
    return ret;
}

function check()
{
	 	   
	var vendId = $("#vendId").val();
	var poType = $("#poType").val(); 
	var payId = $("#payId").val();
	var deliveryId = $("#deliveryId").val();
	var dispatchMode = $("#dispatchMode").val();
	
	if(vendId==null || vendId == "")
	{
	alert("Select Vendor");
	}
	else if(poType==null || poType == "")
	{
	alert("Select PO Type ");
	}
	else if(payId==null || payId == "")
	{
		alert("Select Payment Term");
	}
	else if(deliveryId==null || deliveryId == "")
	{
	alert("Select Delivery");
	}
	else if(dispatchMode==null || dispatchMode == "")
	{
	alert("Select Dispatch Mode");
	}
}

</script>

<script>
// Get the modal
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
 var span = document.getElementById("close");

// When the user clicks the button, open the modal 
btn.onclick = function() {
    modal.style.display = "block";
    itemByIntendId(); 
    getValue();
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none"; 
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        
    }
}


function itemByIntendId()
{
	
	var indId = $("#indId").val();
	$('#loader').show();
	$
	.getJSON(
			'${geIntendDetailByIndId}',

			{
				 
				indId : indId,
				ajax : 'true'

			},
			function(data) {
				
				$('#table_grid1 td').remove();
				$('#loader').hide();

				if (data == "") {
					alert("No records found !!");

				}
				 

			  $.each(
							data,
							function(key, itemList) {
							 
								var tr = $('<tr></tr>'); 
								tr.append($('<td></td>').html('<input type="checkbox" name="select_to_approve"'+
										'id="select_to_approve'+itemList.indDId+'" onchange="requiredField('+itemList.indDId+')" value="'+itemList.indDId+'" >'));  
							  	tr.append($('<td></td>').html(key+1)); 
							  	tr.append($('<td></td>').html(itemList.itemCode)); 
							  	tr.append($('<td></td>').html(itemList.indItemUom));
							  	tr.append($('<td style="text-align:right;"></td>').html(itemList.indQty));
							  	tr.append($('<td ></td>').html('<input type="hidden"   id="indQty'+itemList.indDId+'" name="indQty'+itemList.indDId+'" value="'+itemList.indFyr+'" >'+
							  			'<input style="text-align:right; width:100px" type="text" onkeyup="calculateBalaceQty('+itemList.indDId+')" id="poQty'+itemList.indDId+'" name="poQty'+itemList.indDId+'" onchange="checkQty('+itemList.indDId+')"  class="form-control"  pattern="[+-]?([0-9]*[.])?[0-9]+"  >'));
							  	tr.append($('<td ></td>').html('<input style="text-align:right; width:100px" type="text" id="balanceQty'+itemList.indDId+'" name="balanceQty'+itemList.indDId+'" value="'+itemList.indFyr+'" onchange="checkQty('+itemList.indDId+')" class="form-control"  pattern="[+-]?([0-9]*[.])?[0-9]+" readonly>'));
							  	tr.append($('<td ></td>').html('<input style="text-align:right; width:100px" type="text" id="rate'+itemList.indDId+'" name="rate'+itemList.indDId+'"  onchange="checkQty('+itemList.indDId+')"  class="form-control"  pattern="[+-]?([0-9]*[.])?[0-9]+"  >'));
							  	tr.append($('<td ></td>').html('<input style="text-align:right; width:100px" type="text" id="disc'+itemList.indDId+'" name="disc'+itemList.indDId+'" value="0"  class="form-control"  pattern="[+-]?([0-9]*[.])?[0-9]+"  >'));
							  	tr.append($('<td ></td>').html('<input style="text-align:right; width:100px" type="text" id="indItemSchd'+itemList.indDId+'" name="indItemSchd'+itemList.indDId+'" value="'+itemList.indItemSchd+'"  class="form-control"  pattern="[+-]?([0-9]*[.])?[0-9]+" required>'));
							  	tr.append($('<td ></td>').html('<input style="text-align:right; width:100px" type="text" id="indRemark'+itemList.indDId+'" name="indRemark'+itemList.indDId+'" value="'+itemList.indRemark+'"  class="form-control" required>'));
							  	document.getElementById("indMId").value=itemList.indMId;
							  	 $('#table_grid1 tbody').append(tr);
							  	
							})
				
			});
	
	
}

function checkQty(key)
{
	var poQty = parseInt($("#poQty"+key).val());  
	 var rate = parseInt($("#rate"+key).val()); 
	if(poQty==0)
	{
		document.getElementById("poQty"+key).value=""; 
		alert("Enter Greater Than 0 ");
	} 
	else if(rate==0)
	{
		document.getElementById("rate"+key).value="";
		alert("Enter Greater Than 0 ");
	} 
	
	 
} 

function requiredAll()
{
	var checkboxes = document.getElementsByName('select_to_approve'); 
	
	
	if(document.getElementById("allCheck").checked == true)
	{
		 for (var i=0; i<checkboxes.length; i++) {
			 document.getElementById("select_to_approve"+checkboxes[i].value).checked == true;
		       
		    	  document.getElementById("poQty"+checkboxes[i].value).required=true; 
		  		document.getElementById("rate"+checkboxes[i].value).required=true;
		  		document.getElementById("disc"+checkboxes[i].value).required=true;
		      
		  }
	}
	else
	{
		for (var i=0; i<checkboxes.length; i++) {
			 document.getElementById("select_to_approve"+checkboxes[i].value).checked == false;
		       
		    	  document.getElementById("poQty"+checkboxes[i].value).required=false; 
		  		document.getElementById("rate"+checkboxes[i].value).required=false;
		  		document.getElementById("disc"+checkboxes[i].value).required=false;
		      
		  }
	}
	   
	 
} 

function requiredField(key)
{
	
	if(document.getElementById("select_to_approve"+key).checked == true)
	{
		document.getElementById("poQty"+key).required=true; 
		document.getElementById("rate"+key).required=true;
		document.getElementById("disc"+key).required=true;
	} 
	else
	{
		document.getElementById("poQty"+key).required=false; 
		document.getElementById("rate"+key).required=false;
		document.getElementById("disc"+key).required=false;
	}
	
	 
} 

  function getValue()
{
	
	 
	document.getElementById("vendIdTemp").value=document.getElementById("vendId").value;
	document.getElementById("quotationTemp").value=document.getElementById("quotation").value;
	document.getElementById("poTypeTemp").value=document.getElementById("poType").value;
	document.getElementById("quotationDateTemp").value=document.getElementById("quotationDate").value;
	document.getElementById("payIdTemp").value=document.getElementById("payId").value;
	document.getElementById("deliveryIdTemp").value=document.getElementById("deliveryId").value;
	document.getElementById("dispatchModeTemp").value=document.getElementById("dispatchMode").value;
	document.getElementById("poDateTemp").value=document.getElementById("poDate").value;
	
	 
} 
  function calculateBalaceQty(key)
  {
  	
  	  
  	var indQty = parseInt($("#indQty"+key).val());  
	 var poQty = parseInt($("#poQty"+key).val());  
	  
	    if(poQty>indQty)
		  {
		 	 document.getElementById("poQty"+key).value = 0;
		 	document.getElementById("balanceQty"+key).value = indQty;
		 	alert("Your Enter PO QTY Greater Than Balance QTY");
		  }
	  else
		  {  
		  document.getElementById("balanceQty"+key).value = indQty-poQty;
		   }  
  	
  	 
  } 
  
  function calculation()
  {
	         
  	var packPer = $("#packPer").val();
  	var packValue = $("#packValue").val();
  	var insuPer = $("#insuPer").val();
  	var insuValue = $("#insuValue").val();
  	var freightPer = $("#freightPer").val();
  	var freightValue = $("#freightValue").val();
  	var otherPer = $("#otherPer").val();
  	var otherValue = $("#otherValue").val();
  	var taxPer = $("#taxPer option:selected").text();
  	var taxId = $("#taxPer").val();
  	 
  	
  	$('#loader').show();
  	$
  	.getJSON(
  			'${calculatePurchaseHeaderValues}',

  			{
  				 
  				packPer : packPer,
  				packValue : packValue,
  				insuPer : insuPer,
  				insuValue : insuValue,
  				insuValue : insuValue,
  				freightPer : freightPer,
  				freightValue : freightValue,
  				otherPer : otherPer,
  				otherValue : otherValue,
  				taxId : taxId,
  				taxPer : taxPer,
  				ajax : 'true'

  			},
  			function(data) {
  				
  				$('#table_grid1 td').remove();
  				$('#loader').hide();

  				if (data == "") {
  					alert("No records found !!");

  				}
  				 
  				document.getElementById("packValue").value = data.poPackVal;
  				document.getElementById("insuValue").value = data.poInsuVal;
  				document.getElementById("freightValue").value = data.poFrtVal;
  				document.getElementById("otherValue").value = data.otherChargeAfter; 
  				document.getElementById("poBasicValue").value = data.poBasicValue;
  				document.getElementById("discValue").value = data.discValue;
  				document.getElementById("taxValue").value = data.poTaxValue;
  				document.getElementById("finalValue").value = data.poBasicValue-data.discValue+data.poPackVal+data.poInsuVal
  				+data.poFrtVal+data.poTaxValue+data.otherChargeAfter;
  				
  				
  			});
  	
  	
  }
</script>
<script type="text/javascript">

function getInvoiceNo() {
	
	var date = $("#poDate").val(); 
	var catId = $("#poType").val(); 

	$.getJSON('${getInvoiceNo}', {

		catId:catId,
		docId:2,
		date : date,
		ajax : 'true',

	}, function(data) { 
		
	document.getElementById("poNo").value=data.code;  
	
	});

}

</script>
		
</body>
</html>