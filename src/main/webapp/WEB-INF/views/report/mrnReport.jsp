<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body onload="getSubDeptList()">

	<c:url var="getMrnReportCatwise" value="/getMrnReportCatwise"></c:url>



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

						<i class="fa fa-file-o"></i>MRN Categorywise Report

					</h1>
				</div>
			</div>
			<br>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>MRN Categorywise Report
							</h3>
							<div class="box-tool">
								<a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>

						<div class="box-content">




							<div class="box-content">

								<div class="col-md-1"></div>

								<div class="col-md-2">Select Category*</div>
								<div class="col-md-3">
									<select class="form-control chosen" name="catIdList"
										id="catIdList" multiple="multiple">
										<option value="0">All Category</option>
										<c:forEach items="${catList}" var="catList">


											<option value="${catList.catId}">${catList.catDesc}</option>
										</c:forEach>
									</select>

								</div>
							</div>
							<br> <br> <br>

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
									class="l-3"></span> <span class="l-4"></span> <span class="l-5"></span>
								<span class="l-6"></span>
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

											<th class="col-md-1">Category</th>
											<th class="col-md-1">Item Code</th>
											<th class="col-md-3">Item Desc</th>
											<th class="col-md-1">MRN No</th>
											<th class="col-md-1">Date</th>
											<th class="col-md-1">Qty</th>
											<th class="col-md-1">Remaining Qty</th>
											<th class="col-md-1">Rate</th>
											<th class="col-md-1">Value</th>
											<th class="col-md-1">MRN Days</th>

										</tr>
									</thead>
									<tbody>





									</tbody>

								</table>

							</div>

						</div>



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
		function exportToExcel() {
			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
		}

		function search() {

			//	alert("hii");

			var catIdList = $("#catIdList").val();

			//	alert(catIdList);

			$('#loader').show();

			$
					.getJSON(
							'${getMrnReportCatwise}',

							{

								catIdList : JSON.stringify(catIdList),

								ajax : 'true'

							},
							function(data) {

								//alert(data);

								$('#table1 td').remove();
								$('#loader').hide();

								if (data == "") {
									alert("No records found !!");

								}

								$
										.each(
												data,
												function(key, itemList) {

													//alert(itemList.catDesc);

													var tr = $('<tr></tr>');
													tr.append($('<td></td>')
															.html(key + 1));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.catDesc));

													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.itemCode));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.itemDesc));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.mrnNo));

													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.mrnDate));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.mrnQty));

													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.remainingQty));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.itemRate));

													var value = (parseFloat(itemList.mrnQty) * parseFloat(itemList.itemRate));

													tr
															.append($(
																	'<td></td>')
																	.html(
																			value
																					.toFixed(2)));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			itemList.days));

													$('#table1 tbody').append(
															tr);
												})

							});
		}
	</script>

	<script type="text/javascript">
		function genPdf() {

			/* 	var fromDate = document.getElementById("fromDate").value;
				var toDate = document.getElementById("toDate").value;

				window
						.open('${pageContext.request.contextPath}/showEnqAgQuotReportPdf/'
								+ fromDate + '/' + toDate); */

			window
					.open('${pageContext.request.contextPath}/showMRNCatwiseReportPdf/');
			document.getElementById("PDFButton").disabled = true;
		}
	</script>

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

				/*  if (td1) {
				     if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
				       tr[i].style.display = "";
				     } else {
				       tr[i].style.display = "none";
				     }
				   }   */
			}
		}
	</script>


</body>
</html>