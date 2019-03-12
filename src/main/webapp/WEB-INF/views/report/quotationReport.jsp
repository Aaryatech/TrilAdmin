<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

	<c:url var="getQuotReportBetDAte" value="/getQuotReportBetDAte"></c:url>


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

						<i class="fa fa-file-o"></i>Quotation Report
					</h1>
				</div>
			</div>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Quotation Report
							</h3>
							<div class="box-tool"></div>

						</div>

						<div class="box-content">

							<div class="box-content">

								<div class="col-md-2">From Date*</div>
								<div class="col-md-3">
									<input id="fromDate" class="form-control date-picker"
										placeholder="From Date" value="${fromDate}" name="fromDate"
										type="text" required>


								</div>
								<div class="col-md-1"></div>
								<div class="col-md-2">To Date*</div>
								<div class="col-md-3">
									<input id="toDate" class="form-control date-picker"
										placeholder="To Date" value="${toDate}" name="toDate"
										type="text" required>


								</div>


							</div>
							<br> <br>

							<div class="box-content">



								<div class="col-md-2">Select Status</div>
								<div class="col-md-3">

									<select name="status" id="status" class="form-control chosen"
										tabindex="6">
										<option value="-1">All</option>
										<option value="0">Pending</option>
									</select>

								</div>
							</div>

							<br>
							<div class="form-group">
								<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-5">
									<input type="button" class="btn btn-primary" value="Search "
										onclick="search()">

									<button class="btn btn-primary" value="PDF" id="PDFButton"
										disabled="disabled" onclick="genPdf()">PDF</button>

									<input type="button" id="expExcel" class="btn btn-primary"
										disabled="disabled" value="EXPORT TO Excel"
										onclick="exportToExcel();">
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

							<br /> <br />

							<div class="col-md-8"></div>

							<div class="input-group">
								<input type="text" id="myInput"
									style="text-align: left; color: green;" class="form-control"
									onkeyup="myFunction()" placeholder="Search " /> <span
									class="input-group-addon"> <i class="fa fa-search"></i>
								</span>
							</div>
							<br />
							<div class="clearfix"></div>
							<div class="table-responsive" style="border: 0">
								<table class="table table-advance" id="table1">
									<thead>
										<tr class="bgpink">
											<th style="width: 3%;">Sr no.</th>
											<th class="col-md-1">Enq No</th>
											<th class="col-md-1">Enq Date</th>
											<th class="col-md-1">Item Code</th>
											<th class="col-md-3">Item Desc</th>
											<th class="col-md-1">Enq Qty</th>
											<th class="col-md-2">Enq Remark</th>
											<th class="col-md-1">Header Remark</th>

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
				<p>2018 © AARYATECH SOLUTIONS</p>
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


	<script type="text/javascript">
		function search() {

			//alert("His");

			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			var status = $("#status").val();
			/* 	alert(fromDate);
				alert(toDate);
				alert(status); */

			$('#loader').show();

			$.getJSON('${getQuotReportBetDAte}',

			{

				fromDate : fromDate,
				toDate : toDate,
				status : status,
				ajax : 'true'

			}, function(data) {

				$('#table1 td').remove();
				$('#loader').hide();

				if (data == "") {
					alert("No records found !!");
					document.getElementById("PDFButton").disabled = true;
					document.getElementById("expExcel").disabled = true;

				}
				document.getElementById("PDFButton").disabled = false;
				document.getElementById("expExcel").disabled = false;

				$.each(data, function(key, v) {

					var tr = $('<tr></tr>');

					tr.append($('<td></td>').html(key + 1));
					tr.append($('<td></td>').html(v.enqNo));
					tr.append($('<td></td>').html(v.enqDate));
					tr.append($('<td></td>').html(v.itemCode));
					tr.append($('<td></td>').html(v.itemDesc));

					tr.append($('<td></td>').html(v.enqQty));

					tr.append($('<td></td>').html(v.enqRemark));
					tr.append($('<td></td>').html(v.headerRemark));

					$('#table1 tbody').append(tr);

				})

			});
		}
	</script>

	<script type="text/javascript">
		function genPdf() {

			var fromDate = document.getElementById("fromDate").value;
			var toDate = document.getElementById("toDate").value;

			window
					.open('${pageContext.request.contextPath}/showQuotationReportPdf/'
							+ fromDate + '/' + toDate);
			document.getElementById("PDFButton").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function exportToExcel() {
			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
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