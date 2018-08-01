<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ page import="com.ats.exhibition.common.*"%> --%>

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
</head>
<body>


	<!-- BEGIN Navbar -->

	<div id="navbar" class="navbar"
		style="width: 100%; text-align: center; padding: 15px 0px;">
		<button type="button" class="navbar-toggle navbar-btn collapsed"
			data-toggle="collapse" data-target="#sidebar">
			<span class="fa fa-bars"></span>
		</button>

		<!-- BEGIN Navbar Buttons -->

		<!-- BEGIN Button User -->
		<li class="user-profile"><a data-toggle="dropdown" href="#"
			class="user-menu dropdown-toggle"> <i class="fa fa-caret-down"></i>
		</a> <!-- BEGIN User Dropdown -->
			<ul class="dropdown-menu dropdown-navbar" id="user_menu">

				</a></li>

		</ul>
		<!-- BEGIN User Dropdown -->
		</li>
		<!-- END Button User -->
		</ul>
		<!-- END Navbar Buttons -->
	</div>
	<!-- END Navbar -->

	<!-- BEGIN Container -->
	<div class="container" id="main-container">
		<!-- BEGIN Sidebar -->
		<div id="sidebar" class="navbar-collapse collapse">

			<!-- BEGIN Navlist -->
			<a class="navbar-brand" href="#"
				style="width: 100%; text-align: center; padding: 15px 0px;"> <img
				src="${pageContext.request.contextPath}/resources/images/tlogodesigned.jpg"
				style="position: relative;" alt="">
			</a>
			<div style="clear: both;"></div>
			<ul class="nav nav-list">
				<li class="active"><a
					href="${pageContext.request.contextPath}/showStoreDashboard"> <i
						class="fa fa-dashboard"></i> <span>Dashboard</span>
				</a></li>
				<c:choose>
					<c:when test="${Constants.mainAct==1}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Dashboards</span> <b class="arrow fa fa-angle-right"></b>

				</a>
				<!-- BEGIN Submenu -->
				<ul class="submenu">

					<c:choose>
						<c:when test="${Constants.subAct==111}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>

					<a href="${pageContext.request.contextPath}/showStoreDashboard">Store
						Dashboard</a>
					<c:choose>
						<c:when test="${Constants.subAct==111}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/showPurchaseDashboard">Purchase
						Dashboard</a>

					</li>
				</ul>


				<c:choose>
					<c:when test="${Constants.mainAct==1}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Item Master</span> <b class="arrow fa fa-angle-right"></b>

				</a>

				<ul class="submenu">

					<c:choose>
						<c:when test="${Constants.subAct==11}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addCategory">Add
						Category</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==16}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addItemGroup">Add
						Item Group</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==60}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addItemSubGroup">Add
						Item Sub Group</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==59}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addItem">Add Item</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==59}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/getItemList">Item
						List</a>
					</li>

				</ul>
				</li>


				<c:choose>
					<c:when test="${Constants.mainAct==1}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Store Master</span> <b class="arrow fa fa-angle-right"></b>

				</a>
				<!-- BEGIN Submenu -->
				<ul class="submenu">

					<c:choose>
						<c:when test="${Constants.subAct==59}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addAccountHead">Add
						Account Head</a>
					</li>


					<c:choose>
						<c:when test="${Constants.subAct==13}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addDepartment">Add
						Department</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==14}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addSubDepartment">Add
						Sub Department</a>
					</li>


				</ul>
				</li>

				<c:choose>
					<c:when test="${Constants.mainAct==1}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Purchase Master</span> <b class="arrow fa fa-angle-right"></b>

				</a>
				<!-- BEGIN Submenu -->
				<ul class="submenu">


					<c:choose>
						<c:when test="${Constants.subAct==12}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addDispachMode">Add
						Dispach Mode</a>

					</li>


					<c:choose>
						<c:when test="${Constants.subAct==15}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addDeliveryTerm">Add
						Delivery Term</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==59}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addPaymentTerm">Add
						Payment Term</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==59}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addTaxForm">Add Tax
						Form</a>
					</li>



					<c:choose>
						<c:when test="${Constants.subAct==59}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addVendor">Add
						Vendor</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==59}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/vendorList">Vendor
						List</a>
					</li>


				</ul>
				</li>


				<c:choose>
					<c:when test="${Constants.mainAct==1}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Gatepass Item</span> <b class="arrow fa fa-angle-right"></b>

				</a>

				<ul class="submenu">

					<c:choose>
						<c:when test="${Constants.subAct==11}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>

					<a href="${pageContext.request.contextPath}/addGetpassReturnable">Add
						Returnable Gatepass</a>

					<c:choose>
						<c:when test="${Constants.subAct==111}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addGetpassHeader">Add
						Gatepass Nonreturnable</a>

					<c:choose>
						<c:when test="${Constants.subAct==111}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addGetpassItem">Add
						Gatepass Item</a>
					</li>
				</ul>


				<c:choose>
					<c:when test="${Constants.mainAct==2}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>

				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Enquiry</span> <b class="arrow fa fa-angle-right"></b>
				</a>
				<!-- BEGIN Submenu -->
				<ul class="submenu">

					<c:choose>
						<c:when test="${Constants.subAct==21}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addEnquiry">Add
						Enquiry </a>
					</li>
					<c:choose>
						<c:when test="${Constants.subAct==22}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/listOfEnquiry">Enquiry
						List</a>
					</li>


				</ul>
				</li>

				<c:choose>
					<c:when test="${Constants.mainAct==3}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>

				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Indent</span> <b class="arrow fa fa-angle-right"></b>
				</a>
				<!-- BEGIN Submenu -->
				<ul class="submenu">



					<c:choose>
						<c:when test="${Constants.subAct==33}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/showIndent">Add
						Indent</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==31}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/getIndents">Indent
						List</a>
					</li>

				</ul>
				</li>

				<c:choose>
					<c:when test="${Constants.mainAct==3}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>

				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Purchase Order</span> <b class="arrow fa fa-angle-right"></b>
				</a>
				<!-- BEGIN Submenu -->
				<ul class="submenu">

					<c:choose>
						<c:when test="${Constants.subAct==33}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addPurchaseOrder">Add
						Purchase Order</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==33}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/listOfPurachaseOrder">Purchase
						Order List</a>
					</li>

				</ul>
				</li>

				<c:choose>
					<c:when test="${Constants.mainAct==4}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>MRN</span> <b class="arrow fa fa-angle-right"></b>
				</a>
				<!-- BEGIN Submenu -->
				<ul class="submenu">

					<c:choose>
						<c:when test="${Constants.subAct==41}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/showAddMrn">Add MRN</a>
					</li>
					<c:choose>
						<c:when test="${Constants.subAct==42}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/getMrnHeaders">MRN
						List</a>
					<c:choose>
						<c:when test="${Constants.subAct==42}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/showMrnForInspection">Approve
						MRN</a>
					</li>

				</ul>
				</li>

				<c:choose>
					<c:when test="${Constants.mainAct==5}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Issue</span> <b class="arrow fa fa-angle-right"></b>
				</a>
				<!-- BEGIN Submenu -->
				<ul class="submenu">

					<c:choose>
						<c:when test="${Constants.subAct==51}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/addIssueItem">Add
						Issue</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==52}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/issueList">Issue
						List</a>
					</li>

				</ul>
				</li>

				<c:choose>
					<c:when test="${Constants.mainAct==6}">
						<li class="active">
					</c:when> 
					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Stock</span> <b class="arrow fa fa-angle-right"></b>
				</a>
				<!-- BEGIN Submenu -->
				<ul class="submenu">

					<c:choose>
						<c:when test="${Constants.subAct==51}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/getCurrentStock">Current Stock</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==52}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/monthEndStock">Month End</a>
					</li>
					
					<c:choose>
						<c:when test="${Constants.subAct==52}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/stockBetweenDate">Stock Between Date</a>
					</li>

				</ul>
				</li>
				

				<c:choose>
					<c:when test="${Constants.mainAct==5}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Reports</span> <b class="arrow fa fa-angle-right"></b>
				</a>
				<!-- BEGIN Submenu -->
				<ul class="submenu">

					<c:choose>
						<c:when test="${Constants.subAct==51}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/indetReportList">Indent
						Report</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==52}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/poReportList">PO
						Report</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==51}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a
						href="${pageContext.request.contextPath}/rejectionReportVendorwise">Rejection
						Memo Vendorwise Report</a>
					</li>


					<c:choose>
						<c:when test="${Constants.subAct==51}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a
						href="${pageContext.request.contextPath}/rejectionReportItemwise">Rejection
						Memo Itemwise Report</a>
					</li>


					<c:choose>
						<c:when test="${Constants.subAct==52}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/nonReturnableReport">Non
						Returnable Report</a>
					</li>




					<c:choose>
						<c:when test="${Constants.subAct==52}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/returnableReport">Returnable
						Report</a>
					</li>

				</ul>
				</li>


				<c:choose>
					<c:when test="${Constants.mainAct==6}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${Constants.mainAct==5}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Rejection Memo</span> <b class="arrow fa fa-angle-right"></b>
				</a>
				<!-- BEGIN Submenu -->
				<ul class="submenu">

					<c:choose>
						<c:when test="${Constants.subAct==51}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/showRejectionMemo">Add
						Rejection Memo</a>
					</li>

					<c:choose>
						<c:when test="${Constants.subAct==52}">
							<li class="active">
						</c:when>
						<c:otherwise>
							<li>
						</c:otherwise>
					</c:choose>
					<a href="${pageContext.request.contextPath}/listOfRejectionMemo">Rejection
						Memo List</a>
					</li>

				</ul>
				</li>





				<c:choose>
					<c:when test="${Constants.mainAct==6}">
						<li class="active">
					</c:when>

					<c:otherwise>
						<li>
					</c:otherwise>
				</c:choose>
 
				<a href="#" class="dropdown-toggle"> <i class="fa fa-list"></i>
					<span>Logout</span> <b class="arrow fa fa-angle-right"></b>
				</a>

				<!-- BEGIN Submenu -->
				<ul class="submenu">




					<li><a href="${pageContext.request.contextPath}/logout">Logout</a>
					</li>



				</ul>
				<!-- END Submenu -->
				</li>



			</ul>
			<!-- END Navlist -->

			<!-- BEGIN Sidebar Collapse Button -->
			<div id="sidebar-collapse" class="visible-lg">
				<i class="fa fa-angle-double-left"></i>
			</div>
			<!-- END Sidebar Collapse Button -->
		</div>
		<!-- END Sidebar -->


	</div>
	<!-- END Container -->

	<!--basic scripts-->

	<%-- <script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="${pageContext.request.contextPath}/resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')
	</script>
	<script src="${pageContext.request.contextPath}/resources/assets/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/jquery-cookie/jquery.cookie.js"></script>

	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.resize.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.pie.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.stack.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.crosshair.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.tooltip.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/sparkline/jquery.sparkline.min.js"></script>

	<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>
 --%>
</body>
</html>
