<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

<body>
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
				<!-- <div>
					<h1>
						<i class="fa fa-file-o"></i>Indent Header
					</h1>

				</div> -->
			</div>
			<br>
			<!-- END Page Title -->



			<!-- BEGIN Main Content -->
			<div class="row">
				<div class="col-md-12">
					<div class="box">
						<div class="box-title">
							<h3>
								<i class="fa fa-bars"></i>Po Status Report
							</h3>
							<div class="box-tool">
								<div class="box-tool">
									<%-- 	<a href="${pageContext.request.contextPath}/showIndent">Add Indent</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>  --%>
								</div>
							</div>

						</div>


						<div class="box-content">
							<form
								action="${pageContext.request.contextPath}/getPoStatusReport"
								class="form-horizontal" id="validation-form" method="post">



								<input type="hidden" name="mode_add" id="mode_add"
									value="add_att">

								<div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">From
										Date</label>
									<div class="col-sm-5 col-lg-3 controls">
										<input class="form-control date-picker" id="from_date"
											size="16" type="text" name="from_date" value="${fromDate}"
											required />
									</div>
									<!-- </div>


								<div class="form-group"> -->
									<label class="col-sm-3 col-lg-2 control-label">To Date</label>
									<div class="col-sm-5 col-lg-3 controls">
										<input class="form-control date-picker" id="to_date" size="16"
											type="text" name="to_date" required value="${toDate}" />
									</div>

								</div>


								<div class="box-content">
									<div class="col-md-1"></div>

									<label class="col-md-1"> Type</label>
									<div class="col-md-3">
										<select name="typeId" id="typeId" data-rule-required="true"
											class="form-control chosen" data-rule-required="true">
											<option value="0">Select All</option>
											<c:forEach items="${typeList}" var="typeList">
												<c:choose>
													<c:when test="${typeList.typeId==typeId}">
														<option selected value="${typeList.typeId}">${typeList.typeName}</option>

													</c:when>
													<c:otherwise>
														<option value="${typeList.typeId}">${typeList.typeName}</option>

													</c:otherwise>

												</c:choose>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-1"></div>

									<label class="col-md-1"> Category </label>
									<div class="col-md-3">
										<select id="catId" name="catId" class="form-control chosen"
											placeholder=" Category" onchange="getInvoiceNo()"
											data-rule-required="true">
											<option value="0">Select All</option>
											<c:forEach items="${categoryList}" var="cat"
												varStatus="count">
												<c:choose>
													<c:when test="${cat.catId==catId}">
														<option selected value="${cat.catId}"><c:out value="${cat.catDesc}"/></option>

													</c:when>
													<c:otherwise>
														<option value="${cat.catId}"><c:out value="${cat.catDesc}"/></option>

													</c:otherwise>

												</c:choose>
											</c:forEach>
										</select>
									</div>


									<div
										class="col-sm-25 col-sm-offset-3 col-lg-30 col-lg-offset-0">
										<input type="submit" value="Submit" class="btn btn-primary">
									</div>

								</div>




								<div class="box-content">
									<div class="col-md-1"></div>

									<label class="col-md-1"> Status</label>
									<div class="col-md-3">
										<select name="po_status" id="po_status"
											data-rule-required="true" class="form-control chosen"
											data-rule-required="true">
											
											<c:choose>
											<c:when test="${poStatus==-1}">
											<option selected value="-1">Select All</option>
											<option value="0">Pending</option>
											<option value="2">Completed</option>
											</c:when>
											
											<c:when test="${poStatus==0}">
											<option  value="-1">Select All</option>
											<option selected value="0">Pending</option>
											<option value="2">Completed</option>
											</c:when>
											
											<c:when test="${poStatus==1}">
											<option  value="-1">Select All</option>
											<option value="0">Pending</option>
											<option value="2">Completed</option>
											</c:when>
											
											<c:when test="${poStatus==2}">
										    <option  value="-1">Select All</option>
											<option value="0">Pending</option>
											<option selected value="2">Completed</option>
											</c:when>
											<c:otherwise>
												<option  value="-1">Select All</option>
											<option value="0">Pending</option>
											<option  value="2">Completed</option>
											
											</c:otherwise>
											
											</c:choose>
											
										</select>
									</div>
									<div class="col-md-1"></div>


									<!-- <div
										class="col-sm-25 col-sm-offset-3 col-lg-30 col-lg-offset-0">
										<input type="submit" value="Submit" class="btn btn-primary">
									</div> -->

								</div>

								<c:set value="0" var="isEdit"></c:set>
								<c:set value="0" var="isDelete"></c:set>

								<c:forEach items="${sessionScope.newModuleList}"
									var="allModuleList">
									<c:choose>
										<c:when
											test="${allModuleList.moduleId==sessionScope.sessionModuleId}">
											<c:forEach items="${allModuleList.subModuleJsonList}"
												var="subModuleJsonList">
												<c:choose>
													<c:when
														test="${subModuleJsonList.subModuleId==sessionScope.sessionSubModuleId}">
														<c:choose>
															<c:when
																test="${subModuleJsonList.editReject eq 'visible'}">
																<c:set value="1" var="isEdit"></c:set>
															</c:when>
														</c:choose>
														<c:choose>
															<c:when
																test="${subModuleJsonList.deleteRejectApprove eq 'visible'}">
																<c:set value="1" var="isDelete"></c:set>
															</c:when>
														</c:choose>
													</c:when>
												</c:choose>
											</c:forEach>
										</c:when>
									</c:choose>

								</c:forEach>


								<div class="clearfix"></div>
								<div id="table-scroll" class="table-scroll">

									<div id="faux-table" class="faux-table" aria="hidden">
										<table id="table2" class="main-table">
											<thead>
												<tr class="bgpink">

												</tr>
											</thead>
										</table>

									</div>
									<div class="col-md-8"></div>

									<div class="input-group">
										<input type="text" id="myInput"
											style="text-align: left; color: green;" class="form-control"
											onkeyup="myFunction()" placeholder="Search  By No " /> <span
											class="input-group-addon"> <i class="fa fa-search"></i>
										</span>
									</div>
									<br />
									<div class="table-wrap">

										<table id="table1" class="table table-advance">
											<thead>
												<tr class="bgpink">

													<th width="3%">Sr No</th>
													<th class="col-md-1">PO No</th>
													<th class="col-md-1">Po Date</th>
													<th class="col-md-1">Indent No</th>
													<th class="col-md-1">Party Name</th>
													<th class="col-md-1">Item Desc</th>
													<th class="col-md-1">Po Qty</th>
													<th class="col-md-1">Recd Qty</th>
													<th class="col-md-1">Bal Qty</th>
													<th class="col-md-1">Rate</th>
													<th width="3%">Action</th>

												</tr>
											</thead>

											<tbody>
												<c:forEach items="${poHeaderStatusList}" var="po"
													varStatus="count">
													<tr>

														<td><c:out value="${count.index+1}" /></td>
														<td><c:out value="${po.poNo}" /></td>
														<td><c:out value="${po.poDate}" /></td>
														<td><c:out value="${po.indentNo}" /></td>

														<td><c:out value="${po.vendorName}" /></td>

														<td><c:out value="${po.itemCode}-${po.itemDesc}" /></td>

														<td><c:out value="${po.poQty}" /></td>

														<td><c:out value="${po.recvQty}" /></td>

														<td><c:out value="${po.balQty}" /></td>
														<td><c:out value="${po.itemRate}" /></td>


														<td><a
															href="${pageContext.request.contextPath}/getPoStatusReportDetail/${po.poDetailId}/${po.itemCode}/${count.index}"
															target="_blank" title="Detail"
															onclick="getDetail1(${po.poDetailId},${po.itemCode});"><span
																class="glyphicon glyphicon glyphicon-file"></span></a></td>
													</tr>
												</c:forEach>

											</tbody>
										</table>


										<br> <br>

									</div>

								</div>

								<div class="box-content">
									<div class="col-md-2">
										<button
											style="background-color: #008CBA; border: none; color: white; text-align: center; text-decoration: none; display: block; font-size: 12px; cursor: pointer; width: 50px; height: 30px; margin: auto;"
											onclick="commonPdf()">PDF</button>
									</div>
									<div class="col-md-2">
										<input type="button" id="expExcel" class="btn btn-primary"
											value="EXPORT TO Excel" onclick="exportToExcel();">
									</div>

								</div>
							</form>
						</div>
					</div>

				</div>

			</div>
			<!-- END Main Content -->
			<footer>
			<p>2018 © Trumbak Rubber.</p>
			</footer>

			<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
				class="fa fa-chevron-up"></i></a>
		</div>
		<!-- END Content -->
	</div>
	<!-- END Container -->

	<!--basic scripts-->


	<script type="text/javascript">
		  /* function genPdf(id) {

			window.open('pdfForReport?url=/pdf/indentPdfDoc/' + id);

		}  */ 
		
		  function getDetail(poDetailId,itemCode) {
		//alert("Hi");
				window.open('getPoStatusReportDetail/' + poDetailId+'/'+'/'+itemCode);

		}  
		
		
		 function commonPdf() {
			window.open('${pageContext.request.contextPath}/getPoStatusReportHeaderPdf');

		} 
	</script>


	<script>
function myFunction() {
  var input, filter, table, tr, td,td1, i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("table1");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[5];
    td1 = tr[i].getElementsByTagName("td")[1];
    td4=tr[i].getElementsByTagName("td")[4];
    	td3=tr[i].getElementsByTagName("td")[3];
    if (td || td1) {
      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      }else if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
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
function exportToExcel()
{
	window.open("${pageContext.request.contextPath}/exportToExcel");
	document.getElementById("expExcel").disabled=true;
}
</script>


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
</body>
</html>