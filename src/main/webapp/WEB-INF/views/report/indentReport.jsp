<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

	<c:url var="getIndentListReport" value="/getIndentListReport"></c:url>
	<c:url var="getIndentListExportToExcel"
		value="/getIndentListExportToExcel"></c:url>


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

						<i class="fa fa-file-o"></i>Indent Report

					</h1>
				</div>
			</div>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Indent Report
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}"></a> <a
									data-action="collapse" href="#"><i class="fa fa-chevron-up"></i></a>
							</div>

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

								<div class="col-md-2">Select Category*</div>
								<div class="col-md-3">
									<select class="form-control chosen" name="catIdList[]"
										id="catIdList" multiple="multiple">
										<option value="0">All Category</option>
										<c:forEach items="${catList}" var="catList">


											<option value="${catList.catId}">${catList.catDesc}</option>
										</c:forEach>
									</select>

								</div>
								<div class="col-md-1"></div>
								<div class="col-md-2">Indent Is Monthly</div>
								<div class="col-md-3">

									<select name="isMonthly" id="isMonthly"
										class="form-control chosen" tabindex="6" required>
										<option value="2">All</option>
										<option value="0">No</option>
										<option value="1">Yes</option>

									</select>

								</div>
							</div>
							<br> <br>
							<div class="box-content">


								<div class="col-md-2">Indent Is Development</div>
								<div class="col-md-3">

									<select name="isDev" id="isDev" class="form-control chosen"
										tabindex="6" required>

										<option value="2">All</option>
										<option value="0">No</option>
										<option value="1">Yes</option>

									</select>

								</div>

							</div>
							<br>
							<div class="form-group">
								<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-5">
									<input type="button" class="btn btn-primary" value="Search "
										onclick="search()">
								</div>






							</div>

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
											onkeyup="myFunction()" placeholder="Search  By No/Date/Category " /> <span
											class="input-group-addon"> <i class="fa fa-search"></i>
										</span>
									</div>
									<br />
							<div class="clearfix"></div>
							<div class="table-responsive" style="border: 0">
								<table class="table table-advance" id="table1">
									<thead>
										<tr class="bgpink">
											<th class="col-sm-1">Sr no.</th>
											<th class="col-md-1">Indent No</th>
											<th class="col-md-1">Indent Date</th>
											<th class="col-md-1">Indent Category</th>
											<th class="col-md-1">Monthly Indent</th>
											<th class="col-md-1">Is Dev</th>

											<th class="col-md-1">Indent Status</th>
											<th class="col-md-1">No Of Days</th>

										</tr>
									</thead>
									<tbody>

										<%-- <c:forEach items="${list}" var="list" varStatus="count">

											<c:choose>
												<c:when test="${list.indMStatus==0}">
													<c:set var="modType" value="Pending"></c:set>

												</c:when>
												<c:when test="${list.indMStatus==1}">
													<c:set var="modType" value="Enquiry"></c:set>

												</c:when>
												<c:when test="${list.indMStatus==2}">
													<c:set var="modType" value="Partial Pending"></c:set>

												</c:when>
												<c:when test="${list.indMStatus==3}">
													<c:set var="modType" value="Closed"></c:set>

												</c:when>

											</c:choose>
											<tr>
												<td class="col-md-1"><c:out value="${count.index+1}" /></td>


												<td class="col-md-1"><c:out value="${list.indMNo}" /></td>

												<td class="col-md-1"><c:out value="${list.indMDate}" /></td>
												<td class="col-md-1"><c:out value="${modType}" /></td>

												<td class="col-md-1"><c:choose>
														<c:when test="${list.indMStatus==3}">-</c:when>
														<c:otherwise>
															<c:out value="${list.noOfDays}" />
														</c:otherwise>
													</c:choose></td>






											</tr>
										</c:forEach>
 --%>
									</tbody>

								</table>
								<div class="form-group" id="range">



									<div class="col-sm-3  controls">
										<input type="button" id="expExcel" class="btn btn-primary"
											disabled="disabled" value="EXPORT TO Excel"
											onclick="exportToExcel();">
									</div>
								</div>
								<button class="btn btn-primary" value="PDF" id="PDFButton"
									disabled="disabled" onclick="genPdf()">PDF</button>



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

			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			var catIdList = $("#catIdList").val();
			var isMonthly = $("#isMonthly").val();
			var isDev = $("#isDev").val();

			$('#loader').show();

			$.getJSON('${getIndentListReport}',

			{

				fromDate : fromDate,
				toDate : toDate,
				catIdList : catIdList,
				isMonthly : isMonthly,
				isDev : isDev,

				ajax : 'true'

			}, function(data) {
				//alert(data);

				$('#table1 td').remove();
				$('#loader').hide();
				document.getElementById("expExcel").disabled = false;
				document.getElementById("PDFButton").disabled = false;

				if (data == "") {
					alert("No records found !!");
					document.getElementById("expExcel").disabled = true;
					document.getElementById("PDFButton").disabled = true;

				}

				$.each(data, function(key, itemList) {

					var tr = $('<tr></tr>');
					tr.append($('<td></td>').html(key + 1));
					tr.append($('<td></td>').html(itemList.indMNo));
					tr.append($('<td></td>').html(itemList.indMDate));
					tr.append($('<td></td>').html(itemList.catDesc));
					var modType2;
					if (itemList.indIsmonthly == 0) {
						modType2 = "No";
					} else if (itemList.indIsmonthly == 1) {
						modType2 = "Yes";
					}

					var modType3;
					if (itemList.indIsdev == 0) {
						modType3 = "No";
					} else if (itemList.indIsdev == 1) {
						modType3 = "Yes";
					}

					tr.append($('<td></td>').html(modType2));
					tr.append($('<td></td>').html(modType3));
					var modType;
					if (itemList.indMStatus == 0) {
						modType = "Pending";
					} else if (itemList.indMStatus == 1) {
						modType = "Partial Pending";
					} else if (itemList.indMStatus == 2) {
						modType = "Completed";
					}
					tr.append($('<td></td>').html(modType));
					if (itemList.indMStatus == 3) {
						tr.append($('<td></td>').html("-"));
					} else {
						tr.append($('<td></td>').html(itemList.noOfDays));
					}
					$('#table1 tbody').append(tr);
				})

			});
		}
	</script>

	<script type="text/javascript">
		function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
		}
	</script>

	<script type="text/javascript">
		function genPdf() {
			//alert("hiii");
			var fromDate = document.getElementById("fromDate").value;
			var toDate = document.getElementById("toDate").value;

			window.open('${pageContext.request.contextPath}/showIndentPdf/'
					+ fromDate + '/' + toDate);
			//document.getElementById("expExcel").disabled = true;

		}
		
		function myFunction() {
			  var input, filter, table, tr, td,td1, i;
			  input = document.getElementById("myInput");
			  filter = input.value.toUpperCase();
			  table = document.getElementById("table1");
			  tr = table.getElementsByTagName("tr");
			  for (i = 0; i < tr.length; i++) {
			    td1 = tr[i].getElementsByTagName("td")[1];
			    td4=tr[i].getElementsByTagName("td")[2];
			    	td3=tr[i].getElementsByTagName("td")[3];
			    if (td || td1) {
			       if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
			        tr[i].style.display = "";
			      } else if (td4.innerHTML.toUpperCase().indexOf(filter) > -1) {
			        tr[i].style.display = "";
			      }else if (td3.innerHTML.toUpperCase().indexOf(filter) > -1) {
			        tr[i].style.display = "";
			      } else {
			        tr[i].style.display = "none";
			      }
			    }       
			  }//end of for
			  
			 
			  
			}
	</script>
</body>
</html>