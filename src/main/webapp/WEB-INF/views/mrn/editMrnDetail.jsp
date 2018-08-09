<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tableSearch.css">
<body>
	<%-- <jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include> --%>

	<c:url var="getPOHeaderList" value="/getPOHeaderList" />
	<c:url var="getPODetailList" value="/getpoDetailForEditMrn" />

	<c:url var="getTempPoDetail" value="/getTempPoDetail" />

	<c:url var="editMrnProcess" value="/editMrnProcess" />

	<c:url var="getMrnDetail" value="/getMrnDetail" /> <!--  used here -->
	<c:url var="submitNewMrnItemOnEdit" value="/submitNewMrnItemOnEdit" /> <!--  used here -->
	
	<div class="container" id="main-container">

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
						<i class="fa fa-file-o"></i>
					</h1>

				</div>
			</div>
			<!-- END Page Title -->
			<!-- BEGIN Main Content -->
			<div class="row">
				<div class="col-md-12">
					<div class="box">
						<div class="box-title">
							<h3>
								<i class="fa fa-bars"></i> Edit MRN
							</h3>
							<div class="box-tool">
								<!-- <a href="">Back to List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a> -->
							</div>

						</div>

						<div class="box-content">
							<form method="post" class="form-horizontal" id="validation-form">

								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Grn Type
									</label>
									<div class="col-sm-6 col-lg-4 controls">
										<select name="grn_type" id="grn_type"
											class="form-control chosen" placeholder="Grn Type"
											data-rule-required="true" disabled>

											<c:choose>

												<c:when test="${mrnHeader.mrnType==1}">
													<option selected value="1">Regular</option>
													<option value="2">Job Work</option>
													<option value="3">General</option>
													<option value="4">Other</option>
												</c:when>

												<c:when test="${mrnHeader.mrnType==2}">
													<option value="1">Regular</option>
													<option selected value="2">Job Work</option>
													<option value="3">General</option>
													<option value="4">Other</option>
												</c:when>

												<c:when test="${mrnHeader.mrnType==3}">
													<option value="1">Regular</option>
													<option value="2">Job Work</option>
													<option selected value="3">General</option>
													<option value="4">Other</option>
												</c:when>

												<c:otherwise>
													<option value="1">Regular</option>
													<option value="2">Job Work</option>
													<option value="3">General</option>
													<option selected value="4">Other</option>
												</c:otherwise>

											</c:choose>
										</select>
									</div>

									<label class="col-sm-3 col-lg-2 control-label">
										Vendor </label>
									<div class="col-sm-6 col-lg-4 controls">
										<select name="vendor_id" id="vendor_id"
											class="form-control chosen" placeholder="Vendor"  disabled="disabled"
											data-rule-required="true">

											<option selected value="${mrnHeader.vendorId}"><c:out value="${mrnHeader.vendorName}"/></option>

											<%-- <c:forEach items="${vendorList}" var="vendor"
												varStatus="count">
												<option value="${vendor.vendorId}"><c:out value="${vendor.vendorName}"/></option>
											</c:forEach> --%>
										</select>
									</div>

								</div>

								<div class="form-group">


									<label class="col-sm-3 col-lg-2 control-label">GRN No </label>
									<div class="col-sm-6 col-lg-4 controls">
										<input type="text" name="grn_no" id="grn_no"
											value="${mrnHeader.mrnNo}" class="form-control"
											placeholder="GRN No" data-rule-required="true" readonly="readonly" />
									</div>

									<label class="col-sm-3 col-lg-2 control-label">Grn Date
									</label>

									<div class="col-sm-6 col-lg-4 controls">
										<input class="form-control date-picker" id="grn_date" disabled
											size="16" type="text" name="grn_date"
											value="${mrnHeader.mrnDate}" required />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Gate
										Entry No </label>

									<div class="col-sm-6 col-lg-4 controls">
										<input type="text" name="gate_entry_no" id="gate_entry_no"
											value="${mrnHeader.gateEntryNo}" class="form-control"
											placeholder="Gate Entry No" data-rule-required="true" />
									</div>

									<label class="col-sm-3 col-lg-2 control-label">Gate
										Entry Date </label>
									<div class="col-sm-6 col-lg-4 controls">
										<input class="form-control date-picker" id="gate_entry_date"
											size="16" type="text" name="gate_entry_date"
											value="${mrnHeader.gateEntryDate}" required />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Chalan
										No </label>

									<div class="col-sm-6 col-lg-4 controls">
										<input type="text" name="chalan_no" id="chalan_no"
											class="form-control" placeholder="Chalan No"
											data-rule-required="true" value="${mrnHeader.docNo}" />
									</div>

									<label class="col-sm-3 col-lg-2 control-label">Chalan
										Date </label>
									<div class="col-sm-6 col-lg-4 controls">
										<input class="form-control date-picker" id="chalan_date"
											size="16" type="text" name="chalan_date"
											value="${mrnHeader.docDate}" required />
									</div>

								</div>


								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Bill No
									</label>

									<div class="col-sm-6 col-lg-4 controls">
										<input type="text" name="bill_no" id="bill_no"
											class="form-control" placeholder="Bill No"
											value="${mrnHeader.billNo}" data-rule-required="true" />
									</div>

									<label class="col-sm-3 col-lg-2 control-label">Bill
										Date </label>
									<div class="col-sm-6 col-lg-4 controls">
										<input class="form-control date-picker" id="bill_date"
											size="16" type="text" name="bill_date"
											value="${mrnHeader.billDate}" required />
									</div>

								</div>
	<div class="col-md-2"></div>
	
	<div class="col-md-3">	<input class="btn btn-info"  id="getPoButton" style="text-align: center;"
											onclick="getPoDetail(0,0)" size="16" type="button"
											name="getPoButton" value="Get PO Detail to Add New Item"  > </div>

							<br/><br/>
								<div id="myModal" class="modal">

									<div class="modal-content" style="color: black;">
										<span class="close" id="close" style="display: none">&times;</span>
										<h3 style="text-align: center;">Add New Item</h3>
										<div class=" box-content">
											<div class="row">
												<div
													style="overflow: scroll; height: 70%; width: 100%; overflow: auto">
													<table width="100%" border="0"
														class="table table-bordered table-striped fill-head "
														style="width: 100%" id="table_grid2">
														<thead>
															<tr>
																<th  class="col-md-1" style="text-align: center;">Sr.No.</th>
																<th  class="col-md-2" style="text-align: center;">Item Code</th>
																<th  class="col-md-3" style="text-align: center;">Item Name</th>
																<th  class="col-md-1" style="text-align: center;">PO QTY</th>
																<th  class="col-md-1" style="text-align: center;">Rec QTY</th>
																<th  class="col-md-1" style="text-align: center;">Pend QTY</th>
																<th  class="col-md-1" style="text-align: center;">Po No</th>
																
																<th  class="col-md-1" style="text-align: center;">Status</th>
															</tr>
														</thead>
														<tbody>

														</tbody>
													</table>
												</div>
											</div>

										</div>
										<br>
										<div class="row">
											<div class="col-md-12" style="text-align: center">
												<input type=button class="btn btn-info" value="Temp Submit"
													onclick="tempSubmit(${mrnHeader.mrnId})">

											</div>
										</div>
									</div>
								</div>

								<div class=" box-content">
									<div class="row">
										<div
											style="overflow: scroll; height: auto; width: 100%; overflow: auto">
											<table width="100%" border="1"
												class="table table-advanced "
												style="width: 100%" id="table_grid1">
												<thead>
													<tr>
														<th  class="col-md-1" style="text-align: center;">Sr.No.</th>
														<th  class="col-md-2" style="text-align: center;" >Item Code</th>
														<th  class="col-md-3" style="text-align: center;">Item Name</th>
														<th  class="col-md-1" style="text-align: center;">PO QTY</th>
														<th  class="col-md-1" style="text-align: center;">Mrn QTY</th>
														<th  class="col-md-1" style="text-align: center;">PO No</th>
														<th  class="col-md-1" style="text-align: center;">Status</th>
														<th  class="col-md-1" style="text-align: center;">Action</th>
													</tr>
												</thead>

												<tbody>
													<c:forEach items="${mrnDetailList}" var="mrnDetail"
														varStatus="count">

														<tr>

															<td class="col-md-1" style="text-align: center;"><c:out
																	value="${count.index+1}" /></td>
															<td class="col-md-2" style="text-align: center;"><c:out
																	value="${mrnDetail.itemCode}" /></td>
															<td class="col-md-3" style="text-align: center;"><c:out
																	value="${mrnDetail.itemName}" /></td>
															<c:set var="status" value="o"></c:set>
															<c:choose>
																<c:when test="${mrnDetail.mrnDetailStatus==0}">
																	<c:set var="status" value="Pending"></c:set>
																</c:when>
																<c:when test="${mrnDetail.mrnDetailStatus==1}">
																	<c:set var="status" value="Partial"></c:set>
																</c:when>
																<c:when test="${mrnDetail.mrnDetailStatus==2}">
																	<c:set var="status" value="Completed"></c:set>
																</c:when>
																<c:otherwise>
																	<c:set var="status" value="Other"></c:set>
																</c:otherwise>
															</c:choose>

															<td class="col-md-1" style="text-align: center;"><c:out
																	value="${mrnDetail.poQty}" /></td>

															<td class="col-md-1" style="text-align: center;">
															<input type="text" style="text-align: center;" class="form-control" id="mrnRecQty${mrnDetail.mrnDetailId}" onchange="updateMrnQty(this.value,${mrnDetail.mrnDetailId},${mrnDetail.itemId},${mrnDetail.mrnQty},${mrnDetail.poPendingQty})" name="mrnRecQty${mrnDetail.mrnDetailId}" value="${mrnDetail.mrnQty}">
															</td>

															<td class="col-md-1" style="tsext-align: center;"><c:out
																	value="${mrnDetail.poNo}" /></td>

															<td class="col-md-1" style="text-align: center;"><c:out
																	value="${status}" /></td>
															<td class="col-md-1" style="text-align: center;">		
															<a
															href="${pageContext.request.contextPath}/deleteMrnDetail/${mrnDetail.mrnDetailId}" title="Delete"><span
																class="fa fa-trash-o"></span></a>
																</td>

														</tr>
													</c:forEach>
												</tbody>

											</table>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Transport
									</label>

									<div class="col-sm-6 col-lg-4 controls">
										<input type="text" name="transport" id="transport"
											class="form-control" placeholder="Transport" value="${mrnHeader.transport}"
										
											data-rule-required="true" />
									</div>

									<label class="col-sm-3 col-lg-2 control-label">Lorry No
									</label>
									<div class="col-sm-6 col-lg-4 controls">
										<input type="text" name="lorry_no" id="lorry_no"
											class="form-control" placeholder="Lorry No" value="${mrnHeader.lrNo}"
											data-rule-required="true" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Lorry
										Date </label>
									<div class="col-sm-6 col-lg-4 controls">
										<input class="form-control date-picker" id="lorry_date"
											size="16" type="text" name="lorry_date" value="${mrnHeader.lrDate}"
											required />
									</div>
									<label class="col-sm-3 col-lg-2 control-label">Remark </label>

									<div class="col-sm-6 col-lg-4 controls">
										<input type="text" name="lorry_remark" id="lorry_remark"
											class="form-control" placeholder="Lorry Remark" value="${mrnHeader.remark1}"
											data-rule-required="true" />
									</div>
								</div>

<input type="hidden" value="${mrnHeader.mrnId}" name="mrnId" id="mrnId">
							</form>

						</div>
					</div>
					<div class="form-group">
					<div class="col-md-4"></div>
					<div class="col-md-3" align= center>
					<input type="button"
						
						onclick="editMrn()" class="btn btn-info" value="Edit Mrn">

					<button class="buttonload" id="loader" style="display: none">
						<i class="fa fa-spinner fa-spin"></i>Loading
					</button>
					</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<!-- END Main Content -->
	<footer>
				<p>2018 © TRAMBAK RUBBER</p>
			</footer>

	<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
		class="fa fa-chevron-up"></i></a>
	</div>
	<!-- END Content -->
	<!-- END Container -->

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
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.resize.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.pie.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.stack.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.crosshair.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.tooltip.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/sparkline/jquery.sparkline.min.js"></script>


	<!--page specific plugin scripts-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/additional-methods.min.js"></script>





	<!--flaty scripts-->
	<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>
	<!--page specific plugin scripts-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/chosen-bootstrap/chosen.jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/clockface/js/clockface.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/date.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/daterangepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/common.js"></script>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/additional-methods.min.js"></script>
	<script type="text/javascript">
			function getPoDetail() {
				alert("Hiii out");
				//alert("inside Indent Insetr");

				//alert("called Button")
				//var selectedPoIds = $("#po_list").val();
				//alert(" Selected PO Id  " + selectedPoIds);

				$.getJSON('${getPODetailList}', {
				/* 	poIds : JSON.stringify(selectedPoIds),
					qty	: qty,
					poDId : poDId, */
					ajax : 'true',
				}, function(data) {
					$('#table_grid2 td').remove();
					$('#loader').hide();

					if (data == "") {
						alert("No records found !!");

					}

					$.each(data, function(key, itemList) {
						alert("data received "+data[0]);
						
						var tr = $('<tr></tr>');
						
						
						tr.append($('<td></td>').html(key + 1));
						tr.append($('<td></td>').html(itemList.itemCode));
						tr.append($('<td></td>').html(itemList.itemName));
						tr.append($('<td></td>').html(itemList.itemQty));
						
						tr
						.append($(
								'<td></td>')
						.html("<input type=text style='text-align:right; width:90px' class=form-control name=recQty"+itemList.poDetailId+""+itemList.itemId+" id=recQty"+itemList.poDetailId+""+itemList.itemId+" onchange='callMe(this.value,"+itemList.poDetailId+","+itemList.pendingQty+")' value="+itemList.receivedQty+" />"));
				/* 		var pendQty=0;
						if(itemList.receivedQty==0){
							
							pendQty=itemList.pendingQty;
						}else{ */
						var	pendQty=itemList.pendingQty-itemList.receivedQty;
						//}
						
	//tr.append($('<td></td>').html(itemList.itemQty));//textbox



	tr.append($('<td></td>').html(pendQty));
						tr.append($('<td></td>').html(itemList.poNo));
						var status;
						if(itemList.status==0){
							status="Pending";
						}
						if(itemList.status==1){
							status="Partial";
						}
						if(itemList.status==3){
							status="Completed";
						}
						
					 	tr
						.append($(
								'<td class="col-md-1" style="text-align: center;"></td>')
								.html(status));
					 	
					 	
					 	//tr.append($('<td></td>').html(itemList.status));
						$('#table_grid2 tbody').append(tr);
					})
				});
			}
		
	</script>

		
	<script>
		// Get the modal
		var modal = document.getElementById('myModal');

		// Get the button that opens the modal
		var btn = document.getElementById("getPoButton");

		// Get the <span> element that closes the modal
		var span = document.getElementById("close");

		// When the user clicks the button, open the modal 
		btn.onclick = function() {
			modal.style.display = "block";
			getPoDetail(0,0);
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

		function getPoDetail(qty,poDId) {
			//alert("Hiii in modal");
			//alert("inside Indent Insetr");

			//alert("called Button")
			//var selectedPoIds = $("#po_list").val();
			//alert(" Selected PO Id  " + selectedPoIds);

			$.getJSON('${getPODetailList}', {
			/* 	poIds : JSON.stringify(selectedPoIds),*/
				qty	: qty,
				poDId : poDId, 
				ajax : 'true',
			}, function(data) {
				$('#table_grid2 td').remove();
				$('#loader').hide();

				if (data == "") {
					alert("No records found !!");

				}

				$.each(data, function(key, itemList) {
					
					//alert("data received "+data[0].poDetailId);
					
					
					var tr = $('<tr></tr>');
					
					
					tr.append($('<td></td>').html(key + 1));
					tr.append($('<td></td>').html(itemList.itemCode));
					tr.append($('<td></td>').html(itemList.itemName));
					tr.append($('<td></td>').html(itemList.itemQty));
					
					tr
					.append($(
							'<td></td>')
					.html("<input type=text style='text-align:right; width:90px' class=form-control name=recQty"+itemList.poDetailId+""+itemList.itemId+" id=recQty"+itemList.poDetailId+""+itemList.itemId+" onchange='callMe(this.value,"+itemList.poDetailId+","+itemList.pendingQty+")' value="+itemList.receivedQty+" />"));
			/* 		var pendQty=0;
					if(itemList.receivedQty==0){
						
						pendQty=itemList.pendingQty;
					}else{ */
					var	pendQty=itemList.pendingQty-itemList.receivedQty;
					//}
					
//tr.append($('<td></td>').html(itemList.itemQty));//textbox



tr.append($('<td></td>').html(pendQty));
					tr.append($('<td></td>').html(itemList.poNo));
					var status;
					if(itemList.status==0){
						status="Pending";
					}
					if(itemList.status==1){
						status="Partial";
					}
					if(itemList.status==3){
						status="Completed";
					}
					
				 	tr
					.append($(
							'<td class="col-md-1" style="text-align: center;"></td>')
							.html(status));
				 	
				 	//tr.append($('<td></td>').html(itemList.status));
					$('#table_grid2 tbody').append(tr);
				})
			});
		}
	</script>


<!-- 	<script>
		function myFunction() {
			var input, filter, table, tr, td, i;
			input = document.getElementById("myInput");
			filter = input.value.toUpperCase();
			table = document.getElementById("table1");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[1];
				if (td) {
					if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}
		
		function checkMe(checking){
			alert("check " +checking);
		}
	</script> -->
	<script type="text/javascript">
	function callMe(qty,poDId,pendingQty){
		//alert("pending qty " +pendingQty);
		//alert("Qty  " +qty);
		
		if(parseInt(qty)>parseInt(pendingQty)){

			alert("Received Qty can not be greater than Pending Qty");
			

		}else{
			
			getPoDetail(qty,poDId);


		}
		
	}
	
	
	</script>

	<script type="text/javascript">

		function updateMrnQty(qty,detailId,itemId,oldMrnQty,poPendingQty) {
			
			//alert("po pend " +poPendingQty);
			//alert("oldMrnQty " +oldMrnQty);
			
			var newQty=parseInt(oldMrnQty)+parseInt(poPendingQty);
			if(qty<=newQty){
			$.getJSON('${getMrnDetail}', {
				qty : qty,
				detailId : detailId,
				itemId : itemId,
				ajax : 'true',
			}, function(data) {
				$('#table_grid1 td').remove();
				$('#loader').hide();

				if (data == "") {
					alert("No records found !!");
				}
				var cnt=0;
				$.each(data, function(key, itemList) {
					//alert(itemList.itemCode)
					var tr = $('<tr></tr>');

					tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(key));
					tr.append($('<td class="col-md-2" style="text-align: center;" ></td>').html(itemList.itemCode));
					tr.append($('<td class="col-md-3" style="text-align: center;"></td>').html(itemList.itemName));
					tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(itemList.poQty));
					tr
					.append($(
							'<td class="col-md-1" style="text-align: center;"></td>')
					.html("<input type=text  style='text-align:right; width:90px'  class=form-control name=mrnRecQty"+itemList.mrnDetailId+" id=mrnRecQty"+itemList.mrnDetailId+" onchange='updateMrnQty(this.value,"+itemList.mrnDetailId+","+itemList.itemId+","+itemList.mrnQty+","+itemList.poPendingQty+")' value="+itemList.mrnQty+"  />"));
										
					var status;
					if(itemList.mrnDetailStatus==0){
						status="Pending";
					}
					if(itemList.mrnDetailStatus==1){
						status="Partial";
					}
					
					if(itemList.mrnDetailStatus==2){
						status="Completed";
					}
					
					tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(itemList.poNo));
					tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(status));
					
					tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html("<a href='${pageContext.request.contextPath}/deleteMrnDetail/"+itemList.mrnDetailId+"' title='Delete' class='action_btn'><i class='fa fa-trash-o'></i></a>"));
				  	
					$('#table_grid1 tbody').append(tr);
					//}//end of if received Qty >0
				//	modal.style.display = "none";

				})
			});
			}//end of if
			else{
				alert("Edited Qty Limit Exceeds");
				//$('#mrnRecQty'+detailId).value=oldMrnQty;
				document.getElementById("mrnRecQty"+detailId).value=oldMrnQty;
			}
		}
	</script>
<!-- 	<script type="text/javascript">
	function insertMrn(){
		
		alert("Insert Mrn ");
	}
	
	</script>
 -->

<!-- temp Submit from modal submit -->

<!-- temp Submit from modal submit -->

	<script type="text/javascript">

		function tempSubmit(mrnId) {
			//alert(mrnId);
			
			
				$.getJSON('${submitNewMrnItemOnEdit}', {
				mrnId : mrnId,
				ajax : 'true',
			}, function(data) {
				$('#table_grid1 td').remove();
				$('#loader').hide();
				var modal = document.getElementById('myModal');
				modal.style.display = "none";

				if (data == "") {
					alert("No records found !!");
				}
				var cnt=0;
				$.each(data, function(key, itemList) {
					//alert(itemList.itemCode)
					var tr = $('<tr></tr>');

					tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(key+1));
					tr.append($('<td class="col-md-2" style="text-align: center;" ></td>').html(itemList.itemCode));
					tr.append($('<td class="col-md-3" style="text-align: center;"></td>').html(itemList.itemName));
					tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(itemList.poQty));
					tr
					.append($(
							'<td class="col-md-1" style="text-align: center;"></td>')
					.html("<input type=text  style='text-align:right; width:90px'  class=form-control name=mrnRecQty"+itemList.mrnDetailId+" id=mrnRecQty"+itemList.mrnDetailId+" onchange='updateMrnQty(this.value,"+itemList.mrnDetailId+","+itemList.itemId+","+itemList.mrnQty+","+itemList.poPendingQty+")' value="+itemList.mrnQty+"  />"));
										
					var status;
					if(itemList.mrnDetailStatus==0){
						status="Pending";
					}
					if(itemList.mrnDetailStatus==1){
						status="Partial";
					}
					
					if(itemList.mrnDetailStatus==2){
						status="Completed";
					}
					
					tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(itemList.poNo));
					tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html(status));
					
					tr.append($('<td class="col-md-1" style="text-align: center;"></td>').html("<a href='${pageContext.request.contextPath}/deleteMrnDetail/"+itemList.mrnDetailId+"' title='Delete' class='action_btn'><i class='fa fa-trash-o'></i></a>"));
				  	
					$('#table_grid1 tbody').append(tr);
					//}//end of if received Qty >0
				//	modal.style.display = "none";

				})
			});
			
		}
	</script>



<!-- temp Submit from modal submit end -->




	<script type="text/javascript">
	function editMrn(){
			//alert("Hi ");
					$('#loader').show();
					//mrnId
					
					var mrn_id = $("#mrnId").val();

						var gate_entry_no = $("#gate_entry_no").val();

						var gate_entry_date = $("#gate_entry_date").val();

						var chalan_no = $("#chalan_no").val();

						var chalan_date = $("#chalan_date").val();

						var bill_no = $("#bill_no").val();

						var bill_date = $("#bill_date").val();

						var po_list = $("#po_list").val();
						
						//
						
						var lorry_date = $("#lorry_date").val();

						var lorry_no = $("#lorry_no").val();

						var lorry_remark = $("#lorry_remark").val();

						var transport = $("#transport").val();


							$.getJSON('${editMrnProcess}',{
								
								
								gate_entry_no : gate_entry_no,
								gate_entry_date : gate_entry_date,
								chalan_no : chalan_no,
								chalan_date : chalan_date,
								bill_no : bill_no,
								bill_date : bill_date,
								lorry_date : lorry_date,
								lorry_no : lorry_no,
								lorry_remark : lorry_remark,
								transport : transport,
								
								mrn_id : mrn_id,

								ajax : 'true',
							 },
							 function(data) {
								// $('#loader').hide();
								document.getElementById('loader').style = "display:none";
								//window.location.reload();
									window.open("${pageContext.request.contextPath}/getMrnHeaders","_self");
								 
			});
		//	alert("Hi End  ");
							

	}
	
	
	</script>

</body>
</html>

