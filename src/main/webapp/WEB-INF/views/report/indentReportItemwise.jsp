<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body onload=getSubDeptList()>

	<c:url var="getIndentReportBetDate" value="/getIndentReportBetDate"></c:url>
	<c:url var="getAllCatByAjax" value="/getAllCatByAjax"></c:url>
	<c:url var="getAllTypeByAjax" value="/getAllTypeByAjax"></c:url>

	<c:url var="getAllDeptByAjax" value="/getAllDeptByAjax"></c:url>

	<c:url var="getSubDeptList" value="/getSubDeptList"></c:url>

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
			<!-- <div class="page-title">
				<div>
					<h1>

						<i class="fa fa-file-o"></i>Mrn Category Month Wise Report  

					</h1>
				</div>
			</div> -->
			<br>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Issue List Itemwise Report
							</h3>
							<div class="box-tool">
								<a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>
						<form id="submitPurchaseOrder"
							action="${pageContext.request.contextPath}/mrnMonthCategoryWieReport"
							method="get">
							<div class="box-content">


								<div class="form-group">

									<div class="col-md-2">From Date</div>
									<div class="col-md-3">
										<input id="fromDate" class="form-control date-picker"
											placeholder="From Date" value="${fromDate}" name="fromDate"
											type="text">


									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">To Date</div>
									<div class="col-md-3">
										<input id="toDate" class="form-control date-picker"
											placeholder="To Date" value="${toDate}" name="toDate"
											type="text">


									</div>


								</div>
								<br /> <br />


								<div class="form-group">
									<div class="col-md-2">Select Category*</div>
									<div class="col-md-3">
										<select class="form-control chosen" name="catIdList"
											id="catIdList" onchange="setCatOptions(this.value)"
											multiple="multiple">
											<option value="-1">Select All</option>
											<c:forEach items="${catList}" var="catList">


												<option value="${catList.catId}">${catList.catDesc}</option>
											</c:forEach>
										</select>

									</div>



									<div class="col-md-1"></div>

									<div class="col-md-2">Select Status</div>
									<div class="col-md-3">

										<select name="appStatus" id="appStatus"
											class="form-control chosen" tabindex="6" required>

											<option value="-1">All</option>
											<option value="0">Pending</option>
											<option value="1">Partial Pending</option>
											<option value="2">Closed</option>

										</select>

									</div>

								</div>
								<br> <br> <br> <br>
								<div class="form-group">

									<div class="col-md-2">Select Type*</div>
									<div class="col-md-3">
										<select class="form-control chosen" name="typeId" id="typeId"
											onchange="setTypeOptions(this.value)" multiple="multiple"
											required>
											<option value="-1">Select All</option>
											<c:forEach items="${typeList}" var="typeList">
												<c:choose>
													<c:when test="${typeList.typeId==typeId}">
														<option value="${typeList.typeId}" selected>${typeList.typeName}</option>
													</c:when>
													<c:otherwise>
														<option value="${typeList.typeId}">${typeList.typeName}</option>
													</c:otherwise>
												</c:choose>

											</c:forEach>
										</select>

									</div>



								</div>

								<br> <br>

								<div class="row">
									<div class="col-md-12" style="text-align: center">
										<input type="button" class="btn btn-primary" value="Search"
											onclick="search()"> <input type="button" value="PDF"
											class="btn btn-primary" onclick="genPdf()" />&nbsp; <input
											type="button" id="expExcel" class="btn btn-primary"
											value="EXPORT TO Excel" onclick="exportToExcel();">
									</div>
								</div>
								<br>


								<div align="center" id="loader" style="display: none">

									<span>
										<h4>
											<font color="#343690">Loading</font>
										</h4>
									</span> <span class="l-1"></span> <span class="l-2"></span> <span
										class="l-3"></span> <span class="l-4"></span> <span
										class="l-5"></span> <span class="l-6"></span>
								</div>
								<div class="col-md-9"></div>
								<label for="search" class="col-md-3" id="search"> <i
									class="fa fa-search" style="font-size: 20px"></i> <input
									type="text" id="myInput" onkeyup="myFunction()"
									placeholder="Search.." title="Type in a name">
								</label> <br /> <br />
								<div class="clearfix"></div>
								<div
									style="overflow: scroll; height: 100%; width: 100%; overflow: auto"
									id="tbl">
									<table width="100%" border="0"
										class="table table-bordered table-striped fill-head "
										style="width: 100%" id="table1">
										<thead>


											<tr class="bgpink">
												<th style="width: 1%;">SR</th>

												<th class="col-md-1">Indent No</th>
												<th class="col-md-2">Date</th>
												<th class="col-md-2">Item Description</th>
												<th class="col-md-2">Indent Qty</th>

												<th class="col-md-1">Excess Days</th>
												<th class="col-md-1">Remark</th>



											</tr>
										</thead>
										<tbody>





										</tbody>

									</table>

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
		src="https://www.gstatic.com/charts/loader.js"></script>

	<script type="text/javascript">
		 
		function getDetailReport(id, catDesc) {
			var typeName = $("#typeId option:selected").text();
			var isDevName = $("#isDev option:selected").text();
			var deptName = $("#deptId option:selected").text();
			var subDeptName = $("#subDeptId option:selected").text();
			location.href = '${pageContext.request.contextPath}/mrnMonthItemWiseReportBycatId/'
					+ id
					+ '/'
					+ typeName
					+ '/'
					+ isDevName
					+ '/'
					+ deptName
					+ '/' + subDeptName + '' + catDesc;

		}
		function exportToExcel() {
			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
		}
	 
		function search() {

			 alert("hii");

			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			var typeIdList = $("#typeId").val();
			var catIdList = $("#catIdList").val();
			 
		 
			var appStatus = $("#appStatus").val();
			
		/* 	alert(fromDate);
			alert(toDate);
			alert(typeIdList);
			alert(catIdList);
			alert(appStatus); */

			if (fromDate == "" || fromDate == null)
				alert("Select From Date");
			else if (toDate == "" || toDate == null)
				alert("Select To Date");

			$('#loader').show();

			$.getJSON('${getIndentReportBetDate}',

			{

				fromDate : fromDate,
				toDate : toDate,
				catIdList : JSON.stringify(catIdList),
				typeIdList : JSON.stringify(typeIdList),
			 
				appStatus : appStatus,
				ajax : 'true'

			}, function(data) {

				$('#table1 td').remove();
				$('#loader').hide();

				if (data == "") {
					alert("No records found !!");

				}

				$.each(data, function(key, itemList) {

					var tr = $('<tr></tr>');
					tr.append($('<td></td>').html(key + 1));
					tr.append($('<td></td>').html(itemList.indMNo));
					tr.append($('<td></td>').html(itemList.indItemSchddt));
					tr.append($('<td></td>').html(itemList.itemDesc));
					tr.append($('<td></td>').html(itemList.indQty));
					tr.append($('<td></td>').html(itemList.excessDays));
					tr.append($('<td></td>').html(itemList.indReamrk));
					 
					$('#table1 tbody').append(tr);
				})

			});
		}
	</script>

	<script>
		function myFunction() {
			var input, filter, table, tr, td, td1, i;
			input = document.getElementById("myInput");
			filter = input.value.toUpperCase();
			table = document.getElementById("table_grid");
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

	<script type="text/javascript">
		function setCatOptions(catId) {

			if (catId == -1) {
				$.getJSON('${getAllCatByAjax}', {
					ajax : 'true'
				}, function(data) {
					var len = data.length;

					$('#catIdList').find('option').remove().end()

					$("#catIdList").append(
							$("<option ></option>").attr("value", -1).text(
									"Select All"));

					for (var i = 0; i < len; i++) {

						$("#catIdList").append(
								$("<option selected></option>").attr("value",
										data[i].catId).text(data[i].catDesc));
					}

					$("#catIdList").trigger("chosen:updated");
				});
			}
		}
	</script>

	<script type="text/javascript">
		function setTypeOptions(catId) {

			//alert(catId);
			if (catId == -1) {
				$.getJSON('${getAllTypeByAjax}', {
					ajax : 'true'
				},
						function(data) {
							var len = data.length;

							$('#typeId').find('option').remove().end()

							$("#typeId").append(
									$("<option ></option>").attr("value", -1)
											.text("Select All"));

							for (var i = 0; i < len; i++) {

								$("#typeId").append(
										$("<option selected></option>").attr(
												"value", data[i].typeId).text(
												data[i].typeName));
							}

							$("#typeId").trigger("chosen:updated");
						});
			}
		}
	</script>

	<script type="text/javascript">
		function genPdf() {

			 
			window
					.open('${pageContext.request.contextPath}/showIssueListReportPdf/'
							 );
			document.getElementById("PDFButton").disabled = true;
		}
	</script>

	<!-- <script type="text/javascript">
		function setDeptOptions(catId) {

			//alert(catId);
			if (catId == -1) {
				$.getJSON('${getAllDeptByAjax}', {
					ajax : 'true'
				},
						function(data) {
							var len = data.length;

							$('#deptId').find('option').remove().end()

							$("#deptId").append(
									$("<option ></option>").attr("value", -1)
											.text("Select All"));

							for (var i = 0; i < len; i++) {

								$("#deptId").append(
										$("<option selected></option>").attr(
												"value", data[i].deptId).text(
												data[i].deptDesc));
							}

							$("#deptId").trigger("chosen:updated");
						});
			}
			getSubDeptList();
		}
	</script>

	<script type="text/javascript">
		function setSubDeptOptions(catId) {

			//alert(catId);
			if (catId == -1) {
				$.getJSON('${getSubDeptList}', {
					ajax : 'true'
				},
						function(data) {
							var len = data.length;

							$('#subDeptId').find('option').remove().end()

							$("#subDeptId").append(
									$("<option ></option>").attr("value", -1)
											.text("Select All"));

							for (var i = 0; i < len; i++) {

								$("#subDeptId").append(
										$("<option selected></option>").attr(
												"value", data[i].subDeptId).text(
												data[i].subDeptDesc));
							}

							$("#subDeptId").trigger("chosen:updated");
						});
			}
		}
	</script>
 -->

	<script>
		function myFunction() {
			var input, filter, table, tr, td, td1, td2, i;
			input = document.getElementById("myInput");
			filter = input.value.toUpperCase();
			table = document.getElementById("table1");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[1];
				td1 = tr[i].getElementsByTagName("td")[2];

				td2 = tr[i].getElementsByTagName("td")[3];
				if (td || td1 || td2) {
					if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else if (td2.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}

				 
			}
		}
	</script>


</body>
</html>