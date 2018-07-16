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
    background-color: white; /* Green background */
    border: none; /* Remove borders */
    color: #ec268f; /* White text */
    padding: 12px 20px; /* Some padding */
    font-size: 15px; /* Set a font-size */
    display:none;
}


</style>
<style type="text/css">
.container {
 /*  margin-top:30px; */
}

h1, h2, h3, h4, h5, h6 {
 /*  font-family: 'Source Sans Pro'; */
 /*  font-weight:700; */
}

.fancyTab {
	
	 text-align: center;
     padding:10px 0;
     background-color: #eee;
	 box-shadow: 0 0 0 1px #ddd;
	 top:15px;	
     transition: top .2s;
}

.fancyTab.active {
  top:0;
  transition:top .2s;
}

.whiteBlock {
  display:none;
}

.fancyTab.active .whiteBlock {
  display:block;
  height:2px;
  bottom:-2px;
  background-color:#fff;
  width:99%;
  position:absolute;
  z-index:1;
}

.fancyTab a {
	font-family: 'Source Sans Pro';
	font-size:1.65em;
	font-weight:200;
  transition:.2s;
  color:#333;
}

/*.fancyTab .hidden-xs {
  white-space:nowrap;
}*/

.fancyTabs {
	border-bottom:2px solid #ddd;
  margin: 15px 0 0;
}

li.fancyTab a {
  padding-top: 15px;
  top:-15px;
  padding-bottom:0;
}

li.fancyTab.active a {
  padding-top: inherit;
}

.fancyTab .fa {
  font-size: 20px;
	width:100%;
	padding: 15px 0 5px;
  color:#666;
}

.fancyTab.active .fa {
  color: #cfb87c;
}

.fancyTab a:focus {
	outline:none;
}

.fancyTabContent {
  border-color: transparent;
  box-shadow: 0 -2px 0 -1px #fff, 0 0 0 1px #ddd;
  padding: 30px 15px 15px;
  position:relative;
  background-color:#fff;
}

.nav-tabs > li.fancyTab.active > a, 
.nav-tabs > li.fancyTab.active > a:focus,
.nav-tabs > li.fancyTab.active > a:hover {
	border-width:0;
	height:10px;
}

.nav-tabs > li.fancyTab:hover {
	background-color:#f9f9f9;
	box-shadow: 0 0 0 1px #ddd;
}

.nav-tabs > li.fancyTab.active:hover {
  background-color:#fff;
  box-shadow: 1px 1px 0 1px #fff, 0 0px 0 1px #ddd, -1px 1px 0 0px #ddd inset;
}

.nav-tabs > li.fancyTab:hover a {
	border-color:transparent;
}

.nav.nav-tabs .fancyTab a[data-toggle="tab"] {
  background-color:transparent;
  border-bottom:0;
}

.nav-tabs > li.fancyTab:hover a {
  border-right: 1px solid transparent;
}

.nav-tabs > li.fancyTab > a {
	margin-right:0;
	border-top:0;
  padding-bottom: 30px;
  margin-bottom: -45px;
}

.nav-tabs > li.fancyTab {
	margin-right:0;
	margin-bottom:0;
}

.nav-tabs > li.fancyTab:last-child a {
  border-right: 1px solid transparent;
}

.nav-tabs > li.fancyTab.active:last-child {
  border-right: 0px solid #ddd;
	box-shadow: 0px 2px 0 0px #fff, 0px 0px 0 1px #ddd;
}

.fancyTab:last-child {
  box-shadow: 0 0 0 1px #ddd;
}

.tabs .nav-tabs li.fancyTab.active a {
	box-shadow:none;
  top:0;
}


.fancyTab.active {
  background: #fff;
	box-shadow: 1px 1px 0 1px #fff, 0 0px 0 1px #ddd, -1px 1px 0 0px #ddd inset;
  padding-bottom:30px;
}

.arrow-down {
  display:none;
  width: 0;
  height: 0;
  border-left: 20px solid transparent;
  border-right: 20px solid transparent;
  border-top: 22px solid #ddd;
  position: absolute;
  top: -1px;
  left: calc(50% - 20px);
}

.arrow-down-inner {
  width: 0;
  height: 0;
  border-left: 18px solid transparent;
  border-right: 18px solid transparent;
  border-top: 12px solid #fff;
  position: absolute;
  top: -22px;
  left: -18px;
}

.fancyTab.active .arrow-down {
  display: block;
}

@media (max-width: 1200px) {
  
  .fancyTab .fa {
/*   	font-size: 36px;
 */  }
  
  .fancyTab .hidden-xs {
    font-size:22px;
	}
		
}
	
	
@media (max-width: 992px) {
    
  .fancyTab .fa {
  	/* font-size: 33px; */
  }
    
  .fancyTab .hidden-xs {
  	font-size:13px;
    font-weight:normal;
  }
		
}
	
	
@media (max-width: 768px) {
    
  .fancyTab > a {
    font-size:18px;
  }
    
  .nav > li.fancyTab > a {
    padding:15px 0;
    margin-bottom:inherit;
  }
    
  .fancyTab .fa {
    font-size:15px;
  }
    
  .nav-tabs > li.fancyTab > a {
    border-right:1px solid transparent;
    padding-bottom:0;
  }
    
  .fancyTab.active .fa {
    color: #333;
	}
		
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
<section id="fancyTabWidget" class="tabs t-tabs">
        <ul class="nav nav-tabs fancyTabs" role="tablist" >
        
                    <li class="tab fancyTab active">
                    <div class="arrow-down"><div class="arrow-down-inner"></div></div>	
                        <a id="tab0" href="#tabBody0" role="tab" aria-controls="tabBody0" aria-selected="true" data-toggle="tab" tabindex="0"><span class="fa fa-desktop"><!--  </span><span class="hidden-xs"> -->  Indent Request</span></a>
                    	<div class="whiteBlock"></div>
                    </li>
                    
                    <li class="tab fancyTab">
                    <div class="arrow-down"><div class="arrow-down-inner"></div></div>
                        <a id="tab1" href="#tabBody1" role="tab" aria-controls="tabBody1" aria-selected="true" data-toggle="tab" tabindex="0"><span class="fa fa-question-circle"> <!-- </span><span class="hidden-xs">  --> Pending PO</span></a>
                        <div class="whiteBlock"></div>
                    </li>
                    <li class="tab fancyTab">
                    <div class="arrow-down"><div class="arrow-down-inner"></div></div>
                        <a id="tab5" href="#tabBody5" role="tab" aria-controls="tabBody5" aria-selected="true" data-toggle="tab" tabindex="0"><span class="fa fa-question-circle"><!-- </span><span class="hidden-xs"> -->  Pending MRN</span></a>
                        <div class="whiteBlock"></div>
                    </li>
        </ul>
        <div id="myTabContent" class="tab-content fancyTabContent" aria-live="polite">
                    <div class="tab-pane  fade active in" id="tabBody0" role="tabpanel" aria-labelledby="tab0" aria-hidden="false" tabindex="0">
                        <div>
                        	<div class="row">
                            	
                                <div class="col-md-12">
                        		<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Mechanical
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
											<th class="col-md-1">Item Code</th>
											<th class="col-md-1">Name</th>
											<th class="col-md-1">Item Weight</th>
											<th class="col-md-1">Item UOM</th>
											<th class="col-md-1">Current Stock</th>
											<th class="col-md-1">ROL</th>
											<th class="col-md-1">Max</th>
											 <th class="col-md-1">Action</th> 
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${itemList}" var="itemList" varStatus="count">
											<tr>
												<td class="col-sm-1"><c:out value="${count.index+1}" /></td>
												<td class="col-md-2"><c:out value="${itemList.itemCode}" /></td>
												<td class="col-md-1"><c:out value="${itemList.itemDate}" /></td>
												<td class="col-md-1"><c:out value="${itemList.itemWt}" /></td>
 												<td class="col-md-1"><c:out value="${itemList.itemUom}" /></td>
 												<td><a href="${pageContext.request.contextPath}/editItem/${itemList.itemId}" data-toggle="tooltip" title="Edit"><span
												class="glyphicon glyphicon-edit"></span></a> 
											<a href="${pageContext.request.contextPath}/deleteItem/${itemList.itemId}"
											onClick="return confirm('Are you sure want to delete this record');" data-toggle="tooltip" title="Delete"><span
												class="glyphicon glyphicon-remove"></span></a></td>  
											</tr>
										</c:forEach>
										</tbody>

								</table>
  
					</div>
				</div>
							 


						</div>
						<br>
							<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Electrical
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
											<th class="col-md-1">Item Code</th>
											<th class="col-md-1">Name</th>
											<th class="col-md-1">Item Weight</th>
											<th class="col-md-1">Item UOM</th>
											<th class="col-md-1">Current Stock</th>
											<th class="col-md-1">ROL</th>
											<th class="col-md-1">Max</th>
											 <th class="col-md-1">Action</th> 
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${itemList}" var="itemList" varStatus="count">
											<tr>
												<td class="col-sm-1"><c:out value="${count.index+1}" /></td>
												<td class="col-md-2"><c:out value="${itemList.itemCode}" /></td>
												<td class="col-md-1"><c:out value="${itemList.itemDate}" /></td>
												<td class="col-md-1"><c:out value="${itemList.itemWt}" /></td>
 												<td class="col-md-1"><c:out value="${itemList.itemUom}" /></td>
 												<td><a href="${pageContext.request.contextPath}/editItem/${itemList.itemId}" data-toggle="tooltip" title="Edit"><span
												class="glyphicon glyphicon-edit"></span></a> 
											<a href="${pageContext.request.contextPath}/deleteItem/${itemList.itemId}"
											onClick="return confirm('Are you sure want to delete this record');" data-toggle="tooltip" title="Delete"><span
												class="glyphicon glyphicon-remove"></span></a></td>  
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
                    </div>
                    <div class="tab-pane  fade" id="tabBody1" role="tabpanel" aria-labelledby="tab1" aria-hidden="true" tabindex="0">
                 <c:forEach items="${indentListRes}" var="indentList" varStatus="count">
                                          <div class="box-content">
 <div class="row">
									<div class="col-md-2">Indent No: </div>
									<div class="col-md-2">
								${indentList.indMNo}
									</div>
									
									<div class="col-md-1">Date:</div>
									<div class="col-md-2">
										${indentList.indMDate} 
									</div>
									 <div class="col-md-2">Account Head:</div>
									<div class="col-md-2">
										${indentList.accountHead}  
									</div>
									 
								</div>
							<br>
							<div class="row">
							<div class="col-md-2">Status:</div>
									<div class="col-md-2">
										<c:choose>
										<c:when test="${indentList.indMStatus==0}">
										Pending
										</c:when>
										<c:when test="${indentList.indMStatus==1}">
										Partial Pending
										</c:when>
										</c:choose>
									</div>
							</div></div><br>
                              <div class="row" >
                        <div class="col-md-12">
                        
                        			<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Pending PO Details
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
											<th class="col-md-1">Item Code</th>
											<th class="col-md-1">Name</th>
											<th class="col-md-1">Item UOM</th>
											<th class="col-md-1">Qty</th>
											<th class="col-md-1">Current Stock</th>
											<th class="col-md-1">Date</th>
											<th class="col-md-1">Status</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${indentList.dashIndentDetailList}" var="indent" varStatus="count">
											<tr>
												<td class="col-sm-1"><c:out value="${count.index+1}" /></td>
												<td class="col-md-2"><c:out value="${indent.itemCode}" /></td>
												<td class="col-md-1"><c:out value="${indent.indItemDesc}" /></td>
												<td class="col-md-1"><c:out value="${indent.indItemUom}" /></td>
 												<td class="col-md-1"><c:out value="${indent.indQty}" /></td>
 												<td class="col-md-1"><c:out value="${indent.indItemCurstk}" /></td>
 												<td class="col-md-1"><c:out value="${indent.indItemSchddt}" /></td>
										        <td class="col-md-1">
										        <c:choose>
										        <c:when test="${indent.indDStatus==0}">
										          <c:out value="Pending"/>
										        </c:when>
										          <c:when test="${indent.indDStatus==1}">
										          <c:out value="Enquiry"/>
										        </c:when>
										        <c:when test="${indent.indDStatus==2}">
										          <c:out value="Partial Pending"/>
										        </c:when>
										       <c:otherwise>
										        <c:out value="closed"/>
										       </c:otherwise>
										        </c:choose>
										      </td>
										
											</tr>
										</c:forEach>
										</tbody>

								</table>
  
					</div>
				</div>
							 


						</div>
                                </div>
                            </div><br><br>
                            </c:forEach>
                    </div>
                 
                   
                    <div class="tab-pane  fade" id="tabBody5" role="tabpanel" aria-labelledby="tab5" aria-hidden="true" tabindex="0">
      <div class="row">  	<div class="box-content">

									<div class="col-md-2">PO Type*</div>
									<div class="col-md-2">
									<select class="form-control chosen" name="poType" id="poType"
										required>
										<option value="">Select PO Type</option>
										<option value="1">Regular</option>
										<option value="2">Job Work</option>
										<option value="3">General</option>
									</select>
								</div>
									<div class="col-md-1"></div>
                          	<div class="col-md-2">Status*</div>
									<div class="col-md-2">
									<select class="form-control chosen" name="status" id="status"
										required>
										<option value="">Select Status</option>
										<option value="0">ALL</option>
										<option value="1">Open</option>
										<option value="2">Partially Closed</option>
									</select>
								</div>
<div class="col-md-1"><input type="button" class="btn btn-info" value="Search"
											id="search" onclick="getPoList()"></div>
											<div class="col-md-2"><button class="buttonload" id="loader">
                                   <i class="fa fa-spinner fa-spin"></i>&nbsp;Loading
                                   </button></div>
								</div>
                  </div><br>
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

										<c:forEach items="${itemList}" var="itemList" varStatus="count">
											<tr>
												<td class="col-sm-1"><c:out value="${count.index+1}" /></td>
												<td class="col-md-2"><c:out value="${itemList.itemCode}" /></td>
												<td class="col-md-1"><c:out value="${itemList.itemDate}" /></td>
												<td class="col-md-1"><c:out value="${itemList.itemWt}" /></td>
 												<td class="col-md-1"><c:out value="${itemList.itemUom}" /></td>
 												<td><a href="${pageContext.request.contextPath}/editItem/${itemList.itemId}" data-toggle="tooltip" title="Edit"><span
												class="glyphicon glyphicon-edit"></span></a> 
											<a href="${pageContext.request.contextPath}/deleteItem/${itemList.itemId}"
											onClick="return confirm('Are you sure want to delete this record');" data-toggle="tooltip" title="Delete"><span
												class="glyphicon glyphicon-remove"></span></a></td>  
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
        </div>

    </section>
</div>
<br>
			<!-- BEGIN Tiles -->
			<div class="row">



					<div class="col-md-12">
					<div class="box">
					
		 
						<div class="box-content">
							
							</div><br>
						 
					</div>


				</div>
			</div>


			<footer>
			<p>2018 © AARYATECH SOLUTIONS</p>
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
</body>
</html>
