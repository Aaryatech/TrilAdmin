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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/flaty.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/flaty-responsive.css">

<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.png">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.buttonload {
    background-color: #fdf3e5; /* Green background */
    border: none; /* Remove borders */
    color: #ec268f; /* White text */
    padding: 12px 20px; /* Some padding */
    font-size: 15px; /* Set a font-size */
    display:none;
}
</style>
<style type="text/css">
@import url('https://fonts.googleapis.com/css?family=Roboto');

body{
  font-family: 'Roboto', sans-serif;
}

h2{
  margin:0px;
  text-transform: uppercase;
}

h6{
  margin:0px;
  color:#777;
}

.wrapper{
 /*  text-align:center;
  margin:50px auto; */
}

.tabs1{
 /*  margin-top:50px; */
  font-size:13px;
  padding:0px;
  list-style:none;
  background:#fff;
  box-shadow:0px 5px 20px rgba(0,0,0,0.1);
  display:inline-block;
  border-radius:50px;
  position:relative;
}

.tabs1 a{
  text-decoration:none;
  color: #777;
  text-transform:uppercase;
  padding:10px 20px;
  display:inline-block;
  position:relative;
  z-index:1;
  transition-duration:0.6s;
}

.tabs1 a.active{
  color:#fff;
}

.tabs1 a i{
  margin-right:5px;
}

.tabs1 .selector1{
  height:100%;
  display:inline-block;
  position:absolute;
  left:0px;
  top:0px;
  z-index:1;
  border-radius:50px;
  transition-duration:0.6s;
  transition-timing-function: cubic-bezier(0.68, -0.55, 0.265, 1.55);
  
  background: #05abe0;
  background: -moz-linear-gradient(45deg, #05abe0 0%, #8200f4 100%);
  background: -webkit-linear-gradient(45deg, #05abe0 0%,#8200f4 100%);
  background: linear-gradient(45deg, #058fe0 0%,#8c8537 100%);
  filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#05abe0', endColorstr='#8200f4',GradientType=1 );
}

</style>
<style type="text/css">
.tabs2{
 /*  margin-top:50px; */
  font-size:12px;
  padding:0px;
  list-style:none;
  background:#fff;
  box-shadow:0px 5px 20px rgba(0,0,0,0.1);
  display:inline-block;
  border-radius:50px;
  position:relative;
}

.tabs2 a{
  text-decoration:none;
  color: #777;
  text-transform:uppercase;
  padding:10px 20px;
  display:inline-block;
  position:relative;
  z-index:1;
  transition-duration:0.6s;
}

.tabs2 a.active{
  color:#fff;
}

.tabs2 a i{
  margin-right:5px;
}

.tabs2 .selector2{
  height:100%;
  display:inline-block;
  position:absolute;
  left:0px;
  top:0px;
  z-index:1;
  border-radius:50px;
  transition-duration:0.6s;
  transition-timing-function: cubic-bezier(0.68, -0.55, 0.265, 1.55);
  
  background: #05abe0;
  background: -moz-linear-gradient(45deg, #05abe0 0%, #8200f4 100%);
  background: -webkit-linear-gradient(45deg, #05abe0 0%,#8200f4 100%);
  background: linear-gradient(45deg, #050ce0 0%,#f45a00 100%);
  filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#05abe0', endColorstr='#8200f4',GradientType=1 );
}
</style>
</head>
<body>
     <c:url var="getPoListRes" value="/getPoListRes"></c:url>

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
<div class="wrapper">
 
  <nav class="tabs1">
    <div class="selector1"></div>
    <a href="#" class="active" onclick="enableDiv(1)" ><i class="fa fa-shopping-cart "></i>Purchase Order</a>
    <a href="#"  onclick="enableDiv(2)" ><i class="fa fa-question-circle"></i>Pending MRN</a>
    <a href="#" onclick="enableDiv(3)"><i class="fa fa-file-pdf-o" ></i>Consumption Report</a>
    <!-- <a href="#"><i class="fab fa-superpowers"></i>Black Panther</a> -->
  </nav>
</div>
<div id="poPending">
<br><div class="col-md-1"></div>
<div class="wrapper">
 
  <nav class="tabs2">
    <div class="selector2"></div>
    <a href="#" class="active" onclick="enableDiv1(1)" ><i class="fa fa-question-circle"></i>Pending PO</a>
    <a href="#"  onclick="enableDiv1(2)" ><i class="fa fa-question-circle"></i>Partial PO </a>
    <a href="#" onclick="enableDiv1(3)"><i class="fa fa-question-circle" ></i>Enquiry</a>
    <!-- <a href="#"><i class="fab fa-superpowers"></i>Black Panther</a> -->
  </nav>
</div>
   <br>  <div id="tabBody0" >
                        	<div class="row">
                            	
                                <div class="col-md-12">
                        		<div class="box" id="todayslist">
							<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Pending PO 
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/addItem">
									</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>
								<div class="box-content">

					
					<div class="clearfix"></div>
					<div class="table-responsive" style="border: 0">
						<table class="table table-advance" id="table1">  
									<thead>
										<tr class="bgpink">
											<th class="col-sm-1">Sr No</th>
											<th class="col-md-1">Indent No.</th>
											<th class="col-md-1">Date</th>
											<th class="col-md-1">Category</th>
											<th class="col-md-1">Indent Type</th>
											<th class="col-md-1">Account Head</th>
											<th class="col-md-1">Is Monthly</th>
											<th class="col-md-1">Status</th>
											 <th class="col-md-1">Action</th> 
										</tr>
									</thead>
									<tbody>

									<c:forEach items="${indentListRes1}" var="indent" varStatus="count">
											<tr>
												<td class="col-sm-1"><c:out value="${count.index+1}" /></td>
												<td class="col-md-2"><c:out value="${indent.indMNo}" /></td>
												<td class="col-md-2"><c:out value="${indent.indMDate}" /></td>
												<td class="col-md-1"><c:out value="${indent.catDesc}" /></td>
												<td class="col-md-1"><c:choose><c:when test="${indent.indMType==1}">Regular</c:when><c:when test="${indent.indMType==2}">Job Work</c:when><c:when test="${indent.indMType==3}">General</c:when></c:choose></td>
 												<td class="col-md-1"><c:out value="${indent.accountHead}" /></td>
 												<td class="col-md-1" style="color: red;">
 												<c:choose><c:when test="${indent.indIsmonthly==1}">YES</c:when><c:when test="${indent.indIsmonthly==0}">NO</c:when></c:choose></td>
 												<td class="col-md-1"><c:out value="Pending" /></td>
										       <%--  <td class="col-md-1">
										        <c:choose>
										        <c:when test="${indent.indDStatus==0}">
										          <c:out value="Pending"/>
										        </c:when>
										       <c:otherwise>
										        <c:out value="closed"/>
										       </c:otherwise>
										        </c:choose>
										      </td> --%>
											<td><a>PO </a><span style="visibility: hidden;" class="glyphicon glyphicon-ok" onclick="submit('+key+');" id="ok'+key+'"></span><span class="glyphicon glyphicon-question-sign"  onclick="del('+key+')" id="del'+key+'"></span></td>
										
											</tr>
										</c:forEach>

										</tbody>

								</table>
  
					</div>
				</div>
							 


						</div>
					
						  </div>
                                
                            </div>
                        </div>
                                 
                              <div class="row" id="tabBody1" style="display: none;">
                        <div class="col-md-12">
                        
                        			<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Partial PO 
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/addItem">
									</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>
 
					<div class="box-content">

					<div class="clearfix"></div>
					<div class="table-responsive" style="border: 1px;border: 1px Solid lightblue;">
						<table class="table table-advance" id="table1">  
									<thead>
										<tr class="bgpink">
										    <th class="col-sm-1">Sr No</th>
											<th class="col-md-1">Indent No.</th>
											<th class="col-md-1">Date</th>
											<th class="col-md-1">Category</th>
											<th class="col-md-1">Indent Type</th>
											<th class="col-md-1">Account Head</th>
											<th class="col-md-1">Is Monthly</th>
											<th class="col-md-1">Status</th>
											 <th class="col-md-1">Action</th> 
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${indentListRes2}" var="indent" varStatus="count">
											<tr>
												<td class="col-sm-1"><c:out value="${count.index+1}" /></td>
												<td class="col-md-2"><c:out value="${indent.indMNo}" /></td>
												<td class="col-md-2"><c:out value="${indent.indMDate}" /></td>
												<td class="col-md-1"><c:out value="${indent.catDesc}" /></td>
												<td class="col-md-1"><c:choose><c:when test="${indent.indMType==1}">Regular</c:when><c:when test="${indent.indMType==2}">Job Work</c:when><c:when test="${indent.indMType==3}">General</c:when></c:choose></td>
 												<td class="col-md-1"><c:out value="${indent.accountHead}" /></td>
 												<td class="col-md-1">
 												<c:choose><c:when test="${indent.indIsmonthly==1}">YES</c:when><c:when test="${indent.indIsmonthly==0}">NO</c:when></c:choose></td>
 												<td class="col-md-1"><c:out value="Pending" /></td>
										       <%--  <td class="col-md-1">
										        <c:choose>
										        <c:when test="${indent.indDStatus==0}">
										          <c:out value="Pending"/>
										        </c:when>
										       <c:otherwise>
										        <c:out value="closed"/>
										       </c:otherwise>
										        </c:choose>
										      </td> --%>
											<td><a>PO </a><span style="visibility: hidden;" class="glyphicon glyphicon-ok" onclick="submit('+key+');" id="ok'+key+'"></span><span class="glyphicon glyphicon-question-sign"  onclick="del('+key+')" id="del'+key+'"></span></td>
										
											</tr>
										</c:forEach>
										</tbody>

								</table>
  
					</div>
				</div>
							 


						</div>
                                </div>
                            </div>
                
                    <div class="row" id="tabBody2" style="display: none;">
                        <div class="col-md-12">
                      
                        			<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Enquiry
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/addItem">
									</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>
 
								<div class="box-content">

					
					<div class="clearfix"></div>
					<div class="table-responsive" style="border: 1px; ">
						<table class="table table-advance" id="mrnTable" >  
									<thead>
										<tr class="bgpink">
										  <th class="col-sm-1">Sr No</th>
											<th class="col-md-1">Indent No.</th>
											<th class="col-md-1">Date</th>
											<th class="col-md-1">Category</th>
											<th class="col-md-1">Indent Type</th>
											<th class="col-md-1">Account Head</th>
											<th class="col-md-1">Is Monthly</th>
											<th class="col-md-1">Status</th>
											 <th class="col-md-1">Action</th> 
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${indentListRes3}" var="indent" varStatus="count">
											<tr>
											<td class="col-sm-1"><c:out value="${count.index+1}" /></td>
												<td class="col-md-2"><c:out value="${indent.indMNo}" /></td>
												<td class="col-md-2"><c:out value="${indent.indMDate}" /></td>
												<td class="col-md-1"><c:out value="${indent.catDesc}" /></td>
												<td class="col-md-1"><c:choose><c:when test="${indent.indMType==1}">Regular</c:when><c:when test="${indent.indMType==2}">Job Work</c:when><c:when test="${indent.indMType==3}">General</c:when></c:choose></td>
 												<td class="col-md-1"><c:out value="${indent.accountHead}" /></td>
 												<td class="col-md-1">
 												<c:choose><c:when test="${indent.indIsmonthly==0}">No</c:when><c:when test="${indent.indIsmonthly==1}">Yes</c:when></c:choose></td>
 												<td class="col-md-1"><c:out value="Pending" /></td>       <%--  <td class="col-md-1">
										        <c:choose>
										        <c:when test="${indent.indDStatus==0}">
										          <c:out value="Pending"/>
										        </c:when>
										       <c:otherwise>
										        <c:out value="closed"/>
										       </c:otherwise>
										        </c:choose>
										      </td> --%>
												<td><a>PO </a><span style="visibility: hidden;" class="glyphicon glyphicon-ok" onclick="submit('+key+');" id="ok'+key+'"></span><span class="glyphicon glyphicon-question-sign"  onclick="del('+key+')" id="del'+key+'"></span></td>
										
											</tr>
										</c:forEach>

										</tbody>

								</table>
  
					</div>
				</div>
						</div>
                                </div>
                            </div>
                  
    </div><br>
    <div id="mrnPending" style="display: none;">
      <div class="row">
                        <div class="col-md-12">
                      <div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Pending MRN
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/addItem">
									</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>
				<div class="box-content">
					<div class="clearfix"></div>
					<div class="table-responsive" style="border: 1px; ">
						<table class="table table-advance" id="mrnTable" >  
									<thead>
													<tr class="bgpink">
											<th class="col-sm-1">Sr No</th>
											<th class="col-md-1">Indent No</th>
											<th class="col-md-1">Date</th>
											<th class="col-md-1">PO No.</th>

											<th class="col-md-1">PO Date</th>
											 <th class="col-md-1">PO Type</th> 
											 <th class="col-md-1">PO Status</th> 
											 <th class="col-md-1">Action</th> 
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${headerList}" var="indent" varStatus="count">
											<tr>
											<td class="col-sm-1"><c:out value="${count.index+1}" /></td>
												<td class="col-md-2"><c:out value="${indent.indNo}" /></td>
												<td class="col-md-2"><c:out value="${indent.indDate}" /></td>
												<td class="col-md-1"><c:out value="${indent.poNo}" /></td>
												<td class="col-md-1"><c:out value="${indent.poDate}" /></td>
												<td class="col-md-1"><c:choose><c:when test="${indent.poType==1}">Regular</c:when><c:when test="${indent.poType==2}">Job Work</c:when><c:when test="${indent.poType==3}">General</c:when></c:choose></td>
												<td class="col-md-1"><c:choose><c:when test="${indent.poStatus==1}">Pending Mrn</c:when></c:choose></td>
												<td><a>MRN </a><span style="visibility: hidden;" class="glyphicon glyphicon-ok" onclick="submit('+key+');" id="ok'+key+'"></span><span class="glyphicon glyphicon-question-sign"  onclick="del('+key+')" id="del'+key+'"></span></td>
										
											</tr>
										</c:forEach>

										</tbody>

								</table>
  
					</div>
				</div>
						</div>
                                </div>
                            </div>
    </div>
    <div id="consumptionReport" style="display: none;">
      <div class="row">
                        <div class="col-md-12">
                      <div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Consumption Report
							</h3>
							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/addItem">
									</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>
				<div class="box-content">
					<div class="clearfix"></div>
					<div class="table-responsive" >
						<table class="table table-advance" id="mrnTable" border="1" style="border-left:1px solid black;">  
									<thead>
									<tr class="bgpink">
										  <th class="col-sm-1"></th>
											<th class="col-md-1"></th>
												<c:forEach items="${categoryList}" var="category" varStatus="count">
											<th class="col-md-1" colspan="2">${category.catDesc}</th>
											</c:forEach>
										</tr>
										<tr class="bgpink">
										  <th class="col-sm-1">Sr No</th>
											<th class="col-md-1">PO Type.</th>
												<c:forEach items="${categoryList}" var="category" varStatus="count">
											
											<th class="col-md-1">Target</th>
											<th class="col-md-1">Value</th>
											</c:forEach>
										</tr>
									</thead>
									<tbody>
                                      <tr>
                                      <td class="col-sm-1"><c:out value="1" /></td>
                                      <td class="col-sm-1"><c:out value="Regular" /></td>
										<c:forEach items="${regularList}" var="regular" varStatus="count">
											
											<td class="col-md-2"><c:out value="${regular.target}" /></td>
												<td class="col-md-1"><c:out value="${regular.totalValue}" /></td>
												
											
										</c:forEach>
                                      </tr>
                                        <tr>
                                       <td class="col-sm-1"><c:out value="2" /></td>
                                      <td class="col-sm-1"><c:out value="Job Work" /></td>
										<c:forEach items="${jobWorkList}" var="jobWork" varStatus="count">
												<td class="col-md-2"><c:out value="${jobWork.target}" /></td>
												<td class="col-md-1"><c:out value="${jobWork.totalValue}" /></td>
												
											
										</c:forEach>
										</tr>
										  <tr>
                                       <td class="col-sm-1"><c:out value="3" /></td>
                                      <td class="col-sm-1"><c:out value="General" /></td>
										<c:forEach items="${generalList}" var="general" varStatus="count">
												<td class="col-md-2"><c:out value="${general.target}" /></td>
												<td class="col-md-1"><c:out value="${general.totalValue}" /></td>
												
										</c:forEach>
										</tr>
										</tbody>

								</table>
  
					</div>
				</div>
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
	<script>window.jQuery || document.write('<script src="${pageContext.request.contextPath}/resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')</script>
	<script src="${pageContext.request.contextPath}/resources/assets/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/jquery-cookie/jquery.cookie.js"></script>

	<!--page specific plugin scripts-->
	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.resize.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.pie.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.stack.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.crosshair.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.tooltip.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/sparkline/jquery.sparkline.min.js"></script>

	<!--flaty scripts-->
	<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>
<script type="text/javascript">

$(document).ready(function() {
  

	  
    var numItems = $('li.fancyTab').length;
		
	
			  if (numItems == 12){
					$("li.fancyTab").width('8.3%');
				}
			  if (numItems == 11){
					$("li.fancyTab").width('9%');
				}
			  if (numItems == 10){
					$("li.fancyTab").width('10%');
				}
			  if (numItems == 9){
					$("li.fancyTab").width('11.1%');
				}
			  if (numItems == 8){
					$("li.fancyTab").width('12.5%');
				}
			  if (numItems == 7){
					$("li.fancyTab").width('14.2%');
				}
			  if (numItems == 6){
					$("li.fancyTab").width('16.666666666666667%');
				}
			  if (numItems == 5){
					$("li.fancyTab").width('20%');
				}
			  if (numItems == 4){
					$("li.fancyTab").width('25%');
				}
			  if (numItems == 3){
					$("li.fancyTab").width('33.3%');
				}
			  if (numItems == 2){
					$("li.fancyTab").width('50%');
				}
		  
	 

	
		});

$(window).load(function() {

  $('.fancyTabs').each(function() {

    var highestBox = 0;
    $('.fancyTab a', this).each(function() {

      if ($(this).height() > highestBox)
        highestBox = $(this).height();
    });

    $('.fancyTab a', this).height(highestBox);

  });
});


</script>
<script type="text/javascript">

function getPoList() {
	  
		var poType = $("#poType").val();
		var status = $("#status").val();
		$('#loader').show();

		$.getJSON('${getPoListRes}',
                     {
			                poType : poType,
							status : status,
							ajax : 'true'

						},
						function(data) {
							
							$('#mrnTable td').remove();
							$('#loader').hide();

							if (data == "") {
								alert("No records found !!");

							}
						  $.each(data,
										function(key, poList) {
							  
											var tr = $('<tr></tr>'); 
										  	tr.append($('<td></td>').html(key+1)); 
										  	tr.append($('<td></td>').html(poList.indNo)); 
										  	tr.append($('<td></td>').html(poList.indDate));
										  	tr.append($('<td></td>').html(poList.poNo));
										 	tr.append($('<td></td>').html(poList.poDate));
										 	var poType;
										 	if(poList.poType==1)
										 		{
										 		poType="Regular";
										 		}
										 	else if(poList.poType==2)
										 		{
										 		poType="Job Work";
										 		}
											else if(poList.poType==3)
									 		{
												poType="General";
									 		}
										 	var poStatus;
										 	if(poList.poStatus==0)
										 		{
										 		poStatus="All";
										 		}
										 	else if(poList.poStatus==1)
										 		{
										 		poStatus="Open";
										 		}
										 	else if(poList.poStatus==2)
									 		{
										 		poStatus="Partial Pending";
									 		}
										 	tr.append($('<td></td>').html(poType));
										 	tr.append($('<td></td>').html(poStatus));
										  	tr.append($('<td></td>').html('<span class="glyphicon glyphicon-edit" id="edit'+key+'" onclick="edit('+key+');"> </span><span style="visibility: hidden;" class="glyphicon glyphicon-ok" onclick="submit('+key+');" id="ok'+key+'"></span><span class="glyphicon glyphicon-remove"  onclick="del('+key+')" id="del'+key+'"></span>'));
										    $('#mrnTable tbody').append(tr);
										})  
						});
}


</script>
<script type="text/javascript">
var tabs = $('.tabs1');
var items = $('.tabs1').find('a').length;
var selector = $(".tabs1").find(".selector1");
var activeItem = tabs.find('.active');
var activeWidth = activeItem.innerWidth();
$(".selector1").css({
  "left": activeItem.position.left + "px", 
  "width": activeWidth + "px"
});

$(".tabs1").on("click","a",function(){
  $('.tabs1 a').removeClass("active");
  $(this).addClass('active');
  var activeWidth = $(this).innerWidth();
  var itemPos = $(this).position();
  $(".selector1").css({
    "left":itemPos.left + "px", 
    "width": activeWidth + "px"
  });
});
</script>
<script type="text/javascript">
var tabs = $('.tabs2');
var items = $('.tabs2').find('a').length;
var selector = $(".tabs2").find(".selector2");
var activeItem = tabs.find('.active');
var activeWidth = activeItem.innerWidth();
$(".selector2").css({
  "left": activeItem.position.left + "px", 
  "width": activeWidth + "px"
});

$(".tabs2").on("click","a",function(){
  $('.tabs2 a').removeClass("active");
  $(this).addClass('active');
  var activeWidth = $(this).innerWidth();
  var itemPos = $(this).position();
  $(".selector2").css({
    "left":itemPos.left + "px", 
    "width": activeWidth + "px"
  });
});
</script>
<script type="text/javascript">
function enableDiv(status) {
	if(status==1){
    var x = document.getElementById("poPending");
    x.style.display = "block";
    var y = document.getElementById("mrnPending");
    y.style.display = "none";
    var z = document.getElementById("consumptionReport");
    z.style.display = "none";
	}
	else if(status==2)
		{
		 var x = document.getElementById("poPending");
		    x.style.display = "none";
		    var y = document.getElementById("mrnPending");
		    y.style.display = "block";
		    var z = document.getElementById("consumptionReport");
		    z.style.display = "none";
		}
	else if(status==3)
	{
		 var x = document.getElementById("poPending");
		    x.style.display = "none";
		    var y = document.getElementById("mrnPending");
		    y.style.display = "none";
		    var z = document.getElementById("consumptionReport");
		    z.style.display = "block";
	}
}
function enableDiv1(status) {
	if(status==1){
    var x = document.getElementById("tabBody0");
    x.style.display = "block";
    var y = document.getElementById("tabBody1");
    y.style.display = "none";
    var z = document.getElementById("tabBody2");
    z.style.display = "none";
	}
	else if(status==2)
		{
		 var x = document.getElementById("tabBody0");
		    x.style.display = "none";
		    var y = document.getElementById("tabBody1");
		    y.style.display = "block";
		    var z = document.getElementById("tabBody2");
		    z.style.display = "none";
		}
	else if(status==3)
	{
		 var x = document.getElementById("tabBody0");
		    x.style.display = "none";
		    var y = document.getElementById("tabBody1");
		    y.style.display = "none";
		    var z = document.getElementById("tabBody2");
		    z.style.display = "block";
	}
}
</script>
</body>
</html>
