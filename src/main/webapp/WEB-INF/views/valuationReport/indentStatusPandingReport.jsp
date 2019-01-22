<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<body>

	<c:url var="getStockBetweenDateWithCatId" value="/getStockBetweenDateWithCatId"></c:url>
	<c:url var="getMixingAllListWithDate" value="/getMixingAllListWithDate"></c:url>


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

						<i class="fa fa-file-o"></i>Stock Between Date

					</h1>
				</div>
			</div> --><br>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Indent Status Report
							</h3>
							<div class="box-tool">
								 <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>
						 <form id="submitPurchaseOrder" action="${pageContext.request.contextPath}/indentStatusPandingReport" method="get">
								<div class="box-content">
								
								 
								<div class="box-content">
							
								<div class="col-md-2">From Date</div>
									<div class="col-md-3">
										<input id="fromDate" class="form-control date-picker"
								 placeholder="From Date"   name="fromDate" value="${fromDate}" type="text"  >


									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">To Date</div>
									<div class="col-md-3">
										<input id="toDate" class="form-control date-picker"
								 placeholder="To Date"   name="toDate" value="${toDate}" type="text"  >


									</div>
								
				 
							</div><br>
							
							<div class="box-content">
							
								<div class="col-md-2">Select Type </div>
									<div class="col-md-3">
										 <select name="typeId"   id="typeId"  class="form-control chosen"   required>
											<option value="">Select Type</option>
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
									<div class="col-md-1"></div>
									<div class="col-md-2"> Select Category </div>
									<div class="col-md-3">
										<select id="catId" name="catId" class="form-control chosen" required>
											<option value="">Select Category</option>
											<c:forEach items="${categoryList}" var="cat" >
												<c:choose>
													<c:when test="${cat.catId==catId}">
														<option value="${cat.catId}" selected><c:out value="${cat.catDesc}"/></option>
													</c:when>
													<c:otherwise>
														<option value="${cat.catId}"  ><c:out value="${cat.catDesc}"/></option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>


									</div>
								
				 
							</div><br>
							
							<div class="box-content">
							
								<div class="col-md-2">Select Status </div>
									<div class="col-md-3">
										 <select name="sts"   id="sts"  class="form-control chosen"   required>
											<option value="">Select Status</option>
										 <c:choose>
										 	<c:when test="${sts==1}">
										 		<option value="0" >Approved</option>
												<option value="1" selected>Pending</option>
										 	</c:when>
										 	<c:otherwise>
										 		<option value="0" selected>Approved</option>
												<option value="1"  >Pending</option>
										 	</c:otherwise>
										 </c:choose>
															
														 
										</select> 
									</div>
									 
							</div><br>
							
							 <br>
							
							<div class="row">
							<div class="col-md-12" style="text-align: center">
								<input type="submit" class="btn btn-primary"   value="Search"> 
								  <input type="button" value="PDF" class="btn btn-primary" onclick="genPdf()" /> 
								<!--<input type="button" id="expExcel" class="btn btn-primary" value="EXPORT TO Excel" onclick="exportToExcel();" > -->
							</div>
						</div> <br>
							 
								 
								 
							<div class="col-md-9"></div>
								<label for="search" class="col-md-3" id="search"> <i
									class="fa fa-search" style="font-size: 20px"></i> <input
									type="text" id="myInput" onkeyup="myFunction()"
									placeholder="Search.." title="Type in a name">
								</label> 
					<br /> <br />
					<div class="clearfix"></div>
					<div class="table-responsive" style="border: 0">
						<table class="table table-advance" id="table1">  
									<thead>
									<tr class="bgpink">
										<th style="width:1%;">Sr no.</th>
										<th style="width:7%;">INDENT NO</th>
										<th class="col-md-1">INDENT DATE</th>
										<th class="col-md-3">ITEM DESC</th>  
										<th class="col-md-1">SCH DATE</th>
										<th class="col-md-1">EXPRESS DAYS</th>
										<th class="col-md-1" style="text-align: right;" >INDENT QTY</th> 
										<!-- <th class="col-md-1" style="text-align: right;" >Bal QTY</th>  -->
										<th class="col-md-1" style="text-align: right;" >Rate</th> 
										<th class="col-md-1" style="text-align: right;" >Total</th> 
									</tr>
								</thead>
								<tbody>
								
								<c:set var="sr" value="0"> </c:set>
								<c:set var="total" value="0"> </c:set>
								<c:forEach items="${indentStatusReport}" var="list" varStatus="count">
								  
											<tr>
											 
												<td  ><c:out value="${sr+1}" /></td> 
												<c:set var="sr" value="${sr+1}" ></c:set> 

												<td  ><c:out value="${list.indMNo}" /></td>
												 
												<td  ><c:out value="${list.indMDate}" /></td>
												 
												<td><c:out value="${list.itemCode}" /></td> 
													
											<td><c:out
													value="${list.indItemSchddt}" /></td>
											<td ><c:out
													value="${list.excessDays}" /></td> 
											 <td  align="right"><fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value ="${list.indQty}"/> </td>
											 <%-- <td  align="right"><fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value ="${list.balQty}"/> </td> --%>
											 <td  align="right"><fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value ="${list.rate}"/> </td>
											 <td  align="right"><fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value ="${list.indQty*list.rate}"/> </td>
											 <c:set var="total" value="${total+(list.indQty*list.rate)}"> </c:set>
											 </tr>
										 
										</c:forEach>
										
										<tr>
											 
												<td colspan="8" style="text-align: center;">Total</td>  
											 <td  align="right"><fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value ="${total}"/> </td>
											 </tr>
  
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
 
	<script>
function myFunction() {
  var input, filter, table, tr, td ,td1 ,td2 , i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("table1");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[1]; 
    td1 = tr[i].getElementsByTagName("td")[2]; 
    td2 = tr[i].getElementsByTagName("td")[3]; 
    if (td) {
    	
    	 if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
    	        tr[i].style.display = "";
    	      }
    	 else if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
 	        tr[i].style.display = "";
 	      }
    	 else if (td2.innerHTML.toUpperCase().indexOf(filter) > -1) {
 	        tr[i].style.display = "";
 	      }else {
    	        tr[i].style.display = "none";
    	      }
       
    }  
    
     
  }
}
function genPdf(){
	window.open('${pageContext.request.contextPath}/indentStatusPendingReportPDF/');
}
function exportToExcel()
{
	window.open("${pageContext.request.contextPath}/exportToExcel");
	document.getElementById("expExcel").disabled=true;
}
</script>

</body>
</html>