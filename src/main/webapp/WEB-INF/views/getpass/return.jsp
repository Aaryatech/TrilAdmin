<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/css/datepicker.css" />
<body>


	<c:url var="getQuantity" value="/getQuantity"></c:url>

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
			<div class="page-title">
				<div>
					<h1>
						<i class="fa fa-file-o"></i>Getpass Return
					</h1>
				</div>
			</div>
			<!-- END Page Title -->

			<div class="row">
				<div class="col-md-12">

					<div class="box">
						<div class="box-title">
							<h3>
								<i class="fa fa-table"></i>Getpass Return
							</h3>

							<div class="box-tool">
								<a href="${pageContext.request.contextPath}/listOfGetpassReturn">Return
									Getpass List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a>
							</div>

						</div>


						<div class="box-content">

							<form id="submitMaterialStore"
								action="${pageContext.request.contextPath}/insertGetpassReturn"
								method="post">


								<div class="box-content">

									<div class="col-md-2">Select Getpass Vendor</div>
									<div class="col-md-3">

										<select name="vendId" id="vendId" class="form-control chosen"
											tabindex="6" required>
											<option value="">Select Vendor</option>
											<c:forEach items="${vendorList}" var="vendorList">
												<c:choose>
													<c:when
														test="${vendorList.vendorId==getpassHeaderItemName.gpVendor}">
														<option value="${vendorList.vendorId}" selected>${vendorList.vendorName}</option>
													</c:when>
													<c:otherwise>
														<option value="${vendorList.vendorId}">${vendorList.vendorName}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>

									</div>
									<div class="col-md-2">Getpass No</div>
									<div class="col-md-3">
										<input class="form-control" id="gpNo" placeholder="Getpass No"
											type="text" name="gpNo" value="${getpassHeaderItemName.gpNo}" />
									</div>
									<input type="hidden" name="gpId" id="gpId"
										value="${getpassHeaderItemName.gpId}">

								</div>
								<br>

								<div class="box-content">

									<div class="col-md-2">Return Date*</div>
									<div class="col-md-3">
										<input id="date" class="form-control date-picker"
											placeholder="Return Date" name="date" type="text" required>


									</div>

									<div class="col-md-2">Return No</div>
									<div class="col-md-3">
										<input class="form-control" id="returnNo"
											placeholder="Return No" type="text" name="returnNo" />
									</div>
								</div>
								<br>
								<div class="box-content">



									<div class="col-md-2">Remark</div>
									<div class="col-md-3">
										<input type="text" name="remark" id="remark"
											placeholder="Remark" class="form-control" />

									</div>
								</div>

								<br>
						</div>

						<div class=" box-content">
							<div class="row">
								<div class="col-md-12 table-responsive">
									<table class="table table-bordered table-striped fill-head "
										style="width: 100%" id="table_grid">
										<thead>
											<tr>
												<th>Sr.No.</th>
												<th>Name</th>
												<th>Qty</th>
												<th>Remaining Qty</th>
												<th>Return Qty</th>
												<th>Remark</th>


											</tr>
										</thead>

										<tbody>

											<c:forEach items="${getpassDetailItemName}"
												var="getpassDetailItemName" varStatus="count">
												<tr>
													<td class="col-md-1"><c:out value="${count.index+1}" /></td>


													<td class="col-md-3"><c:out
															value="${getpassDetailItemName.itemCode}" /></td>

													<td class="col-md-2"><input class="form-control"
														id="gpQty${count.index}" placeholder="Qty" type="text"
														name="gpQty${count.index}"
														value="${getpassDetailItemName.gpQty}" Readonly /></td>

													<td class="col-md-2"><input class="form-control"
														id="remQty${count.index}" placeholder=" Rem Qty"
														type="text" name="remQty${count.index}"
														value="${getpassDetailItemName.gpRemQty}" Readonly /></td>


													<td class="col-md-2"><input class="form-control"
														id="retQty${count.index}" placeholder="Return Qty"
														type="text" name="retQty${count.index}" value="0"
														onchange="check(${count.index})"></td>

													<td class="col-md-2"><input class="form-control"
														id="remarkDetail" placeholder="Remark" type="text"
														name="remarkDetail"></td>
												</tr>
											</c:forEach>


										</tbody>
									</table>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-5">
								<input type="submit" class="btn btn-primary" value="Submit">

							</div>
						</div>
						<br> <br>




						</form>
					</div>
				</div>
			</div>
		</div>

		<footer>
			<p>2017 © MONGINIS.</p>
		</footer>
	</div>

	<!-- END Main Content -->


	<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
		class="fa fa-chevron-up"></i></a>

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
		function check(key) {
			
			
			var retQty = $('#retQty'+key).val();
			var remQty = $('#remQty'+key).val();
			
		
			var remQtyRes=remQty-retQty;
			if(remQty <= retQty ){
				 
				document.getElementById("remQty"+key).value = remQtyRes;
			}else
				{
				alert("Please Enter Valid Quanity");
				}
				 
		 }
	</script>


</body>
</html>