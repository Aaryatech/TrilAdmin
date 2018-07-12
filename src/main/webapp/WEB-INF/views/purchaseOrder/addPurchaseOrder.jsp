<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	 

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<body>
	  

	<c:url var="addItemToList" value="/addItemToList"></c:url>
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
			
									<br/>
									
									
									
									<hr/>
									  
					<div class="box-content">
								<div class="col-md-2" >Select Item</div>
									<div class="col-md-3">
										<select name="itemId" id="itemId" class="form-control chosen"placeholder="Select RM " tabindex="6">
										<option value="" >Select Item</option>
									 <c:forEach items="${itemList}" var="itemList" >
							   <option value="${itemList.itemId}"><c:out value="${itemList.itemCode}"/></option>
 													 
												</c:forEach>
										</select>
									</div>	
									
									<div class="col-md-2">Quantity </div>
										<div class="col-md-3">
											<input type="text" placeholder="Enetr Quantity" name="rm_qty" id="rm_qty" class="form-control" onkeypress="return IsNumeric(event);" ondrop="return false;" onpaste="return false;">
										</div> 
		 						 
									
					</div>
			 		<br/>
			 		
			 		<div class="box-content">
			 
								<div class="col-md-2">Discount % </div>
								<div class="col-md-3">
									<input type="text" placeholder="Enter Discount %" name="disc_per" id="disc_per" value="0" class="form-control" onkeypress="return IsNumeric(event);" ondrop="return false;" onpaste="return false;">
								</div>
								
									
								 
					</div><br/><br>
			 
					<div class="box-content"> 
								<div class="col-md-2"></div>
								<div class="col-md-2"></div>
								<div class="col-md-2">
									<input type="button" class="btn btn-info pull-right" onclick="addItem()" value="Add Item"> 
								</div>
					</div><br/>
					<br/>
			
			
				<div class=" box-content">
					<div class="row">
						<div class="col-md-12 table-responsive">
							<table class="table table-bordered table-striped fill-head "
								style="width: 100%" id="table_grid">
								<thead>
									<tr>
										<th>Sr.No.</th>
										<th>Product</th>
										<th>Quantity</th>
										<th>Rate</th>
										<th>Discount %</th>
										<th>Value</th>
										<th>Schedule Days</th>
										<th>RM Remark</th>
										<th>Action</th>

									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
						</div>
					</div>
 
		</div>
		 			 <br/>
		 			 
		 			 <div class="box-content"> 
									<div class="col-md-2">Basic value <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" name="basicValue" id="basicValue" class="form-control" readonly>
										</div>
									<div class="col-md-2">Freight Amt <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" onchange="changeFreightAmt();" name="freightAmt" id="freightAmt" class="form-control"
										value="${materialRecNoteHeader.freightAmt}" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
									<div class="col-md-2">CGST <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" name="cgst" id="cgst" class="form-control"
										value="${materialRecNoteHeader.cgst}" readonly>
										</div>
							
							
							</div><br>
							<div class="box-content">
									<div class="col-md-2">Particular Disc Total <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" name="discAmt2" id="discAmt2" class="form-control"
										value="${materialRecNoteHeader.discAmt2}" readonly>
										</div>
									<div class="col-md-2">Insu Amt <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" onchange="changeFreightAmt();" name="insuranceAmt" id="insuranceAmt" class="form-control"
										value="${materialRecNoteHeader.insuranceAmt}" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
									<div class="col-md-2">SGST <i class="fa fa-inr" style="font-size:13px"></i></div>
									<div class="col-md-2">
										<input style="text-align:right; width:150px" type="text" name="sgst" id="sgst" class="form-control"
									value="${materialRecNoteHeader.sgst}" readonly>
									</div>
							
							</div><br>
						
						<div class="box-content"> 
							 
						 	<div class="col-md-2">Per Disc %</div>
							  <div class="col-md-1">
								<input style="text-align:right; width:50px" type="text" onchange="changeFreightAmt();" name="discPer" id="discPer"
									value="${materialRecNoteHeader.discPer}" class="form-control" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
									
									 </div>  
											<div class="col-md-1"> 
									<input style="text-align:right; width:60px" type="text" name="discAmt" id="discAmt"
									value="${materialRecNoteHeader.discAmt}" class="form-control"
									readonly>
									 
							</div>
							 <div class="col-md-2">Other3(Extra) <i class="fa fa-inr" style="font-size:13px"></i></div>
									<div class="col-md-2">
										<input style="text-align:right; width:150px" type="text" name="Other3" id="other3" onchange="changeFreightAmt();" class="form-control"
									value="${materialRecNoteHeader.other3}" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
									</div>
									
									<div class="col-md-2">IGST <i class="fa fa-inr" style="font-size:13px"></i></div>
									<div class="col-md-2">
										<input style="text-align:right; width:150px" type="text" name="igst" id="igst" class="form-control"
									value="${materialRecNoteHeader.igst}" readonly>
									</div>
							 </div><br>
						  <div class="box-content">
								 
									<div class="col-md-2">Other2 (Discount) <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" name="other2" onchange="changeFreightAmt();" id="other2" class="form-control"
										value="${materialRecNoteHeader.other2}" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
										
										<div class="col-md-2">Other4(Extra) <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" name="other4" onchange="changeFreightAmt();" id="other4" class="form-control"
										value="${materialRecNoteHeader.other4}" pattern="[+-]?([0-9]*[.])?[0-9]+" required>
										</div>
									<div class="col-md-2">Cess <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" name="cess" id="cess" class="form-control"
										value="${materialRecNoteHeader.cess}" readonly>
										</div>
									
							
							</div><br>
							  
							<div class="box-content">
								 	<div class="col-md-2"> </div>
										<div class="col-md-2"> </div>
									<div class="col-md-2"> </div>
										<div class="col-md-2"> </div>
									<div class="col-md-2">Bill Total <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" name="billAmount" id="billAmount" class="form-control"
										value="${materialRecNoteHeader.billAmount}" readonly>
										</div>
									
									
							
							</div><br>
							
							<div class="box-content">
								 
									<div class="col-md-2"> </div>
										<div class="col-md-2"> </div>
									<div class="col-md-2"> </div>
										<div class="col-md-2"> </div>
									
									<div class="col-md-2">Round Off <i class="fa fa-inr" style="font-size:13px"></i></div>
										<div class="col-md-2">
											<input style="text-align:right; width:150px" type="text" name="roundOff" id="roundOff" class="form-control"
										value="${materialRecNoteHeader.roundOff}" readonly>
										</div>
									
							
							</div><br>
			
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

	<script type="text/javascript">
		function updateTotal(orderId, rate) {
			
			var newQty = $("#billQty" + orderId).val();

			var total = parseFloat(newQty) * parseFloat(rate);


			 $('#billTotal'+orderId).html(total);
		}
		function edit(key)
		{
			//alert(key);
			document.getElementById("poQty"+key).disabled = false;
			document.getElementById("edit"+key).style.visibility="hidden";
			document.getElementById("ok"+key).style.visibility="visible";
			
			//style="visibility: hidden;"
		}
		function changeQty(key)
		{
			
			var qty=document.getElementById("poQty"+key).value;
			//alert(qty);
			var rate=document.getElementById("poRate"+key).value;
			//alert("rate"+rate);
			 document.getElementById("poValue"+key).value=qty*rate;
			
		}
		function submit(key)
		{
			var qty=document.getElementById("poQty"+key).value;
			document.getElementById("poQty"+key).disabled = true;
			//alert(qty);
			document.getElementById("edit"+key).style.visibility="visible";
			document.getElementById("ok"+key).style.visibility="hidden";
			$
			.getJSON(
					'${updateRmQty}',

					{
						 
						index : key,
						updateQty : qty,
					
						ajax : 'true'

					},
					function(data) {
						
					});
			
			
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
 
 
 
 $(document).ready(function() { 
	$('#rm_id').change(
			function() {
			var	rm_id =document.getElementById("rm_id").value; 
			
				$.getJSON('${getUomForRawMaterial}', { 
					ajax : 'true'
				}, function(uomlist) {
					
					 var uomlistlength = uomlist.length; 
					 cId=document.getElementById("rm_cat").value; 
								$.getJSON('${getRmListByCatId}', {
									
									catId : cId,
									ajax : 'true'
									
								}, 
								function(data) {
									
									var len = data.length;
									var uom; 
									for ( var i = 0; i < len; i++) { 
											if(data[i].rmId==rm_id)
												{
												uom=data[i].rmUomId;
												break;
												} 
									}
									
									for(var j = 0; j< uomlistlength; j++)
									{
									 
									if(uom==uomlist[j].uomId)
										{ 
										document.getElementById("rm_uom").value=uomlist[j].uom;
										break;
										}
									
									}
									
									 
								});

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


		
		
</body>
</html>