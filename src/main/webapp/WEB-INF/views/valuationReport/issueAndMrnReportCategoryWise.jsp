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

						<i class="fa fa-file-o"></i>Issue & Mrn Summary

					</h1>
				</div>
			</div> --><br>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box" id="todayslist">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Issue & Mrn Summary
							</h3>
							<div class="box-tool">
								<%-- <a href="${pageContext.request.contextPath}/addPurchaseOrder">
									Add PO</a> <a data-action="collapse" href="#"> --%><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>
						 <form id="submitPurchaseOrder" action="${pageContext.request.contextPath}/issueAndMrnReportCategoryWise" method="get">
								<div class="box-content">
								
								 
								<div class="box-content">
							
								<div class="col-md-2">From Date</div>
									<div class="col-md-3">
										<input id="fromDate" class="form-control date-picker"
								 placeholder="From Date"  value="${fromDate}" name="fromDate" type="text"  >


									</div>
									<div class="col-md-1"></div>
									<div class="col-md-2">To Date</div>
									<div class="col-md-3">
										<input id="toDate" class="form-control date-picker"
								 placeholder="To Date" value="${toDate}"  name="toDate" type="text"  >


									</div>
								
				 
							</div><br>
							
							<div class="box-content">

									<div class="col-md-2">Select Type*</div>
									<div class="col-md-3">
										<select class="form-control chosen" name="typeId" id="typeId"
											required>
											<option value="0">All</option>
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
									<div class="col-md-2">Is Development*</div>
									<div class="col-md-3">
										<select class="form-control chosen" name="isDev" id="isDev"
											required>
											<c:choose>
												<c:when test="${isDevelompent==-1}">
													<option value="-1" selected>All</option>
													 <option value="0">No</option>
													 <option value="1">Yes</option>
												</c:when>
												<c:when test="${isDevelompent==0}">
													<option value="-1" >All</option>
													 <option value="0" selected>No</option>
													 <option value="1">Yes</option>
												</c:when>
											
											 <c:when test="${isDevelompent==1}">
													<option value="-1" >All</option>
													 <option value="0"  >No</option>
													 <option value="1"selected>Yes</option>
												</c:when>  
												<c:otherwise>
												<option value="-1" >All</option>
													 <option value="0"  >No</option>
													 <option value="1" >Yes</option>
												</c:otherwise>
												</c:choose>
										</select>

									</div>
								</div><br><br>
							
							<div class="row">
							<div class="col-md-12" style="text-align: center">
								<input type="submit" class="btn btn-info"   value="Search"> 
								<c:choose>
												<c:when test="${fromDate!=null}">
									 
								 
											 
											 
											
											 <input type="button" value="PDF" class="btn btn-primary"
													onclick="genPdf()" />&nbsp;
											 <input type="button" id="expExcel" class="btn btn-primary" value="EXPORT TO Excel" onclick="exportToExcel();" >
											&nbsp;
											   <!--  <input type="button" class="btn search_btn" onclick="showChart()"  value="Graph">  -->
											 
											</c:when></c:choose>
							</div>
						</div> <br>
							 
								
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
								</label> 
					<br /> <br />
					<div class="clearfix"></div>
					<div class="table-responsive" style="border: 0">
						<table class="table table-advance" id="table1">  
									<thead>
									<tr class="bgpink">
										<th style="width:1%;">SR</th>
										<th class="col-md-4">CATEGORY NAME</th> 
										<th class="col-md-1">APPV QTY</th>
										<th class="col-md-1">APPV VALUE</th>
										<th class="col-md-1">ISSUE QTY</th>
										<th class="col-md-1">ISSUE VALUE</th>   
										<th class="col-md-1">Action</th>
									</tr>
								</thead>
								<tbody>
								<c:set var="sr" value="0"> </c:set>
								<c:forEach items="${categoryWiseReport}" var="categoryWiseReport" varStatus="count">
								<c:choose>
												 	<c:when test="${categoryWiseReport.approveQty>0 or categoryWiseReport.approvedQtyValue>0 
												 	or categoryWiseReport.issueQty>0 or categoryWiseReport.issueQtyValue>0}">
											<tr>
											
												 
												<td  ><c:out value="${sr+1}" /></td> 
												<c:set var="sr" value="${sr+1}" ></c:set>

												<td  ><c:out value="${categoryWiseReport.catDesc}" /></td> 
											<td class="col-md-1"><c:out
													value="${categoryWiseReport.approveQty}" /></td>
											<td class="col-md-1"><c:out
													value="${categoryWiseReport.approvedQtyValue}" /></td> 
											<td class="col-md-1"><c:out
													value="${categoryWiseReport.issueQty}" /></td> 
											<td class="col-md-1"><c:out
													value="${categoryWiseReport.issueQtyValue}" /></td> 
											 <td><a href="${pageContext.request.contextPath}/issueAndMrnReportGroupWise/${categoryWiseReport.catId}" class='action_btn'> <abbr title='detailes'> <i class='fa fa-list' ></i></abbr></a>
											
											</tr>
											</c:when>
											 </c:choose>
										</c:forEach>
  
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


	<script type="text/javascript">
	function genPdf(){
		window.open('${pageContext.request.contextPath}/issueAndMrnCategoryWisePDF/');
	}
	function exportToExcel()
	{
		window.open("${pageContext.request.contextPath}/exportToExcel");
		document.getElementById("expExcel").disabled=true;
	}
	function search() {
		  
		
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var catId = $("#catId").val();
		
		if(fromDate=="" || fromDate == null)
			alert("Select From Date");
		else if (toDate=="" || toDate == null)
			alert("Select To Date");
		 
		$('#loader').show();

		$
				.getJSON(
						'${getStockBetweenDateWithCatId}',

						{
							 
							fromDate : fromDate,
							toDate : toDate, 
							catId : catId,
							ajax : 'true'

						},
						function(data) {

							$('#table1 td').remove();
							$('#loader').hide();

							if (data == "") {
								alert("No records found !!");

							}
						 

						  $.each( data,
										function(key, itemList) {
											  
											var tr = $('<tr></tr>'); 
										  	tr.append($('<td></td>').html(key+1));
										  	tr.append($('<td></td>').html(itemList.itemCode));
										  	tr.append($('<td></td>').html(itemList.openingStock));  
										  	tr.append($('<td></td>').html(itemList.opStockValue)); 
										  	tr.append($('<td></td>').html(itemList.approveQty));
										  	tr.append($('<td></td>').html(itemList.approvedQtyValue));
										  	tr.append($('<td></td>').html(itemList.issueQty));
										  	tr.append($('<td></td>').html(itemList.issueQtyValue)); 
										  	tr.append($('<td></td>').html(itemList.damageQty));
										  	tr.append($('<td></td>').html(itemList.damagValue)); 
										  	tr.append($('<td></td>').html(itemList.openingStock+itemList.approveQty-itemList.issueQty+itemList.returnIssueQty-itemList.damageQty-itemList.gatepassQty+itemList.gatepassReturnQty));
											tr.append($('<td></td>').html(itemList.opStockValue+itemList.approvedQtyValue-itemList.issueQtyValue-itemList.damagValue)); 
										  	tr.append($('<td></td>').html("<a href='${pageContext.request.contextPath}/valueationReportDetail/"+itemList.itemId+"/"+itemList.openingStock+"' class='action_btn'> <abbr title='detailes'> <i class='fa fa-list' ></i></abbr>"));
										  	
										    $('#table1 tbody').append(tr); 
										})  
										
							 
						}); 
}
	</script>
	
	<script>
function myFunction() {
  var input, filter, table, tr, td ,td1, i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("table1");
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

</body>
</html>