<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>TRAMBAK RUBBER</title>
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
			
<div class="container"> 
<div class="wrapper">
 
  <nav class="tabs1">
    <div class="selector1"></div>
    <a href="#" class="active" onclick="enableDiv(1)" ><i class="glyphicon glyphicon-hand-up"></i>Indent Request</a>
    <a href="#"  onclick="enableDiv(2)" ><i class="fa fa-question-circle"></i>Pending PO</a>
    <a href="#" onclick="enableDiv(3)"><i class="fa fa-question-circle" ></i>Pending Mrn</a>
  
  </nav>
</div>
<br>    <div id="indentReq">
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
							  
						</div><br>
						
						<div class="box">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i> Low ROL level Item
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
						<table class="table table-advance" id="itemTable">  
									<thead>
										<tr class="bgpink">
											<th class="col-sm-1">Sr No</th>
											<th class="col-md-1">Item Code</th> 
											<th class="col-md-1">ROL QTY</th> 
											<th class="col-md-1">Closing QTY</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${lowReorderItemList}" var="lowReorderItemList" varStatus="count">
											<tr>
												<td class="col-sm-1"><c:out value="${count.index+1}" /></td>
												<td class="col-md-3"><c:out value="${lowReorderItemList.itemCode}" /></td>
												<td class="col-md-1"><c:out value="${lowReorderItemList.rolLevel}" /></td>
												<td class="col-md-1"><c:out value="${lowReorderItemList.openingStock+lowReorderItemList.approveQty-lowReorderItemList.issueQty+lowReorderItemList.returnIssueQty-lowReorderItemList.damageQty-lowReorderItemList.gatepassQty+lowReorderItemList.gatepassReturnQty}" /></td>
 												 
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
                   
              <div id="pendingPo" style="display: none;">   <c:forEach items="${indentListRes}" var="indentList" varStatus="count">
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
                 
                   <div id="pendingMrn" style="display: none;"> <div class="row" >  	<div class="box-content">

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
										<option value="0">Pending ALL</option>
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
function enableDiv(status) {
	if(status==1){
    var x = document.getElementById("indentReq");
    x.style.display = "block";
    var y = document.getElementById("pendingPo");
    y.style.display = "none";
    var z = document.getElementById("pendingMrn");
    z.style.display = "none";
	}
	else if(status==2)
		{
		 var x = document.getElementById("indentReq");
		    x.style.display = "none";
		    var y = document.getElementById("pendingPo");
		    y.style.display = "block";
		    var z = document.getElementById("pendingMrn");
		    z.style.display = "none";
		}
	else if(status==3)
	{
		 var x = document.getElementById("indentReq");
		    x.style.display = "none";
		    var y = document.getElementById("pendingPo");
		    y.style.display = "none";
		    var z = document.getElementById("pendingMrn");
		    z.style.display = "block";
	}
}
</script>

</body>
</html>
