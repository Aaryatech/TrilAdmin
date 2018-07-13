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
	  

	<c:url var="geIntendDetailByIndId" value="/geIntendDetailByIndId"></c:url>
		<c:url var="updateRmQty0" value="/updateRmQty0"></c:url>
		<c:url var="getRmCategory" value="/getRmCategory" />
			<c:url var="getRmListByCatId" value="/getRmListByCatId" />
						<c:url var="getRmRateAndTax" value="/getRmRateAndTax" />

	<c:url var="getUomForRawMaterial" value="/getUomForRawMaterial" />


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
					 
		<div class="box">
			<form id="submitPurchaseOrder"
				action="${pageContext.request.contextPath}/submitPurchaseOrder"
				method="post">
			<div class="box-content">
				<div class="col-md-2">PO No.  </div>
				<div class="col-md-3"><input type="text" id="poNo" name="poNo" value="1" class="form-control" readonly>
				</div>
				<div class="col-md-2">PO Date</div> 
				<div class="col-md-3">
				<input type="text" id="poDate" name="poDate" value="${date}" class="form-control date-picker" required>
					
				</div>
				</div><br/>
				<div class="box-content">
				<div class="col-md-2" >Vendor Name</div>
									<div class="col-md-3">
										<select name="vendId" id="vendId" class="form-control chosen" tabindex="6" required>
										<option value=""  >Select Vendor</option>
											 <c:forEach items="${vendorList}" var="vendorList"
							varStatus="count">
							  <option value="${vendorList.vendorId}"><c:out value="${vendorList.vendorName}"/></option>
 													 
												</c:forEach>
						

										</select>
									</div>
									<div class="col-md-2">Vendor Quotation</div>
				<div class="col-md-3">
					<input type="text" placeholder="Enter Quotation No"  name="quotation" id="quotation" class="form-control" onkeypress="return IsNumeric(event);" ondrop="return false;" onpaste="return false;" required>
				</div>
				 
			</div><br/>
			
			<div class="box-content">
			<div class="col-md-2" >PO Type</div>
									<div class="col-md-3">
										<select name="poType" id="poType" class="form-control chosen" tabindex="6" required>
										<option value="" >Select PO Type</option>
										<option value="1">Regular</option>
										<option value="2">Job Work</option>
										<option value="3">General</option>
										<option value="4">Other</option> 
										</select>
									</div>
									
									<div class="col-md-2" >Quotation Ref. Date</div>
									<div class="col-md-3">
										 <input type="text" placeholder="Select Quotation Date" name="quotation_date" id="quotation_date" class="form-control date-picker" required>
									</div>
									</div><br/>
									
									
			<div class="box-content">
				<div class="col-md-2" >Payment Terms</div>
									<div class="col-md-3">
										<select name="payId" id="payId" class="form-control chosen" tabindex="6" required>
										<option value="">Select Pay Terms</option>
											 <c:forEach items="${paymentTermsList}" var="paymentTermsList" >
							   <option value="${paymentTermsList.pymtTermId}"><c:out value="${paymentTermsList.pymtDesc}"/></option> 
												</c:forEach>
						 
										</select>
									</div>
									
									 <div class="col-md-2" >Select Delivery</div>
									<div class="col-md-3">
										<select name="transportation" id="transportation" class="form-control chosen" tabindex="6" required>
										<option value="" >Select </option>
											 <c:forEach items="${deliveryTermsList}" var="deliveryTermsList" >
							   <option value="${deliveryTermsList.deliveryTermId}"><c:out value="${deliveryTermsList.deliveryDesc}"/></option>
 											 </c:forEach>
						 
										</select>
									</div> 
				</div><br/>
								<div class="box-content">
								
									<div class="col-md-2" >Select Dispatch Mode</div>
									<div class="col-md-3">
									
										<select name="freight" id="freight" class="form-control chosen" tabindex="6" required>
										<option value="" >Select </option>
									 <c:forEach items="${dispatchModeList}" var="dispatchModeList" >
							   <option value="${dispatchModeList.dispModeId}"><c:out value="${dispatchModeList.dispModeDesc}"/></option>
 													 
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
							   <option value="${intedList.indMId}"> ${intedList.indMNo} &nbsp;&nbsp; ${intedList.indMDate}</option>
 													 
												</c:forEach>
										</select>
									</div>	
									<div class="col-md-1"></div>
									<div class="col-md-2"><input type="button" class="btn btn-info" value="Get Item From Intend " id="myBtn"></div>
									
									 
					</div>
			 		<br/>
									
									<hr/>
									
									<div id="myModal" class="modal">

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
										<th>Sr.No.</th>
										<th>Item Name </th>
										<th>UOM</th>
										<th>Intend QTY</th>
										<th>Balance QTY</th>
										<th>PO QTY</th>
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
							<input type="submit" class="btn btn-info" value="Submit" onclick="check()">


						</div>
					</div>
 
					</div>

				</div>
									  
					   
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
										<th>Balance QTY</th>
										<th>PO QTY</th>
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
 
		</div>
		 			 <br/>
		 			 <hr/>
		 			 <div class="box-content"> 
									<div class="col-md-2">Packing Charges %</div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" name="packPer" id="packPer" class="form-control" required>
										</div>
									<div class="col-md-2">Packing Charges Value <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" onchange="changeFreightAmt();" name="packValue" id="packValue" class="form-control"
										value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
									<div class="col-md-2">Remark</div>
										<div class="col-md-2">
											<input   type="text" name="cgst" id="cgst" class="form-control" value="NA"  >
										</div>
							
							
							</div><br>
							<div class="box-content">
									<div class="col-md-2">Insurance Charges %</div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" name="packPer" id="packPer" class="form-control" required>
										</div>
									<div class="col-md-2">Insurance Charges Value <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" onchange="changeFreightAmt();" name="packValue" id="packValue" class="form-control"
										value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
									<div class="col-md-2">Remark</div>
										<div class="col-md-2">
											<input   type="text" name="cgst" id="cgst" class="form-control" value="NA"  >
										</div>
							
							</div><br>
						
						<div class="box-content"> 
							 
						 	<div class="col-md-2">Freight Charges %</div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" name="packPer" id="packPer" class="form-control" required>
										</div>
									<div class="col-md-2">Freight Charges Value <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" onchange="changeFreightAmt();" name="packValue" id="packValue" class="form-control"
										value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
									<div class="col-md-2">Remark</div>
										<div class="col-md-2">
											<input   type="text" name="cgst" id="cgst" class="form-control" value="NA"  >
										</div>
							 </div><br>
						  <div class="box-content">
								 
									<div class="col-md-2">Other Charges %</div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" name="packPer" id="packPer" class="form-control" required>
										</div>
									<div class="col-md-2">Other Charges Value <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" onchange="changeFreightAmt();" name="packValue" id="packValue" class="form-control"
										value="0" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
									<div class="col-md-2">Remark</div>
										<div class="col-md-2">
											<input   type="text" name="cgst" id="cgst" class="form-control" value="NA"  >
										</div>
							
							</div><br>
							   
							 <br>
			
			<div class="row">
						<div class="col-md-12" style="text-align: center">
							<input type="submit" class="btn btn-info" value="Submit" onclick="check()">


						</div>
					</div>
				
			</form>
			</div>
		</div>
	</div>
	</div>
	<!-- END Main Content -->

	<footer>
	<p>2017 © Monginis.</p>
	</footer>

	<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
		class="fa fa-chevron-up"></i></a>

 

	<script type="text/javascript">
		function addItem() {

		 

			  
				var taxation = $("#taxation").val();
				var kind_attn = $("#kind_attn").val();
				var rm_qty = $("#rm_qty").val();
				var supp_id = $("#supp_id").val();

				var po_type = $("#po_type").val();
				var delv_date = $("#delv_date").val();
				var delv_at = $("#delv_at").val();
				var quotation_ref_no = $("#quotation_ref_no").val();
				var po_no = $("#po_no").val();
				var po_date = $("#po_date").val();
				var rm_id = $("#rm_id").val();
				var disc_per = $("#disc_per").val();
				 
		 if(validate()){
				
			 
				 
				   
			 
				$('#loader').show();

				
				$
				.getJSON(
						'${getRmRateAndTax}',

						{
							 
							rm_id : rm_id,
							po_date : po_date,
							po_no : po_no,
							quotation_ref_no : quotation_ref_no,
							delv_at : delv_at,
							delv_date : delv_date,
							po_type : po_type,
							supp_id : supp_id,
							rm_qty : rm_qty,
							kind_attn : kind_attn,
							taxation : taxation,
							disc_per : disc_per,
							ajax : 'true'

						},
						function(data) {
							if(data==0){
								alert("Item rate  is not verified !!");
								$('#loader').hide();
							}
							else{
						
				$
						.getJSON(
								'${addItemToList}',

								{
									 
									rm_id : rm_id,
									po_date : po_date,
									po_no : po_no,
									quotation_ref_no : quotation_ref_no,
									delv_at : delv_at,
									delv_date : delv_date,
									po_type : po_type,
									supp_id : supp_id,
									rm_qty : rm_qty,
									kind_attn : kind_attn,
									taxation : taxation,
									disc_per : disc_per,
									ajax : 'true'

								},
								function(data) {

									$('#table_grid td').remove();
									$('#loader').hide();

									if (data == "") {
										alert("No records found !!");

									}
								 
  
								  $.each(
												data,
												function(key, itemList) {
												

													var tr = $('<tr></tr>');

												
													
													
												  	tr.append($('<td></td>').html(key+1));

												  	tr.append($('<td></td>').html(itemList.rmName));

												  	tr.append($('<td></td>').html('<input type="text" id="poQty'+key+'" onkeyup="changeQty('+key+');"value="'+itemList.poQty+'" class="form-control" disabled="true">'));

												  	tr.append($('<td></td>').html(itemList.poRate+'<input type="hidden" id="poRate'+key+'" value='+itemList.poRate+' readonly>'));

												  	tr.append($('<td></td>').html(itemList.discPer));
												  	
												  	//tr.append($('<td></td>').html(itemList.poTaxable));
												  	tr.append($('<td></td>').html('<input type="text" value="'+itemList.poTaxable+'" id="poValue'+key+'" class="form-control" disabled="true">'));

												  	tr.append($('<td></td>').html(itemList.schDays));
												  	
												  	tr.append($('<td></td>').html(itemList.rmRemark));
												  	tr.append($('<td></td>').html('  <span class="glyphicon glyphicon-edit" id="edit'+key+'" onclick="edit('+key+');"> </span>  <span style="visibility: hidden;" class="glyphicon glyphicon-ok" onclick="submit('+key+');" id="ok'+key+'"></span>    <span class="glyphicon glyphicon-remove" id="delete'+key+'"></span>  '));
												  	 



 

													$('#table_grid tbody').append(tr);

													 
 
												})  
								});
							}  //else
						});
					
		 }
		}
	</script>

	<script type="text/javascript">
		function validate() {
			

			 
	 
			var rm_qty = $("#rm_qty").val();
			var supp_id = $("#supp_id").val();
  
			var rm_id = $("#rm_id").val();
			var disc_per = $("#disc_per").val();

			var isValid = true;

			if (rm_qty=="") {

				isValid = false;
				alert("Please enter valid qty");

			}
			else if (isNaN(rm_qty) || rm_qty <= 0){
				isValid = false;
				alert("Please enter valid qty");
			}
			else if (rm_id == "" || rm_id == null) {

				isValid = false;
				alert("Please select RM Item");

			}
			
			else if (supp_id == "" || supp_id == null) {

				isValid = false;
				alert("Please select Supplier");

			}
			
			else if (  disc_per=="") {

				isValid = false;
				alert("Please enter valid Discount %");
				

			}
			else if(isNaN(disc_per) || disc_per < 0)
				{
				isValid = false;
				alert("Please enter valid Discount %");
				}
			return isValid;

		}
		
	</script>

	 

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
$(document).ready(function() { 
	$('#rm_group').change(
			function() {
				$.getJSON('${getRmCategory}', {
					grpId : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="" disabled="disabled" selected >Select Category</option>';
					
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].catId + '">'
								+ data[i].catName + '</option>';
					}
					html += '</option>';
					$('#rm_cat').html(html);
					$('#rm_cat').trigger("chosen:updated");

				});
			});
});
$(document).ready(function() { 
	$('#rm_cat').change(
			function() {
				$.getJSON('${getRmListByCatId}', {
					catId : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="" disabled="disabled" selected >Select Raw Material</option>';
					
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].rmId + '">'
								+ data[i].rmName + '</option>';
					}
					html += '</option>';
					$('#rm_id').html(html);
					$("#rm_id").trigger("chosen:updated");

				});
				
			 
	
			});
});
 
  
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
	
	var transportation = $("#transportation").val();
	var pay_terms = $("#pay_terms").val();
	// alert(transportation);
	var freight = $("#freight").val();
	var insurance = $("#insurance").val();
	if(transportation==null)
	{
	alert("Select Transporter ");
	}
	else if(pay_terms==null)
	{
	alert("Select Payment ");
	}
	else if(freight==null)
		{
		alert("Enter Freight Amt");
		}
	else if(insurance==null)
	{
	alert("Enter Insurance Amt");
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
							  	tr.append($('<td></td>').html(key+1)); 
							  	tr.append($('<td></td>').html(itemList.itemId)); 
							  	tr.append($('<td></td>').html(itemList.indItemUom));
							  	tr.append($('<td></td>').html(itemList.indQty));
							  	tr.append($('<td></td>').html(itemList.indQty));
							  	tr.append($('<td></td>').html(itemList.indQty));
							  	tr.append($('<td></td>').html(itemList.indQty));
							  	tr.append($('<td></td>').html(itemList.indQty));
							  	tr.append($('<td></td>').html(itemList.indItemSchd));
							  	tr.append($('<td></td>').html(itemList.indRemark));
							  	 $('#table_grid1 tbody').append(tr);
							  	
							})
				
			});
	
	
}
</script>
		
		
</body>
</html>