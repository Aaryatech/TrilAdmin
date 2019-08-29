<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Dashboard - Admin</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

<!--base css styles-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/font-awesome/css/font-awesome.min.css">

<!--page specific css styles-->

<!--flaty css styles-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/flaty.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/flaty-responsive.css">

<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/img/favicon.png">
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/tlogodesigned.jpg"
	type="image/x-icon">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.buttonload {
	background-color: #fdf3e5; /* Green background */
	border: none; /* Remove borders */
	color: #ec268f; /* White text */
	padding: 12px 20px; /* Some padding */
	font-size: 15px; /* Set a font-size */
	display: none;
}
</style>
<style type="text/css">
@import url('https://fonts.googleapis.com/css?family=Roboto');

body {
	font-family: 'Roboto', sans-serif;
}

h2 {
	margin: 0px;
	text-transform: uppercase;
}

h6 {
	margin: 0px;
	color: #777;
}

.wrapper {
	/*  text-align:center;
  margin:50px auto; */
	
}

.tabs1 {
	/*  margin-top:50px; */
	font-size: 13px;
	padding: 0px;
	list-style: none;
	background: #fff;
	box-shadow: 0px 5px 20px rgba(0, 0, 0, 0.1);
	display: inline-block;
	border-radius: 50px;
	position: relative;
}

.tabs1 a {
	text-decoration: none;
	color: #777;
	text-transform: uppercase;
	padding: 10px 20px;
	display: inline-block;
	position: relative;
	z-index: 1;
	transition-duration: 0.6s;
}

.tabs1 a.active {
	color: #fff;
}

.tabs1 a i {
	margin-right: 5px;
}

.tabs1 .selector1 {
	height: 100%;
	display: inline-block;
	position: absolute;
	left: 0px;
	top: 0px;
	z-index: 1;
	border-radius: 50px;
	transition-duration: 0.6s;
	transition-timing-function: cubic-bezier(0.68, -0.55, 0.265, 1.55);
	background: #05abe0;
	background: -moz-linear-gradient(45deg, #05abe0 0%, #8200f4 100%);
	background: -webkit-linear-gradient(45deg, #05abe0 0%, #8200f4 100%);
	background: linear-gradient(45deg, #058fe0 0%, #8c8537 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#05abe0',
		endColorstr='#8200f4', GradientType=1);
}
</style>
<style type="text/css">
.tabs2 {
	/*  margin-top:50px; */
	font-size: 12px;
	padding: 0px;
	list-style: none;
	background: #fff;
	box-shadow: 0px 5px 20px rgba(0, 0, 0, 0.1);
	display: inline-block;
	border-radius: 50px;
	position: relative;
}

.tabs2 a {
	text-decoration: none;
	color: #777;
	text-transform: uppercase;
	padding: 10px 20px;
	display: inline-block;
	position: relative;
	z-index: 1;
	transition-duration: 0.6s;
}

.tabs2 a.active {
	color: #fff;
}

.tabs2 a i {
	margin-right: 5px;
}

.tabs2 .selector2 {
	height: 100%;
	display: inline-block;
	position: absolute;
	left: 0px;
	top: 0px;
	z-index: 1;
	border-radius: 50px;
	transition-duration: 0.6s;
	transition-timing-function: cubic-bezier(0.68, -0.55, 0.265, 1.55);
	background: #05abe0;
	background: -moz-linear-gradient(45deg, #05abe0 0%, #8200f4 100%);
	background: -webkit-linear-gradient(45deg, #05abe0 0%, #8200f4 100%);
	background: linear-gradient(45deg, #050ce0 0%, #f45a00 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#05abe0',
		endColorstr='#8200f4', GradientType=1);
}
</style>
</head>
<body>

	<c:url var="mrnIssueYearlyReport" value="/mrnIssueYearlyReport"></c:url>

	<!-- BEGIN Container -->
	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>


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
					<h5>
						<i class="fa fa-file-o"></i> Dashboard
					</h5>
					
				</div>
			</div>  -->
			<!-- END Page Title -->

			<!-- BEGIN Breadcrumb -->
			<!-- <div id="breadcrumbs">
				<ul class="breadcrumb">
					<li class="active"><i class="fa fa-home"></i> Home</li>
				</ul>
			</div> -->
			<!-- END Breadcrumb -->
			<div class="container">

				<div id="poPending">
					<br>
					<div class="col-md-1"></div>

					<br>
					<div id="tabBody0">
						<div class="row">

							<div class="col-md-12">
								<div class="box" id="todayslist">

									<div class="box-title">
										<h3>
											<i class="fa fa-table"></i>Pending PO
										</h3>
										<div class="box-tool">
											<a href="${pageContext.request.contextPath}/addItem"> </a> <a
												data-action="collapse" href="#"><i
												class="fa fa-chevron-up"></i></a>
										</div>

									</div>

									<form id="submitBillForm"
										action="${pageContext.request.contextPath}/getMrnIssueYearlyReport"
										method="get">

										<div class="box-content">


											<div class="box-content">

												<div class="col-md-1">Year</div>
												<div class="col-md-2">
													<select class="form-control chosen" name="fromToYear"
														id="fromToYear" required>


														<option value="2018"
															${param.fromToYear == '2018' ? 'selected':''}>2018-2019</option>
														<option value="2019"
															${param.fromToYear == '2019' ? 'selected':''}>2019-2020</option>
														<option value="2020"
															${param.fromToYear == '2020' ? 'selected':''}>2020-2021</option>

													</select>
												</div>


												<div class="col-md-2" align="right">Select Category</div>
												<div class="col-md-3">
													<select class="form-control chosen" name="catId" id="catId">
														<option value="-1">All Category</option>
														<c:forEach items="${categoryList}" var="catList">
															<c:choose>
																<c:when test="${catList.catId==catId}">
																	<option selected value="${catList.catId}"><c:out
																			value="${catList.catDesc}"></c:out>
																	</option>
																</c:when>
																<c:otherwise>
																	<option value="${catList.catId}">${catList.catDesc}</option>
																</c:otherwise>

															</c:choose>


														</c:forEach>
													</select>

												</div>

												<div class="col-md-4" align="right">
													<input type="submit" class="btn btn-primary" value="Search"
														onclick="searchReport()"> <input type="button"
														value="PDF" class="btn btn-primary"
														onclick="genMrnIssuePdf()" />&nbsp; <input type="button"
														id="expExcel" class="btn btn-primary"
														value="EXPORT TO Excel"
														onclick="tableToExcel('table_grid2', 'name', 'MRN_ISSUE_Yearly_Valuation.xls')">
												</div>


											</div>

											<br> <br>

										</div>
									</form>



								</div>

							</div>

						</div>
					</div>





				</div>


				<div class="row">
					<div class="col-md-12">
						<div class="box" id="todayslist">

							<div class="box-content">

								<div align="center" id="loader2" style="display: none">

									<span>
										<h4>
											<font color="#343690">Loading</font>
										</h4>
									</span> <span class="l-1"></span> <span class="l-2"></span> <span
										class="l-3"></span> <span class="l-4"></span> <span
										class="l-5"></span> <span class="l-6"></span>
								</div>
								<br> <br>
								<div class="clearfix"></div>
								<div
									style="overflow: scroll; height: 100%; width: 100%; overflow: auto"
									id="tbl1">
									<table width="100%" border="0" name="tbl"
										class="table table-bordered table-striped fill-head "
										style="width: 100%" id="table_grid2">
										<thead>
											<tr class="bgpink">
												<th class="col-sm-1"></th>
												<th class="col-md-1"></th>
												<c:forEach items="${reportList}" var="month"
													varStatus="count">
													<th class="col-md-1" colspan="2">${month.dateStr}</th>
												</c:forEach>
											</tr>
											<tr class="bgpink">
												<th class="col-sm-1">Sr No</th>
												<th class="col-md-1">Type</th>
												<c:forEach items="${reportList}" var="category"
													varStatus="count">

													<th class="col-md-1">MRN</th>
													<th class="col-md-1">ISSUE</th>
												</c:forEach>
											</tr>
										</thead>
										<tbody>
											<c:set var="sr" value="0"></c:set>
											<c:forEach items="${reportList}" var="data" varStatus="count"
												end="0">

												<c:forEach items="${data.typeWiseAmount}" var="type"
													varStatus="count">
													<tr>

														<td><c:out value="${sr+1}" /></td>
														<c:set var="sr" value="${sr+1}"></c:set>

														<td><c:out value="${type.typeName}" /></td>

														<c:forEach items="${reportList}" var="month"
															varStatus="count">

															<c:forEach items="${month.typeWiseAmount}" var="type1"
																varStatus="count">

																<c:if test="${type1.typeId==type.typeId}">

																	<td align="right"><c:out
																			value="${type1.mrnAmount}" /></td>
																	<td align="right"><c:out
																			value="${type1.issueAmount}" /></td>

																</c:if>
															</c:forEach>
														</c:forEach>


													</tr>
												</c:forEach>

											</c:forEach>
										</tbody>

									</table>

								</div>

							</div>
						</div>
					</div>
				</div>

				<br>



				<footer>
				<p>2018 © TRAMBAK RUBBER</p>
				</footer>

				<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
					class="fa fa-chevron-up"></i></a>

			</div>
			<!-- END Content -->
		</div>
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

		<!--flaty scripts-->
		<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
		<script
			src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>
		<script type="text/javascript"
			src="https://www.gstatic.com/charts/loader.js"></script>



		<script type="text/javascript">
			function tableToExcel(table, name, filename) {

				let uri = 'data:application/vnd.ms-excel;base64,', template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><title></title><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>', base64 = function(
						s) {
					return window
							.btoa(decodeURIComponent(encodeURIComponent(s)))
				}, format = function(s, c) {
					return s.replace(/{(\w+)}/g, function(m, p) {
						return c[p];
					})
				}

				if (!table.nodeType)
					table = document.getElementById(table)
				var ctx = {
					worksheet : name || 'Worksheet',
					table : table.innerHTML
				}

				var link = document.createElement('a');
				link.download = filename;
				link.href = uri + base64(format(template, ctx));
				link.click();
			}
		</script>

		<script type="text/javascript">
			function genMrnIssuePdf() {
				var fromToYear = $("#fromToYear").val();
				var catId = $("#catId").val();
				window
						.open('${pageContext.request.contextPath}/mrnIssueYearlyReportPdf/'
								+ fromToYear + '/' + catId);
			}
		</script>
</body>
</html>
