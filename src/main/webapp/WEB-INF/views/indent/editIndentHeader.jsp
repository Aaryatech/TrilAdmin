<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tableSearch.css">
<body onload="checkIndentDept()">
	<%-- <jsp:include page="/WEB-INF/views/include/logout.jsp"></jsp:include> --%>

	<c:url var="getSubDeptListByDeptId" value="/getSubDeptListByDeptId" />
	<c:url var="getgroupListByCatId" value="/getgroupListByCatId" />

	<c:url var="getIndentDetail" value="/getIndentDetail" />

	<c:url var="itemListByGroupId" value="/itemListByGroupId" />

	<c:url var="updateIndDetail" value="/updateIndDetail" />

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
						<i class="fa fa-file-o"></i>Edit Indent
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
								<i class="fa fa-bars"></i>Edit Indent
							</h3>
							<div class="box-tool">
								<!-- <a href="">Back to List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a> -->
							</div>

						</div>


						<div class="box-content">
							<form
								action="${pageContext.request.contextPath}/editIndentProcess"
								method="post" class="form-horizontal" id="validation-form">

								<div class="form-group">

									<label class="col-sm-3 col-lg-2 control-label">Indent
										Category </label>
									<div class="col-sm-6 col-lg-4 controls">
										<c:out value="${indent.catDesc}"></c:out>
										<input type="hidden" value="${indent.indMId}" name="indentId">
									</div>
									<label class="col-sm-3 col-lg-2 control-label">Indent
										No.</label>
									<div class="col-sm-6 col-lg-4 controls">
										<c:out value="${indent.indMNo}"></c:out>

									</div>
								</div>
								<c:set var="indmtype" value="o"></c:set>
								<c:choose>
									<c:when test="${indent.indMType==1}">
										<c:set var="indmtype" value="Regular"></c:set>
									</c:when>
									<c:when test="${indent.indMType==2}">
										<c:set var="indmtype" value="Job Work"></c:set>
									</c:when>
									<c:when test="${indent.indMType==3}">
										<c:set var="indmtype" value="General"></c:set>
									</c:when>
									<c:otherwise>
										<c:set var="indmtype" value="Other"></c:set>
									</c:otherwise>
								</c:choose>
								<div class="box-content">
									<label class="col-sm-3 col-lg-2 control-label">Indent
										Type</label>
									<div class="col-sm-6 col-lg-4 controls">
										<c:out value="${indmtype}"></c:out>

									</div>

									<label class="col-sm-3 col-lg-2 control-label">Date</label>
									<div class="col-sm-6 col-lg-4 controls">
										<input class="form-control date-picker" id="indent_date"
											size="16" type="text" disabled="disabled" name="indent_date"
											value="${indent.indMDate}" required />
									</div>
								</div>
								<br />
								<div class="box-content">
									<label class="col-sm-3 col-lg-2 control-label">Account
										Head</label>
									<div class="col-sm-6 col-lg-4 controls">
										<select name="acc_head" id="acc_head"
											class="form-control chosen" placeholder="Account Head"
											data-rule-required="true">
											<c:forEach items="${accountHeadList}" var="accHead"
												varStatus="count">
												<c:choose>
													<c:when test="${indent.achdId==accHead.accHeadId}">
														<option selected value="${accHead.accHeadId}"><c:out value="${accHead.accHeadDesc}"/></option>
													</c:when>
													<c:otherwise>
														<option value="${accHead.accHeadId}"><c:out value="${accHead.accHeadDesc}"/></option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>

									<label class="col-sm-3 col-lg-2 control-label">Machine
										Specific</label>

									<div class="col-sm-6 col-lg-4 controls">
										<select name="machine_specific" id="machine_specific"
											onchange="showDept()" class="form-control chosen"
											placeholder="Is Machine Specific" data-rule-required="true">
											<c:choose>

												<c:when test="${indent.deptId==0}">
													<option selected value="0">No</option>
													<option value="1">Yes</option>
												</c:when>
												<c:otherwise>
													<option value="0">No</option>
													<option selected value="1">Yes</option>
												</c:otherwise>
											</c:choose>


										</select>
									</div>
								</div>
								<br />
								<div class="box-content" style="display: none" id="deptDiv">
									<label class="col-sm-3 col-lg-2 control-label">Department
									</label>

									<div class="col-sm-6 col-lg-4 controls">
										<select name="dept" id="dept" class="form-control chosen"
											placeholder="Department">
											<c:forEach items="${deparmentList}" var="dept"
												varStatus="count">
												<c:choose>
													<c:when test="${indent.deptId==dept.deptId}">
														<option selected value="${dept.deptId}"><c:out value="${dept.deptDesc}"/></option>
													</c:when>
													<c:otherwise>
														<option value="${dept.deptId}"><c:out value="${dept.deptDesc}"/></option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>

									<label class="col-sm-3 col-lg-2 control-label">Sub
										Department </label>
									<div class="col-sm-6 col-lg-4 controls">
										<select name="sub_dept" id="sub_dept"
											class="form-control chosen" placeholder="Sub Department">

										</select>
									</div>

								</div>
								<br />
								<div class="box-content">
									<label class="col-sm-3 col-lg-2 control-label">Is Dev </label>

									<div class="col-sm-6 col-lg-4 controls">
										<select name="is_dev" id="is_dev" class="form-control"
											placeholder="Is Dev" data-rule-required="true">

											<c:choose>
												<c:when test="${indent.indIsdev==1}">
													<option selected value="1">Yes</option>
													<option value="0">No</option>

												</c:when>
												<c:otherwise>
													<option selected value="0">No</option>
													<option value="1">Yes</option>

												</c:otherwise>
											</c:choose>
										</select>
									</div>

									<label class="col-sm-3 col-lg-2 control-label">Is
										Monthly </label>
									<div class="col-sm-6 col-lg-4 controls">
										<select name="is_monthly" id="is_monthly" class="form-control"
											placeholder="Is Monthly" data-rule-required="true">

											<c:choose>
												<c:when test="${indent.indIsmonthly==1}">
													<option selected value="1">Yes</option>
													<option value="0">No</option>

												</c:when>
												<c:otherwise>
													<option value="1">Yes</option>

													<option selected value="0">No</option>

												</c:otherwise>
											</c:choose>


										</select>
									</div>
								</div>


								<div class="clearfix"></div>
								<div id="table-scroll" class="table-scroll">

									<div class="table-wrap">

										<table id="table1" class="table table-advance">
											<thead>
												<tr class="bgpink">
													<th style="text-align: center;" class="col-md-1">Sr No</th>
													<th style="text-align: center;" class="col-md-1">Item</th>
													<th style="text-align: center;" class="col-md-2">Indent
														Quantity</th>
													<th style="text-align: center;" class="col-md-1">Schedule
														Days</th>
													<th style="text-align: center;" class="col-md-1">Schedule
														Date</th>
													<th style="text-align: center;" class="col-md-1">Remark</th>
													<th style="text-align: center;" class="col-md-1">Action</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${indDetailList}" var="indDetail"
													varStatus="count">

													<tr>
														<td style="text-align: center;" class="col-md-1"><c:out
																value="${count.index+1}" /></td>

														<td style="text-align: center;" class="col-md-2"><c:out
																value="${indDetail.indItemDesc}" /></td>

														<td style="text-align: center;" class="col-md-1"><input
															type="number" class="form-control"
															value="${indDetail.indQty}" min="1" readonly
															onchange="getValue(this.value,${indDetail.indDId},${indent.indMId})"
															id="indQty${indDetail.indDId}"
															name="indQty${indDetail.indDId}"></td>
														<td style="text-align: center;" class="col-md-2"><c:out
																value="${indDetail.indItemSchd}" /></td>

														<td style="text-align: center;" class="col-md-2"><c:out
																value="${indDetail.indItemSchddt}" /></td>

														<td style="text-align: center;" class="col-md-2"><c:out
																value="${indDetail.indRemark}" /></td>

														<td style="text-align: center;" class="col-md-1">
															<%-- <input
															type="button" value="update"
															onclick="updateCall(${indDetail.indDId},${indent.indMId})"> --%>

															<a href="#" class="action_btn" title="Update"
															onclick="updateCall(${indDetail.indDId},${indent.indMId})"><i
																class="fa fa-edit"></i></a>

														</td>

													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>

								<div class="row">
									<div class="col-md-12" style="text-align: center">

										<input type="submit" class="btn btn-info" value="Edit Indent">

									</div>
								</div>

							</form>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END Main Content -->
	<footer>
		<p>2018© Trimbak Rubber</p>
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
		function insertIndent() {
			//alert("inside Indent Insetr");
			var form = document.getElementById("validation-form");
			form.action = "${pageContext.request.contextPath}/saveIndent";
			form.submit();

		}
	</script>
	<script type="text/javascript">
		function showDept() {
			var mac_spec = document.getElementById("machine_specific").value;
			//alert("Machine Specific "+mac_spec);
			if (mac_spec == 1) {
				document.getElementById('deptDiv').style.display = "block";
			}
			if (mac_spec == 0) {
				document.getElementById('deptDiv').style.display = "none";
			}
		}
	</script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {

							$('#dept')
									.change(
											function() {

												$
														.getJSON(
																'${getSubDeptListByDeptId}',
																{
																	deptId : $(
																			this)
																			.val(),
																	ajax : 'true'
																},
																function(data) {

																	var len = data.length;

																	$(
																			'#sub_dept')
																			.find(
																					'option')
																			.remove()
																			.end()
																	// $("#items").append($("<option></option>").attr( "value",-1).text("ALL"));
																	for (var i = 0; i < len; i++) {

																		$(
																				"#sub_dept")
																				.append(
																						$(
																								"<option></option>")
																								.attr(
																										"value",
																										data[i].subDeptId)
																								.text(
																										data[i].subDeptDesc));
																	}

																	$(
																			"#sub_dept")
																			.trigger(
																					"chosen:updated");
																});
											});
						});
	</script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {

							$('#group')
									.change(
											function() {

												$
														.getJSON(
																'${itemListByGroupId}',
																{
																	grpId : $(
																			this)
																			.val(),
																	ajax : 'true'
																},
																function(data) {

																	var len = data.length;

																	$(
																			'#item_name')
																			.find(
																					'option')
																			.remove()
																			.end()
																	// $("#items").append($("<option></option>").attr( "value",-1).text("ALL"));
																	for (var i = 0; i < len; i++) {

																		$(
																				"#item_name")
																				.append(
																						$(
																								"<option></option>")
																								.attr(
																										"value",
																										data[i].itemId)
																								.text(
																										data[i].itemDesc));
																	}

																	$(
																			"#item_name")
																			.trigger(
																					"chosen:updated");
																});
											});
						});
	</script>



	<script type="text/javascript">
		$(document)
				.ready(
						function() {

							$('#ind_cat')
									.change(
											function() {

												$
														.getJSON(
																'${getgroupListByCatId}',
																{
																	catId : $(
																			this)
																			.val(),
																	ajax : 'true'
																},
																function(data) {

																	var len = data.length;

																	$('#group')
																			.find(
																					'option')
																			.remove()
																			.end()
																	// $("#items").append($("<option></option>").attr( "value",-1).text("ALL"));
																	for (var i = 0; i < len; i++) {

																		$(
																				"#group")
																				.append(
																						$(
																								"<option></option>")
																								.attr(
																										"value",
																										data[i].grpId)
																								.text(
																										data[i].grpDesc));
																	}

																	$("#group")
																			.trigger(
																					"chosen:updated");
																});
											});
						});
	</script>

	<script type="text/javascript">
		function insertIndentDetail() {
			//alert
			var itemId = $('#item_name').val();
			var qty = $('#quantity').val();
			var remark = $('#remark').val();
			var schDay = $('#sch_days').val();
			var itemName = $("#item_name option:selected").html();

			var indentDate = $('#indent_date').val();
			$.getJSON('${getIndentDetail}', {
				itemId : itemId,
				qty : qty,
				remark : remark,
				itemName : itemName,
				schDay : schDay,
				indentDate : indentDate,
				ajax : 'true',

			}, function(data) {
				//alert(data);
				var len = data.length;
				$('#table1 td').remove();
				$.each(data, function(key, trans) {
					var tr = $('<tr></tr>');
					tr.append($('<td></td>').html(key + 1));
					tr.append($('<td></td>').html(trans.itemCode));
					tr.append($('<td></td>').html(trans.itemName));
					tr.append($('<td></td>').html(trans.qty));
					tr.append($('<td></td>').html(trans.uom));
					tr.append($('<td></td>').html(trans.schDays));
					tr.append($('<td></td>').html(trans.date));
					tr.append($('<td></td>').html(trans.curStock));

					$('#table1 tbody').append(tr);
				})
			});
		}
	</script>
	<script>
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
	</script>
	<script>
	function updateCall(indDId,indMId) {
		document.getElementById("indQty"+indDId).removeAttribute("readonly");
		//var qty = $('#indQty'+indDId).val();
		//alert(qty);
		//window.open('${pageContext.request.contextPath}/updateIndDetail/'+indDId+'/'+indMId+'/'+qty,"_self");
		
	}
	
	
	function getValue(qty,indDId,indMId)
	{
		//window.open('${pageContext.request.contextPath}/updateIndDetail/'+indDId+'/'+indMId+'/'+qty,"_self");
		
		/* var itemId = $('#item_name').val();
		var qty = $('#quantity').val();
		var remark = $('#remark').val();
		var schDay = $('#sch_days').val();
		var itemName = $("#item_name option:selected").html();
 */
		//var indentDate = $('#indent_date').val();
 //document.getElementById("indQty"+indDId).setAttribute("readonly");
		$.getJSON('${updateIndDetail}', {
	
		qty : qty,
		indDId : indDId,
		indMId : indMId,
			ajax : 'true',

		}, function(data) {
			//alert("dif " +indDId);
			//document.getElementById("indQty"+indDId).setAttribute("readonly");
			 //$('#indQty'+indDId).setAttribute("readonly");
		//	alert(data);
			//alert("In update call")
			//var len = data.length;
			//$('#table1 td').remove();
			

			//$.each(data, function(key, trans) {

			//	var tr = $('<tr></tr>');
				//tr.append($('<td></td>').html(key + 1));
				/* tr.append($('<td></td>').html(trans.indItemDesc));
				tr.append($('<td></td>').html(trans.indQty));
				tr.append($('<td></td>').html(trans.indItemSchd));
				tr.append($('<td></td>').html(trans.indItemSchddt));
				tr.append($('<td></td>').html(trans.indRemark));
				tr.append($('<td></td>').html(trans.indMDate));
				 */
				//$('#table1 tbody').append(tr);
			//})
		});
	}
	</script>
	<script type="text/javascript">
	function checkIndentDept(){
	var dept=${isDept};
	if(dept==0){
		//alert("Dept is 0");
	}else{
		
		document.getElementById('deptDiv').style.display = "block";
		$
		.getJSON(
				'${getSubDeptListByDeptId}',
				{
					deptId : dept,
					ajax : 'true'
				},
				function(data) {

					var len = data.length;

					$(
							'#sub_dept')
							.find(
									'option')
							.remove()
							.end()
					// $("#items").append($("<option></option>").attr( "value",-1).text("ALL"));
					for (var i = 0; i < len; i++) {

						$(
								"#sub_dept")
								.append(
										$(
												"<option></option>")
												.attr(
														"value",
														data[i].subDeptId)
												.text(
														data[i].subDeptDesc));
					}

					$(
							"#sub_dept")
							.trigger(
									"chosen:updated");
				});
		
	}//end of else
	}
	
	</script>


</body>
</html>

